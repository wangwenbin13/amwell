<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户、订单管理-退票-退票记录</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
  <div class="mt10 black1">
  
   <form name="" action="../returnTicket/getRecordTicketList.action" method="post" id="searchForm">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
   	 		<li class="w30p">
           	<span class="fl">时间：</span>
               <span class="r-input fl w33p mr10"><input type="text"  name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w33p ml10"><input type="text"  name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
		<li class="w17p">
        	<span class="fl">订单号：</span>
        	<s:textfield name="search.field03" theme="simple" cssClass="fl r-input gray2 w63p mt2"/>
        </li>
        <li class="w17p">
        	<span class="fl">联系方式：</span>
        	<s:textfield name="search.field04" theme="simple" cssClass="fl r-input gray2 w63p mt2"/>
		</li>
         
          
        <li class="w32p">
        	<span class="fl">线路名称：</span><s:textfield name="search.field05" theme="simple" cssClass="fl r-input gray2 w32p mt2"/>
        	<input type="submit" class="btn-blue4 ml20" value="查找" /> 
        	<s:reset value="重置" cssClass="btn-blue4" onclick="clearFormValue('searchForm')" theme="simple"/>
        </li>      	
  	</ul>   
  </form> 
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
            <table width="100%" border="0" class="table3" id="tableDiv">
              <tr align="center" class="th">
              	<th scope="col" width="4%">序号</th>
                <th scope="col" width="14%">订单号</th>
                <th scope="col" width="10%">班次</th>
                <th scope="col" width="12%">乘客</th>
                <th scope="col">联系方式</th>
                <th scope="col" width="10%">线路名称</th>
                <th scope="col" width="10%">退票金额</th>
                <th scope="col" width="8%">操作人</th>
                <th scope="col" width="12%">操作时间</th>
              </tr>
              <s:if test="null==list || list.size == 0">
	              <tr align="center" class="bg1">
	   					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
	   					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
	   			  </tr>
   			  </s:if>
             <s:iterator value="list" var="returnTicket" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" returnId="${returnTicket.returnId}">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" returnId="${returnTicket.returnId}">
						</s:if>
							<td class="f-arial">${currentPageIndex+s.count}</td>
                    		<td class="f-arial">${returnTicket.leaseOrderNo}</td>
                    		<td class="f-arial">${returnTicket.orderStartTime}</td>
                    		<td class="f-arial">${returnTicket.displayId}/${returnTicket.nickName }</td>
                    		<td class="f-arial">${returnTicket.telephone}</td>
                    		<td class="f-arial">${returnTicket.lineName}</td>
                    		<td class="f-arial">${returnTicket.realBackMoney}</td>
                    		<td class="f-arial">${returnTicket.operatior }</td>
                    		<td class="f-arial">${returnTicket.operTime }</td>
                    	</tr>
                   	</s:iterator>
                   	<s:if test="%{page.pageCount != 0}">
			             <tr align="center" class="tr fw">
			                <th  colspan="8" align="right">合计（元）：</th>
			                <th class="f-arial"><em class="yellow1 fw">￥${totalMoney }</em></th>
			              </tr>
		              </s:if>
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
          </div>   
</div>
<input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
<form action="../returnTicket/getRecordTicketList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>
</body>
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
		//document.forms[0].submit();
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
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(pageNum > ${page.pageCount})
	{
	    $("#goPageNum").attr("value",${page.pageCount });
	}
}

//退票详情
$("#tableDiv tr td").click(function(){
	//如果是没有数据就不调用后面的方法
	if($("#noDate").html()!= undefined){	
		return false;
	}
	//获取选中文字，如果选中文字则不响应点击事件
	//ie8写法
	if(typeof(document.selection) != "undefined")
	{
		var selecter = document.selection.createRange().text;
	}
	else if(typeof(window.getSelection()) != "undefined")
	{
		var selecter = window.getSelection();
	}
	else
	{
		var selecter = "";
	}
	if(selecter != "")
	{
	 	return false;
	}
	else
	{
		var returnId = $(this).parent().attr("returnId");
		//跳转到订单详情
		$("#iframe_right",parent.parent.window.document).attr("src","../returnTicket/returnTicketDetail.action?theTab=1&returnTicket.returnId="+returnId+"&currentPageIndex="+$("#myCurrentPageIndex").val());  
	}
});
</script>
</html>