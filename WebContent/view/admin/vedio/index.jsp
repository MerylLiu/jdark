<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<link href="${basePath}assets/Content/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<script src="${basePath}assets/Scripts/jquery.ztree.all.js"></script>
<script src="${basePath}assets/Scripts/admin.menu.js"></script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="col-md-4">
			<div class="panel panel-default" id="TestPanel">
				<div class='panel-heading'>
					<h4 class='panel-title'>
						系统菜单 <a data-toggle='collapse' href='#collapse-TestPanel'
							aria-expanded='true' class='pull-right btn-collapse'> <i
							class='fa fa-chevron-circle-up'></i>
						</a>
					</h4>
				</div>
				<div id="collapse-TestPanel" class="panel-collapse collapse in">
					<div class="panel-body">
						<ul id="tree-menu" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div class="panel panel-default" id="editPanel">
				<div class='panel-heading'>
					<h4 class='panel-title'>
						&nbsp; <a data-toggle='collapse' href='#collapse-editPanel'
							aria-expanded='true' class='pull-right btn-collapse'> <i
							class='fa fa-chevron-circle-up'></i>
						</a>
					</h4>
				</div>
				<div class='toolbar'>
					<button id='btn-add' class='btn btn-default' style='' type='Button'
						onclick=''>
						<i class='fa fa-plus text-success'></i>新增
					</button>
					<button id='btn-save' class='btn btn-default' style='' type='Button'
						onclick=''>
						<i class='fa fa-save text-success'></i>保存
					</button>
					<button id='btn-del' class='btn btn-default' style='' type='Button'
						onclick=''>
						<i class='fa fa-trash text-danger'></i>删除
					</button>
				</div>
				<div id="collapse-editPanel" class="panel-collapse collapse in">
					<div class="panel-body">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
