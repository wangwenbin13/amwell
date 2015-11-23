<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="pragma" content="no-cache"> 
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0"> 
	<meta charset="UTF-8">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no" name="format-detection">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>上班者联盟-首页</title>
	<link rel="stylesheet" type="text/css" href="../css/fcz.css">
</head>
<body>
	<article>
		<div class="main">
			<div class="f0 p-r"><img src="../images/fczimages/indexTopBg1.jpg"><span class="logo"></span></div>
			<div class="f0"><img src="../images/fczimages/indexTopBg2.jpg"></div>
			<div class="p-r f0 hidden">
				<img src="../images/fczimages/indexTopBg3.jpg">
				<div class="startBox">
                	<div class="stratBtnIco"></div>
                    <a href="javascript:gameStart();" class="stratBtn"></a>
                    <span class="gameRool">游戏规则</span>
                    <span class="gameRoolIcon"></span>
                </div>
			</div>
            <section class="activeTips">
            	<h2 class="tTitle">活动规则</h2>
                <p>1、用户在10秒内对BOSS进行殴打参与游戏，每位用户有2次免费游戏机会；</p>
                <p>2、注册小猪巴士可获得一次额外的殴打机会；用户可邀请 （分享链接）好友联盟殴打，每位好友仅可帮忙1次，与此同 时用户可增加1次自己殴打机会；</p>
                <p>3、成绩达到相应的拳数即可兑换奖品，200拳=小猪巴士50 元代金券，1500拳=30元手机话费 ，3000拳=《复仇者联盟2》 手办一个； </p>
                <p>4、“小猪巴士”官方微信将在活动结束后一周内公布兑奖名单及发放方式。</p>
                <p>5、活动时间：截至2015年6月16日</p>
                <p>6、本活动最终解释权归深圳市小猪巴适科技有限公司所有</p>
            </section>
		</div>
	</article>
	<form id="indexForm" action="../fcz/gameStart" method="post">
	    <input type="hidden" name="type" value="${type}"/>
	    <input type="hidden" name="ownerOpenId" value="${ownerOpenId}"/>
	    <input type="hidden" name="helperOpenId" value="${helperOpenId}"/>
	    <input type="hidden" name="nickname" value="${nickname}"/>
	    <input type="hidden" name="photoImage" value="${photoImage}"/>
	</form>
</body>
</html>
<script type="text/javascript" src="../js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/common/common_util.js"></script>
<script type="text/javascript">
var type = "${type}";
var isAvailableDate = "${isAvailableDate}";
$(document).ready(function(){
	if(isAvailableDate=="true"){
		if(type!=null&&type!=""&&type!=undefined&&type=="1"){
			$("#indexForm").submit();
	    }
	}else{
		$(".stratBtn").attr("href","javascript:('活动已结束！');");
	}
});

function gameStart(){
	$("#indexForm").submit();
}

</script>