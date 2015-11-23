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
	$("#yearSelect,#monthSelect").change(function(){
		var yearIndex = $("#yearSelect").find("option:selected").text();
		var monthIndex = $("#monthSelect").find("option:selected").text();
		//选中的年月
		var selectYear = yearIndex + "-" + monthIndex;
		var dateTime = yearIndex + "-" + monthIndex + "-1";
		//获取月的天数
		var monthDays = getMonthDays(yearIndex,monthIndex);
		initSelectData("daySelect",monthDays);
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
	initSelectData("daySelect",monthDays);
}

//动态创建下拉框
function initSelectData(id,days) {
	$("#"+id).find("option").remove();
	for(var i = 1;i <= days;i++) {
		if(i < 10) {
			i = "0" + i;
		}
		$("#"+id).append("<option value='"+i+"'>"+i+"</option>");
	}
}