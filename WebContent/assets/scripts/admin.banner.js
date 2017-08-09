iv = {
    init: function() {
    	this.bind();
        this.getInfo();

        $("#btn-add").click(function() {
            iv.edit();
        })
        $('#btn-edit').click(function() {
            var param = $("#grid").gridSelectedCols("Id");
            if (param.Id.length > 0) iv.edit(param.Id[0]);
        })
        $('#btn-del').click(function() {
            iv.delete();
        })
    },
    bind: function() {
    	//生成表格
        $("#grid").kendoGrid({
            columns: [ {
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
                    title: "标题",
                    width: "100px",
                    field: "Title",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    }
                },
                {
                    title: "图片路径",
                    width: "100px",
                    field: "ImageUrl",
                    encoded: false,
                    template: "<a class='js-img' href='"+ftpUrl+"#=ImageUrl#'>#=ImageUrl#</a>",
                    attributes: {
                        'class': 'center'
                    }
                },
                {
                    title: "省",
                    width: "100px",
                    field: "Province",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    values: provinces
                },
                {
                    title: "城市",
                    width: "100px",
                    field: "City",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    values: provinces
                },
                {
                    title: "区域",
                    width: "100px",
                    field: "Area",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    values: provinces
                },
                {
                    title: "链接地址",
                    width: "100px",
                    field: "Url",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    }
                },
                {
                    title: "分类",
                    width: "100px",
                    field: "CategoryId",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    values:categorys
                },
                {
                    title: "状态",
                    width: "100px",
                    field: "Status",
                    encoded: false,
                    attributes: {
                        'class': 'center text-danger'
                    },
                    values:bannerStatus
                }
            ],
            page: 1,
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
            autoBind: false,
            //向服务器发送ajax请求，返回的数据对应每个字段的值
            dataSource: createDataSource(basePath + 'admin/banner/bannerList', {
                Title: {
                    type: "string"
                },
                ImageUrl: {
                    type: "string"
                },
                Province: {
                    type: "number"
                },
                City: {
                    type: "number"
                },
                Area: {
                    type: "number"
                },
                Status: {
                    type: "number"
                }
            }),
            dataBound: function() {
                var rows = this.items();
                var page = this.pager.page() - 1;
                var pagesize = this.pager.pageSize();
                $(rows).each(function() {
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
    bindDropDownList:function(){
    	$('#txt-category').kendoDropDownList({
			dataTextField: "text",
			dataValueField: "value",
			optionLabel:'==请选择分类==',
			dataSource: categorys
		});
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
							dataSource: createDataSource(urlArea),
						});
					}
				});
				$('#txt-area').data("kendoDropDownList").setDataSource(null);
			}
		});
    },
    bindUpload:function(){
    	$("#file-upload").uploadify({
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
					$('#img-icon').attr('src',data.url);
					$('#a-img').removeClass('hidden').attr('href',data.url);
					$('#icon-url').val(data.path);
					$("#a-img").fancybox({
	    				'transitionIn'	: 'none',
	    				'transitionOut'	: 'none'	
	    			});
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
    },
    getInfo: function() {
        var grid = $('#grid').data('kendoGrid');
        grid.dataSource.filter({});
    },
    edit: function(id) {
        $.mdlg({
            title: '广告图',
            content: function() {
                return $('#data-tmpl').html();
            },
            onShow: function() {
            	iv.bindDropDownList();
            	$('#txt-city').kendoDropDownList({});
				$('#txt-area').kendoDropDownList({});
				
				iv.bindUpload();
            	$('#sts-status').radioButtonList(status,'Status','text','value',1);
            	
                if (id) {
                    $.get(basePath + 'admin/banner/getBanner', {
                        'id': id
                    }, function(data) {
                        $('#form-banner').formData(data);
                        iv.bindDropDownList();
                        
                        $('#img-icon').attr('src',ftpUrl + data.ImageUrl);
    					$('#a-img').removeClass('hidden').attr('href',ftpUrl + data.ImageUrl);
    					$('#icon-url').val(data.ImageUrl);
                        
    					$("#a-img").fancybox({
    	    				'transitionIn'	: 'none',
    	    				'transitionOut'	: 'none'	
    	    			});
    					
                        var province = $('#txt-province').data("kendoDropDownList");
						province.value(data.Province);
						province.trigger("change");

						var city = $('#txt-city').data("kendoDropDownList");
						city.value(data.City);
						city.trigger("change");
						
						$('#txt-area').data("kendoDropDownList").value(data.Area);
                    });
                }
            },
            showCloseButton: false,
            buttons: ["保存", "取消"],
            buttonStyles: ['btn-success', 'btn-default'],
            //点击事件
            onButtonClick: function(sender, modal, index) {
                var self = this;
                if (index == 0) {
                    var params = $("#form-banner").serializeJson();
                    $.post(basePath + 'admin/banner/save', JSON.stringify(params), function(data) {
                        if (data.result == true){
                            $.mdlg.alert('提示', data.message);
                            iv.getInfo();
                            $(this).closeDialog(modal);
                        } else {
                            $.mdlg.error('错误', data.message);
                        }
                    })
                } else {
                    $(this).closeDialog(modal);
                }
            }
        })
    },
    delete: function() {
        $.mdlg.confirm("删除", "您确认要将所选择的广告图么？", function() {
            var params = $("#grid").gridSelectedCols('Id');
            $.post(basePath + 'admin/banner/delete', JSON.stringify(params), function(data) {
                if (data.result) {
                    $.mdlg.alert('提示', data.message);
                    iv.getInfo();
                } else {
                    $.mdlg.error('错误', data.message);
                }
            }).fail(errors);
        })
    },
}
