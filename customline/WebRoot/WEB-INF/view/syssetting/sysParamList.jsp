<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-操作日志</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;操作日志</p></div>
   <div class="clearfix mt20 ml20 mr20 black1"> 
   	<div class="table-title">
	     <ul>
	        <li class="on"><a href="javascript:void(0)" class="f14 blue2">参数信息</a></li>
	     </ul>
	 </div>
	 <form action="../logList/toLogList.action"  method="post">
   		<ul class="clearfix r-sub-sel mt20">
	           <li class="w18p"><span class="t-r w75 fl">参数名：</span><input type="text" class="r-input w60p" name="search.field03" value="${search.field01 }" /></li>
	           <li class="w18p"><span class="t-r w75 fl">参数类型：</span><input type="text" class="r-input w60p" name="search.field04" value="${search.field03 }" /></li>
	           <li class="w18p"><span class="t-r w75 fl">参数状态：</span><input type="text" class="r-input w60p" name="search.field05" value="${search.field04 }" /></li>
	          <li><input type="submit" class="btn-blue4" value="查找" /></li>
	       </ul> 
	       </form>
       <div class="table-outline fl widthfull mt10">
           <div class="table2-text sh-ttext clearfix">
               <table width="100%" border="0" class="table1">
                     <tr align="center">
                       <th scope="col" width="5%">序号</th>
                       <th scope="col" width="10%">参数名</th>
                       <th scope="col" width="10%">参数值</th>
                       <th scope="col" width="10%">参数类型</th>
                       <th scope="col" width="5%">参数状态</th>
                       <th scope="col" width="10%">操作人</th>
                       <th scope="col" width="10%">操作时间</th>
                       <th scope="col" width="40%">备注</th>
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
					
					<s:iterator value="list" var="sysParamVo" status="s">
                     	<tr align="center" class="bg1 tr">
                     	<td class="f-arial">${s.count }</td>
                     	<td class="f-arial" >${sysParamVo.paramName}</td>
                     	<td class="f-arial" >${sysParamVo.paramValue}</td>
                     	<td class="f-arial" >${sysParamVo.paramType}</td>
                     	<td class="f-arial" >${sysParamVo.paramStatus}</td>
                     	<td class="f-arial" >${sysParamVo.updateBy}</td>
                     	<td class="f-arial" >${sysParamVo.updateOn}</td>
                     	<td class="f-arial" >${sysParamVo.remark}</td>
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
	
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#export").submit();
}
</script>
