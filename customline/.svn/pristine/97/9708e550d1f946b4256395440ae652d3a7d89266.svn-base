<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html >
<head>
    
    <title>线路详情-详情</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
	<base href="<%=basePath%>">   
	<script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
	<link rel="stylesheet" href="css/stationPicker.css"/>
  </head>
  
  <body>
  <div id="mainBody" class="mainBody"></div>
  <div id="showPage" class="showPage"></div>
  <s:form id="addForm" action="line/updateLine.action" method="post" theme="simple">
  <div class="mt20 ml45">
    <input type="hidden" id="delOrderStartTimes" name="delOrderStartTimes" value=""/>
    <s:hidden theme="simple" id="lineBaseId" name="lineDetailVo.lineBaseId"></s:hidden>
    	<ul class="mt30 f12 gray2 clearfix" id="addLineUl">
			<li class="fl widthfull r-input32">
				<span class="fl w106 t-r">线路名称：<em class="red1">*</em></span>
				<s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" name="provinceCode" cssClass="fl r-input mr10" onchange="loadCity(this.value);" value="lineDetailVo.provinceCode"/>
			    <select name="cityCode" id="cityCode" class="fl r-input mr10" onchange="loadArea(this.value);">
			    	<option value="">--选择城市--</option>
			    </select>
			<input type="text" class="r-input w235 gray2" id="lineName" name="lineDetailVo.lineName" maxLength="24" value="${lineDetailVo.lineName}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
			</li>
			<li class="fl widthfull mt15 r-input32">
				<span class="fl w106 t-r">途经区域：<em class="red1">*</em></span>
				<select name="startArea" class="fl r-input mr10 areaCode" value="${lineDetailVo.startArea}">
			    	<option value="">--选择区域--</option>
			    </select>
				<span class="fl ml4 mr4 mt10">—</span>
			    <select name="endArea" class="fl r-input mr10 areaCode" value="${lineDetailVo.endArea}">
			    	<option value="">--选择区域--</option>
			    </select>
			</li>
			<li class="fl widthfull mt15"><span class="fl w106 t-r">站点地图：</span><span id="showImgSpan" class="fl gray2 mt5 mr5"><a href="javascript:void(0)" class="f12 blue1" onclick="showImage('${lineDetailVo.linePicUrl}')">${lineDetailVo.linePicUrl}</a></span><span class="p-r file-box fl" style="width:24px;"><input type="file" class="file2 p-a fl" name="photo" id="file1" style="top: 2px;" onchange="uploadLinePic();"/><input type="hidden" id="linePicUrl" name="linePicUrl" value="${lineDetailVo.linePicUrl}"/><span title="添加地图图片" class="file-ico ml4 mt5"></span></span></li>
			<s:iterator value="lineDetailVo.stationList" var="station" status="s">
			  <s:if test="%{#s.first}">
			      <!-- 起点 -->
			      <li class="fl widthfull mt15 r-input32" id="addLineFristLi"><span class="fl w106 t-r">上车线路：<em class="red1">*</em></span>
			        <!-- 站点名称 -->
					<input type="text" class="fl r-input w210 gray2" name="stationName" maxLength="24" value="${station.stationName}" id="stationNameStart" check="11" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
					<!-- 经度 -->
					<input id="loniStart" class="fl r-input w210 ml10 mr10 gray2" type="text" name="loni" value="${station.loni}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 纬度 -->
					<input id="latiStart" class="fl r-input w210 mr10 gray2" type="text" name="lati" value="${station.lati}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 站点图片URL -->
					<input id="picUrlStart" type="hidden" name="picUrl" value="${station.picUrl}"/>
					
					<span class="file-ico ml10 mr20" title="添加站点图片" onclick="goAddPicPage('${station.stationInfoId}','picUrlStart')"></span>
					<span class="addico mr5"></span>
					<a href="javascript:void(0)" class="f12 blue1" onclick="addAccessPoint()">添加途径点</a>
					<a href="javascript:void(0)" class="f12 blue1 ml10" onclick="goAddMapPage('Start','loniStart','latiStart')">选择经纬度</a>
			      </li>
			  </s:if>
			  <s:else>
			    <s:if test="%{#s.last}">
			      <!-- 终点 -->
			      <li class="fl widthfull mt15 r-input32" id="addLineLastLi"><span class="fl w106 t-r"></span>
			        <!-- 站点名称 -->
					<input type="text" class="fl r-input w210 gray2" name="stationName" maxLength="24" value="${station.stationName}" id="stationNameEnd" check="11" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
					<!-- 经度 -->
					<input id="loniEnd" class="fl r-input w210 ml10 mr10 gray2" type="text" name="loni" value="${station.loni}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 纬度 -->
					<input id="latiEnd" class="fl r-input w210 mr10 gray2" type="text" name="lati" value="${station.lati}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 站点图片URL -->
					<input id="picUrlEnd" type="hidden" name="picUrl" value="${station.picUrl}"/>
					
					<span class="file-ico ml10" title="添加站点图片" onclick="goAddPicPage('${station.stationInfoId}','picUrlEnd')"></span>
					<a href="javascript:void(0)" class="f12 blue1 ml10" onclick="goAddMapPage('End','loniEnd','latiEnd')">选择经纬度</a>
					
				  </li>
			    </s:if>
			    <s:else>
			      <!-- 途经点 -->
			      <li class="fl widthfull mt15 r-input32"><span class="fl w106 t-r"></span>
			        <!-- 站点名称 -->
					<input type="text" class="fl r-input w210 gray2" name="stationName" maxLength="24" value="${station.stationName}" id="stationNameAccess${s.index}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
					<!-- 经度 -->
					<input id="loniAccess${s.index}" class="fl r-input w210 ml10 mr10 gray2" type="text" name="loni" value="${station.loni}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 纬度 -->
					<input id="latiAccess${s.index}" class="fl r-input w210 mr10 gray2" type="text" name="lati" value="${station.lati}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 站点图片URL -->
					<input id="picUrlAccess${s.index}" type="hidden" name="picUrl" value="${station.picUrl}"/>
					<!-- <span class="mapIco fl ml10" title="标注站点" onclick='goAddMapPage("stationNameAccess${s.index}","${station.lati}","${station.loni}")'></span> -->
					<span class="file-ico ml10 mr20" title="添加站点图片" onclick="goAddPicPage('${station.stationInfoId}','picUrlAccess${s.index}')"></span>
					<span id="linePoint${s.index}" count="${s.index}">途径点${s.index}</span><a href="javascript:void(0)" class="f12 blue1 ml10" onclick="deleteAccessPoint(this)">删除</a>
					<a href='javascript:void(0)' class='f12 blue1' id='btnA${s.index}' onclick='addAccessPointPreOrNext(${s.index})'>添加途径点</a>
					<a href='javascript:void(0)' class='f12 blue1 ml10' onclick="goAddMapPage('Access${s.index}','loniAccess${s.index}','latiAccess${s.index}')">选择经纬度</a>
				  </li>
			    </s:else>
			  </s:else>
			</s:iterator>
			
			
			<li class="fl widthfull mt15 r-input32"><span class="fl w106 t-r">预计行程时间：<em class="red1">*</em></span>
			<s:textfield id="lineTime" name="lineDetailVo.lineTime" maxLength="6" cssClass="r-input w235 fl" check="2"></s:textfield>
				<select class="p3 ml10" name="lineTimeType" onchange="changeLineTime();">
					<option value="0">分钟</option>
					<option value="1">小时</option>
				</select>
			</li>
			<li class="fl widthfull mt15"><span class="fl w106 t-r">里程：<em class="red1">*</em></span>
			     <s:textfield id="lineKm" name="lineDetailVo.lineKm" maxLength="6" cssClass="fl r-input w235 mr5" check="2"/>KM
			</li>
			<li class="fl widthfull mt15 r-input32"><span class="fl w106 t-r">选择线路类型：<em class="red1">*</em></span>
			<span class="fl mt5">
			<s:hidden theme="simple" name="lineDetailVo.lineSubType"></s:hidden>
			<s:if test="%{lineDetailVo.lineSubType==0}"><input type="radio" name="line" class="checkbox" checked="checked" disabled="disabled" value="0"/>上下班</s:if>
			<s:else><input type="radio" name="line" class="checkbox" disabled="disabled" value="0"/>上下班</s:else>
			<s:if test="%{lineDetailVo.lineSubType==1}"><input type="radio" name="line" class="checkbox ml10" disabled="disabled" checked="checked" value="1"/>旅游</s:if>
			<s:else><input type="radio" name="line" class="checkbox ml10" disabled="disabled" value="1"/>旅游</s:else>
			</span>
			</li>
			<li class="fl widthfull mt15"><span class="fl w106 t-r">班次时间：<em class="red1">*</em></span>
				<ul class="addClass-text fl" id="addClassUl" style="margin-left:0;">
		   			<li class="fw gray2 addLine-addClassTitle"><span class="fl w210 t-c">发车时间<em class="red1">*</em></span><span class="fl w170 t-c">座位数量<em class="red1">*</em></span><span class="fl w120 t-c">操作</span><span class="fl w120 t-l">工作时间</span><a href="javascript:void(0)" class="f12 blue1 fr mr20" onclick="addClassTime();">添加班次时间</a><span class="addico mr5 mt10 fr"></span></li>
		   			
		   			<s:iterator value="lineDetailVo.lineClassList" status="l">
			   			<li class="clearfix mt10 line24">
				   			<span class="fl w210">
				   				<span class="fl r-input w170 t-l ml20">
				   				<input type="text" name="orderStartTime" id="orderStartTimeStart" value="<s:property value='orderStartTime'/>" readonly="readonly" class="Wdate75 gray2 Wdate fl"  check="11" />
				   				</span>
				   			</span>
				   			<span class="fl w170 t-c"><input type="text" onkeyup="do_change_seats(this,'<s:property value="orderSeats"/>','<s:property value="lineDetailVo.lineBaseId"/>','<s:property value="orderStartTime"/>')" name="orderSeats" id="orderSeatsStart${l.index}" maxLength="3" class="r-input t-c w75 mr5 fl ml30"  value="<s:property value='orderSeats'/>" /><span class="t-l fl">位</span></span>
				   			<!-- 标识是否为新加班次  0为旧的  1为新加的-->
				   			<input id="theNewClassTimeFlag${l.index}" type="hidden" name="theNewClassTimeFlag" class="r-input t-c w75 mr5" value="0"/>
				   			<!-- 排班日期 -->
				   			<input id="orderDateStart${l.index}" type="hidden" name="orderDate" class="r-input t-c w75 mr5" value="<s:property value='orderDates'/>"/>
				   			<!-- 年月-->
				   			<input id="yearAndMonth${l.index}" type="hidden" name="yearAndMonth" class="r-input t-c w75 mr5"/>
				   			<!-- 班次是否包月 -->
				   			<input id="theSupportMonth${l.index}" type="hidden" name="supportMonth" class="r-input t-c w75 mr5"/>
				   			<span class="fl w120 t-c h24"><a href="javascript:void(0)" class="blue1" onclick='deleteClass("<s:property value='lineDetailVo.lineBaseId'/>","<s:property value='orderStartTime'/>",this)'>删除</a></span>
				   			<span class="fl w120 t-l">
				   				<a href="javascript:void(0)" class="blue1" onclick='goDatePage("yearAndMonth${l.index}","orderDateStart${l.index}","theSupportMonth${l.index}","<s:property value='lineDetailVo.lineBaseId'/>","<s:property value='orderStartTime'/>","onCorrectNew${l.index}");'>工作时间<span class="arrow arrow-desc ml4"></span></a>
				   				<span class="mt6 onCorrectNew" id="onCorrectNew${l.index}" style="display:none;"></span>
				   				<div id="onErrorNew${l.index}" class="tipTable" style="margin-top:-5px;margin-left:0;">请选择工作时间</div>
				   			</span>
			   			</li>
		   			</s:iterator>
		   		</ul>
			</li>
		</ul>
		<p class="mt20 f12 gray2"><span class="fl f12 w90 t-r">单次原价：<em class="red1">*</em></span>
			<s:textfield id="originalPrice" name="lineDetailVo.originalPrice" cssClass="r-input mr5 fl" maxLength="8"></s:textfield>元/次
		</p>
		<p class="mt20 f12 gray2"><span class="fl f12 w90 t-r">单次现价：<em class="red1">*</em></span>
			<s:textfield id="orderPrice" name="lineDetailVo.orderPrice" cssClass="r-input mr5 fl" maxLength="8"></s:textfield>元/次
		</p>
		<s:if test="%{lineDetailVo.lineSubType==0}">
			<p class="mt20 f12 gray2"><span class="fl f12 w90 t-r ">包月折扣：<em class="red1">*</em></span><span class="fl line24 ml10 gray2">设置包月折扣：</span>
				<s:textfield id="discountRate" name="lineDetailVo.discountRate" cssClass="r-input mr5 fl" check="2"></s:textfield><em class="f16">%</em>&nbsp;&nbsp;折
				<a href="javascript:void(0)" class="blue1 ml20" onclick="goMonthPricePage();">查看最近包月价格</a>
			</p>
		</s:if>
   		<div class="mt20 f12 gray2"><span class="fl w90 t-r">乘客须知：</span>
   		<textarea class="r-input more-show w87p" id="content" style="height:350px;" onkeyup="if(this.value!=null&&this.value!=''&&this.value.length > 5000) this.value=this.value.substr(0,5000)" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onblur="if(!value){value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}">${lineDetailVo.remark }</textarea>
   		<div style="display: none;">
   			<s:textarea name="lineDetailVo.remark" id="textarea_remark" maxLength="5000"  cssClass="dateTextarea gray2" cssStyle="width:884px;height:100px" onkeyup="if(this.value!=null&&this.value!=''&&this.value.length > 5000) this.value=this.value.substr(0,5000)"></s:textarea>
   		</div>
   		</div>
		<p class="t-c mt20 mb20">
		<a class="display-ib btn1 white ml30" href="javascript:void(0)" onclick="toSubmit();">保存</a>
		<a class="display-ib btn1 white" href="javascript:void(0)" onclick="toBack();">返回</a>
		</p>
	</div>
    </s:form>
  </body>
</html>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">
var lineBaseId = $("#lineBaseId").val();
function changeLineTime(){
   //改变时间类型时，清除行程时间输入框
   $("#lineTime").val("");
}
//验证所有途经点
function validateAccessPoint()
{
	//途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='startArea'],[name='endArea']").each(function()
	{
		if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
		{
			//createErrorTip(this.id,validateJson.isNotNull.tip);
		}
		if($(this).val()!=""&&$(this).val()!=0){
			if($(this).attr("name")=="lati"){
	               var lati= $(this).val();
	               if(lati<3||lati>53){
	            	   createErrorTip(this.id,"纬度不在中国范围内（3~53）");
	               }
				}
				if($(this).attr("name")=="loni"){
				   var loni= $(this).val();
	               if(loni<73||loni>135){
	            	   createErrorTip(this.id,"经度不在中国范围内（73~135）");
	               } 
				}
	    }
	});
	//座位数
	$("[name='orderSeats']").each(function()
	{
		regValidate(this.id,validateJson.IntegeNum.reg,validateJson.IntegeNum.tip);
	});
	
}

//返回上一步
function toBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../line/getLinesList.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="line/getLinesList.action?level=two";
	}
	window.parent.location.href=$the_url;
}

function toSubmit(){
  var flag = judgeForm();
  if(!flag){
	return;
  }
  //
  var lineName = $("#lineName").val();
  var url = "line/checkSameLineName.action?lineBaseId="+lineBaseId+"&lineName="+encodeURI(encodeURI(lineName))+"&lineType=0";
  var flag = true;
  //判断线路名称是否重复
  $.ajax({
		url:url,		
		cache:false,	
		async : false,
		success:function(data){
		  if(data=="error"){
			  flag = false;
		  }
		}
  });
  if(!flag){
  
     parent.parent.showRturnTip("线路名称已存在，请重新输入！",false); 
     return;
  }
  //判断站点名称是否存在空值 
  $("input[name='stationName']").each(function(){
        if($(this).val().length==0||$(this).val().indexOf("设置途径点")>-1){
          flag = false;
        }
  });
  if(!flag){
    
     parent.parent.showRturnTip("站点名称存在空值，请输入！",false); 
     return;
  }
  
   var stationNames = "";
   $("input[name='stationName']").each(function(){
        if($(this).val().length>0){
          stationNames+=$(this).val()+",";
        }
   });
   if(stationNames.length>0){
     	stationNames = stationNames.substring(0,stationNames.length-1);
     	if(checkRepetition(stationNames)){
		     parent.parent.showRturnTip("站点名称重复，请修改！",false); 
		     return;
     	}
   }
   var res = "";
  //发车时间不为空的情况下，判断发车时间不能重复(由于班次在无订单关联的情况下，可以被删除或全部删除，当有发车时间时，需要带发车时间到后台进行验证)
   var orderStartTimes = "";
	//判断班次时间是否有重复
	$("input[name='orderStartTime']").each(function(){
        orderStartTimes+=$(this).val()+',';
    });
    orderStartTimes = orderStartTimes.substring(0,orderStartTimes.length-1);
    var res = checkEmpty(orderStartTimes);
    if(res){
        parent.parent.showRturnTip("班次发车时间存在空值,请选择",false); 
        return;
    }
	res = checkRepetition(orderStartTimes);
	if(res){
	    parent.parent.showRturnTip("班次发车时间重复,请修改发车时间",false); 
	    return;
	}
	
	//orderSeat验证
	var orderSeats = "";
	$("input[name='orderSeats']").each(function(){
        orderSeats+=$(this).val()+',';
    });
    orderSeats=orderSeats.substring(0,orderSeats.length-1);
    res = checkEmpty(orderSeats);
    if(res){
        parent.parent.showRturnTip("班次座位数存在空值,请输入",false); 
        return;
    }
    res = checkNaN(orderSeats);
    if(!res){
       parent.parent.showRturnTip("班次座位数存在非数字,请修改",false); 
       return;
    }
	//orderDate验证
	$("input[name='orderDate']").each(function(){
	    if($(this).val().length==0){
	        res = false;
	    }
    });
    if(!res){
       parent.parent.showRturnTip("班次工作日存在空值,请设置",false); 
       return;
    }
    
 
  //判断包月折扣是否在1－100之间
	var discountRate = $("#discountRate").val();
	if(typeof(discountRate)!="undefined"&&discountRate.length>0){
	    //判断是否为整数
	    if(false==$.isNumeric(discountRate)){
	       parent.parent.showRturnTip("包月折扣必须为1-100之间的整数",false); 
	       return;
	    }
		if(parseInt(discountRate)>100||parseInt(discountRate)<1){
		   parent.parent.showRturnTip("包月折扣必须为1-100之间的整数",false); 
	       return;
		}
	}
   setSubmitDisale(true);
   //验证通过，则提交数据
  
   $("#textarea_remark").text(editor.html());
   var ops = editor.text();
	if(ops.length>6000){
		parent.parent.showPopCommonPage2("乘客须知最多输入5000个字");
		return;
	}
   	$("#addForm").ajaxSubmit({
		
		type : 'post',
		success : function(data) {
			if (data == "success") {
				parent.parent.showRturnTip("修改成功",true);
				
				var $the_url='';
				//判断是否是IE浏览器
				if(navigator.userAgent.indexOf("MSIE")>0){   
					$the_url="../line/getLinesList.action?level=two";
				}
				//谷歌和火狐
				if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
					$the_url="line/getLinesList.action?level=two";
				}
				window.parent.location.href=$the_url;
			}else if("error"==data){
				parent.parent.popLodeShowPage(false);
				parent.parent.showRturnTip("修改失败",false);
			}else if("existOrder"==data){
			    parent.parent.popLodeShowPage(false);
				parent.parent.showRturnTip("已删除的班次已经存在订单,修改失败",false);
			}
		}
	});
}

//检测数据是否重复,str为逗号分隔的字符串
function checkRepetition(str){
   var arrays = str.split(",");
   if(arrays.length==1){
      return false;
   }
   var flag = false; 
   var nary=arrays.sort();
   for(var i=0;i<arrays.length;i++){
	   if (nary[i]==nary[i+1]){
	      flag = true;
	      break;
	   }
   }
   return flag;
}

function checkNaN(str){
  var flag = true;
   var arr = str.split(",");
   for(var i=0;i<arr.length;i++){
      if($.isNumeric(arr[i])){
      }else{
        flag = false;
        break;
      }
   }
   return flag;
   
}

//检测数组元素是否为空，str为逗号分隔的字符串
function checkEmpty(str){
   var flag = false;
   var arr = str.split(",");
   for(var i=0;i<arr.length;i++){
      if(arr[i].length==0){
        flag = true;
        break;
      }
   }
   return flag;
}
function judgeForm(){
	 getthis.each(function(){
		var obj = this;
		checkByType(obj);
	});
	validateAccessPoint();
	selectValidate("provinceCode"); //省份
	selectValidate("cityCode");  //城市
	regValidate("orderPrice",validateJson.Money5Numer.reg,validateJson.Money5Numer.tip);
	regValidate("originalPrice",validateJson.Money5Numer.reg,validateJson.Money5Numer.tip);
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0)
	{
		
		//验证提交 防止多次提交
		setSubmitDisale(false);
		return true;
	}
	else
	{
		
		//验证提交 防止多次提交
		setSubmitDisale(true);
	    return false;
	}
}

//验证方法
function validateFunction()
{
	//validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [
	    {"validateName":"orderStartTimeStart:stationNameStart:stationNameEnd:lineTime","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		{"validateName":"lineTime","validateReg":validateJson.Intege.reg,"validateTip":validateJson.Intege.tip},//座位
		{"validateName":"discountRate","validateReg":validateJson.Discount.reg,"validateTip":validateJson.Discount.tip},//折扣
		{"validateName":"lineKm","validateReg":validateJson.Intege.reg,"validateTip":validateJson.Intege.tip}
		//{"validateName":"orderPrice:originalPrice","validateReg":validateJson.Money.reg,"validateTip":validateJson.Money.tip}
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
	$("#orderPrice,#originalPrice").focus(function(){  //价格
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidate(this.id,validateJson.Money5Numer.reg,validateJson.Money5Numer.tip);
	});
}

//不为空，有验证规则字段的验证方法
function regValidate(id,reg,tip)
{
	var thisRegs = new RegExp(reg);
	var valueText = $("#"+id).val();
	if(typeof(valueText) != "undefined" && valueText != "")
	{
		if(id == "orderPrice" || id == "originalPrice")
		{
			if(thisRegs.test($("#orderPrice").val()) && thisRegs.test($("#originalPrice").val()))
			{
				if(eval($("#orderPrice").val()) > eval($("#originalPrice").val()))
				{
					validateUserDefineClear(id);
					createErrorTip(id,"单次现价不可大于原价");
				}
				else if(typeof(eval($("#orderPrice").val())) == "undefined")
				{
					validateUserDefineClear("orderPrice");
					createErrorTip("orderPrice",tip);
				}
				else if(typeof(eval($("#originalPrice").val())) == "undefined")
				{
					validateUserDefineClear("originalPrice");
					createErrorTip("originalPrice",tip);
				}
				else if(typeof(eval($("#orderPrice").val())) == "undefined" && typeof(eval($("#originalPrice").val())) == "undefined")
				{
					validateUserDefineClear("orderPrice");
					validateUserDefineClear("originalPrice");
					createErrorTip("orderPrice",tip);
					createErrorTip("originalPrice",tip);
				}
				else
				{
					validateUserDefinedSucc("orderPrice");
					validateUserDefinedSucc("originalPrice");
				}
			}
			else
			{
				
				if(id == "originalPrice")
				{
					if(!thisRegs.test($("#originalPrice").val()) || typeof(eval($("#originalPrice").val())) == "undefined" || $("#originalPrice").val() == "")
					{
						validateUserDefineClear("originalPrice");
						createErrorTip("originalPrice",tip);
					}
					else if((typeof($("#orderPrice").val()) == "undefined" && typeof($("#originalPrice").val()) == "undefined") || ($("#originalPrice").val() == "" && $("#orderPrice").val() == ""))
					{
						validateUserDefineClear("orderPrice");
						validateUserDefineClear("originalPrice");
						createErrorTip("orderPrice",tip);
						createErrorTip("originalPrice",tip);
					}
				}
				else if(id == "orderPrice")
				{
					if(!thisRegs.test($("#orderPrice").val()) || typeof(eval($("#orderPrice").val())) == "undefined" || $("#orderPrice").val() == "")
					{
						validateUserDefineClear("orderPrice");
						createErrorTip("orderPrice",tip);
					}
					else if((typeof($("#orderPrice").val()) == "undefined" && typeof($("#originalPrice").val()) == "undefined") || ($("#originalPrice").val() == "" && $("#orderPrice").val() == ""))
					{
						validateUserDefineClear("orderPrice");
						validateUserDefineClear("originalPrice");
						createErrorTip("orderPrice",tip);
						createErrorTip("originalPrice",tip);
					}
				}
				
			}
		}
		else
		{
			if(thisRegs.test(valueText))
			{
				if(reg == validateJson.IntegeNum.reg)
				{
					if(valueText > 100)
					{
						createErrorTip(id,tip);
					}
					else
					{
						validateUserDefinedSucc(id);
					}
				}
			}
			else
			{
				createErrorTip(id,tip);
			}	
		}
	}
	else
	{
		createErrorTip(id,tip);
	}
} 
//查看最近包月价格
function goMonthPricePage()
{
	var classTime = "";
	for(var i = 0;i < $("input[name='orderStartTime']").length;i++)
	{
		classTime += $("input[name='orderStartTime']").eq(i).val()+",";
	}
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("line/viewMonthPrice.action?random="+Math.floor(Math.random()*10000+1),{lineBaseId:lineBaseId,classTime:classTime}); 
    $("#mainBody",parent.window.document).show();
}

//添加图片
function goAddPicPage(stationInfoId,picUrlId)
{
   
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("line/popAddLinePic.action?random="+Math.floor(Math.random()*10000+1),{stationInfoId:stationInfoId,id:picUrlId}); 
    $("#mainBody",parent.window.document).show();
}


function goAddMapPageById(id){
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage1",parent.window.document).show();
    $("#mainBody",parent.window.document).show();
    parent.createMapById(id);
}

function goAddMapPage(id,loniId,latiId)
{
	
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
   
    $("#showPage1",parent.window.document).show();
    $("#mainBody",parent.window.document).show();
    var lon = $("#"+loniId).val();
    var lat = $("#"+latiId).val();
    parent.createMap(id,lon,lat);
}


//将英文退换成数字
function replaceNum(obj){
	if(obj.value==obj.value2)return;if(obj.value.search(/^\d*(?:\.\d*)?$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}

//添加途径点
function addAccessPoint()
{
	 //ul li的个数
	 var count = ($("#addLineUl").children().length - 8)+1;
	 $("#addLineFristLi").after("<li class='fl widthfull mt15 r-input32'>"+
				"<span class='fl w106 t-r'></span>"+
				"<input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w210 gray3' value='设置途径点"+count+"' onfocus='if (value==defaultValue){value=\"\";$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){value=defaultValue;$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
				"<input id='loniAccess"+count+"' class='fl r-input w210 ml10 mr10 gray3' placeholder='经度' value='' type='text' name='loni' maxlength='32' onkeyup='replaceNum(this);'/>"+
				"<input id='latiAccess"+count+"' class='fl r-input w210 mr10 gray3' placeholder='纬度' value='' type='text' name='lati' maxlength='32' onkeyup='replaceNum(this);'/>"+
				"<input id='picUrlAccess"+count+"' type='hidden' name='picUrl'/>"+
				
				"<span class='file-ico ml10 mr20' id='pic"+count+"' title='添加站点图片' onclick='goAddPicPage(\"\",\"picUrlAccess"+count+"\")'></span><span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
				"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a><a href='javascript:void(0)' id='btnB"+count+"' class='f12 blue1 ml10' onclick='goAddMapPageById(\"Access"+count+"\")'>选择经纬度</a></li>");
	 //途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
			
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
		}).blur(function(){  
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
			{
				if(false){
					if($(this).val() == ""){
					    $(this).val(txt);  
					    $(this).removeClass("gray2").addClass("gray3");
					}
					createErrorTip(this.id,validateJson.isNotNull.tip);
				}
			}
			if($(this).val()!=""&&$(this).val()!=0){
				if($(this).attr("name")=="lati"){
		               var lati= $(this).val();
		               if(lati<3||lati>53){
		            	   createErrorTip(this.id,"纬度不在中国范围内（3~53）");
		               }
					}
					if($(this).attr("name")=="loni"){
					   var loni= $(this).val();
		               if(loni<73||loni>135){
		            	   createErrorTip(this.id,"经度不在中国范围内（73~135）");
		               } 
					}
		    }
		}); 
	});
	setOtherAccessPointNotEqOne();
}


//中间随意添加途经点
function addAccessPointPreOrNext(index)
{
	//ul li的个数
	var count = parseInt(parseInt(index)+parseInt(1));
	$("#stationNameAccess"+parseInt(index)).parent("li").after("<li class='fl widthfull mt15 r-input32'>"+
			"<span class='fl w106 t-r'></span><input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w210 gray3' value='设置途径点"+count+"' onfocus='if (value==defaultValue){value=\"\";$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){value=defaultValue;$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
			"<input id='loniAccess"+count+"' class='fl r-input w210 ml10 mr10 gray3' placeholder='经度' value='' type='text' name='loni' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='latiAccess"+count+"' class='fl r-input w210 mr10 gray3' placeholder='纬度' value='' type='text' name='lati' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='picUrlAccess"+count+"' type='hidden' name='picUrl'/>"+
			
			"<span class='file-ico ml10 mr20' id='pic"+count+"' title='添加站点图片' onclick='goAddPicPage(\"picUrlAccess"+count+"\")'></span><span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
			"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a><a href='javascript:void(0)' id='btnB"+count+"' class='f12 blue1 ml10' onclick='goAddMapPageById(\"Access"+count+"\")'>选择经纬度</a></li>");
	
	 //途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
		}).blur(function(){  
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
			{
				if(false){
					if($(this).val() == ""){
					    $(this).val(txt);  
					    $(this).removeClass("gray2").addClass("gray3");
					}
					createErrorTip(this.id,validateJson.isNotNull.tip);
				}
			}
			if($(this).val()!=""&&$(this).val()!=0){
				if($(this).attr("name")=="lati"){
		               var lati= $(this).val();
		               if(lati<3||lati>53){
		            	   createErrorTip(this.id,"纬度不在中国范围内（3~53）");
		               }
					}
					if($(this).attr("name")=="loni"){
					   var loni= $(this).val();
		               if(loni<73||loni>135){
		            	   createErrorTip(this.id,"经度不在中国范围内（73~135）");
		               } 
					}
		    }
		}); 
	});
	setOtherAccessPointNotEqOne();
}

//重置除第一个之外的其余的li相关属性
function setOtherAccessPointNotEqOne()
{
	//总共多少个途经点，除开起始点
	var surplusCount = 0;
	$("[name='stationName']").each(function(){
		surplusCount++;
	});
	for(var i = 0;i < surplusCount;i++)
	{
		if(i != 0 && i != (surplusCount - 1))
		{
			$("[name='stationName']").eq(i).parent("li").find("input").eq(1).attr("id","loniAccess"+parseInt(i));
			$("[name='stationName']").eq(i).parent("li").find("input").eq(2).attr("id","latiAccess"+parseInt(i));
			$("[name='stationName']").eq(i).parent("li").find("input").eq(3).attr("id","picUrlAccess"+parseInt(i));
			
	
			$("[name='stationName']").eq(i).parent("li").find("span").eq(1).attr({"id":"pic"+parseInt(i),"onclick":"goAddPicPage('picUrlAccess"+parseInt(i)+"')"});
			$("[name='stationName']").eq(i).parent("li").find("span").eq(2).attr({"id":"linePoint"+parseInt(i),"count":parseInt(i)}).text("途径点"+parseInt(i));
	
			$("[name='stationName']").eq(i).parent("li").find("a").eq(1).attr({"id":"btnA"+parseInt(i),"onclick":"addAccessPointPreOrNext('"+parseInt(i)+"')"});
			$("[name='stationName']").eq(i).parent("li").find("a").eq(2).attr({"id":"btnB"+parseInt(i),"onclick":"goAddMapPageById('Access"+parseInt(i)+"')"});
	
			if($("[name='stationName']").eq(i).parent("li").find("input").eq(0).val().indexOf("设置途径点") == 0)
			{
				$("[name='stationName']").eq(i).parent("li").find("input").eq(0).attr({"id":"stationNameAccess"+parseInt(i),"value":"设置途径点"+parseInt(i)});
			}
			else
			{
				$("[name='stationName']").eq(i).parent("li").find("input").eq(0).attr({"id":"stationNameAccess"+parseInt(i)});
			}
		}
	}
	$("[name='stationName']").unbind();
	$("[name='stationName']").bind("keyup",loadAvailableStationList);
}

//删除途径点
function deleteAccessPoint(obj)
{
	//本身的count数值
	var selfCount = $(obj).prev().attr("count");
	//要删除的li后面还剩几个li
	var surplusCount = $(obj).parent().nextAll("li").length - 5;
	for(var i = 0;i<surplusCount;i++)
	{
		$("#linePoint"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"linePoint"+parseInt(parseInt(i)+parseInt(selfCount)),"count":parseInt(parseInt(i)+parseInt(selfCount))}).text("途径点"+parseInt(parseInt(i)+parseInt(selfCount)));
		$("#latiAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr("id","latiAccess"+parseInt(parseInt(i)+parseInt(selfCount)));
		$("#loniAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr("id","loniAccess"+parseInt(parseInt(i)+parseInt(selfCount)));
		$("#picUrlAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr("id","picUrlAccess"+parseInt(parseInt(i)+parseInt(selfCount)));
		$("#map"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"map"+parseInt(parseInt(i)+parseInt(selfCount)),"onclick":"goAddMapPage('stationNameAccess"+parseInt(parseInt(i)+parseInt(selfCount))+"')"});
		$("#pic"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"pic"+parseInt(parseInt(i)+parseInt(selfCount)),"onclick":"goAddPicPage('picUrlAccess"+parseInt(parseInt(i)+parseInt(selfCount))+"')"});
		$("#btnA"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"btnA"+parseInt(parseInt(i)+parseInt(selfCount)),"onclick":"addAccessPointPreOrNext('"+parseInt(parseInt(i)+parseInt(selfCount))+"')"});
		if($("#stationNameAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).val().indexOf("设置途径点") == 0)
		{
			$("#stationNameAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"stationNameAccess"+parseInt(parseInt(i)+parseInt(selfCount)),"value":"设置途径点"+parseInt(parseInt(i)+parseInt(selfCount))});
		}
		else
		{
			$("#stationNameAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"stationNameAccess"+parseInt(parseInt(i)+parseInt(selfCount))});
		}
	}
	$(obj).parent().remove();
	setOtherAccessPointNotEqOne();
}

//上下班-工作时间
function goDatePage(yearAndMonth,id,supportMonth,lineBaseId,orderStartTime,onCorrectNew)
{
	var isShow = $("input[name='line']:checked").val();
	
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("line/popAddLineDate.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime+"&random="+Math.floor(Math.random()*10000+1),{yearAndMonth:yearAndMonth,id:id,theSupportMonth:supportMonth,lineBaseId:lineBaseId,orderStartTime:orderStartTime,isShow:isShow,onCorrectNew:onCorrectNew}); 
    $("#mainBody",parent.window.document).show();
}

function goDatePage1(id,supportMonth,yearAndMonth,onCorrectNew)
{
    var isShow = $("input[name='line']:checked").val();
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("line/addLineDateForEdit.action?random="+Math.floor(Math.random()*10000+1),{id:id,theSupportMonth:supportMonth,theYearAndMonth:yearAndMonth,isShow:isShow,onCorrectNew:onCorrectNew}); 
    $("#mainBody",parent.window.document).show();
}

//添加班次时间
function addClassTime()
{
	 var count = $("#addClassUl").children().length;
	 $("#addClassUl li:last").after("<li class='clearfix mt10 line24'>"+
	   			"<span class='fl w210'>"+
	   			"<span class='fl r-input w170 t-l ml20'><input type='text' id='orderStartTimeAccess"+count+"' name='orderStartTime' readonly='readonly' class='Wdate75 gray2 Wdate' onclick='WdatePicker({position:{left:-9,top:4},dateFmt:\"HH:mm\"})'/></span>"+
	   			"</span>"+
   				"<span class='fl w170 t-c'><input type='text' id='orderSeatsAccess"+count+"' name='orderSeats' maxLength='3' class='r-input t-c w75 mr5 fl ml30'/><span class='t-l fl'>位</span></span>"+
   				"<span class='fl w170 t-c'>"+
	   			"<input id='theNewClassTimeFlag"+count+"' type='hidden' name='theNewClassTimeFlag' class='r-input t-c w75 mr5' value='1'/>"+
   				"<input id='orderDateAccess"+count+"' type='hidden' name='orderDate' class='r-input t-c w75 mr5'/>"+
	   			"<input id='yearAndMonthAccess"+count+"' type='hidden' name='yearAndMonth' class='r-input t-c w75 mr5'/>"+
   				"<input id='supportMonthAccess"+count+"' type='hidden' name='supportMonth' class='r-input t-c w75 mr5'/>"+
   				"</span>"+
   				"<span class='fl w120 t-c h24'><a href='javascript:void(0)' class='blue1' onclick='deleteClassTime(this)'>删除</a></span>"+
   				"<span class='fl w120 t-l'><a href='javascript:void(0)' class='blue1' onclick='goDatePage1(\"orderDateAccess"+count+"\",\"supportMonthAccess"+count+"\",\"yearAndMonthAccess"+count+"\",\"onCorrectNew"+count+"\");'>工作时间<span class='arrow arrow-desc ml4'></span></a><span id='onCorrectNew"+count+"' class='mt6 onCorrectNew' style='display:none;margin-left:12px;'></span>"+
   				"<div id='onErrorNew"+count+"' class='tipTable' style='margin-top:-5px;margin-left:0;'>请选择工作时间</div>"+
   				"</span>"+
   			"</li>");
	//座位数
	$("[name='orderSeats']").each(function()
	{
		$(this).focus(function(){  //乘坐人数
			validateUserDefineClear(this.id);
			setSubmitDisale(false);
		}).blur(function(){  
			regValidate(this.id,validateJson.IntegeNum.reg,validateJson.IntegeNum.tip);
		});
	});
}

//
function deleteClass(lineBaseId,orderStartTime,obj){
  if($("input[name='orderStartTime']").length==1){
      parent.parent.showRturnTip("线路至少需要一个发车时间,不能删除",false); 
	  return;
  }
  //删除班次前需要查询班次下是否有订单
  var url = "line/judgeLineClassOrderCount.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime;
  $.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
		  if(data>=1){
			  parent.parent.showCommonTip("该班次已被订座，不能删除该班次！");
		  }else{
		      $(obj).parent().parent().remove();
		      var oldValue = $("#delOrderStartTimes").val();
		      $("#delOrderStartTimes").val(oldValue+orderStartTime+",");
		  }
		}
  });
}

//删除班次,适用于新增的班次
function deleteClassTime(obj)
{
    if($("input[name='orderStartTime']").length==1){
      parent.parent.showRturnTip("线路至少需要一个发车时间,不能删除",false); 
	  return;
    }
	$(obj).parent().parent().remove();
}

function loadOriginalCity(){
   var province = "${lineDetailVo.provinceCode}";
   if(null!=province&&province.length>0&&province!="null"){
      loadCity(province);
      var city = "${lineDetailVo.cityCode}";
      loadArea(city);
      $("[name='startArea']").val("${lineDetailVo.startArea}");
      $("[name='endArea']").val("${lineDetailVo.endArea}");
   }
}

//根据省份加载城市
function loadCity(value){
  	$.ajax({
		url:'merchantAction/getProvince.action?proId='+value,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value=''>--选择城市--</option>");
			$(".areaCode").empty();
			$(".areaCode").append("<option value=''>--选择区域--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
			loadSelectCity();
		}
	});
}

//根据城市加载区域
function loadArea(value){
	if(value==null || value==""){
		$(".areaCode").empty();
		$(".areaCode").append("<option value=''>--选择区域--</option>");
		return;
	}
  	$.ajax({
		url:'merchantAction/getProvince.action?proId='+value,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$(".areaCode").empty();
			$(".areaCode").append("<option value=''>--选择区域--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].areaName+"'>"+data[i].areaName+"</option>").appendTo($(".areaCode"));
			})
		}
	});
}

//加载原来的城市
function loadSelectCity(){
	var city = "${lineDetailVo.cityCode}";
		if(""!=city){
			var ops =  $("#cityCode option");
			for(var i=0;i<ops.length;i++){
				if(city==ops[i].value){
					ops[i].selected = true;
				}
			}
		}
}

function uploadLinePic(){
    var value = $("#file1").val();
	if(""==value||value.indexOf(".")==-1){
	   return;
	}
	/*var arr = value.split(".");
	var suffix = arr[arr.length-1];
	if(suffix!=("jpg") && suffix!=("png") && suffix!=("jpeg")){
		parent.parent.showRturnTip(validateJson.ImgFile.tip,false); 
		return;
	}*/
	var reg = validateJson.ImgFile.reg;
	var thisReg = new RegExp(reg);
	if(!thisReg.test(value))
	{
		parent.parent.showRturnTip(validateJson.ImgFile.tip,false); 
		return;
	}
    $.ajaxFileUpload({
			
			fileElementId:"file1", 
			secureuri:false,
			dataType: 'text',
			url : 'ftpUploadAction/upLoad.action?uploadFileType=1',
			success : function(data) {
    			//parent.popLodingPage(false);
				if (data == "error") {
					parent.parent.showRturnTip("上传失败!",false); 
				}else if(data=="overSize"){
					//图片过大，请上传不大于500K的图片
					parent.parent.showRturnTip("图片过大，请上传不大于500K的图片",false); 
				}else{
					data = eval("(" + data + ")");
					
					//显示原来的图片名称
					var pathArr = value.split("\\");
					var fileName = pathArr[pathArr.length-1];
					//相对路径
					$("#linePicUrl").val(data.dbFileUrl);
					//绝对路径
					var imgUrl = "<a href=\"javascript:void(0)\" class=\"f12 ml5 blue1\" onclick=\"showImage('"+data.dbFileUrl+"');\">"+fileName+"</a>";
					
					$("#showImgSpan").html(imgUrl);
				}
			},
			error:function(){
				parent.parent.showRturnTip("上传失败!",false); 
			}
     });
}
function showImage(value){
    //传值为相对路径
    $("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    var isLinePic="true";
    $("#showPage",parent.window.document).load("line/showDetailImage.action?random="+Math.floor(Math.random()*10000+1),{filePath:value,isLinePic:isLinePic}); 
    $("#mainBody",parent.window.document).show();
}
//清除value值
$(function(){
	
	//加载城市
	loadOriginalCity();
	validateFunction();
	//途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='startArea'],[name='endArea']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				
			} 
			validateUserDefineClear(this.id);
		}).blur(function(){  
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
			{
				if($(this).val() == ""){
				   $(this).val(txt);  
				 
				}
				createErrorTip(this.id,validateJson.isNotNull.tip);
			}
		}); 
	});
	//座位数
	$("[name='orderSeats']").each(function()
	{
		$(this).focus(function(){  //乘坐人数
			validateUserDefineClear(this.id);
			setSubmitDisale(false);
		}).blur(function(){  
			regValidate(this.id,validateJson.IntegeNum.reg,validateJson.IntegeNum.tip);
		});
	});
	$("#provinceCode").focus(function(){   //省份
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	}); 
	
	$("#cityCode").focus(function(){   //城市
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	});
});

//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
    $("#"+id).parents("li").find(".onCorrect").remove();
    $("#"+id).parent().append("<span id='"+id+"Tip'></span>");
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
	//座位数
	if($("#"+id).attr("name") == "orderSeats")
	{
		$("#"+id+"ErrTip").css("margin-left","30px");
		$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
	}
	else if(id == "originalPrice" || id == "orderPrice")
	{
		$("#"+id+"ErrTip").css("margin-left","90px");
		$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
	}
	else
	{
		if($("#"+id).prevAll("input").length > 0)
		{
			if(id == "endArea")
			{
				$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+($("#"+id).prevAll("input").length*$("#"+id).prevAll("input").width()+$("#"+id).prevAll("input").length*15+12)+"px");
			}
			else
			{
				$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+($("#"+id).prevAll("input").length*$("#"+id).prevAll("input").width()+$("#"+id).prevAll("input").length*15)+"px");
			}
		}
		else if($("#"+id).prevAll("select").length > 0)
		{
			$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+($("#"+id).prevAll("select").length*$("#"+id).prevAll("select").width()+$("#"+id).prevAll("select").length*15)+"px");
		}
		else
		{
			$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
		}
		$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
	}
}
//下拉框验证
function selectValidate(id)
{
	var selectIndex = $("#"+id).find("option:selected").index();
    if(selectIndex == 0)
    {
        createErrorTip(id,validateJson.Select.tip);
    }
    else
    {
         validateUserDefinedSucc(id);
    }
}
//自定义验证通过
function validateUserDefinedSucc(id)
{
    $("#"+id).parents("li").find(".onCorrect").remove();
    $("#"+id).parent().append("<span id='"+id+"Tip' class='mt4'></span>");
    $("#"+id+"Tip").addClass('onCorrect');
	$("#"+id+"ErrTip").hide();
}

//清除自定义验证提示语句
function validateUserDefineClear(id)
{
  $("#"+id+"ErrTip").hide();
  $("#"+id).parent().find(".onCorrect").remove();
}


$(function(){
	$(".sh-add-new").css("height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("height",$(window).height()-130+"px");	
	})
});
//创建文字编辑器
var editor;
KindEditor.ready(function(K) {
	var options = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist']
	}
	editor = K.create("#content",options);
});

var $theOldSeatsTemp=0;
var $orderStartTime='';
function do_change_seats(obj,theOldSeats,lineBaseId,orderStartTime){
	var $theNewSeats=$.trim($(obj).val());
	if($theNewSeats==''){
		parent.parent.showRturnTip("座位数量不能为空！");
		return false;
	}
	var reg = /^\+?[1-9][0-9]{0,1}$/;
	
	if(!reg.test($theNewSeats))
	{
		parent.parent.showRturnTip("座位数量应输入大于0小于100的正整数！");
		return false;
	}
	
	if($orderStartTime!=orderStartTime){
		$theOldSeatsTemp=theOldSeats;
		$orderStartTime=orderStartTime;
	}
	
	if($theNewSeats!=$theOldSeatsTemp){
		$.ajax({
			url:'line/judgeLineClassOrderSeats.action?lineBaseId='+lineBaseId+'&orderStartTime='+orderStartTime+'&theNewSeats='+$theNewSeats,		
			cache:false,	
			async : false,
			success:function(data){
			    var $strs=data.split(',');
				if($strs[0]=='-1'){
					parent.parent.showRturnTipLong("班次"+orderStartTime+"最大订票数为"+$strs[1]+"，座位数量不能小于"+$strs[1]+"！",false);
				}
				else{
					if($strs[0]<=0){
						parent.parent.showRturnTip("班次"+orderStartTime+"的座位数量修改失败！",false);
					}
					else{
						parent.parent.showRturnTip("座位数修改成功！",true);
						$theOldSeatsTemp=$theNewSeats;
					}
				}
			}
	  });
	}
}

</script>
<script src="http://api.map.baidu.com/api?v=1.3" type="text/javascript"></script>
<script type="text/javascript" src="js/line/common_util.js"></script>
<script type="text/javascript" src="js/line/lineAdd.js"></script>