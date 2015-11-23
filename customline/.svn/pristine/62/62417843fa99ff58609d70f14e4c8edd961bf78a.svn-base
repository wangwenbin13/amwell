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
	    	<div class="pop-t-l fl fw white">用户导入</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="loadPage.window.closePopAddPage();" class="pop-close fr mt4 mr4"></a></div>
	    </div>
	    <div class="pop-main p10">
	       <%--  <P class="mt10">您成功导入<em class="green1"></em>条信息，还有<em class="red1">${wrongPhoneNO }</em>条信息导入失败，请检查后再导入！</P> --%>
	        <P class="mt10">您有<em class="red1">${wrongPhoneNO }</em>条信息导入失败，请检查后再导入！</P>
	        <a href="javascript:void(0);" class="blue1"  onclick="loadPage.window.toExport();" >下载失败列表</a>	
	           
	    </div>
	    <div class="pop-main p10">
	    		 <input type="button" onclick="loadPage.window.toPassengerList();" class="display-ib btn1 white mr40 f14"  value="确定"/>   
	    </div>
	</div>
</body>
</html>
<script language="Javascript">
//关闭弹出层
function closePopAddPage()
{
	$("#popMapPage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
   
}
//导出数据
function toExport(){
	var count="${wrongPhoneNO}";
    //alert(count);
	if(count==0){
		parent.parent.showCommonTip("没有数据可导出");
		return ;
	}

    window.location.href="../passengerRegist/exportErrorData.action";

}
function toPassengerList(){
	closePopAddPage();
	//window.location.href="../operationPassenger/passengerList.action";
}
</script>
