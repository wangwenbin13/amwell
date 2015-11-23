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
<title>优惠券管理-优惠券列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out">
<p class="r-sub-nav gray2">
当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;优惠券列表
<span style="display:inline-block; float:right; margin-right:24px;">老版优惠券入口：
<a href="../gift/forwardGiftListPage.action">礼品配置</a>
<a href="../coupon/toList.action">优惠券列表</a>
</span>
</p>
</div>
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">优惠券列表</a></li>
           </ul>
       </div>
       <s:form id="searchform" action="../couponGroup/couponGroupList.action" theme="simple">
       <ul class="r-sub-sel black1 mt20 clearfix">
		   	<li class="w16p"><span class="fl">批次号：</span><s:textfield name="search.field01" cssClass="r-input w64p" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"></s:textfield></li>
		   	<li class="w16p"><span class="fl">优惠券名：</span><s:textfield name="search.field05" cssClass="r-input w64p" onkeyup="this.value=this.value.replace(/[%]/g,'')"></s:textfield></li>
		   	<li class="w29p"><span class="fl">有效期：</span>
		   	<span class="r-input fl w36p mr4"><input type="text" name="search.field02" value="${search.field02}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w36p ml4"><input type="text" name="search.field03" value="${search.field03}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
            
<%--            <span class="r-input fl w36p mr4"><input type="text" name="search.field02" value="${search.field02}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d'})"/></span>--%>
<%--            <span class="fl">至</span>--%>
<%--            <span class="r-input fl w36p ml4"><input type="text" name="search.field03" value="${search.field03}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>--%>
		   	</li>
		   	<li class="w17p"><span class="fl">配置人名：</span><s:textfield name="search.field04" cssClass="r-input w64p" onkeyup="this.value=this.value.replace(/[%]/g,'')"></s:textfield></li>
		   	
		   	<li>
		   	<input type="submit" class="btn-blue4" value="查找" />
        	<s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchform')"/>
        	</li>
	   </ul>
       </s:form>
	   <div class="table-title mt10">
        	<a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
        </div>
      <div class="table2-text sh-ttext">  
      	<table width="100%" border="0" class="table1 nohover" id="tableDiv">
      		<tr align="center" class="th">
               <th scope="col" width="3%">序号</th>
               <th scope="col" width="10%">批次号</th>
               <th scope="col" width="6%">发行总量</th>
               <th scope="col" width="6%">人均限领份额</th>
               <th scope="col" width="15%">优惠券名</th>
               <th scope="col" width="6%">面值（元）</th>
               <th scope="col" width="6%">门槛（元）</th>
<!--               <th scope="col" width="6%">发行量</th> -->
              <th scope="col" width="6%">数量</th>
               <th scope="col" width="15%">有效期</th>
               <th scope="col" width="6%">配置人</th>
               <th scope="col" width="8%">配置时间</th>
<%--               <th scope="col">操作</th>--%>
            </tr>
            <s:if test="null==list || list.size == 0">
                   <tr align="center">
      					<td colspan="12"><p class="f13 f-yahei gray4 t-c line26 mt10">
      					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
      				</tr>
     		 </s:if>
            <s:iterator value="list" var="v" status="l">
            <tr class="tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
            	<td class="f-arial" rowspan="<s:property value='#v.couponList.size()'/>">${(page.currentPage-1)*page.pageSize+l.index+1}</td>	
            	<td class="f-arial" rowspan="<s:property value='#v.couponList.size()'/>"><s:property value="couponGroupID"/></td>
            	<td class="f-arial" rowspan="<s:property value='#v.couponList.size()'/>"><s:property value="couponGroupCount"/></td>
            	<td class="f-arial" rowspan="<s:property value='#v.couponList.size()'/>" style="border-right:1px solid #cddfec"><s:property value="averageNum"/></td>
            	<td class="f-arial"><s:property value="#v.couponList.get(0).couponName"/></td>
            	<td class="f-arial"><s:property value="#v.couponList.get(0).couponValue"/>元</td>
            	<td class="f-arial"><s:property value="#v.couponList.get(0).couponCon"/>元</td>
<%--            	<td class="f-arial"><s:property value="#v.couponList.get(0).couponCount"/></td> --%>
           	<td class="f-arial"><s:property value="#v.couponList.get(0).num"/></td>
            	<td class="f-arial">
            	<s:if test="%{null == #v.couponList.get(0).delayDays || '' == #v.couponList.get(0).delayDays}">
            	<s:property value="#v.couponList.get(0).effectiveTime"/> 至 <s:property value="#v.couponList.get(0).expirationTime"/>
            	</s:if>
            	<s:else>
            	券到账后有效期<s:property value="#v.couponList.get(0).delayDays"/>天
            	</s:else>
            	</td>
            	<td class="f-arial"><s:property value="#v.couponList.get(0).userName"/></td>
            	<td class="f-arial"><s:property value="#v.couponList.get(0).createOn"/></td>
<%--            	<td>--%>
<%--            	<a href="javascript:void(0);" class="blue1">查看详情</a>--%>
<%--            	<a href="javascript:void(0);" class="blue1">生成优惠码</a>--%>
<%--            	</td>--%>
            </tr>
	            <s:iterator value="#v.couponList" var="v1" begin="1">
		            <tr class="tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
		            	<td class="f-arial"><s:property value="#v1.couponName"/></td>
		            	<td class="f-arial"><s:property value="#v1.couponValue"/>元</td>
		            	<td class="f-arial"><s:property value="#v1.couponCon"/>元</td>
<%-- 		            	<td class="f-arial"><s:property value="#v1.couponCount"/></td> --%>
		            	<td class="f-arial"><s:property value="#v1.num"/></td>
		            	<td class="f-arial">
		            	<s:if test="%{null == #v1.delayDays || '' == #v1.delayDays}">
		            	<s:property value="#v1.effectiveTime"/> 至 <s:property value="#v1.expirationTime"/>
		            	</s:if>
		            	<s:else>
		            	券到账后有效期<s:property value="#v1.delayDays"/>天
		            	</s:else>
		            	</td>
		            	<td class="f-arial"><s:property value="#v1.userName"/></td>
		            	<td class="f-arial"><s:property value="#v1.createOn"/></td>
<%--		            	<td>--%>
<%--		            	<a href="javascript:void(0);" class="blue1">查看详情</a>--%>
<%--		            	<a href="javascript:void(0);" class="blue1">生成优惠码</a>--%>
<%--		            	</td>--%>
		            </tr>
	            </s:iterator>
            </s:iterator>
      	</table>
      	<!--Start page-->
		<s:if test="%{page.pageCount != 0}"><jsp:include page="../page.jsp"></jsp:include></s:if>
		<!--End page-->     
		<form method="post" style="display: none;" id="turnPage">
	   	<input type="text" name="search.field01" value="${search.field01}"/>
	    <input type="text" name="search.field02" value="${search.field02}"/>
	    <input type="text" name="search.field03" value="${search.field03}"/>
	    <input type="text" name="search.field04" value="${search.field04}"/>
	    <input type="text" name="search.field05" value="${search.field05}"/>
    	<input type="text" name="currentPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
  		</form>   
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
