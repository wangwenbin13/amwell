<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<meta name="Description" content="" />
<title>包车管理-已报价列表-已报价详情</title>
<jsp:include page="../resource.jsp"/>
<script src="../js/common/Util.js" type="text/javascript"></script>
</head>

<body>
    <input id="lineBaseId" type="hidden" value="${lineDetailVo.lineBaseId}" />
	<p class="subNav">当前位置：首页&nbsp;&gt;&nbsp;包车管理&nbsp;&gt;&nbsp;已报价详情<a class="red1 ml30" href="javascript:void(0)" onclick="javascript:history.go(-1);">返回</a></p>
	<div class="mt20 ml30 black1 mr28">
		<p class="fw f14 f-yahei">已报价详情</p>
		<p class="mt20 libg clearfix"><span class="fl ml20 lh28">乘客需求</span></p>	
		<div class="fl lineDetail-left mt20 ml15 mr30" style="height:80px;">
			<p class="clearfix">
				 <s:if test="%{alreadyQuoteVo.businessType==1}"><span class="display-ib lineKind lineKind-work fl">旅游包车</span></s:if>
			     <s:if test="%{alreadyQuoteVo.businessType==2}"><span class="display-ib lineKind lineKind-free">商务接送</span></s:if>
			     <s:if test="%{alreadyQuoteVo.businessType==3}"><span class="display-ib lineKind lineKind-hongkong">过港接送</span></s:if>
				 <!-- <span class="fr ml20">预算<em class="f16 fw yellow4 mr4">810</em>元</span> -->
			</p>
			<!--  <p class="mt10 clearfix"><span class="fl">人数：49人</span><span class="fr ml20">车辆数：2辆</span></p> -->
			<p class="mt10 clearfix">人数：${alreadyQuoteVo.totalPassengers }人</p>
			<p class="mt10 clearfix">车辆数：${alreadyQuoteVo.totalCars }辆</p>
		</div>
		<div class="mt20 clearfix">
			<ul>
				<li class="mt5 fl w18p h20">发布时间：${alreadyQuoteVo.createOn }</li>
				<li class="mt5 fl w18p h20">车龄：<s:if test="%{alreadyQuoteVo.carAge==0}">不限</s:if><s:if test="%{alreadyQuoteVo.carAge==1}">1年以内</s:if><s:if test="%{alreadyQuoteVo.carAge==2}">3年以内</s:if><s:if test="%{alreadyQuoteVo.carAge==3}">5年以内</s:if></li>
				<li class="mt5 fl w43p h20">包车方式：
					<s:if test='%{alreadyQuoteVo.charteredMode==1}'>单程 </s:if>
				    <s:if test='%{alreadyQuoteVo.charteredMode==2}'>往返 </s:if>
				    <s:if test='%{alreadyQuoteVo.charteredMode==3}'>包天</s:if>
				&nbsp;&nbsp;&nbsp;&nbsp;<s:if test='%{alreadyQuoteVo.needInvoice==0}'>不需发票</s:if><s:if test='%{alreadyQuoteVo.needInvoice==1}'>需发票(${alreadyQuoteVo.invoiceHeader})</s:if></li>
				<li class="mt5 fl w18p h20">出车时间：${alreadyQuoteVo.startTime}</li>
				<li class="mt5 fl w18p h20">起点：<span class="display-ib lineDoit lineDoit-start"></span><em class="green1 display-ib stationWn" title="${alreadyQuoteVo.beginAddress}" id="beginAddressSpan"><script type="text/javascript">$("#beginAddressSpan").text(Util.cutstr('${alreadyQuoteVo.beginAddress}',30))</script></em></li>
				<li class="mt5 fl w43p h20">
					<span class="fl w260">行程备注：<span id="tripDetailSpan"><script type="text/javascript">$("#tripDetailSpan").text(Util.cutstr('${alreadyQuoteVo.journeyRemark}',30))</script></span></span>
					<div class="p-r fl w55 h30 t-c" id="showTripDetail">
						<a class="display-ib btn1-red" href="javascript:void(0);">看详情</a>
						<div class="p-a w500 detail-div hide" id="tripDetailDiv">
							<div class="p-a detail-triangle"></div>
							<P class="fw f14 t-l">行程备注：</P>
							<p class="mt15 line20 t-l">${alreadyQuoteVo.journeyRemark}</p>
						</div>
					</div>
				</li>
				<li class="mt5 fl w18p h20">返程时间：${alreadyQuoteVo.returnTime}</li>
				<li class="mt5 fl w18p h20">终点：<span class="display-ib lineDoit lineDoit-end"></span><em class="red4 display-ib stationWn" title="${alreadyQuoteVo.endAddress}" id="endAddressSpan"><script type="text/javascript">$("#endAddressSpan").text(Util.cutstr('${alreadyQuoteVo.endAddress}',30))</script></em></li>
				<!--  <li class="mt5 fl w43p h20">需求备注：希望提供2年内新车，车内环境舒适。</li> -->
			</ul>
		</div>
		<p class="mt20 libg"><span class="fl ml20 lh28">报价</span></p>	
		<p class="fw f14 f-yahei mt20"><span class="t-c titleTop">车辆信息</span></p>
		<ul class="widthfull" id="dirverCarUl">
			<s:iterator value="%{alreadyQuoteVo.carInfo}" var="carInfo">
	            <li class="border-bottom ptb10 ptb25 clearfix">
	            	<span class="w75 t-r fl">车辆类型：</span>
	            	<input type="text" class="r-input w100 fl" carSeat="carStyle" value="${carInfo.carType }"/>
		            <span class="w75 t-r fl ml20">车辆座位数：</span>
		            <input type="text" class="r-input w70 fl" carSeat="carSeat" value="${carInfo.carSeats }"/>
	            	<span class="w75 t-r fl ml20">车龄：</span>
		            <input type="text" class="r-input w70 fl mr8" carNum="carNum" value="${carInfo.carAge }"/>
		            <span class="w75 t-r fl">车辆数：</span>
		            <input type="text" class="r-input w70 fl mr8" carNum="carCount" value="${carInfo.totalCars }"/>
	            </li>
            </s:iterator>
        </ul>
		<p class="fw f14 f-yahei mt20 ptb10"><span class="t-c titleTop">报价总额</span></p>
		<ul class="mt20">
			<li class="clearfix mt20 red1">司机餐费，司机住宿费，油费未勾选项将不计入报价总额，停车费，过路过桥费默认不计入。</li>
            <li class="clearfix mt20">基本费用：<input type="text" class="r-input w130 mr8" id="baseFee" value="${alreadyQuoteVo.basicCost }"/>元</li>
            <li class="clearfix mt10">
	             <input type="checkbox" class="checkbox mr4" nocheck="true" <s:if test="%{alreadyQuoteVo.driverMealCost>0}">checked="checked"</s:if>/>司机餐费：
	             <s:if test="%{alreadyQuoteVo.driverMealCost>0}">
	             	<input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" id="driverFoodFee"  value="${alreadyQuoteVo.driverMealCost }"/>元
	             </s:if>
	             <s:if test="%{alreadyQuoteVo.driverMealCost==0}">
	             	<input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" id="driverFoodFee"  value=""/>元
	             </s:if>
	             	<input type="checkbox" class="checkbox mr4 ml40" nocheck="true" <s:if test="%{alreadyQuoteVo.driverHotelCost>0}">checked="checked"</s:if> />司机住宿费：
	             <s:if test="%{alreadyQuoteVo.driverHotelCost>0}">                                             
	             	<input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" id="driverStayFee"  value="${alreadyQuoteVo.driverHotelCost }"/>元
	             </s:if>
	              <s:if test="%{alreadyQuoteVo.driverHotelCost==0}">
	             	<input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" id="driverStayFee"  value=""/>元
	             </s:if>
	             <!-- 油费选中则不显示oilFeeLi，未选中则显示  -->
	             <input type="checkbox" class="checkbox mr4 ml40" nocheck="true" <s:if test="%{alreadyQuoteVo.oilCost>0}">checked="checked"</s:if> id="oilFee"/>油费：
	             <s:if test="%{alreadyQuoteVo.oilCost>0}">
	            	 <input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" id="driverOilFee"  value="${alreadyQuoteVo.oilCost}"/>元
	             </s:if>
	             <s:else>
	             	<input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" id="driverOilFee"  value=""/>元
	             </s:else>
            </li>
            <li class="clearfix mt20">报价总额：<em class="f20 fw red4 mr4">￥${alreadyQuoteVo.totalCost }</em>元</li>
        </ul>
       <p class="fw f14 f-yahei mt20 border-top ptb10"><span class="t-c titleTop">乘客须知</span></p>
       <ul class="mt20 clearfix" >
       
            <li class="mt10"><span class="square mr6"></span>不计入报价的项，若实际运行时，发生此类费用，将在行程结束时现场结算。</li>
            <!-- 包车方式为包天才显示 -->
           <!--  <li class="mt10 <s:if test='%{alreadyQuoteVo.charteredMode!=3}'>hide</s:if>"><span class="square mr6"></span>根据相关法规车辆正常使用时间为<em class="fw f14">6:00-2:00</em>（次日），司机工作时间为 <input type="text" class="r-input w60 mr4 ml4" value="8"/>小时，超出<em class="fw">1</em>小时之外的时间按 <input type="text" class="r-input w60 mr4 ml4" value="100" />元/小时补足司机工资。</li> -->
            <!-- 包车方式为包天才显示 -->
            <!-- <li class="mt10 <s:if test='%{alreadyQuoteVo.charteredMode !=3}'>hide</s:if>"><span class="square mr6"></span>本次运行按<input type="text" class="r-input w60 mr4 ml4" value="200" />公里/天算，超出公里数按<input type="text" class="r-input w60 mr4 ml4" value="4" />元/公里另计。</li> -->
            <li class="mt10" id="oilFeeLi"><span class="square mr6"></span>本次报价不包含油费，客户用车完毕后与司机将油箱的油料补满。（以油表为准）。</li>
            <!--<li class="mt10"><span class="square mr6"></span>报价之外的费用需在行程结束后现场支付。</li>  -->
        </ul>
       <p class="fw f14 f-yahei mt20 border-top ptb10"><span class="t-c titleTop">退票须知</span></p>
       <ul class="mt20 clearfix">
            <li class="clearfix mt10"><span class="square mr6"></span>发车前<input type="text" class="r-input w60 mr4 ml4" value="${alreadyQuoteVo.noReturn }" />小时不退款。</li>
            <li class="clearfix mt10"><span class="square mr6"></span>在发车<input type="text" class="r-input w60 mr4 ml4" value="${alreadyQuoteVo.returnOneTime}" />小时前可以申请退款，退款手续费为百分之<input type="text" class="r-input w60 mr4 ml4" value="${alreadyQuoteVo.returnOnePer }" />。</li>
            <li class="clearfix mt10"><span class="square mr6"></span>在发车<input type="text" class="r-input w60 mr4 ml4" value="${alreadyQuoteVo.returnTwoTime }" />小时前可以申请退款，退款手续费为百分之 <input type="text" class="r-input w60 mr4 ml4" value="${alreadyQuoteVo.returnTwoPer }" />。</li>
        </ul>
        <p class="mt20 libg"><span class="fl ml20 lh28">其他</span></p>	
        <p class="fw f14 f-yahei mt20 ptb10"><span class="t-c titleTop">附加服务</span></p>
     	<p class="mt20">
     		<textarea class="r-input more-show w400" id="contentServer">${alreadyQuoteVo.additionalServices }</textarea>
     	</p>
     	<p class="fw f14 f-yahei mt20 ptb10"><span class="t-c titleTop">备注信息</span></p>
     	<p class="mt20">
     		<textarea class="r-input more-show w400" id="contentRemark">${alreadyQuoteVo.remark }</textarea>
     	</p>
	</div>
</body>
</html>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script>
$(function(){
	// 油费选中则不显示oilFeeLi，未选中则显示 
	if($("#oilFee").attr("checked"))
	{
		$("#oilFeeLi").hide();
	}
	else
	{
		$("#oilFeeLi").show();
	}
	//input框不可输入点击，选中
	$("input:text").each(function(){
		$(this).addClass("r-input-gray").attr("readonly","readonly");
	});
	$("input:checkbox").each(function(){
		$(this).addClass("r-input-gray").attr("disabled","true");
	});

	//行程备注
	$("#showTripDetail").hover(function(){
	    $("#tripDetailDiv").show();
	},function(){
		$("#tripDetailDiv").hide();
	});
});

//附加服务
var contentServerEditor;
KindEditor.ready(function(K) {
	var options = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist']
	}
	contentServerEditor = K.create("#contentServer",options);
	//文字编辑器不可输入
	contentServerEditor.readonly(true);
});

//备注信息
var contentRemarkEditor;
KindEditor.ready(function(K) {
	var options = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist']
	}
	contentRemarkEditor = K.create("#contentRemark",options);
	//文字编辑器不可输入
	contentRemarkEditor.readonly(true);
});
</script>