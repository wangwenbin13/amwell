<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
    <title>运营平台－线路-设置班次</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="renderer" content="webkit"/> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible"/>
	<jsp:include page="../resource.jsp"/>
	<style>
	    .errorTip{
	        display:inline-block;
	        color:#d1261e;
	        background:#f7e7e9;
	        margin-left:10px;
	        padding:0 5px;
	    }
	    .timeChangeTip{
	    	padding-left:5px;
	    	padding-right:5px;
	    	float:left;
	    	margin-left:20px;
	    	color:#d1261e;
	    	background-color:cornsilk;
	    	line-height: 23px;
	    }
	</style>
</head>
<body>
	<input type="hidden" id="startYearAndMonth" value="${startYearAndMonth}"/>
	<input type="hidden" id="endYearAndMonth" value="${endYearAndMonth}"/>
	<!-- 运营平台－创建线路-上下班-工作时间 -->
    <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布线路 <span class="blue1 ml25"></span><span>
    <s:if test="%{gobackSign==1}"><a class="gray2" href="javascript:goback();">返回</a></s:if>
    </span></p></div>
    <div class="steps">
    	<ol class="clearfix">
			<c:if test="${lineBaseId==null}">
				<li><i>1</i><span class="tsl">线路规划</span></li>
				<li class="active"><i>2</i><span class="tsl">设置班次</span></li>
				<li><i>3</i><span class="tsl">定价格</span></li>
				<li><i>4</i><span class="tsl">设置供应商</span></li>
				<li><i>5</i><span class="tsl">完成设置</span></li>
			</c:if>
			<c:if test="${lineBaseId!=null}">
				<a href="../publishLine/loadLineBaseInfo.action?lineBaseId=${lineBaseId}"><li><i>1</i><span class="tsl">线路规划</span></li></a>
				<a href="../publishLine/loadClassInfo.action?lineBaseId=${lineBaseId}"><li class="active"><i>2</i><span class="tsl">设置班次</span></li></a>
				<a href="../publishLine/loadClassPrice.action?lineBaseId=${lineBaseId}"><li><i>3</i><span class="tsl">定价格</span></li></a>
				<a href="../publishLine/loadBusinessInfo.action?lineBaseId=${lineBaseId}"><li><i>4</i><span class="tsl">设置供应商</span></li></a>
				<a href="../publishLine/loadCommitLine.action?lineBaseId=${lineBaseId}"><li><i>5</i><span class="tsl">完成设置</span></li></a>
			</c:if>
		</ol>		
    </div>
   <s:form id="addForm" method="post" theme="simple">
   <input type="hidden" name="lineBaseId" value="${lineBaseId}"/>
   <input type="hidden" name="orderDateArr" value="${orderDateArr}"/>
   <input type="hidden" name="deletingOrderDates" value=""/>
   <ul class="mt20 ml45">
   		<li class="mt20 line24 clearfix">
   			<span class="fl w90 t-r">班次时间：<em class="red1">*</em></span>
   			<span class="fl r-input w170 t-l">
   			    <c:if test="${orderStartTime==null}">
	   			    <input name="orderStartTime" id="orderStartTimeStart" type="text" 
	   					value="" readonly="readonly" class="fl Wdate75 gray2 Wdate" 
	   					onClick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})"/>
   			    </c:if>
   			    <c:if test="${orderStartTime!=null}">
   			    	<input name="orderStartTimeShow" id="orderStartTimeStart" type="text" 
	   					value="${orderStartTime}" readonly="readonly"  class="fl Wdate75 gray2 Wdate"/>
	   				<input name="orderStartTime" id="orderStartTime" type="hidden" value="${orderStartTime}"/>
   			    </c:if>
   				
   			</span>
			<span class="ml30 gray1">如：18:15</span>
			<c:if test="${orderStartTime!=null}">
				<a href="javascript:void(0);" class="blue1 ml30" onclick="changeTime()">修改班次时间</a>
			</c:if>
			<span id="orderStartTimeError" class="errorTip"></span>
		</li>
		<c:if test="${lineTimeChange!=null}">
			<li class="mt20 line24 clearfix">
			    <div class="timeChangeTip">修改的班次时间：${lineTimeChange.a1},生效时间是：${lineTimeChange.a2} </div>
			</li>
		</c:if>
   		<li class="mt20 line24 clearfix">
   			<span class="fl w90 t-r">座位数：<em class="red1">*</em></span>
   			<input type="text" maxLength="3" name="orderSeats" value="${orderSeats}" class="r-input w175 mr5 fl"/>
   			<span class="t-l fl">位</span>
   			<span class="ml30 gray1">如：50</span>
   			<span id="orderSeatsError" class="errorTip"></span>
   		</li>
   		<li class="mt20 line24 clearfix">
   			<span class="fl w90 t-r">设置工作日：<em class="red1">*</em></span>
   			<div class="sh-add dateList fl" style="width:630px;">
				<P class="gray1"><span class="fl">周一至周五（不含节假日） (周一/周二/周三/周四/周五)</span></P><span id="orderDateError" class="errorTip"></span>
				<div style="width:630px;overflow-y:auto;max-height:370px;">
					<div class="clearfix">
						<span class="fl fw mt15" id="currMonth"></span>
						<div class="fr">
							<span id="checkAll1" onclick="clickCheckAll('tableDate0','checkAll1')" class="btn display_ib ml10 mr10">取消全部</span>
						</div>
					</div>
					<table class="date-table newtd mt5" id="tableDate0">
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
						<span class="fl fw mt15" id="nextOneMonth"></span>
						<div class="fr mt15">
							<span id="checkAll2" onclick="clickCheckAll('tableDate1','checkAll2');" class="btn display_ib ml10 mr10">取消全部</span>
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
						<span class="fl fw mt15" id="nextTwoMonth"></span>
						<div class="fr mt15">
							<span id="checkAll3" onclick="clickCheckAll('tableDate2','checkAll3');" class="btn display_ib ml10 mr10">取消全部</span>
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
				<P class="mt10 gray1">
					<span class="dateTd-bg-box dateTd-bg1-box mr5"></span>为工作日(为可预定的日期)
					<span class="dateTd-bg-box dateTd-bg2-box ml15 mr5"></span>为周六、周日（为不可预定日期）
				</P>				
			</div> 
   		</li>
   		<li class="mt20 line24 clearfix">
   			<span class="fl w90 t-r"></span>
   			自动放票
   			<c:if test="${lineBaseInfo.autoPutTicket=='true'||lineBaseInfo.autoPutTicket=='30'}">
   			    <input type="radio" name="autoPutTicket" class="input-r" value="30" checked="checked"/>
   			    <span style="line-height:30px;margin-left:5px;">30天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="7"/>
   			    <span style="line-height:30px;margin-left:5px;">7天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="3"/>
   			    <span style="line-height:30px;margin-left:5px;">3天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value=""/>
   			    <span style="line-height:30px;margin-left:5px;">0天</span>
   			</c:if>
   			<c:if test="${lineBaseInfo.autoPutTicket=='7'}">
   				<input type="radio" name="autoPutTicket" class="input-r" value="30"/>
   			    <span style="line-height:30px;margin-left:5px;">30天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="7" checked="checked"/>
   			    <span style="line-height:30px;margin-left:5px;">7天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="3"/>
   			    <span style="line-height:30px;margin-left:5px;">3天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value=""/>
   			    <span style="line-height:30px;margin-left:5px;">0天</span>
   			</c:if>
   			<c:if test="${lineBaseInfo.autoPutTicket=='3'}">
   				<input type="radio" name="autoPutTicket" class="input-r" value="30"/>
   			    <span style="line-height:30px;margin-left:5px;">30天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="7"/>
   			    <span style="line-height:30px;margin-left:5px;">7天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="3" checked="checked"/>
   			    <span style="line-height:30px;margin-left:5px;">3天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value=""/>
   			    <span style="line-height:30px;margin-left:5px;">0天</span>
   			</c:if>
   			<c:if test="${lineBaseInfo.autoPutTicket==null||lineBaseInfo.autoPutTicket==''||lineBaseInfo.autoPutTicket=='false'}">
   			    <input type="radio" name="autoPutTicket" class="input-r" value="30"/>
   			    <span style="line-height:30px;margin-left:5px;">30天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="7"/>
   			    <span style="line-height:30px;margin-left:5px;">7天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="3"/>
   			    <span style="line-height:30px;margin-left:5px;">3天</span>
   			    <input type="radio" name="autoPutTicket" class="input-r" value="" checked="checked"/>
   			    <span style="line-height:30px;margin-left:5px;">0天</span>
   			</c:if>
   		</li>
   		<p class="clearfix mt30 line28">
   			<span class="fl w90 t-r"></span>
   			<a class="blue1" href="../publishLine/loadLineBaseInfo.action?lineBaseId=${lineBaseId}">上一步</a>
   			<a class="display-ib btn1 white ml30" href="javascript:saveSetDate('1');">保存当前数据</a>
   			<a class="display-ib btn1 white ml30" href="javascript:saveSetDate('2');">下一步</a>
   		</p>
   </ul>
	</s:form>
	<div class="showbox" style="width:1000px;">
		<h2>温馨提醒：您正在修改发车时间，请谨慎操作！<a class="close" href="javascript:void(0);">关闭</a></h2>
		<div class="showboxContent">
			<div class="mainlist">
			    <form id="changeTimeForm">
		   			<input type="hidden" name="cid" id="lineId_change"/>
		   			<p class="f14" id="changeInfo"></p>
					<div class="pop-main-tips mt10" style="height:auto;line-height:25px;padding-top:5px;padding-bottom:5px;font-size:16px;box-shadow:inset 1px -1px 0 #f0edd5,inset -1px 1px 0 #f0edd5">
						我要把线路为<span class="red1 ml4 mr4 fw" id="lineName_change">TK102</span>
						的发车时间由原来的<span class="red1 ml4 mr4 fw" id="lineTime_old">8:15</span>
						改为<input class="r-input ml4" name="ctime" readonly="readonly" type="text" id="lineTime_change" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})" size="8" value=""/>,
						生效时间为<span class="r-input w140 display-ib" style="vertical-align:-7px;width:200px;"><input class="Wdate75 gray2 Wdate" type="text" name="workTime" readonly="readonly" id="workTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d}'})"/></span>
						<p>并且<select id="changeMSG" name="changeMSG" onchange="changeMSG1()" class="p3 w15p">
							<option selected="selected" value="1">不发送</<option>
							<option value="0">发送</option></select>短信。
						</p>
					</div>
					<textarea name="content" id="content" disabled="disabled" class="mt10 textarea"></textarea>
				</form>
	   		</div>
		   	<p class="clearfix mt30 line28" style="text-align:center;">
			    <a class="display-ib btn1 white mr40 f14 updateTime" href="javascript:void(0);">确&nbsp;定</a>
		   	</p>
   		</div>
	</div>
</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
var id = "${lineBaseId}";
var orderDateArray = "${orderDateArray}".split(';');
if (orderDateArray.length && !orderDateArray[orderDateArray.length - 1]) {
	orderDateArray.pop();
}
var originOrderDates = orderDateArray.slice(0);
var isOk = true;
var isSubmitAvailable = true;

String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}

$(document).ready(function(){	
	//默认显示当前系统时间
	showDate();
	changeCheckAllText("tableDate0","checkAll1");
	changeCheckAllText("tableDate1","checkAll2");
	changeCheckAllText("tableDate2","checkAll3");
	bindDateClickCheckboxChange("tableDate0","checkAll1");
	bindDateClickCheckboxChange("tableDate1","checkAll2");
	bindDateClickCheckboxChange("tableDate2","checkAll3");
});

// 绑定日历表格的事件
function bindDateClickCheckboxChange(tableId,checkId){
	$("#"+tableId +" [type='checkbox']:enabled").click(function(){
		var $theWeek=$(this).parent().parent().parent().attr("week");
    	if($theWeek!="星期六"&&$theWeek!="星期天"){//排除周末
    		changeCheckAllText(tableId,checkId);
        }
	});
}

// 更新全选按钮的状态
function changeCheckAllText(tableId,checkAllId){
	var checkboxEnabledCount=0;
	$("#"+tableId + " [type='checkbox']:enabled").each(function(){
    	var $theWeek=$(this).parent().parent().parent().attr("week");
    	if($theWeek!="星期六"&&$theWeek!="星期天"){
           checkboxEnabledCount++;
        }
	});
	var checkboxCheckedCount=$("#"+tableId + " [type='checkbox']:enabled:checked").length;
	if(checkboxEnabledCount==checkboxCheckedCount){
       $("#"+checkAllId).text("取消全选");
	}else{
		$("#"+checkAllId).text("全选");
	}
}

//显示当前时间的日历
function showDate(){
	//当期月
	var currYear = $("#startYearAndMonth").val();
	//下个月
	var nextOneMonth = getNextMonthDate(currYear,1);
	//下下个月
	var nextTwoMonth = getNextMonthDate(nextOneMonth,1);
	$("#currMonth").text(currYear);
	$("#nextOneMonth").text(nextOneMonth);
	$("#nextTwoMonth").text(nextTwoMonth);
	initTable("tableDate0",getMonthDays(currYear.split("-")[0],currYear.split("-")[1]),getWeekDay(currYear + "-1"),currYear);
	initTable("tableDate1",getMonthDays(nextOneMonth.split("-")[0],nextOneMonth.split("-")[1]),getWeekDay(nextOneMonth + "-1"),nextOneMonth);
	initTable("tableDate2",getMonthDays(nextTwoMonth.split("-")[0],nextTwoMonth.split("-")[1]),getWeekDay(nextTwoMonth + "-1"),nextTwoMonth);
}

//获取下个月，或者下下个月
function getNextMonthDate(date,flag){
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

// 根据月份获取天数
function getMonthDays(year,month){
	var leap_year = judgeLeapYear(year); 
	var month = month - 1;
	// 4, 6, 9, 11 月为 30 天，注意上面的month = month - 1 
	if (month == 3 || month == 5 || month == 8 || month == 10) { 
		return 30; 
	} else if (month == 1) {// 2 月为 28 或者 29 天，视是否为闰年而定
		return 28 + leap_year; 
	} else {// 其它月则为 31 天 
		return 31; 
	}
}

// 根据年份判断是否是闰年还是平年
function judgeLeapYear(year){
	// 闰年是指年号能被4整除、但不能被100整除，二是年号能被4整除，又能被400整除,闰年二月有29天，平年二月是28天
	var leap =(year % 4 == 0 && year % 100 != 0)||(year % 400 == 0) ? 1 : 0;
    return leap;
}

// 根据日期获取星期几
function getWeekDay(dateStr){
	var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];  
	var myDate = new Date(Date.parse(dateStr.replace(/-/g, "/")));  
	return weekDay[myDate.getDay()];
}

//动态创建表格
function initTable(id,monthDays,weekDay,selectYear){
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	var thLength = $("#"+id+" tr th").length;
	var trTmp = "";
	for(var i = 0;i < 6;i++){
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
		if(weekDay == $("#"+id+"date_time"+j).attr("week")){
			preCount = j - 1;
			for(var m = 0;m < monthDays;m++){
				var el;
				if(!orderDateArray.length){
					if($("#"+id+"date_time"+parseInt(m+j)).attr("week") == "星期六" || $("#"+id+"date_time"+parseInt(m+j)).attr("week") == "星期天"){
						el = $("#"+id+"date_num"+parseInt(m+j)).html((m+1)+"<p class='t-c'><input type='checkbox' class='checkbox' name='dateCheck' value='"+(showYear+"-"+showMonth+"-"+parseDate((m+1)))+"'/></p>");
					}else{
						el = $("#"+id+"date_num"+parseInt(m+j)).html((m+1)+"<p class='t-c'><input type='checkbox' class='checkbox' name='dateCheck' value='"+(showYear+"-"+showMonth+"-"+parseDate((m+1)))+"'/></p>");
					}
				}else{
					var dateValue = showYear+"-"+showMonth+"-"+parseDate((m+1));
					if($.inArray(dateValue, orderDateArray)>=0){
					    el = $("#"+id+"date_num"+parseInt(m+j)).html((m+1)+"<p class='t-c'><input type='checkbox' class='checkbox' name='dateCheck' value='"+(showYear+"-"+showMonth+"-"+parseDate((m+1)))+"' checked='true'/></p>");
					}else{
						el = $("#"+id+"date_num"+parseInt(m+j)).html((m+1)+"<p class='t-c'><input type='checkbox' class='checkbox' name='dateCheck' value='"+(showYear+"-"+showMonth+"-"+parseDate((m+1)))+"'/></p>");
					}
				}
				el.find('input[type=checkbox]').click(function() {
					var cb = $(this);
					if (cb.prop('checked')) {
						orderDateArray.push(cb.val());
					} else {
						orderDateArray = $.grep(orderDateArray, function(od) {
							return od != cb.val();
						});
					}
				});
				if(selectYear == currYear){
					if(dayIndex == m){
						$("#"+id+"date_time"+parseInt(m+preCount)).css({"background":"#e9ad1d"});
					}else if(m < dayIndex){
						$("#"+id+"date_time"+parseInt(m+preCount)).css({"background":"#e7e5e5"});
						$("#"+id+"date_num"+parseInt(m+preCount)).children("p").find("input").attr("checked",false);
						$("#"+id+"date_num"+parseInt(m+preCount)).children("p").find("input").attr("disabled","disabled");
					}
				}
			}
		}
	}
	//后一个月剩余展示的天数
	var nextCount = 0;
	nextCount = 42-(monthDays+preCount);
	if(nextCount >= 7){
		$("#"+id+"tr5").hide();
	}else{
		$("#"+id+"tr5").show();
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
	return myDate.getFullYear() + "-" + parseDate(month) + "-" + myDate.getDate();
}

//如果数据小于10.加一个0
function parseDate(date){
	if (date < 10) {
		date = '0' + date;
	}
	return date;
}

function clickCheckAll(tableId,checkAllId){
	if($("#"+checkAllId).text()=='全选'){
    	$("#"+tableId).find("[type='checkbox']:enabled").each(function(){
        	var $theWeek=$(this).parent().parent().parent().attr("week");
        	if($theWeek!="星期六"&&$theWeek!="星期天"){//排除周末
        		$ (this).attr("checked",true);
        		var index = $.inArray($(this).val(), orderDateArray);
        		if(index==-1){
        			orderDateArray.push($(this).val());
        		}
            }
   	    });
    	$("#"+checkAllId).text("取消全选");
    }else{
    	$("#"+tableId).find("[type='checkbox']:enabled").each(function(){
    		var $theWeek=$(this).parent().parent().parent().attr("week");
    		if($theWeek!="星期六"&&$theWeek!="星期天"){//排除周末
            	$(this).attr("checked",false);
            	orderDateArray.splice($.inArray($(this).val(),orderDateArray),1);
    		}
   	    });
    	$("#"+checkAllId).text("全选");
    }
	console.log(orderDateArray);
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

//获取选中日期
function saveSetDate(redirectType){
	$(".errorTip").html("");
	var orderStartTime = $("[name='orderStartTime']").val();
	if(orderStartTime==null||orderStartTime==""){
        $("#orderStartTimeError").html("请填写班次时间");
        return;
    }
    var orderSeats = $("[name='orderSeats']").val();
    if(orderSeats==null||orderSeats==""){
        $("#orderSeatsError").html("请填写座位数");
        return;
    }
    var num_regx=new RegExp("^[1-9][0-9]*$");
    if(!num_regx.test(orderSeats)){
        $("#orderSeatsError").html("请填写座位数格式不正确");
        return;
    }
	if(!orderDateArray.length){
        $("#orderDateError").html("请设置工作日");
        return;
	}
	if(isSubmitAvailable){
    	isSubmitAvailable = false;
		$("[name='orderDateArr']").val(orderDateArray.join(';'));
		var deleting = $.grep(originOrderDates, function(od) {
			return $.inArray(od, orderDateArray) < 0;
		});
		$('[name=deletingOrderDates]').val(deleting.join(';'));
		var url = "../publishLine/saveClassInfo.action";
		var theForm = $("#addForm");
		leasePostAjax(url,theForm,"json",function(data){
			isSubmitAvailable = true;
	         if(data.a1=="100"){
		         if(redirectType=="1"){
			         // 成功提示
	        	     parent.parent.showRturnTip("保存成功",true); 
		         }else{
			         // 跳转到设置班次价格的界面
                     document.location.href="../publishLine/loadClassPrice.action?lineBaseId=${lineBaseId}";
			     }
		     }else if(data.a1=="201"){
		    	 parent.parent.showRturnTip("座位数不能小于已购票的最大票数（从今天起）");
		     }else{
		    	 parent.parent.showRturnTip("保存失败",false); 
			 }
		});
    }
}

function changeMSG1(){
	var value = $("#changeMSG").val();
	if(value=="0"){
        $("#content").attr("disabled",false);
	}else{
        $("#content").attr("disabled",true);
    }
}

//修改班次时间弹窗/////////////////////////////////////////////////////////////
//修改发车时间(弹窗)
function changeTime(){
		$("#content").val("【小猪巴士】尊敬的先生/小姐，小猪巴士A→B线路发车时间将于XXXX-XX-XX更改XX:XX发车，请各位预计好时间准时乘坐，谢谢大家的支持！");
		$("#content").attr("disabled",true); 
		$("#lineName_change").html("${lineBaseInfo.lineName}");
		$("#lineTime_old").html("${orderStartTime}");
		$("#lineId_change").val("${lineBaseInfo.lineBaseId}");
		$("#lineTime_change").val("");
		$("#changeInfo").html("");
		$("#workTime").val("");
		var box =300;
		var th= $(window).scrollTop()+$(window).height()/1.6-box;
		var h =document.body.clientHeight;
		var rw =$(window).width()/2-box;
		$(".showbox").animate({top:th,opacity:'show',width:1000,height:150,right:rw},500);
		$("body").prepend("<div class='mask'></div>");
		$(".mask").css({opacity:"0.5"}).css("height",h);
		return false;
}

$(".showbox .updateTime").click(function(){
	var cid = $("#lineId_change").val();
	var ctime = $("#lineTime_change").val();
	var workTime = $("#workTime").val();
	var changeMSG = $("#changeMSG ").val();
	var content = $("#content").val();
	if(ctime=="" || ctime == null || workTime == "" || workTime == null){
		alert("请将信息填完整！");
		return;
	}
	if(confirm("想清楚了要进行此操作吗？")){
		var url = '../line/updateLineTime.action';
		leasePostAjax(url,$("#changeTimeForm"),"text",function(data){
			if(data=="success"){
				document.location.href="../publishLine/loadClassInfo.action?lineBaseId=${lineBaseId}";
		    }else{
		    	alert("失败");
		    }
		});
		$(this).parents(".showbox").animate({top:0,opacity: 'hide',width:0,height:0,right:0},500);
		$(".mask").fadeOut("fast");
		return false;
	}
});

$(".showbox .close").click(function(){
	$("#lineTime_change").val("");
	$(this).parents(".showbox").animate({top:0,opacity: 'hide',width:0,height:0,right:0},500);
	$(".mask").fadeOut("fast");
	return false;
});

//返回上一步
function goback(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="line/getLinesList.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="../line/getLinesList.action?level=two";
	}
	
	window.location.href=$the_url;
}

</script>