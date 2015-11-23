<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-修改密码</title>
<%@include file="../resource.jsp" %>
</head>

<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;修改密码</p></div>
<div class="mt15 ml20 mr20 black1">
    <div class="sys-text sys-text1 sh-add">
        <form action="" method="post" id="updatepassword">
        <ul class="clearfix mt20 ml30">
            <li><span class="w95 t-r fl">请输入旧密码：<em class="red1 mr4">*</em></span><input name="oldpassword" type="password" class="r-input w38p fl" id="oldpassword" autocomplete="off"/></li>
            <li><span class="w95 t-r fl">请输入新密码：<em class="red1 mr4">*</em></span><input name="newpassword" type="password" class="r-input w38p fl" id="newpassword" check="5" autocomplete="off"/></li>
            <li><span class="w95 t-r fl">确认新密码：<em class="red1 mr4">*</em></span><input name="repassword" type="password" class="r-input w38p  fl" id="repassword" check="5" autocomplete="off"/></li>
        </ul>
         <p class="mt20 ml30"><span class="w95 t-r fl"></span>
         <input type="button" class="display-ib btn1 white mr40" value="确认更改" id="submitBtn"/>
         <input type="button" class="display-ib btn1 white" href="javascript:void(0);" onclick="clearValue();" value="取消"/></p>
         </form>
    </div>
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
$(function(){
	//浏览器可视窗口的的高度
	$(".sys-text").css("height",($(window).height()-120+"px"));
	$(window).resize(function() {
		$(".sys-text").css("height",($(window).height()-120+"px"));
	});  
	//旧密码
	$("#oldpassword").focus(function(){  
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		passwordIsRight();
	});
});

//验证所有密码
function validataAllPass()
{
	
	var newpwd1 = $("#newpassword").val().trim();
	
	$.ajax({
		url:'../changePassWord/updateUserPassWord.action?newpwd1='+newpwd1,		
		cache:false,	
		dataType:"html",	
		success:function(data){
			if(data=='no')
			{
				validateUserDefinedSucc("oldpassword");
			}
			else if(data=="yes"){
				parent.parent.showRturnTip("修改成功，请牢记您的新密码！",true);
				clearValue();
			}	
		}
				
	});
}

//重置
function clearValue(){
	              
	setSubmitDisale(false);
	$('input:text,input:password').each(function(){ 
		$(this).val("");
		$("#"+this.id+"Tip").hide(); 
		$("#"+this.id+"ErrTip").hide();
	});
}

//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
	$("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id+"ErrTip").remove();
	if($("#"+id).parent().is("span"))
	{
		$("#"+id).parent().after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	}
	else
	{
	    $("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	}
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
	$("#"+id+"ErrTip").css("margin-top",$("#"+id).height());
	$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
}

//自定义验证通过
function validateUserDefinedSucc(id)
{
    $("#"+id).parents("li").find(".onCorrect").remove();
    $("#"+id).parents("li").append("<span id='"+id+"Tip'></span>");
    $("#"+id+"Tip").addClass("onCorrect");
	$("#"+id+"ErrTip").hide();
}

//清除自定义验证提示语句
function validateUserDefineClear(id)
{
    $("#"+id+"ErrTip").hide();
    $("#"+id).parents("li").find(".onCorrect").remove();
    setSubmitDisale(false);
}

//验证旧密码是否正确
function passwordIsRight(){
	var oldPwd = $("#oldpassword").val().trim();
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
					if("yes" == data){
						//说明密码正确
						validateUserDefinedSucc("oldpassword");
					}
					else if("no" == data){
						//说明密码错误
						createErrorTip("oldpassword","旧密码不正确");
					}
					else if("invalid" == data){
						//session过期
						window.top.location.href='login';
					}	
					else if("error" == data){
						//系统错误，一般不会显示
						parent.parent.showRturnTip("系统错误！",false);
					}		
				}
			}
		}
	});
}

//验证提交 防止多次提交  flag 为true表示按钮不可点击，false表示按钮可点击
function setSubmitDisale(flag)
{
   	 if(flag)
	 {
		$("#submitBtn").removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $("#submitBtn").removeClass("btn1-gray").addClass("btn1");
	 } 
   	 $("#submitBtn").attr("disabled", flag);
}

//输入空格提示语句
function inputSpaceErrorStatement(id){

	
	createErrorTip(id,"密码不能为空格");
}
</script>
