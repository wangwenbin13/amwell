<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-招募线路-用户申请线路统计</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
   <s:form action="../line/getUserLinesCount.action" method="post" theme="simple" >
   	 <ul class="r-sub-sel black1 mt20 clearfix">       	
        <li class="w18p"><span class="t-r w65 fl">统计类型：</span>
            
	        <s:select id="selectCount" name="search.field01" list="#{1:'按上车点统计',2:'按下车点统计'}"  listKey="key" listValue="value" cssClass="p3 w65p"/>
        </li>
        <li><s:submit value="查找" cssClass="btn-blue4"/></li>
  	</ul>   
  </s:form>    
      <div class="table-title mt10">            
           <input type="button" class="btn fr mr4 mt4" value="导出" onclick="exportUserLineCount();"/>
       </div>
      <div class="table2-text sh-ttext">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table1" id="tableDiv">
                    <tr align="center">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="12%"> 
	                      <s:if test="%{search.field01==1}">上车点</s:if>
	                      <s:if test="%{search.field01==2}">下车点</s:if>
                      </th>
                      <th scope="col" width="10%">申请人数</th>
                      <th scope="col" width="10%">操作</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
             		  <s:iterator value="list" var="userLineStationCount" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" address="${userLineStationCount.address}">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" address="${userLineStationCount.address}">
						</s:if>
						<td>${s.index+1}</td>
                    	<td class="f-arial">${userLineStationCount.address}</td>
                    	<td class="f-arial">${userLineStationCount.applyCount}</td>
                    	<td class="f-arial">
                    		<a href="javascript:void(0);" class="fn blue1" onclick="getStationApplyList('${search.field01}','${userLineStationCount.address}')">详情</a>
                    	</td>
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
<form action="../line/getUserLinesCount.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="search.field01" value="${search.field01}"/>
  <input type="text" name="currentPageIndex" id="currentPageIndex"/>
</form>   

<form action="../line/exportUserLineCount.action" method="post" style="display: none;" id="exportUserLineCountForm">
  <input type="text" name="search.field01" value="${search.field01}"/>
</form> 
 
</body>
</html>
<script type="text/javascript">
function exportUserLineCount(){
	$("#exportUserLineCountForm").submit();
}

//查看乘客申请列表
function getStationApplyList(type,stationName)
{
    if(null==type||""==type||null==stationName||""==stationName){
       return ;
    }
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide",parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("../line/selectStationApply.action?random="+Math.floor(Math.random()*10000+1),{type:type,stationName:stationName}); 
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
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(parseInt(pageNum) > parseInt(totalPage))
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}
</script>