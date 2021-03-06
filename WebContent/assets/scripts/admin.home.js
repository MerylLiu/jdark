iv = {
	init:function(){
		this.bind();
		this.getInfo();
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
	            title: "用户名",
	            width: "200px",
	            field: "UserName",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
            	title: "登录时间",
            	width: "150px",
            	field: "LoginDate",
            	encoded: false,
            	attributes: {'class': 'center'},
            	format:'{0:yyyy-MM-dd HH:mm:ss}'
            }],
			page:1,
	        filterable: true,
	        selectable: "Multiple, Row",
	        scrollable: false,
	        resizable:true,
			pageable: {
				"refresh": true,
				"autoBind": false,
				"input": true,
				"buttonCount": 10
			},
			autoBind:false,
	        dataSource: createDataSource(basePath + 'sys/logLogin/getLogLoginLatest',{
	        	Id: {
                    type: "number"
                },
                UserName: {
                	type: "string"
                },
                LoginDate: {
                	type: "date", parse: function(value) { return new Date(value);}
                },
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
	getInfo : function() {
		var grid = $('#grid').data('kendoGrid');
		grid.dataSource.filter({});
	},
}