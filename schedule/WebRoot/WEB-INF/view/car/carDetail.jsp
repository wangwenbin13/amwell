 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车辆管理-车辆列表-车辆详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<input type="hidden" id="pageSize" />
<input type="hidden" id="currPage" />
<input type="hidden" id="totalCount" />
<input type="hidden" id="beginSelectDate" />
<input type="hidden" id="endSelectDate" />
<input type="hidden" id="tableDatePreClickClass" />
<input type="hidden" id="tableDateNextClickClass" />
<p class="subNav">当前位置：首页&nbsp;>&nbsp;车辆管理&nbsp;>&nbsp;车辆详情<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 black1 mr28">
	<p class="fw f14 f-yahei">车辆详情</p>
	<p class="mt20 libg"><span class="fl ml20 lh28">基本信息</span></p>	
	<input type="hidden" id="vehicleId" value="<s:property value='vehicleInfoEntity.vehicleId'/>"/>
	<ul class="mt20 black4 clearfix">
		<li class="fl mr25 listBorderRight">
		     
		    <div class="carImg p-r">
		    	<s:if test="%{!(null==vehicleInfoEntity.vehicleUrl||''==vehicleInfoEntity.vehicleUrl)}">
		    	    <img src="<s:property value='vehicleInfoEntity.vehicleUrl'/>" width="118" height="70" />
					<div class="carImgBig p-a"><img src="<s:property value='vehicleInfoEntity.vehicleUrl'/>"/></div>
				</s:if>
			</div>
		   
			<p class="mt10 ml8 black1"><em class="fw mr5"><s:property value='vehicleInfoEntity.vehicleBrand'/></em><s:property value='vehicleInfoEntity.vehicleModel'/></p>
			<p class="mt10 ml8 black2"><s:property value='vehicleInfoEntity.vehicleNumber'/></p>
		</li>
		<li class="fl w176 line24 mr25">
			<p><em class="fw"><s:if test="vehicleInfoEntity.vehicleType==0">大巴</s:if><s:if test="vehicleInfoEntity.vehicleType==1">中巴</s:if><s:if test="vehicleInfoEntity.vehicleType==2">小巴</s:if></em><span class="display-ib carNumBg ml10"><em class="fw f14 f-arial"><s:property value='vehicleInfoEntity.vehicleSeats'/></em>座</span></p>
			<p class="mt25">车辆编号：<em  <s:if test="%{null==vehicleInfoEntity.vehicleNo||''==vehicleInfoEntity.vehicleNo}">class="gray3"</s:if>><s:property value='%{(null==vehicleInfoEntity.vehicleNo||""==vehicleInfoEntity.vehicleNo)?"暂无信息":vehicleInfoEntity.vehicleNo}'/></em></p>
			<p>车&nbsp;&nbsp;&nbsp;&nbsp;系：<em  <s:if test="%{null==vehicleInfoEntity.vehicleDepart||''==vehicleInfoEntity.vehicleDepart}">class="gray3"</s:if>><s:property value='%{(null==vehicleInfoEntity.vehicleDepart||""==vehicleInfoEntity.vehicleDepart)?"暂无信息":vehicleInfoEntity.vehicleDepart}'/></em></p>
			<p>车辆颜色：<em  <s:if test="%{null==vehicleInfoEntity.vehicleColor||''==vehicleInfoEntity.vehicleColor}">class="gray3"</s:if>><s:property value='%{(null==vehicleInfoEntity.vehicleColor||""==vehicleInfoEntity.vehicleColor)?"暂无信息":vehicleInfoEntity.vehicleColor}'/></em></p>
		</li>
		<li class="line24 mr25 mt50 display-ib">
<%--		    <p class="mt50">车辆类型：<s:if test="vehicleInfoEntity.vehicleType==0">大巴</s:if><s:if test="vehicleInfoEntity.vehicleType==1">中巴</s:if><s:if test="vehicleInfoEntity.vehicleType==2">小巴</s:if></p>--%>
			<p>购买日期：<em <s:if test="%{null==vehicleInfoEntity.vehicleBuyTime||''==vehicleInfoEntity.vehicleBuyTime}">class="gray3"</s:if>><s:property value='%{(null==vehicleInfoEntity.vehicleBuyTime||""==vehicleInfoEntity.vehicleBuyTime)?"暂无信息":vehicleInfoEntity.vehicleBuyTime}'/></em></p>
			<p>备注信息：<em  <s:if test="%{null==vehicleInfoEntity.remark||''==vehicleInfoEntity.remark}">class="gray3"</s:if>><s:property value='%{(null==vehicleInfoEntity.remark||""==vehicleInfoEntity.remark)?"暂无信息":vehicleInfoEntity.remark}'/></em></p>
		</li>
	</ul>
	<p class="mt20 libg"><span class="fl ml20 lh28">排班情况</span></p>	
	<div class="mt10 clearfix t-c line35 p-r">
		<span class="fr export-bg lh32 fw mr60" onclick="doExport()"><em class="export-icon fl mt2 ml4"></em>导出</span>
		<em class="f-arial mr4 fw" id="beginSelectDate_str">2014-08-01</em>至<em class="f-arial ml4 mr4 fw" id="endSelectDate_str">2014-08-01</em>排班情况
		<div class="fl p-r driverWorkDateHover"><span class="fl driverWorkDate">选择时段</span>		
			<div class="driverWorkDate-dateList p-a">
				<span class="driverWorkDate-dateListArrow"></span>
				<div class="fl driverWorkDate-dateListLeft">
					<p class="fw f13 t-c">开始时间</p>
					<div class="driverWorkDate-dateListTxt clearfix f-arial">
						<div class="clearfix">
							<span class="fl dateArrowY dateArrowY-left cursor" id="delYearPre" onclick="addOrDelYear('del','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre')"></span>
							<span class="fl dateArrowM dateArrowM-left ml8 mr10 cursor" id="delMonthPre" onclick="addOrDelMonth('del','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre')"></span>
							<div class="p-r fl">
								<input class="driverWorkDate-ym w40 t-c f13 fw" id="monthSelectPre" maxlength="2" onclick="showUlLiSelect('monthUlPre','yearUlPre');" onchange="selectOneYear(this.value,'monthUlPre')"  onkeyup="this.value=this.value.replace(/\D/g,'');checkInputNum(this.value,'monthSelectPre');" onafterpaste="this.value=this.value.replace(/\D/g,''checkInputNum(this.value,'monthSelectPre');"/>
								<ul class="p-a driverWorkDate-selTime hide" id="monthUlPre">
									<li class="fl" onclick="selectOneYear('1','monthUlPre')">1</li>
									<li class="fl" onclick="selectOneYear('2','monthUlPre')">2</li>
									<li class="fl" onclick="selectOneYear('3','monthUlPre')">3</li>
									<li class="fl" onclick="selectOneYear('4','monthUlPre')">4</li>
									<li class="fl" onclick="selectOneYear('5','monthUlPre')">5</li>
									<li class="fl" onclick="selectOneYear('6','monthUlPre')">6</li>
									<li class="fl" onclick="selectOneYear('7','monthUlPre')">7</li>
									<li class="fl" onclick="selectOneYear('8','monthUlPre')">8</li>
									<li class="fl" onclick="selectOneYear('9','monthUlPre')">9</li>
									<li class="fl" onclick="selectOneYear('10','monthUlPre')">10</li>
									<li class="fl" onclick="selectOneYear('11','monthUlPre')">11</li>
									<li class="fl" onclick="selectOneYear('12','monthUlPre')">12</li>
								</ul>
							</div>
							<div class="p-r fl ml10">
								<input class="driverWorkDate-ym w40 t-c f13 fw" value="" id="yearSelectPre" maxlength="4" onclick="showUlLiSelect('yearUlPre','monthUlPre')" onchange="selectOneYear(this.value,'yearUlPre')"  onkeyup="this.value=this.value.replace(/\D/g,'');checkInputNum(this.value,'yearSelectPre');" onafterpaste="this.value=this.value.replace(/\D/g,''checkInputNum(this.value,'yearSelectPre');"/>
								<ul class="p-a driverWorkDate-selTime hide" id="yearUlPre"></ul>
							</div>
							<span class="fl dateArrowM dateArrowM-right ml10 cursor" id="addMonthPre" onclick="addOrDelMonth('add','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre')"></span>
							<span class="fl dateArrowY dateArrowY-right ml8 cursor" id="addYearPre" onclick="addOrDelYear('add','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre')"></span>
						</div>
						<table width="100%" class="f-arial driverWorkDate-table mt15" id="tableDatePre">
							<tr>
								<th>日</th>
								<th>一</th>
								<th>二</th>
								<th>三</th>
								<th>四</th>
								<th>五</th>
								<th>六</th>
							</tr>
						</table>
					</div>
				</div>
				<span class="fl driverWorkDate-dateListMLine"></span>
				<div class="fl driverWorkDate-dateListLeft">
					<p class="fw f13 t-c">结束时间</p>
					<div class="driverWorkDate-dateListTxt clearfix f-arial">
						<div class="clearfix">
							<span class="fl dateArrowY dateArrowY-left cursor" id="delYearNext" onclick="addOrDelYear('del','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDateNext')"></span>
							<span class="fl dateArrowM dateArrowM-left ml8 mr10 cursor" id="delMonthNext" onclick="addOrDelMonth('del','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDateNext')"></span>
							<div class="p-r fl">
								<input class="driverWorkDate-ym w40 t-c f13 fw" id="monthSelectNext" maxlength="2" onclick="showUlLiSelect('monthUlNext','yearUlNext')" onchange="selectOneYear(this.value,'monthUlNext')"  onkeyup="this.value=this.value.replace(/\D/g,'');checkInputNum(this.value,'monthSelectNext');" onafterpaste="this.value=this.value.replace(/\D/g,''checkInputNum(this.value,'monthSelectNext');"/>
								<ul class="p-a driverWorkDate-selTime hide" id="monthUlNext">
									<li class="fl" onclick="selectOneYear('1','monthUlNext')">1</li>
									<li class="fl" onclick="selectOneYear('2','monthUlNext')">2</li>
									<li class="fl" onclick="selectOneYear('3','monthUlNext')">3</li>
									<li class="fl" onclick="selectOneYear('4','monthUlNext')">4</li>
									<li class="fl" onclick="selectOneYear('5','monthUlNext')">5</li>
									<li class="fl" onclick="selectOneYear('6','monthUlNext')">6</li>
									<li class="fl" onclick="selectOneYear('7','monthUlNext')">7</li>
									<li class="fl" onclick="selectOneYear('8','monthUlNext')">8</li>
									<li class="fl" onclick="selectOneYear('9','monthUlNext')">9</li>
									<li class="fl" onclick="selectOneYear('10','monthUlNext')">10</li>
									<li class="fl" onclick="selectOneYear('11','monthUlNext')">11</li>
									<li class="fl" onclick="selectOneYear('12','monthUlNext')">12</li>
								</ul>
							</div>
							<div class="p-r fl ml10">
								<input class="driverWorkDate-ym w40 t-c f13 fw" value="" id="yearSelectNext" maxlength="4" onclick="showUlLiSelect('yearUlNext','monthUlNext')" onchange="selectOneYear(this.value,'yearUlNext')"  onkeyup="this.value=this.value.replace(/\D/g,'');checkInputNum(this.value,'yearSelectNext');" onafterpaste="this.value=this.value.replace(/\D/g,''checkInputNum(this.value,'yearSelectNext');"/>
								<ul class="p-a driverWorkDate-selTime hide" id="yearUlNext"></ul>
							</div>
							<span class="fl dateArrowM dateArrowM-right ml10 cursor" id="addMonthNext" onclick="addOrDelMonth('add','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDateNext')"></span>
							<span class="fl dateArrowY dateArrowY-right ml8 cursor" id="addYearNext" onclick="addOrDelYear('add','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDateNext')"></span>
						</div>
						<table width="100%" class="f-arial driverWorkDate-table mt15" id="tableDateNext">
							<tr>
								<th>日</th>
								<th>一</th>
								<th>二</th>
								<th>三</th>
								<th>四</th>
								<th>五</th>
								<th>六</th>
							</tr>
						</table>
					</div>
				</div>
				<p class="clearfix"></p>
				<p class="fl mt10">
					<a class="fl btn1 white1 fw mr25" href="javascript:void(0);" onclick="getSelectDate()">确&nbsp;定</a>
					<span id="errorTipSpan" class="fl hide ml10"><em class="error mt5 ml4"></em><span class="red2" id="errorTipText"></span></span>
				</p>
			</div>
		</div>
	</div>
	
	<!-- 暂无数据 start -->
	<div id="noDateDiv" class="t-c hide mt20">
		<img src="../images/noDate.png" width="169" height="169" alt="暂无数据" /><!-- 默认没有线路 -->
		<p class="mt15 f18 f-yahei">暂无数据</p>
	</div>
	<!-- 暂无数据 end -->
	
	<!-- 列表start -->
	<div class="driverWorkListOut mt20">
		<span class="scrollOut-click fl" style="margin-top:-13px" href="javascript:void(0)"><span class="scrollOut-arrow scrollOut-arrowUp"></span></span>
		<ul class="driverWorkList clearfix" id="driverWorkList"></ul>
		<span class="scrollOut-click fl" href="javascript:void(0)"><span class="scrollOut-arrow scrollOut-arrowDown"></span></span>
	</div>
	<!-- 列表end -->
	
	<div class="page mt20" id="pageDiv">
       <a href="javascript:void(0);" id="homePage" onclick="goToPage('homePage');">首页</a>
       <a href="javascript:void(0);" id="prePage" onclick="goToPage('prePage');">上一页</a>
       <ul id="pageNumDiv"></ul>
       <a href="javascript:void(0);" id="nextPage" onclick="goToPage('nextPage');">下一页</a>
       <a href="javascript:void(0);" id="endPage" onclick="goToPage('endPage');">末页</a>
       <span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onblur="goToPage('goPageNum');"  onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);"/>页</span>
       <span class="ml25">[共<span id="totalPageNum" class="fw"></span>页]</span>
   </div>
   
</div>
</body>
</html>
<script type="text/javascript" src="../js/common/page.js" type="text/javascript"></script><!-- 分页js -->
<script type="text/javascript">
//初始化日历
$(function(){	
	$(".driverWorkDateHover").click(function(e){
		$(".driverWorkDate-dateList").show();
		e = e||event; stopFunc(e);
		showCurrDate(false);
	})
	
	$(".driverWorkDate-dateList").click(function(e){
		e = e||event; stopFunc(e);
	})
	$(document).click(function(e){		
		$(".driverWorkDate-dateList").hide();
		$("#errorTipSpan").hide();
		$("#errorTipText").text("");
		$("#monthUlPre,#monthUlNext").hide();
		$("#yearUlPre,#yearUlNext").find("li").remove();
		$("#yearUlPre,#yearUlNext").hide();
		initLiSelectData("yearUlPre","add");
		initLiSelectData("yearUlNext","add");
    })
    function stopFunc(e){   
	    e.stopPropagation?e.stopPropagation():e.cancelBubble = true;       
	}
	
	/*$(".driverWorkDateHover").hover(function(){
		$(".driverWorkDate-dateList").show();
		//默认显示当前系统时间
		showCurrDate(false);
	},function(){
		$(".driverWorkDate-dateList").hide();
		//$("#beginSelectDate,#endSelectDate,#tableDatePreClickClass,#tableDateNextClickClass").val("");
		$("#errorTipSpan").hide();
		$("#errorTipText").text("");
		$("#monthUlPre,#monthUlNext").hide();
		$("#yearUlPre,#yearUlNext").find("li").remove();
		initLiSelectData("yearUlPre","add");
		initLiSelectData("yearUlNext","add");
	});
	*/
	//默认显示当前系统时间
	showCurrDate(true);
	//初始化加载详情数据
    loadDetailDate(currPage);
});

var jsonDate;

//调用后台   加载详情数据
function loadDetailDate(currPage)
{
	console.log("loadDetailDate");
	try{
	$.ajax({
		url : "../dispatchVehicle/getCarScheduleList.action",
		type : "post",
		dataType : "json",
		data : {
		vehicleId : $("#vehicleId").val(),
		beginOrderDate : $("#beginSelectDate").val(),
		endOrderDate : $("#endSelectDate").val(),
		pageSize : $("#pageSize").val(),
		currPage : $("#currPage").val(),
		totalCount : $("#totalCount").val()
		},
		cache: false,//不从浏览器缓存中取
		async: false, //async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(textStatus);
			console.log(errorThrown);
			$("#noDateDiv").show();
			$(".driverWorkListOut,.page,.export-bg").hide();
		},
		success : function(data) {
			jsonDate = data;
			console.log(jsonDate);
			//无数据时提示暂无数据
			if(jsonDate.resultDate.length == 0)
			{
				$("#noDateDiv").show();
				$(".driverWorkListOut,.page,.export-bg").hide();
			}
			else
			{
				$("#noDateDiv").hide();
				$(".driverWorkListOut,.page,.export-bg").show();
				pageSize = jsonDate.pageSize;//每页显示的条数
				totalCount = jsonDate.totalCount;//总条数
				currPage = jsonDate.currPage;//当前页码

				$("#pageSize").val(pageSize);
				$("#currPage").val(currPage);
				$("#totalCount").val(totalCount);
				
				getDetailDate(jsonDate,"driverWorkList");
				checkShowPage(currPage);
			}
		}
	});
	}catch(e){Console.log(e);}
}

//点击导出按钮
function doExport(){
	var vehicleId = $("#vehicleId").val();
	var beginOrderDate = $("#beginSelectDate").val();
	var endOrderDate = $("#endSelectDate").val();
	var pageSize = $("#pageSize").val();
	var currPage = $("#currPage").val();
	var totalCount = $("#totalCount").val();

	window.location.href="../dispatchVehicle/exportCarScheduleList.action?vehicleId="+vehicleId+"&beginOrderDate="+beginOrderDate+"&endOrderDate="+endOrderDate+"&pageSize="+pageSize+"&currPage="+currPage+"&totalCount="+totalCount;
}

function goToPage(id)
{ 
    if(id == "homePage")
    {
        currPage = 1;
    }
    else if(id == "endPage")
    {
        currPage = totalPage;
    }
    else if(id == "prePage")
    {
        currPage = parseInt(currPage) - 1;
    }
    else if(id == "nextPage")
    {
        currPage = parseInt(currPage) + 1;
    }
    else if(id == "goPageNum")
    {
        currPage = $("#"+id).val();
        //输入为空不跳转
        if(currPage == "")
    	{
    		return false;
    	}
    }
    else if(id == "goPage"+id.substring(6))
    {
        currPage = parseInt(id.substring(6));
    }
    currPage = parseInt(currPage);
    $("#currPage").val(currPage);
    loadDetailDate(currPage);
}

//展示详情排班数据
function getDetailDate(jsonDate,id)
{
	$("#"+id).children("li").remove(); //删除所有的li
	for(var i = 0;i < jsonDate.resultDate.length;i++){
		var $li = "<li class='fl p-r' id='"+id+"li"+i+"'>" +
			"<div class='p-a driverWorkList-dateYm f-arial fw'>"+jsonDate.resultDate[i].orderDate.split("-")[0]+"-"+jsonDate.resultDate[i].orderDate.split("-")[1]+"<em class='driverWorkList-dateYmLine ml5'></em></div>"+
			"<div class='fl driverWorkList-date'><p class='f21 white1 f-arial'>"+jsonDate.resultDate[i].orderDate.split("-")[2]+"</p></div>"+
		"</li>"; 
		$("#"+id).append($li);
		//<!-- 当天过期的班次的样式driverWorkList-dateTimeGone，上午班次driverWorkList-dateTimeAm，下午班次driverWorkList-dateTimePm-->
		for(var j = 0;j < jsonDate.resultDate[i].scheduleList.length;j++){
			var $div = "<div class='fl driverWorkList-text' id='line"+i+"div"+j+"'>"+
				"<p class='driverWorkList-dateTime'><span></span><em class='f-arial ml8 fw'>"+jsonDate.resultDate[i].scheduleList[j].orderStartTime+"</em></p>"+
				"<p class='ml25 mt15'>从<em class='red1 ml5 mr5'>"+jsonDate.resultDate[i].scheduleList[j].startname+"</em>开往<em class='red1 ml5 mr5'>"+jsonDate.resultDate[i].scheduleList[j].endname+"</em></p>"+
				"<p class='ml25 mt10'>预计行驶<em class='red1 ml5 mr5 f-arial'>"+jsonDate.resultDate[i].scheduleList[j].lineTime+"</em></p>"+
				"<p class='ml25 mt15 black2'></p>"+
			"</div>";
			$("#"+id+"li"+i).append($div);
			if(isAMorPM(jsonDate.resultDate[i].scheduleList[j].orderStartTime.split(":")[0]))
			{
				$("#line"+i+"div"+j).find("p").eq(0).children("span").text("上午");
				$("#line"+i+"div"+j).find("p").eq(0).addClass("driverWorkList-dateTimeAm");
			}
			else
			{
				$("#line"+i+"div"+j).find("p").eq(0).children("span").text("下午");
				//一期只用同一个图标
				//$("#line"+i+"div"+j).find("p").eq(0).addClass("driverWorkList-dateTimePm");
				$("#line"+i+"div"+j).find("p").eq(0).addClass("driverWorkList-dateTimeAm");
			}
			//排班时间小于系统当前时间  置灰
			if(isExtendCurrDate(jsonDate.resultDate[i].orderDate,jsonDate.resultDate[i].scheduleList[j].orderStartTime.split(":")[0],jsonDate.resultDate[i].scheduleList[j].orderStartTime.split(":")[1]))
			{
				if($("#line"+i+"div"+j).find("p").eq(0).hasClass("driverWorkList-dateTimeAm"))
				{
					$("#line"+i+"div"+j).find("p").eq(0).removeClass("driverWorkList-dateTimeAm").addClass("driverWorkList-dateTimeGone");
				}
				else if($("#line"+i+"div"+j).find("p").eq(0).hasClass("driverWorkList-dateTimePm"))
				{
					$("#line"+i+"div"+j).find("p").eq(0).removeClass("driverWorkList-dateTimePm").addClass("driverWorkList-dateTimePmGone");
				}
			}
			if(jsonDate.resultDate[i].scheduleList[j].lineSubType == 0) //0=上下班  1=自由行
			{
				$("#line"+i+"div"+j).find("p").eq(3).text("上下班线路");
			}
			else if(jsonDate.resultDate[i].scheduleList[j].lineSubType == 1)
			{
				$("#line"+i+"div"+j).find("p").eq(3).text("自由行线路");
			}
		}
	}
}

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

//判断上午还是下午及当前时间
function isAMorPM(hours)
{
	if(hours >=0 && hours <= 12)
	{
		return true; //"上午"
	}
	else if(hours > 12 && hours <= 24)
	{
		return false; //"下午"
	}
}

//判断是否超过当前时分
function isExtendCurrDate(date,hours,minutes)
{
	//系统当前的时分
	var myDate = new Date();
	var currMinutes = myDate.getMinutes();
	var currHours = myDate.getHours();
	var currYearDate = myDate.getFullYear() + "-" + parseDate(myDate.getMonth()+1) + "-" + parseDate(myDate.getDate());
	if(parseInt(currMinutes) < 10){
		currMinutes = "0" + currMinutes;
	}
	if(parseInt(currHours) < 10){
		currHours = "0" + currHours;
	}
	//排班时间小于系统当前时间
	if(date == currYearDate)
	{
		if(hours < currHours)
		{
			return true;
		}
		else if(hours == currHours && minutes <= currMinutes)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	else if(date < currYearDate)
	{
		return true;
	}
	else
	{
		return false;
	}
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
	$("#"+yearSelect).val(yearIndex);
	$("#"+monthSelect).val(monthIndex);
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
	$("#"+yearSelect).val(yearIndex);
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
		var yearIndexPre = $("#"+yearPre).val();
		var monthIndexPre = $("#"+monthPre).val();
		addOrdDelOneMonth(statu,yearIndexPre,monthIndexPre,yearPre,monthPre);
		showChangeDate(statu,yearPre,monthPre,"tableDatePre");
	}
	//右侧的日历
	else if(tableFlag == "tableDateNext")
	{
		//后一个月赋新值
		var yearIndexNext = $("#"+yearNext).val();
		var monthIndexNext = $("#"+monthNext).val();
		addOrdDelOneMonth(statu,yearIndexNext,monthIndexNext,yearNext,monthNext);
		showChangeDate(statu,yearNext,monthNext,"tableDateNext");
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
		var yearIndexPre = $("#"+yearPre).val();
		addOrdDelOneYear(statu,yearIndexPre,yearPre);
		showChangeDate(statu,yearPre,monthPre,"tableDatePre");
	}
	//右侧的日历
	else if(tableFlag == "tableDateNext")
	{
		//后一年赋新值
		var yearIndexNext = $("#"+yearNext).val();
		addOrdDelOneYear(statu,yearIndexNext,yearNext);
		showChangeDate(statu,yearNext,monthNext,"tableDateNext");
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

//改变年月展示日历
/*
参数说明
year:   所选年份下拉框id,
month:  所选月份下拉框id,
tableFlag: tableDatePre左侧日历table的id;tableDateNext右侧日历table的id,
*/
function showChangeDate(statu,year,month,tableFlag)
{
	var yearIndex = $("#"+year).val();
	var monthIndex = $("#"+month).val();
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
	tableTdClick();
}

//显示当前时间的日历
function showCurrDate(isShowSelect)
{
	var yearIndex = getCurrTime().split("-")[0];
	var monthIndex = getCurrTime().split("-")[1];
	
	//左侧日历显示
	if(isShowSelect)
	{
		$("#yearSelectPre").val(yearIndex);
		$("#monthSelectPre").val(parseInt(monthIndex,10));
		initLiSelectData("yearUlPre","add");
		$("#beginSelectDate").val(getCurrTime());
		$("#beginSelectDate_str").html(getCurrTime());
	}
	else
	{
		$("#yearSelectPre").val($("#beginSelectDate").val().split("-")[0]);
		$("#monthSelectPre").val(parseInt($("#beginSelectDate").val().split("-")[1],10));
	}
	addOrDelMonth('default','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDatePre');
	//显示系统当前时间的后7天
	var nextDate = getCurrDayPreOrNextDay("next",7);
	//右侧日历显示
	if(isShowSelect)
	{
		$("#yearSelectNext").val(nextDate.split("-")[0]);
		$("#monthSelectNext").val(nextDate.split("-")[1]);
		initLiSelectData("yearUlNext","add");
		$("#endSelectDate").val(nextDate);
		$("#endSelectDate_str").html(nextDate);
	}
	else
	{
		$("#yearSelectNext").val($("#endSelectDate").val().split("-")[0]);
		$("#monthSelectNext").val(parseInt($("#endSelectDate").val().split("-")[1],10));
	}
	addOrDelMonth('default','yearSelectPre','monthSelectPre','yearSelectNext','monthSelectNext','tableDateNext');
}

//table  td点击事件
function tableTdClick()
{
	
	//左侧日历选择
	$("#tableDatePre tr td:not([date='0'])").click(function(){
		$("#errorTipSpan").hide();
		$("#errorTipText").text("");
		$("#"+$("#tableDatePreClickClass").val().split(",")[1]).removeClass("tdYellowBg1").addClass($("#tableDatePreClickClass").val().split(",")[0]);
		var selectPreDate = $(this).attr("date");
		if(typeof(selectPreDate) != "undefined")
		{
			$("#beginSelectDate").val(selectPreDate);
			$("#tableDatePreClickClass").val($(this).attr("class")+","+$(this).attr("id"));
			for(var i = 0; i < $("#tableDatePre tr").length;i++)
			{
				$("#tableDatePre tr").eq(i).find("td").removeClass("tdYellowBg1");
			}
			$(this).removeClass("noweek_end noscheduling week_end scheduling").addClass("tdYellowBg1");
		}
		else
		{
			$("#beginSelectDate").val("");
		}
		
	});
	
	//右侧日历选择
	$("#tableDateNext tr td:not([date='0'])").click(function(){
		$("#errorTipSpan").hide();
		$("#errorTipText").text("");
		$("#"+$("#tableDateNextClickClass").val().split(",")[1]).removeClass("tdYellowBg1").addClass($("#tableDateNextClickClass").val().split(",")[0]);
		var selectNextDate = $(this).attr("date");
		if(typeof(selectNextDate) != "undefined")
		{
			$("#endSelectDate").val(selectNextDate);
			$("#tableDateNextClickClass").val($(this).attr("class")+","+$(this).attr("id"));
			for(var i = 0; i < $("#tableDateNext tr").length;i++)
			{
				$("#tableDateNext tr").eq(i).find("td").removeClass("tdYellowBg1");
			}
			$(this).removeClass("noweek_end noscheduling week_end scheduling").addClass("tdYellowBg1");
		}
		else
		{
			$("#endSelectDate").val("");
		}
	});
}

//动态创建表格
function initTable(statu,id,monthDays,weekDay,nextMonthDay,selectYear)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	var thLength = $("#"+id+" tr th").length;
	var trTmp = "";
	for(var i = 0;i < 6;i++)
	{
		var $table = $("#"+id);
		var $tr = $("<tr id='"+id+"tr"+i+"'>" +
		"<td class='noweek_end tdGrayBg red4' id='"+id+"date_time"+parseInt(thLength*i+1)+"' week='星期天' date='0'><p id='"+id+"date_num"+parseInt(thLength*i+1)+"'></p></td>"+ 
		"<td class='noscheduling tdGrayBg' id='"+id+"date_time"+parseInt(thLength*i+2)+"' week='星期一' date='0'><p id='"+id+"date_num"+parseInt(thLength*i+2)+"'></p></td>"+   
		"<td class='noscheduling tdGrayBg' id='"+id+"date_time"+parseInt(thLength*i+3)+"' week='星期二' date='0'><p id='"+id+"date_num"+parseInt(thLength*i+3)+"'></p></td>"+ 
		"<td class='noscheduling tdGrayBg' id='"+id+"date_time"+parseInt(thLength*i+4)+"' week='星期三' date='0'><p id='"+id+"date_num"+parseInt(thLength*i+4)+"'></p></td>"+
		"<td class='noscheduling tdGrayBg' id='"+id+"date_time"+parseInt(thLength*i+5)+"' week='星期四' date='0'><p id='"+id+"date_num"+parseInt(thLength*i+5)+"'></p></td>"+ 
		"<td class='noscheduling tdGrayBg' id='"+id+"date_time"+parseInt(thLength*i+6)+"' week='星期五' date='0'><p id='"+id+"date_num"+parseInt(thLength*i+6)+"'></p></td>"+ 
		"<td class='noweek_end tdGrayBg red4' id='"+id+"date_time"+parseInt(thLength*i+7)+"' week='星期六' date='0'><p id='"+id+"date_num"+parseInt(thLength*i+7)+"'></p></td>"+ 
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
	
	//显示系统当前时间的后7天
	var nextDate = getCurrDayPreOrNextDay("next",7);
	//选择的开始时间
	var beginSelectDate = $("#beginSelectDate").val();
	//选择的结束时间
	var endSelectDate = $("#endSelectDate").val();
	
	for(var j = 1;j<8;j++){
		//所选月份展示天数
		if(weekDay == $("#"+id+"date_time"+j).attr("week"))
		{
			//前一个月份剩余展示天数
			preCount = j - 1;
			for(var m = 0;m < monthDays;m++)
			{
				$("#"+id+"date_num"+parseInt(m+j)).html(m+1);
				$("#"+id+"date_time"+parseInt(m+j)).attr("date",selectYear+"-"+parseDate((m+1))).addClass("cursor");

				/*for(var n = 0; n < jsonDate.length;n++)
				{
					if(jsonDate[n].dateTime == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
					{
						if($("#"+id+"date_time"+parseInt(m+preCount+1)).hasClass("noweek_end"))
						{
							$("#"+id+"date_time"+parseInt(m+preCount+1)).removeClass("noweek_end").addClass("week_end");
						}
						if($("#"+id+"date_time"+parseInt(m+preCount+1)).hasClass("noscheduling"))
						{
							$("#"+id+"date_time"+parseInt(m+preCount+1)).removeClass("noscheduling").addClass("scheduling");
						}
					}
				}*/
				//系统当前日期 需要选中样式
				if(id == "tableDatePre" && beginSelectDate == $("#"+id+"date_time"+parseInt(m+j)).attr("date"))
				{
					$("#"+id+"ClickClass").val($("#"+id+"date_time"+parseInt(m+j)).attr("class")+","+$("#"+id+"date_time"+parseInt(m+preCount)).attr("id"));
					$("#"+id+"date_time"+parseInt(m+j)).removeClass("noweek_end noscheduling week_end scheduling").addClass("tdYellowBg1");
				} 
				if(id == "tableDateNext" && endSelectDate == $("#"+id+"date_time"+parseInt(m+j)).attr("date"))
				{
					$("#"+id+"ClickClass").val($("#"+id+"date_time"+parseInt(m+j)).attr("class")+","+$("#"+id+"date_time"+parseInt(m+preCount)).attr("id"));
					$("#"+id+"date_time"+parseInt(m+j)).removeClass("noweek_end noscheduling week_end scheduling").addClass("tdYellowBg1");
				}
			}
		}
	}
	
	//前一个月份剩余展示天数
	for(var n = 1;n <= preCount;n++)
	{
		//$("#"+id+"date_time"+n).html((preMonthDay-preCount)+n);
		$("#"+id+"date_time"+n).removeClass("tdYellowBg1 tdGrayBg red4");
	}
	
	//后一个月剩余展示的天数
	nextCount = 42 - (monthDays+preCount);
	if(nextCount >= 7)
	{
		$("#"+id+"tr5").hide();
	}
	else
	{
		$("#"+id+"tr5").show();
	}
	for(var l = 1;l <= nextCount;l++)
	{
		//$("#"+id+"date_time"+(monthDays+preCount+l)).html(l);
		$("#"+id+"date_time"+(monthDays+preCount+l)).removeClass("tdYellowBg1 tdGrayBg red4");
	}
	
} 

//获取左右2个日历，选择的日历值
function getSelectDate()
{
	//开始时间
	var beginSelectDate = $("#beginSelectDate").val();
	//结束时间
	var endSelectDate = $("#endSelectDate").val();
	var sdate = new Date(beginSelectDate.replace(/-/g,"/"));  //把所有的“-”转成“/”  
	var edate = new Date(endSelectDate.replace(/-/g,"/"));  //把所有的“-”转成“/”  

	if(beginSelectDate == "" && endSelectDate == "")
	{
		$("#errorTipSpan").show();
		$("#errorTipText").text("开始日期和结束日期不可都为空");
		return;
	}
	//日期选择时间校验
	if(Date.parse(edate) < Date.parse(sdate))
	{
		$("#errorTipSpan").show();
		$("#errorTipText").text("开始日期不可以大于结束日期，请重新选择");
	}
	else
	{	
		//重新选择条件后，将当前页赋值为1
		currPage = 1;
		$("#currPage").val(1);
		//alert("beginSelectDate==="+beginSelectDate);
		//alert("endSelectDate==="+endSelectDate);
		$("#beginSelectDate_str").html(beginSelectDate);
		$("#endSelectDate_str").html(endSelectDate);
		//alert(beginSelectDate+"***"+endSelectDate);

		//根据条件加载数据
		loadDetailDate(1);
		$(".driverWorkDate-dateList").hide();
		/*alert(beginSelectDate+"***"+endSelectDate);
		$(".driverWorkDate-dateList").hide();
		$("#beginSelectDate,#endSelectDate,#tableDatePreClickClass,#tableDateNextClickClass").val("");
		$("#errorTipSpan").hide();
		$("#errorTipText").text("");*/
	}	
}

//动态创建li下拉框
function initLiSelectData(id,flag)
{
	var liFristValue = $("#"+id+" li:first").attr("value");
	var liLastValue = $("#"+id+" li:eq(11)").attr("value");
	//从2014年开始
	if(liFristValue == 2014 && flag == "del")
	{
		return;
	}
	//从2019年结束
	if(liLastValue == 2109 && flag == "add")
	{
		return;
	}
	$("#"+id).find("li").remove();
	//增加年份
	if(flag == "add")
	{
		if(typeof(liLastValue) == "undefined")
		{
			liLastValue = 2013;
		}
		for(var i = (parseInt(liLastValue)+1);i <= (parseInt(liLastValue)+12);i++)
	　　{
	　　　　$("#"+id).append("<li class='fl' value='"+i+"' onclick='selectOneYear(\""+i+"\",\""+id+"\")'>"+i+"</li>");
	　  	}
	}
	//减少年份
	else if(flag == "del")
	{
		for(var i = (parseInt(liFristValue)-12);i < parseInt(liFristValue);i++)
	　　{
	　　　　$("#"+id).append("<li class='fl' value='"+i+"' onclick='selectOneYear(\""+i+"\",\""+id+"\")'>"+i+"</li>");
	　  	}
	}
	
	$("#"+id).append("<li class='fl driverWorkDate-selTimeLiNoBg' style='width:33%' onclick='initLiSelectData(\""+id+"\",\"del\")'><span class=\'fl dateArrowPrevNext dateArrowPrevNext-prev\'></span></li><li class='fl driverWorkDate-selTimeLiNoBg' style='width:33%' onclick='closeUlLiSelect(\""+id+"\")'><span class=\'fl closeIco\'></span></li><li class='fl driverWorkDate-selTimeLiNoBg' style='width:33%' onclick='initLiSelectData(\""+id+"\",\"add\")'><span class=\'fl dateArrowPrevNext dateArrowPrevNext-next\'></span></li>");
}

//显示下拉框
function showUlLiSelect(id1,id2)
{
	$("#"+id1).show();
	closeUlLiSelect(id2)
}
//关闭下拉框
function closeUlLiSelect(id)
{
	$("#"+id).hide();
}
//选中下拉中的某个日期
function selectOneYear(date,id)
{
	$("#"+id).prev("input").val(date);
	closeUlLiSelect(id);
	if(id == "yearUlPre" || id == "monthUlPre")
	{
		showChangeDate("not","yearSelectPre","monthSelectPre","tableDatePre");
	}
	else if(id == "yearUlNext" || id == "monthUlNext")
	{
		showChangeDate("not","yearSelectNext","monthSelectNext","tableDateNext");
	}
}

//检查输入的日期是否大于最大数
function checkInputNum(date,id)
{
	//验证输入首字段不能是0
	$("#"+id).attr("value",date.substring(0,1) == '0' ? date = '' : date = date);
	if(id == "yearSelectPre" || id == "yearSelectNext")  //年份判断
	{
		if(date > 2109)
		{
		    $("#"+id).attr("value",2109);
		}
		else if(parseInt(date.substring(3,4)) < 4 && parseInt(date.substring(2,3)) <= 1 || parseInt(date.substring(2,3)) == 0)
		{
		    $("#"+id).attr("value",2014);
		}
		
	}
	else if(id == "monthSelectPre" || id == "monthSelectNext") //月份判断
	{
		if(date > 12)
		{
		    $("#"+id).attr("value",12);
		}
		else if(date < 0)
		{
		    $("#"+id).attr("value",1);
		}
	}
	
}

</script>
