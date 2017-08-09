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
		        width: "80px",
		        field: "Id",
		        encoded: false,
				attributes:{ 'class':'center'},
				filterable:false,
				sortable:false,
				template: "<span class='row-number'></span>" 
		    },   
	        {
	            title: "名称",
	            width: "150px",
	            field: "Name",
	            encoded: false,
				attributes:{ 'class':'center'},
	        },
	        {
	            title: "缩略图",
	            width: "150px",
	            field: "ImageUrl",
	            template: "# if (ImageUrl == null&&typeof(ImageUrl)=='undefined') { #" +  
                "<span></span>" +  
                "# }else{ #"+
                "<a class='js-img' href='"+ftpUrl+"#=ImageUrl#'>#=ImageUrl#</a> # } #",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "创建时间",
	            width: "150px",
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
	        dataSource: createDataSource(basePath + 'admin/template/templateList',{
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
                
                $(".js-img").fancybox({
    				'transitionIn'	: 'none',
    				'transitionOut'	: 'none'	
    			});
			}
	    });	
	},
	getInfo:function(){
	    var grid = $('#grid').data('kendoGrid');
	    grid.dataSource.filter({});
	},
	bindUpload:function(){
    	$('#upload').uploadify({
			height          : 34,
			swf             : basePath + '/assets/scripts/uploadify.swf',
			uploader        : basePath + '/admin/file/upload',
			width           : 120,
			multi		    : false,
			buttonText      : '选择图片',
			fileSizeLimit	: 500,
			fileTypeDesc  : '支持格式:jpg/gif/jpeg/png/bmp.',
			fileTypeExts  : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
			onUploadSuccess : function(file, data, response) {
				data = $.parseJSON(data);
				
				if(data.result){
					$('#image').attr('src',data.url);
					$('#img').removeClass('hidden').attr('href',data.url);
					$('#ImageUrl').val(data.path);
					$("#img").fancybox({
	    				'transitionIn'	: 'none',
	    				'transitionOut'	: 'none'	
	    			});
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
	},
	edit:function(id){
		$.mdlg({
			title:'编辑模板',
			width:'850px',
			content:function(){
				return $('#data-tmpl').html();
			},
			onShow:function(){
				iv.bindUpload();
				if(id){
					$.get(basePath + 'admin/template/getTemplate',{Id:id},function(data){
						$('#form-notice').formData(data);
						$('#editor_id').html(data.Content);
						KindEditor.create('textarea[name="Content"]', {
							uploadJson : basePath + 'admin/template/upload',
							fileManagerJson: basePath + 'admin/template/fileManager',  
							allowFileManager : true,
							resizeType : 1,
							items:[ 'selectall','source', '|', 'undo', 'redo', '|', 'preview', 'template', 'code', 
								'cut', 'copy', 'paste', 'plainpaste', '|', 'justifyleft', 'justifycenter', 
								'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 
								'outdent', 'clearhtml', 'quickformat', '|',
								'table','|','fullscreen' ],
							afterBlur: function(){this.sync();}
						});
						

						if(typeof(data.ImageUrl)!='undefined'){
							$('#image').attr('src',ftpUrl + data.ImageUrl);
	    					$('#img').removeClass('hidden').attr('href',ftpUrl + data.ImageUrl);
	    					$('#ImageUrl').val(data.ImageUrl);
	                        
	    					$("#img").fancybox({
	    	    				'transitionIn'	: 'none',
	    	    				'transitionOut'	: 'none'	
	    	    			});
						}
					});
				}else{
					KindEditor.create('textarea[name="Content"]', {
						uploadJson : basePath + 'admin/template/upload',
						fileManagerJson: basePath + 'admin/template/fileManager',  
						allowFileManager : true,
						resizeType : 1,
						items:['selectall', 'source', '|', 'undo', 'redo', '|', 'preview', 'template', 'code', 
							'cut', 'copy', 'paste', 'plainpaste', '|', 'justifyleft', 'justifycenter', 
							'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 
							'outdent',  'clearhtml', 'quickformat', '|',
							 'table','|','fullscreen' ],
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
					$.post(basePath + 'admin/template/save',JSON.stringify(params) , function(data) {
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
		})
	},
	delete:function(){
		$.mdlg.confirm("删除","您确认要将所选择的模板删除么？",function(){
			var params = $("#grid").gridSelectedCols('Id');

			$.post(basePath + 'admin/template/delete', JSON.stringify(params), function (data) {
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