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
<title>App消息详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;App消息&nbsp;>&nbsp;App消息详情<a class="display-ib btn ml10" href="javascript:history.back();">返回</a></p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">消息详情</a></li>
        </ul>
    </div>
    <div class="sh-add-new map-shebei-li gray2 f12" >
    <form action="" id="ajaxForm" method="post"></form>
    <s:iterator value="msgModel" var="app" status="s">
    	<ul class="marLi clearfix f12">
            <li><span class="fl w62 t-r">发送时间：</span>${app.createOn }</li>
            <li><span class="fl w62 t-r">操作人：</span>${app.createBy }</li>
            <li><span class="fl w62 t-r">发送数量：</span>${app.msgCount }</li>
            <li class="widthfull" style="height:auto;"><span class="fl w62 t-r">短信内容：</span><div style="overflow:hidden;">${app.msgContext }</div></li> 
        </ul>
         </s:iterator>
         <div class="f12">
	        <span class="fl w62 t-r">发送人群：</span>
	        <div class="marketTable" style="overflow:hidden;">
		        <table width="100%" border="0" class="table1" id="tableDiv">
			           <tr align="center" class="th">
		             <th scope="col" width="12%">手机号码</th> 
					 <th scope="col" width="8%">性别</th>
		             <th scope="col" width="10%">乘客昵称</th>
		             <th scope="col" width="15%">乘坐日期</th>
		             <th scope="col" width="8%">班次</th>                   
		             <th scope="col">线路名称</th>
		           </tr>
		           <s:iterator value="msgModel.passengerList" var="passenger" status="s">
		           <tr align="center">
		           		<td>${passenger.sendPhoneNo }</td>
		           		<s:if test="%{#passenger.nickName==null }">
		           			<td></td>	
		           		</s:if>
		           		<s:else>
			           		<s:if  test="%{#passenger.sex==0}"><td >男</td></s:if>
			           		<s:else><td>女</td></s:else>
		           		</s:else>
		           		<td>${passenger.nickName }</td>
		           		<td>${app.orderDate }</td>
		           		<td>${app.orderStartTime }</td>
		           		<td>${app.lineName }</td>
		           </tr>
		        </s:iterator>
		         </table>
	         </div>
         </div>
         <!--Start page-->
               <div class="page t-c mt20 clearfix" id="pageDiv">
                 <s:if test="%{currentPageIndex!=0}">
                		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
                  		<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
                 </s:if>
                    <ul id="pageNumDiv">
                    		<s:iterator value="page.pageList">
				<s:if test="field02==1">
					<b><span class="current"><s:property value="field01" />
					</span>
					</b>
				</s:if>
				<s:else>
					<li>
						<a href="javascript:void(0);" onclick="toPage(${field03});"><s:property value="field01" /></a>
					</li>
				</s:else>
			</s:iterator>
                    </ul>
                     <s:if test="%{page.pageCount!=0 && ((currentPageIndex/pageSize)+1)!=page.pageCount}">
                    		<a href="javascript:void(0);" onclick="toPage(${page.nextIndex});">下一页</a>
                    		<a href="javascript:void(0);" onclick="toPage(${page.lastIndex});">末页</a>
                    </s:if>
                    <s:if test="%{page.pageCount!=1 && page.pageCount!=0}">
                    		<span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onblur="toJudgePage(this.value);"/>页</span>
                    </s:if>
                    <s:if test="%{page.pageCount!=0}">
                    		<span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页]</span>
                    </s:if>
              </div>
            <!--End page-->
              <p class="t-c mt20"><input type="button" value="返回" onclick="history.back();"  class="display-ib btn1 white"/></p>
    </div>
</div>
</body>
<script type="text/javascript">
$(function(){
	$(".sh-add-new").css("height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("height",$(window).height()-130+"px");	
	})
})
</script>
</html>
