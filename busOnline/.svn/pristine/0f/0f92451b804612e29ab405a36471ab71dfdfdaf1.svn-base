<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.velocity.runtime.directive.Include"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="template/headMeta.jsp" %>
	<title>小猪巴士</title>
	<style type="text/css">
		#detailHead { background-color: rgba(248, 248, 248, 0.95); height: 64px; border-bottom: 1px solid #ecedec; width: 100%; position: fixed; top: 0px; left: 0px; }
		#detailHead > div {  width: 28px; height: 46px; font-size: 14px; }
		#detailHead > div > em { display: block; width: 8px; height: 14px; }
		#detailHead .detail_head_arrow_left {float: left; text-align: left; color: #0cde8a; padding: 18px 0px 0px 16px; }
		#detailHead .detail_head_arrow_right { float: right; text-align: right; color: #272b2e; padding: 18px 16px 0px 0px;}
		#detailHead .detail_head_arrow_left.off { color: rgba(12, 208, 138, 0.6); }
		#detailHead .detail_head_arrow_right.off { color: rgba(39, 43, 46, 0.4); }
		#detailHead .detail_head_arrow_left em { margin-bottom: 8px; background: url(http://img.pig84.com/download/100000000031) no-repeat; background-size: 100%; }
		#detailHead .detail_head_arrow_right em { margin: 0px 0px 8px 24px; -webkit-transform: rotateZ(180deg); background: url(http://img.pig84.com/download/100000000031) no-repeat; background-size: 100%; }
		#detailHead section { overflow: hidden; }
		#detailHead section ul { list-style: none;  margin: 0px auto; height: 64px; overflow: hidden; -webkit-transition: all 0.8s ease-in-out 0s; }
		#detailHead section ul li { float: left; margin: 0px 2px; line-height: 16px; text-align: center; }
		#detailHead section ul li > div { margin: 6px 2px 4px; padding: 2px; }
		#detailHead section ul li > div > p:nth-child(1) { font-size: 16px; color: #272b2e; }
		#detailHead section ul li > div > p:nth-child(2) { font-size: 14px; color: #999999; }
		#detailHead section ul li p:nth-child(2) { font-size: 8px; color: #fb7b7b; }
		#detailHead section ul li.dis > div { background: #FFDD20; border-radius: 4px; }
		#detailHead section ul li.dis > div > p:nth-child(1) { font-size: 16px; color: #FFFFFF; }
		#detailHead section ul li.dis > div > p:nth-child(2) { font-size: 14px; color: #FFFFFF; }
		#detailHead section ul li.on > div { border-radius: 4px; background: #0cd18a; }
		#detailHead section ul li.on > div > p:nth-child(1) { font-size: 16px; color: #FFFFFF; }
		#detailHead section ul li.on > div > p:nth-child(2) { font-size: 14px; color: #FFFFFF; }
		
		#detailLineSpace { position: fixed; top: 65px; right: 0px; overflow: hidden; width: 66px; height: 96px; z-index: 5; }
		#detailLineSpace .line_btn { margin-top: 40px; text-align: center; width: 46px; height: 48px; border-radius: 28px 0px 0px 28px; background-color: rgba(0, 0, 0, 0.6); padding: 8px 4px 0px 16px; color: #FFF; font-size: 14px; }
		#detailLineSpace .line_btn em { display: block; width: 18px; height: 26px; background: url(http://img.pig84.com/download/100000000032) no-repeat; background-size: 100%; margin: 0px auto; }
		#detailLineSpace .line_stations { background-color: rgba(0, 0, 0, 0.6); overflow: auto; }
		#detailLineSpace .line_stations > p { line-height: 36px; font-size: 14px; padding: 12px 0px 18px; height: 32px; color: #FFF; letter-spacing: 2px; }
		#detailLineSpace .line_stations > p > em { float: left; width: 22px; height: 32px; background: url(http://img.pig84.com/download/100000000032) no-repeat; background-size: 100%; margin: 0px 16px; }
		#detailLineSpace .line_stations > ul { list-style: none; }
		#detailLineSpace .line_stations > ul > li { height: 68px; }
		#detailLineSpace .line_stations > ul > li > p { height: 24px; line-height: 24px; width: 100%; float: left; }
		#detailLineSpace .line_stations > ul > li > p > span { float: left; color: #FFF; text-align: center; }
		#detailLineSpace .line_stations > ul > li > p > span:nth-child(1) { width: 50px; }
		#detailLineSpace .line_stations > ul > li > p > span:nth-child(2) { background-color: #FFF; border-radius: 8px; width: 16px; height: 24px;}
		#detailLineSpace .line_stations > ul > li > p > span:nth-child(3) { text-align: left; padding-left: 8px; font-size: 14px; width: 120px; overflow: hidden; }
		#detailLineSpace .line_stations > ul > li > div { width: 140px; height: 44px; float: right; margin: 0px 0px 0px 56px; border-left: 4px solid #8dffd6; }
		#detailLineSpace .line_stations > ul > li:last-child > div { display: none; }
		
		#detailLineSpace .line_stations > ul > li.choking {}
		#detailLineSpace .line_stations > ul > li.choking > div {  }
		#detailLineSpace.open { height: auto; right: -200px; width: 200px; }
		#detailLineSpace.open .line_btn { display: none; }
		
		.detail_options { position: absolute; bottom: -78px; left: 0px; height: 78px; width: 100%; display: none; }
		#detailCoupon { width: 100%; height: 34px; background-color: rgba(255, 255, 255, 0.6); line-height: 34px; }
		#detailCoupon .detail_coupon_left { float: left; width: 54%; color: #999999; font-size: 14px; letter-spacing: 1px; }
		#detailCoupon .detail_coupon_left em { margin: 7px 8px 7px 16px; background: url(http://img.pig84.com/download/100000000033) no-repeat; float: left; background-size: 100%; width: 34px; height: 20px; }
		#detailCoupon .detail_coupon_right { float: right; width: 45%; color: #fb7b7b; font-size: 22px; text-align: right; height: 34px; }
		#detailCoupon .detail_coupon_right span { font-size: 20px; margin: 2px 4px 0px; letter-spacing: 1px; }
		#detailCoupon .detail_coupon_right em { margin: 7px 16px 7px 8px; background: url(http://img.pig84.com/download/100000000035) no-repeat; float: right; background-size: 100%; width: 10px; height: 20px; }
		#detailCoupon.on .detail_coupon_left { color: #fb7b7b; }
		#detailCoupon.on .detail_coupon_left em { background: url(http://img.pig84.com/download/100000000034) no-repeat; background-size: 100%; }
		#detailCoupon.on .detail_coupon_right em { background: url(http://img.pig84.com/download/100000000036) no-repeat; background-size: 100%; }
		
		#detailBuy { line-height: 44px; height: 44px; background-color: #1cd391; color: #FFF; font-size: 20px; width: 100%; text-align: center; clear: both; }
		#detailBuy.dis { background: #CCC; }
		#detailMask { display: none; width: 100%; height: 100%; position: fixed; top: 0px; left: 0px; z-index: 4; background-color: rgba(0, 0, 0, 0.05); }
	</style>
</head>
<body class="none">

	<div id="map"></div>
	
	<div id="detailHead">
		<div class="detail_head_arrow_right" onclick="weekChange(1);"><em></em><span>下周</span></div>
		<div class="detail_head_arrow_left" onclick="weekChange(-1);"><em></em><span>本周</span></div>
		<section><ul></ul></section>
	</div>
	
	<div id="detailLineSpace">
		<div class="line_btn" onclick="showStations();"><em></em>线路</div>
		<div class="line_stations">
			<p><em></em><span class="bus_plate"></span>线路</p>
			<ul></ul>
		</div>
	</div>
	
	<div class="detail_options">
		<div id="detailCoupon" onclick="onCoupon();"><p class="detail_coupon_left"><em></em>使用优惠劵<span></span></p><p class="detail_coupon_right">￥<span></span><em></em></p></div>
		<div id="detailBuy" onclick="onBuy()">支付</div>
	</div>
	
	<div id="detailMask"></div>
	
	<%@include file="template/orderView.jsp" %>
	<%@include file="template/loginView.jsp" %>
	<%@include file="template/couponView.jsp" %>
	<%@include file="template/loadingView.jsp" %>
	<%@include file="template/tipView.jsp" %>
	<form id="buyFrom" method="post" action="" >
		<input type="hidden" name="lineId" value="" />
		<input type="hidden" name="classId" value="" />
		<input type="hidden" name="splitId" value="" />
		<input type="hidden" name="uid" value="" />
		<input type="hidden" name="openId" value="" />
		<input type="hidden" name="gifId" value="" />
	</form>
	<input type="hidden" id="lineInfoIpt" value='${lineInfo}' >
</body>
</html>
<%@include file="template/footJavascript.jsp" %>
<script type="text/javascript">
var lineDetail = $('#lineInfoIpt').val(), lineSeatWeekMax = 0, lineSeatWeekNum = 0;
if (lineDetail)
	lineDetail = eval('(' + lineDetail + ')');
var openId = localStorage.getItem("openId");
openId && $('input[name=openId]').val(openId);
// 优惠价格；票总价(总价会被优惠券页面使用)；
var gifVal = 0, ticketPrices = 0;
var weekItmeWidth = ($(window).width() - 88) / 7 >> 0;

// 页面信息装填；
$(document).ready(function() {
	var stations = lineDetail['list'], seats = lineDetail['list1'], tempHtml = [];
	// 生成顶部的票务信息
	var templateSeats = ['<li style="width:', null, 'px;" class="', null, '" onclick="selectSeatChange(this);" price="', null, '" seat="', null, '" isbuy="', null, '" classid="', null, '"><div><p>', null, '</p><p>', null, '</p></div><p>', null, '</p></li>'];
	var seatsLen = seats.length;
	lineSeatWeekMax = seatsLen / 7 >> 0;
	seatsLen = lineSeatWeekMax * 7;
	lineSeatWeekMax -= 1;
	var liW = weekItmeWidth - 4, today = new Date();
	for (var i = 0, item; i < seatsLen; i ++) {
		item = seats[i];
		var day = new Date(item['orderDate']); 
		if (i == 0)
			$('input[name=lineId]').val(item['lineBaseId']);
		templateSeats[1] = liW;
		if (item['isbook'] == '1') {
			templateSeats[3] = 'dis';
			templateSeats[17] = '已购';
		} else {
			templateSeats[3] =  '';
			templateSeats[17] = checkClassStat(item['freeSeat'] - 0, day.getDay());
		}
		templateSeats[5] = item['price'];
		templateSeats[7] = item['freeSeat'];
		templateSeats[9] = item['isbook'];
		templateSeats[11] = item['lineClassid'];
		templateSeats[13] = day.getDate();
		templateSeats[15] = toWeekDay(today, day);
		tempHtml.push(templateSeats.join(''));
	}
	$('#detailHead section').css({
		width: weekItmeWidth * 7 + 'px'
	});
	var elem = $('#detailHead ul');
	elem.css({
		width: seatsLen * weekItmeWidth + 'px'
	});
	elem.html(tempHtml.join(''));
	
	// 选取默认的票；
	var allSeats = $('#detailHead li > p');
	for (var i = 0, pElem; pElem = allSeats.get(i); i ++) {
		pElem = $(pElem);
		if (!pElem.html() && !pElem.parent().hasClass('on')) {
			pElem.parent().click();
			break;
		}
	}
	
	// 生成站点信息；
	var templateStation = ['<li onclick="gotoStation(', null, ', ', null, ');"><p><span>', null, '</span><span></span><span>', null, '</span></p><div></div></li>'];
	tempHtml = [];
	for (var i = 0, item; item = stations[i]; i ++) {
		templateStation[1] = item['a3'];
		templateStation[3] = item['a4'];
		templateStation[5] = i + 1;
		templateStation[7] = item['a2'];
		tempHtml.push(templateStation.join(''));
	}
	$('#detailLineSpace ul').html(tempHtml.join(''));
	$('#detailLineSpace .bus_plate').html(lineDetail['a12']);
	
	// 其他信息
	$('input[name=splitId]').val(lineDetail['a9']);
	
	// 处理优惠券的界面模板；
	couponTemplate = couponTemplateUse;
	
	// 处理地图
	var headerTop = $('#detailHead').height();
	$('#map').css({
		height: $(window).height() - headerTop + 'px',
		'margin-top': headerTop + 'px'
	});
	var len = stations.length - 1, wps = [],
		fp = stations[0], ep = stations[len];
	var p1 = new BMap.Point(fp['a3'], fp['a4']), p2 = new BMap.Point(ep['a3'], ep['a4']);
	map = new BMap.Map("map");
	map.enableScrollWheelZoom(true);
	map.centerAndZoom(p1, 15);
	for (var i = 1; i < len; i ++) {
		var point = stations[i];
		wps.push(new BMap.Point(point['a3'], point['a4']));
	}
	var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
	driving.search(p1, p2, {waypoints: wps});
});

/** 查看站点位置； */
function gotoStation(lng, lat) {
	map.panTo(new BMap.Point(lng, lat));
}

/** 处理票务状态； */
function checkClassStat(seat, day) {
	if (seat == -1) {
		if (day == 0 || day == 6)
			return '休息';
		return '停售';
	} else {
		if (seat == 0)
			return '已满';
	}
	return '';
}

/** 日期转换 */
function toWeekDay(date1, date2) {
	var offset = date2.getTime() - date1.getTime(), day = date2.getDay();
	if (offset > 0) {
		if (offset < 86400000)
			day = -2;
		else if (offset < 172800000)
			day = -3;
	} else
		day = -1
	switch(day) {
		case -3: return '后';
		case -2: return '明';
		case -1: return '今';
		case 1: return '一';
		case 2: return '二';
		case 3: return '三';
		case 4: return '四';
		case 5: return '五';
		case 6: return '六';
		default: return '日';
	}
}

/** 更新票务信息； */
function updateBuyInfo() {
	var tcount = 0, psum = 0, cids = [];
	$('#detailHead li.on[isbuy=0]').each(function () {
		psum += this.getAttribute('price') - 0;
		cids.push(this.getAttribute('classid'));
		tcount ++;
	});
	ticketPrices = psum;
	if (gifVal)
		psum -= gifVal;
	psum = (psum < 0 ? 0 : psum).toFixed(2);
	$('#detailCoupon .detail_coupon_right span').html(psum);
	$('input[name=classId]').val(cids.join(','));
	if (tcount > 0)
		showOptions();
	else 
		hideOptions();
}

/** 优惠劵处理操作； */
function updateByCoupon(price, pid) {
	gifVal = price;
	$('input[name=gifId]').val(pid);
	$('#detailCoupon').get(0).className = 'on';
	$('.detail_coupon_left span').html(-gifVal);
	updateBuyInfo();
}

/** 优惠劵操作； */
function onCoupon() {
	if (getLoginUser()) {
		forwardHash('#coupon');
	} else {
		localStorage.removeItem("MyUserInfo");
		forwardHash('#login');
	}
}

/** 支付操作 */
function onBuy() {
	var elem = $('#detailBuy');
	if (elem.hasClass('dis')) {
		return;
	}
	if ($('input[name=classId]').val()) {
		var user = getLoginUser();
		if (user) {
			var uid = user['a2'];
			$('input[name=uid]').val(uid);
			elem.addClass('dis');
			elem.html('支付中...');
			$.post('${contextPath}/v1/order/buy', $('#buyFrom').serialize(), function (res) {
				elem.removeClass('dis');
				elem.html('支付');
				var json = eval('(' + res + ')'), orderId = json['a2'];
				if (json['a3'] == 0) {
					location.href = ["${contextPath}/v1/user/ticket?id=" + orderId, '&uid=', uid].join('');
				} else if (json['a4']) {
					alert(json['a4']);
				} else if (json['a5']) {
					var wx = json['a5'];
					callpay(wx['appId'], wx['timestamp'], wx['noncestr'], wx['package'], wx['sign'], wx['signType'], orderId, uid);
				} else {
					onOrderShow(orderId);
				}
			}, 'text');
		} else {
			localStorage.removeItem("MyUserInfo");
			forwardHash('#login');
		}
	}
}

/** 选票； */
function selectSeatChange(elem) {
	elem = $(elem);
	if (elem.attr('seat') <= 0 || elem.attr('isbuy') != '0')
		return;
	if (elem.hasClass('on'))
		elem.removeClass('on');
	else
		elem.addClass('on');
	updateBuyInfo();
}

/** 周选择变更； */
function weekChange(t) {
	lineSeatWeekNum += t;
	if (lineSeatWeekNum < 0 || lineSeatWeekNum > lineSeatWeekMax) {
		lineSeatWeekNum -= t;
		return;
	} else {
		if (lineSeatWeekNum) {
			$('.detail_head_arrow_left').removeClass('off');
			$('.detail_head_arrow_left span').html('上周');
		} else {
			$('.detail_head_arrow_left span').html('本周');
			$('.detail_head_arrow_left').addClass('off');
		}
		
		if (lineSeatWeekNum == lineSeatWeekMax) {
			$('.detail_head_arrow_right').addClass('off');
		} else {
			$('.detail_head_arrow_right').removeClass('off');
		}
	}
	var styles = {};
	styles['-webkit-transform'] = ['translateX(', lineSeatWeekNum * -weekItmeWidth * 7, 'px)'].join('');
	$('#detailHead ul').css(styles);
}

/** 显示线路列表； */
function showStations() {
	var h = $(window).height() - $('#detailHead').height(), opsElem = $('.detail_options');
	if (opsElem.css('display') == 'block')
		h -= opsElem.height();
	$('#detailLineSpace > .line_stations').css({
		height: h + 'px'
	});
	elem = $('#detailLineSpace');
	elem.addClass('open');
	elem.css({
		right: '-200px'
	});
	elem.animate({
		right: '0px'
	}, {
		duration: 400,
		complete: function () {
			var elem = $('#detailMask');
			elem.show();
			elem.click(hideStations);
		}
	});
}

/** 显示操作界面； */
function showOptions() {
	var elem = $('.detail_options');
	if (elem.css('display') == 'block')
		return;
	elem.css({
		bottom: '-78px',
		display: 'block'
	});
	elem.animate({
		bottom: '0px'
	}, {
		duration: 400
	});
}

/** 隐藏操作界面； */
function hideOptions() {
	var elem = $('.detail_options');
	elem.animate({
		bottom: '-78px'
	}, {
		duration: 400,
		complete: function () {
			elem.hide();
		}
	});
}

/** 隐藏线路列表； */
function hideStations() {
	elem = $('#detailLineSpace');
	elem.animate({
		right: '-200px'
	}, {
		duration: 400,
		complete: function () {
			var elem = $('#detailLineSpace');
			elem.css({
				right: '0px'
			});
			elem.stop(1, 1);
			elem.removeClass('open');
			
			elem = $('#detailMask');
			elem.click(null);
			elem.hide();
		}
	});
}
</script>
