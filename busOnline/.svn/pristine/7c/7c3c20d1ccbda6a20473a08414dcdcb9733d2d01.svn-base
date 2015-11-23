<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="template/headMeta.jsp" %>
<title>小猪巴士</title>

<style>
#homeHeader { color: #FFF; z-index: 1; width: 100%; position: fixed; left: 0px; top: 0px; height: 41px; font-size: 14px; padding: 12px 15px; background-color: #4cbd71;  }
#homeHeader > img { width:43px; float: left; }
#homeHeader > p { float: left; width: 156px; margin: 1px 8px; line-height: 16px; }
#homeHeader span.couponIcon { display: block; float: right; background: url(http://img.pig84.com/download/100000000030) no-repeat; width: 30px; height: 30px; margin: 6px 30px 0px 0px; }
#homeHeader .phone { background: url(http://img.pig84.com/download/100000000029) no-repeat  0px 6px; padding: 4px 0px 4px 14px; }
#homeHeader .phone > span{ border-left: 1px solid #666; margin-left: 8px; padding-left: 8px; }

#homeTicketsSpace { width: 314px; overflow: auto; margin-top: 72px; }
#homeTickets { list-style: none; }
#homeTickets > li { margin: 8px 4px 0px; background-color: #FFF; box-shadow: 0px 0px 2px #999; width: 290px; height: 94px; padding: 8px 8px; }
#homeTickets > li .date { color: #FFF; float: right; width: 120px; height: 58px; background: rgba(135, 205, 108, 1); padding: 4px 4px 0px; margin-top: 12px; }
#homeTickets > li .date p:nth-child(1) { font-size: 13px; line-height: 18px; float: left; width: 64px; margin-left: 4px; }
#homeTickets > li .date p:nth-child(2) { font-size: 14px; line-height: 18px; text-align: right; float: right; width: 48px; margin-right: 4px; }
#homeTickets > li .date p:nth-child(3) { font-size: 28px; line-height: 40px; clear: both; text-align: center; }
#homeTickets > li .info { width: 160px; }
#homeTickets > li .info .time { line-height: 22px; font-size: 12px; }
#homeTickets > li .info .time span { font-size: 16px; }
#homeTickets > li .info .begin { line-height: 24px; font-size: 14px; overflow: hidden; white-space:nowrap; }
#homeTickets > li .info .begin em { margin-right: 4px; float: left; width: 20px; height: 20px; text-align: center; border: 1px solid #4C4; color: #4C4; border-radius: 4px; font-size: 14px; line-height: 150%; }
#homeTickets > li .info .end { line-height: 24px; font-size: 14px; overflow: hidden; white-space:nowrap; }
#homeTickets > li .info .end em { margin-right: 4px; float: left; width: 20px; height: 20px; text-align: center; border: 1px solid #e32; color: #e32; border-radius: 4px; font-size: 14px; line-height: 150%; }
#homeTickets > li .info .price { line-height: 28px; color: #CCC; font-size: 13px; }

#homeTicketsSpace > div.tickets_more { line-height: 48px; background-color: rgba(255, 255, 255, 0.4); border-radius: 8px; text-align: center; font-size: 20px; color: #999; }

#tabPace { background-color: rgba(255, 255, 255, 0.97); border-top: 1px solid #e3e4e5; position: fixed; bottom: 0px; left: 0px; width: 100%; height: 55px; }
#tabPace > a { display: block; height: 48px; margin: 4px 0px; color: #999; text-align: center; }
#tabPace .bus { float: left; width: 49%; }
#tabPace .bus em { clear: both; display: block; background: url(http://img.pig84.com/download/100000000024) no-repeat; width: 27px; height: 23px; margin: 4px auto;}
#tabPace .bus.on { color: #4cc48d; }
#tabPace .bus.on em { background: url(http://img.pig84.com/download/100000000025) no-repeat; }
#tabPace .user { float: right; width: 49%; }
#tabPace .user em { clear: both; display: block; background: url(http://img.pig84.com/download/100000000026) no-repeat; width: 21px; height: 23px; margin: 4px auto; }
#tabPace .user.on { color: #4cc48d; }
#tabPace .user.on em { background: url(http://img.pig84.com/download/100000000027) no-repeat; }
</style>
</head>
<body class="fuzzy">
	<!-- content -->
	<div id="homeHeader">
		<img onclick="unbind();" src="http://img.pig84.com/download/100000000028" />
		<span class="couponIcon" onclick="forwardHash('#coupon');"></span>
		<p class="name"></p>
		<p class="phone"><strong>${cityName}</strong><span></span></p>
	</div>
    <div id="homeTicketsSpace">
    	<ul id="homeTickets"></ul>
    	<div class="tickets_more" onclick="loadTickets(ticketsPage + 1);"></div>
    </div>
    
    <div id="tabPace">
    	<a class="bus" href="javascript:void(0);"><em></em>坐车</a>
    	<a class="user on" href="javascript:void(0);"><em></em>我的</a>
    </div>
	<input type="hidden" id="passengerIpt" value='${passenger}'/>
	<%@include file="template/loginView.jsp" %>
	<%@include file="template/tipView.jsp" %>
	<%@include file="template/loadingView.jsp" %>
	<%@include file="template/couponView.jsp" %>
</body>
</html>
<%@include file="template/footJavascript.jsp" %>
<script>
	var ticketPrices = 0;
	var openId = '${openId}', passenger = $('#passengerIpt').val(), isTicketsLoaded = 1, ticketsPage = 0;
	openId && localStorage.setItem("openId", openId);
	couponTemplate = couponTemplateDis;
	// 渠道类型；
	var channelType = '${channel}';
	channelType && localStorage.setItem("channel", channelType);
	
	var url = location.href;
	$('.bus').attr('href', '${contextPath}/v1/line/workday' + url.substring(url.indexOf('?')));
	setLoginSuccessCallback(initPage);
	
	$(function () {
		// 处理用户
		if (passenger) {
			localStorage.setItem("MyUserInfo", passenger);
			initPage();
		} else {
			localStorage.removeItem('MyUserInfo');
			forwardHash('#login');
		}
	});
	
	/** 解除绑定； */
	function unbind() {
	
	}
	
	/** 装载页面内容； */
	function initPage() {
		var user = getLoginUser();
		$('#homeHeader .name').html(user['a4']);
		$('#homeHeader .phone > span').html(user['a6']);
		
		$('#homeTicketsSpace').css({
			height: $(window).height() - 72 - 56 + 'px'
		});
		loadTickets();
	}
	
	/** 打开车票详情界面； */
	function openTicket(id, cid, uid) {
		location.href = ['${contextPath}/v1/user/ticket?id=', id, '&uid=', uid, '&cid=', cid].join('');
	}
	
	var myCity = new BMap.LocalCity();
	myCity.get(function (result) {
		if (result && result['name']) {
			$('.phone > strong').html(result['name']);
		}
	});
	
	var ticketTemplate = ['<li onclick="openTicket(\'', null, '\', \'', null, '\', \'', null, 
    		'\')"><div class="date"><p>', null, '</p><p>', null, '</p><p>', null, 
    		'</p></div><div class="info"><p class="time"><span>', null, '</span>-<strong>', null, 
    		'</strong></p><p class="begin"><em>上</em>', null, '</p><p class="end"><em>下</em>', null, 
    		'</p><p class="price">票价：￥<span>', null, '</span></p></div>'];
	/** 加载车票。 */
	function loadTickets(page) {
		var user = getLoginUser();
		if (!user) {
			forwardHash('#login');
			return;
		}
		if (!isTicketsLoaded || ticketsPage == page)
			return;
		page = page || 0;
		onLoadingShow('车票加载中...');
		$.post('${contextPath}/v1/user/tickets', {
			uid: user['a2'],
			page: ticketsPage = page
		}, function(data) {
			var tickets = data['list'], ticketsHtml = [];
			if (tickets && tickets.length) {
				var today = new Date();
				for (var i = 0, tickets; ticket = tickets[i]; i++) {
					var stat = ticket['a5'];
					if (stat == '0' || stat == 2) {
						try {
							ticketTemplate[1] = ticket['a3'];
							ticketTemplate[3] = ticket['a1'];
							ticketTemplate[5] = ticket['a2'];
							//ticketTemplate[5] = user['a2'];
							var d = ticket['a11'], di = d.indexOf('-'), dd = new Date(d);
							ticketTemplate[7] = d.substring(0, di);
							ticketTemplate[9] = toLocalWeekDay(today, dd);
							ticketTemplate[11] = [dd.getMonth() + 1, '月', dd.getDate(), '日'].join('');
							ticketTemplate[13] = ticket['a12'];
							ticketTemplate[15] = (ticket['a14'] || ticket['a14']).cnSubstring(8, 140);
							ticketTemplate[17] = (ticket['a15'] || ticket['a15']).cnSubstring(7, 128);
							ticketTemplate[19] = ticket['a16'].cnSubstring(7, 128);
							ticketTemplate[21] = ticket['a4'];
							ticketsHtml.push(ticketTemplate.join(''));
						} catch(e) {}
					}
				}
			}
			
			$("#homeTickets")[page == 0 ? 'html' : 'append'](ticketsHtml.join(''));
			$('.tickets_more').html((isTicketsLoaded = data['a2'] == 1) ? '点击加载更多' : '没有更多车票');
			onLoadingHide();
	    }, 'json');
	    
	    function formatTime(time) {
	    	return time.substring(time.indexOf('-') + 1);
	    }
	}
</script>
