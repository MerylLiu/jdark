iv = {
	init:function(){
		this.home();
		this.getHomeInfo();
		
		$('#btn-add1').click(function(){
			iv.homeEdit();
		})
		$('#btn-del1').click(function(){
			iv.hotspotDelete();
		})
		$('#btn-edit1').click(function(){
			var param = $("#homeGrid").gridSelectedCols("Id");
			if(param.Id.length >0) iv.homeEdit(param.Id[0]);
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
	            sortable:false,
				attributes:{ 'class':'center'},
				filterable:false,
				template: "<span class='row-number'></span>" 
	        },
	        {
	            title: "版本号",
	            width: "100px",
	            field: "VersionId",
	            encoded: false,
				attributes:{ 'class':'center'},
				values:versions
	        },
	        {
	            title: "热点类型",
	            width: "100px",
	            field: "HotspotType",
	            encoded: false,
				attributes:{ 'class':'center'},
				values:hotspotType
	        },
	        {
	            title: "标题",
	            width: "100px",
	            field: "Title",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "省",
	            width: "100px",
	            field: "Province",
	            encoded: false,
				attributes:{ 'class':'center'},
				values:province
	        },
	        {
	            title: "市",
	            width: "100px",
	            field: "City",
	            encoded: false,
				attributes:{ 'class':'center'},
				values:province
	        },
	        {
	            title: "区",
	            width: "100px",
	            field: "Area",
	            encoded: false,
				attributes:{ 'class':'center'},
				values:province
	        },
	        {
	            title: "背景图(小)",
	            width: "100px",
	            field: "BackgroundUrl",
	            template: "# if (BackgroundUrl == null&&typeof(BackgroundUrl)=='undefined') { #" +  
                "<span></span>" +  
                "# }else{ #"+
                "<a class='js-img' href='"+ftpUrl+"#=BackgroundUrl#'>#=BackgroundUrl#</a> # } #",
	            encoded: false,
				attributes:{ 'class':'center'}
	        },
	        {
	            title: "背景图(大)",
	            width: "100px",
	            field: "BackgroundUrl2",
	            template: "# if (BackgroundUrl2 == null&&typeof(BackgroundUrl2)=='undefined') { #" +  
                "<span></span>" +  
                "# }else{ #"+
                "<a class='js-img' href='"+ftpUrl+"#=BackgroundUrl2#'>#=BackgroundUrl2#</a> # } #",
	            encoded: false,
				attributes:{ 'class':'center'}
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
				"autoBind": false,
				"input": true,
				"buttonCount": 10
			},
			autoBind:false,
	        dataSource: createDataSource(basePath + 'admin/hotspot/hotspotList',{
	        	CreateDate: {type: "date", parse: function(value) { return new Date(value);}},
	        	VersionId: {type: "string"},
	        	HotspotType: {type: "string"},
	        	Title:{type:"string"},
	        	Province:{type:"string"},
	        	City:{type:"string"},
	        	Area:{type:"string"},
	        	BackgroundUrl:{type:"string"},
	        	BackgroundUrl2:{type:"string"},
	        	ImageUrl:{type:"string"},
	        	ThumbnailUrl:{type:"string"},
	        	ThumbnailUrl2:{type:"string"},
	        	ThumbnailUrl3:{type:"string"},
	        	ActivityUrl:{type:"string"},
	        	ActivityImages:{type:"string"},
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
	getHomeInfo:function(){
	    var grid = $('#homeGrid').data('kendoGrid');
	    grid.dataSource.filter({});
	},
	bind:function(){
		$("#grid").kendoGrid({
	        columns: [
	        {
	            title: "序号",
	            width: "40px",
	            field: "Id",
	            encoded: false,
	            sortable:false,
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
	            title: "所属分类",
	            width: "10%",
	            field: "CategoryName",
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
	            title: "商家编号",
	            width: "10%",
	            field: "SellerCode",
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
				"autoBind": false,
				"input": true,
				"buttonCount": 10
			},
			autoBind:false,
	        dataSource: createDataSource(basePath + 'admin/video/publishVideoPaged',{
	        	Code: {type: "string"},
	        	Name: {type: "string"},
	        	SellerName:{type:"string"},
	        	CategoryName:{type:"string"},
	        	SellerCode:{type:"string"}
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
	getInfo:function(filter){
		iv.bind();
	    var grid = $('#grid').data('kendoGrid');
	    
	    if(typeof filter === 'undefined'){
			grid.dataSource.filter({});
	    } else {
			grid.dataSource.filter(filter);
	    }
	},
	bindUpload:function(){
    	$('#background-upload').uploadify({
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
					$('#img-background').attr('src',data.url);
					$('#b-img').removeClass('hidden').attr('href',data.url);
					$('#background-url').val(data.path);
					$("#b-img").fancybox({
	    				'transitionIn'	: 'none',
	    				'transitionOut'	: 'none'	
	    			});
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
		
		$('#background-upload2').uploadify({
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
					$('#img-background2').attr('src',data.url);
					$('#b-img2').removeClass('hidden').attr('href',data.url);
					$('#background-url2').val(data.path);
					$("#b-img2").fancybox({
	    				'transitionIn'	: 'none',
	    				'transitionOut'	: 'none'	
	    			});
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
		
		$('#image-upload').uploadify({
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
					$('#img-image').attr('src',data.url);
					$('#a-img').removeClass('hidden').attr('href',data.url);
					$('#image-url').val(data.path);
					$("#a-img").fancybox({
	    				'transitionIn'	: 'none',
	    				'transitionOut'	: 'none'	
	    			});
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
		
		$('#t1-upload').uploadify({
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
					$('#t1-image').attr('src',data.url);
					$('#t1-img').removeClass('hidden').attr('href',data.url);
					$('#t1-url').val(data.path);
					$("#t1-img").fancybox({
	    				'transitionIn'	: 'none',
	    				'transitionOut'	: 'none'	
	    			});
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
		
		$('#t2-upload').uploadify({
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
					$('#t2-image').attr('src',data.url);
					$('#t2-img').removeClass('hidden').attr('href',data.url);
					$('#t2-url').val(data.path);
					$("#t2-img").fancybox({
	    				'transitionIn'	: 'none',
	    				'transitionOut'	: 'none'	
	    			});
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
		
		$('#t3-upload').uploadify({
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
					$('#t3-image').attr('src',data.url);
					$('#t3-img').removeClass('hidden').attr('href',data.url);
					$('#t3-url').val(data.path);
					$("#t3-img").fancybox({
	    				'transitionIn'	: 'none',
	    				'transitionOut'	: 'none'	
	    			});
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
		
		$('#activity-upload').uploadify({
			height          : 34,
			swf             : basePath + '/assets/scripts/uploadify.swf',
			uploader        : basePath + '/admin/file/upload',
			width           : 120,
			multi		    : false,
			buttonText      : '选择图片',
			fileSizeLimit	: 500,
			fileTypeDesc  : '支持格式:jpg/gif/jpeg/png/bmp.',
			fileTypeExts  : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
			onDialogClose : function(queueData){
				$('#form-data').data('index',0);
				$('#form-data').data('total',queueData.filesSelected);
			},
			onUploadSuccess : function(file, data, response) {
				data = $.parseJSON(data);
				var index = $('#form-data').data('index');
				var total = $('#form-data').data('total');
				
				if(parseInt(index)+1==total){
					$('#form-data').data('index',0);
				}
				
				if(index==0){
					$('#activity-background1').attr('src','');
					$('#activity-img1').addClass('hidden').attr('href','');
					$('#activity-url1').val('');
					$('#activity-upload').siblings('input').each(function(i,v){
						if(i!=0){
							$(v).next().remove();
							$(v).remove();
						}
					})
				}
				
				if(data.result){
					var b = true;
					var i = 1;
					while(b){
						if(typeof($('#activity-background'+i).attr('src'))=='undefined' || ($('#activity-background'+i).attr('src'))==''){
							if(i==1){
								$('#activity-background1').attr('src',data.url);
								$('#activity-img1').removeClass('hidden').attr('href',data.url);
								$('#activity-url1').val(data.path);
								$("#activity-img1").fancybox({
				    				'transitionIn'	: 'none',
				    				'transitionOut'	: 'none'	
				    			});
								b=false;
							}else{
								$('#activity-img'+(i-1)).after('<input type="hidden" class="form-control" id="activity-url'+i+'"'+
										'name="ActivityImages"> <a id="activity-img'+i+'" class="hidden" href="">'+
										'<img width="120" height="80" src="" id="activity-background'+i+'"'+
										'style="border: 1px solid #ccc">'+
									'</a>')
								$('#activity-background'+i).attr('src',data.url);
								$('#activity-img'+i).removeClass('hidden').attr('href',data.url);
								$('#activity-url'+i).val(data.path);
								$("#activity-img"+i).fancybox({
				    				'transitionIn'	: 'none',
				    				'transitionOut'	: 'none'	
				    			});	
								b=false;
							}
						}
						i++;
					}
				} else {
					$.mdlg.error('错误',data.message);
				}
				
				$('#form-data').data('index',index+1);
			}
		})
    },
	bindDropDownList:function(){
		$('input[name="VersionId"]').kendoDropDownList({
			dataTextField: "Text",
			dataValueField: "Value",
			optionLabel:'==请选择版本==',
			dataSource: versions
		});
		$('input[name="HotspotType"]').kendoDropDownList({
			dataTextField: "Text",
			dataValueField: "KeyValue",
			optionLabel:'==请选择热点类型==',
			dataSource: hotspotType
		});
		$('input[name="OrderNum"]').kendoNumericTextBox({
			format: "#",
            decimals: 0,
            min: 1,
            max:99999999,
            value:1
		});
		
		$('#txt-city').kendoDropDownList({});
		$('#txt-area').kendoDropDownList({});
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
	homeEdit:function(id){
		$.mdlg({
			title:'活动',
			width:'900px',
			content:function(){
				return $('#data-tmpl').html();
			},
			onShow:function(){
				iv.bindDropDownList();
				iv.bindUpload();
				$('#txt-city,#txt-area').kendoDropDownList({});
				
				if(id){
					$.get(basePath + 'admin/hotspot/getHotspot',{Id:id},function(data){
						$('#form-data').formData(data);
						iv.bindDropDownList();
						
						if(typeof(data.VideoId)!='undefined' && data.VideoId.length>0){
							var arr = new Array();
							$.each(data.VideoId,function(i,v){
								arr.push(v.VideoId);
							})
							
							var filter = new Array();
							filter.push({
								field: "Id",
								operator: 'in',
								value:arr
							});
							
							$("#btn-add").data('filter',filter);
							iv.getInfo(filter);
						}
						
						if(typeof(data.BackgroundUrl)!='undefined'){
							$('#img-background').attr('src',ftpUrl + data.BackgroundUrl);
	    					$('#b-img').removeClass('hidden').attr('href',ftpUrl + data.BackgroundUrl);
	    					$('#background-url').val(data.BackgroundUrl);
	                        
	    					$("#b-img").fancybox({
	    	    				'transitionIn'	: 'none',
	    	    				'transitionOut'	: 'none'	
	    	    			});
						}
						if(typeof(data.BackgroundUrl2)!='undefined'){
							$('#img-background2').attr('src',ftpUrl + data.BackgroundUrl2);
	    					$('#b-img2').removeClass('hidden').attr('href',ftpUrl + data.BackgroundUrl2);
	    					$('#background-url2').val(data.BackgroundUrl2);
	                        
	    					$("#b-img2").fancybox({
	    	    				'transitionIn'	: 'none',
	    	    				'transitionOut'	: 'none'	
	    	    			});
						}
						if(typeof(data.ImageUrl)!='undefined'){
	    					$('#img-image').attr('src',ftpUrl + data.ImageUrl);
	     					$('#a-img').removeClass('hidden').attr('href',ftpUrl + data.ImageUrl);
	     					$('#image-url').val(data.ImageUrl);
	                         
	     					$("#a-img").fancybox({
	     	    				'transitionIn'	: 'none',
	     	    				'transitionOut'	: 'none'	
	     	    			});
						}
						if(typeof(data.ThumbnailUrl)!='undefined'){
	     					$('#t1-image').attr('src',ftpUrl + data.ThumbnailUrl);
	     					$('#t1-img').removeClass('hidden').attr('href',ftpUrl + data.ThumbnailUrl);
	     					$('#t1-url').val(data.ThumbnailUrl);
	                         
	     					$("#t1-img").fancybox({
	     	    				'transitionIn'	: 'none',
	     	    				'transitionOut'	: 'none'	
	     	    			});
						}	
						if(typeof(data.ThumbnailUrl2)!='undefined'){
	     					$('#t2-image').attr('src',ftpUrl + data.ThumbnailUrl2);
	     					$('#t2-img').removeClass('hidden').attr('href',ftpUrl + data.ThumbnailUrl2);
	     					$('#t2-url').val(data.ThumbnailUrl2);
	                         
	     					$("#t2-img").fancybox({
	     	    				'transitionIn'	: 'none',
	     	    				'transitionOut'	: 'none'	
	     	    			});
						}	
						if(typeof(data.ThumbnailUrl3)!='undefined'){
	     					$('#t3-image').attr('src',ftpUrl + data.ThumbnailUrl3);
	     					$('#t3-img').removeClass('hidden').attr('href',ftpUrl + data.ThumbnailUrl3);
	     					$('#t3-url').val(data.ThumbnailUrl3);
	                         
	     					$("#t3-img").fancybox({
	     	    				'transitionIn'	: 'none',
	     	    				'transitionOut'	: 'none'	
	     	    			});
						}	
						
						if(typeof(data.ActivityImages)!='undefined' && data.ActivityImages!=''){
							var arr = data.ActivityImages.split('|');
							$.each(arr,function(i,v){
								if(i==0){
									$('#activity-background1').attr('src',ftpUrl+v);
									$('#activity-img1').removeClass('hidden').attr('href',ftpUrl+v);
									$('#activity-url1').val(v);
									$("#activity-img1").fancybox({
					    				'transitionIn'	: 'none',
					    				'transitionOut'	: 'none'	
					    			})
								}else{
									$('#activity-img'+(i)).after('<input type="hidden" class="form-control" id="activity-url'+(parseInt(i)+1)+'"'+
											'name="ActivityImages"> <a id="activity-img'+(parseInt(i)+1)+'" class="hidden" href="">'+
											'<img width="120" height="80" src="" id="activity-background'+(parseInt(i)+1)+'"'+
											'style="border: 1px solid #ccc">'+
										'</a>')
									$('#activity-background'+(parseInt(i)+1)).attr('src',ftpUrl+v);
									$('#activity-img'+(parseInt(i)+1)).removeClass('hidden').attr('href',ftpUrl+v);
									$('#activity-url'+(parseInt(i)+1)).val(v);
									$("#activity-img"+(parseInt(i)+1)).fancybox({
					    				'transitionIn'	: 'none',
					    				'transitionOut'	: 'none'	
					    			});	
								}	
							})
						}
	    					
						var province = $('#txt-province').data("kendoDropDownList");
						province.value(data.Province);
						province.trigger("change");

						var city = $('#txt-city').data("kendoDropDownList");
						city.value(data.City);
						city.trigger("change");

						$('#txt-area').data("kendoDropDownList").value(data.Area);
						$('#orderNum').data("kendoNumericTextBox").value(data.OrderNum);
						
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
					var gridObj = $("#grid").data("kendoGrid");
					var data = Array();
					if(typeof(gridObj)!='undefined'){
						gridObj.items().each(function() {
							var dataItem = gridObj.dataItem($(this));
							data.push(dataItem['Id']);
						});
					params['VideoId'] = data;
					}
					$.post(basePath + 'admin/hotspot/save',JSON.stringify(params),function(data){
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
	edit:function(){
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
					
					if(params.Id.length>0){
						var filter = new Array();
						
						filter.push({
							field: "Id",
							operator: 'in',
							value:params.Id
						});
						
						var oldFilter = $("#btn-add").data('filter');
						var newFilter = new Array();
						
						if(typeof(oldFilter)!='undefined'){
							var f = new Array();
							$.each(filter[0].value,function(i,v){
								var b = true;
								$.each(oldFilter[0].value,function(ii,vv){
									if(v==vv){
										b=false
									}
								})
								if(b){
									f.push(v)
								}
							})
							newFilter.push({
								field: "Id",
								operator: 'in',
								value:f.concat(oldFilter[0].value)
							});
						}else{
							newFilter = filter;
						}
						
						$("#btn-add").data('filter',newFilter);
						
						if(typeof(filter)!='undefined'){
							iv.getInfo(newFilter);
						}
					}
					$(this).closeDialog(modal);
				}else{
					$(this).closeDialog(modal);
				}
			}
		})
	},
	delete:function(){
		$.mdlg.confirm("删除","您确认要删除么？",function(){
			var params = $("#grid").gridSelectedCols('Id');
			var oldFilter = $("#btn-add").data('filter');
			var arr = new Array();
			$.each(oldFilter[0].value,function(i,v){
				var b = true;
				$.each(params.Id,function(ii,vv){
					if(v==vv){
						b=false;
					}
				})
				if(b){
					arr.push(v);
				}
			})
			var filter = new Array();
			filter.push({
				field: "Id",
				operator: 'in',
				value:arr
			});
			
			$("#btn-add").data('filter',filter);
			
			if(filter[0].value.length>0){
				iv.getInfo(filter);
			}else{
				var newfilter = new Array();
				newfilter.push({
					field: "Id",
					operator: 'eq',
					value:arr
				});
				iv.getInfo(newfilter);
			}
		})
	},
	hotspotDelete:function(){
		$.mdlg.confirm("删除","您确认要删除么？",function(){
			var params = $("#homeGrid").gridSelectedCols('Id');

			$.post(basePath + 'admin/hotspot/delete', JSON.stringify(params), function (data) {
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