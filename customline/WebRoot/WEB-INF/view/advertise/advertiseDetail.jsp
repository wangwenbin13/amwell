<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销管理-广告详情</title>
<jsp:include page="../resource.jsp"/>
<script type="text/javascript" src="../js/jquery/ajaxfileupload.js"></script>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;广告详情<span class="blue1 ml25"></span></p></div>
<form action="../adManageAction/editAdManage.action" method="post" id="addForm">
  <input type="hidden" name="ad_id" value="${adModel.ad_id}"/>
  <div class="mt10 ml20 mr20 black1">
    <div class="table-title">
       <ul>
       	<li class="on"><a href="javascript:void(0)" class="f14 blue2">广告详情</a></li>
       </ul>
    </div>
    <div class="sys-text sh-add-new">
    	<ul class="mt10 clearfix gray2 f12">
    		<li><span class="fl w80 t-r">选择客户端：<em class="red1">*</em></span>
    			<select class="p3" id="selectClient" name="clientType" >
    				<s:if test="%{adModel.clientType==1}"><option value="1" >Android</option></s:if>
    				<s:if test="%{adModel.clientType==2}"><option value="2" >IOS</option></s:if>
    			</select>
    		</li>
    		
    		<s:if test="%{adModel.clientType==1}">
    			<li style="width:100%;height:auto"><span class="fl w80 t-r">选择版本：<em class="red1">*</em></span>
				<s:iterator value="vsnList" var="v" status="s">
					<input type="checkbox" class="checkbox mr10" name="versionNO" value="${v.vsn}" <s:if test="%{#v.ifChecked==1}">checked="checked" </s:if> />${v.vsn }
				</s:iterator>	
    			</li>
    		</s:if>
    		
    		<s:if test="%{adModel.clientType==2}">
    			<li style="width:100%;height:auto"><span class="fl w80 t-r">选择版本：<em class="red1">*</em></span>
				<s:iterator value="iosVersion" var="v" status="s">
					<input type="checkbox" class="checkbox mr10" name="versionNO" value="${v.version}" <s:if test="%{#v.ifChecked==1}">checked="checked" </s:if> />${v.version }
				</s:iterator>	
    			</li>
    		</s:if>
    		
    		<li><span class="fl w80 t-r">生效时间：<em class="red1">*</em></span>
    			<span class="r-input display-ib"><input type="text" class="Wdate75 gray2 Wdate" name="effectiveTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})" id="txtStartDate" value="${adModel.effectiveTime}"/></span>
    		</li>
    		<li><span class="fl w80 t-r">失效时间：<em class="red1">*</em></span>
    			<span class="r-input display-ib"><input type="text" class="Wdate75 gray2 Wdate" name="expirationTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'txtStartDate\');}'})" id="txtEndDate" value="${adModel.expirationTime}"/></span>
    		</li>
    		<li style="width: 60%"><span class="fl w80 t-r">请输入链接：<em class="red1">*</em></span>
    			<input type="text" class="r-input w50p" id="urlValue" name="urlLink" value="${adModel.urlLink}" />
    			<a href="javascript:void(0)" class="btn-blue4" onclick="checkURL();" >查看</a>(注意：请以"http://"开头,100个字符以内)
    		</li>
    		<li><span class="fl w80 t-r">广告标题：<em class="red1">*</em></span>
    			<input type="text" class="r-input w50p" id="adTitle" name="adTitle" value="${adModel.adTitle}" />
    		</li>
    		<li><span class="fl w80 t-r">广告跳转方式：<em class="red1">*</em></span>
    		<s:if test="%{adModel.urlSign==1}"> 
    			<s:radio cssClass="checkbox mr4"  list="#{1:'软件内链接',2:'软件外链接'}"  name="urlSign" id="urlSign" value="1"  theme="simple"></s:radio>
    		</s:if>
    		<s:if test="%{adModel.urlSign==2}">
    			<s:radio cssClass="checkbox mr4"  list="#{1:'软件内链接',2:'软件外链接'}"  name="urlSign" id="urlSign" value="2"  theme="simple"></s:radio>
    		</s:if>
    		<s:if test="%{adModel.urlSign==''}">
    			<s:radio cssClass="checkbox mr4"  list="#{1:'软件内链接',2:'软件外链接'}"  name="urlSign" id="urlSign"   theme="simple"></s:radio>
    		</s:if>
    		</li>
    		<li><span class="fl w80 t-r">请选择图片：<em class="red1">*</em></span><span class="gray1">（请上传多种分辨率图片，支持上传gif jpg bmp png等不大于2M的文件）</span></li>
    		
    		
    		<s:if test="%{adModel.clientType==1}">
    			<div id="andriod" class="andriod">
	    		<li style="width:100%;height:auto">
	    			<p class="fw mb5 ml20">Android客户端分辨率</p>
	    			
	    			<s:iterator value="imageList" var="pic" status="s">
		    			<div class="t-c fl ml20">
			    		<div class="p-r" style="width:200px;height:100px;">
			    			<div class="adImgBg" <s:if test='%{#pic.imageUrl != ""}'> style="background:url('${pic.fullImageUrl}');background-size:200px 100px;" </s:if> id="driving_licence_img${s.index+1 }"></div>
		    				<input type="file" class="fileAd" id="driving_licence${s.index+1 }"   name="adImage" onchange="updatePic(${s.index+1 },this);" title="${pic.picTitle }"/>	
		    				<input type="hidden" id="file${s.index }" value="1"/>  
			    		</div>
			    			<p class="red1">${pic.imageResolution}</p>
		    			</div>
	    			</s:iterator>
	    		</li>
    			</div>
    			
    		</s:if><s:elseif test="%{adModel.clientType==2}">
    			<div id="ios" class="ios">
	    		<li style="width:100%;height:auto">
	    			<p class="fw mb5 ml20">IOS客户端分辨率</p>
	    			
	    			<s:iterator value="imageList" var="pic" status="s">
	    			<div class="t-c fl ml20">
		    		<div class="p-r" style="width:350px;height:150px;">
		    			<div class="adImgBg" <s:if test='%{#pic.imageUrl != ""}'> style="background:url('${pic.fullImageUrl}');background-size:350px 150px;" </s:if> id="driving_licence_img${s.index+1 }"></div>
	    				<input type="file" class="fileAd" id="driving_licence${s.index+1 }"   name="adImage" onchange="updatePic(${s.index+1 },this);" title="${pic.picTitle }"/>	
	    				<input type="hidden" id="file${s.index }" value="1"/>  
		    		</div>
		    			<p class="red1">${pic.imageResolution}</p>
	    			</div>
	    			</s:iterator>
	    			
	    		</li>
    			</div>
    		</s:elseif>
    		
    	</ul>
    	<p class=" mt20 t-c"><input type="button" id="submitButton" class="display-ib btn1 white mr40 ml10" onclick="judgeForm()" value="编辑"/>
			<input type="button"  value="返回" class="display-ib btn1 white" onclick="returnList()"/></p>
    </div>
</div>
<s:iterator value="imageList" var="pic" status="s">
	<input type="hidden"  name="imageUrl" id="imageUrl${s.index+1 }" <s:if test='%{#pic.imageUrl != ""}'>value="${pic.imageUrl}"</s:if><s:else>value="0"</s:else>>
</s:iterator>
		
</form>
</body>
</html>
<script type="text/javascript">
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}

//增加版本号
function addversion()
{
    $("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#mainBody").show();
    $("#showPage").load("../adManageAction/versionAddManage.action"); 
}

//URL字符控制
function charControl(){
   	var	urlValue=$("#urlValue").val().length;
	if(urlValue>98){
		parent.parent.showRturnTip("链接地址过长",false);
	}
}

$(function(){

	clearInputValidateDefaultVal();
// 	clientTypeChange();
// 	var vsns = $("#vsns").val();
// 	var vsnss=vsns.split(",");
// 	for(var i=0;i<vsnss.length;i++){
// 		var vsnid="vsn"+vsnss[i];
// 		$("#"+vsnid).attr("checked", true);
// 		alert($("#"+vsnid).val());
// 	}	
})
//清除input框默认值 
function clearInputValidateDefaultVal()
{
	$('input:text,input:file,select').each(function(){  
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
	$("#selectVersion").focus(function(){   //选择客户端,选择版本
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	}); 

	$("#txtStartDate,#txtEndDate,#urlValue,#adTitle").focus(function(){
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){
		isNotNullValidate(this.id);
	});
}


function judgeForm(){
// 	selectValidate("selectClient"); //选择客户端
	selectValidate("selectVersion");  //选择版本
	isNotNullValidate("txtStartDate:txtEndDate:urlValue:adTitle");
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0)
	{
		//验证提交 防止多次提交
		setSubmitDisale(false);
		//此处写验证成功后提交后台的方法
		//此处写验证成功后提交后台的方法
		 $("#addForm").ajaxSubmit({
			type : 'post',
			success : function(data) {
			if (data == "success") {
			    parent.parent.showRturnTip("广告配置成功!",true);
			    $("#iframe_right",parent.parent.window.document).attr("src","../adManageAction/getAdManageList.action");
			}else if("error"==data){
				parent.parent.showRturnTip("广告配置失败!",false);
			}else if("NoPic"==data){
				parent.parent.showRturnTip("图片配置失败!",false);
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
	$("#"+id+"ErrTip").css("margin-top","0");
}

//自定义验证通过
function validateUserDefinedSucc(id)
{
    $("#"+id).parents("li").find(".onCorrect").remove();
	if( id=="selectVersion" || id=="txtStartDate" || id=="txtEndDate" || id=="urlValue" || id=="adTitle")
	{
		$("#"+id).parents("li").append("<span id='"+id+"Tip'></span>");
	}
	else
	{
		$("#"+id).parent().append("<span id='"+id+"Tip'></span>");
	}
    $("#"+id+"Tip").addClass('onCorrect');
	$("#"+id+"ErrTip").hide();
}

//清除自定义验证提示语句
function validateUserDefineClear(id)
{
    $("#"+id+"ErrTip").hide();
    $("#"+id).parent().find(".onCorrect").remove();
}

//上传图片
function updatePic(size,obj){
	//异步上传图片
	var clientType =$("#selectClient").val();
	var value = $(obj).val();
	if("" == value){
		return;
	}
	value = value.split(".");
	value = value[value.length-1];
	var reg = validateJson.UploadFile5K.reg;

	var thisReg = new RegExp(reg);
	if(thisReg.test(value))
	{
		
		parent.parent.popLodingPage(true);
		$.ajaxFileUpload({
				fileElementId:$(obj).attr("id"), 
				secureuri:false,
				dataType: 'text',
				data : {"size":size,"clientType":clientType},
				url : '../adManageAction/upLoad.action?size='+size,
				success : function(data) {
			    parent.parent.popLodingPage(false);
					if (data == "error") {
						parent.parent.showRturnTip("上传失败！",false);
					}else if(data=="overSize"){
						//图片过大，请上传不大于500K的图片
						parent.parent.showRturnTip("图片过大，请上传不大于2M的图片！",false);
					}else if(data=="errorResolution"){
						//分辨率错误
						parent.parent.showRturnTip("图片分辨率错误！",false);
					}else{
						data = eval("(" + data + ")");
						var i = size;
						if(clientType==1){
							$("#driving_licence_img"+i).find("img").remove(); 
							$("#driving_licence_img"+i).append("<img src='"+data.downFileUrl+"' width='200' height='100'/>");
							$("#imageUrl"+i).val(data.dbFileUrl);
							$("#file"+(i-1)).val(1);
						}else{
							$("#driving_licence_img"+i).find("img").remove(); 
							$("#driving_licence_img"+i).append("<img src='"+data.downFileUrl+"' width='350' height='150'/>");
							$("#imageUrl"+i).val(data.dbFileUrl);
							$("#file"+(i-1)).val(1);
						}
												
					}
					
				},
				error:function(){
					parent.showRturnTip("上传失败！",false);
				}
	     });
	}
	
}

function checkURL(){
	var urlLine=$("#urlValue").val();
	if(urlLine==''){
		parent.parent.showRturnTip("查看链接前，请先输入链接地址!",false);
	}else{
		window.open(urlLine,'newwindow','height=400,width=400,top=20%,left=20%,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
	}
}

function returnList(){
	$("#iframe_right",parent.parent.window.document).attr("src","../adManageAction/getAdManageList.action");
}

// function clientTypeChange(){
// 	var clientType =$("#selectClient").val();
// 	if(clientType==1){
// 		$("#andriod").show();
// 		$("#ios").hide();
// 	}else if(clientType==2){
// 		$("#andriod").hide();
// 		$("#ios").show();
// 	}
// }


</script>