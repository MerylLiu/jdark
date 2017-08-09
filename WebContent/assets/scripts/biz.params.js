iv = {
    init: function() {
    	this.bind();
        this.getInfo();
        
        $("#btn-add").click(function() {
            iv.edit();
        })
        
        $('#btn-edit').click(function() {
            var keyParam = $("#grid").gridSelectedCols("KeyCode");
            var valueParam = $("#grid").gridSelectedCols("KeyValue");

            if ((keyParam.KeyCode.length > 0) &&(valueParam.KeyValue.length > 0)){
            	iv.edit(keyParam.KeyCode[0],valueParam.KeyValue[0]);
            }
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
                    title: "参数名",
                    width: "150px",
                    field: "KeyCode",
                    encoded: false,
                    sortable:false
                },
                {
                    title: "参数值",
                    width: "150px",
                    field: "KeyValue",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    }
                },
                {
                    title: "显示文本",
                    width: "15%",
                    field: "Text",
                    encoded: false
                },
               {
                    title: "是否启用",
                    width: "150px",
                    field: "IsEnable",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    template:  "# if (IsEnable == 0) { #" +  
                    "<span>关闭</span>" +  
                    "# }else{ #"+
                   "<span>启用</span> # } #"
                },
                {
                    title: "是否可见",
                    width: "150px",
                    field: "IsVisible",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    template:  "# if (IsVisible ==false) { #" +  
                    "<span>不可见</span>" +  
                    "# }else{ #"+
                   "<span>可见</span> # } #"
                },
                {
                    title: "备注",
                    width: "20%",
                    field: "Remark",
                    encoded: false
                }],
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
            dataSource: createDataSource(basePath + 'admin/bizParams/bizParamsList', {
            	Key: { type: "string" },
                Value: { type: "string" },
                Text: { type: "string" },
                IsEnable: { type: "number" },
                IsVisible: { type: "boolen" },
                OrderNum: { type: "number" },
                Remark:{ type: "string" }
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
            }
        });
    },
    getInfo: function() {
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
			buttonText      : '选择文件',
			fileSizeLimit	: 4000,
			fileTypeDesc  : '支持格式:jpg/gif/jpeg/png/bmp/txt/rar/zip/excel/word/ppt.',
			fileTypeExts  : '*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.txt;*.rar;*.zip;*.excel;*.word;*.ppt',
			onUploadSuccess : function(file, data, response) {
				data = $.parseJSON(data);
				
				if(data.result){
					$('#file-name').html(file.name);
					$('#file-url').val(data.path);
				} else {
					$.mdlg.error('错误',data.message);
				}
			}
		})
    },
    edit: function(key,value) {
    	var beforeKey;
    	var beforeValue;
    	
        $.mdlg({
            title: '业务参数',
            width: '850px',
            content: function() {
                return $('#data-tmpl').html();
            },
            onShow: function() {
            	$('#txt-order').kendoNumericTextBox({
   					format: "#",
					decimals: 0,
					min: 1,
					max:99999999,
					value:1
                });
            	
            	$('input:radio[name="IsFile"]').change(function(){
            		if(this.value==1){
            			$('#file-url').val('');
            			$('#file-name').html('');
            			$('#text').show();
            			$('#file').hide();
            		}else{
            			$('#text-content').val('');
            			$('#text').hide();
            			$('#file').show();
            			iv.bindUpload();
            		}
            	})
            	 
                if (typeof(key)!='undefined' && typeof(value)!='undefined') {
                    $.post(basePath + 'admin/bizParams/getParam', JSON.stringify({
                        'KeyCode': key,'KeyValue':value
                    }), function(data) {
                    	$('#form-dictionary').formData(data);
                    	var regex = '\/.*\/.*\.(jpg|gif|jpeg|png|bmp|txt|rar|zip|excel|word|ppt)?'
                    	if(typeof(data.KeyValue)!='undefined'&&data.KeyValue.match(regex)){
                    		iv.bindUpload();
                    		$('#file').show();
                    		$('#text').hide();
                    		$('#text-content').val('');
                    		var arr = data.KeyValue.split('\/');
                    		$('#file-name').html(arr.pop());
                    		$('input:radio[name="IsFile"]:eq(0)').attr("checked",'checked');
                    	}else{
                    		$('#file-url').val('');
                    		$('#file-name').html('');
                    	}
                    	
                        beforeKey = key;
                        beforeValue = value;
                    });
                }
            },
            showCloseButton: false,
            buttons: ["保存", "取消"],
            buttonStyles: ['btn-success', 'btn-default'],
            onButtonClick: function(sender, modal, index) {
                var self = this;
                if (index == 0) {
                    var params = $("#form-dictionary").serializeJson();
                    params['BeforeValue'] = beforeValue;
                    params['BeforeKey'] = beforeKey;
                    
                    $.post(basePath + 'admin/bizParams/update', JSON.stringify(params), function(data) {
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
        });
    },
    delete: function() {
        $.mdlg.confirm("删除", "您确认要将所选择的业务参数吗？", function() {
        	var keyParam = $("#grid").gridSelectedCols("KeyCode");
            var valueParam = $("#grid").gridSelectedCols("KeyValue");
            var param = {};
            param['KeyCode'] = keyParam.KeyCode;
            param['KeyValue'] = valueParam.KeyValue;
            
            $.post(basePath + 'admin/bizParams/delete',JSON.stringify(param), function(data) {
                if (data.result) {
                    $.mdlg.alert('提示', data.message);
                    iv.getInfo();
                } else {
                    $.mdlg.error('错误', data.message);
                }
            }).fail(errors);
        });
    },
}
