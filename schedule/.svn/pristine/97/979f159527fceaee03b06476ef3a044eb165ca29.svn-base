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
<title>主页</title>
<jsp:include page="resource.jsp"/>
</head>

<body>
<!-- 遮罩层 start -->
<div id="mainBody" class="mainBody hide"></div>
<div id="showPage" class="showPage p-r hide" style="z-index:200;"></div>
<!-- 遮罩层 end -->

<!--添加或修改或删除等成功、失败提示 start-->
<div class="systips-box f13 hide" id="returnTipDiv">
	<span class="fl systips-ico mr5" id="returnIcon"></span><span id="returnTip"></span><!--成功：systips-ico-ok；失败：systips-ico-fail，感叹：systips-ico-remind-->
</div>
<!--添加或修改或删除等成功、失败提示end-->

<!--普通提示 start-->
<div class="systips-box f13 hide" id="commonTipDiv" style="max-width:200px;">
	<span id="commonTip"></span>
</div>
<!--普通提示 end-->

<!-- 通用遮罩层  例如是否删除，是否修改等提示-->
<div id="commonHide" class="hideBody hide"></div>
<!-- 通用选择提示层 start 有2个按钮 -->
<div id="commonShowPage" class="showPage p-r hide" style="z-index:200;top: 40%;left: 62%;">
 	<div class="pop" style="width:275px;">
		<div class="ml40 mt30 mr10 pop-tip f14" >
			<span class="fl systips-ico mr5 hide" id="commonIcon"></span><!--成功：systips-ico-ok；失败：systips-ico-fail，感叹：systips-ico-remind-->
			<span id="commonStatement"></span>
		</div>
	   	<p class="ml40 mt10 mb20">
		    <a id="commonSure" class="display-ib btn2 btn2-ok white1 mr40" href="javascript:void(0);">确&nbsp;定</a>
		   	<a class="display-ib btn2 btn2-no red1" id="commonCancel" href="javascript:void(0);" onclick="closePopCommonPage();">取&nbsp;消</a>
	   	</p>
    </div>   
</div>
<!-- 通用选择提示层 end 有2个按钮 -->

<!-- 通用选择提示层2 只有一个确定按钮  start -->
<div id="commonHide2" class="hideBody hide"></div>
<div id="commonShowPage2" class="showPage p-r hide" style="z-index:200;top: 40%;left: 62%;">
 	<div class="pop black1" style="width:275px;">
		<div class="ml40 mt30 mr10 pop-tip f14" >
			<span class="fl systips-ico mr5 hide" id="commonIcon2"></span><!--成功：systips-ico-ok；失败：systips-ico-fail，感叹：systips-ico-remind-->
			<span id="commonStatement2"></span>
		</div>
	   	<p class="ml40 mt10 mb20">
		   	<a class="display-ib btn2 btn2-ok white1 ml40" style="width:58px;height:26px;" id="commonCancel" href="javascript:void(0);" onclick="closePopCommonPage2();">确&nbsp;定</a>
	   	</p>
    </div>   
</div>
<!-- 通用选择提示层2 只有一个确定按钮 end -->

<!-- loding遮罩层  start-->
<div id="lodingHide" class="hideBody hide" id="lodingDiv"></div>
<div id="lodingShowPage" class="showPage p-r hide" style="z-index:200;top: 30%;left: 62%;">
 	<div class="pop black1" style="width:350px;height:90px;">
		<div class="pop-t">
	        <div class="fr mt5 mr5"><a herf="javascript:void(0)" id="closeLoding" class="fr ml15" onclick="popLodingPage(false);"><em class="display-ib fw gray2 f-arial mr4 vf1">X</em></a></div>
	    </div>
	    <div class="p10">
	   		<p id="t1" class="t-c"><img src="../images/loading.gif" width="24" height="24" class="mr10 vf8" />处理中，请稍等···</p>
	   	</div>
    </div>
</div>
<!-- loding遮罩层  end-->

<!-- 左侧div start -->
<div class="leftBar fl p-r">
	<div class="logo"></div>
	<ul class="leftMenu f-yahei f14" id="leftmenu">
    	<li class="mt11"><a href="javascript:void(0)" class="yellow2 clearfix" onclick="changePage('../homePage/showHomePage.action',this);" url="../homePage/showHomePage.action" id="leftmenua0"><span class="leftIco indexIco"></span>首页</a></li>
    	<s:iterator value="mainMenu" status="status">
        <li class="mt5"><a href="javascript:void(0)" class="yellow2 clearfix" id="leftmenua${status.index+1 }"><span class="fr arrowIco mt10"><!-- 展开arrowIco 收起arrowIco-up --></span><span class="${iconUrl}"></span>${name }</a>
        	<ul class="leftMenu-subMenu hide f12">
        		<s:iterator value="childPermission" >
            	<li class="mt10"><a href="javascript:void(0)" class="yellow2" onclick="changePage('${url}.action',this);" url="${url}.action"><span class="subIco"></span>${name }</a></li>
            	</s:iterator>
            </ul>
        </li>
        </s:iterator>
        <%--
        <li class="mt11"><span class="fr arrowIco mt10"><!-- 展开arrowIco 收起arrowIco-up --></span><a href="javascript:void(0)" class="yellow2" id="leftmenua2"><span class="leftIco payIco"></span>支付结算</a>
        	<ul class="leftMenu-subMenu hide">
            	<li class="mt11"><a href="javascript:void(0)" class="yellow2" onclick="changePage('../statDayIncomeAction/getDayIncome.action',this);" url="../statDayIncomeAction/getDayIncome.action"><span class="subIco"></span>日收入统计</a></li>
                <li class="mt11"><a href="javascript:void(0)" class="yellow2" onclick="changePage('../statMonthIncomeAction/getMonthIncome.action',this);" url="../statMonthIncomeAction/getMonthIncome.action"><span class="subIco"></span>月收入统计</a></li>
            </ul>
        </li>
        <li class="mt11"><span class="fr arrowIco mt10"><!-- 展开arrowIco 收起arrowIco-up --></span><a href="javascript:void(0)" class="yellow2" id="leftmenua3"><span class="leftIco dirverIco"></span>司机管理</a>
        	<ul class="leftMenu-subMenu hide">
            	<li class="mt11"><a href="javascript:void(0)" class="yellow2" onclick="changePage('../dispatchDriver/driverInfoList.action',this);" url="../dispatchDriver/driverInfoList.action"><span class="subIco"></span>司机列表</a></li>
                <li class="mt11"><a href="javascript:void(0)" class="yellow2" onclick="changePage('../dispatchDriver/toDriverEditPage.action',this);" url="../dispatchDriver/toDriverEditPage.action"><span class="subIco"></span>添加司机</a></li>
            </ul>
        </li>
        <li class="mt11"><span class="fr arrowIco mt10"><!-- 展开arrowIco 收起arrowIco-up --></span><a href="javascript:void(0)" class="yellow2" id="leftmenua4"><span class="leftIco carIco"></span>车辆管理</a>
        	<ul class="leftMenu-subMenu hide">
            	<li class="mt11"><a href="javascript:void(0)" class="yellow2" onclick="changePage('../dispatchVehicle/vehicleInfoList.action',this);" url="../dispatchVehicle/vehicleInfoList.action"><span class="subIco"></span>车辆列表</a></li>
                <li class="mt11"><a href="javascript:void(0)" class="yellow2" onclick="changePage('../dispatchVehicle/toVehicleEditPage.action',this);" url="../dispatchVehicle/toVehicleEditPage.action"><span class="subIco"></span>添加车辆</a></li>
            </ul>
        </li>
        <li class="mt11"><span class="fr arrowIco mt10"><!-- 展开arrowIco 收起arrowIco-up --></span><a href="javascript:void(0)" class="yellow2" id="leftmenua5"><span class="leftIco setIco"></span>系统设置</a>
        	<ul class="leftMenu-subMenu hide">
                <li class="mt11"><a href="javascript:void(0)" class="yellow2" onclick="changePage('../personalInfo/show.action',this);" url="../personalInfo/show.action"><span class="subIco"></span>个人信息</a></li>
                <li class="mt11"><a href="javascript:void(0)" class="yellow2" onclick="changePage('../changePassWord/toChangePassWord.action',this);" url="../changePassWord/toChangePassWord.action"><span class="subIco"></span>修改密码</a></li>
            </ul>
        </li>
    --%></ul>
</div>	
<!-- 左侧div start -->  

<!-- 右侧div start -->
	<!-- 顶部div start -->
	<div class="top">
		<a href="javascript:void(0)" class="black5" onclick="openLeftMenu('../personalInfo/show.action');"><span class="welIco"></span><s:property value="#session.userInfo.userName"/></a><em class="gray1 ml10">[<s:property value="#session.userInfo.brefName"/>]</em><em class="gray1 ml30">欢迎登录调度系统</em><a href="javascript:void(0)" class="yellow5 ml10" onclick="toLoginOut();">退出</a>
	    <span class="fr black5"><em class="telIco"></em>客服热线：<em class="fw f16 f-arial yellow6 vf2">400-168-1866</em></span>
	</div>
	<!-- 顶部div end -->
<div class="rightBar">
	<!-- 右侧iframe -->
	<iframe frameborder="0" id="iframe_right" name="iframe_right" class="right-iframe" src="../homePage/showHomePage.action" width="100%" height="100%"></iframe>
	<div class="footer">
		<em class="f-arial">Copyright&nbsp;&copy;&nbsp;</em>深圳市小猪巴适科技有限公司&nbsp;<a href="http://www.miitbeian.gov.cn" target="_blank" class="black2">粤ICP备14029252号-2</a><em class="ml30">服务热线：400-168-1866 </em>
	</div>
</div>
<!-- 右侧div start --> 
   
</body>
</html>
<script type="text/javascript">
//初始化方法
$(function(){
	//左侧菜单/右侧iframe的高度
	$(".leftBar").css("height",$(window).height()+"px");
	$(".right-iframe").css("height",$(window).height()-66+"px");
	$(window).resize(function(){
		$(".leftBar").css("height",$(window).height()+"px");
		$(".right-iframe").css("height",$(window).height()-66+"px");	
	});
	//加载左侧菜单点击事件
	initMenu();
});

//加载左侧菜单点击事件
function initMenu() {
  //每一个父菜单点击事件,展开arrowIco-down 收起arrowIco-up
  $("#leftmenu li a").click(function() {
	   //$(this).next() 指当前节点下一个节点 即ul
	   var checkElement = $(this).next();
	   if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
		   //去除当前a标签的arrowIco-up样式
		   $(this).prev().removeClass("arrowIco-up");
		   //将当前的ul关闭
		   checkElement.slideUp();
	   }
	   if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
		   //当前节点#leftmenu li a的父节点为li，然后其同级节点都是li，li后找到a标签，即去除同级li标签中的a标签已经添加过arrowIco-up样式
		   $(this).parent().siblings().find("span").removeClass("arrowIco-up");
		   //将之前展开的ul关闭
		   $('#leftmenu ul:visible').slideUp('normal');
		   //将当前点击的ul展开
		   checkElement.slideDown();
		   //增加当前a标签的arrowIco-up样式
		   $(this).prev().addClass("arrowIco-up");
		}
	});
}	

//点击左侧菜单，右侧ifream刷新
function changePage(url,obj){
	//首页
	if(url == "../homePage/showHomePage.action")
	{
		//将之前展开的ul关闭
		$('#leftmenu ul:visible').slideUp();
	}
	//右侧ifream刷新
	$("#iframe_right").attr("src",url); 
	$("#iframe_right").attr("url",url);
	$(".leftMenu-subMenu li a").removeClass("active");
	$(obj).addClass("active"); 
}

//展开左侧菜单
/*
参数说明：
*index:父菜单下标
*currIndex:父菜单下某个子菜单的下标
*/
function openLeftMenu(url)
{
	var aLength = $("#leftmenu li a").length;
	//隐藏之前点击过的菜单
	$("#leftmenu ul").hide();
	//去除左侧父级菜单上次点击过的active样式
	$("#leftmenu li a").removeClass("active");
	for(var  i = 0;i < aLength;i++)
	{      
		 if(url == $("#leftmenu li a").eq(i).attr("url"))
		 {
			  //点击顶部菜单，左侧父菜单，增加active样式	
			  $("#leftmenu li a").eq(i).parent("li").parent("ul").prev("a").next().show();
			  $("#leftmenu li a").eq(i).addClass("active");
			  $('#iframe_right').attr('src',url);
		 }
	}
}

//关闭通用选择弹出层
function closePopCommonPage()
{
	$("#commonStatement").text("");
	$("#commonHide").hide();
	$("#commonIcon").removeClass("systips-ico-ok systips-ico-fail systips-ico-remind");
    $("#commonShowPage").hide();
}

//确定或者取消弹窗   有2个按钮
/**
           确定或者取消弹窗    有2个按钮
    tip 表示提示语句 （tip根据需求自己填写）
    showFlag=true表示显示图标，showFlag=false表示隐藏图标
    iconFlag=true表示成功，false表示失败，remind表示感叹提醒,none表示无图标
    showPopCommonPage(popTitle,showFlag,iconFlag)
**/
function showPopCommonPage(popTitle,showFlag,iconFlag)
{
	$("#commonSure").text("确定");
	$("#commonCancel").text("取消");
	if(showFlag == "true")
	{
		if(iconFlag == "true")
		{
			$("#commonIcon").removeClass("systips-ico-fail systips-ico-remind").addClass("systips-ico-ok").show(); //成功打钩
		}
		else if(iconFlag == "remind")
		{
		    $("#commonIcon").removeClass("systips-ico-ok systips-ico-fail").addClass("systips-ico-remind").show(); //感叹提醒
		}
		else
		{
		    $("#commonIcon").removeClass("systips-ico-ok systips-ico-remind").addClass("systips-ico-fail").show(); //失败打叉
		}
	}
	else
	{
		$("#commonIcon").hide();
	}
	$("#commonHide").show();
    $("#commonShowPage").show();
    //弹出层的标题
    $("#commonStatement").text(popTitle);
}

//关闭通用选择弹出层2
function closePopCommonPage2()
{
	$("#commonStatement2").text("");
	$("#commonHide2").hide();
	$("#commonIcon2").removeClass("systips-ico-ok systips-ico-fail systips-ico-remind");
    $("#commonShowPage2").hide();
}


//确定弹窗   只有一个按钮
/**
           确定按钮弹窗  只有一个按钮
    tip 表示提示语句 （tip根据需求自己填写）
    showFlag=true表示显示图标，showFlag=false表示隐藏图标
    iconFlag=true表示成功，false表示失败，remind表示感叹提醒,none表示无图标
    showPopCommonPage(popTitle,showFlag,iconFlag)
**/
function showPopCommonPage2(popTitle,showFlag,iconFlag)
{
	if(showFlag == "true")
	{
		if(iconFlag == "true")
		{
			$("#commonIcon2").removeClass("systips-ico-fail systips-ico-remind").addClass("systips-ico-ok").show(); //成功打钩
		}
		else if(iconFlag == "remind")
		{
		    $("#commonIcon2").removeClass("systips-ico-ok systips-ico-fail").addClass("systips-ico-remind").show(); //感叹提醒
		}
		else
		{
		    $("#commonIcon2").removeClass("systips-ico-ok systips-ico-remind").addClass("systips-ico-fail").show(); //失败打叉
		}
	}
	else
	{
		$("#commonIcon2").hide();
	}
	$("#commonHide2").show();
    $("#commonShowPage2").show();
    //弹出层的标题
    $("#commonStatement2").text(popTitle);
}

/**
          加载loding页面
    falg=true表示显示，falg=false表示隐藏
    popLodingPage(flag)
**/
function popLodingPage(flag){
	if(flag){
		$("#lodingHide").show();
    	$("#lodingShowPage").show();
	}else{
		$("#lodingHide").hide();
    	$("#lodingShowPage").hide();
	}
}

/**
          展示带图标的提示语句  
    tip 表示提示语句   
    falg=true表示成功，false表示失败，remind表示感叹提醒
    showRturnTip(tip,flag)
**/
function showRturnTip(tip,flag){
	if(flag == "true")
	{
		$("#returnIcon").removeClass("systips-ico-ok systips-ico-fail systips-ico-remind").addClass("systips-ico-ok"); //成功打钩
	}
	else if(flag == "remind")
	{
	    $("#returnIcon").removeClass("systips-ico-ok systips-ico-fail systips-ico-remind").addClass("systips-ico-remind"); //感叹提醒
	}
	else
	{
	    $("#returnIcon").removeClass("systips-ico-ok systips-ico-fail systips-ico-remind").addClass("systips-ico-fail"); //失败打叉
	}
	$("#returnTip").text(tip);
	$("#returnTipDiv").fadeIn();	
	setTimeout("closeRturnTip();",3000);
}

//关闭提示语句
function closeRturnTip(){
	
	$("#returnTipDiv").fadeOut("fast",function(){
		$("#returnTip").text("");
		$("#returnIcon").removeClass("systips-ico-ok systips-ico-fail systips-ico-remind");
	});
}

/**
   	展示不带图标的提示语句  
    tip 表示提示语句   
    showCommonTip(tip,flag)
**/
function showCommonTip(tip){
	$("#commonTip").text(tip);
	$("#commonTipDiv").fadeIn();
	setTimeout("closeCommonTip();",2000);
}

//关闭常用提示语句
function closeCommonTip(){
	$("#commonTipDiv").fadeOut("fast",function(){
		$("#commonTip").text("");
	});
}

//退出登录
function toLoginOut(){
	showPopCommonPage("确定要退出吗？","false","none");
	//解绑定，防止多次执行click事件
	$("#commonSure").unbind('click');
	$("#commonSure").click(function(){
		//解绑定，防止多次执行click事件
		$("#commonSure").unbind('click');
		//退出
		window.location.href="../login/toLoginOut.action";
	});

}

//关闭弹出层页面
function closeViewPrice()
{
    $("#showPage").children().remove();
    $("#mainBody,#showPage").hide();
}
</script>