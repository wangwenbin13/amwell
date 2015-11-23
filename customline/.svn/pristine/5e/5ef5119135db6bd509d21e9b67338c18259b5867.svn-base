<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>结算设置-新增结算</title>
<%@include file="../resource.jsp" %>

</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;结算设置&nbsp;>&nbsp;新增结算<span class="blue1 ml25"></span></p></div>
 <div class="mt20 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">新增结算</a></li>
                </ul>
            </div>
<s:form id="addForm" action="" method="post" theme="simple">

<div class="table2-text">
  	<p class="bt-bot-line"><span class="red1">*字段为必须填写，不填写无法完成创建</span></p>
 	  <div class="sh-add clearfix">
  		<ul>
  			<li class="mr1p widthfull"><span class="w100 fl t-r"><em class="red1 mr4">*</em>模板类型：</span>
	            <input type="radio" name="setName" value="保底" class="mr4 vf2" checked="checked"/>保底
	            <input type="radio" name="setName" value="保底+分成" class="mr4 vf2"/>保底+分成
	            <input type="radio" name="setName" value="分成" class="mr4 vf2"/>分成
          	</li>
  			<li class="widthfull"><span class="w110 fl t-c">&nbsp;&nbsp;&nbsp;结算方式：</span></li>
  			<li class="widthfull" id="li1"><span class="fl w110 t-r"><em class="red1 mr4">*</em>保底：</span><s:textfield  cssClass="r-input w15p gray2 fl mr4" theme="simple" maxlength="30" onkeyup="replaceNumInteger(this);" onafterpaste="replaceNumInteger(this);"/>位</li>
  			<li class="widthfull" id="li2">
	           <span class="fl w110 t-r"><em class="red1 mr4">*</em>周期：</span>
	            <span class="r-input fl w15p mr10"><input type="text" value="" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
	            <span class="fl">至</span>
	            <span class="r-input fl w15p ml10"><input type="text" value="" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
	        </li>
  			<li class="widthfull" id="li3"><span class="fl w110 t-r"><em class="red1 mr4">*</em>承运商分成：</span><s:textfield  cssClass="r-input w15p gray2 fl mr4" theme="simple" maxlength="30" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>%</li>
  			<li class="widthfull" id="li4"><span class="fl w110 t-r"><em class="red1 mr4">*</em>租车在线分成：</span><s:textfield  cssClass="r-input w15p gray2 fl mr4" theme="simple" maxlength="30" onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d*)?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"/>%</li>
  			
  	  	</ul>
  	  </div>
  	<p class="t-c mt20"><input type="button" class="display-ib btn1 white mr40" value="保存" /><input type="reset" onclick="javascript:history.go(-1);" class="display-ib btn1 white" value="取消"/></p>
</div>
</s:form>
</div>
</div>
</body>
</html>
<script>
$(function(){
	$("[name='setName']").click(function(){
		getCheckInfo()
	});
	getCheckInfo();
});

//模板类型:1,保底。2,保底+分成。3,分成
function getCheckInfo()
{
	var index = $("[name='setName']:checked").index();
	if(index == 1)
	{
		$("#li1").show().nextAll("li").hide();
	}
	else if(index == 2)
	{
		$("#li1,#li2,#li3,#li4").show();
	}
	else if(index == 3)
	{
		$("#li1,#li2").hide();
		$("#li3,#li4").show();
	}
}

//正整数，不可以输0
function replaceNumPositiveInteger(obj)
{
	if(obj.value.length==1){obj.value=obj.value.replace(/[^1-9]/g,'')}else{obj.value=obj.value.replace(/\D/g,'')}; 
	return obj.value;
}

//可以输0
function replaceNumInteger(obj)
{
	return obj.value=obj.value.replace(/\D/g,'');
}

//绘制窗体大小
function resizeWindow()
{
	//浏览器可视窗口的的高度  窗体整体高度减去头部和尾部相加的高度
	$(".table2-text").css("height",($(window).height()-125+"px"));
	
	$(window).resize(function() {
		$(".table2-text").css("height",($(window).height()-125+"px"));
	}); 
}
</script>