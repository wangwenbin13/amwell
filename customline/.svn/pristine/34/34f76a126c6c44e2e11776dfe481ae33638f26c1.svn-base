<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>营销管理-APP站内信-站内信列表</title>
<jsp:include page="../resource.jsp"/>
</head>
<script>
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}
 function recontext(id){
 	var title=$("#"+id).attr("title");
 	var sub="";
 	if(title.length>30){
 		sub=title.substring(0,30);
 		sub+="....";
 	}else{
 		sub=title;
 	}
 	$("#"+id).text(sub);
 	
 }


</script>

<body>
   <s:form name="" action="../insideMessage/getAPPInsideList.action" method="post" theme="simple">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w33p">
           	<span class="t-r w65 fl">时间：</span>
            <span class="r-input fl w34p mr10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w34p ml10"><input type="text" name="search.field03" value="${search.field03 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
        <li class="w41p"><span class="t-r w95 fl">内容关键字：</span><input type="text" name="search.field01" value="${search.field01}" class="r-input w58p gray2"/>
        <input type="submit" class="btn-blue4" value="查找" />
        </li>
        
  	</ul>   
  </s:form> 
   
   
      <div class="table2-text sh-ttext mt10">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table1" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
					  <th scope="col" width="11%">发送时间</th>
                      <th scope="col" width="8%">发送人</th>
                      <th scope="col" width="5%">发送数量 </th>    
                      <th scope="col" width="20%">标题</th>                
                      <th scope="col">内容</th>
                    </tr>
                    <s:if test="list==null || list.isEmpty">
                    	<tr align="center" class="tr bg1">
							<td colspan="11">
                    			<p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p>
                    		</td>
                    	</tr>
                    </s:if>
                    
				<s:iterator value="list" var="marketingVo" status="s">
					<tr  align="center" marketingId=${marketingVo.msgId } class="cursor">
						<td class="f-arial">${s.count}</td>
						<td class="f-arial">${marketingVo.createOn }</td>
						<td class="f-arial">${marketingVo.createBy }</td>
						<td class="f-arial">${marketingVo.msgCount }</td>
						<td class="f-arial">${marketingVo.msgTitle}</td>
						<td class="f-arial"><span id="context${s.index }" title="${marketingVo.msgContext}">
						<script type="text/javascript">
							recontext("context"+${s.index });
						</script></span> </td>
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
    <form action="../insideMessage/getAPPInsideList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
  </form> 
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
				
			} 
		})  
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


//短信详情
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
		var marketingId = $(this).parent().attr("marketingId");
		//alert(feedbackId);
		//跳转到帮助反馈详情
		$("#iframe_right",parent.parent.window.document).attr("src","../insideMessage/insideAPPDetail.action?messageId="+marketingId);
	}
});


//
</script>