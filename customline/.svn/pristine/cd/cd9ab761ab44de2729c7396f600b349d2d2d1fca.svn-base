<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../resource.jsp"/>
<%
    String path1 = request.getContextPath();
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报价详情</title>
</head>
<body>
<div class="pop black1" id="popSelectMerchantPage" style="width:900px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">报价详情</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopPricePage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10">
       <p class="fw">车辆信息：</p>
       <s:iterator value="list" var="bus" status="s" >
	       <p class="ml10 mt10">车龄：${bus.carAge } 年<span class="ml20">座位数：${bus.carSeats }</span><span class="ml20">车辆类型：${bus.carType }&nbsp;&nbsp; ${bus.totalCars }辆</span></p>
       </s:iterator>
        <p class="fw mt20">费用信息</p>    
        <div class="table2-text sh-ttext" style="margin-top:10px;">       
           <table width="100%" border="0" class="table1" id="tableDivDetail">
                 <tr align="center">
                   <th scope="col">费用</th>
                   <th scope="col" width="50%">金额（元）</th>
                 </tr>
                 <tr align="center">                 	
                 	<td>基本费用</td>
                 	<td class="f-arial">${offerModel.basicCost }</td>
                 </tr>
                 <s:if test="%{offerModel.driverMealCost!=0}">
	                 <tr align="center">                 	
	                 	<td>司机住宿费</td>
	                 	<td class="f-arial">${offerModel.driverMealCost }</td>
	                 </tr>
                 </s:if>
                 
                 <s:if test="%{offerModel.driverHotelCost!=0}">
                 <tr align="center">                 	
                 	<td>司机餐饮费</td>
                 	<td class="f-arial">${offerModel.driverHotelCost }</td>
                 </tr>
                 </s:if>
                 <s:if test="%{offerModel.oilCost!=0}">
                 	<tr align="center">
                 	<td>油费</td>
                 	<td class="f-arial"> ${offerModel.oilCost }</td>
                 	</tr>
                 </s:if>
                 <tr align="center">                 	
                 	<td class="fw">合计</td>
                 	<td class="f-arial fw">${offerModel.totalCost}</td>
                 </tr>
            </table>        
         </div>
         <p class="fw mt20">购买须知</p>
         <p class="mt10 gray1">购票须知</p>
         <div class="sh-add-new line24 priceDetail">
         	<p><em class="red1 mr4">*</em>不计入报价的项，若实际运行时，发生此类费用，将在行程结束时现场结算。</p>
         	<!--  
			<p><em class="red1 mr4">*</em>根据相关法规车辆正常使用时间为6:00-2:00（次日），司机工作时间为  &nbsp; ${offerModel.driverWorkHour } &nbsp;小时，超出&nbsp; ${offerModel.driverWorkHour } &nbsp; 小时之外的时间按 &nbsp; ${offerModel.overtimeCost } &nbsp; 元/小时补足司机工资。</p>
			<p><em class="red1 mr4">*</em>本次运行按 &nbsp;  ${offerModel.limitMileage } &nbsp;公里/天算，超出公里数按&nbsp;   ${offerModel.overmileageCost } &nbsp;元/公里另计；</p>
			-->
			<s:if test="%{offerModel.oilCost==0 }">
			<p><em class="red1 mr4">*</em>本次报价不包含油费，客户用车完毕后与司机将油箱的油料补满。（以油表为准）。</p>
			</s:if>
			<!-- <p><em class="red1 mr4">*</em>报价之外的费用需在行程结束后现场支付。</p> -->     	
         </div>
         <p class="mt10 gray1">退票须知</p>
         <div class="sh-add-new line24 priceDetail">
         	<p><em class="red1 mr4">*</em>发车前<em class="fw red1 f14"> ${offerModel.noReturn }&nbsp;</em>小时不退款；</p>
			<p><em class="red1 mr4">*</em>在发车<em class="fw red1 f14"> ${offerModel.returnOneTime }&nbsp;</em>小时前可以申请退款，退款手续费为<em class="fw red1"> ${offerModel.returnOnePer }%</em>；</p>
			<p><em class="red1 mr4">*</em>在发车<em class="fw red1 f14"> ${offerModel.returnTwoTime }&nbsp;</em>小时前可以申请退款，退款手续费为<em class="fw red1"> ${offerModel.returnTwoPer }%</em>；</p>
         </div>
         <p class="mt10 gray1">附加服务</p>
          <div class="sh-add-new line24 priceDetail">
          	<p> ${offerModel.additionalServices }</p>
          </div>
         <p class="mt10 gray1">备注</p>
          <div class="sh-add-new line24 priceDetail">
          	${offerModel.remark }
          </div>
       <p class="t-c mt10 mb20">
        	<a  class="display-ib btn1 white f14" onclick="closePopPricePage();" href="javascript:void(0);">取消</a>
        </p>
    </div>
</div>
</body>
</html>
<script type="text/javascript">


//关闭增加弹出层页面
function closePopPricePage()
{
	 	$("#popSelectMerchantPage").hide();
	    $("#topHide", parent.window.document).hide();
	    $("#leftHide", parent.window.document).hide();
	    $("#mainBody").hide();
}
</script>
