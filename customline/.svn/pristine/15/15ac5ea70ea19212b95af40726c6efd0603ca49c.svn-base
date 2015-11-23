<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路详情</title>
<jsp:include page="../resource.jsp"/>
<!-- 操作地图的js --> 
<script src="http://api.map.baidu.com/api?v=1.3" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
</head>

<body>

<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div id="showPagePic" class="showPage"></div>
<!-- 地图弹出层  -->
<div id="showPage1" class="showPage hide">
	<div class="pop black1 w960" id="popMapPage">
	    <div class="pop-t">
	    	<div class="pop-t-l fl fw white">位置点搜索</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopMapPage();" class="pop-close fr mt4 mr4"></a></div>
	    </div>
	    <div class="pop-main p10">
	        <div>
	        	<input type="text" class="r-input w35p gray3" placeholder="请输入位置点搜索" id="searchText" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onblur="if(!value){value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}"/>
	        	<input type="submit" class="btn-blue4" value="查找" onclick="localSearch()"/>
	        	<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
	        </div>
	        <div>
	        	<!--map-->
	        	<div id="lineMap" style="margin:10px;width:920px;height:600px;background: #fff;"></div>
	        </div>
	    </div>
	</div>
</div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;线路列表<a class="display-ib btn ml10" href="javascript:void(0)" onclick="goBackToList();">返回</a><span class="blue1 ml25"></span></p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
          
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(0)">线路详情</a></li>
                    <li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(1)">调度信息</a></li>
                    <li><a href="javascript:void(0)" class="f14 blue2" onclick="changePage(2)">订购人数</a></li>
                </ul>
            </div>
            <div class="tab_box">
            	<iframe frameborder="0" id="loadPage" name="loadPage" src="getLinesDetail.action" width="100%"></iframe>
            </div>
            
          </div>
           
        </div>
</body>
</html>
<script type="text/javascript">
var oldMarker;

//tab 切换
$(function(){
	var $div_li =$(".table-title ul li");
	$div_li.click(function(){
		$(this).addClass("on").siblings().removeClass("on"); 
	});
	
	//iframe高度
	$("#loadPage").css("min-height",$(window).height()-101+"px");
	$(window).resize(function(){
		$("#loadPage").css("min-height",$(window).height()-101+"px");	
	});
});

function changePage(index)
{
	if(index == 0)
	{

		
		$("#loadPage").attr("src","<%=path%>/line/getLinesDetail.action");
	}
	else if(index == 1)
	{
	   
	    $("#loadPage").attr("src","<%=path%>/line/getScheduleInfo.action");
	}
	else if(index == 2)
	{
	    
	    $("#loadPage").attr("src","<%=path%>/line/getOrderNum.action");
	}	
}

//关闭地图弹出层
function closePopMapPage()
{
    $("#showPage1").hide();
    $("#mainBody").hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
    $("#searchText").val('');
}

$(function(){
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				//$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
		}).blur(function(){  
			if(false){
				if($(this).val() == ""){
				    $(this).val(txt);  
				    $(this).removeClass("gray2").addClass("gray3");
				}
			}
		});  
	});
});

var map;
var inputId;
// 百度地图API功能
function createMapById(id)
{
	inputId = id;
	map = new BMap.Map("lineMap");            // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 10);
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
	map.enableScrollWheelZoom(); // 启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();// 启用地图惯性拖拽，默认禁用
	//localSearch('detail');
	map.addEventListener('click', clickHandler);

}

//百度地图API功能
function createMap(id,loni,lati)
{
	var isChina = (loni>73&&loni<135)&&(lati>3&&lati<53);
	inputId = id;
	map = new BMap.Map("lineMap");            // 创建Map实例
	var point;
	if(isChina){
		point = new BMap.Point(loni, lati);
	}else{
		point = new BMap.Point(116.404, 39.915);
		//根据IP获取城市
		var currCity = new BMap.LocalCity();
		currCity.get(getCityName);
	}
	map.centerAndZoom(point, 10);
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
	if(isChina){
		var marker1 = new BMap.Marker(point); //初始化地图标记  
		oldMarker = marker1;
		map.addOverlay(marker1); //将标记添加到地图
		map.enableScrollWheelZoom(); // 启用滚轮放大缩小，默认禁用
		map.enableContinuousZoom();// 启用地图惯性拖拽，默认禁用
		var gc = new BMap.Geocoder();//地址解析类
		gc.getLocation(point, function(rs){  
			var addComp = rs.addressComponents;  
			var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;  
			var title = rs.address;
			var iw = createInfoWindow(title,address,point.lat,point.lng);
			marker1.openInfoWindow(iw);
			marker1.addEventListener("click", function(e){  
				   // 移除地图click事件
				  
				   if($("#sureMarker").length > 0)
				   {
					  $("#sureMarker").remove();
				   }
				   marker1.openInfoWindow(iw);
			}); 
			iw.addEventListener("open", function () {
				// 移除地图click事件
			});
			iw.addEventListener("close", function () {
				// 添加地图click事件
			});
		}); 
	}
	map.addEventListener('click', clickHandler); 
	//localSearch('detail');

}

function getCityName(result){
    var cityName = result.name;
    map.setCenter(cityName);   //关于setCenter()可参考API文档---”传送门“
}

//搜索功能
function localSearch(flag)
{
	map.clearOverlays(); //清除地图上所有标记
	var local = new BMap.LocalSearch("全国", {
	  renderOptions: {
	    map: map,
	    autoViewport: true,
	    selectFirstResult: false
	  }
	});
	//从详情进入
	if(flag == "detail")
	{
		var searchText = $("#"+inputId,loadPage.window.document).val();
		local.setPageCapacity(1);
	}
	else
	{
		var searchText = $("#searchText").val();
	}
	local.search(searchText);
	//单击获取点击的经纬度
	map.addEventListener('click', clickHandler);
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
	
	localSearch();
});



//获取点击的markert信息,给input框赋值
function getCurrMarkerInfo(title,address,lat,lng)
{

    
	$("#"+inputId,loadPage.window.document).val(title).removeClass("gray3").addClass("gray2");
	$("#"+inputId+"ErrTip",loadPage.window.document).remove();
	//经纬度赋值
	if(inputId.indexOf("Start")!=-1){
	  
	   $("#lati"+inputId.substring(inputId.indexOf("Start")),loadPage.window.document).val(lat).removeClass("gray3").addClass("gray2");
	   $("#loni"+inputId.substring(inputId.indexOf("Start")),loadPage.window.document).val(lng).removeClass("gray3").addClass("gray2");
	}
	if(inputId.indexOf("End")!=-1){
	   
	   $("#lati"+inputId.substring(inputId.indexOf("End")),loadPage.window.document).val(lat).removeClass("gray3").addClass("gray2");
	   $("#loni"+inputId.substring(inputId.indexOf("End")),loadPage.window.document).val(lng).removeClass("gray3").addClass("gray2");
	}
	if(inputId.indexOf("Access")!=-1){
	  
	   $("#lati"+inputId.substring(inputId.indexOf("Access")),loadPage.window.document).val(lat).removeClass("gray3").addClass("gray2");
	   $("#loni"+inputId.substring(inputId.indexOf("Access")),loadPage.window.document).val(lng).removeClass("gray3").addClass("gray2");
	}
	closePopMapPage();
	
	
}

function showImage(filePath){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../line/showImage.action?random="+Math.floor(Math.random()*10000+1);
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="line/showImage.action?random="+Math.floor(Math.random()*10000+1);
	}
	
  
   var filePathValue = $(filePath).val();
   
   $("#showPagePic").load($the_url,{filePath:filePathValue}); 
}

function uploadFile(fileId,picId,filePathId,absoluteFilePath){
    var value = $("#"+fileId).val();
	if(""==value||value.indexOf(".")==-1){
		return;
	}
	/*var arr = value.split(".");
	var suffix = arr[arr.length-1];
	if(suffix!=("jpg") && suffix!=("png") && suffix!=("jpeg")){
		parent.showRturnTip(validateJson.ImgFile.tip,false); 
		return;
	}*/
	var reg = validateJson.ImgFile.reg;
	var thisReg = new RegExp(reg);
	if(!thisReg.test(value))
	{
		parent.showRturnTip(validateJson.ImgFile.tip,false); 
		return;
	}
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url='../ftpUploadAction/upLoad.action?uploadFileType=1';
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url='ftpUploadAction/upLoad.action?uploadFileType=1';
	}

	parent.popLodingPage(true);
	$.ajaxFileUpload({
			
			fileElementId:fileId, 
			secureuri:false,
			dataType: 'text',
			url : $the_url,
			success : function(data) {
				parent.popLodingPage(false);
				if (data == "error") {
					parent.showRturnTip("上传失败!",false); 
				}else if(data=="overSize"){
					//图片过大，请上传不大于500K的图片
					parent.showRturnTip("图片过大，请上传不大于500K的图片",false); 
				}else{
					data = eval("(" + data + ")");
					//显示原来的图片名称
					var pathArr = value.split("\\");
					var fileName = pathArr[pathArr.length-1];
					//相对路径
					
					$("#"+filePathId).val(data.dbFileUrl);
					//绝对路径
					
					$("#"+absoluteFilePath).val(data.downFileUrl);
					var imgUrl = "<a href='javascript:void(0)' onclick='showImage("+absoluteFilePath+");'>"+fileName+"</a>";
					
					$("#"+picId).html(imgUrl);
				}
			},
			error:function(){
				parent.showRturnTip("上传失败!",false); 
			}
     });
}

//地图随处添加marker
var markersAddress = [];
var markersArr = [];

var clickHandler = function(e)
{
	if(e.overlay)
	{
		return;
	}
	map.clearOverlays();
	markersArr.push(e.point);
	var point = new BMap.Point(e.point.lng, e.point.lat);  
	var marker = new BMap.Marker(point); //初始化地图标记  
	oldMarker = marker;
	//marker.enableDragging(); //标记开启拖拽  
	map.addOverlay(marker); //将标记添加到地图

	var gc = new BMap.Geocoder();//地址解析类
	gc.getLocation(e.point, function(rs){  
		var addComp = rs.addressComponents;  
		var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;  
		var title = rs.address;
		var iw = createInfoWindow(title,address,e.point.lat,e.point.lng);
		marker.openInfoWindow(iw);
		marker.addEventListener("click", function(e){  
			   // 移除地图click事件
			  
			   if($("#sureMarker").length > 0)
			   {
				  $("#sureMarker").remove();
			   }
			   marker.openInfoWindow(iw);
		}); 
		marker.addEventListener("dragend", function(e){  
			   var gc = new BMap.Geocoder();//地址解析类
			   gc.getLocation(e.point, function(rs){  
				    var addComp = rs.addressComponents;  
					var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;  
					var title = rs.address;
					var iw = createInfoWindow(title,address,e.point.lat,e.point.lng);
					marker.openInfoWindow(iw);
				   // 移除地图click事件
				  
				   if($("#sureMarker").length > 0)
				   {
				   	    $("#sureMarker").remove();
				   }
				   marker.openInfoWindow(iw);
			   });  
		}); 
		iw.addEventListener("open", function () {
			// 移除地图click事件
		   
		});
		iw.addEventListener("close", function () {
			// 添加地图click事件
			
		});
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

//返回上一步
function goBackToList(){
	var basePath = "<%=basePath%>";
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url=basePath+"line/getLinesList.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url=basePath+"line/getLinesList.action?level=two";
	}
	window.location.href=$the_url;
}
</script>