<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<meta name="Description" content="" />
<title>支付结算-日收入统计</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;支付结算&nbsp;>&nbsp;日收入统计<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 black1 mr28">
	<form action="../statDayIncomeAction/getDayIncome.action" method="post" id="form1">
      	<p class="fw f14 f-arial">日收入统计</p>
      	<ul class="r-sub-sel mt20 clearfix">
            <li class="fl">
	             <span class="fl">线路：</span>
	             <s:textfield type="text" cssClass="r-input w130 gray3" name="search.field01" value="请输入线路名称" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('black1');}" onblur="if(!value){value=defaultValue;$(this).removeClass('black1').addClass('gray3');}" id="field01"/>
            </li>
            <li class="fl">
                <span class="fl">司机：</span> 
                <s:textfield type="text" cssClass="r-input w130 gray3" name="search.field02" value="请输入司机姓名" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('black1');}" onblur="if(!value){value=defaultValue;$(this).removeClass('black1').addClass('gray3');}" id="field02"/>
            </li>
            <li class="fl">
                <span class="fl">车辆：</span> 
                <s:textfield type="text" cssClass="r-input w130 gray3" name="search.field03" value="请输入车牌号码" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('black1');}" onblur="if(!value){value=defaultValue;$(this).removeClass('black1').addClass('gray3');}" id="field03"/>
            </li>
			<li class="fl">
	           	<span class="t-l w65 fl">统计时段：</span>
	            <span class="r-input fl w130 mr10"><input type="text" name="search.field04" value="${search.field04 }" class="Wdate75 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
	            <span class="fl">至</span>
	            <span class="r-input fl w130 ml10"><input type="text" name="search.field05" value="${search.field05 }" class="Wdate75 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-4,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}',maxDate:'${maxDate}'})"/></span>
       		</li>
          	<li class="fl">
          		<input class="search-btn" type="button" onclick="toSubmit();"/>
          	</li>
          	<input type="hidden" name="searchOrNo" value="1"/>
          	<input type="hidden" name="maxDate" value="${maxDate }"/>
         </ul>
      </form>
      <div class="mt20 p-r come-title">
      	<div class="come-icon p-a"></div>
      	<span class="ml50 f14 fw black1"></span>&nbsp;&nbsp;
      	<span class="f13 black1">总共收入：<em class="red4 fw f22">${totalMoney }</em>&nbsp;元</span>
      	<div class="fr export-bg mr50 mt8 black1 lh32 fw" onclick="toExport();"><em class="export-icon fl mt2 ml4"></em>导&nbsp;出</div>
      </div>
      <table class="table1" border="0" width="100%" id="tableDiv">
		<tr class="th">
			<th scope="col" width="15%">日期</th>
            <th scope="col" width="10%">线路类型</th>
            <th scope="col" width="15%">线路名称</th>
            <th scope="col" width="20%">乘客</th>
			<th scope="col" width="10%">司机</th>
            <th scope="col" width="10%">车辆</th>
            <th scope="col" align="left"><em class="ml15 fw">收款金额（元）</em></th>
		</tr>
		<s:if test="%{listSize==0}">
			<tr class="noDateList">
				<th colspan="8">				
					<div class="t-c mt115 mb180">
						<s:if test="%{searchOrNo==0}">
							<img src="../images/noDate.png" width="169" height="169" alt="暂无数据" /><!-- 默认没有线路 -->
							<p class="mt15 f18 f-yahei">暂无数据</p>
						</s:if>
						<s:if test="%{searchOrNo==1}">
							<img src="../images/noSearchDate.png" width="169" height="169" alt="暂无数据" /><!-- 没有查询结果 -->
							<p class="mt15 f18 f-yahei">暂无数据</p>
							<!--<p class="gray3 mt15">没有查询结果噢，你可以重新设置条件进行搜索,或者返回列表。 <a href="javascript:void(0)" class="red1" onclick="toBackList();">返回</a> 没有查询结果的提示 </p>	-->				
						</s:if>
					</div>				
				</th>
			</tr>
		</s:if>
		<s:iterator value="list" var="statTotalEntity" status="s">
			<tr align="center">
				<td>${statTotalEntity.orderDate}</td>
				<td>
					<s:if test="%{#statTotalEntity.lineSubType==0}">
						<span class="display-ib lineKind lineKind-work">上下班</span>
					</s:if>
					<s:if test="%{#statTotalEntity.lineSubType==1}">
						<span class="display-ib lineKind lineKind-free">自由行</span>
					</s:if>
				</td>
				<td>${statTotalEntity.lineName}</td>
				<td>${statTotalEntity.displayId }/${statTotalEntity.nickName}</td>
				<td>${statTotalEntity.driverName}</td>
				<td>${statTotalEntity.vehicleNumber}</td>
				<td align="left"><em class="fw yellow4 ml15">${statTotalEntity.consumeLimit}</em></td>
			</tr>		
		</s:iterator>
	</table>
	<!--Start page-->
    <div class="page mt20 line24">
        <s:if test="%{currentPageIndex!=0}">
       		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
         	<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
        </s:if>
        <ul id="pageNumDiv">
           <s:iterator value="page.pageList">
					<s:if test="field02==1">
						<li class="on fw"><s:property value="field01" /></li>
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
<form action="../statDayIncomeAction/getDayIncome.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="searchOrNo" value="${searchOrNo }"/>
    <input type="text" name="maxDate" value="${maxDate }"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>       
<form action="../statDayIncomeAction/exportExcel.action" method="post" style="display: none;" id="export">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="maxDate" value="${maxDate }"/>
 </form>
</div>
</body>
</html>
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

//导出
function toExport(){
	var listSize = "${listSize}";
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#export").submit();
}

//搜索按钮
function toSubmit(){
	var field01 = $("#field01").val();
	var field02 = $("#field02").val();
	var field03 = $("#field03").val();
	if(field01=="请输入线路名称"){
		$("#field01").val("");
	}
	if(field02=="请输入司机姓名"){
		$("#field02").val("");
	}
	if(field03=="请输入车牌号码"){
		$("#field03").val("");
	}
	$("#form1").submit();
}

//函数入口
$(function(){
	//判断搜索框的值
	judgeSearchInputInfo();
});

//判断搜索框的值
function judgeSearchInputInfo(){
	var field01 = "${search.field01}";
	var field02 = "${search.field02}";
	var field03 = "${search.field03}";
	if(field01!=""){
		$("#field01").val(field01);
		$("#field01").removeClass('gray3').addClass('black1');
	}
	if(field02!=""){
		$("#field02").val(field02);
		$("#field02").removeClass('gray3').addClass('black1');
	}
	if(field03!=""){
		$("#field03").val(field03);
		$("#field03").removeClass('gray3').addClass('black1');
	}
}

//返回列表页面
function toBackList(){
	window.location.href="../statDayIncomeAction/getDayIncome.action"
}
</script>