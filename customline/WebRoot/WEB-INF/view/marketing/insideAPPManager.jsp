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
<title>营销管理-站内信</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;站内信</p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	
                     <s:iterator value="#request.threeLevelMenu" var="p" status="v">
                		${v.index == 0?"<li class='on'>":"<li>" }
                			<a href="javascript:void(0)" class="f14 blue2" onclick="changePage('${p.url}.action')">${p.name }</a>
                		</li>
                	</s:iterator>
                </ul>
            </div>
            <div class="tab_box">
           		
            	 <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#request.defultUrl'/>+.action" width="100%"></iframe>
            </div>
            
          </div>
           
        </div>
</body>
</html>
<script type="text/javascript">
/*
 * getURLParameter函数是根据参数名获取url中的参数;
 * 1个必传参数
 * @paraName    
 * @return {字符串} 参数的值
 */
function getURLParameter(param){
	var url = window.location.href;
    var params = (url.substr(url.indexOf("?") + 1)).split("&");
    if (params != null) {
        for (var i = 0; i < params.length; i++) {
            var strs = params[i].split("=");
            if (strs[0] == param && strs[1]) {
                return strs[1];
            }
        }
    }
    return "";
}
 
 //代表从添加页面过来的
var toAdd = ("" == getURLParameter("toAdd")) ? "" : parseInt(getURLParameter("toAdd"));
if(toAdd != "")
{
	$("#loadPage").attr("src","../insideMessage/insideAPPTemplate.action");
	$(".table-title ul li").eq(toAdd).addClass("on").siblings().removeClass("on");
}

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
function changePage(url)
{
	$("#loadPage").attr("src",url);
	
}
</script>