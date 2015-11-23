<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户、订单管理-改签-改签记录</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
  <div class="mt10 black1">
  
   <form action="../changeTicket/changedTicketList.action" method="post" id="searchForm">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
   	 		<li class="w25p">
           	<span class="fl">操作时间：</span>
               <span class="r-input fl w31p mr10"><input type="text"  name="search.field01" value="${search.field01}"  class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w31p ml10"><input type="text"  name="search.field02" value="${search.field02}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
		<li class="w14p">
        	<span class="fl">订单号：</span><input type="text" name="search.field03" value="${search.field03}" maxLength="35" class="fl r-input gray2 w63p mt2" />   
        </li>
        <li class="w14p">
        	<span class="fl">联系方式：</span>
        	<input type="text" name="search.field04" value="${search.field04}" maxLength="16" class="fl r-input gray2 w58p mt2" />
		</li>
        <li class="w14p">
        	<span class="fl">操作方式：</span>
        	<s:select cssClass="w61p p3" list="#{0:'系统处理',1:'人工处理'}" theme="simple" name="search.field06">
	        </s:select>
		</li>
         
        <li class="w28p">
        	<span class="fl">线路名称：</span><input type="text" name="search.field05" value="${search.field05}" maxLength="24" class="fl r-input gray2 w24p mt2" />  
        	<input type="submit" class="btn-blue4 ml20" value="查找" /> 
        	<s:reset value="重置" cssClass="btn-blue4" onclick="clearFormValue('searchForm')" theme="simple"/>
        </li>      	
  	</ul>   
  </form> 
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
            <table width="100%" border="0" class="table1">
              <tr align="center">
              	<th scope="col" width="4%">序号</th>
                <th scope="col" width="14%">订单号</th>
                <th scope="col" width="10%">乘客</th>
                <th scope="col" width="14%">联系方式</th>
                <th scope="col">线路名称</th>
                <th scope="col" width="10%">改签前</th>
                <th scope="col" width="10%">改签后</th>
                <th scope="col" width="8%">操作人</th>
                <th scope="col" width="11%">操作时间</th>
              </tr>
             <s:if test="null==list || list.size == 0">
		              <tr align="center" class="bg1">
		   					<td colspan="12"><p class="f13 f-yahei gray4 t-c line26 mt10">
		   					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
		   			  </tr>
   			  </s:if>
              <s:iterator value="list" var="passengerChangeOrder" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
						<td>${s.index+1}</td>
						<td class="f-arial">${passengerChangeOrder.orgLeaseOrderId}</td>
						<td class="f-arial">${passengerChangeOrder.displayId}/${passengerChangeOrder.nickName}</td>               	
                    	<td class="f-arial">${passengerChangeOrder.telephone}</td>
                    	<td class="f-arial">${passengerChangeOrder.orgLineName}</td>
                    	<td class="f-arial">${passengerChangeOrder.orgOrderDate}&nbsp;${passengerChangeOrder.orgOrderStartTime}</td>
                    	<td class="f-arial">${passengerChangeOrder.newOrderDate}&nbsp;${passengerChangeOrder.newOrderStartTime}</td>        
                    	<td class="f-arial">${passengerChangeOrder.createBy}</td>
                    	<td class="f-arial">${passengerChangeOrder.createOn}</td>
                        </tr>
            </s:iterator>
            </table>                  
          	</div>
       		 <!--Start page-->
                   <div class="page t-c mt20  fl widthfull" id="pageDiv">
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
                       <s:if test="%{page.pageCount!=0 && ((currentPageIndex/page.pageSize)+1)!=page.pageCount}">
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
          </div>   
</div>
<form action="../changeTicket/changedTicketList.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="search.field01" value="${search.field01}"/>
  <input type="text" name="search.field02" value="${search.field02}"/>
  <input type="text" name="search.field03" value="${search.field03}"/>
  <input type="text" name="search.field04" value="${search.field04}"/>
  <input type="text" name="search.field05" value="${search.field05}"/>
  <input type="text" name="search.field06" value="${search.field06}"/>
  <input type="text" name="currentPageIndex" id="currentPageIndex"/>
</form>   
</body>
<script type="text/javascript">
//翻页方法
function toPage(value){
	$("#currentPageIndex").val(value);
	$("#turnPage").submit();
}

//判断输入的参数是否符合规定
function toJudgePage(value){
	var totalPage = "${page.pageCount}";
	totalPage = parseInt(totalPage);
	if(""==value){
		return;
	}
	value = parseInt(value);
	if(value<1){
		parent.parent.showCommonTip("请输入大于0的数!");
		return;
	}else if(value>totalPage){
		parent.parent.showCommonTip("超出最大页数!");
		return;
	}
	var pageSize = "${pageSize}";
	toPage((value-1)*pageSize);
}
/*分页enter按键处理函数*/
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		var currPage = $("#goPageNum").val();
        //输入为空不跳转
        if(currPage == "" || typeof(currPage) == "undefined")
    	{
    		return false;
    	}
	    toJudgePage(currPage);
		break;
	default:
		break;
	}
}

var ua = window.navigator.userAgent;
//判断浏览器类型
if (ua.indexOf("MSIE") >= 1 || ua.indexOf("Chrome") >= 1){
	//禁止后退键  作用于IE、Chrome  
	document.onkeydown = pressKey;
}else if(ua.indexOf("Firefox") >= 1){
	//禁止后退键 作用于Firefox、Opera  
	document.onkeypress = pressKey;  
}else{
	document.onkeypress = pressKey;  
}

//检查跳页是否大于总页数
function checkGoPageNum(pageNum)
{
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(parseInt(pageNum) > parseInt(totalPage))
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}
</script>
</html>