<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script src="${basePath}assets/scripts/admin.page.statistics.js"></script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="panel panel-default" id="test-panel">
			<div class='panel-heading'>
				<h4 class='panel-title'>
					页面统计<a data-toggle='collapse' href='#collapse-test-panel'
						aria-expanded='true' class='pull-right btn-collapse'> <i
						class='fa fa-chevron-circle-up'></i>
					</a>
				</h4>
			</div>
			<div id="collapse-test-panel" class="panel-collapse collapse in">
				<div class="panel-body">
					<div class="row-fluid clearfix">
						<div class="col-md-3 block-info" style="padding-right: 50px">
							<div class="info-title col-md-6">页面总点击量：${totalPageStatistics[0].Total}</div>
							<ul class="col-md-6 list-group no-padding no-margin">
								<li class="list-group-item">日：${totalPageStatistics[0].PerYesterday}%</li>
								<li class="list-group-item">周：${totalPageStatistics[0].PerLastWeek}%</li>
								<li class="list-group-item">月：${totalPageStatistics[0].PerLastMonth}%</li>
							</ul>
						</div>
						<div class="col-md-3 block-info" style="padding: 0 25px">
							<div class="info-title col-md-6">首页点击量：${mainPageStatistics[0].Total}</div>
							<ul class="col-md-6 list-group no-padding no-margin">
								<li class="list-group-item">日：${mainPageStatistics[0].PerYesterday}%</li>
								<li class="list-group-item">周：${mainPageStatistics[0].PerLastWeek}%</li>
								<li class="list-group-item">月：${mainPageStatistics[0].PerLastMonth}%</li>
							</ul>
						</div>
						<div class="col-md-3 block-info no-padding"
							style="padding-left: 50px">
							<div class="info-title col-md-6">视频详情页点击量：${detailPageStatistics[0].Total}</div>
							<ul class="col-md-6 list-group no-padding no-margin">
								<li class="list-group-item">日：${detailPageStatistics[0].PerYesterday}%</li>
								<li class="list-group-item">周：${detailPageStatistics[0].PerLastWeek}%</li>
								<li class="list-group-item">月：${detailPageStatistics[0].PerLastMonth}%</li>
							</ul>
						</div>
						<div class="col-md-3 block-info no-padding"
							style="padding-left: 50px">
							<div class="info-title col-md-6">二维码扫描量：${qrcodeStatistics[0].Total}</div>
							<ul class="col-md-6 list-group no-padding no-margin">
								<li class="list-group-item">日：${qrcodeStatistics[0].PerYesterday}%</li>
								<li class="list-group-item">周：${qrcodeStatistics[0].PerLastWeek}%</li>
								<li class="list-group-item">月：${qrcodeStatistics[0].PerLastMonth}%</li>
							</ul>
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
					id="tab-total">页面点击量</a></li>
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
						<i class='fa fa-file-excel-o text-success'></i>导出Excel表格
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
