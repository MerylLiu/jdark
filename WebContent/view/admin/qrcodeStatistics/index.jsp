<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script src="${basePath}assets/scripts/admin.qrcode.statistics.js"></script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="panel panel-default" id="test-panel">
			<div class='panel-heading'>
				<h4 class='panel-title'>
					二维码扫描量 <a data-toggle='collapse' href='#collapse-test-panel'
						aria-expanded='true' class='pull-right btn-collapse'> <i
						class='fa fa-chevron-circle-up'></i>
					</a>
				</h4>
			</div>
			<div class="toolbar">
				<button class="btn btn-default" type="button" id="btn-query">
					<i class='fa fa-search text-success'></i>查询
				</button>
				<button class="btn btn-default" type="button" id="btn-detail-query">
					<i class='fa fa-search-plus text-success'></i>查看详情
				</button>
				<button class="btn btn-default" type="button"
					id="btn-download-table">
					<i class='fa fa-file-excel-o text-success'></i>导出Excel
				</button>
			</div>
			<div id="collapse-test-panel" class="panel-collapse collapse in">
				<div class="panel-body">
					<form class="form-horizontal" id="form-data">
						<br />
						<div class="form-group">
							<label class="col-sm-1 control-label">商家名称:</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" name="SellerName"
									placeholder="商家名称" maxlength="20" id="txt-seller-name">
							</div>
							<label class="col-sm-1 control-label">视频编号:</label>
							<div class="col-sm-1">
								<input type="text" class="form-control" name="VideoCode"
									placeholder="视频编号" maxlength="20" id="txt-video-id">
							</div>
							<label class="control-label"  style="margin-left: 50px">录入时间:</label> 
							<input type="text" class="form-control" name="StartDate"
								placeholder="开始时间" maxlength="20" id="start-date"> 
							<label class="control-label">到</label> 
							<input type="text" class="form-control" name="EndDate" placeholder="结束时间"
								maxlength="20" id="end-date">
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">省:</label> <input
								type="text" class="form-control" name="Province"
								placeholder="请选择省" maxlength="20" id="txt-province">
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">城市:</label>
							<div id="chk-city"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">区域:</label>
							<div id="chk-area"></div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<br />
		<div class="panel panel-default" id="test-panel">
			<!-- Tab panes -->
			<div id="collapse-editPanel" class="panel-collapse collapse in">
				<div id="grid"></div>
			</div>
		</div>
	</div>
</body>
</html>
