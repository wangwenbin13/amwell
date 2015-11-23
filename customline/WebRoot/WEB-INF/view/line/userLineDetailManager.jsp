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
<title>线路管理-招募线路-用户申请线路</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;招募线路&nbsp;>&nbsp;用户申请线路<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBack();">返回</a><span class="blue1 ml25"><!--《<a href="javascript:void(0);" onclick="javascript:history.go(-1);" class="blue1">返回</a>--></span></p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(0)">用户申请线路详情</a></li>
                    <li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(1)">报名人数</a></li>
                </ul>
            </div>
            <div class="tab_box">
           		
            	<iframe frameborder="0" id="loadPage" name="loadPage" src="getUserLineDetail.action" width="100%"></iframe>
            </div>
            
          </div>
           
        </div>
</body>
</html>
<script type="text/javascript">
var backstatu = <%=request.getParameter("backstatu")%>;
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
	changePage(0);
});
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
	$("#loadPage").attr("src","../line/getUserLines.action");
	$(".table-title ul li").eq(toAdd).addClass("on").siblings().removeClass("on");
}
function changePage(index)
{
	if(index == 0)
	{

		$("#loadPage").attr("src","getUserLineDetail.action?backstatu="+backstatu);
	}
	else if(index == 1)
	{
	    $("#loadPage").attr("src","getUserLineApplyList.action");
	}
}

//返回上一步
function goBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../recruitLinesManager.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="recruitLinesManager.action?level=two";
	}
	window.location.href=$the_url;
}
</script>