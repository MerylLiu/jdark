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
					template: "<span class='row-number'></span>" 
		        },
		        {
		            title: "商家编号",
		            width: "100px",
		            field: "Code",
		            encoded: false,
					attributes:{ 'class':'center'}
		        },
		        {
		            title: "商家名称",
		            width: "120px",
		            field: "Name",
		            encoded: false,
					attributes:{ 'class':'center'}
		        },
		        {
		            title: "商家家服网ID",
		            width: "100px",
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
					attributes:{ 'class':'center'},
					//values: $.parseJSON($('#role-list').val())
		        },
		        {
		            title: "联系地址",
		            width: "100px",
		            field: "Address",
		            encoded: false,
					attributes:{ 'class':'center'}
		        },
		        {
		            title: "视频数",
		            width: "100px",
		            field: "VedioCount",
		            encoded: false,
					attributes:{ 'class':'center'}
		        },
		        {
		            title: "开店时间",
		            width: "100px",
		            field: "SetUpDate",
		            encoded: false,
					attributes:{ 'class':'center'},
					format:'{0:yyyy-MM-dd}'
		        },
		        {
		            title: "推荐分类",
		            width: "100px",
		            field: "RecommendCategoryId",
		            encoded: false,
					attributes:{ 'class':'center'},
					values:category
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
	        dataSource: createDataSource(basePath + 'admin/recommendSeller/recommendSellerList',{
	        	SetUpDate: {type: "date", parse: function(value) { return new Date(value);}},
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
	getHomeInfo:function(){
	    var grid = $('#homeGrid').data('kendoGrid');
	    grid.dataSource.filter({});
	},
	add:function(){
		$.mdlg({
			title:'商家清单',
			content:function(){
				return $('#tmpl').html();
			},
			width:'850px',
			onShow:function(){
				$("#sellerGrid").kendoGrid({
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
				            title: "商家编号",
				            width: "100px",
				            field: "Code",
				            encoded: false,
							attributes:{ 'class':'center'}
				        },
				        {
				            title: "商家名称",
				            width: "120px",
				            field: "Name",
				            encoded: false,
							attributes:{ 'class':'center'}
				        },
				        {
				            title: "商家家服网ID",
				            width: "100px",
				            field: "SellerId",
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
			        dataSource: createDataSource(basePath + 'admin/seller/sellerList',{
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
				var grid = $('#sellerGrid').data('kendoGrid');
			    grid.dataSource.filter({});
			    $('#recommendCategory').kendoDropDownList({
					dataTextField: "text",
					dataValueField: "value",
					optionLabel:'==请选择推荐分类==',
					dataSource: category
				});
			},
			showCloseButton:false,
			buttons:["保存","取消"],
			buttonStyles:['btn-success','btn-default'],
			onButtonClick:function(sender,modal,index){
				var self = this;
				if(index == 0){
					var params = $("#sellerGrid").gridSelectedCols('Id');
					params['RecommendCategoryId'] = $("#recommendCategory").val();
					$.post(basePath + 'admin/recommendSeller/save',JSON.stringify(params),function(data){
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

			$.post(basePath + 'admin/recommendSeller/delete', JSON.stringify(params), function (data) {
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