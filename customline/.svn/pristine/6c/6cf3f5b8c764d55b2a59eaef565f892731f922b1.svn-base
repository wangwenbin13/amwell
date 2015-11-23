<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <form id="addForm" action="line/saveLineData.action" method="post">
	    <ul class="mt30 ml45 f14 gray2 r-input32" id="addLineUl">
				<li class="fl widthfull"><span class="fl w106 t-r">线路名称：<em class="red1">*</em></span><input id="lineName" type="text" name="lineName" maxLength="24" class="fl r-input w235 gray3" value="请输入专线名称" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onblur="if(!value){value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}" onkeyup="this.value=this.value.replace(/-/,'')";/></li>
				<li class="fl widthfull mt15"><span class="fl w106 t-r">上车线路：<em class="red1">*</em></span>
					<input type="text" name="stationName" maxLength="24" id="stationNameStart" class="fl r-input w235 gray3" value="设置起点位置" check="11" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onblur="if(!value){value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}" onkeyup="this.value=this.value.replace(/-/,'')";/>
					<!-- 经度 -->
					<input id="latiStart" class="fl r-input w235 ml10 mr10 gray3" value="经度" type="text" name="lati"/>
					<!-- 纬度 -->
					<input id="loniStart" class="fl r-input w235 mr10 gray3" value="纬度" type="text" name="loni"/>
					<!-- 站点图片URL -->
					<input id="picUrlStart" type="hidden" name="picUrl"/>
					<!-- 站点地址 -->
					<input id="addressStart" type="hidden" name="address"/>

					<span class="file-ico ml10 mr20" title="添加站点图片" onclick="goAddPicPage('picUrlStart')"></span>
					<span class="addico mr5"></span>
					<a href="javascript:void(0)" class="f12 blue1" onclick="addAccessPoint()">添加途径点</a>
				</li>
				<li class="fl widthfull mt15" id="addLineLastLi"><span class="fl w106 t-r"><em class="red1">*</em></span>
					<input type="text" name="stationName" maxLength="24" id="stationNameEnd" class="fl r-input w235 gray3" value="设置终点位置" check="11" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onblur="if(!value){value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}" onkeyup="this.value=this.value.replace(/-/,'')";/>
					<!-- 经度 -->
					<input id="latiEnd" class="fl r-input w235 ml10 mr10 gray3" value="经度" type="text" name="lati"/>
					<!-- 纬度 -->
					<input id="loniEnd" class="fl r-input w235 mr10 gray3" value="纬度" type="text" name="loni"/>
					<!-- 站点图片URL -->
					<input id="picUrlEnd" type="hidden" name="picUrl"/>
					<!-- 站点地址 -->
					<input id="addressEnd" type="hidden" name="address"/>

					<span class="file-ico ml10" title="添加站点图片" onclick="goAddPicPage('picUrlEnd')"></span>
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
	</form>
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
				$(this).val(""); 
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
});


function judgeForm(){
	 getthis.each(function(){
		var obj = this;
		checkByType(obj);
	});

	var flag = isExitLineName("lineName");
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


//添加途径点
function addAccessPoint()
{
	 //ul li的个数
	 var count = ($("#addLineUl").children().length - 7)+1;
	 $("#addLineLastLi").prev().after("<li class='fl widthfull mt15'>"+
				"<span class='fl w106 t-r'></span><input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w235 gray3' value='设置途径点"+count+"' onfocus='if (value==defaultValue){value=\"\";$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){value=defaultValue;$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='this.value=this.value.replace(/-/,\"\")';/>"+
				"<input id='latiAccess"+count+"' class='fl r-input w235 ml10 mr10 gray3' value='经度' type='text' name='lati'/>"+
				"<input id='loniAccess"+count+"' class='fl r-input w235 mr10 gray3' value='纬度' type='text' name='loni'/>"+
				"<input id='picUrlAccess"+count+"' type='hidden' name='picUrl'/>"+
				"<input id='addressAccess"+count+"' type='hidden' name='address'/>"+
				
				"<span class='file-ico ml10 mr20' id='pic"+count+"' title='添加站点图片' onclick='goAddPicPage(\"picUrlAccess"+count+"\")'></span><span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
				"<a href='javascript:void(0)' class='f12 blue1 ml10 mr20' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a></li>");
}

//中间随意添加途经点
function addAccessPointPreOrNext(index)
{
	//ul li的个数
	var count = parseInt(parseInt(index)+parseInt(1));
	$("#stationNameAccess"+parseInt(index)).parent("li").after("<li class='fl widthfull mt15'>"+
			"<span class='fl w106 t-r'></span><input type='text' name='stationName' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w235 gray3' value='设置途径点"+count+"' onfocus='if (value==defaultValue){value=\"\";$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){value=defaultValue;$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='this.value=this.value.replace(/-/,\"\")';/>"+
			"<input id='latiAccess"+count+"' class='fl r-input w235 ml10 mr10 gray3' value='经度' type='text' name='lati'/>"+
			"<input id='loniAccess"+count+"' class='fl r-input w235 mr10 gray3' value='纬度' type='text' name='loni'/>"+
			"<input id='picUrlAccess"+count+"' type='hidden' name='picUrl'/>"+
			"<input id='addressAccess"+count+"' type='hidden' name='address'/>"+
		
			"<span class='file-ico ml10 mr20' id='pic"+count+"' title='添加站点图片' onclick='goAddPicPage(\"picUrlAccess"+count+"\")'></span><span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
			"<a href='javascript:void(0)' class='f12 blue1 ml10 mr20' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a></li>");
	setOtherAccessPoint(count);
}

function setOtherAccessPoint(count)
{
	//selfCount 本身的count数值
	var selfCount = count;
	//要删除的li后面还剩几个li
	var surplusCount = $("#stationNameAccess"+count).parent("li").nextAll("li").length - 5;
	for(var i = 0;i<=surplusCount;i++)	{
		$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("input").eq(1).attr("id","latiAccess"+parseInt(i+selfCount));
		$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("input").eq(2).attr("id","loniAccess"+parseInt(i+selfCount));
		$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("input").eq(3).attr("id","picUrlAccess"+parseInt(i+selfCount));
		$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("input").eq(4).attr("id","addressAccess"+parseInt(i+selfCount));

		$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("span").eq(1).attr({"id":"pic"+parseInt(i+selfCount),"onclick":"goAddPicPage('picUrlAccess"+parseInt(i+selfCount)+"')"});
		$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("span").eq(2).attr({"id":"linePoint"+parseInt(i+selfCount),"count":parseInt(i+selfCount)}).text("途径点"+parseInt(i+selfCount));

		$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("a").eq(1).attr({"id":"btnA"+parseInt(i+selfCount),"onclick":"addAccessPointPreOrNext('"+parseInt(i+selfCount)+"')"});

		if($("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("input").eq(0).val().indexOf("设置途径点") == 0)
		{
			$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("input").eq(0).attr({"id":"stationNameAccess"+parseInt(i+selfCount),"value":"设置途径点"+parseInt(i+selfCount)});
		}
		else
		{
			$("#addLineUl").children("li").eq(parseInt(i+selfCount+1)).find("input").eq(0).attr({"id":"stationNameAccess"+parseInt(i+selfCount)});
		}
	}
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

//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
    $("#"+id).parents("li").find(".onCorrect").remove();
    $("#"+id).parent().append("<span id='"+id+"Tip'></span>");
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
	$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
	$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
}

//自定义验证通过
function validateUserDefinedSucc(id)
{
    $("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id).parent().append("<span id='"+id+"Tip'></span>");
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
<!-- 操作地图的js --> 
<script src="http://api.map.baidu.com/api?v=1.3" type="text/javascript"></script>
