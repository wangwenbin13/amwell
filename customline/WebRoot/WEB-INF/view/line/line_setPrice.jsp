<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
    <title>运营平台－线路-定价格</title>   
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
	        margin-top:10px;
	    }
	</style>
</head>
<body>
	<input type="hidden" id="startYearAndMonth" value="${startYearAndMonth}">
	<input type="hidden" id="endYearAndMonth" value="${endYearAndMonth}">
	<!-- 运营平台－创建线路-上下班-设置价格 -->
    <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布线路 <span class="blue1 ml25"></span><span>
    <s:if test="%{gobackSign==1}"><a class="gray2" href="javascript:goback();">返回</a></s:if>
    </span></p></div>
    <div class="steps">
    	<ol class="clearfix">
			<c:if test="${lineBaseId==null}">
				<li><i>1</i><span class="tsl">线路规划</span></li>
				<li><i>2</i><span class="tsl">设置班次</span></li>
				<li class="active"><i>3</i><span class="tsl">定价格</span></li>
				<li><i>4</i><span class="tsl">设置供应商</span></li>
				<li><i>5</i><span class="tsl">完成设置</span></li>
			</c:if>
			<c:if test="${lineBaseId!=null}">
				<a href="../publishLine/loadLineBaseInfo.action?lineBaseId=${lineBaseId}"><li><i>1</i><span class="tsl">线路规划</span></li></a>
				<a href="../publishLine/loadClassInfo.action?lineBaseId=${lineBaseId}"><li><i>2</i><span class="tsl">设置班次</span></li></a>
				<a href="../publishLine/loadClassPrice.action?lineBaseId=${lineBaseId}"><li class="active"><i>3</i><span class="tsl">定价格</span></li></a>
				<a href="../publishLine/loadBusinessInfo.action?lineBaseId=${lineBaseId}"><li><i>4</i><span class="tsl">设置供应商</span></li></a>
				<a href="../publishLine/loadCommitLine.action?lineBaseId=${lineBaseId}"><li><i>5</i><span class="tsl">完成设置</span></li></a>
			</c:if>
		</ol>		
    </div>
	<s:form id="addForm" method="post" theme="simple">
	<input type="hidden" name="lineBaseId" value="${lineBaseId}"/>
	<input type="hidden" name="linePrice" value="${linePrice}"/>
	<input type="hidden" name="orderStartTime" value="${orderStartTime}"/>
	<input type="hidden" name="classPriceInfoArrJson" value=""/>
	<ul class="mt20 ml45">
   		<li class="mt20 line24 clearfix">
   			<span class="fl w90 t-r">设置票惠：<em class="red1">*</em></span>
   			<div class="sh-add dateList fl" style="width:630px;">
				<P class="gray1"><span class="fl">周一至周五（不含节假日） (周一/周二/周三/周四/周五)</span></P>
				<div style="width:630px;overflow-y:auto;max-height:370px;">
					<div class="clearfix">
						<span class="fl fw mt15" id="currYear_0"></span>
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
					<span class="errorTip" id="priceErrorTip"><span>
				</P>				
			</div> 
   		</li>
   		<p class="clearfix mt30 line28">
   			<span class="fl w90 t-r"></span>
   			<a class="blue1" href="../publishLine/loadClassInfo.action?lineBaseId=${lineBaseId}">上一步</a>
   			<a class="display-ib btn1 white ml30" href="javascript:saveSetDate(1);" >保存当前数据</a>
   			<a class="display-ib btn1 white ml30" href="javascript:void(0)" onclick="saveSetDate(2);">下一步</a>
   		</p>
	</ul>
	</s:form>
	</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
var money_regx = new RegExp("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
var classPriceInfoArr;

var classPriceArrayJson;

var lineOrderPrice = "${lineOrderPrice}";

var isSubmitAvailable = true;

$(document).ready(function(){
	try{
	  classPriceArrayJson = JSON.parse("${classPriceArrayJson}");
	  showDate();
	}catch(e){
		alert('错误' + e.message + '发生在' +   e.lineNumber + '行'); 
	}
});

function getPriceByDate(date){
	var price = null;
	for(var index=0;index<classPriceArrayJson.length;index++){
         var classPrice = classPriceArrayJson[index];
         if(classPrice.orderDate==date){
              price = classPrice.price;
              break;
         }
    }
	return price;
}

// 保存价格
function saveSetDate(redirectType){
	$("#priceErrorTip").html("");
	var classPriceArr = $("[name='classPrice']");
	classPriceInfoArr = new Array();
	var isOk = true;
	$.each(classPriceArr,function(index,classPriceValue){
		var classPriceInfo = new Object();
		classPriceInfo.orderDate = $(classPriceValue).attr("dateValue");
        classPriceInfo.price = $(classPriceValue).val();
        classPriceInfoArr[classPriceInfoArr.length] = classPriceInfo;
	});
	var errorTip = "";
	var linePrice = $("[name='linePrice']").val();
	for(var index=0;index<classPriceInfoArr.length&&isOk==true;index++){
		 var classPriceInfo = classPriceInfoArr[index];
		 if(classPriceInfo.price==null||classPriceInfo.price==""){
				errorTip+=classPriceInfo.orderDate+"的价格不能为空,";
				$("#priceErrorTip").html(errorTip);
				return;
		 }else if(!money_regx.test(classPriceInfo.price)){
				errorTip+=classPriceInfo.orderDate+"的价格格式有误,";
				$("#priceErrorTip").html(errorTip);
				return;
		 }else if(classPriceInfo.price>linePrice){
			    errorTip+=classPriceInfo.orderDate+"优惠价不能高于通票价格";
			    $("#priceErrorTip").html(errorTip);
			    return;
		 }
	}
	
    var classPriceInfoArrJson = JSON.stringify(classPriceInfoArr);
    $("[name='classPriceInfoArrJson']").val(classPriceInfoArrJson);
    var url ="../publishLine/saveClassPrice.action";
    if(isSubmitAvailable){
    	isSubmitAvailable = false;
    	leasePostAjax(url,$("#addForm"),"json",function(data){
    		isSubmitAvailable = true;
            if(data.a1=="100"){
                if(redirectType=="1"){
                	// 成功提示
           	        parent.parent.showRturnTip("保存成功",true);
                }else{
                	document.location.href="../publishLine/loadBusinessInfo.action?lineBaseId=${lineBaseId}";
                }
            }else{
            	// 失败提示
       	         parent.parent.showRturnTip(data.a2,false); 
            }
        });
    }
}

// 获取下个月
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

// 获取工作日数据
function getNoWeekDate(date){
	//获取月的天数
	var monthDays = getMonthDays(date.split("-")[0],date.split("-")[1]);
	var noWeekDate = "";
	for(var i = 1; i <= monthDays;i++){
		//每一天是周几
		var weekDay = getWeekDay(date+"-"+i);
		if(weekDay == "星期六" || weekDay == "星期天"){
			
		}else{
			if(getCurrTime().split("-")[0] +"-"+ getCurrTime().split("-")[1] == date){
				if(i >= parseInt(getCurrTime().split("-")[2])){
					noWeekDate += (date+"-"+parseDate(i)) + ";";
				}
			}else{
				noWeekDate += (date+"-"+parseDate(i)) + ";";
			}
		}
	}
	return noWeekDate;
}

// 根据年份判断是否是闰年还是平年
function judgeLeapYear(year){
	//闰年是指年号能被4整除、但不能被100整除，二是年号能被4整除，又能被400整除,闰年二月有29天，平年二月是28天
	var leap =(year % 4 == 0 && year % 100 != 0)||(year % 400 == 0) ? 1 : 0;
    return leap;
}

// 根据月份获取天数
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

// 根据日期获取星期几
function getWeekDay(dateStr){
	var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];  
	var myDate = new Date(Date.parse(dateStr.replace(/-/g, "/")));  
	return weekDay[myDate.getDay()];
}

// 获取下一个月天数
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

// 如果数据小于10.加一个0
function parseDate(date){
	if (date < 10) {
		date = '0' + date;
	}
	return date;
}
// 获取系统当前时间
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

//显示当前时间的日历
function showDate(){
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
		// 所选月份展示天数
		if(weekDay == $("#"+id+"date_time"+j).attr("week")){
			preCount = j - 1;
			for(var m = 0;m < monthDays;m++){
				var checkItemDay = $("#"+id+"date_time"+parseInt(m+j));
				var dateValue = (showYear+"-"+showMonth+"-"+parseDate((m+1)));
				var dateValue = showYear+"-"+showMonth+"-"+parseDate((m+1));
				var getPriceByDateValue = getPriceByDate(dateValue);
				if(getPriceByDateValue==null){
					checkItemDay.html((m+1)+"<p class='t-c'><input type='checkbox' class='checkbox' name='dateCheck' disabled=\"disabled\" value='"+
							 dateValue +"'/></p>");
				}else{
					checkItemDay.html((m+1)+"<p class='t-c'><input type='text' class='r-input w45 t-c' name='classPrice' value='"+getPriceByDateValue+"' dateValue=\"" + dateValue + "\"/></p>");
				}
				if(selectYear == currYear){
					if(dayIndex == m){
						$("#"+id+"date_time"+parseInt(m+preCount)).css({"background":"#e9ad1d"});
					}else if(m < dayIndex){
						$("#"+id+"date_time"+parseInt(m+preCount)).css({"background":"#e7e5e5"});
						$("#"+id+"date_num"+parseInt(m+preCount)).children("p").find("input").attr("checked",false);
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
	// 后一个月剩余展示的天数
	var nextCount = 0;
	nextCount = 42-(monthDays+preCount);
	if(nextCount >= 7){
		$("#"+id+"tr5").hide();
	}else{
		$("#"+id+"tr5").show();
	}
} 

// 获取选中的所有id
function getCheckDateIds(){
	var checkboxs = $("input[name='dateCheck']");
	var dateIds = "";
	for (var i = 0; i < checkboxs.length; i++){
		if (checkboxs[i].type == "checkbox"){
			if (checkboxs[i].checked){
				dateIds = dateIds + checkboxs[i].value + ";";
			}
		}
	}
	if ("" != dateIds){
		dateIds = dateIds.substring(0, dateIds.length - 1);
	}
	return dateIds;
}

// 获取未选中的所有id
function getNotCheckDateIds(){
	var checkboxs = $("input[name='dateCheck']");
	var dateIds = "";
	for (var i = 0; i < checkboxs.length; i++){
		if (checkboxs[i].type == "checkbox"){
			if (!checkboxs[i].checked){
				dateIds = dateIds + checkboxs[i].value + ";";
			}
		}
	}
	if ("" != dateIds){
		dateIds = dateIds.substring(0, dateIds.length - 1);
	}
	return dateIds;
}

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