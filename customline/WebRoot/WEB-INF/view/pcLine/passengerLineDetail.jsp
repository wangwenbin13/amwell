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
<title>拼车管理-乘客详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;拼车管理&nbsp;>&nbsp;乘客拼车线路详情<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBack();">返回</a></p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">乘客拼车线路详情</a></li>
        </ul>
    </div>
    <div class="sys-text sh-add-new">
        <ul class="mt10 clearfix gray2 f12">
            <li><span class="fl w95 t-r">提交时间：</span>${passengerLineVo.createOn}</li>
     <%--        <li><span class="fl w95 t-r">地区：</span>${passengerLineVo.provinceName}/${passengerLineVo.cityName}</li> --%>
            <li><span class="fl w95 t-r">用户名：</span>${passengerLineVo.displayId}/${passengerLineVo.nickName}</li>
            <li><span class="fl w95 t-r">线路类型：</span>${passengerLineVo.lineType}</li>
            <li><span class="fl w95 t-r">单程/往返：</span>${passengerLineVo.lineSubType}</li>
            <s:iterator value="passengerLineVo.stationList" status="s">
               <s:if test="%{#s.first}">
                 <li><span class="fl w95 t-r">起点：</span><s:property value="stationName"/></li>
               </s:if>
               <s:else>
                  <s:if test="%{#s.last}"><li><span class="fl w95 t-r">终点：</span><s:property value="stationName"/></li></s:if>
                  <s:else><li><span class="fl w95 t-r">途经点:</span><s:property value="stationName"/></li></s:else>
               </s:else>
            </s:iterator>
            <li><span class="fl w95 t-r">去程时间：</span>${passengerLineVo.goTime}</li>
            <li><span class="fl w95 t-r">返程时间：</span>${passengerLineVo.returnTime}</li>
            <s:if test="%{passengerLineVo.lineType=='上下班'}">
             <li><span class="fl w95 t-r">重复时间：</span>${passengerLineVo.repeatTime}</li>
            </s:if>
            <li><span class="fl w95 t-r">联系电话：</span>${passengerLineVo.telephone}</li>
            <li><span class="fl w95 t-r">座位数：</span>${passengerLineVo.carSeats}</li>
            <li style="height:90px;width:80%"><span class="fl w95 t-r">备注：</span><span class="fl break w80p">${passengerLineVo.remark}</span></li> 
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
		$the_url="../getLineList.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="getLineList.action?level=two";
	}
	window.location.href=$the_url;
}
</script>
</html>
