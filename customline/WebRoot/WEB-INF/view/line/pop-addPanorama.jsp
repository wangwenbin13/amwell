<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="pop black1 w960" id="popMapPage">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">站点全景</div>
        <div class="pop-t-r fr">
        	<a href="javascript:void(0);" onclick="saveAndToMap();" class="pop-close fr mt4 mr4"></a>
        </div>
    </div>
    <div class="pop-main p10">
        <div style="font-weight:bold;text-align:center;font-size:18px;color:red;">
          <span style="margin-right:10px;margin-left:50px;">当前经纬度:</span>
          <span style="margin-right:10px;" class="lnginfo">${lng}</span>
          <span srtle="margin-right:20px;" class="latinfo">${lat}</span>
          <span style="margin-right:20px;font-size:14px;color:green;">(点击右上角叉返回地图视图，点击确定保存当前经纬度)</span>
        </div>
        <div>
        	<div id="panorama" style="margin:10px;width:920px;height:600px;background: #fff;"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
var lng = "${lng}";
var lat = "${lat}";
var panorama;
var labelPosition;
var label;
var labelOptions;

$(document).ready(function(){
	createPanorama();
});

function createPanorama(){
	panorama = new BMap.Panorama('panorama');
	
	labelPosition = new BMap.Point(lng,lat);
	
	panorama.setPosition(labelPosition);
	labelOptions = {
		position: labelPosition
	};//设置标注点的经纬度位置和高度
	label = new BMap.PanoramaLabel('自定义标注-天安门广场', labelOptions);
	label.addEventListener('click', function() { //给标注点注册点击事件
	    panorama.setPov({  //修改点的视角
	      pitch: 10, 
	      heading: 14
	    });
	});
	//panorama.addOverlay(label);//在全景地图里添加该标注
	
	 
	panorama.addEventListener('position_changed', function(e){ //全景位置改变事件
		   panoramaCallBack(e);
	});
	
}

function panoramaCallBack(e){ //事件回调函数
	if (e.type=='onposition_changed') {
		var position = panorama.getPosition();
		lng = position.lng;
		lat = position.lat;
		$(".lnginfo").html(lng);
		$(".latinfo").html(lat);
		labelPosition = new BMap.Point(lng,lat);
	}
}

function saveAndToMap(){
	var id = "${id}";
	closePopMapPage();
	$("#topHide", parent.window.document).show();
	$("#leftHide", parent.window.document).show();
	var url = "../publishLine/addLineMap.action?id="+id+"&lng="+lng+"&lat="+lat;
	$("#showPage").load(url); 
	$("#mainBody").show();
}

//关闭增加弹出层页面
function closePopMapPage(){
    $("#popMapPage").hide();
    $("#mainBody").hide();
    $("#topHide",parent.window.document).hide();
    $("#leftHide",parent.window.document).hide();
    $("#searchText").val('');
}
</script>