<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-APP相关设置</title>
<jsp:include page="../resource.jsp" />
	<style>
	    .errorTip{
	        display:inline-block;
	        color:#d1261e;
	        background:#f7e7e9;
	        margin-left:10px;
	        padding:0 5px;
	        margin-top:10px;
	    }
	</style>
</head>

<body>
	<div class="r-sub-nav-out">
		<p class="r-sub-nav gray2">
			当前位置：首页&nbsp;>&nbsp;订单管理&nbsp;>&nbsp;修改APP版本
		</p>
		<input type="hidden" id="vsnId" value="${andriodVo.appId }"/>
	</div>
		<div class="table2-text black1" >
		 	 <s:if test="%{client==1}">
				<p class="bt-bot-line f-yahei f13 fw blue2">
					<span class="bt-ico fl mr5"></span>Android
				</p>
					<ul class="mt10 sh-add clearfix">
					<li  class="w98p mb8"><span class="fl t-r w106">应用类型 ：</span>
							<input type="radio" class="checkbox mr4" name="appUserType" checked="checked" id="noUp"  value="1" onclick="checkClient(1);"/>乘客端  
							<input type="radio" class="checkbox mr4 ml10" id="yesUp" name="appUserType" value="2"  onclick="checkClient(2);"/>司机端 &nbsp;&nbsp;&nbsp;<font color="red">注:选择应用类型</font>
					</li> 
						<li  class="w98p mb8"><span class="fl t-r w106">新版本号 ：</span><input type="text" class="r-input" id="vsn" value="${andriodVo.vsn }" onblur="checkNumber(this);" onclick="removeError();" readonly="readonly" /><span class="errorTip" id="errorVsn" ></span></li>
						<li  class="w98p mb8"><span class="fl t-r w106">版本标识 ：</span><input type="text" class="r-input" id="flag" value="${andriodVo.flag }" onblur="checkFlag(this);" onclick="removeError();" readonly="readonly"/><span class="errorTip" id="errorFlag"></span></li>
						<li  class="w98p mb8"><span class="fl t-r w106">服务器存放路径 ：</span><input type="text" class="r-input" id="url" value="${andriodVo.url }" onclick="removeError();"/><span class="errorTip" id="errorUrl" ></span></li>
						<li  class="w98p mb8"><span class="fl t-r w106">是否强制更新 ：</span>
							<input type="radio" class="checkbox mr4"      name="appUpdate" id="noAppUp"  value="0" <s:if test="%{andriodVo.ismust==0}">checked="checked"</s:if> />不强制更新  
							<input type="radio" class="checkbox mr4 ml10" id="yesAppUp" name="appUpdate" value="1"  <s:if test="%{andriodVo.ismust==1}">checked="checked"</s:if> />强制更新
						</li>
						
						<li><span class="fl t-r w180">更新内容：</span> <textarea class="r-input more-show fl" id="appWXcontent"   style="width:500px;height:200px;" onclick="removeError();"  >${andriodVo.info }</textarea>
						 <span class="errorTip" id="errorContent"></span><em class="red1">注:更新内容（用“#”）分隔</em></li>
					</ul>
				<p class="ml70 mt20">
					<li style="text-align:center;"><input type="button" class="display-ib btn1 white mr40 ml10"   value="保存"	onclick="appSet()" />
					<input type="button" class="display-ib btn1 white mr40 ml10"   value="返回"	onclick="history.back()" />
					</li>
				</p>
		 	 </s:if>
	  
			<s:if test="%{client==2}">
				<p class="bt-bot-line f-yahei f13 fw blue2">
					<span class="bt-ico fl mr5"></span>IOS(最新版本 : ${iosVsn })
				</p>
					<ul class="mt10 sh-add clearfix">
						<li  class="w98p mb8"><span class="fl t-r w106">应用类型 ：</span>
							<input type="radio" class="checkbox mr4" name="iosUserType" id="noUp"  value="1" checked="checked" onclick="iosCheckClient(1)"/>乘客端  
							<input type="radio" class="checkbox mr4 ml10" id="yesUp" name="iosUserType" value="2"   onclick="iosCheckClient(2);"/>司机端
						</li>
					
					<li  class="w98p mb8"><span class="fl t-r w106">新版本号 ：</span><input type="text" id="vsnIos" onblur="checkNumber(this)" onclick="removeError();" value="${iosVo.version }" readonly="readonly"><span class="errorTip" id="errorIosVsn"></span> </li>
						<li  class="w98p mb8"><span class="fl t-r w106">是否强制更新 ：</span>
							<input type="radio" class="checkbox mr4" name="iosUpdate" id="noNew"  value="0"  <s:if test="%{iosVo.ismust==0}">checked="checked"</s:if>  />不强制更新  
							<input type="radio" class="checkbox mr4 ml10" id="yesNew" name="iosUpdate" value="1" <s:if test="%{iosVo.ismust==1}">checked="checked"</s:if>  />强制更新
						</li>

						<li><span class="fl t-r w180">更新内容：</span> <textarea class="r-input more-show fl" id="iosWXcontent"  style="width:500px;height:200px;" onclick="removeError();"  >${iosVo.info }</textarea>
						 <span class="errorTip" id="errorIosContent"></span><em class="red1">注:更新内容（用“#”）分隔</em></li>
					</ul>
				<p class="ml70 mt20">
					<li style="text-align:center;"><input type="submit" class="display-ib btn1 white mr40 ml10"   value="保存"	onclick="iosSet()" />
					<input type="button" class="display-ib btn1 white mr40 ml10"   value="返回"	onclick="history.back()" />
					</li>
				</p>
			</s:if>	
  </div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		//浏览器可视窗口的的高度
		
		$(".table2-text").css("min-height", ($(window).height() - 150 + "px"));
		$(window).resize(
				function() {
					$(".table2-text").css("min-height",
							($(window).height() - 150 + "px"));
				});
	});

//正整数，不可以输0
	function replaceNumPositiveInteger(obj)
	{
		if(obj.value.length==1){obj.value=obj.value.replace(/[^1-9]/g,'')}else{obj.value=obj.value.replace(/\D/g,'')}; 
		return obj.value;
	}
	
function appSet(){
	var isOk=true;
	var appUpdate = $("[name='appUpdate']:checked").val();
	var appUserType = $("[name='appUserType']:checked").val();
	var appWXcontent = $("#appWXcontent").val();
	var vsn = $("#vsn").val();
	var flag = $("#flag").val();
	var url = $("#url").val();
	var appId = $("#vsnId").val();
	if(null==vsn || vsn==""){
		$("#errorVsn").html("请先添加版本数据!");
		isOk=false;
	}
	if(null==url || url==""){
		$("#errorUrl").html("路径不能为空!");
		isOk=false;
	}
	if(null==appWXcontent || appWXcontent==""){
		$("#errorContent").html("更新内容不能为空!");
		isOk=false;
	}
	
	parent.setSubmitDisale(false);
	if(isOk){
		$.ajax({
		url  : '../appAction/andriodUpdateVsn.action',
		data : {"appUpdate":appUpdate,"appUserType":appUserType,"appWXcontent":appWXcontent,"vsn":vsn,"flag":flag,"url":url,"appId":appId},
		type : 'post',
		cache: false,
		dataType :"text",
		success  :function(data){
			if(data=="success"){
				parent.parent.showRturnTip("保存成功!",true);
				$("#iframe_right",parent.window.document).attr("src","../appAction/appVersion.action");
			}else if(data=="passengerError"){
				parent.parent.showRturnTip("版本标识号错误!",false);
			}else{
				parent.parent.showRturnTip("保存失败!",false);
			}
		}   
	})
	}
	
}

function iosSet(){
//     parent.parent.setSubmitDisale(true);
	var isOk=true;
	var vsnIos = $("#vsnIos").val();
	var iosUpdate = $("[name='iosUpdate']:checked").val();
	var iosUserType = $("[name='iosUserType']:checked").val();
	var iosWXcontent = $("#iosWXcontent").val();
	if(vsnIos==null||vsnIos==""){
		$("#errorIosVsn").html("请先添加版本数据!");
		isOk=false;
	}
	if(iosWXcontent==null || iosWXcontent==""){
		$("#errorIosContent").html("版本内容不能为空");
		isOk=false;
	}
	if(isOk){
		$.ajax({
			url  : '../appAction/iosVsnSet.action',
			data : {"vsnIos":vsnIos,"iosUpdate":iosUpdate,"iosUserType":iosUserType,"iosWXcontent":iosWXcontent,"update":"1"},
			type : 'post',
			cache: false,
			dataType :"text",
			success  :function(data){
				if(data=="success"){
				parent.parent.showRturnTip("保存数据成功!",true);
					$("#iframe_right",parent.window.document).attr("src","../appAction/appVersion.action");
				}else if(data=="passengerError"){
						parent.parent.showRturnTip("输入数据错误!",false);
				}else if(data = "error"){
						parent.parent.showRturnTip("保存错误!",false);
				
				}
			}   
	})
	}
	
}

///~********************验证数字 开始************************~/
	function checkNumber(obj){
		var reg = new RegExp("^(([0-9]+)([.]([0-9]+))([.]([0-9]+))?)$");
		if(!reg.test(obj.value)){
			$("#vsn").val("");
			$("#vsnIos").val("");
		}
		return obj.value;
	}
	function checkFlag(obj){
		var reg = new RegExp("^[0-9]+$");
		if(!reg.test(obj.value)){
			$("#flag").val("");
		}
	}

///~*********************验证数字 结束 ***********************~/
//页面跳转(appSettion)	
	function appSettion(){
		$("#iframe_right",parent.window.document).attr("src","../appAction/appSetting.action");
	}
	
	
	function removeError(){
		$("#errorVsn").html("");
		$("#errorFlag").html("");
		$("#errorUrl").html("");
		$("#errorContent").html("");
		$("#errorIosVsn").html("");
		$("#errorIosContent").html("");
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//修改版本设置
	function checkClient(clientType){
		$.ajax({
			url : '../appAction/andriodVsnMsg.action',
			data : {"clientType":clientType},
			type : 'post',
			dataType : "html",
			cache : false, 
			success : function(msg){
			 msg = eval('(' + msg + ')');
				$("#vsn").val(msg.vsn);
				$("#flag").val(msg.flag);
				$("#url").val(msg.url);
				$("#vsnId").val(msg.appId);
				$("#appWXcontent").html(msg.info);
				var i =msg.ismust;
				if(i==0){
					$("#noAppUp").attr("checked",true);
				}else{
					$("#yesAppUp").attr("checked",true);
				}
			}
		})
	}
	
	function iosCheckClient(clientType){
		$.ajax({
			url : '../appAction/iosVsnMsg.action',
			data : {"clientType":clientType},
			type : 'post',
			dataType : "json",
			cache : false, 
			success : function(data){
// 				alert(data.version);
// 				 msg = eval("(" + data + ")");
				$("#vsnIos").val(data.version);
				$("#iosWXcontent").html(data.info);
				var i = data.ismust;
				if(i==0){
					$("#noNew").attr("checked",true);
				}else{
					$("#yesNew").attr("checked",true);
				}
			}
		})
	}
</script>