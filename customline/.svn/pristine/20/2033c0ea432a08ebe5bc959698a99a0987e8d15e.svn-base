<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>包车管理-包车车票退票-包车订单列表</title>
<jsp:include page="../resource.jsp"/>
<jsp:include page="../public_select.jsp"/>
</head>

<body>
  <div class="mt10 black1">
  
   <form id="searchForm" action="../bcReturnTicket/getBcReturnOrderList.action" method="post">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
        <li class="w30p">
           	<span class="fl w65 t-r">下单时间：</span>
               <span class="r-input fl w33p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w33p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" 
            onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}',maxDate:'${maxDate}'})"/></span>
        </li>
        <li class="w16p">
        	<span class="fl w65 t-r">订单号：</span><s:textfield name="search.field03" theme="simple" cssClass="fl r-input gray2 w52p mt2"/>    
        </li>
        <li class="w16p">
        	<span class="fl w65 t-r">联系电话：</span><s:textfield name="search.field04" theme="simple" cssClass="fl r-input gray2 w52p mt2"/>    
        </li>
         <li class="w16p">
        	<span class="fl w65 t-r">包车商家：</span>
        	<span class="fl w62p p-r">
		    	<span class="p-a arrow arrow-desc" style="top:10px;right:4%"></span>
		        <input class="fl r-input gray2 w98p mt2" id="selectMgrBusinName" width="w98p" value="${search.field05 }" name="search.field05"/>
	        </span>
        </li>	
         <li class="w16p"><span class="t-r w65 fl">包车类型：</span>
        	<s:select cssClass="w62p p3" list="#{0:'所有类型',1:'旅游包车',2:'商务接送',3:'过港接送'}" theme="simple" name="search.field06">
	        </s:select>
        </li>
        <li class="w36p"><span class="t-r w65 fl">状态：</span>
        	<s:select cssClass="w35p p3" list="#{0:'所有状态',1:'待调度',2:'已调度',3:'已完成'}" theme="simple" name="search.field07">
	        </s:select>
        	<input type="submit" class="btn-blue4 ml20" value="查找" />
        	<s:reset value="重置" cssClass="btn-blue4" onclick="clearFormValue('searchForm')" theme="simple"/>
        </li> 	
  	</ul>   
  </form> 
      <div class="table2-text sh-ttext mt10">       
       	<div style="overflow-x:auto;overflow-y:hidden">
            <table width="100%" border="0" class="table1">
              <tr align="center">
              	<th scope="col" width="4%">序号</th>
              	<th scope="col" width="11%">下单时间</th>
                <th scope="col" width="8%">订单号</th>
                <th scope="col" width="7%">用户名称</th>
                <th scope="col" width="8%">联系电话</th>
                <th scope="col" width="5%">包车类型</th>
                <th scope="col" width="5%">包车方式</th>
                <th scope="col" width="11%">出发时间</th>
                <th scope="col" width="11%">返程时间</th>
                <th scope="col" width="13%">包车商家</th>
                <th scope="col" width="5%">金额(元)</th>
                <th scope="col" width="5%">状态</th>
                <th scope="col">操作</th>
              </tr>
              <s:if test="null==bcReturnVos || bcReturnVos.size == 0">
		              <tr align="center" class="bg1">
		   					<td colspan="13"><p class="f13 f-yahei gray4 t-c line26 mt10">
		   					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
		   			  </tr>
   			  </s:if>
			<s:iterator value="bcReturnVos" var="bcReturnVo" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
							<td class="f-arial">${currentPageIndex+s.count}</td>
                    		<td class="f-arial">${bcReturnVo.payTime}</td>
                    		<td class="f-arial">${bcReturnVo.orderNo}</td>
                    		<td class="f-arial">${bcReturnVo.nickName }</td>
                    		<td class="f-arial">${bcReturnVo.telephone}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#bcReturnVo.businessType==1}"><em>旅游包车  </em></s:if>
                    			<s:if test="%{#bcReturnVo.businessType==2}"><em>商务接送  </em></s:if>
                    			<s:if test="%{#bcReturnVo.businessType==3}"><em>过港接送  </em></s:if>
                    		</td>
                    		<td class="f-arial">
                    			<s:if test="%{#bcReturnVo.charteredMode==1}"><em>单趟  </em></s:if>
                    			<s:if test="%{#bcReturnVo.charteredMode==2}"><em>往返  </em></s:if>
                    			<s:if test="%{#bcReturnVo.charteredMode==3}"><em>包天 </em></s:if>
                    		</td>
                    		<td class="f-arial">${bcReturnVo.startTime}</td>
                    		<td class="f-arial">${bcReturnVo.returnTime}</td>
                    		<td class="f-arial">${bcReturnVo.brefName}</td>
                    		<td class="f-arial">${bcReturnVo.totalPrice}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#bcReturnVo.orderStatus==1}"><em>待调度  </em></s:if>
                    			<s:if test="%{#bcReturnVo.orderStatus==2}"><em>已调度  </em></s:if>
                    			<s:if test="%{#bcReturnVo.orderStatus==3}"><em>已完成  </em></s:if>
                    		</td>
                    		<s:if test="%{#bcReturnVo.returnFlag}">
                    			<td class="f-arial"><a  class="fn blue1" onclick="goOrderReturnPage('${bcReturnVo.orderId}','${bcReturnVo.orderNo}');" href="javascript:void(0);">退票</a></td>
                    		</s:if>
                    		<s:else>
                    			<td class="f-arial">不可退票</td>
                    		</s:else>
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
<form action="../bcReturnTicket/getBcReturnOrderList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
    <input type="text" name="search.field07" value="${search.field07 }"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>
 
<!-- 搜索内容 -->
<!-- 商家名称 -->
<div class="hide">
  <select id="selectMgrBusin">
  </select>
</div>

</body>


<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();

	//加载商家名称
	loadMgrBusin();
});



//加载商家名称
function loadMgrBusin(){
	var id = "selectMgrBusin";
	$.ajax({
		url:'../orderAction/getSupportBcMgrBusinBref.action',		
		cache:false,	
		dataType:"json",	
		success:function(data){
			$("#"+id).empty();
			$(data).each(function(i){ //遍历结果数组
			   $("<option>"+data[i]+"</option>").appendTo($("#"+id));
			})
			//加载select
			createSelectSearch("selectMgrBusinName",id);
		}
	});
}

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

//包车退票页面
function goOrderReturnPage(id,orderNo)
{

	var url = '../bcReturnTicket/checkBcOrder.action?bcReturnVo.orderNo='+orderNo;
	$.ajax({
		url:url,		
		cache:false,
		async : false,
		success:function(data){
		  if(data=="success"){
			  $("#iframe_right",parent.parent.window.document).attr("src","../bcReturnTicket/getBcReturnedDetail.action?bcReturnVo.orderId="+id+"&random="+Math.floor(Math.random()*10000+1));  
		  }else{
			  parent.parent.showCommonTip("该订单是优惠订单,不允许退票");
			}
		},error:function(){
			parent.parent.showCommonTip("存在异常");
		}
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

//分页enter按键处理函数
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	//ok
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
</html>