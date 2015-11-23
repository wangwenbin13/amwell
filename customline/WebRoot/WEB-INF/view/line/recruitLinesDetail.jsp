<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>招募线路-详情</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
	<script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
  </head>
                
  <body>
    <s:form action="line/updateRecruitLine.action" method="post" id="addForm" theme="simple">
    <ul class="mt30 ml45 f12 gray2" id="addLineUl">
            <input type="hidden" id="lineBaseId" name="lineBaseId" value="${recruitLineStationVo.lineBaseId}">
			<li class="fl widthfull r-input32"><span class="fl w106 t-r">线路名称：<em class="red1">*</em></span>
			<s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName"	headerKey="" headerValue="--选择省份--" name="provinceCode" cssClass="fl r-input mr10" onchange="loadCity(this.value);" value="recruitLineStationVo.provinceCode"/><select name="cityCode" id="cityCode" class="fl r-input mr10"><option value="">--选择城市--</option></select>
			<input type="text" class="r-input w235 gray2" id="lineName" name="lineName" maxLength="24" value="${recruitLineStationVo.lineName}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/></li>
			<li class="fl widthfull mt15 r-input32"><span class="fl w106 t-r">途经区域：<em class="red1">*</em></span>
				  <input type="text" name="startArea" id="startArea" maxLength="24" class="fl r-input w235" value="${recruitLineStationVo.startArea}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/><span class="fl ml4 mr4 mt10">—</span><input type="text" name="endArea" id="endArea" maxLength="24" class="fl r-input w235" value="${recruitLineStationVo.endArea}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
			</li>
			<li class="fl widthfull mt15"><span class="fl w106 t-r">站点地图：</span><span id="showImgSpan" class="fl gray2 mt5 mr5"  onclick=""><a href="javascript:void(0)" class="f12 blue1" onclick="showImage('${recruitLineStationVo.linePicUrl}')">${recruitLineStationVo.linePicUrl}</a></span><span class="p-r file-box fl" style="width:24px;"><input type="file" class="file2 p-a fl" name="photo" id="file1" style="top: 2px;" onchange="uploadLinePic();"/><input type="hidden" id="linePicUrl" name="linePicUrl" value="${recruitLineStationVo.linePicUrl}"/><span title="添加地图图片" class="file-ico ml4 mt5"></span></span></li>
			<s:iterator value="recruitLineStationVo.stationList" var="station" status="s">
			  <s:if test="%{#s.first}">
			      <!-- 起点 -->
			      <li class="fl widthfull mt15 r-input32" id="addLineFristLi"><span class="fl w106 t-r">上车线路：<em class="red1">*</em></span>
			        <!-- 站点名称 -->
					<input type="text" class="fl r-input w235 gray2" name="stationName" maxLength="24" value="${station.stationName}" id="stationNameStart" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
					<!-- 经度 -->
					<input id="loniStart" class="fl r-input w235 ml10 mr10 gray2" type="text" name="loni" value="${station.loni}"  maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 纬度 -->
					<input id="latiStart" class="fl r-input w235 mr10 gray2" type="text" name="lati" value="${station.lati}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 站点图片URL -->
					<input id="picUrlStart" type="hidden" name="picUrl" value="${station.picUrl}"/>
					<!-- <span class="mapIco fl ml10" title="标注站点" onclick='goAddMapPage("stationNameStart","${station.lati}","${station.loni}")'></span> -->
					<span class="file-ico ml10 mr20" title="添加站点图片" onclick="goAddPicPage('${station.stationInfoId}','picUrlStart')"></span>
					<span class="addico mr5"></span>
					<a href="javascript:void(0)" class="f12 blue1" onclick="addAccessPoint()">添加途径点</a>
			      </li>
			  </s:if>
			  <s:else>
			    <s:if test="%{#s.last}">
			      <!-- 终点 -->
			      <li class="fl widthfull mt15 r-input32" id="addLineLastLi"><span class="fl w106 t-r"></span>
			        <!-- 站点名称 -->
					<input type="text" class="fl r-input w235 gray2" name="stationName" maxLength="24" value="${station.stationName}" id="stationNameEnd" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
					<!-- 经度 -->
					<input id="loniEnd" class="fl r-input w235 ml10 mr10 gray2" type="text" name="loni" value="${station.loni}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 纬度 -->
					<input id="latiEnd" class="fl r-input w235 mr10 gray2" type="text" name="lati" value="${station.lati}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 站点图片URL -->
					<input id="picUrlEnd" type="hidden" name="picUrl" value="${station.picUrl}"/>
					
					<span class="file-ico ml10" title="添加站点图片" onclick="goAddPicPage('${station.stationInfoId}','picUrlEnd')"></span>
				  </li>
			    </s:if>
			    <s:else>
			      <!-- 途经点 -->
			      <li class="fl widthfull mt15 r-input32"><span class="fl w106 t-r"></span>
			        <!-- 站点名称 -->
					<input type="text" class="fl r-input w235 gray2" name="stationName" maxLength="24" value="${station.stationName}" id="stationNameAccess${s.index}" onkeyup="if(/-/.test(this.value)){this.value=this.value.replace(/-/,'');}"/>
					<!-- 经度 -->
					<input id="loniAccess${s.index}" class="fl r-input w235 ml10 mr10 gray2" type="text" name="loni" value="${station.loni}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 纬度 -->
					<input id="latiAccess${s.index}" class="fl r-input w235 mr10 gray2" type="text" name="lati" value="${station.lati}" maxlength='32' onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>
					<!-- 站点图片URL -->
					<input id="picUrlAccess${s.index}" type="hidden" name="picUrl" value="${station.picUrl}"/>
					
					<span class="file-ico ml10 mr20" title="添加站点图片" onclick="goAddPicPage('${station.stationInfoId}','picUrlAccess${s.index}')"></span>
					<span id="linePoint${s.index}" count="${s.index}">途径点${s.index}</span><a href="javascript:void(0)" class="f12 blue1 ml10" onclick="deleteAccessPoint(this)">删除</a>
					<a href='javascript:void(0)' class='f12 blue1' id='btnA${s.index}' onclick='addAccessPointPreOrNext(${s.index})'>添加途径点</a>
				  </li>
			    </s:else>
			  </s:else>
			</s:iterator>
			<li class="fl widthfull mt15 r-input32"><span class="fl w106 t-r">预计行程时间：<em class="red1">*</em></span><input type="text" class="r-input w235 fl" name="lineTime" maxLength="6" id="lineTime" value="${recruitLineStationVo.lineTime}" check="1"/>
				<select class="p3 ml10" name="lineTimeType" onchange="changeLineTime();">
					<option value="0">分钟</option>
					<option value="1">小时</option>
				</select>
			</li>
			<li class="fl widthfull mt15"><span class="fl w106 t-r">里程：<em class="red1">*</em></span>
				<input type="text" id="lineKm" name="lineKm" value="${recruitLineStationVo.lineKm}" maxLength="6" class="fl r-input w235 mr5" check="2"/>KM
			</li>
			<li class="fl widthfull mt15 r-input32"><span class="fl w106 t-r">选择线路类型：<em class="red1">*</em></span><span class="fl mt5">招募</span></li>
			<li class="fl widthfull mt15 p-r"><span class="fl w106 t-r">招募类型：<em class="red1">*</em></span>
				<s:if test="%{recruitLineStationVo.lineSubType==0}">上下班</s:if><s:if test="%{recruitLineStationVo.lineSubType==1}">自由行</s:if>
		   		 <div style="left: 107px; top: 29px;width:540px" class="dj-box p-r p-a">
		            <span class="arrow2 p-a" style="left:0"></span>
		            <ul class="clearfix">
						<li class="fl w98p mt10"><span class="fl w100">班次时间：</span>
						    <span class="fl"><input type="text" id="classTime" class="r-input w110" name="classTime" maxlength='64' value="${recruitLineStationVo.classTime}"><em class="ml10 gray1">多个班次时间请以英文,隔开,如：08:00,09:00</em></span>
					    </li>
						<li class="fl w98p mt10"><span class="fl w100">选择固定时间：</span>
							<p class="fl line24">
									<input type="checkbox" class="checkbox mr4" name="recruitFixTime" value="1" <s:if test="recruitLineStationVo.fixTimeList[0]==1">checked="checked"</s:if>/>周一
									<input type="checkbox" class="checkbox mr4" name="recruitFixTime" value="2" <s:if test="recruitLineStationVo.fixTimeList[1]==2">checked="checked"</s:if>/>周二
									<input type="checkbox" class="checkbox mr4 ml10" name="recruitFixTime" value="3" <s:if test="recruitLineStationVo.fixTimeList[2]==3">checked="checked"</s:if>/>周三
									<input type="checkbox" class="checkbox mr4 ml10" name="recruitFixTime" value="4" <s:if test="recruitLineStationVo.fixTimeList[3]==4">checked="checked"</s:if>/>周四
									<input type="checkbox" class="checkbox mr4 ml10" name="recruitFixTime" value="5" <s:if test="recruitLineStationVo.fixTimeList[4]==5">checked="checked"</s:if>/>周五
									<input type="checkbox" class="checkbox mr4 ml10" name="recruitFixTime" value="6" <s:if test="recruitLineStationVo.fixTimeList[5]==6">checked="checked"</s:if>/>周六
									<input type="checkbox" class="checkbox mr4 ml10" name="recruitFixTime" value="7" <s:if test="recruitLineStationVo.fixTimeList[6]==7">checked="checked"</s:if>/>周日
							</p>
						</li>
						<li class="fl w98p mt10 gray1"><span class="fl w95"></span>说明：勾选后，都会根据选择时间运行。</li>
		            </ul>
	        	</div>
			</li>
			<li class="fl w98p mt10" style="margin-top:180px;"><span class="fl w106 t-r">报名截止日期：<em class="red1">*</em></span><span class="fl r-input w170 t-l"><input type="text" name="deadlineTime" value="${recruitLineStationVo.deadlineTime}" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd'})" readonly="readonly" class="Wdate75 gray2 Wdate"></span></li>
			<li class="fl widthfull mt15" style="margin-top:15px;"><span class="fl w106 t-r">设置价格：<em class="red1">*</em></span><input type="text" class="fl r-input w235 gray2" name="orderPrice" id="orderPrice" maxLength="5" value="${recruitLineStationVo.orderPrice}" check="1"/>元/次</li>
			<li class="fl widthfull mt15" style="margin-top:15px;"><span class="fl w106 t-r">招募须知：</span>
				<textarea class="dateTextarea gray2" id="content" style="width:884px;height:100px" maxLength="5000" onkeyup="if(this.value!=null&&this.value!=''&&this.value.length > 5000) this.value=this.value.substr(0,5000)">${recruitLineStationVo.remark}</textarea>
			</li>
			<li style="display: none;">
	   			<textarea class="dateTextarea gray3" id="textarea_remark" name="remark" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onblur="if(!value){value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}" name="remark" maxLength="5000" style="width:875px;height:100px" onKeyUp="if(this.value.length > 5000) this.value=this.value.substr(0,5000)">最多输入5000个字</textarea>
	   		</li>
			<li class="fl widthfull mt30">
			     <span class="fl w100 t-r"></span>
			     <p class="mt20 mb20">
                 <a class="display-ib btn1 white ml5" href="javascript:void(0)" onclick="toSubmit();">保存</a>
                 <a class="display-ib btn1 white" href="javascript:void(0)" onclick="toBack();">返回</a>
			     </p>
			</li>
		</ul>
	</s:form>
  </body>
</html>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">
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
			createErrorTip(this.id,validateJson.isNotNull.tip);
		}
	});
}
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
//添加图片
function goAddPicPage(stationInfoId,picUrlId)
{
    
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("line/popAddLinePic.action?random="+Math.floor(Math.random()*10000+1),{stationInfoId:stationInfoId,id:picUrlId}); 
    $("#mainBody",parent.window.document).show();
}

//标注站点 地图查询页面
function goAddMapPage(id,lati,loni)
{
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
  
    $("#showPage1",parent.window.document).show();
    $("#mainBody",parent.window.document).show();
    setTimeout("parent.createMap('"+id+"')",300);
}

//将英文退换成数字
function replaceNum(obj){
	if(obj.value==obj.value2)return;if(obj.value.search(/^\d*(?:\.\d*)?$/)==-1)obj.value=(obj.value2)?obj.value2:'';else obj.value2=obj.value;
}
//添加途径点
function addAccessPoint()
{
	 //ul li的个数
	 var count = ($("#addLineUl").children().length - 14)+1;
	 $("#addLineFristLi").after("<li class='fl widthfull mt15 r-input32'>"+
			"<span class='fl w106 t-r'></span>"+
			"<input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w235 gray3' value='设置途径点"+count+"' onfocus='if (value==defaultValue){value=\"\";$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){value=defaultValue;$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
			"<input id='loniAccess"+count+"' class='fl r-input w235 ml10 mr10 gray3' value='经度' type='text' name='loni' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='latiAccess"+count+"' class='fl r-input w235 mr10 gray3' value='纬度' type='text' name='lati' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='picUrlAccess"+count+"' type='hidden' name='picUrl'/>"+
			
			"<span class='file-ico ml10 mr20' id='pic"+count+"' title='添加站点图片' onclick='goAddPicPage(\"\",\"picUrlAccess"+count+"\")'></span><span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
			"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a></li>");
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
	$("#stationNameAccess"+parseInt(index)).parent("li").after("<li class='fl widthfull mt15 r-input32'>"+
			"<span class='fl w106 t-r'></span><input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w235 gray3' value='设置途径点"+count+"' onfocus='if (value==defaultValue){value=\"\";$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){value=defaultValue;$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
			"<input id='loniAccess"+count+"' class='fl r-input w235 ml10 mr10 gray3' value='经度' type='text' name='loni' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='latiAccess"+count+"' class='fl r-input w235 mr10 gray3' value='纬度' type='text' name='lati' maxlength='32' onkeyup='replaceNum(this);'/>"+
			"<input id='picUrlAccess"+count+"' type='hidden' name='picUrl'/>"+
			
			"<span class='file-ico ml10 mr20' id='pic"+count+"' title='添加站点图片' onclick='goAddPicPage(\"picUrlAccess"+count+"\")'></span><span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
			"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a></li>");
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
			$("[name='stationName']").eq(i).parent("li").find("input").eq(3).attr("id","picUrlAccess"+parseInt(i));
			
	
			$("[name='stationName']").eq(i).parent("li").find("span").eq(1).attr({"id":"pic"+parseInt(i),"onclick":"goAddPicPage('picUrlAccess"+parseInt(i)+"')"});
			$("[name='stationName']").eq(i).parent("li").find("span").eq(2).attr({"id":"linePoint"+parseInt(i),"count":parseInt(i)}).text("途径点"+parseInt(i));
	
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
	var surplusCount = $(obj).parent().nextAll("li").length - 10;
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
function goDatePage()
{
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("line/popAddLineDate.action?random="+Math.floor(Math.random()*10000+1)); 
    $("#mainBody",parent.window.document).show();
}

//添加班次时间
function addClassTime()
{
	 $("#addClassUl li:last").after("<li class='clearfix mt10 line24'>"+
	   			"<span class='fl w210'>"+
	   				"<span class='fl r-input w170 t-l ml20'><input type='text' readonly='readonly' class='Wdate75 gray2 Wdate' onclick='WdatePicker({position:{left:-9,top:4},dateFmt:\"yyyy-MM-dd\"})'/></span>"+
	   			"</span>"+
   				"<span class='fl w170 t-c'><input type='text' class='r-input t-c w75 mr5'/>位</span>"+
   				"<span class='fl w120 t-c h24'><a href='javascript:void(0)' class='blue1' onclick='deleteClassTime(this)'>删除</a></span>"+
   				"<span class='fl w120 t-c'><a href='javascript:void(0)' class='blue1' onclick='goDatePage();'>工作时间<span class='arrow arrow-desc ml4'></span></a></span>"+
   			"</li>");		
}

//删除途径点
function deleteClassTime(obj)
{
	$(obj).parent().parent().remove();
}

function loadOriginalCity(){
   var province = "${recruitLineStationVo.provinceCode}";
   if(null!=province&&province.length>0&&province!="null"){
      loadCity(province);
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
			$("#cityCode").append("<option value='0'>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
			loadSelectCity();
		}
	});
}

//加载原来的城市
function loadSelectCity(){
	var city = "${recruitLineStationVo.cityCode}";
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

$(function(){
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

	$("#classTime").focus(function(){  //班次时间
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidateTime(this.id,validateJson.isTime.reg,validateJson.isTime.tip);
	});
});

//不为空，有验证规则字段的验证方法
function regValidateTime(id,reg,tip)
{
	var thisRegs = new RegExp(reg);
	var valueText = $("#"+id).val();
	var valueTextArr = "";
	if(valueText != "")
	{
		valueTextArr = valueText.split(",");
		for(i=0;i<valueTextArr.length;i++)
		{
			var valueText = valueTextArr[i];
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
	else
	{
		createErrorTip(id,tip);
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
	if(id == "classTime")
	{
		$("#"+id+"ErrTip").css("margin-left","0");
		$("#"+id+"ErrTip").css("margin-top","0");
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

//验证方法
function validateFunction()
{
	//validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [
	    {"validateName":"classTime","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
	    {"validateName":"lineTime","validateReg":validateJson.Intege.reg,"validateTip":validateJson.Intege.tip},
		{"validateName":"lineKm","validateReg":validateJson.Intege.reg,"validateTip":validateJson.Intege.tip},
		{"validateName":"orderPrice","validateReg":validateJson.Money.reg,"validateTip":validateJson.Money.tip}
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
}
function judgeForm(){
	 getthis.each(function(){
		var obj = this;
		checkByType(obj);
	});
	validateAccessPoint();
	selectValidate("provinceCode"); //省份
	selectValidate("cityCode");  //城市
	regValidateTime("classTime",validateJson.isTime.reg,validateJson.isTime.tip);//班次时间
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
function toSubmit(){
  var flag = judgeForm();
  if(!flag){
	return;
  }
  
  var lineBaseId = $("#lineBaseId").val();
  var lineName = $("#lineName").val();
   //判断站点名称是否存在空值 
  $("input[name='stationName']").each(function(){
        if($(this).val().length==0||$(this).val().indexOf("设置途径点")>-1){
          flag = false;
        }
  });
  if(!flag){
     //parent.parent.showCommonTip("线路名称已存在，请重新输入！");
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
  var url = "line/checkSameLineName.action?lineBaseId="+lineBaseId+"&lineName="+encodeURI(encodeURI(lineName))+"&lineType=2";
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
     //parent.parent.showCommonTip("线路名称已存在，请重新输入！");
     parent.parent.showRturnTip("线路名称已存在，请重新输入！",false); 
     return;
  }
  $("#textarea_remark").text(editor.html());
  var ops = editor.text();
	if(ops.length>6000){
		parent.parent.showPopCommonPage2("招募须知最多输入5000个字");
		return;
	}
  //验证通过，则提交数据
  
  $("#addForm").ajaxSubmit({
		
		type : 'post',
		success : function(data) {
			if (data == "success") {
				parent.parent.showRturnTip("修改成功",true);
				
				var $the_url='';
				//判断是否是IE浏览器
				if(navigator.userAgent.indexOf("MSIE")>0){   
					$the_url="../line/recruitLinesManager.action?level=two";
				}
				//谷歌和火狐
				if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
					$the_url="line/recruitLinesManager.action?level=two";
				}
				window.parent.location.href=$the_url;
			}else if("error"==data){
				parent.parent.popLodeShowPage(false);
				parent.parent.showRturnTip("修改失败",false);
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

//返回上一步
function toBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../line/recruitLinesManager.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="line/recruitLinesManager.action?level=two";
	}
	window.parent.location.href=$the_url;
}
</script>
