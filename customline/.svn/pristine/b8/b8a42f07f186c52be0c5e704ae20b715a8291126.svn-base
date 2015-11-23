<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户投诉-反馈详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;客户投诉&nbsp;>&nbsp;反馈详情</p></div>
<div class="mt25 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">反馈详情</a></li>
        </ul>
    </div>
    <div class="sh-add-new feedbackLi" >
    
    	<s:form action="" id="ajaxForm" method="post" theme="simple">
	    	<s:iterator value="list" var="help"  status="s">
	    	<s:hidden theme="simple" name="helpFeedbackVo.feedbackId" id="feedbackId" value="%{#help.feedbackId} "></s:hidden>
	    	
	        <ul class="mt20 clearfix gray2 f12">
	            <li><span class="fl w95 t-r">反馈时间：</span>${help.feedbackTime }</li>
	            <li id=""><span class="fl w95 t-r">乘客：</span>${help.nickName }</li>
	            <li><span class="fl w95 t-r">联系电话：</span>${help.telephone}</li> 
	            <li><span class="fl w95 t-r">反馈内容：</span><div style="overflow:hidden">${help.feedbackContext }</div></li> 
	           		<s:if test="#help.status >0 && #help.handleFeedbackingRemark != null">
		            	<li>
		            		<span class="fl w95 t-r">处理反馈：</span>
		            			<div class="fl ml90">${help.handleFeedbackingRemark}<span class="ml20">${help.middleHandleUser }(${help.middleHandleTime })</span>
		            			</div>
		            	</li> 
	            	</s:if>
	            <li class="newwidth">
	            	<s:if test="#help.status < 2">
	            		<span class="fl w95 t-r">回复内容：</span>
	            		<s:textarea check="11" theme="simple" cssClass="fl w80p r-input feedbackLiMore" name="helpFeedbackVo.handleFeedbackendRemark" id="handleFeedbackendRemark"></s:textarea>
	            	</s:if>
	            	<s:if test="#help.status == 2">
	            	<li class="newwidth">
	            		<span class="fl w95 t-r">最终回复：</span>
	            			<div class="fl w80p">${help.handleFeedbackendRemark}<span class="ml20">${help.handleUser }(${help.handleTime })</span></div>
	            	</li> 
	            	</s:if>
	            </li>
	            <li>
	            	
	            	<s:if test="#help.status == 0">
	            		<span class="fl w95 t-r fw">状态：</span>
	            		<s:select list="#{1:'处理中',2:'已处理'}" listKey="key" listValue="value" cssClass="p3" name="helpFeedbackVo.status"></s:select>
	            	</s:if>
	            	<s:if test="#help.status == 1">
	            		<span class="fl w95 t-r fw">状态：</span>
	            		<s:select list="#{2:'已处理'}" listKey="key" listValue="value" cssClass="p3" name="helpFeedbackVo.status"></s:select>
	            	</s:if>
	            </li>
	            	<s:if test="#help.status < 2">
	            		<li class="t-c mt20"><input type="button" value="确&nbsp;定" class="display-ib btn1 white mr40 f14" id="submitButton"/>
	        				<a class="display-ib btn1 white f14" href="javascript:void(0);" onclick="history.back()">返&nbsp;回</a>
	        			</li>
	        		</s:if><s:else>
	        				<p class="t-c mt20"><input type="button" value="返回" onclick="history.back();"  class="display-ib btn1 white"/></p>
	        		</s:else>
	        </ul>
	    	</s:iterator>
	    	<input type="hidden" id="telephone" name="helpFeedbackVo.phoneNo" value="${help.telephone}"/>
    	</s:form>
    </div>
</div>
</body>
<script type="text/javascript">
$(function(){
	$(".sh-add-new").css("min-height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("min-height",$(window).height()-130+"px");	
	});

	validateFunction();
});

//
function cancle(){
	window.location.href='../help/getFeedbackList.action?level=two';
}
//验证方法
function validateFunction()
{
	
    //validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [{"validateName":"handleFeedbackendRemark","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip}//必填字段不为空判断
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
		
		
	}
	$("#handleFeedbackendRemark").blur(function(){
		$("#handleFeedbackendRemarkErrTip").css("marginTop","0");
	});
	//一个form表单提交验证
	$('#submitButton').click( function () {
		 
		 getthis.each(function(){
			var obj = this;
			checkByType(obj);
			$("#handleFeedbackendRemarkErrTip").css("marginTop","0");
		});
		
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;
		
		if(failValidateCount == 0)
		{
			//验证提交 防止多次提交
			setSubmitDisales(true);
			//closePopAddPage();
			ajaxFormSubmit("ajaxForm");
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

function ajaxFormSubmit(submitForm){

	var handleFeedbackendRemark = $("#handleFeedbackendRemark").val();
	var telephone = $("#telephone").val();
	$("#"+submitForm).ajaxSubmit({
		type : 'post',
		data:{"handleFeedbackendRemark":handleFeedbackendRemark,"telephone":telephone},
		url : "../help/handleFeedback.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"text",
		success : function(data) {
			
			if("yes" == data){
				parent.parent.showRturnTip("处理成功",true);
				window.location.href='../help/getFeedbackList.action';
			}
			if("no" == data){
				parent.parent.showRturnTip("处理失败",false);
			}
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
</script>
</html>
