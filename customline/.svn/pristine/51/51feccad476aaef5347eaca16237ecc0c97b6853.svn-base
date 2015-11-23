<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商管理-车辆位置</title>
<jsp:include page="../resource.jsp"/>
<style>
	.search-line {
		float: right;
    	overflow: auto;
    	width: 124px;
    	background-color: #FFF;
    	line-height: 20px;
    	height: 20px;
	}
	#opts {
		clear: both;
		float: right;
    	overflow: auto;
    	width: 128px;
    	background-color: #FFF;
    	border-left: 2px solid #999;
    }
	#opts ul li { line-height: 28px; padding: 4px 8px; border-bottom: 1px solid #ccc; }
</style>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;车辆最后位置</p></div>
	<div class="mt10 ml20 mr20 black1">
		<input class="search-line" type="text" placeholder="线路名称搜索">
		<div id="opts">
			<ul>
    		<s:iterator value="#request.cars" var="p" status="v">
    			<li id="bus_${p.lineName}" onclick="showMap('${p.lineBaseId}', '${p.vehicleNumber}');" tip="${p.orderStartTime}"><a href="javascript:void(0);">${p.lineName}</a></li>
    		</s:iterator>
    		</ul>
    	</div>
    	<div id="map"></div>
	</div>
</body>
</html>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=pK2ve3bnmdyRFzGNuz6M9VNe"></script>
<script type="text/javascript">
	var map, convertor = new BMap.Convertor(), busIcon;
	var carGps;
	function showMap1(lineId, vehicle) {
		console.log("showMap1 "+lineId+","+vehicle);
		$.post('${contextPath}/line/getLineGps.action', {
			lineId: lineId, // '150702151420408708', 
			vehicle: vehicle //'粤BK2267'
		}, function (res) {
			if (!res){
				alert('数据获取失败！');
				clearInterval(carGps);
			}
			if (res['lat'] && res['lon']) {
				map.clearOverlays();
        		convertor.translate([new BMap.Point(res['lon'], res['lat'])], 1, 5, function (bdPoint) {
					if (bdPoint['status'] === 0) {
						bdPoint = bdPoint['points'][0];
						var p = new BMap.Point(bdPoint['lng'], bdPoint['lat']);
						// var marker = new BMap.Marker(p, {icon: busIcon});
						var label = new BMap.Label("车牌:"+vehicle+"<br/>时间:"+res['time']+"<br/>速度:"+res['speed'], {offset:new BMap.Size(22, 4)});
						var marker = new BMap.Marker(p);
						marker.setLabel(label);
						map.addOverlay(marker);
						map.panTo(p);
					}
				});
			} else {
				alert('车辆未启动，或者GPS未定位！');
				clearInterval(carGps);
			}
		}, 'json');
	}
	
	function showMap(lineId, vehicle){
		clearInterval(carGps);
		console.log("showMap "+lineId+","+vehicle);
		showMap1(lineId, vehicle);
		carGps = setInterval(function(){
			showMap1(lineId, vehicle);
		},15000);
	}
	
	(function () {
		
		var vw = $(window).width() - 180, vh = $(window).height() - 48;
		
		$('#opts').height(vh - 24);
		$('#map').css({
			width: vw + 'px',
			height: vh + 'px'
		});
		
		$('.search-line').on('keypress', function (evt) {
			if (evt.keyCode === 13) {
				$('#bus_' + $(this).val()).click();
			}
		});
		
		map = new BMap.Map("map");
		map.enableScrollWheelZoom(true);
		map.centerAndZoom(new BMap.Point(114.005974, 22.546054), 15);
		
		//小车图片(sjx: 太丑还没用...)
		busIcon = new BMap.Icon("http://img.pig84.com/download/100000000002", new BMap.Size(24, 24), {
			imageOffset: new BMap.Size(0, 0)    //图片的偏移量。为了是图片底部中心对准坐标点。
		});
	})();
</script>