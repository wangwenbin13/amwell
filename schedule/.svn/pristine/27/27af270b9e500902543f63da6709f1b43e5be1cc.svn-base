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
<title>线路管理-线路列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
	<p class="subNav">当前位置：首页&nbsp;&gt;&nbsp;线路管理&nbsp;&gt;&nbsp;线路详情<a class="red1 ml30" href="javascript:void(0)">返回</a></p>
	<div class="mt20 ml30 black1 mr28">
		<p class="fw f14 f-yahei">线路详情</p>
		<p class="mt20 libg"><span class="fl ml20 lh28">基本信息</span></p>	
		<div class="fl lineDetail-left mt20 ml15 mr30">
			<p>
				<span class="display-ib lineKind lineKind-work">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>
				<span class="fw ml10">龙华宝安特快线</span><span class="gray3">（约50分钟）</span><span class="red4 ml80">待调度<!-- 待调度 red4;待发布 yellow4;已发布 green1;已下线black2; --></span>
			</p>
			<p class="mt20">单次价：<em class="f16 fw yellow4 mr4 ml15">5</em>元/次</p>
			<p class="mt5">单次价：<em class="f16 fw yellow4 mr4 ml15">5</em>元(9月)<em class="f16 fw yellow4 mr4 ml15">5</em>元(10月)<em class="f16 fw yellow4 mr4 ml15">5</em>元(11月)</p>
			<p class="mt5">备&nbsp;&nbsp;注：<em class="gray1 ml15">暂无信息</em></p>
		</div>
		<div class="mt20 clearfix">
			<p class="green1"><span class="lineDetail-ico lineDetail-ico-start mr10"></span>西乡步行街</p>
			<div><span class="lineDetail-ico-go mr10"></span>西乡---宝安---西乡---宝安---西乡---宝安</div>
			<p class="red4"><span class="lineDetail-ico lineDetail-ico-end mr10"></span>西乡步行街</p>
		</div>
		<p class="mt20 libg"><span class="fl ml20 lh28">调度和订购情况</span></p>	
		<div class="mt30 ml15 clearfix">
			<a herf="javascript:void(0)" class="fl lineTime lineTime-current white1"><em class="f14 f-arial fw mr15">10:00</em><em class="f-arial">50</em>座</a><!-- 当前班次 -->
			<a herf="javascript:void(0)" class="fl lineTime lineTime-next white1"><em class="f14 f-arial fw mr15">10:00</em><em class="f-arial">50</em>座</a>
			<a herf="javascript:void(0)" class="fl lineTime lineTime-next white1"><em class="f14 f-arial fw mr15">10:00</em><em class="f-arial">50</em>座</a>
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
					<!--<div class="t-c">
						<img src="../images/noDate.png" class="mt35" width="169" height="169" alt="暂无司机" />
						<p class="mt15 f18 f-yahei">暂无司机</p>
						<P class="gray3 mt15">您还没有添加司机，添加司机后才可完成调度噢,快去添加吧！</P>
						<p class="mt40"><a href="../dispatchDriver/toDriverEditPage.action" class="yellow-btn"><em class="fw f22 va3 mr4">+</em>添加司机</a></p>
					</div>-->
					<!-- 没有数据 end -->
					<div class="mt13"><input type="text" class="lineSelectMore-selInput gray3 mr15" value="请输入司机姓名" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('black1');}" onblur="if(!value){value=defaultValue;$(this).removeClass('black1').addClass('gray3');}"/><input type="submit" class="search-btn vf10" value="" /></div>				
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
							<div class="p-a widthfull" id="moveDriverListDiv">
								<ul class="lineSelectMore-list" id="driverListUL">
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">张三</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1111111111</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'张三','1111111111','001')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver0" onclick="selectOneDriver(this.id,'张三','1111111111','001')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">李四</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2222222222</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'李四','2222222222','002')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver1" onclick="selectOneDriver(this.id,'李四','2222222222','002')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">刘三姐</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">123456788541</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'刘三姐','123456788541','003')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver2" onclick="selectOneDriver(this.id,'刘三姐','123456788541','003')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">云飞</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3333333333</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'云飞','3333333333','004')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver3" onclick="selectOneDriver(this.id,'云飞','3333333333','004')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">熠熠</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">555555555</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'熠熠','555555555','005')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver4" onclick="selectOneDriver(this.id,'熠熠','555555555','005')">选择</a></p>
									</li>	
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">柒澈</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">666666</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'柒澈','666666','006')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver5" onclick="selectOneDriver(this.id,'柒澈','666666','006')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">琦琦</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">7777</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'琦琦','7777','007')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver6" onclick="selectOneDriver(this.id,'琦琦','7777','007')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">宝宝</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">88888</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'宝宝','88888','008')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver7" onclick="selectOneDriver(this.id,'宝宝','88888','008')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">啾啾</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">9999</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'啾啾','9999','009')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver8" onclick="selectOneDriver(this.id,'啾啾','9999','009')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">祘</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">101010</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'祘','101010','010')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver9" onclick="selectOneDriver(this.id,'祘','101010','010')">选择</a></p>
									</li>	
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">十一</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1111</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'十一','1111','011')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver10" onclick="selectOneDriver(this.id,'十一','1111','011')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">十二</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1212</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'十二','1212','012')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver11" onclick="selectOneDriver(this.id,'十二','1212','012')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">13</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1313</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'13','1313','013')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver12" onclick="selectOneDriver(this.id,'13','1313','013')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">14</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1414</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'14','1414','014')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver13" onclick="selectOneDriver(this.id,'14','1414','014')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">15</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1515</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'15','1515','015')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver14" onclick="selectOneDriver(this.id,'15','1515','015')">选择</a></p>
									</li>	
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">16</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1616</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'16','1616','016')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver15" onclick="selectOneDriver(this.id,'16','1616','016')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">17</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1717</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'17','1717','017')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver16" onclick="selectOneDriver(this.id,'17','1717','017')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">18</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1818</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'18','1818','018')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver17" onclick="selectOneDriver(this.id,'18','1818','018')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">19</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">1919</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'19','1919','019')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver18" onclick="selectOneDriver(this.id,'19','1919','019')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">20</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2020</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'20','2020','020')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver19" onclick="selectOneDriver(this.id,'20','2020','020')">选择</a></p>
									</li>	
									
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">21</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2121</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'21','2121','021')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver20" onclick="selectOneDriver(this.id,'21','2121','021')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">22</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2222</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'22','2222','022')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver21" onclick="selectOneDriver(this.id,'22','2222','022')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">23</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2323</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'23','2323','023')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver22" onclick="selectOneDriver(this.id,'23','2323','023')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">24</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2424</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'24','2424','024')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver23" onclick="selectOneDriver(this.id,'24','2424','024')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">25</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2525</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'25','2525','025')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver24" onclick="selectOneDriver(this.id,'25','2525','025')">选择</a></p>
									</li>	
									
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">26</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2626</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'26','2626','026')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver25" onclick="selectOneDriver(this.id,'26','2626','026')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">27</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2727</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'27','2727','027')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver26" onclick="selectOneDriver(this.id,'27','2727','027')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">28</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2828</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'28','2828','028')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver27" onclick="selectOneDriver(this.id,'28','2828','028')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">29</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">2929</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'29','2929','029')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver28" onclick="selectOneDriver(this.id,'29','2929','029')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">30</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3030</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'30','3030','030')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver29" onclick="selectOneDriver(this.id,'30','3030','030')">选择</a></p>
									</li>	
									
									
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">31</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3131</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'31','3131','031')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver30" onclick="selectOneDriver(this.id,'31','3131','031')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">32</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3232</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'32','3232','032')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver31" onclick="selectOneDriver(this.id,'32','3232','032')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">33</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3333</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'33','3333','033')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver32" onclick="selectOneDriver(this.id,'33','3333','033')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">34</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3434</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'34','3434','034')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver33" onclick="selectOneDriver(this.id,'34','3434','034')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">35</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3535</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'35','3535','035')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver34" onclick="selectOneDriver(this.id,'35','3535','035')">选择</a></p>
									</li>	
									
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">36</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3636</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'36','3636','036')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver35" onclick="selectOneDriver(this.id,'36','3636','036')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">37</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3737</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'37','3737','037')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver36" onclick="selectOneDriver(this.id,'37','3737','037')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">38</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3838</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'38','3838','038')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver37" onclick="selectOneDriver(this.id,'38','3838','038')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">39</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">3939</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'39','3939','039')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver38" onclick="selectOneDriver(this.id,'39','3939','039')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">40</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4040</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'40','4040','040')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver39" onclick="selectOneDriver(this.id,'40','4040','040')">选择</a></p>
									</li>	
									
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">41</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4141</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'41','4141','041')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver40" onclick="selectOneDriver(this.id,'41','4141','041')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">42</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4242</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'42','4242','042')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver41" onclick="selectOneDriver(this.id,'42','4242','042')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">43</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4343</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'43','4343','043')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver42" onclick="selectOneDriver(this.id,'43','4343','043')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">44</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4444</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'44','4444','044')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver43" onclick="selectOneDriver(this.id,'44','4444','044')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">45</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4545</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'45','4545','045')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver44" onclick="selectOneDriver(this.id,'45','4545','045')">选择</a></p>
									</li>	
									
									
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">46</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4646</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'46','4646','046')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver45" onclick="selectOneDriver(this.id,'46','4646','046')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">47</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4747</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'47','4747','047')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver46" onclick="selectOneDriver(this.id,'47','4747','047')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">48</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4848</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'48','4848','048')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver47" onclick="selectOneDriver(this.id,'48','4848','048')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">49</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">4949</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'49','4949','049')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver48" onclick="selectOneDriver(this.id,'49','4949','049')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">50</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">5050</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'50','5050','050')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver49" onclick="selectOneDriver(this.id,'50','5050','050')">选择</a></p>
									</li>	
									
									
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">51</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">5151</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'51','5151','051')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver50" onclick="selectOneDriver(this.id,'51','5151','051')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><span class="sex sexGirl"><!--女--></span><em class="f14 fw">52</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">5252</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'52','5252','052')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver51" onclick="selectOneDriver(this.id,'52','5252','052')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexNone"><!--没有填写性别--></span><em class="f14 fw">53</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">53</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'53','5353','053')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver52" onclick="selectOneDriver(this.id,'53','5353','053')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><span class="sex sexGirl"></span><em class="f14 fw">54</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">5454</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'54','5454','054')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver53" onclick="selectOneDriver(this.id,'54','5454','054')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><span class="sex sexBoy"><!--男--></span><em class="f14 fw">55</em></p>
										<p><span class="lineSelectMore-list-tel"></span><em class="f-arial">5555</em></p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookDriverClass(this,'55','5555','055')">查看排班</a><a href="javascript:void(0)" class="white1" id="driver54" onclick="selectOneDriver(this.id,'55','5555','055')">选择</a></p>
									</li>		
									
											
								</ul>
							</div>
						</div>
						<!--司机列表 end-->
					</div>				
				</div>		
				<!--查看司机列表 Big Div end-->
						
				<!--查看司机排班情况   Big Div start-->
				<div class="lineSelectMore-text ml10 hide" id="selectDriverClassListDiv">
					<p class="t-c lineSelectMore-listTitle mt10"><a href="javascript:void(0)" class="fr red1" id="selectThisDriver" onclick="selectThisDriver()">选此司机</a><a href="javascript:void(0)" class="fl red1" onclick="goBackDriverList();">返回</a>司机<span id="driverName"></span>的排班情况</p>					
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
							<div class="p-a widthfull" id="moveDriverClassListDiv">
								<ul class="line24">
									<li>
							            <span class="fl r-input w15p"><input type="text" name="" value="" class="Wdate75 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
							            <span class="fl ml4 mr4 mt4">至</span>
							            <span class="fl r-input w15p"><input type="text" name="" value="" class="Wdate75 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
						       			<input type="submit" class="search-btn ml15" value="" />
						       		</li>
									<li class="mt28">
										<p class="f14">2014-08-01  01:00 <em class="gray3 ml10">(约1小时)</em></p>
										<P><span class="display-ib lineKind lineKind-work mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">龙华汽车站</em> 开往 <em class="fw mr4 ml4">宝运达物流信息大厦</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-02  02:00 <em class="gray3 ml10">(约2小时)</em></p>
										<P><span class="display-ib lineKind lineKind-free mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">宝安汽车站</em> 开往 <em class="fw mr4 ml4">龙华汽车站</em> </P>
									</li>
								</ul>
							</div>
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
					<!--<div class="t-c">
						<img src="../images/noDate.png" class="mt35" width="169" height="169" alt="暂无车辆" />
						<p class="mt15 f18 f-yahei">暂无车辆</p>
						<P class="gray3 mt15">您还没有添加车辆，添加车辆后才可完成调度噢,快去添加吧！</P>
						<p class="mt40"><a href="javascript:void(0)" class="yellow-btn"><em class="fw f22 va3 mr4">+</em>添加司机</a></p>
					</div>-->
					<!-- 没有数据 end -->
					<div class="mt13"><input type="text" class="lineSelectMore-selInput gray3 mr15" value="请输入品牌车型或车牌号" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('black1');}" onblur="if(!value){value=defaultValue;$(this).removeClass('black1').addClass('gray3');}"/><input type="submit" class="search-btn vf10" value="" /></div>				
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
							<div class="p-a widthfull" id="moveCarListDiv">
								<ul class="lineSelectMore-list" id="carListUL">
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><em class="f14 fw">金龙客车</em></p>
										<p class="f-arial black4"><em class="fr green1">30座</em>粤B12345</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'金龙客车','30座','粤B12345','001')">查看排班</a><a href="javascript:void(0)" class="white1" id="car0" onclick="selectOneCar(this.id,'金龙客车','30座','粤B12345','001')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><em class="f14 fw">火车</em></p>
										<p class="f-arial black4"><em class="fr green1">50座</em>粤B456789</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'火车','50座','粤B456789','002')">查看排班</a><a href="javascript:void(0)" class="white1" id="car1" onclick="selectOneCar(this.id,'火车','50座','粤B456789','002')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><em class="f14 fw">汽车</em></p>
										<p class="f-arial black4"><em class="fr green1">45座</em>粤B124589</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'汽车','45座','粤B124589','003')">查看排班</a><a href="javascript:void(0)" class="white1" id="car2" onclick="selectOneCar(this.id,'汽车','45座','粤B124589','003')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><em class="f14 fw">卡车</em></p>
										<p class="f-arial black4"><em class="fr green1">60座</em>粤B55555</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'卡车','60座','粤B55555','004')">查看排班</a><a href="javascript:void(0)" class="white1" id="car3" onclick="selectOneCar(this.id,'卡车','60座','粤B55555','004')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><em class="f14 fw">马车</em></p>
										<p class="f-arial black4"><em class="fr green1">32座</em>粤6666666</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'马车','32座','粤6666666','005')">查看排班</a><a href="javascript:void(0)" class="white1" id="car4" onclick="selectOneCar(this.id,'马车','32座','粤6666666','005')">选择</a></p>
									</li>
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><em class="f14 fw">金龙客车</em></p>
										<p class="f-arial black4"><em class="fr green1">30座</em>粤B12345</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'金龙客车','30座','粤B12345','001')">查看排班</a><a href="javascript:void(0)" class="white1" id="car0" onclick="selectOneCar(this.id,'金龙客车','30座','粤B12345','001')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><em class="f14 fw">火车</em></p>
										<p class="f-arial black4"><em class="fr green1">50座</em>粤B456789</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'火车','50座','粤B456789','002')">查看排班</a><a href="javascript:void(0)" class="white1" id="car1" onclick="selectOneCar(this.id,'火车','50座','粤B456789','002')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><em class="f14 fw">汽车</em></p>
										<p class="f-arial black4"><em class="fr green1">45座</em>粤B124589</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'汽车','45座','粤B124589','003')">查看排班</a><a href="javascript:void(0)" class="white1" id="car2" onclick="selectOneCar(this.id,'汽车','45座','粤B124589','003')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><em class="f14 fw">卡车</em></p>
										<p class="f-arial black4"><em class="fr green1">60座</em>粤B55555</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'卡车','60座','粤B55555','004')">查看排班</a><a href="javascript:void(0)" class="white1" id="car3" onclick="selectOneCar(this.id,'卡车','60座','粤B55555','004')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><em class="f14 fw">马车</em></p>
										<p class="f-arial black4"><em class="fr green1">32座</em>粤6666666</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'马车','32座','粤6666666','005')">查看排班</a><a href="javascript:void(0)" class="white1" id="car4" onclick="selectOneCar(this.id,'马车','32座','粤6666666','005')">选择</a></p>
									</li>	
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><em class="f14 fw">金龙客车</em></p>
										<p class="f-arial black4"><em class="fr green1">30座</em>粤B12345</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'金龙客车','30座','粤B12345','001')">查看排班</a><a href="javascript:void(0)" class="white1" id="car0" onclick="selectOneCar(this.id,'金龙客车','30座','粤B12345','001')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><em class="f14 fw">火车</em></p>
										<p class="f-arial black4"><em class="fr green1">50座</em>粤B456789</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'火车','50座','粤B456789','002')">查看排班</a><a href="javascript:void(0)" class="white1" id="car1" onclick="selectOneCar(this.id,'火车','50座','粤B456789','002')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><em class="f14 fw">汽车</em></p>
										<p class="f-arial black4"><em class="fr green1">45座</em>粤B124589</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'汽车','45座','粤B124589','003')">查看排班</a><a href="javascript:void(0)" class="white1" id="car2" onclick="selectOneCar(this.id,'汽车','45座','粤B124589','003')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><em class="f14 fw">卡车</em></p>
										<p class="f-arial black4"><em class="fr green1">60座</em>粤B55555</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'卡车','60座','粤B55555','004')">查看排班</a><a href="javascript:void(0)" class="white1" id="car3" onclick="selectOneCar(this.id,'卡车','60座','粤B55555','004')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><em class="f14 fw">马车</em></p>
										<p class="f-arial black4"><em class="fr green1">32座</em>粤6666666</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'马车','32座','粤6666666','005')">查看排班</a><a href="javascript:void(0)" class="white1" id="car4" onclick="selectOneCar(this.id,'马车','32座','粤6666666','005')">选择</a></p>
									</li>	
									<li class="fl p-r"><!--当前选中的追加样式 cur-->
										<p><em class="f14 fw">金龙客车</em></p>
										<p class="f-arial black4"><em class="fr green1">30座</em>粤B12345</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'金龙客车','30座','粤B12345','001')">查看排班</a><a href="javascript:void(0)" class="white1" id="car0" onclick="selectOneCar(this.id,'金龙客车','30座','粤B12345','001')">选择</a></p>
									</li>
									<li class="fl p-r hadwork"><!--有排班情况的追加样式hadwork-->
										<p><span class="fr">有排班</span><em class="f14 fw">火车</em></p>
										<p class="f-arial black4"><em class="fr green1">50座</em>粤B456789</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'火车','50座','粤B456789','002')">查看排班</a><a href="javascript:void(0)" class="white1" id="car1" onclick="selectOneCar(this.id,'火车','50座','粤B456789','002')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><em class="f14 fw">汽车</em></p>
										<p class="f-arial black4"><em class="fr green1">45座</em>粤B124589</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'汽车','45座','粤B124589','003')">查看排班</a><a href="javascript:void(0)" class="white1" id="car2" onclick="selectOneCar(this.id,'汽车','45座','粤B124589','003')">选择</a></p>
									</li>
									<li class="fl p-r">
										<p><em class="f14 fw">卡车</em></p>
										<p class="f-arial black4"><em class="fr green1">60座</em>粤B55555</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'卡车','60座','粤B55555','004')">查看排班</a><a href="javascript:void(0)" class="white1" id="car3" onclick="selectOneCar(this.id,'卡车','60座','粤B55555','004')">选择</a></p>
									</li>	
									<li class="fl p-r">
										<p><em class="f14 fw">马车</em></p>
										<p class="f-arial black4"><em class="fr green1">32座</em>粤6666666</p>
										<p class="p-a lineSelectMore-listMore"><a href="javascript:void(0)" class="white1 fr" onclick="lookCarClass(this,'马车','32座','粤6666666','005')">查看排班</a><a href="javascript:void(0)" class="white1" id="car4" onclick="selectOneCar(this.id,'马车','32座','粤6666666','005')">选择</a></p>
									</li>		
								</ul>
							</div>			
						</div>			
						<!--车辆列表 end-->
					</div>				
				</div>
				<!--查看车辆列表   Big Div end-->
				
				<!--查看车辆排班情况   Big Div start-->
				<div class="lineSelectMore-text ml10 hide" id="selectCarClassListDiv">
					<p class="t-c lineSelectMore-listTitle mt10"><a href="javascript:void(0)" class="fr red1" id="selectThisCar" onclick="selectThisCar()">选此车辆</a><a href="javascript:void(0)" class="fl red1" onclick="goBackCarList();">返回</a>车辆<span id="carNumber"></span>的排班情况</p>					
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
							<div class="p-a widthfull" id="moveCarClassListDiv">
								<ul class="line24">
									<li>
							            <span class="fl r-input w15p"><input type="text" name="" value="" class="Wdate75 Wdate" readonly="readonly" id="txtStartDate2" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate2\');}'})"/></span>
							            <span class="fl ml4 mr4 mt4">至</span>
							            <span class="fl r-input w15p"><input type="text" name="" value="" class="Wdate75 Wdate" readonly="readonly" id="txtEndDate2" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate2\');}'})"/></span>
						       			<input type="submit" class="search-btn ml15" value="" />
						       		</li>
									<li class="mt28">
										<p class="f14">2014-08-01  01:00 <em class="gray3 ml10">(约1小时)</em></p>
										<P><span class="display-ib lineKind lineKind-work mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">龙华汽车站</em> 开往 <em class="fw mr4 ml4">宝运达物流信息大厦</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-02  02:00 <em class="gray3 ml10">(约2小时)</em></p>
										<P><span class="display-ib lineKind lineKind-free mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">宝安汽车站</em> 开往 <em class="fw mr4 ml4">龙华汽车站</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-03  03:00 <em class="gray3 ml10">(约3小时)</em></p>
										<P><span class="display-ib lineKind lineKind-work mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">固戍地铁站</em> 开往 <em class="fw mr4 ml4">平洲</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-04  04:00 <em class="gray3 ml10">(约4小时)</em></p>
										<P><span class="display-ib lineKind lineKind-free mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">平洲站</em> 开往 <em class="fw mr4 ml4">桃园站</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-05  05:00 <em class="gray3 ml10">(约5小时)</em></p>
										<P><span class="display-ib lineKind lineKind-work mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">桃园站</em> 开往 <em class="fw mr4 ml4">布吉站</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-06  06:00 <em class="gray3 ml10">(约6小时)</em></p>
										<P><span class="display-ib lineKind lineKind-free mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">布吉站</em> 开往 <em class="fw mr4 ml4">华夏大厦</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-07  07:00 <em class="gray3 ml10">(约7小时)</em></p>
										<P><span class="display-ib lineKind lineKind-work mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">龙华汽车站</em> 开往 <em class="fw mr4 ml4">宝运达物流信息大厦</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-08  08:00 <em class="gray3 ml10">(约8小时)</em></p>
										<P><span class="display-ib lineKind lineKind-free mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">宝安汽车站</em> 开往 <em class="fw mr4 ml4">龙华汽车站</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-09  09:00 <em class="gray3 ml10">(约9小时)</em></p>
										<P><span class="display-ib lineKind lineKind-work mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">固戍地铁站</em> 开往 <em class="fw mr4 ml4">平洲</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-10  10:00 <em class="gray3 ml10">(约10小时)</em></p>
										<P><span class="display-ib lineKind lineKind-free mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">平洲站</em> 开往 <em class="fw mr4 ml4">桃园站</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-11  11:00 <em class="gray3 ml10">(约11小时)</em></p>
										<P><span class="display-ib lineKind lineKind-work mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">桃园站</em> 开往 <em class="fw mr4 ml4">布吉站</em> </P>
									</li>
									<li class="mt28">
										<p class="f14">2014-08-12  12:00 <em class="gray3 ml10">(约12小时)</em></p>
										<P><span class="display-ib lineKind lineKind-free mr10">上下班<!--上下班 lineKind-work，自由行lineKind-free，下线样式lineKind-down--></span>从 <em class="fw mr4 ml4">布吉站</em> 开往 <em class="fw mr4 ml4">华夏大厦</em> </P>
									</li>
								</ul>
							</div>
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
			<p class="tips gray4"><a herf="javascript:void(0)" class="fr ml15"><em class="display-ib fw gray2 f-arial mr4 vf1">X</em>关闭</a><span class="display-ib tips-light mr10"></span>点“调度”按钮可调度近两个月的主要司机和车辆，调度完后如有特殊换班，可鼠标移至对应日期方格，点编辑图标重新设置。</p>
			<div class="mt10 lineTime-dateTitle line24 clearfix">
				<div class="fr">司机负责人：<em class="gray2 mr10">暂未调度</em>所属车辆：<em class="gray2">暂未调度</em><a href="javascript:void(0)" class="display-ib btn1 white1 ml20">调度</a><!--调度完成后的样式<a class="red1 ml20" href="javascript:void(0)">修改</a>  --></div>
				<div class="fl p-r ml15">
					<div class="fl selTime-time w85 f18"><span id="selTimeYearDiv"></span><span class="fr selTime-arrow mt7 ml5"></span></div>
					<select class="p-a selTime-sel w93" id="yearSelect">
						<option>2011</option>
						<option>2012</option>
						<option>2013</option>
						<option>2014</option>
						<option>2015</option>
					</select>
				</div>
				<div class="fl p-r ml15">
					<div class="fl selTime-time w55 f18"><span id="selTimeMonthDiv"></span><span class="fr selTime-arrow mt7 ml5"></span></div>
					<select class="p-a selTime-sel w65" id="monthSelect">
						<option>01</option>
						<option>02</option>
						<option>03</option>
						<option>04</option>
						<option>05</option>
						<option>06</option>
						<option>07</option>
						<option>08</option>
						<option>09</option>
						<option>10</option>
						<option>11</option>
						<option>12</option>
					</select>
				</div>
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
			 		<a href="javascript:void(0);" class="fr btn1-no mr50" onclick="resetDriverAndCar()">取&nbsp;消</a>
			 		<a href="javascript:void(0);" class="fr btn1 white1 fw mr25" onclick="saveDriverAndCar()">保&nbsp;&nbsp;存</a>
	         		
	         	</p>
		 	</div>		 	
		</div>
		<!-- 司机排班日历end -->	
		
	</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//判断分辨率
	if($(window.parent).width()>1280){
		$(".lineTime-date").css("width","49%");
		$(".lineSelectMore").css("width",$(window.parent).width()-$(".lineTime-date").width()-310+"px");
	}else{
		$(".lineTime-date").css("width",544+"px");
		$(".lineSelectMore").css("width",$(window.parent).width()-$(".lineTime-date").width()-250+"px");
	};
	$(window).resize(function(){
		if($(window.parent).width()>1280){
		$(".lineTime-date").css("width","49%");
		$(".lineSelectMore").css("width",$(window.parent).width()-$(".lineTime-date").width()-310+"px");
		}else{
			$(".lineTime-date").css("width",544+"px");
			$(".lineSelectMore").css("width",$(window.parent).width()-$(".lineTime-date").width()-250+"px");
		};
	});
	
});
/** -----------------------------------------------------------------------------------日历相关js start --------------------------------------------------------------------------------- **/
//json数据
var jsonDate = [{
	"driverName": "张司机",
	"dateTime":"2014-08-01",
	"carNum":"粤B45897",
	"seatNum":"40",
	"id":"001"
},{
	"driverName": "张司机",
	"dateTime":"2014-08-04",
	"carNum":"粤B45897",
	"seatNum":"35",
	"id":"002"
},{
	"driverName": "张司机",
	"dateTime":"2014-08-12",
	"carNum":"粤B45897",
	"seatNum":"45",
	"id":"003"
},{
	"driverName": "张司机",
	"dateTime":"2014-08-18",
	"carNum":"粤B45897",
	"seatNum":"38",
	"id":"004"
},{
	"driverName": "张司机",
	"dateTime":"2014-08-22",
	"carNum":"粤B45897",
	"seatNum":"42",
	"id":"005"
},{
	"driverName": "张司机",
	"dateTime":"2014-08-23",
	"carNum":"粤B45897",
	"seatNum":"36",
	"id":"006"
},{
	"driverName": "张司机",
	"dateTime":"2014-09-02",
	"carNum":"粤B45897",
	"seatNum":"40",
	"id":"007"
},{
	"driverName": "张司机",
	"dateTime":"2014-09-06",
	"carNum":"粤B45897",
	"seatNum":"38",
	"id":"008"
},{
	"driverName": "张司机",
	"dateTime":"2014-09-22",
	"carNum":"粤B45897",
	"seatNum":"30",
	"id":"009"
},{
	"driverName": "张司机",
	"dateTime":"2014-10-08",
	"carNum":"粤B45897",
	"seatNum":"48",
	"id":"010"
},{
	"driverName": "张司机",
	"dateTime":"2014-10-12",
	"carNum":"粤B45897",
	"seatNum":"25",
	"id":"010"
},{
	"driverName": "张司机",
	"dateTime":"2014-10-25",
	"carNum":"粤B45897",
	"seatNum":"39",
	"id":"011"
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
	return myDate.getFullYear() + "-" + parseDate(month) + "-" + parseDate(myDate.getDate());
}

$(function(){	
	//日历切换
	$("#yearSelect,#monthSelect").change(function(){
		var yearIndex = $("#yearSelect").find("option:selected").text();
		var monthIndex = $("#monthSelect").find("option:selected").text();
		$("#selTimeYearDiv").text(yearIndex+"年");
		$("#selTimeMonthDiv").text(monthIndex+"月");
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
	$("#selTimeYearDiv").text(yearIndex+"年");
	$("#selTimeMonthDiv").text(monthIndex+"月");
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
		/*选中日期样式  select_date = on*/
		/*周末排班日期样式   week_end = tdYellowBg2*/
		/*周末未排班日期样式   noweek_end = tdGrayBg*/
		/*非周末排班日期样式    scheduling = tdYellowBg2*/
		/*非周末未排班日期样式   noscheduling = tdGrayBg*/
		/*当前系统日期样式  tdYellowBg1*/
		var $table = $("#"+id);
		var $tr = $("<tr id='"+id+"tr"+i+"'>" +
		"<td class='noweek_end tdGrayBg' valign='top' id='"+id+"date_time"+parseInt(thLength*i+1)+"' week='星期天' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw red4'><span class='fr lineTableNo' id='"+id+"date_no"+parseInt(thLength*i+1)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+1)+"'></span></p><p class='gray3 mt10 line24' id='"+id+"noweek"+parseInt(thLength*i+1)+"'>非工作日</p></div></td>"+ 
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+2)+"' week='星期一' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+2)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+2)+"'></span></p></div></td>"+   
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+3)+"' week='星期二' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+3)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+3)+"'></span></p></div></td>"+ 
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+4)+"' week='星期三' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+4)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+4)+"'></span></p></div></td>"+ 
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+5)+"' week='星期四' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+5)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+5)+"'></span></p></div></td>"+ 
		"<td class='noscheduling tdGrayBg tdBorderRight' valign='top' id='"+id+"date_time"+parseInt(thLength*i+6)+"' week='星期五' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw'><span class='fr lineTableNum gray1 f13' id='"+id+"date_no"+parseInt(thLength*i+6)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+6)+"'></span></p></div></td>"+ 
		"<td class='noweek_end tdGrayBg' valign='top' id='"+id+"date_time"+parseInt(thLength*i+7)+"' week='星期六' date='0'><div class='lineTableDate-text p-r'><p class='f22 f-arial fw red4'><span class='fr lineTableNo' id='"+id+"date_no"+parseInt(thLength*i+7)+"'></span><span id='"+id+"date_num"+parseInt(thLength*i+7)+"'></span></p><p class='gray3 mt10 line24' id='"+id+"noweek"+parseInt(thLength*i+7)+"'>非工作日</p></div></td>"+ 
		"</tr>");  
		$table.append($tr);
	} //系统显示的当前年月
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
				if(!($("#"+id+"date_time"+parseInt(m+j)).hasClass("noweek_end")))
				{
					//$("#"+id+"date_num"+parseInt(m+j)).parent("p").after("<p class='black4'>王小二</p><p class='black4'>港541234</p><span class='lineTableDate-textEdit'></span>");
					//hover事件
					$("#"+id+"date_time"+parseInt(m+j)).hover(function(){
						$(this).children("div").append("<span class='lineTableDate-textEdit' onclick='editDriverOrCar(\""+$(this).attr('date')+"\",\""+this.id+"\")'></span>").addClass("on");
					},function(){
					    $(this).children("div").find(".lineTableDate-textEdit").remove();
					    $(this).children("div").removeClass("on");
					});
				}
				for(var n = 0; n < jsonDate.length;n++)
				{
					if(jsonDate[n].dateTime == (showYear+"-"+showMonth+"-"+parseDate(m)))
					{
						$("#"+id+"date_num"+parseInt(m+preCount)).children("p").remove();
						//周末未调班的情况
						if($("#"+id+"date_time"+parseInt(m+preCount)).hasClass("noweek_end"))
						{
							$("#"+id+"noweek"+parseInt(m+preCount)).remove();
							$("#"+id+"date_time"+parseInt(m+preCount)).removeClass("noweek_end").addClass("tdYellowBg2 week_end");
							$("#"+id+"date_no"+parseInt(m+preCount)).removeClass("lineTableNo").addClass("lineTableNum gray1 f13");
							//hover事件
							$("#"+id+"date_time"+parseInt(m+preCount)).hover(function(){
								$(this).children("div").append("<span class='lineTableDate-textEdit' onclick='editDriverOrCar(\""+$(this).attr('date')+"\",\""+this.id+"\")'></span>").addClass("on");
							},function(){
							    $(this).children("div").find(".lineTableDate-textEdit").remove();
							    $(this).children("div").removeClass("on");
							});
						}
						//非周末未排版情况
						if($("#"+id+"date_time"+parseInt(m+preCount)).hasClass("noscheduling"))
						{
							$("#"+id+"date_time"+parseInt(m+preCount)).removeClass("noscheduling").addClass("tdYellowBg2 scheduling");
						}
						$("#"+id+"date_num"+parseInt(m+preCount)).parent("p").after("<p class='black4'>"+jsonDate[n].driverName+"</p><p class='black4'>"+jsonDate[n].carNum+"</p>");
						$("#"+id+"date_no"+parseInt(m+preCount)).text(jsonDate[n].seatNum);
					}
				}
				//系统当前日期 需要选中样式
				if(selectYear == currYear && dayIndex == m)
				{
					$("#"+id+"date_time"+parseInt(m+preCount)).addClass("tdYellowBg1");
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
//动态创建下拉框
function initSelectData(id)
{
	$("#"+id).find("option").remove();
	for(var i = 1;i <= 5;i++)
　　{
　　　　$("#"+id).append("<option value='"+i+"'>下拉选项"+i+"</option>");
　  }
}

//编辑司机或者车辆
function editDriverOrCar(selectDate,id)
{
	alert("选择的日期为="+selectDate+"==id="+id);
	$("#rightBigDiv").show();
	//司机列表DIV分页
	calculateMoveHeight("moveDriverListDiv","scrollDriverListBarBigDiv","scrollDriverListBarDiv");
}

/** -----------------------------------------------------------------------------------日历相关js end --------------------------------------------------------------------------------- **/

/** -----------------------------------------------------------------------------------右侧司机相关js start --------------------------------------------------------------------------------- **/
$(function(){	
	
	//选择司机大div
	$("#selectDriverIcon").click(function(){
		if($("#selectDriverBigDiv").css("display") == "none")
		{
			$("#selectDriverBigDiv").slideDown();
			$("#selectCarBigDiv").slideUp();
			$("#selectCarIcon").removeClass("lineSelectMore-ico-up").addClass("lineSelectMore-ico-down");
			$(this).removeClass("lineSelectMore-ico-down").addClass("lineSelectMore-ico-up");
			//司机列表DIV分页
			calculateMoveHeight("moveDriverListDiv","scrollDriverListBarBigDiv","scrollDriverListBarDiv");
		}
		else
		{
			$("#selectDriverBigDiv").slideUp();
			$(this).removeClass("lineSelectMore-ico-up").addClass("lineSelectMore-ico-down");
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
			//车辆列表DIV分页
			calculateMoveHeight("moveCarListDiv","scrollCarListBarBigDiv","scrollCarListBarDiv");
		}
		else
		{
			$("#selectCarBigDiv").slideUp();
			$(this).removeClass("lineSelectMore-ico-up").addClass("lineSelectMore-ico-down");
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
		oneDriverArray.push({"driverName":driverName,"driverTel":driverTel,"driverId":driverId});
		$("#"+id).text("取消选择");
	}
	alert("司机="+oneDriverArray[0].driverName);
}

//查看某个司机排班
function lookDriverClass(obj,driverName,driverTel,driverId)
{
	$("#selectDriverListDiv").hide();
	$("#selectDriverClassListDiv").show();
	$("#driverName").text(driverName);
	$("#selectThisDriver").attr("driverName",driverName);
	$("#selectThisDriver").attr("driverTel",driverTel);
	$("#selectThisDriver").attr("driverId",driverId);
	$("#selectThisDriver").attr("driverDivId",$(obj).next().attr("id"));
	setPreParameter();
	//查看某个司机排班列表DIV分页
	calculateMoveHeight("moveDriverClassListDiv","scrollDriverClassListBarBigDiv","scrollDriverClassListBarDiv");
}

//从查看某个司机排班情况返回到司机列表
function goBackDriverList()
{
	$("#selectDriverListDiv").show();
	$("#selectDriverClassListDiv").hide();
	setCurrParameter();
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
function selectOneCar(id,carName,carNo,carNumber,carId)
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
		oneCarArray.push({"carName":carName,"carNo":carNo,"carNumber":carNumber,"carId":carId});
		$("#"+id).text("取消选择");
	}
	alert("车辆="+oneCarArray[0].carName);
}

//查看某个车辆排班
function lookCarClass(obj,carName,carNo,carNumber,carId)
{
	$("#selectCarListDiv").hide();
	$("#selectCarClassListDiv").show();
	$("#carNumber").text(carNumber);
	$("#selectThisCar").attr("carName",carName);
	$("#selectThisCar").attr("carNo",carNo);
	$("#selectThisCar").attr("carNumber",carNumber);
	$("#selectThisCar").attr("carId",carId);
	$("#selectThisCar").attr("carDivId",$(obj).next().attr("id"));
	setPreParameter();
	//查看某个车辆排班列表DIV分页
	calculateMoveHeight("moveCarClassListDiv","scrollCarClassListBarBigDiv","scrollCarClassListBarDiv");
}

//从查看某个车辆排班情况返回到车辆列表
function goBackCarList()
{
	$("#selectCarListDiv").show();
	$("#selectCarClassListDiv").hide();
	setCurrParameter();
}

//选此车辆
function selectThisCar()
{
	selectOneCar($("#selectThisCar").attr("carDivId"),$("#selectThisCar").attr("carName"),$("#selectThisCar").attr("carNo"),$("#selectThisCar").attr("carNumber"),$("#selectThisCar").attr("carId"));
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
	if(oneDriverArray.length > 0)
	{
		alert("选择的司机为："+oneDriverArray[0].driverName);
	}
	if(oneCarArray.length > 0)
	{
		alert("选择的车辆为："+oneCarArray[0].carNumber);
	}
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
</script>

