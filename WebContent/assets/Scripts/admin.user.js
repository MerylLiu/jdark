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
            		width: "80px",
            		field: "Id",
            		encoded: false,
            		attributes:{ 'class':'center'},
					filterable:false,
					template: "<span class='row-number'></span>" 
            	},
            	{
                    title: "用户编码",
                    width: "80px",
                    field: "Code",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                    filterable: false,
                },
                {
                    title: "账号",
                    width: "15%",
                    field: "LoginName",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },

                },
                {
                    title: "用户名",
                    width: "15%",
                    field: "UserName",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },

                },
                {
                    title: "组织Id",
                    width: "20%",
                    field: "OrganizationId",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },

                },
                {
                    title: "性别",
                    width: "20%",
                    field: "Gender",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },

                },
                {
                    title: "电话",
                    width: "20%",
                    field: "Tel",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                },
                {
                    title: "邮箱",
                    width: "20%",
                    field: "Email",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                },
                {
                    title: "角色Id",
                    width: "20%",
                    field: "RoleId",
                    encoded: false,
                    attributes: {
                        'class': 'center'
                    },
                },
            ],
            page: 1,
            filterable: true,
            selectable: "Multiple, Row",
            scrollable: false,
            pageable: {
                "refresh": true,
                "autoBind": false,
                "input": true,
                "buttonCount": 10
            },
            autoBind: false,
            //向服务器发送ajax请求，返回的数据对应每个字段的值
            dataSource: createDataSource(basePath + 'admin/user/userList', {
                LoginName: {
                    type: "string"
                },
                UserName: {
                    type: "string"
                },
                OrganizationId: {
                    type: "number"
                },
                Gender: {
                    type: "number"
                },
                Tel: {
                    type: "string"
                },
                Email: {
                    type: "string"
                },
                RoleId: {
                    type: "number"
                },
                Code: {
                    type: "string"
                },
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
    edit: function(id) {
        var p;
        var l;
        //弹窗
        $.mdlg({
            title: '用户',
            width: '850px',
            content: function() {
                return $('#data-tmpl').html();
            },
            //页面需要预加载的内容
            onShow: function() {
                $('#rdi-sex').radioButtonList(sex, 'Gender', 'Text', 'Value', 1);
                //通过get请求异步获得指定id的用户信息，然后通过封装的formData方法，给每个标签对应name赋值
                if (id) {
                    $.get(basePath + 'admin/user/getUser', {
                        'id': id
                    }, function(data) {
                        $('#form-seller').formData(data);
                        p = $("input[name='Password']").val();
                        l = $("input[name='LoginName']").val();
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
                    var params = $("#form-seller").serializeJson();
                    if (p == $("input[name='Password']").val()) {
                        params["PasswordJudge"] = "true";
                    } else {
                        params["PasswordJudge"] = "false";
                    }
                    params["BeforeLoginName"] = l;
                    if ($("input[name='Password']").val().length > 0) {
                        params["Password"] = $.md5($("input[name='Password']").val());
                    }
                    if($("input[name='Password']").val().length < 6){
            			$.mdlg.error('提示','请输入6位以上密码。')
            			return;
            		}
                    $.post(basePath + 'admin/user/update', JSON.stringify(params), function(data) {
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
        $.mdlg.confirm("删除", "您确认要将所选择的用户么？", function() {
            var params = $("#grid").gridSelectedCols('Id');
            $.post(basePath + 'admin/user/delete', JSON.stringify(params), function(data) {
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
