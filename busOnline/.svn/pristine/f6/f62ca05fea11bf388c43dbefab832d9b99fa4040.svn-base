<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../v1/template/headMeta.jsp" %>
<link rel="stylesheet" type="text/css" href="../css/beatboss/beatboss.css">
<style>
	body{background:inherit;}
</style>
</head>
<body class="bg-index" style="padding:0 !important">
	<div class="main bg-1">  
		<div class="desc">
			<p>2015年10月17日是<span class="big">世界消除贫困日</span></p>
			<p>万恶的房东又要<span class="big">涨房租</span></p>
			<p>租房的人民站起来</p>
		</div>
		
		<div class="btn-start"><img alt="" src="../images/games/beatboss/btn_start.png"></div>

	</div>
</body>
</html>
<%@include file="../v1/template/footJavascript.jsp" %>
<script type="text/javascript" src="http://img.pig84.com/download/100000000011"></script>
<script type="text/javascript" src="../js/common/common_util.js"></script>
<script src="../js/beatboss/jquery.touch.min.js"></script>
<script type="text/javascript">
var openId = '${openId}';

$(".bg-1").css({"height":$(window).height()});

$('.btn-start').tap(function () {
	check();
})

function check(){
	var url = "../beatboss/available?openId="+openId;
	leaseGetAjax(url,"text",function(data){
		if(data=="available"){
			document.location.href="../beatboss/game?openId="+openId;
		}else if(data=="overtime"){
			alert("活动已截止");
		}else if(data=="overnum"){
			document.location.href="../beatboss/result?openId="+openId;
		}
	});
}
</script>
<jsp:include page="wxShare.jsp"></jsp:include>