<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script type="text/javascript" src="${basePath}assets/scripts/sys.log.login.js"></script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="panel panel-default" data-fit="true" id="editPanel">
			<div class='panel-heading'>
				<h4 class='panel-title'>
					登录日志<a data-toggle='collapse' href='#collapse-editPanel'
						aria-expanded='true' class='pull-right btn-collapse'> <i
						class='fa fa-chevron-circle-up'></i></a>
				</h4>
			</div>
			<div id="collapse-editPanel" class="panel-collapse collapse in">
				<div class="">
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
