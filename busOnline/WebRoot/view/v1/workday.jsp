<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="template/headMeta.jsp" %>
<title>小猪巴士</title>

<style>
#stationPanel { z-index: 1; width: 282px; position: relative; margin-top: 16px; height: 72px; font-size: 14px; padding: 16px 16px 12px; background-color: rgba(248, 248, 248, 0.95); border-radius: 8px 8px 0px 0px; }

#stationPanel .station-start, #stationPanel .station-end { float: left; clear: both; width: 248px; }
#stationPanel .station-start em { display: block; float: left; width: 14px; height: 14px; background: url(http://img.pig84.com/download/100000000038); background-size: 100%; }
#stationPanel .station-middle { clear: both; float: left; border-bottom: 1px solid #e5e4e4; height: 0px; width: 244px; margin: 14px 4px 16px; }

#stationPanel .station-end em { display: block; float: left; width: 14px; height: 14px; background: url(http://img.pig84.com/download/100000000039); background-size: 100%;  }

#startStationId, #endStationId { margin-left: 12px; border: none; background: none; width: 200px; color: #4cbd71; line-height: 16px; font-size: 14px; }
#startStationId::-webkit-input-placeholder { color: #4cbd71; }
#endStationId::-webkit-input-placeholder { color: #878688; }

.swop-icom { position: absolute; right: 16px; top: 38px; background: url(http://img.pig84.com/download/100000000053); background-size: 100%; width: 18px; height: 18px; }

#stationList { display: none; z-index: 2; position: absolute; background: rgba(248, 247, 247, 0.95); margin: 0px; border: 1px solid rgba(72, 72, 72, 0.2); border-top: none; width: 274px; overflow: auto; border-radius: 0px 0px 4px 4px; left: 38px; }
#stationList li { border-bottom: 1px solid rgba(108, 108, 108, 0.2); margin: 8px; line-height: 32px; background-color: rgba(233, 233, 233, 0.1); }
#stationList li:last-child { border-bottom: none; }

#to_load_more { overflow: auto; margin-top: 10px; background-color: rgba(255, 255, 255, 0.01); }
#to_load_more .linePanel { height: 84px; width: 272px; margin: 0px 0px 8px; border-radius: 8px; padding: 10px 16px; background-color: #F9F9F9; border-left: 10px solid #1ad28f; position: relative; }
#to_load_more .linePanel .section_rotate{ position: absolute; left: 10px; top: 10px;font-size: 16px; width: 1em;}
#to_load_more .linePanel table { width: 248px; margin-left: 22px; border-left: 1px solid #e5e4e4; line-height: 28px; padding-left: 4px; }
#to_load_more .linePanel table td > div { width: 144px; overflow: hidden; white-space:nowrap; }

#to_load_more .linePanel .section_line { padding-top: 4px; }
#to_load_more .linePanel .section_line em { background: url(http://img.pig84.com/download/100000000040) no-repeat; width: 7px; height: 44px; background-size: 100%; display: block; }

#to_load_more .linePanel .section_buy { color: #a9a9ab; font-size: 10px; text-align: center; line-height: 28px; }
#to_load_more .linePanel .section_buy span { display: block; background-color: #54dcaa; border-radius: 8px; color: #fff; font-size: 14px; width: 54px;}

#to_load_more .linePanel .section_time_begin { line-height: 14px; color: #646467; font-size: 10px; padding-left: 4px; }
#to_load_more .linePanel .section_time_begin ul { list-style: none; }
#to_load_more .linePanel .section_time_begin li { float: left; margin-right: 8px; }
#to_load_more .linePanel .section_time_begin p { font-size: 12px; padding-left: 2px; text-align: center; }
#to_load_more .linePanel .section_time_begin em { background: url(http://img.pig84.com/download/100000000041) no-repeat; width: 10px; height: 10px; background-size: 100%; float: left; margin: 2px; }

#to_load_more .linePanel li.section_time_sum { padding-left: 2px; border-left: 1px solid #e5e4e4; color: #a9a9ab; font-size: 14px; line-height: 28px; }
#to_load_more .linePanel li.section_time_sum em { background: url(http://img.pig84.com/download/100000000042) no-repeat; width: 12px; height: 16px; background-size: 100%; float: left; margin: 6px; }

#to_load_more .linePanel li.section_price { margin-right: 0px; padding-left: 8px; border-left: 1px solid #e5e4e4; color: #646467; font-size: 18px; text-align: center; line-height: 28px; }
#to_load_more .linePanel .bottom_line { border-top: 1px solid #e5e4e4; padding-top: 4px; }

#to_load_more .section_more { line-height: 48px; background-color: rgba(255, 255, 255, 0.4); border-radius: 8px; text-align: center; font-size: 20px; color: #777; }
    
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
	<div id="stationPanel">
		<form action="" onsubmit="return false;">
	    <div class="station-start"><em></em>
	        <input type="search" placeholder="当前位置" onclick="onSearchStation(this);" id="startStationId"/>
	        <input type="hidden" id="startStationVal"/>
	    </div>
	    <div class="station-middle"></div>
	    <div class="station-end"><em></em>
	        <input type="search" placeholder="点击设置目的地" onclick="onSearchStation(this);" id="endStationId"/>
	        <input type="hidden" id="endStationVal"/>
	    </div>
	    <div class="swop-icom"></div>
	    <ul id="stationList"></ul>
	    </form>
	</div>
	
    <div id="to_load_more"></div>
    
    <div id="tabPace">
    	<a class="bus on" href="javascript:void(0);"><em></em>坐车</a>
    	<a class="user" href="javascript:void(0);"><em></em>我的</a>
    </div>
    
    <%@include file="template/tipView.jsp" %>
    <%@include file="template/loadingView.jsp" %>
	<input type="hidden" id="currentPage" value="0"/>
	<input type="hidden" id="cityName" value="${cityName}"/>
	<input type="hidden" id="passengerIpt" value='${passenger}'/>
	<input type="hidden" id="lon" value='${lon}'/>
	<input type="hidden" id="lat" value='${lat}'/>
</body>
</html>
<%@include file="template/footJavascript.jsp" %>
<script>
	// 渠道类型；
	var channelType = '${channel}', isLineLoading = false, isStationListShow = false;
	channelType && localStorage.setItem("channel", channelType);
	var openId = '${openId}', isGeolocation = true; deflPlaceholder = '当前位置';
	openId && localStorage.setItem("openId", openId);
	// 处理用户
	var passenger = $('#passengerIpt').val();
	if (passenger) {
		localStorage.setItem("MyUserInfo", passenger);
	} else {
		localStorage.removeItem('MyUserInfo');
	}
	
	var myCity = new BMap.LocalCity();
	myCity.get(function (result) {
		if (result && result['name']) {
			$('#cityName').val(result['name']);
		}
	});
	
	// 页面逻辑
	var searchStationInterval, searchStationTimer, searchLineTimer;
	/** 启动搜索的轮询； */
	function onSearchStation(elem) {
		isStationListShow = true;
		elem = $(elem);
		if (searchLineTimer)
			clearTimeout(searchLineTimer);
		searchLineTimer = null;
		var keyword = elem.val(), listSpace = $('#stationList'), key = elem.attr('id'), pos = elem.offset();
		listSpace.css('top', pos.top + 'px');
		listSpace.html('');
		if (searchStationInterval != null) 
			clearInterval(searchStationInterval);
		searchStationInterval = setInterval(function () {
			var cur = elem.val() || '';
			checkSwop();
			if (!cur) {
				listSpace.hide();
				return;
			}
			if (cur && keyword != cur) {
				keyword = cur;
				if (searchStationTimer)
					clearTimeout(searchStationTimer);
				searchStationTimer = setTimeout(function () {
					$.post('${contextPath}/v1/line/getStations', {
						kw: cur,
						city: $('#cityName').val()
					}, function (data) {
						if (!isStationListShow) {
							listSpace.html('');
							listSpace.hide();
							return;
						}
						if (data['a1'] && data.a1 == "100") {
							var optionArr = [], tempA2 = data.a2;
							for(var i = 0, n = tempA2.length; i < n;i ++) {
								var tempItem = tempA2[i], stationInfo = typeof tempItem == 'object' ? tempItem : eval('(' + tempItem + ')'), 
									html = ["<li stationId=\""], id = stationInfo['a1'], name = stationInfo['a1'];
								html.push(id);
								html.push("\" onclick=\"onStationSelected('");
								html.push(name);
								html.push('##');
								html.push(stationInfo['a3']);
								html.push('##');
								html.push(stationInfo['a2']);
								html.push("', '");
								html.push(key);
								html.push("')\">");
								html.push(name);
								html.push("</li>");
								optionArr.push(html.join(''));
							}
							var txt = optionArr.join('');
							listSpace.html(txt);
							listSpace[txt ? 'show' : 'hide']();
						}
					}, 'json');
				}, 800);
			}
		}, 500);
	}
	
	/** 站点选取后的事件； */
	function onStationSelected(station, key) {
		var elem = $('#' + key);
		elem.val(station.substring(0, station.indexOf('##')));
		$('#' + key.replace('Id', 'Val')).val(station);
		clearSearchStation();
	}
	
	/** 清除搜索的轮询； */
	function clearSearchStation() {
		isStationListShow = false;
		setTimeout(function () {
			$('#stationList').hide();
		}, 200);
		if (searchStationInterval) {
			clearInterval(searchStationInterval);
			searchStationInterval = null;
		}
		$('input[type=search]').blur();
		searchLineTimer = setTimeout(function () {
			searchStationInterval = null;
			var cityName = encodeURI(encodeURI($("#cityName").val()));
			loadLineList(cityName, 1);
		}, 200);
		return false;
	}
	
	/**
	 * 加载线路列表
	 * cityName 城市名
	 * currentPage 页码
	 */
	function loadLineList(cityName, currentPage) {
		onLoadingShow('加载线路...');
		if (isLineLoading)
			return;
		isLineLoading = true;
		var url = ["${contextPath}/v1/line/getLines?cityName=", cityName], isStation = false;
		var startStation = $("#startStationVal").val();
		
		if ($("#startStationId").val()) {
			var ssArr = startStation.split('##');
			url.push("&startStation=");
			url.push(ssArr[0]);
			url.push("&lat=");
			url.push(ssArr[1]);
			url.push("&lon=");
			url.push(ssArr[2]);
			isStation = true;
		}

		var endStation = $("#endStationVal").val();
		if ($("#endStationId").val()) {
			var esArr = endStation.split('##');
			url.push("&endStation=");
			url.push(esArr[0]);
			url.push("&elat=");
			url.push(esArr[1]);
			url.push("&elon=");
			url.push(esArr[2]);
			isStation = true;
		}
		// 追加分页信息
		url.push("&currentPage=");
		url.push(currentPage || 0);
		// 经纬度
		if (!isStation) {
			url.push("&lat=");
			url.push(localStorage.getItem('latitude'));
			url.push("&lon=");
			url.push(localStorage.getItem('longitude'));
		}
		leaseGetAjax(url.join(''), "json", function(data) {
			if (data.a1 == "100") {
				$('.section_more').remove();
				var infoData = data.a2, lineList = infoData.list, lineListContent = [];
				if (lineList && lineList.length) {
					for (var i = 0, line; line = lineList[i]; i++) {
						var html = ["<section ontouchstart=\"onTouchClassDetail(event, this);\" class=\"linePanel\" lineid=\""];
						html.push(line.a1);
						html.push("\" splitid=\"");
						html.push(line.a7);
						html.push('\"><div class="section_rotate">小猪巴士</div><table cellpadding="0" cellspacing="0"><tr><td rowspan="2" width="16px" align="center" class="section_line"><em></em></td><td colspan="2"><div>');
						html.push(line.a2);		
						html.push('</div></td><td rowspan="2" width="34%" class="section_buy" align="center"><span>购票</span></td></tr><tr><td colspan="2"><div>');
						html.push(line.a3);
						html.push('</div></td></tr><tr><td colspan="4" class="bottom_line section_time_begin"><ul><li><em></em>发车时间<p>');
						html.push(line.a6);
						html.push('</p></li><li class="section_time_sum"><em></em><span>');
						html.push(line.a5);
						html.push('</span>分钟</li><li class="section_price">￥<span>');
						html.push(line.a4);
						html.push('</span></td></tr></table></section>');
						lineListContent.push(html.join(''));
					}
				}
				var moreElem = $("#to_load_more");
				moreElem.html(lineListContent.join(''));
				moreElem.scrollTop(0);
				$("#currentPage").val(infoData.page);
				onLoadingHide();
			} else {
				alert("内部错误。");
			}
			isLineLoading = false;
	    });
	}
	
	//查看线路详情
	function classDetail(elem) {
		var id = elem.getAttribute('lineid'), sid = elem.getAttribute('splitid'), user = getLoginUser();
		location.href = ["${contextPath}/v1/line/detail?id=", id, '&sid=', sid, '&uid=', user ? user['a2'] : ''].join('');
	}
	
	/** 检测交换是否可以执行； */
	function checkSwop() {
		var startIpt = $("#startStationId"), endIpt = $("#endStationId");
		if (startIpt.val() || endIpt.val()) {
			$('.swop-icom').show();
		} else {
			$('.swop-icom').hide();
			startIpt.attr('placeholder', deflPlaceholder);
			endIpt.attr('placeholder', '点击设置目的地');
		}
	}

	//加载更多数据
	function loadMore(elem) {
		var elem = $(elem);
		if (elem.html() != '加载中...') {
			$('.section_more').html('加载中...');
			var currentPage = $("#currentPage").val(), new_currentPage = parseInt(currentPage) + 1;
			var cityName = encodeURI(encodeURI($("#cityName").val()));
			loadLineList(cityName, new_currentPage);
		}
	}
	
	var detailTouchTime, detailX, detailY;
	function onTouchClassDetail(evt, elem) {
		if (evt['touches'] && evt.touches.length) {
			evt = evt.touches[0];
		}
		detailX = evt.clientX;
		detailY = evt.clientY;
		elem.addEventListener('touchmove', onTouchClassDetailMove, false);
		elem.addEventListener('touchend', onTouchClassDetailEnd, false);
		
		// elem.addEventListener('mousemove', onTouchClassDetailMove, false);
		// elem.addEventListener('mouseup', onTouchClassDetailEnd, false);
		detailTouchTime = new Date().getTime();
	}
	
	function onTouchClassDetailMove(evt) {
		if (evt['touches'] && evt.touches.length) {
			evt = evt.touches[0];
		}
		var mx = detailX - evt.clientX, my = detailY - evt.clientY;
		if (mx > 4 || mx < -4 || my > 4 || my < -4) {
			this.removeEventListener('touchmove', onTouchClassDetailMove, false);
			this.removeEventListener('touchend', onTouchClassDetailEnd, false);
			// this.removeEventListener('mousemove', onTouchClassDetailMove, false);
			// this.removeEventListener('mouseup', onTouchClassDetailEnd, false);
		}
	}
	
	function onTouchClassDetailEnd(evt) {
		this.removeEventListener('touchmove', onTouchClassDetailMove, false);
		this.removeEventListener('touchend', onTouchClassDetailEnd, false);
		// this.removeEventListener('mousemove', onTouchClassDetailMove, false);
		// this.removeEventListener('mouseup', onTouchClassDetailEnd, false);
		if (new Date().getTime() - detailTouchTime < 100)
			classDetail(this);
	}
	
	$(function () {
		$('#stationList').css({
			'max-height': ($(window).height() - 140 >> 1) + 'px'
		});
		
		var startIpt = $("#startStationId"), endIpt = $("#endStationId");
		if (startIpt.val() || endIpt.val()) {
			clearSearchStation();
		} else {
			var lat = $('#lat').val(), lon = $('#lon').val();
			if (lat && lon) {
				localStorage.setItem('latitude', lat);
				localStorage.setItem('longitude', lon);
				$('#startStationId').val('');
				$('#startStationVal').val('');
				clearSearchStation();
			} else {
				onLoadingShow('获取您的位置中...');
				/** 获取用户位置； */
				getPosition(function (pos) {
					if (pos) {
						localStorage.setItem('latitude', pos.latitude);
						localStorage.setItem('longitude', pos.longitude);
						$('#startStationId').val('');
						$('#startStationVal').val('');
						clearSearchStation();
					} else { // 定位超时
						isGeolocation = false;
						alert('位置获取失败，可能您的网络状态不好或者未开启手机定位。');
						onLoadingHide();
						$('#startStationId').attr('placeholder', deflPlaceholder = '点击设置出发点');
					}
				});
			}
		}
		
		$('input[type=search]').bind('keydown', function (evt) {
			if (evt.keyCode == 13) {
				var lists = $('#stationList > li');
				if (lists.length) {
					lists.get(0).click();
				} else {
					var elem = $(this);
					elem.val('');
					$('#' + elem.attr('id').replace('Id', 'Val')).val('');
				}
				clearSearchStation();
			}
		});
		
		var vh = $(window).height();
		$('#to_load_more').css({
			maxHeight: vh - 124 - 55 + 'px'
		});
		
		var dragSpace = document.getElementById('to_load_more'), dragBeginY;
		dragSpace.addEventListener('touchstart', function (evt) {
			var stopEvt = evt;
			if (evt['touches'] && evt.touches.length) {
				evt = evt.touches[0];
			}
			dragBeginY = evt.clientY;
			stopEvent(stopEvt);
		}, false);
			
		dragSpace.addEventListener('touchmove', function (evt) {
			var stopEvt = evt;
			if (evt['touches'] && evt.touches.length) {
				evt = evt.touches[0];
			}
			this.scrollTop += dragBeginY - evt.clientY;
			dragBeginY = evt.clientY;
			stopEvent(stopEvt);
		}, false);
		
		
		$('.linePanel .stations').live('click', function (evt) {
			loadStation(this.getAttribute('lineid'));
			return stopEvent(evt);
		});
		
		/** 交换操作； */
		$('.swop-icom').click(function () {
			var startIpt = $("#startStationId"), endIpt = $("#endStationId");
			var temp = startIpt.val(), temp1 = endIpt.val();
			startIpt.val(temp1);
			endIpt.val(temp);
			
			if (temp) {
				startIpt.attr('placeholder', deflPlaceholder);
			} else {
				endIpt.attr('placeholder', isGeolocation ? deflPlaceholder : '点击设置目的地');
			}
			
			startIpt = $("#startStationVal"), endIpt = $("#endStationVal");
			temp = startIpt.val(), temp1 = endIpt.val();
			startIpt.val(temp1);
			endIpt.val(temp);
			clearSearchStation();
		});
		
		var url = location.href;
		$('.user').attr('href', '${contextPath}/v1/user/home' + url.substring(url.indexOf('?')));
		
		checkSwop();
	});
</script>