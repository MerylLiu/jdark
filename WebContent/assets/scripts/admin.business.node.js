iv = {
    init: function() {
    	this.bind();
        this.getInfo();
        //this.chart2();
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
            		width: "100px",
            		field: "Id",
            		encoded: false,
            		attributes:{ 'class':'center'},
					filterable:false,
					sortable:false,
					template: "<span class='row-number'></span>" 
            	},
            	{
                    title: "环节编号",
                    width: "100px",
                    field: "Code",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    }
                },
                {
                    title: "环节名称",
                    width: "100px",
                    field: "Name",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    }
                },
                {
                    title: "档案分类",
                    width: "100px",
                    field: "CategoryId",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    values:categorys
                },
                {
                    title: "档案材料",
                    width: "100px",
                    field: "Material",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    filterable:false,
					sortable:false
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
            dataSource: createDataSource(basePath + 'admin/businessNode/businessList', {
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
    bindDropDownList:function(){
    	$('#chk-area').checkBoxList(materials,{ nameField:'MaterialId',isCheckAll:false });
    },
    getInfo: function() {
        var grid = $('#grid').data('kendoGrid');
        grid.dataSource.filter({});
    },
    edit: function(id) {
        //弹窗
        $.mdlg({
            title: '业务环节',
            width: '850px',
            content: function() {
                return $('#data-tmpl').html();
            },
            //页面需要预加载的内容
            onShow: function() {
            	iv.bindDropDownList();
            	
            	$("#btn-category").click(function(){
            		$.mdlg({
            			title: '档案分类',
            			content: function() {
            				return $('#data-tree').html();
            			},
            			onShow:function(){
            				var setting = {
            			            view: {
            			                showTitle : false
            			            },
            			            check: {
            			            	enable: true,
            			        		chkStyle: "radio",
            			        		radioType: "all"
            			            }
            			        };
            			      var zTreeObj = $.fn.zTree.init($("#tree"), setting, categoryTree);
            			      var treeObj = $.fn.zTree.getZTreeObj("tree");
            			      if(typeof $("#categoryId").val() != 'undefined'){
            			    	  var node = treeObj.getNodeByParam("id", $("#categoryId").val(), null);
                                  treeObj.checkNode(node, true, false);
            			      }
            			},
            			showCloseButton: false,
                        buttons: ["确认", "取消"],
                        buttonStyles: ['btn-success', 'btn-default'],
                        onButtonClick: function(sender, modal, index) {
                            var self = this;
                            if (index == 0) {
                            	var treeObj = $.fn.zTree.getZTreeObj("tree");
                                nodes = treeObj.getCheckedNodes();
                            	if(nodes.length > 0 ){
                            		$("#categoryId").val(nodes[0].id);
                            		$("#categoryName").val(nodes[0].name);
                            	}
                            	$(this).closeDialog(modal);
                            }else{
                            	$(this).closeDialog(modal);
                            }
                        }    
            		})
            	})
            	
                if (id) {
                    $.get(basePath + 'admin/businessNode/getBusiness', {
                        'id': id
                    }, function(data) {
                        $('#form-data').formData(data);
                        iv.bindDropDownList();
                        $.each(data.MaterialId.split(','),function(i,v){
                        	$("input[name='MaterialId'][value='"+v+"']").attr('checked','checked');
                        })
                        $("#categoryId").val(data.CategoryId);
                		$("#categoryName").val(data.CategoryName);
                    });
                }
            },
            showCloseButton: false,
            buttons: ["保存", "取消"],
            buttonStyles: ['btn-success', 'btn-default'],
            onButtonClick: function(sender, modal, index) {
                var self = this;
                if (index == 0) {
                    var params = $("#form-data").serializeJson();
                    $.post(basePath + 'admin/businessNode/save', JSON.stringify(params), function(data) {
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
        $.mdlg.confirm("删除", "您确认要将所选择的环节么？", function() {
            var params = $("#grid").gridSelectedCols('Id');
            $.post(basePath + 'admin/businessNode/delete', JSON.stringify(params), function(data) {
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
