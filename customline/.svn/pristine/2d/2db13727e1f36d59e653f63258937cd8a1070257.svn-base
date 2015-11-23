<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户投诉-用户协议</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;客户投诉&nbsp;>&nbsp;用户协议</p></div>
<s:form action="" method="post" id="userAgreement" theme="simple">
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">用户协议</a></li>
        </ul>
    </div>
    <div class="sh-add-new feedbackLi" >
    	<ul class="mt20 clearfix gray2 f12">

            <li><div><span class="fl w65 t-r">类型：</span>
           
	            <input  type="radio" class="checkbox mr4" name="agreementType"  <s:if test="%{userAgreements.size ==0}">disabled="disabled"</s:if> value="1" <s:if test="%{userAgreements.size>0}">checked="checked" onclick="useExistUserAgreement()"</s:if>/><span class="w110 display-ib t-l">使用已有的用户协议</span>
	            <s:if test="%{userAgreements.size ==0}">
	           		 <s:select name="userAgreementVo.agreementId" disabled="true"  list="userAgreements" cssClass="p3 minWidthSel" id="typeSelect" listKey="agreementId" listValue="agreementTitle"  ></s:select>
	            </s:if>
	             <s:if test="%{userAgreements.size >0}">
	           		 <s:select  name="userAgreementVo.agreementId" list="userAgreements" cssClass="p3" id="agreementId" listKey="agreementId" listValue="agreementTitle" onchange="useExistUserAgreement()" value="userAgreementVo.agreementId" ></s:select>
				</s:if>

			</div>
			<div class="mt10"><span class="fl w65 h24"></span>
				<input type="radio" class="checkbox mr4" name="agreementType" onclick="createNewUserAgreement()" value="2"  <s:if test="%{userAgreements.size ==0}">checked="checked"</s:if>/><span class="w110 display-ib t-l">创建新的用户协议</span>
				<input type="text" class="r-input w132" name="userAgreementVo.agreementTitle" maxlength="20" id="agreementTitlenew"/> 
			</div>
			</li>
            <li style="width:90%"><span class="fl w65 t-r">内容：</span>
                <s:textarea  cssClass="r-input more-show w87p" name="userAgreementVo.agreementContent" id="content" cssStyle="height:350px;" value="%{userAgreementVo.agreementContent}"></s:textarea>
            	<div><span class="fl w65 t-r">&nbsp;</span><span id="context" class="fl"></span></div>
            </li>
            <li class="t-c mt20"><input type="button" id="addBtn1" value="保&nbsp;存" class="display-ib btn1 white mr40 f14" onclick="validateFunction()"/></li>
       </ul>
    </div>
</div>
</s:form>
</body>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	$(".sh-add-new").css("height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("height",$(window).height()-130+"px");	
	})
	
	clearInputDefaultVal();

});
//创建文字编辑器
var editor;
KindEditor.ready(function(K) {
	var options = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons','link'],
		afterBlur:function(){
			//this.count('text');
			var length = this.count('text');
			if(length == 0)
			{
				createErrorTip("context","协议内容不能为空");
			}
		},
		afterFocus:function(){
			validateUserDefineClear("context");
		}
	}
	editor = K.create("#content",options);
	
	
});

//取得编辑器文字的内容
function addUserAgreement(){
	
	var $theText=editor.html();//带换行、空格等标签的  .text()取的是纯文本
	
	/**
	if(''==$.trim(editor.text())){
		parent.showRturnTip("内容不能为空！",false);
		return false;
	}
	*/
	
	$("#content").text($theText);
	
	$("#userAgreement").ajaxSubmit({
		type : 'post',
		data:{},
		url : "../userAgreement/addUserAgreement.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"html",
		success : function(data) {
			if("success" == data){
				parent.showRturnTip("保存成功",true);
				window.location.href="../userAgreement/toList.action?random="+Math.floor(Math.random()*10000+1);
			}
			
			if("fail" == data){
				parent.showRturnTip("保存失败",false);
			}
		}
	});
}



// 创建新协议按钮
function createNewUserAgreement(){
	// 清空协议内容框
	editor.text("");
}

// 使用已有的用户协议
function useExistUserAgreement(){
	// 获取用户协议ID
	var agreementId = $("#agreementId").val();
	
	
	//通过协议ID获取协议内容
	$.ajax({
		url:'../userAgreement/queryUserAgreement.action',	
		cache:false,
		type: 'POST',	
		async : false,
		data:{"userAgreementVo.agreementId":agreementId},
		success:function(data){
			
			if("" != data){
				editor.html(data);
			}
		}
	});
	
}
function clearInputDefaultVal(){
	
	$("#agreementTitlenew").each(function(){
		$(this).focus(function(){
			// 清除提示语句
			validateUserDefineClear("agreementTitle");
			 //验证提交 防止多次提交
			setSubmitDisales(false);
		}).blur(function(){
			// 验证协议标题名称是否重复
			agreementNameIsExist();
			//去除左右两端的空格
			setSubmitDisales(true);
		});
	});
	
	$("#context").each(function(){
		$(this).focus(function(){
			// 清除提示语句
			validateUserDefineClear("context");
			 //验证提交 防止多次提交
			setSubmitDisales(false);
		}).blur(function(){
			// 协议内容不能为空
			createErrorTip("context","协议内容不能为空");
			//去除左右两端的空格
			setSubmitDisales(true);
		});
	});
	
}



// 验证开始
function agreementNameIsExist(){
	
	var agreementType = $("[name='agreementType']:checked").val();
	//alert("agreementType:"+agreementType);
	// 是新增
	if(agreementType=="2"){
		//alert(1);
		// 新增标题
		var agreementTitle = $("#agreementTitlenew").val();
	
		if("" == agreementTitle.trim()){
			//alert(2);
			createErrorTip("agreementTitlenew","协议名称不能为空");
			return;
		}
		$.ajax({
			url:'../userAgreement/userAgreementTitleIsExist.action',	
			cache:false,
			type: 'POST',	
			async : false,
			data:{"userAgreementVo.agreementTitle":agreementTitle},
			success:function(data){
				
				if("yes" == data){
					createErrorTip("agreementTitlenew","协议名称已存在");
					return;
				}
				if("no" == data){
					validateUserDefinedSucc("agreementTitlenew");
					return;
				}
			}
		});
	}
}

//验证协议内容不能为空
function agreementContentIsEmpty(){
	var context = editor.text();
	if("" ==  context){
		createErrorTip("context","协议内容不能为空");
		return;
	}
}
function validateFunction(){
	
	//如果是新增,验证该协议名称是否已经存在
	agreementNameIsExist();
	
	//验证内容协议内容是否为空
	agreementContentIsEmpty();
	
	//验证未通过的计数
    var failValidateCount = $(document).find(".tipTable:visible").length;
	
    if(failValidateCount == 0)
	{
		addUserAgreement();
	    //验证提交 防止多次提交,true表示将按钮高亮
		setSubmitDisales(true);
	}
	else
	{
		//验证提交 防止多次提交
		setSubmitDisales(true);
	    return false;
	}
}
//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
	
	$("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id+"ErrTip").remove();
	
	if($("#"+id).parent().is("div"))
	{
		
		$("#"+id).parent().append("<span id='"+id+"ErrTip' class='tipTable'></span>");
		$("#"+id+"ErrTip").css("margin-left","7px");
	}
	else
	{
	    $("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	    $("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
		$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
	}
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
}
//清除自定义验证提示语句
function validateUserDefineClear(id)
{
    $("#"+id+"ErrTip").hide();
    $("#"+id).parent().find(".onCorrect").remove();
}
//自定义验证通过
function validateUserDefinedSucc(id)
{
	$("#"+id).parents("li").find(".onCorrect").remove();
    if($("#"+id).parent().is("div"))
    {
    	$("#"+id).parent().append("<span id='"+id+"Tip'></span>");
    }
    else
    {
    	 $("#"+id).parents("li").append("<span id='"+id+"Tip'></span>");
    }
    $("#"+id+"Tip").addClass("onCorrect");
	$("#"+id+"ErrTip").hide();
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

// 验证结束


</script>
</html>
