<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－创建线路-招募-填写班次</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布专线 <span class="blue1 ml25"></span></p></div>
    <div class="steps">
    	<ol class="clearfix">
			<li><i>1</i><span class="tsl">填写基本信息</span></li>
			<li class="active"><i>2</i><span class="tsl">填写班次</span></li>
			<li><i>3</i><span class="tsl">创建完成</span></li>
		</ol>		
    </div>
   <form id="addForm" action="line/addLineFinish.action" method="post">
   <div class="mt20 ml45 w1015">
   		<div class="p-r recuitLine clearfix"><span class="fl w120 t-r">选择招募类型：<em class="red1">*</em></span>
   			<input type="radio" name="lineSubType" class="checkbox mr5" checked="checked" value="0"/>上下班
   			<input type="radio" name="lineSubType" class="checkbox mr5 ml10" value="1"/>自由行
	   		 <div style="left: 50px; top: 29px;" class="dj-box p-r w85p p-a">
	            <span class="arrow2 p-a" id="arrow2"></span>
	            <ul class="clearfix">
					<li class="fl w98p mt10"><span class="fl w95">班次时间：<em class="red1">*</em></span>
						<span class="fl"><input type="text" id="classTime" class="r-input w175" maxlength='64' name="classTime" value="${classTime}"><em class="ml10 gray1">多个班次时间请以英文,隔开,如：08:00,09:00</em></span>
					</li>
					<li class="fl w98p mt10"><span class="fl w95">选择固定时间：</span>
						<p class="fl line24">
							<input type="checkbox" name="recruitFixTime" class="checkbox mr4" value="1"/>周一
							<input type="checkbox" name="recruitFixTime" class="checkbox mr4" value="2"/>周二
							<input type="checkbox" name="recruitFixTime" class="checkbox mr4 ml10" value="3"/>周三
							<input type="checkbox" name="recruitFixTime" class="checkbox mr4 ml10" value="4"/>周四
							<input type="checkbox" name="recruitFixTime" class="checkbox mr4 ml10" value="5"/>周五
							<input type="checkbox" name="recruitFixTime" class="checkbox mr4 ml10" value="6"/>周六
							<input type="checkbox" name="recruitFixTime" class="checkbox mr4 ml10" value="7"/>周日
						</p>
					</li>
					<li class="fl w98p mt10 gray1"><span class="fl w95"></span>说明：勾选后，都会根据选择时间运行。</li>
	            </ul>
        	</div>
		
   		<div style="margin-top:182px" class="clearfix"><span class="fl w120 t-r">报名截止时间：<em class="red1">*</em></span>
			<span class="fl r-input w170 t-l"><input type="text" id="deadlineTime" name="deadlineTime" onClick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" readonly="readonly" class="Wdate75 gray2 Wdate fl" check="11"></span>
   		</div>
   		<p class="mt20"><span class="fl">设置价格：</span><span class="fl t-r gray2"><em class="red1">*</em>单次价格：</span><input type="text" id="orderPrice" name="orderPrice" maxLength="5" class="r-input mr5 fl" check="2" style="margin-top: -4px;"/>元/次</p>
   		<p class="mt20">
	   		<span class="fl t-r">招募须知：</span>
	   		<textarea class="r-input more-show w87p" id="content" style="height:350px;" onKeyUp="if(this.value!=null&&this.value!=''&&this.value.length > 5000) this.value=this.value.substr(0,5000)" onFocus="if(this.value==defaultValue){value='';$(this).removeClass('gray3').addClass('gray2');}" onBlur="if(!this.value){this.value=defaultValue;$(this).removeClass('gray2').addClass('gray3');}"></textarea>
   		</p>
   		<div style="display: none;">
   			<textarea name="remark" id="textarea_remark" >最多输入5000个字</textarea>
   		</div>
   		<p class="t-c mt20 mb20" style="margin-top:150px;"><input class="display-ib btn1 white mr40 f14" type="button" onClick="toAdd();" id="createFinish" value="创建完成"/></p>
   </div>
   </div>
   </form>
  </body>
</html>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">
$(function(){
  		$('[name=lineSubType]:radio').click(function(){
  			var index = $('[name=lineSubType]:radio').index(this);
  			if(index == 0)
  			{
  				$("#arrow2").css("left","60px");
  			}
  			else if(index == 1)
  			{
  				$("#arrow2").css("left","133px");
  			}
  		});
  		validateFunction();
  		$("#classTime").focus(function(){  //班次时间
  			validateUserDefineClear(this.id);
  			setSubmitDisale(false);
  		}).blur(function(){  
  			regValidateTime(this.id,validateJson.isTime.reg,validateJson.isTime.tip);
  		});
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

//验证提交 防止多次提交  flag 为true表示按钮不可点击，false表示按钮可点击
function setSubmitDisale(flag)
{
   	 if(flag)
	 {
		$('#createFinish').removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		$('#createFinish').removeClass("btn1-gray").addClass("btn1");
	 } 
   	 $('#createFinish').attr("disabled", flag);
     $('input:text').each(function(){  
    	$(this).removeClass("gray3").addClass("gray2");
	 });
}
function toAdd(){
    var flag = judgeForm();
	if(!flag){
	    return;
	}
	var count = 0;
	$("input[name='recruitFixTime']").each(function(){
       if(!$(this).attr("checked")){
         count+=1;
       }
    });
    if(count==7){
        parent.parent.showRturnTip("请至少选择一个固定时间",false); 
        return;
    }
    setSubmitDisale(true);
    $("#textarea_remark").text(editor.html());
    var ops = editor.text();
	if(ops.length>6000){
		parent.parent.showPopCommonPage2("乘客须知最多输入5000个字");
		return;
	}
    $("#addForm").submit();
}

function judgeForm(){
	 getthis.each(function(){
		var obj = this;
		checkByType(obj);
	});
	regValidateTime("classTime",validateJson.isTime.reg,validateJson.isTime.tip);//班次时间
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
	$("#"+id+"ErrTip").css("margin-left","0");
	$("#"+id+"ErrTip").css("margin-top","0");
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

//验证方法
function validateFunction()
{
	//validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [
	    {"validateName":"deadlineTime:classTime","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		{"validateName":"orderPrice","validateReg":validateJson.Money.reg,"validateTip":validateJson.Money.tip}
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
}
</script>
