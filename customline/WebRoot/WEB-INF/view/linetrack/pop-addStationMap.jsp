<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path1 = request.getContextPath();
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营平台－创建线路  地图弹出层页面</title>
<!-- 操作地图的js --> 
</head>
<body>
<div class="pop black1 w960" id="popMapPage">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">位置点搜索</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopMapPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10 clearfix">
        
        <div>
        	<input type="hidden" class="r-input w35p gray3" id="searchText" value="请输入位置点搜索"/>
        	<input type="hidden" class="btn-blue4" value="查找" onclick="localSearch()"/>
        	<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
        </div>
        <!--map-->
        <div id="lineMap" style="margin-bottom:10px;width:940px;height:600px;background: #fff;"></div>
        <div class="fr">
        	<input type="text" class="btn-blue4" value="保存" onclick="saveStationLine()"/>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">

//地图随处添加marker
var markersAddress = [];
//经度数组
var markersArrLng = [];
//纬度数组
var markersArrLat = [];

var map;

var stationArray = new Array();

var endStation;

var stationIndex=1;

var lineBaseId = "${lineBaseId}";
var startStationId = "${startStationId}";
var endStationId = "${endStationId}";

$(document).ready(function(){
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
		}).blur(function(){  
			if($(this).val() == ""){
			    $(this).val(txt);  
			    $(this).removeClass("gray2").addClass("gray3");
			}
		});  
	});
	$.ajax({
      url:"<%=basePath1%>lineTrack/loadStationTrackMap.action",
      data:{"lineBaseId":lineBaseId,"startStationId":startStationId,"endStationId":endStationId},	
      dataType:"json",	
	  cache:false,
	  async : false,
	  type : 'post',
      success:function(data){
         for(var i=0;i<data.length-1;i++){
             var station =new Object();
             station.stationInfoId=data[i].stationInfoId;
             station.lng = data[i].lon;
             station.lat = data[i].lat;
             station.sort = i+1;
             stationArray[i] = station;
         }
         for(var i=0;i<stationArray.length;i++){
            //showStation(stationArray[i]);
         }
         endStation = new Object();
         endStation.stationInfoId=data[data.length-1].stationInfoId;
         endStation.lng = data[data.length-1].lon;
         endStation.lat = data[data.length-1].lat;
         endStation.sort= 1000;
         //showStation(endStation);
         createMap();
      },
      error:function(){
          alert("后台错误");
      }
    });
});

function showStation(station){
	alert("[sort:"+station.sort+",lng:"+station.lng+",lat:"+station.lat+"]");
}


//关闭增加弹出层页面
function closePopMapPage()
{
    $("#popMapPage").hide();
    $("#mainBody").hide();
    $("#topHide",parent.window.document).hide();
    $("#leftHide",parent.window.document).hide();
    //window.document.location.reload();
    //$("#searchText").val('');
    loadDate();
}


// 百度地图API功能
function createMap()
{
	map = new BMap.Map("lineMap");            // 创建Map实例
	var firstPoint = new BMap.Point(stationArray[0].lng, stationArray[0].lat);
	var endPoint = new BMap.Point(endStation.lng,endStation.lat);
	//var middleLng = (stationArray[0].lng-endStation.lng)>0?(stationArray[0].lng-endStation.lng)/2+endStation.lng:(endStation.lng-stationArray[0].lng)/2+stationArray[0].lng;
	//var middleLat = (stationArray[0].lat-endStation.lat)>0?(stationArray[0].lat-endStation.lat)/2+endStation.lat:(endStation.lat-stationArray[0].lat)/2+stationArray[0].lat;
	map.centerAndZoom(firstPoint,12);
	// 添加默认缩放平移控件
	map.addControl(new BMap.NavigationControl());
	// 添加默认比例尺控件
	map.addControl(new BMap.ScaleControl());
	// 添加默认缩略地图控件
	map.addControl(new BMap.OverviewMapControl());
	// 添加默认地图控件
	map.addControl(new BMap.MapTypeControl());
	map.enableScrollWheelZoom(); // 启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();// 启用地图惯性拖拽，默认禁用

	//标记已有的标注
	markMapExit();
}



//根据IP获取城市
var currCity = new BMap.LocalCity();
currCity.get(getCityName);

function getCityName(result){
    var cityName = result.name;
    map.setCenter(cityName);   //关于setCenter()可参考API文档---”传送门“
}

//搜索功能
function localSearch()
{
	map.clearOverlays(); //清除地图上所有标记
	//var searchText = $("#searchText").val();
	var local = new BMap.LocalSearch("全国", {
	  renderOptions: {
	    map: map,
	    autoViewport: true,
	    selectFirstResult: false
	  }
	});
	
	local.search(searchText);
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

//创建自动提示，下拉搜索功能
var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
	{"input" : "searchText",
	"location" : map
});


//设置地图下拉框
function setMapSelect()
{
	if($(".tangram-suggestion-main").length > 0)
	{
		//$("#searchText").val("请输入位置点搜索");
		$(".tangram-suggestion-main").css({"z-index":"100"});
		//单击获取点击的经纬度
		map.addEventListener('click', clickHandler);
	}
	else
	{
		setTimeout('setMapSelect()',200);
	}
}

setTimeout('setMapSelect()',200);

ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
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
	//setPlace();
	localSearch();
});




//获取点击的markert信息,给input框赋值
function getCurrMarkerInfo(title,address,lat,lng)
{
	
	var id = "<%=request.getParameter("id")%>";
	$("#"+id).val(title).removeClass("gray3").addClass("gray2");
	$("#"+id+"ErrTip").remove();
	//经纬度赋值
	if(id.indexOf("Start")!=-1){
	   $("#lati"+id.substring(id.indexOf("Start"))).val(lat).removeClass("gray3").addClass("gray2");
	   $("#loni"+id.substring(id.indexOf("Start"))).val(lng).removeClass("gray3").addClass("gray2");
	   
	}
	if(id.indexOf("End")!=-1){
	   $("#lati"+id.substring(id.indexOf("End"))).val(lat).removeClass("gray3").addClass("gray2");
	   $("#loni"+id.substring(id.indexOf("End"))).val(lng).removeClass("gray3").addClass("gray2");
	   
	}
	if(id.indexOf("Access")!=-1){
	   $("#lati"+id.substring(id.indexOf("Access"))).val(lat).removeClass("gray3").addClass("gray2");
	   $("#loni"+id.substring(id.indexOf("Access"))).val(lng).removeClass("gray3").addClass("gray2");
	  
	}
	closePopMapPage();
}



//标记已有的标注
function markMapExit(){
    for(var i=0;i<stationArray.length;i++){
        createMarker(stationArray[i].lng,stationArray[i].lat,i+1);
        stationIndex = i+1;
    }
    createMarker(endStation.lng,endStation.lat,5000);

}

function createMarker(lng,lat,index){
	var point = new BMap.Point(lng,lat);  
	var marker = new BMap.Marker(point); //初始化地图标记  
	var label;
	if(index==1){
		label = new BMap.Label("起始点",{offset:new BMap.Size(20,-10)});
	}else if(index==5000){
		label = new BMap.Label("终止点",{offset:new BMap.Size(20,-10)});
	}else{
		label = new BMap.Label(index-1,{offset:new BMap.Size(20,-10)});
		marker.addEventListener("rightclick", function(e){
        	//右键移除事件  
    		map.removeOverlay(this);
    		var station;
    		for(var i=0;i<stationArray.length;i++){
                if(stationArray[i].lng==this.point.lng&&stationArray[i].lat==this.point.lat){
                   station = stationArray[i];
                }
        	}
    		stationArray.remove(station);
    		for(var i=0;i<stationArray.length;i++){
               //showStation(stationArray[i]);
        	}
		});
	}
	marker.setLabel(label);
	//marker.enableDragging(); //标记开启拖拽  
	map.addOverlay(marker); //将标记添加到地图
}



var clickHandler = function(e)
{
	if(e.overlay)
	{
		return;
	}
	//地图上点标注的时候添加对应的经纬度
	markersArrLng.push(e.point.lng);
	markersArrLat.push(e.point.lat);


	
	var point = new BMap.Point(e.point.lng, e.point.lat);  
	var marker = new BMap.Marker(point); //初始化地图标记  
	//marker.enableDragging(); //标记开启拖拽  
	var label = new BMap.Label(stationIndex,{offset:new BMap.Size(20,-10)});
	marker.setLabel(label);
	map.addOverlay(marker); //将标记添加到地图
	var station = new Object();
	station.lng = e.point.lng;
	station.lat = e.point.lat;
	station.sort = stationIndex+1;
	stationIndex = station.sort;
	stationArray[stationArray.length] = station;
	for(var i=0;i<stationArray.length;i++){
      //showStation(stationArray[i]);
    }
	var gc = new BMap.Geocoder();//地址解析类
	gc.getLocation(e.point, function(rs){  
		/* 显示地址信息
		var addComp = rs.addressComponents;  
		var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;  
		var title = rs.address;
		var iw = createInfoWindow(title,address,e.point.lat,e.point.lng);
		marker.openInfoWindow(iw);
		*/
		marker.addEventListener("click", function(e){  
			   // 移除地图click事件
			  
			   if($("#sureMarker").length > 0)
			   {
				  $("#sureMarker").remove();
			   }
			   //marker.openInfoWindow(iw);
		}); 
		marker.addEventListener("rightclick", function(e){
        	//右键移除事件  
    		map.removeOverlay(this);
    		var station;
    		for(var i=0;i<stationArray.length;i++){
                if(stationArray[i].lng==this.point.lng&&stationArray[i].lat==this.point.lat){
                   station = stationArray[i];
                }
        	}
    		stationArray.remove(station);
    		for(var i=0;i<stationArray.length;i++){
               //showStation(stationArray[i]);
        	}
		});
		/* 
		iw.addEventListener("open", function () {
			// 移除地图click事件
		   
		});
		iw.addEventListener("close", function () {
			// 添加地图click事件
			
		});
		*/
		map.addEventListener("addoverlay",function(e){

	    });
	});  
}

//保存地图上面的标注点
function saveStationLine(){
	var json_array_data = JSON.stringify(stationArray);
	var json_end_station = JSON.stringify(endStation);
	$.ajax({
		url:"lineTrack/saveStationMap.action",
		data:{"lineBaseId":lineBaseId,"stationInfoId":startStationId,"jsonArrayData":json_array_data,"jsonEndStation":json_end_station},		
		cache:false,
		async : false,
		type : 'post',
		success:function(data){
			if("success"==data){
				parent.parent.showRturnTip("保存成功",true);
				closePopMapPage();
			}else{
				parent.parent.showRturnTip("保存失败",false);
			}
		},
		error:function(){
		}
	});
}

//对应不同的Marker创建不同的InfoWindow
function createInfoWindow(title,address,lat,lng)
{
	var opts = {  
      width : 250     //信息窗口宽度     //信息窗口高度  
    } 
	$("#sureMarker").remove();
	var sContent = "<div class='mt10'>地址："+address+"</div><p class='t-c' id='sureMarker'><input type='button' class='btn-gray fr mr4 mt4' value='确定' onclick='getCurrMarkerInfo(\""+title+"\",\""+address+"\",\""+lat+"\",\""+lng+"\")'></p>";  
	var iw = new BMap.InfoWindow(sContent,opts,{enableMessage: false });
	iw.setTitle("<label style=\"font: bold 14px/16px arial,sans-serif;margin: 0;color: #cc5522;white-space: nowrap;width: 210px;overflow: hidden;\">" + title + "</label>");
	iw.setContent(sContent);
	return iw;
}

//用于查找指定的元素在数组中的位置，即索引
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) return i;
	}
	return -1;
};

//然后使用通过得到这个元素的索引，使用js数组自己固有的函数去删除这个元素
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
</script>