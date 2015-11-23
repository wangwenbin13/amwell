<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<meta name="Description" content="" />
<title>支付结算-月收入统计</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;支付结算&nbsp;>&nbsp;月收入统计<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 black1 mr28">
	<form action="../statMonthIncomeAction/getMonthIncome.action" method="post" id="form1">
      	<p class="fw f14 f-arial">月收入统计</p>
      	<ul class="r-sub-sel mt20 clearfix">
			<li class="fl">
	           	<span class="t-l w65 fl">统计月度：</span>
	            <span class="r-input fl w130 mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-4,top:4},dateFmt:'yyyy-MM',maxDate:'${maxDate}'})"/></span>
       		</li>
          	<li class="fl">
          		<input class="search-btn" type="button" onclick="toSearch();"/>
          		<input type="hidden" name="searchOrNo" value="1"/>
          	</li>
         </ul>
      </form>
      <div class="mt20 p-r come-title">
      	<div class="come-icon p-a"></div>
      	<span class="ml50 f14 fw black1"></span>&nbsp;&nbsp;
      	<span class="f13 black1">总共收入：<em class="red4 fw f22" id="total_em">0</em>&nbsp;元</span>
      	<div class="fr p-r mr50 mt8">
      		<div class="export-bg black1 lh32 fw" style="width:92px;"><em class="export-icon fl mt2 ml4"></em>导&nbsp;出<span class="down-arrow fr mt13 mr4"></span></div>
	      	<select id="comeStyle" class="p-a sel-demo w93 h29" style="z-index:6;left:0;top:2px;">
			    <option value="0">请选择</option>
			    <option value="1">该月每日</option>
			    <option value="2">车辆月度</option>
			    <option value="3">线路月度</option>
			</select>
      	</div>
     </div>
     <!--  该月每日收入明细表  start -->
     <div class="mt20 fl w33p" id="byDateTable"></div>
     <!--  该月每日收入明细表  end -->
     
     <!--  车辆月度收入统计表  start -->
     <div class="mt20 fl w33p ml8" id="byBusinessTable"></div>
     <!--  车辆月度收入统计表  end -->
     
     <!--  线路月度收入统计表  start -->
     <div class="mt20 fr w33p" id="byLineTable"></div>
     <!--  线路月度收入统计表  end -->
     
</div>      
</body>
</html>
<script type="text/javascript">
$(function(){
	//导出类型
	$("#comeStyle").change(function(){
		var comeStyle = $("#comeStyle").find("option:selected").val();
		if(1==comeStyle){
			toExportDate();
		}else if(2==comeStyle){
			toExportBusiness();
		}else if(3==comeStyle){
			toExportline();
		}
	});

	//清除IE缓存
	$.ajaxSetup ({ 
		cache: false 
	});
	
	//按日期月度账目统计报表
	loadStatDate();
	//按车辆月度账目统计报表
	loadStatBusiness();
	//按线路月度账目统计报表
	loadStatLine();
});


//按日期月度账目统计报表
function loadStatDate(value){
	var date = "${search.field01}";
	var searchOrNo = "${searchOrNo}";
	var field02 = "${search.field02}";
	var url = '';
	if(null==value){
		value = '0';
	}
	url = "../statMonthIncomeAction/getDateTable.action?dateCurrentPageIndex="+value+"&search.field01="+date+"&searchOrNo="+searchOrNo+"&search.field02="+field02;
	
	$("#byDateTable").load(url);
}

//按车辆月度账目统计报表
function loadStatBusiness(value){
	var date = "${search.field01}";
	var searchOrNo = "${searchOrNo}";
	var field02 = "${search.field02}";
	var url = '';
	if(null==value){
		value = '0';
	}
	url = "../statMonthIncomeAction/getBusinessTable.action?businessCurrentPageIndex="+value+"&search.field01="+date+"&searchOrNo="+searchOrNo+"&search.field02="+field02;
	$("#byBusinessTable").load(url);
}

///按线路月度账目统计报表
function loadStatLine(value){
	var date = "${search.field01}";
	var searchOrNo = "${searchOrNo}";
	var field02 = "${search.field02}";
	var url = '';
	if(null==value){
		value = '0';
	}
	url = "../statMonthIncomeAction/getLineTable.action?lineCurrentPageIndex="+value+"&search.field01="+date+"&searchOrNo="+searchOrNo+"&search.field02="+field02;
	$("#byLineTable").load(url);
}

function toSearch(){
	$("#form1").submit();
}

//返回列表
function toBackList(){
	window.location.href="../statMonthIncomeAction/getMonthIncome.action";
}

</script>