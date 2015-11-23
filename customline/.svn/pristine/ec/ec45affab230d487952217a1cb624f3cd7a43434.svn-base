<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销管理-优惠劵配置</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;优惠劵配置<span class="blue1 ml25"></span></p></div>
<s:form action="../coupon/updateCoupon.action" method="post" id="addForm" theme="simple">
<input type="hidden" name="couponId" value="${couponVo.couponId}"/>
 <div class="mt10 ml20 mr20 black1">
    <div class="table-title">
       <ul>
       	<li class="on"><a href="javascript:void(0)" class="f14 blue2">优惠劵配置</a></li>
       </ul>
    </div>
	 <div class="table2-text table2-text-height">
		<div class="xinxi-box clearfix">
		 <div class="fl xinxi-box-t f14 fw" style="margin-top:80px;">基本信息</div>
		 
		    <ul class="mt15 w80p fl">
                <li class="w58p mb10 clearfix">
                	<span class="fl w100 t-r">名称：<em class="red1">*</em></span><input type="text" class="r-input w50p" id="couponName" name="couponName" value="${couponVo.couponName}" maxlength="32"/>
                </li>
            	<li class="w58p mb10 clearfix">
            		<span class="fl w100 t-r">推广位置：<em class="red1">*</em></span>
  					<span class="black1"><input type="radio" class="checkbox" value="0" name="couponType" <s:if test="%{couponVo.couponType==0}">checked="checked"</s:if> /></span><em class="ml4">全部渠道</em>		
	              <span class="black1 ml10"><input type="radio" class="checkbox"  value="1" name="couponType" <s:if test="%{couponVo.couponType==1}">checked="checked"</s:if> /></span><em class="ml4">APP渠道</em>
	              <span class="black1 ml10"><input type="radio" class="checkbox" value="2" name="couponType" <s:if test="%{couponVo.couponType==2}">checked="checked"</s:if> /></span><em class="ml4">微信渠道</em>
            	</li>
            <s:if test="%{couponVo.selectPass != 3 }">
            	<li class="w58p mb10 clearfix" id="area">
             	<span class="fl w100 t-r">选择城市：<em class="red1">*</em></span>
             	<s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" name="provinceCode" id="provinceCode" cssClass="p3 mr10" onchange="loadCity(this.value);" value="couponVo.provinceCode"/>
	  			<select class="p3" name="cityCode" id="cityCode">
	  				<option value="">--选择城市--</option>
	  			</select>
         		  </li>
            </s:if>
           <li class="w58p mb10 clearfix">
             	<span class="fl w100 t-r">发放方式：<em class="red1">*</em></span>
  				<span class="black1"><input type="radio" class="checkbox" value="1" name="sendCouponType" <s:if test="%{couponVo.sendCouponType==1}">checked="checked"</s:if> /></span><em class="ml4">用户领取</em>		
              <span class="black1 ml10"><input type="radio" class="checkbox"  value="2" name="sendCouponType" <s:if test="%{couponVo.sendCouponType==2}">checked="checked"</s:if> /></span><em class="ml4">系统发放</em>
           </li>
           <li class="w58p mb10 clearfix">
             	<span class="fl w100 t-r">选择用户：<em class="red1">*</em></span>
  			<select class="p3" id="couponSelect" name="selectPass" onclick="closeArea();">
  				<option value="">请选择用户类型</option>
  				<s:if test="%{couponVo.selectPass==0}"><option value="0" selected="selected">全部用户</option></s:if>
  				<s:if test="%{couponVo.selectPass==1}"><option value="1" selected="selected">新用户</option></s:if>
  				<s:if test="%{couponVo.selectPass==3}"><option value="3" selected="selected">自定义用户</option></s:if>
  				<s:if test="%{couponVo.selectPass==4}"><option value="4" selected="selected">推荐有奖用户</option></s:if>
  			</select>
           </li>
           <li class="w58p mb10 clearfix" style="display:none;" id="userdefinedLi">
           		<span class="fl w100 t-r"></span>
           		<p class="fl w80p gray2">请输入自定义用户的账号：账号和账号之间用；隔开</p>
            	<textarea class="r-input more-show"  id="content" style="width:500px;height:200px;" name="teles" onkeyup="check(this);" onkeydown="check(this);" onblur="checkPhone(this);">${couponVo.teles}</textarea>
           	</li>
            </ul>
		</div>
		<div class="xinxi-box mt20" id="giftInfo">
		    <table width="100%" border="0" class="line24">
		        <tr>
		            <th scope="row" class="f14 fw xinxi-box-t">礼品信息</th>
		            <td valign="top">
		                <ul class="mt15 ml10">
		                	<li class="w58p mb10 clearfix">
		                		<span class="fl w100 t-r">选择礼品：<em class="red1">*</em></span>
		                		<span class="fl mr30" id="addGiftIcon">
		                			<span class="fl addico mr5 ml10"></span>
		                			<a href="javascript:void(0)" class="fl f12 blue1" onclick="showPopGiftPage()">点击替换礼品</a>
		                		</span>
		                		<input type="hidden" id="giftId" name="giftId" value="${couponVo.giftId}"/>
		                		<span class="fl f12 blue1 hide" id="giftCountsSpan">当前<em id="giftCounts"></em>个礼品</span>
		                	</li>
		                	<li class="w50p mb10 clearfix hide" id="giftLi">
		                		<div class="ml75 mr50 clearfix" id="giftDiv"></div>
		                	</li>
		                	<li class="w58p mb10 clearfix">
		                		<span class="fl w100 t-r">上线时间：<em class="red1">*</em></span><span class="r-input display-ib"><input type="text" class="Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d'})" id="txtOnlineDate" name="upLineTime" value="${couponVo.upLineTime}"/></span>
		                	</li>
		                    <li class="w58p mb10 clearfix">
		                    	<span class="fl w100 t-r">下线时间：<em class="red1">*</em></span><span class="r-input display-ib"><input type="text" class="Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'txtOnlineDate\');}'})" id="txtUnlineDate" name="downLineTime" value="${couponVo.downLineTime}"/></span>
		                    </li>
		                	<li class="w58p mb10 clearfix">
		                		<span class="fl w100 t-r">有效时间：<em class="red1">*</em></span>
				    			<span class="fl r-input display-ib mr5"><input type="text" class="Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'txtUnlineDate\');}'})" id="txtStartDate" name="effectiveTime" value="${couponVo.effectiveTime}"/></span>
		                		<span class="fl">至</span>
		                		<span class="fl r-input display-ib ml5"><input type="text" class="Wdate75 gray2 Wdate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'txtStartDate\');}'})" id="txtEndDate" name="expirationTime" value="${couponVo.expirationTime}"/></span>
		                	</li>
		                	<li class="w58p mb10 clearfix">
		                    	<span class="fl w100 t-r">发行数量：<em class="red1">*</em></span><input type="text" class="r-input w50p" id="couponCount" name="issueNum" onkeyup="replaceNumInteger(this);" onafterpaste="replaceNumInteger(this);" value="${couponVo.issueNum}" maxlength="9"/>
		                    </li>
				             <li class="w58p mb10 clearfix">
			                	<span class="fl w100 t-r">发放/限领份数：<em class="red1">*</em></span>
			                	<input type="text" class="r-input w50p" name="limitNum" id="limitNum" name="limitNum" maxlength="2" onkeyup="replaceNumPositiveInteger(this);" onafterpaste="replaceNumPositiveInteger(this);" value="${couponVo.limitNum}"/>
				             </li>
		                </ul>
		            </td>
		        </tr>
		    </table>
		</div>
		<p class="t-c mt20"><input type="button" class="display-ib btn1 white mr40" id="submitButton" onclick="judgeForm()" value="保存"/>
		<input type="reset" onclick="resetValue();" class="display-ib btn1 white" value="取消"/></p>
	</div>
</div>
</s:form>
</body>
</html>
<script type="text/javascript">
function loadOriginalCity(){
   var province = "${couponVo.provinceCode}";
   if(null!=province&&province.length>0&&province!="null"){
      loadCity(province);
   }
}
//根据省份加载城市
function loadCity(value){
	if(value==null || value==""){
		$("#cityCode").empty();
		$("#cityCode").append("<option value=''>--选择城市--</option>");
		return;
	}
  	$.ajax({
		url:'../merchantAction/getProvince.action?proId='+value,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value=''>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			});
			loadSelectCity();
		}
	});
}

//加载原来的城市
function loadSelectCity(){
	var city = "${couponVo.cityCode}";
		if(""!=city){
			var ops =  $("#cityCode option");
			for(var i=0;i<ops.length;i++){
				if(city==ops[i].value){
					ops[i].selected = true;
				}
			}
		}
}
//新增礼品
function showPopGiftPage()
{ 
	var checkBoxId = $("#giftId").val();
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("../coupon/addSelectGift.action?random="+Math.floor(Math.random()*10000+1)+"&checkBoxId="+checkBoxId); 
    $("#mainBody").show();
}

function delGift(obj)
{
	$(obj).parent("span").remove();
	var trLength = $("#giftDiv span").length;
	if(trLength > 0)
	{
		$("#giftCountsSpan").show();
		$("#giftCounts").text(trLength);
	}
	else
	{
		$("#giftCountsSpan").hide();
		$("#giftCounts").text('');
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
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}


//重置
function resetValue(){
    $("#iframe_right",parent.parent.window.document).attr("src","../coupon/toList.action");
    /*
	$("#couponName,#txtOnlineDate,#txtUnlineDate,#txtStartDate,#txtEndDate,#couponCount,#content,#limitNum").val("");
	$("#couponSelect option:first").attr("selected",true);
	$("#provinceCode option:first").attr("selected",true);
	$("#cityCode option:first").attr("selected",true);
	//清除所有的验证语句
	validateUserDefineClear("couponName:txtOnlineDate:txtUnlineDate:txtStartDate:txtEndDate:couponCount:couponSelect:limitNum:content");
	//验证提交 防止多次提交
	setSubmitDisale(false);
	*/
}

//
function showGiftData()
{
	var $trs = "<span class='ml20 fl w40p' giftId='${couponVo.giftId}'>礼品ID：<em class='mr10'>${couponVo.giftId}</em><em>${couponVo.giftName}</em></span>";
	$("#giftDiv").append($trs);
	//设隐藏域
	$("#giftId").val("${couponVo.giftId}");
	var childLength = $("#giftDiv span").length;
	if(childLength > 0)
	{
		$("#giftLi").show();
		$("#giftCountsSpan").show();
		$("#giftCounts").text(childLength);
	}
}

$(function(){
	var sendCouponType = "${couponVo.sendCouponType}";
	var selectPass = "${couponVo.selectPass}";
	if(sendCouponType=="1"&&selectPass=="3"){
	    $("#userdefinedLi").show();
	}
	//用户自定义类型
	$("#couponSelect").change(function(){
		var selectIndex = $("#couponSelect option:selected").index();
		if(selectIndex == 3)
		{
			$("#userdefinedLi").show();
			//$("#giftInfo").hide();
		}
		else
		{
			$("#userdefinedLi").hide();
			//$("#giftInfo").show();
		}
		
	});

	//在新增优惠券时，发放方式选择的“系统发放”，发行数据字段应该隐藏
	$("[name=sendCouponType]").change(function(){
		showCouponCount();
	});
	
	clearInputValidateDefaultVal();
	//加载城市
	loadOriginalCity();
	
	showGiftData();

	showCouponCount();
	
});

//在新增优惠券时，发放方式选择的“系统发放”，发行数据字段应该隐藏
function showCouponCount()
{
	var checkIndex = $("[name=sendCouponType]:checked").val();
	if(checkIndex == 2)
	{
		$("#couponCount").hide();
		$("#couponCount").parent("li").hide();
	}
	else
	{
		$("#couponCount").show();
		$("#couponCount").parent("li").show();
	}
}

//清除input框默认值 
function clearInputValidateDefaultVal()
{
	$('input:text,select').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			$(this).removeClass("gray3").addClass("gray2");
			if(txt == $(this).val()){
				
			} 
			setSubmitDisale(false);
		}).blur(function(){  
			if($(this).val() == ""){
			    $(this).removeClass("gray2").addClass("gray3");
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
	
	$("#couponSelect").focus(function(){   //用户类型
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	}); 

	$("#couponName,#txtOnlineDate,#txtUnlineDate,#txtStartDate,#txtEndDate,#couponCount,#content,#limitNum").focus(function(){
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){
		isNotNullValidate(this.id);
	});
}


function judgeForm(){
	selectValidate("couponSelect"); //用户类型
	selectValidate("provinceCode"); //省份
	selectValidate("cityCode");  //城市
	//用户自定义类型
	var selectIndex = $("#couponSelect option:selected").index();
	if(selectIndex == 3)
	{
		if($("#couponCount:visible"))
		{
			isNotNullValidate("couponName:txtOnlineDate:txtUnlineDate:txtStartDate:txtEndDate:couponCount:limitNum:content");
		}
		else
		{
			isNotNullValidate("couponName:txtOnlineDate:txtUnlineDate:txtStartDate:txtEndDate:limitNum:content");
		}
	}
	else
	{
		if($("#couponCount:visible"))
		{
			isNotNullValidate("couponName:txtOnlineDate:txtUnlineDate:txtStartDate:txtEndDate:couponCount:limitNum");
		}
		else
		{
			isNotNullValidate("couponName:txtOnlineDate:txtUnlineDate:txtStartDate:txtEndDate:limitNum");
		}
		
	}
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0)
	{
		//验证提交 防止多次提交
		setSubmitDisale(false);
		//此处写验证成功后提交后台的方法
		 $("#addForm").ajaxSubmit({
			type : 'post',
			success : function(data) {
			if (data == "success") {
			    parent.parent.showRturnTip("优惠劵配置成功!",true);
			    $("#iframe_right",parent.parent.window.document).attr("src","../coupon/toList.action");
			}else if("error"==data){
				parent.parent.showRturnTip("优惠劵配置失败!",false);
			}else if(data=="countError"){
				parent.parent.showRturnTip("发行数量错误",false);
			}
		}
		});
		return true;
	}
	else
	{
		//验证提交 防止多次提交
		setSubmitDisale(true);
	    return false;
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
				validateUserDefinedSucc(idArr[i]);	
			}
		}
	}
}
//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
    $("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
	var left = $("#"+id).offset().left-$("#"+id).parents().offset().left;
	if(left < 0)
	{
		left = $("#"+id).parent("div").offset().left-$("#"+id).parents().offset().left;
	}
	$("#"+id+"ErrTip").css("margin-left",left+"px");
	$("#"+id+"ErrTip").css("margin-top","0");
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
function validateUserDefineClear(ids)
{
  var idArr = "";
	if(ids != "")
	{
		idArr = ids.split(":");
		for(i=0;i<idArr.length;i++)
		{
			$("#"+idArr[i]+"ErrTip").hide();
			  $("#"+idArr[i]).parent().find(".onCorrect").remove();
		}
	}
}

//验证手机号码
function checkPhone(obj){
	var telephones = $("#content").val();
	var cityCode = $("#cityCode").val();
	$.ajax({
			url:'../coupon/checkTelephoneExist.action',
			type:'post',
			data:{telephones:telephones,cityCode:cityCode},
			cache:false,
			dataType:"text",
			success:function(data){
// 				alert(data+"===eorro");
				if(data=="eorro"){
					parent.parent.showRturnTip("输入号码为空！",false);
					$("#content").val("");
				}else if(data!="success"){
// 					parent.parent.showRturnTip("输入号码有误！",false);
					 var phones=data;
					 $("#topHide", parent.window.document).show();
				   	 $("#leftHide", parent.window.document).show();
				     $("#mainBody").show();
				     $("#showPage").load("../coupon/wrongPhonePage.action?phones="+phones);
					$("#content").val("");
				}
			}
		});

}

//验证号码输入
function check(obj){
	obj.value=obj.value.replace(/[^0-9;]/g,'');
	
// 	if(obj.value.length==1){obj.value=obj.value.replace(/[^1-9]/g,'')}else{obj.value=obj.value.replace(/\D/g,'')}; 
}

function closeArea(){
	if($("#couponSelect").val()==3){
	  $("#userdefinedLi").show();
	}
}
</script>