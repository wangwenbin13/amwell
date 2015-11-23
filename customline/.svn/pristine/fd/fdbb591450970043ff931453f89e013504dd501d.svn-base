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
<title>包车管理-包车退票列表-包车退票</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;包车管理&nbsp;>&nbsp;退票详情</p></div>
<div class="mt25 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">退票详情</a></li>
        </ul>
    </div>
    <p class="mt20 fw"><span>订单号：<em class="blue1 fw f16">${bcReturnHistoryVo.orderNo }</em></span><span class="ml40 fw">包车商家：${bcReturnHistoryVo.brefName}</span></p>
    <div class="sh-add-new bsLineDetailLi mt20">
       <ul class="clearfix gray2">
            <li class="fl"><span class="fl w100 t-r">用户昵称/ID：</span>${bcReturnHistoryVo.nickName }/${bcReturnHistoryVo.displayId }</li>
         <li class="fl"><span class="fl w100 t-r">联系电话：</span>${bcReturnHistoryVo.telephone }</li>
         <li class="fl"><span class="fl w100 t-r">人数：</span>${bcReturnHistoryVo.totalPassengers }</li>
         <li class="fl"><span class="fl w100 t-r">用车联系人：</span>${bcReturnHistoryVo.contacts }</li>
         <li class="fl"><span class="fl w100 t-r">联系电话：</span>${bcReturnHistoryVo.contactPhone }</li>
         <li class="fl"><span class="fl w100 t-r">车辆数：</span>${bcReturnHistoryVo.totalCars }</li>
         <li class="fl"><span class="fl w100 t-r">出发时间：</span>${bcReturnHistoryVo.startTime }</li>
         <li class="fl"><span class="fl w100 t-r">返程时间：</span>${bcReturnHistoryVo.returnTime }</li>  
         <li class="fl"><span class="fl w100 t-r">车辆年限：</span>
         	<s:if test="%{bcReturnHistoryVo.carAge==0}">不限</s:if>
         	<s:if test="%{bcReturnHistoryVo.carAge==1}">1年以内</s:if>
         	<s:if test="%{bcReturnHistoryVo.carAge==2}">3年以内</s:if>
         	<s:if test="%{bcReturnHistoryVo.carAge==3}">5年以内</s:if>
         </li>         
         <li class="fl"><span class="fl w100 t-r">上车点：</span>${bcReturnHistoryVo.beginAddress }</li>
         <li class="fl"><span class="fl w100 t-r">下车点：</span>${bcReturnHistoryVo.endAddress }</li>                     
         <li class="fl"><span class="fl w100 t-r">包车方式：</span>
         	<s:if test="%{bcReturnHistoryVo.charteredMode==1}">单趟</s:if>
         	<s:if test="%{bcReturnHistoryVo.charteredMode==2}">往返</s:if>
         	<s:if test="%{bcReturnHistoryVo.charteredMode==3}">包天</s:if>
         </li>
         <li class="fl"><span class="fl w100 t-r">包车类型：</span>
         	<s:if test="%{bcReturnHistoryVo.businessType==1}">旅游包车</s:if>
         	<s:if test="%{bcReturnHistoryVo.businessType==2}">商务接送</s:if>
         	<s:if test="%{bcReturnHistoryVo.businessType==3}">过港接送</s:if>
         </li>
         <li class="fl"><span class="fl w100 t-r">开具发票：</span>
         	<s:if test="%{bcReturnHistoryVo.needInvoice==0}">不需要发票</s:if>
         	<s:if test="%{bcReturnHistoryVo.needInvoice==1}">需要发票</s:if>
         </li>         
        </ul>      
    </div>
    <p class="mt20 fw">退票金额：  <em class="red1 fw f16">￥ ${bcReturnHistoryVo.bcReturnMoney }</em> 元</p>
    <p class="mt20 fw">退票手续费：扣 <em class="blue1 fw f16">${bcReturnHistoryVo.realPer }%</em> 手续费</p>
    <p class="mt20 fw">退票时间：${bcReturnHistoryVo.operateTime }</p>
    <p class="mt20 fw">操作人：${bcReturnHistoryVo.userName }</p>
   <p class="t-c mt10 mb20">
        <a href="javascript:void(0);" class="display-ib btn1 white mr40 f14" onclick="toBack()">关闭</a>
    </p>
</div>
<script type="text/javascript">
//返回上一步
function toBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../forwardBcReturnPage.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="forwardBcReturnPage.action?level=two";
	}
	window.location.href=$the_url;
}
</script>
</body>
</html>
