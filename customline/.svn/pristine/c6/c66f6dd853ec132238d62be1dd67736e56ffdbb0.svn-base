<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>运营平台－用户、订单管理-退票弹窗</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <div class="pop black1" style="width:730px;background:#fff;" id="PopOrderReturnPage">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white">退票</div>
	        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onclick="loadPage.window.closePopOrderReturnPage();"></a></div>
	    </div>
	    <div class="pop-main p10 clearfix">
	    	<div class="sh-add dateList">
	    		<div class="mb10 clearfix divRetrun">
		    		<span class="fl">订单号：</span>
			    	<span class="fl mr10">${returnTicketVo.leaseOrderNo}</span>  
		    	</div>
		    	<div class="mb10 clearfix divRetrun">
		    		<span class="fl">乘客：</span>
			    	<span class="fl mr10 w210">${returnTicketVo.displayId}/${returnTicketVo.nickName}</span>  
		    	</div>
		    	<div class="mb10 clearfix divRetrun">
		    		<span class="fl">联系电话：</span>
			    	<span class="fl mr10 w210">${returnTicketVo.telephone}</span>  
		    	</div>
		    	<div class="mb10 clearfix divRetrun">
		    		<span class="fl">线路名称：</span>
			    	<span>${returnTicketVo.lineName}</span>  
		    	</div>
		    	<div class="mb10 clearfix divRetrun">
		    		<span class="fl">班次：</span>
			    	<span class="fl">${returnTicketVo.orderStartTime}</span>  
		    	</div>
		    	<div class="mb10 clearfix">
		    		<span class="fl">日期：</span>
			    	<span>
			    		<s:iterator value="returnTicketVo.orderLocalVos" var="orderLocalVo" status="s">
							<s:if test="%{oneWeekAgo<=#orderLocalVo.orderDate}">
								<input type="checkbox" value="${orderLocalVo.localId}" class="vf4 mr4" id="orderDate_${s.count }" onclick="loadPage.window.changePrice(3);"/>
								<input type="hidden" value="${orderLocalVo.lineClassId }" id="lineClassId_${s.count }"/>
							</s:if>
							<span id="span_order_date_${s.count }">
								${orderLocalVo.orderDate}
							</span>
							<s:if test="%{#s.count<=dateSize-1}">,</s:if>
						</s:iterator>
			    	</span>  
		    	</div>
		    	<div class="mb10 clearfix divRetrun">
		    		<span class="fl">退票原因：</span>
			    	<span class="fl"><input type="radio" name="radio" id="checkFelBox_1" class="vf2 mr4" onclick="loadPage.window.changePrice(1);"/>乘客原因,扣取手续费</span>

			    	<span class="fl"><input type="radio" name="radio" id="checkFelBox_2" class="vf2 ml20 mr4" onclick="loadPage.window.changePrice(2);"/>非乘客原因,不扣取手续费</span>  
		    	</div>
		    	<div class="mb10 clearfix divRetrun">
		    		<span class="fl">退款金额：</span>
			    	<span class="fl mr60"><em class="red1 fw f16 mr4" id="returnMoney">￥0</em>元</span>
		    	</div>
		    	<div class="mb10 clearfix">
		    		<span class="fl" style="height: 50px;">备注：</span><p>1、退票手续费：10%</p>  
		    	</div>
	    	</div>
	    	
	        <p class="t-c mb20 mt20">
		        <input type="button" value="确&nbsp;&nbsp;定" class="display-ib btn1 white f14 mr20" onclick="loadPage.window.saveSelect();" />
		        <input type="button" value="取&nbsp;&nbsp;消" class="display-ib btn1 white f14 mr20" onclick="loadPage.window.closePopOrderReturnPage();"/>
	        </p>   
	    </div>
    </div>
  </body>
</html>
<script type="text/javascript">

//关闭增加弹出层页面
function closePopOrderReturnPage()
{
    $("#PopOrderReturnPage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}

//实际退款金额
var realBackMoney = "";
// 手续费
var realPoundage = "";
//手续费百分比
var percent = "0.1";
//退票天数
var returnDateNum = "";
//具体退票的日期
var returnDates = "";
//实际支付
var realPayMoney = "";
//退票原因
var returnReson = "";
//班次ID集合
var lineClassIds = "";

function saveSelect()
{
	var checkFelBox_1 = $("#checkFelBox_1",parent.window.document).attr("checked");
	var checkFelBox_2 = $("#checkFelBox_2",parent.window.document).attr("checked");
	if("checked"!=checkFelBox_1 && "checked"!=checkFelBox_2){
		parent.parent.showPopCommonPage2("请选择要退票的原因");
		return;
	}
	parent.parent.popLodingPage(true);
	var dateSize = "${dateSize}";
	var selectValue = "";
	var selectDate = "";
	var selectLineClass = "";
	for(var i = 1;i<=dateSize;i++){
		var value = $("#orderDate_"+i,parent.window.document).val();
		var dateChecked = $("#orderDate_"+i,parent.window.document).attr("checked");
		var sedate = $("#span_order_date_"+i,parent.window.document).html();
		var lineClassId = $("#lineClassId_"+i,parent.window.document).val();
		if("checked"==dateChecked){
			selectValue +=value+",";
			selectDate +=sedate+",";
			selectLineClass +=lineClassId+",";
		}
	}
	var localId = selectValue;
	returnDates = selectDate;
	lineClassIds = selectLineClass;
	var telephone = "${returnTicketVo.telephone}";
	var orderStartTime = "${returnTicketVo.orderStartTime}";
	var oldMoney = "${returnTicketVo.payMoney}";
	var leaseOrderNo = "${returnTicketVo.leaseOrderNo}";
	var leaseOrderId = "${returnTicketVo.leaseOrderId}";
  	var url = "../returnTicket/doReturnTicket.action?lineClassIds="+lineClassIds+"&returnTicket.returnReson="+returnReson+"&returnTicket.realPayMoney="+realPayMoney+"&localIds="+localId+"&returnTicket.lineBaseId=${returnTicketVo.lineBaseId}&returnTicket.orderStartTime="+orderStartTime+"&returnTicket.passengerId=${returnTicketVo.passengerId}&returnTicket.leaseOrderNo="+leaseOrderNo+"&leaseOrderId="+leaseOrderId +"&returnTicket.realBackMoney="+realBackMoney+"&returnTicket.realPoundage="+realPoundage+"&returnTicket.percent="+percent+"&returnTicket.returnDateNum="+returnDateNum+"&returnTicket.returnDates="+returnDates+"&dateSize="+dateSize+"&returnTicket.oldMoney="+oldMoney+"&random="+Math.floor(Math.random()*10000+1);
	$.ajax({
			url:url,		
			cache:false,	
			async : false,
			success:function(data){
			  if(data=="success"){
				  parent.parent.popLodingPage(false);
				  parent.parent.showRturnTip("退票成功",true);
				  closePopOrderReturnPage();
				  window.location.href="../returnTicket/getReturnTicketList.action";
			  }
			},error:function(){
				parent.parent.popLodingPage(false);
				parent.parent.showRturnTip("退票失败",false);
			}
	  });
}

//改变价格
function changePrice(type){
	if(type==3){
		//添加或取消日期
		if("checked"==$("#checkFelBox_1",parent.window.document).attr("checked")){
			type=1;
		}
		if("checked"==$("#checkFelBox_2",parent.window.document).attr("checked")){
			type=2;
		}
	}
	var checkFelBox = $("#checkFelBox_"+type,parent.window.document).attr("checked");//document.getElementById("#checkFelBox_"+type);
	var dateSize = "${dateSize}";
	if("checked"!=checkFelBox){
		$("#returnMoney",parent.window.document).html("￥0");
		return;
	}
	var flag = false;
	var num = 0;
	for(var i = 1;i<=dateSize;i++){
		var value = $("#orderDate_"+i,parent.window.document).val();
		var dateChecked = $("#orderDate_"+i,parent.window.document).attr("checked");
		if("checked"==dateChecked){
			flag = true;
			num++;
		}
	}
	if(!flag){
		parent.parent.showPopCommonPage2("请选择要退票的日期");
		$("#returnMoney",parent.window.document).html("￥0");
		$("#checkFelBox_"+type,parent.window.document).attr("checked",false);
	}else{
		var oldMoney = "${returnTicketVo.payMoney}";
		var newMoney = parseFloat(oldMoney/dateSize)*num;
		//手续费
		var poundage =(Number(parseFloat(newMoney*0.1))).toFixed(2);
		var realMoney = "";
		if(2==type){
			//非乘客原因
			newMoney = (Number(parseFloat(newMoney))).toFixed(2)
			$("#returnMoney",parent.window.document).html("￥"+newMoney);
			poundage = "0.00";
			realMoney = (Number(parseFloat(newMoney)-parseFloat(poundage))).toFixed(2);
		}else{
			realMoney = (Number(parseFloat(newMoney)-parseFloat(poundage))).toFixed(2);
			$("#returnMoney",parent.window.document).html("￥"+realMoney);
		}
		realPoundage = poundage;
		returnDateNum = num;
		realBackMoney = realMoney;
		realPayMoney = (Number(parseFloat(oldMoney)-parseFloat(newMoney))).toFixed(2);
		returnReson = type;
	}
}
</script>
