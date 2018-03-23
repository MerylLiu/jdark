<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/assets/inc.jsp"%>
<script type="text/javascript" src="${basePath}assets/scripts/sys.log.js"></script>
<style type="text/css">
	.modal-body {
		word-wrap:break-word;
	}
</style>
<script type="text/javascript">
	var operationType = [{
	        "text": "错误日志",
	        "value": 0
	    }, {
	        "text": "插入数据",
	        "value": 1
	    }, {
	        "text": "修改数据",
	        "value": 2
	    },{
	    	"text": "删除数据",
	        "value": 3
	    },{
	    	"text": "其他",
	        "value": 8
	    }];
</script>
</head>
<body>
<div class="container-fluid page-container">
		<div class="panel panel-default" data-fit="true" id="editPanel">
			<div class='toolbar'>
				<button id='txt-del' class='btn btn-default'  type='Button' data-permision='Delete'>删除</button>
				<button id='txt-del-all' class='btn btn-default' type='Button' data-permision='Delete'>清空</button>
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
</body>
</html>