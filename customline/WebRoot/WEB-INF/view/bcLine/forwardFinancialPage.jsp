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
<title>包车管理-财务统计</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;包车管理&nbsp;>&nbsp;财务统计</p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                     <s:iterator value="#request.threeLevelMenu" var="p" status="v">
                		<li <c:if test="${v.index == 0 }">class="on"</c:if>>
                			<a href="javascript:void(0)" class="f14 blue2" onclick="changePage('${p.url}.action')">${p.name }</a>
                		</li>
                	</s:iterator>
                </ul>
            </div>
            <div class="tab_box">
            	<iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#request.defultUrl'/>.action" width="100%"></iframe>
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