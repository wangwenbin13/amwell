<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统维护中</title>
<style type="text/css">
body,div,p,a{
	margin:0;
	padding:0;
	}
body{
	font-size:12px;
	background:url(../images/404_b.jpg) center bottom repeat-x #e1eff9;
	}
.logo{
	display:block;
	width:179px;
	height:78px;
	margin:50px 0 0 55px;
	background:url(../images/logo.png) no-repeat;
	}
.error-box{
	width:650px;
	height:250px;
	position:absolute;
	line-height:25px;
	background:#fff;
	border:1px solid #cddfec;
	border-radius:3px;
	box-shadow:1px 1px #d7e6f1;
	text-align:center;
	}
.fw{font-weight:bold;}
.f23{font-size:23px;}
.black1{color:#696969;}
.mt20{margin-top:20px}
</style>
</head>
<body>
	<div class="logo"></div>
    <div class="error-box black1">
    	<p class="mt20"><img src='../images/noDate.png'/></p>
    	<p class="fw f23 mt20 ml65">网站系统升级维护中,给大家带来的不便敬请谅解!</p>
    </div>
</body>
</html>
<script src="../js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
$(function(){
//浏览器可视窗口的的高度
function resizeW(){
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
}
resizeW();	
})

</script>