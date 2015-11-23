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
<title>优惠券管理-规则列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;规则列表</p></div>
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">规则列表</a></li>
           </ul>
       </div>
       <s:form id="searchform" action="../couponRule/couponRuleList.action" theme="simple">
       <ul class="r-sub-sel black1 mt20 clearfix">
		   	<li class="w16p"><span class="fl">规则名称：</span><s:textfield name="search.field01" cssClass="r-input w64p"></s:textfield></li>
		   	<li class="w16p"><span class="fl">配置人名：</span><s:textfield name="search.field02" cssClass="r-input w64p"></s:textfield></li>
		   	<li><input type="submit" class="btn-blue4" value="查找" />
        		<input type="reset" value="重置" class="btn-blue4"  onclick="clearFormValue('searchform')"/>
        	</li>
	   </ul>
	   </s:form>
      <div class="table2-text sh-ttext mt20">  
      	<table width="100%" border="0" class="table1" id="tableDiv">
      		<tr align="center" class="th">
               <th scope="col" width="3%">序号</th>
               <th scope="col" width="10%">id</th>
               <th scope="col">规则名称</th>
               <th scope="col" width="15%">条件</th>
               <th scope="col" width="15%">条件值</th>
               <th scope="col" width="12%">配置时间</th>
               <th scope="col" width="10%">配置人</th>
            </tr>
            <s:if test="null==list || list.size == 0">
                   <tr align="center" class="bg1">
      					<td colspan="7"><p class="f13 f-yahei gray4 t-c line26 mt10">
      					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
      				</tr>
     		 </s:if>
            <s:iterator value="list" var="v" status="l">
            <tr class="bg1 tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
            	<td class="f-arial">${(page.currentPage-1)*page.pageSize+l.index+1}</td>	
            	<td class="f-arial"><s:property value="id"/></td>
            	<td class="f-arial"><s:property value="ruleName"/></td>
            	<td class="f-arial"><s:property value="theCondition"/></td>
            	<td class="f-arial"><s:property value="theValue" /></td>
            	<td class="f-arial"><s:property value="createOn"/></td>
            	<td class="f-arial"><s:property value="userName"/></td>
            </tr>
            </s:iterator>
      	</table>    
      	<!--Start page-->
		<s:if test="%{page.pageCount != 0}"><jsp:include page="../page.jsp"></jsp:include></s:if>
		<!--End page--> 
      </div>
   </div>
</body>
</html>
