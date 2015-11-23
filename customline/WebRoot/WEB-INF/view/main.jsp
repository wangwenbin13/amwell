<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<meta name="Description" content="" />
<title>首页</title>
<jsp:include page="resource.jsp"/>
</head>

<body>
<!--删除或修改回添加等成功、失败提示 start-->
<div class="systips-box f13 fw hide" id="returnTipDiv">
	<span class="fl systips-ico mr5" id="returnIcon"></span><span id="returnTip"></span><!--删除失败只需替换systips-ico-ok为systips-ico-file-->
</div>
<!--删除或修改回添加等成功、失败提示 end-->

<!--删除或修改回添加等成功、失败提示 start-->
<div class="systips-box f13 fw hide" id="returnTipDivLong">
	<span class="fl systips-ico mr5" id="returnIconLong"></span><span id="returnTipLong"></span><!--删除失败只需替换systips-ico-ok为systips-ico-file-->
</div>
<!--删除或修改回添加等成功、失败提示 end-->

<!--普通提示 start-->
<div class="systips-box f13 fw hide" id="commonTipDiv" style="max-width:200px;">
	<span id="commonTip"></span>
</div>
<!--普通提示 end-->

<!-- 通用遮罩层  例如是否删除，是否修改等提示-->
<div id="commonHide" class="mainBody p-r hide" style="z-index:100;width:100%;left:0;"></div>
<!-- 通用选择提示层 start -->
<div id="commonShowPage" class="showPage p-r hide" style="z-index:200;top: 40%;left: 62%;">
 	<div class="pop black1" style="width:500px;height:150px;background: #fff;">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white" id="commonPopTitle">提示</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" class="pop-close fr mt4 mr4" onclick="closePopCommonPage();"></a></div>
	    </div>
	    <div class="pop-main p10">
	   		<p class="pop-main-tips" id="commonStatement"></p>
	   	</div>
	   	<p class="t-c mt10 mb20">
		    <a id="commonSure" class="display-ib btn1 white mr40 f14" href="javascript:void(0);">确&nbsp;定</a>
		   	<a class="display-ib btn1 white f14" id="commonCancel" href="javascript:void(0);" onclick="closePopCommonPage();">取&nbsp;消</a>
	   	</p>
    </div>   
</div>
<!-- 通用选择提示层 end -->

<!-- 通用选择提示层2 start -->
<div id="commonHide2" class="mainBody p-r hide" style="z-index:100;width:100%;left:0;"></div>
<div id="commonShowPage2" class="showPage p-r hide" style="z-index:200;top: 40%;left: 62%;">
 	<div class="pop black1" style="width:500px;height:150px;background: #fff;">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white" id="commonPopTitle2">提示</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" class="pop-close fr mt4 mr4" onclick="closePopCommonPage2();"></a></div>
	    </div>
	    <div class="pop-main p10">
	   		<p class="pop-main-tips" id="commonStatement2"></p>
	   	</div>
	   	<p class="t-c mt10 mb20">
		   	<a class="display-ib btn1 white f14" id="commonCancel" href="javascript:void(0);" onclick="closePopCommonPage2();">确&nbsp;定</a>
	   	</p>
    </div>   
</div>
<!-- 通用选择提示层2 end -->

<!-- loding遮罩层  start-->
<div id="lodingHide" class="mainBody p-r" id="lodingDiv" style="z-index:100;left:0;width:100%;height:100%;"></div>
<div id="lodingShowPage" class="showPage p-r hide" style="z-index:200;top: 30%;left: 62%;">
 	<div class="pop black1" style="width:438px;height:90px;background: #fff;">
		<div class="pop-t">
	        <div class="pop-t-r fr"><a href="javascript:void(0);" id="closeLoding" class="pop-close fr mt4 mr4" onclick="popLodingPage(false);"></a></div>
	    </div>
	    <div class="p10">
	   		<p id="t1" class="t-c"><img src="../images/loading.gif" width="24" height="24" class="mr10 vf8" />处理中，请稍等···</p>
	   	</div>
    </div>
</div>
<!-- loding遮罩层  end-->

<!-- 加载loding遮罩层  start-->
<div id="lodeShowPage" class="showPage p-r hide" style="z-index:200;top:30%;left:75%;">
 	<div class="systips-box w157">
	   		<p class="t-c"><img src="../images/loading.gif" width="24" height="24" class="mr10 vf8" />处理中，请稍等···</p>
	</div>
</div>
<!-- 加载loding遮罩层  end-->

<div class="main clearfix">
     <!-- 顶部遮罩层 -->
     <div id="topHide" class="hideBody"></div>
     <div class="top" id="topDiv">
        <div class="top-left"><div class="logo mt10 ml10">
        <img src="../images/logo_other.png"/>
        </div></div>
        <div class="top-nav">
        	<div class="fr top-tool mt30">
             
                <span class="fl top-tool-ico top-tool-per"></span><a href="javascript:void(0);" class="white fl mr10" onclick="openTowMenu('../changePassWord/personInfo.action?level=two');$('#iframe_right').attr('src','../changePassWord/personInfo.action?level=two');">${session.userInfo.userName }</a><em class="fl white mr20">欢迎您登录运营平台！ </em> 
              	 <s:if test="#request.iShow == 'yes'">
              	 	<span class="fl icon set mr5"></span>
              	 	<a href="javascript:void(0);" onclick="openTowMenu('${rightSysUrl}.action?level=two');$('#iframe_right').attr('src','${rightSysUrl}.action?level=two');" class="white fl mr10">系统设置</a>
              	 </s:if>
              	 
                 <!-- <a  href="../../customline/batch/toSplitStation.action" class="white fl" target="_block">拆分站点</a>-->
              	 <span class="fl icon set mr5"></span><a style="display:none;" href="../../customline/publishLine/batchLineSplit.action" class="white fl" target="_block">批量插入拆分表</a>
                <span class="fl top-tool-ico top-tool-quite mr5" onclick="toLoginOut();"></span><a href="javascript:void(0);" class="white fl" onclick="toLoginOut();">退出</a>
            </div>
        </div>
    </div>
    
    <div class="left fl p-r" id="leftDiv">
    	<a href="javascript:void(0);" class="close-left" id="closeLeftBtn"><span class="fl close-left-ico close-left-ico1"><!--收缩：close-left-ico1，展开：close-left-ico2--></span></a> 
    	<!-- 左侧遮罩层 -->
    	<div id="leftHide" class="hideBody" style="top:2px;"></div>
    	<iframe frameborder="0" scrolling="no" id="leftIframe" name="leftIframe" class="left-iframe" src="../changePassWord/toLeft.action" width="100%" height="100%"></iframe>
    </div>
    <div class="right" id="rightDiv">
   	<iframe frameborder="0" id="iframe_right" name="iframe_right" class="right-iframe" src="${rightDefaultUrl }.action?level=two" width="100%" height="100%"></iframe>
    </div>
</div>
<div class="footer" id="footDiv"><span class="footer-left"></span><span class="f-arial">Copyright&nbsp;&copy;&nbsp;2013-2015&nbsp;</span>
小猪巴适&nbsp;&nbsp;服务热线：0755-61139168 
<a href="http://www.miitbeian.gov.cn" target="_blank" class="ml4 white">粤ICP备14029252号-2</a>
	
</div>
<!--<iframe id="targetFrame" src="../login/keepLogin.action" frameborder="0" height="0" width="0" style="display: none;"></iframe>-->
</body>
</html>
<script type="text/javascript">
//关闭右侧弹出层
function closePopRightPage()
{
	$("#popTitle").text("");
    $("#popTip").text("");
	$("#rightHide").hide();
    $("#rightShowPage").hide();
}
//展示右侧弹出层
function showPopRightPage(popTitle,popTip)
{
	$("#rightHide").show();
    $("#rightShowPage").show();
    //弹出层的标题
    $("#popTitle").text(popTitle);
    //弹出层的提示内容
    $("#popTip").text(popTip);
}

//关闭通用选择弹出层
function closePopCommonPage()
{
	$("#commonStatement").text("");
	$("#commonHide").hide();
    $("#commonShowPage").hide();
}

//展示通用选择弹出层
function showPopCommonPage(popTitle)
{
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
    $("#commonShowPage2").hide();
}

//展示通用选择弹出层2
function showPopCommonPage2(popTitle)
{
	$("#commonHide2").show();
    $("#commonShowPage2").show();
    //弹出层的标题
    $("#commonStatement2").text(popTitle);
}

//数据加载loding页面
function popLodeShowPage(flag){
	if(flag){
    	$("#lodeShowPage").show();
	}else{
    	$("#lodeShowPage").hide();
	}
}
//加载loding页面
function popLodingPage(flag){
	if(flag){
		$("#lodingHide").show();
    	$("#lodingShowPage").show();
	}else{
		$("#lodingHide").hide();
    	$("#lodingShowPage").hide();
	}
}
//展示提示语句
//tip 表示提示语句   falg=true表示成功，false表示失败
function showRturnTip(tip,flag){
	if(flag)
	{
		$("#returnIcon").removeClass("systips-ico-file").addClass("systips-ico-ok"); //成功
	}
	else
	{
	    $("#returnIcon").removeClass("systips-ico-ok").addClass("systips-ico-file"); //失败
	}
	$("#returnTip").text(tip);
	$("#returnTipDiv").fadeIn();	
	setTimeout("closeRturnTip();",2000);
}

//关闭提示语句
function closeRturnTip(){
	
	$("#returnTipDiv").fadeOut("fast",function(){
		$("#returnTip").text("");
	});
}

//展示提示语句
//tip 表示提示语句   falg=true表示成功，false表示失败
function showRturnTipLong(tip,flag){
	if(flag)
	{
		$("#returnIconLong").removeClass("systips-ico-file").addClass("systips-ico-ok"); //成功
	}
	else
	{
	    $("#returnIconLong").removeClass("systips-ico-ok").addClass("systips-ico-file"); //失败
	}
	$("#returnTipLong").text(tip);
	$("#returnTipDivLong").fadeIn();	
	setTimeout("closeRturnTipLong();",3000);
}

//关闭提示语句
function closeRturnTipLong(){
	$("#returnTipDivLong").fadeOut("fast",function(){
		$("#returnTipLong").text("");
	});
}

//展示常用提示语句
//tip 表示提示语句
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



function goDiffPages(index)
{
	if(index == 0)
	{
		$("#tool-info-box").hide();
		parent.parent.changePopLeftMeun("-1","106","106001","../user/userInfo"); //个人信息
	}
	else if(index == 1)
	{
		$("#tool-info-box").hide();
		parent.parent.changePopLeftMeun("-1","106","106006","../userToRole/toUpdatePassword"); //修改密码
	}
}                       

$(function(){
	var $div_li =$(".table-title ul li");
	$div_li.click(function(){
		$(this).addClass("on").siblings().removeClass("on"); 
		var index =  $div_li.index(this); 
		$("div.tab_box > div").eq(index).show().siblings().hide();
	});
	
	showHideLeftMenu();
});
$(function(){
	//浏览器可视窗口的的高度
	$(".right").css("height",($(window).height()-136+"px"));
	$(".right-iframe").css("height",($(window).height()-140+"px"));
	$(".left,.left-iframe").css("height",($(window).height()-125+"px"));
	$("#topHide").css("height","101px");
	$("#leftHide").css("height",($(window).height()-136+"px"));
	$("#leftHide").css("width",("205px"));
	$(".right-tree-list").css("height",($(window).height()-212+"px"));	
	$("#rightHide,#commonHide").css("height",window.document.body.offsetHeight+"px");	
	$(window).resize(function() {
		$(".right").css("height",($(window).height()-136+"px"));
		$(".right-iframe").css("height",($(window).height()-140+"px"));
		$(".left,.left-iframe").css("height",($(window).height()-125+"px"));
		$("#topHide").css("height","101px");
		$("#leftHide").css("height",($(window).height()-136+"px"));
		$("#leftHide").css("width",("205px"));
		$(".right-tree-list").css("height",($(window).height()-212+"px"));	
		$("#rightHide,#commonHide").css("height",window.document.body.offsetHeight+"px");
	});
	  
   // keepLogin();
});


//显示或者隐藏左侧菜单
function showHideLeftMenu()
{
    
    $("#closeLeftBtn").toggle(
	  function(){
	  	//360浏览器不支持width为0px
	      $("#leftDiv").animate({
			width:'1px'
		  },"fast");
		  $("#closeLeftBtn").animate({
			left:'0px'
		  },"fast",function(){
		  	  $(this).find("span").removeClass("close-left-ico1").addClass("close-left-ico2");
		  	  $("#leftHide").css("width","16px");
		  });
	  },
	  function(){
	      $("#leftDiv").animate({
			width:'190px'
		  },"fast");
		  $("#closeLeftBtn").animate({
			left:'190px'
		  },"fast",function(){
		  	  $(this).find("span").removeClass("close-left-ico2").addClass("close-left-ico1");
		  	  $("#leftHide").css("width","205px");
		  });
	  }
	);
}
//退出登录
function toLoginOut(){
	showPopCommonPage("确定要退出吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure").unbind('click');
	$("#commonSure").click(function(){
			//解绑定，防止多次执行click事件
			$("#commonSure").unbind('click');
			//退出
			window.location.href="../login/toLoginOut.action";
		});
}
//线路列表,系统设置
function openTowMenu(url)
{
	  var aLength = $($("#leftIframe").contents()).find("#leftmenu li a").length;
	  //隐藏之前点击过的菜单
	  $($("#leftIframe").contents()).find("#leftmenu ul").hide();
	  //去除左侧父级菜单上次点击过的active样式
	  $($("#leftIframe").contents()).find("#leftmenu li a").removeClass("active");
	  //去除之前选中的左侧子菜单样式
	  $($("#leftIframe").contents()).find("#leftmenu ul li").removeClass("hover");
	  $($("#leftIframe").contents()).find("#leftmenu ul li").removeClass("cur");
	  for(var  i = 0;i < aLength;i++)
	  {      
		  if(url == $($("#leftIframe").contents()).find("#leftmenu li a").eq(i).attr("url"))
		  {
			  //点击顶部菜单，左侧父菜单，增加active样式	
			  $($("#leftIframe").contents()).find("#leftmenu li a").eq(i).parent("li").parent("ul").prev("a").next().show();
			  $($("#leftIframe").contents()).find("#leftmenu li a").eq(i).parent("li").parent("ul").prev("a").addClass("active");
			  //点击顶部菜单，左侧相对应子菜單顯示選中
			  $($("#leftIframe").contents()).find("#leftmenu li a").eq(i).parent("li").addClass("cur").siblings().removeClass("cur");
		  }
	  }
}

//维持登录

//function keepLogin(){
	//$('#targetFrame').attr('src', $('#targetFrame').attr('src'));
	//setInterval(keepLogin, 300000);
//}


</script>