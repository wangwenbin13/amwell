<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户、订单管理-改签</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;用户、订单管理&nbsp;>&nbsp;改签</p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(0)">订单列表</a></li>
                    <li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(1)">改签记录</a></li>
                    <li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(2)">用户申请改签</a></li>
                </ul>
            </div>
            <div class="tab_box">
            	<iframe frameborder="0" id="loadPage" name="loadPage" src="<%=path%>/changeTicket/getTicketList.action" width="100%"></iframe>
            </div>
            
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
	//加载车型品牌
	//loadBrandId();

	
	//iframe高度
	$("#loadPage").css("min-height",$(window).height()-101+"px");
	$(window).resize(function(){
		$("#loadPage").css("min-height",$(window).height()-101+"px");	
	});
});
function changePage(index)
{
	//$("#loadPage").attr("src",url);
	if(index == 0)
	{
		$("#loadPage").attr("src","<%=path%>/changeTicket/getTicketList.action");
	}
	else if(index == 1)
	{
	    $("#loadPage").attr("src","<%=path%>/changeTicket/changedTicketList.action");
	}	
	else if(index == 2)
	{
	    $("#loadPage").attr("src","<%=path%>/changeTicket/userChangeTicket.action");
	}
}
</script>