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
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;优惠券列表</p></div>
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">优惠券列表</a></li>
           </ul>
       </div>
       <s:form id="searchform" action="../couponInfo/couponInfoList.action" theme="simple">
        <ul class="r-sub-sel black1 mt20 clearfix">
		   	<li class="w16p"><span class="fl">优惠券名：</span><s:textfield name="search.field01" cssClass="r-input w64p"></s:textfield></li>
		   	<li class="w16p"><span class="fl">面值：</span><s:textfield name="search.field02" cssClass="r-input w67p"></s:textfield></li>
		   	<li class="w16p"><span class="fl">门槛：</span><s:textfield name="search.field03" cssClass="r-input w67p"></s:textfield></li>
		   	<li class="w16p"><span class="fl">组合券名：</span><s:textfield name="search.field05" cssClass="r-input w64p"></s:textfield></li>
		   	<li class="w16p"><span class="fl">配置人名：</span><s:textfield name="search.field04" cssClass="r-input w64p"></s:textfield></li>
		   	<li>
		   	<input type="submit" class="btn-blue4 ml10" value="查找" />
            <s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchform')"/>
        	</li>
	   </ul>
       </s:form>
	   <div class="table-title mt10">
        	<a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
        </div>
      <div class="table2-text sh-ttext">  
      	<table width="100%" border="0" class="table1" id="tableDiv">
      		<tr align="center" class="th">
               <th scope="col" width="3%">序号</th>
               <th scope="col" width="10%">id</th>
               <th scope="col">优惠券名</th>
               <th scope="col" width="10%">面值</th>
               <th scope="col" width="10%">门槛</th>
               <th scope="col" width="10%">后延天数</th>
               <th scope="col" width="10%">组合券名</th>
               <th scope="col" width="10%">在组合券中的数量</th>
               <th scope="col" width="10%">配置时间</th>
               <th scope="col" width="10%">配置人</th>
               <th scope="col" width="6%">操作</th>
            </tr>
            <s:if test="null==list || list.size == 0">
                   <tr align="center" class="bg1">
      					<td colspan="11"><p class="f13 f-yahei gray4 t-c line26 mt10">
      					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
      				</tr>
     		 </s:if>
            <s:iterator value="list" var="v" status="l">
            <tr class="bg1 tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
            	<td class="f-arial">${(page.currentPage-1)*page.pageSize+l.index+1}</td>	
            	<td class="f-arial"><s:property value="id"/></td>
            	<td class="f-arial"><s:property value="couponName"/></td>
            	<td class="f-arial"><s:property value="couponValue"/>元</td>
            	<td class="f-arial"><s:property value="couponCon" />元</td>
            	<td class="f-arial"><s:property value="delayDays"/></td>
            	<td class="f-arial"><s:property value="couponGroupName"/></td>
            	<td class="f-arial"><s:property value="num"/></td>
            	<td class="f-arial"><s:property value="createOn"/></td>
            	<td class="f-arial"><s:property value="userName"/></td>
            	<td>
            	<a href="javascript:void(0);" class="blue1" onclick="delCoupon(${v.id});">删除</a>
            	</td>
            </tr>
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
//增加弹出层页面
function showAddPage()
{
    $("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#mainBody").show();
    $("#showPage").load("../couponInfo/toCouponAddPage.action"); 
}

//删除优惠券
function delCoupon(couponId)
{
	parent.parent.showPopCommonPage("确定要删除此优惠券吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();

		$.ajax({
	           url:'../couponInfo/couponDel.action?couponId='+couponId+'&temp='+new Date(),
	           dataType:'text',
	           cache:false,//不从浏览器缓存中取
	           async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
	           success:function(data){
              	  if(data==1){
              		 parent.parent.showRturnTip("删除成功",true);
	                  //刷新本页
	            	 $("#turnPage").attr("action","../couponInfo/couponInfoList.action");
	            	 //刷新当前页信息
		             $("#turnPage").submit(); 
	              }
              	  if(data==0){
              		parent.parent.showRturnTip("优惠券与组合券关联，不能删除",false);
                  }
              	  if(data==-1){
              		parent.parent.showRturnTip("删除失败",false);
                  }
	           }
			});

		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
	});
}
</script>
</html>
