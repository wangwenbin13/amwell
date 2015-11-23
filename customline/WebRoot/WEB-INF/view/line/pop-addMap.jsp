<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="pop black1 w960" id="popMapPage">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">位置点搜索</div>
        <div class="pop-t-r fr">
        	<a href="javascript:void(0);" onclick="closePopMapPage();" class="pop-close fr mt4 mr4"></a>
        </div>
    </div>
    <div class="pop-main p10">
        <div>
        	<input type="text" class="r-input w35p gray3" id="searchText" placeholder="请输入位置点搜索"/>
        	<span class="fl w106 t-r">所属城市：<em class="red1">*</em></span>
			<select name="provinceCode" id="provinceCodePop" class="fl r-input mr10" onchange="loadCity(this.value);">
			    <option value="">--选择省份--</option>
			    <s:iterator value="#request.proSysAreas" var="proSysArea">
			        <option value="${proSysArea.arearCode}">${areaName}</option>
			    </s:iterator>
			</select>
			<span id="provinceCodeError" class="errorTip"></span>
			<select name="cityCode" id="cityCodePop" class="fl r-input mr10" onchange="changeCity();">
				<option value="">--选择城市--</option>
			</select>
        	<input type="submit" class="btn-blue4" value="查找" onclick="localSearch()"/>
        	<div style="float:right;font-size:12px;">左鼠标选择，右鼠标取消</div>
        	<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
        </div>
        <div>
        	<div id="lineMap" style="margin:10px;width:920px;height:600px;background: #fff;"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
var map;
//地图随处添加marker
var markersAddress = [];
var markersArr = [];
var localCityName;

var gc = new BMap.Geocoder();//地址解析类
var ac;

$(document).ready(function(){
	var provinceNameLine = $("#provinceCode").val();
	if(provinceNameLine!=null||provinceNameLine!=""){
		$("#provinceCodePop").val(provinceNameLine);
		loadCity2(provinceNameLine);
	}
	try{
		createMap();
	}catch(e){
        alert(e.name+":"+e.message);
	}
});

//根据省份加载城市
function loadCity(value){
	$("#cityCodePop").empty();
	$("#cityCodePop").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].areaName+"'>"+data[i].areaName+"</option>").appendTo($("#cityCodePop"));
			})
	    });
	}
}

//根据省份加载城市
function loadCity2(value){
	$("#cityCodePop").empty();
	$("#cityCodePop").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].areaName+"'>"+data[i].areaName+"</option>").appendTo($("#cityCodePop"));
			})
	    });
	}
}

function changeCity(){
	ac.setLocation($("#cityCodePop").val());
}

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
	var lng = "${lng}";
	var lat = "${lat}";
	var isChina = false;
	if(lng!=null&&lng!=""&&lat!=null&&lat!=""){
      if(lng > 73 && lng < 135 && lat > 3 && lat < 53){
        isChina = true;
      }
	}
	map = new BMap.Map("lineMap");
	if(isChina){
		var defaultPoint = new BMap.Point(lng, lat);
		map.centerAndZoom(defaultPoint,12);
		var defaultMarker = new BMap.Marker(defaultPoint);
		gc.getLocation(defaultPoint, function(rs){  
			var addComp = rs.addressComponents;  
			var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;  
			var title = rs.address;
			var infoWindow = createInfoWindow(title,address,defaultPoint.lat,defaultPoint.lng);
			defaultMarker.openInfoWindow(infoWindow);
			defaultMarker.addEventListener("rightclick", function(e){  
				  defaultMarker.remove();
			}); 
		}); 
		map.addOverlay(defaultMarker);
	}else{
		map.centerAndZoom(new BMap.Point(116.404, 39.915),12);
	}
	//根据IP获取城市
	var currCity = new BMap.LocalCity();
	currCity.get(getCityName);
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
}

function getCityName(result){
	var cityName = result.name;
	
	var cityNameLine = $("#cityCode").find("option:selected").text(); 
	if(cityNameLine!=null){
		cityName = cityNameLine;
		$("#cityCodePop").val(cityNameLine);
	}
    localCityName = cityName;
    map.setCenter(cityName);
    $("#cityCodePop").val(localCityName);
 // 创建自动提示，下拉搜索功能  建立一个自动完成的对象
	ac = new BMap.Autocomplete({
		"input" : "searchText",
		"location" : localCityName
	});
 
	// 鼠标放在下拉列表上的事件
	ac.addEventListener("onhighlight", function(e) {  
		var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
		document.getElementById("searchResultPanel").innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
		var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		document.getElementById("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		$("#searchText").val(_value.business);
		localSearch();
	});
}

// 搜索功能
function localSearch(){
	// 清除地图上所有标记
	map.clearOverlays(); 
	var searchText = $("#searchText").val();
	if($("#cityCodePop").val()!=null&&$("#cityCodePop").val()!=""){
        localCityName = $("#cityCodePop").val();
	}
	if(localCityName==""||localCityName==null){
		localCityName = "全国";
	}
	var local = new BMap.LocalSearch(localCityName, {
		renderOptions: {
	    map: map,
	    autoViewport: true,
	    selectFirstResult: false
	  }
	});
	var keywords = searchText;
	keywords = keywords.replace("公交车站","公交站");
	local.search(keywords);
	local.setMarkersSetCallback(function(pois){
	     for(var i=0;i<pois.length;i++){
	         pois[i].marker.addEventListener("infowindowopen", function(e){
	              var title = e.currentTarget.getTitle();
				  var address = $(".BMap_bubble_content").find("td").eq(1).text();
				  var lat = e.currentTarget.getPosition().lat;
				  var lng = e.currentTarget.getPosition().lng;
	              $("#sureMarker").remove();
	              $(".BMap_bubble_max_content").after("<p class='t-c' id='sureMarker'><input type='button' class='btn-gray fr mr4 mt4' value='确定' onclick='getCurrMarkerInfo(\""+title+"\",\""+address+"\",\""+lat+"\",\""+lng+"\")'></p>");
	         });  
	     }
	});
}

// 设置地图下拉框
function setMapSelect(){
	if($(".tangram-suggestion-main").length > 0){
		$(".tangram-suggestion-main").css({"z-index":"100"});
		// 单击获取点击的经纬度
		map.addEventListener('click', clickHandler);
	}else{
		setTimeout('setMapSelect()',200);
	}
}

setTimeout('setMapSelect()',200);

//获取点击的markert信息,给input框赋值
function getCurrMarkerInfo(title,address,lat,lng){
	var id = "${id}";
	var station = $("#stationName"+id);
	if(lat==null||lat==undefined||lat==""){
		parent.parent.showRturnTip("请重新选择经纬度",false); 
		return;
	}
	if(lng==null||lng==undefined||lng==""){
		parent.parent.showRturnTip("请重新选择经纬度",false); 
		return;
	}
	// 判断是否所选点为中国范围内
	if(lng<73||lng>135||lat<3||lat>53){
		parent.parent.showRturnTip("请选择中国范围内的坐标点",false); 
		return;
    }
	station.attr("lng",lng);
	station.attr("lat",lat);
	station.removeAttr("readonly");
	var searchText = $("#searchText").val();
	if(searchText==null||searchText==""){
		if(station.val()==""){
			station.val(title.replace("-",""));
		}
	}else{
		if(station.val()==""){
			station.val(searchText.replace("-",""));
		}
	}
	station.removeAttr("disabled");
	closePopMapPage();
}

// 点击事件处理函数
var clickHandler = function(e){
	// 清除地图上所有标记
	map.clearOverlays();
	markersArr.push(e.point);
	var point = new BMap.Point(e.point.lng, e.point.lat); 
	// 初始化地图标记   
	var marker = new BMap.Marker(point); 
	// 将标记添加到地图
	map.addOverlay(marker); 
	// 地址解析类
	gc.getLocation(e.point, function(rs){  
		var addComp = rs.addressComponents;  
		var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;  
		var title = rs.address;
		var infoWindow = createInfoWindow(title,address,e.point.lat,e.point.lng);
		marker.openInfoWindow(infoWindow);
		marker.addEventListener("rightclick", function(me){  
			marker.remove();
		}); 
	});  
}

//对应不同的Marker创建不同的InfoWindow
function createInfoWindow(title,address,lat,lng){
	var id = "${id}";
	var opts = {  
      width : 250 ,    //信息窗口宽度     //信息窗口高度  
      height:100
    } 
	$("#sureMarker").remove();
	var sContent = "<div class='mt10'>地址："+address+"</div>"+
	       "<p class='t-c' id='sureMarker'>"+
	       "<input type='button' class='btn-gray fr mr4 mt4' style='margin-bottom:20px;' value='确定' onclick='getCurrMarkerInfo(\""+title+"\",\""+address+"\",\""+lat+"\",\""+lng+"\")'>"+
	       "<input type='button' class='btn-gray fr mr4 mt4' style='margin-bottom:20px;' value='全景视角' onclick='goPanorama(\""+id+"\",\""+lng+"\",\""+lat+"\")'/>"+
	       "</p>";  
	var infoWindow = new BMap.InfoWindow(sContent,opts,{enableMessage: false });
	infoWindow.setTitle("<label style=\"font: bold 14px/16px arial,sans-serif;margin: 0;color: #cc5522;white-space: nowrap;width: 210px;overflow: hidden;\">" + title + "</label>");
	infoWindow.setContent(sContent);
	return infoWindow;
}

function goPanorama(id,lng,lat){
	closePopMapPage();
	$("#topHide", parent.window.document).show();
	$("#leftHide", parent.window.document).show();
	var url = "../publishLine/addPanorama.action?id="+id+"&lng="+lng+"&lat="+lat;
	$("#showPage").load(url); 
	$("#mainBody").show();
}
</script>