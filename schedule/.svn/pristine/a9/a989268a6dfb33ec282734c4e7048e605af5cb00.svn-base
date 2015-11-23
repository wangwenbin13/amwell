<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<meta name="Description" content="" />
<title>包车管理-待报价列表-待报价详情</title>
<jsp:include page="../resource.jsp"/>
<script src="../js/common/Util.js" type="text/javascript"></script>
</head>

<body>
    <form id="waitQuoteForm" action="../businessBidding/addBusinessBidding.action" method="post">
        <input id="businessBiddingId" name="businessBiddingId" type="hidden" value="${bcLineVo.businessBiddingId}" />
	    <!-- 线路ID -->
	    <input id="bc_line_id" name="bc_line_id" type="hidden" value="${bcLineVo.bc_line_id}" />
	    <!-- 进入此页面时，服务器的当前时间 -->
	    <input id="nowTime" type="hidden" value="${bcLineVo.nowTime}" />
	    <!-- 线路的过期时间 -->
	    <input id="expireTime" name="expireTime" type="hidden" value="${bcLineVo.expireTime}" />
	    <!-- 进入此页面时，线路的倒计时间 -->
	    <input id="remainingTime" type="hidden" value="${bcLineVo.remainingTime}" />
		<p class="subNav">当前位置：首页&nbsp;&gt;&nbsp;包车管理&nbsp;&gt;&nbsp;待报价详情<a class="red1 ml30" href="javascript:void(0)" onclick="javascript:history.go(-1);">返回</a></p>
		<div class="mt20 ml30 black1 mr28">
			<p class="fw f14 f-yahei">待报价详情</p>
			<p class="mt20 libg clearfix"><span class="fl ml20 lh28">乘客需求</span></p>	
			<div class="fl lineDetail-left mt20 ml15 mr30" style="height:80px;">
				<p class="clearfix">
				     <s:if test="%{bcLineVo.businessType==1}"><span class="display-ib lineKind lineKind-work fl">旅游包车</span></s:if>
				     <s:if test="%{bcLineVo.businessType==2}"><span class="display-ib lineKind lineKind-free">商务接送</span></s:if>
				     <s:if test="%{bcLineVo.businessType==3}"><span class="display-ib lineKind lineKind-hongkong">过港接送</span></s:if>
				     <!--  
					 <span class="fr ml20">预算<em class="f16 fw yellow4 mr4">${bcLineVo.expectPrice}</em>元</span>
					 -->
				</p>
				<p class="mt10 clearfix"><span class="fl">人数：${bcLineVo.totalPassengers}人</span><span class="fr ml20">车辆数：${bcLineVo.totalCars}辆</span></p>
				<p class="mt10 clearfix"><span class="fl" id="countDownSpanShow">还剩<em class="red4 fw ml4 mr4" id="countDownEmShow"></em>过期</span><span class="fl red4 fw hide" id="countDownEnd">报价已结束</span></p>
			</div>
			<div class="mt20 clearfix">
				<ul>
					<li class="mt5 fl w18p h20">发布时间：${bcLineVo.createOn}</li>
					<li class="mt5 fl w18p h20">车龄：<s:if test="%{bcLineVo.carAge==0}">不限</s:if><s:if test="%{bcLineVo.carAge==1}">1年以内</s:if><s:if test="%{bcLineVo.carAge==2}">3年以内</s:if><s:if test="%{bcLineVo.carAge==3}">5年以内</s:if></li>
					<li class="mt5 fl w43p h20">包车方式： <s:if test='%{bcLineVo.charteredMode==1}'>单程 </s:if><s:if test='%{bcLineVo.charteredMode==2}'>往返 </s:if><s:if test='%{bcLineVo.charteredMode==3}'>包天</s:if>&nbsp;&nbsp;&nbsp;&nbsp;<s:if test='%{bcLineVo.needInvoice==1}'>需发票（${bcLineVo.invoiceHeader}）</s:if><s:if test='%{bcLineVo.needInvoice==0}'>不需发票</s:if></li>
					<li class="mt5 fl w18p h20">出车时间：${bcLineVo.startTime}</li>
					<li class="mt5 fl w18p h20">起点：<span class="display-ib lineDoit lineDoit-start"></span><em class="green1 display-ib stationWn" title="${bcLineVo.beginAddress}" id="beginAddressSpan"><script type="text/javascript">$("#beginAddressSpan").text(Util.cutstr('${bcLineVo.beginAddress}',30))</script></em></li>
					<li class="mt5 fl w43p h20">
						<span class="fl w260">行程备注：<span id="tripDetailSpan"><script type="text/javascript">$("#tripDetailSpan").text(Util.cutstr('${bcLineVo.journeyRemark}',30))</script></span></span>
						<div class="p-r fl w55 h30 t-c" id="showTripDetail">
							<a class="display-ib btn1-red" href="javascript:void(0);">看详情</a>
							<div class="p-a w500 detail-div hide" id="tripDetailDiv">
								<div class="p-a detail-triangle"></div>
								<P class="fw f14 t-l">行程备注：</P>
								<p class="mt15 line20 t-l">${bcLineVo.journeyRemark}</p>
							</div>
						</div>
					</li>
					<li class="mt5 fl w18p h20">返程时间：${bcLineVo.returnTime}</li>
					<li class="mt5 fl w18p h20">终点：<span class="display-ib lineDoit lineDoit-end"></span><em class="red4 display-ib stationWn" title="${bcLineVo.endAddress}" id="endAddressSpan"><script type="text/javascript">$("#endAddressSpan").text(Util.cutstr('${bcLineVo.endAddress}',30))</script></em></li>
					<!--  <li class="mt5 fl w43p h20">需求备注：希望提供2年内新车，车内环境舒适。</li> -->
				</ul>
			</div>
			<p class="mt20 libg"><span class="fl ml20 lh28">报价</span></p>	
			<p class="mt20"><span class="fw f14 f-yahei t-c titleTop">车辆信息</span><span class="ml20 lh28 gray4">（以下信息为必填）</span></p>
			<ul class="mt20 widthfull" id="dirverCarUl">
	            <li class="clearfix p-r">
	            	<span class="w75 t-r fl">车辆类型：</span>
	            	<div class="fl r-input w130"><span id="carStyleDiv0" carStyleDiv="carStyleDiv">大巴</span><span class="fr sel-arrow mt10 mr10"></span></div>
	            	<select id="carStyle0" carStyle="carStyle" name="carType" class="p-a sel-demo w134 h29" style="left:75px;">
					    <option value="大巴">大巴</option>
					    <option value="中巴">中巴</option>
					    <option value="小巴">小巴</option>
					    <option value="商务车">商务车</option>
					    <option value="过港车">过港车</option>
					    <option value="轿车">轿车</option>
					    <option value="其他">其他</option>
					</select>
					<input type="text" class="r-input w100 fl ml4 hide" id="carOther0" carOther="carOther"/>
		            <span class="w75 t-r fl ml30">车辆座位数：</span>
		            <input type="text" class="r-input w70 fl" id="carSeat0" carSeat="carSeat" name="carSeats" maxlength="8" onkeyup="replaceNumPositiveInteger(this);" onafterpaste="replaceNumPositiveInteger(this);"/>
	            	<span class="w75 t-r fl ml10">车龄：</span>
		            <input type="text" class="r-input w70 fl" id="carNum0" carNum="carNum" name="carAge" maxlength="10" onkeyup="replaceNum(this);"/>
		            <span class="w75 t-r fl ml10">车辆数：</span>
		            <input type="text" class="r-input w70 fl" id="carCount0" carCount="carCount" name="totalCars" maxlength="8" onkeyup="replaceNumPositiveInteger(this);" onafterpaste="replaceNumPositiveInteger(this);"/>
	            	<input type="button" class="display-ib btn1 btn1-width white1 ml20 mr20 fw" value="增&nbsp;&nbsp;加" onclick="addNewOneLi(this);"/>
	            </li>
	        </ul>
			<p class="fw f14 f-yahei mt20 border-top ptb10"><span class="t-c titleTop">报价总额</span></p>
			<ul class="mt20">
				<li class="clearfix mt20 red1">司机餐费，司机住宿费，油费未勾选项将不计入报价总额，停车费，过路过桥费默认不计入。</li>
	            <li class="clearfix mt20">基本费用：<input type="text" class="r-input w130 mr8" maxlength="30" onkeyup="replaceNum(this);" id="baseFee" name="basicCost"/>元</li>
	            <li class="clearfix mt10">
		             <input type="checkbox" class="checkbox mr4" nocheck="true" />司机餐费：<input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" maxlength="30" onkeyup="replaceNum(this);" id="driverFoodFee" name="driverMealCost"/>元
		             <input type="checkbox" class="checkbox mr4 ml40" nocheck="true" />司机住宿费：<input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" maxlength="30" onkeyup="replaceNum(this);" id="driverStayFee" name="driverHotelCost"/>元
		             <input type="checkbox" class="checkbox mr4 ml40" nocheck="true" id="oilFee"/>油费：<input type="text" class="r-input r-input-gray w130 mr8" readonly="readonly" maxlength="30" onkeyup="replaceNum(this);" id="driverOilFee" name="oilCost" />元
	            </li>
	            <li class="clearfix mt20">报价总额：<em class="f20 fw red4 mr4" id="totalPrice">￥0</em>元</li>
	        </ul>
	       <p class="fw f14 f-yahei mt20 border-top ptb10"><span class="t-c titleTop">乘客须知</span></p>
	       <ul class="mt20">
	            <li class="mt10"><span class="square mr6"></span>不计入报价的项，若实际运行时，发生此类费用，将在行程结束时现场结算。</li>
	            <!-- 包车方式为包天才显示 -->
	            <!-- <li class="mt10 <s:if test='%{bcLineVo.charteredMode!=3}'>hide</s:if>"><span class="square mr6"></span>根据相关法规车辆正常使用时间为<em class="fw f14">6:00-2:00</em>（次日），司机工作时间为 <input type="text" class="r-input w60 mr4 ml4" value="8" maxlength="30" onkeyup="replaceNum(this);" id="outTime" name="driverWorkHour"/>小时，超出<em class="fw">1</em>小时之外的时间按 <input type="text" class="r-input w60 mr4 ml4" value="100" maxlength="30" onkeyup="replaceNum(this);" id="outTimeFee" name="overtimeCost"/>元/小时补足司机工资。</li> -->
	            <!-- 包车方式为包天才显示 -->
	            <!-- <li class="mt10 <s:if test='%{bcLineVo.charteredMode!=3}'>hide</s:if>"><span class="square mr6"></span>本次运行按<input type="text" class="r-input w60 mr4 ml4" value="200" maxlength="30" onkeyup="replaceNum(this);" id="outKm" name="limitMileage"/>公里/天算，超出公里数按<input type="text" class="r-input w60 mr4 ml4" value="4" maxlength="30" maxlength="30" onkeyup="replaceNum(this);" id="outKmFee" name="overmileageCost"/>元/公里另计。</li>  -->
	            <li class="mt10" id="oilFeeLi"><span class="square mr6"></span>本次报价不包含油费，客户用车完毕后与司机将油箱的油料补满。（以油表为准）。</li>
	            <!--<li class="mt10"><span class="square mr6"></span>报价之外的费用需在行程结束后现场支付。</li>-->
	        </ul>
	       <p class="fw f14 f-yahei mt20 border-top ptb10"><span class="t-c titleTop">退票须知</span></p>
	       <ul class="mt20">
	            <li class="mt10"><span class="square mr6"></span>发车前<input type="text" class="r-input w60 mr4 ml4" value="24" maxlength="30" onkeyup="replaceNum(this);" id="fullFee0" name="noReturn"/>小时不退款。</li>
	            <li class="mt10"><span class="square mr6"></span>在发车<input type="text" class="r-input w60 mr4 ml4" value="48" maxlength="30" onkeyup="replaceNum(this);" id="fullFee1" name="returnOneTime"/>小时前可以申请退款，退款手续费为百分之<input type="text" class="r-input w60 mr4 ml4" value="10" maxlength="30" onkeyup="replaceNum(this);" id="parent0" name="returnOnePer"/>。</li>
	            <li class="mt10"><span class="square mr6"></span>在发车<input type="text" class="r-input w60 mr4 ml4" value="24" maxlength="30" onkeyup="replaceNum(this);" id="fullFee2" name="returnTwoTime"/>小时前可以申请退款，退款手续费为百分之 <input type="text" class="r-input w60 mr4 ml4" value="20" maxlength="30" onkeyup="replaceNum(this);" id="parent1" name="returnTwoPer"/>。</li>
	        </ul>
	        <p class="mt20 libg"><span class="fl ml20 lh28">其他</span></p>	
	        <p class="fw f14 f-yahei mt20 ptb10"><span class="t-c titleTop">附加服务</span></p>
	     	<p class="mt20">
	     		<textarea class="r-input more-show w400" id="contentServer" onKeyUp="if(this.value!=null&&this.value!=''&&this.value.length > 10) this.value=this.value.substr(0,10)" onFocus="if(this.value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onBlur="if(!this.value){this.value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}"></textarea>
	     		<textarea id="additionalServices" name="additionalServices" style="display:none;" onKeyUp="if(this.value!=null&&this.value!=''&&this.value.length > 10) this.value=this.value.substr(0,10)"></textarea>
	     	</p>
	     	<p class="fw f14 f-yahei mt20 ptb10"><span class="t-c titleTop">备注信息</span></p>
	     	<p class="mt20">
	     		<textarea class="r-input more-show w400" id="contentRemark"></textarea>
	     		<textarea id="remark" name="remark" style="display:none;"></textarea>
	     		<div class="mt5 gray4"><em class="mr4">*</em>备注信息输入字数请限定在5000个字以内 </div> 
	     	</p>
	        <p class="clearfix mt20">
	       	    <input type="button" class="display-ib btn1 white1 mr30 fw" value="保&nbsp;&nbsp;存" id="submitBtn"/>
	       	    <a class="display-ib btn1-no" href="javascript:void(0);" onclick="clearValue();">取&nbsp;消</a>
	        </p>
		</div>
	</form>
</body>
</html>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script>
$(function(){
	$("input[nocheck]").click(function(){
		if(this.checked)
		{
			// 油费选中则不显示oilFeeLi，未选中则显示 
			if(this.id == "oilFee")
			{
				$("#oilFeeLi").hide();
			}
			$("#"+$(this).next("input").attr("id")).css("background","#f6f5f5");
			$(this).next("input").removeAttr("readonly","readonly");
			setSubmitDisale(false);	
		}
		else
		{
			// 油费选中则不显示oilFeeLi，未选中则显示 
			if(this.id == "oilFee")
			{
				$("#oilFeeLi").show();
			}
			//input框不可点击readonly=readonly
			$("#"+$(this).next("input").attr("id")+"ErrTip").hide();
			$("#"+$(this).next("input").attr("id")).css("background","#e9e8e8");
			$(this).next("input").attr("readonly","readonly").val("");
			//计算报价总额
			getTotalPrice();
			setSubmitDisale(false);	
		}
	});

	//行程备注
	$("#showTripDetail").hover(function(){
	    $("#tripDetailDiv").show();
	},function(){
		$("#tripDetailDiv").hide();
	});

	validateFunction();
	submitBtnClick();
	//显示倒计时
	showCountdown();
});

//设定倒数秒数  
var surplusSeconds = parseInt($("#remainingTime").val());  
//显示倒计时
function showCountdown(){  
	surplusSeconds -= 1;  
    var surplusCountdown = getCountdown(surplusSeconds);
	if(surplusCountdown == 0)
	{
		$("#countDownEnd").show();
		$("#countDownSpanShow").hide();
		$("#countDownEmShow").text('');
	}
	else
	{
		$("#countDownEnd").hide();
		$("#countDownSpanShow").show();
		$("#countDownEmShow").text(surplusCountdown);
	} 
    //每秒执行一次,showCountdown()  
    setTimeout("showCountdown()",1000);  
}  

//倒计时 剩余秒数
function getCountdown(surplusSeconds)
{
	//var endTime = new Date("2014/11/30,15:21:00");
	//var nowTime = new Date();
	//var surplusSeconds = ((endTime.getTime()-nowTime.getTime())/1000);
	var d = parseInt((surplusSeconds/3600)/24);
	var h = parseInt((surplusSeconds/3600)%24);
	var m = parseInt((surplusSeconds/60)%60);
	var s = parseInt(surplusSeconds%60);
	if(surplusSeconds <= 0)
	{
		return 0;
	}
	var str = "";
	if(d != 0)
	{
		str = d+"天";
	}
	if(h != 0)
	{
		str = str+h+"小时";
	}	
	if(m != 0)
	{
		str = str+m+"分";
	}
	if(s != 0)
	{
		str = str+s+"秒";
	}
	return str;
}

//添加一条新的信息
function addNewOneLi(obj)
{
	var count = ($("#dirverCarUl").children("li").length - 1) + 1;
	$(obj).parent("li").after("<li class='mt20 border-top ptb10 clearfix p-r'>"+
		"<span class='w75 t-r fl'>车辆类型：</span>"+
       	"<div class='fl r-input w130'><span id='carStyleDiv"+count+"' carStyleDiv='carStyleDiv'>大巴</span><span class='fr sel-arrow mt10 mr10'></span></div>"+
       	"<select id='carStyle"+count+"' carStyle='carStyle' name='carType' class='p-a sel-demo w134 h29' style='left:75px;top:20px'>"+
		    "<option value='大巴'>大巴</option>"+
		    "<option value='中巴'>中巴</option>"+
		    "<option value='小巴'>小巴</option>"+
		    "<option value='商务车'>商务车</option>"+
		    "<option value='过港车'>过港车</option>"+
		    "<option value='轿车'>轿车</option>"+
		    "<option value='其他'>其他</option>"+
		"</select>"+
		"<input type='text' class='r-input w100 fl ml4 hide' id='carOther"+count+"' carOther='carOther' />"+
		"<span class='w75 t-r fl ml30'>车辆座位数：</span>"+
	    "<input type='text' class='r-input w70 fl' id='carSeat"+count+"' name='carSeats' carSeat='carSeat' maxlength='8' onkeyup='replaceNumInteger(this);' onafterpaste='replaceNumInteger(this);'/>"+
		"<span class='w75 t-r fl ml10'>车龄：</span>"+
	    "<input type='text' class='r-input w70 fl' id='carNum"+count+"' name='carAge' carNum='carNum' maxlength='10' onkeyup='replaceNum(this);'/>"+
	    "<span class='w75 t-r fl ml10'>车辆数：</span>"+
	    "<input type='text' class='r-input w70 fl' id='carCount"+count+"' name='totalCars' carCount='carCount' maxlength='8' onkeyup='replaceNumInteger(this);' onafterpaste='replaceNumInteger(this);'/>"+
       	"<input type='button' class='display-ib btn1 btn1-width white1 ml20 mr20 fw' value='增&nbsp;&nbsp;加' onclick='addNewOneLi(this);'/>"+
       	"<a class='display-ib btn1-red' href='javascript:void(0);' onclick='deleteOneLi(this);'>删除</a>"+
       	"</li>");
     setOtherAccessPointNotEqOne();
     validateFunction();
}

//重置除第一个之外的其余的li相关属性
function setOtherAccessPointNotEqOne()
{
	//总共多少个添加的司机，除开第一条
	var surplusCount = 0;
	surplusCount = $("#dirverCarUl").children("li").length;
	for(var i = 0;i < surplusCount;i++)
	{
		if(i != 0)
		{
			$("#dirverCarUl").children("li").eq(i).find("div").eq(0).children("span").eq(0).attr("id","carStyleDiv"+parseInt(i)); //车辆类型div
			$("#dirverCarUl").children("li").eq(i).find("select").eq(0).attr("id","carStyle"+parseInt(i)); //车辆类型select
			$("#dirverCarUl").children("li").eq(i).find("input").eq(0).attr("id","carOther"+parseInt(i)); //其他
			$("#dirverCarUl").children("li").eq(i).find("input").eq(1).attr("id","carSeat"+parseInt(i)); //车辆座位数
			$("#dirverCarUl").children("li").eq(i).find("input").eq(2).attr("id","carNum"+parseInt(i));  //车龄
			$("#dirverCarUl").children("li").eq(i).find("input").eq(3).attr("id","carCount"+parseInt(i)); //车辆数
		}
	}
	setSubmitDisale(false);
}

//删除当前选中的信息
function deleteOneLi(obj)
{
	$(obj).parent().remove();
	setOtherAccessPointNotEqOne();
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

//验证只可以输入数字和小数，并且保留是2位小数点
function replaceNum(obj){
	if(obj.value == obj.value2)
	{
		return;
	}
	//^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$
	if(obj.value.search(/^\d*(?:\.\d*)?$/)==-1)
	{
		obj.value=(obj.value2) ? obj.value2:'';
	}
	else
	{	
		
		if(obj.value.indexOf(".") != -1)
		{
			var numArr = obj.value.split(".");
			if(numArr[1].length > 2)
			{
				obj.value = numArr[0]+"."+numArr[1].substring(0,2);
			}
		}
		obj.value2 = obj.value;
	}

}

//附加服务
var contentServerEditor;
KindEditor.ready(function(K) {
	var options = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist']
			/*afterChange : function() {
		      //this.count() 字数统计包含HTML代码
		      //this.count('text') 字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
		      //限制字数
		      var limitNum = 100;  //设定限制字数
		      $('#wordCount').text(limitNum); //输入显示
		      if(this.count() > limitNum) {
			       //超过字数限制自动截取
			       var strValue = editor.html();
			       strValue = strValue.substring(0,limitNum);
			       editor.html(strValue);   
			       return;   
		       } else {
			       //计算剩余字数
			       var result = limitNum - this.count(); 
			       $('#wordCount').text(result); //输入显示
		       }
		     } */
	}
	contentServerEditor = K.create("#contentServer",options);
});

//备注信息
var contentRemarkEditor;
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
	contentRemarkEditor = K.create("#contentRemark",options);
});

//获得焦点和失去焦点的验证方法
function validateFunction()
{
	//不为空验证
	$("#baseFee,#fullFee0,#fullFee1,#fullFee2,#parent0,#parent1,input[carNum='carNum'],input[carSeat='carSeat'],input[carCount='carCount']").focus(function(){  
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		isNotNullValidate(this.id);
		if(this.id == "baseFee")
		{
			//计算报价总额
			getTotalPrice();
		}
	});

	//不为空验证
	$("input[carOther='carOther']").focus(function(){  
		var selectIndex = $(this).prev("select").find("option:selected").index();
		if(selectIndex == $(this).prev("select").find("option").length -1) 
		{
			validateUserDefineClear(this.id);
			setSubmitDisale(false);
		}
	}).blur(function(){  
		var selectIndex = $(this).prev("select").find("option:selected").index();
		if(selectIndex == $(this).prev("select").find("option").length -1) 
		{
			isNotNullValidate(this.id);	
		}
	});
	
	//不为空验证
	$("#driverFoodFee,#driverStayFee,#driverOilFee").focus(function(){  
		if($(this).attr("readonly") != "readonly")
		{
			validateUserDefineClear(this.id);
			setSubmitDisale(false);
		}
	}).blur(function(){  
		if($(this).attr("readonly") != "readonly")
		{
			isNotNullValidate(this.id);
			//计算报价总额
			getTotalPrice();
		}
	});

	//车辆类型
	$("select[carStyle='carStyle']").each(function(){
		$(this).change(function(){
			var selectIndex = $(this).find("option:selected").index();
			if(selectIndex == $(this).find("option").length -1)
			{
				$(this).next("input").show();
			}
			else
			{
				$(this).next("input").hide();
			}
			$(this).prev("div").children("span").eq(0).text($(this).find("option:selected").text());
		});
	});
}

function selectValidate(id)
{
	var selectIndex = $("#"+id).find("option:selected").index();
	alert(selectIndex);
}
//计算报价总额
function getTotalPrice()
{
	var baseFee = $("#baseFee").val() == "" ? 0 : parseFloat($("#baseFee").val());
	var driverFoodFee = $("#driverFoodFee").val() == "" ? 0 : parseFloat($("#driverFoodFee").val());
	var driverStayFee = $("#driverStayFee").val() == "" ? 0 : parseFloat($("#driverStayFee").val());
	var driverOilFee = $("#driverOilFee").val() == "" ? 0 : parseFloat($("#driverOilFee").val());
	var totalPrice = (baseFee + driverFoodFee + driverStayFee + driverOilFee).toFixed(2);
	$("#totalPrice").text("￥"+totalPrice);
}

//保留小数点后面2位
/**
function tofloat(f,dec) { 
	if(dec < 0) return "Error:dec<0!"; 
	result = parseInt(f) + (dec == 0 ? "" : "."); 
	f -= parseInt(f); 
	if(f == 0) 
	{
		for(i=0;i<dec;i++)
		{
			result+='0'; 
		}
	}
	else { 
		for(i=0;i<dec;i++) f*=10; 
		{
			result+=parseInt(Math.round(f)); 
		}
	} 
	return result; 
} 
**/
//提交验证
function submitValidate()
{
	//不为空验证
	$("#baseFee,#fullFee0,#fullFee1,#fullFee2,#parent0,#parent1,input[carNum='carNum'],input[carSeat='carSeat'],input[carCount='carCount']").each(function(){  
		isNotNullValidate(this.id);		
	});
	
	//不为空验证
	$("input[carOther='carOther']").each(function(){ 
		var selectIndex = $(this).prev("select").find("option:selected").index();
		if(selectIndex == $(this).prev("select").find("option").length -1) 
		{
			isNotNullValidate(this.id);	
		}
	});
	
	//不为空验证
	$("#driverFoodFee,#driverStayFee,#driverOilFee").each(function(){  
		if($(this).attr("readonly") != "readonly")
		{
			isNotNullValidate(this.id);	
		}
	});
}

//一个form表单提交验证
function submitBtnClick()
{
	//一个form表单提交验证
	$('#submitBtn').click( function () {
		submitValidate();
		//failValidateCount = 0  代表页面全部验证通过
		// 乘客须知
		$("#additionalServices").text(contentServerEditor.html());
		var ops1 = contentServerEditor.text();
		if(ops1.length>1000){
			parent.showRturnTip("乘客须知最多输入1000个字","false");
			return;
		}

		// 备注信息
		$("#remark").text(contentRemarkEditor.html());
		var ops2 = contentRemarkEditor.text();
		if(ops2.length>1000){
			parent.showRturnTip("备注信息最多输入1000个字","false");
			return;
		}
		
		$("[name='carType']").each(function(){
			var selectIndex = $(this).find("option:selected").index();
			if(selectIndex == $(this).find("option").length -1) 
			{
				$(this).children("option:selected").val($(this).next("input").val());
			}
		})
		
		
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;
		if(failValidateCount == 0)
		{
			//$("#additionalServices").text(contentServerEditor.html());
		    //$("#remark").text(contentRemarkEditor.html());
		  	//需要先判断报价信息是否结束，结束则不保存，提示报价结束；否则保存
			//parent.showPopCommonPage2("此报价信息已结束，不能对其信息进行报价！","true","remind");
		    parent.popLodingPage(true);
		   	$("#waitQuoteForm").ajaxSubmit({
				type : 'post',
				success : function(data) {
					parent.popLodingPage(false);
					if (data == "success") {
							parent.showRturnTip("报价成功","true");
							/*var $the_url='';
							//判断是否是IE浏览器
							if(navigator.userAgent.indexOf("MSIE")>0){   
								$the_url="../line/getLinesList.action?level=two";
							}
							//谷歌和火狐
							if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
								$the_url="line/getLinesList.action?level=two";
							}*/
							
							//window.parent.location.href=$the_url;
							//已报价页面
							parent.openLeftMenu("../businessBidding/getAlreadyQuoteList.action");
						}else if("error"==data){
							parent.showRturnTip("报价失败","false");
							
						}else if("overtime"==data){
							//需要先判断报价信息是否结束，结束则不保存，提示报价结束；否则保存
							parent.showPopCommonPage2("此报价信息已结束，不能对其信息进行报价！","true","remind");
						}
					}
			});
			//验证提交 防止多次提交
			setSubmitDisale(false);	
		}
		else
		{
			window.scrollTo(100,250);
			//验证提交 防止多次提交
			setSubmitDisale(true);
		}
	});
}

//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}

//不为空，没有验证规则字段的 验证方法 ，多个id用冒号:隔开
function isNotNullValidate(ids)
{
	var idArr = "";
	if(ids != "")
	{
		idArr = ids.split(":");
		for(i=0;i<idArr.length;i++)
		{
			var valueText = $("#"+idArr[i]).val();
		   	//判断是否为空
			if("" == valueText || valueText.trim() == "")
			{
			    createErrorTip(idArr[i],validateJson.isNotNull.tip);
			}
			else
			{
				if(idArr[i] == "driverFoodFee" || idArr[i] == "driverStayFee" || idArr[i] == "driverOilFee")
				{
					//司机餐费、司机住宿费、油费 目前先这样处理
					if(valueText == "0" || valueText == "0.0" || valueText == "0.00" || valueText == "0.")
					{
						createErrorTip(idArr[i],"请输入大于0的数值");
					}
					else
					{
						validateUserDefinedSucc(idArr[i]);
					}
				}
				else
				{
					validateUserDefinedSucc(idArr[i]);
				}
				
			}
		}
	}
}

//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
	//同行中存在其他的错误提示语句，先隐藏其他的提示语句，然后再去创建新的提示语句
	$("#"+id).parents("li").find(".tipTable:visible").hide();
	$("#"+id+"ErrTip").remove();
	$("#"+id).parent().append('<span id="'+id+'ErrTip" class="tipTable display-ib"><span class="error mt5 ml4"></span><span class="red2" id="'+id+'ErrTipText"></span></span>');
   	$("#"+id+"ErrTipText").text(tip);
	//增加错误框input框颜色
	$("#"+id).css("background","#fcf0ee");
}

//自定义验证通过
function validateUserDefinedSucc(id)
{
	$("#"+id+"ErrTip").remove();
	//同行中存在其他错误提示语句，先将之前隐藏的提示语句展示出来，然后再去删除当前展示的这条错误提示语句
	var hiddenLength = 0;
	$("#"+id).parents("li").find(".tipTable:hidden").each(function(){
		hiddenLength++;
	});
	if(hiddenLength > 0)
	{
		$("#"+id).parents("li").find(".tipTable:visible").hide();
		$("#"+id).parents("li").find(".tipTable:hidden").eq(0).show();
	}
    //还原错误框input框颜色
	$("#"+id).css("background","#f6f5f5");
    setSubmitDisale(false);
}

//清除自定义验证提示语句
function validateUserDefineClear(id)
{
	$("#"+id+"ErrTip").remove();
     //还原错误框input框颜色
	$("#"+id).css("background","#f6f5f5");
    setSubmitDisale(false);
}

//验证提交 防止多次提交  flag 为true表示按钮不可点击，false表示按钮可点击
function setSubmitDisale(flag)
{
   	 $("#submitBtn").attr("disabled", flag);
}

//清空input框的值
function clearValue(){
	              
	setSubmitDisale(false);
	$('input:text,input:password,input:file,textarea').each(function(){ 
		$(this).val("");
		$("#"+this.id+"Tip").hide(); 
		$("#"+this.id+"ErrTip").hide();
		$("#"+this.id).css("background","#f6f5f5");
	});
	$('select').each(function(){ 
		$("#"+this.id).find("option").eq(0).attr("selected",true);
		$("#"+this.id+"Tip").hide(); 
		$("#"+this.id+"ErrTip").hide();
		$("#"+this.id).css("background","#f6f5f5");
	});
	$("select[carStyle='carStyle']").each(function(){
		$(this).next("input").hide();
		$(this).prev("div").children("span").eq(0).text($(this).find("option:selected").text());
	});
}
</script>