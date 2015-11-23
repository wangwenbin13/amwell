<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>运营平台－乘客管理-订票信息-乘客日期信息</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <div class="pop black1" style="width:400px;background:#fff;" id="PopDateDetailPage">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white" style="width:331px;">
	    	<s:if test="#request.theListType==1">乘坐日期</s:if>
	    	<s:else>退票日期</s:else>
	    	</div>
	        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onclick="loadPage.window.closePopDateDetailPage();"></a></div>
	    </div>
	    <div class="pop-main p10 clearfix">
	    	<div class="sh-add dateList">
		    	<div class="mb10 clearfix">
		    		<p class="fw">
		    		<s:if test="#request.theListType==1">乘坐日期：</s:if>
	    	        <s:else>退票日期：</s:else>
		    		</p>
			    	<ul class="dateDetailList">
			    	  <s:iterator value="#request.dateList" var="theDate">
			    	    <li class="mr24">${theDate}</li>
			    	  </s:iterator>

			    	</ul> 
		    	</div>
		    	
	        <p class="t-c mb20 mt20">
		        <input type="button" value="确&nbsp;&nbsp;定" class="display-ib btn1 white f14 mr20" onclick="loadPage.window.closePopDateDetailPage();" />
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
    $("#PopDateDetailPage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}
</script>
