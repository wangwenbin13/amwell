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
<title>优惠券管理-优惠券统计</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out">
<p class="r-sub-nav gray2">
当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;优惠券统计
</p>
</div>
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">优惠券统计</a></li>
           </ul>
       </div>
       <s:form id="searchform" action="../couponStatistics/couponStatisticsList.action" theme="simple">
       <ul class="r-sub-sel black1 mt20 clearfix">
		   	<li class="w16p"><span class="fl">批次号：</span><s:textfield name="search.field01" cssClass="r-input w64p" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"></s:textfield></li>
		   	<li class="w16p"><span class="fl">优惠券名：</span><s:textfield name="search.field02" cssClass="r-input w64p" onkeyup="this.value=this.value.replace(/[%]/g,'')"></s:textfield></li>		   	
		   	<li>
		   	<input type="submit" class="btn-blue4" value="查找" />
        	<s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchform')"/>
        	</li>
	   </ul>
       </s:form>
      <div class="table2-text sh-ttext">  
      	<table width="100%" border="0" class="table1 nohover" id="tableDiv">
      		<tr align="center" class="th">
               <th scope="col" width="6%">批次号</th>
               <th scope="col" width="15%">优惠券名称</th>
               <th scope="col" width="6%">面值（元）</th>
               <th scope="col" width="6%">使用门槛（元）</th>
               <th scope="col" width="2%">数量</th>
               <th scope="col" width="15%">有效期</th>
               <th scope="col" width="6%">发行量</th>
               <th scope="col" width="8%">发行金额（元）</th>
               <th scope="col" width="8%">发放数量</th>
               <th scope="col" width="8%">使用数量</th>
               <th scope="col" width="8%">发放金额（元）</th>
               <th scope="col" width="8%">使用金额（元）</th>
               <th scope="col" width="8%">使用率</th>
            </tr>
            <s:if test="null==list || list.size == 0">
                   <tr align="center">
      					<td colspan="13"><p class="f13 f-yahei gray4 t-c line26 mt10">
      					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
      				</tr>
     		 </s:if>
            <s:iterator value="list" var="v" status="l">
            <tr class="tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
            	<td class="f-arial" rowspan="<s:property value='#v.statisticsList.size()'/>" style="border-right:1px solid #cddfec"><s:property value="couponGroupID"/></td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponName"/></td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponValue"/>元</td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponCon"/>元</td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).num"/></td>
            	<td class="f-arial">
            	<s:if test="%{null == #v.statisticsList.get(0).delayDays || '' == #v.statisticsList.get(0).delayDays}">
            	<s:property value="#v.statisticsList.get(0).effectiveTime"/> 至 <s:property value="#v.statisticsList.get(0).expirationTime"/>
            	</s:if>
            	<s:else>
            	券到账后有效期<s:property value="#v.statisticsList.get(0).delayDays"/>天
            	</s:else>
            	</td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponCount"/></td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponTotalValue"/>元</td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponSendCount"/></td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponUsedCount"/></td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponSendValue"/>元</td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponUsedValue"/>元</td>
            	<td class="f-arial"><s:property value="#v.statisticsList.get(0).couponUsedProbability"/></td>
            </tr>
	            <s:iterator value="#v.statisticsList" var="v1" begin="1">
		            <tr class="tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
		            	<td class="f-arial"><s:property value="#v1.couponName"/></td>
		            	<td class="f-arial"><s:property value="#v1.couponValue"/>元</td>
		            	<td class="f-arial"><s:property value="#v1.couponCon"/>元</td>
		            	<td class="f-arial"><s:property value="#v1.num"/></td>
		            	<td class="f-arial">
		            	<s:if test="%{null == #v1.delayDays || '' == #v1.delayDays}">
		            	<s:property value="#v1.effectiveTime"/> 至 <s:property value="#v1.expirationTime"/>
		            	</s:if>
		            	<s:else>
		            	券到账后有效期<s:property value="#v1.delayDays"/>天
		            	</s:else>
		            	</td>
		            	<td class="f-arial"><s:property value="#v1.couponCount"/></td>
		            	<td class="f-arial"><s:property value="#v1.couponTotalValue"/>元</td>
		            	<td class="f-arial"><s:property value="#v1.couponSendCount"/></td>
		            	<td class="f-arial"><s:property value="#v1.couponUsedCount"/></td>
		            	<td class="f-arial"><s:property value="#v1.couponSendValue"/>元</td>
		            	<td class="f-arial"><s:property value="#v1.couponUsedValue"/>元</td>
		            	<td class="f-arial"><s:property value="#v1.couponUsedProbability"/></td>
		            </tr>
	            </s:iterator>
            </s:iterator>
      	</table>
      	<!--Start page-->
		<s:if test="%{page.pageCount != 0}"><jsp:include page="../page.jsp"></jsp:include></s:if>
		<!--End page-->     
      </div>
   </div>
</body>
<script>
function showAddPage()
{
    $("#iframe_right", parent.parent.window.document).attr("src","../couponGroup/toCouponGroupAddPage.action");
}
</script>
</html>
