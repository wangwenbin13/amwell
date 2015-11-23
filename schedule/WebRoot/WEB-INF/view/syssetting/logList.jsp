<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-操作日志</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;操作日志<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
   <div class="mt20 ml30 black1 mr28"> 
   	<p class="fw f14 f-yahei">操作日志</p>
	<form action="../logList/toLogList.action"  method="post">
   		<ul class="clearfix r-sub-sel mt20">
	          <li class="w35p">
	           		<span class="t-r w75 fl">操作时间：</span>
	                <span class="r-input fl w40p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly"  id="txtStartDate" onclick="WdatePicker({position:{left:-4,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
		            <span class="fl">至</span>
		            <span class="r-input fl w40p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-4,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
	           </li>
	           <li class="w18p"><span class="t-r w75 fl">用户名：</span><input type="text" class="r-input w65p" name="search.field03" value="${search.field03 }" /></li>
	           <li class="w18p"><span class="t-r w75 fl">用户动作：</span><input type="text" class="r-input w65p" name="search.field04" value="${search.field04 }" /></li>
	           <li class="w18p"><span class="t-r w75 fl">操作结果：</span><input type="text" class="r-input w65p" name="search.field05" value="${search.field05 }" />
	           		<input type="submit"  class="search-btn ml20" value="" />
	           </li>

	    </ul>
	</form>
	    <div onclick="toExport();" class="fr export-bg black1 lh32 fw"><em class="export-icon fl mt2 ml4"></em>导&nbsp;出</div>    
      <table width="100%" border="0" class="table1 mt20">
          <tr align="center" class="th">
            <th scope="col" width="5%">序号</th>
            <th scope="col" width="15%">登录名</th>
            <th scope="col" width="20%">用户姓名</th>
            <th scope="col" width="15%">用户IP地址</th>
            <th scope="col" width="15%">用户动作</th>
            <th scope="col" width="15%">操作结果</th>
            <th scope="col" width="15%">操作时间</th>
          </tr> 
          
			<s:if test="(list==null || list.isEmpty()) && (search.field01!=null)">
				<!-- 没有数据 start -->
				<tr class="noDateList">
					<th colspan="10">
						<div class="t-c mt115 mb180">
							<img src="../images/noSearchDate.png" width="169" height="169"	alt="暂无数据" />
							<p class="mt15 f18 f-yahei">暂无数据</p>
						</div></th>
				</tr>
				<!-- 没有数据 end -->
			</s:if>
			<s:elseif test="list==null || list.isEmpty()">
				<!-- 没有数据 start -->
				<tr class="noDateList">
					<th colspan="10">
						<div class="t-c mt115 mb180">
							<img src="../images/noDate.png" width="169" height="169"
								alt="暂无数据" />
							<p class="mt15 f18 f-yahei">暂无数据</p>
						</div></th>
				</tr>
				<!-- 没有数据 end -->
			</s:elseif>

			<s:iterator value="list" var="sysLogVo" status="s">
          	<tr align="center" class="bg1 tr">
          	<!-- <td><input type="checkbox" name="checkboxchild" id="child0" /></td> -->
          	<td class="f-arial">${s.count}</td>
          	<td class="f-arial" >${sysLogVo.loginName }</td>
          	<td class="f-arial" >${sysLogVo.userName }</td>
          	<td class="f-arial" >${sysLogVo.userIp }</td>
          	<td class="f-arial" >${sysLogVo.action }</td>
          	<td class="f-arial" >${sysLogVo.operateResult }</td>
          	<td class="f-arial" >${sysLogVo.operateTime }</td>
          	</tr>         
          </s:iterator>   
      </table>	
     <!--Start page-->
        <div class="page mt20 line24" id="pageDiv">
          <s:if test="%{currentPage!=0}">
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
             <s:if test="%{page.pageCount!=0 && ((currentPage/pageSize)+1)!=page.pageCount}">
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
		parent.showCommonTip("请输入大于0的数!");
		return;
	}else if(value>totalPage){
		parent.showCommonTip("超出最大页数!");
		return;
	}
	var pageSize = "${pageSize}";
	toPage((value-1)*pageSize);
}
//导出
function toExport(){
	var listSize = "${listSize}";
	//alert(listSize);
	if(0==listSize){
		parent.showCommonTip("没有需要导出的数据!");
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
