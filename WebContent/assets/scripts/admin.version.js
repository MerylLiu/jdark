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
				sortable:false,
				template: "<span class='row-number'></span>" 
	        },
	        {
	            title: "版本编号",
	            width: "100px",
	            field: "Code",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "开始时间",
	            width: "100px",
	            field: "StartDate",
	            encoded: false,
				attributes:{ 'class':'center'},
				format:'{0:yyyy-MM-dd HH:mm:ss}'
	        },
	        {
	            title: "结束时间",
	            width: "100px",
	            field: "EndDate",
	            encoded: false,
				attributes:{ 'class':'center'},
				format:'{0:yyyy-MM-dd HH:mm:ss}'
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
	            title: "状态",
	            width: "100px",
	            field: "Status",
	            encoded: false,
	            attributes: {
                    'class': 'center text-danger'
                },
				values: videoStatus
	        },
	        {
	            title: "创建时间",
	            width: "100px",
	            field: "CreateDate",
	            encoded: false,
				attributes:{ 'class':'center'},
				format:'{0:yyyy-MM-dd HH:mm:ss}'
	        }],
			page:1,
	        filterable: true,
	        selectable: "Multiple, Row",
	        scrollable: true,
	        sortable:true,
	        resizable:true,
			pageable: {
				"refresh": true,
				"autoBind": false,
				"input": true,
				"buttonCount": 10
			},
			autoBind:false,
	        dataSource: createDataSource(basePath + 'admin/version/versionList',{
				StartDate: {type: "date", parse: function(value) { return new Date(value);}},
				EndDate: {type: "date", parse: function(value) { return new Date(value);}},
				Province: {type: "number"},
				City: {type: "number"},
				Area: {type: "number"},
				Status:{type:"number"},
				CreateDate: {type: "date", parse: function(value) { return new Date(value);}},
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
			title:'版本',
			content:function(){
				return $('#data-tmpl').html();
			},
			onShow:function(){
				iv.bindDropDownList();
				$('#txt-city').kendoDropDownList({});
				$('#txt-area').kendoDropDownList({});
				
				$('#rdi-status').radioButtonList(status, 'Status', 'Text', 'value', 1);
				
				$('input[name="StartDate"],input[name="EndDate"]').kendoDateTimePicker({
					format:"yyyy-MM-dd HH:mm:ss"
				});

				if(id){
					$.get(basePath + 'admin/version/getVersion',{Id:id},function(data){
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
						
						$('input[name="StartDate"]').kendoDateTimePicker({
							format:"yyyy-MM-dd HH:mm:ss",
							value: new Date(data.StartDate)
						});
						$('input[name="EndDate"]').kendoDateTimePicker({
							format:"yyyy-MM-dd HH:mm:ss",
							value: new Date(data.EndDate)
						});
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
					$.post(basePath + 'admin/version/save',JSON.stringify(params) , function(data) {
						if(data.result == true){
							$.mdlg.alert('提示',data.message);
							$('#form-data').clearForm();
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
		})
	},
	delete:function(){
		$.mdlg.confirm("删除","您确认要删除么？",function(){
			var params = $("#grid").gridSelectedCols('Id');

			$.post(basePath + 'admin/version/delete', JSON.stringify(params), function (data) {
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