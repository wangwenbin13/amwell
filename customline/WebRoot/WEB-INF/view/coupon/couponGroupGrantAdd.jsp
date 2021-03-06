<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠券管理-优惠券发放页面</title>
<jsp:include page="../resource.jsp"/>
<script type="text/javascript" src="<%=basePath %>js/jquery/chosen.jquery.min.js"></script>
<link href="<%=basePath %>js/jquery/chosen.css" type="text/css" rel="stylesheet"></link>
<script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;优惠券发放页面</p></div>
<s:form id="addForm" theme="simple">
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">优惠券发放</a></li>
           </ul>
       </div>
       <div class="sh-add-new sh-add-new-coupon">  
	       <ul class="clearfix mt20 f12 ruleListBox">
			   	<li class="clearfix"><span class="w120 t-r fl">优惠券批次号：</span>
			   	<s:textfield id="couponNum"  name="couponGroupGrant.couponGroupId" cssClass="r-input" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" maxLength="10"></s:textfield>
			   	<span class="gray1">如：1507230001</span>
			   	<span id="couponNumError" class="errorTip"></span>
				</li>
				<li class="clearfix"><span class="w120 t-r fl">任务名称：</span>
			   	<s:textfield id="taskName"  name="couponGroupGrant.taskName" cssClass="r-input" onkeyup="this.value=this.value.replace(/[%]/g,'')" maxLength="25"></s:textfield>
			   	<span id="taskNameError" class="errorTip"></span>
				</li>
			   	<li class="clearfix" id="ruleList">
			   	<table border="0" class="fl" id="target"> 
			   		<tr>
			   			<td height="36"><span class="w120 t-r fl">发放规则：</span></td>
			   			<td height="36" id="model1">
			   				<select name="ruleName" onchange="changeTheCondition(this)" class="p3 mr10 fl classRuleName">
				   		      <option value=" ">规则名称</option>
				   		      <option value="telephone">手机号</option>
				   		      <option value="registTime">注册时间</option>
				   		      <option value="ticketNumber">购票次数</option>
				   		      <option value="isDoRecommend">是否推荐</option>
				   		      <option value="recommendTime">推荐时间</option>
				   		      <option value="isRecommended">是否被推荐</option>
				   		      <option value="recommendedTime">被推荐时间</option>
				   		      <option value="cityCode">城市</option>
				   		      <option value="lineBaseId">线路</option>
<%--				   		      <option value="recommendNumber">推荐人数</option>--%>
<%--				   		      <option value="terminal">设备类型</option>--%>
<%--				   		      <option value="sourcefrom">用户来源</option>--%>
<%--				   		      <option value="sex">性别</option>--%>
                              <option value="ticketTime">购票时间</option>
                              <option value="singleHandle">第三方操作</option>
                              <option value="timingHandle">第三方定时</option>
				   		    </select>
			   			</td>
			   			<td height="36" id="model2">
			   				<select name="theCondition" class="p3 mr10 fl w95 classRuleName">
<%--					   		  <option value=" ">规则条件</option>--%>
					   		  <option value="gt">大于</option>
					   		  <option value="equal">等于</option>
					   		  <option value="lt">小于</option>
					   		</select>
			   			</td>
			   			<td height="36" id="model3">
			   				<input type="text" name="theValue" class="r-input fl w120 classRuleName1"/>
			   			</td>
			   			<td height="36" id="model4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			   		</tr>
			   	</table>
			   	<input type="button" class="btn-blue4 ml20" onclick="addTr();" value="增加规则"/>
			   	<span id="ruleListError" class="errorTip"></span>
			   	</li>
			   	<li class="clearfix"><span class="w120 t-r fl">&nbsp;</span><span class="gray1">如：给新人发优惠券，则发放规则选择注册时间  大于  当前时间，且购票次数  等于  0</span></li>
			    <li class="clearfix"><span class="w120 t-r fl">&nbsp;</span><span style="color:red;">注：多个手机号码之间用英文分号";"隔开，支持txt文件导入手机号</span></li>
			   	<li class="clearfix"><span class="w120 t-r fl">发放方式：</span>
			   	<s:select name="couponGroupGrant.sendCouponType" list="#{'sys send':'系统发放','user get':'用户领取'}" cssClass="r-input w210"></s:select>
			   	</li>
			   	<li class="clearfix"><span class="w120 t-r fl">发放用户：</span>
			   	<s:select name="couponGroupGrant.selectPass" list="#{'oldUser':'现有用户','newUser':'新进用户'}" id="selectPass" cssClass="r-input w210"></s:select>
			   	<span id="selectPassError" class="errorTip"></span>
			   	</li>
			   	<li class="clearfix"><span class="w120 t-r fl">&nbsp;</span>
			   	<span style="color:red;">注：需要立即完成发放的优惠券选择“现有用户”，需要长期不断发放的优惠券选择“新进用户”</span>
			   	</li>
	            <li class="clearfix"><span class="w120 t-r fl">发放时段：</span>
			   		<span class="r-input fl w210"><input type="text" name="couponGroupGrant.startTime" class="Wdate75 gray2 Wdate" readonly="readonly" id="startTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"/></span>
			   		<span id="couponStartTimeError" class="errorTip"></span>
			   	</li>
		   		<li class="clearfix"><span class="w120 t-r fl">至</span>
		   			<span class="r-input fl w210"><input type="text" name="couponGroupGrant.endTime" class="Wdate75 gray2 Wdate" readonly="readonly" id="endTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\');}'})"/></span>
		   			<span id="couponEndTimeError" class="errorTip"></span>
		   		</li>
		   		<s:hidden name="couponGroupGrant.rules" id="rules"></s:hidden>
		   </ul>
		   <p class="mt20 ml20"><input type="button" class="btn1 white" value="保存" id="doSave" onclick="adminFormSubmit('addForm')"/>
	          <input value="返回" class="btn1 white" onclick="javascript:window.location.href='../couponGroupGrant/couponGroupGrantList.action'"/>
	        </p>
	   </div>
   </div>
</s:form>
</body>
<script>
$(function(){
	$(".sh-add-new").css("min-height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("min-height",$(window).height()-130+"px");	
	})

	$(".chzn-select").chosen();
	$(".chzn-select").change(function(){
		$("#couponGroupName").val($("#couponGroupId").find("option:selected").text());
	});

})

//----------------------------------------------------------------------------------
function getCity(){
	var str = "<select name='theValue' class='p3 w105 chzn-select'>";
	$.ajax({
        url:'../couponGroupGrant/getCityInfo.action?temp='+new Date(),
        dataType:'text',
        cache:false,//不从浏览器缓存中取
        async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
        success:function(data){
          var jsonData=eval(data);
          for(var i=0;i<jsonData.length;i++){
        	  str += "<option value="+jsonData[i].arearCode+">"+jsonData[i].areaName+"</option>";
          }
          str += "</select>";
		}
	});
	return str;
}

function getLine(){
	var str = "<select name='theValue' class='p3 w105 chzn-select'>";
	$.ajax({
        url:'../couponGroupGrant/getLineInfo.action?temp='+new Date(),
        dataType:'text',
        cache:false,//不从浏览器缓存中取
        async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
        success:function(data){
          var jsonData=eval(data);
          for(var i=0;i<jsonData.length;i++){
        	  str += "<option value="+jsonData[i].lineBaseId+">"+jsonData[i].lineName+"</option>";
          }
          str += "</select>";
		}
	});
	return str;
}

var $telephoneNum=1;
//切换条件select
function changeTheCondition(val){
	var s1Val = val.value;//当前选中值
	var tr = $(val).parent().parent();//当前对象中的TR
	$(tr).children().eq(2).html('');//清空条件select

<%--	<option value=\" \">规则条件</option>--%>
	var all = "<select name=\"theCondition\" class=\"p3 mr10 fl w95 classRuleName\"><option value=\"gt\">大于</option><option value=\"equal\">等于</option><option value=\"lt\">小于</option></select>";	
	var all1 = "<select name=\"theCondition\" class=\"p3 mr10 fl w95 classRuleName\"><option value=\"gt\">后于</option><option value=\"equal\">等于</option><option value=\"lt\">前于</option></select>";	
	var equ = "<select name=\"theCondition\" class=\"p3 mr10 fl w95\"><option value=\"equal\">等于</option></select>";
	var equ1 = "<select name=\"theCondition\" class=\"p3 mr10 fl w95\"><option value=\"equal\">等于</option><option value=\"notEq\">不等于</option></select>";
	
	//条件select
	if( s1Val=='lineBaseId' || s1Val=='telephone' || s1Val=='terminal' || s1Val=='sourcefrom' || s1Val=='sex' ||  s1Val=='isRecommended' ||  s1Val=='isDoRecommend' ||  s1Val=='ticketNumber' ||  s1Val=='ticketTime' ||  s1Val=='singleHandle' ||  s1Val=='timingHandle'){
		$(tr).children().eq(2).html(equ);  
	}else if(s1Val=='registTime' || s1Val=='recommendTime' || s1Val=='recommendedTime'){
		$(tr).children().eq(2).html(all1);
	}else if(s1Val=='cityCode'){
		$(tr).children().eq(2).html(equ1);  
	}else{
		$(tr).children().eq(2).html(all);
	}
	
	//内容
	if(s1Val=='cityCode'){
		var city = getCity();
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html(city);//城市列表
		$(".chzn-select").chosen();
	}else if(s1Val=='lineBaseId'){
		var line = getLine();
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html(line);//线路列表
		$(".chzn-select").chosen();
	}else if(s1Val=='terminal'){
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html('<select name="theValue" class="p3 mr10 fl w95"><option value="1">android</option><option value="2">ios</option><option value="3">微信</option></select>');//设备类型
	}else if(s1Val=='sourcefrom'){
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html('<select name="theValue" class="p3 mr10 fl w95"><option value="0">小猪巴士</option><option value="1">蛇口</option><option value="2">彩生活</option><option value="3">微信</option></select>');//用户来源
	}else if(s1Val=='sex'){
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html('<select name="theValue" class="p3 mr10 fl w95"><option value="0">男</option><option value="1">女</option></select>');//性别
	}else if(s1Val=='isRecommended'||s1Val=='isDoRecommend'){
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html('<select name="theValue" class="p3 mr10 fl w95"><option value="1">是</option><option value="0">否</option></select>');//是否被推荐
	}else if(s1Val=='telephone'){
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html(
		'<textarea class="r-input more-show" style="width:250px;height:300px;" name="theValue" onkeyup="check(this);" onkeydown="check(this);" onblur="checkPhone(this);"></textarea>'+
		'<br><input type="file" id="file'+$telephoneNum+'" name="telephoneFile" onchange="uploadTelephone(this)"/>' 
		);//手机号码
		$telephoneNum++;
	}else if(s1Val=='singleHandle' ||  s1Val=='timingHandle'){
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html("<input type='text' name='theValue' class='r-input fl w320 classRuleName1'/>");//手机号码
	}else if(s1Val=='registTime'||s1Val=='recommendTime'||s1Val=='recommendedTime'){
		var cal = "<span class=\"r-input fl w120\"><input type=\"text\" name=\"theValue\" class=\"Wdate75 gray2 Wdate\" readonly=\"readonly\" id=\"theValue\" onclick=\"WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d-2}'})\"/></span>";
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html(cal);
	}else if(s1Val=='ticketTime'){
		var cal = "<span class=\"r-input fl w120\"><input type=\"text\" name=\"theValue\" class=\"Wdate75 gray2 Wdate\" readonly=\"readonly\" id=\"theValue\" onclick=\"WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd'})\"/></span>";
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html(cal);
	}else{
		$(tr).children().eq(3).html('');//清空条件select
		$(tr).children().eq(3).html("<input type='text' name='theValue' class='r-input fl w120 classRuleName1' onkeyup='this.value=this.value.replace(/[^0-9]/g,\"\")'/>");//赋值
	}
	
}

//新增规则
function addTr(){
	var temp1 = $("#model1").html();
	var temp2 = $("#model2").html();
	var temp3 = "<input type='text' name='theValue' class='r-input fl w120 classRuleName1'/>";
	var temp4 = "<a href=\"javascript:void(0)\" onclick=\"delTr(this)\" class=\"blue1 ml10\">删除</a>";
	var tr = "<tr><td height=\"36\"><span class=\"w120 t-r fl\">&nbsp;&nbsp;&nbsp;&nbsp;</span></td><td height=\"36\">"+temp1+"</td><td>"+temp2+"</td><td>"+temp3+"</td><td>"+temp4+"</td></tr>";
	var $table = $("#target");  
    $table.append(tr);  
}

//删除规则
function delTr(obj){
	 $(obj).parent().parent().remove();
}

//----------------------------------------------------------------------------------

function adminFormSubmit(submitForm){
	
	$(".errorTip").html("");
	
	if($("#couponNum").val()==null||$("#couponNum").val()==""){
        $("#couponNumError").html("请输入优惠券批次号");
        return false;
	}
	if($("#taskName").val()==null||$("#taskName").val()==""){
        $("#taskNameError").html("任务名称不能为空");
        return false;
	}
	if($(".classRuleName").val()==null||$(".classRuleName").val()==" "||$(".classRuleName1").val()==""){
        $("#ruleListError").html("请选择或输入对应的发放规则");
        return false;
    }
	 if($("#startTime").val()==null||$("#startTime").val()==""){
        $("#couponStartTimeError").html("发放时段开始时间不能为空");
        return false;
	}
	if($("#endTime").val()==null||$("#endTime").val()==""){
		$("#couponEndTimeError").html("发放时段结束时间不能为空");
		return false;
	}
	if($("#startTime").val()==$("#endTime").val()){
		$("#couponEndTimeError").html("发放时段开始时间和结束时间不能相同");
		return false;
	}
	
	if($(".classRuleName").val()=='singleHandle'||$(".classRuleName").val()=='timingHandle'){
		if($("#selectPass").val()=='oldUser'){
			$("#selectPassError").html("当规则为第三方时，发放用户只能选新进用户");
			return false;
		}
	}

	var $errorNum=0;
	//检查输入的号码在系统中是否存在
	$("#target").find("textarea").each(function(){
		if(!checkPhone(this)){
			$errorNum++;
			return false;
		}
	});
	
	if($errorNum>0){
		return false;
	}
	
	parent.parent.showPopCommonPage("确认保存并执行发放？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		
		$("#lodingShowPage",parent.parent.window.document).find("#t1").html('<img src="../images/loading.gif" width="24" height="24" class="mr10 vf8" />处理中，请稍等···');
		parent.parent.popLodingPage(true);
		
		$("#"+submitForm).ajaxSubmit({
			type : 'post',
			data:{},
			url : "../couponGroupGrant/couponGroupGrantAdd.action",
			dataType:"text",
			success : function(data) {
				if(data > 0){
					parent.parent.popLodingPage(false);
					parent.parent.showRturnTip("添加成功",true);
					window.location.href='../couponGroupGrant/couponGroupGrantList.action';
				}
				
				var $str='';
				
				if(0 == data){
					$str='添加失败';
				}
				if(-2 == data){
					$str='批次号不存在，请重新输入';
				}
				if(-3 == data){
					$str='添加成功，但用户已没有领取机会';
				}
				if(-4 == data){
					$str='添加成功，但没有用户符合发放规则';
				}
				$("#lodingShowPage",parent.parent.window.document).find("#t1").html("<font color='red'>"+$str+"</font>");
			}
		});
		
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
	});
}

//验证号码输入
function check(obj){
	obj.value=obj.value.replace(/[^0-9;]/g,'');
}

//验证手机号码
function checkPhone(obj){
	var $flag=false;
	var telephones = $(obj).val();
	$.ajax({
			url:'../couponGroupGrant/checkTelephoneExist.action',
			type:'post',
			data:{telephones:telephones},
			dataType:"text",
			cache:false,//不从浏览器缓存中取
	        async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
			success:function(data){
				if(data=="error"){
					parent.parent.showRturnTip("输入号码为空！",false);
					$(obj).val("");
				}else if(data!="success"){
					 var phones=data;
					 $("#topHide", parent.window.document).show();
				   	 $("#leftHide", parent.window.document).show();
				     $("#mainBody").show();
				     $("#showPage").load("../coupon/wrongPhonePage.action?phones="+phones);
				     //$(obj).val("");
				}
				else{
					$flag=true;
				}
			}
		});
	return $flag;
}

//导入手机号
function uploadTelephone(obj){
	
	var value = $(obj).val();
	if("" == value){
		return;
	}
	
	var picName=value.split("\\");
	picName=picName[picName.length-1];
	value = value.split(".");
	value = value[value.length-1];

	if(value!='txt'){
		parent.showRturnTip("请选择txt记事本文本文件！",false);
		return;
	}

	var temp=$(obj).parent().find("textarea");
	var tdTemp=$(obj).parent();

	parent.parent.popLodingPage(true);
	$.ajaxFileUpload({
			fileElementId:$(obj).attr("id"), 
			//secureuri:false,
			secureuri:true,
			dataType: 'text',
			url : '../couponGroupGrant/upLoad.action',
			success : function(data) {
		        parent.parent.popLodingPage(false);
		        if(data=="-1"){
		        	parent.showRturnTip("请选择txt记事本文本文件！",false);
		        }
		        else if(data=="-2"){
		        	parent.showRturnTip("请选择不为空的txt记事本文本文件！",false);
		        }
		        else{
		        	var a=temp.val();
		        	if(a==""){
		        		a=data;
		        	}
		        	else{
		        		a=a+";"+data;
		        	}
		        	temp.val(a);
		        	tdTemp.append('<br><span>'+picName+'</span>');
		        }
			},
			error:function(){
				parent.showRturnTip("读取文件失败，请重试！",false);
			}
     });
}

function doInfo(){
	var arr = [{"id":1,"name":"haha"},{"id":1,"name":"hehe"},{"id":2,"name":"name2"},{"id":1,"name":"heihei"},{"id":2,"name":"name2222"},{"id":3,"name":"name3"}];
	var obj = eval("("+arr+")");  
	
	var newArr = [
	          	[{"id":1,"name":"haha"},{"id":1,"name":"hehe"},{"id":1,"name":"heihei"}],
	          	[{"id":2,"name":"name2"},{"id":2,"name":"name2222"}],
	          	[{"id":3,"name":"name3"}]
	          ];
}
</script>
</html>
