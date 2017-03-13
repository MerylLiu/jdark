<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<link href="${basePath}assets/Content/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<script src="${basePath}assets/Scripts/jquery.ztree.all.js"></script>
<script src="${basePath}assets/Scripts/admin.category.js"></script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="col-md-4">
			<div class="panel panel-default" id="TestPanel">
				<div class='panel-heading'>
					<h4 class='panel-title'>
						视频分类 <a data-toggle='collapse' href='#collapse-TestPanel'
							aria-expanded='true' class='pull-right btn-collapse'> <i
							class='fa fa-chevron-circle-up'></i>
						</a>
					</h4>
				</div>
				<div id="collapse-TestPanel" class="panel-collapse collapse in">
					<div class="panel-body">
						<ul id="tree-category" class="ztree"></ul>
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
						<form class="form-horizontal" id="form-data">
							<input type="hidden" name="Id" id="cateId">
							<input type="hidden" name="ParentId" id="parentId">
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">分类名称</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="name" name="Name"
										placeholder="分类名称">
								</div>
							</div>
							<div class="form-group">
								<label for="orderNum" class="col-sm-2 control-label">序号</label>
								<div class="col-sm-10">
									<input type="number" class="form-control" id="orderNum"
										name="OrderNum" placeholder="序号">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<div class="radio" id="sts-status">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="remark" class="col-sm-2 control-label">备注</label>
								<div class="col-sm-10">
									<textarea rows="3" class="form-control" id="remark"
										name="Remark" placeholder="备注"></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var status = '${status}';
	</script>
</body>
</html>
