<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户、订单管理-用户管理-评论列表</title>
<jsp:include page="../resource.jsp"/>
</head>
  
  <s:url id="preurl" value="/operationComment/commentList.action">
	<s:param name="p">
		<s:property value="page.previousIndex" />
	</s:param>
</s:url>


<s:url id="nexturl" value="/operationComment/commentList.action">
	<s:param name="p">
		<s:property value="page.nextIndex" />
	</s:param>
</s:url>
<s:url id="firsturl" value="/operationComment/commentList.action">
	<s:param name="p">
		<s:property value="0" />
	</s:param>
</s:url>
<s:url id="lasturl" value="/operationComment/commentList.action">
	<s:param name="p">
		<s:property value="page.lastIndex" />
	</s:param>
</s:url>
<s:url id="tourl" value="/operationComment/commentList.action">
	<s:param name="p">
		<s:property value="page.currentPage" />
	</s:param>
</s:url>
  
  <body>
  <div class="mt10 black1">
   <form name="" action="../operationComment/commentList.action" method="post" id="searchform">
   <!-- 存排序方式信息 -->
   <s:hidden theme="simple" name="search.field07" id="orderByWay" ></s:hidden>
   <s:hidden theme="simple" name="search.field08" id="orderByColumnName"></s:hidden>
   
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w30p">
           	<span class="fl">时间：</span>
            <span class="r-input fl w33p mr10">
            <input type="text" name="search.field01" value="${search.field01}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/>
            </span>
            <span class="fl">至</span>
            <span class="r-input fl w33p ml10">
            <input type="text" name="search.field02" value="${search.field02}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/>
            </span>
        </li>
        <li class="w18p"><span class="fl">乘客：</span>
        <s:textfield name="search.field03" cssClass="r-input w58p gray2"></s:textfield>
        </li>
        <li class="w18p"><span class="fl">线路：</span>
        <s:textfield name="search.field04" cssClass="r-input w58p gray2"></s:textfield>
        </li><%--
        <li class="w18p"><span class="fl">订单：</span>
        <s:textfield name="search.field05" cssClass="r-input w58p gray2"></s:textfield>
        </li>
        --%><li class="w30p"><span class="fl">评分：</span>
        <s:textfield name="search.field06" cssClass="r-input w35p gray2"></s:textfield>
        <input type="submit" class="btn-blue4 ml20" value="查找" />
        <s:reset value="重置" cssClass="btn-blue4" onclick="clearFormValue('searchform')" theme="simple"/>
        </li>
  	</ul>   
  </form> 
   
<%--   <s:form >--%>
   <div class="table2-text sh-ttext mt10">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table1" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="8%" class="cursor-d" onClick="orderByClick('commentTime');">评论时间<span class="arrow arrow-desc ml4" id="commentTime"><!--arrow为公用样式，升序在后面追加arrow-asc,降序追加arrow-desc--></span></th>
                      <th scope="col" width="8%">乘客</th>
                      <th scope="col" width="5%">线路</th>
                      <th scope="col" width="10%">订单</th>
                      <th scope="col" width="5%">评分</th>
                      <th scope="col" width="10%">建议</th>
                      <th scope="col">评论内容</th>
                      <th scope="col" width="5%">状态</th>
                      <th scope="col" width="5%">操作</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="list" status="l" var="v">
                      <tr class="bg1 tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
                    	<td class="f-arial">

                    	${(page.currentPage-1)*page.pageSize+l.index+1}
                    	</td>
                    	<td class="f-arial"><s:property value="commentTime" /></td>
                    	<td class="f-arial"><s:property value="displayId" />&nbsp;&nbsp;<s:property value="nickName" /></td>
                    	<td class="f-arial"><s:property value="lineName" /></td>
                    	<td class="f-arial"><s:property value="leaseOrderNo" /></td>
                    	<td class="f-arial"><s:property value="starPoint" /></td>
                    	<td class="f-arial" style="text-align:left;">${v.advinces }</td>
                    	<td class="f-arial" style="text-align:left;" title="<s:property value='commentContext'/>">
	                    	<%--<s:if test="%{null!=commentContext&&commentContext.length()>50}">
	                    		<s:property value="%{commentContext.substring(0, 50)}"/>...
	                    	</s:if>
	                    	<s:else>
	                    		<s:property value="commentContext"/>
	                    	</s:else>
	                    	--%><s:property value="commentContext"/>
                    	</td>
                    	<td class="f-arial">
                    	<s:if test="%{null==commentStatus||''==commentStatus||commentStatus==0}">
                    	<em class="green1">显示</em>
                    	</s:if>
                    	<s:else>
                    	<em class="red1">屏蔽</em>
                    	</s:else>

                    	</td>
                    	<td><a href="javascript:void(0);" class="fn blue1" onclick="toDoHide('<s:property value="commentId"/>','<s:property value="commentStatus"/>')"><s:property value="%{(null==commentStatus||''==commentStatus||commentStatus==0)?'屏蔽':'显示'}"/></a></td>
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
                  <form action="../operationComment/commentList.action" method="post" style="display: none;" id="turnPage">
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
<%--   </s:form>--%>
 
</div>
  </body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();

	//修改排序样式
	changeClass();
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

//----------------------------排序模式start-------------------------------------
//排序
function orderByClick(orderByParam)
{
	//初始化按降序排列
	var orderTypeParam = "ASC";

	var $orderByWay=$("#orderByWay").val();
	if($orderByWay!=''){
		if($orderByWay.toUpperCase()=='DESC'){
			orderTypeParam='ASC';
		}
		else{
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
		if ($("#orderByWay").val().toUpperCase() == "ASC")
		{
			addAndRemoveClass('commentTime',"arrow-desc","arrow-asc");
		}
		
		else
		{
			addAndRemoveClass('commentTime',"arrow-asc","arrow-desc");
	   	}
	}
}

//排序时添加或是删除样式
function addAndRemoveClass(orderByParam,class1,class2)
{
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

//点击隐藏
function toDoHide(commentId,commentStatus){
	var str='';
	if(null==commentStatus||''==commentStatus){
		commentStatus="0";
	}
	if(commentStatus=="0"){
		str='确定将该评论屏蔽？';
	}
	else if(commentStatus=="1"){
		str='确定将该评论显示？';
	}

		parent.parent.showPopCommonPage(str);
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
		//动态添加方法
		$("#commonSure",parent.parent.window.document).click(function(){
			parent.parent.closePopCommonPage();
			
			$.ajax({
		           url:'../operationComment/commentChangeStatus.action?commentId='+commentId+'&commentStatus='+commentStatus+'&temp='+new Date(),
		           dataType:'text',
		           cache:false,//不从浏览器缓存中取
		           async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
		           success:function(data){
	              	  if(data=='ok'){
		                
		            	
		            	 //刷新当前页信息
			              $("#turnPage").submit(); 
		              }
		              else{
		            	  parent.parent.showRturnTip(data,false);
		              }
		           }
				});

			//解绑定，防止多次执行click事件
			$("#commonSure",parent.parent.window.document).unbind('click');
		});
}
</script>
