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
<title>供应商管理-提现申请-详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;用户管理&nbsp;>&nbsp;提现详情
<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBack();">返回</a>
<%--<a class="display-ib btn ml10" href="javascript:history.go(-1)">返回</a>--%>
</p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">提现详情</a></li>
        </ul>
    </div>
    <div class="sys-text sh-add-new map-shebei-li">
        <ul class="marLi clearfix gray2 f12">
            <li><span class="fl w95 t-r">申请时间：</span><s:property value="driverWithdrawAskforVo.askforTime"/></li>
            <li><span class="fl w95 t-r">申请司机：</span><s:property value="driverWithdrawAskforVo.driverName"/></li>
            <li><span class="fl w95 t-r">登录手机号码：</span><s:property value="driverWithdrawAskforVo.telephone"/></li>
            <li><span class="fl w95 t-r">所属商家：</span><s:property value="driverWithdrawAskforVo.businessName"/></li>
            <li><span class="fl w95 t-r">提现金额：</span><s:property value="driverWithdrawAskforVo.amount"/></li>
            <li><span class="fl w95 t-r">提现方式：</span>
            <s:if test="%{driverWithdrawAskforVo.withdrawType==1}">支付宝</s:if>
            <s:if test="%{driverWithdrawAskforVo.withdrawType==2}">微信</s:if>
            <s:if test="%{driverWithdrawAskforVo.withdrawType==3}">银联</s:if>
            </li>
            <li><span class="fl w95 t-r">提现账号：</span><s:property value="driverWithdrawAskforVo.withdrawAccount"/></li>
            <li><span class="fl w95 t-r">账号姓名/昵称：</span><s:property value="driverWithdrawAskforVo.driverName"/></li>
            <li><span class="fl w95 t-r">支付状态：</span><s:property value="%{driverWithdrawAskforVo.handleType==0?'未支付':'已支付'}"/></li>
            <li><span class="fl w95 t-r">操作人：</span><s:property value="driverWithdrawAskforVo.handleBy"/></li> 
            <li><span class="fl w95 t-r">操作时间：</span><s:property value="driverWithdrawAskforVo.handleOn"/></li>
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
		$the_url="../driverManager.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="driverManager.action?level=two";
	}
	
	window.location.href=$the_url;
}
</script>
</html>
