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
    <title>运营平台－招募详情-报名人数</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <input type="hidden" id="lineBaseId" value="${recruitLineStationVo.lineBaseId}"/>
    <p class="pop-main-tips mt10 gray2">线路信息：${recruitLineStationVo.lineName}<span class="ml20">当前报名人数：<em class="green1 fw mr4">${recruitLineStationVo.applyTotal}</em>人</span><span class="ml20">单次 价格：<em class="red1 fw mr4">${recruitLineStationVo.orderPrice}</em>元</span>
    	
    </p>
    <div class="table-title mt10">            
         <input type="button" class="btn fr mr4 mt4" value="导出" onclick="exportTelephone();">
    </div>
    <div class="table2-text sh-ttext">       
         <table width="100%" border="0" class="table1 gray2 f12" id="tableDiv">
               <tr align="center">
                 <th scope="col" width="5%">序号</th>
                 <th scope="col" width="12%">报名时间</th>
                 <th scope="col" width="7%">用户ID</th>
                 <th scope="col" width="10%">昵称</th>
                 <th scope="col" width="5%">姓别</th>
                 <th scope="col" width="10%">手机号</th>                     
                 <th scope="col" width="10%">上车点</th>                     
                 <th scope="col" width="10%">下车点</th>      
                 <th scope="col" width="7%">乘坐人数</th>                     
                 <th scope="col">备注</th>                    
               </tr>
               <s:iterator value="list" var="p" status="s">
		            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="bg1" passengerId="${p.passengerId}">
					</s:if>
					<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="bg2" passengerId="${p.passengerId}">
					</s:if>
					<td>${s.index+1}</td>
					<!-- 此处为注册时间 -->
			       	<td class="f-arial">${p.presetTime}</td>
	               	<td class="f-arial">${p.displayId}</td>
	               	<td>${p.nickName}</td>
	               	<td><s:if test="%{#p.sex==0}">男</s:if><s:if test="%{#p.sex==1}">女</s:if><s:if test="%{#p.sex=='--'}">--</s:if></td>
	               	<td class="f-arial">${p.telephone}</td>
	               	<td class="f-arial">${p.ustationName}</td>
	               	<td class="f-arial">${p.dstationName}</td>
	               	<td class="f-arial">${p.applyNum}</td>
	               	<td class="f-arial" align="left" ><span style="display:block;width:100%;line-height:20px;height:20px;overflow:hidden;" title="${p.remark}">${p.remark}</span></td>
               	</tr>
			   </s:iterator>
               
             </table>        	
          </div> 
          <!--Start page-->
            <div class="page t-c mt20  fl widthfull" id="pageDiv">
	                   <s:if test="%{currentPageIndex!=0}">
	                  		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
                     		<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
	                   </s:if>
                       <ul id="pageNumDiv">
                       		<s:iterator value="page.pageList">
							<s:if test="field02==1">
								<b><span class="current"><s:property value="field01" />
								</span>
								</b>
							</s:if>
							<s:else>
								<li>
									<a href="javascript:void(0);" onclick="toPage(${field03});"><s:property value="field01" /></a>
								</li>
							</s:else>
						</s:iterator>
                       </ul>
                       <s:if test="%{page.pageCount!=1 && page.pageCount!=0}">
                       		<a href="javascript:void(0);" onclick="toPage(${page.nextIndex});">下一页</a>
                       		<a href="javascript:void(0);" onclick="toPage(${page.lastIndex});">末页</a>
                       		<span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');" onblur="toJudgePage(this.value);"/>页</span>
                       </s:if>
                       <span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页]</span>
            </div>
           <!--End page-->
 </body>
 <form action="line/recruitLinesOrderNum.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="currentPageIndex" id="currentPageIndex"/>
</form> 
</html>
<script type="text/javascript">
function exportTelephone(){
  var lineBaseId = '${recruitLineStationVo.lineBaseId}';
  var url="";
  if(navigator.userAgent.indexOf("MSIE")>0)  
  {   
    //ie8
    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
    {  
      url = "../line/exportTelephone.action?lineBaseId="+lineBaseId;
    }   
    else  
	{
	  url = "../line/exportTelephone.action?lineBaseId="+lineBaseId;
	}
  }
  else  
  {  
      url = "<%=path%>/line/exportTelephone.action?lineBaseId="+lineBaseId;
  } 
  window.location.href=url;
}

//订购人数详情页面
function goOrderNumDetail()
{
	$("#topHide",parent.parent.window.document).show();
    $("#leftHide",parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("line/getOrderNumDetail.action?random="+Math.floor(Math.random()*10000+1)); 
    $("#mainBody",parent.window.document).show();
}

//根据不同的班次展示不同的日历
function getDiffDateInfo(classType,classTime){
	if(classType == "0")  
	{
		$("#arrowIcon").css("left","7px");
		showCurrDate();
	}
	else if(classType == "1")  
	{
		$("#arrowIcon").css("left","110px");
		showCurrDate();
	}
	else if(classType == "2")  
	{
		$("#arrowIcon").css("left","210px");
		showCurrDate();
	}
}

//json数据
var jsonDate = [{
	"lineNum":"45", //人数
	"dateTime":"2014-07-01", //日期
	"id":"001"  //ID
},{
	"lineNum":"45",
	"dateTime":"2014-08-01",
	"id":"001"
},{
	"lineNum":"45",
	"dateTime":"2014-05-02",
	"id":"002"
},{
	"lineNum":"45",
	"dateTime":"2014-08-04",
	"id":"003"
},{
	"lineNum":"45",
	"dateTime":"2014-08-12",
	"id":"004"
},{
	"lineNum":"45",
	"dateTime":"2014-08-18",
	"id":"005"
},{
	"lineNum":"45",
	"dateTime":"2014-08-22",
	"id":"006"
}];

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
		//获取选择的月份是周几
		var weekDay = getWeekDay(dateTime);
		//获取下一个月天数
		var nextMonthDay = getNextMonth(yearIndex+" -"+monthIndex);
		initTable("tableDate",monthDays,weekDay,nextMonthDay,selectYear);
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
	initTable("tableDate",monthDays,weekDay,nextMonthDay,selectYear);
}


//动态创建表格
function initTable(id,monthDays,weekDay,nextMonthDay,selectYear)
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
				$("#date_num"+parseInt(m+j)).html(m+1);
				for(var n = 0; n < jsonDate.length;n++)
				{
					if(jsonDate[n].dateTime == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
					{
						$("#date_num"+parseInt(m+j)).append("<p class='mt7'><a href='javascript:void(0);' class='blue3 cursor' onclick='goOrderNumDetail("+parseInt(m)+")'>"+jsonDate[n].lineNum+"</a></p>");
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
//动态创建下拉框
function initSelectData(id)
{
	$("#"+id).find("option").remove();
	for(var i = 1;i <= 5;i++)
　　{
　　　　$("#"+id).append("<option value='"+i+"'>"+i+"</option>");
　  }
}

//翻页方法
function toPage(value){
	$("#currentPageIndex").val(value);
	$("#turnPage").submit();
}

//判断输入的参数是否符合规定
function toJudgePage(value){
	var totalPage = "${page.pageCount}";
	totalPage = parseInt(totalPage);
	if(""==value){
		return;
	}
	value = parseInt(value);
	if(value<1){
		parent.parent.showCommonTip("请输入大于0的数!");
		return;
	}else if(value>totalPage){
		parent.parent.showCommonTip("超出最大页数!");
		return;
	}
	var pageSize = "${pageSize}";
	toPage((value-1)*pageSize);
}

/*分页enter按键处理函数*/
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		var currPage = $("#goPageNum").val();
        //输入为空不跳转
        if(currPage == "")
    	{
    		return false;
    	}
	    toJudgePage(currPage);
		break;
	default:
		break;
	}
}

var ua = window.navigator.userAgent;
//判断浏览器类型
if (ua.indexOf("MSIE") >= 1 || ua.indexOf("Chrome") >= 1){
	//禁止后退键  作用于IE、Chrome  
	document.onkeydown = pressKey;
}else if(ua.indexOf("Firefox") >= 1){
	//禁止后退键 作用于Firefox、Opera  
	document.onkeypress = pressKey;  
}else{
	document.onkeypress = pressKey;  
}

//检查跳页是否大于总页数
function checkGoPageNum(pageNum)
{
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(parseInt(pageNum) > parseInt(totalPage))
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}
</script>
