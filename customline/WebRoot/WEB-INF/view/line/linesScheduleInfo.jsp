<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－线路详情-调度信息</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <!-- 隐藏线路信息 -->
    <input id="lineBaseId" type="hidden" value="${lineScheduleInfo.lineBaseId}">
    <p class="pop-main-tips mt10 gray2">商家：${lineScheduleInfo.brefName}<span class="ml20">联系人：${lineScheduleInfo.contacts}</span><span class="ml20">联系电话：${lineScheduleInfo.contactsPhone}</span></p>
    <div class="mt20 gray2 p-r classNumList">
    	<span>选择班次：</span>
    	<s:iterator value="lineScheduleInfo.lineClassList" var="c" status="s">
    	  <input type="radio" name="className" class="checkbox mr5" value="${c.orderStartTime}" <s:if test="#s.index==0">checked="checked"</s:if> onclick="getDiffDateInfo(${s.index},'${c.orderStartTime}')"/>${c.orderStartTime}&nbsp;&nbsp;${c.orderSeats}位
    	</s:iterator>
    	
   		<div style="left: 50px; top: 29px;" class="dj-box p-r p-a">
	        <span class="arrow2 p-a" id="arrowIcon" style="left: 7px;"></span>
	       
	        <p class="mt15">
				<select id="yearSelect" class="p3">
					<option value="2013">2013</option>
					<option value="2014">2014</option>
					<option value="2015">2015</option>
					<option value="2016">2016</option>
					<option value="2017">2017</option>
					<option value="2018">2018</option>
					<option value="2019">2019</option>
					<option value="2020">2020</option>
					<option value="2021">2021</option>
					<option value="2022">2022</option>
					<option value="2023">2023</option>
					<option value="2024">2024</option>
					<option value="2025">2025</option>
				</select>
				<select id="monthSelect" class="p3">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
				</select>
				<input type="button" class="btn-blue2 ml20" value="返回今天" id="getCurrTime"/>
			</p>
	        <table class="date-table mt15 f12 mb20" id="tableDate">
				<tr>
					<th width="90" class="fw">星期一</th>
					<th width="90" class="fw">星期二</th>
					<th width="90" class="fw">星期三</th>
					<th width="90" class="fw">星期四</th>
					<th width="90" class="fw">星期五</th>
					<th width="90" class="fw">星期六</th>
					<th width="90" class="fw">星期天</th>
				</tr>
			</table>   
	    </div>
    </div>
  </body>
</html>
<script type="text/javascript">
var lineBaseId = $("#lineBaseId").val();
var orderStartTime = "";
var orderDate ="";
//根据不同的班次展示不同的日历
function getDiffDateInfo(classType,classTime){
    var leftWidth=(parseInt(classType)*100+10)+"px";
	$("#arrowIcon").css("left",leftWidth);
	showCurrDate();
}

//json数据
var jsonDate = null;
function loadData(lineBaseId,orderStartTime,orderDate){
    var url = "line/getClassCarDriver.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime+"&orderDate="+orderDate;
  	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			//alert(data);
			jsonDate = data;
		}
	});
}

//根据年份判断是否是闰年还是平年
function judgeLeapYear(year)
{
	//闰年是指年号能被4整除、但不能被100整除，二是年号能被4整除，又能被400整除,闰年二月有29天，平年二月是28天
	var leap =(year % 4 == 0 && year % 100 != 0)||(year % 400 == 0) ? 1 : 0;
    return leap;
}

//根据月份获取天数
function getMonthDays(year,month)
{
	var leap_year = judgeLeapYear(year); 
	var month = month - 1;
	/*4, 6, 9, 11 月为 30 天，注意上面的month = month - 1*/ 
	if (month == 3 || month == 5 || month == 8 || month == 10) { 
		return 30; 
	} else if (month == 1) {/*2 月为 28 或者 29 天，视是否为闰年而定*/ 
		return 28 + leap_year; 
	} else {/*其它月则为 31 天*/ 
		return 31; 
	}
}

//根据日期获取星期几
function getWeekDay(dateStr)
{
	var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];  
	var myDate = new Date(Date.parse(dateStr.replace(/-/g, "/")));  
	return weekDay[myDate.getDay()];
}

//获取下一个月天数
function getNextMonth(date) {
	var arr = date.split('-');
	var year = arr[0]; //获取当前日期的年份
	var month = arr[1]; //获取当前日期的月份
	var year2 = year;
	var month2 = parseInt(month) + 1;
	if (month2 == 13) {
		year2 = parseInt(year2) + 1;
		month2 = 1;
	}
	if (month2 < 10) {
		month2 = '0' + month2;
	}
	var t2 = getMonthDays(year2,month2);
	return t2;
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
	return myDate.getFullYear() + "-" + month + "-" + myDate.getDate();
}

$(function(){	
	//后台调用
	$("#yearSelect,#monthSelect").change(function(){
		var yearIndex = $("#yearSelect").find("option:selected").text();
		var monthIndex = $("#monthSelect").find("option:selected").text();
		//选中的年月
		var selectYear = yearIndex + "-" + monthIndex;
		var dateTime = yearIndex + "-" + monthIndex + "-1";
		//获取月的天数
		var monthDays = getMonthDays(yearIndex,monthIndex);
		//获取选择的月份是周几
		var weekDay = getWeekDay(dateTime);
		//获取下一个月天数
		var nextMonthDay = getNextMonth(yearIndex+" -"+monthIndex);
		//后台调用
		orderStartTime = $("input[name='className']:checked").val();
		orderDate= selectYear;
		loadData(lineBaseId,orderStartTime,orderDate);
		initTable("tableDate",monthDays,weekDay,nextMonthDay,selectYear,jsonDate);
	});
	
	//返回今天
	$("#getCurrTime").click(function(){
		showCurrDate();
	});
	
	//默认显示当前系统时间
	showCurrDate();
	
});

//显示当前时间的日历
function showCurrDate()
{
    
	var yearIndex = getCurrTime().split("-")[0];
	var monthIndex = getCurrTime().split("-")[1];
	//选中的年月
	var selectYear = yearIndex + "-" + monthIndex;
	$("#yearSelect").val(yearIndex);
	$("#monthSelect").val(monthIndex);
	var dateTime = yearIndex + "-" + monthIndex + "-1";
	//获取月的天数
	var monthDays = getMonthDays(yearIndex,monthIndex);
	//获取选择的月份是周几
	var weekDay = getWeekDay(dateTime);
	//获取下一个月天数
	var nextMonthDay = getNextMonth(yearIndex+" -"+monthIndex);
	//后台调用
	orderStartTime = $("input[name='className']:checked").val();
	orderDate= selectYear;
	loadData(lineBaseId,orderStartTime,orderDate);
	initTable("tableDate",monthDays,weekDay,nextMonthDay,selectYear,jsonDate);
}


//动态创建表格
function initTable(id,monthDays,weekDay,nextMonthDay,selectYear,jsonDate)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	var thLength = $("#"+id+" tr th").length;
	var trTmp = "";
	for(var i = 0;i < 6;i++)
	{
		var $table = $("#"+id);
		
		var $tr = $("<tr id='tr"+i+"'>" +
		"<td height='80' class='dateTd-bg1' id='date_time"+parseInt(thLength*i+1)+"' week='星期一'><p class='t-c' id='date_num"+parseInt(thLength*i+1)+"'></p></td>"+   
		"<td height='80' class='dateTd-bg1' id='date_time"+parseInt(thLength*i+2)+"' week='星期二'><p class='t-c' id='date_num"+parseInt(thLength*i+2)+"'></p></td>"+ 
		"<td height='80' class='dateTd-bg1' id='date_time"+parseInt(thLength*i+3)+"' week='星期三'><p class='t-c' id='date_num"+parseInt(thLength*i+3)+"'></p></td>"+
		"<td height='80' class='dateTd-bg1' id='date_time"+parseInt(thLength*i+4)+"' week='星期四'><p class='t-c' id='date_num"+parseInt(thLength*i+4)+"'></p></td>"+ 
		"<td height='80' class='dateTd-bg1' id='date_time"+parseInt(thLength*i+5)+"' week='星期五'><p class='t-c' id='date_num"+parseInt(thLength*i+5)+"'></p></td>"+ 
		"<td height='80' class='dateTd-bg2' id='date_time"+parseInt(thLength*i+6)+"' week='星期六'><p class='t-c' id='date_num"+parseInt(thLength*i+6)+"'></p></td>"+ 
		"<td height='80' class='dateTd-bg2' id='date_time"+parseInt(thLength*i+7)+"' week='星期天'><p class='t-c' id='date_num"+parseInt(thLength*i+7)+"'></p></td>"+ 
		+"</tr>"); 
		$table.append($tr);
	} 
	//前一个月份剩余展示天数
	var preCount = 0;
	
	//当前年月
	var yearIndex = getCurrTime().split("-")[0];
	var monthIndex = getCurrTime().split("-")[1];
	var dayIndex = getCurrTime().split("-")[2];
	var currYear = yearIndex + "-" + monthIndex;
	
	var showYear = selectYear.split("-")[0];
	var showMonth = parseDate(selectYear.split("-")[1]);
	for(var j = 1;j<8;j++){
		//所选月份展示天数
		if(weekDay == $("#date_time"+j).attr("week"))
		{
			preCount = j - 1;
			for(var m = 0;m < monthDays;m++)
			{
				if(selectYear == currYear && dayIndex == m)
				{
					
					$("#date_time"+parseInt(m+preCount)).css({"background":"#e9ad1d"});
				}
				$("#date_num"+parseInt(m+j)).html(m+1+"<p class='mt7' id='defaultText'><span class='gray3'>未设置</span></p>");
				for(var n = 0; n < jsonDate.length;n++)
				{
					if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
					{
						
						$("#date_num"+parseInt(m+j)).children().eq(0).remove();
						$("#date_num"+parseInt(m+j)).append("<p class='mt7'><span class='blue3'>"+jsonDate[n].driverName+"</span></p><p class='mt7'><span class='blue3'>"+jsonDate[n].vehicleNumber+"("+jsonDate[n].vehicleSeats+"座)"+"</span></p>");
					}
				}
			}
		}
	}
	//后一个月剩余展示的天数
	var nextCount = 0;
	nextCount = 42-(monthDays+preCount);
	if(nextCount >= 7)
	{
		$("#tr5").hide();
	}
	else
	{
		$("#tr5").show();
	}
} 
</script>