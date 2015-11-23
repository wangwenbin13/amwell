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
<style type="text/css">
.orderDetail{width:80%;	margin-bottom:10px;	height:60%}
</style>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;订单管理&nbsp;>&nbsp;订单详情<a class="display-ib btn ml10" href="javascript:history.go(-1);">返回</a></p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">订单详情</a></li>
        </ul>
    </div>
    <div class=" sh-add-new orderDetail" style="height: auto;">
       <ul class="mt20 clearfix gray2 f12">
            <li><span class="fl w65 t-r">下单时间：</span><s:property value="leaseOrderModelVo.leaseOrderTime"/></li>
            <li><span class="fl w65 t-r">订单号：</span><s:property value="leaseOrderModelVo.leaseOrderNo"/></li>
            <li><span class="fl w65 t-r">乘客：</span><s:property value="leaseOrderModelVo.displayId"/>/<s:property value="leaseOrderModelVo.nickName"/></li>
            <li><span class="fl w65 t-r">联系方式：</span><s:property value="leaseOrderModelVo.telephone"/></li>
            <li><span class="fl w65 t-r">线路名称：</span><s:property value="leaseOrderModelVo.lineName"/></li>
            <li><span class="fl w65 t-r">上车点：</span><s:property value="leaseOrderModelVo.ustation"/></li>
            <li><span class="fl w65 t-r">下车点：</span><s:property value="leaseOrderModelVo.dstation"/></li>
            <li><span class="fl w65 t-r">金额：</span><s:property value="leaseOrderModelVo.payMoney"/>元</li>
            <li style="width:inherit;"><span class="fl w65 t-r">乘坐日期：</span>
			</li>
			<s:iterator value="leaseOrderModelVo.lineClassEntities" var="lineClass">
				<li style="margin-left: 40px; margin-top: 5px;">${lineClass.orderDate }/${lineClass.orderStartTime }</li>
			</s:iterator> 
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
		$the_url="../getAllOrderList.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="getAllOrderList.action?level=two";
	}
	
	window.location.href=$the_url;
}
</script>
</html>
