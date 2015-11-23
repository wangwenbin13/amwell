<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../v1/template/headMeta.jsp" %>
<title>小猪巴士邀您一起看谍中谍</title>

<style>
	body.game { overflow: auto; background: none; }
	#impossibleView { position: absolute; left: 0px; top: 0px; background-color: #030409; background-image: url(http://img.pig84.com/download/100000000054); background-size: 100%; background-repeat: no-repeat; }
	#prize, #tombola { width: 90%; height: 16%; position: absolute; left: 5%; }
	#prize { color: #34d8ff; text-align: center; font-size: 32px; z-index: 1; }
	#tombola { z-index: 2; }
	#impossibleView > ul { list-style: none; margin-top: 128%; }
	#impossibleView > ul li { text-align: center; margin-top: 8px; }
	#impossibleView > .arrow { clear: both; background: url(http://img.pig84.com/download/100000000061) no-repeat; display: block; background-size: 100%; width: 25px; height: 25px; margin: 14px auto; }
	#impossibleView > .note { clear: both; color: #91a8b8; line-height: 160%; padding: 48px 4% 28px; display: block; width: 84%; margin: 28px auto 48px; background-color: #171e31; background-image: url(http://img.pig84.com/download/100000000056); background-repeat: no-repeat; background-position: 0px 0px; background-size: 100%; }
	#impossibleView > .note > p { clear: both; }
	#impossibleView > .note > p > span:nth-child(1) { float: left; }
	#impossibleView > .note > p > span:nth-child(2) { float: left; width: 91%; }
</style>
</head>
<body class="game" center="1">
	<div id="impossibleView">
	<!-- content -->
	<p id="prize"></p>
	<canvas id="tombola"></canvas>
	
	<ul>
		<li><img src="http://img.pig84.com/download/100000000057" width="92%" ></li>
		<li><img src="http://img.pig84.com/download/100000000058" width="92%" ></li>
		<li><img src="http://img.pig84.com/download/100000000059" width="92%" ></li>
		<li><img src="http://img.pig84.com/download/100000000060" width="92%" ></li>
	</ul>
	
	<div class="arrow"></div>
	<div class="note">
		<p><span>1、</span><span>小猪巴士注册用户才有机会获得奖品，未注册用户所得奖品将作废</span></p>
		<p><span>2、</span><span>本次活动奖品由TCL全程赞助</span></p>
		<p><span>3、</span><span>电影票仅支持在中影国际影城（欢乐海岸店）使用</span></p>
		<p><span>4、</span><span>小猪巴士将在活动结束后7个工作日内联系奖品获得者确定寄送事宜</span></p>
		<p><span>5、</span><span>本活动最终解释权归小猪巴士所有</span></p>
	</div>
	<img src="http://img.pig84.com/download/100000000055" width="100%">
	</div>
    
	<input type="hidden" id="passengerIpt" value='${passenger}'/>
	<%@include file="../v1/template/loginView.jsp" %>
	<%@include file="../v1/template/tipView.jsp" %>
	<%@include file="../v1/template/loadingView.jsp" %>
</body>
</html>
<%@include file="../v1/template/footJavascript.jsp" %>
<script>
	var openId = '${openId}', passenger = $('#passengerIpt').val(), isActivite = ${isActivate}, 
		isTicketsLoaded = 1, ticketsPage = 0;
	openId && localStorage.setItem("openId", openId);
	// 渠道类型；
	var channelType = '${channel}';
	channelType && localStorage.setItem("channel", channelType);
	setLoginSuccessCallback(save);
	// 处理用户
	if (passenger) {
		localStorage.setItem("MyUserInfo", passenger);
	} else {
		localStorage.removeItem('MyUserInfo');
	}
	
	var img = new Image(), isImgLoaded = false;
	img.onload = function () {
		isImgLoaded = true;
		initPage();
	}
	img.src = 'http://img.pig84.com/download/100000000062';
	var ctx, pattern, vw, vh, offsetX, offsetY, resultPrize = 0;
	var clearRadius = 12, clearLine = 4, clearColor = 'rbga(0, 0, 0, 0.05)';
	/** 装载页面内容； */
	function initPage() {
		var zoom = $(window).width() / 640;
		$('#prize, #tombola').css({
			top: (480 * zoom >> 0) + 'px',
			height: (146 * zoom >> 0) + 'px'
		});
		var lab = $('#prize'), canvas = document.getElementById('tombola');
		vw = canvas.offsetWidth;
		vh = canvas.offsetHeight;
		offsetX = canvas.offsetLeft;
		offsetY = canvas.offsetTop;
		lab.css({
			lineHeight: vh + 'px'
		});
			
		if (!isActivite) {
			$('#tombola').fadeOut();
			lab.html('活动结束');
			return;
		}
		var user = getLoginUser();
		$.post('${contextPath}/games/impossible/result', {
			uid: user ? user['a2'] : '',
		}, function (res) {
			resultPrize = res['res'] || 0;
			switch(resultPrize) {
				case 1: lab.html('一等奖'); break;
				case 2: lab.html('二等奖'); break;
				case 3: lab.html('三等奖'); break;
				case 4: lab.html('四等奖'); break;
				default: lab.html('谢谢参与'); break;
			}
			if (!res['isNew']) {
				$('#tombola').fadeOut();
				return;
			}
			canvas.setAttribute('width', vw);
			canvas.setAttribute('height', vh);
			ctx = canvas.getContext("2d");
			ctx.textAlign = 'center';
			ctx.textBaseline = 'middle';
			ctx.font = '28px 微软雅黑';
			pattern = ctx.createPattern(img, "repeat");
			reset();
        	canvas.addEventListener('touchstart', onTouchStart, false);
        	setInterval(fps, 40);
		}, 'json');
	}
	
	// 绘制默认界面；
	function reset() {
		ctx.clearRect(0, 0, vw, vh);
		ctx.fillStyle = pattern;
		ctx.beginPath();
        ctx.rect(0, 0, vw, vh);
        ctx.fill();
        ctx.closePath();
        ctx.fillStyle = '#8997bc';
        ctx.fillText('刮刮乐', vw >> 1, vh >> 1);
        ctx.globalCompositeOperation = 'destination-out';
	}
	
	// 更新手机操作路线；
	var radius = 10, offsetR = 5, paths = [], PI = Math.PI * 2;
	function fps() {
		if (paths.length > 0) {
			var points = paths.splice(0, paths.length);
			while(points.length) {
				var x = points.shift(), y = points.shift();
				ctx.beginPath();
				var radgrad = ctx.createRadialGradient(x, y, 0, x, y, radius);
    			radgrad.addColorStop(0, 'rgba(0, 0, 0, 0.9)');
    			radgrad.addColorStop(1, 'rgba(0, 0, 0, 0)');
				ctx.fillStyle = radgrad;
				ctx.arc(x, y, radius, 0, PI, 1);
				ctx.fill();
			}
		}
	}
	
	function onTouchStart(evt) {
		var stopEvt = evt;
		if (evt['touches'] && evt.touches.length)
			evt = evt.touches[0];
		if (!getLoginUser()) {
			if (confirm('您要绑定手机号码并且开始刮奖吗？')) {
					$('#impossibleView').hide();
					document.body.style['overflow'] = 'hidden';
					document.documentElement.scrollTop = 0;
					forwardHash('#login');
        	}
		} else {
			paths.push(evt.clientX - offsetR - offsetX);
			paths.push(evt.clientY - offsetR - offsetY - this.scrollTop);
			document.documentElement.addEventListener('touchmove', onTouchMove, false);
        	document.documentElement.addEventListener('touchend', onTouchEnd, false);
        }
		stopEvent(stopEvt);
	}
	
	function onTouchEnd(evt) {
		document.documentElement.removeEventListener('touchmove', onTouchMove, false);
        document.documentElement.removeEventListener('touchend', onTouchEnd, false);
        save();
        stopEvent(evt);
	}
	
	function save() {
		document.body.style['overflow'] = 'auto';
		$('#impossibleView').show();
		var user = getLoginUser();
		$.post('${contextPath}/games/impossible/login', {
			tel: user['a6'],
			uid: user['a2'],
			prize: resultPrize + ''
		});
	}
	
	function onTouchMove(evt) {
		var stopEvt = evt;
		if (evt['touches'] && evt.touches.length)
			evt = evt.touches[0];
		paths.push(evt.clientX - offsetR - offsetX);
		paths.push(evt.clientY - offsetR - offsetY - this.scrollTop);
		stopEvent(stopEvt);
	}
</script>
