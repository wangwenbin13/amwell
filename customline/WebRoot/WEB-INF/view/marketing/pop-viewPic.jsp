<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－创建线路-添加站点图片-图片预览</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
  </head>
  
  <body>
    <div class="pop black1 w500" id="popAddPagePic">
	<div class="pop-t">
    	<div class="pop-t-l fl fw white">图片预览</div>
        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onclick="loadPage.window.closePopPicView();"></a></div>
    </div>
    <div class="pop-main appSendImgBig p10">
    	<img alt="" src="images/msg_img_bg1.png" />
    </div>
</div>
  </body>
</html>
<script type="text/javascript">
//关闭增加弹出层页面
function closePopPicView()
	{
		$("#popAddPagePic",parent.window.document).hide();
	    $("#mainBody",parent.window.document).hide();
	    $("#topHide", parent.parent.window.document).hide();
	    $("#leftHide", parent.parent.window.document).hide();
	}
</script>

