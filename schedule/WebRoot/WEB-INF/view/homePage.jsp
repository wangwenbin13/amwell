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
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<meta name="Description" content="" />
<title>首页</title>
<jsp:include page="resource.jsp"/>
</head>

<body>
<p class="subNav">当前位置：首页</p>
<div class="mt20 ml30 black1 mr28">
      	<p class="fw f14 f-arial">调度提醒</p>  
      	<div class="mt20 home-title p-r">
      		<span class="display-ib tips-light mr10 ml15 mt20"></span><span class="f14">待调度线路&nbsp;<em class="red4 fw f20">${page.totalCount}</em>&nbsp;条</span>
      		<s:if test="%{page.totalCount != 0}"><div class="p-a home-oprate" id="home-oprate"><span class="fl down-arrow mt5 ml53"></span></div></s:if>
      	</div>
      	<!-- 调度提醒 start -->
      	<div id="remindTable" class="hide">
      		<table class="table1 tableCursor" border="0" width="100%" id="tableLineTop" style="border-top:0 none;">
				<tr class="th">
					<th width="10%">线路类型</th>
					<th width="40%">起点/终点</th>
					<th align="right" class="pr5p">操作</th>
				</tr>
				<s:iterator value="list" var="merchantLine" status="s">
				  <tr align="center" lineBaseId="${merchantLine.lineBaseId}">
						<td><s:if test="%{#merchantLine.lineSubType==0}"><span class="display-ib lineKind lineKind-work">上下班</span></s:if><s:if test="%{#merchantLine.lineSubType==1}"><span class="display-ib lineKind lineKind-free">自由行</span></s:if></td>
						<td>
							<span class="display-ib lineDoit lineDoit-start"><!--起点 --></span>${merchantLine.startname}
							<span class="display-ib lineDoitArrow"></span>
							<span class="display-ib lineDoit lineDoit-end"><!--终点 --></span>${merchantLine.endname}
						</td>
						<td align="right" class="pr5p"><a href="javascript:void(0)" class="yellow3" onclick="schedule('${merchantLine.lineBaseId}')">调度</a></td>
				  </tr>
				</s:iterator>
			</table>
      	</div>
      	<!-- 调度提醒 end -->
      	<p class="mt20 fw f14 f-arial">今日行程安排</p>
      	<div class="mt20 home-title p-r">
      		<span class="r-input fl w176 mr10 ml10 mt13">
      			<input type="text" readonly="readonly" id="txtSelectDate" class="Wdate75 Wdate" onclick="WdatePicker({position:{left:-4,top:4},dateFmt:'yyyy-MM-dd',onpicked:function(dp){dateChange(); }});" value="" />
      		</span>
			<a class="fl red1 ml30 mt2" href="javascript:void(0)" id="getCurrTime">返回今天</a>
      	</div>
      	
      	<!-- 今日行程安排 start -->
      	<div >
      		<table class="table1 tableCursor" border="0" width="100%" id="tableLine" valign="middle" style="border-top:0 none;border-bottom:0 none;">
				<tr class="th">
					<th width="10%">班次时间</th>
					<th width="9%">线路类型</th>
					<th width="10%">线路名称</th>
					<th width="9%">司机</th>
					<th width="9%">品牌车型</th>
					<th width="9%">车牌号码</th>
					<th width="10%">已订购人数</th>
					<th align="right" class="pr5p"></th>
				</tr>
		    </table>
      	</div>
      	<div class="home-border" id="home-table">
      		<!-- 没有数据 start -->	
      		<div class='m10 hide noDateList' id="noDateList">
	      		<table width='100%' class='table2' valign='middle'>
					<tr class="noDateList">
						<td width='9%' class='lefttd' >
							<div widht="50" class="p-r">
								<p class='t-c'><em class='carIcon display-ib'></em><em class="p-a indexArrow"></em></p>
							</div>
						</td>
						<td colspan="7">				
							<div class="t-c mt115 mb180">
								<img src="../images/noDate.png" width="169" height="169" alt="暂无数据 " /><!-- 默认没有行程安排  -->
								<p class="mt15 f18 f-yahei">暂无数据 </p>
							</div>				
						</td>
					</tr>
	      		</table>
      		</div>
      		<!-- 没有数据 end -->
      	</div>
      	<!-- 今日行程安排 end -->
</div>
</body>
</html>
<script type="text/javascript">

var jsonDate = null; 

function schedule(s){
  window.location.href="../line/getLineDetail.action?lineBaseId="+s;
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
	return myDate.getFullYear() + "-" + parseDate(month) + "-" + parseDate(myDate.getDate());
}

$(function(){	
	
	$("#txtSelectDate").val(getCurrTime().split("-")[0]+"-"+getCurrTime().split("-")[1]+"-"+getCurrTime().split("-")[2]);
	
	//返回今天
	$("#getCurrTime").click(function(){
		loadDateData(true);
	});
	
	//默认显示当前系统时间
	loadDateData(true);
	
	//调度提醒显示或者隐藏
	$("#home-oprate").click(function(){
		if($("#remindTable").css("display") == "none")
		{
			$("#remindTable").slideDown("slow");
			$(this).children().removeClass("down-arrow").addClass("up-arrow");
		}
		else
		{
			$("#remindTable").slideUp();
			$(this).children().removeClass("up-arrow").addClass("down-arrow");
		}
	});
});

//日历选择改变
function dateChange()
{
	 loadDateData(false);
}

//加载后台数据显示
function loadDateData(isCurr){
	//isCurr=true默认显示当前系统时间
	if(isCurr)
	{
		$("#txtSelectDate").val(getCurrTime());
		var selectYear = getCurrTime(); //选中的年月
	}
	else
	{
		var selectYear = $("#txtSelectDate").val(); //选中的年月
	}
	loadData(selectYear);
}

function loadData(orderDate){
    var url = "../homePage/getMerchantSchedulingList.action?orderDate="+orderDate;
  	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			//jsonDate = jsonDate;
			jsonDate = data;
			if(jsonDate.length == 0)
			{
			    $("#home-table").children("div:not(.noDateList)").remove(); //删除之前的table
				$("#noDateList").show();
			}
			else
			{
				$("#noDateList").hide();
				$("#home-table").children("div:not(.noDateList)").remove(); //删除之前的table
				showDate(jsonDate,"home-table");
			}
		},
		error:function(){
			$("#home-table").children("div:not(.noDateList)").remove(); //删除之前的table
			$("#noDateList").show();
		}
	});
}

function showDate(jsonDate,id)
{
	for(var i = 0;i < jsonDate.length;i++)
	{
		$("#"+id).append("<div class='m10' id='"+id+"div"+i+"'><table width='100%' valign='middle' class='table2' id='"+id+"table"+i+"'></table></div>");
		for(var j = 0;j < jsonDate[i].merchantOrderList.length;j++)
		{
				var $tr1 = "<tr><td width='7%' rowspan='"+jsonDate[i].merchantOrderList.length+"' class='lefttd'><div class=' p-r'><p class='t-c'><em class='carIcon display-ib'></em></p><p class=' t-c black1 fw f18 mt10'>"+jsonDate[i].orderStartTime+"</p><em class='p-a indexArrow'></em></div></td>";
				var $tr2 = "<td width='13%' align='center'><span class='display-ib lineKind' id='lineSubType"+i+"td"+j+"'></span></td>"+
					"<td width='8%' height='40' align='center'>"+jsonDate[i].merchantOrderList[j].lineName+"</td>"+
					"<td width='11%' height='40' align='center'>"+jsonDate[i].merchantOrderList[j].driverName+"</td>"+
					"<td width='7%' height='40' align='center'>"+jsonDate[i].merchantOrderList[j].vehicleBrand+"</td>"+
					"<td width='11%' height='40' align='center'>"+jsonDate[i].merchantOrderList[j].vehicleNumber+"</td>"+
					"<td width='8%' height='40' align='center'>"+jsonDate[i].merchantOrderList[j].orderCount+"</td>"+
					"<td align='right' height='40' class='pr5p' valign='center'></td>"+
				"</tr>";
				if(j == 0)
				{
					$tr2 = $tr1 + $tr2;
				}
				else
				{
					$tr2 = "<tr>" + $tr2;
				}
				$("#"+id+"table"+i).append($tr2);
				if(jsonDate[i].merchantOrderList[j].lineSubType == "0")
				{
					$("#lineSubType"+i+"td"+j).addClass("lineKind-work").text("上下班");
				}
				else if(jsonDate[i].merchantOrderList[j].lineSubType == "1")
				{
					$("#lineSubType"+i+"td"+j).addClass("lineKind-free").text("自由行");
				}
		}
		
	} 
}

//线路详情
$("#tableLineTop tr td").click(function(){
	//获取选中文字，如果选中文字则不响应点击事件
	//ie8写法
	if(typeof(document.selection) != "undefined")
	{
		var selecter = document.selection.createRange().text;
	}
	else if(typeof(window.getSelection()) != "undefined")
	{
		var selecter = window.getSelection();
	}
	else
	{
		var selecter = "";
	}
	if(selecter != "")
	{
	 	return false;
	}
	else
	{
		var lineBaseId = $(this).parent().attr("lineBaseId");
		//线路详情
		$("#iframe_right", parent.window.document).attr("src","../line/getLineDetail.action?lineBaseId="+lineBaseId);
	}
});
</script>