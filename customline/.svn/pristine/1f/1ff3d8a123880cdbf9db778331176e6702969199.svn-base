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
<title>包车管理-包车订单-订单详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;包车管理&nbsp;>&nbsp;包车订单</p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">用户包车详情</a></li>
        </ul>
    </div>
    <p class="mt20 fw">基本信息： </p>
    <p class="mt10">下单时间：${bcOrderVo.payTime}<span class="ml20">订单号：${bcOrderVo.orderNo}</span></p>
    <div class="sh-add-new bsLineDetailLi">
       <ul class="clearfix gray2">
         <li class="fl"><span class="fl w100 t-r">用户昵称/ID：</span>${bcOrderVo.nickName }/${bcOrderVo.displayId }</li>
         <li class="fl"><span class="fl w100 t-r">联系电话：</span>${bcOrderVo.telephone }</li>
         <li class="fl"><span class="fl w100 t-r">人数：</span>${bcOrderVo.totalPassengers }</li>
         <li class="fl"><span class="fl w100 t-r">用车联系人：</span>${bcOrderVo.contacts }</li>
         <li class="fl"><span class="fl w100 t-r">联系电话：</span>${bcOrderVo.contactPhone }</li>
         <li class="fl"><span class="fl w100 t-r">车辆数：</span>${bcOrderVo.totalCars }</li>
         <li class="fl"><span class="fl w100 t-r">出发时间：</span>${bcOrderVo.startTime }</li>
         <li class="fl"><span class="fl w100 t-r">返程时间：</span>${bcOrderVo.returnTime }</li>  
         <li class="fl"><span class="fl w100 t-r">车辆年限：</span>
         	<s:if test="%{bcOrderVo.carAge==0}">不限</s:if>
         	<s:if test="%{bcOrderVo.carAge==1}">1年以内</s:if>
         	<s:if test="%{bcOrderVo.carAge==2}">3年以内</s:if>
         	<s:if test="%{bcOrderVo.carAge==3}">5年以内</s:if>
         </li>         
         <li class="fl"><span class="fl w100 t-r">上车点：</span>${bcOrderVo.beginAddress }</li>
         <li class="fl"><span class="fl w100 t-r">下车点：</span>${bcOrderVo.endAddress }</li>                     
         <li class="fl"><span class="fl w100 t-r">包车方式：</span>
         	<s:if test="%{bcOrderVo.charteredMode==1}">单趟</s:if>
         	<s:if test="%{bcOrderVo.charteredMode==2}">往返</s:if>
         	<s:if test="%{bcOrderVo.charteredMode==3}">包天</s:if>
         </li>
         <li class="fl"><span class="fl w100 t-r">包车类型：</span>
         	<s:if test="%{bcOrderVo.businessType==1}">旅游包车</s:if>
         	<s:if test="%{bcOrderVo.businessType==2}">商务接送</s:if>
         	<s:if test="%{bcOrderVo.businessType==3}">过港接送</s:if>
         </li>
         <!--                     
         <li class="fl"><span class="fl w100 t-r">提交时间：</span>2014-14-14 14:14</li>
          -->  
         <li class="fl"><span class="fl w100 t-r">开具发票：</span>
         	<s:if test="%{bcOrderVo.needInvoice==0}">不需要发票</s:if>
         	<s:if test="%{bcOrderVo.needInvoice==1}">需要发票</s:if>
         </li>          
         <s:if test="%{bcOrderVo.needInvoice==1}">
            <li class="fl" style="width:52%;height:auto"><span class="fl w100 t-r">发票抬头：</span>${bcOrderVo.invoiceHeader}</li>
         </s:if>
         
     </ul>      
    </div>
    <p class="mt20 fw">行程描述： </p>
    <s:if test="%{bcOrderVo.journeyRemark==null || bcOrderVo.journeyRemark==''}">
    	 <p class="mt10 ml20">无</p> 
    </s:if>
    <s:else>
    	 <p class="mt10 ml20">${bcOrderVo.journeyRemark }</p> 
    </s:else>
    <%--
    <p class="mt20 fw">备注信息： </p>
    <s:if test="%{bcOrderVo.remark==null || bcOrderVo.remark==''}">
    	 <p class="mt10 ml20">无</p> 
    </s:if>
    <s:else>
    	 <p class="mt10 ml20">${bcOrderVo.remark }</p> 
    </s:else>
     
    --%><p class="fw mt20">支付信息：</p>
   	<p class="mt10 ml20">商家：${bcOrderVo.brefName}<span class="ml20">金额：<em class="red1 f14 f-arial mr4">${bcOrderVo.totalPrice }</em>元</span><span class="ml20">支付方式：
   		<s:if test="%{bcOrderVo.payModel==1}">支付宝</s:if>
   		<s:if test="%{bcOrderVo.payModel==2}">银联</s:if>
   		<s:if test="%{bcOrderVo.payModel==3}">微信</s:if>
   		<s:if test="%{bcOrderVo.payModel==4}">(APP)微信</s:if>
   	</span></p>
   	<p class="fw mt20">调度信息：</p>
   	<s:if test="true">
   		<p class="mt10 ml20">暂无信息</p>
   	</s:if>
   	<s:else>
   		<p class="mt10 ml20">司机：echo<span class="ml20">联系电话：<em class="f14 f-arial">135869885986</em></span><span class="ml20">车辆：粤B52654</span><span class="ml20">座位数：45座</span></p>
   		<p class="mt10 ml20">司机：echo<span class="ml20">联系电话：<em class="f14 f-arial">135869885986</em></span><span class="ml20">车辆：粤B52654</span><span class="ml20">座位数：45座</span></p>
   	</s:else>
   	
</div>
  <p class="t-c mt20"><input type="button" value="返回" onclick="history.back();"  class="display-ib btn1 white"/></p>
</body>

</html>
