<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>业务参数管理</title>
<%@include file="/assets/inc.jsp"%>
<link href="${basePath}assets/content/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}assets/content/jquery.fancybox-1.3.4.css" />
<link href="${basePath}assets/content/uploadify.css" rel="stylesheet">
<script src="${basePath}assets/scripts/jquery.ztree.all.js"></script>
<script type="text/javascript" src="${basePath}assets/scripts/sys.bizparams.js"></script>
<script src="${basePath}assets/scripts/jquery.uploadify.js"></script>
<script src="${basePath}assets/scripts/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="${basePath}assets/scripts/jquery.md5.js"></script>
<script type="text/javascript">
</script>
<script type="text/javascript">
	var staticUrl = '${staticUrl}'; 
</script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="col-md-3">
			<div class="panel panel-default" data-fit="true" id="TestPanel">
				<div class='panel-heading'>
					<h4 class='panel-title'>
						业务参数 <a data-toggle='collapse' href='#collapse-TestPanel' aria-expanded='true' class='pull-right btn-collapse'>
							<i class='fa fa-chevron-circle-up'></i>
						</a>
					</h4>
				</div>
				<div class='toolbar'>
					<button id='btn-add-left' class='btn btn-default'  type='Button' data-permision='Add'>新增</button>
					<button id='btn-edit-left' class='btn btn-default' type='Button' data-permision='Edit'>编辑</button>
					<button id='btn-del-left' class='btn btn-default'  type='Button' data-permision='Delete'>删除</button>
				</div>
				<div id="collapse-TestPanel" class="panel-collapse collapse in">
					<div class="panel-body">
						<ul id="tree-category" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div class="panel panel-default" data-fit="true" id="editPanel">
				<div class='panel-heading'>
					<h4 class='panel-title'>
						&nbsp; <a data-toggle='collapse' href='#collapse-editPanel' aria-expanded='true' class='pull-right btn-collapse'>
							<i class='fa fa-chevron-circle-up'></i>
						</a>
					</h4>
				</div>
				<div class='toolbar'>
					<button id='btn-add' class='btn btn-default'  type='Button' data-permision='Add'>新增</button>
					<button id='btn-edit' class='btn btn-default' type='Button' data-permision='Edit'>编辑</button>
					<button id='btn-del' class='btn btn-default'  type='Button' data-permision='Delete'>删除</button>
				</div>
				<div id="collapse-editPanel" class="panel-collapse collapse in">
				<div class="">
					<div id="grid"></div>
				</div>
			</div>
			</div>
		</div>
	</div>
			
	<script type="text/x-jquery-tmpl" id="data-tmpl-left">
		<form class="form-horizontal" id="form-dictionary-left">
			<div class="form-group">
				<label class="col-sm-3 control-label">参数名称</label>
				<div class="col-sm-9">
					<div class="col-sm-11">
						<input type="text" class="form-control" placeholder="请输入参数名称" maxlength="45" name="KeyName" >
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">参数编码</label>
				<div class="col-sm-9">
					<div class="col-sm-11">
						<input type="text" class="form-control" placeholder="请输入参数编码" maxlength="45" name="KeyCode" >
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
			</div>
		</form>
	</script>
	<script type="text/x-jquery-tmpl" id="data-tmpl">
		<form class="form-horizontal" id="form-dictionary">
			<input type="hidden" name="KeyName" id="name" >
			<div class="form-group">
				<label class="col-sm-2 control-label">参数编码</label>
				<div class="col-sm-4">
					<div class="col-sm-11">
						<input type="text" class="form-control" placeholder="请输入参数名"
						 maxlength="20" name="KeyCode" id="code" readonly>
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
				<label class="col-sm-2 control-label" >显示文本</label>
				<div class="col-sm-4">
					<div class="col-sm-11">
						<input type="text" class="form-control" placeholder="请输入显示文本" maxlength="30" name="Text">
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">参数类型</label>
				<div class="col-sm-4">
					<div class="col-sm-11">
						<div class="radio">
							<lable class="radio-inline"><input type="radio"  value="0" name="IsFile">文件</lable>
							<lable class="radio-inline"><input type="radio"  value="1" name="IsFile" checked="checked">文本</lable>
						</div>
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
				<label class="col-sm-2 control-label" id="isFile">参数值</label>
				<div class="col-sm-4"  id="text">
					<div class="col-sm-11">
						<input type="text" class="form-control" placeholder="请输入参数值" id="text-content" maxlength="15" name="KeyValue" >
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
               	<div class="col-sm-4" id="file" hidden>
					<div class="col-sm-11">
                  	 		<div id="upload"></div>
                   		<input type="hidden" class="form-control" id="file-url" name="KeyValue"> 
						<span id="file-name"></span>
					</div>
					<span class="cols-sm-1 required">*</span>
               	</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">是否启用</label>
				<div class="col-sm-4">
					<div class="col-sm-11">
						<div class="radio">
							<lable class="radio-inline"><input type="radio"  value="0" name="IsEnable">否</lable>
							<lable class="radio-inline"><input type="radio"  value="1" name="IsEnable" checked="checked">是</lable>
						</div>
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
				<label class="col-sm-2 control-label">是否可见</label>
				<div class="col-sm-4">
					<div class="col-sm-11">
						<div class="radio">
							<lable class="radio-inline"><input type="radio"  value="0" name="IsVisible">否</lable>
							<lable class="radio-inline"><input type="radio"  value="1" name="IsVisible" checked="checked">是</lable>
						</div>
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">排序号</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" id="txt-order" name="OrderNum" placeholder="请输入排序号" maxlength="8">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
			</div>
			<div class="form-group">
				<label for="remark" class="col-sm-2 control-label">备注</label>
				<div class="col-sm-10">
					<textarea rows="5" class="form-control" id="remark"
						maxlength="200" name="Remark" placeholder="备注"></textarea>
				</div>
			</div>
		</form>
	</script>
</body>
</html>
