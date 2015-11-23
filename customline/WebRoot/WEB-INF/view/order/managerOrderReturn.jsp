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
<title>用户、订单管理-退票</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;用户、订单管理&nbsp;>&nbsp;退票</p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
          <s:set name="theUrl1"></s:set>
          <s:set name="theUrl2"></s:set>
          <s:set name="theUrl3"></s:set>
          <s:set name="theUrl4"></s:set>
          
           <div class="table-title">
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
                	  <s:elseif test="#attr.theTab==4 && #v.index==3">
                	    <li class='on'>
                	    <s:set name="theUrl4" value="#p.url"></s:set>
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
                      <s:if test="#attr.theTab==1">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl1'/>.action" width="100%"></iframe>
                	  </s:if>
                	  <s:elseif test="#attr.theTab==2">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl2'/>.action" width="100%"></iframe>
                	  </s:elseif>
                	  <s:elseif test="#attr.theTab==3">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl3'/>.action" width="100%"></iframe>
                	  </s:elseif>
                	  <s:elseif test="#attr.theTab==4">
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#theUrl4'/>.action" width="100%"></iframe>
                	  </s:elseif>
                	  <s:else>
                	    <iframe frameborder="0" id="loadPage" name="loadPage" src="<s:property value='#request.defultUrl'/>.action" width="100%"></iframe>
                	  </s:else>
            	
            	 
            </div>
          
          <!-- 
            <div class="table-title">
                <ul>
                    <li <s:if test="#attr.theTab==1">class="on"</s:if>><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(0)">订单列表</a></li>
                    <li <s:if test="#attr.theTab==2">class="on"</s:if>><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(1)">退票记录</a></li>
                    <li <s:if test="#attr.theTab==3">class="on"</s:if>><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(3)">乘客申请退票列表</a></li>
                </ul>
            </div>
            <div class="tab_box">
              <s:if test="#attr.theTab==1">
                <iframe frameborder="0" id="loadPage" name="loadPage" src="../returnTicket/getReturnTicketList.action" width="100%"></iframe>
              </s:if>
              <s:if test="#attr.theTab==2">
              	 <iframe frameborder="0" id="loadPage" name="loadPage" src="../returnTicket/getRecordTicketList.action" width="100%"></iframe>
              </s:if>
              <s:if test="#attr.theTab==3">
                <iframe frameborder="0" id="loadPage" name="loadPage" src="../returnTicket/applyReturnTicketList.action" width="100%"></iframe>
              </s:if>
            </div>
          </div>
            -->
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
	/**
	if(index == 0)
	{
		$("#loadPage").attr("src","../returnTicket/getReturnTicketList.action");  //订单列表
	}
	else if(index == 1)
	{
	    $("#loadPage").attr("src","../returnTicket/getRecordTicketList.action");  //退票记录
	}else
	{
		 $("#loadPage").attr("src","../returnTicket/applyReturnTicketList.action");  //乘客申请退票列表
	}
	**/	
}
</script>