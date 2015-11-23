<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="template/headMeta.jsp" %>
<title>班车详情</title>

<style>
	#lineSpace { width: 100%; height: 100%; overflow: hidden; }
	#lineSpace > header { background: #FFF; padding: 8px 0px 2px; }
	#lineSpace > header > p { line-height: 24px; color: #666; font-size: 16px; }
	#lineSpace > header .time { padding: 4px 18px 0px; font-size: 28px; color: #000; line-height: 30px; }
	#lineSpace > header .time span { margin-left: 8px; font-size: 14px; }
	#lineSpace > header .begin {  padding: 4px 18px; }
	#lineSpace > header .begin span { margin-right: 4px; border: 1px solid #38c083; border-radius: 4px; color: #38c083; }
	#lineSpace > header .end {  padding: 4px 18px; }
	#lineSpace > header .end span { margin-right: 4px;  border: 1px solid #f15d5d; border-radius: 4px; color: #f15d5d; }
	#lineSpace > header .price { margin-right: 12px; float: right; height: 96px; line-height: 128px; color: #ff8f4b; font-size: 22px; }
	#lineSpace > header .price span { font-size: 12px; }
	#lineSpace > header > div { clear: both; border-top: 1px solid #e0e0e0; text-align: center; margin-top: 8px; padding-bottom: 10px; box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2); }
	#lineSpace > header > div p { line-height: 20px; font-size: 16px; margin: 12px 0px; }
	#lineSpace > header > div a { margin: 0px auto; display: block; width: 180px; line-height: 44px; color: #FFF; background-color: #4cbd71; font-size: 22px; border-radius: 4px; height: 44px; }
	#lineSpace > header > div a.on { background: #c0c0c0; }
	
	#lineSpace > footer { bottom: 0px; left: 0px; width: 100%; position: absolute; background: #fff; text-align: center; height: 40px; padding: 12px 0px; }
	#lineSpace > #map {  }
</style>
</head>
<body class="none">
	<!-- content -->
	<div id="lineSpace">
		<header>
			<p class="price"></p>
			<p class="time"></p>
			<p class="begin"></p>
			<p class="end"></p>
			<div>
				<p class="signer"></p>
				<a>立即报名</a>
			</div>	
		</header>
		
		<div id="map"></div>
		<footer>
			<img src="http://img.pig84.com/download/100000000023" height="40" />
		</footer>
	</div>
	
	<%@include file="template/loginView.jsp" %>
	<%@include file="template/tipView.jsp" %>
	<%@include file="template/loadingView.jsp" %>
	<input type="hidden" value='${lineInfo}' id="lineInfo" />
</body>
</html>
<%@include file="template/footJavascript.jsp" %>
<script>
	var map, signupLineInfo, isSignupLine;
	signupLineInfo = eval('(' + $('#lineInfo').val() + ')');
	$(function () {
		document.body.style.padding = '0';
	
		$('#lineSpace > header > .price').html('<span>￥</span>' + signupLineInfo['a3']);
		$('#lineSpace > header > .time').html([signupLineInfo['a2'], '<span>全程', signupLineInfo['a6'], 'km&nbsp;&nbsp;约', signupLineInfo['a7'], '分钟</span>'].join(''));
		$('#lineSpace > header > .begin').html('<span>上</span>' + signupLineInfo['a4']);
		$('#lineSpace > header > .end').html('<span>下</span>' + signupLineInfo['a5']);
		$('#lineSpace > header .signer').html(signupLineInfo['a8'] + '人报名，满50人即可开线');
		$('#lineSpace > header a').click(function () {
			if (isSignupLine)
				return;
			var user = getLoginUser();
			if (user) {
				$.post('${contextPath}/v1/line/signupLine', {
					id: signupLineInfo['a1'],
					uid: user['a2']
				}, function (res) {
					isSignupLine = true;
					var btn = $('#lineSpace > header a');
					btn.html('报名成功');
					btn.addClass('on');
					$('#lineSpace > header a').click(null);
				}, 'text');
			} else {
				localStorage.removeItem("MyUserInfo");
				forwardHash('#login');
			}
		});
		
		$('#map').css({
			height: $(window).height() - $('#lineSpace > header').height() - $('#lineSpace > footer').height() + 'px'
		});
	
		map = new BMap.Map("map");
		map.centerAndZoom(new BMap.Point(signupLineInfo['a11'], signupLineInfo['a10']), 15);
		var myP1 = new BMap.Point(signupLineInfo['a11'], signupLineInfo['a10']);
		var myP2 = new BMap.Point(signupLineInfo['a13'], signupLineInfo['a12']);
		var driving2 = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});    //驾车实例
		driving2.search(myP1, myP2);
	});
</script>
