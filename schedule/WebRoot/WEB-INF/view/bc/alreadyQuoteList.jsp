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
<title>包车管理-已报价列表</title>
<jsp:include page="../resource.jsp"/>
<script src="../js/common/Util.js" type="text/javascript"></script>
</head>

<body>
	<p class="subNav">当前位置：首页&nbsp;&gt;&nbsp;包车管理&nbsp;&gt;&nbsp;已报价列表<a class="red1 ml30" href="javascript:void(0)" onclick="javascript:history.go(-1);">返回</a></p>
	<div class="mt20 ml30 black1 mr28">
		<p class="fw f14 f-yahei">已报价列表</p>
		<s:form id="searchForm" action="../businessBidding/getAlreadyQuoteList.action" method="post"  theme="simple"> 
		<input type="hidden" name="searchOrNo" value="1"/>
		<ul class="r-sub-sel mt20 clearfix">
			<li class="fl p-r"><span class="fl w65 t-r">业务类型：</span>
				<div class="fl r-input w130"><span id="lineStyleDiv"></span><span class="fr sel-arrow mt10 mr10"></span></div>
				<s:select id="lineStyle" name="search.field02" list="#{'':'所有类型',1:'旅游包车',2:'商务接送',3:'过港接送'}"  listKey="key" listValue="value" cssClass="p-a sel-demo w134 h29"/>
			</li>
			<!--  
			<li class="fl"><span class="fl">出车时间：</span><span class="r-input fl w167 mr10 ml10">
				<input type="text" readonly="readonly" name="search.field04" class="Wdate75 Wdate" onclick="WdatePicker({position:{left:-4,top:4},dateFmt:'yyyy-MM-dd HH:mm'});" value=""></span>
			</li> -->
			<!--  
			<li class="fl p-r"><span class="fl w65 t-r">报价状态：</span>
				<div class="fl r-input w130"><span id="lineStatusDiv"></span><span class="fr sel-arrow mt10 mr10"></span></div>
				<s:select id="lineStatus" name="search.field03" list="#{'':'所有状态',1:'已报价 ',2:'已过期'}" listKey="key" listValue="value" cssClass="p-a sel-demo w134 h29"/>
			</li>
			-->
			<li><s:submit value="" cssClass="search-btn"/></li>
		</ul>
		</s:form>
		<table class="table1 tableCursor mt20" border="0" width="100%" id="tableLine">
			<tr class="th">
				<th width="4%">序号</th>
				<th width="12%">发布时间</th>
				<th width="8%">业务类型</th>
				<th width="10%">出车时间</th>
				<th width="10%">返程时间</th>
				<th width="23%">起点/终点</th>
				<th width="4%">人数</th> 
				<th width="5%">车辆数</th> 
				<th width="6%">包车方式</th>
				<th width="6%">开具发票</th>
				<th width="8%">总报价</th>
				<th width="5%">状态</th>
			</tr>
			<s:if test="%{page.totalCount == 0}">
				<!-- 没有数据 start -->	
				<tr class="noDateList">
					<th colspan="12">				
						<div class="t-c mt115 mb180">
							<s:if test="%{searchOrNo==0}">
								<img src="../images/noDate.png" width="169" height="169" alt="暂无数据" /><!-- 默认没有线路 -->
								<p class="mt15 f18 f-yahei">暂无数据</p>
							</s:if>
							<s:if test="%{searchOrNo==1}">
								<img src="../images/noSearchDate.png" width="169" height="169" alt="暂无数据" /><!-- 没有查询结果 -->
								<p class="mt15 f18 f-yahei">暂无数据</p>
							</s:if>								
						</div>				
					</th>
				</tr>
				<!-- 没有数据 end -->
			</s:if>
			<s:else>
				<s:iterator value="list" var="businessOfferLine" status="s">
					<tr align="center" id="${businessOfferLine.id}">
						<td>${currentPageIndex+s.count}</td>
						<td>${businessOfferLine.createOn}</td>
						<td>
							<s:if test='%{#businessOfferLine.businessType==1}'> 
						        <span class="display-ib lineKind lineKind-work">旅游包车</span>
						    </s:if>
							<s:if test='%{#businessOfferLine.businessType==2}'> 
						        <span class="display-ib lineKind lineKind-free">商务接送</span>
						    </s:if>
						    <s:if test='%{#businessOfferLine.businessType==3}'> 
						        <span class="display-ib lineKind lineKind-hongkong">过港接送</span>
						    </s:if>
						</td>
						<td>${businessOfferLine.startTime}</td>
						<td>${businessOfferLine.returnTime}</td>
						<td>
							<span class="display-ib lineDoit lineDoit-start"><!--起点 --></span><span class="display-ib stationW" id="beginAddress${s.index}" title="${businessOfferLine.beginAddress}"><script type="text/javascript">$("#beginAddress"+${s.index}).text(Util.cutstr('${businessOfferLine.beginAddress}',20))</script></span>
							<span class="display-ib lineDoitArrow"></span>
							<span class="display-ib lineDoit lineDoit-end"><!--终点 --></span><span class="display-ib stationW" id="endAddress${s.index}" title="${businessOfferLine.endAddress}"><script type="text/javascript">$("#endAddress"+${s.index}).text(Util.cutstr('${businessOfferLine.endAddress}',20))</script></span>
						</td>
						<td>${businessOfferLine.totalPassengers}</td>
						<td>${businessOfferLine.totalCars}</td>
						<td>
							<s:if test='%{#businessOfferLine.charteredMode==1}'>单程 </s:if>
						    <s:if test='%{#businessOfferLine.charteredMode==2}'>往返 </s:if>
						    <s:if test='%{#businessOfferLine.charteredMode==3}'>包天</s:if>
						</td>
						<td>
							<s:if test='%{#businessOfferLine.needInvoice==0}'>否 </s:if>
						    <s:if test='%{#businessOfferLine.needInvoice==1}'>是 </s:if>
						</td>
						<td><em class="red4 fw">￥${businessOfferLine.totalCost>0?businessOfferLine.totalCost:0}</em></td>
						<td>
							<s:if test="%{#businessOfferLine.offerStatus==1}">
								<em class="green1">已报价</em>
							</s:if>
							<s:if test="%{#businessOfferLine.offerStatus==2}">
								<em class="black2">已过期</em> 
							</s:if>
						</td>
					</tr>
				</s:iterator> 
			</s:else>
		</table>
		<div class="page mt20 line24" id="pageDiv">
            <s:if test="%{currentPageIndex!=0}">
           		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
             	<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
            </s:if>
            <ul id="pageNumDiv">
                <s:iterator value="page.pageList">
			        <s:if test="field02==1">
						<li class="on fw"><s:property value="field01" />
						</li>
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
</div>
<form action="../businessBidding/getAlreadyQuoteList.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="search.field02" value="${search.field02}"/>
  <input type="text" name="search.field04" value="${search.field04}"/>
  <input type="text" name="search.field05" value="${search.field05}"/>
  <input type="text" name="currentPageIndex" id="currentPageIndex"/>
</form> 
</body>
</html>
<script type="text/javascript">
$(function(){
	//线路类型
	$("#lineStyle").change(function(){
		$("#lineStyleDiv").text($("#lineStyle").find("option:selected").text());
	});
	
	//线路状态
	$("#lineStatus").change(function(){
		$("#lineStatusDiv").text($("#lineStatus").find("option:selected").text());
	});
	
	$("#lineStyleDiv").text($("#lineStyle").find("option:selected").text());
	$("#lineStatusDiv").text($("#lineStatus").find("option:selected").text());
	
});

//已报价详情
$("#tableLine tr td").click(function(){
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
		var id = $(this).parent().attr("id");
		
		//已报价详情
		$("#iframe_right", parent.window.document).attr("src","../businessBidding/getAlreadytQuoteDetail.action?id="+id);
	}
});

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
//已报价详情页面
function schedule(s){
  window.location.href="../businessBidding/getAlreadytQuoteDetail.action?lineBaseId="+s;
}
</script>