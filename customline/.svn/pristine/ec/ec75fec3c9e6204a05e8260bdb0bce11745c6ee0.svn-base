<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户、订单管理-退票-订单列表</title>
<jsp:include page="../resource.jsp"/>
<jsp:include page="../public_select.jsp"/>
</head>

<body>
  <div class="mt10 black1">
  
   <form id="searchForm" action="../returnTicket/getReturnTicketList.action" method="post">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
        <li class="w17p">
        	<span class="fl w65 t-r">乘客电话：</span>
        	<input type="text" class="fl r-input gray2 w63p mt2" name="search.field01" value="${search.field01}"/>
		</li>
        <li class="w17p">
        	<span class="fl w65 t-r">订单号：</span><input type="text" class="fl r-input gray2 w63p mt2" name="search.field02" value="${search.field02}"/>   
        </li>  
        <li class="w17p">
        	<span class="fl w65 t-r">班次：</span>
        	<span class="fl w61p p-r">
		    	<span class="p-a arrow arrow-desc" style="top:10px;right:4%"></span>
		        <input class="fl r-input gray2 w98p mt2" id="selectClassName" width="w98p" value="${search.field03 }" name="search.field03"/>
	        </span>
	       
        </li>  
        <li><input type="submit" class="btn-blue4 ml20" value="查找" /></li>    	
  	</ul>   
  </form> 
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
            <table width="100%" border="0" class="table1">
              <tr align="center">
              	<th scope="col" width="4%">序号</th>
                <th scope="col" width="14%">订单号</th>
                <th scope="col" width="10%">乘车班次</th>
                <th scope="col" width="15%">乘客</th>
                <th scope="col" width="14%">联系方式</th>
                <th scope="col">线路名称</th>
                <th scope="col" width="6%">线路类型</th>
                <th scope="col" width="11%">操作</th>
              </tr>
              <s:if test="null==list || list.size == 0">
		              <tr align="center" class="bg1">
		   					<td colspan="12"><p class="f13 f-yahei gray4 t-c line26 mt10">
		   					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
		   			  </tr>
   			  </s:if>
			<s:iterator value="list" var="returnTicketVo" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
							<td class="f-arial">${currentPageIndex+s.count}</td>
                    		<td class="f-arial">${returnTicketVo.leaseOrderNo}</td>
                    		<td class="f-arial">${returnTicketVo.orderStartTime}</td>
                    		<td class="f-arial">${returnTicketVo.displayId}/${returnTicketVo.nickName }</td>
                    		<td class="f-arial">${returnTicketVo.telephone}</td>
                    		<td class="f-arial">${returnTicketVo.lineName}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#returnTicketVo.lineSubType==0}"><em>上下班  </em></s:if>
                    			<s:if test="%{#returnTicketVo.lineSubType==1}"><em>自由行  </em></s:if>
                    		</td>
                    		<td class="f-arial"><a  class="fn blue1" onclick="goOrderReturnPage('${returnTicketVo.leaseOrderId}','${returnTicketVo.orderStartTime}');" href="javascript:void(0);">退票</a></td>
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
                       		<span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页]</span>
                       </s:if>
                 </div>
                  <!--End page-->
          </div>   
</div>
<form action="../returnTicket/getReturnTicketList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>
 
 <!-- 搜索内容 -->
<!-- 班次 -->
<div class="hide">
  <select id="selectClass">
  </select>
</div>

</body>


<script type="text/javascript">

$(function(){
	//加载班次
	loadClass();
});

//加载班次
function loadClass(){
	var id = "selectClass";
	$.ajax({
		url:'../orderAction/getAllClass.action',		
		cache:false,	
		dataType:"json",	
		success:function(data){
			$("#"+id).empty();
			$(data).each(function(i){ //遍历结果数组
			   $("<option>"+data[i]+"</option>").appendTo($("#"+id));
			})
			//加载select
			createSelectSearch("selectClassName",id);
		}
	});
}


//车票退票弹窗页面
function goOrderReturnPage(id,orderStartTime)
{
	var url = '../returnTicket/checkLeaseOrder.action?leaseOrderId='+id;
	$.ajax({
		url:url,		
		cache:false,
		async : false,
		success:function(data){
		  if(data=="success"){
			  $("#topHide", parent.parent.window.document).show();
			    $("#leftHide", parent.parent.window.document).show();
			    $("#showPage",parent.window.document).load("../returnTicket/returnPopTicket.action?returnTicketVo.leaseOrderId="+id+"&returnTicketVo.orderStartTime="+orderStartTime+"&random="+Math.floor(Math.random()*10000+1)); 
			    $("#mainBody",parent.window.document).show();
		  }else{
			  parent.parent.showCommonTip("该订单是优惠订单或存在余额支付方式,不允许退票");
			}
		},error:function(){
			parent.parent.showCommonTip("存在异常");
		}
  });
	
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
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(pageNum > parseInt(totalPage))
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}
</script>
</html>