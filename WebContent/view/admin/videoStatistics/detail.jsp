<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script src="${basePath}assets/scripts/admin.video.statistics.detail.js"></script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="panel panel-default" id="test-panel">
			<div class='panel-heading'>
				<h4 class='panel-title'>
					视频详情 <a data-toggle='collapse' href='#collapse-test-panel'
						aria-expanded='true' class='pull-right btn-collapse'> <i
						class='fa fa-chevron-circle-up'></i>
					</a>
				</h4>
			</div>
			<div class="toolbar">
				<button class="btn btn-default" type="button"
					id="btn-detail-query-list">
					<i class='fa fa-search text-success'></i>查询
				</button>
				<button class="btn btn-default" type="button"
					id="btn-download-table">
					<i class='fa fa-file-excel-o text-success'></i>导出Excel
				</button>
				<button class="btn btn-default" type="button" id="btn-back">
					<i class='fa fa-undo text-success'></i>返回
				</button>
			</div>
			<div id="collapse-test-panel" class="panel-collapse collapse in">
				<div class="panel-body">
					<form class="form-horizontal" id="form-data">
						<br/>
						<div class="form-group">
							<span id="ctl-date">
								<label class="col-sm-1 control-label">录入时间:</label>
								<input type="text" class="form-control" name="StartDate" placeholder="开始时间" maxlength="20" id="start-date">
								<label class="control-label">到</label>
								<input type="text" class="form-control" name="EndDate" placeholder="结束时间" maxlength="20" id="end-date">
							</span>
							<label class="control-label" style="margin-left:50px">省</label>
							<input type="text" class="form-control" name="Province" placeholder="请选择省" maxlength="20" id="txt-province">
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">城市:</label>
							<div id="chk-city"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">区域:</label>
							<div id="chk-area"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">营销渠道:</label>
							<div id="chk-source-channel"></div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<br />
		<div class="panel panel-default" id="test-panel">
			<!-- Tab panes -->
			<div id="collapse-editPanel" class="panel-collapse collapse in">
				<div id="detail-grid"></div>
			</div>
		</div>
	</div>
</body>
</html>
