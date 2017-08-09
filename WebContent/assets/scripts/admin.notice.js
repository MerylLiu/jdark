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
	            title: "标题",
	            width: "160px",
	            field: "Title",
	            encoded: false,
				attributes:{ 'class':'center'},
	        },
	        {
	            title: "副标题",
	            width: "150px",
	            field: "Subhead",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "作者",
	            width: "70px",
	            field: "UserId",
	            encoded: false,
				attributes:{ 'class':'center'},
				values:users
	        },
	        {
	            title: "分类",
	            width: "70px",
	            field: "CategoryId",
	            encoded: false,
				attributes:{ 'class':'center'},
				values:category
	        },
	        {
	            title: "省",
	            width: "110px",
	            field: "Province",
	            encoded: false,
				attributes:{ 'class':'center'},
				values: provinces
	        },
	        {
	            title: "城市",
	            width: "110px",
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
	            title: "创建时间",
	            width: "135px",
	            field: "CreateDate",
	            encoded: false,
				attributes:{ 'class':'center'},
				format:'{0:yyyy-MM-dd HH:mm:ss}'
	        },
	        {
	        	title: "开始时间",
	        	width: "135px",
	        	field: "StartDate",
	        	encoded: false,
	        	attributes:{ 'class':'center'},
	        	format:'{0:yyyy-MM-dd HH:mm:ss}'
	        },
	        {
	        	title: "结束时间",
	        	width: "135px",
	        	field: "EndDate",
	        	encoded: false,
	        	attributes:{ 'class':'center'},
	        	format:'{0:yyyy-MM-dd HH:mm:ss}'
	        },
	        ],
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
	        dataSource: createDataSource(basePath + 'admin/notice/noticeList',{
	        	CreateDate: {type: "date", parse: function(value) { return new Date(value);}},
	        	StartDate: {
                	type: "date", parse: function(value) { return new Date(value);}
                },
	        	EndDate: {
	        		type: "date", parse: function(value) { return new Date(value);}
	        	},
	        	Title: {type: "string"},
	        	Subhead: {type: "string"},
	        	UserId: {type: "number"},
	        	Status: {type: "number"},
	        	CategoryId:{type: "number"},
	        	Province: {type: "number"},
	        	
	        	
	        	
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
		//分类的加载
		$('#txt-category').kendoDropDownList({
			dataTextField: "text",
			dataValueField: "value",
			optionLabel:'==请选择分类==',
			dataSource: category
		});
		//省市区域的 加载
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
		$.mdlg({
			title:'公告',
			width:'850px',
			content:function(){
				return $('#data-tmpl').html();
			},
			onShow:function(){
				
				// 加载省份 、分类 及下拉图标 
				iv.bindDropDownList();
				$('#txt-city').kendoDropDownList({});
				$('#txt-area').kendoDropDownList({});
				 //新增时对时间的格式进行设置和选择时间的 图标
           	 $('input[name="StartDate"]').kendoDateTimePicker({
           		 format:"yyyy-MM-dd HH:mm:ss"
           	 });
           	 $('input[name="EndDate"]').kendoDateTimePicker({
           		 format:"yyyy-MM-dd HH:mm:ss"
           	 });
				if(id){
					$.get(basePath + 'admin/notice/getNotice',{Id:id},function(data){
						$('#form-data').formData(data);
						// 修改时对省 市县的加载和图标
						iv.bindDropDownList();
						
						var province = $('#txt-province').data("kendoDropDownList");
						province.value(data.Province);
						province.trigger("change");
						
						var city = $('#txt-city').data("kendoDropDownList");
						city.value(data.City);
						city.trigger("change");
						
						$('#txt-area').data("kendoDropDownList").value(data.Area);
						p = $("input[name='Code']").val();
						
						$('#form-notice').formData(data);
						$('#editor_id').html(data.Content);
						KindEditor.create('textarea[name="Content"]', {
							uploadJson : basePath + 'admin/notice/upload',
							fileManagerJson: basePath + 'admin/notice/fileManager',  
							allowFileManager : true,
							resizeType : 1,
							items:[ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 
								'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 
								'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 
								'outdent', 'subscript', 'superscript', 'clearhtml', 'quickformat', 'selectall', '|',
								'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
								'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage', 'flash', 'media', 'insertfile', 
								'table', 'hr', 'emoticons', 'baidumap', 'pagebreak', 'anchor', 'link', 'unlink', '|', 'about','fullscreen' ],
							afterBlur: function(){this.sync();}
						});
						
						 $('input[name="StartDate"]').kendoDateTimePicker({
	                        //转换成时间的样式 
							value:new Date(data.StartDate),
	                        //显示时间格式
							format:"yyyy-MM-dd HH:mm:ss"
						});
						 $('input[name="EndDate"]').kendoDateTimePicker({
							 //转换成时间的样式 
							 value:new Date(data.EndDate),
							 //显示时间格式
							 format:"yyyy-MM-dd HH:mm:ss"
						 });
					});
				}else{
					KindEditor.create('textarea[name="Content"]', {
						uploadJson : basePath + 'admin/notice/upload',
						fileManagerJson: basePath + 'admin/notice/upload',  
						allowFileManager : true,
						resizeType : 1,
						items:[ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 
							'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 
							'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 
							'outdent', 'subscript', 'superscript', 'clearhtml', 'quickformat', 'selectall', '|',
							'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
							'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage', 'flash', 'media', 'insertfile', 
							'table', 'hr', 'emoticons', 'baidumap', 'pagebreak', 'anchor', 'link', 'unlink', '|', 'about','fullscreen' ],
						afterBlur: function(){this.sync();}
					});
				}
			},
			showCloseButton:false,
			buttons:["保存","取消"],
			buttonStyles:['btn-success','btn-default'],
			onButtonClick:function(sender,modal,index){
				var self = this;

				if(index == 0){
					var params = $("#form-notice").serializeJson();
					$.post(basePath + 'admin/notice/save',JSON.stringify(params) , function(data) {
						if(data.result == true){
							$.mdlg.alert('提示',data.message);
							$('#form-notice').clearForm();
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

			$.post(basePath + 'admin/notice/delete', JSON.stringify(params), function (data) {
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