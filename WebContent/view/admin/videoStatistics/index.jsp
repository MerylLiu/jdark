<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script src="${basePath}assets/scripts/admin.video.statistics.js"></script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="panel panel-default" id="test-panel">
			<div class='panel-heading'>
				<h4 class='panel-title'>
					昨日关键指标<a data-toggle='collapse' href='#collapse-test-panel'
						aria-expanded='true' class='pull-right btn-collapse'> <i
						class='fa fa-chevron-circle-up'></i>
					</a>
				</h4>
			</div>
			<div id="collapse-test-panel" class="panel-collapse collapse in">
				<div class="panel-body">
					<div class="row-fluid clearfix">
						<div class="col-md-3 block-info" style="padding-right: 50px">
							<div class="info-title col-md-6">视频播放总量：${videoPlayList[0].Total}</div>
							<ul class="col-md-6 list-group no-padding no-margin">
								<li class="list-group-item">日：${videoPlayList[0].PerYesterday}%</li>
								<li class="list-group-item">周：${videoPlayList[0].PerLastWeek}%</li>
								<li class="list-group-item">月：${videoPlayList[0].PerLastMonth}%</li>
							</ul>
						</div>
						<div class="col-md-3 block-info" style="padding: 0 25px">
							<div class="info-title col-md-6">视频下线：${videoOfflineList[0].Total}</div>
							<ul class="col-md-6 list-group no-padding no-margin">
								<li class="list-group-item">日：${videoOfflineList[0].PerYesterday}%</li>
								<li class="list-group-item">周：${videoOfflineList[0].PerLastWeek}%</li>
								<li class="list-group-item">月：${videoOfflineList[0].PerLastMonth}%</li>
							</ul>
						</div>
						<div class="col-md-3 block-info no-padding"
							style="padding-left: 50px">
							<div class="info-title col-md-6">视频提交：${videoSubmitList[0].Total}</div>
							<ul class="col-md-6 list-group no-padding no-margin">
								<li class="list-group-item">日：${videoSubmitList[0].PerYesterday}%</li>
								<li class="list-group-item">周：${videoSubmitList[0].PerLastWeek}%</li>
								<li class="list-group-item">月：${videoSubmitList[0].PerLastMonth}%</li>
							</ul>
						</div>
						<div class="col-md-3 block-info no-padding"
							style="padding-left: 50px">
							<div class="info-title col-md-6">视频添加：${videoAddList[0].Total}</div>
							<ul class="col-md-6 list-group no-padding no-margin">
								<li class="list-group-item">日：${videoAddList[0].PerYesterday}%</li>
								<li class="list-group-item">周：${videoAddList[0].PerLastWeek}%</li>
								<li class="list-group-item">月：${videoAddList[0].PerLastMonth}%</li>
							</ul>
						</div>
					</div>
					<div class="row-fluid">
						<div class="col-md-3 no-padding" style="padding-right: 50px">
							<div class="alert alert-warning text-center"
								style="margin-top: 8px; margin-bottom: 0">视频总量：${videoCount[0].Total}</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="panel-body no-padding">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#total"
					aria-controls="total" role="tab" data-toggle="tab"
					id="tab-total">视频点击量</a></li>
				<li role="presentation"><a href="#timeInterval"
					aria-controls="timeInterval" role="tab" data-toggle="tab"
					id="tab-intervale">分时段点击量</a></li>
			</ul>
			<form class="form-horizontal tab-filter border-bottom" id="form-statisics">
			<div class="toolbar">
					<button class="btn btn-default" type="button"
						id="btn-query">
						<i class='fa fa-search text-success'></i>查询
					</button>
					<button class="btn btn-default" type="button"
						id="btn-detail-query">
						<i class='fa fa-search-plus text-success'></i>查看详情
					</button>
				</div>
				<br />
				<div class="form-group">
					<span id="ctl-date">
						<label class="col-sm-1 control-label">录入时间:</label>
						<input type="text" class="form-control" name="StartDate" placeholder="开始时间" maxlength="20" id="start-date">

						<label class="control-label">到</label>
						<input type="text" class="form-control" name="EndDate" placeholder="结束时间" maxlength="20" id="end-date">
					</span>
					<span class="hide" id="ctl-time">
						<label class="col-sm-1 control-label">录入时间:</label>
						<input type="text" class="form-control" name="StartTime" placeholder="开始时间" maxlength="20" id="start-time">

						<label class="control-label">到</label>
						<input type="text" class="form-control" name="EndTime" placeholder="结束时间" maxlength="20" id="end-time">
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

			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane fade in active" id="total">
					<div id="grid-total"></div>
				</div>
				<div role="tabpanel" class="tab-pane fade" id="timeInterval">
					<div id="grid-interval"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
