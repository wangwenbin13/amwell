<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-添加管理员</title>
<%@include file="../resource.jsp" %>
</head>

<body style="background:#fff;">
<!-- 系统设置-添加管理员 -->
<form action="" method="post" id="adminForm">
<div class="pop black1" id="popAddPage" style="width:840px">
	<div class="pop-t">
    	<div class="pop-t-l fl fw white">添加管理员</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" class="pop-close fr mt4 mr4" onclick="closePopAddPage();"></a></div>
    </div>
    <div class="pop-main p10">
        <ul class="pop-lilist mt15 clearfix line26">	
        	<li class="w49p"><span class="t-r w100 fl">用户姓名：<em class="red1 mr4">*</em></span> 
        		<s:textfield cssClass="fl r-input w50p gray2" name="sysAdminVo.userName" theme="simple" id="userName" maxlength="16" check="11"/>
        	</li>
            <li class="w49p"><span class="t-r w100 fl">登录名：<em class="red1 mr4">*</em></span>
            	<s:textfield cssClass="fl r-input w50p gray3" name="sysAdminVo.loginName" theme="simple" id="loginName" maxlength="16"/>
            </li>
            <li class="w49p"><span class="t-r w100 fl">登录密码：<em class="red1 mr4">*</em></span>
            	<s:password cssClass="fl r-input w50p"  theme="simple" id="passWord" check="5" name="sysAdminVo.passWord"></s:password>
            </li>
            <li class="w49p"><span class="t-r w100 fl">确认密码：<em class="red1 mr4">*</em></span>
           		<s:password cssClass="r-input w50p fl"  theme="simple" id="passWord1"  check="5"></s:password>
            </li>

            <li class="w49p"><span class="t-r w100 fl">角色：<em class="red1 mr4">*</em></span>
	            <s:select list="sysAdminVo.sysRoleList" listKey="roleId" listValue="roleName" cssClass="p3 w51p" name="sysAdminVo.roleId" id="roleId"></s:select>
            </li>
	        <li class="w49p"><span class="t-r w100 fl">账号状态：<em class="red1 mr4">*</em></span>
	            <s:select list="#{1:'有效',0:'无效'}" listKey="key" listValue="value"  cssClass="p3 w51p" name="sysAdminVo.status" id="status"></s:select>
	        </li>
	        <li class="w49p"><span class="t-r w100 fl">联系电话：</span>
            	<s:textfield cssClass="fl r-input w50p gray2" name="sysAdminVo.telephone" theme="simple" id="telephone" maxlength="16" check="1"/>
            </li>
            <li style="width:99%;"><span class="t-r w100 fl">备注：</span>
                <s:textarea cssClass="r-input more-show" theme="simple" maxlength="200" onKeyUp="if(this.value.length > 200) this.value=this.value.substr(0,200)" name="sysAdminVo.remark" id="remark">
                </s:textarea>
            </li>
            <li style="width:99%;"><span class="t-r w100 fl">省份：</span>
                <s:select list="proSysAreas" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" cssClass="p3 w51p" name="sysAdminVo.provinceCode" id="provinceCode" onchange="loadCity(this.value)"></s:select>
            </li>
            <li style="width:99%;"><span class="t-r w100 fl">城市：</span>
                <select name="sysAdminVo.cityCode" class="p3 w51p" id="cityCode">
                    <option value="">--选择城市--</option>
                </select>
            </li>
        </ul>
        <p class="t-c mt10 mb20"><input type="button" class="display-ib btn1 white mr40 f14"  value="添&nbsp;加"  id="submitButton"/>
        <a href="javascript:void(0);" class="display-ib btn1 white f14" onclick="resetValue();">重&nbsp;置</a></p>
        
    </div>
</div>
</form>
</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
//密码确认
var passIdArr = "passWord,passWord1";
//关闭增加弹出层页面
function closePopAddPage()
{
    $("#popAddPage").hide();
    $("#mainBody").hide();
    $("#topHide", window.parent.document).hide();
    $("#leftHide", window.parent.document).hide();
}
//重置
function resetValue(){
	$("#userName").val("");
	$("#loginName").val("");
	$("#passWord").val("");
	$("#passWord1").val("");
	//$("#roleId").val("");
	$("#status").val("1");
	$("#telephone").val("");
	$("#remark").val("");
	//清除所有的验证语句
	validateUserDefineClear("userName");
	validateUserDefineClear("loginName");
	validateUserDefineClear("passWord");
	validateUserDefineClear("passWord1");
	validateUserDefineClear("telephone");
	setSubmitDisales(false);
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
		{"validateName":"passWord:passWord1","validateReg":validateJson.Password.reg,"validateTip":validateJson.Password.tip},//密码校验
		{"validateName":"telephone","validateReg":validateJson.Phone.reg,"validateTip":validateJson.Phone.tip}//手机
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
	//一个form表单提交验证
	$('#submitButton').click( function () {
		 
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
			
			adminFormSubmit("adminForm");
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
		$('#submitButton').removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $('#submitButton').removeClass("btn1-gray").addClass("btn1");
	 } 
	 $('#submitButton').attr("disabled", flag);
}
//验证结束=============================================================================================

	
function adminFormSubmit(submitForm){

	$("#"+submitForm).ajaxSubmit({
		type : 'post',
		data:{},
		url : "../adminManager/administratorManagerAdd.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"text",
		success : function(data) {
			
			if("yes" == data){
				closePopAddPage();
				parent.parent.showRturnTip("添加成功",true);
				window.location.href='../adminManager/toList.action';
			}
			if("no" == data){
				parent.parent.showRturnTip("添加失败",false);
			}
		}
	});
}

//根据省份加载城市
function loadCity(value){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
	    });
	}
}

	
$(function(){
	validateFunction();
});
</script>

