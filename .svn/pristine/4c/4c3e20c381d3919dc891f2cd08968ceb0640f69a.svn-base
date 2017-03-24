<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh_CN" style="overflow: hidden;">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <!-- Set render engine for 360 browser -->
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <title> 后台管理中心</title>
    <meta name="description" content="This is page-header (.page-header &gt; h1)">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="<%=basePath %>assets/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath %>assets/Content/admin.css" rel="stylesheet">
    <link href="<%=basePath %>assets/Content/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!--[if IE 7]> <link rel="stylesheet" href="<%=basePath %>assets/Content/font-awesome/4.4.0/css/font-awesome-ie7.min.css"> <![endif]-->
    <!--[if lte IE 8]> <link rel="stylesheet" href="<%=basePath %>assets/Content/admin-ie.css?" /> <![endif]-->

    <script src="<%=basePath %>assets/Scripts/jquery.min.js"></script>
    <script src="<%=basePath %>assets/bootstrap3/js/bootstrap.min.js"></script>
    <script src="<%=basePath %>assets/Scripts/admin.js"></script>
    <script type="text/javascript">
		$(document).ready(function(){
			$('#nav-menu-list').menu('<%=basePath%>admin/menu/menuList');
		})
    </script>
</head>

<body style="min-width:900px;" screen_capture_injected="true">
    <div id="loading"><i class="loadingicon"></i><span>努力加载中...</span></div>
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container-fluid">
                <a href="/index.php?g=admin&m=index&a=index" class="navbar-brand">
					<small><img src="<%=basePath %>assets/Images/admin/logo-18.png">后台管理中心</small>
				</a>
                <div class="pull-left nav_shortcuts">
                    <a class="btn btn-xs btn-warning" href="<%=basePath %>" title="网站首页" target="_blank"> <i class="fa fa-home"></i> </a>
                    <a class="btn btn-xs btn-success" href="javascript:openapp('/index.php?g=portal&m=admin_term&a=index','index_termlist','分类管理');" title="分类管理"> <i class="fa fa-th"></i> </a>
                    <a class="btn btn-xs btn-info" href="javascript:openapp('/index.php?g=portal&m=admin_post&a=index','index_postlist','文章管理');" title="文章管理"> <i class="fa fa-pencil"></i> </a>
                    <a class="btn btn-xs btn-danger" href="javascript:openapp('/index.php?g=admin&m=setting&a=clearcache','index_clearcache','清除缓存');" title="清除缓存"> <i class="fa fa-trash-o"></i> </a>
                </div>
                <ul class="nav simplewind-nav pull-right">
                    <li>
                        <a data-toggle="dropdown" href="#" class="dropdown-toggle">
							<img class="nav-user-photo" width="30" height="30" src="<%=basePath %>assets/Images/admin/user.png" alt="admin">
							<span class="user-info">欢迎, admin</span>
							<i class="fa fa-caret-down"></i>
						</a>
                        <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
                            <li><a href="javascript:openapp('/index.php?g=admin&m=setting&a=site','index_site','网站信息');"><i class="fa fa-cog"></i> 网站信息</a></li>
                            <li><a href="javascript:openapp('/index.php?g=admin&m=user&a=userinfo','index_userinfo','修改信息');"><i class="fa fa-user"></i> 修改信息</a></li>
                            <li><a href="/index.php?g=admin&m=public&a=logout"><i class="fa fa-sign-out"></i> 退出</a></li>
                        </ul>
                    </li>
				</ul>
			</div>
        </div>
    </div>
    <div class="main-container container-fluid">
        <div class="sidebar" id="sidebar">
            <!-- <div class="sidebar-shortcuts" id="sidebar-shortcuts"> </div> -->
            <div id="nav_wraper">
                <ul class="nav nav-list" id="nav-menu-list">
                </ul>
            </div>
        </div>
        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
				<a id="task-pre" class="task-changebt"><i class="fa fa-caret-left"></i></a>
                <div id="task-content">
                    <ul class="macro-component-tab clearfix" id="task-content-inner">
                        <li class="macro-component-tabitem noclose" app-id="0" app-url="index.php" app-name="首页">
							<span class="macro-tabs-item-text">首页</span>
						</li>
                    </ul>
                </div>
				<a id="task-next" class="task-changebt"><i class="fa fa-caret-right"></i></a>

				<div class="dropdown pull-right task-btn" id="btn-tabs-close">
					<a class="dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
						关闭<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
						<li role="presentation"><a id="btn-close-current" role="menuitem" tabindex="-1" href="javascript:void(0);">关闭当前</a></li>
						<li role="presentation" class="divider"></li>
						<li role="presentation"><a id="btn-close-all" role="menuitem" tabindex="-1" href="javascript:void(0);">关闭全部</a></li>
					</ul>
				</div>

				<div id="refresh_wrapper" class="task-btn pull-right" title="刷新"><i class="fa fa-refresh right_tool_icon"></i></div>

				<div id="task-extend" class="dropdown pull-right task-btn">
					<a class="dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" title="更多">
						<span class="glyphicon glyphicon-list-alt"></span>
					</a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
						<li role="presentation"><a id="btn-close-current" role="menuitem" tabindex="-1" href="javascript:void(0);">关闭当前</a></li>
						<li role="presentation"><a id="btn-close-all" role="menuitem" tabindex="-1" href="javascript:void(0);">关闭全部</a></li>
					</ul>
				</div>
			</div>

            <div class="page-content" id="content">
				<iframe src="javascript:void(0);" style="width:100%;height: 100%;" frameborder="0" id="appiframe-0" class="appiframe"></iframe>
			</div>
        </div>
    </div>
</body>

</html>
