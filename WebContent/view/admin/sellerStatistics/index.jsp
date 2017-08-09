<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script src="${basePath}assets/scripts/admin.seller.statistics.js"></script>
</head>
<body>
	<div class="container-fluid page-container">
		<div class="panel panel-default" id="TestPanel">
			<div class='panel-heading'>
				<h4 class='panel-title'>
					昨日关键指标<a data-toggle='collapse' href='#collapse-TestPanel' aria-expanded='true' class='pull-right btn-collapse'>
						<i class='fa fa-chevron-circle-up'></i>
					</a>
				</h4>
			</div>
			<div id="collapse-TestPanel" class="panel-collapse collapse in">
				<div class="panel-body">
					<div class="row-fluid clearfix">
						<div class="col-md-4 block-info" style="padding-right: 50px">
							<div class="info-title col-md-5">商家总量：${total.Total }</div>
							<ul class="col-md-7 list-group no-padding no-margin">
								<li class="list-group-item">日：${total.PerYesterday } %</li>
								<li class="list-group-item">周：${total.PerLastWeek } %</li>
								<li class="list-group-item">月：${total.PerLastMonth } %</li>
							</ul>
						</div>
						<div class="col-md-4 block-info" style="padding: 0 25px">
							<div class="info-title col-md-5">商家增加：${added.Total }</div>
							<ul class="col-md-7 list-group no-padding no-margin">
								<li class="list-group-item">日：${added.PerYesterday } %</li>
								<li class="list-group-item">周：${added.PerLastWeek } %</li>
								<li class="list-group-item">月：${added.PerLastMonth } %</li>
							</ul>
						</div>
						<div class="col-md-4 block-info no-padding" style="padding-left: 50px">
							<div class="info-title col-md-5">视频下线：${offline.Total }</div>
							<ul class="col-md-7 list-group no-padding no-margin">
								<li class="list-group-item">日：${offline.PerYesterday } %</li>
								<li class="list-group-item">周：${offline.PerLastWeek } %</li>
								<li class="list-group-item">月：${offline.PerLastMonth } %</li>
							</ul>
						</div>
					</div>
					<div class="row-fluid">
						<div class="col-md-4 no-padding" style="padding-right: 50px">
							<div class="alert alert-warning text-center" style="margin-top: 8px; margin-bottom: 0">视频浏览总量：${offline.TotalPlay }</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="panel panel-default" id="TestPanel">
			<div class='panel-heading'>
				<h4 class='panel-title'>
					商家统计<a data-toggle='collapse' href='#collapse-total-pannel' aria-expanded='true' class='pull-right btn-collapse'>
						<i class='fa fa-chevron-circle-up'></i>
					</a>
				</h4>
			</div>
			<div class="toolbar">
				<button class="btn btn-default" type="button" id="btn-query">
					<i class='fa fa-search text-success'></i>查询
				</button>
			</div>
			<div id="collapse-total-pannel" class="panel-collapse collapse in">
				<div class="panel-body no-padding">
					<form class="form-horizontal border-bottom" id="form-data">
						<br />
						<div class="form-group">
							<span id="ctl-date"> <label class="col-sm-1 control-label">录入时间</label> <input type="text"
								class="form-control" name="StartDate" placeholder="开始时间" maxlength="20" id="start-date"> <label
								class="control-label">到</label> <input type="text" class="form-control" name="EndDate" placeholder="结束时间"
								maxlength="20" id="end-date">
							</span> <span class="hide" id="ctl-time"> <label class="col-sm-1 control-label">录入时间</label> <input type="text"
								class="form-control" name="StartTime" placeholder="开始时间" maxlength="20" id="start-time"> <label
								class="control-label">到</label> <input type="text" class="form-control" name="EndTime" placeholder="结束时间"
								maxlength="20" id="end-time">
							</span> <label class="control-label" style="margin-left: 50px">省</label> <input type="text" class="form-control"
								name="Province" placeholder="请选择省" maxlength="20" id="txt-province">
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">城市</label>
							<div id="rdi-city"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">区域</label>
							<div id="chk-area"></div>
						</div>
					</form>
					<div id="grid-total"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
