$(function() {
    $(".header-search input:text").focus(function() {
        $(this).val("").addClass("curr");
    });

    $(".header-search input:text").blur(function() {
        var txt = $(this).val();
        if (txt == "") {
            $(this).val("搜索服务、商户名、个人");
            $(this).removeClass("curr");
        } else {}
    });
});

function letDivCenter(showClass) {
    $(".bgblock").css({
        "display": "block",
        "height": $(document).height()
    });
    var $box = $(showClass);
    $box.css({
        "left": ($("body").width() - $box.width()) / 2 + "px",
        "top": ($(window).height() - $box.height()) / 2 + $(window).scrollTop() + "px",
        "display": "block"
    });
}

function letDivHide(hideClass) {
    $(hideClass).hide();
    $(".bgblock").hide();
}