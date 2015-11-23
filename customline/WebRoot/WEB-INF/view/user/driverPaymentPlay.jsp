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
<title>供应商管理-发放奖励-发放</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;发放奖励<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBack();">返回</a></p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">发放</a></li>
        </ul>
    </div>
    <div class="sys-text sh-add-new map-shebei-li">
        <ul class="marLi clearfix gray2 f12">
            <li><span class="fl w95 t-r">申请时间：</span></li>
            <li><span class="fl w95 t-r">申请司机：</span></li>
            <li><span class="fl w95 t-r">登录手机号码：</span></li>
            <li><span class="fl w95 t-r">所属商家：</span></li>
            <li><span class="fl w95 t-r">提现金额：</span></li>
            <li><span class="fl w95 t-r">提现方式：</span></li>
            <li><span class="fl w95 t-r">提现账号：</span></li>
            <li><span class="fl w95 t-r">账号姓名/昵称：</span></li>
            <li><span class="fl w95 t-r">支付状态：</span></li>
            <li><span class="fl w95 t-r">操作人：</span></li> 
            <li><span class="fl w95 t-r">操作时间：</span></li>
        </ul>
    </div>
</div>
</body>
<script>	
$(function(){
	//浏览器可视窗口的的高度
	$(".sys-text").css("min-height",($(window).height()-120+"px"));
	$(window).resize(function() {
		$(".sys-text").css("min-height",($(window).height()-120+"px"));
	}); 
})

//返回上一步
function goBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../driverList.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="driverList.action?level=two";
	}
	
	window.location.href=$the_url;
}
</script>
</html>
