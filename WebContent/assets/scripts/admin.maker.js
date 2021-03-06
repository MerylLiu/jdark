iv = {
	init:function(){
		this.bind();
		this.getInfo();
		
		$('#btn-add').click(function(){
			iv.edit();
		})
		$('#btn-edit').click(function(){
			var param = $("#grid").gridSelectedCols("Id");
			if(param.Id.length >0){
				iv.edit(param.Id[0]);
			}
		})
		$('#btn-del').click(function(){
			iv.delete();
		})
	},
	bind:function(){
		$("#grid").kendoGrid({
	        columns: [
	        {
	            title: "序号",
	            width: "80px",
	            field: "Id",
	            encoded: false,
				attributes:{ 'class':'center'},
				filterable:false,
				template: "<span class='row-number'></span>" 
	        },
	        {
	            title: "制作商编号",
	            width: "150px",
	            field: "Code",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "制作商名称",
	            width: "200px",
	            field: "Name",
	            encoded: false,
				attributes:{ 'class':'left'}
	        },
	        {
	            title: "家服网ID",
	            width: "150px",
	            field: "SellerId",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "省",
	            width: "100px",
	            field: "Province",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "城市",
	            width: "100px",
	            field: "City",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "区域",
	            width: "100px",
	            field: "Area",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "联系电话",
	            width: "100px",
	            field: "Tel",
	            encoded: false,
				attributes:{ 'class':'left'}
	        },
	        {
	            title: "联系地址",
	            width: "200px",
	            field: "Address",
	            encoded: false,
				attributes:{ 'class':'left'}
	        }],
			page:1,
	        filterable: true,
	        selectable: "Multiple, Row",
	        scrollable: true,
	        resizable:true,
			pageable: {
				"refresh": true,
				"autoBind": false,
				"input": true,
				"buttonCount": 10
			},
			autoBind:false,
	        dataSource: createDataSource(basePath + 'admin/maker/makerList',{
				VedioCount: {type: "number"},
				Province: {type: "number"} 
			}),
			dataBound:function(){
				var rows = this.items();  
                var page = this.pager.page() - 1;  
                var pagesize = this.pager.pageSize();  
                $(rows).each(function () {  
                    var index = $(this).index() + 1 + page * pagesize;  
                    var rowLabel = $(this).find(".row-number");  
                    $(rowLabel).html(index);  
                });  	
			}
	    });	
	},
	getInfo:function(){
	    var grid = $('#grid').data('kendoGrid');
	    grid.dataSource.filter({});
	},
	bindDropDownList:function(){
		$('#txt-province').kendoDropDownList({
			dataTextField: "text",
			dataValueField: "value",
			optionLabel:'==请选择省==',
			dataSource: createDataSource(basePath+'region/getRegions'),
			change:function(){
				var urlCity = basePath+'region/getRegions?parentId=' + $('#txt-province').data('kendoDropDownList').value();

				$('#txt-city').kendoDropDownList({
					dataTextField: "text",
					dataValueField: "value",
					optionLabel:'==请选择市==',
					dataSource: createDataSource(urlCity),
					change:function(){
						var urlArea = basePath+'region/getRegions?parentId=' + $('#txt-city').data('kendoDropDownList').value();
						
						$('#txt-area').kendoDropDownList({
							dataTextField: "text",
							dataValueField: "value",
							optionLabel:'==请选择区／县==',
							dataSource: createDataSource(urlArea)
						});
					}
				});
				$('#txt-area').data("kendoDropDownList").setDataSource(null);
			}
		});
	},
	edit:function(id){
		var p;
		$.mdlg({
			title:'制作商',
			content:function(){
				return $('#data-tmpl').html();
			},
			onShow:function(){
				iv.bindDropDownList();
				$('#txt-city').kendoDropDownList({});
				$('#txt-area').kendoDropDownList({});

				$('#txt-order').kendoNumericTextBox({
					 format: "#",
                     decimals: 0,
                     min: 1,
                     max:99999999,
                     value:1
				});


				if(id){
					$.get(basePath + 'admin/maker/getMaker',{Id:id},function(data){
						$('#form-data').formData(data);
						iv.bindDropDownList();
						
						var province = $('#txt-province').data("kendoDropDownList");
						province.value(data.Province);
						province.trigger("change");

						var city = $('#txt-city').data("kendoDropDownList");
						city.value(data.City);
						city.trigger("change");
						
						$('#txt-area').data("kendoDropDownList").value(data.Area);
						p = $("input[name='Code']").val();
					});
				}
			},
			showCloseButton:false,
			buttons:["保存","取消"],
			buttonStyles:['btn-success','btn-default'],
			onButtonClick:function(sender,modal,index){
				var self = this;

				if(index == 0){
					var params = $("#form-data").serializeJson();
					params["BeforeCode"] = p;
					$.post(basePath + 'admin/maker/save',JSON.stringify(params) , function(data) {
						if(data.result == true){
							$.mdlg.alert('提示',data.message);
							$('#form-menu').clearForm();
							iv.getInfo();
							$(this).closeDialog(modal);
						}else{
							$.mdlg.error('错误',data.message);
						}
					})
				}else{
					$(this).closeDialog(modal);
				}
			}
		});
	},
	delete:function(){
		$.mdlg.confirm("删除","您确认要删除么？",function(){
			var params = $("#grid").gridSelectedCols('Id');

			$.post(basePath + 'admin/maker/delete', JSON.stringify(params), function (data) {
				if (data.result) {
					$.mdlg.alert('提示',data.message);
					iv.getInfo();
				} else {
					$.mdlg.error('错误',data.message);
				}
			}).fail(errors);
		})
	}
}