<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script type="text/javascript" src="${basePath}assets/Scripts/admin.video.js"></script>
<script type="text/javascript">
	var provinces = ${province};
	var sellers = ${sellers};
	var category = ${category};
	var videoStatus = ${status};
	var cost = ${cost};
	var costFZ = ${costFZ};
	var videoStyle = ${videoStyle};
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
				<button id='btn-online' class='btn btn-default' style='' type='Button'
					onclick=''>
					<i class='fa fa-cloud-upload text-primary'></i>上线
				</button>
				<button id='btn-offline' class='btn btn-default' style='' type='Button'
					onclick=''>
					<i class='fa fa-cloud-upload text-primary'></i>下线
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
				<legend><h5>商家信息</h5></legend>
				<div class="form-group">
					<label class="col-sm-2 control-label">商家名称</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" placeholder="商家名称" maxlength="16" id="txt-sel-name">
					</div>
					<label class="col-sm-2 control-label">商家编号</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" placeholder="商家编号" maxlength="25" id="txt-sel-code" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商家电话</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" placeholder="商家电话" maxlength="26" id="txt-sel-tel" readonly>
					</div>
					<label class="col-sm-2 control-label">家服网ID</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" placeholder="家服网ID" maxlength="20" id="txt-sel-sid" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商家地址</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" placeholder="商家地址" maxlength="150" id="txt-sel-addr" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">开店时间</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" placeholder="开店时间" maxlength="15" id="txt-sel-sdate" readonly>
					</div>
					<label class="col-sm-3 control-label">是否安装电信宽带</label>
					<div class="col-sm-3">
						<div class="radio">
							<label class="radio-inline"> <input type="radio"
								name="IsInstall" id="yes" value="1" disabled> 是
							</label> <label class="radio-inline"> <input type="radio"
								name="IsInstall" id="no" value="0" disabled> 否
							</label>
						</div>
					</div>
				</div>
			</fieldset>
		</form>
		<br/>
		<form class="form-horizontal" id="form-video">
			<input type="text" name="Id">
			<input type="text" name="SellerKeyId">
			<fieldset>
				<legend><h5>视频信息</h5></legend>
				<div class="form-group">
					<label class="col-sm-2 control-label">视频编号</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="Code" placeholder="视频编号" maxlength="10">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
					<label class="col-sm-2 control-label">视频名称</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="Name" placeholder="视频名称" maxlength="20">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">简拼</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="Jianpin" placeholder="视频名称简拼" maxlength="10">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
					<label class="col-sm-2 control-label">全拼</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="Quanpin" placeholder="视频名称全拼" maxlength="20">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">视频制作费</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="Cost" placeholder="视频制作费" maxlength="20" id="txt-cost">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
					<label class="col-sm-2 control-label">视频放置费</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="CostFZ" placeholder="视频放置费" maxlength="20" id="txt-costfz">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">视频风格</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="Style" placeholder="视频风格" maxlength="10" id="txt-style">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
					<label class="col-sm-2 control-label">视频分类</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="CategoryId" placeholder="视频分类" maxlength="10" id="txt-category">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">视频链接</label>
					<div class="col-sm-10">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="VideoUrl" placeholder="视频链接" maxlength="150">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">视频有效期</label>
					<div class="col-sm-10">
						<div class="col-sm-5">
							<input type="text" class="form-control" name="StartDate" placeholder="开始时间" maxlength="20">
						</div>
						<label class="col-sm-1 control-label" style="text-align:center">到</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="EndDate" placeholder="结束时间" maxlength="20">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">视频介绍</label>
					<div class="col-sm-10">
						<div class="col-sm-11">
							<textarea class="form-control" rows="3" placeholder="视频介绍" name="Description" maxlength="65"></textarea>
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">静态图</label>
					<div class="col-sm-10">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="ImageUrl" placeholder="视频静态图" maxlength="150">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">动态图</label>
					<div class="col-sm-10">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="GifUrl" placeholder="视频动态图" maxlength="150">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">二维码</label>
					<div class="col-sm-10">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="QrCode" placeholder="二维码信息" maxlength="150">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">排序号</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="txt-order" name="OrderNum" placeholder="排序号" maxlength="8">
					</div>
				</div>
			</fieldset>
			<fieldset>
				<legend><h5>展示区域</h5></legend>
				<div class="form-group">
					<label class="col-sm-2 control-label">省</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="txt-province" name="Province" maxlength="10">
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">市</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="txt-city" name="City" maxlength="10">
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">区／县</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="txt-area" name="Area" maxlength="10">
					</div>
					<span class="cols-sm-1 required">*</span>
				</div>
			</fieldset>
			<fieldset>
				<legend><h5>视频来源及制作商</h5></legend>
				<div class="form-group">
					<label class="col-sm-2 control-label">提供商家</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="Provider" placeholder="提供商家" maxlength="50">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
					<label class="col-sm-2 control-label">视频制作商</label>
					<div class="col-sm-4">
						<div class="col-sm-11">
							<input type="text" class="form-control" name="Maker" placeholder="视频制作商" maxlength="50">
						</div>
						<span class="cols-sm-1 required">*</span>
					</div>
				</div>
			</fieldset>
		</form>
	</script>
</body>
</html>
