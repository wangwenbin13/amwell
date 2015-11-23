<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html >
<head>
    <base href="<%=basePath%>">   
    <title>运营平台－创建线路</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
	<script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
	<link rel="stylesheet" href="css/stationPicker.css"/>
  </head>
  
  <body>
 <!-- 运营平台－创建线路-添加图片,添加地图 -->
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div id="showPagePic" class="showPage"></div>
    <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布专线 <span class="blue1 ml25"></span></p></div>
    <div class="steps">
    	<ol class="clearfix">
			<li class="active"><i>1</i><span class="tsl">填写基本信息</span></li>
			<li><i>2</i><span class="tsl">填写班次</span></li>
			<li><i>3</i><span class="tsl">创建完成</span></li>
		</ol>		
    </div>
    <s:form id="addForm" action="line/saveLineData.action" method="post" theme="simple">
	    <ul class="mt30 ml45 gray2 r-input32" id="addLineUl">
	    		<li class="fl widthfull mt15">
	    			<span class="fl w106 t-r">发起人手机号：</span>
	    			<input class="fl r-input w235" type="text" id="passengerTel" name="passengerTel" maxlength="11" onblur="checkTelephone(this);"/>
	    		</li>
	    		<li class="fl widthfull mt15">
	    			<span class="fl w106 t-r">发起人票价：</span>
	    			<input class="fl r-input w235" type="text" id="passengerCoupon" name="passengerCoupon" value="0" onblur="checkPrice(this);" maxlength="5" />
	    		</li>
				<li class="fl widthfull mt15">
					<span class="fl w106 t-r">线路名称：<em class="red1">*</em></span>
					<s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" name="provinceCode" cssClass="fl r-input mr10" onchange="loadCity(this.value);"/>
					<select name="cityCode" id="cityCode" class="fl r-input mr10" onchange="loadArea(this.value);">
						<option value="">--选择城市--</option>
					</select>
					<input id="lineName" type="text" name="lineName" maxLength="24" class="fl r-input w235 gray3" value="请输入专线名称" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onblur="if(!value){value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
				</li>
				<li class="fl widthfull mt15"><span class="fl w106 t-r">途经区域：<em class="red1">*</em></span>
				  <select name="startArea" id="startCode" class="fl r-input mr10 areaCode">
						<option value="">--选择区域--</option>
				  </select>
				  <span class="fl ml4 mr4 mt10">—</span>
				  <select name="endArea" id="endCode" class="fl r-input mr10 areaCode">
						<option value="">--选择区域--</option>
				  </select>
				</li>
				<li class="fl widthfull mt15"><span class="fl w106 t-r">站点地图：</span><span id="showImgSpan" class="fl mt5 mr5"></span><span class="p-r file-box fl" style="width:24px;"><input type="file" class="file2 p-a fl" name="photo" id="file1" style="top: 2px;" onchange="uploadLinePic();"/><input type="hidden" id="linePicUrl" name="linePicUrl"/><span title="添加地图图片" class="file-ico ml4 mt5"></span></span></li>
				<li class="fl widthfull mt15" id="addLineFristLi"><span class="fl w106 t-r">上车线路：<em class="red1">*</em></span>
					<input type="text" name="stationName" maxLength="24" id="stationNameStart" class="fl r-input w222" placeholder="设置起点位置" check="11" onfocus="if(value==defaultValue){$(this).removeClass('gray2').addClass('gray3');}" onblur="if(!value){$(this).removeClass('gray3').addClass('gray2');}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
					<!-- 经度 -->
					<input id="loniStart" class="fl r-input w222 ml10 mr10" placeholder="经度" type="text" name="loni" maxlength="32" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 纬度 -->
					<input id="latiStart" class="fl r-input w222 mr10" placeholder="纬度" type="text" name="lati" maxlength="32" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 站点图片URL -->
					<input id="picUrlStart" type="hidden" name="picUrl"/>
					<!-- 站点地址 -->
					<input id="addressStart" type="hidden" name="address"/>					
					<span class="file-ico ml10 mr20" title="添加站点图片" onclick="goAddPicPage('picUrlStart')"></span>
					<span class="addico mr5"></span>
					<a href="javascript:void(0)" class="f12 blue1" onclick="addAccessPoint()">添加途径点</a>
					<a href="javascript:void(0)" class="f12 blue1 ml10" onclick="goAddMapPage('Start')">选择经纬度</a>
				</li>
				<li class="fl widthfull mt15" id="addLineLastLi"><span class="fl w106 t-r"><em class="red1">*</em></span>
					<input type="text" name="stationName" maxLength="24" id="stationNameEnd" class="fl r-input w222 gray3" placeholder="设置终点位置" check="11" onfocus="if(value==defaultValue){$(this).removeClass('gray2').addClass('gray3');}" onblur="if(!value){value=defaultValue;$(this).removeClass('gray3').addClass('gray2');}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
					<!-- 经度 -->
					<input id="loniEnd" class="fl r-input w222 ml10 mr10" placeholder="经度" type="text" name="loni" maxlength="32" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 纬度 -->
					<input id="latiEnd" class="fl r-input w222 mr10" placeholder="纬度" type="text" name="lati" maxlength="32" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 站点图片URL -->
					<input id="picUrlEnd" type="hidden" name="picUrl"/>
					<!-- 站点地址 -->
					<input id="addressEnd" type="hidden" name="address"/>
					<span class="file-ico ml10" title="添加站点图片" onclick="goAddPicPage('picUrlEnd')"></span>
					<a href="javascript:void(0)" class="f12 blue1 ml10" onclick="goAddMapPage('End')">选择经纬度</a>
				</li>
				<li class="fl widthfull mt15"><span class="fl w106 t-r">预计行程时间：<em class="red1">*</em></span><input type="text" id="lineTime" name="lineTime" maxLength="6" class="fl r-input w235" check="2"/>
					<select class="p3 ml10" id="lineTimeType" name="lineTimeType" onchange="changeLineTime();">
						<option value="0">分钟</option>
						<option value="1">小时</option>
					</select>
				</li>
				<li class="fl widthfull mt15"><span class="fl w106 t-r">里程：<em class="red1">*</em></span>
				     <input type="text" id="lineKm" name="lineKm" maxLength="6" class="fl r-input w235 mr5" check="2"/>KM
				</li>
				<li class="fl widthfull mt15"><span class="fl w106 t-r">选择线路类型：<em class="red1">*</em></span><span class="fl mt5"><input type="radio" name="lineType" class="checkbox" checked="checked" value="0"/>上下班<input type="radio" name="lineType" class="checkbox ml10" value="1"/>旅游<input type="radio" name="lineType" class="checkbox ml10" value="2"/>招募</span></li>
				<li class="fl widthfull mt30"><span class="fl w106 t-r"></span><a class="display-ib btn1 white ml30" href="javascript:void(0)" onclick="goDiffPage()">下一步</a></li>
		</ul>
	</s:form>
  </body>
</html>
<script type="text/javascript">
function changeLineTime(){
   //改变时间类型时，清除行程时间输入框
   $("#lineTime").val("");
}
//清除value值
$(function(){
	validateFunction();
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				//$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
			if(this.id == "lineName")
			{
				validateUserDefineClear(this.id)
			}
		}).blur(function(){  
			if($(this).val() == ""){
			    $(this).val(txt);  
			   $(this).removeClass("gray2").addClass("gray3");
			}
			if(this.id == "lineName")
			{
				isExitLineName(this.id);
				$(this).removeClass("gray3").addClass("gray2");
			}
		});  
	});
	//切换线路类型
	$("input[name=lineType]").click(function(){
	   isExitLineName("lineName");
	   $(this).removeClass("gray3").addClass("gray2");
	});

	//途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='startArea'],[name='endArea']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				//$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
		}).blur(function(){  
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
			{
				//屏蔽空值校验
				if(false){
					if($(this).val() == ""){
					    $(this).val(txt);  
					   $(this).removeClass("gray2").addClass("gray3");
					}
					createErrorTip(this.id,validateJson.isNotNull.tip);
				}
			}
			if($(this).val()!=""){
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

//验证所有途经点
function validateAccessPoint()
{
	//途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='startArea'],[name='endArea']").each(function()
	{
		//屏蔽空值校验
		if(false){
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
			{
				createErrorTip(this.id,validateJson.isNotNull.tip);
			}
		}
		if($(this).val()!=""){
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
}
function judgeForm(){
	 getthis.each(function(){
		var obj = this;
		checkByType(obj);
	});

	var flag = isExitLineName("lineName");
	validateAccessPoint();
	selectValidate("provinceCode"); //省份
	selectValidate("cityCode");  //城市
	selectValidate("startCode");  //起始区域
	selectValidate("endCode");  //截止区域
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	
	if(failValidateCount == 0 && flag == "success")
	{
		//$('#action').val(checkNode());
		//验证提交 防止多次提交
		setSubmitDisale(false);
		return true;
	}
	else
	{
		//$('#action').val(checkNode());
		//验证提交 防止多次提交
		setSubmitDisale(true);
	    return false;
	}
}
//跳转到不同的页面
function goDiffPage()
{
      var flag = judgeForm();
	  if(!flag){
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
	  $("#addForm").ajaxSubmit({
		type : 'post',
		success : function(data) {
			if (data == "success") {
				    var lineType = $("input[name='lineType']:checked").val();
					//上下班
					if(lineType == "0")  
					{
						$("#iframe_right", parent.parent.window.document).attr("src","../line/addClass.action");
					}
					//旅游
					else if(lineType == "1")  
					{
						$("#iframe_right", parent.parent.window.document).attr("src","../line/addClassFreeLine.action");
					}
					//招募
					else if(lineType == "2")  
					{
						$("#iframe_right", parent.parent.window.document).attr("src","../line/addClassRecruitLine.action");
					}
			}else if(data=="repetition"){
			       parent.parent.showRturnTip("线路名称重复,请修改线路名称",false); 	
			}else{
			       parent.parent.showRturnTip("保存失败,请填写相关字段数据",false); 
			}
		}
	});
}

function isExitLineName(id)
{
	 var result = "";
	 //线路类型
     var lineType = $('input[name="lineType"]:checked').val();
     //提交时清空默认值
	 var substr = $('#'+id).val().substring(0,2);
	 if(substr == "请输" || substr == "设置")
	 {
		$('#'+id).val("");
	 }
	 //线路名称  如果输入全部都是空格则表示空字符串
	 var name = $('#'+id).val().trim();
     if(name == "")
     {
      	 result = "fail";
      	 createErrorTip(id,validateJson.isNotNull.tip);
      	 return result;
     }
     var url = "line/checkSameLineName.action?lineName="+encodeURI(encodeURI(name))+"&lineType="+lineType;
	 $.ajax({
		url:url,	
		cache:false,	
		async : false,
		//dataType:"html",	
		success:function(data){
			result = data;
		},
		error:function(){
			result = "fail";
		}
	});
	if(result == "error")
	{
	    createErrorTip(id,validateJson.name.tip);
	}
	else if(result == "success")
	{
		validateUserDefinedSucc(id);
	}
	else
	{
		createErrorTip(id,validateJson.isNotNull.tip);
	}
	return result;
}

//添加图片
function goAddPicPage(id)
{
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("line/addLinePic.action?random="+Math.floor(Math.random()*10000+1),{id:id}); 
    $("#mainBody").show();
}

//标注站点 地图查询页面
function goAddMapPage(id)
{
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("line/addLineMap.action?random="+Math.floor(Math.random()*10000+1),{id:id}); 
    $("#mainBody").show();
}

//将英文退换成数字
function replaceNum(obj){
	if(obj.value==obj.value2)return;if(obj.value.search(/^\d*(?:\.\d*)?$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}

//添加途径点
function addAccessPoint()
{
	 //ul li的个数
	 var count = 1;

	 var content = "<li class='fl widthfull mt15 r-input32'>"+
		"<span class='fl w106 t-r'></span>"+
		"<input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w222' placeholder='设置途径点"+count+"' onfocus='if (value==defaultValue){$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
		"<input id='loniAccess"+count+"' class='fl r-input w222 ml10 mr10' placeholder='经度' type='text' name='loni' maxlength='32' onkeyup='replaceNum(this);'/>"+
		"<input id='latiAccess"+count+"' class='fl r-input w222 mr10' placeholder='纬度' type='text' name='lati' maxlength='32' onkeyup='replaceNum(this);'/>"+
		"<input id='picUrlAccess"+count+"' type='hidden' name='picUrl'/>"+
		//"<span class='mapIco fl ml10' id='map"+count+"' title='标注站点' onclick='goAddMapPage(\"stationNameAccess"+count+"\")'></span>"+
		"<span class='file-ico ml10 mr20' id='pic"+count+"' title='添加站点图片' onclick='goAddPicPage(\"\",\"picUrlAccess"+count+"\")'></span><span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
		"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a><a href='javascript:void(0)' id='btnB"+count+"' class='f12 blue1 ml10' onclick='goAddMapPage(\"Access"+count+"\")'>选择经纬度</a></li>";
	 $("#addLineFristLi").after(content);

	 //途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				//$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
		}).blur(function(){  
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
			{
				//屏蔽空值校验
				if(false){
					if($(this).val() == ""){
					    $(this).val(txt);  
					   $(this).removeClass("gray2").addClass("gray3");
					}
					createErrorTip(this.id,validateJson.isNotNull.tip);
				}
				if($(this).val()!=""){
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
			"<span class='fl w106 t-r'></span><input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w222' placeholder='设置途径点"+count+"' onfocus='if (value==defaultValue){$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
			"<input id='loniAccess"+count+"' class='fl r-input w222 ml10 mr10' placeholder='经度' type='text' name='loni' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='latiAccess"+count+"' class='fl r-input w222 mr10' placeholder='纬度' type='text' name='lati' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='picUrlAccess"+count+"' type='hidden' name='picUrl'/>"+
			//"<input id='addressAccess"+count+"' type='hidden' name='address'/>"+
			//"<span class='mapIco fl ml10' id='map"+count+"' title='标注站点' onclick='goAddMapPage(\"stationNameAccess"+count+"\")'></span>"+
			"<span class='file-ico ml10 mr20' id='pic"+count+"' title='添加站点图片' onclick='goAddPicPage(\"picUrlAccess"+count+"\")'></span><span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
			"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a><a href='javascript:void(0)' class='f12 blue1 ml10' onclick='goAddMapPage(\"Access"+count+"\")'>选择经纬度</a></li>");
	//途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				//$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
		}).blur(function(){ 
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
			{
				//屏蔽空值校验
				if(false){
					if($(this).val() == ""){
					    $(this).val(txt);  
					   $(this).removeClass("gray2").addClass("gray3");
					}
					createErrorTip(this.id,validateJson.isNotNull.tip);
				}
			}
			if($(this).val()!=""){
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
			$("[name='stationName']").eq(i).parent("li").find("a").eq(2).attr({"onclick":"goAddMapPage('Access"+parseInt(i)+"')"});
	
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
	try{
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
	}catch(e){
       alert(e);
	}
}


//验证方法
function validateFunction()
{
	//validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [
	    {"validateName":"stationNameStart:stationNameEnd:lineTime:lineKm","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		{"validateName":"lineTime:lineKm","validateReg":validateJson.Intege.reg,"validateTip":validateJson.Intege.tip}//行程、里程检验
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
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
//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
    $("#"+id).parents("li").find(".onCorrect").remove();
    $("#"+id).parent().append("<span id='"+id+"Tip'></span>");
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
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
//根据省份加载城市
function loadCity(value){
	if(value==null || value==""){
		$("#cityCode").empty();
		$("#cityCode").append("<option value=''>--选择城市--</option>");
		return;
	}
  	$.ajax({
		url:'merchantAction/getProvince.action?proId='+value,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value=''>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
			$(".areaCode").empty();
			$(".areaCode").append("<option value=''>--选择区域--</option>");
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
function uploadLinePic(){
    var value = $("#file1").val();
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
    $.ajaxFileUpload({
			//dataType:'text',
			fileElementId:"file1", 
			secureuri:false,
			dataType: 'text',
			url : 'ftpUploadAction/upLoad.action?uploadFileType=1',
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
					$("#linePicUrl").val(data.dbFileUrl);
					//绝对路径
					var imgUrl = "<a href=\"javascript:void(0)\" class=\"f12 ml5 blue1\" onclick=\"showImage('"+data.dbFileUrl+"');\">"+fileName+"</a>";
					//alert("imgUrl:"+imgUrl);
					$("#showImgSpan").html(imgUrl);
				}
			},
			error:function(){
				parent.showRturnTip("上传失败!",false); 
			}
     });
}

function showImage(value){
    //传值为相对路径
    $("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    var isLinePic="true";
    $("#showPage").load("line/showImage.action?random="+Math.floor(Math.random()*10000+1),{filePath:value,isLinePic:isLinePic}); 
    $("#mainBody").show();
}

function checkTelephone(obj){
	var passengerTel = obj.value;
	$.ajax({
		url:'line/checkTelephone.action',
		cache:false,
		data:{passengerTel:passengerTel},
		dataTypt:"text",
		type:'post',
		success:function(data){
			if(data=="error"){
				parent.showRturnTip("手机号码错误！",false); 
				$("#passengerTel").val("");
			}else if(data=="success"){
// 				parent.showRturnTip("输入正确！",true); 
			}
		},
		error:function(){
			parent.showRturnTip("输入错误",false); 
		}
	})
}

function checkPrice(obj){
// 	return obj.value=obj.value=obj.value.replace(/^\-?[0-9]*\.?[0-9]*$/g,'');
	var price = obj.value;
	var regExp = /^(0|[0-9]{1,2})(\.[0-9]{1,2})?$/;
	if(!regExp.test(price)){
		parent.showRturnTip("输入错误",false); 
		return obj.value="";
	}
	
}
</script>
<!-- 操作地图的js --> 
<script src="http://api.map.baidu.com/api?v=1.3" type="text/javascript"></script>
<script type="text/javascript" src="js/line/common_util.js"></script>
<script type="text/javascript" src="js/line/lineAdd.js"></script>