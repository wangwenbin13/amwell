<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-线路列表-设置补贴</title>
</head>

<body>
    <s:form id="setAllowanceForm" action="../line/setLineAllowance.action" method="post" theme="simple">
	<div id="popMapPage" class="pop black1" style="width:425px;">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white">设置补贴</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="loadPage.window.closePopAddPage();" class="pop-close fr mt4 mr4"></a></div>
	    </div>
	    <div class="pop-main p10">
	        <ul class="pop-lilist mt15 clearfix line26">	
	            <input type="hidden" name="lineBaseId" value="${lineAllowanceDetailVo.lineBaseId}"/>
	            <input type="hidden" name="allowanceId" value="${lineAllowanceDetailVo.allowanceId}"/>
	        	<li class="widthfull"><span class="t-r w110 fl">线路名称：</span><span class="red1">${lineAllowanceDetailVo.lineName}</span></li>
	            <li class="widthfull"><span class="t-r w110 fl">原价：</span><span class="green1">${lineAllowanceDetailVo.originalPrice}</span></li>
	            <li class="widthfull"><span class="t-r w110 fl">现价：</span><span class="yellow1">${lineAllowanceDetailVo.orderPrice}</span></li>
	            <li class="widthfull"><span class="t-r w110 fl">小猪巴士补贴：</span>
		            <input type="text" id="ownAllowance" name="ownAllowance" value="${lineAllowanceDetailVo.ownAllowance}" class="r-input w75"/>
		            <span class="ml4 red1 hide errTip" id="errTipownAllowance">最多可输入7位数字,保留两位小数</span><span class="onCorrect" style="display:none;" id="onCorrectownAllowance"></span>
	            </li>
	            <li class="widthfull"><span class="t-r w110 fl">财富之舟补贴：</span>
		            <input type="text" id="otherAllowance" name="otherAllowance" value="${lineAllowanceDetailVo.otherAllowance}" class="r-input w75"/>
		            <span class="ml4 red1 hide errTip" id="errTipotherAllowance">最多可输入7位数字,保留两位小数</span><span class="onCorrect" style="display:none;" id="onCorrectotherAllowance"></span>
	            </li>
	            <li class="widthfull"><span class="t-r w110 fl">状态：</span>
					<input type="radio" name="statusFlag" <s:if test="%{lineAllowanceDetailVo.statusFlag==1}">checked="checked"</s:if> value="1"/>启用
					<input type="radio" class="ml10" name="statusFlag"  <s:if test="%{lineAllowanceDetailVo.statusFlag==2}">checked="checked"</s:if> value="2"/>禁用
				</li>
				 <li class="widthfull hide" id="errorTip">
					<span class="t-c red1 ml30">补贴金额设置错误,补贴金额之和不得大于原价与现价之差</span>
				</li>                 
	        </ul>
	        <p class="t-c mt10 mb20"><input type="button" class="display-ib btn1 white mr40 f14" value="确&nbsp;定" onclick="loadPage.window.addFunction()"/></p>
	    </div>
	</div>
	</s:form>
</body>
</html>
<script language="Javascript">
//关闭弹出层
function closePopAddPage()
{
	$("#popMapPage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
   
}
function addData(){
    var originalPrice = parseFloat("${lineAllowanceDetailVo.originalPrice}");
    var orderPrice = parseFloat("${lineAllowanceDetailVo.orderPrice}");
    var ownAllowance = parseFloat($("#ownAllowance",parent.window.document).val());
    var otherAllowance = parseFloat($("#otherAllowance",parent.window.document).val());
    //alert(originalPrice+","+orderPrice+","+ownAllowance+","+otherAllowance);
    if((ownAllowance+otherAllowance)>(originalPrice-orderPrice)){
       $("#errorTip",parent.window.document).show();
       $("#errTipownAllowance",parent.window.document).hide();
       $("#errTipotherAllowance",parent.window.document).hide();
	   $("#onCorrectownAllowance",parent.window.document).hide();
	   $("#onCorrectotherAllowance",parent.window.document).hide();
       return;
    }
    $("#setAllowanceForm",parent.window.document).ajaxSubmit({
		type : 'post',
		success : function(data) {
			if (data == "success") {
			    parent.parent.showRturnTip("设置成功",true); 
			    closePopAddPage();
			}else{
			    parent.parent.showRturnTip("设置失败",false); 
			}
		}
	});
}

//金钱
function isMoneyVal(id)
{
	if(isMoney($("#"+id,parent.window.document).val()))
	{
		$("#onCorrect"+id,parent.window.document).show();
		$("#errTip"+id,parent.window.document).hide();
		setSubmitDisale(false);
	}
	else
	{
		$("#onCorrect"+id,parent.window.document).hide();
		$("#errTip"+id,parent.window.document).show();
		setSubmitDisale(false);
	}
}

//金钱
function isMoney(money)
{
	var isMoney = new RegExp(validateJson.Money8Numer.reg);
	if(isMoney.test(money))
	{
		 return true;	
	}
	else
	{
		return false;	
	}
}

$(function(){
	//验证方法
	$("#ownAllowance",parent.window.document).focus(function(){
		$("#onCorrect"+this.id,parent.window.document).hide();
		$("#errTip"+this.id,parent.window.document).hide();
		setSubmitDisale(false);
	}).blur(function(){
		isMoneyVal(this.id);
	});
	$("#otherAllowance",parent.window.document).focus(function(){
		$("#onCorrect"+this.id,parent.window.document).hide();
		$("#errTip"+this.id,parent.window.document).hide();
		setSubmitDisale(false);
	}).blur(function(){
		isMoneyVal(this.id);
	});
	$('input:text',parent.window.document).each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			setSubmitDisale(false);
			$("#errorTip",parent.window.document).hide();
		}).blur(function(){  

		});  
	});
});

function addFunction()
{
	isMoneyVal("ownAllowance");
	isMoneyVal("otherAllowance");
	var failValidateCount = $(parent.window.document).find(".errTip:visible").length;
	//failValidateCount=0代表验证成功
	if(failValidateCount == 0)
	{
		
		//验证提交 防止多次提交
		setSubmitDisale(false);
		addData();
		return true;
	}
	else
	{
		
		//验证提交 防止多次提交
		setSubmitDisale(true);
	    return false;
	}
}

//验证提交 防止多次提交  flag 为true表示按钮不可点击，false表示按钮可点击
function setSubmitDisale(flag)
{
	if(flag)
	 {
		$("#submitButton",parent.window.document).removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $("#submitButton",parent.window.document).removeClass("btn1-gray").addClass("btn1");
	 } 
	$("#submitButton",parent.window.document).attr("disabled", flag);
}
</script>
