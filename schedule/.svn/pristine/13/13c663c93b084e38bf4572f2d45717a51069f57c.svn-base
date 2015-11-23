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
<title>系统设置-修改密码</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;修改密码<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 mr28">
        <form action="" method="post" id="updatepassword">
        <p class="fw f14 f-yahei">修改密码</p>
        <ul class="clearfix mt40">
            <li class="clearfix"><span class="w75 t-l fl">旧&nbsp;密&nbsp;码：</span><input name="oldpassword" type="password" class="r-input w400 fl" id="oldpassword" autocomplete="off"/></li>
            <li class="clearfix mt20"><span class="w75 t-l fl">新&nbsp;密&nbsp;码：</span><input name="newpassword" type="password" class="r-input w400 fl" id="newpassword" check="5" autocomplete="off"/></li>
            <li class="clearfix mt20"><span class="w75 t-l fl">确认密码：</span><input name="repassword" type="password" class="r-input w400 fl" id="repassword" check="5" autocomplete="off"/></li>
        </ul>
         <p class="mt20"><span class="w75 t-l fl"></span>
	         <input type="button" class="display-ib btn1 white1 mr30 fw" value="保&nbsp;&nbsp;存" id="submitBtn"/>
	         <a class="display-ib btn1-no" href="javascript:void(0);" onclick="clearValue();">取&nbsp;消</a></p>
         </form>
</div>
</body>
</html>
<script type="text/javascript">
//密码确认
var passIdArr = "newpassword,repassword";
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();
	//验证方法
	validateFunction();
	//旧密码
	$("#oldpassword").focus(function(){  
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		passwordIsRight();
	});
});
//清除input框默认值 
function clearInputDefaultVal()
{
	
	$('input:password').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				$(this).val(""); 
			} 
			setSubmitDisale(false);
		}).blur(function(){  
			if($(this).val() == ""){
			    $(this).val(txt);  
			}
			checkPassWordIsLike();
		});  
	});
}

function checkPassWordIsLike()
{
	var oldPwd = $("#oldpassword").val().trim();
	var newpwd1 = $("#newpassword").val().trim();
	var newpwd2 = $("#repassword").val().trim();
	if((oldPwd == newpwd1 || oldPwd == newpwd2) && oldPwd != "")
	{
		if(newpwd1 != "" && newpwd2 == "")
		{
			createErrorTip("newpassword","新密码和旧密码不能一样");
		}
		else if(newpwd2 != "" && newpwd1 == "")
		{
			createErrorTip("repassword","新密码和旧密码不能一样");
		}
		else if(newpwd1 != "" && newpwd2 != "")
		{
			createErrorTip("newpassword","新密码和旧密码不能一样");
			createErrorTip("repassword","新密码和旧密码不能一样");
		}
	}
}
//验证方法
function validateFunction()
{
    //validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [{"validateName":"newpassword:repassword","validateReg":validateJson.Password.reg,"validateTip":validateJson.Password.tip}];//必填字段不为空判断

	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
	
	//一个form表单提交验证
	$('#submitBtn').click( function () {
		 getthis.each(function(){
			var obj = this;
			checkByType(obj);
		});
		passwordIsRight();
		checkPassWordIsLike();
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;
		if(failValidateCount == 0)
		{
			//验证提交 防止多次提交
			setSubmitDisale(false);
			validataAllPass();
			return false;
		}
		else
		{
			//验证提交 防止多次提交
			setSubmitDisale(true);
		    return false;
		}
	});
}
//验证所有密码
function validataAllPass()
{
	var newpwd1 = $("#newpassword").val().trim();
	
	$.ajax({
		url:'../changePassWord/updateUserPassWord.action?newpwd1='+newpwd1,	
		cache:false,	
		dataType:"html",	
		success:function(data){
			if(data=="yes"){
				parent.showRturnTip("修改成功","true");
				clearValue();
			}	
		}
				
	});
}

/*页面按键处理函数*/
function pressKey(evt) {
	//evt = evt || window.event; 
    //var keyCode = evt.keyCode || evt.which || evt.charCode;//支持IE、FF 
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		//屏蔽enter键
		evt.returnValue = false;
		break;
	default:
		break;
	}

}

var ua = window.navigator.userAgent;
//判断浏览器类型
if (ua.indexOf("MSIE") >= 1 || ua.indexOf("Chrome") >= 1){
	//禁止后退键  作用于IE、Chrome  
	document.onkeydown = pressKey;
}else if(ua.indexOf("Firefox") >= 1){
	//禁止后退键 作用于Firefox、Opera  
	document.onkeypress = pressKey;  
}else{
	document.onkeypress = pressKey;  
}
//重置
function clearValue(){
	              
	setSubmitDisale(false);
	$('input:text,input:password').each(function(){ 
		$(this).val("");
		$("#"+this.id+"Tip").hide(); 
		$("#"+this.id+"ErrTip").hide();
		$("#"+this.id).css("background","#f6f5f5");
	});
}
//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
	$("#"+id+"ErrTip").remove();
	$("#"+id).parent().append('<div id="'+id+'ErrTip" class="tipTable"><span class="error mt5 ml4"></span><span class="red2" id="'+id+'ErrTipText"></span></div>');
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTipText").text(tip);
	//增加错误框input框颜色
	$("#"+id).css("background","#fcf0ee");
}


//清除自定义验证提示语句
function validateUserDefineClear(id)
{
    //还原错误框input框颜色
	$("#"+id).css("background","#f6f5f5");
    $("#"+id+"ErrTip").hide();
    setSubmitDisale(false);
}

//验证旧密码是否正确
function passwordIsRight(){
	var oldPwd = $("#oldpassword").val().trim();
	var newpassword = $("#newpassword").val().trim();
	
	var password = $("#oldpassword").val();
	if(oldPwd == "")
	{
		createErrorTip("oldpassword","旧密码不能为空");
		return;
	}
	else if(password.indexOf(" ")!=-1){
		//说明密码错误，密码不能包含空格
		createErrorTip("oldpassword","旧密码不正确,不能包含空格");
		return;
	}

	$.ajax({
		url:'../changePassWord/oldPassWordIsRight.action?oldPwd='+password,		
		cache:false,	
		dataType:"text",	
		success:function(data){
			if(null != data){
				if("" != data){
					
					if("no" == data){
						//说明密码错误
						createErrorTip("oldpassword","旧密码不正确");
					}
					else if("invalid" == data){
						//session过期
						window.top.location.href='login';
					}	
					else if("error" == data){
						//系统错误，一般不会显示
						parent.showRturnTip("系统错误！","false");
					}		
				}
			}
		}
	});
}

//验证提交 防止多次提交  flag 为true表示按钮不可点击，false表示按钮可点击
function setSubmitDisale(flag)
{
   	 $("#submitBtn").attr("disabled", flag);
}

//输入空格提示语句
function inputSpaceErrorStatement(id){
	createErrorTip(id,"密码不能为空格");
}


</script>