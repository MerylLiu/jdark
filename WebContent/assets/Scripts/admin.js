/* ---------------
 * -    Tabs     -
 *---------------*/
(function($) {
    $task_content_inner = null;
    $mainiframe = null;
    var tabwidth = 118;
    $loading = null;
    $nav_wraper = $("#nav_wraper");
    var tempTab = null;

    String.prototype.render = function(args) {
        var res = this.toString();

        if (arguments.length > 0) {
            $.each(arguments, function(i, v) {
                res = res.replace('\$\{' + i + '\}', v);
            })
        }

        return res;
    }

    function calcTaskitemsWidth() {
        var width = $("#task-content-inner li").length * tabwidth;
        $("#task-content-inner").width(width);

        if (($(document).width() - 268 - tabwidth - 30 * 2) < width) {
            $("#task-content").width($(document).width() - 268 - tabwidth - 30 * 2);
            // $("#task-next,#task-pre,#task-extend,#btn-tabs-close").show();
            $("#task-next,#task-pre,#btn-tabs-close").show();

            /*
             * $("#task-content-inner li").each(function(i,e){ if(tabwidth *
             * (i+1) > ($(document).width() - 268 - tabwidth - 30 * 2) &&
             * tempTab == null){ tempTab = this; } })
             * 
             * if(tempTab !=null){ $('#task-extend ul').html('');
             * 
             * var tmpl = '<li role="presentation"><a role="menuitem"
             * tabindex="-1" href="javascript:void(0);">${0}</a></li>'
             * $('#task-extend
             * ul').append(tmpl.render($(tempTab).find('span').html()));
             * $(tempTab).nextUntil("ul").each(function(){ $('#task-extend
             * ul').append(tmpl.render($(this).find('span').html())); })
             * 
             * tempTab=null; }
             */
        } else {
            $("#task-next,#task-pre,#task-extend,#btn-tabs-close").hide();
            $("#task-content").width(width);
        }

        if ($("#task-content-inner li").length > 1) {
            $("#btn-tabs-close").show();
        }
    }

    window.closeapp = function($this) {
        if (!$this.is(".noclose")) {
            $this.prev().click();
            $this.remove();
            $("#appiframe-" + $this.attr("app-id")).remove();
            calcTaskitemsWidth();
            $("#task-next").click();
        }

    }

    var task_item_tpl = '<li class="macro-component-tabitem">' +
        '<span class="macro-tabs-item-text"></span>' +
        '<a class="macro-component-tabclose" href="javascript:void(0)" title="关闭">' +
        '<span></span><b class="macro-component-tabclose-icon">×</b>' +
        '</a></li>';

    var appiframe_tpl = '<iframe style="width:100%;height: 100%;" frameborder="0" class="appiframe"></iframe>';

    window.openapp = function(url, appid, appname, refresh) {
        var $app = $("#task-content-inner li[app-id='" + appid + "']");
        $("#task-content-inner .current").removeClass("current");

        if ($app.length == 0) {
            var task = $(task_item_tpl).attr("app-id", appid).attr("app-url", url).attr("app-name", appname).addClass("current");
            task.find(".macro-tabs-item-text").html(appname).attr("title", appname);
            $task_content_inner.append(task);
            $(".appiframe").hide();
            $loading.show();
            $appiframe = $(appiframe_tpl).attr("src", url).attr("id", "appiframe-" + appid);
            $appiframe.appendTo("#content");
            $appiframe.load(function() {
                $loading.hide();
            });
            calcTaskitemsWidth();
        } else {
            $app.addClass("current");
            $(".appiframe").hide();
            var $iframe = $("#appiframe-" + appid);
            var src = $iframe.get(0).src;
            src = src.substr(src.indexOf("://") + 3);

            if (refresh === true) {
                $loading.show();
                $iframe.attr("src", url);
                $iframe.load(function() {
                    $loading.hide();
                });
            }
            $iframe.show();
        }

        //
        var itemoffset = $("#task-content-inner li[app-id='" + appid + "']").index() * tabwidth;
        var width = $("#task-content-inner li").length * tabwidth;

        var content_width = $("#task-content").width();
        var offset = itemoffset + tabwidth - content_width;

        var lesswidth = content_width - width;

        var marginleft = $task_content_inner.css("margin-left");

        marginleft = parseInt(marginleft.replace("px", ""));
        var copymarginleft = marginleft;
        if (offset > 0) {
            marginleft = marginleft > -offset ? -offset : marginleft;
        } else {
            marginleft = itemoffset + marginleft >= 0 ? marginleft : -itemoffset;
        }

        if (-itemoffset == marginleft) {
            marginleft = marginleft + tabwidth > 0 ? 0 : marginleft + tabwidth;
        }

        if (content_width - copymarginleft - tabwidth == itemoffset) {
            marginleft = marginleft - tabwidth <= lesswidth ? lesswidth : marginleft - tabwidth;
        }

        $task_content_inner.animate({
            "margin-left": marginleft + "px"
        }, 300, 'swing');
    };

    var initTabs = function() {
        $mainiframe = $("#mainiframe");
        $content = $("#content");
        $loading = $("#loading");
        var headerheight = 86;
        $content.height($(window).height() - headerheight);


        $nav_wraper.height($(window).height() - 45);
        $nav_wraper.css("overflow", "auto");
        // $nav_wraper.niceScroll();

        $(window).resize(function() {
            $nav_wraper.height($(window).height() - 45);
            $content.height($(window).height() - headerheight);
            calcTaskitemsWidth();
        });

        $("#content iframe").load(function() {
            $loading.hide();
        });

        $task_content_inner = $("#task-content-inner");


        $("#searchMenuKeyWord").keyup(function() {
            var wd = $(this).val();
            // searchedmenus
            var $tmp = $("<div></div>");
            if (wd != "") {
                $("#allmenus a:contains('" + wd + "')").each(
                    function() {
                        $clone = $(this).clone().prepend('<img src="/images/left/01/note.png">');

                        $clone.wrapAll('<div class="menuitemsbig"></div>').parent().attr("onclick", $clone.attr("onclick")).appendTo($tmp);
                    }
                );
                $("#searchedmenus").html($tmp.html());
                $("#searchedmenus").show();
                $("#allmenus").hide();
                $("#defaultstartmenu").hide();
                $("#allmenuslink .menu_item_linkbutton").html("返回");
                isAllDefault = false;
                // $("#searchedmenus").html($tmp).show();

            }

        });


        $("#appbox  li .delete").click(function(e) {
            $(this).parent().remove();
            return false;
        });

        $(document).on("click", ".apps_container li", function() {
            var app = '<li><span class="delete" style="display:inline">×</span><img src="" class="icon"><a href="#" class="title"></a></li>';
            var $app = $(app);
            $app.attr("data-appname", $(this).attr("data-appname"));
            $app.attr("data-appid", $(this).attr("data-appid"));
            $app.attr("data-appurl", $(this).attr("data-appurl"));
            $app.find(".icon").attr("src", $(this).attr("data-icon"));
            $app.find(".title").html($(this).attr("data-appname"));
            $app.appendTo("#appbox");
            $("#appbox  li .delete").off("click");
            $("#appbox  li .delete").click(function() {
                $(this).parent().remove();
                return false;
            });
        });

        $("#tdshortcutsmor1").click(function() {
            $(".window").hide();
        });

        $(document).on("click", ".task-item", function() {
            var appid = $(this).attr("app-id");
            var $app = $('#' + appid);
            showTopWindow($app);
        });

        $(document).on("click", "#task-content-inner li", function() {
            openapp($(this).attr("app-url"), $(this).attr("app-id"), $(this).attr("app-name"));
            return false;
        });

        $(document).on("dblclick", "#task-content-inner li", function() {
            closeapp($(this));
            return false;

        });

        $(document).on("click", "#task-content-inner a.macro-component-tabclose", function() {
            closeapp($(this).parent());
            return false;
        });

        $("#task-next").click(function() {
            var marginleft = $task_content_inner.css("margin-left");
            marginleft = marginleft.replace("px", "");
            var width = $("#task-content-inner li").length * tabwidth;
            var content_width = $("#task-content").width();
            var lesswidth = content_width - width;
            marginleft = marginleft - tabwidth <= lesswidth ? lesswidth : marginleft - tabwidth;

            $task_content_inner.stop();
            $task_content_inner.animate({
                "margin-left": marginleft + "px"
            }, 300, 'swing');
        });

        $("#task-pre").click(function() {
            var marginleft = $task_content_inner.css("margin-left");
            marginleft = parseInt(marginleft.replace("px", ""));
            marginleft = marginleft + tabwidth > 0 ? 0 : marginleft + tabwidth;
            // $task_content_inner.css("margin-left", marginleft + "px");
            $task_content_inner.stop();
            $task_content_inner.animate({
                "margin-left": marginleft + "px"
            }, 300, 'swing');
        });

        $("#refresh_wrapper").click(function() {
            var $current_iframe = $("#content iframe:visible");
            $loading.show();
            $current_iframe.attr("src", $current_iframe.attr("src"));
            // $current_iframe[0].contentWindow.location.reload();
            return false;
        });

        $('#btn-close-current').click(function() {
            closeapp($("#task-content-inner > .current"));
        });
        $('#btn-close-all').click(function() {
            $("#task-content-inner > li").not('.noclose').each(function() {
                closeapp($(this));
            })
        });

        calcTaskitemsWidth();
    };

    // ///
    $(document).ready(function() {
        initTabs();
    })
})(jQuery);

/*
 * --------------- - Menu - ---------------
 */
(function($) {
    var ismenumin = $("#sidebar").hasClass("menu-min");

    var init = function() {
        $(".nav-list").on("click", function(event) {
            var closest_a = $(event.target).closest("a");
            if (!closest_a || closest_a.length == 0) {
                return
            }
            if (!closest_a.hasClass("dropdown-toggle")) {
                if (ismenumin && "click" == "tap" && closest_a.get(0).parentNode.parentNode == this) {
                    var closest_a_menu_text = closest_a.find(".menu-text").get(0);
                    if (event.target != closest_a_menu_text && !$.contains(closest_a_menu_text, event.target)) {
                        return false
                    }
                }
                return
            }

            var closest_a_next = closest_a.next().get(0);

            if (!$(closest_a_next).is(":visible")) {
                var closest_ul = $(closest_a_next.parentNode).closest("ul");
                if (ismenumin && closest_ul.hasClass("nav-list")) {
                    return
                }
                closest_ul.find("> .open > .submenu").each(function() {
                    if (this != closest_a_next && !$(this.parentNode).hasClass("active")) {
                        $(this).slideUp(150).parent().removeClass("open")
                        $(this).slideUp(150).parent().find('a>b').addClass("fa-angle-bottom");
                    }
                });
            }

            if (ismenumin && $(closest_a_next.parentNode.parentNode).hasClass("nav-list")) {
                return false;
            }

            $(closest_a_next).slideToggle(150).parent().toggleClass("open");
            closest_a.parent().parent().find("a").not(closest_a).find('b').removeClass('fa-angle-down');
            closest_a.parent().parent().find(closest_a).find('b').toggleClass('fa-angle-down');

            closest_a.parent().parent().find("a").not(closest_a).find('b.fa-plus-circle').removeClass('fa-minus-circle');
            closest_a.parent().parent().find(closest_a).find('b.fa-plus-circle').removeClass('fa-angle-down');
            closest_a.parent().parent().find(closest_a).find('b.fa-plus-circle').toggleClass('fa-minus-circle');

            closest_a.parent().find("a").not(closest_a).parent().find('.submenu').removeClass('open').hide();
            closest_a.parent().find("a").not(closest_a).find('.submenu').removeClass('open').hide();

            return false;
        })
    }

    var createTree = function(obj, data) {
        function getUrlAndArrow(v) {
            var url = "<li>";
            if (typeof v.PageUrl == "undefined") {
                url = 'javascript:void(0);';
            } else {
                url = 'javascript:openapp(\'' + v.PageUrl + '\',\'m-' + v.Id + '\',\'' + v.Name + '\',true);'
            }

            var arrow = '';
            if (v.Level == 1) {
                arrow = '<b class="arrow fa fa-plus-circle normal"></b>'
            } else if (v.Level == 2) {
                arrow = '<b class="arrow fa fa-angle-right"></b>'
            }

            return {
                url: url,
                arrow: arrow
            };
        }

        var html = '',
            temp = '',
            dropDown = '',
            childCount = 0;

        $.each(data, function(i, v) {
            var u = getUrlAndArrow(v);
            childCount = 0;

            if (typeof v.ParentId == 'undefined' || v.ParentId == 0) {
                $.each(data, function(i2, v2) {
                    if (v2.ParentId == v.Id) {
                        childCount++;
                    }
                });
                childCount > 0 ? dropDown = 'dropdown-toggle' : dropDown = '';

                temp = '<li><a href="' + u.url + '" class="' + dropDown + '">' +
                    '<i class="fa ' + v.MenuIcon + ' normal"></i><span class="menu-text normal">' + v.Name + '</span>' +
                    u.arrow + '<i class="fa fa-reply back"></i><span class="menu-text back">返回</span></a>';
                html += temp;

                var childHtml = "";
                $.each(data, function(i2, v2) {
                    childCount = 0;

                    if (v2.ParentId == v.Id) {
                        $.each(data, function(i3, v3) {
                            if (v3.ParentId == v2.Id) {
                                childCount++;
                            }
                        });
                        childCount > 0 ? dropDown = 'dropdown-toggle' : dropDown = '';

                        var u2 = getUrlAndArrow(v2);

                        childHtml += '<li><a href="' + u2.url + '" class="' + dropDown + '">' +
                            '<i class="fa fa-caret-right"></i><span class="menu-text">' + v2.Name + '</span>' +
                            u2.arrow + '</a>';

                        var childHtml2 = "";
                        $.each(data, function(i3, v3) {
                            if (v3.ParentId == v2.Id) {
                                var u3 = getUrlAndArrow(v3);

                                childHtml2 += '<li><a href="' + u3.url + '">' +
                                    '<i class="fa fa-angle-double-right"></i><span class="menu-text">&nbsp;' +
                                    v3.Name + '</span></a></li>';
                            }
                        });

                        if (childHtml2.length > 0) {
                            childHtml += "<ul class=\"submenu\">"
                            childHtml += childHtml2;
                            childHtml += "</ul>"
                        }

                        childHtml += '</li>';
                    }
                });

                if (childHtml.length > 0) {
                    html += "<ul class=\"submenu\">"
                    html += childHtml;
                    html += "</ul>"
                }

                html += '</li>';
            }
        });

        if (html.length > 0) {
            $(obj).html(html);

            init();
            $('a[class!="dropdown-toggle"]').click(function() {
                $(this).addClass('selected');
                $('a').not(this).removeClass('selected');
            })
        }
    }

    jQuery.fn.menu = function(data) {
        var obj = this;
        if (typeof data == 'object') {
            createTree(obj, data);
        } else if (typeof data == 'string') {
            $.get(data, null, function(res) {
                createTree(obj, res);
            })
        }
    }
})(jQuery);


/*--------------------
*    transition
*--------------------*/
(function ($) {
    var collapse = function () {
        $('.btn-collapse').click(function () {
            $(this).find('i.fa').toggleClass('fa-chevron-circle-down');
        });
    };

    $(document).ready(function () {
        collapse();
    });
})(jQuery);


/*
 * ----------------Modal box-------------
 * */
(function($) {
    $.fn.mdlg = function(options) {
        var defaults = {
            title: '标题',
            width:'',
            content: '<p>内容</p>',
            showCloseButton: true,
            otherButtons: [],
            otherButtonStyles: [],
            bootstrapModalOption: {},
            dialogShow: function() {},
            dialogShown: function() {},
            dialogHide: function() {},
            dialogHidden: function() {},
            clickButton: function(sender, modal, index) {},
        };
        options = $.extend(defaults, options);
        var modalID = '';

        //生成一个惟一的ID
        function random(a, b) {
            return Math.random() > 0.5 ? -1 : 1;
        }

        function getModalID() {
            return "mdlg-" + ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'Q', 'q', 'W', 'w', 'E', 'e', 'R', 'r', 'T', 't', 'Y', 'y', 'U', 'u', 'I', 'i', 'O', 'o', 'P', 'p', 'A', 'a', 'S', 's', 'D', 'd', 'F', 'f', 'G', 'g', 'H', 'h', 'J', 'j', 'K', 'k', 'L', 'l', 'Z', 'z', 'X', 'x', 'C', 'c', 'V', 'v', 'B', 'b', 'N', 'n', 'M', 'm'].sort(random).join('').substring(5, 20);
        }

        $.fn.extend({
            closeDialog: function(modal) {
                modal.modal('hide');
            },
            setContent: function(cont){
            	$('#'+modalID).find('.modal-body').html(cont);
            }
        });

        return this.each(function() {
            var obj = $(this);
            modalID = getModalID();
            var tmpHtml = '<div class="modal fade" id="{ID}" role="dialog" aria-hidden="true"><div class="modal-dialog" style="width:{width}"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button><h4 class="modal-title">{title}</h4></div><div class="modal-body">{body}</div><div class="modal-footer">{button}</div></div></div></div>';
            var buttonHtml = '<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>';
            if (!options.showCloseButton && options.otherButtons.length > 0) {
                buttonHtml = '';
            }
            //生成按钮
            var btnClass = 'cls-' + modalID;
            for (var i = 0; i < options.otherButtons.length; i++) {
                buttonHtml += '<button buttonIndex="' + i + '" class="' + btnClass + ' btn ' + options.otherButtonStyles[i] + '">' + options.otherButtons[i] + '</button>';
            }
            //替换模板标记
            tmpHtml = tmpHtml.replace(/{ID}/g, modalID).replace(/{width}/,options.width).replace(/{title}/g, options.title).replace(/{body}/g, options.content).replace(/{button}/g, buttonHtml);
            obj.append(tmpHtml);

            var modalObj = $('#' + modalID);
            //绑定按钮事件,不包括关闭按钮
            $('.' + btnClass).click(function() {
                var index = $(this).attr('buttonIndex');
                options.clickButton($(this), modalObj, index);
            });
            //绑定本身的事件
            modalObj.on('show.bs.modal', function() {
                options.dialogShow();
            });
            modalObj.on('shown.bs.modal', function() {
                options.dialogShown();
            });
            modalObj.on('hide.bs.modal', function() {
                options.dialogHide();
            });
            modalObj.on('hidden.bs.modal', function() {
                options.dialogHidden();
                modalObj.remove();
            });
            modalObj.modal(options.bootstrapModalOption);
        });    
    };
    
    $.extend({
        mdlg: function(options) {
            $("body").mdlg(options);
        },
    });
    
	$.mdlg.error = function(title,content){
		$("body").mdlg({
			title:title,
			content:'<div class="text-danger">'+content+'</div>'
		});
	}

	$.mdlg.alert = function(title,content){
		$("body").mdlg({
			title:title,
			content:content
		});
	}

})(jQuery);

/*
 * -----------------JQuery plugin-------------
 * */
(function($,w) {
    $.fn.serializeJson = function() {
        var serializeObj = {};
        $(this.serializeArray()).each(function() {
            if (this.value.length > 0)
                serializeObj[this.name] = this.value;
        });
        return serializeObj;
    };

    $.fn.formData = function(data) {
        var obj = data;
        var key, value, tagName, type, arr;

        $(this).resetForm();

        for (x in obj) {
            key = x;
            value = obj[x];

            $("[name='" + key + "'],[name='" + key + "[]']").each(function() {
                tagName = $(this)[0].tagName;
                type = $(this).attr('type');
                if (tagName == 'INPUT') {
                    if (type == 'radio') {
                        $(this).prop('checked', $(this).val() == value);
                    } else if (type == 'checkbox') {
                        arr = value.split(',');
                        for (var i = 0; i < arr.length; i++) {
                            if ($(this).val() == arr[i]) {
                                $(this).prop('checked', true);
                                break;
                            }
                        }
                    } else {
                        $(this).val(value);
                    }
                } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                    $(this).val(value);
                }
            });
        }
    }
    
    w.createDataSource = function(url,fields) {
    	var data = new kendo.data.DataSource ({
    		transport: {
    			read: {
    				url: url,
    				type: 'GET',
    				dataType: 'json',
    				contentType: 'application/json; charset=utf-8',
    			},
    			parameterMap: function (options, operation) {
    				if (operation == "read") {
    					return {page:options.page,pageSize:options.pageSize};
    				}
    			}
    		},
    		schema: {
    			data: "data",
    			total: "total",
    			errors: "errors",
    			model: {
    				fields: fields
    			}
    		},
    		batch: true,
    		pageSize: 20,
    		serverPaging:true,
    		serverFiltering:true
    	});

      return data;
    };
    
	$.fn.gridSelIds=function(){
		var gridObj = $(this).data("kendoGrid");
		var res = Array();
		gridObj.select().each(function() {
			var dataItem = gridObj.dataItem($(this));
			res.push(dataItem.Id);
		});

		return res;
	}
})(jQuery,window);

$.ajaxSetup({
    cache: false,
    contentType: 'application/json;charset=utf-8'
});

