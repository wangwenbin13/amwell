<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>供应商管理-司机管理-司机列表</title>
	<jsp:include page="../resource.jsp"/>
</head>
<body>
<%--	<div class="r-sub-nav-out">--%>
<%--		<p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;用户管理&nbsp;>&nbsp;司机列表--%>
<%--		<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBack();">返回</a>--%>
<%--		</p>--%>
<%--	</div>--%>
	<s:form name="" action="../operationDriver/driverList.action" method="post" id="searchform" theme="simple">
		<!-- 存排序方式信息 -->
		<s:hidden theme="simple" name="search.field07" id="orderByWay"></s:hidden>
		<s:hidden theme="simple" name="search.field08" id="orderByColumnName"></s:hidden>
		<ul class="r-sub-sel black1 mt20 ml20 clearfix">
        	<li class="w16p">
        		<span class="fl">所属商家：</span>
        		<s:textfield name="search.field01" cssClass="r-input w55p gray2"></s:textfield>
       	 	</li>
	        <li class="w16p">
	        	<span class="fl">司机姓名：</span>
	        	<s:textfield name="search.field02" cssClass="r-input w63p gray2"></s:textfield>
	        </li>
	        <li class="w16p">
	        	<span class="fl">手机号码：</span>
	        	<s:textfield name="search.field03" cssClass="r-input w63p gray2"></s:textfield>
	        </li>
	        <li class="w16p">
	        	<span class="fl">帐号状态：</span>
	        	<s:select name="search.field04" list="#{'':'全部状态','0':'启用','1':'禁用'}" listKey="key" listValue="value" cssClass="p3 w66p"></s:select>
	        </li>
	        <li class="w14p">
	        	<span class="fl">每页显示：</span>
	        	<s:select name="search.field05" list="#{10:'10条',20:'20条',30:'30条 ',40:'40条',50:'50条'}" listKey="key" listValue="value" cssClass="p3 w50p" />
	        </li>
	        <li>
	        	<input type="submit" class="btn-blue4" value="查找" />
	        	<s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchform')"/>
	        </li>
		</ul>   
	</s:form> 
	<div class="table-title mt10">
		<a href="javascript:void(0)" onclick="toExport();" class="btn fr mr8 mt4">导出</a>
	</div>
	<div class="table2-text sh-ttext">       
		<div style="overflow-x:auto;overflow-y:hidden">
			<table width="100%" border="0" class="table3" id="tableDiv">
				<tr align="center" class="th">
					<th scope="col" width="5%">序号</th>
                    <th scope="col" width="15%" class="cursor-d" onClick="orderByClick('createOn');">注册时间<span class="arrow arrow-desc ml4" id="createOn"><!--arrow为公用样式，升序在后面追加arrow-asc,降序追加arrow-desc--></span></th>
                    <th scope="col" width="15%">所属商家</th>
                    <th scope="col" width="10%">司机姓名</th>
                    <th scope="col" width="10%">性别</th>
                    <th scope="col" width="10%">登录手机号码</th>
                    <th scope="col" width="15%" class="cursor-d" onClick="orderByClick('totalAmount');">奖励账户<span class="arrow arrow-desc ml4" id="totalAmount"><!--arrow为公用样式，升序在后面追加arrow-asc,降序追加arrow-desc--></span></th>
                    <th scope="col" width="10%">账号状态</th>
				</tr>
				<s:if test="null==list || list.size == 0">
					<tr align="center" class="bg1">
						<td colspan="9">
							<p class="f13 f-yahei gray4 t-c line26 mt10">
         					    <span class="index-nodate mr10"></span>
         					    <span id="noDate">暂无数据</span>
         					</p>
         				</td>
         		    </tr>
				</s:if>
				<s:iterator value="list" status="l">
                    <tr class="bg1 tr" align="center" style="l">
                    	<td class="f-arial">${(page.currentPage-1)*page.pageSize+l.index+1}<s:hidden theme="simple" id="theId" name="driverId"></s:hidden></td>
                    	<td class="f-arial"><s:property value="createOn"/></td>
                    	<td class="f-arial"><s:property value="businessName"/></td>
                    	<td class="f-arial"><em class="blue1"><s:property value="driverName"/></em></td>
                    	<td class="f-arial"><s:property value="%{sex==0?'男':'女'}"/></td>
                    	<td class="f-arial"><s:property value="telephone"/></td>
                    	<td class="f-arial"><s:property value="totalAmount"/></td>
                    	<td class="f-arial">
	                    	<s:if test="%{accountStatus==0}">
	                    		<em class="green1">启用</em>
	                    	</s:if>
	                    	<s:else>
	                    		<em class="red1">禁用</em>
	                    	</s:else>
						</td>
					</tr>
				</s:iterator>
			</table>
			<s:hidden theme="simple" id="theListSize" value="%{list.size()}"></s:hidden>
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
					<b><span class="current"><s:property value="field01"/></span></b>
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
	<input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
	<input type="hidden" id="theTab" value="${theTab}"/>
	<!--End page-->
	<form action="../operationDriver/driverList.action" method="post" style="display: none;" id="turnPage">
	   	<input type="text" name="search.field01" value="${search.field01}"/>
	    <input type="text" name="search.field02" value="${search.field02}"/>
	    <input type="text" name="search.field03" value="${search.field03}"/>
	    <input type="text" name="search.field04" value="${search.field04}"/>
	    <input type="text" name="search.field05" value="${search.field05}"/>
	    <input type="text" name="search.field07" value="${search.field07}"/>
	    <input type="text" name="search.field08" value="${search.field08}"/>
	    <input type="text" name="currentPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
	</form> 
	<form action="../operationDriver/exportExcel.action" method="post" style="display: none;" id="export">
	   	<input type="text" name="search.field01" value="${search.field01}"/>
	    <input type="text" name="search.field02" value="${search.field02}"/>
	    <input type="text" name="search.field03" value="${search.field03}"/>
	    <input type="text" name="search.field04" value="${search.field04}"/>
	    <input type="text" name="search.field05" value="${search.field05}"/>
	    <input type="text" name="search.field07" value="${search.field07}"/>
	    <input type="text" name="search.field08" value="${search.field08}"/>
	    <input type="text" name="currentPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
	</form> 
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//修改排序样式
	changeClass();
});

//司机详情
$("#tableDiv tr td").click(function(){
	//如果是没有数据就不调用后面的方法
	if($("#noDate").html()!= undefined){	
		return false;
	}
	//获取选中文字，如果选中文字则不响应点击事件
	//ie8写法
	if(typeof(document.selection) != "undefined"){
		var selecter = document.selection.createRange().text;
	}else if(typeof(window.getSelection()) != "undefined"){
		var selecter = window.getSelection();
	}else{
		var selecter = "";
	}
	if(selecter != ""){
	 	return false;
	}else{
		//跳转到司机详情
		$("#iframe_right",parent.parent.window.document).attr("src","../operationDriver/driverDetail.action?driverId="+$(this).parent().find("#theId").val()+"&currentPageIndex="+$("#myCurrentPageIndex").val()+"&theTab="+$("#theTab").val());
	}
});

//----------------------------排序模式start-------------------------------------
//排序
function orderByClick(orderByParam){
	//初始化按降序排列
	var orderTypeParam = "ASC";
	var $orderByWay=$("#orderByWay").val();
	if($orderByWay!=''){
		if($orderByWay.toUpperCase()=='DESC'){
			orderTypeParam='ASC';
		}else{
			orderTypeParam='DESC';
		}
	}
	$("#orderByWay").val(orderTypeParam);
	$("#orderByColumnName").val(orderByParam);
	//排序后去请求数据
	$("#searchform").submit();
}

function changeClass(){
	//升序添加样式
	if($("#orderByWay").val()!=''){
		if ($("#orderByWay").val().toUpperCase() == "ASC"){
			addAndRemoveClass('createOn',"arrow-desc","arrow-asc");
		}else{
			addAndRemoveClass('createOn',"arrow-asc","arrow-desc");
	   	}
	}
}

//排序时添加或是删除样式
function addAndRemoveClass(orderByParam,class1,class2){
	$("#"+orderByParam).removeClass(class1).addClass(class2);
}
//----------------------------排序模式end-------------------------------------

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
        if(currPage == "" || typeof(currPage) == "undefined"){
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
function checkGoPageNum(pageNum){
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(parseInt(pageNum) > parseInt(totalPage)){
	    $("#goPageNum").attr("value",totalPage);
	}
}

//导出
function toExport(){
	var listSize = $("#theListSize").val();
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#export").submit();
}
</script>
