<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－用户、订单管理-改签弹窗</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <input type="hidden" id="beginSelectDate" />
    <input type="hidden" id="startYearAndMonth" value="${startYearAndMonth}">
    <input type="hidden" id="endYearAndMonth" value="${endYearAndMonth}">
    <div class="pop black1" style="width:730px;background:#fff;" id="PopOrderChangePage">
	<div class="pop-t">
    	<div class="pop-t-l fl fw white">改签</div>
        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onclick="loadPage.window.closePopOrderChangePage();"></a></div>
    </div>
    <div class="pop-main p10 clearfix">
    	<div class="mb10 clearfix">
    		<span class="fl">选择班次：</span>
	    	
	        <select class="r-input fl w210 mr10" id="classTime"></select>
    	</div>
    	<span class="fl">选择日期：</span>
    	<div class="fl sh-add dateList">
    	<div class="t-c fw">
    		<a href="javascript:void(0);" class="btn fl" id="delMonthPre" onclick="loadPage.window.addOrDelMonth('del','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre')">上一月</a>
    		<span id="yearSelectPre"></span>年<span id="monthSelectPre"></span>月
    		<a href="javascript:void(0);" class="btn fr" id="addMonthPre" onclick="loadPage.window.addOrDelMonth('add','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre')">下一月</a>
    	</div>
			<div style="width:595px;max-height:370px;overflow-y:auto;">
				<table class="date-table newtd2 mt5" id="tableDatePre">
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
			<P class="mt10 gray1"><span class="dateTd-bg-box dateTd-bg3 mr5"></span>可改签<span class="dateTd-bg-box dateTd-bg7-box ml15 mr5"></span>满座<span class="dateTd-bg-box dateTd-bg4-box ml15 mr5"></span>已购买<span class="dateTd-bg-box dateTd-bg6-box ml15 mr5"></span>无班次</P>				
		</div> 
    </div>
    <div id="tipError" class="ml100 hide" style="color:#d1261e;"></div>
    <p class="t-c mb20 mt20">
	        <input type="button" value="确&nbsp;&nbsp;定" class="display-ib btn1 white f14 mr20" onclick="loadPage.window.saveSelectDate();" />
	        <input type="button" value="取&nbsp;&nbsp;消" class="display-ib btn1 white f14 mr20" onclick="loadPage.window.closePopOrderChangePage();"/>
     </p>
</div>
  </body>
</html>
<script type="text/javascript">
//json数据
var jsonDate = [{
	"orderDate":"2014-10-20",//orderDate
	"status":"-2",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-21",//orderDate
	"status":"12",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-23",//orderDate
	"status":"56",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-24",//orderDate
	"status":"0",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-25",//orderDate
	"status":"2",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-26",//orderDate
	"status":"21",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-27",//orderDate
	"status":"15",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-28",//orderDate
	"status":"12",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-29",//orderDate
	"status":"18",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-30",//orderDate
	"status":"-2",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-10-31",//orderDate
	"status":"22",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-11-01",//orderDate
	"status":"33",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-11-02",//orderDate
	"status":"50",// -1无班次；-2 已购买；0满座；>0可改签
},{
	"orderDate":"2014-11-03",//orderDate
	"status":"9",// -1无班次；-2 已购买；0满座；>0可改签
}];

//关闭增加弹出层页面
function closePopOrderChangePage()
{
    $("#PopOrderChangePage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}
var leaseOrderId=null;
var lineBaseId=null;
var passengerId=null;
var orderStartTime=null;
var orderDate=null;
//初始化日历
$(function(){	
    leaseOrderId="<%=request.getParameter("leaseOrderId")%>";
    lineBaseId="<%=request.getParameter("lineBaseId")%>";
    passengerId="<%=request.getParameter("passengerId")%>";
    orderStartTime="<%=request.getParameter("orderStartTime")%>";
    orderDate="<%=request.getParameter("orderDate")%>";
    
	//默认显示当前系统时间
	showCurrDate();
});

//获取当前日期前面几天或者后面几天
function getCurrDayPreOrNextDay(flag,dayParamater)
{
 	var d = new Date();
 	if(flag == "prev")  //前面几天
 	{
 		//当前日期的毫秒数 - 天数 * 一天的毫秒数
   		var n = d.getTime() - dayParamater * 24 * 60 * 60 * 1000;
 	}
 	else if(flag == "next")  //后面几天
 	{
 		//当前日期的毫秒数 + 天数 * 一天的毫秒数
   		var n = d.getTime() + dayParamater * 24 * 60 * 60 * 1000;
 	}
    var result = new Date(n);
    return result.getFullYear() + "-" + parseDate(result.getMonth() + 1) + "-" + parseDate(result.getDate());
}

//增加或者减少一个月
/*
参数说明
statu:       add增加或者del删除
yearIndex:   所选的年份,
monthIndex:  所选的月份,
yearSelect:  所选年份下拉框id,
monthSelect: 所选月份下拉框id,
*/
function addOrdDelOneMonth(statu,yearIndex,monthIndex,yearSelect,monthSelect)
{
	var yearIndex = yearIndex;
	var monthIndex = monthIndex;
	if(statu == "add")
	{
		monthIndex = parseInt(monthIndex) + 1;
		if (monthIndex == 13) {
			yearIndex = parseInt(yearIndex) + 1;
			if(yearIndex == 2100)
			{
				yearIndex = 2109;
				return;
			}
			monthIndex = 1;
		}
	}
	else if(statu == "del")
	{
		monthIndex = parseInt(monthIndex) - 1;
		if (monthIndex == 0) {
			yearIndex = parseInt(yearIndex) - 1;
			if(yearIndex == 2013)
			{
				yearIndex = 2014;
				return;
			}
			monthIndex = 12;
		}
	}
	else if(statu == "default")
	{
		monthIndex = parseInt(monthIndex);
		yearIndex = parseInt(yearIndex);
	}
	$("#"+yearSelect,parent.window.document).text(yearIndex);
	$("#"+monthSelect,parent.window.document).text(monthIndex);
}

//增加或者减少一年
/*
参数说明
statu:       add增加或者del删除
yearIndex:   所选的年份,
yearSelect:  所选年份下拉框id,
*/
function addOrdDelOneYear(statu,yearIndex,yearSelect)
{
	var yearIndex = yearIndex;
	if(statu == "add")
	{
		yearIndex = parseInt(yearIndex) + 1;
		if(yearIndex == 2110)
		{
			yearIndex = 2109;
			return;
		}
	}
	else if(statu == "del")
	{
		yearIndex = parseInt(yearIndex) - 1;
		if(yearIndex == 2013)
		{
			yearIndex = 2014;
			return;
		}
	}
	else if(statu == "default")
	{
		yearIndex = parseInt(yearIndex);
	}
	$("#"+yearSelect,parent.window.document).text(yearIndex);
}

//增加或者减少月份
/*
参数说明
statu:     add增加或者del删除
yearPre:   yearSelectPre左侧日历年份下拉框id,
monthPre:  monthSelectPre左侧日历月份下拉框id,
yearNext:  yearSelectNext右侧日历年份下拉框id,
monthNext: monthSelectNext右侧日历月份下拉框id,
tableFlag: tableDatePre左侧日历table的id;tableDateNext右侧日历table的id,
*/
function addOrDelMonth(statu,yearPre,monthPre,yearNext,monthNext,tableFlag)
{
	//左侧的日历
	if(tableFlag == "tableDatePre")
	{
		//前一个月赋新值
		var yearIndexPre = $("#"+yearPre,parent.window.document).text();
		var monthIndexPre = $("#"+monthPre,parent.window.document).text();
		addOrdDelOneMonth(statu,yearIndexPre,monthIndexPre,yearPre,monthPre);
		showChangeDate(statu,yearPre,monthPre,"tableDatePre");
	}	
}

//增加或者减少年份
/*
参数说明
statu:     add增加或者del删除
yearPre:   yearSelectPre左侧日历年份下拉框id,
monthPre:  monthSelectPre左侧日历月份下拉框id,
yearNext:  yearSelectNext右侧日历年份下拉框id,
monthNext: monthSelectNext右侧日历月份下拉框id,
tableFlag: tableDatePre左侧日历table的id;tableDateNext右侧日历table的id,
*/
function addOrDelYear(statu,yearPre,monthPre,yearNext,monthNext,tableFlag)
{
	//左侧的日历
	if(tableFlag == "tableDatePre")
	{
		//前一年赋新值
		var yearIndexPre = $("#"+yearPre,parent.window.document).text();
		addOrdDelOneYear(statu,yearIndexPre,yearPre);
		showChangeDate(statu,yearPre,monthPre,"tableDatePre");
	}
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
	var month = myDate.getMonth()+1;
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

//显示当前时间的日历
function showCurrDate()
{
	var yearIndex = getCurrTime().split("-")[0];
	var monthIndex = getCurrTime().split("-")[1];
	loadLineOrderStartTimes();
    loadPassengerOrderData();
	//左侧日历显示
	$("#yearSelectPre",parent.window.document).text(yearIndex);
	$("#monthSelectPre",parent.window.document).text(parseInt(monthIndex,10));
	addOrDelMonth('default','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre');
}

//改变年月展示日历
/*
参数说明
year:   所选年份下拉框id,
month:  所选月份下拉框id,
tableFlag: tableDatePre左侧日历table的id;tableDateNext右侧日历table的id,
*/
function showChangeDate(statu,year,month,tableFlag)
{
	var yearIndex = $("#"+year,parent.window.document).text();
	var monthIndex = $("#"+month,parent.window.document).text();
	//选中的年月
	var selectYear = yearIndex + "-" + monthIndex;
	var dateTime = yearIndex + "-" + monthIndex + "-1";
	//获取月的天数
	var monthDays = getMonthDays(yearIndex,monthIndex);
	//获取选择的月份是周几
	var weekDay = getWeekDay(dateTime);
	//获取下一个月天数
	var nextMonthDay = getNextMonth(yearIndex+" -"+monthIndex);
	initTable(statu,tableFlag,monthDays,weekDay,nextMonthDay,selectYear);
}

//动态创建表格
function initTable(statu,id,monthDays,weekDay,nextMonthDay,selectYear)
{
	$("#"+id+" tr",parent.window.document).eq(0).nextAll().remove(); //除第一行外删除所有的tr
	var thLength = $("#"+id+" tr th",parent.window.document).length;
	var trTmp = "";
	for(var i = 0;i < 6;i++)
	{
		var $table = $("#"+id,parent.window.document);
		//-- 选中dateTd-bg3-box -- 满座dateTd-bg1  -- 已购买dateTd-bg1
		var $tr = $("<tr id='"+id+"tr"+i+"'>" +
		"<td height='40' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+1)+"' week='星期一'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+1)+"'></p></td>"+   
		"<td height='40' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+2)+"' week='星期二'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+2)+"'></p></td>"+ 
		"<td height='40' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+3)+"' week='星期三'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+3)+"'></p></td>"+
		"<td height='40' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+4)+"' week='星期四'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+4)+"'></p></td>"+ 
		"<td height='40' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+5)+"' week='星期五'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+5)+"'></p></td>"+ 
		"<td height='40' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+6)+"' week='星期六'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+6)+"'></p></td>"+ 
		"<td height='40' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+7)+"' week='星期天'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+7)+"'></p></td>"+ 
		+"</tr>"); 
		$table.append($tr);
	} 
	
	
	//系统显示的当前年月
	var yearIndex = getCurrTime().split("-")[0];
	var monthIndex = getCurrTime().split("-")[1];
	var dayIndex = getCurrTime().split("-")[2];
	var currYear = yearIndex + "-" + monthIndex;
	
	//下拉框所选的年月
	var showYear = selectYear.split("-")[0];
	var showMonth = parseDate(selectYear.split("-")[1]);
	selectYear = showYear + "-" + showMonth;
	//前一个月份剩余展示天数
	var preCount = 0;
	//后一个月剩余展示的天数
	var nextCount = 0;
	
	//显示系统当前时间的后15天
	var nextDate = getCurrDayPreOrNextDay("next",14);
	var selectDate = $("#beginSelectDate",parent.window.document).val();

	for(var j = 1;j<8;j++){
		//所选月份展示天数
		if(weekDay == $("#"+id+"date_time"+j,parent.window.document).attr("week"))
		{
			//前一个月份剩余展示天数
			preCount = j - 1;
			for(var m = 0;m < monthDays;m++)
			{
				$("#"+id+"date_num"+parseInt(m+j),parent.window.document).html(m+1);
				$("#"+id+"date_time"+parseInt(m+j),parent.window.document).attr("date",selectYear+"-"+parseDate((m+1)));

				//系统当前日期 需要选中样式
				/*if(selectYear == currYear && dayIndex == parseDate(m+1) && selectDate == getCurrTime())
				{
					$("#"+id+"date_time"+parseInt(m+j),parent.window.document).removeClass("dateTd-bg2 dateTd-bg3").addClass("dateTd-bg3-box");
				} */
				//选中的日期增加选中样式
				if(selectDate == (selectYear+"-"+parseDate(m+1)))
				{
					$("#"+id+"date_time"+parseInt(m+j),parent.window.document).removeClass("dateTd-bg1 dateTd-bg2 dateTd-bg3 dateTd-bg4").addClass("dateTd-bg3-box");
				}
				//当期日期后天
				if(($("#"+id+"date_time"+parseInt(m+j),parent.window.document).attr("date") > getCurrTime()) && ($("#"+id+"date_time"+parseInt(m+j),parent.window.document).attr("date") <= nextDate))
				{
					$("#"+id+"date_time"+parseInt(m+j),parent.window.document).removeClass("dateTd-bg1 dateTd-bg2 dateTd-bg3 dateTd-bg4").addClass("dateTd-bg4");
				}
				//status: -1无班次；-2 已购买；0满座；>0可改签
				for(var n = 0; n < jsonDate.length;n++)
				{
					if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
					{
						if(jsonDate[n].status ==-1)
						{
							$("#"+id+"date_time"+parseInt(m+preCount+1),parent.window.document).removeClass("dateTd-bg1 dateTd-bg2 dateTd-bg3 dateTd-bg4").addClass("dateTd-bg4");
						}
						else if(jsonDate[n].status ==-2)
						{
							$("#"+id+"date_time"+parseInt(m+preCount+1),parent.window.document).removeClass("dateTd-bg1 dateTd-bg2 dateTd-bg3 dateTd-bg4").addClass("dateTd-bg4-box");
						}
						else if(jsonDate[n].status == 0)
						{
							$("#"+id+"date_time"+parseInt(m+preCount+1),parent.window.document).removeClass("dateTd-bg1 dateTd-bg2 dateTd-bg3 dateTd-bg4").addClass("dateTd-bg7-box");
						}
						else if(jsonDate[n].status > 0)
						{
							$("#"+id+"date_time"+parseInt(m+preCount+1),parent.window.document).removeClass("dateTd-bg1 dateTd-bg2 dateTd-bg3 dateTd-bg4").addClass("dateTd-bg3");
						}
					}
				}
				
			}
		}
	}
	
	//前一个月份剩余展示天数
	for(var n = 1;n <= preCount;n++)
	{
		$("#"+id+"date_time"+n,parent.window.document).removeClass("dateTd-bg1 dateTd-bg2 dateTd-bg3 dateTd-bg4");
	}
	
	//后一个月剩余展示的天数
	nextCount = 42 - (monthDays+preCount);
	if(nextCount >= 7)
	{
		$("#"+id+"tr5",parent.window.document).hide();
	}
	else
	{
		$("#"+id+"tr5",parent.window.document).show();
	}
	for(var l = 1;l <= nextCount;l++)
	{
		$("#"+id+"date_time"+(monthDays+preCount+l),parent.window.document).removeClass("dateTd-bg1 dateTd-bg2 dateTd-bg3 dateTd-bg4");
	}

	$('#tableDatePre tr td[class="dateTd-bg3"]',parent.window.document).click(function(){
		$("#tipError",parent.window.document).text("");
		var selectPreDate = $(this).attr("date");
		if(typeof(selectPreDate) != "undefined")
		{
			$("#beginSelectDate",parent.window.document).val(selectPreDate);
			$(this).parent().parent().find("td").siblings().removeClass("dateTd-bg3-box");
			$(this).addClass("dateTd-bg3-box");
		}
		else
		{
			$("#beginSelectDate",parent.window.document).val("");
			$("#tipError",parent.window.document).text("").hide();
		}
	});
} 

function saveSelectDate()
{
	if($("#beginSelectDate",parent.window.document).val() == "")
	{
		$("#tipError",parent.window.document).text("请选择日期").show();
		return;
	}
	
	var newOrderStartTime = $("#classTime",parent.window.document).val();
	var newOrderDate = $("#beginSelectDate",parent.window.document).val();
	var url = "<%=path%>/changeTicket/changeTicket.action?lineBaseId="+lineBaseId+"&leaseOrderId="+leaseOrderId+"&passengerId="+passengerId+"&orderStartTime="+orderStartTime+"&orderDate="+orderDate+"&newLineBaseId="+lineBaseId+"&newOrderStartTime="+newOrderStartTime+"&newOrderDate="+newOrderDate;
    
    $.ajax({
		url:url,		
		cache:false,	
		
		async : false,
		success:function(data){
			if(data=="success"){
			    
			      parent.parent.popLodingPage(false);
				  parent.parent.showRturnTip("改签成功",true);
				  closePopOrderChangePage();
				  window.location.href="<%=path%>/changeTicket/getTicketList.action";
			}else{
			    if(data=="before"){
			       parent.parent.showRturnTip("该班次已发车，不能改签!",false);
			    }else{
			       parent.parent.showRturnTip("改签失败!",false);
			    }
			    
			}
			
		}
    });
	closePopOrderChangePage();
}

function loadLineOrderStartTimes(){
   var url = "../changeTicket/getLineOrderStartTimes.action?lineBaseId="+lineBaseId;
   $.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			initSelectData("classTime",data);
		}
   });
}

//创建班次的下拉框
function initSelectData(id,data)
{
	$("#"+id,parent.window.document).find("option").remove();
	for(var i = 0;i<data.length;i++)
	{
	  
	  if(orderStartTime==data[i].orderStartTime){
	     $("#"+id,parent.window.document).append("<option value='"+data[i].orderStartTime+"' selected='selected'>"+data[i].orderStartTime+"</option>");
	  }else{
	     $("#"+id,parent.window.document).append("<option value='"+data[i].orderStartTime+"'>"+data[i].orderStartTime+"</option>");
	  }
	}
	$("#classTime",parent.window.document).change(function(){
		loadPassengerOrderData();
	});
}

function loadPassengerOrderData(){
   var selectOrderStartTime=$("#classTime",parent.window.document).find("option:selected").text();
   var url = "../changeTicket/getPassengerOrderList.action?lineBaseId="+lineBaseId+"&orderStartTime="+selectOrderStartTime+"&passengerId="+passengerId;
   $.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
		  jsonDate=data;
		  addOrDelMonth('default','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre');
		}
   });
}


</script>
