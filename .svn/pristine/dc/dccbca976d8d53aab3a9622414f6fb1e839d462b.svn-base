function handleAuth(e) {
    if (typeof e.unAuth != 'undefined') {
        window.location.href = '/AdminUser/Login';
    }
}

function getErrorMessage(e) {
    if (typeof e.errors != 'undefined') {
        var message = "错误信息:\n";
        $.each(e.errors, function (key, value) {
            if ('errors' in value) {
                $.each(value.errors, function () {
                    message += this + "\n";
                });
            }
        });
        return message;
    } else if (typeof e.statusText != 'undefined') {
        return e.statusText;
    } else if (typeof e.message != 'undefined') {
        return e.message;
    } else {
        return e;
    }
}
function errors(e) {
    if (typeof e.errors != 'undefined') {
        var message = "错误信息:\n";
        $.each(e.errors, function (key, value) {
            if ('errors' in value) {
                $.each(value.errors, function () {
                    message += this + "\n";
                });
            }
        });
        alert(message);
    } else if (typeof e.statusText != 'undefined') {
        alert(e.statusText);
    } else if (typeof e.unAuth != 'undefined') {
        window.location.href = '/AdminUser/Login';
    } else if (typeof e.message != 'undefined') {
        alert(e.message);
    } else {
        alert(e);
    }
}

kendo.dataviz.ui.Chart.fn.options.autoBind = false;
kendo.culture("zh-CN");

kendo.ui.FilterMenu.prototype.options.messages =
  $.extend(kendo.ui.FilterMenu.prototype.options.messages, {
      info: "条件",
      filter: "筛选",
      clear: "清除",
      isTrue: "为真",
      isFalse: "为假",
      and: "并且",
      or: "或者",
      selectValue: "-请选择-"
});

kendo.ui.FilterMenu.prototype.options.operators =
  $.extend(kendo.ui.FilterMenu.prototype.options.operators, {
      string: {
          eq: "等于",
          neq: "不等于",
          startswith: "开始于",
          contains: "包含",
          doesnotcontain: "不包含",
          endswith: "结束于"
      },
      number: {
          eq: "等于",
          neq: "不等于",
          gte: "大于等于",
          gt: "大于",
          lte: "小于等于",
          lt: "小于"
      },
      date: {
          eq: "等于",
          neq: "不等于",
          gte: "大于等于",
          gt: "大于",
          lte: "小于等于",
          lt: "小于"
      },
      enums: {
          eq: "等于",
          neq: "不等于"
      }
  });

kendo.ui.Pager.prototype.options.messages =
  $.extend(kendo.ui.Pager.prototype.options.messages, {
      display: "{0} - {1} 条，共 {2} 条记录",
      empty: "没有记录",
      page: "第",
      of: "页，共 {0} 页",
      itemsPerPage: "每页记录",
      first: "首页",
      previous: "上一页",
      next: "下一页",
      last: "尾页",
      refresh: "刷新"
  });

function kendoErrors(e) {
    var errors = null;

    try {
        errors = JSON.parse(e.xhr.responseText);
    } catch (error) {
        alert("异常错误。");
        return;
    }

    if (typeof errors.unAuth != 'undefined') {
        window.location.href = basePath+'/admin/dminUser/Login';
    }else if (typeof e.message != 'undefined') {
        alert(errors.message);
    } else {
        alert(errors);
    }
}