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
<title>供应商管理-司机列表-司机详情</title>
<jsp:include page="../resource.jsp"/>
</head>
<body>
<div class="r-sub-nav-out">
	<p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;司机详情
	<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBack();">返回</a>
	</p>
</div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">司机详情</a></li>
        </ul>
    </div>
    <div class="sys-text sh-add-new map-shebei-li">
        <ul class="marLi clearfix gray2 f12">
            <li><span class="fl w95 t-r">所属商家简称：</span><s:property value="driverInfoEntity.businessName"/></li>
            <li><span class="fl w95 t-r">司机姓名：</span><s:property value="driverInfoEntity.driverName"/></li>
            <li><span class="fl w95 t-r">性别：</span><s:property value="%{driverInfoEntity.sex==0?'男':'女'}"/></li>
            <li><span class="fl w95 t-r">年龄：</span><s:property value="driverInfoEntity.age"/></li>
            <li><span class="fl w95 t-r">驾驶证号：</span><s:property value="driverInfoEntity.driverNo"/></li>
            <li><span class="fl w95 t-r">身份证号：</span><s:property value="driverInfoEntity.idNumber"/></li>
            <li><span class="fl w95 t-r">驾照类型：</span>
            <s:if test="%{driverInfoEntity.driverType==0}">A1</s:if>
            <s:if test="%{driverInfoEntity.driverType==1}">A2</s:if>
            <s:if test="%{driverInfoEntity.driverType==2}">A3</s:if>
            <s:if test="%{driverInfoEntity.driverType==3}">B1</s:if>
            <s:if test="%{driverInfoEntity.driverType==4}">B2</s:if>
            </li>
            <li><span class="fl w95 t-r">出生年月：</span><s:property value="driverInfoEntity.birthday"/></li>
            <li><span class="fl w95 t-r">年检日期：</span><s:property value="driverInfoEntity.inspectionDate"/></li>
            <li><span class="fl w95 t-r">联系电话：</span><s:property value="driverInfoEntity.telephone"/></li> 
            <li><span class="fl w95 t-r">账号状态：</span>
            <s:if test="driverInfoEntity.accountStatus==0">
              <em class="green1">启用</em>
            </s:if>
            <s:else>
              <em class="red1">禁用</em>
            </s:else>
            </li>
            <li><span class="fl w95 t-r">奖励账户：</span><s:property value="driverInfoEntity.totalAmount"/></li> 
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
