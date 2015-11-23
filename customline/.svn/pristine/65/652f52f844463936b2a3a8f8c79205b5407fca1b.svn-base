<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>运营平台－营销管理-礼品删除弹窗</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <div class="pop black1" style="width:400px;background:#fff;" id="PopDateDetailPage">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white" style="width:331px;">提示</div>
	        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onclick="closePopDateDetailPage();"></a></div>
	    </div>
	    <div class="pop-main p10 clearfix">
	    	<div class="sh-add dateList">
		    	<div class="mb10 f14">
		    		礼品正在使用中，无法删除
		    	</div>
		    	
	        <p class="t-c mt20">
		        <input type="button" value="确&nbsp;&nbsp;定" class="display-ib btn-blue4 white" onclick="closePopDateDetailPage();" />
		        <%--<input type="button" value="取&nbsp;&nbsp;消" class="display-ib btn-blue4 white ml20" onclick="closePopDateDetailPage();" />--%>
	        </p>   
	    </div>
    </div>
 </div>
  </body>
</html>
<script type="text/javascript">

//关闭增加弹出层页面
function closePopDateDetailPage()
{
	$("#PopDateDetailPage").hide();
    $("#mainBody").hide();
    $("#topHide", window.parent.parent.document).hide();
    $("#leftHide", window.parent.parent.document).hide();
}
</script>
