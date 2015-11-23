<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营平台-系统设置-个人信息</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;个人信息</p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">个人信息</a></li>
        </ul>
    </div>
    <div class="sys-text sh-add-new map-shebei-li">
        <ul class="mt10 clearfix gray2 f12">
            <li><span class="fl w95 t-r">用户名：</span><s:property value="#session.userInfo.userName" /></li>
            <li><span class="fl w95 t-r">登录名：</span><s:property value="#session.userInfo.loginName" /></li>
            <li><span class="fl w95 t-r">联系电话：</span><s:property value="#session.userInfo.telephone" /></li>
            <li><span class="fl w95 t-r">角色：</span><s:property value="#session.userInfo.roleName" /></li>
            <li><span class="fl w95 t-r">状态：</span><s:property value="#session.userInfo.status==1?'有效':'无效'" /></li>
            <li style="height:250px;width:80%"><span class="fl w95 t-r">备注：</span><span class="fl w350"><s:property value="#session.userInfo.remark" /></span></li> 
        </ul>
    </div>
</div>
</body>
<script>	
$(function(){
	//浏览器可视窗口的的高度
	$(".sys-text").css("height",($(window).height()-120+"px"));
	$(window).resize(function() {
		$(".sys-text").css("height",($(window).height()-120+"px"));
	}); 
})
</script>
</html>
