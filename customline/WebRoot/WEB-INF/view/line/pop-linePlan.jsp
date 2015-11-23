<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pop black1 w960" id="popMapPage">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">位置点搜索</div>
        <div class="pop-t-r fr">
        	<a href="javascript:void(0);" onclick="closePopMapPage();" class="pop-close fr mt4 mr4"></a>
        </div>
    </div>
    <div class="pop-main p10">
        <div>
        	<div id="lineMap" style="margin:10px;width:920px;height:600px;background: #fff;"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
var map;
var stationArr = new Array();

var gc = new BMap.Geocoder();//地址解析类

$(document).ready(function(){
	var stationArrJSONString = "${stationArr}";
	stationArr = JSON.parse(stationArrJSONString);
	createMap();
});

// 关闭增加弹出层页面
function closePopMapPage(){
    $("#popMapPage").hide();
    $("#mainBody").hide();
    $("#topHide",parent.window.document).hide();
    $("#leftHide",parent.window.document).hide();
    $("#searchText").val('');
}

// 百度地图API功能
function createMap(){
	try{
		map = new BMap.Map("lineMap");
	if(stationArr!=null&&stationArr.length>0){
		var pointArr = new Array();
		for(var index=0;index<stationArr.length;index++){
			var station = stationArr[index];
			var point = new BMap.Point(station.lng, station.lat);
			pointArr[index] = point;
	    }
	    map.centerAndZoom(pointArr[0],12);
    	var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
    	var middleArr = new Array();
    	if(pointArr.length>2){
    		middleArr = pointArr.slice(1,pointArr.length-1);
    	}
    	var option = new Object();
    	option.waypoints=middleArr;
    	driving.search(pointArr[0],pointArr[pointArr.length-1],option);
		// 创建折线
		//var polyline = new BMap.Polyline(pointArr, {strokeColor:"red", strokeWeight:10, strokeOpacity:0.5});
		// 增加折线
        //map.addOverlay(polyline);  
        for(var j=0;j<stationArr.length;j++){
            var station = stationArr[j];
            var pointItem = new BMap.Point(station.lng,station.lat);
        	var markerItem = new BMap.Marker(pointItem);
        	var opts = {
			  position : pointItem,// 指定文本标注所在的地理位置
			  offset   : new BMap.Size(0,-30)//设置文本偏移量
			}
   			// 创建文本标注对象
   			var label = new BMap.Label(station.stationName, opts);  
   			label.setStyle({
   				 color : "green",
   				 fontSize : "12px",
   				 height : "20px",
   				 lineHeight : "20px",
   				 borderColor:"green",
   				 fontFamily:"微软雅黑"
   			 });
   			markerItem.setLabel(label);
   			map.addOverlay(markerItem);
        } 
	}else{
		map.centerAndZoom(new BMap.Point(116.404, 39.915),10);
		//根据IP获取城市
		var currCity = new BMap.LocalCity();
		currCity.get(getCityName);
	}
	// 添加默认缩放平移控件
	map.addControl(new BMap.NavigationControl());
	// 添加默认比例尺控件
	map.addControl(new BMap.ScaleControl());
	// 添加默认缩略地图控件
	map.addControl(new BMap.OverviewMapControl());
	// 添加默认地图控件
	map.addControl(new BMap.MapTypeControl());
	// 启用滚轮放大缩小，默认禁用
	map.enableScrollWheelZoom(); 
	// 启用地图惯性拖拽，默认禁用
	map.enableContinuousZoom();
	}catch(e){
		alert('错误' + e.message + '发生在' +   e.lineNumber + '行'); 
	}
}

function getCityName(result){
    var cityName = result.name;
    map.setCenter(cityName);
}

//对应不同的Marker创建不同的InfoWindow
function createInfoWindow(title,address,lat,lng){
	var opts = {  
      width : 250     //信息窗口宽度     //信息窗口高度  
    } 
	$("#sureMarker").remove();
	var sContent = "<div class='mt10'>地址："+address+"</div><p class='t-c' id='sureMarker'><input type='button' class='btn-gray fr mr4 mt4' value='确定' onclick='getCurrMarkerInfo(\""+title+"\",\""+address+"\",\""+lat+"\",\""+lng+"\")'></p>";  
	var infoWindow = new BMap.InfoWindow(sContent,opts,{enableMessage: false });
	infoWindow.setTitle("<label style=\"font: bold 14px/16px arial,sans-serif;margin: 0;color: #cc5522;white-space: nowrap;width: 210px;overflow: hidden;\">" + title + "</label>");
	infoWindow.setContent(sContent);
	return infoWindow;
}
</script>