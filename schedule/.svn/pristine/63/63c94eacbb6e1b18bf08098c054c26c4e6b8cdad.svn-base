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
<link href="../css/scroll.css" rel="stylesheet" type="text/css" />
<title>线路管理-线路列表</title>
<jsp:include page="../resource.jsp"/>
<script type="text/javascript" src="../js/scroll/mousewheel.js"></script>
<script type="text/javascript" src="../js/scroll/easyscroll.js"></script>
</head>

<body>
    <input id="lineBaseId" type="hidden" value="${lineDetailVo.lineBaseId}" />
	<p class="subNav">当前位置：首页&nbsp;&gt;&nbsp;线路管理&nbsp;&gt;&nbsp;线路详情<a class="red1 ml30" href="javascript:void(0)" onclick="javascript:history.go(-1);">返回</a></p>
	<div class="mt20 ml30 black1 mr28">
		<p class="fw f14 f-yahei">线路详情</p>
		<p class="mt20 libg"><span class="fl ml20 lh28">基本信息</span></p>	
		<div class="fl lineDetail-left mt20 ml15 mr30">
			<p>
				 <s:if test="%{lineDetailVo.lineSubType==0}"><span class="display-ib lineKind lineKind-work">上下班 </span></s:if>
			     <s:if test="%{lineDetailVo.lineSubType==1}"><span class="display-ib lineKind lineKind-free">自由行</span></s:if>
			     
				 <span class="fw ml10">${lineDetailVo.lineName}</span><span class="gray3">(约${lineDetailVo.lineTime})</span>
				 
			     <s:if test="%{lineDetailVo.lineStatus==1}"><span class="red4 ml80">待调度</span></s:if>
              	 <s:if test="%{lineDetailVo.lineStatus==2}"><span class="yellow4 ml80">待发布</span></s:if>
              	 <s:if test="%{lineDetailVo.lineStatus==3}"><span class="green1 ml80">已发布</span></s:if>
              	 <s:if test="%{lineDetailVo.lineStatus==4}"><span class="black2 ml80">已下线</span></s:if>
			</p>
			<p class="mt20">单次价：<em class="f16 fw yellow4 mr4 ml15">${lineDetailVo.orderPrice}</em>元/次</p>
			<s:if test="%{lineDetailVo.lineSubType==0}">
				<div class="mt5">
					包月价：<a href="javascript:void(0)" class="ml15 red1"  onclick="goMonthPricePage();">查看包月价格</a>
				</div>
			</s:if>
			<!--  <p class="mt5">备&nbsp;&nbsp;注：<em class="gray1 ml15">${lineDetailVo.remark}</em></p>-->
		</div>
		<div class="mt20 clearfix">
			<p class="green1"><span class="lineDetail-ico lineDetail-ico-start mr10"></span><span class="cursor" onclick="viewSitePice('${lineDetailVo.stationList[0]}');">${lineDetailVo.stationList[0]}</span></p>
			<div><span class="lineDetail-ico-go mr10"></span><!--<span class="cursor">${lineDetailVo.stationList[1]}</span>-->${lineDetailVo.stationList[1]}</div>
			<p class="red4"><span class="lineDetail-ico lineDetail-ico-end mr10"></span><span class="cursor" onclick="viewSitePice('${lineDetailVo.stationList[2]}');">${lineDetailVo.stationList[2]}</span></p>
		</div>
		<p class="mt20 libg"><span class="fl ml20 lh28">调度和订购情况</span></p>	
		<div class="mt30 ml15 clearfix" id="currClass">
		    <!-- 当前班次 -->
			<s:iterator value="lineDetailVo.lineClassList" var="l" status="s">
			   <s:if test="#s.index==0">
			     <input id="firstOrderStartTime" type="hidden" value="${l.orderStartTime}"/>
			     <a herf="javascript:void(0)" onclick="setOrderStartTime('${l.orderStartTime}',this)" class="fl lineTime lineTime-current white1"><em class="f14 f-arial fw mr15">${l.orderStartTime}</em><em class="f-arial">${l.orderSeats}</em>座</a>
			   </s:if>
			   <s:else>
			     <a herf="javascript:void(0)" onclick="setOrderStartTime('${l.orderStartTime}',this)" class="fl lineTime lineTime-next white1"><em class="f14 f-arial fw mr15">${l.orderStartTime}</em><em class="f-arial">${l.orderSeats}</em>座</a>
			   </s:else>
			</s:iterator>
		</div>
		<!-- 右侧整个 Big Div start -->
		<div class="fr lineSelectMore mt10 hide" id="rightBigDiv">
			<!-- 选择司机 图标 -->
			<p class="lineSelectMore-title fw"><span class="display-ib lineSelectMore-ico lineSelectMore-ico-up mr10" id="selectDriverIcon"><!--lineSelectMore-ico-down--></span>选择司机</p>
			
			<!-- 选择司机 Big Div start -->
			<div class="lineSelectMoreOut" id="selectDriverBigDiv">
				<!--查看司机列表 Big Div start -->
				<div class="lineSelectMore-text ml30 mr20" id="selectDriverListDiv">
					<!-- 没有数据 start -->	
					<div class="t-c hide" id="noDriverListDate">
						<img src="../images/noDate.png" class="mt35" width="169" height="169" alt="暂无司机" />
						<p class="mt15 f18 f-yahei">暂无司机</p>
						<P class="gray3 mt15">您还没有添加司机，添加司机后才可完成调度噢,快去添加吧！</P>
						<p class="mt40"><a href="javascript:void(0)" onclick="parent.openLeftMenu('../dispatchDriver/toDriverEditPage.action');" class="yellow-btn"><em class="fw f22 va3 mr4">+</em>添加司机</a></p>
					</div>
					<!-- 没有数据 end -->
					<!-- 没有搜索数据 start -->	
					<div class="t-c hide" id="noSearchDriverListDate">
						<img src="../images/noSearchDate.png" class="mt35" width="169" height="169" alt="暂无司机" />
						<p class="mt15"><span class="f18 f-yahei">暂无司机</span><a href="javascript:void(0)" class="red1 ml15 mt10" onclick="$('#noSearchDriverListDate').hide();$('#noSearchDriverListDate').nextAll('div').show();">返回</a></p>
					</div>
					<!-- 没有搜索数据 end -->
					<div class="mt13"><input type="text" id="driverName" class="fl lineSelectMore-selInput gray3 mr15" value="请输入司机姓名" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('black1');}" onblur="if(!value){value=defaultValue;$(this).removeClass('black1').addClass('gray3');}"/><input type="submit" class="search-btn vf10" value="" onclick="searchDriver();"/></div>				
					<div class="mt15 clearfix">
						<!--滚动条部分 start-->
						<div class="fr scrollOut hide" id="scrollDriverListBarBigDiv">
							<a href="javascript:void(0)" class="scrollOut-click" onclick="moveDiv('up','scrollDriverListBarBigDiv','scrollDriverListBarDiv')"><span class="scrollOut-arrow scrollOut-arrowUp"></span></a>
							<div class="scrollOut-m p-r">
								<span class="scrollOut-m-scroll p-a" id="scrollDriverListBarDiv"></span>
							</div>
							<a href="javascript:void(0)" class="scrollOut-click" onclick="moveDiv('down','scrollDriverListBarBigDiv','scrollDriverListBarDiv')"><span class="scrollOut-arrow scrollOut-arrowDown"></span></a>
						</div>
						<!--滚动条部分 end-->
						
						<!--司机列表 start-->
						<div class="moveBigDiv">
							<div class="p-a widthfull div_scroll" id="moveDriverListDiv">
								<ul class="lineSelectMore-list" id="driverListUL"></ul>
							</div>
						</div>
						<!--司机列表 end-->
					</div>				
				</div>		
				<!--查看司机列表 Big Div end-->
						
				<!--查看司机排班情况   Big Div start-->
				<div class="lineSelectMore-text ml10 hide" id="selectDriverClassListDiv">
					<p class="t-c lineSelectMore-listTitle mt10"><a href="javascript:void(0)" class="fr red1" id="selectThisDriver" onclick="selectThisDriver()">选此司机</a><a href="javascript:void(0)" class="fl red1" onclick="goBackDriverList();">返回</a>司机<span id="driverNameSelect"></span>的排班情况</p>					
					<div class="mt17 clearfix">
						<!--滚动条部分 start-->
						<div class="fr scrollOut hide" id="scrollDriverClassListBarBigDiv">
							<a href="javascript:void(0)" class="scrollOut-click" onclick="moveDiv('up','scrollDriverClassListBarBigDiv','scrollDriverClassListBarDiv')"><span class="scrollOut-arrow scrollOut-arrowUp"></span></a>
							<div class="scrollOut-m p-r">
								<span class="scrollOut-m-scroll p-a" id="scrollDriverClassListBarDiv"></span>
							</div>
							<a href="javascript:void(0)" class="scrollOut-click" onclick="moveDiv('down','scrollDriverClassListBarBigDiv','scrollDriverClassListBarDiv')"><span class="scrollOut-arrow scrollOut-arrowDown"></span></a>
						</div>
						<!--滚动条部分 end-->
						
						<!--查看司机排班情况列表 start-->
						<div class="moveBigDiv">
							<div class="p-a widthfull div_scroll" id="moveDriverClassListDiv">
								<ul class="line24" id="driverClassListUL">
									<li>
							            <span class="fl r-input w15p"><input type="text" name="" value="" class="Wdate75 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
							            <span class="fl ml4 mr4 mt4">至</span>
							            <span class="fl r-input w15p"><input type="text" name="" value="" class="Wdate75 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
						       			<input type="submit" class="search-btn ml15" value="" onclick="searchDriverClass();"/>
						       		</li>
								</ul>
							</div>
							<!-- 没有数据 start -->	
							<div class="t-c hide" id="noDriverClassListDate">
								<img src="../images/noDate.png" class="mt35" width="169" height="169" alt="暂无排班" />
								<p class="mt15 f18 f-yahei">暂无排班</p>
							</div>
							<!-- 没有数据 end -->
						</div>
						<!--查看司机排班情况列表 end-->
					</div>
				</div>
				<!--查看司机排班情况  Big Div end-->
				
			</div>
			<!-- 选择司机 Big Div end -->
			
			<!-- 选择车辆 图标 -->
			<p class="lineSelectMore-title fw mt10"><span class="display-ib lineSelectMore-ico lineSelectMore-ico-down mr10" id="selectCarIcon"><!--lineSelectMore-ico-down--></span>选择车辆</p>
			
			<!-- 选择车辆 Big Div start -->
			<div class="lineSelectMoreOut hide" id="selectCarBigDiv">
				<!--查看车辆列表   Big Div start-->
				<div class="lineSelectMore-text ml30 mr20" id="selectCarListDiv">
					<!-- 没有数据 start -->	
					<div class="t-c hide" id="noCarListDate">
						<img src="../images/noDate.png" class="mt35" width="169" height="169" alt="暂无车辆" />
						<p class="mt15 f18 f-yahei">暂无车辆</p>
						<P class="gray3 mt15">您还没有添加车辆，添加车辆后才可完成调度噢,快去添加吧！</P>
						<p class="mt40"><a href="javascript:void(0)" onclick="parent.openLeftMenu('../dispatchVehicle/toVehicleEditPage.action');" class="yellow-btn"><em class="fw f22 va3 mr4">+</em>添加车辆</a></p>
					</div>
					<!-- 没有数据 end -->
					<!-- 没有搜索数据 start -->	
					<div class="t-c hide" id="noSearchCarListDate">
						<img src="../images/noSearchDate.png" class="mt35" width="169" height="169" alt="暂无车辆" />
						<p class="mt15"><span class="f18 f-yahei">暂无车辆</span><a href="javascript:void(0)" class="red1 ml15 mt10" onclick="$('#noSearchCarListDate').hide();$('#noSearchCarListDate').nextAll('div').show();">返回</a></p>
					</div>
					<!-- 没有搜索数据 end -->
					<div class="mt13"><input type="text" id="carInfo" class="fl lineSelectMore-selInput gray3 mr15" value="请输入车牌号" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('black1');}" onblur="if(!value){value=defaultValue;$(this).removeClass('black1').addClass('gray3');}"/><input type="submit" class="search-btn vf10" value="" onclick="searchCar();"/></div>				
					<div class="mt15 clearfix">
						<!--滚动条部分 start-->
						<div class="fr scrollOut hide" id="scrollCarListBarBigDiv">
							<a href="javascript:void(0)" class="scrollOut-click" onclick="moveDiv('up','scrollCarListBarBigDiv','scrollCarListBarDiv')"><span class="scrollOut-arrow scrollOut-arrowUp"></span></a>
							<div class="scrollOut-m p-r">
								<span class="scrollOut-m-scroll p-a" id="scrollCarListBarDiv"></span>
							</div>
							<a href="javascript:void(0)" class="scrollOut-click" onclick="moveDiv('down','scrollCarListBarBigDiv','scrollCarListBarDiv')"><span class="scrollOut-arrow scrollOut-arrowDown"></span></a>
						</div>
						<!--滚动条部分 end-->
						
						<!--车辆列表 start-->
						<div class="moveBigDiv">
							<div class="p-a widthfull div_scroll" id="moveCarListDiv">
								<ul class="lineSelectMore-list" id="carListUL"></ul>
							</div>			
						</div>			
						<!--车辆列表 end-->
					</div>				
				</div>
				<!--查看车辆列表   Big Div end-->
				
				<!--查看车辆排班情况   Big Div start-->
				<div class="lineSelectMore-text ml10 hide" id="selectCarClassListDiv">
					<p class="t-c lineSelectMore-listTitle mt10"><a href="javascript:void(0)" class="fr red1" id="selectThisCar" onclick="selectThisCar()">选此车辆</a><a href="javascript:void(0)" class="fl red1" onclick="goBackCarList();">返回</a>车辆<span id="vehicleNumber"></span>的排班情况</p>					
					<div class="mt17 clearfix">
						<!--滚动条部分 start-->
						<div class="fr scrollOut hide" id="scrollCarClassListBarBigDiv">
							<a href="javascript:void(0)" class="scrollOut-click" onclick="moveDiv('up','scrollCarClassListBarBigDiv','scrollCarClassListBarDiv')"><span class="scrollOut-arrow scrollOut-arrowUp"></span></a>
							<div class="scrollOut-m p-r">
								<span class="scrollOut-m-scroll p-a" id="scrollCarClassListBarDiv"></span>
							</div>
							<a href="javascript:void(0)" class="scrollOut-click" onclick="moveDiv('down','scrollCarClassListBarBigDiv','scrollCarClassListBarDiv')"><span class="scrollOut-arrow scrollOut-arrowDown"></span></a>
						</div>
						<!--滚动条部分 end-->
						
						<!--查看车辆排班情况列表 start-->
						<div class="moveBigDiv">
							<div class="p-a widthfull div_scroll" id="moveCarClassListDiv">
								<ul class="line24" id="carClassListUL">
									<li>
							            <span class="fl r-input w15p"><input type="text" name="" value="" class="Wdate75 Wdate" readonly="readonly" id="txtStartDate2" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate2\');}'})"/></span>
							            <span class="fl ml4 mr4 mt4">至</span>
							            <span class="fl r-input w15p"><input type="text" name="" value="" class="Wdate75 Wdate" readonly="readonly" id="txtEndDate2" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate2\');}'})"/></span>
						       			<input type="submit" class="search-btn ml15" value="" onclick="searchCarClass();"/>
						       		</li>
								</ul>
							</div>
							<!-- 没有数据 start -->	
							<div class="t-c hide" id="noCarClassListDate">
								<img src="../images/noDate.png" class="mt35" width="169" height="169" alt="暂无排班" />
								<p class="mt15 f18 f-yahei">暂无排班</p>
							</div>
							<!-- 没有数据 end -->
						</div>
						<!--查看车辆排班情况列表 end-->
					</div>
				</div>
				<!--查看车辆排班情况   Big Div end-->
				
			</div>
			<!-- 选择车辆 Big Div end -->
			
		</div>
		<!-- 右侧整个 Big Div end -->
		
		
		<!-- 司机排班日历start -->		
		<div class="fl lineTime-date ml15 mt10">
			<p class="tips gray4" id="tips"><a herf="javascript:void(0)" class="fr ml15" onclick="closeTip();"><em class="display-ib fw gray2 f-arial mr4 vf1">X</em>关闭</a><span class="display-ib tips-light mr10"></span>点“调度”按钮可调度近两个月的主要司机和车辆，调度完后如有特殊换班，可鼠标移至对应日期方格，点编辑图标重新设置。</p>
			<div class="mt10 lineTime-dateTitle line24 clearfix">
				<div class="fr"><!-- 司机负责人：<em class="gray2 mr10">暂未调度</em>所属车辆：<em class="gray2">暂未调度</em> --><a href="javascript:void(0)" id="editDriverOrCarBtn" class="display-ib btn1 white1 ml20" onclick='editDriverOrCar()'>调度</a><!--调度完成后的样式<a class="red1 ml20" href="javascript:void(0)">修改</a>  --></div>
				<span class="r-input fl w24p mr10 ml10"><input type="text" readonly="readonly" id="txtSelectDate" class="Wdate75 Wdate" onclick="WdatePicker({position:{left:-4,top:4},dateFmt:'yyyy-MM',minDate:'%y-{%M-1}',maxDate:'%y-{%M+2}',onpicking:function(dp){dateChange(dp.cal.getDateStr(),dp.cal.getNewDateStr());},onpicked:function(){setNewSelectDate()}});" value="" /></span>
				<a class="fl red1 ml15" href="javascript:void(0)" id="getCurrTime">返回今天</a>
			</div>
			<div class="lineTableDate">
			 	<table width="100%" cellspacing="7" id="tableDate">
			 		<tr>
			 			<th class="ml10">日</th>
			 			<th>一</th>
			 			<th>二</th>
			 			<th>三</th>
			 			<th>四</th>
			 			<th>五</th>
			 			<th class="mr10">六</th>
			 		</tr>
			 	</table>
			 	<p class="mt10 clearfix line30">
			 		<!--  <a href="javascript:void(0);" class="fr btn1-no mr50" id="resetDriverAndCarBtn" onclick="resetDriverAndCar()">取&nbsp;消</a> -->
			 		<a href="javascript:void(0);" class="fr btn1 white1 fw mr25" id="saveDriverAndCarBtn" onclick="saveDriverAndCar()">保&nbsp;&nbsp;存</a>
	         	</p>
		 	</div>		 	
		</div>
		<!-- 司机排班日历end -->	
		
	</div>
</body>
</html>
<script type="text/javascript">
var lineBaseId = $("#lineBaseId").val();
var orderStartTime = $("input[name='className']:checked").val();
//单个司机ID
var tempDriverId = null;
//单个车辆ID
var tempVehicleId = null;
//查询司机
function searchDriver(){
   var driverName = $("#driverName").val();
   if(driverName.length==0||driverName=='请输入司机姓名'){
     driverName="";
   }
   loadDriverList(selectDate,driverName);
}
//查询车辆
function searchCar(){
   var carInfo = $("#carInfo").val();
   if(carInfo.length==0||carInfo=='请输入车牌号'){
     carInfo="";
   }
   loadCarList(selectDate,carInfo);
}
//查询司机排班
function searchDriverClass(){
   var beginOrderDate = $("#txtStartDate").val();
   var endOrderDate = $("#txtEndDate").val();
   loadDriverClass(tempDriverId,beginOrderDate,endOrderDate);
}
//查询车辆排班
function searchCarClass(){
   var beginOrderDate = $("#txtStartDate2").val();
   var endOrderDate = $("#txtEndDate2").val();
   loadCarClass(tempVehicleId,beginOrderDate,endOrderDate);
}

/******************** 
* 取窗口滚动条高度  
******************/  
function getScrollTop()  
{  
    var scrollTop=0;  
    if(document.documentElement&&document.documentElement.scrollTop)  
    {  
        scrollTop=document.documentElement.scrollTop;  
    }  
    else if(document.body)  
    {  
        scrollTop=document.body.scrollTop;  
    }  
    return scrollTop;  
}  
  
  
/******************** 
* 取窗口可视范围的高度  
*******************/  
function getClientHeight()  
{  
    var clientHeight=0;  
    if(document.body.clientHeight&&document.documentElement.clientHeight)  
    {  
        var clientHeight = (document.body.clientHeight<document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;          
    }  
    else  
    {  
        var clientHeight = (document.body.clientHeight>document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;      
    }  
    return clientHeight;  
} 

/******************** 
* 取文档内容实际高度  
*******************/  
function getScrollHeight()  
{  
    return Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);  
}

$(function(){
	//判断分辨率
	if($(window.parent).width()>1280){
		$(".lineTime-date").css("width","49%");
		$(".lineSelectMore").css("width",$(window.parent).width()-$(".lineTime-date").width()-310+"px");
	}else{
		$(".lineTime-date").css("width",544+"px");
		$(".lineSelectMore").css("width",1280-$(".lineTime-date").width()-303+"px");
	};
	$(window).resize(function(){
		if($(window.parent).width()>1280){
			$(".lineTime-date").css("width","49%");
			$(".lineSelectMore").css("width",$(window.parent).width()-$(".lineTime-date").width()-310+"px");
		}else{
			$(".lineTime-date").css("width",544+"px");
			$(".lineSelectMore").css("width",1280-$(".lineTime-date").width()-303+"px");
			
		};
	});
});
			
/** -----------------------------------------------------------------------------------日历相关js start --------------------------------------------------------------------------------- **/
//json数据
var jsonDate = [{
	"driverName": "张司机",
	"orderDate":"2014-09-01",//orderDate
	"vehicleNumber":"粤B45897",//vehicleNumber
	"vehicleSeats":"40",//vehicleSeats
	"lineClassId":"001"//lineClassId
}];

//司机列表的json
var driverListJson = [{
	"driverName": "张司机",
	"telephone":"13245698745",//电话
	"scheduleCount":"0",//排班状态：0：未排班，1：已排班
	"sex":"0",  //性别 0:男 1:女
	"driverId":"001"
}];

//车辆列表的json
var carListJson = [{
	"vehicleBrand":"金龙客车",//品牌
	"vehicleNumber":"粤A123456",  //车牌号
	"vehicleSeats":"25",  //座位数
	"scheduleCount":"0",//排班状态：0：未排班，1：已排班
	"vehicleId":"001"  //车辆ID
}];

//司机排班的json
var  driverClassListJson = [{
	"orderDate":"2014-08-13 07:30",//时间
	"lineTime":"（约1小时）",  //大约几小时
	"lineSubType":"0",  //lineSubType : 0: 上下班 1:自由行
	"startname":"龙华汽车站",//起点
	"endname":"宝运达物流信息大厦",  //终点
	"lineClassId":"001"  //排班ID
}];

//车辆排班的json
var  carClassListJson = [{
	"orderDate":"2014-08-25 07:30",//时间
	"lineTime":"（约2小时）",  //大约几小时
	"lineSubType":"1",  //lineSubType : 0: 上下班 1:自由行
	"startname":"流塘站",//起点
	"endname":"宝安体育中心站",  //终点
	"lineClassId":"001"  //排班ID
}];

function closeTip()
{
	$("#tips").hide();
}

function showTip()
{
	$("#tips").show();
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
	return myDate.getFullYear() + "-" + parseDate(month) + "-" + parseDate(myDate.getDate());
}

$(function(){	
	//默认显示当前系统时间
	$("#txtSelectDate").val(getCurrTime().split("-")[0]+ "-" +getCurrTime().split("-")[1]);

	//返回今天
	$("#getCurrTime").click(function(){
		//进行其他操作时，检查是否保存了之前修改过的数据
		var checkIfSaveFlag = checkIfSaveData($("#txtSelectDate").val());
		if(checkIfSaveFlag)
		{
			loadDateData(lineBaseId,orderStartTime,true);
		}
	});
	
	//默认显示当前系统时间
	loadDateData(lineBaseId,orderStartTime,true);
});

//进行其他操作时，检查是否保存了之前修改过的数据
function checkIfSaveData(selectDate)
{
	//所有json数据
	var allJsonSetDriverAndCar = getAllJsonSetDriverAndCar("tableDate",selectDate);
	var checkIfSaveFlag = true;
	for(var i = 0;i < allJsonSetDriverAndCar.length;i++)
	{
		if(jsonDate[i].driverId != allJsonSetDriverAndCar[i].driverId || jsonDate[i].vehicleId != allJsonSetDriverAndCar[i].vehicleId)
		{
			parent.showPopCommonPage2("请先保存编辑的数据","true","remind");
			checkIfSaveFlag = false;
		}
	}
	return checkIfSaveFlag;
}

//班次切换
function setOrderStartTime(s,obj){
    //进行其他操作时，检查是否保存了之前修改过的数据
	var checkIfSaveFlag = checkIfSaveData($("#txtSelectDate").val());
	if(checkIfSaveFlag)
	{
	   orderStartTime = s;
	   $(obj).addClass("lineTime-current").removeClass("lineTime-next").siblings().removeClass("lineTime-current").addClass("lineTime-next");
		//刷新左侧日历
	   loadDateData(lineBaseId,orderStartTime,false);
	}
}

//日历新设置的时间
var newSelectDate = "";
//日历选择改变
function dateChange(preDate,newDate)
{
	//进行其他操作时，检查是否保存了之前修改过的数据
	$("#txtSelectDate").val(preDate);
	var checkIfSaveFlag = checkIfSaveData($("#txtSelectDate").val());
	if(checkIfSaveFlag)
	{
		$("#txtSelectDate").val(newDate);
		newSelectDate = newDate;
		loadDateData(lineBaseId,orderStartTime,false);
	}
	else
	{
		$("#txtSelectDate").val(preDate);
		newSelectDate = preDate;
	}
}

//设置日历新的时间
function setNewSelectDate()
{
	$("#txtSelectDate").val(newSelectDate);
}

//加载日历数据显示
function loadDateData(lineBaseId,orderStartTime,isCurr){
    //isCurr=true默认显示当前系统时间
	if(isCurr)
	{
		var yearIndex = getCurrTime().split("-")[0];
		var monthIndex = getCurrTime().split("-")[1];
		//显示当前系统时间
		$("#txtSelectDate").val(getCurrTime().split("-")[0]+ "-" +getCurrTime().split("-")[1]);
	}
	else
	{
		var yearIndex = $("#txtSelectDate").val().split("-")[0];
		var monthIndex =  $("#txtSelectDate").val().split("-")[1];
	}
	//选中的年月
	var selectYear = yearIndex + "-" + monthIndex;
	var dateTime = yearIndex + "-" + monthIndex + "-1";
	//获取月的天数
	var monthDays = getMonthDays(yearIndex,monthIndex);
	//获取选择的月份是周几
	var weekDay = getWeekDay(dateTime);
	//获取下一个月天数
	var nextMonthDay = getNextMonth(yearIndex+" -"+monthIndex);
	if(typeof(orderStartTime) == "undefined"){
	    orderStartTime = $("#firstOrderStartTime").val();             
	}	

    var url = "../line/getClassCarDriver.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime+"&orderDate="+selectYear;
  	
  	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			jsonDate = data;
			//alert("jsonDate  length==="+jsonDate.length);
			initTable("tableDate",monthDays,weekDay,nextMonthDay,selectYear,jsonDate);
		}
	});
    //如果车辆列表页面是展开的
	if($("#selectCarListDiv").css("display") == "block" && $("#selectDriverIcon").hasClass("lineSelectMore-ico-down"))
	{
		 //调用后台车辆列表
		loadCarList(selectDate)
	}
	 //如果车辆排班列表页面是展开的
	else if($("#selectCarClassListDiv").css("display") == "block")
	{
		$("#selectCarListDiv").show();
		$("#selectCarClassListDiv").hide();
		//调用后台车辆列表
		loadCarList(selectDate)
	}
	 //如果司机列表页面是展开的
	else if($("#selectDriverListDiv").css("display") == "block" && $("#selectCarIcon").hasClass("lineSelectMore-ico-down"))
	{
		//加载司机列表的数据
		loadDriverList(selectDate);
	}
	 //如果司机排班列表页面是展开的
	else if($("#selectDriverClassListDiv").css("display") == "block")
	{
		$("#selectDriverListDiv").show();
		$("#selectDriverClassListDiv").hide();
		//加载司机列表的数据
		loadDriverList(selectDate);
	}
}


//动态创建表格
function initTable(id,monthDays,weekDay,nextMonthDay,selectYear,jsonDate)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	var thLength = $("#"+id+" tr th").length;
	var trTmp = "";
	for(var i = 0;i < 6;i++)
	{
		/*选中日期样式  select_date = on*/
		/*周末排班日期样式   week_end = tdYellowBg2*/
		/*周末未排班日期样式   noweek_end = tdGrayBg*/
		/*非周末排班日期样式    scheduling = tdYellowBg2*/
		/*非周末未排班日期样式   noscheduling = tdGrayBg*/
		/*当前系统日期样式  tdYellowBg1*/
		var $table = $("#"+id);
		var $tr = $("<tr id='"+id+"tr"+i+"'>" +
		"<td class='noweek_end tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+1)+"' week='星期天' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw red4'><span class='fr lineTableNo' id='"+id+"date_no"+parseInt(thLength*i+1)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+1)+"'></span></p><p class='gray3 mt10 line24' id='"+id+"noweek"+parseInt(thLength*i+1)+"'>非工作日</p><p class='black4 h15'></p><p class='black4 h15'></p></div></td>"+ 
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+2)+"' week='星期一' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+2)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+2)+"'></span></p><p class='gray3 mt10 line24 hide'>未调度</p><p class='black4 h15'></p><p class='black4 h15'></p></div></td>"+   
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+3)+"' week='星期二' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+3)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+3)+"'></span></p><p class='gray3 mt10 line24 hide'>未调度</p><p class='black4 h15'></p><p class='black4 h15'></p></div></td>"+ 
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+4)+"' week='星期三' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+4)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+4)+"'></span></p><p class='gray3 mt10 line24 hide'>未调度</p><p class='black4 h15'></p><p class='black4 h15'></p></div></td>"+ 
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+5)+"' week='星期四' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+5)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+5)+"'></span></p><p class='gray3 mt10 line24 hide'>未调度</p><p class='black4 h15'></p><p class='black4 h15'></p></div></td>"+ 
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+6)+"' week='星期五' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+6)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+6)+"'></span></p><p class='gray3 mt10 line24 hide'>未调度</p><p class='black4 h15'></p><p class='black4 h15'></p></div></td>"+ 
		"<td class='noweek_end tdGrayBg' valign='top' id='"+id+"date_time"+parseInt(thLength*i+7)+"' week='星期六' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw red4'><span class='fr lineTableNo' id='"+id+"date_no"+parseInt(thLength*i+7)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+7)+"'></span></p><p class='gray3 mt10 line24' id='"+id+"noweek"+parseInt(thLength*i+7)+"'>非工作日</p><p class='black4 h15'></p><p class='black4 h15'></p></div></td>"+ 
		"</tr>");  
		$table.append($tr);
	} 
	//系统显示的当前年月
	var yearIndex = getCurrTime().split("-")[0];
	var monthIndex = getCurrTime().split("-")[1];
	var dayIndex = getCurrTime().split("-")[2];
	var currYear = yearIndex + "-" + monthIndex;
	
	//下拉框所选的年月
	var showYear = selectYear.split("-")[0];
	var showMonth = selectYear.split("-")[1];
	
	//前一个月份剩余展示天数
	var preCount = 0;
	//后一个月剩余展示的天数
	var nextCount = 0;

	for(var j = 1;j<8;j++){
		//所选月份展示天数
		if(weekDay == $("#"+id+"date_time"+j).attr("week"))
		{
			//前一个月份剩余展示天数
			preCount = j - 1;
			for(var m = 0;m < monthDays;m++)
			{
				
				$("#"+id+"date_num"+parseInt(m+j)).html(m+1);
				$("#"+id+"date_time"+parseInt(m+j)).attr("date",selectYear+"-"+parseDate((m+1)));
				//系统当前日期 需要选中样式
				if(selectYear == currYear && dayIndex == m)
				{
					$("#"+id+"date_time"+parseInt(m+preCount)).addClass("tdYellowBg1");
				}
				
				//未调度时，无数据展示“未调度”
				if(jsonDate.length == 0)
				{
					if(!($("#"+id+"date_time"+parseInt(m+j)).hasClass("noweek_end")))
					{
						$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).show();
					}
					$("#editDriverOrCarBtn,#saveDriverAndCarBtn").hide();
					closeTip();
				}
				else
				{
					//小于当前的年月，不展示“调度”和“保存”以及“编辑”按钮
					if((showYear+"-"+showMonth) < currYear)
					{
						$("#editDriverOrCarBtn,#saveDriverAndCarBtn").hide();
						closeTip();
					}
					else if((showYear+"-"+showMonth) == currYear && parseDate((m+1)) < dayIndex)
					{
						$("#editDriverOrCarBtn,#saveDriverAndCarBtn").hide();
						closeTip();
					}
					else
					{
						$("#editDriverOrCarBtn,#saveDriverAndCarBtn").show();
						showTip();
					}
					$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).text("非工作日").show();
					$("#"+id+"date_no"+parseInt(m+j)).removeClass("lineTableNum gray1 f13").addClass("lineTableNo");
					for(var n = 0; n < jsonDate.length;n++)
					{
						if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
						{
							//周末未调班的情况
							if($("#"+id+"date_time"+parseInt(m+j)).hasClass("noweek_end"))
							{
								$("#"+id+"noweek"+parseInt(m+j)).hide();
								//一期先屏蔽
								//$("#"+id+"date_time"+parseInt(m+j)).removeClass("noweek_end").addClass("tdYellowBg2 week_end");
								$("#"+id+"date_no"+parseInt(m+j)).removeClass("lineTableNo").addClass("lineTableNum gray1 f13");
							}
							//小于当前的年月，不展示“调度”和“保存”以及“编辑”按钮
							if((showYear+"-"+showMonth) == currYear)
							{
								if(parseDate((m+1)) >= dayIndex)
								{
									//hover事件
									$("#"+id+"date_time"+parseInt(m+j)).hover(function(){
										$(this).children("div").append("<span class='lineTableDate-textEdit' onclick='editDriverOrCar(\""+$(this).attr('date')+"\",\""+this.id+"\")'></span>").addClass("on");
									},function(){
									    $(this).children("div").find(".lineTableDate-textEdit").remove();
									    $(this).children("div").removeClass("on");
									});
								}
							}
							else if((showYear+"-"+showMonth) > currYear)
							{
								//hover事件
								$("#"+id+"date_time"+parseInt(m+j)).hover(function(){
									$(this).children("div").append("<span class='lineTableDate-textEdit' onclick='editDriverOrCar(\""+$(this).attr('date')+"\",\""+this.id+"\")'></span>").addClass("on");
								},function(){
								    $(this).children("div").find(".lineTableDate-textEdit").remove();
								    $(this).children("div").removeClass("on");
								});
							}
							
							//非周末未排版情况
							if($("#"+id+"date_time"+parseInt(m+j)).hasClass("noscheduling"))
							{
								//一期先屏蔽
								//$("#"+id+"date_time"+parseInt(m+j)).removeClass("noscheduling").addClass("tdYellowBg2 scheduling");
							}
							//小于当前的年月，不展示“调度”和“保存”以及“编辑”按钮
							if((showYear+"-"+showMonth) == currYear)
							{
								if(parseDate((m+1)) >= dayIndex)
								{
									$("#"+id+"date_time"+parseInt(m+j)).addClass("cursor");
								}
							}
							else if((showYear+"-"+showMonth) > currYear)
							{
								$("#"+id+"date_time"+parseInt(m+j)).addClass("cursor");
							}
							
							$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).hide();
							$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).text(jsonDate[n].driverName);
							$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).text(jsonDate[n].vehicleNumber);
							$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).attr("driverId",jsonDate[n].driverId);
							$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).attr("vehicleId",jsonDate[n].vehicleId);
							$("#"+id+"date_no"+parseInt(m+j)).removeClass("lineTableNo").addClass("lineTableNum gray1 f13");
							$("#"+id+"date_no"+parseInt(m+j)).text(jsonDate[n].orderCount);
						}
					}
				}
			}
		}
	}
	
	//前一个月份剩余展示天数
	for(var n = 1;n <= preCount;n++)
	{
		//$("#"+id+"date_time"+n).html((preMonthDay-preCount)+n);
		$("#"+id+"date_time"+n).removeClass("tdGrayBg tdYellowBg1 tdYellowBg2");
		$("#"+id+"noweek"+n).html('');
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
		$("#"+id+"date_time"+(monthDays+preCount+l)).removeClass("tdGrayBg tdYellowBg1 tdYellowBg2");
		$("#"+id+"noweek"+(monthDays+preCount+l)).html('');
	}
	
} 

//日历上选择的日期
var selectDate = undefined;
//日历上选择的ID
var selectId = undefined;;

//编辑司机或者车辆
function editDriverOrCar(date,id)
{
	selectDate = date;
	selectId = id;
	$("#rightBigDiv").show();
	//如果车辆列表页面是展开的
	if($("#selectCarListDiv").css("display") == "block" && $("#selectDriverIcon").hasClass("lineSelectMore-ico-down"))
	{
		 //调用后台车辆列表
		loadCarList(selectDate)
	}
	 //如果车辆排班列表页面是展开的
	else if($("#selectCarClassListDiv").css("display") == "block")
	{
		$("#selectCarListDiv").show();
		$("#selectCarClassListDiv").hide();
		//调用后台车辆列表
		loadCarList(selectDate)
	}
	 //如果司机列表页面是展开的
	else if($("#selectDriverListDiv").css("display") == "block" && $("#selectCarIcon").hasClass("lineSelectMore-ico-down"))
	{
		//加载司机列表的数据
		loadDriverList(selectDate);
	}
	 //如果司机排班列表页面是展开的
	else if($("#selectDriverClassListDiv").css("display") == "block")
	{
		$("#selectDriverListDiv").show();
		$("#selectDriverClassListDiv").hide();
		//加载司机列表的数据
		loadDriverList(selectDate);
	}
	//添加编辑状态颜色 currEdit
	for(var i = 0; i < $("#tableDate tr").length;i++)
	{
		$("#tableDate tr").eq(i).find("div").removeClass("currEdit");
	}
	$("#"+id).children("div").addClass("currEdit");
}

function loadDriverList(selectDate,driverName){
    if(typeof(orderStartTime) == "undefined"){
	    orderStartTime = $("#firstOrderStartTime").val();
	}
	if(typeof(selectDate) == "undefined"){
	   selectDate = $("#txtSelectDate").val();
	}
	//调用后台司机列表
	var url = "../line/getDriverList.action?orderStartTime="+orderStartTime+"&orderDate="+selectDate;
	if(typeof(driverName)!="undefined"&&driverName.length>0){
	   url +="&driverName="+encodeURI(encodeURI(driverName));
	}
  	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			driverListJson = data;
			//无数据时提示暂无司机
			if(driverListJson.length == 0)
			{
				if(typeof(driverName)!="undefined"&&driverName.length>0){
					$("#noSearchDriverListDate").show();
				}
				else
				{
					$("#noDriverListDate").show();
				}
				$("#noSearchDriverListDate").nextAll("div").hide();
			}
			else
			{
				$("#noDriverListDate,#noSearchDriverListDate").hide();
				$("#noSearchDriverListDate").nextAll("div").show();
				showDriverListDate(driverListJson,"driverListUL");
				//判断是否是ie8
				if(navigator.userAgent.indexOf("MSIE")>0)  
				{   
				    //ie8
				    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
				    {  
						$("#moveDriverListDiv").removeClass("div_scroll");
						//司机列表DIV分页
						calculateMoveHeight("moveDriverListDiv","scrollDriverListBarBigDiv","scrollDriverListBarDiv");
				    }   
				    else  
					{  
						$(".div_scroll").scroll_absolute({arrows:true});
					}
				}
				else  
				{  
					$(".div_scroll").scroll_absolute({arrows:true});
				} 
			}
		},
		error:function()
		{
			if(typeof(driverName)!="undefined"&&driverName.length>0){
				$("#noSearchDriverListDate").show();
			}
			else
			{
				$("#noDriverListDate").show();
			}
			$("#noSearchDriverListDate").nextAll("div").hide();
		}
	});
}

function loadCarList(selectDate,carInfo){
    //调用后台车辆列表
	if(orderStartTime==undefined){
	    orderStartTime = $("#firstOrderStartTime").val();
	}
	if(typeof(selectDate) == "undefined"){
	   selectDate = $("#txtSelectDate").val();
	}
	carListJson = "";
	//调用后台司机列表
	var url = "../line/getCarList.action?orderStartTime="+orderStartTime+"&orderDate="+selectDate;
	if(typeof(carInfo)!="undefined"&&carInfo.length>0){
	   url +="&carInfo="+encodeURI(encodeURI(carInfo));
	}
	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			carListJson = data;
			//无数据时提示暂无车辆
			if(carListJson.length == 0)
			{
				if(typeof(carInfo)!="undefined"&&carInfo.length>0){
					$("#noSearchCarListDate").show();
				}
				else
				{
					$("#noCarListDate").show();
				}
				$("#noSearchCarListDate").nextAll("div").hide();
			}
			else
			{
				$("#noSearchCarListDate,#noCarListDate").hide();
				$("#noSearchCarListDate").nextAll("div").show();
				showCarListDate(carListJson,"carListUL");
				//判断是否是ie8
				if(navigator.userAgent.indexOf("MSIE")>0)  
				{   
				    //ie8
				    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
				    {  
						$("#moveCarListDiv").removeClass("div_scroll");
						//车辆列表DIV分页
						calculateMoveHeight("moveCarListDiv","scrollCarListBarBigDiv","scrollCarListBarDiv");
				    }
				    else  
					{  
						$(".div_scroll").scroll_absolute({arrows:true});
					}   
				}
				else  
				{  
					$(".div_scroll").scroll_absolute({arrows:true});
				} 
			}
		},
		error:function()
		{
			if(typeof(carInfo)!="undefined"&&carInfo.length>0){
				$("#noSearchCarListDate").show();
			}
			else
			{
				$("#noCarListDate").show();
			}
			$("#noSearchCarListDate").nextAll("div").hide();
		}
	});
}
//未调度的时候点击调度按钮，选择了车辆和司机后，获取非双休的日期并且统一赋值
function setNoWeekDateValue(id,driverName,vehicleNumber,orderCount,driverId,vehicleId,flag)
{
	var yearIndex = $("#txtSelectDate").val().split("-")[0];
	var monthIndex =  $("#txtSelectDate").val().split("-")[1];
	var dayIndex =  $("#txtSelectDate").val().split("-")[2];
	//选中的年月
	var selectYear = yearIndex + "-" + monthIndex;
	var dateTime = yearIndex + "-" + monthIndex + "-1";
	//获取月的天数
	var monthDays = getMonthDays(yearIndex,monthIndex);
	//获取选择的月份是周几
	var weekDay = getWeekDay(dateTime);
	//下拉框所选的年月
	var showYear = selectYear.split("-")[0];
	var showMonth = selectYear.split("-")[1];
	
	for(var j = 1;j<8;j++){
		//所选月份展示天数
		if(weekDay == $("#"+id+"date_time"+j).attr("week"))
		{
			for(var m = 0;m < monthDays;m++)
			{
				//系统显示的当前年月
				if(selectYear ==  getCurrTime().split("-")[0]+"-"+ getCurrTime().split("-")[1])
				{
					if(parseDate((m+1)) >= getCurrTime().split("-")[2])
					{
						$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).text("非工作日").show();	
						for(var n = 0; n < jsonDate.length;n++)
						{
							//司机赋值
							if(flag == "driver" || flag == "driverClear")
							{
								if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
								{
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).attr("driverId",driverId);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).text(driverName);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).hide();
								}
								
							}
							//座位数和车牌号赋值
							else if(flag == "car" || flag == "carClear")
							{
								if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
								{
									$("#"+id+"date_no"+parseInt(m+j)).text(orderCount);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).attr("vehicleId",vehicleId);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).text(vehicleNumber);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).hide();
								}
							}
							//清空所有设置的车辆和车牌号
							else if(flag == "allClear")
							{
								if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
								{
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).text(driverName);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).text(vehicleNumber);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).attr("driverId",driverId);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).attr("vehicleId",vehicleId);
									$("#"+id+"date_no"+parseInt(m+j)).text(jsonDate[n].orderCount);
									$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).hide();
								}
							}
						}
					}
				}
				else
				{
					$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).text("非工作日").show();	
					for(var n = 0; n < jsonDate.length;n++)
					{
						//司机赋值
						if(flag == "driver" || flag == "driverClear")
						{
							if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
							{
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).attr("driverId",driverId);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).text(driverName);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).hide();
							}
							
						}
						//座位数和车牌号赋值
						else if(flag == "car" || flag == "carClear")
						{
							if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
							{
								$("#"+id+"date_no"+parseInt(m+j)).text(orderCount);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).attr("vehicleId",vehicleId);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).text(vehicleNumber);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).hide();
							}
						}
						//清空所有设置的车辆和车牌号
						else if(flag == "allClear")
						{
							if(jsonDate[n].orderDate == (showYear+"-"+showMonth+"-"+parseDate(m+1)))
							{
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).text(driverName);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).text(vehicleNumber);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).attr("driverId",driverId);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).attr("vehicleId",vehicleId);
								$("#"+id+"date_no"+parseInt(m+j)).text(jsonDate[n].orderCount);
								$("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).hide();
							}
						}
					}
				}
			}
		}
	}
}

//获取所有非工作日的所有设置的日期的司机和车牌号和座位数
function getAllJsonSetDriverAndCar(id,selectDate)
{
	var allJsonSetDriverAndCar = [];
	var yearIndex = selectDate.split("-")[0];
	var monthIndex =  selectDate.split("-")[1];
	//选中的年月
	var selectYear = yearIndex + "-" + monthIndex;
	var dateTime = yearIndex + "-" + monthIndex + "-1";
	//获取月的天数
	var monthDays = getMonthDays(yearIndex,monthIndex);
	//获取选择的月份是周几
	var weekDay = getWeekDay(dateTime);

	for(var j = 1;j<8;j++){
		//所选月份展示天数
		if(weekDay == $("#"+id+"date_time"+j).attr("week"))
		{
			for(var m = 0;m < monthDays;m++)
			{
				if($("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(1).css("display") == "none")	
				{
					var orderDate = $("#"+id+"date_time"+parseInt(m+j)).attr("date");
					var driverName = $("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).text();
					var vehicleSeats = $("#"+id+"date_no"+parseInt(m+j)).text();
					var vehicleNumber = $("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).text();
					var driverId = $("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).attr("driverId");
					var vehicleId = $("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).attr("vehicleId");
					allJsonSetDriverAndCar.push({"orderDate":orderDate,"driverName":driverName,"vehicleSeats":vehicleSeats,"vehicleNumber":vehicleNumber,"driverId":driverId,"vehicleId":vehicleId});
				}
			}
		}
	}
	return allJsonSetDriverAndCar;
}

//获取所有设置的日期的司机和车牌号和座位数
function getAllSetDriverAndCar(id)
{
	var allDriverAndCarArrs = [];
	var yearIndex = $("#txtSelectDate").val().split("-")[0];
	var monthIndex =  $("#txtSelectDate").val().split("-")[1];
	//选中的年月
	var selectYear = yearIndex + "-" + monthIndex;
	var dateTime = yearIndex + "-" + monthIndex + "-1";
	//获取月的天数
	var monthDays = getMonthDays(yearIndex,monthIndex);
	//获取选择的月份是周几
	var weekDay = getWeekDay(dateTime);

	for(var j = 1;j<8;j++){
		//所选月份展示天数
		if(weekDay == $("#"+id+"date_time"+j).attr("week"))
		{
			for(var m = 0;m < monthDays;m++)
			{
				var orderDate = $("#"+id+"date_time"+parseInt(m+j)).attr("date");
				var driverName = $("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).text();
				var vehicleSeats = $("#"+id+"date_no"+parseInt(m+j)).text();
				var vehicleNumber = $("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).text();
				var driverId = $("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(2).attr("driverId");
				var vehicleId = $("#"+id+"date_time"+parseInt(m+j)).children("div").find("p").eq(3).attr("vehicleId");
				allDriverAndCarArrs.push({"orderDate":orderDate,"driverName":driverName,"vehicleSeats":vehicleSeats,"vehicleNumber":vehicleNumber,"driverId":driverId,"vehicleId":vehicleId});
			}
		}
	}
	return allDriverAndCarArrs;
}

//获取本月所有非空的数据
function getAllNotNullSetDriverAndCar()
{
	var allDriverAndCarArr = getAllSetDriverAndCar("tableDate");
	var allNotNullSetDriverAndCar = [];
	for(var i = 0;i < allDriverAndCarArr.length;i++)
	{
		if((typeof(allDriverAndCarArr[i].driverId) != "undefined" && typeof(allDriverAndCarArr[i].vehicleId) != "undefined") && (allDriverAndCarArr[i].driverId != "" && allDriverAndCarArr[i].vehicleId != ""))
		{
			allNotNullSetDriverAndCar.push({"orderDate":allDriverAndCarArr[i].orderDate,"driverName":allDriverAndCarArr[i].driverName,"vehicleSeats":allDriverAndCarArr[i].vehicleSeats,"vehicleNumber":allDriverAndCarArr[i].vehicleNumber,"driverId":allDriverAndCarArr[i].driverId,"vehicleId":allDriverAndCarArr[i].vehicleId});
		}
	}
	return allNotNullSetDriverAndCar;
}
/** -----------------------------------------------------------------------------------日历相关js end --------------------------------------------------------------------------------- **/

/** -----------------------------------------------------------------------------------右侧司机相关js start --------------------------------------------------------------------------------- **/
//显示司机列表
function showDriverListDate(driverListJson,id)
{
	$("#"+id).children("li").remove(); //移除之前所有的li
	for(var i = 0;i < driverListJson.length;i++){
		var $driverListUL = $("#"+id);
		/**
		第一个p标签的第一个span显示性别
		男  sexBoy,女   sexGirl,没有填写性别   sexNone
		第一个p标签的第一个span后的em显示名字
		
		第二个p标签的第一个span后的em显示电话
		
		有排班  li追加样式hadwork
		当前选中的li追加样式 cur
		
		第三个p标签第一个a标签查看排班，第二个a标签选择
		**/ 
		var $li = $("<li class='fl p-r' id='"+id+"li"+i+"'>" +
			"<p><span class='fr hide'>有排班</span><span class='sex'></span><em class='f14 fw' title="+driverListJson[i].driverName+">"+cutstr(driverListJson[i].driverName,6)+"</em></p>"+
			"<p><span class='lineSelectMore-list-tel'></span><em class='f-arial'>"+driverListJson[i].telephone+"</em></p>"+
			"<p class='p-a lineSelectMore-listMore'><a href='javascript:void(0)' class='white1 fr' onclick='lookDriverClass(this,\""+driverListJson[i].driverName+"\",\""+driverListJson[i].telephone+"\",\""+driverListJson[i].driverId+"\")'>查看排班</a><a href='javascript:void(0)' class='white1' id='driver"+i+"' onclick='selectOneDriver(this.id,\""+driverListJson[i].driverName+"\",\""+driverListJson[i].telephone+"\",\""+driverListJson[i].driverId+"\")'>选择</a></p>"+
		"</li>"); 
		$driverListUL.append($li);
		if(driverListJson[i].sex == "0") //0表示男
		{
			$("#"+id+"li"+i).children("p").eq(0).children("span").eq(1).addClass("sexBoy");
		}
		else if(driverListJson[i].sex == "1")  //0表示女
		{ 
			$("#"+id+"li"+i).children("p").eq(0).children("span").eq(1).addClass("sexGirl");
		}
		else  //没有填写性别
		{
			$("#"+id+"li"+i).children("p").eq(0).children("span").eq(1).addClass("sexNone");
		}
		if(driverListJson[i].scheduleCount > 0){ //1表示已排班
			$("#"+id+"li"+i).addClass("hadwork");
			$("#"+id+"li"+i).children("p").eq(0).children("span").eq(0).show();
		}
	}
}

/*
 * cutstr函数是截取字符串;
 * 2个必传参数
 * @str    需要截取的字符串
 * @len    需要截取的长度（中文占2个）
 * @return {字符串} str新的字符串
 */
function cutstr(str,len)
{ 
    var str_length = 0; 
    var str_len = 0; 
    str_cut = new String(); 
    str_len = str.length; 
    for(var i = 0; i < str_len; i++) 
    { 
        a = str.charAt(i); 
        str_length++; 
        if(escape(a).length > 4) 
        { 
            //中文字符的长度经编码之后大于4 
            str_length++; 
        } 
        str_cut = str_cut.concat(a);
        if(str_length > len) 
        { 
            //str_cut = str_cut.substring(0,str_cut.length-1).concat("..."); 
            str_cut = str_cut.substring(0,str_cut.length-1);
            return str_cut; 
        } 
    } 
    //如果给定字符串小于等于指定长度，则返回源字符串； 
    if(str_length <= len){ 
        return  str; 
    } 
} 
//显示车辆列表
function showCarListDate(carListJson,id)
{
	$("#"+id).children("li").remove(); //移除之前所有的li
	for(var i = 0;i < carListJson.length;i++){
		var $carListUL = $("#"+id);
		/**
		有排班  li追加样式hadwork
		当前选中的li追加样式 cur
		**/ 
		var $li = $("<li class='fl p-r' id='"+id+"li"+i+"'>" +
			"<p><span class='fr hide'>有排班</span><em class='f14 fw' title="+carListJson[i].vehicleBrand+">"+cutstr(carListJson[i].vehicleBrand,6)+"</em></p>"+
			"<p class='f-arial black4'><em class='fr green1'>"+carListJson[i].vehicleSeats+"</em>"+carListJson[i].vehicleNumber+"</p>"+
			"<p class='p-a lineSelectMore-listMore'><a href='javascript:void(0)' class='white1 fr' onclick='lookCarClass(this,\""+carListJson[i].vehicleBrand+"\",\""+carListJson[i].vehicleSeats+"\",\""+carListJson[i].vehicleNumber+"\",\""+carListJson[i].vehicleId+"\")'>查看排班</a><a href='javascript:void(0)' class='white1' id='car"+i+"' onclick='selectOneCar(this.id,\""+carListJson[i].vehicleBrand+"\",\""+carListJson[i].vehicleSeats+"\",\""+carListJson[i].vehicleNumber+"\",\""+carListJson[i].vehicleId+"\")'>选择</a></p>"+
		"</li>"); 
		$carListUL.append($li);

		if(carListJson[i].scheduleCount > 0){ //1表示已排班
			$("#"+id+"li"+i).addClass("hadwork");
			$("#"+id+"li"+i).children("p").eq(0).children("span").eq(0).show();
		}
	}
}

//显示司机或者车辆排班列表
function showDriverOrCarClassListDate(classListJson,id)
{
	$("#"+id).children("li:eq(0)").show();
	$("#"+id).children("li:not(:eq(0))").remove(); //除了第一行搜索框，移除之前所有的li
	for(var i = 0;i < classListJson.length;i++){
		var $classListUL = $("#"+id);
		/**
		<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down-->
		**/ 
		var $li = $("<li class='mt30' id='"+id+"li"+i+"'>" +
		"<p class='f14'>"+classListJson[i].orderDate+"<em class='gray3 ml10'>(约"+classListJson[i].lineTime+")</em></p>"+
		"<P><span class='display-ib lineKind mr10'></span>从 <em class='fw mr4 ml4'>"+classListJson[i].startname+"</em> 开往 <em class='fw mr4 ml4'>"+classListJson[i].endname
+"</em> </P>"+
		"</li>"); 
		$classListUL.append($li);

		if(classListJson[i].lineSubType == "0"){ //lineSubType : 0: 上下班 1:自由行
			$("#"+id+"li"+i).addClass("hadwork");
			$("#"+id+"li"+i).children("p").eq(1).children("span").eq(0).text("上下班").addClass("lineKind-work");
		}
		else if(classListJson[i].lineSubType == "1"){ //lineSubType : 0: 上下班 1:自由行
			$("#"+id+"li"+i).addClass("hadwork");
			$("#"+id+"li"+i).children("p").eq(1).children("span").eq(0).text("自由行").addClass("lineKind-free");
		}
	}
}

$(function(){	
	
	//选择司机大div
	$("#selectDriverIcon").click(function(){
		if($("#selectDriverBigDiv").css("display") == "none")
		{
			$("#selectDriverBigDiv").slideDown();
			$("#selectCarBigDiv").slideUp();
			$("#selectCarIcon").removeClass("lineSelectMore-ico-up").addClass("lineSelectMore-ico-down");
			$(this).removeClass("lineSelectMore-ico-down").addClass("lineSelectMore-ico-up");
			 //如果司机列表页面是展开的
			if($("#selectDriverListDiv").css("display") == "block" && $("#selectCarIcon").hasClass("lineSelectMore-ico-down"))
			{
				//加载司机列表的数据
				loadDriverList(selectDate);
			}
			 //如果司机排班列表页面是展开的
			else if($("#selectDriverClassListDiv").css("display") == "block")
			{
				//加载司机排班列表的数据
				searchDriverClass();
			}
		}
		else
		{
			$("#selectDriverBigDiv").slideUp();
			$("#selectCarBigDiv").slideDown();
			$("#selectCarIcon").removeClass("lineSelectMore-ico-down").addClass("lineSelectMore-ico-up");
			$(this).removeClass("lineSelectMore-ico-up").addClass("lineSelectMore-ico-down");
			//如果车辆列表页面是展开的
			if($("#selectCarListDiv").css("display") == "block" && $("#selectDriverIcon").hasClass("lineSelectMore-ico-down"))
			{
				 //调用后台车辆列表
				loadCarList(selectDate)
			}
			 //如果车辆排班列表页面是展开的
			else if($("#selectCarClassListDiv").css("display") == "block")
			{
				//加载车辆排班列表的数据
				searchCarClass();
			}
		}
	});
	
	//选择车辆大div
	$("#selectCarIcon").click(function(){
		if($("#selectCarBigDiv").css("display") == "none")
		{
			$("#selectCarBigDiv").slideDown();
			$("#selectDriverBigDiv").slideUp();
			$("#selectDriverIcon").removeClass("lineSelectMore-ico-up").addClass("lineSelectMore-ico-down");
			$(this).removeClass("lineSelectMore-ico-down").addClass("lineSelectMore-ico-up");
			//如果车辆列表页面是展开的
			if($("#selectCarListDiv").css("display") == "block" && $("#selectDriverIcon").hasClass("lineSelectMore-ico-down"))
			{
				 //调用后台车辆列表
				loadCarList(selectDate)
			}
			 //如果车辆排班列表页面是展开的
			else if($("#selectCarClassListDiv").css("display") == "block")
			{
				//加载车辆排班列表的数据
				searchCarClass();
			}
		}
		else
		{
			$("#selectCarBigDiv").slideUp();
			$("#selectDriverBigDiv").slideDown();
			$("#selectDriverIcon").removeClass("lineSelectMore-ico-down").addClass("lineSelectMore-ico-up");
			$(this).removeClass("lineSelectMore-ico-up").addClass("lineSelectMore-ico-down");
			//如果司机列表页面是展开的
			if($("#selectDriverListDiv").css("display") == "block" && $("#selectCarIcon").hasClass("lineSelectMore-ico-down"))
			{
				//加载司机列表的数据
				loadDriverList(selectDate);
			}
			 //如果司机排班列表页面是展开的
			else if($("#selectDriverClassListDiv").css("display") == "block")
			{
				//加载司机排班列表的数据
				searchDriverClass();
			}
		}
	});
});

//存某个司机的数据数组
var oneDriverArray = [];
//从司机列表中选择某个司机
function selectOneDriver(id,driverName,driverTel,driverId)
{
	if($("#"+id).text() == "取消选择")
	{
		$("#"+id).text("选择");
		$("#"+id).parents("li").removeClass("cur");
		oneDriverArray.splice(0,oneDriverArray.length);
	}
	else
	{
		$("#"+id).parents("li").addClass("cur").siblings().removeClass("cur");
		for(var i = 0; i < $("#driverListUL").children("li").length;i++)
		{
			$("#driverListUL li").eq(i).find("p").eq(2).children("a").eq(1).text("选择");
		}
		if(oneDriverArray.length > 0)
		{
			oneDriverArray.splice(0,oneDriverArray.length);
		}
		oneDriverArray.push({"orderDate":$("#"+selectId).attr("date"),"driverName":driverName,"driverTel":driverTel,"driverId":driverId});
		$("#"+id).text("取消选择");
	}
	//整个日历一直赋值
	if(typeof(selectId) == "undefined")
	{
		if(oneDriverArray.length > 0)
		{
			setNoWeekDateValue("tableDate",oneDriverArray[0].driverName,"","",oneDriverArray[0].driverId,"","driver");
		}
		else
		{
			setNoWeekDateValue("tableDate","","","","","","driverClear");
		}
	}
	else
	{
		if(oneDriverArray.length > 0)
		{
			$("#"+selectId).children("div").find("p").eq(1).hide();
			//选择司机    司机姓名赋值
			$("#"+selectId).children("div").find("p").eq(2).text(oneDriverArray[0].driverName);
			$("#"+selectId).children("div").find("p").eq(2).attr("driverId",oneDriverArray[0].driverId);
			//alert("司机="+oneDriverArray[0].driverName);
		}
		else
		{
			//取消选择   司机姓名清空  
			$("#"+selectId).children("div").find("p").eq(2).text("");
			$("#"+selectId).children("div").find("p").eq(2).attr("driverId","");
		}
	}
}
//查看司机排班
function loadDriverClass(driverId,beginOrderDate,endOrderDate){
	if(orderStartTime==undefined){
	    orderStartTime = $("#firstOrderStartTime").val();
	}
	var selectDate = $("#txtSelectDate").val();
	driverClassListJson = "";
	//调用后台司机排班列表
	var url = "../line/getDriverClass.action?orderStartTime="+orderStartTime+"&orderDate="+selectDate+"&driverId="+driverId;
	if(typeof(beginOrderDate)!="undefined"&&beginOrderDate.length>0){
	   url +="&beginOrderDate="+beginOrderDate;
	}
	if(typeof(endOrderDate)!="undefined"&&endOrderDate.length>0){
	   url +="&endOrderDate="+endOrderDate;
	}
	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			driverClassListJson = data;
			//无数据时提示暂无排班
			if(driverClassListJson.length == 0)
			{
				$("#noDriverClassListDate").show();
				$("#driverClassListUL").children("li:not(:eq(0))").remove(); //移除之前所有的li
				$("#driverClassListUL").children("li:eq(0)").hide();
				$("#moveDriverClassListDiv").hide();
			}
			else
			{
				$("#noDriverClassListDate").hide();
				$("#moveDriverClassListDiv").show();
				showDriverOrCarClassListDate(driverClassListJson,"driverClassListUL");
				//判断是否是ie8
				if(navigator.userAgent.indexOf("MSIE")>0)  
				{   
				    //ie8
				    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
				    {  
						$("#moveDriverClassListDiv").removeClass("div_scroll");
						//查看某个司机排班列表DIV分页
						calculateMoveHeight("moveDriverClassListDiv","scrollDriverClassListBarBigDiv","scrollDriverClassListBarDiv");
				    }
				    else  
					{  
						$(".div_scroll").scroll_absolute({arrows:true});
					}   
				}
				else  
				{  
					$(".div_scroll").scroll_absolute({arrows:true});
				} 
			}
		},
		error:function()
		{
			$("#noDriverClassListDate").show();
			$("#driverClassListUL").children("li:not(:eq(0))").remove(); //移除之前所有的li
			$("#driverClassListUL").children("li:eq(0)").hide();
			$("#moveDriverClassListDiv").hide();
		}
	});
}

//查看某个司机排班
function lookDriverClass(obj,driverName,driverTel,driverId)
{
	$("#selectDriverListDiv").hide();
	$("#selectDriverClassListDiv").show();
	$("#driverNameSelect").text(driverName);
	$("#selectThisDriver").attr("driverName",driverName);
	$("#selectThisDriver").attr("driverTel",driverTel);
	$("#selectThisDriver").attr("driverId",driverId);
	$("#selectThisDriver").attr("driverDivId",$(obj).next().attr("id"));
	setPreParameter();
	tempDriverId = driverId;
	//调用后台司机排班数据
	loadDriverClass(driverId);
}

//从查看某个司机排班情况返回到司机列表
function goBackDriverList()
{
	$("#selectDriverListDiv").show();
	$("#selectDriverClassListDiv").hide();
	setCurrParameter();
	//判断是否是ie8
	if(navigator.userAgent.indexOf("MSIE")>0)  
	{   
	    //ie8
	    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
	    {  
			
	    }
	    else  
		{  
			$(".div_scroll").scroll_absolute({arrows:true});
		} 
	}
	else  
	{  
		$(".div_scroll").scroll_absolute({arrows:true});
	} 
}

//选此司机
function selectThisDriver()
{
	selectOneDriver($("#selectThisDriver").attr("driverDivId"),$("#selectThisDriver").attr("driverName"),$("#selectThisDriver").attr("driverTel"),$("#selectThisDriver").attr("driverId"));
	goBackDriverList();
}

/** -----------------------------------------------------------------------------------右侧司机相关js end --------------------------------------------------------------------------------- **/


/** -----------------------------------------------------------------------------------右侧车辆相关js start --------------------------------------------------------------------------------- **/
//存某个车辆的数据数组
var oneCarArray = [];
//从车辆列表中选择某个车辆
function selectOneCar(id,vehicleBrand,vehicleSeats,vehicleNumber,vehicleId)
{
	if($("#"+id).text() == "取消选择")
	{
		$("#"+id).text("选择");
		$("#"+id).parents("li").removeClass("cur");
		oneCarArray.splice(0,oneCarArray.length);
	}
	else
	{
		$("#"+id).parents("li").addClass("cur").siblings().removeClass("cur");
		for(var i = 0; i < $("#carListUL").children("li").length;i++)
		{
			$("#carListUL li").eq(i).find("p").eq(2).children("a").eq(1).text("选择");
		}
		if(oneCarArray.length > 0)
		{
			oneCarArray.splice(0,oneCarArray.length);
		}
		oneCarArray.push({"orderDate":$("#"+selectId).attr("date"),"vehicleBrand":vehicleBrand,"vehicleSeats":vehicleSeats,"vehicleNumber":vehicleNumber,"vehicleId":vehicleId});
		$("#"+id).text("取消选择");
	}
	//整个日历一直赋值
	if(typeof(selectId) == "undefined")
	{
		if(oneCarArray.length > 0)
		{
			setNoWeekDateValue("tableDate","",oneCarArray[0].vehicleNumber,oneCarArray[0].orderCount,"",oneCarArray[0].vehicleId,"car");
		}
		else
		{
			setNoWeekDateValue("tableDate","","","","","","carClear");
		}
	}
	else
	{
		if(oneCarArray.length > 0)
		{
			$("#"+selectId).children("div").find("p").eq(1).hide();
			//座位数赋值
			//$("#"+selectId).children("div").find("p").eq(0).find("span").eq(0).text(oneCarArray[0].vehicleSeats);
			//车牌号赋值
			$("#"+selectId).children("div").find("p").eq(3).text(oneCarArray[0].vehicleNumber);
			$("#"+selectId).children("div").find("p").eq(3).attr("vehicleId",oneCarArray[0].vehicleId);
			//alert("车辆="+oneCarArray[0].vehicleNumber);
		}
		else
		{
			//取消选择清空  座位数和车牌号
			//$("#"+selectId).children("div").find("p").eq(0).find("span").eq(0).text("");
			$("#"+selectId).children("div").find("p").eq(3).text("");
			$("#"+selectId).children("div").find("p").eq(3).attr("vehicleId","");
		}
	}
}

//查看车辆排班
function loadCarClass(vehicleId,beginOrderDate,endOrderDate){
	if(orderStartTime==undefined){
	    orderStartTime = $("#firstOrderStartTime").val();
	}
	var selectDate = $("#txtSelectDate").val();
	carClassListJson = "";
	//调用后台司机排班列表
	var url = "../line/getCarClass.action?orderStartTime="+orderStartTime+"&orderDate="+selectDate+"&vehicleId="+vehicleId;
	if(typeof(beginOrderDate)!="undefined"&&beginOrderDate.length>0){
	   url +="&beginOrderDate="+beginOrderDate;
	}
	if(typeof(endOrderDate)!="undefined"&&endOrderDate.length>0){
	   url +="&endOrderDate="+endOrderDate;
	}
	//alert("url:"+url);
	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			carClassListJson = data;
			//无数据时提示暂无排班
			if(carClassListJson.length == 0)
			{
				$("#noCarClassListDate").show();
				$("#carClassListUL").children("li:not(:eq(0))").remove(); //移除之前所有的li
				$("#carClassListUL").children("li:eq(0)").hide();
				$("#moveCarClassListDiv").hide();
			}
			else
			{
				$("#noCarClassListDate").hide();
				$("#moveCarClassListDiv").show();
				showDriverOrCarClassListDate(carClassListJson,"carClassListUL");
				//判断是否是ie8
				if(navigator.userAgent.indexOf("MSIE")>0)  
				{   
				    //ie8
				    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
				    {  
						$("#moveCarClassListDiv").removeClass("div_scroll");
						//查看某个车辆排班列表DIV分页
						calculateMoveHeight("moveCarClassListDiv","scrollCarClassListBarBigDiv","scrollCarClassListBarDiv");
				    }  
				    else  
					{  
						$(".div_scroll").scroll_absolute({arrows:true});
					}  
				}
				else  
				{  
					$(".div_scroll").scroll_absolute({arrows:true});
				} 
			}
		},
		error:function()
		{
			$("#noCarClassListDate").show();
			$("#carClassListUL").children("li:not(:eq(0))").remove(); //移除之前所有的li
			$("#carClassListUL").children("li:eq(0)").hide();
			$("#moveCarClassListDiv").hide();
		}
	});
}

//查看某个车辆排班
function lookCarClass(obj,vehicleBrand,vehicleSeats,vehicleNumber,vehicleId)
{
	$("#selectCarListDiv").hide();
	$("#selectCarClassListDiv").show();
	$("#vehicleNumber").text(vehicleNumber);
	$("#selectThisCar").attr("vehicleBrand",vehicleBrand);
	$("#selectThisCar").attr("vehicleSeats",vehicleSeats);
	$("#selectThisCar").attr("vehicleNumber",vehicleNumber);
	$("#selectThisCar").attr("vehicleId",vehicleId);
	$("#selectThisCar").attr("carDivId",$(obj).next().attr("id"));
	setPreParameter();
	tempVehicleId=vehicleId;
	//调用后台车辆排班数据
	loadCarClass(vehicleId);
}

//从查看某个车辆排班情况返回到车辆列表
function goBackCarList()
{
	$("#selectCarListDiv").show();
	$("#selectCarClassListDiv").hide();
	setCurrParameter();
	//判断是否是ie8
	if(navigator.userAgent.indexOf("MSIE")>0)  
	{   
	    //ie8
	    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
	    {  
			
	    } 
	    else  
		{  
			$(".div_scroll").scroll_absolute({arrows:true});
		}   
	}
	else  
	{  
		$(".div_scroll").scroll_absolute({arrows:true});
	}   
}

//选此车辆
function selectThisCar()
{
	selectOneCar($("#selectThisCar").attr("carDivId"),$("#selectThisCar").attr("vehicleBrand"),$("#selectThisCar").attr("vehicleSeats"),$("#selectThisCar").attr("vehicleNumber"),$("#selectThisCar").attr("vehicleId"));
	goBackCarList();
}

//设置上一次的保存参数
function setPreParameter()
{
	//上一次当前页数
	preMoveCurrPage = moveCurrPage;
	//上一次总页数
	preMoveTotalPage = moveTotalPage;
	//上一次移动的Div对象
    preMoveObj = moveObj;
    //上一次移动的div可视内容区域高度
    preMoveObjHeight = moveObjHeight;
}

//设置当前保存的参数
function setCurrParameter()
{
	//返回后当前页数要等于上一次当前页数
	moveCurrPage = preMoveCurrPage;
	//返回后总页数要等于上一次总页数
	moveTotalPage = preMoveTotalPage;
	//返回后移动的div对象要等于上一次移动的Div对象
    moveObj = preMoveObj;
    //返回后div可视内容区域高度要等于上一次移动的div可视内容区域高度
    moveObjHeight = preMoveObjHeight;
}
/** -----------------------------------------------------------------------------------右侧车辆相关js start --------------------------------------------------------------------------------- **/
//保存
function saveDriverAndCar()
{
    var selectYearAndMonth = $("#txtSelectDate").val();
	if(typeof(orderStartTime) == "undefined"){
	    orderStartTime = $("#firstOrderStartTime").val();
	}
	//获取所有的设置司机和车辆数组
	var allDriverAndCarArr = getAllSetDriverAndCar("tableDate");
	//获取本月所有非空的数据
	var allNotNullSetDriverAndCar = getAllNotNullSetDriverAndCar();
	if(allDriverAndCarArr.length > 0)
	{
	    var orderDates = "";
	    var driverIds = "";
	    var vehicleIds = "";
	    var count = 0;
	    
		for(var i = 0;i < allDriverAndCarArr.length;i++)
		{
			for(var n = 0; n < jsonDate.length;n++)
			{
				//整个月或者单个进行编辑，设置司机和车辆
				if(jsonDate[n].orderDate == allDriverAndCarArr[i].orderDate)
				{
					//本月的司机或者车辆某个为空时
					if((allDriverAndCarArr[i].driverId == "" && allDriverAndCarArr[i].vehicleId != "") || (allDriverAndCarArr[i].driverId != "" && allDriverAndCarArr[i].vehicleId == "") || (typeof(allDriverAndCarArr[i].driverId) == "undefined" && typeof(allDriverAndCarArr[i].vehicleId) != "undefined") || (typeof(allDriverAndCarArr[i].driverId) != "undefined" && typeof(allDriverAndCarArr[i].vehicleId) == "undefined"))
					{
						parent.showPopCommonPage2("请设置"+allDriverAndCarArr[i].orderDate+"号司机或者车辆","true","remind");
						return;
					} 
					//本月全部设置为空
					if((allDriverAndCarArr[i].driverId == "" && allDriverAndCarArr[i].vehicleId == "" && 0 == allNotNullSetDriverAndCar.length) || (typeof(allDriverAndCarArr[i].driverId) == "undefined" && typeof(allDriverAndCarArr[i].vehicleId) == "undefined" && 0 == allNotNullSetDriverAndCar.length))
					{
						parent.showPopCommonPage2("请设置本月的司机和车辆后再保存","true","remind");
						return;
					}
					//本月中某一天被设置未空
					if(allDriverAndCarArr[i].driverId == "" && allDriverAndCarArr[i].vehicleId == "" && jsonDate[n].driverId != allDriverAndCarArr[i].driverId && jsonDate[n].vehicleId != allDriverAndCarArr[i].vehicleId)
					{
						parent.showPopCommonPage2("请设置"+allDriverAndCarArr[i].orderDate+"号的司机或者车辆","true","remind");
						return;
					} 
				}
			}
			
			//整个月进行编辑，设置司机和车辆  , 获取所有非空的数据
			if((typeof(allDriverAndCarArr[i].driverId) != "undefined" && typeof(allDriverAndCarArr[i].vehicleId) != "undefined") && (allDriverAndCarArr[i].driverId != "" && allDriverAndCarArr[i].vehicleId != ""))
			{
				   orderDates+=allDriverAndCarArr[i].orderDate+",";
				   driverIds+=allDriverAndCarArr[i].driverId+",";
				   vehicleIds+=allDriverAndCarArr[i].vehicleId+",";
			}
		}
		//整个月进行编辑，设置司机和车辆
		orderDates = orderDates.substring(0, orderDates.length-1);
		driverIds = driverIds.substring(0, driverIds.length-1);
		vehicleIds = vehicleIds.substring(0, vehicleIds.length-1);
		removeHref("saveDriverAndCarBtn");
	    var url = "../line/scheduleClassCarDriver.action";
	    $.ajax({
	    	type: 'POST',
			url:url,		
			data:{lineBaseId:lineBaseId,orderStartTime:orderStartTime,orderDates:orderDates,driverIds:driverIds,vehicleIds:vehicleIds},
			cache:false,	
			dataType:"html",
			async : false,
			success:function(data){
				if(data == "success")
				{
					 addHref("saveDriverAndCarBtn");
					 parent.showRturnTip("保存成功","true");
					 loadDateData(lineBaseId,orderStartTime,false);
				}
				else
				{
					 addHref("saveDriverAndCarBtn");
					 parent.showRturnTip("保存失败","false");
				}
			},
			error:function(data){
			    addHref("saveDriverAndCarBtn");
				parent.showRturnTip("保存失败","false");
			}
	    });
	}
}

//动态添加A标签的href属性
function addHref(id)
{
	$("#"+id).attr("href","javascript:void(0);");
	$("#"+id).attr("onClick","saveDriverAndCar()");
}

//动态去除A标签的href属性
function removeHref(id)
{
	$("#"+id).removeAttr("href","javascript:void(0);");
	$("#"+id).removeAttr("onClick","return false;");
}

//取消
function resetDriverAndCar()
{
	if(oneDriverArray.length > 0)
	{
		oneDriverArray.splice(0,oneDriverArray.length);
	}
	if(oneCarArray.length > 0)
	{
		oneCarArray.splice(0,oneCarArray.length);
	}
	for(var i = 0; i < $("#driverListUL").children("li").length;i++)
	{
		$("#driverListUL li").eq(i).removeClass("cur");
		$("#driverListUL li").eq(i).find("p").eq(2).children("a").eq(1).text("选择");
	}
	for(var i = 0; i < $("#carListUL").children("li").length;i++)
	{
		$("#carListUL li").eq(i).removeClass("cur");
		$("#carListUL li").eq(i).find("p").eq(2).children("a").eq(1).text("选择");
	}
	//整个日历一直赋值
	if(typeof(selectId) == "undefined")
	{
		setNoWeekDateValue("tableDate","","","","","","allClear");
	}
	else
	{
		//司机姓名清空  
		$("#"+selectId).children("div").find("p").eq(1).text("");
		//座位数和车牌号
		$("#"+selectId).children("div").find("p").eq(0).find("span").eq(0).text("");
		$("#"+selectId).children("div").find("p").eq(2).text("");
	}
}

/** -----------------------------------------------------------------------------------Div移动翻页 相关js start --------------------------------------------------------------------------------- **/
//移动的div
var moveObj;
//div可视内容区域高度
var moveObjHeight = 0;
//可移动的可移动范围的高度
var shadeHeight = 370;
//可移动的div最大高度
var moveHeight = 0;
//定时器
var timeOut = -1;
//移动内容的当前页
var moveCurrPage = 1;
//移动内容的总页数
var moveTotalPage = 1;
//滚动条移动div总高度
var scrollBarBigDivHeight = 324;
//上一次当前页数
var preMoveCurrPage = 1;
//上一次总页数
var preMoveTotalPage = 1;
//上一次移动的Div对象
var preMoveObj;
//上一次移动的div可视内容区域高度
var preMoveObjHeight;

//可移动div计算可移动的高度
function calculateMoveHeight(id,scrollBarBigDivId,scrollBarDivId){
	moveObj = document.getElementById(id);
	moveObjHeight = moveObj.clientHeight;
	if (moveObjHeight > 0) {
		//移动内容的当前页
		moveCurrPage = 1;
		//移动内容的总页数
		moveTotalPage = 1;
		moveObj.style.top = "0px";
		if (moveObjHeight > shadeHeight) {
			moveHeight = parseInt(shadeHeight);
			moveTotalPage = parseInt(moveObjHeight / moveHeight);
			if (moveObjHeight % moveHeight) {
				moveTotalPage++;
			}
			refreshPageInfo(scrollBarBigDivId,scrollBarDivId);
		}
	}
	else {
		clearTimeout(timeOut);
		timeOut = setTimeout("calculateMoveHeight(\""+id+"\",\""+scrollBarBigDivId+"\",\""+scrollBarDivId+"\")", 500);
	}
}

//右侧数据移动方法
function moveDiv(action,scrollBarBigDivId,scrollBarDivId){
	var top = Math.abs(parseInt(moveObj.style.top));
	switch (action) {
		case "up":
			if (moveCurrPage > 1) {
				moveCurrPage--;
				if (top > 0) {
					moveObj.style.top = parseInt(moveObj.style.top) + moveHeight + "px";
				}
			}
			break;
		case "down":
			if (moveCurrPage < moveTotalPage) {
				moveCurrPage++;
				var currHeight = moveObjHeight - top;
				if (currHeight >= shadeHeight) {
					moveObj.style.top = parseInt(moveObj.style.top) - moveHeight + "px";
				}
			}
			break;
		default:
			break;
	}
	refreshPageInfo(scrollBarBigDivId,scrollBarDivId);
}

//右侧div分页，判断是否展示滚动条，及滚动条的位置
function refreshPageInfo(scrollBarBigDivId,scrollBarDivId) {
	if(moveTotalPage > 1) {
		//显示滚动条
		$("#"+scrollBarBigDivId).show();
	}
	else
	{
		//不显示滚动条
		$("#"+scrollBarBigDivId).hide();
	}
	if(moveCurrPage == 1 && moveTotalPage == 1)  //有且只有一页或没有数据
	{	 
		//不显示滚动条
		$("#"+scrollBarBigDivId).hide();
	}
	else if(moveCurrPage == 1 && (moveCurrPage < moveTotalPage)) //第一页
	{
		$("#"+scrollBarDivId).css({"height":(scrollBarBigDivHeight/moveTotalPage)+"px","top":"0"});
	}
	else  //中间页和最后一页
	{
		$("#"+scrollBarDivId).css({"height":(scrollBarBigDivHeight/moveTotalPage)+"px","top":($("#"+scrollBarDivId).height() * (moveCurrPage -1))+"px"});
	}
}	

/** -----------------------------------------------------------------------------------Div移动翻页 相关js start --------------------------------------------------------------------------------- **/
//查看包月价格
function goMonthPricePage()
{	
	var classTime = "";
	for(var i = 0;i < $("#currClass").children("a").length;i++)
	{
		classTime += $("#currClass").children("a").eq(i).find("em:eq(0)").text()+",";
	}
    $("#showPage",parent.window.document).load("../line/viewMonthPrice.action?random="+Math.floor(Math.random()*10000+1),{lineBaseId:lineBaseId,classTime:classTime}).show(); 
    $("#mainBody",parent.window.document).show();
}
//查看站点图片
function viewSitePice(name)
{	
    $("#showPage",parent.window.document).load("../line/viewSitePice.action?random="+Math.floor(Math.random()*10000+1),{name:escape(name)}).show(); 
    $("#mainBody",parent.window.document).show();
}
</script>

