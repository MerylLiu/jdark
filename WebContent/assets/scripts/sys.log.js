iv = {
	init:function(){
		this.bind();
		this.getInfo();
		$("#txt-del-all").click(function(){
			iv.deleteAll();
		});
		$("#txt-del").click(function(){
			iv.delete();
		});
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
	            title: "用户编号",
	            width: "100px",
	            field: "UserCode",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "用户名称",
	            width: "100px",
	            field: "UserName",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "IP地址",
	            width: "100px",
	            field: "IPAddress",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "操作类型",
	            width: "100px",
	            field: "OperationType",
	            encoded: false,
				attributes:{ 'class':'center'},
				values:operationType
	        },
	        {
	        	title: "操作内容",
	        	width: "150px",
	        	field: "Operation",
	        	encoded: false,
	        	attributes:{ 'class':'center'}
	        },
	        {
	            title: "操作时间",
	            width: "100px",
	            field: "CreateDate",
	            encoded: false,
				attributes:{ 'class':'center'},
				format:'{0:yyyy-MM-dd HH:mm:ss}'
	        },
	        {
	            title: "备注",
	            width: "100px",
	            field: "Remark",
	            encoded: false,
				attributes:{ 'class':'center'},
				template: "# if (typeof Remark != 'undefined'){# "+"<a href='javascript:void(0);' " +
	 				"onclick='iv.viewDetail(this)'>" +
	 				"#=Remark#</a>"+"#}#"
	        }],
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
	        dataSource: createDataSource(basePath + 'sys/log/logList',{
	        	CreateDate: {type: "date", parse: function(value) { return new Date(value);}}
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
	viewDetail:function(obj){
		var content = obj.innerHTML;
		content = content.replace(/\n/g, "<br>");
		content = content.replace(/,/g, "<br>");
		content = content.replace(/，/g, "<br>");
		$.mdlg.alert("备注",content);
	},
	delete:function(){
		$.mdlg.confirm("删除","您确认要删除么？",function(){
			var params = $("#grid").gridSelectedCols('Id');

			$.post(basePath + 'sys/log/delete', JSON.stringify(params), function (data) {
				if (data.result) {
					$.mdlg.alert('提示',data.message);
					iv.getInfo();
				} else {
					$.mdlg.error('错误',data.message);
				}
			}).fail(errors);
		});
	},
	deleteAll:function(){
		$.mdlg.confirm("清空","您确认要删除所有日志吗？",function(){

			$.post(basePath + 'sys/log/deleteAll', function (data) {
				if (data.result) {
					$.mdlg.alert('提示',data.message);
					iv.getInfo();
				} else {
					$.mdlg.error('错误',data.message);
				}
			}).fail(errors);
		});
	}
}