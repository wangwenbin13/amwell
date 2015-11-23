<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户、订单管理-退票-订单列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
  <div class="mt10 black1">
  
   <form id="searchForm" action="../returnTicket/applyReturnTicketList.action" method="post">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
        <li class="w30p">
           	<span class="fl">时间：</span>
               <span class="r-input fl w33p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w33p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
        <li class="w15p">
        	<span class="fl">订单号：</span><input type="text" class="fl r-input gray2 w63p mt2" name="search.field03" value="${search.field03}"/>   
        </li>  
        <li class="w16p">
        	<span class="fl">乘客电话：</span>
        	<input type="text" class="fl r-input gray2 w63p mt2" name="search.field04" value="${search.field04}"/>
		</li>
        <li class="w17p"><span class="fl">退票状态：</span>
        	<s:select cssClass="w68p p3" list="#{2:'所有方式',0:'未退票',1:'已退票'}" theme="simple" name="search.field05">
	        </s:select>
        </li> 
        <li class="w17p"><span class="fl">支付方式：</span>
        	<s:select cssClass="w68p p3" list="#{7:'所有方式',0:'无',1:'余额支付',2:'财付通',3:'银联',4:'支付宝',5:'微信',6:'(APP)微信'}" theme="simple" name="search.field06">
	        </s:select>
        </li> 
        <li><input type="submit" class="btn-blue4 ml20" value="查找" />
        <a href="javascript:void(0);" class="blue1 ml10" onclick="toExport();">下载搜索结果</a>
        </li>
            	
  	</ul>   
  </form> 
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
            <table width="100%" border="0" class="table1">
              <tr align="center">
              	<th scope="col" width="4%">序号</th>
                <th scope="col" width="12%">订单号</th>
                <th scope="col" width="8%">原退票的总价</th>
                <th scope="col" width="8%">手续费百分比</th>
                <th scope="col" width="8%">实际退票金额</th>
                <th scope="col" width="8%">手续费</th>
                <th scope="col">退票日期</th>
                <th scope="col" width="10%">乘客电话</th>
                <th scope="col" width="10%">申请时间</th>
                <th scope="col" width="10%">操作时间</th>
                <th scope="col" width="6%">退票状态</th>
                <th scope="col" width="6%">支付方式</th>
                <th scope="col" width="4%">操作</th>
              </tr>
              <s:if test="null==list || list.size == 0">
		              <tr align="center" class="bg1">
		   					<td colspan="13"><p class="f13 f-yahei gray4 t-c line26 mt10">
		   					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
		   			  </tr>
   			  </s:if>
			<s:iterator value="list" var="applyReturn" status="s">
	             <s:if test="%{#s.count%2 == 1}">
					<tr align="center" class="tr bg1">
				</s:if>
				<s:if test="%{#s.count%2 == 0}">
					<tr align="center" class="tr bg2">
				</s:if>
				<td class="f-arial">${currentPageIndex+s.count}</td>
             		<td class="f-arial">${applyReturn.leaseOrderNo}</td>
             		<td class="f-arial">${applyReturn.oldReturn}</td>
             		<td class="f-arial">${applyReturn.percent}</td>
             		<td class="f-arial">${applyReturn.realReturn}</td>
             		<td class="f-arial">${applyReturn.realPoundage}</td>
             		<td class="f-arial">${applyReturn.returnDates}</td>
             		<td class="f-arial">${applyReturn.telephone}</td>
             		<td class="f-arial">${applyReturn.applyTime}</td>
             		<td class="f-arial">${applyReturn.operTime}</td>
             		<td class="f-arial">
             			<s:if test="%{#applyReturn.type==0}"><em>未退票  </em></s:if>
             			<s:if test="%{#applyReturn.type==1}"><em>已退票  </em></s:if>
             		</td>
             		<td class="f-arial">${applyReturn.payModel }</td>
             		<td class="f-arial">
             			<s:if test="%{#applyReturn.type==0}">
             				<a  class="fn blue1" onclick="makeSureReturnTicket('${applyReturn.applyReturnId}');" href="javascript:void(0);">确认退票</a></td>
             			</s:if>
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
<form action="../returnTicket/applyReturnTicketList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>
 
 <form action="../returnTicket/exportExcel.action" method="post" style="display: none;" id="export">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
 </form>
 
</body>


<script type="text/javascript">

//确认退票弹窗
function makeSureReturnTicket(applyReturnId)
{
	var value = "${currentPageIndex}";
	var data = {"applyReturn.applyReturnId":applyReturnId};
	$.ajax({
		url : '../returnTicket/updateApplyReturnTicketType.action',
		type:'post',
		cache:false,	
		dataType:"text",
		data:data,	
		success:function(data){	
			if(null != data){
				if("success"==data){
					parent.parent.showRturnTip("退票成功!",true);
					parent.parent.showPopCommonPage2("退票成功,请尽快退款!");
					toPage(value);
				}
				else
				{
					parent.parent.showRturnTip("退票失败!",false);
				}
			}
		},
		error:function(){
			parent.parent.showRturnTip("退票失败!",false);
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
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(pageNum > ${page.pageCount})
	{
	    $("#goPageNum").attr("value",${page.pageCount });
	}
}

//导出
function toExport(){
	var listSize = "${listSize}";
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#export").submit();
}

</script>
</html>