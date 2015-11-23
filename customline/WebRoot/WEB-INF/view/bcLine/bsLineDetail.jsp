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
<input type="hidden" id="expireTime" value="${lineModel.expireTime }"/>
<input type="hidden" id="nowTime" value="${lineModel.nowTime }"/>
<input type="hidden" id="remainingTime" value="${lineModel.remainingTime }"/>
<input type="hidden" id="usePhone" value="${lineModel.telephone}"/> 
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<input type="hidden" id="bc_line_id" value="${lineModel.bc_line_id}"/>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;订单管理&nbsp;>&nbsp;订单详情</p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">用户包车详情</a></li>
        </ul>
    </div>
    <p class="mt20 fw">基本信息： </p>
    <div class="sh-add-new bsLineDetailLi">
	    <ul class="clearfix gray2">
	         <li class="fl"><span class="fl w100 t-r">用户昵称/ID：</span>${lineModel.nickName}/${lineModel.displayId}</li>
	         <li class="fl"><span class="fl w100 t-r">联系电话：</span>${lineModel.telephone}</li>
	         <li class="fl"><span class="fl w100 t-r">人数：</span>${lineModel.totalPassengers}</li>
	         <li class="fl"><span class="fl w100 t-r">乘车人：</span>${lineModel.contacts}</li>
	         <li class="fl"><span class="fl w100 t-r">联系电话：</span>${lineModel.contactPhone}</li>
	         <li class="fl"><span class="fl w100 t-r">车辆数：</span>${lineModel.totalCars}</li>
	         <li class="fl"><span class="fl w100 t-r">出发时间：</span>${lineModel.startTime}</li>
	         <li class="fl"><span class="fl w100 t-r">返程时间：</span>${lineModel.returnTime}</li>  
	         <li class="fl"><span class="fl w100 t-r">车辆年限：</span>${lineModel.carAge}</li>                                    
	         <li class="fl"><span class="fl w100 t-r">包车方式：</span>${lineModel.charteredMode==1?'单趟':lineModel.charteredMode==2?'往返':'包天' }</li>
	         <li class="fl"><span class="fl w100 t-r">包车类型：</span>${lineModel.businessType==1?'旅游包车':lineModel.businessType==2?'商务接送':'过港接送' }</li>                      
	         <li class="fl"><span class="fl w100 t-r">提交时间：</span>${lineModel.createOn}</li>	          
	         <li class="fl"><span class="fl w100 t-r">出发省份：</span>${lineModel.areaName}</li>
	         <li class="fl"><span class="fl w100 t-r">出发城市：</span>${lineModel.cityName}</li>   
	         <li class="fl"><span class="fl w100 t-r">上车点：</span>${lineModel.beginAddress}</li>
	         <li class="fl"><span class="fl w100 t-r">下车点：</span>${lineModel.endAddress}</li>   
	         <li class="fl"><span class="fl w100 t-r">开具发票：</span>${lineModel.needInvoice==0?'不需要发票':'需要发票'}</li>   
	         <s:if test="%{lineModel.needInvoice==1}">
	            <li class="fl" style="width:52%;height:auto"><span class="fl w100 t-r">发票抬头：</span>${lineModel.invoiceHeader}</li>  
	         </s:if>  
	     </ul>      
    </div>
    <p class="mt20 fw">行程描述： </p>
    <p class="mt10 ml20">${lineModel.journeyRemark}</p>
<%--     <p class="mt20 fw">备注信息： </p>
    <p class="mt10 ml20">${lineModel.remark}</p> --%>
    <s:if test="%{lineModel.lineStatus==4}">
    	<p class="mt20 fw red">报价时间已过期，请返回！ </p>
    </s:if>
    <s:if test="%{lineModel.lineStatus==0}">
   		<p class="mt20 fw">反馈信息： </p>
  	    <textarea style="width:600px;height:100px;" class="r-input more-show mt10" id="feedbackMsg" onkeydown="textCounts();" onkeyup="textCounts();" check="11" ></textarea>字数限定在1000字节以内（2字节=1个中文字）
     
    </s:if>
     <s:if test="%{lineModel.lineStatus==1}">
   		<p class="mt20 fw">反馈信息： </p>
  	    <textarea style="width:600px;height:100px;" class="r-input more-show mt10" id="feedbackMsg" >${lineModel.rejectContent}</textarea>
    </s:if>
    <div class="mt20">
	    <s:if test="%{lineModel.lineStatus==2 || lineModel.lineStatus==3  || lineModel.lineStatus==0}">
	    	<p><span id="countDownSpanShow">还剩<em class="red1 fw ml4 mr4" id="countDownEmShow"></em>过期</span><span class="fl red4 fw hide" id="countDownEnd">报价已结束</span></p>
	    </s:if>
		   <s:if test="%{lineModel.lineStatus==2 || lineModel.lineStatus==3}">
	    	<div class="table2-text sh-ttext mt20 w80p" id="tableDivDetail">
		    	<table width="100%" border="0" class="table1">
		    		<tr align="center">
		    			<th scope="col">商家</th>
		    			<th scope="col" width="20%">报价</th>
		    			<th scope="col" width="20%">操作</th>
		    		</tr>
		    		<s:iterator value="list" var="offer" status="s"  >
		    			<s:if test="%{#offer.offerStatus==0 }">
				    		<tr align="center" class="tr bg1"  >
				    			<td>${offer.name }</td>
				    			<td>未报价</td>
				    			<td>…………</td>
				    		</tr>
		    			</s:if><s:else>
		    				<tr align="center" class="tr bg1" offerId="${offer.id}">
				    			<td>${offer.name }</td>
				    			<td>${offer.totalCost }</td>
				    			<td><input type="button" value="详情" class="blue1 f14 " onclick="getBusinessOffer('${offer.id}')"/></td>
				    		</tr>
		    			</s:else>
		    		</s:iterator>
		    	</table>
	    	</div>
		   </s:if>
    	<p class="t-c mt10 mb20">
    	<s:if test="%{lineModel.lineStatus==0}">
    		<input  type="button" onclick="goSelectMerchantPage()" class="display-ib btn1 white mr40 f14"  value="发送商家"/>
        	<input type="button"  class="display-ib btn1 white f14 mr40" id="audit"  value="审核不通过"/>
    	</s:if>
			<p class="t-c mt20"><input type="button" value="返回" onclick="history.back();"  class="display-ib btn1 white"/></p>
	        	<!-- <a  class="display-ib btn1 white f14" href="javascript:void(0);">返回</a> -->
    		<!-- <a href="javascript:void(0);" onclick="goSelectMerchantPage()" class="display-ib btn1 white mr40 f14">发送商家</a> -->
        	<!--   <p class="t-c mt20"><input type="button" value="返回" onclick="history.back();"  class="display-ib btn1 white"/></p> -->
        </p>
    </div>
</div>
</body>
<script type="text/javascript">
//添加商家
function goSelectMerchantPage()
{
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    var bcLineId=$("#bc_line_id").val();
//     alert(bcLineId);
    $("#showPage").load("../charteredLine/getSelectMerchant.action?random="+Math.floor(Math.random()*10000+1)+"&bcLineId="+bcLineId); 
    $("#mainBody").show();
}

//报价详情
// $("#tableDivDetail tr td").click(function(){
function getBusinessOffer(offerId){	
// 	alert(i);
	//如果是没有数据就不调用后面的方法
	if($("#noDate").html()!= undefined){			
		return false;
	}
	//获取选中文字，如果选中文字则不响应点击事件
	//ie8写法
	if(typeof(document.selection) != "undefined")
	{
		var selecter = document.selection.createRange().text;
	}
	else if(typeof(window.getSelection()) != "undefined")
	{
		var selecter = window.getSelection();
	}
	else
	{
		var selecter = "";
	}
	if(selecter != "")
	{
	 	return false;
	}
	else
	{
		//var leaseOrderId = $(this).parent().attr("leaseOrderId");
		//跳转到订单详情
// 		alert(offerId);

		$("#topHide", parent.window.document).show();
	    $("#leftHide", parent.window.document).show();
	    $("#showPage").load("../charteredLine/getMerchantPriceDetail.action?random="+Math.floor(Math.random()*10000+1)+"&offerId="+offerId); 
	    $("#mainBody").show(); 
	}
}


function goMerchantPriceDetailPage()
{
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("../charteredLine/getMerchantPriceDetail.action?random="+Math.floor(Math.random()*10000+1)); 
    $("#mainBody").show();
}

//审核不能过
$('#audit').click(function(){
	setSubmitDisales(true);
	var feedbackMsg=$("#feedbackMsg").val();
	var bcLineId=$("#bc_line_id").val();
	var usePhone=$("#usePhone").val();
// 	alert(feedbackMsg);
	if(feedbackMsg==null || feedbackMsg==""){
		parent.parent.showRturnTip("反馈信息不能为空",false);
		setSubmitDisales(false);
	}else{
		
		$.ajax({
		url:'../charteredLine/getOutAudit.action?',
		cache:false,
		type:'post',
		data:{"feedbackMsg":feedbackMsg,"bcLineId":bcLineId,"usePhone":usePhone},
		dataType:"json",
		success:function(data){
			if(data=="success"){
				$("#iframe_right",parent.parent.window.document).attr("src","../charteredLine/getCharteredLineList.action?");
			}else{
				parent.parent.showRturnTip("审核失败！",false);
			}
		}
	})
	}
	

})

//返回列表页面
function reset(){
	$("#iframe_right",parent.parent.window.document).attr("src","../charteredLine/getCharteredLineList.action");
}

// 倒计时开始

//设定倒数秒数  
var surplusSeconds = parseInt($("#remainingTime").val());
//倒计时 剩余秒数
function getCountdown(surplusSeconds)
{
	//var endTime = new Date("2014/11/30,15:21:00");
	//var nowTime = new Date();
	//var surplusSeconds = ((endTime.getTime()-nowTime.getTime())/1000);
	
	var d = parseInt((surplusSeconds/3600)/24);
	var h = parseInt((surplusSeconds/3600)%24);
	var m = parseInt((surplusSeconds/60)%60);
	var s = parseInt(surplusSeconds%60);
	if(surplusSeconds <= 0)
	{
		return 0;
	}
	var str = "";
	if(d != 0)
	{
		str = d+"天";
	}
	if(h != 0)
	{
		str = str+h+"小时";
	}	
	if(m != 0)
	{
		str = str+m+"分";
	}
	if(s != 0)
	{
		str = str+s+"秒";
	}
	return str;
}

//显示倒计时
function showCountdown(){  
	surplusSeconds -= 1;  
    var surplusCountdown = getCountdown(surplusSeconds);
	if(surplusCountdown == 0)
	{
		$("#countDownEnd").show();
		$("#countDownSpanShow").hide();
		$("#countDownEmShow").text('');
	}
	else
	{
		$("#countDownEnd").hide();
		$("#countDownSpanShow").show();
		$("#countDownEmShow").text(surplusCountdown);
	} 
    //每秒执行一次,showTime()  
    setTimeout("showCountdown()",1000);  
    
}  
$(function(){
	showCountdown();
// 	alert(surplusSeconds);
});

// 倒计时结束

//模板标题、模板内容字数限制
 function textCounts(){
 	var maxlimit = 1000;
 	var count=0;	//计算字符数
 	var content=$("#feedbackMsg").val();
	var char=0;		//计算剩余字符数	
// 	alert(content.length);
 	for(var i=0;i<content.length;i++){
//  	 	alert("count="+count+"i="+i);
		var ask11=content[i].charCodeAt();
		if(0<=ask11 && ask11<161){
			count++;
			if(count<maxlimit){
			char++;
			}
		}else{
			count+=2;
			if(count<maxlimit){
			char++;
			}
		}
	}
	if(count>maxlimit){
		content = content.substring(0, char);
		
		$("#feedbackMsg").val(content);
	}
 	
 	
 	
 }
 
 //按键至灰
 function setSubmitDisales(flag)
{
     if(flag)
  {
  $('#audit').removeClass("btn1").addClass("btn1-gray");
  }
  else
  {
   $('#audit').removeClass("btn1-gray").addClass("btn1");
  } 
  $('#audit').attr("disabled", flag);
}
</script>
</html>
