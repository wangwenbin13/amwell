<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../resource.jsp"/>
<%
    String path1 = request.getContextPath();
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠券管理-增加优惠券</title>
<style>
    .errorTip{
    	float:left;
        display:none;
        color:#d1261e;
        background:#f7e7e9;
        margin-left:10px;
        padding:3px 5px;
    }
</style>
</head>
<body>
<s:form id="addForm" theme="simple">
<div class="pop black1" id="popMapPage" style="width:440px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">增加优惠券</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopMerchantPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main clearfix p10">
        <ul class="pop-lilist mt15">
        	<li class="clearfix"><span class="fl w90 t-r">优惠券名：</span><s:textfield id="couponName" name="couponInfo.couponName" cssClass="fl r-input"></s:textfield><span id="couponNameError" class="errorTip"></span></li>
        	<li class="clearfix"><span class="fl w90 t-r">面值：</span><s:textfield id="fullMoney" name="couponInfo.couponValue" cssClass="fl r-input"></s:textfield><span id="fullMoneyError" class="errorTip"></span></li>
        	<li class="clearfix"><span class="fl w90 t-r">门槛：</span><s:textfield id="getMoney" name="couponInfo.couponCon" value="0.00" cssClass="fl r-input"></s:textfield><span id="getMoneyError" class="errorTip"></span></li>
        	<li class="clearfix"><span class="fl w90 t-r">后延天数：</span><s:textfield id="getDay" name="couponInfo.delayDays" cssClass="fl r-input"></s:textfield><span id="getDayError" class="errorTip"></span></li>
        	<li class="clearfix"><span class="fl w90"></span>
        	<input type="button" value="添&nbsp;加" id="submitButton" class="display-ib btn1 white mr40 f14">
        	<a href="javascript:void(0);" class="display-ib btn1 white f14" onclick="resetValue();">重&nbsp;置</a>
        	</li>
        </ul>
    </div>
</div>
</s:form>
</body>
</html>
<script type="text/javascript">
var money_regx = new RegExp("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
var day_regx = new RegExp("^[0-9]\\d*$");
$(function(){
	
	validateFunction();
});
//关闭增加弹出层页面
function closePopMerchantPage()
{
    $("#popMapPage").hide();
    $("#mainBody").hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}
//重置
function resetValue(){
	$("#couponName").val("");
	$("#fullMoney").val("");
	$("#getMoney").val("0.00");
	$("#getDay").val("");
	//清除所有的验证语句
	validateUserDefineClear("couponName");
	validateUserDefineClear("fullMoney");
	validateUserDefineClear("getMoney");
	validateUserDefineClear("getDay");
	setSubmitDisales(false);
}
//验证方法
function validateFunction()
{
	//一个form表单提交验证
	$('#submitButton').click( function () {
		if($("#couponName").val()==null||$("#couponName").val()==""){
			$("#couponNameError").show();
	        $("#couponNameError").html("优惠券名不能为空");
		}
		if($("#fullMoney").val()==null||$("#fullMoney").val()==""){
			$("#fullMoneyError").show();
	        $("#fullMoneyError").html("面值不能为空");
		}else if(!money_regx.test($("#fullMoney").val())){
			$("#fullMoneyError").show();
	    	$("#fullMoneyError").html("面值价格格式不正确");
	    }
		if($("#getMoney").val()==null||$("#getMoney").val()==""){
			$("#getMoneyError").show();
	        $("#getMoneyError").html("门槛不能为空");
		}else if(!money_regx.test($("#getMoney").val())){
			$("#getMoneyError").show();
	    	$("#getMoneyError").html("门槛格式不正确");
	    }
		if($("#getDay").val()==null||$("#getDay").val()==""){
			$("#getDayError").show();
	        $("#getDayError").html("后延天数不能为空");
		}else if(!day_regx.test($("#getDay").val())){
			$("#getDayError").show();
	    	$("#getDayError").html("后延天数格式不正确");
	    }
		
		 getthis.each(function(){
			var obj = this;
			checkByType(obj);
		});
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".errorTip:visible").length;

		if(failValidateCount == 0)
		{
			//验证提交 防止多次提交
			setSubmitDisales(false);
			
			adminFormSubmit("addForm");
			return false;
		}
		else
		{
			//验证提交 防止多次提交
			setSubmitDisales(true);
		    return false;
		}
		
	});
	
	
}
function setSubmitDisales(flag)
{
     if(flag)
	 {
		$('#submitButton').removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $('#submitButton').removeClass("btn1-gray").addClass("btn1");
	 } 
	 $('#submitButton').attr("disabled", flag);
}
//清除自定义验证提示语句
function validateUserDefineClear(id)
{
    $("#"+id+"Error").hide();
}

function adminFormSubmit(submitForm){
	
	$("#"+submitForm).ajaxSubmit({
		type : 'post',
		data:{},
		url : "../couponInfo/couponAdd.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"text",
		success : function(data) {
			if(1 == data){
				parent.parent.showRturnTip("添加成功",true);
				closePopMerchantPage();
				window.location.href='../couponInfo/couponInfoList.action';
			}
			if(-1 == data){
				parent.parent.showRturnTip("优惠券名称已存在",false);
			}
			if(0 == data){
				parent.parent.showRturnTip("添加失败",false);
			}
		}
	});
}

</script>
