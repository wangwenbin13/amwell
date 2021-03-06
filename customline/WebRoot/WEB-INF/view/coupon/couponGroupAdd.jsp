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
<title>优惠券管理-添加组合券</title>
<jsp:include page="../resource.jsp"/>
<style type="text/css">
.pop-main-tips {
	height:auto;
}
</style>
</head>

<body>
<!-- 通用选择提示层 start -->
<div id="commonShowPage" class="showPage p-r hide" style="z-index:200;top: 40%;left: 62%;">
 	<div class="pop black1" style="width:500px;height:150px;background: #fff;">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white" id="commonPopTitle">提示</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" class="pop-close fr mt4 mr4" onclick="closePopCommonPage();"></a></div>
	    </div>
	    <div class="pop-main p10">
	   		<p class="pop-main-tips" id="commonStatement"></p>
	   	</div>
	   	<p class="t-c mt10 mb20">
		    <a id="commonSure" class="display-ib btn1 white mr40 f14" href="javascript:void(0);">确&nbsp;定</a>
		   	<a class="display-ib btn1 white f14" id="commonCancel" href="javascript:void(0);" onclick="closePopCommonPage();">取&nbsp;消</a>
	   	</p>
    </div>   
</div>
<!-- 通用选择提示层 end -->
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;添加优惠券</p></div>
<s:form id="addForm" theme="simple">
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">添加优惠券</a></li>
           </ul>
       </div>

       <div class="sh-add-new">
        <s:if test="%{couponGroupID == '-1'}">
      		 <div class="pop-main-tips red1 f12">今天优惠券的发行个数达到10000，请悉知!</div>
       </s:if>
       		<div class="mt10 f12">
       			<p class="fw">优惠券类型：</p>
       			<p class="ml20"><input type="radio" value="1" name="theCoupon" class="checkbox mr4" checked="checked"/><span class="couponRadioSpan">单张券</span></p>	
       			<p class="ml20"><input type="radio" value="2" name="theCoupon" class="checkbox mr4" /><span class="couponRadioSpan">组合券（如要发放总面值30元，其中5元2张、10元2张，请选组合券）</span></p>
       		</div>  
           <div class="f12 mt10">
           		<p class="fw">有效期选择：</p>
           		<div class="ml20">
           			<input type="radio" id="theTime1" class="checkbox mr4" value="2" name="theTime" checked="checked"/><span class="timeRadioSpan">非固定有效期</span>
           			<span class="ml10">券到账后有效期<input type="text" name="delayDays" id="delayDays" class="r-input w45 mr4 ml4" value="30" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>天</span>
           			<span id="delayDaysError" class="errorTip"></span>
           		</div>	
           		<div class="ml20 mt10 clearfix">
           			<span class="fl"><input id="theTime" type="radio" class="checkbox mr4" value="1" name="theTime"/><span class="timeRadioSpan">固定有效期</span></span>
<%--           			<span class="fl ml10">开始时间：</span><span class="r-input fl w210"><input type="text" name="effectiveTime" class="Wdate75 gray2 Wdate" readonly="readonly" id="startTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d'})"/></span>--%>
<%--           			<span class="fl ml10">结束时间：</span><span class="r-input fl w210"><input type="text" name="expirationTime" class="Wdate75 gray2 Wdate" readonly="readonly" id="endTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\');}'})"/></span>--%>
           			
           			<span class="fl ml10">开始时间：</span><span class="r-input fl w210"><input type="text" name="effectiveTime" class="Wdate75 gray2 Wdate" readonly="readonly" id="startTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"/></span>
           			<span class="fl ml10">结束时间：</span><span class="r-input fl w210"><input type="text" name="expirationTime" class="Wdate75 gray2 Wdate" readonly="readonly" id="endTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\');}'})"/></span>
           			<span id="timeError" class="errorTip"></span>
           		</div>
           </div>
           <br/>
           <div class="f12 mt10">
           <span class="ml10">发行总量：<s:textfield id="couponGroupCount" name="couponGroupCount" cssClass="r-input" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"></s:textfield></span>
           <span id="couponGroupCountError" class="errorTip"></span>
           </div>
            <br/>
            <div class="f12 mt10">
           <span class="ml10">人均限领份额：<s:textfield id="averageNum" name="averageNum" cssClass="r-input" value="1" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"></s:textfield></span>
           <span id="averageNumError" class="errorTip"></span>
           </div>
            <br/>
	        <div class="table-title mt10" id="theAdd">
	        	<a href="javascript:void(0)" class="btn fr mr8" onclick="addTr()">添加一行</a>
	        </div>
	        <div class="table2-text sh-ttext">  
	            <input type="hidden" name="couponGroupID" id="couponGroupID" value="${couponGroupID}"/>
			   	<table class="table1" width="100%" id="couponTable">
			   	  <tr class="th" align="center">
			   	  	<th>批次号</th><th>名称</th><th>面值（元）</th><th>门槛（元）</th>
			   	  	<th>数量</th>
			   	  	<th>操作</th>
			   	  </tr>
			   	  <tr align="center" id="oldTr">
				   	  <td>${couponGroupID}</td>
				   	  <td><input class="r-input" type="text" name="couponName" onkeyup="doOnKeyUp(this)"/></td>
				   	  <td><input class="r-input" type="text" name="couponValue" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/></td>
				   	  <td><input class="r-input" type="text" name="couponCon" value="0" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/></td>
				   	  <td><input class="r-input" type="text" name="num" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/></td>
				   	  <td>删除</td>
			   	  </tr>
			   	</table>
		   	</div>
		   	
		    <p class="mt20 ml20">
		      <input type="button" class="btn1 white" value="保存"  onclick="adminFormSubmit('addForm')"/>
<%--	          <input type="button" value="返回" class="btn1 white" onclick="javascript:window.location.href='../couponGroup/couponGroupList.action'"/>--%>
	          <span id="couponError" class="errorTip"></span>
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

	//-------------------------------------------------------
	var $theCouponChecked=$("input[name='theCoupon']:checked").val();
	if($theCouponChecked=='1'){//如果选择单张券，则隐藏添加按钮
		$("#theAdd").hide();
		$("#oldTr").find("td:eq(4)").html('<span>1</span><input class="r-input" type="hidden" name="num" value="1">');
	}
	$("input[name='theCoupon']").click(function(){
		if($(this).val()=='2'){//如果选择组合券，则显示添加按钮
			$("#theAdd").show();
			$("#oldTr").find("td:eq(4)").html('<input class="r-input" type="text" name="num" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')"/>');
		}
		else{
			$("#theAdd").hide();
			$("#oldTr").find("td:eq(4)").html('<span>1</span><input class="r-input" type="hidden" name="num" value="1">');
			var $theTr=$("#couponTable").find("tr");
			for(var i=2;i<$theTr.length;i++){
				$theTr.get(i).remove();
			}
		}
	});

	//点击radio的文字选中radio
	$(".couponRadioSpan").click(function(){
		$(this).parent().find("input[type='radio']").attr("checked",true);
		var $radioVal=$(this).parent().find("input[type='radio']").val();
		if($radioVal=='2'){//如果选择组合券，则显示添加按钮
			$("#theAdd").show();
		}
		else{
			$("#theAdd").hide();
		}
	});
	
	//-------------------------------------------------------

	//-------------------------------------------------------
	var $theTimeChecked=$("input[name='theTime']:checked").val();
	if($theTimeChecked=='2'){//如果选择非固定时间，则让日期不可选
		$("#startTime").val('');
		$("#endTime").val('');
		$("#startTime").attr("disabled",true);
		$("#endTime").attr("disabled",true);
		$("#delayDaysError").html("");
	}
	$("input[name='theTime']").click(function(){
		if($(this).val()=='1'){//如果选择固定时间，则让天数不可输入
			$("#startTime").attr("disabled",false);
			$("#endTime").attr("disabled",false);
			$("#delayDays").val('');
			$("#delayDays").attr("disabled",true);
			$("#timeError").html("");
		}
		else{
			$("#startTime").val('');
			$("#endTime").val('');
			$("#startTime").attr("disabled",true);
			$("#endTime").attr("disabled",true);
			$("#delayDays").attr("disabled",false);
			$("#delayDaysError").html("");
			$("#timeError").html("");
		}
	});

	//点击radio的文字选中radio
	$(".timeRadioSpan").click(function(){
		$(this).parent().find("input[type='radio']").attr("checked",true);
		var $radioVal=$(this).parent().find("input[type='radio']").val();
		if($radioVal=='1'){//如果选择固定时间，则让天数不可输入
			$("#startTime").attr("disabled",false);
			$("#endTime").attr("disabled",false);
			$("#delayDays").val('');
			$("#delayDays").attr("disabled",true);
			$("#timeError").html("");
		}
		else{
			$("#startTime").val('');
			$("#endTime").val('');
			$("#startTime").attr("disabled",true);
			$("#endTime").attr("disabled",true);
			$("#delayDays").attr("disabled",false);
			$("#delayDaysError").html("");
			$("#timeError").html("");
		}
	});
	//-------------------------------------------------------
})

//修改优惠券名称的
function doOnKeyUp(obj){
	//控制文本框只能输入中文、英文、数字
	var pattern = /[\a-\z\A-\Z0-9\u4E00-\u9FA5]+$/g;
    if(!pattern.test($(obj).val()))
    {
    	$(obj).val('');
    }
	$("#couponTable").find("input[name='couponName']").val($(obj).val());
}

//新增一行
function addTr(){
	$(".errorTip").html("");
	
	var $couponName=$("#oldTr").find("input[name='couponName']").val();
	var $trHtml='<tr align="center">'+
		         '<td>'+$("#couponGroupID").val()+'</td>'+
				 '<td><input class="r-input" type="text" name="couponName" value="'+$couponName+'" onkeyup="doOnKeyUp(this)"/></td>'+
				 '<td><input class="r-input" type="text" name="couponValue" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')"/></td>'+
				 '<td><input class="r-input" type="text" name="couponCon" value="0" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')"/></td>'+
				 '<td><input class="r-input" type="text" name="num" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')"/></td>'+
				 '<td><a href="#" class="blue1" onclick="delTr(this)">删除</a></td>'+
				 '</tr>';
    $("#couponTable").append($trHtml);
}
//删除一行
function delTr(obj){
	 $(obj).parent().parent().remove();
}

//表单验证
function doValidate(){
$(".errorTip").html("");
	
	var $theTime=$("input[name='theTime']:checked").val();
	if($theTime=='1'){//如果选择固定时间，则让天数不可输入
		if($("#startTime").val()==''&&$("#endTime").val()==''){
			$("#timeError").html("选择固定有效期，开始时间和结束时间都不能为空");
			return false;
		}
		if($("#startTime").val()==$("#endTime").val()){
			$("#timeError").html("选择固定有效期，开始时间和结束时间不能相同");
			return false;
		}
	}
	else{
		if($("#delayDays").val()==''){
			$("#delayDaysError").html("选择非固定有效期，天数不能为空");
			return false;
		}
		if(parseInt($("#delayDays").val())==''){
			$("#delayDaysError").html("选择非固定有效期，天数必须大于零");
			return false;
		}
		if($("#delayDays").val().length > 4){
			$("#delayDaysError").html("选择非固定有效期，天数长度不能大于4");
			return false;
		}
	}

	if($("#couponGroupCount").val()==''){
		$("#couponGroupCountError").html("发行总量不能为空");
		return false;
	}
	if(parseInt($("#couponGroupCount").val())==''){
		$("#couponGroupCountError").html("发行总量必须大于零");
		return false;
	}
	if($("#couponGroupCount").val().length > 10){
		$("#couponGroupCountError").html("发行总量长度不能大于10");
		return false;
	}

	if($("#averageNum").val()==''){
		$("#averageNumError").html("人均限领份额不能为空");
		return false;
	}
	if(parseInt($("#averageNum").val())==''){
		$("#averageNumError").html("人均限领份额必须大于零");
		return false;
	}
	if($("#averageNum").val().length > 10){
		$("#averageNumError").html("人均限领份额长度不能大于10");
		return false;
	}

	var $errorNum=0;
	$("#couponTable").find("input[name='couponName']").each(function(){
		if($(this).val()==''){
			$("#couponError").html("优惠券名称不能为空");
			$errorNum++;
			return false;
		}

		if($(this).val().length > 30){
			$("#couponError").html("优惠券名称的长度不能大于30");
			$errorNum++;
			return false;
		}
	});
	if($errorNum>0){
       return false;
	}

	$("#couponTable").find("input[name='couponValue']").each(function(){
		if($(this).val()==''){
			$("#couponError").html("优惠券面值不能为空");
			$errorNum++;
			return false;
		}
		else{
           if(parseFloat($(this).val()).toFixed(2)<=0){
        	   $("#couponError").html("优惠券面值必须大于零");
	   		   $errorNum++;
	   		   return false;
           }
		}
		if($(this).val().length > 4){
			$("#couponError").html("优惠券面值长度不能大于4");
			$errorNum++;
			return false;
		}
	});
	if($errorNum>0){
       return false;
	}

	$("#couponTable").find("input[name='couponCon']").each(function(){
		if($(this).val()==''){
			$("#couponError").html("优惠券门槛不能为空");
			$errorNum++;
			return false;
		}
		else{
           if(parseFloat($(this).val()).toFixed(2)<0){
        	   $("#couponError").html("优惠券门槛不能小于零");
	   		   $errorNum++;
	   		   return false;
           }
		}
		if($(this).val().length > 4){
			$("#couponError").html("优惠券门槛长度不能大于4");
			$errorNum++;
			return false;
		}
	});
	if($errorNum>0){
       return false;
	}

	$("#couponTable").find("input[name='num']").each(function(){
		if($(this).val()==''){
			$("#couponError").html("数量不能为空");
			$errorNum++;
			return false;
		}
		else{
           if(parseInt($(this).val())<=0){
        	   $("#couponError").html("数量必须大于零");
	   		   $errorNum++;
	   		   return false;
           }
		}
		if($(this).val().length > 4){
			$("#couponError").html("数量长度不能大于4");
			$errorNum++;
			return false;
		}
	});
	if($errorNum>0){
       return false;
	}
	return true;
}

function adminFormSubmit(submitForm){
	if(!doValidate()){
		return false;
	}

	if($("input[name='theCoupon']:checked").val()==2){
		if($("#couponTable").find("input[name='couponValue']").length<2){
			$("#couponError").html("组合券里至少包含两个优惠券");
			   return false;
		}
	}

	//-----计算总面值，组织提示语句----------------------------------------------------------------
	var $totalValue=0.00;
	var $str='';
	$("input[name='couponValue']").each(function(){
		var $a=$(this).val();
		var $b=$(this).parent().parent().find("input[name='num']").val();
		$totalValue=(parseFloat($totalValue)+parseFloat($a)*parseFloat($b)).toFixed(2);
		//$totalValue=(parseFloat($totalValue)+parseFloat($a)).toFixed(2);
		if($str==''){
			$str='<em class=\"red1 f14 fw\">'+$a+'</em>元<em class=\"red1 f14 fw\">'+$b+"</em>张";
			//$str='<em class=\"red1 f14 fw\">'+$a+'</em>元<em class=\"red1 f14 fw\">1</em>张';
		}
		else{
			$str=$str+'、<em class=\"red1 f14 fw\">'+$a+'</em>元<em class=\"red1 f14 fw\">'+$b+"</em>张";
			//$str=$str+'、<em class=\"red1 f14 fw\">'+$a+'</em>元<em class=\"red1 f14 fw\">1</em>张';
		}
	});

	/* var res = [0];
	$("input[name='couponValue']").each(function(){
		var $a=$(this).val();
		var repeat = false;
		  for(var j = 0; j < res.length; j++){
		   if($a == res[j]){
		    repeat = true;
		    break;
		   }
		  }
		  if(!repeat){
		   res.push($a);
		  }
		$totalValue=(parseFloat($totalValue)+parseFloat($a)).toFixed(2);
	});
	for(var i=0;i<res.length;i++){
		var $a=res[i];
		var $num=0;
		$("input[name='couponValue']").each(function(){
			if($a==$(this).val()){
				$num++;
			}
		});
		if($a!=0){
			if($str==''){
				$str='<em class=\"red1 f14 fw\">'+$a+'</em>元<em class=\"red1 f14 fw\">'+$num+'</em>张';
			}
			else{
				$str=$str+'、<em class=\"red1 f14 fw\">'+$a+'</em>元<em class=\"red1 f14 fw\">'+$num+'</em>张';
			}
		}
	}*/

	var $str1='';
		if($("#startTime").val()!=''&&$("#endTime").val()!=''){
			$str1='<em class=\"red1 f14 fw\">'+$("#startTime").val()+'至'+$("#endTime").val()+'</em>';
		}

		if($("#delayDays").val()!=''){
			$str1='券到账后有效期<em class=\"red1 f14 fw\">'+$("#delayDays").val()+'</em>天';
		}
	
    var $temp='优惠券信息：总面值为<em class=\"red1 f14 fw\">'+$totalValue+'</em>元，其中'+$str+'，有效期为'+$str1+'，可发放<em class=\"red1 f14 fw\">'+$("#couponGroupCount").val()+'</em>人';
   //---------------------------------------------------------------------
	
	showPopCommonPage($temp+"；确认保存？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
		closePopCommonPage();

		$("#"+submitForm).ajaxSubmit({
			type : 'post',
			data:{},
			url : "../couponGroup/couponGroupAdd.action",
			dataType:"text",
			success : function(data) {
				if(1 == data){
					parent.parent.showRturnTip("添加成功",true);
					window.location.href='../couponGroup/couponGroupList.action';
				}
				if(-1 == data||0 == data){
					parent.parent.showRturnTip("添加失败",false);
				}
			}
		});

		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
	});
}

//关闭通用选择弹出层
function closePopCommonPage()
{
	$("#commonStatement",parent.parent.window.document).text("");
	$("#commonHide",parent.parent.window.document).hide();
    $("#commonShowPage",parent.parent.window.document).hide();
}

//展示通用选择弹出层
function showPopCommonPage(popTitle)
{
	$("#commonHide",parent.parent.window.document).show();
    $("#commonShowPage",parent.parent.window.document).show();
    //弹出层的标题
    $("#commonStatement",parent.parent.window.document).append(popTitle);
}

</script>
</html>
