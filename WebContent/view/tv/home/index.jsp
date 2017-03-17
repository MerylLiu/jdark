<%@page import="java.net.URLEncoder"%>
<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.iptv.com/UrlUtil" prefix="u"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@include file="/assets/inc_tv.jsp"%>
	<link rel="stylesheet" href="${basePath}assets/Content/sharedWithIndex.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}assets/Content/index.css" type="text/css" />
    <script type='text/javascript' src='${basePath}assets/Scripts/tv.function.js'></script>
    <script type='text/javascript'>
        $(function() {
            // 排序
            var $sortItem = $('.top .sort-items a');
            $sortItem.on('keydown', function(e) {
                if (e.keyCode == 0x000d) { // 确定
                    var $parent = $(this).parents('dd');
                    if ($parent[0] != $('.top .sort-items dd.curr')[0]) {
                        $parent.addClass('curr').siblings().removeClass('curr');
                        // code

                        // 设置页数
                        $('.page').html(
                            '<span>共' + totalVideo + '</span>' + currentPage +
                            '/' + totalPage);
                    }
                }
            });

            // 分类导航
            $('.nav a').on(
                'keydown',
                function(e) {
                    switch (e.keyCode) {
                        case 0x0025: // 左
                            IPTV.Focus.nav(0, function() {
                                // code

                                // 设置页数
                                currentPage = 1;
                                totalPage = 30;
                                totalVideo = 500;
                                $('.page').html(
                                    '<span>共' + totalVideo + '</span>' +
                                    currentPage + '/' + totalPage);
                            });
                            // code
                            break;
                        case 0x0026: // 上
                            IPTV.Focus.nav(2);
                            break;
                        case 0x0027: // 右
                            IPTV.Focus.nav(1, function() {
                                // code

                                // 设置页数
                                currentPage = 1;
                                totalPage = 20;
                                totalVideo = 200;
                                $('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage);
                            });
                            break;
                        case 0x0028: // 下
                            IPTV.Focus.nav(3);
                            break;
                    }
                });

            // 视频列表
            var currentPage = 1,
                totalPage = 30,
                totalVideo = 500; //当前页数，总页数，总视频数
            var tipsTimer; //回到第一页定时器
            var tipsPage = 30; //超过页数提示返回
            $('.video a').on(
                'keydown',
                function(e) {
                    var $currLink = $(this).parents('.link');
                    switch (e.keyCode) {
                        case 0x000d: // 确定
                            $(this).parents('li').addClass('on').siblings()
                                .removeClass('on');
                            // code

                            break;
                        case 0x0025: // 左
                            // 向左加载分类数据
                            IPTV.Focus.video($(this), 0, function() {
                                // code

                                // 设置页数
                                currentPage = 1;
                                totalPage = 30;
                                totalVideo = 500;
                                $('.page').html(
                                    '<span>共' + totalVideo + '</span>' +
                                    currentPage + '/' + totalPage);
                            });
                            break;
                        case 0x0026: //上
                            // 向上加载内容数据
                            IPTV.Focus.video($(this), 2, function() {
                                // code

                                // 设置页数
                                currentPage = currentPage <= 1 ? 1 : --currentPage;
                                $('.page').html(
                                    '<span>共' + totalVideo + '</span>' +
                                    currentPage + '/' + totalPage);
                            }, [currentPage, totalPage]);
                            break;
                        case 0x0027: // 右
                            // 向右加载分类数据
                            IPTV.Focus.video($(this), 1, function() {
                                // code

                                // 设置页数
                                currentPage = 1;
                                totalPage = 20;
                                totalVideo = 200;
                                $('.page').html(
                                    '<span>共' + totalVideo + '</span>' +
                                    currentPage + '/' + totalPage);
                            });
                            break;
                        case 0x0028: //下
                            // 向下加载内容数据
                            IPTV.Focus.video($(this), 3, function() {
                                // code

                                // 设置页数
                                currentPage = currentPage >= totalPage ? totalPage :
                                    ++currentPage;
                                $('.page').html(
                                    '<span>共' + totalVideo + '</span>' +
                                    currentPage + '/' + totalPage);

                                // 到N页显示提示
                                if (!$('.tips').is(':visible') &&
                                    currentPage > tipsPage) {
                                    $('.tips').fadeIn();
                                    tipsTimer = setTimeout(function() {
                                        $('.tips').fadeOut();
                                    }, 3000);
                                }
                            }, [currentPage, totalPage]);
                            break;
                    }
                });

            // 按返回键返回到第一页
            $(document).on( 'keydown', function(e) {
				if (e.keyCode == 0x0008) { // 返回
					// code

					// 设置页数
					currentPage = 1;
					$('.page').html(
						'<span>共' + totalVideo + '</span>' +
						currentPage + '/' + totalPage);
				}
			});
        });    
    </script>
</head>

<body>
	<!--顶部-->
	<div class="top clf">
		<div class="title fl">TV彩页</div>
		<div class="menu fr">
			<ul>
				<li class="search">
					<div class="link">
						<a href="#"></a> <span></span>
					</div>
				</li>
				<li class="sort">
					<div class="link">
						<a href="#"></a> <span></span>
					</div>
					<div class="sort-items">
						<dl>
							<dt>
								<img src="${basePath}assets/Images/point_up.png" alt="">
							</dt>
							<dd class="curr">
								<div class="link">
									<a href="#"></a> <b>更新时间</b>
								</div>
							</dd>
							<dd>
								<div class="link">
									<a href="#"></a> <b>人气排序</b>
								</div>
							</dd>
						</dl>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<!--导航-->
	<div class="nav">
		<div class="list">
			<ul>
				<li class="on">
					<div class="link">
						<a href="#"></a> <span>全部</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>食鉴</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>造物记</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>休闲娱乐</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>运动</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>秀场</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>其他分类</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>其他分类</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>其他分类</span>
					</div>
				</li>
				<li>
					<div class="link">
						<a href="#"></a> <span>其他分类</span>
					</div>
				</li>
			</ul>
		</div>
		<div class="point">
			<img src="${basePath}assets/Images/point_right.png" alt="">
		</div>
	</div>
	<!--视频-->
	<div class="video">
		<div class="point">
			<img src="${basePath}assets/Images/point_left.png" alt="">
		</div>
		<div class="items">
			<!--加载-->
			<!--		<div class="loading"><img src="${basePath}assets/Images/loading.gif" alt=""></div>-->
			<div class="item clf">
				<!--视频-->
				<div class="play">
					<div class="link">
						<a href="#"></a>
						<div class="vid">
							<img src="${basePath}assets/Images/video.jpg" alt="">
						</div>
					</div>
				</div>
				<!--列表-->
				<div class="list clf">
					<ul id="video-list">
					</ul>
				</div>
			</div>
			<div class="item second clf">
				<!-- 信息展示，没有a标签-->
				<div class="list clf">
					<ul id="preview-list">
						<c:forEach items="${nextVideos}" var="vi">
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<div class="bottom">
		<div class="tips">按“返回”按钮回到第1页</div>
		<div class="more">
			<img src="${basePath}assets/Images/point_down.png" alt="">
		</div>
		<div class="page">
			<span>共500+</span>1/30
		</div>
	</div>
</body>
<script type="text/javascript">
	tv = {
		init:function(){
			this.loadVideo();
			this.loadPreviewList(1);
		},
		loadVideo:function(){
			var data = {page:1,pageSize:15};
			$.get(basePath + 'home/videoList',data,function(res){
				$('#video-list').html("");

				$.each(res.data,function(i,v){
					var html = '<li><div class="link">'
							 + '<a href="#"></a>'
							 + '<div class="vid">'
							 + '<div class="thumbnail"><img src="${basePath}'+('image/index?p='+encodeURI(v.ImageUrl))+'" alt=""></div>'
							 + '<div class="info">'
							 + '<p>'+v.Name+'</p>'
							 + '<span><img src="${basePath}assets/Images/play.png" alt=""></span>'
							 + '</div>'
							 + '</div>'
							 + '</div></li>';
							 
					$('#video-list').append(html);
				})
			});
		},
		loadPreviewList:function(cid){
			var data = {cid:cid};
			$.get(basePath + 'home/previewList',data,function(res){
				$('#preview-list').html();

				$.each(res,function(i,v){
					var html = '<li><div class="vid"><div class="thumbnail">'
							 + '<img src="${basePath}'+('image/index?p='+encodeURI(v.ImageUrl))+'" alt="">'			
							 + '</div>'
							 + '<div class="info">'	
							 + '<p>'+v.Name+'</p>'
							 + '<span><img src="${basePath}assets/Images/play.png" alt=""></span>'
							 + '</div></div></li>'	

					$('#preview-list').append(html);
				})
			})
		}
	}
</script>
</html>
