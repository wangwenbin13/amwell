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
<title>营销管理-推荐有奖设置</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;推荐有奖设置</p></div>
<s:form action="" method="post" id="userAgreement" theme="simple">
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">推荐有奖设置</a></li>
        </ul>
    </div>
    <div class="sh-add-new feedbackLi" >
    	<ul class="mt20 clearfix gray2 f12">
            <li id="recommendAwardSwitch"><div>
            <span class="fl w110 t-r">推荐有奖开关：</span>
            <s:if test="%{recommendAwardSet.recommendAwardSwitch == 1}">
            <input type="radio" name="recommendAwardSet.recommendAwardSwitch" value="1" class="checkbox" checked="checked"/>开
            <input type="radio" name="recommendAwardSet.recommendAwardSwitch" value="0" class="checkbox"/>关
            </s:if>
            <s:if test="%{recommendAwardSet.recommendAwardSwitch == 0}">
            <input type="radio" name="recommendAwardSet.recommendAwardSwitch" value="1" class="checkbox"/>开
            <input type="radio" name="recommendAwardSet.recommendAwardSwitch" value="0" class="checkbox" checked="checked"/>关
            </s:if>
<%--            <s:radio list="#{'1':'开','0':'关'}" name="recommendAwardSet.recommendAwardSwitch" cssClass="checkbox" ></s:radio>--%>
            </div></li>
            <li><div>
            <span class="fl w126 t-r">拉新用户优惠券批次号：</span>
            <s:textfield name="recommendAwardSet.newUserGiftId" cssClass="r-input" id="newUserGiftId"></s:textfield>
            </div></li>
            <li><div>
            <span class="fl w126 t-r">老用户优惠券批次号：</span>
            <s:textfield name="recommendAwardSet.oldUserGiftId" cssClass="r-input" id="oldUserGiftId"></s:textfield>
            </div></li>
            <li style="width:90%" id="actionRule"><span class="fl w110 t-r">推荐有奖活动规则：</span>
                <s:textarea  cssClass="r-input more-show w87p" name="recommendAwardSet.actionRule" id="content" cssStyle="height:350px;" value="%{recommendAwardSet.actionRule}"></s:textarea>
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
				createErrorTip("context","活动规则不能为空");
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
	
	$("#content").text($theText);
	
	$("#userAgreement").ajaxSubmit({
		type : 'post',
		data:{},
		url : "../recommendAward/addRecommendAwardSet.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"html",
		success : function(data) {
			if(1 == data){
				parent.showRturnTip("保存成功",true);
				window.location.href="../recommendAward/forwardSetPage.action?random="+Math.floor(Math.random()*10000+1);
			}
			if(0 == data){
				parent.showRturnTip("保存失败",false);
			}
			if(-1 == data){
				parent.showRturnTip("输入的老用户礼品ID不是推荐有奖礼品",false);
			}
			if(-2 == data){
				parent.showRturnTip("输入的拉新用户礼品ID不是推荐有奖礼品",false);
			}
			if(-3 == data){
				parent.showRturnTip("输入的礼品Id不存在",false);
			}
		}
	});
}

function clearInputDefaultVal(){
	$("#context").each(function(){
		$(this).focus(function(){
			// 清除提示语句
			validateUserDefineClear("context");
			 //验证提交 防止多次提交
			setSubmitDisales(false);
		}).blur(function(){
			// 活动规则不能为空
			createErrorTip("context","活动规则不能为空");
			//去除左右两端的空格
			setSubmitDisales(true);
		});
	});
	
}

//验证活动规则不能为空
function agreementContentIsEmpty(){
	var $newUserGiftId=$("#newUserGiftId").val();
	if("" == $newUserGiftId){
		createErrorTip("context","拉新用户礼品ID不能为空");
		return;
	}
	var $oldUserGiftId=$("#oldUserGiftId").val();
	if("" == $oldUserGiftId){
		createErrorTip("context","老用户礼品ID不能为空");
		return;
	}
	var context = editor.text();
	if("" ==  context){
		createErrorTip("context","活动规则不能为空");
		return;
	}
}
function validateFunction(){
	
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

$(function(){
	$(".sh-add-new").css("min-height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("min-height",$(window).height()-130+"px");	
	})
	
	clearInputDefaultVal();

	$("#recommendAwardSwitch").find("input").click(function(){
		changeReadonly($(this).val());
	});
	$("#recommendAwardSwitch").find("input").each(function(){
		if($(this).attr("checked")){
			changeReadonly($(this).val());
		}
	});
});

function changeReadonly(theVal){
	if(theVal=='0'){//选择关，则置灰输入框
        $("#newUserGiftId").attr("readonly",true);
        $("#oldUserGiftId").attr("readonly",true);
        editor.readonly();
    }
    else{//选择开，则不置灰输入框
   	 $("#newUserGiftId").attr("readonly",false);
        $("#oldUserGiftId").attr("readonly",false);
        editor.readonly(false);
    }
}
</script>
</html>
