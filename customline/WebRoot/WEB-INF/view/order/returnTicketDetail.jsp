<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单管理-订单列表-订单详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;订单管理&nbsp;>&nbsp;订单详情<a class="display-ib btn ml10" href="javascript:goBack();">返回</a></p></div>
<div class="mt25 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">订单详情</a></li>
        </ul>
    </div>
    <div class="sys-text sh-add-new map-shebei-li">
       <ul class="mt20 clearfix gray2 f12">
            <li><span class="fl w95 t-r">订单号：</span><s:property value="returnTicket.leaseOrderNo"/></li>
            <li><span class="fl w95 t-r">乘客：</span><s:property value="returnTicket.displayId"/>/<s:property value="returnTicket.nickName"/></li>
            <li><span class="fl w95 t-r">联系方式：</span><s:property value="returnTicket.telephone"/></li>
            <li><span class="fl w95 t-r">线路名称：</span><s:property value="returnTicket.lineName"/></li>
            <li><span class="fl w95 t-r">班次：</span><s:property value="returnTicket.orderStartTime"/></li>
            <li><span class="fl w95 t-r">原金额：</span><s:property value="returnTicket.oldMoney"/></li>
            <li><span class="fl w95 t-r">退款金额：</span><s:property value="returnTicket.realBackMoney"/></li>
            <li><span class="fl w95 t-r">手续费：</span><s:property value="returnTicket.realPoundage"/></li>
            <li><span class="fl w95 t-r">剩余金额：</span><s:property value="returnTicket.realPayMoney"/></li>
            <li><span class="fl w95 t-r">百分比：</span><s:property value="returnTicket.percent"/></li>
            <li><span class="fl w95 t-r">退票天数：</span><s:property value="returnTicket.returnDateNum"/></li>
            <li style="width:100%"><span class="fl w95 t-r">退票的日期：</span><s:property value="returnTicket.returnDates"/></li>
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

//返回上一步
function goBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../returnTicketMainPage.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="returnTicketMainPage.action?level=two";
	}
	
	window.location.href=$the_url;
}
</script>
</html>
