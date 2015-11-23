<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销管理-礼品配置</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;礼品配置<span class="blue1 ml25"></span></p></div>
<s:form action="../gift/addGiftPage.action" method="post" id="addForm" theme="simple">
  <div class="mt10 ml20 mr20 black1">
    <div class="table-title">
       <ul>
       	<li class="on"><a href="javascript:void(0)" class="f14 blue2">礼品配置</a></li>
       </ul>
    </div>
    <div class="sys-text sh-add-new">
    	<ul class="mt10 clearfix gray2 f12">
    		<li><span class="fl w90 t-r">礼品ID：</span><input type="text" class="fl r-input" id="giftId" name="giftId"   readonly="readonly" value="${giftId }"/></li>
    		<li><span class="fl w90 t-r">礼品名称：<em class="red1">*</em></span><input type="text" class="fl r-input" id="giftName" name="giftName" maxlength="16"/>（字数限制为：16个）</li>
    		<li><span class="fl w90 t-r">礼品类型：<em class="red1">*</em></span>
    			<select class="p3" id="giftSelect" name="giftSelect">
    				<option value="">请选择礼品类型</option>
    				<option value="1">通用型礼品</option>
    				<option value="2">上下班专车礼品</option>
    				<option value="3">包车礼品</option>
    				<option value="4">拼车礼品</option>
    				<option value="5">推荐有奖礼品</option>
    			</select>
    		</li>
    		<li><span class="fl w90 t-r">礼品面值：<em class="red1">*</em></span><input type="text" class="fl r-input mr5" id="giftPrice" name="giftPrice" onkeyup="replaceNumPositiveInteger(this);" onafterpaste="replaceNumPositiveInteger(this);" onblur="checkCount();" maxlength="5"/>元</li>
    		<li><span class="fl w90 t-r">约束条件：</span><input type="text" class="fl r-input mr5" id="giftConstraint" name="giftConstraint" onkeyup="replaceNumInteger(this);" onafterpaste="replaceNumInteger(this);" maxlength="5"/>元</li>
    	</ul>
    	<p class="mt20"><input type="button" id="submitButton" class="display-ib btn1 white mr40 ml90" onclick="judgeForm()" value="提交"/>
			<input type="reset"  value="重置" class="display-ib btn1 white" onclick="resetValue();"/></p>
   	 </div>
	</div>
</s:form>
</body>
</html>
<script type="text/javascript">
//正整数，不可以输0
function replaceNumPositiveInteger(obj)
{
	if(obj.value.length==1){obj.value=obj.value.replace(/[^1-9]/g,'')}else{obj.value=obj.value.replace(/\D/g,'')}; 
	return obj.value;
}

//可以输0
function replaceNumInteger(obj)
{
	return obj.value=obj.value.replace(/\D/g,'');
}
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}


//重置
function resetValue(){
	$("#giftName").val("");
	$("#giftPrice").val("");
	$("#giftConstraint").val("");
	$("#giftSelect option:first").attr("selected",true);
	//清除所有的验证语句
	validateUserDefineClear("giftName");
	validateUserDefineClear("giftPrice");
	validateUserDefineClear("giftConstraint");
	validateUserDefineClear("giftSelect");
	//验证提交 防止多次提交
	setSubmitDisale(false);
}

$(function(){

	clearInputValidateDefaultVal();
})
//清除input框默认值 
function clearInputValidateDefaultVal()
{
	$('input:text,select').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			$(this).removeClass("gray3").addClass("gray2");
			if(txt == $(this).val()){
				
			} 
			setSubmitDisale(false);
		}).blur(function(){  
			if($(this).val() == ""){
			    $(this).removeClass("gray2").addClass("gray3");
			}
		});  
	});
	$("#giftSelect").focus(function(){   //礼品类型
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	}); 

	//$("#giftName,#giftPrice,#giftConstraint").focus(function(){
	$("#giftName,#giftPrice").focus(function(){
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){
		isNotNullValidate(this.id);
	});
}


function judgeForm(){
	selectValidate("giftSelect"); //礼品类型
	//isNotNullValidate("giftName:giftPrice:giftConstraint");
	isNotNullValidate("giftName:giftPrice");

	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0)
	{
		//验证提交 防止多次提交
		setSubmitDisale(false);
		
		//此处写验证成功后提交后台的方法
		 $("#addForm").ajaxSubmit({
			type : 'post',
			dataType:"json",
			success : function(data) {
			if (data=="success") {
			    parent.parent.showRturnTip("礼品配置成功!",true);
			    $("#iframe_right",parent.parent.window.document).attr("src","../gift/forwardGiftListPage.action");
			}else if("error"==data){
				parent.parent.showRturnTip("礼品配置失败，或者名称相重!",false);
			}
		}
		});
		return true;
	}
	else
	{
		//验证提交 防止多次提交
		setSubmitDisale(true);
	    return false;
	}
}
//下拉框验证
function selectValidate(id)
{
	var selectIndex = $("#"+id).find("option:selected").index();
  if(selectIndex == 0)
  {
      createErrorTip(id,validateJson.Select.tip);
  }
  else
  {
       validateUserDefinedSucc(id);
  }
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
//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
    $("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
	var left = $("#"+id).offset().left-$("#"+id).parents().offset().left;
	if(left < 0)
	{
		left = $("#"+id).parent("div").offset().left-$("#"+id).parents().offset().left;
	}
	$("#"+id+"ErrTip").css("margin-left",left+"px");
	if(id == "giftSelect")
	{
		$("#"+id+"ErrTip").css("margin-top","0");
	}
	else
	{
		$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
	}
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

//礼品面值验证
function checkCount(){
	var giftPrice = $("#giftPrice").val();
	if(giftPrice<1){
		$("#giftPrice").val("");
	}
}
$(function(){
	$(".sys-text").css("min-height",($(window).height()-116+"px"));
	$(window).resize(function(){
		$(".sys-text").css("min-height",($(window).height()-116+"px"));
	})
})
</script>