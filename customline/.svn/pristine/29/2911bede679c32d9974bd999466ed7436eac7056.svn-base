<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-操作日志</title>
<jsp:include page="../resource.jsp"/>
<script>
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}
 function recontext(id){
 	var title=$("#"+id).attr("title");
 	var sub="";
 	if(title.length>30){
 		sub=title.substring(0,40);
 		sub+="....";
 	}else{
 		sub=title;
 	}
 	$("#"+id).text(sub);
 	
 }


</script>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;操作日志</p></div>
   <div class="clearfix mt10 ml20 mr20 black1"> 
   	<div class="table-title">
	     <ul>
	        <li class="on"><a href="javascript:void(0)" class="f14 blue2">日志信息</a></li>
	     </ul>
	 </div>
	 <form action="../logList/toLogList.action"  method="post" id="searchform">
   		<ul class="clearfix r-sub-sel mt20">
	          <li class="w28p">
	           		<span class="t-r w75 fl">操作时间：</span>
	                <span class="r-input fl w31p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly"  id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
		            <span class="fl">至</span>
		            <span class="r-input fl w31p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
	           </li>
	           <li class="w18p"><span class="t-r w75 fl">用户名：</span><input type="text" class="r-input w60p" name="search.field03" value="${search.field03 }" /></li>
	           <li class="w18p"><span class="t-r w75 fl">用户动作：</span><input type="text" class="r-input w60p" name="search.field04" value="${search.field04 }" /></li>
	           <li class="w18p"><span class="t-r w75 fl">操作结果：</span><input type="text" class="r-input w60p" name="search.field05" value="${search.field05 }" /></li>
	          <li><input type="submit" class="btn-blue4" value="查找" /><s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchform')"/></li>
	       </ul> 
	       </form>
       <div class="table-outline fl widthfull mt10">
           <div class="table-title">            
               <input type="button" class="btn fr mr4 mt4" value="导出" onclick="toExport();" />
           </div>
	      
           <div class="table2-text sh-ttext clearfix">
               <table width="100%" border="0" class="table1">
                     <tr align="center">
                       <th scope="col" width="5%">序号</th>
                       <th scope="col" width="12%">登录名</th>
                       <th scope="col" width="15%">用户姓名</th>
                       <th scope="col" width="15%">用户IP地址</th>
                       <th scope="col" width="15%">用户动作</th>
                       <th scope="col" >操作结果</th>
                       <th scope="col" width="15%">操作时间</th>
                     </tr>
                     
					<s:if test="(list==null || list.isEmpty())">
						<tr align="center" class="tr bg1">
							<td colspan="11">
								<p	class="f13 f-yahei gray4 t-c line26 mt10">
									<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span>
								</p>
							</td>
						</tr>
					</s:if>
					
					<s:iterator value="list" var="sysLogVo" status="s">
                     	<tr align="center" class="bg1 tr">
                     	
                     	<td class="f-arial">${s.count }</td>
                     	<td class="f-arial" >${sysLogVo.loginName }</td>
                     	<td class="f-arial" >${sysLogVo.userName }</td>
                     	<td class="f-arial" >${sysLogVo.userIp }</td>
                     	<td class="f-arial" >${sysLogVo.action }</td>
                     	<td class="f-arial" ><span id="context${s.index }" title="${sysLogVo.operateResult }">
						<script type="text/javascript">
							recontext("context"+${s.index });
						</script></span> 
						
						</td>
                     	<td class="f-arial" >${sysLogVo.operateTime }</td>
                     	</tr>
                     </s:iterator>
                   </table>
           </div>
       </div>	
        <!--Start page-->
                 <div class="page t-c mt20  fl widthfull" id="pageDiv">
	                   <s:if test="%{currentPage!=0}">
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
                       <s:if test="%{page.pageCount!=0 && ((currentPage/page.pageSize)+1)!=page.pageCount}">
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
    <form action="../logList/toLogList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
    <input type="text" name="currentPage" id="currentPage"/>
  </form> 
  
    <form action="../logList/exportExcel.action" method="post" style="display: none;" id="export">
   	<input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
  </form> 
</body>
</html>
<script type="text/javascript">
$(function(){
	//浏览器可视窗口的的高度
	$(".right-tree-list").css("height",($(window).height()-35+"px"));	
	$(window).resize(function() {
		$(".right-tree-list").css("height",($(window).height()-35+"px"));	
	});    
});

//------------------------- 列表选中和未选中start------------------------------
//全部选中
$("#checkAllBox").click(function(){
	//判断是否已经打勾
	$('input[name="checkboxchild"]').attr("checked",this.checked);
});

//选中某一项
$('input[name="checkboxchild"]').click(function(){
	var $checkBoxChild = $("input[name='checkboxchild']");
	$("#checkAllBox").attr("checked",$checkBoxChild.length == $("input[name='checkboxchild']:checked").length ? true : false);
});

//删除
$("#deleteBtn").click(function(){
	//判断是否已经打勾
	if($("input[name='checkboxchild']:checked").length <= 0)
	{
		parent.parent.showCommonTip("请至少选择一项");
	}
	else
	{
		deleteData();
	}
});
//获取选中的所有id
function getCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

//获取未选中的所有id
function getNotCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (!checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

function deleteData()
{
	var loginIds = getCheckLoginIds();
	//alert("选中的用户为："+loginIds);
	
}
//翻页方法
function toPage(value){
	$("#currentPage").val(value);
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
        if(currPage == "")
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
	//alert(listSize);
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#export").submit();
}
</script>

