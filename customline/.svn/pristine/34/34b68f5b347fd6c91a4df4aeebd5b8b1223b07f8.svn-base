<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>包车管理-车票退票</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;包车管理&nbsp;>&nbsp;车票退票</p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                  <s:if test="#attr.theTab==1">
                    <li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(0)">包车订单列表</a></li>
                    <li class="on" id="li_2"><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(1)">包车退票记录</a></li>
	              </s:if>
	              <s:else>
	                <li class="on"><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(0)">包车订单列表</a></li>
                    <li id="li_2"><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(1)">包车退票记录</a></li>
	              </s:else>
                	
                </ul>
            </div>
            <div class="tab_box">
              <s:if test="#attr.theTab==1">
                <iframe frameborder="0" id="loadPage" name="loadPage" src="../bcReturnTicket/getBcReturnedList.action" width="100%"></iframe>
              </s:if>
              <s:else>
                <iframe frameborder="0" id="loadPage" name="loadPage" src="../bcReturnTicket/getBcReturnOrderList.action" width="100%"></iframe>
              </s:else>
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
	//iframe高度
	$("#loadPage").css("min-height",$(window).height()-101+"px");
	$(window).resize(function(){
		$("#loadPage").css("min-height",$(window).height()-101+"px");	
	});
});
function changePage(index)
{
	if(index == 0)
	{
		$("#loadPage").attr("src","../bcReturnTicket/getBcReturnOrderList.action");  //订单列表
	}
	else if(index == 1)
	{
	    $("#loadPage").attr("src","../bcReturnTicket/getBcReturnedList.action");  //退票记录
	}	
}
</script>