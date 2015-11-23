<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<script src="../js/common/Util.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户投诉-乘客反馈</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

    
   <s:form name="" action="../help/getFeedbackList.action" method="post" id="searchForm" theme="simple">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w30p">
           	<span class="fl">时间：</span>
               <span class="r-input fl w34p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w34p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
         <li class="w17p"><span class="fl">处理状态：</span>
         	
         	<s:select list="#{'':'请选择',0:'未处理',1:'处理中',2:'已处理'}" name="search.field05" listKey="key" listValue="value" cssClass="p3 w63p"></s:select>
		 </li>
        <li class="w17p"><span class="fl">乘客：</span><input type="text" name="search.field03" value="${search.field03 }" class="r-input w63p gray2"/></li>
        <li class="w17p"><span class="fl">手机号码：</span><input type="text" name="search.field04" value="${search.field04 }" class="r-input w63p gray2"/></li>
        <li><input type="submit" class="btn-blue4" value="查找" /><s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchForm')"/></li>
  	</ul>   
  </s:form> 
   
   
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="13%">反馈时间</th>
                      <th scope="col" width="10%">乘客</th>
                      <th scope="col">反馈内容</th>
                      <th scope="col" width="10%">联系电话</th>
                      <th scope="col" width="15%">回复内容</th>
                      <th scope="col" width="10%">操作人</th>
                      <th scope="col" width="13%">操作时间</th>
                      <th scope="col" width="8%">状态</th>
                    </tr>
                    <!-- 没有数据 -->
                    <s:if test="list==null || list.isEmpty">
                    	<tr align="center" class="tr bg1">
							<td colspan="11">
								<p	class="f13 f-yahei gray4 t-c line26 mt10">
									<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span>
								</p>
							</td>
						</tr>
                    </s:if>
                    <s:iterator value="list" var="help" status="s" >
                    <tr align="center" class="tr bg1" feedbackId ="${help.feedbackId}">
                  
                    <td class="f-arial">${s.count}</td>
                    <td class="f-arial">${help.feedbackTime} </td>
                    <td class="f-arial">${help.nickName }/${help.displayId }</td>
                    
                    <td class="f-arial"><span id="context${s.index }" title="${help.feedbackContext } ">
                    <script type="text/javascript">$("#context"+${s.index }).text(Util.cutstr('${help.feedbackContext }',30))</script></span></td>
					<%--                     
					<s:if test='%{#help.phoneNo==""}'><td class="f-arial"> ${help.telephone } </td></s:if>
                    <s:else>
                    <td class="f-arial">${help.phoneNo }</td>
                    </s:else>
                     --%>
                    <td class="f-arial">${help.phoneNo }</td>
					<td>
					
						<span id="contexty${s.index }" title="${help.handleFeedbackendRemark } ">
							<script type="text/javascript">$("#contexty"+${s.index }).text(Util.cutstr('${help.handleFeedbackendRemark }',30))</script></span>
					</td>
					<td>${help.handleUser }</td>
					<td class="f-arial">${help.handleTime }</td>
					<td>${help.status==0?'未处理':help.status==1?'处理中':'已处理' }</td>
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
    <form action="../help/getFeedbackList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
  </form> 
</body>

</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();
	//alert($("#currentPageIndex").val());
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

//翻页方法
function toPage(value){
	$("#currentPageIndex").val(value);
	$("#turnPage").submit();
// 	alert($("#currentPageIndex").val());
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
		var feedbackId = $(this).parent().attr("feedbackId");
		
		//跳转到帮助反馈详情
		//window.location.href = "getFeedbackDetail.action?feedbackId="+feedbackId;
		$("#iframe_right", parent.parent.window.document).attr("src","../help/getFeedbackDetail.action?feedbackId="+feedbackId);
	}
});

</script>