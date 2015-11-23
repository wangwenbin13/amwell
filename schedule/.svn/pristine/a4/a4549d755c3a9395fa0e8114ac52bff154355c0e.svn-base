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
<title>包车管理-包车线路列表-包车调度</title>
<jsp:include page="../resource.jsp"/>
<script src="../js/common/Util.js" type="text/javascript"></script>
</head>

<body>
    <form id="scheduleForm" action="../businessBidding/scheduleDriverAndCar.action" method="post">
	    <input id="orderNo" type="hidden" value="${scheduleVo.orderNo}" name="orderNo"/>
		<p class="subNav">当前位置：首页&nbsp;&gt;&nbsp;包车管理&nbsp;&gt;&nbsp;包车调度<a class="red1 ml30" href="javascript:void(0)" onclick="javascript:history.go(-1);">返回</a></p>
		<div class="mt20 ml30 black1 mr28">
			<p class="fw f14 f-yahei">包车调度</p>
			<p class="mt20 libg"><span class="fl ml20 lh28">订购信息</span></p>	
			<div class="fl lineDetail-left mt20 ml15 mr30" style="height:80px;">
				<p class="clearfix">
			 	    <s:if test='%{scheduleVo.businessType==1}'> 
				        <span class="display-ib lineKind lineKind-work">旅游包车</span>
				    </s:if>
					<s:if test='%{scheduleVo.businessType==2}'> 
				        <span class="display-ib lineKind lineKind-free">商务接送</span>
				    </s:if>
				    <s:if test='%{scheduleVo.businessType==3}'> 
				        <span class="display-ib lineKind lineKind-hongkong">过港接送</span>
				    </s:if>
					<span class="fr ml20"><em class="f16 fw yellow4 mr4">${scheduleVo.totalPrice}</em>元</span>
				</p>
				<p class="mt10 clearfix"><span class="fl">人数：${scheduleVo.totalPassengers}人</span><span class="fr ml20">车辆数：${scheduleVo.totalCars}辆</span></p>
				<p class="mt10 clearfix"><span class="fl">联系人：${scheduleVo.nickName}/${scheduleVo.telephone}</span></p>
			</div>
			<div class="mt20 clearfix">
				<ul>
					<li class="mt5 fl w17p h20">车龄:<s:if test='%{scheduleVo.carAge==0}'>不限</s:if><s:if test='%{scheduleVo.carAge==1}'>1年以内</s:if><s:if test='%{scheduleVo.carAge==2}'>3年以内</s:if><s:if test='%{scheduleVo.carAge==3}'>5年以内</s:if></li> 
					<li class="mt5 fl w43p h20">包车方式：包天&nbsp;&nbsp;&nbsp;&nbsp;<s:if test='%{scheduleVo.needInvoice==0}'>不需要</s:if><s:if test='%{scheduleVo.needInvoice==1}'>需发票（${scheduleVo.invoiceHeader}）</s:if></li>
					<li class="mt5 fl w17p h20">出车时间：${scheduleVo.startTime}</li>
					<li class="mt5 fl w17p h20">起点：<span class="display-ib lineDoit lineDoit-start"></span><em class="green1 display-ib stationWn" title="${scheduleVo.beginAddress}" id="beginAddressSpan"><script type="text/javascript">$("#beginAddressSpan").text(Util.cutstr('${scheduleVo.beginAddress}',30))</script></em></li>
					<li class="mt5 fl w43p h20">
						<span class="fl w260">行程备注：<span id="tripDetailSpan"><script type="text/javascript">$("#tripDetailSpan").text(Util.cutstr('${scheduleVo.journeyRemark}',30))</script></span></span>
						<div class="p-r fl w55 h30 t-c" id="showTripDetail">
							<a class="display-ib btn1-red" href="javascript:void(0);">看详情</a>
							<div class="p-a w500 detail-div hide" id="tripDetailDiv">
								<div class="p-a detail-triangle"></div>
								<P class="fw f14 t-l">行程备注：</P>
								<p class="mt15 line20 t-l">${scheduleVo.journeyRemark}</p>
							</div>
						</div>
					</li>
					<li class="mt5 fl w17p h20">返程时间：${scheduleVo.returnTime}</li>
					<li class="mt5 fl w17p h20">终点：<span class="display-ib lineDoit lineDoit-end"></span><em class="red4 display-ib stationWn" title="${scheduleVo.endAddress}" id="endAddressSpan"><script type="text/javascript">$("#endAddressSpan").text(Util.cutstr('${scheduleVo.endAddress}',30))</script></em></li>
					<!--  <li class="mt5 fl w43p h20">需求备注：希望提供2年内新车，车内环境舒适。</li> -->
				</ul>
			</div>
			<p class="mt20 libg"><span class="fl ml20 lh28">调度（以下信息为必填）</span></p>	
			<ul class="mt20 widthfull" id="dirverCarUl">
				<s:if test="null == scheduleVo.scheduleList or 0==scheduleVo.scheduleList.size">
					<li class="ptb10 clearfix" id="firstLi">
				            <span class="w75 t-r fl">司机姓名：</span>
				            <input type="text" class="r-input w100 fl mr8" id="driverName0" name="driverName" driverName="driverName" maxlength="15" value="${schedule.driverName }" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">readonly="readonly"</s:if>/>
				            <span class="w75 t-r fl ml10">电话号码：</span>
				            <input type="text" class="r-input w123 fl mr8" id="driverTel0" name="driverTelephone" driverTel="driverTel" maxlength="15" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">readonly="readonly"</s:if>/>
				            <span class="w75 t-r fl ml10">车牌号：</span>
				            <input type="text" class="r-input w100 fl mr8" id="carNum0" name="carNumber" carNum="carNum" maxlength="10" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">readonly="readonly"</s:if>/>
				            <span class="w75 t-r fl ml10">座位数：</span>
				            <input type="text" class="r-input w70 fl" id="carSeat0" name="carSeats" carSeat="carSeat" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">readonly="readonly"</s:if>/>
			            	<input type="button" class="display-ib btn1 btn1-width white1 ml20 mr20 fw" value="增&nbsp;&nbsp;加" onclick="addNewOneLi(this);" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">style="display: none"</s:if>/>
			            </li>
				</s:if>
				<s:else>
					<s:iterator value="scheduleVo.scheduleList" var="schedule" status="v">
			            <li class="ptb10 clearfix" id="firstLi">
				            <span class="w75 t-r fl">司机姓名：</span>
				            <input type="text" class="r-input w100 fl mr8" id="driverName${v.index }" name="driverName" driverName="driverName" maxlength="15" value="${schedule.driverName }" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">readonly="readonly"</s:if>/>
				            <span class="w75 t-r fl ml10">电话号码：</span>
				            <input type="text" class="r-input w123 fl mr8" id="driverTel${v.index }" name="driverTelephone" driverTel="driverTel" maxlength="15" value="${schedule.driverTelephone }" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">readonly="readonly"</s:if>/>
				            <span class="w75 t-r fl ml10">车牌号：</span>
				            <input type="text" class="r-input w100 fl mr8" id="carNum${v.index }" name="carNumber" carNum="carNum" maxlength="10" value="${schedule.carNumber}" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">readonly="readonly"</s:if>/>
				            <span class="w75 t-r fl ml10">座位数：</span>
				            <input type="text" class="r-input w70 fl" id="carSeat${v.index }" name="carSeats" carSeat="carSeat" maxlength="8" value="${schedule.carSeats }" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">readonly="readonly"</s:if>/>
			            	<input type="button" class="display-ib btn1 btn1-width white1 ml20 mr20 fw" value="增&nbsp;&nbsp;加" onclick="addNewOneLi(this);" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">style="display: none"</s:if>/>
			            	<s:if test="%{#v.index>0}">
			            		<a class='display-ib btn1-red' href='javascript:void(0);' onclick='deleteOneLi(this);' <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">style="display: none"</s:if>>删除</a>
			            	</s:if>
			            </li>
		            </s:iterator>
	            </s:else>
	            
	            
	            <li class="clearfix mt40">
	         	    <input type="button" class="display-ib btn1 white1 mr30 fw" value="保&nbsp;&nbsp;存" id="submitBtn" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">style="display: none"</s:if>/>
	         	    <a class="display-ib btn1-no" href="javascript:void(0);" onclick="clearValue();" <s:if test="scheduleVo.orderStatus == 3 or 4 == scheduleVo.orderStatus">style="display: none"</s:if>>取&nbsp;消</a>
	            </li>
	        </ul>
		</div>
	</form>
</body>
</html>
<script>
$(function(){
	//行程备注
	$("#showTripDetail").hover(function(){
	    $("#tripDetailDiv").show();
	},function(){
		$("#tripDetailDiv").hide();
	});
	validateFunction();
	submitBtnClick();
});

//添加一条新的信息
function addNewOneLi(obj)
{
	var count = ($("#dirverCarUl").children("li").length - 2) + 1;
	$(obj).parent("li").after("<li class='mt20 border-top ptb10'>"+
		"<span class='w75 t-r fl'>司机姓名：</span>"+
        "<input type='text' class='r-input w100 fl mr8' id='driverName"+count+"' name='driverName' driverName='driverName' maxlength='15'/>"+
        "<span class='w75 t-r fl ml10'>电话号码：</span>"+
        "<input type='text' class='r-input w123 fl mr8' id='driverTel"+count+"' name='driverTelephone' driverTel='driverTel' maxlength='15'/>"+
        "<span class='w75 t-r fl ml10'>车牌号：</span>"+
        "<input type='text' class='r-input w100 fl mr8' id='carNum"+count+"' name='carNumber' carNum='carNum' maxlength='10'/>"+
        "<span class='w75 t-r fl ml10'>座位数：</span>"+
        "<input type='text' class='r-input w70 fl' id='carSeat"+count+"' name='carSeats' carSeat='carSeat' maxlength='8' onkeyup='replaceNum(this);' onafterpaste='replaceNum(this);'/>"+
       	"<input type='button' class='display-ib btn1 btn1-width white1 ml20 mr20 fw' value='增&nbsp;&nbsp;加' onclick='addNewOneLi(this);'/>"+
       	"<a class='display-ib btn1-red' href='javascript:void(0);' onclick='deleteOneLi(this);'>删除</a>"+
       	"</li>");
     setOtherAccessPointNotEqOne();
     validateFunction();
}

//重置除第一个之外的其余的li相关属性
function setOtherAccessPointNotEqOne()
{
	//总共多少个添加的司机，除开第一条和最后一条
	var surplusCount = 0;
	surplusCount = $("#dirverCarUl").children("li").length;
	for(var i = 0;i < surplusCount;i++)
	{
		if(i != 0 && i != (surplusCount - 1))
		{
			$("#dirverCarUl").children("li").eq(i).find("input").eq(0).attr("id","driverName"+parseInt(i));
			$("#dirverCarUl").children("li").eq(i).find("input").eq(1).attr("id","driverTel"+parseInt(i));
			$("#dirverCarUl").children("li").eq(i).find("input").eq(2).attr("id","carNum"+parseInt(i));
			$("#dirverCarUl").children("li").eq(i).find("input").eq(3).attr("id","carSeat"+parseInt(i));
		}
	}
	setSubmitDisale(false);
}

//删除当前选中的信息
function deleteOneLi(obj)
{
	$(obj).parent().remove();
	setOtherAccessPointNotEqOne();
}


//获得焦点和失去焦点的验证方法
function validateFunction()
{
	//司机姓名不为空验证
	$("input[driverName='driverName']").focus(function(){  
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		isNotNullValidate(this.id);
	});
	
	//司机电话验证
	$("input[driverTel='driverTel']").focus(function(){  
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidate(this.id,validateJson.MobileOrTell.reg,validateJson.MobileOrTell.tip); 
	});
	
	//车牌号验证
	$("input[carNum='carNum']").focus(function(){  
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidate(this.id,validateJson.isCarNum.reg,validateJson.isCarNum.tip); 
	});
	
	//座位数验证
	$("input[carSeat='carSeat']").focus(function(){  
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidate(this.id,validateJson.Intege.reg,validateJson.Intege.tip); 
	});
}

//提交验证
function submitValidate()
{
	//司机姓名不为空验证
	$("input[driverName='driverName']").each(function(){  
		isNotNullValidate(this.id);		
	});
	
	//司机电话验证
	$("input[driverTel='driverTel']").each(function(){  
		//regValidate(this.id,validateJson.MobileOrTell.reg,validateJson.MobileOrTell.tip); 
		regValidate(this.id,validateJson.MobileOrTell.reg,validateJson.isNotNull.tip); 
	});
	
	//车牌号验证
	$("input[carNum='carNum']").each(function(){  
		//regValidate(this.id,validateJson.isCarNum.reg,validateJson.isCarNum.tip); 		
		regValidate(this.id,validateJson.isCarNum.reg,validateJson.isNotNull.tip); 		
	});
	
	//座位数验证
	$("input[carSeat='carSeat']").each(function(){  
		//regValidate(this.id,validateJson.Intege.reg,validateJson.Intege.tip); 
		regValidate(this.id,validateJson.Intege.reg,validateJson.isNotNull.tip); 
	});	
}

//一个form表单提交验证
function submitBtnClick()
{
	//一个form表单提交验证
	$('#submitBtn').click( function () {
		submitValidate();
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;
		if(failValidateCount == 0)
		{
			parent.popLodingPage(true);
		   	$("#scheduleForm").ajaxSubmit({
				type : 'post',
				success : function(data) {
					parent.popLodingPage(false);
					if (data == "success") {
						parent.showRturnTip("调度成功","true");
						parent.openLeftMenu("../businessBidding/getBCAllLineList.action");
					}else if("error"==data){
						parent.showRturnTip("调度失败","false");
						
					}else if("overtime"==data){
						//需要先判断报价信息是否结束，结束则不保存，提示报价结束；否则保存
						parent.showPopCommonPage2("当前时间已超过线路发车时间，不能对其进行调度！","true","remind");
					}
				}
			});
			//验证提交 防止多次提交
			setSubmitDisale(false);	
		}
		else
		{
			//验证提交 防止多次提交
			setSubmitDisale(true);
		}
	});
}

//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}

//不为空，没有验证规则字段的 验证方法 ，多个id用冒号:隔开
function isNotNullValidate(ids)
{
	var idArr = "";
	if(ids != "")
	{
		idArr = ids.split(":");
		for(i=0;i<idArr.length;i++)
		{
			var valueText = $("#"+idArr[i]).val();
		   	//判断是否为空
			if("" == valueText || valueText.trim() == "")
			{
			    createErrorTip(idArr[i],validateJson.isNotNull.tip);
			}
			else
			{
				validateUserDefinedSucc(idArr[i]);
			}
		}
	}
}

//数字校验，只可以输入数字
function replaceNum(obj){
	return obj.value=obj.value.replace(/\D/g,'');
}

//不为空，有验证规则字段的验证方法
function regValidate(id,reg,tip)
{
	var thisRegs = new RegExp(reg);
	var valueText = $("#"+id).val();
	//手机和固定座机电话一起校验
	if(reg == validateJson.MobileOrTell.reg)
	{
		var isMobile = new RegExp(validateJson.Phone.reg);
		var isTell = new RegExp(validateJson.Tell.reg);
		if(typeof(valueText) != "undefined")
		{
			if(isMobile.test(valueText) || isTell.test(valueText))
			{
				validateUserDefinedSucc(id);
			}
			else
			{
				createErrorTip(id,tip);
			}	
		}
		else
		{
			createErrorTip(id,tip);
		}
	}
	else
	{
		if(typeof(valueText) != "undefined")
		{
			if(thisRegs.test(valueText))
			{
				validateUserDefinedSucc(id);
			}
			else
			{
				createErrorTip(id,tip);
			}	
		}
		else
		{
			createErrorTip(id,tip);
		}
	}
}

//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
	//同行中存在其他的错误提示语句，先隐藏其他的提示语句，然后再去创建新的提示语句
	$("#"+id).parents("li").find(".tipTable:visible").hide();
	$("#"+id+"ErrTip").remove();
	$("#"+id).parent().append('<span id="'+id+'ErrTip" class="tipTable display-ib"><span class="error mt5 ml4"></span><span class="red2" id="'+id+'ErrTipText"></span></span>');
   	$("#"+id+"ErrTipText").text(tip);
	//增加错误框input框颜色
	$("#"+id).css("background","#fcf0ee");
}

//自定义验证通过
function validateUserDefinedSucc(id)
{
	$("#"+id+"ErrTip").remove();
	//同行中存在其他错误提示语句，先将之前隐藏的提示语句展示出来，然后再去删除当前展示的这条错误提示语句
	var hiddenLength = 0;
	$("#"+id).parents("li").find(".tipTable:hidden").each(function(){
		hiddenLength++;
	});
	if(hiddenLength > 0)
	{
		$("#"+id).parents("li").find(".tipTable:visible").hide();
		$("#"+id).parents("li").find(".tipTable:hidden").eq(0).show();
	}
    //还原错误框input框颜色
	$("#"+id).css("background","#f6f5f5");
    setSubmitDisale(false);
}

//清除自定义验证提示语句
function validateUserDefineClear(id)
{
	$("#"+id+"ErrTip").remove();
     //还原错误框input框颜色
	$("#"+id).css("background","#f6f5f5");
    setSubmitDisale(false);
}

//验证提交 防止多次提交  flag 为true表示按钮不可点击，false表示按钮可点击
function setSubmitDisale(flag)
{
   	 $("#submitBtn").attr("disabled", flag);
}

//清空input框的值
function clearValue(){
	              
	setSubmitDisale(false);
	$('input:text,input:password,input:file,textarea').each(function(){ 
		$(this).val("");
		$("#"+this.id+"Tip").hide(); 
		$("#"+this.id+"ErrTip").hide();
		$("#"+this.id).css("background","#f6f5f5");
	});
	$('select').each(function(){ 
		$("#"+this.id).find("option").eq(0).attr("selected",true);
		$("#"+this.id+"Tip").hide(); 
		$("#"+this.id+"ErrTip").hide();
		$("#"+this.id).css("background","#f6f5f5");
	});
}
</script>