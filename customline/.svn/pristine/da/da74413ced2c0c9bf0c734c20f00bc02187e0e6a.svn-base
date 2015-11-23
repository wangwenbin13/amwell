<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理-批量注册</title>
<%@include file="../resource.jsp" %>
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
</head>

<body>
	<div id="popMapPage" class="pop black1" style="width:425px;">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white">批量注册</div>
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
	        	<li class="widthfull"><span class="t-r w75 fl">导入文件：</span>
		      	  	<input type="text" class="r-input w222 fl" id="showImg" readonly="readonly"/>
		      	  	<input type="hidden" id="choocePassengerList" name="choocePassengerList"/> 
		       		 <span class="p-r yc-file fl ml10 btn-blue4">
			       		 <input id="excelFile" name="excelFile" type="file" class="file2 p-a" onchange="uploadFile();"/>
			       		 <a class="display-ib btn-gray" style="color:#fff;" href="javascript:void(0)">浏览</a>
			       		
		       		 </span>
	        	</li>
	        	<li> <a href="../passengerRegist/downloadTemplate.action" class="blue1 ml10">下载Excel模板</a></li>	   
	        	<li >
					<img src="../images/loading.gif"  id="checkImportImg"  style="display: none;width:24px;height:24px;margin-top:98px"/>
					<!-- <input type="hidden" value="正在批量导入，请等待……" id="waitting" /> -->
					<div id="waitting"><p>正在批量导入，请等待……</p></div>
					
	        	</li>        
	        </ul>
	        <p class="t-c mt10 mb20"><input type="button" class="display-ib btn1 white mr40 f14" value="确&nbsp;定" onclick="addFunction()" id="submitButton"/></p>
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

$(function(){
	$("#waitting",parent.window.document).hide();
})



</script>
