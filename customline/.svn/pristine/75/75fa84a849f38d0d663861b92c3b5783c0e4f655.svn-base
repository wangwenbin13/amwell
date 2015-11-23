<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销管理-App站内信-增加站内信模板</title>
<%@include file="../resource.jsp" %>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript" src="../js/jquery/ajaxfileupload.js"></script>
</head>
<body>
<s:form id="addSMSTemplate" action="" method="post" theme="simple">
<s:hidden theme="simple" name="msgTemplateVo.id"></s:hidden>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;App站内信&nbsp;>&nbsp;编辑站内信模板</p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">编辑模板</a></li>
        </ul>
    </div>
    <ul class="mt25 ml10">
      	<li class="w58p mb10 clearfix"><span class="w75 t-r fl">模板名称：</span>                		
          	<s:textfield type="text" theme="simple" cssClass="r-input w50p fl" name="msgTemplateVo.template_name" maxlength="30" id="template_name" check="11"></s:textfield>
      	</li>
          <li class="w58p mb10 clearfix"><span class="w75 t-r fl">模板内容：</span>
          	<s:textarea theme="simple" cssClass="r-input more-show w85p fl"  id="template_content"  style="height:200px;" name="msgTemplateVo.template_content" check="11" onkeydown="textCounts()" onkeyup="textCounts()"></s:textarea>
          </li>
	</ul>	
	<p class=" mt20"><span class="w75 t-r fl"></span><a class="display-ib btn1 white mr40 ml10" href="javascript:void(0)" id="submitButton">修改</a>
	<input type="reset" class="display-ib btn1 white" value="取消" onclick="cancle()"/></p>               
 </div>
</s:form>
</body>
</html>
<script type="text/javascript">

//------------------------- 列表选中和未选中end------------------------------
//table2-text高度
$(function(){	
	$(".table2-text-height").css("min-height",$(window).height()-36+"px");
	$(window).resize(function(){
		$(".table2-text-height").css("min-height",$(window).height()-36+"px");	
	});

	$("#template_content,#template_name").focus(function(){
		 //验证提交 防止多次提交
		setSubmitDisales(false);
	}).blur(function(){
		
		
	});
	
	validateFunction();
});


function cancle(){
	$("#iframe_right",parent.parent.window.document).attr("src","../insideMessage/insideAPPManager.action?level=two&toAdd=2");
}

function adminFormSubmit(){
	
	$("#addSMSTemplate").ajaxSubmit({
		type : 'post',
		data:{},
		url : "../insideMessage/editMarketingModel.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"text",
		success : function(data) {
			
			if("yes" == data){
				parent.parent.showRturnTip("修改成功",true);
				cancle();
			}
			if("no" == data){
				parent.parent.showRturnTip("修改失败",false);
			}
		}
	});
	
}


//验证方法
function validateFunction()
{
	
    //validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [{"validateName":"template_name:template_content","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip}//必填字段不为空判断
		
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
	//一个form表单提交验证
	$('#submitButton').click( function () {
		 
		 getthis.each(function(){
			var obj = this;
			checkByType(obj);
		});
	   
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;

		if(failValidateCount == 0)
		{
			//验证提交 防止多次提交
			setSubmitDisales(false);
			
			adminFormSubmit();
			return false;
		}
		else
		{
			//验证提交 防止多次提交
			setSubmitDisales(true);
		    return false;
		}
	});
}

function setSubmitDisales(flag)
{
     if(flag)
	 {
		$('#submitButton').removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $('#submitButton').removeClass("btn1-gray").addClass("btn1");
	 } 
	 $('#submitButton').attr("disabled", flag);
}


//字符数限制
function textCounts(){
	var content=$("#template_content").val();

 	var maxlimit = 600; 
 	var count=0;	//计算字符数
	var char=0;		//计算剩余字符数				
	for(var i=0;i<content.length;i++){
		var ask11=content[i].charCodeAt();  //ascii码
		if(0<=ask11 && ask11<161){
			count++;
			if(count<maxlimit){
			char++;
			}
		}else{
			count+=2;
			if(count<maxlimit){
			char++;
			}
		}
	}
	if(count>maxlimit){
		content = content.substring(0, char);
		$("#template_content").val(content);
	}
}

</script>