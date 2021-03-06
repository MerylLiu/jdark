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
    <script type='text/javascript' src='${basePath}assets/Scripts/epg.util.js'></script>
    <script type='text/javascript'>
    $(function(){
		var currentPage = 1, totalPage = 0, totalVideo = 0; 	//当前页数，总页数，总视频数
		var tipsTimer; 	//回到第一页定时器
		var tipsPage = 1; 	//超过页数提示返回
        var catgoryId = 0;//当前分类
        var playIndex = 1;
		var time = 0;
        var timer = new Array();
        var playCode = "";
		
		$(document).on('keydown', function(e) {
			var keycode = e.keyCode, target = $('a:focus');
			if($('.menu li > .link a').index(target) != -1) {  // 顶部菜单
				if(keycode == 0x0028) {
					IPTV.Focus.menu(target, 3);
				} else if(keycode == 0x0025) {
					IPTV.Focus.menu(target, 0);
				} else if(keycode == 0x0026) {
					IPTV.Focus.menu(target, 2);
				} else if(keycode == 0x0027) {
					IPTV.Focus.menu(target, 1);
				} else if(keycode == 0x000d) {  // 确定
					// code
					if(target.parents('.search').length) {
						window.location.href = "${basePath}video/search";
					}
				}
			} else if($('.sort-items a').index(target) != -1) { // 排序
				if(keycode == 0x0028) {
					IPTV.Focus.sort(target, 3);
				} else if(keycode == 0x0025) {
					IPTV.Focus.sort(target, 0);
				} else if(keycode == 0x0026) {
					IPTV.Focus.sort(target, 2);
				} else if(keycode == 0x0027) {
					IPTV.Focus.sort(target, 1);
				} else if(keycode == 0x000d) {	// 确定
					var $parent =target.parents('dd');
					if($parent[0] != $('.top .sort-items dd.curr')[0]) {
						$parent.addClass('curr').siblings().removeClass('curr');
						// code

						// 设置页数
						$('.page').html('<span>共'+totalVideo+'</span>'+currentPage+'/'+totalPage);
					}
				}
			} else if($('.nav a').index(target) != -1) { // 分类列表
				switch(keycode) {
					case 0x0025: 	// 左
						IPTV.Focus.nav(0, function() {
							categoryId = $('.nav li.on a').find('.js-cat').val();
							var ncid = $('.nav li.on a').parent().parent().next().find('.js-cat').val();
							loadVideo(currentPage,categoryId)
							loadPreviewList(ncid);
							
							$('#win-player').remove();

							$('.page').html('<span>共'+totalVideo+'</span>'+currentPage+'/'+totalPage);
						});
						// code
						break;
					case 0x0026: 	// 上
						IPTV.Focus.nav(2);
						break;
					case 0x0027:	// 右
						IPTV.Focus.nav(1, function() {
							$('#win-player').remove();

							categoryId = $('.nav li.on a').find('.js-cat').val();
							var ncid = $('.nav li.on a').parent().parent().next().find('.js-cat').val();
							loadVideo(currentPage,categoryId)
							loadPreviewList(ncid);

							$('.page').html('<span>共'+totalVideo+'</span>'+currentPage+'/'+totalPage);
						});
						break;
					case 0x0028: 	// 下
						$('.nav li:first-child').removeAttr('style');
						IPTV.Focus.nav(3);
						break;
				}
			} else if($('.video a').index(target) != -1) { // 视频列表
				switch(keycode) {
					case 0x000d: 	// 确定
						// target.parents('li').addClass('on').siblings().removeClass('on');
						// code
						// window.location.href = '${basePath}/video/detail?id='+ $(self).find("input:hidden").val();
						
						// 全屏播放
						if(target[0] == $('.play a')[0]) full_play(playCode);
						
						break;
					case 0x0025: 	// 左
						// 向左加载分类数据
						IPTV.Focus.video(target, 0, function() {
							$('#win-player').remove();

							categoryId = $('.nav li.on a').find('.js-cat').val();
							var ncid = $('.nav li.on a').parent().parent().next().find('.js-cat').val();
							loadVideo(currentPage,categoryId,function(){
								$('.video li:first-child a')[0].focus();
								$('.link').removeClass('on');
								if($('.video a').length > 0) {
									$('.video li:first-child a').parents('.link').addClass('on');
								} else {
									$('.nav li.on a').parents('.link').addClass('on');
								}
							})
							loadPreviewList(ncid);

							$('.page').html('<span>共'+totalVideo+'</span>'+currentPage+'/'+totalPage);
						});
						break;
					case 0x0026://上
						// 向上加载内容数据
						IPTV.Focus.video(target, 2, function() {
							// code
							// 设置页数
							$('.page').html('<span>共'+totalVideo+'</span>'+currentPage+'/'+totalPage);
						}, [currentPage, totalPage]);
						break;
					case 0x0027: 	// 右
						// 向右加载分类数据
						IPTV.Focus.video(target, 1, function() {
							// code
							categoryId = $('.nav li.on a').find('.js-cat').val();
							var ncid = $('.nav li.on a').parent().parent().next().find('.js-cat').val();
							loadVideo(currentPage,categoryId,function(){
								$('.video li:first-child a')[0].focus();
								$('.link').removeClass('on');
								if($('.video a').length > 0) {
									$('.video li:first-child a').parents('.link').addClass('on');
								} else {
									$('.nav li.on a').parents('.link').addClass('on');
								}
							});
							loadPreviewList(ncid);

							$('.page').html('<span>共'+totalVideo+'</span>'+currentPage+'/'+totalPage);
						});
						break;
					case 0x0028://下
						// 向下加载内容数据
						IPTV.Focus.video(target, 3, function() {
							// code
							
							// 设置页数
							currentPage = currentPage >= totalPage ? totalPage : ++currentPage;
							//$('.page').html('<span>共'+totalVideo+'</span>'+currentPage+'/'+totalPage);

							$('#win-player').remove();

							loadVideo(currentPage,categoryId,function(){
								$('.video li:first-child a')[0].focus();
								$('.link').removeClass('on');
								$('.video li:first-child a').parents('.link').addClass('on');
							});
							// 到N页显示提示
							if(!$('.tips').is(':visible') && currentPage > tipsPage) {
								$('.tips').fadeIn();
								tipsTimer = setTimeout(function() {
									$('.tips').fadeOut();
								}, 3000);
							}
						}, [currentPage, totalPage]);
						break;
					case 0x0008://返回
						IPTV.Focus.video(target, 4, function() {
							// code
							if(currentPage == 1) { // 返回频道
								window.location.href = "http://115.28.77.76:9600/iptv-web/home";
							} else {
								// 设置页数
								if(currentPage >= tipsPage) {
									currentPage = 1;
									$('.page').html('<span>共'+totalVideo+'</span>'+currentPage+'/'+totalPage);

									$('#win-player').remove();
									loadVideo(currentPage,categoryId, function() {
										$('.video li:first-child a')[0].focus();
										$('.link').removeClass('on');
										$('.video li:first-child a').parents('.link').addClass('on');
									});
								}
							}
						}, [currentPage, totalPage]);
						break;
				}
			}
			
			// 返回频道
			if(keycode == 0x0008) {
				window.location.href = "http://115.28.77.76:9600/iptv-web/home";
			}
		});

		$('a').on('focus', function(e) {
			var $target = $(this);

			if(!$target.parents('.sort').length) {
				$('.top .sort-items').hide();
			}

			if($('.nav li:first-child a').is(':focus')) {
				$('.nav li:first-child').css({'padding-left': 20});
			} else {
				$('.nav li:first-child').removeAttr('style');
			}
		});

		$('.top .sort a').on('focus', function() {
			$('.top .sort-items').show();
		});

		if($('.nav .list ul').width() > $('.nav .list').width()) $('.nav .point').show();
		if($('.nav li.on').index() == 0) $('.video .point').hide();
		
	
		var loadVideo = function(p,cid,callback){
			cid = isNaN(parseInt(cid)) ? 0 : parseInt(cid);
			var data = {page:p,categoryId:cid,pageSize:15};
			$.get(basePath + 'home/videoList',data,function(res){
				if(0 == cid){
					p > 1 ? $('.play').hide() : $('.play').show();
				}else{
					$('.play').hide();
				}
				$('#video-list').html("");

				$.each(res.data,function(i,v){
					var html = '<li><input type="hidden" value="'+v.PlayTime+'" class="js-time">'
							 + '<input type="hidden" value="'+v.DxCode+'" class="js-code"><div class="link">'
							 + '<a href="${basePath}/video/detail?id='+v.Id+'"><input type="hidden" value="'+v.Id+'"></a>'
							 + '<div class="vid">'
							 + '<div class="thumbnail"><img src="${staticUrl}/'+v.ImageUrl+'" alt="" width="200" height="150"></div>'
							 + '<div class="info">'
							 + '<p>'+v.Name+'</p>'
							 + '<span><img src="${basePath}assets/Images/play.png" alt=""></span>'
							 + '</div></div></div></li>';
							 
					$('#video-list').append(html);
				})
				
				totalPage = res.pageNum;
				totalVideo = res.total;

				$('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage);
				
				//auto play video
				var timeList = $('#video-list .js-time');
				var codeList = $('#video-list .js-code');
				var length = $('#video-list .js-code').length;

				if(0 == cid && p==1){
					if(length >0) {
						$(timeList[0]).parent().addClass('on')
						$('#win-p-cont').append('<iframe id="win-player" width="620" height="310" src="javascript:void(0);"  marginwidth="0" marginheight="0" scrolling="no"></iframe>');
						$('#video-bg').hide();
						small_play($(codeList[0]).val());
						playCode = $(codeList[0]).val();
					}
					interval(timeList,codeList)
				}else{
					for(var i=0;i<timer.length;i++){
						clearTimeout(timer[i]);
					}
				}
	
				if(typeof callback != "undefined"){
					callback();
				}
			});
		};
		
		var interval = function(timeList,codeList){
			var t = setTimeout(function(){
				time++;
				var length = timeList.length;
				if(time == $(timeList[playIndex]).val()){
					$('#video-bg').hide();
					small_play($(codeList[playIndex]).val());
					time = 0;
					playCode = $(codeList[playIndex]).val()

					$('#video-list li').removeClass('on')
					$(timeList[playIndex]).parent().addClass('on')

					playIndex++;
					if(playIndex >= length){
						playIndex = 0;
						time = 0;
					}
				}
				clearTimeout(timer);
				interval(timeList,codeList);
			},1000)
			
			timer.push(t);
		}
		
		var loadPreviewList = function(cid){
			var data = {cid:cid};
			$.get(basePath + 'home/previewList',data,function(res){
				$('#preview-list').html('');

				$.each(res,function(i,v){
					var html = '<li><div class="vid"><div class="thumbnail">'
							 + '<img src="${staticUrl}/'+v.ImageUrl+'" alt="" width="200" height="150">'			
							 + '</div>'
							 + '<div class="info">'	
							 + '<p>'+v.Name+'</p>'
							 + '<span><img src="${basePath}assets/Images/play.png" alt=""></span>'
							 + '</div></div></li>'	

					$('#preview-list').append(html);
				})
			})
		}

		loadVideo(currentPage,catgoryId,function(){
			$('.video li:first-child a')[0].focus();
			$('.video li:first-child a').parents('.link').addClass('on');
		});
		loadPreviewList($($('.nav .js-cat')[1]).val());
    })
    </script>
</head>

<body>
	<!--顶部-->
	<div class="top clf">
		<div class="title fl"><img src="${basePath}assets/Images/iptv_logo.png" alt=""></div>
		<div class="menu fr">
			<ul>
				<li class="search">
					<div class="link">
						<a href="#"></a>
						<span></span>
					</div>
				</li>
				<li class="sort" style="display:none;">
					<div class="link">
						<a href="#"></a>
						<span></span>
					</div>
					<div class="sort-items">
						<dl>
							<dt><img src="${basePath}assets/Images/point_up.png" alt=""></dt>
							<dd class="curr">
								<div class="link">
									<a href="#"></a>
									<b>更新时间</b>
								</div>
							</dd>
							<dd>
								<div class="link">
									<a href="#"></a>
									<b>人气排序</b>
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
						<a href="#"><input type="hidden" value="0" class="js-cat"></a>
						<span>全部</span>
					</div>
				</li>
				<c:forEach items="${categories}" var="cat">
					<li>
						<div class="link">
							<a href="#"><input type="hidden" value="${cat.Id}" class="js-cat"></a> <span>${cat.Name}</span>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="point"><img src="${basePath}assets/Images/point_right.png" alt=""></div>
	</div>
	<!--视频-->
	<div class="video">
		<div class="point"><img src="${basePath}assets/Images/point_left.png" alt=""></div>
		<div class="items">
			<!--加载-->
	<!--		<div class="loading"><img src="${basePath}assets/Images/loading.gif" alt=""></div>-->
			<div class="item clf">
				<!--视频-->
				<div class="play">
					<div class="link">
						<a href="#"></a>
						<div class="vid" id="win-p-cont">
							<img src="${basePath}assets/Images/video.png" alt="" id="video-bg">
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
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<div class="bottom">
		<div class="tips">按“返回”按钮回到第1页</div>
		<div class="more"><img src="${basePath}assets/Images/point_down.png" alt=""></div>
		<div class="page"><span>共0+</span>1/0</div>
	</div>
</body>
</html>
