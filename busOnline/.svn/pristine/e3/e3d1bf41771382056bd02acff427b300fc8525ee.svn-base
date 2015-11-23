<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../v1/template/headMeta.jsp"%>
<link rel="stylesheet" type="text/css" href="../css/beatboss/beatboss.css">
</head>
<body style="padding:0 !important" onload="playGame();">
	<div class="main bg-2">
		<div class="hand"></div>
		<div id="startNum" class="num-3"></div>
		<div class="boss"><span class="bang"></span></div>
		<div class="countNum clock"><span id="timerc">30</span></div>
		<div class="countNum fist"><span id="fist-count">0</span></div>
		<div class="guide"><img src="../images/games/beatboss/guide.png" alt=""></div>
	</div>
	<form id="gameStartForm">
		<input type="hidden" name="totalPoint" />
		<input type="hidden" name="openId" value="${openId}"/>
  	</form>
</body>
</html>
<%@include file="../v1/template/footJavascript.jsp" %>
<script src="../js/beatboss/jquery.touch.min.js"></script>
<script type="text/javascript">
function playGame(){
	$(".main").css({"height":$(window).height()});
	
	$(document).bind('touchstart', function(e) {
    	e.preventDefault();
    });
	
    /* 击打效果 */
	var i= 0;
    
	// 监听点击事件
	$('.main').tap(function(e){
    	if(timeCount>0 && timeCount<31){
	        // 击打效果
			$(".hand").stop(!0,!1).animate({"top":'20%'},30,function(){
                $(".bang").show();
                $(".hand").stop(!0,!1).animate({"top":'-17.8%'},30,function(){
                    $(".bang").hide();
                });
             })
            i++;
	        $("#fist-count").html(i);
		}
    });
	
	/* 引导 */
	$('.guide').animate({'bottom': '15%'}, 500);
	
    /* 准备效果 */
	var num = 3;
    timerId = setInterval(function() {
		num--;
		$("#startNum").removeClass().addClass('num-'+num);
		if (num==0) {
			$('.guide').hide();
			clearInterval(timerId);
			$("#startNum").removeClass().addClass('go').animate({'opacity': 0},500,gameTime());
		};
	},1000);
    
    /* 倒计时 */
	var timeCount = 31;
	var timerId;
	function gameTime() {
		timeCount = 30;
		timerId = setInterval(function() {
        	if(timeCount>0){
        		--timeCount;
          		$("#timerc").html(timeCount);
        	}else{
          		clearInterval(timerId);
          		// 计时结束后发送数据
          		var totalPoint=$("#fist-count").html();
          		var url ="../beatboss/owner";
          		$("[name='totalPoint']").val(totalPoint);
      				leasePostAjax(url,$("#gameStartForm"),"text",function(data){
      					var openId = $("[name='openId']").val();
      					document.location.href="../beatboss/result?openId="+openId;
      				}); 
			}
		},1000);
	}  
}
</script>

