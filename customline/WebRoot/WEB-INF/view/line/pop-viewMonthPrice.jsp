<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－线路详情-查看折扣价格弹窗</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
</head>
<body>
<div class="pop black1 w500" id="popMonthPage">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">查看折扣价</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="loadPage.window.closeViewPrice();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10">
    	<div class="mb10 clearfix">
	    	<span class="fl t-r w75">选择时间：</span>
	    	<div class="r-input w170">
				<input type="text" id="orderDate" name="orderDate" value="" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM',onpicked:function(dp){loadPage.window.dateChanges();}})" readonly="readonly" class="Wdate75 gray2 Wdate" />
			</div>
		</div>
        <div class="mb10 clearfix">
        	<span class="fl t-r w75">选择班次：</span>
        	<select class="p3 ml10" id="classTime"></select>
        </div>
		
		<div class="table2-text sh-ttext">
			<table class="table1"  width="100%" border="0" id="contentBody">
				<tr>
					<th scope="col">工作日(天)</th>
					<th scope="col">价格(元)</th>
					<th scope="col">折扣</th>
					<th scope="col">折扣后价格(元)</th>
				</tr>
			</table>
		</div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
//关闭增加弹出层页面
function closeViewPrice()
{
    $("#popMonthPage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide",parent.parent.window.document).hide();
    $("#leftHide",parent.parent.window.document).hide();
}

//如果数据小于10.加一个0
function parseDate(date)
{
	if (date < 10) {
		date = '0' + date;
	}
	return date;
}
//获取系统当前时间
function getCurrTime(){
	var myDate = new Date();
	var month = myDate.getMonth()+1
	var minutes = myDate.getMinutes();
	var hours = myDate.getHours();
	if(parseInt(minutes) < 10){
		minutes = "0" + minutes;
	}
	if(parseInt(hours) == 0){
		hours = "0" + hours;
	}
	return myDate.getFullYear() + "-" + parseDate(month) + "-" + myDate.getDate();
}

var lineBaseId;
var classTime;

function dateChanges()
{
	loadData();
}

$(function(){
	lineBaseId = "<%=request.getParameter("lineBaseId")%>";
	//班次时间
	classTime = "<%=request.getParameter("classTime")%>";
	initSelectData("classTime",classTime);
	
	var yearIndex = getCurrTime().split("-")[0];
	var monthIndex = getCurrTime().split("-")[1];
	//选中的年月
	var selectYear = yearIndex + "-" + monthIndex;
	$("#orderDate",parent.window.document).val(selectYear);
	loadData();
	
	//班次时间切换加载数据
	$("#classTime",parent.window.document).change(function(){
		loadData();
	});
});

function loadData(){
   var orderStartTime = $("#classTime",parent.window.document).find("option:selected").text();
   var orderDate = $("#orderDate",parent.window.document).val();
   var url = "line/getClassOneMonthOrderPrice.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime+"&orderDate="+orderDate;
   $.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			showOneMonthOrderListData(data,"contentBody");
		}
	});
}

function showOneMonthOrderListData(jsonData,id){
  $("#"+id+" tr",parent.window.document).eq(0).nextAll().remove();
  if(jsonData.length == 0)
  {
     $("#"+id,parent.window.document).append("<tr align='center' class='tr bg1'><td colspan='4'><p class='f13 f-yahei gray4 t-c line26 mt10'><span class='index-nodate mr10'></span><span id='noDate'>暂无数据</span></p></td></tr>");
  }
  else
  {
  	 var $tr = $("<tr align='center'>"+
					"<td class='green1'>"+jsonData[0].totalDays+"</td>"+
					"<td class='red1'>"+jsonData[0].totalPrice+"</td>"+
					"<td class='yellow1'>"+jsonData[0].discountRate+"折</td>"+
					"<td class='green1 fw'>"+jsonData[0].discountPrice+"</td>"+
				"</tr>");
     $("#"+id,parent.window.document).append($tr);
  }
}

//创建班次的下拉框
function initSelectData(id,arrs)
{
	var arr = arrs.split(",");
	$("#"+id,parent.window.document).find("option").remove();
	for(var i = 0;i <= arr.length-2;i++)
	{
	　　$("#"+id,parent.window.document).append("<option value='"+arr[i]+"'>"+arr[i]+"</option>");
	}
}
</script>