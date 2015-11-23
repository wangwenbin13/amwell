<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商管理-司机管理</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;司机管理
<%--<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBack();">返回</a>--%>
</p></div>
  <div class="mt10 ml20 mr20 black1">
          <s:set name="theUrl1"></s:set>
          <s:set name="theUrl2"></s:set>
          <s:set name="theUrl3"></s:set>
     <div class="table-title">
<%--	    <ul>--%>
<%--	    	<li class="on"><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(0)">司机列表</a></li>--%>
<%--	    	<li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(1)">提现申请</a></li>--%>
<%--	    	<li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(2)">发放奖励</a></li>--%>
<%--	    </ul>--%>
	    
	    <ul>
                	<s:iterator value="#request.threeLevelMenu" var="p" status="v">
                	  <s:if test="#attr.theTab==1 && #v.index==0">
                	    <li class='on'>
                	    <s:set name="theUrl1" value="#p.url"></s:set>
                	  </s:if>
                	  <s:elseif test="#attr.theTab==2 && #v.index==1">
                	    <li class='on'>
                	    <s:set name="theUrl2" value="#p.url"></s:set>
                	  </s:elseif>
                	  <s:elseif test="#attr.theTab==3 && #v.index==2">
                	    <li class='on'>
                	    <s:set name="theUrl3" value="#p.url"></s:set>
                	  </s:elseif>
                	  <s:elseif test="(#attr.theTab!=1&&#attr.theTab!=2&&#attr.theTab!=3) && #v.index==0">
                	    <li class='on'>
                	  </s:elseif>
                	  <s:else>
                	    <li>
                	  </s:else>
                			<a href="javascript:void(0)" class="f14 blue2" onclick="changePage('${p.url}.action')">${p.name }</a>
                		</li>
                	</s:iterator>
                	
                </ul>
	    
    </div>
    <div class="tab_box">
    
    
<%--    	<iframe frameborder="0" id="loadPage" name="loadPage" src="<%=basePath%>/operationDriver/driverList.action" width="100%"></iframe>--%>
    	
    	
    	              <s:if test="#attr.theTab==1">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl1'/>.action" width="100%"></iframe>
                	  </s:if>
                	  <s:elseif test="#attr.theTab==2">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl2'/>.action" width="100%"></iframe>
                	  </s:elseif>
                	  <s:elseif test="#attr.theTab==3">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl3'/>.action" width="100%"></iframe>
                	  </s:elseif>
                	  <s:else>
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#request.defultUrl'/>.action" width="100%"></iframe>
                	  </s:else>
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

	$("#loadPage").attr("src",index);


<%--	if(index == 0)--%>
<%--	{--%>
<%--		$("#loadPage").attr("src","<%=basePath%>/operationDriver/driverList.action");--%>
<%--	}--%>
<%--	else if(index == 1)--%>
<%--	{--%>
<%--	    $("#loadPage").attr("src","<%=basePath%>/operationDriver/driverApply.action");--%>
<%--	}	--%>
<%--	else if(index == 2)--%>
<%--	{--%>
<%--	    $("#loadPage").attr("src","<%=basePath%>/operationDriver/driverPayment.action");--%>
<%--	}--%>
	
}
</script>