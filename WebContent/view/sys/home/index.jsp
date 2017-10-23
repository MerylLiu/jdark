<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<%@include file="/assets/inc.jsp"%>
<script type="text/javascript" src="${basePath}assets/scripts/admin.home.js"></script>
<style type="text/css">
.btn-info {
	width: 25px;
	height: 25px;
	border: 1px #dadada solid;
	border-radius: 50%;
	text-align: center;
	vertical-align: middle;
	line-height: 25px;
}

.fa-chevron-left {
	color: #F8F8FF;
	margin: 0;
	padding: 0;
	margin-left: -3px;
	margin-top: 2px;
	text-align: center;
}

.fa-chevron-right {
	color: #F8F8FF;
	margin: 0;
	padding: 0;
	margin-left: 1px;
	margin-top: 2px;
	text-align: center;
}
.login-record{
	color: #333;
	font-size:12px;
}
.login-record .user-name{
	display:inline-block;
	min-width: 35px;
}
</style>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="form-group clearfix">
			<div class="col-sm-8">
				<div class="panel panel-default" id="editPanel" data-fit="true">
					<div class="panel-heading">代办事项</div>
					<div class="panel-body no-padding" style="height: 270px;">
						<div id="todo-grid"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-default">
					<div id="collapse-editPanel">
						<div class="panel-body">
							<marquee direction="up" truespeed="truespeed" height="117px" behavior="scroll" scrolldelay="300">
								<c:forEach items="${loginRecord}" var="record">
									<div class="login-record">用户 <span class="text-primary user-name">${record.UserName}</span> 于 
									<span class="text-warning"><fmt:formatDate value="${record.LoginDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span> 登录系统。</div>
								</c:forEach>
							</marquee>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4" style="margin-top:10px;">
				<div class="panel panel-default">
					<div class="panel-heading">我的消息</div>
					<div class="panel-body">
						<div id="calendar" style="height:117px"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group clearfix">
			<div class="col-sm-6">
				<div class="panel panel-default" id="TestPanel">
					<div class="toolbar" style="border-bottom: none;">
						<div class="pull-right">
							<button class="btn-info" type="button" id="btn-left-chart">
								<i class='fa fa-chevron-left'></i>
							</button>
							<span id="year-chart"></span><span>年</span>
							<button class="btn-info" type="button" id="btn-right-chart">
								<i class='fa fa-chevron-right'></i>
							</button>
						</div>
					</div>
					<div id="collapse-leftPanel" class="panel-collapse collapse in">
						<div class="panel-body">
							<div id="chart"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="panel panel-default" id="TestPanel">
					<div class="toolbar" style="border-bottom: none;">
						<div class="pull-right">
							<button class="btn-info" type="button" id="btn-left-bar">
								<i class='fa fa-chevron-left text-success'></i>
							</button>
							<span id="year-bar"></span><span>年</span>
							<button class="btn-info" type="button" id="btn-right-bar">
								<i class='fa fa-chevron-right text-success'></i>
							</button>
						</div>
					</div>
					<div id="collapse-rightPanel" class="panel-collapse collapse in">
						<div class="panel-body">
							<div id="bar"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>