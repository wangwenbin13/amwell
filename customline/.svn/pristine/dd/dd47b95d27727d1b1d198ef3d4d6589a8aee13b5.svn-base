<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－创建线路-上下班-填写班次</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
<!-- 运营平台－创建线路-上下班-工作时间 -->
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
    <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布专线</p></div>
    <div class="steps">
    	<ol class="clearfix">
			<li><i>1</i><span class="tsl">填写基本信息</span></li>
			<li class="active"><i>2</i><span class="tsl">填写班次</span></li>
			<li><i>3</i><span class="tsl">创建完成</span></li>
		</ol>		
    </div>
   <form id="addForm" action="line/addLineFinish.action" method="post">
   <div class="mt20 ml45 w1015">
   		<span class="w90 t-r fl">班次时间：</span>
   		<ul class="mt10 addClass-text" id="addClassUl">
   			<li class="fw gray2 addLine-addClassTitle"><span class="fl w210 t-c">发车时间<em class="red1">*</em></span><span class="fl w170 t-c">座位数量<em class="red1">*</em></span><span class="fl w120 t-c">操作</span><span class="fl w120 t-c">工作时间</span><a href="javascript:void(0)" class="f12 blue1 fr mr20" onClick="addClassTime();">添加班次时间</a><span class="addico mr5 mt10 fr"></span></li>
   			<li class="clearfix mt10 line24">
	   			<span class="fl w210">
	   				<span class="fl r-input w170 t-l ml20"><input type="text" id="orderStartTimeStart" name="orderStartTime" value="${recruitLineStationVo.classTime}" readonly="readonly" class="fl Wdate75 gray2 Wdate" onClick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})" check="11"></span>
	   			</span>
	   			<span class="fl w170 t-c"><input type="text" id="orderSeatsStart0" name="orderSeats" maxLength="3" class="r-input t-c w75 mr5 fl ml30"/><span class="t-l fl">位</span></span>
	   			<!-- 排班日期 -->
	   			<span class="fl w170 t-c">
	   			<input id="orderDateStart0" type="hidden" name="orderDate" class="r-input t-c w75 mr5"/>
	   			<!-- 班次是否包月 -->
	   			<input id="theSupportMonth" type="hidden" name="supportMonth" class="r-input t-c w75 mr5"/>
	   			</span>
	   			<span class="fl w120 t-c h24"><a href="javascript:void(0)" class="blue1" onclick='deleteClassTime(this)'>删除</a></span>
	   			<span class="fl w120 t-l">
	   				<a href="javascript:void(0)" class="blue1" onclick='goDatePage("orderDateStart0","theSupportMonth");'>工作时间<span class="arrow arrow-desc ml4"></span></a>
	   				<span class="mt6 onCorrectNew" id="onCorrectNew0" style="display:none;"></span>
				   	<div id="onErrorNew0" class="tipTable" style="margin-top:-5px;margin-left:0;">请选择工作时间</div>
	   			</span>
   			</li>
   		</ul>
   		<p class="mt20 line24"><span class="fl w90 t-r">单次原价：<em class="red1">*</em></span><input maxLength="8" type="text" id="originalPrice" name="originalPrice" value="${recruitLineStationVo.orderPrice}" class="fl r-input mr5"/>元/次</p>
   		<p class="mt20 line24"><span class="fl w90 t-r">单次现价：<em class="red1">*</em></span><input maxLength="8" type="text" id="orderPrice" name="orderPrice" class="fl r-input mr5"/>元/次</p>
   		<p class="mt20"><span class="fl w90 t-r">包月折扣：</span><span class="fl line24 gray2">设置包月折扣：</span><input type="text" id="discountRate" name="discountRate" class="fl r-input mr5" value="100" check="1"/><em class="f16">%</em>&nbsp;&nbsp;折</p>
   		<div class="mt20"><span class="fl w90 t-r">乘客须知：</span>
   			<textarea class="r-input more-show w87p" id="content" style="height:350px;" onKeyUp="if(this.value!=null&&this.value!=''&&this.value.length > 5000) this.value=this.value.substr(0,5000)" onFocus="if(this.value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onBlur="if(!this.value){this.value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}"></textarea>
   		<div style="display: none;">
   			<textarea class="dateTextarea gray3"  name="remark" id="textarea_remark" onKeyUp="if(this.value!=null&&this.value!=''&&this.value.length > 5000) this.value=this.value.substr(0,5000)">最多输入5000个字</textarea>
   		</div>
   		</div>
   		<p class="t-c mt20 mb20"><input class="display-ib btn1 white mr40 f14" type="button" onClick="toAdd();" id="createFinish" value="创建完成"/></p>
   </div>
   
   </form>
  </body>
</html>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">

$(function(){
	validateFunction();
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

	$("#originalPrice,#orderPrice").focus(function(){  //价格
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		regValidate(this.id,validateJson.Money5Numer.reg,validateJson.Money5Numer.tip);
	});
});

function toAdd(){
    var flag = judgeForm();
	if(!flag){
	    return;
	}
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
    //检查包月折扣,如果全部班次都不支持包月,则必须将折扣率写为100
	//判断包月折扣是否在1－100之间
	var discountRate = $("#discountRate").val();
	if(discountRate.length>0){
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
	$("#textarea_remark").text(editor.html());
	var ops = editor.text();
	if(ops.length>6000){
		parent.parent.showPopCommonPage2("乘客须知最多输入5000个字");
		return;
	}
	//未触发blur事件直接提交数据，需要做验证
	var regExp = /^\d+(\.\d{2})?$/;
    if(!regExp.test($("#originalPrice").val())){
        parent.parent.showRturnTip("单次原价保留两位小数",false); 
	    return;
    }			
    if(!regExp.test($("#orderPrice").val())){
        parent.parent.showRturnTip("单次现价保留两位小数",false); 
	    return;
    }
	setSubmitDisale(true);
	$("#addForm").submit();
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

function judgeForm(){
	 getthis.each(function(){
		var obj = this;
		checkByType(obj);
	});
	//座位数
	$("[name='orderSeats']").each(function()
	{
		regValidate(this.id,validateJson.IntegeNum.reg,validateJson.IntegeNum.tip);
	});
	
	regValidate("originalPrice",validateJson.Money5Numer.reg,validateJson.Money5Numer.tip);
	regValidate("orderPrice",validateJson.Money5Numer.reg,validateJson.Money5Numer.tip);
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
//验证方法
function validateFunction()
{
	//validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [
	    {"validateName":"orderStartTimeStart:orderPrice","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		//{"validateName":"orderSeatsStart","validateReg":validateJson.Intege.reg,"validateTip":validateJson.Intege.tip},//座位
		{"validateName":"discountRate","validateReg":validateJson.Discount.reg,"validateTip":validateJson.Discount.tip}//折扣
		//{"validateName":"orderPrice:originalPrice","validateReg":validateJson.Money.reg,"validateTip":validateJson.Money.tip}
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
			$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+($("#"+id).prevAll("input").length*$("#"+id).prevAll("input").width()+$("#"+id).prevAll("input").length*15)+"px");
		}
		else
		{
			$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
		}
		$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
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
//上下班-工作时间
function goDatePage(id,supportMonth)
{
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("line/addLineDate.action?random="+Math.floor(Math.random()*10000+1),{id:id,theSupportMonth:supportMonth,isShow:"0"}); 
    $("#mainBody").show();
}

//添加班次时间
function addClassTime()
{
     var count = $("#addClassUl").children().length-1;
	 $("#addClassUl li:last").after("<li class='clearfix mt10 line24'>"+
	   			"<span class='fl w210'>"+
	   			"<span class='fl r-input w170 t-l ml20'><input type='text' id='orderStartTimeAccess"+count+"' name='orderStartTime' readonly='readonly' class='Wdate75 gray2 Wdate' onclick='WdatePicker({position:{left:-9,top:4},dateFmt:\"HH:mm\"})'/></span>"+
	   			"</span>"+
   				"<span class='fl w170 t-c'><input type='text' id='orderSeatsAccess"+count+"' maxLength='3' name='orderSeats' class='r-input t-c w75 mr5 fl ml30'/><span class='t-l fl'>位</span></span>"+
   				"<span class='fl w170 t-c'><input id='orderDateAcces"+count+"' type='hidden' name='orderDate' class='r-input t-c w75 mr5'/><input id='supportMonthAccess"+count+"' type='hidden' name='supportMonth' class='r-input t-c w75 mr5'/></span>"+
   				"<span class='fl w120 t-c h24'><a href='javascript:void(0)' class='blue1' onclick='deleteClassTime(this)'>删除</a></span>"+
   				"<span class='fl w120 t-l'><a href='javascript:void(0)' class='blue1' onclick='goDatePage(\"orderDateAcces"+count+"\",\"supportMonthAccess"+count+"\");'>工作时间<span class='arrow arrow-desc ml4'></span></a><span id='onCorrectNew"+count+"' class='mt6 onCorrectNew' style='display:none;margin-left:12px;'></span>"+
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

//删除途径点
function deleteClassTime(obj)
{
	$(obj).parent().parent().remove();
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



</script>

