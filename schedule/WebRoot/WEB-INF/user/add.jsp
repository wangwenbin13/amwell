<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <form theme="simple" action="user/add.action" method="post">
    		<s:hidden theme="simple" name="user.rec_id"></s:hidden>
   			<table border="1" align="center" width="600">
   				<tr>
   					<td align="center" colspan="2">用户信息</td>
   				</tr>
   				<tr>
   					<td>用户名：</td>
   					<td><s:textfield name="user.username" theme="simple"/></td>
   				</tr>
   				
   				<tr>
   					<td>密码：</td>
   					<td><s:textfield name="user.pwd" theme="simple"/></td>
   				</tr>
   				
   				<tr>
   					<td>真实姓名：</td>
   					<td><s:textfield name="user.realname" theme="simple"/></td>
   				</tr>
   				<tr>
   					<td colspan="2" align="center"><input type="submit" value="保存"/></td>
   				</tr>
   			</table>
   		</form>
  </body>
</html>
