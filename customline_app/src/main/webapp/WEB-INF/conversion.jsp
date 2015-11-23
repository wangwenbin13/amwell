<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<script>
function loading()
{
	//进网页时弹框,更多资料搜索w3c
	var value = window.prompt("请输入执行密码","默认内容");
	if(null!=value && ''!=value){
		document.getElementById("pwd").value = value;
		document.getElementById("conversionAction").submit();
	}
}
</script>
</head>
<body onload="loading()">
<form action="../app_conversionLineStation/conversion.action" id="conversionAction" method="post" style="display: none;">
	<input name="pwd" id="pwd"/>
</form>
</body>
</html>
