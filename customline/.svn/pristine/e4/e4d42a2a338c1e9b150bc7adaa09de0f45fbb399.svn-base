<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新用户特权设置</title>
<%@include file="../resource.jsp" %>
</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;用户管理&nbsp;>&nbsp;新用户特权设置<span class="blue1 ml25"></span></p></div>
 <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">新用户特权设置</a></li>
                </ul>
            </div>
<form id="addForm" action="../sysDiscount/updateSysDiscount.action" method="post">
<input type="hidden" name="id" value="${discountVo.id}"/>
<div class="table2-text">
 	  <div class="sh-add clearfix">
  		<ul>
  			<li class="widthfull"><span class="w100 fl t-r">活动价格：<em class="red1 mr4">*</em></span><input type="text"  class="r-input w35p gray2"  maxlength="13" name="disprice" id="disprice" value="${discountVo.disprice}"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">活动次数：<em class="red1 mr4">*</em></span><input type="text"  class="r-input w35p gray2" maxlength="10"  name="distimes" id="distimes" value="${discountVo.distimes}" onkeyup="replaceNumPositiveInteger(this);" onafterpaste="replaceNumPositiveInteger(this);"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">是否有效：<em class="red1 mr4">*</em></span><input type="radio" class="checkbox"  name="iswork" value="1" <s:if test="%{discountVo.iswork==1}">checked="checked"</s:if>/>有效  &nbsp;&nbsp;<input type="radio" class="checkbox" name="iswork" value="2" <s:if test="%{discountVo.iswork==2}">checked="checked"</s:if>/>无效 </li>
  			<li class="widthfull" style="height:120px;"><span class="w100 fl t-r">备注：<em class="red1 mr4">*</em></span><textarea  class="r-input w35p gray2" style="height:100px;" name="remark" id="remark" onkeyup="if(this.value.length > 200) this.value=this.value.substr(0,200)">${discountVo.remark}</textarea></li>
  	  	</ul>
  	  </div>
  	<p class="t-c mt20"><input type="button" class="display-ib btn1 white mr40" value="保存" onclick="updateSysdiscount();"/></p>
</div>
</form>
</div>
</div>
<script type="text/javascript">
function updateSysdiscount(){
	regValidate("disprice","^((\-?[0-9]{1}\\d{0,9})|([0]{1}))(\\.(\\d){1,2})?$","最多可输入10位数字,保留两位小数");
	isNotNullValidate("distimes:remark");
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0)
	{
		//验证提交 防止多次提交
		setSubmitDisale(false);
		//此处写验证成功后提交后台的方法
		 $("#addForm").ajaxSubmit({
			type : 'post',
			success : function(data) {
				if (data == "success") {
				    parent.parent.showRturnTip("新用户特权修改成功!",true);
				}else if("error"==data){
					parent.parent.showRturnTip("新用户特权修改失败!",false);
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

$(function(){
	$("#disprice").focus(function(){  //价格
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidate(this.id,"^((\-?[0-9]{1}\\d{0,9})|([0]{1}))(\\.(\\d){1,2})?$","最多可输入10位数字,保留两位小数");
	});

	$("#distimes,#remark").focus(function(){
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){
		isNotNullValidate(this.id);
	});
})

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

function regValidate(id,reg,tip)
{
	var thisRegs = new RegExp(reg);
	var valueText = $("#"+id).val();
	if(typeof(valueText) != "undefined")
	{
		if(thisRegs.test(valueText))
		{
			validateUserDefinedSucc(id);
			return true;
		}else{
			createErrorTip(id,tip);
			return false;
		}
	}else{
		createErrorTip(id,tip);
		return false;
	}
}

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
	$("#"+id+"ErrTip").css("margin-top","0");
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
function validateUserDefineClear(ids)
{
  var idArr = "";
	if(ids != "")
	{
		idArr = ids.split(":");
		for(i=0;i<idArr.length;i++)
		{
			$("#"+idArr[i]+"ErrTip").hide();
			  $("#"+idArr[i]).parent().find(".onCorrect").remove();
		}
	}
}
</script>
</body>
</html>