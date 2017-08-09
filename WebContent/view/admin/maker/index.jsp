<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script type="text/javascript" src="${basePath}assets/scripts/admin.maker.js"></script>
<script type="text/javascript">
	var provinces = ${province};
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
		<form class="form-horizontal" id="form-data">
			<input type="hidden" name="Id" id="menuId">
			<div class="form-group">
				<label class="col-sm-3 control-label">制作商编号</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="Code" placeholder="制作商编号" maxlength="20">
				</div>
				<span class="cols-sm-1 required">*</span>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">制作商名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="Name" placeholder="制作商名称" maxlength="16">
				</div>
				<span class="cols-sm-1 required">*</span>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">制作商电话</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="Tel" placeholder="制作商电话" maxlength="13">
				</div>
				<span class="cols-sm-1 required">*</span>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">省</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="txt-province" name="Province" maxlength="10">
				</div>
				<span class="cols-sm-1 required">*</span>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">市</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="txt-city" name="City" maxlength="10">
				</div>
				<span class="cols-sm-1 required">*</span>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">区／县</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="txt-area" name="Area" maxlength="10">
				</div>
				<span class="cols-sm-1 required">*</span>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">制作商地址</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="Address" placeholder="制作商地址" maxlength="150">
				</div>
				<span class="cols-sm-1 required">*</span>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">家服网ID</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="SellerId" placeholder="家服网ID" maxlength="20">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">排序号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="txt-order" name="OrderNum" placeholder="排序号" maxlength="8">
				</div>
			</div>
		</form>
	</script>
</body>
</html>
