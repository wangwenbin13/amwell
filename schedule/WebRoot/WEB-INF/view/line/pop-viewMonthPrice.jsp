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
<title>调度平台－线路详情-查看折扣价格弹窗</title>

</head>
<body>
<div class="popMain black1" id="popMonthPage" style="width:500px;">
    <div class="popMain-t fw">查看包月价<a href="javascript:void(0);" onclick="closeViewPrice();" class="fr white1 f-arial" title="关闭">X</a></div>
    <div class="popMain-main p10">
    	<div class="mb10 clearfix">
	    	<span class="fl t-r w65 line24">选择时间：</span>
	    	<div class="r-input w130">
				<input type="text" id="orderDate" name="orderDate" value="" onclick="WdatePicker({position:{left:-4,top:4},dateFmt:'yyyy-MM',onpicked:function(dp){iframe_right.window.loadDate(false);}});" readonly="readonly" class="Wdate75 Wdate" />
			</div>
		</div>
        <div class="mt10 clearfix p-r">
        	<span class="fl t-r w65 line24">选择班次：</span>
        	<select class="p-a sel-demo w134 h29" id="classTime"></select>
           <div class="fl r-input w130"><span id="classTimeDiv"></span><span class="fr sel-arrow mt10 mr10"></span></div>           
        </div>		
		<table class="table1 mt10"  width="100%" border="0" id="table1">
			<tr class="th">
				<th scope="col">工作日(天)</th>
				<th scope="col">价格(元)</th>
				<th scope="col">折扣</th>
				<th scope="col">折扣后价格(元)</th>
			</tr>
	 	</table>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//详情id
	var lineBaseId = "<%=request.getParameter("lineBaseId")%>";
	//班次时间
	var classTime = "<%=request.getParameter("classTime")%>";
	initSelectData("classTime",classTime);
	
	//班次时间切换加载数据
	$("#classTime",parent.window.document).change(function(){
		$("#classTimeDiv",parent.window.document).text($("#classTime",parent.window.document).find("option:selected").text());
		loadDate(false);
	});
	
	//回显被选项
	$("#classTimeDiv",parent.window.document).text($("#classTime",parent.window.document).find("option:selected").text());
	loadDate(true);
});

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
	return myDate.getFullYear() + "-" + parseDate(month) + "-" + parseDate(myDate.getDate());
}

//加载数据
function loadDate(isCurr)
{
	//isCurr=true默认显示当前系统时间
	if(isCurr)
	{
		var yearIndex = getCurrTime().split("-")[0];
		var monthIndex = getCurrTime().split("-")[1];
		//显示当前系统时间
		$("#orderDate",parent.window.document).val(getCurrTime().split("-")[0]+ "-" +getCurrTime().split("-")[1]);
	}
	else
	{
		var yearIndex = $("#orderDate",parent.window.document).val().split("-")[0];
		var monthIndex = $("#orderDate",parent.window.document).val().split("-")[1];
	}
	//选中的年月
	var selectYear = yearIndex + "-" + monthIndex;
	var url = "../line/getClassOneMonthOrderPrice.action?lineBaseId="+lineBaseId+"&orderStartTime="+$("#classTimeDiv",parent.window.document).text()+"&orderDate="+selectYear;
  	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			if(data.length == 0)
			{
				createNoDate("table1");
			}
			else
			{
				initTable("table1",data);
			}
		},
		error:function()
		{
			createNoDate("table1");
		}
	});
}

//无数据
function createNoDate(id)
{
	$("#"+id+" tr",parent.window.document).eq(0).nextAll().remove(); //除第一行外删除所有的tr
	$("#"+id,parent.window.document).append("<tr align='center'><td colspan='4'><img src='../images/noDate.png' class='mt35' width='169' height='169' alt='暂无数据' /><p class='mt15 mb20 f18 f-yahei'>暂无数据</p></td></tr>");
}

//创建table数据
function initTable(id,jsonDate)
{
	$("#"+id+" tr",parent.window.document).eq(0).nextAll().remove(); //除第一行外删除所有的tr
	for(var i = 0;i < jsonDate.length;i++)
	{
		$("#"+id,parent.window.document).append("<tr align='center'><td>"+jsonDate[i].totalDays+"</td><td>"+jsonDate[i].totalPrice+"</td><td>"+jsonDate[i].discountRate+"</td><td>"+jsonDate[i].discountPrice+"</td></tr>");
	}
}
</script>