<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>IPTV</title>
<link rel="stylesheet" href="${basePath}assets/Content/common.css" type="text/css" />
<script type='text/javascript' src='${basePath}assets/Scripts/jquery-1.8.3.min.js'></script>
<script type="text/javascript">
	var basePath = '${basePath}';
</script>
