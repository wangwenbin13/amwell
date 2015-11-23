<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
    <title>运营平台－线路-设置供应商</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
	<style>
	    .errorTip{
	        display:inline-block;
	        color:#d1261e;
	        background:#f7e7e9;
	        margin-left:10px;
	        padding:0 5px;
	    }
	</style>
</head>
<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div id="showPagePic" class="showPage"></div>
<!-- 运营平台－创建线路-上下班-设置价格 -->
	<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布线路</p></div>
    <div class="steps">
    	<ol class="clearfix">
			<c:if test="${lineBaseId==null}">
				<li><i>1</i><span class="tsl">线路规划</span></li>
				<li><i>2</i><span class="tsl">设置班次</span></li>
				<li><i>3</i><span class="tsl">定价格</span></li>
				<li class="active"><i>4</i><span class="tsl">设置供应商</span></li>
				<li><i>5</i><span class="tsl">完成设置</span></li>
			</c:if>
			<c:if test="${lineBaseId!=null}">
				<a href="../publishLine/loadLineBaseInfo.action?lineBaseId=${lineBaseId}"><li><i>1</i><span class="tsl">线路规划</span></li></a>
				<a href="../publishLine/loadClassInfo.action?lineBaseId=${lineBaseId}"><li><i>2</i><span class="tsl">设置班次</span></li></a>
				<a href="../publishLine/loadClassPrice.action?lineBaseId=${lineBaseId}"><li><i>3</i><span class="tsl">定价格</span></li></a>
				<a href="../publishLine/loadBusinessInfo.action?lineBaseId=${lineBaseId}"><li class="active"><i>4</i><span class="tsl">设置供应商</span></li></a>
				<a href="../publishLine/loadCommitLine.action?lineBaseId=${lineBaseId}"><li><i>5</i><span class="tsl">完成设置</span></li></a>
			</c:if>
		</ol>		
    </div>
	<form action="" method="post">
	<input id="lineBaseId" name="lineBaseId" type="hidden" value="${lineBaseId}">
	<input id="orderSeats" type="hidden" value="${orderSeats}"/>
	<ul class="mt20 ml45">
   		<li class="mt20 line24 clearfix">
   			<div class="clearfix">
   			    <span class="fl w90 t-r">商家：<em class="red1">*</em></span>
   			    	 <input type="text" class="r-input w170" name="businessName" id="businessName" value="${businessName}"/>
   			    	 <font id="t_font" style="display: none;">座位数：</font><input style="display: none;" type="text" class="r-input w30" name="seatCount" id="seatCount" />
   			    	 <input type="hidden" class="r-input w170" name="businessId" id="businessId" value="${businessId}"/>
   			    	 <input type="hidden" class="r-input w170" name="businessId_temp" id="businessId_temp" value="${businessId}"/>
   			    	 <input type="hidden" class="r-input w170" name="businessIdOld" id="businessIdOld" value="${businessId}"/>
   			    	 <c:if test="${merchant==null}">
   			    	     <input type="hidden" class="r-input w170" id="firstFlag" value="1"/>
   			    	     <input type="button" value="选择供应商" onclick="selectMerchant();" class="btn-blue2 m120"/>
   			    	 </c:if>
   			    	 <c:if test="${merchant!=null}">
	   			    	 <input type="button" value="切换供应商" onclick="selectMerchant();" class="btn-blue2 ml20"/>
		   			     <input type="button" value="确定切换" onclick="setMerchant();" class="btn-blue2 ml20"/>
	   			     </c:if>
   				<span class="pop-main-tips red1 ml10 display-ib pr10" style="display:none;">修改的供应商为：壹通达旅运&nbsp;&nbsp;生效时间：2015年6月28日</span>
   				<c:if test="${merchant==null}">
   				</c:if>
   				<span class="errorTip" id="businessNameErrorTip"></span>
   			</div>
   			<c:if test="${merchant!=null}">
	   			<div class="table2-text sh-ttext ml90 mt10" style="width:666px">
		   			<table class="table1 f12" border="0" width="100%">
		   				<tr align="center">
		   					<th>商家名称</th>
		   					<th width="25%">联系人</th>
		   					<th width="25%">联系电话</th>
		   				</tr>
		   				<tr align="center">
		   					<td>${merchant.brefName}</td>
		   					<td>${merchant.contacts}</td>
		   					<td>${merchant.contactsPhone}</td>
		   				</tr>
		   			</table>
	   			</div>
   			</c:if>
   		</li>
   		<li class="mt20 line24 clearfix">
   			<p class="clearfix mb10"><span class="fl w90 t-r">调度信息：</span>
   			    <c:if test="${merchant!=null}">
   				<span class="pop-main-tips gray2 display-ib" style="width:658px;">商家：${merchant.brefName}
   					<span class="ml20">联系人：${merchant.contacts}</span>
   					<span class="ml20">联系电话：${merchant.contactsPhone}</span>
   			    </span>
   			    </c:if>
   			</p>
   			<div class="sh-add dateList ml90" style="width:630px;">
				<div style="width:630px;">
					<p>
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
					<table class="date-table mt15 f12" id="tableDate">
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
   		</li>
   		<p class="clearfix mt30 line28">
   			<span class="fl w90 t-r"></span>
   			<a class="blue1" href="../publishLine/loadClassPrice.action?lineBaseId=${lineBaseId}">上一步</a>
   			<a class="display-ib btn1 white ml30" href="javascript:saveMerchant(1);" >保存当前数据</a>
   			<a class="display-ib btn1 white ml30" href="javascript:saveMerchant(2);">下一步</a>
   		</p>
   </ul>
   </form>
</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
var lineBaseId = "${lineBaseId}";
var orderStartTime = "${orderStartTime}";
var orderDate ="";
var isOk = true;
var isSubmitAvailable = true;

//json数据
var jsonDate = null;

$(document).ready(function(){	
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

// 保存商家
function saveMerchant(redirectType){
    isOk = true;
    var firstFlag = $("#firstFlag").val();
    if(firstFlag=="1"){
    	$("#businessId").val($("#businessId_temp").val());
    }
	var businessId = $("[name='businessId']").val();
	if(businessId==null||businessId==""){
		if(redirectType!=1){
        	$("#businessNameErrorTip").html("请设置商家");
		}
        isOk = false;
    }else{
        $("#businessNameErrorTip").html("");
    }
	if(isOk){
		if(isSubmitAvailable){
			isSubmitAvailable = false;
			var url = "../publishLine/saveBusinessInfo.action?lineBaseId=${lineBaseId}&businessId="+businessId;
			leaseGetAjax(url,"json",function(data){
				isSubmitAvailable = true;
		        if(data.a1="100"){
		            if(redirectType=="1"){
		            	parent.parent.showRturnTip("保存成功。",true);
		            }else{
		            	document.location.href="../publishLine/loadCommitLine.action?lineBaseId=${lineBaseId}";
		            }
		        }else{
		        	parent.parent.showRturnTip(data.a2,false);
		        }
		    });
		}
	}
}

//选择商家
function selectMerchant(){
	var firstFlag = $("#firstFlag").val();
	if(firstFlag!="1"){
		$("#t_font").show();
		$("#seatCount").show();
	}
	$("#topHide",parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("../publishLine/loadBusinessList.action?lineBaseId=${lineBaseId}"); 
    $("#mainBody").show();
}

//根据不同的班次展示不同的日历
function getDiffDateInfo(classType,classTime){
    var leftWidth=(parseInt(classType)*100+10)+"px";
	$("#arrowIcon").css("left",leftWidth);
	showCurrDate();
}

function loadData(lineBaseId,orderStartTime,orderDate){
    var url = "../line/getClassCarDriver.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime+"&orderDate="+orderDate;
  	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			jsonDate = data;
		}
	});
}

//根据年份判断是否是闰年还是平年
function judgeLeapYear(year){
	//闰年是指年号能被4整除、但不能被100整除，二是年号能被4整除，又能被400整除,闰年二月有29天，平年二月是28天
	var leap =(year % 4 == 0 && year % 100 != 0)||(year % 400 == 0) ? 1 : 0;
    return leap;
}

//根据月份获取天数
function getMonthDays(year,month){
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
function getWeekDay(dateStr){
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
function parseDate(date){
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

//显示当前时间的日历
function showCurrDate(){
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
	orderDate= selectYear;
	loadData(lineBaseId,orderStartTime,orderDate);
	initTable("tableDate",monthDays,weekDay,nextMonthDay,selectYear,jsonDate);
}

//动态创建表格
function initTable(id,monthDays,weekDay,nextMonthDay,selectYear,jsonDate){
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	var thLength = $("#"+id+" tr th").length;
	var trTmp = "";
	for(var i = 0;i < 6;i++){
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
		if(weekDay == $("#date_time"+j).attr("week")){
			preCount = j - 1;
			for(var m = 0;m < monthDays;m++){
				if(selectYear == currYear && dayIndex == m){
					$("#date_time"+parseInt(m+preCount)).css({"background":"#e9ad1d"});
				}
				$("#date_num"+parseInt(m+j)).html(m+1+"<p class='mt7' id='defaultText'><span class='gray3'>未设置</span></p>");
				for(var n = 0; n < jsonDate.length;n++){
					if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1))){
						$("#date_num"+parseInt(m+j)).children().eq(0).remove();
						$("#date_num"+parseInt(m+j)).append("<p class='mt7'><span class='blue3'>"+jsonDate[n].driverName+"</span></p><p class='mt7'><span class='blue3'>"+jsonDate[n].vehicleNumber+"("+jsonDate[n].vehicleSeats+"座)"+"</span></p>"+"<p class='mt7'><span class='blue3'>"+jsonDate[n].businessName+"</span></p><p class='mt7'><span class='blue3'>");
					}
				}
			}
		}
	}
	//后一个月剩余展示的天数
	var nextCount = 0;
	nextCount = 42-(monthDays+preCount);
	if(nextCount >= 7){
		$("#tr5").hide();
	}else{
		$("#tr5").show();
	}
} 

function setMerchant(){
	var isok = confirm("确定要切换吗？切换后将清除此线路当天及以后的司机车辆信息。");
	if(!isok){
		return;
	}
	$("#businessId").val($("#businessId_temp").val());
	var t1 = $("#businessId").val();
	var t2 = $("#businessIdOld").val();
	var seatCount = $("#seatCount").val();
	var lineBaseId = $("#lineBaseId").val();
	if(t1==t2){
		alert("供应商没有改变！");
		return ;
	}
	if(seatCount==null || seatCount ==''){
		alert("请设置座位数，且注意数量！");
		return;
	}
	var orderSeats = $("#orderSeats").val();
	if(parseInt(seatCount)<parseInt(orderSeats)){
		alert("座位数不能比原有的座位数小。");
		return;
	}
	
	var url = "../publishLine/changeBusiness.action?seatCount="+seatCount+"&newBusiness="+t1+"&lineId="+lineBaseId+"&oldBusiness="+t2;
  	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			jsonDate = data;
			if(data=='1'){
				alert("切换成功，请及时安排车辆和司机！");
				 $("#seatCount").val('');
			}
		}
	});
	
}
</script>