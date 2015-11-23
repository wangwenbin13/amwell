<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理-添加用户</title>
<%@include file="../resource.jsp" %>
</head>

<body>
	<div id="popMapPage" class="pop black1" style="width:425px;">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white">添加用户</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="loadPage.window.closePopAddPage();" class="pop-close fr mt4 mr4"></a></div>
	    </div>
	    <div class="pop-main p10">
	        <ul class="pop-lilist mt15 clearfix line26">
	            <li>
		            <span class="fl w100 t-r">选择城市：<em class="red1">*</em></span>
	             	<s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" name="provinceCode" id="provinceCode" cssClass="p3 mr10" onchange="loadPage.window.loadCity(this.value);"/>
		  			<select class="p3" name="cityCode" id="cityCode">
		  				<option value="">--选择城市--</option>
		  			</select>
	  			</li>
	        	<li class="widthfull"><span class="t-r w95 fl">手机号码：<em class="red1 mr4">*</em></span>
	        		<input class="r-input" id="contactsPhone" /><span class="ml4 red1 hide errTip" id="errTip">请输入正确的手机号码</span><span class="onCorrect" style="display:none;" id="onCorrect"></span>
	        	</li>
	            <li class="widthfull"><span class="t-r w95 fl">昵称：</span>
	        		<input class="r-input" id="nickName"/>
	        	</li>
	            <li class="widthfull"><span class="t-r w95 fl">性别：</span>
	        	   <select class="p3" id="sex">
	        	   	<option value="0">男</option>
	        	   	<option value="1">女</option>
	        	   </select>
	        	</li>
	        </ul>
	        <p class="t-c mt10 mb20"><input type="button" class="display-ib btn1 white mr40 f14" value="添&nbsp;加" onclick="loadPage.window.addFunction()" id="submitButton"/></p>
	    </div>
	</div>
</body>
</html>
<script language="Javascript">
//根据省份加载城市
function loadCity(value){
	if(value==null || value==""){
		$("#cityCode",parent.window.document).empty();
		$("#cityCode",parent.window.document).append("<option value=''>--选择城市--</option>");
		return;
	}
  	$.ajax({
		url:'../merchantAction/getProvince.action?proId='+value,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#cityCode",parent.window.document).empty();
			$("#cityCode",parent.window.document).append("<option value=''>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode",parent.window.document));
			})
		}
	});
}

//关闭弹出层
function closePopAddPage()
{
	$("#popMapPage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
   
}
//手机和座机校验方法,不可以为空
function isPhoneOrTellVal(id)
{
	if(isPhoneOrTell($("#"+id,parent.window.document).val()))
	{
		$("#onCorrect",parent.window.document).show();
		$("#errTip",parent.window.document).hide();
		setSubmitDisale(false);
	}
	else
	{
		$("#onCorrect",parent.window.document).hide();
		$("#errTip",parent.window.document).show();
		setSubmitDisale(false);
	}
}

//手机和固定座机电话一起校验
function isPhoneOrTell(number)
{
	var isMobile = new RegExp(validateJson.Phone.reg);
	var isTell = new RegExp(validateJson.Tell.reg);
	if(isMobile.test(number) || isTell.test(number))
	{
		 return true;	
	}
	else
	{
		return false;	
	}
}

$(function(){
		setSubmitDisale(true);	
	//验证方法
	$("#contactsPhone",parent.window.document).focus(function(){
		$("#onCorrect",parent.window.document).hide();
		$("#errTip",parent.window.document).hide();
		setSubmitDisale(false);
	}).blur(function(){
		isPhoneOrTellVal(this.id);
	});
	$('input:text',parent.window.document).each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			setSubmitDisale(false);
		}).blur(function(){  

		});  
	});
});

function addFunction()
{
	isPhoneOrTellVal("contactsPhone");
	var failValidateCount = $(parent.window.document).find(".errTip:visible").length;
	//failValidateCount=0代表验证成功
	
	if(failValidateCount == 0)
	{
		//验证提交 防止多次提交
		setSubmitDisale(false);
	}
	else
	{
		//验证提交 防止多次提交
		setSubmitDisale(true);
	}
	var provinceCode=$("#provinceCode",parent.window.document).val();
	var cityCode = $("#cityCode",parent.window.document).val();
	var contactsPhone=$("#contactsPhone",parent.window.document).val();
	var nickName=$("#nickName",parent.window.document).val();
	var sex=$("#sex",parent.window.document).val();
// 	alert(contactsPhone+"==="+nickName+"==="+sex);
		$.ajax({
			url:'../passengerRegist/addPassenger.action',
			cache:false,
			type : 'post',
			data:{"provinceCode":provinceCode,"cityCode":cityCode,"contactsPhone":contactsPhone,"nickName":nickName,"sex":sex},
			dataType:"text",
			success:function(data){	
// 				alert(data);
				if(data=="success"){
					parent.parent.showRturnTip("添加成功",true);
					closePopAddPage();
// 					window.location.href="../operationPassenger/passengerList.action";
					$("#iframe_right").attr("src","../operationPassenger/passengerList.action")
				}else if(data=="telephone"){
					parent.parent.showRturnTip("请正确填写号码",false);
				}else{
					parent.parent.showRturnTip("号码已存在或必填字段内容未填",false);
				}

	 		}
	 });
		

		
		
}

//验证提交 防止多次提交  flag 为true表示按钮不可点击，false表示按钮可点击
function setSubmitDisale(flag)
{
	if(flag)
	 {
		$("#submitButton",parent.window.document).removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $("#submitButton",parent.window.document).removeClass("btn1-gray").addClass("btn1");
	 } 
	$("#submitButton",parent.window.document).attr("disabled", flag);
}
</script>
