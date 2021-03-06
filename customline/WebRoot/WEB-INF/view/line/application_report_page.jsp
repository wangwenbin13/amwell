<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!doctype html>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>定制记录</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;定制记录</p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                    <c:set var="myUrl"></c:set>
                    <c:forEach items="${threeLevelMenu}" var="p" varStatus="status">
                    
                        <c:if test="${status.index==0}">
                        	<c:set var="myUrl" value="${p.url}.action"></c:set>
                        </c:if>
                		<li <c:if test="${theTab==1&&status.index==0}">class="on"</c:if><c:if test="${theTab==2&&status.index==1}">class="on"</c:if>>
                			<a href="javascript:void(0)" class="f14 blue2" onclick="changePage('${p.url}.action')">${p.name}</a>
                		</li>
                	</c:forEach>
                </ul>
            </div>
            <div class="tab_box">
                  <iframe frameborder="0" id="loadPage" name="loadPage" src="${myUrl}" width="100%"></iframe>
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
function changePage(url)
{
	$("#loadPage").attr("src",url);
	
}
</script>