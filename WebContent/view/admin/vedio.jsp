<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/assets/inc.jsp"%>
<script type="text/javascript">
	$(function(){
		$('#btn-call').click(function(){
			var data = {
					cid:$("#cid").val(),
					xmlurl:$("#xml-url").val()
			};
			$.post('<%=basePath %>admin/vedio/pubVedio',JSON.stringify(data),function(res){
				console.log(res)
				$('.res-info').append("调用结果编码："+res.result +"；  描述："+res.errorDescription+"<br/>");
			});
		})
	})
</script>
</head>
<body>
	<br/>
	<br/>
	<div>
		<label>CorrelateID</label><input type="text" id="cid">
		<label>XML:</label><input type="text" id="xml-url">
		<button id="btn-call">调用</button>
	</div>
	<div class="res-info"></div>
</body>
</html>