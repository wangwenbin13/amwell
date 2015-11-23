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
    
    <title>My JSP 'ok.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <s:url id="preurl" value="/center/list.action">
	<s:param name="p">
		<s:property value="page.previousIndex" />
	</s:param>
</s:url>


<s:url id="nexturl" value="/center/list.action">
	<s:param name="p">
		<s:property value="page.nextIndex" />
	</s:param>
</s:url>
<s:url id="firsturl" value="/center/list.action">
	<s:param name="p">
		<s:property value="0" />
	</s:param>
</s:url>
<s:url id="lasturl" value="/center/list.action">
	<s:param name="p">
		<s:property value="page.lastIndex" />
	</s:param>
</s:url>
<s:url id="tourl" value="/center/list.action">
	<s:param name="p">
		<s:property value="page.currentPage" />
	</s:param>
</s:url>
  
  <body>
  		<table border="1" align="center" width="800">
   				<tr>
   					<td align="center" colspan="5">用户列表</td>
   				</tr>
   				
   				<tr>
					<form action="center/list.action" method="post">
						<td align="center" colspan="5">
						用户名：<s:textfield theme="simple" name="search.field01"/>
						密码：<s:textfield theme="simple" name="search.field02"/>
						真实姓名：<s:textfield theme="simple" name="search.field03"/>
						<s:submit theme="simple" value="查询"/>
						</td>
					</form>
   				</tr>
   				
   				<tr>
   					<td align="center">用户名</td>
   					<td align="center">密码</td>
   					<td align="center">真实姓名</td>
   					<td align="center">注册时间</td>
   					<td align="center">操作</td>
   				</tr>
   				
   				<s:iterator value="list">
   					<tr>
   						<td align="center"><s:property value="username"/></td>
   						<td align="center"><s:property value="pwd"/></td>
   						<td align="center"><s:property value="realname"/></td>
   						<td align="center"><s:property value="cdate"/></td>
   						<td align="center"><a href="user/add_forward.action?id=<s:property value='rec_id'/>">详情</a>||<a href="user/del.action?id=<s:property value='rec_id'/>">删除</a></td>
   					</tr>
   				</s:iterator>
   				
   				<tr>
   					<td colspan="5" align="center">
						<s:a href="%{firsturl}">首页</s:a>
						<s:a href="%{preurl}">上一页</s:a>
						<s:iterator value="page.pageList">
							<s:if test="field02==1">
								<b><span class="current"><s:property value="field01" />
								</span>
								</b>
							</s:if>
							<s:else>
								<a href="center/list.action?p=<s:property value="field03"/>"><s:property value="field01" /></a>
							</s:else>
						</s:iterator>
						<s:a href="%{nexturl}">下一页</s:a>
						<s:a href="%{lasturl}">末页</s:a>&nbsp;[&nbsp;
						<font color="red"><s:property value="page.currentPage" />/<s:property value="page.pageCount"/></font>&nbsp;共&nbsp;
						<font color="red"><s:property value="page.totalCount" />
						</font>&nbsp;条记录]
   					</td>
   				</tr>
   				
   			</table>
  </body>
</html>
