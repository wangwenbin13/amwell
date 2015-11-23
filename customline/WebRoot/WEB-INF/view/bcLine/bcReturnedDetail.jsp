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
<title>包车管理-包车订单列表-包车退票</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;包车管理&nbsp;>&nbsp;包车退票</p></div>
<div class="mt25 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">包车退票</a></li>
        </ul>
    </div>
    <p class="mt20 fw"><span>订单号：<em class="blue1 fw f16">${bcReturnVo.orderNo }</em></span><span class="ml40 fw">订单金额：￥${bcReturnVo.totalPrice}</span><span class="ml40 fw">包车商家：${bcReturnVo.brefName}</span></p>
    <div class="sh-add-new bsLineDetailLi mt20">
       <ul class="clearfix gray2">
            <li class="fl"><span class="fl w100 t-r">用户昵称/ID：</span>${bcReturnVo.nickName }/${bcReturnVo.displayId }</li>
         <li class="fl"><span class="fl w100 t-r">联系电话：</span>${bcReturnVo.telephone }</li>
         <li class="fl"><span class="fl w100 t-r">人数：</span>${bcReturnVo.totalPassengers }</li>
         <li class="fl"><span class="fl w100 t-r">用车联系人：</span>${bcReturnVo.contacts }</li>
         <li class="fl"><span class="fl w100 t-r">联系电话：</span>${bcReturnVo.contactPhone }</li>
         <li class="fl"><span class="fl w100 t-r">车辆数：</span>${bcReturnVo.totalCars }</li>
         <li class="fl"><span class="fl w100 t-r">出发时间：</span>${bcReturnVo.startTime }</li>
         <li class="fl"><span class="fl w100 t-r">返程时间：</span>${bcReturnVo.returnTime }</li>  
         <li class="fl"><span class="fl w100 t-r">车辆年限：</span>
         	<s:if test="%{bcReturnVo.carAge==0}">不限</s:if>
         	<s:if test="%{bcReturnVo.carAge==1}">1年以内</s:if>
         	<s:if test="%{bcReturnVo.carAge==2}">3年以内</s:if>
         	<s:if test="%{bcReturnVo.carAge==3}">5年以内</s:if>
         </li>         
         <li class="fl"><span class="fl w100 t-r">上车点：</span>${bcReturnVo.beginAddress }</li>
         <li class="fl"><span class="fl w100 t-r">下车点：</span>${bcReturnVo.endAddress }</li>                     
         <li class="fl"><span class="fl w100 t-r">包车方式：</span>
         	<s:if test="%{bcReturnVo.charteredMode==1}">单趟</s:if>
         	<s:if test="%{bcReturnVo.charteredMode==2}">往返</s:if>
         	<s:if test="%{bcReturnVo.charteredMode==3}">包天</s:if>
         </li>
         <li class="fl"><span class="fl w100 t-r">包车类型：</span>
         	<s:if test="%{bcReturnVo.businessType==1}">旅游包车</s:if>
         	<s:if test="%{bcReturnVo.businessType==2}">商务接送</s:if>
         	<s:if test="%{bcReturnVo.businessType==3}">过港接送</s:if>
         </li>
         <!--                     
         <li class="fl"><span class="fl w100 t-r">提交时间：</span>2014-14-14 14:14</li>
          -->  
         <li class="fl"><span class="fl w100 t-r">开具发票：</span>
         	<s:if test="%{bcReturnVo.needInvoice==0}">不需要发票</s:if>
         	<s:if test="%{bcReturnVo.needInvoice==1}">需要发票</s:if>
         </li> 
         <s:if test="%{bcReturnVo.needInvoice==1}">
             <li class="fl"><span class="fl w100 t-r">发票抬头：</span>${bcReturnVo.invoiceHeader}</li>
         </s:if>        
        </ul>      
    </div>
    <p class="mt20 fw">退票手续费：扣 <em class="blue1 fw f16">${bcReturnVo.realPer }%</em> 手续费</p>
    <p class="mt20 fw">退票金额：  <em class="red1 fw f16" id="realReturnMoney">￥ 2250.00</em> 元</p>
    <p class="mt20 fw">退票须知： </p>
    <p class="mt10 ml20">*发车前 ${bcReturnVo.noReturn} 小时不退款；</p>
    <p class="mt10 ml20">*在发车 ${bcReturnVo.returnOneTime} 小时前可以申请退款，退款手续费为  ${bcReturnVo.returnOnePer }%；</p>
    <p class="mt10 ml20">*在发车 ${bcReturnVo.returnTwoTime} 小时前可以申请退款，退款手续费为  ${bcReturnVo.returnTwoPer }%；</p>
    <p class="t-c mt10 mb20">
        <a href="javascript:void(0);" class="display-ib btn1 white mr40 f14" onclick="subMit();">确定</a>
        <a  class="display-ib btn1 white f14" href="javascript:void(0);" onclick="history.back();">取消</a>
    </p> 
</div>
<form action="" style="display: none;" id="addForm">
	<input type="text" name="bcReturnHistoryEntity.orderId" value="${bcReturnVo.orderId }"/>
	<input type="text" name="bcReturnHistoryEntity.orderNo" value="${bcReturnVo.orderNo }"/>
	<input type="text" name="bcReturnHistoryEntity.passengerId" value="${bcReturnVo.passengerId }"/>
	<input type="text" name="bcReturnHistoryEntity.businessId" value="${bcReturnVo.businessId }"/>
	<input type="text" name="bcReturnHistoryEntity.bc_line_id" value="${bcReturnVo.bc_line_id }"/>
	<input type="text" name="bcReturnHistoryEntity.oldMoney" id="oldMoney"/>
	<input type="text" name="bcReturnHistoryEntity.bcReturnMoney" id="bcReturnMoney"/>
	<input type="text" name="bcReturnHistoryEntity.realPer" id="realPer"/>
	<input type="text" name="telephone" value="${bcReturnVo.telephone}"/>
</form>
</body>
</html>
<script type="text/javascript">
	$(function(){
		//计算实际退款金额
		countRealReturnMoney();
	});

	//计算实际退款金额
	function countRealReturnMoney(){
		var oldMoney = "${bcReturnVo.totalPrice}";
		//手续费率
		var realPer = "${bcReturnVo.realPer}";
		var per = parseFloat(realPer/100);
		var poundage =(Number(parseFloat(oldMoney*per))).toFixed(2);
		var realReturnMoney = (Number(parseFloat(oldMoney-poundage))).toFixed(2);
		$("#realReturnMoney").html("￥"+realReturnMoney);
		$("#oldMoney").val(oldMoney);
		$("#bcReturnMoney").val(realReturnMoney);
		$("#realPer").val(realPer);
	}

	//执行退票操作
	function subMit(){
		parent.window.showPopCommonPage("确定要退票吗？");
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
		$("#commonSure",parent.window.document).click(function(){
				//解绑定，防止多次执行click事件
				$("#commonSure",parent.window.document).unbind('click');
				parent.window.popLodeShowPage(true);
				parent.window.closePopCommonPage();
				//退票
				$("#addForm").ajaxSubmit({
					
					type : 'post',
					url : '../bcReturnTicket/doBcReturn.action',
					success : function(data) {
						if (data == "success") {
							parent.window.popLodeShowPage(false);
							parent.window.showRturnTip("退票成功",true);
							window.location.href="../bcReturnTicket/forwardBcReturnPage.action";
						}else if("error"==data){
							parent.window.popLodeShowPage(false);
							parent.window.showRturnTip("退票失败",false);
						}else{
							parent.window.popLodeShowPage(false);
							parent.window.showRturnTip(data,false);
						}
					}
				});
			});
	}
</script>
