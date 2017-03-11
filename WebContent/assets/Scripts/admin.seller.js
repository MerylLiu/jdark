iv = {
	init:function(){
		this.bind();
		this.getInfo();
		
		$('#btn-add,#btn-edit').click(function(){
			iv.edit();
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
				clientTemplete:"#= renderNumber(data) #"
	        },
	        {
	            title: "商家编号",
	            width: "15%",
	            field: "Code",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "商家名称",
	            width: "20%",
	            field: "Name",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "商家家服网ID",
	            width: "15%",
	            field: "SellerId",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "省",
	            width: "10%",
	            field: "Province",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "城市",
	            width: "10%",
	            field: "City",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "区域",
	            width: "10%",
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
				attributes:{ 'class':'center'},
				//values: $.parseJSON($('#role-list').val())
	        },
	        {
	            title: "联系地址",
	            width: "20%",
	            field: "Address",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "视频数",
	            width: "10%",
	            field: "VedioCount",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "录入时间",
	            width: "145px",
	            field: "CreateDate",
	            encoded: false,
				attributes:{ 'class':'center'},
				format:'{0:yyyy-MM-dd HH:mm:ss}'
	        }],
			page:1,
	        filterable: true,
	        selectable: "Multiple, Row",
	        scrollable: false,
			pageable: {
				"refresh": true,
				"autoBind": false,
				"input": true,
				"buttonCount": 10
			},
			autoBind:false,
	        dataSource: createDataSource(basePath + 'admin/seller/sellerList',{
				CreateDate: {type: "date", parse: function(value) { return new Date(value);}},
				VedioCount: {type: "number"},
				Province: {type: "number"} 
			})
	    });	
	},
	getInfo:function(){
	    var grid = $('#grid').data('kendoGrid');
	    grid.dataSource.filter({});
	},
	edit:function(id){
		$.mdlg({
			title:'商家',
			content:function(){
				return $('#data-tmpl').html();
			},
			dialogShow:function(){
				$('#txt-province').kendoDropDownList({
					dataTextField: "text",
                    dataValueField: "value",
                    optionLabel:'请选择省',
                    dataSource: createDataSource(basePath+'region/getRegions'),
                    change:function(){
						var urlCity = basePath+'region/getRegions?parentId=' + $('#txt-province').data('kendoDropDownList').value();

						$('#txt-city').kendoDropDownList({
							dataTextField: "text",
							dataValueField: "value",
							optionLabel:'请选择市',
							dataSource: createDataSource(urlCity),
							change:function(){
								var urlArea = basePath+'region/getRegions?parentId=' + $('#txt-city').data('kendoDropDownList').value();
								
								$('#txt-area').kendoDropDownList({
									dataTextField: "text",
									dataValueField: "value",
									optionLabel:'请选择区／县',
									dataSource: createDataSource(urlArea),
								});
							}
						});
                    }
                });

				$('#txt-city').kendoDropDownList({});
				$('#txt-area').kendoDropDownList({});

				$('#txt-order').kendoNumericTextBox({
					 format: "#",
                     decimals: 0,
                     min: 1,
                     max:99999999,
                     value:1
				});

				$('#txt-set-date').kendoDatePicker({
					format:"yyyy-MM-dd"
				});
			},
			showCloseButton:false,
			otherButtons:["确定","取消"],
			otherButtonStyles:['btn-success','btn-default'],
			clickButton:function(sender,modal,index){
				var self = this;

				if(index == 0){
					var params = $("#form-data").serializeJson();

					$.post(basePath + 'admin/seller/save',JSON.stringify(params) , function(data) {
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
		})
	}
}