<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销管理-广告添加</title>
<jsp:include page="../resource.jsp"/>
<script type="text/javascript" src="../js/jquery/ajaxfileupload.js"></script>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;广告添加<span class="blue1 ml25"></span></p></div>
<form action="../adManageAction/addAdManage.action" method="post" id="addForm">
  <div class="mt10 ml20 mr20 black1">
    <div class="table-title">
       <ul>
       	<li class="on"><a href="javascript:void(0)" class="f14 blue2">广告添加</a></li>
       </ul>
    </div>
    <div class="sys-text sh-add-new">
    	<ul class="mt10 clearfix gray2 f12">
    		<li><span class="fl w80 t-r">选择客户端：<em class="red1">*</em></span>
    			<select class="p3" id="selectClient" name="clientType" onchange="clientTypeChange();">
    				<option value="">请选择客户端</option>
    				<option value="1">Android</option>
    				<option value="2">IOS</option>
    			</select>
    		</li>
    		<li style="width:100%;height:auto" id="androidVsn"><span class="fl w80 t-r">选择版本：<em class="red1">*</em></span>
					<s:iterator value="vsnList" var="v" status="s" >
					<input type="checkbox" class="checkbox mr10" name="versionNO"  value="${v.vsn }"/>${v.vsn }
					</s:iterator>	
    		</li>
    		<li style="width:100%;height:auto" id="IOSVsn"><span class="fl w80 t-r">选择版本：<em class="red1">*</em></span>
					<s:iterator value="iosVersion" var="v" status="s" >
					<input type="checkbox" class="checkbox mr10" name="versionNO"  value="${v.version }"/>${v.version }
					</s:iterator>	
    		</li>
    		<li><span class="fl w80 t-r">生效时间：<em class="red1">*</em></span>
    			<span class="r-input display-ib"><input type="text" name="effectiveTime" class="Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})" id="txtStartDate" /></span>
    		</li>
    		<li><span class="fl w80 t-r">失效时间：<em class="red1">*</em></span>
    			<span class="r-input display-ib"><input type="text" name="expirationTime" class="Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'txtStartDate\');}'})" id="txtEndDate" /></span>
    		</li>
    		<li>
    			<span class="fl w80 t-r">广告跳转方式：<em class="red1">*</em></span>
    			<s:radio cssClass="checkbox mr4"  list="#{1:'软件内链接',2:'软件外链接'}"  name="urlSign" id="urlSign" value="2"  theme="simple" onclick="hideUrl(this);"></s:radio>
    		</li>
    		<li class="url" style="width: 60%"><span class="fl w80 t-r">请输入链接：<em class="red1">*</em></span>
    			<input type="text" class="r-input w50p" id="urlValue" name="urlLink"  placeholder="例如：http://www.pig84.com" />
    			<a href="javascript:void(0)" class="btn-blue4" onclick="viewUrlLink();" >查看</a>(注意：请以"http://"开头,1000个字符以内)
    		</li>
    		<li><span class="fl w80 t-r">广告标题：<em class="red1">*</em></span>
    			<input type="text" class="r-input w50p" id="adTitle" name="adTitle"  placeholder="请输入广告标题" />
    		</li>
    		
    		<li><span class="fl w80 t-r">请选择图片：<em class="red1">*</em></span><span class="gray1">（请上传多种分辨率图片，支持上传gif jpg bmp png等不大于2M的文件）</span></li>
    		
    		<div id="andriod" class="andriod">
	    		
    		</div>
    		
    		<div id="ios" class="ios">
	    		
    		</div>
    		
    	</ul>
    	<p class=" mt20 t-c"><input type="button" id="submitButton" class="display-ib btn1 white mr40 ml10" onclick="judgeForm()" value="提交"/>
			<input type="button"  value="重置" class="display-ib btn1 white" onclick="resetValue();"/></p>
   	 </div>
	</div>
	<div id="imageUrlStr" class="imageUrlStr"></div>
	<%-- <s:hidden name="imageUrl" id="imageUrl1" value="0"></s:hidden>
	<s:hidden name="imageUrl" id="imageUrl2" value="0"></s:hidden>
	<s:hidden name="imageUrl" id="imageUrl3" value="0"></s:hidden>
	<s:hidden name="imageUrl" id="imageUrl4" value="0"></s:hidden>
	<s:hidden name="imageUrl" id="imageUrl5" value="0"></s:hidden>
	<s:hidden name="imageUrl" id="imageUrl6" value="0"></s:hidden>
	<s:hidden name="imageUrl" id="imageUrl7" value="0"></s:hidden>
	<s:hidden name="imageUrl" id="imageUrl8" value="0"></s:hidden> --%>
	
	
</form>
</body>
</html>
<script type="text/javascript">
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}


//URL字符控制
function charControl(){
   	var	urlValue=$("#urlValue").val().length;
	if(urlValue>98){
		parent.parent.showRturnTip("链接地址过长",false);
	}
}



//重置
function resetValue(){
	$("#selectClient").val("");
	$("#txtStartDate").val("");
	$("#txtEndDate").val("");
	$("#urlValue").val("");
	$("#adTitle").val("");
	//清除所有的验证语句
	validateUserDefineClear("selectClient");
	validateUserDefineClear("txtStartDate");
	validateUserDefineClear("txtEndDate");
	validateUserDefineClear("urlValue");
	validateUserDefineClear("adTitle");
}

$(function(){

	clearInputValidateDefaultVal();
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
	$("#selectClient,#selectVersion").focus(function(){   //选择客户端,选择版本
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	}); 
	
// 	var clientType =$("#selectClient").val();
// 	if(clientType)
	
	$("#txtStartDate,#txtEndDate,#urlValue,#adTitle,#file0,#file1,#file2,#file3,#file4,#file5").focus(function(){
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){
		isNotNullValidate(this.id);
	});
}


function judgeForm(){
	selectValidate("selectClient"); //选择客户端
	selectValidate("selectVersion");  //选择版本
	var client = $("#selectClient").val();
	if(client==1){
		isNotNullValidate("txtStartDate:txtEndDate:urlValue:adTitle");
	}else {
		isNotNullValidate("txtStartDate:txtEndDate:urlValue:adTitle");
	}
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
			    parent.parent.showRturnTip("广告配置成功!",true);
			    $("#iframe_right",parent.parent.window.document).attr("src","../adManageAction/getAdManageList.action");
			}else if("error"==data){
				parent.parent.showRturnTip("广告配置失败!",false);
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
	if(id=="selectClient" || id=="selectVersion" || id=="txtStartDate" || id=="txtEndDate" || id=="urlValue"|| id=="adTitle")
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

//查看链接
function viewUrlLink(){
    if(''==$("#urlValue").val()){
    	parent.parent.showRturnTip("查看链接前，请先输入链接地址!",false);
    }else{
    	//var url="http://"+$("#urlValue").val();
    	var url=$("#urlValue").val();
    	window.open(url,'newwindow','height=400,width=400,top=20%,left=20%,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
    }
}

//上传图片
function updatePic(size,obj){
	//异步上传图片
	var selectClient=$("#selectClient").val();
	var value = $(obj).val();
	if("" == value){
		return;
	}
	var picName=value.split("\\");
	picName=picName[picName.length-1];
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
				data:{"clientType":selectClient,"size":size},
				url : '../adManageAction/upLoad.action?',
				success : function(data) {
			    parent.parent.popLodingPage(false);
			    	if(""==selectClient){
			    		parent.parent.showRturnTip("请选客户端！",false);
			    	}else if (data == "error") {
						parent.parent.showRturnTip("上传失败！",false);
					}else if(data=="overSize"){
						//图片过大，请上传不大于500K的图片
						parent.parent.showRturnTip("图片过大，请上传不大于2M的图片！",false);
					}else if(data=="errorResolution"){
						//分辨率错误
						parent.parent.showRturnTip("图片分辨率错误！",false);
					}else{
						data = eval("(" + data + ")");
						var clientType =$("#selectClient").val();
						if(clientType==1){
									var i = size;
// 									alert("#driving_licence"+i+"="+picName+"data.downFileUrl="+data.downFileUrl);
									$("#driving_licence"+i).attr("title",picName);
									$("#driving_licence_img"+i).find("img").remove(); 
									$("#driving_licence_img"+i).append("<img src='"+data.downFileUrl+"' width='200' height='100'/>");
									$("#imageUrl"+i).val(data.dbFileUrl);
									$("#file"+(i-1)).val(1);
								
						}else{
									var i = size;
									$("#driving_licence"+i).attr("title",picName);
									$("#driving_licence_img"+i).find("img").remove(); 
									$("#driving_licence_img"+i).append("<img src='"+data.downFileUrl+"' width='320' height='150'/>");
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

//客户端转换－－清理数据
$(".ios").hide();
$(".andriod").hide();
$("#androidVsn").hide();
$("#IOSVsn").hide();
function clientTypeChange(){
	var clientType =$("#selectClient").val();
	$("#imageUrlStr").empty();
	$.ajax({
		url:'../adManageAction/clientType.action',
		type:'post',
		data:{"clientType":clientType},
		cache:false,
		dataType:"json",
		success:function(data){
			if(clientType==1){
				$("#androidVsn").show();
				$("#IOSVsn").hide();
				$(".andriod").show();
				$(".ios").empty();
				$("#andriod").append("<li style='width:100%;height:auto'><p class='fw mb5 ml20'>Android客户端分辨率</p></li>");
				for(var i=0;i<data.length;i++){
					$("#andriod").append('<div class="t-c fl ml20"><div class="p-r" style="width:200px;height:100px;"><div class="adImgBg" id="driving_licence_img'+(i+1)+'"></div>'+
				    				'<input type="file" class="fileAd" id="driving_licence'+(i+1)+'"   name="adImage" onchange="updatePic('+(i+1)+',this);" />'+
				    				'<input type="hidden" id="file'+i+'"/></div><p class="red1">'+data[i]+'</p></div>'); 
					$("#imageUrlStr").append("<input type='hidden' name='imageUrl' id='imageUrl"+(i+1)+"' value='0'/>");
				}
			$("#andriod").append('</li>');
			}else if(clientType==2){
				$("#androidVsn").hide();
				$("#IOSVsn").show();
				$(".ios").show();
				$(".andriod").empty();
				$("#ios").append("<li style='width:100%;height:auto'><p class='fw mb5 ml20'>IOS客户端分辨率</p></li>");
				for(var i=0;i<data.length;i++){
					$("#ios").append('<div class="t-c fl ml20"><div class="p-r" style="width:350px;height:150px;"><div class="adImgBg" id="driving_licence_img'+(i+1)+'"></div>'+
				    				'<input type="file" class="fileAd" id="driving_licence'+(i+1)+'"   name="adImage" onchange="updatePic('+(i+1)+',this);" />'+
				    				'<input type="hidden" id="file'+i+'"/></div><p class="red1">'+data[i]+'</p></div>');  
					$("#imageUrlStr").append("<input type='hidden' name='imageUrl' id='imageUrl"+(i+1)+"' value='0'/>");
					}
				$("#ios").append('</li>');
			}
			
			
		}
	})
}


//URL地址
function hideUrl(obj){
	var sign = $("[name='urlSign']:checked").val();
	if(sign==1){
		$("#urlValue").val("注:内链接跳转地址由软件内部提供!")
	}else{
		$("#urlValue").val("");
	}
}






</script>