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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/common/Util.js" type="text/javascript"></script>
<title>包车管理-包车列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;包车列表</p></div>
  <div class="mt10 ml20 mr20 black1">         
            <div class="table-title">
                <ul>
		        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">所有包车列表</a></li>
		        </ul>
            </div>
		<form action="../charteredLine/getCharteredLineList.action" method="post" id="searchForm">
            <ul class="r-sub-sel black1 mt20 clearfix">
		       	<li class="w30p">
		           	<span class="t-r w65 fl">提交时间：</span>
		               <span class="r-input fl w33p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
		            <span class="fl">至</span>
		            <span class="r-input fl w33p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
		        </li>
		        <li class="w16p"><span class="t-r w65 fl">联系电话：</span><s:textfield name="search.field03" theme="simple" cssClass="r-input w59p gray2"/></li>
		        <li class="w16p"><span class="t-r w65 fl">包车类型：</span>
		        <s:select list="#{'':'所有类型',1:'旅游包车',2:'商务接送',3:'过港接送' }" listKey="key" listValue="value" cssClass="w63p p3" name="search.field04"></s:select>

		        </li>
<!-- 		        
		        <li class="w20p"><s:select list="%{proSysAreas}"  listKey="arearCode" listValue="areaName" headerKey="" headerValue="选择省份" name="search.field06" cssClass="fl r-input mr10" onchange="loadCity(this.value);" />
		        <select id="cityCode" name="search.field07" class="fl r-input mr10" ><option value="">选择城市</option></select>
		        </li>
-->
		        <li class="w34p"><span class="t-r w65 fl">状态：</span>
		        	<s:select list="#{'':'所有状态',0:'待审核',1:'未通过',2:'待报价',3:'已报价',4:'已过期'}" listKey="key" 
		        		listValue="value" cssClass="w36p p3"   name="search.field05"></s:select>

		        	<input type="submit" class="btn-blue4 ml20" value="查找" />
		        	<s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchForm')"/>
		        </li>
	        </ul>
		</form>
            <div class="table2-text sh-ttext mt10" id="tableDiv">
            	<table width="100%" border="0" class="table3">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="11%">提交时间</th>
                      <th scope="col">用户昵称</th>
                      <th scope="col" width="6%">出发城市</th>
                      <th scope="col" width="9%">联系电话</th>
                      <th scope="col" width="6%">包车类型</th>
                      <th scope="col" width="5%">包车方式</th>
                      <th scope="col" width="10%">出发时间</th>
                      <th scope="col" width="10%">返程时间</th>
                      <th scope="col" width="10%">乘车人</th>
                      <th scope="col" width="8%">联系电话</th>
                      <th scope="col" width="8%">状态</th>
                    </tr>                  
          
      				<s:if test="list==null || list.isEmpty">
      					<tr align="center" class="tr bg1">
							<td colspan="11">
								<p	class="f13 f-yahei gray4 t-c line26 mt10">
									<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span>
								</p>
							</td>
						</tr>
      				</s:if>
      				<s:iterator value="list" var="bc" status="s">
      				<tr align="center" class="tr bg1" bc_line_id="${bc.bc_line_id } ">
      					<td class="f-arial">${s.count+currentPageIndex }</td>
      					<td class="f-arial">${bc.createOn }</td>
      					<td>${bc.nickName }</td>
      					<td>${bc.areaName }/${bc.cityName }</td>
      					<td class="f-arial">${bc.telephone }</td>
      					<td>${bc.businessType==1?'旅游包车':bc.businessType==2?'商务接送':'过港接送' }</td>
      					<td>${bc.charteredMode==1?'单趟':bc.charteredMode==2?'往返':'包天' }</td>
      					<td class="f-arial">${bc.startTime }</td>
      					<td class="f-arial">${bc.returnTime }</td>      					
      					<td>${bc.contacts }</td>
      					<td class="f-arial">${bc.contactPhone }</td>
      					<%-- <td><em class="yellow1">${bc.lineStatus==0?'待审核':bc.lineStatus==1?'未审核通过':bc.lineStatus==2'已发送'}</em></td> --%>
      					<td>
	      					<s:if test="%{#bc.lineStatus==0}"><em class="yellow1">待审核</em></s:if>
	      					<s:if test="%{#bc.lineStatus==1}"><em class="red1">未通过</em></s:if>
	      					<s:if test="%{#bc.lineStatus==2}"><em class="yellow1">待报价</em></s:if>
	      					<s:if test="%{#bc.lineStatus==3}"><em class="green1">已报价</em></s:if>
	      					<s:if test="%{#bc.lineStatus==4}"><em class="break">已过期</em></s:if>
      					</td>
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
        
       <form action="../charteredLine/getCharteredLineList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>
</body>
</html>
<script type="text/javascript">
//订单详情
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
		//var leaseOrderId = $(this).parent().attr("leaseOrderId");
		var bc_line_id=$(this).parent().attr("bc_line_id"); 
		//跳转到订单详情                                                                                                                                                                                  
		$("#iframe_right",parent.window.document).attr("src","../charteredLine/getCharteredLineDetail.action?bc_line_id="+bc_line_id);  
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

//查询城市列表
function loadCity(value){
	
	$.ajax({
		url:'../charteredLine/getProvince.action?proId='+value,
		cache:false,
		dataType:"json",
		async:false,
		success:function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value=''>选择城市</option>");
// 			alert(data);
//          遍历结果数组
			$(data).each(function(i){ 
				
				$("<option value=' "+data[i].arearCode+" '  >"+data[i].areaName+"  </option>").appendTo($("#cityCode"));
			})
		}
	});

}





</script>