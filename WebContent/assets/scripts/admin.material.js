iv = {
	init:function(){
		this.bind();
		this.getInfo();
		
		$('#btn-add').click(function(){
			iv.edit();
		})
		$('#btn-edit').click(function(){
			var param = $("#grid").gridSelectedCols("Id");
			if(param.Id.length >0) iv.edit(param.Id[0]);
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
		        width: "50px",
		        field: "Id",
		        encoded: false,
				attributes:{ 'class':'center'},
				filterable:false,
				sortable:false,
				template: "<span class='row-number'></span>" 
		    },   
	        {
	            title: "材料编号",
	            width: "160px",
	            field: "Code",
	            encoded: false,
				attributes:{ 'class':'center'},
	        },
	        {
	            title: "材料名称",
	            width: "150px",
	            field: "Name",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	        	title: "材料数量",
	        	width: "150px",
	        	field: "Count",
	        	encoded: false,
	        	attributes:{ 'class':'center'}
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
	        dataSource: createDataSource(basePath + 'admin/material/materialList',{
	        	Code: {type: "string"},
	        	Name: {type: "string"},
	        	Count: {type: "number"},
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
	edit:function(id){
		$.mdlg({
			title:'编辑材料',
			width:'600px',
			content:function(){
				return $('#data-tmpl').html();
			},
			onShow:function(){
				$('input[name="Count"]').kendoNumericTextBox({
					 format: "#",
					 decimals: 0,
					 min: 1,
					 max:999999999,
					 value:1
				});
				if(id){
					$.get(basePath + 'admin/material/getMaterial',{Id:id},function(data){
						$('#form-data').formData(data);
						$('#count').data("kendoNumericTextBox").value(data.Count);
					});
				}
			},
			showCloseButton:false,
			buttons:["保存","取消"],
			buttonStyles:['btn-success','btn-default'],
			onButtonClick:function(sender,modal,index){
				var self = this;

				if(index == 0){
					var params = $("#form-material").serializeJson();
					$.post(basePath + 'admin/material/saveMaterial',JSON.stringify(params) , function(data) {
						if(data.result == true){
							$.mdlg.alert('提示',data.message);
							$('#form-material').clearForm();
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
		$.mdlg.confirm("删除","您确认要将所选择的公告删除么？",function(){
			var params = $("#grid").gridSelectedCols('Id');

			$.post(basePath + 'admin/material/deleteMaterial', JSON.stringify(params), function (data) {
				if (data.result) {
					$.mdlg.alert('提示',data.message);
					iv.getInfo();
				} else {
					$.mdlg.error('错误',data.message);
				}
			});
		})
	}
}