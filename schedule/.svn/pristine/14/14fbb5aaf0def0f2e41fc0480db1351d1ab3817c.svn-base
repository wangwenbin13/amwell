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
<title>系统设置-个人信息</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;个人信息<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 mr28">
      <p class="fw f14 f-yahei">个人信息</p>
      <ul class="clearfix mt40">
      	<li class="clearfix"><span class="self-name fw f20 t-c fl black3" title="${userInfo.userName}"><s:property value="#session.userInfo.userName==''?'暂无':#session.userInfo.userName" /></span></li>
      	<li class="clearfix mt11"><span class="w38p t-l fw black2 f13 f-arial"><em class="sys-info-ico mr4"></em><s:property value="#session.userInfo.telephone==''?'暂无信息':#session.userInfo.telephone" /></span></li>
          <li class="clearfix mt40"><span class="w75 t-l">登录名：</span><span class="w38p t-l"><s:property value="#session.userInfo.loginName" /></span></li>
          <li class="clearfix"><span class="w75 t-l">状&nbsp;态：</span><span class="w38p t-l"><s:property value="#session.userInfo.status==1?'启用':'禁用'" /></span></li>
          <li class="clearfix"><span class="w42 t-l fl">备&nbsp;注：</span><span class="w38p t-l fl mt4 line22"><textarea id="content" style="height:350px;"></textarea></span></li>
          <li class="clearfix" style="display:none;"><span class="w42 t-l fl" id="hideRemark"><s:property value="#session.userInfo.remark==''?'暂无信息':#session.userInfo.remark" /></span></li>
      </ul>
</div>
</body>
</html>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script>
	//创建文字编辑器
	var editor;
	KindEditor.ready(function(K) {
		var options = {
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist']
		}
		editor = K.create("#content",options);
		editor.readonly(true);
		editor.html($("#hideRemark").text());
	});
</script>