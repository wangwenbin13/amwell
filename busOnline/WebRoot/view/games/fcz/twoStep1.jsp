<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no" name="format-detection">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>上班者联盟-开始游戏</title>
	<link rel="stylesheet" type="text/css" href="../../css/fcz.css">
    <script type="text/javascript" src="../../js/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="../../js/jquery/fastclick.js"></script>

</head>
<body class="greenBg">
	<article>
		<div class="main">
			<div class="hand"></div>
            <p><span class="num">3</span><!--  <span class="go"></span> --></p>
            <div class="boss p-r"><span class="bang"></span></div>
            <div class="countDiv">
                <div class="count clearfix">
                    <span class="countNum fist" id="click_num">0</span>
                    <span class="countNum clock" id="timeCount">10</span>  
                </div>
            </div>
		</div>
	</article>
</body>
<script type="text/javascript">
window.addEventListener('load', function() {
    FastClick.attach(document.body);
}, false);
var i= 0;
$(function(){
	gameTime();
    $(".main").on("touchend",function(e){
         $(".hand").stop(!0,!1).animate({"top":184+"px"},30,function(){
                    $(".bang").show();
                $(".hand").stop(!0,!1).animate({"top":0+"px"},30,function(){
                    $(".bang").hide();
                });
             });
         e.preventDefault();
         i++;
         $("#click_num").html(i);
    })
})
var j = 13;
function gameTime(){
	$("#timeCount").val(j);
	j=j-1;
	if(j>-1){
		setTimeout("gameTime()",1000);
	}
}

</script>
</html>
