<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付结算-支出统计</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;支付结算&nbsp;>&nbsp;支出统计<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
  <div class="mt20 ml30 black1 mr28 clearfix">
  
   <form name="" action="../statOutAction/getStatOutList.action" method="post" id="form1">
   <p class="fw f14 f-arial">支出统计</p>
   	 <ul class="r-sub-sel black1 mt20 clearfix">
        	<li class="w28p">
           	<span class="fl w65 t-r">操作时间：</span>
               <span class="r-input fl w29p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w29p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" 
            onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}',maxDate:'${maxDate}'})"/></span>
        </li>
        <li class="w28p">
        	<span class="fl w65 t-r">线路名称：</span><s:textfield name="search.field03" theme="simple" cssClass="fl r-input gray2 w65p mt2"/>    
        	<!--<a class="search-btn1 fl mt2 ml5" href="javascript:void(0)"></a>           	
        --></li>
        <!-- <li class="w28p">
        	<span class="fl w65 t-r">商家：</span><s:textfield name="search.field04" theme="simple" cssClass="fl r-input gray2 w65p mt2"/>   
        	<a class="search-btn1 fl mt2 ml5" href="javascript:void(0)"></a>           	
        </li>-->
        <li class="w28p">
        	<span class="fl w65 t-r">联系方式：</span><s:textfield name="search.field05" theme="simple" cssClass="fl r-input gray2 w65p mt2"/>    
        	<!--<a class="search-btn1 fl mt2 ml5" href="javascript:void(0)"></a> -->           	
       </li>
        <li class="w28p">
        	<span class="fl w65 t-r">订单号：</span><s:textfield name="search.field06" theme="simple" cssClass="fl r-input gray2 w65p mt2"/>    
        	<!--<a class="search-btn1 fl mt2 ml5" href="javascript:void(0)"></a> -->           	
       </li>
       
        <!--  
        <li class="w28p"><span class="t-r w65 fl">付款方式：</span>
        	<s:select cssClass="w66p p3" list="#{3:'所有方式',0:'刷卡上车',1:'系统付款',2:'退款'}" theme="simple" name="search.field06">
	        </s:select>
        </li>
        -->
         <li class="w40p"><span class="t-r w65 fl">退款类型：</span>
        	<s:select cssClass="fl w65p r-input" list="#{0:'所有方式',1:'退票',2:'改签'}" theme="simple" name="search.field07">
	        </s:select>
	        <input class="search-btn ml10" type="button" onclick="toSubmit();"/>
	        
        </li>
  	</ul>   
  </form> 
  <div class="clearfix"><div class="fr export-bg black1 lh32 fw" onclick="toExport();"><em class="export-icon fl mt2 ml4"></em>导&nbsp;出</div></div>    
            <table width="100%" border="0" class="table1">
              <tr align="center" class="th">
              	<th scope="col" width="3%">序号</th>
              	<th scope="col" width="6%">乘车日期</th>
              	<th scope="col" width="4%">班次</th>
                <th scope="col" width="9%">订单号</th>
                <th scope="col" width="6%">线路类型</th>
                <th scope="col">线路名称</th>
                <th scope="col" width="6%">乘客</th>
                <th scope="col" width="8%">联系方式</th>
                <%--<th scope="col" width="9%">商家</th>--%>
                <th scope="col" width="6%">司机</th>
                <th scope="col" width="7%">车辆</th>
                <th scope="col" width="5%">退款金额(元)</th>
                <th scope="col" width="5%">退款类型</th>
                <th scope="col" width="5%">操作人</th>
                <th scope="col" width="10%">操作时间</th>
              </tr>
              <s:if test="%{listSize==0}">
				<tr class="noDateList">
					<th colspan="13">				
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
              <s:iterator value="list" var="statOutEntity" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
							<td class="f-arial">${currentPageIndex+s.count}</td>
                    		<td class="f-arial">${statOutEntity.orderDate}</td>
                    		<td class="f-arial">${statOutEntity.orderStartTime}</td>
                    		<td class="f-arial">${statOutEntity.leaseOrderNo}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#statOutEntity.lineSubType==0}"><em>上下班  </em></s:if>
                    			<s:if test="%{#statOutEntity.lineSubType==1}"><em>自由行  </em></s:if>
                    		</td>
                    		<td class="f-arial">${statOutEntity.lineName}</td>
                    		<td class="f-arial">${statOutEntity.displayId}/${statOutEntity.nickName }</td>
                    		<td class="f-arial">${statOutEntity.telephone}</td>
                    		<%--<td class="f-arial">${statOutEntity.brefName}</td>
                    		--%><td class="f-arial">${statOutEntity.driverName}</td>
                    		<td class="f-arial">${statOutEntity.vehicleNumber}</td>
                    		<td class="f-arial">${statOutEntity.outMoney}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#statOutEntity.outType==1}"><em>退票  </em></s:if>
                    			<s:if test="%{#statOutEntity.outType==2}"><em>改签  </em></s:if>
                    		</td>
                    		<td class="f-arial">${statOutEntity.loginName}</td>
                    		<td class="f-arial">${statOutEntity.operatTime}</td>
                    	</tr>
                   	</s:iterator>
                    <s:if test="%{page.pageCount != 0}">
		              <tr align="center" class="trbg4 fw">
		                <td  colspan="13" align="right">合计（元）：</td>
		                <td class="f-arial"><em class="yellow1 fw">￥${totalMoney }</em></td>
		              </tr>
	              </s:if>
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
<form action="../statOutAction/getStatOutList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
    <input type="text" name="search.field07" value="${search.field07 }"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>       
<form action="../statOutAction/exportExcel.action" method="post" style="display: none;" id="export">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
    <input type="text" name="search.field07" value="${search.field07 }"/>
 </form>      
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();
});

function clearInputDefaultVal()
{
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				//$(this).val(""); 
			} 
		})  
	});
}

//帮助反馈详情
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
		//跳转到帮助反馈详情
		window.location.href = "getFeedbackDetail.action";
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

//导出
function toExport(){
	var listSize = "${listSize}";
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#export").submit();
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

//搜索按钮
function toSubmit(){
	$("#form1").submit();
}
</script>