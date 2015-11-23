<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-编辑管理员</title>
<% String path = request.getContextPath() + "/"; %>
<!-- 公共资源CSS,JS  -->
<!--Css -->
<!-- 收藏图标，浏览器图标 -->
<link rel="Shortcut Icon" href="<%=request.getContextPath()%>/images/favicon.ico"  type="image/x-icon" />
<link href="<%=path %>css/default.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>css/zTreeStyle.css" rel="stylesheet" type="text/css" />

<!-- ** jquery Javascript ** -->
<script src="<%=path %>js/jquery/jquery-1.8.2.min.js" type="text/javascript"></script>
<!-- ** jquery 解决window.onresize在ie上刷新多次，导致浏览器死机问题 ** -->
<script src="<%=path %>js/jquery/jquery.wresize.js" type="text/javascript"></script>
<!-- ** 时间控件  Javascript ** -->
<script src="<%=path %>js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<!-- ** 树形菜单Javascript ** -->
<script src="<%=path %>js/tree/jquery.ztree.core-3.4.js" type="text/javascript"></script>
<script src="<%=path %>js/tree/jquery.ztree.excheck-3.4.js" type="text/javascript"></script>

<!-- ** 验证Javascript ** -->
<script type="text/javascript" src="<%=path %>js/jquery/jquery-validator.js"></script>
<script type="text/javascript" src="<%=path %>js/config/validator-config.js"></script>
<script src="<%=path %>js/jquery/jquery.form.js" type="text/javascript"></script>
<!-- ** 首页左侧菜单js ** -->
<%@include file="../public.jsp" %>
</head>
<body style="background:#fff;">
<!-- 系统设置-编辑管理员 -->
<form action="" method="post" id="adminForm1">
<s:hidden theme="simple" name="sysAdminVo.userId"></s:hidden>
<s:hidden theme="simple" value="%{sysAdminVo.loginName}" id="originalUserNameUpdate"></s:hidden>
<div class="pop black1" id="popEditPage" style="width:840px">
	<div class="pop-t">
    	<div class="pop-t-l fl fw white">编辑管理员</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" class="pop-close fr mt4 mr4" onclick="closePopEditPage();"></a></div>
    </div>
    <div class="pop-main p10">
        <ul class="pop-lilist mt15 clearfix line26">	
        	<li class="w49p"><span class="t-r w100 fl">用户姓名：<em class="red1 mr4">*</em></span> 
        		<s:textfield cssClass="fl r-input w50p gray2" name="sysAdminVo.userName" theme="simple" id="userName" maxlength="16" check="11"/>
        	</li>
            <li class="w49p"><span class="t-r w100 fl">登录名：<em class="red1 mr4">*</em></span>
            	<s:textfield cssClass="fl r-input w50p gray2" name="sysAdminVo.loginName" theme="simple" id="loginName" maxlength="16"/>
            </li>
            <li class="w49p"><span class="t-r w100 fl">角色：<em class="red1 mr4">*</em></span>
            	<s:select list="sysAdminVo.sysRoleList" listKey="roleId" listValue="roleName" cssClass="p3 w51p" name="sysAdminVo.roleId" id="roleId"></s:select>
            </li>
	        <li class="w49p"><span class="t-r w100 fl">账号状态：<em class="red1 mr4">*</em></span>
	        	<s:select list="#{1:'有效',0:'无效'}" listKey="key" listValue="value"  cssClass="p3 w51p" name="sysAdminVo.status" id="status"></s:select>
	        </li>
	        <li class="w49p"><span class="t-r w100 fl">联系电话：</span>
            	<s:textfield cssClass="fl r-input w50p gray2" name="sysAdminVo.telephone" theme="simple" id="telephoneNum" maxlength="16" check="1"/>
            </li>
            <li style="width:99%;"><span class="t-r w100 fl">备注：</span>
                 <s:textarea cssClass="r-input more-show" theme="simple" maxlength="200" onKeyUp="if(this.value.length > 200) this.value=this.value.substr(0,200)" name="sysAdminVo.remark" id="remark">
                </s:textarea>
            </li>
            <li style="width:99%;"><span class="t-r w100 fl">省份：</span>
                <s:select list="proSysAreas" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" cssClass="p3 w51p" id="provinceCode" name="sysAdminVo.provinceCode"  onchange="loadCity(this.value)" check="1"></s:select>
            </li>
            <li style="width:99%;"><span class="t-r w100 fl">城市：</span>
                <select id="cityCode" name="sysAdminVo.cityCode" class="p3 w51p" check="1"></select>
            </li>
        </ul>
        <p class="t-c mt10 mb20"><input type="button" class="display-ib btn1 white mr40 f14"  value="编&nbsp;辑" id="submitButton1"/>
        <a href="javascript:void(0);" class="display-ib btn1 white f14" onclick="closePopEditPage();">取&nbsp;消</a></p>
    </div>
</div>
</form>
</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">

console.log($(window));
//关闭编辑弹出层页面
function closePopEditPage()
{
    $("#popEditPage").hide();
    $("#mainBody").hide();
    $("#topHide", window.parent.document).hide();
    $("#leftHide", window.parent.document).hide();
}

//验证开始=============================================================================================

function clearInputDefaultVal(){

	$("#loginName").each(function(){
			
		$(this).focus(function(){
			$(this).removeClass("gray3").addClass("gray2");
			//验证登录名称是否存在
			validateUserDefineClear("loginName");
			 //验证提交 防止多次提交
			setSubmitDisales(false);
		}).blur(function(){
			//验证角色名称是否重复
			var verificationValue = $("#loginName").val();
			//第一个参数是验证的Id,第二个是值
			loginNameIsRepetition("loginName",verificationValue);
			//去除左右两端的空格
			textTrim("loginName");
			
		});
	});
}
//自定义验证
function validateUserDefined(){
	var verificationValue = $("#loginName").val();
	//第一个参数是验证的Id,第二个是值
	loginNameIsRepetition("loginName",verificationValue);
	//去除左右两端的空格
	textTrim("loginName");
}
//验证方法
function validateFunction()
{
	
    //validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [{"validateName":"userName","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		//{"validateName":"passWord:passWord1","validateReg":validateJson.Password.reg,"validateTip":validateJson.Password.tip},//密码校验
		{"validateName":"telephoneNum","validateReg":validateJson.Phone.reg,"validateTip":validateJson.Phone.tip}//手机
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
	//一个form表单提交验证
	$('#submitButton1').click( function () {
		 getthis.each(function(){
			var obj = this;
			checkByType(obj);
		});

		
		//自定义验证 登录名 
	    validateUserDefined();
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;

		if(failValidateCount == 0)
		{
			//验证提交 防止多次提交
			setSubmitDisales(false);
			
			adminFormSubmit("adminForm1");
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

//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
    $("#"+id).parents("li").find(".onCorrect").remove();
    $("#"+id).parent().append("<span id='"+id+"Tip'></span>");
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
	$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
	$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
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

//自定义验证登录名是否存在
function loginNameIsRepetition(verificationId,verificationValue){
	//判断是否为空
	if("" == verificationValue.trim()){
		//创建自定义提示语句
		createErrorTip(verificationId,"登录名称不能为空");
		return;
	}
	

	var originalUserName = $("#originalUserNameUpdate").val();
	
	if(originalUserName == verificationValue.trim()){
		return;
	}
	regValidate(verificationId,validateJson.IsLoginName.reg,validateJson.IsLoginName.tip);
}

//不为空，有验证规则字段的验证方法
function regValidate(id,reg,tip)
{
	var thisRegs = new RegExp(reg);
	var valueText = $("#"+id).val();
	if(typeof(valueText) != "undefined")
	{
		if(thisRegs.test(valueText))
		{
			$.ajax({
				url:'../adminManager/loginNameIsRepetition.action',	
				cache:false,
				type: 'POST',	
				async : false,
				data:{loginName:valueText},
				
				success:function(data){
					if("yes" == data){
						createErrorTip(id,validateJson.LoginName.tip);
					}
					if("no" == data){
						validateUserDefinedSucc(id);
					}
				}
						
			});
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


function setSubmitDisales(flag)
{
     if(flag)
	 {
		$('#submitButton1').removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $('#submitButton1').removeClass("btn1-gray").addClass("btn1");
	 } 
	 $('#submitButton1').attr("disabled", flag);
}
//验证结束=============================================================================================

	
function adminFormSubmit(submitForm){
	
	$("#"+submitForm).ajaxSubmit({
		type : 'post',
		data:{},
		url : "../adminManager/administratorManagerUpdate.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"text",
		success : function(data) {
			
			if("yes" == data){
				closePopEditPage();
				parent.parent.showRturnTip("修改成功",true);
				window.location.href='../adminManager/toList.action';
			}
			if("no" == data){
				parent.parent.showRturnTip("修改失败",false);
			}
		}
	});
}

//根据省份加载城市
function loadCity(value){
console.log();
	$("#cityCode").html("<option value=''>--选择城市--</option>");
	if (value) {
		var url = "../merchantAction/getProvince.action?proId=" + value;
		leaseGetAjax(url,"json",function(data){
			var elem = $("#cityCode"), html = [];
			$(data).each(function(i){
				html.push("<option value='");
				html.push(data[i].arearCode);
				html.push("'>");
				html.push(data[i].areaName);
				html.push("</option>");
			});
			elem.append(html.join(''));
	    });
	}
}

function loadCityInit(value){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			var html = ""
			$(data).each(function(i){
				html = html + "<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>";
			});
			$("#cityCode").append(html);
			$("#cityCode").val("${sysAdminVo.cityCode}");
	    });
	}
}

	
$(function(){
	$("#provinceCode").val('${sysAdminVo.provinceCode}');
	loadCityInit('${sysAdminVo.provinceCode}');
	validateFunction();
	clearInputDefaultVal();
});
</script>

