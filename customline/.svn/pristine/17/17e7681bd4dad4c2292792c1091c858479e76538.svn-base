<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理-乘客列表-乘客详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
  <div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;用户管理&nbsp;>&nbsp;乘客详情<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBack();">返回</a></p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
          	<li class='on'><a href="javascript:void(0)" class="f14 blue2" onclick="changePage('passengerDetailPage.action?passengerId=<%=request.getParameter("passengerId")%>')">乘客详情</a></li>
          	<li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage('passengerTicketList.action?passengerId=<%=request.getParameter("passengerId")%>')">订票信息</a></li>
          	<li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage('passengerReturnTicketList.action?passengerId=<%=request.getParameter("passengerId")%>')">退票记录</a></li>
          	<li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage('passengerChangeTicketList.action?passengerId=<%=request.getParameter("passengerId")%>')">改签记录</a></li>
        </ul>
    </div>
    <div class="tab_box">
      	<iframe frameborder="0" id="loadPage" name="loadPage" src="passengerDetailPage.action?passengerId=<%=request.getParameter("passengerId")%>" width="100%"></iframe>
    </div>
      
</div>
</body>
</html>
<script type="text/javascript">
//tab 切换
$(function(){
	var $div_li =$(".table-title ul li");
	$div_li.click(function(){
		$(this).addClass("on").siblings().removeClass("on"); 
	});
	
	//iframe高度
	$("#loadPage").css("min-height",$(window).height()-101+"px");
	$(window).resize(function(){
		$("#loadPage").css("min-height",$(window).height()-101+"px");	
	});
});
function changePage(url)
{
	$("#loadPage").attr("src",url);
}
//返回上一步
function goBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../customManager.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="customManager.action?level=two";
	}
	
	window.location.href=$the_url;
}
</script>
