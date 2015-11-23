<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理-乘客列表-退票记录</title>
<jsp:include page="../resource.jsp"/>
</head>
 
  <body>
  <div class="mt10 black1">
   <form name="" action="" method="post" id="searchform">
   <s:hidden theme="simple" name="passengerId"></s:hidden>
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w30p">
           	<span class="t-r w65 fl">时间：</span>
            <span class="r-input fl w33p mr10">
            <input type="text" class="Wdate75 gray2 Wdate" readonly="readonly"  name="search.field01" value="${search.field01 }" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/>
            </span>
            <span class="fl">至</span>
            <span class="r-input fl w33p ml10">
            <input type="text" class="Wdate75 gray2 Wdate" readonly="readonly"  name="search.field02" value="${search.field02 }" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/>
            </span>
        </li>
        <li>
        <input type="submit" class="btn-blue4" value="查找"/>
        </li>
  	</ul>   
  </form> 

   <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="10%">退票时间</th>
                      <th scope="col" width="10%">订单号</th>
                      <th scope="col" width="10%">乘客</th>
                      <th scope="col" width="10%">联系方式</th>
                      <th scope="col" width="10%">线路名称</th>
                      <th scope="col" width="5%">退票金额</th>
                      <th scope="col" width="10%">班次</th>
                      <th scope="col" width="10%">退票日期</th>
                      <th scope="col" width="10%">退票原因</th>
                      <th scope="col" width="10%">操作人</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="10"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="list" var="returnTicket" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
							<td class="f-arial">${currentPageIndex+s.count}</td>
							<td class="f-arial">${returnTicket.operTime }</td>
                    		<td class="f-arial">${returnTicket.leaseOrderNo}</td>
                    		<td class="f-arial">${returnTicket.displayId}/${returnTicket.nickName }</td>
                    		<td class="f-arial">${returnTicket.telephone}</td>
                    		<td class="f-arial">${returnTicket.lineName}</td>
                    		<td class="f-arial">${returnTicket.realBackMoney}元</td>
                    		<td class="f-arial">${returnTicket.orderStartTime}</td>
                    		<td class="f-arial">
                    		<input type="hidden" id="theOrderDate" value="${returnTicket.returnDates}"/>
                    		<div id="orderDates"></div>
                    		</td>
                    		<td class="f-arial">
                    		<s:if test="%{returnReson==1}">乘客原因</s:if>
                    		<s:else>非乘客原因</s:else>
                    		</td>
                    		<td class="f-arial">${returnTicket.operatior }</td>
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
                       <s:if test="%{page.pageCount!=0 && ((currentPageIndex/pageSize)+1)!=page.pageCount}">
                       		<a href="javascript:void(0);" onclick="toPage(${page.nextIndex});">下一页</a>
                       		<a href="javascript:void(0);" onclick="toPage(${page.lastIndex});">末页</a>
                       </s:if>
                       <s:if test="%{page.pageCount!=1 && page.pageCount!=0}">
                       		<span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onblur="toJudgePage(this.value);"/>页</span>
                       </s:if>
                       <s:if test="%{page.pageCount!=0}">
                       		<span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页&nbsp;&nbsp;<s:property value="page.totalCount" />条数据]</span>
                       </s:if>
                 </div>
                  <!--End page-->
 
</div>
<form action="" method="post" style="display: none;" id="turnPage">
    <s:hidden theme="simple" name="passengerId"></s:hidden>
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>
  </body>
</html>
<script type="text/javascript">
$(function(){
	//处理多个日期显示问题
	$("#tableDiv tr").each(function(){
		if($(this).attr('class')!='th'){
			var $theVal=$(this).find("#theOrderDate").val();
			var $theStr='';
			if(undefined!=$theVal&&''!=$theVal){
				var $orderDates=$theVal.split(',');
				if($orderDates.length>5){
					
					$theStr = "<a href='javascript:void(0)' class='fn blue1' onclick='goDateDetailPage(\""+$theVal+"\");'>查看</a>";
				}
				else{
					for(var i=0;i<$orderDates.length;i++){
						if($theStr==''){
							$theStr=$orderDates[i];
						}
						else{
							$theStr=$theStr+'<br>'+$orderDates[i];
						}
					}
				}
			}
			$(this).find("#orderDates").html($theStr);
		}
	});
});

//弹出显示退票日期
function goDateDetailPage(trObj)
{
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("../operationPassenger/showDateDetail.action?theListType=2&orderDates="+trObj+"&random="+Math.floor(Math.random()*10000+1)); 
    $("#mainBody",parent.window.document).show();
}

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
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(pageNum > ${page.pageCount})
	{
	    $("#goPageNum").attr("value",${page.pageCount });
	}
}
</script>
