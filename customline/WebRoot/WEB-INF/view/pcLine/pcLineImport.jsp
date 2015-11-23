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
    <title>运营平台－拼车管理-发布拼车线路</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
  </head>
 <body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：拼车管理 &gt; 发布拼车线路 <span class="blue1 ml25"></span></p></div>
<div class="mt10 ml20 mr20 black1">
    <div class="fl widthfull p-r">
        <div class="table-title">
             <ul><li class="on"><a href="javascript:void(0)" class="f14 blue2">发布拼车线路</a></li></ul>
        </div>
		<form id="addPcLineForm" action="pinCheLineImport/addLine.action" method="post">
			<div class="table2-text">
			  	<p class="bt-bot-line"><span class="red1">*字段为必须填写，不填写无法完成创建</span></p>
			 	  <div class="sh-add clearfix">
			  		<ul id="addLineUl">
			  			<li class="widthfull"><span class="w100 fl t-r"><em class="red1 mr4">*</em>发布者身份：</span><input type="radio" name="publisherFlag" class="checkbox" checked="checked" value="1"/>乘客<input type="radio" name="publisherFlag" class="checkbox ml10" value="2"/>车主(须至少填写车牌号信息，否则不保存车辆相关信息)</li>
			  			<li class="widthfull">
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em>姓名：</span><input type="text" id="nickName" name="nickName" maxlength="30" class="fl r-input w235 gray2 mr5"/>
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em>手机：</span><input type="text" id="telephone" name="telephone" maxlength="11" class="fl r-input w235 gray2 mr5"/>
			  			</li>
			  			<li class="widthfull">
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em>性别：</span><input type="radio" name="sex" class="checkbox" checked="checked" value="0"/>男<input type="radio" name="sex" class="checkbox ml10" value="1"/>女
			  			</li>
			  			<li class="widthfull">
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em>线路类型：</span><input type="radio" name="lineType" class="checkbox" checked="checked" value="1"/>上下班<input type="radio" name="lineType" class="checkbox ml10" value="2"/>长途
			  			</li>
			  			<li class="widthfull">
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em>单程/往返：</span><input type="radio" name="lineSubType" class="checkbox" checked="checked" value="1"/>单程<input type="radio" name="lineSubType" class="checkbox ml10" value="2"/>往返
			  			</li>
			  			<li class="widthfull">
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em>座位数：</span><input type="text" id="carSeats" name="carSeats" class="fl r-input w235 gray2 mr5"/>
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em>去程时间：</span>
			  				<!-- 上下班 -->
			  				<span class="fl r-input w235 t-l" id="goTimeSpan0"><input type="text" id="goTime" name="goTime" value="" readonly="readonly" class="fl Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})" /></span>
			  				<!-- 长途 -->
			  				<span class="fl r-input w235 t-l hide" id="goTimeSpan1"><input type="text" id="goTime" name="goTime" value="" readonly="readonly" class="fl Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm'})" /></span>
			  			</li>
			  			<li class="widthfull" id="timeLi">
			  				<span id="returnTimebigSpan">
				  				<span class="w100 fl t-r">返程时间：</span>
				  				<!-- 上下班 -->
				  				<span class="fl r-input w235 t-l" id="returnTimeSpan0"><input type="text" id="returnTime" name="returnTime" value="" readonly="readonly" class="fl Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})" /></span>
				  				<!-- 长途 -->
				  				<span class="fl r-input w235 t-l hide" id="returnTimeSpan1"><input type="text" id="returnTime" name="returnTime" value="" readonly="readonly" class="fl Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm'})" /></span>
			  				</span>
			  				<span id="repeatTimeSpan">
				  				<span class="w100 fl t-r">重复时间：</span><input type="text" id="repeatTime" name="repeatTime" class="fl r-input w235 gray2 mr5"/>
				  				<em class="ml10 gray1">多个时间请以中文顿号、隔开,如：周一、周二、周六、周日</em>
			  				</span>
			  			</li>
			  			<li class="widthfull">
			  				<span class="w100 fl t-r">预计行程时间：</span><input type="text" id="lineTime" name="lineTime" class="fl r-input w164 gray2 mr5" />
			  				<select class="p3 ml10 fl mr5" style="padding:1px" id="lineTimeType" name="lineTimeType" onchange="changeLineTime();">
								<option value="0">分钟</option>
								<option value="1">小时</option>
							</select>
							<span class="w100 fl t-r">里程：</span><input type="text" id="lineKm" name="lineKm" class="fl r-input w235 gray2 mr5" />
			  			</li>
			  			<li class="widthfull">
			  				<span class="w100 fl t-r">价格：</span><input type="text" id="realPrice" name="realPrice" class="fl r-input w235 gray2 mr5" />
			  				<span class="w100 fl t-r">车辆颜色：</span><input type="text" id="carColor" name="carColor" class="fl r-input w235 gray2 mr5"/>
			  			</li>
			  			<li class="widthfull">
			  				<span class="w100 fl t-r">车型：</span><input type="text" id="carModel" name="carModel" class="fl r-input w235 gray2 mr5"/>
			  				<span class="w100 fl t-r">车牌号：</span><input type="text" id="carNumber" name="carNumber" class="fl r-input w235 gray2 mr5"/>
			  				
			  			</li>
			  			<li class="widthfull" id="addLineFristLi">
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em>上车线路：</span>
			  				<input type="text" name="stationName" maxlength="24" id="stationNameStart" class="fl r-input w170 gray3" value="设置起点位置" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
							<!-- 经度 -->
							<input id="loniStart" class="fl r-input w170 ml10 mr10 gray3" value="经度" type="text" name="loni" maxlength="32" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
							<!-- 纬度 -->
							<input id="latiStart" class="fl r-input w170 mr10 gray3" value="纬度" type="text" name="lati" maxlength="32" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
							<!-- 站点城市-->
							<input id="cityNameStart" class="fl r-input w170 mr10 gray3" value="城市名" type="text" name="cityName"/>
							<span class="addico mr5"></span>
							<a href="javascript:void(0)" class="f12 blue1" onclick="addAccessPoint()">添加途径点</a>
			  			</li>
			  			<li class="widthfull" id="addLineLastLi">
			  				<span class="w100 fl t-r"><em class="red1 mr4">*</em></span>
			  				<input type="text" name="stationName" maxlength="24" id="stationNameEnd" class="fl r-input w170 gray3" value="设置终点位置" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
							<!-- 经度 -->
							<input id="loniEnd" class="fl r-input w170 ml10 mr10 gray3" value="经度" type="text" name="loni" maxlength="32" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
							<!-- 纬度 -->
							<input id="latiEnd" class="fl r-input w170 mr10 gray3" value="纬度" type="text" name="lati" maxlength="32" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
							<!-- 站点城市-->
							<input id="cityNameEnd" class="fl r-input w170 mr10 gray3" value="城市名" type="text" name="cityName"/>
			  			</li>
			  	  	</ul>
			  	  </div>
			  	<p class="t-c mt20"><input type="button" class="display-ib btn1 white mr40" value="保存" onclick="subMit();"/><input type="reset" onclick="javascript:history.go(-1);" class="display-ib btn1 white" value="取消"/></p>
			</div>
		</form>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
function changeLineTime(){
	showLi();
	$("[name='lineType'],[name='lineSubType']").click(function(){
		showLi();
		setSubmitDisale(false);
	});
}

function showLi()
{
	//线路类型  lineType
	var lineType = $("[name='lineType']:checked").val();
	//单程/往返   lineSubType
	var lineSubType = $("[name='lineSubType']:checked").val();
	if(lineType == 1 && lineSubType == 1) //上下班，单程
	{
		$("#timeLi").show();
		$("#repeatTimeSpan").show();
		$("#returnTimebigSpan").hide();
		$("#goTimeSpan0").show();
		$("#goTimeSpan1").hide();
	}
	else if(lineType == 1 && lineSubType == 2) //上下班，往返
	{
		$("#timeLi").show();
		$("#repeatTimeSpan").show();
		$("#returnTimebigSpan").show();
		$("#goTimeSpan0").show();
		$("#goTimeSpan1").hide();
		$("#returnTimeSpan0").show();
		$("#returnTimeSpan1").hide();
	} 
	if(lineType == 2 && lineSubType == 1) //长途，单程
	{
		$("#returnTimebigSpan,#repeatTimeSpan").hide();
		$("#goTimeSpan0").hide();
		$("#goTimeSpan1").show();
		$("#timeLi").hide();
	}
	else if(lineType == 2 && lineSubType == 2) //=长途，往返
	{
		$("#timeLi").show();
		$("#repeatTimeSpan").hide();
		$("#returnTimebigSpan").show();
		$("#goTimeSpan0").hide();
		$("#goTimeSpan1").show();
		$("#returnTimeSpan0").hide();
		$("#returnTimeSpan1").show();
	} 
}
//清除value值
$(function(){
	changeLineTime();	
	$("#carSeats").focus(function(){  //座位数
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidate(this.id,validateJson.Intege.reg,validateJson.Intege.tip);
	});
	$("#telephone").focus(function(){  //手机
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidate(this.id,validateJson.Phone.reg,validateJson.Phone.tip);
	});
	$("#lineTime,#lineKm,#realPrice").focus(function(){  //里程、价格、预计时间
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidateNull(this.id,validateJson.Money8Numer.reg,validateJson.Money8Numer.tip);
	});
	//途经点的验证 ;必填字段不为空判断
	$("[id='nickName'],[id='stationNameStart'],[id='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='cityName']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
			setSubmitDisale(false);
		}).blur(function(){  
			if($(this).val().indexOf("设置起点位置") == 0 || $(this).val().indexOf("设置终点位置") == 0 || $(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val().indexOf("城市名") == 0 || $(this).val() == "")
			{
				if($(this).val() == ""){
				    $(this).val(txt);  
				   $(this).removeClass("gray2").addClass("gray3");
				}
				createErrorTip(this.id,validateJson.isNotNull.tip);
			}
		}); 
	});
	$("[name='goTime']:visible").focus(function(){  //去程
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		if($(this).val() == "")
		{
			createErrorTip(this.id,validateJson.isNotNull.tip);
		}
		else
		{
			validateUserDefinedSucc(this.id);
		}
	});
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  	
			setSubmitDisale(false);
		}).blur(function(){  

		});  
	});
});

//验证所有途经点
function validateAccessPoint()
{
	//途经点的验证
	$("[id='nickName'],[name='goTime'],[id='stationNameStart'],[id='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='cityName']").each(function()
	{		
		if($(this).val().indexOf("设置起点位置") == 0 || $(this).val().indexOf("设置终点位置") == 0 || $(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val().indexOf("城市名") == 0 || $(this).val() == "")
		{
			createErrorTip(this.id,validateJson.isNotNull.tip);
		}
	});
	$("[name='goTime']:visible").each(function()  //去程
	{		
		if($(this).val() == "")
		{
			createErrorTip(this.id,validateJson.isNotNull.tip);
		}
		else
		{
			validateUserDefinedSucc(this.id);
		}
	});
	regValidate("carSeats",validateJson.Intege.reg,validateJson.Intege.tip);
	regValidate("telephone",validateJson.Phone.reg,validateJson.Phone.tip);
	regValidateNull("lineTime",validateJson.Money8Numer.reg,validateJson.Money8Numer.tip);
	regValidateNull("lineKm",validateJson.Money8Numer.reg,validateJson.Money8Numer.tip);
	regValidateNull("realPrice",validateJson.Money8Numer.reg,validateJson.Money8Numer.tip);
}

//保存
function subMit()
{
	var flag = judgeForm();
	if(!flag){
		return;
	}
	$("#addPcLineForm").ajaxSubmit({
		type : 'post',
		success : function(data) {
			if (data == "success") {
				parent.parent.showRturnTip("发布成功",true); 
			}else{
			    parent.parent.showRturnTip("保存失败,请填写相关字段数据",false); 
			}
		}
	});
}

function judgeForm(){
	 getthis.each(function(){
		var obj = this;
		checkByType(obj);
	});
	validateAccessPoint();
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0)
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

//将英文退换成数字
function replaceNum(obj){
	if(obj.value==obj.value2)return;if(obj.value.search(/^\d*(?:\.\d*)?$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}

//添加途径点
function addAccessPoint()
{
	 //ul li的个数
	 var count = 1;
	 $("#addLineFristLi").after("<li class='widthfull'>"+
				"<span class='fl w100 t-r'></span>"+
				"<input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w170 gray3' value='设置途径点"+count+"' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
				"<input id='loniAccess"+count+"' class='fl r-input w170 ml10 mr10 gray3' value='经度' type='text' name='loni' maxlength='32' onkeyup='replaceNum(this);'/>"+
				"<input id='latiAccess"+count+"' class='fl r-input w170 mr10 gray3' value='纬度' type='text' name='lati' maxlength='32' onkeyup='replaceNum(this);'/>"+
				"<input id='cityName"+count+"' type='text' name='cityName' class='fl r-input w170 mr10 gray3' value='城市名'/>"+
				"<span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
				"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a></li>");

	 //途经点的验证
	$("[id='stationNameStart'],[id='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='cityName']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
			setSubmitDisale(false);
		}).blur(function(){  
			if($(this).val().indexOf("设置起点位置") == 0 || $(this).val().indexOf("设置终点位置") == 0 || $(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val().indexOf("城市名") == 0 || $(this).val() == "")
			{
				if($(this).val() == ""){
				    $(this).val(txt);  
				   $(this).removeClass("gray2").addClass("gray3");
				}
				createErrorTip(this.id,validateJson.isNotNull.tip);
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
	$("#stationNameAccess"+parseInt(index)).parent("li").after("<li class='widthfull'>"+
			"<span class='fl w100 t-r'></span><input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w170 gray3' value='设置途径点"+count+"' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
			"<input id='loniAccess"+count+"' class='fl r-input w170 ml10 mr10 gray3' value='经度' type='text' name='loni' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='latiAccess"+count+"' class='fl r-input w170 mr10 gray3' value='纬度' type='text' name='lati' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='cityName"+count+"' type='text' name='cityName' class='fl r-input w170 mr10 gray3' value='城市名'/>"+
			"<span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
			"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a></li>");
	//途经点的验证
	$("[id='stationNameStart'],[id='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='cityName']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
			setSubmitDisale(false);
		}).blur(function(){  
			if($(this).val().indexOf("设置起点位置") == 0 || $(this).val().indexOf("设置终点位置") == 0 || $(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val().indexOf("城市名") == 0 || $(this).val() == "")
			{
				if($(this).val() == ""){
				    $(this).val(txt);  
				   $(this).removeClass("gray2").addClass("gray3");
				}
				createErrorTip(this.id,validateJson.isNotNull.tip);
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
			$("[name='stationName']").eq(i).parent("li").find("input").eq(1).attr("id","latiAccess"+parseInt(i));
			$("[name='stationName']").eq(i).parent("li").find("input").eq(2).attr("id","loniAccess"+parseInt(i));
			$("[name='stationName']").eq(i).parent("li").find("input").eq(3).attr("id","cityName"+parseInt(i));	
			$("[name='stationName']").eq(i).parent("li").find("span").eq(1).attr({"id":"linePoint"+parseInt(i),"count":parseInt(i)}).text("途径点"+parseInt(i));
			$("[name='stationName']").eq(i).parent("li").find("a").eq(1).attr({"id":"btnA"+parseInt(i),"onclick":"addAccessPointPreOrNext('"+parseInt(i)+"')"});
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
}

//删除途径点
function deleteAccessPoint(obj)
{
	//本身的count数值
	var selfCount = $(obj).prev().attr("count");
	//要删除的li后面还剩几个li
	var surplusCount = $(obj).parent().nextAll("li").length - 1;
	for(var i = 0;i<surplusCount;i++)
	{
		$("#linePoint"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"linePoint"+parseInt(parseInt(i)+parseInt(selfCount)),"count":parseInt(parseInt(i)+parseInt(selfCount))}).text("途径点"+parseInt(parseInt(i)+parseInt(selfCount)));
		$("#latiAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr("id","latiAccess"+parseInt(parseInt(i)+parseInt(selfCount)));
		$("#loniAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr("id","loniAccess"+parseInt(parseInt(i)+parseInt(selfCount)));
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
}

//不为空，有验证规则字段的验证方法
function regValidate(id,reg,tip)
{
	var thisRegs = new RegExp(reg);
	var valueText = $("#"+id).val();
	if(typeof(valueText) != "undefined" && valueText != "")
	{
		if(thisRegs.test(valueText))
		{
			validateUserDefinedSucc(id);
		}
		else
		{
			createErrorTip(id,tip);
		}	
	}
	else
	{
		createErrorTip(id,tip);
	}
}
//为空，有验证规则字段的验证方法
function regValidateNull(id,reg,tip)
{
	var thisRegs = new RegExp(reg);
	var valueText = $("#"+id).val();
	if(typeof(valueText) != "undefined" && valueText != "")
	{
		if(thisRegs.test(valueText))
		{
			validateUserDefinedSucc(id);
		}
		else
		{
			createErrorTip(id,tip);
		}	
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
	var left = $("#"+id).offset().left - $("#"+id).parents().offset().left;
	$("#"+id+"ErrTip").css("margin-left",left+"px");
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
</script>