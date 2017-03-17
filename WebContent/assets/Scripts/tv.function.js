$(function() {
    // 联动
    var Linkage = {
        left: function(target, navLeft) {
            var idx = target.index(), theLeft = target.position().left;
            if(idx > 0) $('.nav li').eq(--idx).addClass('on').siblings().removeClass('on');
            if(navScrollWide > 0) {
                var move = navLeft + $('.nav li').eq(idx+1).outerWidth(true);
                if(move > 0) move = 0;
                $('.nav ul').css({left: move}); 
            }
            if(navArrowShow) $('.nav .point').show();
            if(idx == 0) $('.video .point').hide();
        },
        right: function(target, navLeft) {
            var idx = target.index(), theLeft = target.position().left;
            if(idx < navLen) {
                $('.nav ul li').eq(++idx).addClass('on').siblings().removeClass('on');
            }
            if(navScrollWide > 0) {
                var move = navLeft - $('.nav li').eq(idx-1).outerWidth(true);
                if(Math.abs(move) > navScrollWide) move = -navScrollWide-20;
                if(theLeft > parseInt(navVisibleView/2)) {
                    $('.nav ul').css({left: move});
                }
            }
            if(idx == navLen) $('.nav .point').hide();
            if(navLen > 0) $('.video .point').show();
        },
        move: function(direction) {
            var $target = $('.nav ul li.on'),
                navLeft = parseFloat($('.nav ul').css('left'));
            if(direction == 0) {
                this.left($target, navLeft);
            } else if(direction == 1) {
                this.right($target, navLeft);
            }
        }
    };

    // 焦点
    var Focus = {
        video: function(target, direction, callback, page) {
            if(direction == 0) {
                function leftSide() {
                    if($('.nav li.on').index() == 0) {
                        target[0].focus();
                    } else {
                        if(callback != undefined) callback();
                        Linkage.move(direction);
                        $('.video li:first-child a')[0].focus();
                    }
                }
                if($('.play')[0] == undefined) {
                    var idx = $('.video a').index(target);
                    if(idx%5 == 0) leftSide();
                } else {
                    var idx = $('.video li a').index(target);
                    if(target[0] == $('.play a')[0] || idx == 4) {
                        leftSide();
                    } else {
                        if(idx == 0 || idx == 2) {
                            $('.play a')[0].focus();
                        }
                    }
                }
            } else if(direction == 1) {
                function rightSide() {
                    if($('.nav li.on').index() == navLen) {
                        target[0].focus();
                    } else {
                        if(callback != undefined) callback();
                        Linkage.move(direction);
                        $('.video li:first-child a')[0].focus();
                    }
                }
                if($('.play')[0] == undefined) {
                    var idx = $('.video a').index(target);
                    if(idx%5 == 4) rightSide();
                } else {
                    var idx = $('.video li a').index(target);
                    if(idx == 1 || idx == 3 || idx == 8) rightSide();
                }
            } else if(direction == 2) {
                function topSide() {
                    if(page[0] == 1) {
                        $('.nav li.on a')[0].focus();
                    } else {
                        if(callback != undefined) callback();
                        // $('.video li:first-child a')[0].focus();
                        $('.nav li.on a')[0].focus();
                    }
                }
                if($('.play')[0] == undefined) {
                    var idx = $('.video a').index(target);
                    if(parseInt(idx/5) == 0) topSide();
                } else {
                    var idx = $('.video li a').index(target);
                    if(target[0] == $('.play a')[0] || idx == 0 || idx == 1) {
                        topSide();
                    } else {
                        if(idx == 4 || idx == 5 || idx == 6) {
                            $('.play a')[0].focus();
                        }
                    }
                }
            } else if(direction == 3) {
                function bottomSide() {
                    if(page[0] == page[1]) {
                        target[0].focus();
                    } else {
                        if(callback != undefined) callback();
                        $('.video li:first-child a')[0].focus();
                    }
                }
                if($('.play')[0] == undefined) {
                    var idx = $('.video a').index(target);
                    if(parseInt(idx/5) == 2) bottomSide();
                } else {
                    var idx = $('.video li a').index(target);
                    if(idx == 4 || idx == 5 || idx == 6 || idx == 7 || idx == 8) bottomSide();
                }
            }
        },
        nav: function(direction, callback) {
            if(direction == 0 || direction == 1) {
                Linkage.move(direction);
                $('.nav li.on a')[0].focus();
                callback();
            } else if(direction == 2) {
                $('.menu li:first-child a')[0].focus();
            } else if(direction == 3) {
                $('.video li:first-child a')[0].focus();
            }
        },
        menu: function(target, direction) {
            if(direction == 3) {
                if(target.parents('.sort')[0] == undefined || target[0] == $('.sort-items dd:last-child a')[0]) {
                    $('.nav li.on a')[0].focus();
                }
            } else if(direction == 0) {
                if(target[0] == $('.menu li:first-child a')[0]) {
                    $('.nav li.on a')[0].focus();
                }
            }
        }
    };

    IPTV.Focus = Focus;

	$('a').on('focus', function(e) {
        var $target = $(this);

        // 隐藏排序
        if($target.parents('.sort')[0] == undefined) {
            $('.top .sort-items').hide();
        }

        // 分类
        if($target[0] == $('.nav li:first-child a')[0]) {
            $('.nav li:first-child').css({'padding-left': 20});
        } else {
            $('.nav li:first-child').removeAttr('style');
        }
	});

    // 初始焦点
    $($('.video li:first-child a')[0]).focus();

    // 顶部菜单
    $('.menu a').on('keydown', function(e) {
        var keycode = e.keyCode;
        if(keycode == 0x0028) {
            Focus.menu($(this), 3);
        } else if(keycode == 0x0025) {
            Focus.menu($(this), 0);
        }
    });

    // 显示排序
    $('.top .sort > .link a').on('focus', function() {
        $('.top .sort-items').show();
    });

    // 分类导航
    var navLen = $('.nav ul li').length-1,
        navVisibleView = parseFloat($('.nav .list').width()),
        navScrollWide = parseFloat($('.nav ul').width()) - navVisibleView;
    var navArrowShow = parseFloat($('.nav .list ul').width()) > parseFloat($('.nav .list').width());
    if(navArrowShow) $('.nav .point').show();
    if($('.nav li.on').index() == 0) $('.video .point').hide();
});

var IPTV = {};