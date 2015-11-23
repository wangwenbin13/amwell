<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商管理-编辑商户</title>
<%@include file="../resource.jsp" %>

</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;添加商户<span class="blue1 ml25"></span></p></div>
 <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<s:if test="%{mgrBusinessEntity.businessId!=null}">
                		<li class="on"><a href="javascript:void(0)" class="f14 blue2">编辑商户</a></li>
                	</s:if>
                	<s:else>
                		<li class="on"><a href="javascript:void(0)" class="f14 blue2">添加商户</a></li>
                	</s:else>
                </ul>
            </div>
<s:form id="addForm" action="" method="post" theme="simple">

<s:hidden theme="simple" name="mgrBusinessEntity.businessId"></s:hidden>
<s:hidden theme="simple" name="mgrBusinessEntity.createBy"></s:hidden>
<input type="hidden" id="cityName" name="mgrBusinessEntity.cityName"/>
<div class="table2-text">
  	<p class="bt-bot-line"><span class="red1">*字段为必须填写，不填写无法完成创建</span></p>
 	  <div class="sh-add clearfix">
  		<ul>
  			<li class="widthfull"><span class="w100 fl t-r">企业机构名称：<em class="red1 mr4">*</em></span><s:textfield  cssClass="r-input w35p gray2 fl" name="mgrBusinessEntity.name" theme="simple" id="businessName" check="11" maxlength="30"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">简称：<em class="red1 mr4">*</em></span><s:textfield  cssClass="r-input w35p gray2 fl" name="mgrBusinessEntity.brefName" theme="simple" id="brefName" check="11" maxlength="30"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">登录名：<em class="red1 mr4">*</em></span><s:textfield  cssClass="r-input w35p gray2 fl" name="mgrBusinessEntity.loginName" id="loginName" check="11" maxlength="15"/></li>
  			<s:if test="%{mgrBusinessEntity==null}">
  				<li class="widthfull"><span class="w100 fl t-r">登录密码：<em class="red1 mr4">*</em></span><s:textfield type="password" maxlength="16" cssClass="r-input w35p gray2 fl" name="mgrBusinessEntity.loginPassword" theme="simple" id="password0" check="5"/></li>
  				<li class="widthfull"><span class="w100 fl t-r">确认密码：<em class="red1 mr4">*</em></span><s:textfield type="password" maxlength="16" cssClass="r-input w35p gray2 fl" id="password1" theme="simple"  check="5"/></li>
  			</s:if>
  			<li class="widthfull"><span class="w100 fl t-r">联系人：</span><input  class="r-input w35p gray2 fl" name="mgrBusinessEntity.contacts" value="${mgrBusinessEntity.contacts }" maxlength="10"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">联系电话：<em class="red1 mr4">*</em></span><s:textfield  cssClass="r-input w35p gray2 fl" name="mgrBusinessEntity.contactsPhone" theme="simple" id="contactsPhone" check="10"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">地址：<em class="red1 mr4">*</em></span>
  			<s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName"	headerKey="0" id="provinceCode" headerValue="请选择" name="mgrBusinessEntity.provinceCode" cssClass="w12p p3 fl mr4" onchange="loadCity(this.value);"/>
  			<select class="w12p p3 fl" name="mgrBusinessEntity.areaCode" id="areaCode">
             		<option value="0">请选择</option>
             </select>
             <s:textfield  cssClass="ml8 r-input w35p gray2 fl" name="mgrBusinessEntity.address"  maxlength="225" onkeyup="if(this.value.length > 225) this.value=this.value.substr(0,225)"/>
            </li>
            <li class="mr1p widthfull"><span class="w100 fl t-r">账号状态：<em class="red1 mr4">*</em></span>
	            <s:radio theme="simple" name="mgrBusinessEntity.accountStatus" list="#{'1':'启用','0':'禁用'}" listKey="key" listValue="value"/>
          	</li>
            <li class="mr1p widthfull"><span class="w100 fl t-r">业务类型：<em class="red1 mr4">*</em></span>
	            <s:radio theme="simple" name="mgrBusinessEntity.businessType" list="#{'1':'上下班','2':'包车','3':'两者都支持'}" listKey="key" listValue="value"/>
          	</li>
          	<li class="widthfull" style="height:auto;width:100%"><span class="w100 fl t-r">商户介绍：</span>
          	<textarea class="r-input more-show w87p" id="content" style="height:350px;" onkeyup="if(this.value.length > 5000) this.value=this.value.substr(0,5000)" name="news.newsContext">${mgrBusinessEntity.remark }</textarea>
          	
	          <textarea name="mgrBusinessEntity.remark" style="display:none;" id="textarea_remark"></textarea>
          	</li>
  	  	</ul>
  	  </div>
  	<p class="t-c mt20"><input type="button" class="display-ib btn1 white mr40" <s:if test="%{mgrBusinessEntity.businessId!=null}">value="编辑"</s:if><s:else>value="保存"</s:else> onclick="subMit();"/><input type="reset" onclick="javascript:history.go(-1);" class="display-ib btn1 white" value="取消"/></p>
</div>
</s:form>
</div>
</div>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputValidateDefaultVal();
	//验证方法
	validateFunction();
	//初始化帐号状态
	initAccountStatus();
	
	//加载城市
	selectCitys();

	//清除IE缓存
	$.ajaxSetup ({ 
		cache: false 
	});
});

//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}

//初始化帐号状态
function initAccountStatus(){
	var  businessId= "${mgrBusinessEntity.businessId}";
	if(""==businessId){
		$("#mgrBusinessEntity_accountStatus1").attr("checked",true);;
	}

	var businessType = "${mgrBusinessEntity.businessType}";
	if(""==businessType){
		$("#mgrBusinessEntity_businessType1").attr("checked",true);;
	}
}

//清除input框默认值 
function clearInputValidateDefaultVal()
{
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			$(this).removeClass("gray3").addClass("gray2");
			if(this.id == "businessName")
			{
			    validateUserDefineClear(this.id);
			}
			if(this.id == "brefName")
			{
			    validateUserDefineClear(this.id);
			}
			if(this.id == "loginName")
			{
			    validateUserDefineClear(this.id);
			}
			
			if(txt == $(this).val()){
				
			} 
			setSubmitDisale(false);
		}).blur(function(){  
			if($(this).val() == ""){
			    
			    $(this).removeClass("gray2").addClass("gray3");
			}
			if(this.id == "businessName")
			{
			    validateUserDefined(this.id);
			}
			if(this.id == "brefName")
			{
			    validateUserDefined(this.id);
			}
			if(this.id == "loginName")
			{
			    validateUserDefined(this.id);
			}
		});  
	});
	$("#provinceCode").focus(function(){   //省份
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	}); 
	
	$("#areaCode").focus(function(){   //城市
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	});
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
//密码确认
var passIdArr = "password0,password1";

//异步提交添加/修改
function subMit(){
	var flag = judgeForm();
	if(!flag){
		return;
	}
	var areaCode = $("#areaCode").val();
	var cityName = $("#areaName_"+areaCode).html();
	$("#cityName").val(cityName);
	
	$("#textarea_remark").text(editor.html());
	var ops = editor.text();   
	if(ops.length>6000){
		parent.parent.showPopCommonPage2("商户介绍最多输入5000个字");
		return;
	}
	$("#addForm").ajaxSubmit({
		
		type : 'post',
		url : '../merchantAction/addMerchant.action',
		success : function(data) {
			if (data == "success") {
				parent.parent.showRturnTip("保存成功",true);
				window.location.href="../merchantAction/getMerchantList.action";
			}else if("error"==data){
				parent.parent.popLodeShowPage(false);
				parent.parent.showRturnTip("保存失败",false);
			}else{
				parent.parent.popLodeShowPage(false);
				parent.parent.showRturnTip(data,false);
			}
		}
	});
}

//验证表单
function judgeForm(){
	 getthis.each(function(){
			var obj = this;
			checkByType(obj);
		});
	//自定义验证 验证企业机构名称 
	validateUserDefined("businessName");
	//自定义验证 验证企业机构简称 
	validateUserDefined("brefName");
	//自定义验证 验证登录名 
	validateUserDefined("loginName");
	selectValidate("provinceCode"); //省份
	selectValidate("areaCode");  //城市
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0)
	{
		
		//验证提交 防止多次提交
		setSubmitDisale(false);
		return true;
	}
	else
	{
		
		//验证提交 防止多次提交
		setSubmitDisale(true);
	    return false;
	}
}
//验证方法
function validateFunction()
{
	//validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [
	    {"validateName":"businessName:brefName:loginName:password0","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		{"validateName":"password0:password1","validateReg":validateJson.Password.reg,"validateTip":validateJson.Password.tip},//密码校验
		{"validateName":"contactsPhone","validateReg":validateJson.Phone.reg,"validateTip":validateJson.Phone.tip}//手机
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
}

//页面自定义验证方法
function validateUserDefined(id)
{
	//验证企业机构名称   如果输入全部都是空格则表示空字符串
	var businessName = $('#'+id).val().trim();
	var flag = checkExistName(id);
	//验证企业机构名称为空
	if(flag == "false")
	{
	    createErrorTip(id,validateJson.isNotNull.tip);
	}
	//验证企业机构名称已经存在
	else if(businessName != "" && flag == "error")
	{
	    createErrorTip(id,validateJson.name.tip);
	}
	//验证企业机构名称验证
	else if(businessName != "" && flag == "success")
	{
		validateUserDefinedSucc(id);
	}
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
	var left = $("#"+id).offset().left-$("#"+id).parents().offset().left;
	$("#"+id+"ErrTip").css("margin-left",left+"px");
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

//验证企业机构名称是否存在
function checkExistName(id){
	var flag = "";
	//  如果输入全部都是空格则表示空字符串
	var name = $('#'+id).val().trim();
	var url = "";
	var businessId = "${mgrBusinessEntity.businessId}";
	if("businessName"==id){
		//企业机构名称
		url = "../merchantAction/checkNameExist.action?mgrBusinessEntity.name="+encodeURI(encodeURI(name))+"&mgrBusinessEntity.businessId="+businessId;
	}else if("brefName"==id){
		//企业机构简称
		url = "../merchantAction/checkNameExist.action?mgrBusinessEntity.brefName="+encodeURI(encodeURI(name))+"&mgrBusinessEntity.businessId="+businessId;
	}else if("loginName"==id){
		var flag = regValidate(id,validateJson.IsLoginName.reg,validateJson.IsLoginName.tip);
		if(flag){
			//登录名
			url = "../merchantAction/checkNameExist.action?mgrBusinessEntity.loginName="+encodeURI(encodeURI(name))+"&mgrBusinessEntity.businessId="+businessId;
		}else{
			return;
		}
	}
	if(name == "" || name == "请输入登录账号")
	{
	    flag = "false";
	    return flag;
	}
	$.ajax({
		url:url,	
		cache:false,	
		async : false,
		
		success:function(data){
			flag=data;
		}
	});
	return flag;
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


//加载省份
function loadCity(value){
	$.ajax({
		url:'../merchantAction/getProvince.action?proId='+value,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#areaCode").empty();
			$("#areaCode").append("<option value='0'>请选择</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"' id='areaName_"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#areaCode"));
			})
			loadSelectCity();
		}
	});
}

//显示原来的城市
function selectCitys(){
	var pro = "${mgrBusinessEntity.provinceCode}";
	if(""!=pro){
		loadCity(pro);
	}
	
}

//加载原来的城市
function loadSelectCity(){
	var city = "${mgrBusinessEntity.areaCode}";
		if(""!=city){
			var ops =  $("#areaCode option");
			for(var i=0;i<ops.length;i++){
				if(city==ops[i].value){
					ops[i].selected = true;
				}
			}
		}
}

$(function(){
	$(".sh-add-new").css("height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("height",$(window).height()-130+"px");	
	})
});

//创建文字编辑器
var editor;
var remark = '${mgrBusinessEntity.businessId}';
var oldHtml;
KindEditor.ready(function(K) {
	 editor = K.create('textarea[name="news.newsContext"]', {
		cssPath : '../js/kindeditor-4.1.1/plugins/code/prettify.css',
		uploadJson : '../js/kindeditor-4.1.1/jsp/upload_json.jsp',
		fileManagerJson : '../js/kindeditor-4.1.1/jsp/file_manager_json.jsp',
		allowFileManager : true,
		items : ['source', '|', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
		         'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
		         'insertunorderedlist', '|', 'emoticons','image', 'link'],
		 afterCreate : function() {
			var self = this;
			K.ctrl(document, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
			K.ctrl(self.edit.doc, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
		}
	 
	});
});

</script>
</body>
</html>