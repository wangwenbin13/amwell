<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单管理-日统计查询</title>
<jsp:include page="../resource.jsp"/>
<jsp:include page="../public_select.jsp"/>
</head>

<body>
  <div class="mt10 black1">
  
   <form name="" action="../charteredOrder/getDayExpendList.action" method="post" id="searchForm">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
        <li class="w26p">
           	<span class="fl">时间：</span>
               <span class="r-input fl w36p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w36p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" 
            onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}',maxDate:'${maxDate}'})"/></span>
        </li>
        <li class="w14p">
        	<span class="fl w65 t-r">订单号：</span><s:textfield name="search.field03" theme="simple" cssClass="fl r-input gray2 w54p mt2"/>    
        </li>
        <li class="w14p">
        	<span class="fl w65 t-r">联系电话：</span><s:textfield name="search.field04" theme="simple" cssClass="fl r-input gray2 w54p mt2"/>    
        </li>
         <li class="w14p">
        	<span class="fl w65 t-r">包车商家：</span>
        	<span class="fl w58p p-r">
		    	<span class="p-a arrow arrow-desc" style="top:10px;right:4%"></span>
		        <input class="fl r-input gray2 w98p mt2" id="selectMgrBusinName" width="w98p" value="${search.field05 }" name="search.field05"/>
	        </span>
        </li>	
         <li class="w27p"><span class="t-r w65 fl">包车类型：</span>
        	<s:select cssClass="w22p p3" list="#{0:'所有类型',1:'旅游包车',2:'商务接送',3:'过港接送'}" theme="simple" name="search.field06">
	        </s:select>
	         <input type="submit" class="btn-blue4 ml10" value="查找" />
	         <s:reset value="重置" cssClass="btn-blue4" onclick="clearFormValue('searchForm')" theme="simple"/>
	         <a href="javascript:void(0);" class="blue1 ml10" onclick="toExport();">下载搜索结果</a>
        </li> 	
  	</ul>
  	<input type="hidden" name="maxDate" value="${maxDate }"/>     
  </form> 
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
            <table width="100%" border="0" class="table1">
              <tr align="center">
              	<th scope="col" width="3%">序号</th>
              	<th scope="col" width="5%">日期</th>
              	<th scope="col" width="8%">订单号</th>
                <th scope="col" width="5%">用户名称</th>
                <th scope="col" width="8%">联系电话</th>
                <th scope="col" width="5%">包车类型</th>
                <th scope="col" width="8%">上车点</th>
                <th scope="col" width="8%">下车点</th>
                <th scope="col" width="3%">人数</th>
                <th scope="col" width="3%">车辆数</th>
                <th scope="col" width="7%">包车方式</th>
                <th scope="col" width="8%">出车时间</th>
                <th scope="col" width="8%">返程时间</th>
                <th scope="col" width="8%">包车商家</th>
                <th scope="col" width="5%">金额（元）</th>
                <th scope="col" width="8%">支出原因</th>
              </tr>
              <s:if test="%{page.pageCount == 0}">
                  <tr align="center" class="bg1">
     					<td colspan="16"><p class="f13 f-yahei gray4 t-c line26 mt10">
     					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
     			  </tr>
              </s:if>
              <s:iterator value="list" var="bcOutVo" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
							<td class="f-arial">${currentPageIndex+s.count}</td>
                    		<td class="f-arial">${bcOutVo.operateTime}</td>
                    		<td class="f-arial">${bcOutVo.orderNo}</td>
                    		<td class="f-arial">${bcOutVo.nickName}</td>
                    		<td class="f-arial">${bcOutVo.telephone}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#bcOutVo.businessType==1}"><em>旅游包车  </em></s:if>
                    			<s:if test="%{#bcOutVo.businessType==2}"><em>商务接送  </em></s:if>
                    			<s:if test="%{#bcOutVo.businessType==3}"><em>过港接送  </em></s:if>
                    		</td>
                    		<td class="f-arial">${bcOutVo.beginAddress}</td>
                    		<td class="f-arial">${bcOutVo.endAddress}</td>
                    		<td class="f-arial">${bcOutVo.totalPassengers}</td>
                    		<td class="f-arial">${bcOutVo.totalCars}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#bcOutVo.charteredMode==1}"><em>单趟  </em></s:if>
                    			<s:if test="%{#bcOutVo.charteredMode==2}"><em>往返  </em></s:if>
                    			<s:if test="%{#bcOutVo.charteredMode==3}"><em>包天 </em></s:if>
                    		</td>
                    		<td class="f-arial">${bcOutVo.startTime}</td>
                    		<td class="f-arial">${bcOutVo.returnTime}</td>
                    		<td class="f-arial">${bcOutVo.brefName}</td>
                    		<td class="f-arial">${bcOutVo.bc_out_money}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#bcOutVo.bc_out_type==1}"><em>退票  </em></s:if>
                    		</td>
                    	</tr>
                   	</s:iterator>
                    <s:if test="%{page.pageCount != 0}">
		              <tr align="center" class="trbg4 fw">
		                <td  colspan="15" align="right">合计（元）：</td>
		                <td class="f-arial"><em class="yellow1 fw">￥${totalMoney }</em></td>
		              </tr>
	              </s:if>
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
<form action="../charteredOrder/getDayExpendList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>       
<form action="../charteredOrder/exportDayExpendList.action" method="post" style="display: none;" id="export">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
 </form>      
</div>


<!-- 搜索内容 -->
<!-- 商家名称 -->
<div class="hide">
  <select id="selectMgrBusin">
  </select>
</div>

</body>
</html>

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
				//$(this).val(""); 
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

//导出
function toExport(){
	var listSize = "${listSize}";
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
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