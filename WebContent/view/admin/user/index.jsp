<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script type="text/javascript" src="${basePath}assets/Scripts/admin.user.js"></script>
<script type="text/javascript" src="${basePath}assets/Scripts/jquery.md5.js"></script>
<script type="text/javascript">
   var sex = ${sex};
</script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="panel panel-default" id="editPanel">
			<div class='toolbar'>
				<button id='btn-add' class='btn btn-default' style='' type='Button'
					onclick=''>
					<i class='fa fa-plus text-success'></i>新增
				</button>
				<button id='btn-edit' class='btn btn-default' style='' type='Button'
					onclick=''>
					<i class='fa fa-edit text-success'></i>编辑
				</button>
				<button id='btn-del' class='btn btn-default' style='' type='Button'
					onclick=''>
					<i class='fa fa-trash text-danger'></i>删除
				</button>
				<div class="wrap-collapse">
					<a data-toggle='collapse' href='#collapse-editPanel'
						aria-expanded='true' class='pull-right btn-collapse'> <i
						class='fa fa-chevron-circle-up'></i>
					</a>
				</div>
			</div>
			<div id="collapse-editPanel" class="panel-collapse collapse in">
				<div class="">
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/x-jquery-tmpl" id="data-tmpl">
		<form class="form-horizontal" id="form-seller">
			<input type="hidden" name="Id" id="menuId">
			<fieldset>
				<legend><h5>用户信息</h5></legend>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户编码</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" placeholder="Code" maxlength="16" name="Code" >
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
					<label class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" placeholder="用户名" maxlength="16" name="UserName" >
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">账号</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" placeholder="账号" maxlength="25" name="LoginName"  >
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
					<label class="col-sm-2 control-label">密码</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="password" class="form-control" placeholder="密码" maxlength="25" name="Password">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">组织ID</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" placeholder="组织ID" maxlength="25" name="OrganizationId">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
					<label class="col-sm-2 control-label">性别</label>
					<div class="col-sm-4">
						<div class="col-sm-11" id="rdi-sex">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电话</label>
						<div class="col-sm-4">
							<div class="col-sm-11">
								<input type="text" class="form-control" placeholder="电话" maxlength="25" name="Tel">
							</div>
							<span class="cols-sm-1 required">*</span>
						</div>
					<label class="col-sm-2 control-label">邮箱</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" placeholder="邮箱" maxlength="25" name="Email">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">角色ID</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" placeholder="角色ID" maxlength="25" name="RoleId">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
			</fieldset>
		</form>
	</script>
</body>
</html>
