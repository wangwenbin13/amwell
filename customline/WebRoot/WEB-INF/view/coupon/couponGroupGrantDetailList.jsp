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
<title>优惠券管理-用户优惠券</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;用户优惠券</p></div>
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">用户优惠券</a></li>
           </ul>
       </div>
       <s:form id="searchform" action="../couponGroupGrantDetail/couponGroupGrantDetailList.action" theme="simple">
       <ul class="r-sub-sel black1 mt20 clearfix">
		   	<li class="w16p"><span class="fl">手机号码：</span><s:textfield name="search.field01" cssClass="r-input w64p" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"></s:textfield></li>
		   	<li>
		   	<input type="submit" class="btn-blue4" value="查找" />
        	<s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchform')"/>
        	</li>
	   </ul>
       </s:form>
      <div class="table2-text sh-ttext">  
      	<table width="100%" border="0" class="table1 nohover" id="tableDiv">
      		<tr align="center" class="th">
               <th scope="col" width="3%">序号</th>
               <th scope="col" width="10%">批次号</th>
               <th scope="col" width="15%">优惠券名</th>
               <th scope="col" width="6%">面值（元）</th>
               <th scope="col" width="6%">使用门槛（元）</th>
               <th scope="col" width="15%">有效期</th>
               <th scope="col" width="6%">到账时间</th>
               <th scope="col" width="8%">使用状态</th>
               <th scope="col" width="8%">使用时间</th>
               <th scope="col" width="8%">使用订单号</th>
            </tr>
            <s:if test="null==list || list.size == 0">
                   <tr align="center">
      					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
      					<span class="index-nodate mr10"></span><span id="noDate">请输入手机号码进行查找</span></p></td>
      				</tr>
     		 </s:if>
            <s:iterator value="list" var="v" status="l">
            <tr class="tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
            	<td class="f-arial">${(page.currentPage-1)*page.pageSize+l.index+1}</td>	
            	<td class="f-arial"><s:property value="couponGroupId"/></td>
            	<td class="f-arial"><s:property value="couponName"/></td>
            	<td class="f-arial"><s:property value="couponValue"/>元</td>
            	<td class="f-arial"><s:property value="couponCon"/>元</td>
            	<td class="f-arial"><s:property value="effectiveTime"/> 至 <s:property value="expirationTime"/></td>
            	<td class="f-arial"><s:property value="getTime"/></td>
            	<td class="f-arial">
            	<s:if test="%{useState == 'unused'}">未使用</s:if>
            	<s:elseif test="%{useState == 'used'}">已使用</s:elseif>
            	<s:else>已过期</s:else>
            	</td>
            	<td class="f-arial"><s:property value="useTime"/></td>
            	<td class="f-arial">
<%--            	<a href="javascript:showOrderDetail(${orderId})">--%>
            	<s:property value="orderId"/>
<%--            	</a>--%>
            	</td>
            </tr>
            </s:iterator>
      	</table>
      	<!--Start page-->
		<s:if test="%{page.pageCount != 0}"><jsp:include page="../page.jsp"></jsp:include></s:if>
		<!--End page-->      
      </div>
   </div>

<script>
function showOrderDetail(orderId)
{
    $("#iframe_right", parent.parent.window.document).attr("src","../orderAction/orderDetail.action?leaseOrderModelVo.leaseOrderId="+orderId);
<%--    $("#iframe_right",parent.window.document).attr("src","../orderAction/orderDetail.action?leaseOrderModelVo.leaseOrderId="+leaseOrderId+"&currentPageIndex="+$("#myCurrentPageIndex").val());--%>
}
</script>
</body>
</html>
