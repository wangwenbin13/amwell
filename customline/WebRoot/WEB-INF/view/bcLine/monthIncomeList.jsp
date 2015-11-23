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
<title>订单管理-月统计查询</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
  <div class="mt10 black1">
  
   <form name="" action="../bcMonthIncomeAction/getMonthIncomeList.action" method="post">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w20p">
           	<span class="t-r w65 fl">选择时间：</span>
            <span class="r-input fl w65p "><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" 
            onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM',maxDate:'${maxDate}'})"/></span>
        </li>
        <li><input type="submit" class="btn-blue4" value="查找" /></li>
  	</ul>   
  </form> 
 <!-- 按日期月度账目统计报表 start -->   
       	<div style="overflow-x:auto;overflow-y:hidden" id="byDateTable">
       	
      	</div>
  <!-- 按日期月度账目统计报表 end -->    
  
  <!-- 按商家月度账目统计报表  start -->
       	<div style="overflow-x:auto;overflow-y:hidden" id="byBusinessTable">
       		
      	</div>
      	<!-- 按商家月度账目统计报表 end -->    
      	
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//清除IE缓存
	$.ajaxSetup ({ 
		cache: false 
	});
	//清除input框默认值  
	clearInputDefaultVal();
	//按日期月度账目统计报表
	loadStatDate();
	//按商家月度账目统计报表
	loadStatBusiness();
});


//按车辆月度账目统计报表
function loadStatDate(value){
	var date = "${search.field01}";
	var url = '';
	if(null==value){
		value = '0';
	}
	url = "../bcMonthIncomeAction/getDateTable.action?dateCurrentPageIndex="+value+"&search.field01="+date;
	$("#byDateTable").load(url);
}

//按商家月度账目统计报表
function loadStatBusiness(value){
	var date = "${search.field01}";
	var url = '';
	if(null==value){
		value = '0';
	}
	url = "../bcMonthIncomeAction/getBusinessTable.action?businessCurrentPageIndex="+value+"&search.field01="+date;
	$("#byBusinessTable").load(url);
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


</script>