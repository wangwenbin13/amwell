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
<title>客户投诉-乘客反馈管理</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;客户投诉&nbsp;>&nbsp;乘客反馈管理</p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
          <s:set name="theUrl1"></s:set>
          <s:set name="theUrl2"></s:set>
            <div class="table-title">
                <ul>
                	<s:iterator value="#request.threeLevelMenu" var="p" status="v">
                	  <s:if test="#attr.theTab==-1 && #v.index==0">
                	    <li class='on'>
                	    <s:set name="theUrl1" value="#p.url"></s:set>
                	  </s:if>
                	  <s:elseif test="#attr.theTab==1 && #v.index==1">
                	    <li class='on'>
                	    <s:set name="theUrl2" value="#p.url"></s:set>
                	  </s:elseif>
                	  <s:elseif test="(#attr.theTab!=-1&&#attr.theTab!=1&&#attr.theTab!=3&&#attr.theTab!=4) && #v.index==0">
                	    <li class='on'>
                	  </s:elseif>
                	  <s:else>
                	    <li>
                	  </s:else>
<%--                		${v.index == 0?"<li class='on'>":"<li>" }--%>
                			<a href="javascript:void(0)" class="f14 blue2" onclick="changePage('${p.url}.action')">${p.name }</a>
                		</li>
                	</s:iterator>
                	
                </ul>
            </div>
            <div class="tab_box">
                      <s:if test="#attr.theTab==-1">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl1'/>.action" width="100%"></iframe>
                	  </s:if>
                	  <s:elseif test="#attr.theTab==1">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl2'/>.action" width="100%"></iframe>
                	  </s:elseif>
                	  <s:else>
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#request.defultUrl'/>.action" width="100%"></iframe>
                	  </s:else>
            	
            	 
            </div>
          </div>
           
        </div>
</body>
</html>
<script type="text/javascript" src="../js/jquery/ajaxfileupload.js"></script>
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
	/**
	if(index == 0)
	{

		$("#loadPage").attr("src","../operationPassenger/passengerList.action");
	}
	else if(index == 1)
	{
	    $("#loadPage").attr("src","../operationComment/commentList.action");
	}
	*/	
}

</script>