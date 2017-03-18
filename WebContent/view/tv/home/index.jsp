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
                        $('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage);
                    }
                }
            });

            // 分类导航
            $('.nav a').on( 'keydown', function(e) {
				switch (e.keyCode) {
					case 0x0025: // 左
						IPTV.Focus.nav(0, function() {
							// 设置页数
							currentPage = 1;
							totalPage = 0;
							totalVideo = 0;
							categoryId = $('.nav a:focus').find('input[type="hidden"]').val();
							
							loadVideo(currentPage,categoryId)

							ncid = $('.nav a:focus').parent().parent().next().find('input[type="hidden"]').val();
							loadPreviewList(ncid);

							$('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage);
						});
						// code
						break;
					case 0x0026: // 上
						IPTV.Focus.nav(2);
						break;
					case 0x0027: // 右
						IPTV.Focus.nav(1, function() {
							// 设置页数
							currentPage = 1;
							totalPage = 0;
							totalVideo = 0;
							categoryId = $('.nav a:focus').find('input[type="hidden"]').val();
							
							loadVideo(currentPage,categoryId)

							ncid = $('.nav a:focus').parent().parent().next().find('input[type="hidden"]').val();
							loadPreviewList(ncid);

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
                totalPage = 0,
                totalVideo = 0; //当前页数，总页数，总视频数
            var tipsTimer; //回到第一页定时器
            var tipsPage = 1; //超过页数提示返回
            var catgoryId = 0;//当前分类
            var playIndex = 1;
			var time = 0;
            var timer = new Array();
            var epgdomain = Authentication.CTCGetConfig("EPGDomain");
            //var epgdomain = "";
            var host = epgdomain.replace(/(http:\/\/(\w|\.|:)*\/)(.*)/g, '$1');
            
    		var loadVideo = function(p,cid){
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
    							 + '<a href="#"></a>'
    							 + '<div class="vid">'
    							 + '<div class="thumbnail"><img src="${basePath}'+('image/index?p='+encodeURI(v.ImageUrl))+'" alt=""></div>'
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

					if(0 == cid && p==1){
						if(length >0) {
							$(timeList[0]).parent().addClass('on')
							play($(codeList[0]).val());
						}
						interval(timeList,codeList)
					}else{
						for(var i=0;i<timer.length;i++){
							clearTimeout(timer[i]);
						}
					}
    			});
    		};
    		
    		var interval = function(timeList,codeList){
    			var t = setTimeout(function(){
					time++;
					var length = timeList.length;
					if(time == $(timeList[playIndex]).val()){
						play($(codeList[playIndex]).val());
						time = 0;

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
    		
    		var play = function(code){
				var type = 'hw';
				$('.play .vid').find('img').hide();

    			if(type == 'hw'){
					var urlhw = host + "EPG/jsp/tools/playControl/playUrlInVas.jsp?CODE="+code+"&PLAYTYPE=1&CONTENTTYPE=0&BUSINESSTYPE=1&SPID=20001041&USERID=&USERTOKEN=";

					$.get(urlhw, {}, function(data) {
						data = jQuery.parseJSON(data);
						var playurl = data.retDesc.replace(/.*http/, "http");
						var left = $('#win-player').offset().left;
						var top = $('#win-player').offset().top;

						var nurl = basePath + "video/player?mediatype=1&playurl=" + escape(playurl) + "&left=" + left + "&top=" + top + "&width=620&height=313";
						$("#win-player").attr("src", nurl);
					})
    			}
    		}

    		var loadPreviewList = function(cid){
    			var data = {cid:cid};
    			$.get(basePath + 'home/previewList',data,function(res){
    				$('#preview-list').html('');

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

			loadVideo(currentPage,catgoryId);
			loadPreviewList($($('.nav input:hidden')[1]).val());
            
            $('.video a').live( 'keydown', function(e) {
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
							totalPage = 0;
							totalVideo = 0;
							$('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage);
						});
						break;
					case 0x0026: //上
						// 向上加载内容数据
						IPTV.Focus.video($(this), 2, function() {
							// 设置页数
							currentPage = currentPage <= 1 ? 1 : --currentPage;
							$('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage);
							
							loadVideo(currentPage,categoryId);
						}, [currentPage, totalPage]);
						break;
					case 0x0027: // 右
						// 向右加载分类数据
						IPTV.Focus.video($(this), 1, function() {
							// 设置页数
							currentPage = 1;
							totalPage = 0;
							totalVideo = 0;
							$('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage);
						});
						break;
					case 0x0028: //下
						// 向下加载内容数据
						IPTV.Focus.video($(this), 3, function() {
							// 设置页数
							currentPage = currentPage >= totalPage ? totalPage : ++currentPage;
							$('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage); 

							loadVideo(currentPage,categoryId);
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
					case 0x0008:
						// 设置页数
						currentPage = 1;
						$('.page').html( '<span>共' + totalVideo + '</span>' + currentPage + '/' + totalPage); 

						loadVideo(currentPage,categoryId);
						break;
				}
			});
            
        	// 按8键
        	$(document).on('keydown', function(e) {
        		if(e.keyCode == 0x0038) {	// 返回
        			window.location.href = "http://115.28.77.76:9600/iptv-web/home";
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
						<a href="#"><input type="hidden" value="0"></a> <span id="test">全部</span>
					</div>
				</li>
				<c:forEach items="${categories}" var="cat">
					<li>
						<div class="link">
							<a href="#"><input type="hidden" value="${cat.Id}"></a> <span>${cat.Name}</span>
						</div>
					</li>
				</c:forEach>
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
							<iframe id="win-player" width="620" height="310" src="javascript:void(0);"  marginwidth="0" marginheight="0" scrolling="no"></iframe>
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
		<div class="more">
			<img src="${basePath}assets/Images/point_down.png" alt="">
		</div>
		<div class="page"><span>共500+</span>1/30</div>
	</div>
</body>
</html>
