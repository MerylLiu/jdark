<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.iptv.com/UrlUtil" prefix="u"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/assets/inc_tv.jsp"%>
<link rel="stylesheet" href="${basePath}assets/Content/common.css"
	type="text/css" />
<link rel="stylesheet" href="${basePath}assets/Content/videoDetails.css"
	type="text/css" />
</head>
<body>
	<!---->
	<div class="main">
		<div class="title">${Name}</div>
		<div class="videos">
			<a href="javascript:void(0)">111</a>
			<div class="video-pic">
				<img src="${basePath}image/index?p=${u:urlEncode(ImageUrl)}">
			</div>
			<div class="video"></div>
		</div>
		<div class="content">
			<div class="introduce">
				<h3>视频简介</h3>
				<div class="intro-text">
					${Description}
				</div>
			</div>
			<div class="position">
				<h3>故事通讯：</h3>
				<div class="intro-text">
					${Tel}
				</div>
				<h3>故事发生地址：</h3>
				<div class="intro-text">${Address}</div>
			</div>
			<div class="btn">
				<a href="javascript:void(0)">1111</a>
				<p class="btn-p focus">二维码</p>
			</div>
		</div>

	</div>
	<div class="pop" style="display: block">
		<a href="${basePath}home/index">1111</a>
		<div class="pop-con">
			<div class="pop-t">${Name}</div>
			<div class="code-pic">
				<img src="${basePath}image/index?p=${u:urlEncode(QrCode)}">
			</div>
			<div class="pop-notes">
				打开<span>微信</span>扫描二维码
			</div>

		</div>
	</div>
</body>
</html>
