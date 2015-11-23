<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="template/headMeta.jsp" %>
<title>小猪巴士</title>
<style>
#ticketSpace { display: none; position: absolute; top: 32px; width: 314px; height: 230px; }

#ticketSpace .bus { padding-top: 20px; height: 128px; color: #FFF; background-image: url(http://img.pig84.com/download/100000000044); background-repeat: no-repeat; background-position: 0px; background-size: 100%; border-radius: 10px 10px 0px 0px; }
#ticketSpace .bus h1 { line-height: 48px; font-weight: bold; font-size: 32px; text-align: center; margin-top: 8px; }
#ticketSpace .bus p { font-size: 18px; text-align: center; line-height: 20px; }
#ticketSpace .bus p > span { font-size: 16px; }
#ticketSpace .bus > ul { list-style: none; width: 72px; height: 20px; margin: 0px auto; }
#ticketSpace .bus > ul li { float: left; width: 11px; height: 18px; background: url(http://img.pig84.com/download/100000000043) no-repeat; background-size: 100%; }
#ticketSpace .bus .arrow0 { -webkit-animation-name: 'runAnimation'; -webkit-animation-duration: 2s; -webkit-animation-delay: 0s; -webkit-animation-timing-function: linear; -webkit-animation-iteration-count: infinite; }
#ticketSpace .bus .arrow1 { -webkit-animation-name: 'runAnimation'; -webkit-animation-duration: 2s; -webkit-animation-delay: 0.2s; -webkit-animation-timing-function: linear; -webkit-animation-iteration-count: infinite; }
#ticketSpace .bus .arrow2 { -webkit-animation-name: 'runAnimation'; -webkit-animation-duration: 2s; -webkit-animation-delay: 0.4s; -webkit-animation-timing-function: linear; -webkit-animation-iteration-count: infinite; }
#ticketSpace .bus .arrow3 { -webkit-animation-name: 'runAnimation'; -webkit-animation-duration: 2s; -webkit-animation-delay: 0.6s; -webkit-animation-timing-function: linear; -webkit-animation-iteration-count: infinite; }
#ticketSpace .bus .arrow4 { -webkit-animation-name: 'runAnimation'; -webkit-animation-duration: 2s; -webkit-animation-delay: 0.8s; -webkit-animation-timing-function: linear; -webkit-animation-iteration-count: infinite; }
#ticketSpace .bus .arrow5 { -webkit-animation-name: 'runAnimation'; -webkit-animation-duration: 2s; -webkit-animation-delay: 1s; -webkit-animation-timing-function: linear; -webkit-animation-iteration-count: infinite; }

#ticketSpace .station { background-color: #FFF; padding: 12px 10px; height: 56px; border-radius: 0px 0px 18px 18px; border: 1px solid #ccc; border-top: none; box-shadow: 1px 1px 2px #ccc; }
#ticketSpace .station .left-icon { margin: 4px 0px; width: 9px; height: 48px; float: left; background: url(http://img.pig84.com/download/100000000045) no-repeat; background-size: 100%; }
#ticketSpace .station .code { float: right; }
#ticketSpace .station .code em { width: 140px; height: 44px; display: block; background-image: url(http://img.pig84.com/download/100000000052); background-repeat: repeat-x; background-size: 100%; }
#ticketSpace .station .code em.step0 { background-position-x: 0px; }
#ticketSpace .station .code em.step1 { background-position-x: 35px; }
#ticketSpace .station .code em.step2 { background-position-x: 70px; }
#ticketSpace .station .code em.step3 { background-position-x: 105px; }
#ticketSpace .station .code p { letter-spacing: 1px; }
#ticketSpace .station .begin { padding-left: 16px; font-size: 16px; width: 128px; line-height: 22px; color: rgba(72, 72, 72, 0.8); }
#ticketSpace .station .end { padding-left: 16px; font-size: 16px; width: 128px; line-height: 22px; margin-top: 10px; color: rgba(72, 72, 72, 0.8); }

.btn { display: none; position: absolute; bottom: 0px; left: 0px; width: 100%; height: 48px; font-size: 20px; color: #FFF; background-color: #28c940; line-height: 48px; text-align: center; }

#ticketSpace.over .bus ul { visibility: hidden; }
#ticketSpace.over .station .code em { background-image: url(http://img.pig84.com/download/100000000051); }

#tuochView { display: none; z-index: 15; position: absolute; left: 0px; top: 0px; background: rgba(0, 0, 0, 0.9); }
#tuochView > div { background-color: #28c940; width: 240px; padding: 4px 8px; height: 96px; border-radius: 10px; margin: 20% auto; box-shadow: 0px 0px 8px #28c940; }
#tuochView > div h1 { font-size: 24px; color: #FFF; text-align: center; line-height: 52px; }
#tuochView > div p { font-size: 16px; color: #F00; line-height: 40px; }

@-webkit-keyframes 'runAnimation' {
	0% { opacity: 0; }
	50% { opacity: 1; }
	100% { opacity: 0; }
}

</style>
</head>
<body class="fuzzy">
	<!-- content -->
	<div id="ticketSpace">
		<div class="bus">
			<ul>
				<li class="arrow0"></li>
				<li class="arrow1"></li>
				<li class="arrow2"></li>
				<li class="arrow3"></li>
				<li class="arrow4"></li>
				<li class="arrow5"></li>
			</ul>
			<h1></h1>
			<p><strong></strong>-<span></span></p>
		</div>
		<div class="station">
			<div class="left-icon"></div>
			<div class="code"><em></em><p>120.0.0.0.0.0.0021020</p></div>
			<p class="begin"></p>
			<p class="end"></p>
		</div>
	</div>
	<a class="btn" href="http://cli.im/DteoJx">下载APP看车到哪儿了</a>
	
	<div id="tuochView" onmousedown="onTicketActive(event);" ontouchstart="onTicketActive(event);" onmouseup="onTicketReset(event);" ontouchend="onTicketReset(event);" ontouchcancel="onTicketReset(event);">
		<div>
			<h1>长按屏幕票激活车票</h1>
			<p>警告：票激活以后5分钟会失效。</p>	
		</div>
	</div>
	
	<%@include file="template/tipView.jsp" %>
	
	<input type="hidden" value='${ticket}' id="ticketInfo" />
</body>
</html>
<%@include file="template/footJavascript.jsp" %>
<script>
	var ticketInfo = $('#ticketInfo').val();
	if (ticketInfo)
		ticketInfo = eval('(' + ticketInfo + ')');
	$(function () {
		if (ticketInfo['a7'])
			alert(ticketInfo['a7']);
		var date = ticketInfo['a4'];
		if (date.indexOf('0') == 0)
			date = date.substring(1);
		$('#ticketSpace .bus p > strong').html(date.replace('.', '月') + '日');
		$('#ticketSpace .bus p > span').html(ticketInfo['a2']);
		$('#ticketSpace .bus h1').html(ticketInfo['a1']);
		$('#ticketSpace > .station p.begin').html(ticketInfo['a5'].cnSubstring(8, 120));
		$('#ticketSpace > .station p.end').html(ticketInfo['a6'].cnSubstring(8, 120));
		var bgc = 'rgb(200, 200, 200)';
		if (ticketInfo['a8'] == 0) {
			$('#ticketSpace').addClass('over');
			$('.btn').html('点此下载APP退票、改签');
			$('.btn').css({
				display: 'block',
				'background-color': bgc
			});
		} else {
			var bgCol = ticketInfo['a9'];
			if (bgCol)
				bgc = '#' + bgCol
			$('.btn').css({
				display: 'block',
				'background-color': '#28c940'
			});
		}
		$('#ticketSpace .bus').css({
			display: 'block',
			'background-color': bgc
		}); 
		
		$('#ticketSpace').css({
			display: 'block'
		});
		startColorCodeRun();
		
		$('#tuochView').css({
			width: $(window).width() + 'px',
			height: $(window).height() + 'px'
		});
	});
	
	var ticketGradientArr = ['-webkit-radial-gradient(', null, ' ', null, ', circle farthest-side, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0.2) ', null, '%, rgba(0, 0, 0, 0.9) ', null, '%)'];
	var activeTimer = 0;
	/** 激活车票； */
	function onTicketActive(evt) {
		var stopEvt = evt;
		if (evt['touches'] && evt.touches.length) {
			evt = evt.touches[0];
		}
		ticketGradientArr[1] = evt.clientX + 'px';
		ticketGradientArr[3] = evt.clientY + 'px';
		var elem = $('#tuochView'), progress = -40;
		clearInterval(activeTimer);
		activeTimer = setInterval(function () {
			progress += 1;
			ticketGradientArr[5] = progress;
			ticketGradientArr[7] = progress + 40;
			elem.css({background: ticketGradientArr.join('')});
			if (progress >= 100) {
				clearInterval(activeTimer);
				$('#tuochView').fadeOut(200);
				// 车票激活完毕
			}
		}, 20);
	}
	
	/** 放弃激活车票； */
	function onTicketReset() {
		clearInterval(activeTimer);
		$('#tuochView').css({'background': 'rgba(0, 0, 0, 0.9)'});
	}
	
	/** 条码闪烁的动画； */
	function startColorCodeRun() {
		var elem = $('#ticketSpace .station .code em').get(0), index = 0;
		setInterval(function () {
			elem.className = 'step' + (index ++ % 4);
		}, 200);
	}
</script>
