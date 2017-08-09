iv = {
	init:function(){
		this.home();
		this.getHomeInfo();
		
		$('#btn-add').click(function(){
			iv.add();
		})
		$('#btn-del').click(function(){
			iv.delete();
		})
	},
	home:function(){
		$("#homeGrid").kendoGrid({
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
		            title: "视频编号",
		            width: "120px",
		            field: "Code",
		            encoded: false,
					attributes:{ 'class':'center'}
		        },
		        {
		            title: "视频名称",
		            width: "165px",
		            field: "Name",
		            encoded: false
		        },
		        {
		            title: "所属商家",
		            width: "120px",
		            field: "SellerKeyId",
		            encoded: false,
					attributes:{ 'class':'center'},
					values:sellers
		        },
		        {
		            title: "商家家服网ID",
		            width: "120px",
		            field: "SellerId",
		            encoded: false,
					attributes:{ 'class':'center'}
		        },
		        {
		            title: "省",
		            width: "75px",
		            field: "Province",
		            encoded: false,
					attributes:{ 'class':'center'},
					values: provinces
		        },
		        {
		            title: "城市",
		            width: "75px",
		            field: "City",
		            encoded: false,
					attributes:{ 'class':'center'},
					values: provinces
		        },
		        {
		            title: "区域",
		            width: "75px",
		            field: "Area",
		            encoded: false,
					attributes:{ 'class':'center'},
					values: provinces
		        },
		        {
		            title: "视频分类",
		            width: "100px",
		            field: "CategoryId",
		            encoded: false,
					attributes:{ 'class':'center'},
					values:category
		        },
		        {
		            title: "视频收费",
		            width: "100px",
		            field: "CostFZ",
		            encoded: false,
					attributes:{ 'class':'center'},
					values:costFZ
		        },
		        {
		            title: "到期时间",
		            width: "145px",
		            field: "EndDate",
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
				"autoBind": false,
				"input": true,
				"buttonCount": 10
			},
			autoBind:false,
	        dataSource: createDataSource(basePath + 'admin/recommendVideo/recommendVideoList',{
	        	EndDate: {type: "date", parse: function(value) { return new Date(value);}},
				VedioCount: {type: "number"},
				Province: {type: "number"},
				SellerKeyId:{type: "number"},
				CategoryId:{type: "number"},
				CostFZ:{type: "number"} 
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
	getHomeInfo:function(){
	    var grid = $('#homeGrid').data('kendoGrid');
	    grid.dataSource.filter({});
	},
	add:function(){
		$.mdlg({
			title:'视频清单',
			content:function(){
				return $('#tmpl').html();
			},
			width:'850px',
			onShow:function(){
				$("#videoGrid").kendoGrid({
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
			            title: "视频编号",
			            width: "15%",
			            field: "Code",
			            encoded: false,
						attributes:{ 'class':'center'}
			        },
			        {
			            title: "视频名称",
			            width: "20%",
			            field: "Name",
			            encoded: false,
						attributes:{ 'class':'center'}
			        },
			        {
			            title: "商家名称",
			            width: "10%",
			            field: "SellerName",
			            encoded: false,
						attributes:{ 'class':'center'}
			        },
			        {
			            title: "所属分类",
			            width: "15%",
			            field: "CategoryName",
			            encoded: false,
						attributes:{ 'class':'center'}
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
			        dataSource: createDataSource(basePath + 'admin/video/publishVideoPaged',{
			        	Code: {type: "string"},
			        	Name: {type: "string"},
			        	SellerName:{type:"string"},
			        	CategoryName:{type:"string"}
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
				var grid = $('#videoGrid').data('kendoGrid');
			    grid.dataSource.filter({});
			},
			showCloseButton:false,
			buttons:["保存","取消"],
			buttonStyles:['btn-success','btn-default'],
			onButtonClick:function(sender,modal,index){
				var self = this;
				if(index == 0){
					var params = $("#videoGrid").gridSelectedCols('Id');
					$.post(basePath + 'admin/recommendVideo/save',JSON.stringify(params),function(data){
						if(data.result == true){
							$.mdlg.alert('提示',data.message);
							$(this).closeDialog(modal);
							iv.getHomeInfo();
						}else{
							$.mdlg.error('错误',data.message);
							iv.getHomeInfo();
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
			var params = $("#homeGrid").gridSelectedCols('Id');

			$.post(basePath + 'admin/recommendVideo/delete', JSON.stringify(params), function (data) {
				if (data.result) {
					$.mdlg.alert('提示',data.message);
					iv.getHomeInfo();
				} else {
					$.mdlg.error('错误',data.message);
				}
			}).fail(errors);
		})
	}
}