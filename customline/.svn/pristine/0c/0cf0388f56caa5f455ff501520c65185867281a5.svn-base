<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－创建线路-添加工作时间</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <input type="hidden" id="startYearAndMonth" value="${startYearAndMonth}">
    <input type="hidden" id="endYearAndMonth" value="${endYearAndMonth}">
    <div class="pop black1" style="width:730px;background:#fff;" id="popDatePage">
	<div class="pop-t">
    	<div class="pop-t-l fl fw white">添加工作时间</div>
        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onclick="closePopDatePage();"></a></div>
    </div>
    <div class="pop-main p10">
    	<div class="sh-add dateList mr20 ml20">
			<P class="gray1"><span class="fl">周一至周五（不含节假日） (周一/周二/周三/周四/周五)</span></P>
			<div style="width:635px;max-height:370px;overflow-y:auto;">
				<div class="clearfix">
					<span class="fl fw mt15" id="currYear_0"></span>
					
					<div  class="fr mt15">
					<span id="supportMonthSpan1"><input id="supportMonthForChild1" type="checkbox" class="checkbox mr5" name="supportMonthForChild" value="1"/>支持包月</span>
					<span id="checkAll1" class="btn display_ib ml10 mr10">取消全部</span>
					</div>
					
				</div>
				<table class="date-table newtd mt5" id="tableDate">
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
				<div class="clearfix">
					<span class="fl fw mt15" id="nextOneYear_0"></span>
					
					<div  class="fr mt15">
					<span id="supportMonthSpan2"><input id="supportMonthForChild2" type="checkbox" class="checkbox mr5" name="supportMonthForChild" value="1"/>支持包月</span>
					<span id="checkAll2" class="btn display_ib ml10 mr10">取消全部</span>
					</div>
					
				</div>
				<table class="date-table newtd mt5" id="tableDate1">
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
				<div class="clearfix">
					<span class="fl fw mt15" id="nextTwoYear_0"></span>
					
					<div  class="fr mt15">
					<span id="supportMonthSpan3"><input id="supportMonthForChild3" type="checkbox" class="checkbox mr5" name="supportMonthForChild" value="1"/>支持包月</span>
					<span id="checkAll3" class="btn display_ib ml10 mr10">取消全部</span>
					</div>
					
				</div>
				<table class="date-table newtd mt5" id="tableDate2">
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
			<P class="mt10 gray1"><span class="dateTd-bg-box dateTd-bg1-box mr5"></span>为工作日(为可预定的日期)<span class="dateTd-bg-box dateTd-bg2-box ml15 mr5"></span>为周六、周日（为不可预定日期）</P>				
		</div> 
    </div>
    <p class="t-c mb20 mt20">
	        <input type="button" value="确&nbsp;&nbsp;定" class="display-ib btn1 white f14 mr20" onclick="saveSetDate()"/>
	        <input type="button" value="取&nbsp;&nbsp;消" class="display-ib btn1 white f14" onclick="closePopDatePage();"/>
        </p>
</div>
  </body>
</html>
<script type="text/javascript">
//获取下个月，或者下下个月
function getNextMonthDate(date,flag)
{
	var arr = date.split('-');
	var year = arr[0]; //获取当前日期的年份
	var month = arr[1]; //获取当前日期的月份
	var year2 = year;
	var month2 = parseInt(month) + parseInt(flag);
	
	if (month2 >= 13) {
		year2 = parseInt(year2) + 1;
		month2 = parseInt(flag);
	}
	if (month2 < 10) {
		month2 = '0' + month2;
	}
	return year2+"-"+month2;
}

function getNoWeekDate(date)
{
	//获取月的天数
	var monthDays = getMonthDays(date.split("-")[0],date.split("-")[1]);
	var noWeekDate = "";
	for(var i = 1; i <= monthDays;i++)
	{
		
		//每一天是周几
		var weekDay = getWeekDay(date+"-"+i);
		if(weekDay == "星期六" || weekDay == "星期天")
		{
			
		}
		else
		{
			if(getCurrTime().split("-")[0] +"-"+ getCurrTime().split("-")[1] == date)
			{
				if(i >= parseInt(getCurrTime().split("-")[2]))
				{
					noWeekDate += (date+"-"+parseDate(i)) + ";";
				}
			}
			else
			{
				noWeekDate += (date+"-"+parseDate(i)) + ";";
			}
		}
	}
	return noWeekDate;
}

String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}

var id = null;
var theSupportMonth=null;
//获取选中日期
function saveSetDate()
{
	var allSelectDate = getCheckDateIds();
	$("#"+id).val(allSelectDate);
	
	var supportMonthValue = "";
	$("input[name='supportMonthForChild']").each(function(){
        supportMonthValue+=$(this).attr("checked")=="checked"?"1,":"0,";
    });
    supportMonthValue=supportMonthValue.substring(0,supportMonthValue.length-1);
	
	$("#"+theSupportMonth).val(supportMonthValue);
	
	closePopDatePage();
}
//关闭日历弹出层页面
function closePopDatePage()
{
    $("#popDatePage").hide();
    $("#mainBody").hide();
    $("#topHide", window.parent.document).hide();
    $("#leftHide", window.parent.document).hide();
    var allSelectDate = getCheckDateIds();
    var count = id.substring(14,id.length);
	if(allSelectDate != "")
	{
		$("#onCorrectNew"+count).show();
		$("#onErrorNew"+count).css("display","none");
	}
	else
	{
		$("#onErrorNew"+count).show();
		$("#onCorrectNew"+count).css("display","none");
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

$(function(){	
    id = "<%=request.getParameter("id")%>";
    theSupportMonth="<%=request.getParameter("theSupportMonth")%>";
	//上下班才显示包月，其余都不显示
	var isShow = "<%=request.getParameter("isShow")%>";
	if(isShow == "0")
	{
		$("#supportMonthSpan1").show();
		$("#supportMonthSpan2").show();
		$("#supportMonthSpan3").show();
	}
	else
	{
		$("#supportMonthSpan1").hide();
		$("#supportMonthSpan2").hide();
		$("#supportMonthSpan3").hide();
	}

	//默认显示当前系统时间
	showDate();


	//全选按钮1
	changeCheckAllText("tableDate","checkAll1");
	
	$("#checkAll1").click(function(){
		clickCheckAll("tableDate","checkAll1");
	});
	
	$("#tableDate",parent.window.document).find("[type='checkbox']:enabled").click(function(){
		var $theWeek=$(this).parent().parent().parent().attr("week");
    	if($theWeek!="星期六"&&$theWeek!="星期天"){//排除周末
    		changeCheckAllText("tableDate","checkAll1");
        }
	});
	//全选按钮2
	changeCheckAllText("tableDate1","checkAll2");
	
	$("#checkAll2").click(function(){
		clickCheckAll("tableDate1","checkAll2");
	});
	
	$("#tableDate1",parent.window.document).find("[type='checkbox']:enabled").click(function(){
		var $theWeek=$(this).parent().parent().parent().attr("week");
    	if($theWeek!="星期六"&&$theWeek!="星期天"){//排除周末
    		changeCheckAllText("tableDate1","checkAll2");
        }
	});
	//全选按钮3
	changeCheckAllText("tableDate2","checkAll3");
	
	$("#checkAll3").click(function(){
		clickCheckAll("tableDate2","checkAll3");
	});
	
	$("#tableDate2",parent.window.document).find("[type='checkbox']:enabled").click(function(){
		var $theWeek=$(this).parent().parent().parent().attr("week");
    	if($theWeek!="星期六"&&$theWeek!="星期天"){//排除周末
    		changeCheckAllText("tableDate2","checkAll3");
        }
	});
});

function changeCheckAllText(tableId,checkAllId){
	var checkboxEnabledCount=0;
	$("#"+tableId,parent.window.document).find("[type='checkbox']:enabled").each(function(){
    	var $theWeek=$(this).parent().parent().parent().attr("week");
    	if($theWeek!="星期六"&&$theWeek!="星期天"){//排除周末
    		checkboxEnabledCount++;
        }
	});
	var checkboxCheckedCount=$("#"+tableId,parent.window.document).find("[type='checkbox']:enabled:checked").length;
	if(checkboxEnabledCount==checkboxCheckedCount){
       $("#"+checkAllId,parent.window.document).text("取消全选");
	}
	else{
		$("#"+checkAllId,parent.window.document).text("全选");
	}
}

function clickCheckAll(tableId,checkAllId){
	if($("#"+checkAllId).text()=='全选'){
    	$("#"+tableId).find("[type='checkbox']:enabled").each(function(){
        	var $theWeek=$(this).parent().parent().parent().attr("week");
        	if($theWeek!="星期六"&&$theWeek!="星期天"){//排除周末
        		$(this).attr("checked",true);
            }
   	    });
    	$("#"+checkAllId).text("取消全选");
    }
    else{
    	$("#"+tableId).find("[type='checkbox']:enabled").each(function(){
            $(this).attr("checked",false);
   	    });
    	$("#"+checkAllId).text("全选");
    }
}

//显示当前时间的日历
function showDate()
{
	//当期月
	var currYear = $("#startYearAndMonth").val();
	//下个月
	var nextOneMonth = getNextMonthDate(currYear,1);
	//下下个月
	var nextTwoMonth = getNextMonthDate(currYear,2);

	$("#currYear_0").text(currYear);
	$("#nextOneYear_0").text(nextOneMonth);
	$("#nextTwoYear_0").text(nextTwoMonth);
	initTable("tableDate",getMonthDays(currYear.split("-")[0],currYear.split("-")[1]),getWeekDay(currYear + "-1"),currYear);
	initTable("tableDate1",getMonthDays(nextOneMonth.split("-")[0],nextOneMonth.split("-")[1]),getWeekDay(nextOneMonth + "-1"),nextOneMonth);
	initTable("tableDate2",getMonthDays(nextTwoMonth.split("-")[0],nextTwoMonth.split("-")[1]),getWeekDay(nextTwoMonth + "-1"),nextTwoMonth);
}


//动态创建表格
function initTable(id,monthDays,weekDay,selectYear)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	var thLength = $("#"+id+" tr th").length;
	var trTmp = "";
	for(var i = 0;i < 6;i++)
	{
		var $table = $("#"+id);
		
		var $tr = $("<tr id='"+id+"tr"+i+"'>" +
		"<td height='65' class='dateTd-bg1' id='"+id+"date_time"+parseInt(thLength*i+1)+"' week='星期一'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+1)+"'></p></td>"+   
		"<td height='65' class='dateTd-bg1' id='"+id+"date_time"+parseInt(thLength*i+2)+"' week='星期二'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+2)+"'></p></td>"+ 
		"<td height='65' class='dateTd-bg1' id='"+id+"date_time"+parseInt(thLength*i+3)+"' week='星期三'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+3)+"'></p></td>"+
		"<td height='65' class='dateTd-bg1' id='"+id+"date_time"+parseInt(thLength*i+4)+"' week='星期四'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+4)+"'></p></td>"+ 
		"<td height='65' class='dateTd-bg1' id='"+id+"date_time"+parseInt(thLength*i+5)+"' week='星期五'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+5)+"'></p></td>"+ 
		"<td height='65' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+6)+"' week='星期六'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+6)+"'></p></td>"+ 
		"<td height='65' class='dateTd-bg2' id='"+id+"date_time"+parseInt(thLength*i+7)+"' week='星期天'><p class='t-c' id='"+id+"date_num"+parseInt(thLength*i+7)+"'></p></td>"+ 
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
	var showMonth = selectYear.split("-")[1];
	var orderDateStart0 = $("#orderDateStart0").val();
	for(var j = 1;j<8;j++){
		//所选月份展示天数
		if(weekDay == $("#"+id+"date_time"+j).attr("week"))
		{
			preCount = j - 1;
			for(var m = 0;m < monthDays;m++)
			{
				if($("#"+id+"date_time"+parseInt(m+j)).attr("week") == "星期六" || $("#"+id+"date_time"+parseInt(m+j)).attr("week") == "星期天") 
				{
					$("#"+id+"date_num"+parseInt(m+j)).html((m+1)+"<p class='t-c'><input type='checkbox' class='checkbox' name='dateCheck' value='"+(showYear+"-"+showMonth+"-"+parseDate((m+1)))+"'/></p>");
				}
				else
				{
					$("#"+id+"date_num"+parseInt(m+j)).html((m+1)+"<p class='t-c'><input type='checkbox' class='checkbox' name='dateCheck' value='"+(showYear+"-"+showMonth+"-"+parseDate((m+1)))+"' checked='checked'/></p>");
				}
				if(selectYear == currYear)
				{
					
					if(dayIndex == m)
					{
						$("#"+id+"date_time"+parseInt(m+preCount)).css({"background":"#e9ad1d"});
					}
					else if(m < dayIndex)
					{
						$("#"+id+"date_time"+parseInt(m+preCount)).css({"background":"#e7e5e5"});
						$("#"+id+"date_num"+parseInt(m+preCount)).children("p").find("input").attr("checked",false);
						$("#"+id+"date_num"+parseInt(m+preCount)).children("p").find("input").attr("disabled","disabled");
					}
				}
				if(orderDateStart0!=null&&orderDateStart0!=""){
					var orderDateStart0Array = orderDateStart0.split(";");
					for(var ii=0;ii<orderDateStart0Array.length;ii++){
                       if(orderDateStart0Array[ii]==showYear+"-"+showMonth+"-"+parseDate((m+1))){
                    	   $("#"+id+"date_num"+parseInt(m+j)).children("p").find("input").attr("checked",true);
                    	   break;
                       }else{
                    	   $("#"+id+"date_num"+parseInt(m+j)).children("p").find("input").attr("checked",false);
                       }
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
		$("#"+id+"tr5").hide();
	}
	else
	{
		$("#"+id+"tr5").show();
	}
} 

//获取选中的所有id
function getCheckDateIds()
{
	var checkboxs = $("input[name='dateCheck']");
	var dateIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox")
		{
			if (checkboxs[i].checked)
			{
				dateIds = dateIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != dateIds)
	{
		dateIds = dateIds.substring(0, dateIds.length - 1);
	}
	
	return dateIds;
}

//获取未选中的所有id
function getNotCheckDateIds()
{
	var checkboxs = $("input[name='dateCheck']");
	var dateIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox")
		{
			if (!checkboxs[i].checked)
			{
				dateIds = dateIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != dateIds)
	{
		dateIds = dateIds.substring(0, dateIds.length - 1);
	}
	
	return dateIds;
}

//动态创建下拉框
/**
id select下拉框id
flag=year表示年，month表示月
**/
function initSelectData(id,flag)
{
	$("#"+id).find("option").remove();
	if(flag == "year")
	{
		var currYear = getCurrTime().split("-")[0];
		for(var i = currYear;i <= 2099;i++)
	　　{
	　　　　$("#"+id).append("<option value='"+i+"'>"+i+"</option>");
	　      }
	}
}
</script>
