<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
	<title>运营平台－线路-线路规划</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
	<link rel="stylesheet" href="../css/stationPicker.css"/>
	<jsp:include page="../public_select.jsp"/>
	<style>
		.errorTip{
	    	display:inline-block;
	        color:#d1261e;
	        background:#f7e7e9;
	        margin-left:10px;
	        padding:0 5px;
		}
		.w80{
		    width:80px;
		}
		.ml10{
		    margin-left:10px;
		}
	</style>
	<script type="text/javascript" src="../js/jquery/ajaxfileupload.js"></script>
</head>
<body>
    <div id="mainBody" class="mainBody"></div>
    <div id="showPage" class="showPage"></div>
    <div id="showPagePic" class="showPage"></div>
    
    <div class="r-sub-nav-out">
    	<p class="r-sub-nav gray2">
    		<span>当前位置：首页 &gt; 发布线路</span>
    		<span class="blue1 ml25"></span>
    	    <s:if test="%{gobackSign==1}"><span><a class="gray2" href="javascript:goback();">返回</a></span></s:if>
    	</p>
    </div>
    
	<div class="steps">
    	<ol class="clearfix">
    	    <c:if test="${lineBaseId==null}">
				<li class="active"><i>1</i><span class="tsl">线路规划</span></li>
				<li><i>2</i><span class="tsl">设置班次</span></li>
				<li><i>3</i><span class="tsl">定价格</span></li>
				<li><i>4</i><span class="tsl">设置供应商</span></li>
				<li><i>5</i><span class="tsl">完成设置</span></li>
			</c:if>
			<c:if test="${lineBaseId!=null}">
				<a href="../publishLine/loadLineBaseInfo.action?lineBaseId=${lineBaseId}"><li class="active"><i>1</i><span class="tsl">线路规划</span></li></a>
				<a href="../publishLine/loadClassInfo.action?lineBaseId=${lineBaseId}"><li><i>2</i><span class="tsl">设置班次</span></li></a>
				<a href="../publishLine/loadClassPrice.action?lineBaseId=${lineBaseId}"><li><i>3</i><span class="tsl">定价格</span></li></a>
				<a href="../publishLine/loadBusinessInfo.action?lineBaseId=${lineBaseId}"><li><i>4</i><span class="tsl">设置供应商</span></li></a>
				<a href="../publishLine/loadCommitLine.action?lineBaseId=${lineBaseId}"><li><i>5</i><span class="tsl">完成设置</span></li></a>
			</c:if>
		</ol>		
    </div>
    <s:form id="addForm" method="post" theme="simple">
        <input type="hidden" name="stationArr"/>
        <input type="hidden" name="lineBaseId"/>
        <input type="hidden" name="lineStatus" value="0"/>
	    <ul id="addLineUl" class="mt30 ml45 gray2 r-input32">
			<li class="clearfix mt15 line28">
			    <span class="fl w106 t-r">归属人：<em class="red1">*</em></span>
				<span class="fl w15p p-r">
		            <span class="p-a arrow arrow-desc" style="top:10px;right:34%;"></span>
		            <input type="text" name="lineManager"  id="lineManager" class="r-input fl mr5"/>
					<span id="lineManagerError" class="errorTip"></span>
		        </span> 
			</li>
			<li class="clearfix mt15 line28">
				<span class="fl w106 t-r">所属城市：<em class="red1">*</em></span>
				<select name="provinceCode" id="provinceCode" class="fl r-input mr10" onchange="loadCity4Line(this.value);">
					<option value="">--选择省份--</option>
				    <s:iterator value="#request.proSysAreas" var="proSysArea">
				        <option value="${proSysArea.arearCode}">${areaName}</option>
				    </s:iterator>
				</select>
				<span id="provinceCodeError" class="errorTip"></span>
				<select name="cityCode" id="cityCode" class="fl r-input mr10">
					<option value="">--选择城市--</option>
				</select>
			    <span id="cityCodeError" class="errorTip"></span>
			</li>
			<li class="clearfix mt15 line28">
			    <span class="fl w106 t-r">线路名称：<em class="red1">*</em></span>
			    <input name="lineName" id="lineName" type="text" maxLength="24" class="fl r-input w222" placeholder="设置线路名称"/>
				<span id="lineNameError" class="errorTip"></span>
			</li>
			<li id="LiStart" class="clearfix mt15 line28">
				<span class="fl w106 t-r">上车线路：<em class="red1">*</em></span>
				<input name="stationName" id="stationNameStart" type="text" maxLength="24" class="fl r-input w222" placeholder="设置起点位置"/>
				<span class="mapIco ml10" title="选择站点位置" onclick="goAddMapPage('Start')"></span>
				<select class="fl r-input ml10 mr10" id="stationSuffixStart" name="stationSuffix">
				    <option value="">请选择站点类型</option>
				    <option value="busStation">公交站</option>
				    <option value="metroStation">地铁站</option>
				    <option value="default">其他</option>
				</select>
				到站时间：<input class="r-input" name="arriveTime" readonly="readonly" type="text" id="arriveTimeStart" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})" size="8" value=""/>
				站点描述：<input class="r-input m14" name="tipdesc" type="text" id="tipdescStart" size="32" value=""/>
				<a href="javascript:void(0)" class="f12 blue1" onclick="addAccessPoint('Start')">添加途径点</a>
				<span id="stationNameStartError" class="errorTip"></span>
				<input type="hidden" name="stationType" value="1"/>
			</li>
			<li id="LiEnd" class="clearfix mt15 line28" >
				<span class="fl w106 t-r"><em class="red1">*</em></span>
				<input type="text" name="stationName" maxLength="24" id="stationNameEnd" class="fl r-input w222 gray3" placeholder="设置终点位置"/>
				<input id="addressEnd" type="hidden" name="address"/>
				<span class="mapIco ml10" title="添加站点位置"  onclick="goAddMapPage('End')"></span>
				<select class="fl r-input ml10 mr10" id="stationSuffixEnd" name="stationSuffix">
				    <option value="">请选择站点类型</option>
				    <option value="busStation">公交站</option>
				    <option value="metroStation">地铁站</option>
				    <option value="default">其他</option>
				</select>
				到站时间：<input class="r-input" name="arriveTime" type="text" id="arriveTimeEnd" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})" size="8" value=""/>
				站点描述：<input class="r-input m14" name="tipdesc" type="text" id="tipdescEnd" size="32" value=""/>
				<span id="stationNameEndError" class="errorTip"></span>
				<input type="hidden" name="stationType" value="0"/>
			</li>
			<span id="stationTypeError" class="errorTip"></span>
			<li class="clearfix mt15 line28">
				<span class="fl w106 t-r"></span>
				<a href="javascript:showPlan();" class="btn btn-blue" style="color:#fff">查看详细线路规划</a>
			</li>
			<li class="clearfix mt15 line28">
			    <span class="fl w106 t-r">预计行程时间：<em class="red1">*</em></span>
			    <input type="text" id="lineTime" name="lineTime" maxLength="6" class="fl r-input w235"/>
				<select name="lineTimeType" class="p3 ml10" id="lineTimeType">
					<option value="0">分钟</option>
					<option value="1">小时</option>
				</select><span id="lineTimeError" class="errorTip"></span>
			</li>
			<li class="clearfix mt15 line28">
			     <span class="fl w106 t-r">里程：<em class="red1">*</em></span>
			     <input type="text" id="lineKm" name="lineKm" maxLength="6" class="fl r-input w235 mr5"/>KM<span id="lineKmError" class="errorTip"></span>
			</li>
			<li class="clearfix mt15 line28">
			     <span class="fl w106 t-r">通用票价：<em class="red1">*</em></span>
			     <input type="text" id="orderPrice" name="orderPrice" maxLength="6" class="fl r-input w235 mr5"/>元<span id="orderPriceError" class="errorTip"></span><span id="priceChangeTip" style="color:green;"></span>
			</li>
			<li class="clearfix mt15 line28">
			     <span class="fl w106 t-r">原价：</span>
			     <input type="text" id="originalPrice" name="originalPrice" maxLength="6" class="fl r-input w235 mr5"/><span class="fl mr5">元</span><span id="originalPriceError" class="errorTip"></span>
			     <input type="checkbox" id="originalFlag" name="originalFlag" value="true" class="fl r-input mr5"/>
			</li>
			<li class="clearfix mt15 line28">
			    <span class="fl w106 t-r">选择线路类型：<em class="red1">*</em></span>
			    <span class="fl mt5">
			        <input type="radio" name="lineSubType" class="checkbox shangXiaBanType" checked="checked" value="0"/>上下班
			        <input type="radio" name="lineSubType" class="checkbox ml10 lvYouType" value="1"/>旅游
			    </span>
			</li>
			<li class="clearfix mt30 line28">
			    <span class="fl w106 t-r" id="saveTip" style="color:orange;font-size:14px;font-weight:bold;"></span>
			    <a class="display-ib btn1 white ml30 saveBtn" id="savePage" href="javascript:saveBaseInfo(1);">保存当前数据</a>
			    <a class="display-ib btn1 white ml30 saveBtn" id="saveNext" href="javascript:saveBaseInfo(2);">下一步</a>
			</li>
		</ul>
	</s:form>
		<!-- 线路名称 -->
		<div class="hide">
		  <select id="selectLineBelonger" name="selectLineBelonger" >
		  </select>
		</div>
  </body>
</html>
<script src="http://api.map.baidu.com/api?v=2.0&ak=U6PCj4spceYZTcx8GEnj1wVt" type="text/javascript"></script>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
var isSubmitAvailable = true;
var stationArr = new Array();
var isOk = true;
var char_regx = new RegExp("^[a-zA-Z0-9\u4e00-\u9fa5\(\)①-⑨（）_]*$");
var money_regx = new RegExp("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
var time_regx = new RegExp("^(\\d{2}):(\\d{2})$");
$(document).ready(function(){
	var lineBaseId = "${lineBaseId}";
	if(lineBaseId!=null&&lineBaseId!=""){
		var isCopy = "${isCopy}";
		if(isCopy=="true"){
			initCopyInfo();
		}else{
			initPageInfo();
		}
	}else{
    	$("#stationNameStart").attr("readonly","readonly");
    	$("#stationNameEnd").attr("readonly","readonly");
	}
	getLineBelonger();
});

function initCopyInfo(){
	var url = "../publishLine/loadLineBaseInfoAjax.action?lineBaseId=${lineBaseId}";
	leaseGetAjax(url,"json",function(data){
		if(data.a1=="100"){
			var lineBaseInfo = data.a2;
			var lineStationVoList = data.a3;
			if(lineBaseInfo!=null){
				$("[name='provinceCode']").val(lineBaseInfo.provinceCode);
				loadCity2(lineBaseInfo.provinceCode,lineBaseInfo.cityCode);
				$("[name='lineTime']").val(lineBaseInfo.lineTime);
				$("[name='lineKm']").val(lineBaseInfo.lineKm);
				$("[name='orderPrice']").val(lineBaseInfo.orderPrice);
				$("[name='originalPrice']").val(lineBaseInfo.originalPrice);
				if(lineBaseInfo.originalFlag=='true'){
					$("[name='originalFlag']").attr("checked","checked");
				}
				if(lineBaseInfo.lineSubType=="0"){
					$(".shangXiaBanType").attr("checked","checked");
					$(".lvYouType").removeAttr("checked");
				}else{
					$(".shangXiaBanType").removeAttr("checked");
                    $(".lvYouType").attr("checked","checked");
				}
			}
			if(lineStationVoList!=null){
                   var firstStation = lineStationVoList[0];
                   $("#stationNameStart").val(firstStation.stationName);
                   $("#stationNameStart").attr("lng",firstStation.loni);
                   $("#stationNameStart").attr("lat",firstStation.lati);
                   $("#stationNameStart").attr("stationSuffix",firstStation.stationSuffix);
                   $("#stationSuffixStart").val(firstStation.stationSuffix);
                   $("#arriveTimeStart").val(firstStation.arriveTime);
                   $("#tipdescStart").val(firstStation.tipdesc);
                   var endStation = lineStationVoList[lineStationVoList.length-1];
                   $("#stationNameEnd").val(endStation.stationName);
                   $("#stationNameEnd").attr("lng",endStation.loni);
                   $("#stationNameEnd").attr("lat",endStation.lati);
                   $("#stationNameEnd").attr("stationSuffix",endStation.stationSuffix);
                   $("#stationSuffixEnd").val(endStation.stationSuffix);
                   $("#arriveTimeEnd").val(endStation.arriveTime);
                   $("#tipdescEnd").val(endStation.tipdesc);
                   var content = "";
                   var count = 1;
   				for(var index=1;index<lineStationVoList.length-1;index++){
   					var stationInfo = lineStationVoList[index];
   					 content += "<li class='clearfix mt15 r-input32 line28' id=\"li\">";
   				     content += "<span class='fl w106 t-r'></span>";
   				     if(stationInfo.lati!=null&&stationInfo.loni!=null){
   				    	 content += "<input type='text' name='stationName' maxLength='24' id='stationName"+count+
   						 "' class='fl r-input w222' placeholder='设置途径点"+count+"' lng=\""+stationInfo.loni+
   						 "\" lat=\""+stationInfo.lati+"\" value=\""+stationInfo.stationName+"\" stationSuffix=\""+stationInfo.stationSuffix+"\"/>";
   					 }else{
   						 content += "<input type='text' name='stationName' maxLength='24' id='stationName"+count+
   						 "' class='fl r-input w222' placeholder='设置途径点"+count+"' lng=\""+stationInfo.loni+
   						 "\" lat=\""+stationInfo.lati+"\" value=\""+stationInfo.stationName+"\"/>";
   				     }
   					 content += "<span class='mapIco ml10 mr10' id='map"+count+"' title='添加站点位置' onclick='goAddMapPage(\""+count+"\")'></span>";
   					 content += "<select class='fl r-input' name='stationType'>";
                        if(stationInfo.stationType=="1"){
                       	 content += "<option value=\"1\" selected=\"selected\">上车点</option><option value=\"2\">引导点</option><option value=\"0\">下车点</option>";
                        }else if(stationInfo.stationType=="0"){
                       	 content += "<option value=\"1\">上车点</option><option value=\"2\">引导点</option><option value=\"0\" selected=\"selected\">下车点</option>";
                        }else{
                       	 content += "<option value=\"1\">上车点</option><option value=\"2\" selected=\"selected\">引导点</option><option value=\"0\">下车点</option>";
                        }
                        content += "</select>";
                        content += "<select class=\"fl r-input ml10 mr10\" name=\"stationSuffix\">";
                        if(stationInfo.stationSuffix=="busStation"){
                       	 content += "<option value=\"\">请选择站点类型</option><option value=\"default\">其他</option><option value=\"busStation\" selected=\"selected\">公交站</option><option value=\"metroStation\">地铁站</option>";
                        }else if(stationInfo.stationSuffix=="metroStation"){
                       	 content += "<option value=\"\">请选择站点类型</option><option value=\"default\">其他</option><option value=\"busStation\">公交站</option><option value=\"metroStation\" selected=\"selected\">地铁站</option>";
                        }else{
                       	 content += "<option value=\"\">请选择站点类型</option><option value=\"default\" selected=\"selected\">其他</option><option value=\"busStation\">公交站</option><option value=\"metroStation\">地铁站</option>";
                        }
   					 content += "</select>";
   					 content += "到站时间:";
   					 content += "<input class=\"r-input mr5\" name=\"arriveTime\" readonly=\"readonly\" type=\"text\" onclick=\"WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})\" size=\"8\" value=\""+stationInfo.arriveTime+"\"/>";
   					 content += "站点描述:";
   					 if(stationInfo.tipdesc==undefined){
   						 stationInfo.tipdesc = "";
   					 }
   					 content += "<input class=\"r-input m14\" name=\"tipdesc\" type=\"text\" size=\"32\" value=\""+stationInfo.tipdesc+"\"/>";
   					 content += "<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPoint()'>添加途径点</a>";
   				     content += "<span id=\"stationName"+count+"Error\" class=\"errorTip\"></span></li>";
   				}
   				$("#LiStart").after(content);
   				setOtherAccessPointNotEqOne();
               }
		}else{
			parent.parent.showRturnTip("加载线路信息失败",false); 
		}
	});
}

// 修改线路的时候，加载线路信息
function initPageInfo(){
	var url = "../publishLine/loadLineBaseInfoAjax.action?lineBaseId=${lineBaseId}";
	leaseGetAjax(url,"json",function(data){
        if(data.a1=="100"){
            var lineBaseInfo = data.a2;
            var lineStationVoList = data.a3;
            if(lineBaseInfo!=null){
            	$("[name='lineManager']").val(lineBaseInfo.userName);
            	$("[name='lineBaseId']").val(lineBaseInfo.lineBaseId);
            	$("[name='provinceCode']").val(lineBaseInfo.provinceCode);
            	loadCity2(lineBaseInfo.provinceCode,lineBaseInfo.cityCode);
                $("[name='lineName']").val(lineBaseInfo.lineName);
                $("[name='lineStatus']").val(lineBaseInfo.lineStatus);
                $("[name='lineTime']").val(lineBaseInfo.lineTime);
                $("[name='lineKm']").val(lineBaseInfo.lineKm);
                $("[name='orderPrice']").val(lineBaseInfo.orderPrice);
                $("[name='originalPrice']").val(lineBaseInfo.originalPrice);
                if(lineBaseInfo.originalFlag=='true'){
                	$("[name='originalFlag']").attr("checked","checked");
                }
                if(lineBaseInfo.lineSubType=='0'){
                    $(".shangXiaBanType").attr("checked","checked");
                    $(".lvYouType").removeAttr("checked");
                }else{
                    $(".shangXiaBanType").removeAttr("checked");
                    $(".lvYouType").attr("checked","checked");
                }
            }
            if(lineStationVoList!=null){
                var firstStation = lineStationVoList[0];
                $("#stationNameStart").val(firstStation.stationName);
                $("#stationNameStart").attr("stationInfoId",firstStation.stationInfoId);
                $("#stationNameStart").attr("lng",firstStation.loni);
                $("#stationNameStart").attr("lat",firstStation.lati);
                $("#stationNameStart").attr("stationSuffix",firstStation.stationSuffix);
                $("#stationSuffixStart").val(firstStation.stationSuffix);
                $("#arriveTimeStart").val(firstStation.arriveTime);
                $("#tipdescStart").val(firstStation.tipdesc);
                var endStation = lineStationVoList[lineStationVoList.length-1];
                $("#stationNameEnd").val(endStation.stationName);
                $("#stationNameEnd").attr("stationInfoId",endStation.stationInfoId);
                $("#stationNameEnd").attr("lng",endStation.loni);
                $("#stationNameEnd").attr("lat",endStation.lati);
                $("#stationNameEnd").attr("stationSuffix",endStation.stationSuffix);
                $("#stationSuffixEnd").val(endStation.stationSuffix);
                $("#arriveTimeEnd").val(endStation.arriveTime);
                $("#tipdescEnd").val(endStation.tipdesc);
                var content = "";
                var count = 1;
				for(var index=1;index<lineStationVoList.length-1;index++){
					var stationInfo = lineStationVoList[index];
					 content += "<li class='clearfix mt15 r-input32 line28' id=\"li\">";
				     content += "<span class='fl w106 t-r'></span>";
				     if(stationInfo.lati!=null&&stationInfo.loni!=null){
				    	 content += "<input type='text' name='stationName' maxLength='24' id='stationName"+count+
						 "' class='fl r-input w222' placeholder='设置途径点"+count+"' stationInfoId=\""+stationInfo.stationInfoId+"\" lng=\""+stationInfo.loni+
						 "\" lat=\""+stationInfo.lati+"\" value=\""+stationInfo.stationName+"\" stationSuffix=\""+stationInfo.stationSuffix+"\"/>";
					 }else{
						 content += "<input type='text' name='stationName' maxLength='24' id='stationName"+count+
						 "' class='fl r-input w222' placeholder='设置途径点"+count+"' stationInfoId=\""+stationInfo.stationInfoId+"\" lng=\""+stationInfo.loni+
						 "\" lat=\""+stationInfo.lati+"\" value=\""+stationInfo.stationName+"\"/>";
				     }
					 content += "<span class='mapIco ml10 mr10' id='map"+count+"' title='添加站点位置' onclick='goAddMapPage(\""+count+"\")'></span>";
					 content += "<select class='fl r-input' name='stationType'>";
                     if(stationInfo.stationType=="1"){
                    	 content += "<option value=\"1\" selected=\"selected\">上车点</option><option value=\"2\">引导点</option><option value=\"0\">下车点</option>";
                     }else if(stationInfo.stationType=="0"){
                    	 content += "<option value=\"1\">上车点</option><option value=\"2\">引导点</option><option value=\"0\" selected=\"selected\">下车点</option>";
                     }else{
                    	 content += "<option value=\"1\">上车点</option><option value=\"2\" selected=\"selected\">引导点</option><option value=\"0\">下车点</option>";
                     }
                     content += "</select>";
                     content += "<select class=\"fl r-input ml10 mr10\" name=\"stationSuffix\">";
                     if(stationInfo.stationSuffix=="busStation"){
                    	 content += "<option value=\"\">请选择站点类型</option><option value=\"default\">其他</option><option value=\"busStation\" selected=\"selected\">公交站</option><option value=\"metroStation\">地铁站</option>";
                     }else if(stationInfo.stationSuffix=="metroStation"){
                    	 content += "<option value=\"\">请选择站点类型</option><option value=\"default\">其他</option><option value=\"busStation\">公交站</option><option value=\"metroStation\" selected=\"selected\">地铁站</option>";
                     }else{
                    	 content += "<option value=\"\">请选择站点类型</option><option value=\"default\" selected=\"selected\">其他</option><option value=\"busStation\">公交站</option><option value=\"metroStation\">地铁站</option>";
                     }
					 content += "</select>";
					 content += "到站时间:";
					 content += "<input class=\"r-input ml4 mr10\" name=\"arriveTime\" readonly=\"readonly\" type=\"text\" onclick=\"WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})\" size=\"8\" value=\""+stationInfo.arriveTime+"\"/>";
					 content += "站点描述:";
					 if(stationInfo.tipdesc==undefined){
						 stationInfo.tipdesc = "";
					 }
					 content += "<input class=\"r-input m14\" name=\"tipdesc\"  type=\"text\" size=\"32\" value=\""+stationInfo.tipdesc+"\"/>";
					 content += "<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' stationId=\""+stationInfo.stationInfoId+"\" onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPoint()'>添加途径点</a>";
				     content += "<span id=\"stationName"+count+"Error\" class=\"errorTip\"></span></li>";
				}
				$("#LiStart").after(content);
				setOtherAccessPointNotEqOne();
            }
        }else{
        	parent.parent.showRturnTip("加载线路信息失败",false); 
        }
    });
}

//保存基础数据
function saveBaseInfo(directType){
	isOk = true;
	$(".errorTip").html("");
	validate();
	if(isOk){
	   if(isSubmitAvailable){
		   $(".saveBtn").hide();
		   $("#saveTip").html("正在提交中...");
		   isSubmitAvailable = false;
       		handleSave(directType);
	   }
	}
}

//执行保存
function handleSave(directType){
	var url = "../publishLine/saveLineBaseInfo.action";
	makeStationArr();
	var stationArrJSON = JSON.stringify(stationArr);
	$("[name='stationArr']").val(stationArrJSON);
	var theForm = $("#addForm");
	leasePostAjax(url,theForm,"json",function(data){
		isSubmitAvailable = true;
		$(".saveBtn").show();
		$("#saveTip").html("");
        if(data.a1=="100"){
            $("[name='lineBaseId']").val(data.a2);
            if(directType==1){
            	parent.parent.showRturnTip("保存成功",true);
            	if(data.a3=="true"){
            		$("#priceChangeTip").html("未设置优惠价的排班价格立即生效。");
            	}
            }else{
                document.location.href="../publishLine/loadClassInfo.action?lineBaseId="+data.a2;
            }
        }else{
        	parent.parent.showRturnTip(data.a2,false); 
        }
	});
}

//验证数据
function validate(){
	$(".errorTip").html("");
	var lineName = $("#lineName").val();
	if(lineName==null||lineName==""){
        $("#lineNameError").html("线路名称不能为空");
        isOk = false;
	}else if(!char_regx.test(lineName)){
		$("#lineNameError").html("线路名称格式错误");
        isOk = false;
	}
	var orderPrice = $("#orderPrice").val();
	if(orderPrice==null||orderPrice==""){
        $("#orderPriceError").html("通票价格不能为空");
        isOk = false;
	}else if(!money_regx.test(orderPrice)){
    	$("#orderPriceError").html("通票价格格式不正确");
        isOk = false;
    }
	var originalPrice = $("#originalPrice").val();
	if($("#originalFlag").attr("checked")=='checked'){
		if(originalPrice==null||originalPrice==""){
	        $("#originalPriceError").html("原不能为空");
	        isOk = false;
		}else if(!money_regx.test(originalPrice)){
	    	$("#originalPriceError").html("原价格式不正确");
	        isOk = false;
	    }
		if(originalPrice!=null&&originalPrice!=""){
			if(parseFloat(originalPrice)<parseFloat(orderPrice)){
				$("#originalPriceError").html("原价不能低于通票价格");
				isOk = false;
			}
		}
	}else{
		if(originalPrice!=""){
			isOk = false;
			$("#originalPriceError").html("请勾选原价复选框，或者不设原价");
		} 
	}
	
	
    var num_regx = new RegExp("^[0-9]+[/.]?[0-9]*$");
    var lineKm = $("#lineKm").val();
    if(lineKm==null||lineKm==""){
    	$("#lineKmError").html("里程不能为空");
    	isOk = false;
    }else if(!num_regx.test(lineKm)){
    	$("#lineKmError").html("里程的格式不正确");
    	isOk = false;
    }else if(parseInt(lineKm)==0){
    	$("#lineKmError").html("里程不能为零");
    	isOk = false;
    }
    var lineTime = $("#lineTime").val();
	if(lineTime==null||lineTime==""){
        $("#lineTimeError").html("预计时间不能为空");
        isOk = false;
    }else if(!num_regx.test(lineTime)){
    	$("#lineTimeError").html("预计时间格式不正确");
        isOk = false;
    }else if(parseInt(lineTime)==0){
    	$("#lineTimeError").html("预计时间不能为0");
        isOk = false;
    }
	var provinceCode = $("#provinceCode").val();
	if(provinceCode==null||provinceCode==""){
        $("#provinceCodeError").html("请选择省份");
        isOk = false;
    }
	var cityCode = $("#cityCode").val();
	if(cityCode==null||cityCode==""){
       	$("#cityCodeError").html("请选择城市");
        isOk = false;
    }
	var lineManager = $("#lineManager").val();
    if(lineManager==null||lineManager==""){
    	$("#lineManagerError").html("请选择线路运营人");
    	isOk=false;
    }
    
	validateStationFull();
	validateStationType();
}

//验证站点列表
function validateStation(){
	$.each($("[name='stationName']"),function(index,item){
       if($(item).val()==null||$(item).val()==""){
           $("#"+$(item).attr("id")+"Error").html("请设置站点信息");
           isOk = false;
       }
       if($(item).val().indexOf("-")>0){
    	   $("#"+$(item).attr("id")+"Error").html("站点名称中间不能包含'-'");
           isOk = false;
       }
       
       if(!char_regx.test($(item).val())){
    	   $("#"+$(item).attr("id")+"Error").html("站点名称格式不正确");
    	   isOk = false;
       }
	});
}

function validateStationFull(){
	validateStation();
	$.each($("[name='stationName']"),function(index,item){
		if($(item).attr("lat")==null||$(item).attr("lat")==""){
		   $("#"+$(item).attr("id")+"Error").html("站点没有选择经纬度");
	 	   isOk = false;
	    }
	    if($(item).attr("lng")==null||$(item).attr("lng")==""){
		   $("#"+$(item).attr("id")+"Error").html("站点没有选择经纬度");
	 	   isOk = false;
	    }
	});
}

//验证站点类型
function validateStationType(){
	var nowType = "1";
	$.each($("[name='stationType']"),function(index,item){
        var jItem = $(item);
        if(jItem.val()=="0"){
            nowType = "0";
        }
        if(jItem.val()=="1"){
            if(nowType=="0"){
            	parent.parent.showRturnTip("站点类型错误,下车点之后不能再有上车点",false); 
            	isOk = false;
            }
        }
	});
	
	$.each($("[name='stationSuffix']"),function(index,item){
		var jItem = $(item);
		if(jItem.val()==""){
			parent.parent.showRturnTip("有站点的【站点类型】没有选，请选择'公交站','地铁站' 或 '其他'");
			isOk = false;
		}
	});
	
	$.each($("[name='arriveTime']"),function(index,item){
		if($(item).val()!=null&&$(item).val()!=""&&$(item).val()!=undefined){
			if(!time_regx.test($(item).val())){
				parent.parent.showRturnTip("站点抵站时刻格式不正确，正确格式如 HH:mm");
				isOk = false;
			}
		}
	});
}

//清空
function clearAllInput(){
	$("#lineManager").val("");
    $("#lineName").val("");
    $("#orderPrice").val("");
    $("#lineKm").val("");
    $("#lineTime").val("");
    $("#provinceCode").val("");
    $("#cityCode").val("");
    $("#lineManager").val("");
}

//展示线路规划
function showPlan(){
	isOk = true;
	$(".errorTip").html("");
	validateStationFull(2);
	if(isOk){
		makeStationArr();
		var stationArrJSONString = JSON.stringify(stationArr);
		var url = "../publishLine/popLinePlan.action?stationArr="+encodeURI(encodeURI(stationArrJSONString));
	    $("#showPage").load(url); 
	    $("#mainBody").show();
	    $("#topHide", parent.window.document).show();
	    $("#leftHide", parent.window.document).show();
	}
}

//把页面的站点信息组织成为json字符串 用于传递参数
function makeStationArr(){
	stationArr = new Array();
	$("[name='stationName']").each(function(){
		var station = new Object();
		station.stationInfoId = $(this).attr("stationInfoId");
		station.lng = $(this).attr("lng");
		station.lat = $(this).attr("lat");
		station.stationName = $(this).val();
		station.stationSuffix = $(this).attr("stationSuffix");
		stationArr[stationArr.length] = station;
	});
	$.each($("[name='stationType']"),function(index,item){
        var station = stationArr[index];
        station.stationType = $(item).val();
	});
	$.each($("[name='stationSuffix']"),function(index,item){
        var station = stationArr[index];
        station.stationSuffix = $(item).val();
	});
	$.each($("[name='arriveTime']"),function(index,item){
		var station = stationArr[index];
		station.arriveTime = $(item).val();
	});
	$.each($("[name='tipdesc']"),function(index,item){
		var station = stationArr[index];
		station.tipdesc = $(item).val();
	});
}

//根据省份加载城市
function loadCity4Line(value){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
	    });
	}
}

//根据省份加载城市
function loadCity2(province,city){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(province != null && province != ""){
		var url = "../merchantAction/getProvince.action?proId="+province;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
			$("#cityCode").val(city);
	    });
	}
}

//添加途径点
function addAccessPoint(id){
	 var count = 1;
	 var content = "<li class='clearfix mt15 r-input32 line28' id=\"li\">";
     content += "<span class='fl w106 t-r'></span>";
	 content += "<input type='text' name='stationName' disabled='true' maxLength='24' id='stationName"+count+"' class='fl r-input w222' placeholder='设置途径点"+count+"'/>";
	 content += "<span class='mapIco ml10 mr10' id='map"+count+"' title='添加站点位置' onclick='goAddMapPage(\""+count+"\")'></span>";
	 content += "<select class='fl r-input' name='stationType'><option value=\"1\">上车点</option><option value=\"2\">引导点</option><option value=\"0\">下车点</option></select>";
	 content += "</select>";
     content += "<select class=\"fl r-input ml5 mr5\" name=\"stationSuffix\">";
     content += "<option value=\"\">请选择站点类型</option>";
	 content += "<option value=\"busStation\">公交站</option>";   
	 content += "<option value=\"metroStation\">地铁站</option>";
     content += "<option value=\"default\">其他</option>";
	 content += "</select>";
	 content += "到站时间:";
	 content += "<input class=\"r-input mr10\" name=\"arriveTime\" readonly=\"readonly\" type=\"text\" onclick=\"WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})\" size=\"8\" value=\"\"/>";
	 content += "站点描述:";
	 content += "<input class=\"r-input m110\" name=\"tipdesc\" type=\"text\" size=\"32\" value=\"\"/>";
	 content += "<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPoint()'>添加途径点</a>";
     content += "<span id=\"stationName"+count+"Error\" class=\"errorTip\"></span></li>";
	 $("#Li"+id).after(content);
	 setOtherAccessPointNotEqOne();
}

//重置除第一个之外的其余的li相关属性
function setOtherAccessPointNotEqOne(){
	var surplusCount = 0;
	$("[name='stationName']").each(function(){
		surplusCount++;
	});
	for(var i = 0;i < surplusCount;i++){
		if(i != 0 && i != (surplusCount - 1)){
			var liObj = $("[name='stationName']").eq(i).parent("li");
			liObj.attr("id","Li"+i);
			liObj.find("span").eq(2).attr("id","stationName"+parseInt(i)+"Error");
			liObj.find("a").eq(1).attr({"id":"btnA"+parseInt(i),"onclick":"addAccessPoint(\""+i+"\")"});
			liObj.find(".mapIco").attr({"onclick":"goAddMapPage('"+parseInt(i)+"')"});
			if(liObj.find("input").eq(0).val().indexOf("设置途径点") == 0){
				liObj.find("input").eq(0).attr({"id":"stationName"+parseInt(i),"value":"设置途径点"+parseInt(i)});
				liObj.find("input").eq(0).attr({"id":"stationName"+parseInt(i),"value":"设置途径点"+parseInt(i),"placeholder":"设置途径点"+parseInt(i)});
			}else{
				$("[name='stationName']").eq(i).parent("li").find("input").eq(0).attr({"id":"stationName"+parseInt(i),"placeholder":"设置途径点"+parseInt(i)});
			} 
		}
	}
}

//删除途径点
function deleteAccessPoint(obj){
	var stationId = $(obj).attr("stationId");
	if(stationId==null||stationId==""){
		$(obj).parent().remove();
		setOtherAccessPointNotEqOne();
	}else{
		var url = "../publishLine/isBuyForStation.action?stationId="+stationId;
		leaseGetAjax(url,"json",function(data){
			var isBuy = data.isBuy;
			if(isBuy=="0"){
				$(obj).parent().remove();
				setOtherAccessPointNotEqOne();
			}else{
				alert("该站点今天已经有购票，不能被删除。请在没有购票的情况下删除。");
			}
		});
	}
}

//标注站点
function goAddMapPage(id){
	var stationInput = $("#stationName"+id);
	var lng = stationInput.attr("lng");
	if(lng == undefined){
       lng = null;
	}
	var lat = stationInput.attr("lat");
	if(lat == undefined){
       lat = null;
	}
	isOk = true;
	$("#topHide", parent.window.document).show();
	$("#leftHide", parent.window.document).show();
	var url = "../publishLine/addLineMap.action?id="+id+"&lng="+lng+"&lat="+lat;
	$("#showPage").load(url); 
	$("#mainBody").show();
}

//返回上一步
function goback(){
	var url='';
	if(navigator.userAgent.indexOf("MSIE")>0){   
		url="line/getLinesList.action?level=two";
	}
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		url="../line/getLinesList.action?level=two";
	}
	window.location.href=url;
}

function getLineBelonger(){
	var url = '../publishLine/getLineBelonger.action';
	leaseGetAjax(url,'json',function(data){
		var id = "selectLineBelonger";
		$("#"+id).empty();
		$(data).each(function(i){
			$("<option value=' "+data[i].userId +" id="+data[i].userId+" '> "+data[i].userName+" </option>").appendTo($("#"+id));
		});
		createSelectSearch("lineManager",id);
	});
}

</script>