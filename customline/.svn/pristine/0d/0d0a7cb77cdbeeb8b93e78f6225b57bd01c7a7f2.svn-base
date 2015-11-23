<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>404</title>
<style type="text/css">
body,div,p,a{
	margin:0;
	padding:0;
	}
a{text-decoration:none;}
a:hover{text-decoration:underline}
body{
	font-size:12px;
	background:url(../images/404_b.jpg) center bottom repeat-x #e1eff9;
	}
.logo{
	display:block;
	width:250px;
	height:62px;
	margin:50px 0 0 55px;
	background:url(../images/404_logo.png) no-repeat
	}
.error-box{
	width:780px;
	height:310px;
	position:absolute;
	line-height:25px;
	background:url(../images/404.png) 470px center no-repeat #fff;
	border:1px solid #cddfec;
	border-radius:3px;
	box-shadow:1px 1px #d7e6f1;
	}
.btn1{
	display:inline-block;
	height:31px;
	width:101px;
	text-align:center;
	line-height:31px;
	color:#fff;
	margin-right:30px;
	background:url(../images/ele.png) -190px -386px no-repeat;
	}
.btn1:hover{
	text-decoration:none;
	background:url(../images/ele.png) right -386px no-repeat;
	}
.fw{font-weight:bold;}
.f14{font-size:14px;}
.f23{font-size:23px;}
.black1{color:#696969;}
.blue1{color:#0093dd}
.mt40{margin-top:40px}
.mt50{margin-top:50px}
.mt60{margin-top:60px}
.ml65{margin-left:65px;}
</style>
</head>
<body>
	<div class="logo"></div>
    <div class="error-box black1">
    	<p class="fw f23 mt60 ml65">糟糕，出现错误了！</p>
        <p class="f14 mt40 ml65">没有找到您要访问的页面，或您的登录状态已经失效,</p>
        <p class="f14 ml65">您可以返回首页或重新登录。</p>
        <p class="mt50 ml65"><a href="../orderInfoList/overTimeList" class="btn1 f14 mr30">返回首页</a><a href="javascript:window.history.go(-1);" class="blue1">返回上一页</a></p>
    </div>
</body>
</html>
<script src="../js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
$(function(){
//浏览器可视窗口的的高度
	var W = $(window).width();
	var H = $(window).height();
	var boxW = $(".error-box").width();
	var boxH = $(".error-box").height();
	$("body").css("height",($(window).height()-50+"px"));
	$(".error-box").css({"left":(W-boxW)/2+"px","top":(H-boxH)/2+"px"});
	$(window).resize(function() {
		$("body").css("height",($(window).height()-50+"px"));
		$(".error-box").css({"left":(W-boxW)/2+"px","top":(H-boxH)/2+"px"});
	});  
})
</script>