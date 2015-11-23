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
<title>优惠券管理-增加组合券-选择优惠券弹窗</title>
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
<div class="pop black1" id="popMapPage" style="width:660px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">选择优惠券</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopMerchantPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main clearfix p10">
        <div class="table2-text sh-ttext">  
	      	<table width="100%" border="0" class="table1" id="tableDiv">
	      		<tr align="center" class="th">
	               <th scope="col" width="6%"></th>
	               <th scope="col" width="10%">id</th>
	               <th scope="col">优惠券名</th>
	               <th scope="col" width="10%">面值</th>
	               <th scope="col" width="10%">门槛</th>
	               <th scope="col" width="10%">后延天数</th>
	               <th scope="col" width="15%">配置时间</th>
	            </tr>
	            <tr align="center" class="bg1">
	            	<td><input type="checkbox" class="checkbox"/></td>
	            	<td>1</td>
	            	<td>1</td>
	            	<td>1</td>
	            	<td>1</td>
	            	<td>1</td>
	            	<td>2015-15-15</td>
	            </tr>
	      	</table>   
      </div>
      <ul class="mt20">
    		<li class="mb10">10元优惠券，数量<input type="text" class="r-input w45 mr4 ml4 t-c"/>张</li>
    		<li class="mb10">10元优惠券，数量<input type="text" class="r-input w45 mr4 ml4 t-c"/>张</li>
    	</ul>
    	<p class="mt20 t-c"><input type="button" value="确&nbsp;定"  class="display-ib btn1 white mr40 f14">
        		<a href="javascript:void(0);" class="display-ib btn1 white f14" onclick="resetValue();">重&nbsp;置</a></p>
    </div>
</div>
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
	$("#getMoney").val("");
	$("#getDay").val("");
	//清除所有的验证语句
	validateUserDefineClear("couponName");
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
	    var failValidateCount = $(document).find(".tipTable:visible").length;

		if(failValidateCount == 0)
		{
			//验证提交 防止多次提交
			setSubmitDisales(false);
			
			//adminFormSubmit("adminForm");
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
//自定义验证通过
function validateUserDefinedSucc(id)
{
    $("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id).parent().append("<span id='"+id+"Tip'></span>");
    $("#"+id+"Tip").addClass('onCorrect');
	$("#"+id+"ErrTip").hide();
}

//清除自定义验证提示语句
function validateUserDefineClear(id)
{
    $("#"+id+"ErrTip").hide();
    $("#"+id).parent().find(".onCorrect").remove();
}

</script>
