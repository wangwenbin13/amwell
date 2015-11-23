<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>司机管理-添加司机</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;司机管理&nbsp;>&nbsp;${(null==driverInfoEntity.driverId||driverInfoEntity.driverId=="")?"添加司机":"编辑司机"}<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 mr28">
        <form method="post" id="driverForm" onsubmit="return false">
        <s:hidden theme="simple" name="driverInfoEntity.driverId" id="driverId"></s:hidden>
        <p class="fw f14">${(null==driverInfoEntity.driverId||driverInfoEntity.driverId=="")?"添加司机":"编辑司机"}</p>
        <p class="mt20 libg mt20"><span class="fl ml20 lh28">以下信息为必填</span></p>
        <ul class="clearfix">
            <li class="clearfix mt20"><span class="w75 t-r fl"><em class="red3 mr4 fl">*</em>司机姓名：</span>
            <s:textfield name="driverInfoEntity.driverName" maxlength="12" cssClass="r-input w400 fl" check="11" id="drivername"></s:textfield>
            </li>
            <li class="clearfix mt20"><span class="w75 t-r fl"><em class="red3 mr4 fl">*</em>手机号码：</span>
            <s:textfield name="driverInfoEntity.telephone" maxlength="16" cssClass="r-input w400 fl" check="2" id="drivertel"></s:textfield>
            </li>
            <li class="clearfix mt20"><span class="w75 t-r fl"><em class="red3 mr4 fl">*</em>账号状态：</span>
            <input type="hidden" id="accountStatus" value="${driverInfoEntity.accountStatus}"/>
                <span class="fl mr8 mt5" ><input type="radio" name="driverInfoEntity.accountStatus" class="radiobox" checked="checked" value="0" id="accountStatus_0"/><em class="ml4">启用</em></span>
				<span class="fl mt5" ><input type="radio" name="driverInfoEntity.accountStatus" class="radiobox" value="1" id="accountStatus_1"><em class="ml4">禁用</em></span>
            </li>
        </ul>
        <p class="mt20 libg"><span class="fl ml20 lh28">以下信息为选填</span></p>
        <ul class="clearfix mt20 w640">
            <li class="clearfix p-r">
	            <span class="w75 t-r fl">司机性别：</span>
				<div class="fl r-input w100"><span id="sexSelectDiv"></span><span class="fr sel-arrow mt10 mr10"></span></div>
				<s:select name="driverInfoEntity.sex" list="#{'0':'男','1':'女'}" cssClass="p-a sel-demo w104 h29" id="sexSelect" style="left:75px;"></s:select>
             </li>
             <li class="clearfix mt20"><span class="w75 t-r fl">出生日期：</span>
             	<span class="r-input w400 fl"><input name="driverInfoEntity.birthday" type="text" value="${driverInfoEntity.birthday}" class="Wdate75 Wdate" readonly="readonly" onclick="WdatePicker({position:{left:-1,top:0},dateFmt:'yyyy-MM-dd'})"/></span>
             </li>
             <li class="clearfix mt20 p-r"><span class="w75 t-r fl">驾照类型：</span>
             	<div class="fl r-input w400"><span id="driverCardDiv"></span><span class="fr sel-arrow mt10 mr10"></span></div>
<%--             	'0':'C1','1':'B1','2':'B2','3':'A1','4':'A2'--%>
				<s:select name="driverInfoEntity.driverType" list="#{'0':'A1','1':'A2','2':'A3','3':'B1','4':'B2'}" cssClass="p-a sel-demo w404 h29" id="driverCard" style="left:75px;"></s:select>
<%--				'1':'A1','2':'A2','3':'A3','4':'B1','5':'B2','6':'C1','7':'C2','8':'C3','9':'C4','10':'D','11':'E','12':'F','13':'M','14':'N','15':'P'--%>
<%--				将驾照分为A1、A2、A3、B1、B2、C1、C2、C3、C4、D、E、F、M、N、P共15个级别--%>
			</li>
			<li class="clearfix mt20"><span class="w75 t-r fl">驾驶证号：</span>
             <s:textfield name="driverInfoEntity.driverNo" maxlength="32" cssClass="r-input w400 fl" id="driverNo" check="1"></s:textfield>
             </li>
             <li class="clearfix mt20"><span class="w75 t-r fl">年检日期：</span>
             	<span class="r-input w400 fl"><input name="driverInfoEntity.inspectionDate" type="text" value="${driverInfoEntity.inspectionDate}" class="Wdate75 Wdate" readonly="readonly" onclick="WdatePicker({position:{left:-1,top:0},dateFmt:'yyyy-MM-dd'})"/></span>
             </li>
             <li class="clearfix mt20"><span class="w75 t-r fl">身份证号：</span>
             <s:textfield name="driverInfoEntity.idNumber" maxlength="32" cssClass="r-input w400 fl" id="driverIdCard" check="1"></s:textfield>
             </li>
        </ul>
        <p class="clearfix mt20"><span class="w75 t-l fl"></span>
	         <input type="button" class="display-ib btn1 white1 mr30 fw" value="保&nbsp;&nbsp;存" id="submitBtn"/>
	         <s:if test="%{null==driverInfoEntity||null==driverInfoEntity.driverId||''==driverInfoEntity.driverId}">
	         <a class="display-ib btn1-no" href="javascript:void(0);" onclick="clearValue();">取&nbsp;消</a>
	         </s:if>
	         <s:else>
	         <a class="display-ib btn1-no" href="javascript:window.location.href='<%=basePath %>dispatchDriver/driverInfoList.action'">取&nbsp;消</a>
	         </s:else>
	         </p>
         </form>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//性别
	$("#sexSelect").change(function(){
		$("#sexSelectDiv").text($("#sexSelect").find("option:selected").text());
	});
	//回显所选值
	$("#sexSelectDiv").text($("#sexSelect").find("option:selected").text());
	
	//驾照类型
	$("#driverCard").change(function(){
		$("#driverCardDiv").text($("#driverCard").find("option:selected").text());
	});
	//回显所选值
	$("#driverCardDiv").text($("#driverCard").find("option:selected").text());
	validateFunction();

	//回显状态信息
	if($("#accountStatus").val()!=''){
        if($("#accountStatus").val()==0){
            $("#accountStatus_0").attr("checked",true);
        }
        if($("#accountStatus").val()==1){
            $("#accountStatus_1").attr("checked",true);
        }
	}
});
//验证方法
function validateFunction()
{
    //validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [{"validateName":"drivername","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		{"validateName":"drivertel","validateReg":validateJson.Phone.reg,"validateTip":validateJson.Phone.tip},//手机
		{"validateName":"driverIdCard","validateReg":validateJson.IdCard.reg,"validateTip":validateJson.IdCard.tip},//身份证号
		{"validateName":"driverNo","validateReg":validateJson.DriverNo.reg,"validateTip":validateJson.DriverNo.tip}//驾驶证号
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
	
	//一个form表单提交验证
	//$('#driverForm').submit( function () {
	$('#submitBtn').click( function () {
		 getthis.each(function(){
			var obj = this;
			checkByType(obj);
		});
		
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;
		if(failValidateCount == 0)
		{

			$("#driverForm").ajaxSubmit({
				type : 'post',
				data:{},
				url : "../dispatchDriver/driverInfoSave.action?requestType=page&random="+Math.floor(Math.random()*10000+1),
				dataType:"json",
				success : function(data) {
					var status = data.status;
					var message = data.message;
					if(status=="1"){
						parent.showRturnTip("在【"+message+"】商户下存在该手机号码，请重新输入","false");
						//验证提交 防止多次提交
						setSubmitDisale(true);
					}
					if(status=="2"){
						parent.showPopCommonPage("添加成功","true","true"); 

						$("#commonSure",parent.window.document).text("再添加");
						$("#commonCancel",parent.window.document).text("去查看");

						$("#commonSure",parent.window.document).click(function(){
							parent.closePopCommonPage();
							window.location.href="../dispatchDriver/toDriverEditPage.action";
							//解绑定，防止多次执行click事件
							$("#commonSure",parent.window.document).unbind('click');
						});

						$("#commonCancel",parent.window.document).click(function(){
							parent.closePopCommonPage();
							parent.openLeftMenu("../dispatchDriver/driverInfoList.action");
							//解绑定，防止多次执行click事件
							$("#commonSure",parent.window.document).unbind('click');
						});
						
						
						//验证提交 防止多次提交
						setSubmitDisale(false);
					}
					if(status=="3"){
						parent.showRturnTip("添加失败","false");
						//验证提交 防止多次提交
						setSubmitDisale(true);
					}
					if(status=="4"){
						parent.showRturnTip("修改成功","true");
						//验证提交 防止多次提交
						setSubmitDisale(false);
						window.location.href="../dispatchDriver/driverInfoList.action";
					}
					if(status=="5"){
						parent.showRturnTip("修改失败","false");
						//验证提交 防止多次提交
						setSubmitDisale(true);
					}
					if(status=="9"){
						parent.showRturnTip(message,"false");
						//验证提交 防止多次提交
						setSubmitDisale(true);
					}
				}
			});
		}
		else
		{
			
			//验证提交 防止多次提交
			setSubmitDisale(true);
		   // return false;
		}
	});
}

//取消
function clearValue(){
	              
	setSubmitDisale(false);
	$('input:text,input:password,textarea').each(function(){ 
		$(this).val("");
		$("#"+this.id+"Tip").hide(); 
		$("#"+this.id+"ErrTip").hide();
		$("#"+this.id).css("background","#f6f5f5");
	});
}
</script>