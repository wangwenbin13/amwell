<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<title>首页</title>
<%@include file="resource.jsp" %>
<script type="text/javascript" >
$(function(){
	//浏览器可视窗口的的高度
	$(".leftbar_bg,.leftbar").css("height",($(window.parent).height()-125+"px"));
	$(window).resize(function() {
		$(".leftbar_bg,.leftbar").css("height",($(window.parent).height()-125+"px"));
	});  
	openOneMenu();
});

//默认打开第一个菜单
function openOneMenu()
{
	 $("#leftmenua0").next().show();
     //点击顶部菜单，左侧父菜单，增加active样式	
	 $("#leftmenua0").addClass("active");
	 //点击顶部菜单，左侧相对应子菜單顯示選中
	 $("#leftmenua0").next().find("li").eq(0).addClass("cur");
   	 //右侧ifream展示内容
   	 var url = $("#leftmenua0").next().find("li").find("a").attr("url");
   	 $("#iframe_right").attr("src","");  
}

function changePage(url,obj){
	
	parent.document.getElementById("iframe_right").src = url+"&date="+new Date().getTime();
	
	$("#leftmenu ul li").removeClass("cur");
	$(obj).parent().addClass("cur"); 
}
</script>
</head>
<body>
<div class="leftbar_bg" id="leftbar_bg">
  <div class="leftbar">
   	<ul id="leftmenu">
   	
        <s:iterator value="mainMenu" status="status">
        	 <li class="left-first-li"><a href="javascript:void(0);" class="fw menubar f13" id="leftmenua${status.index}"><span class="arrow_slid fr"></span>${name }</a>
				<ul class="sub_leftbar">
					<s:iterator value="childPermission" >
							<li><a href="javascript:void(0);" url="${url}.action?level=two" onclick="changePage('${url}.action?level=two',this);"><span class="${iconUrl}"></span>${name }</a></li>
					</s:iterator>
				</ul>
		    </li>
        </s:iterator>
				
        </ul>
    </div>  
</div>
</body>
</html>