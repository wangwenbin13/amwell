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
    <div class="pop black1" style="width:500px;" id="popPicView">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white">图片预览</div>
	        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onclick="loadPage.window.closePopPicView();"></a></div>
	    </div>
	    <div class="pop-main p10 viewPic">
	    	<div class="viewPicInner"><span></span><img alt="" src="<s:property value="#request['filePath']"/>" style="max-width:460px;" onclick="loadPage.window.closePopPicView();"/></div>
	    </div>
	</div>
  </body>
</html>
<script type="text/javascript">
var isLinePic=null;
$(function(){
   isLinePic = "<%=request.getParameter("isLinePic")%>";
   
});
//关闭增加弹出层页面
function closePopPicView()
{
    $("#popPicView",parent.window.document).hide();
    if(isLinePic!=null&&isLinePic!="null"&&isLinePic.length>0&&isLinePic=="true"){
      $("#mainBody",parent.window.document).hide();
      $("#topHide",parent.parent.window.document).hide();
      $("#leftHide",parent.parent.window.document).hide();
    }
  
}
</script>

