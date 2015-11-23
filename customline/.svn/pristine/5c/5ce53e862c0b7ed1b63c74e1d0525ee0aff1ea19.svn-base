<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-公司详情</title>
<%@include file="../resource.jsp" %>

</head>
<body>

<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<input type="hidden" id="pageSizeEm" />
<input type="hidden" id="currPageEm" />
<input type="hidden" id="totalCountEm" />
<!-- 隐藏线路对象 -->
<input type="hidden" id="lineStr" value="${company.lineStr }"/>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;线路管理&nbsp;>&nbsp;公司详情<span class="blue1 ml25"></span></p></div>
<div class="mt10 ml20 mr20 black1">
    <div class="fl widthfull p-r">
	    <div class="table-title">
	       <ul>
	       	   <li class="on"><a href="javascript:void(0)" class="f14 blue2">公司详情</a></li>
	       </ul>
	     </div>
		<s:form id="addForm" action="" method="post" theme="simple">
		<!-- 隐藏公司ID -->
		<input name="search.field01" type="hidden" value="${company.companyId }" id="companyId"/>
		<input name="company.companyCity" type="hidden" value="${company.companyCity}" id="companyCity"/>
		<!-- 存放导入的手机号码 -->
		<input type="hidden" id="dbTel" value="${company.telephone }" name="search.field02"/> <!-- 已经拥有的电话号码 -->
		<input type="hidden" id="choocePassengerList"  value="${company.telephone }"/><!-- 存放导入的电话号码 -->
		<input type="hidden" id="choocePassengerList1" value=""/>
		<input type="hidden" id="lineIds" name="search.field03" value=""/>
		<div class="table2-text">
		     <p class="bt-bot-line"><span class="red1">*字段为必须填写，不填写无法完成创建</span></p>
		 	 <div class="table2-text table2-text-height clearfix">
			  		<ul>
			  			<li class="widthfull fl"><span class="w100 fl t-r">公司名称：<em class="red1 mr4">*</em></span>
			  				<s:textfield  cssClass="r-input w35p gray2 fl" name="search.field04" theme="simple" id="businessName" check="11" maxlength="30" value="%{company.companyName}"/></li>
			  			<li class="widthfull fl mt10"><span class="w100 fl t-r">地址：<em class="red1 mr4">*</em></span>
				  			<select class="p3 fl" id="provinceCode" onchange="queryAreaCityEm()" name="search.field05">
				             		<option value="请选择" >请选择</option>
				             		<s:iterator value="proSysAreas" var="area" >
				             			<option value="${area.arearCode }" <s:if test="%{#area.arearCode == company.companyProvince}">selected="selected"</s:if>>${area.areaName }</option>
				             		</s:iterator>
				             </select>
				  			<select class="p3 fl ml8" id="areaCode" name="search.field06">
				             		<option value="请选择">请选择</option>
				             </select>
			            </li>
			            <li class="w99p h37 mb10 fl">
			                <div class="sb-top p-r" id="sb-top">
				               <span class="black1 ml10" onclick="changePage('spacialListDiv','passengerListDiv');"><input type="radio" class="checkbox" value="1" name="changeType" checked="checked"/></span><em class="ml4">专线</em>
				               <span class="black1 ml10" onclick="changePage('passengerListDiv','spacialListDiv');"><input type="radio" class="checkbox" value="2" name="changeType"/></span><em class="ml4">员工</em>
			           	    </div>
			
			           </li>
			  	  	</ul>
			  	  	<!-- 专线列表 start -->
			  	  	<div class="mb15 clearfix" id="spacialListDiv">
						<div class="fl dj-box p-r" style="margin-left:0;width:800px;background:#fff;">
							<span style="left:27px;top:-10px" class="sb-arrow p-a"></span>
							<!-- 专线列表 -->
							<p class="bt-bot-line f12 blue2 mt10 clearfix line24">
								<span class="bt-ico fl mr5"></span><span class="fl">专线列表</span>
								<a href="javascript:void(0);" class="fr fn blue1" onclick="deleteTr('spacialList','checkAllBoxOne','checkboxchildOne')">删除</a>
								<a href="javascript:void(0);" class="fr fn blue1 mr10" onclick="showPopSpacialPage()">新增</a>
							</p>
							<div class="table2-text sh-ttext" style="max-height:200px;overflow-y:auto;">
									<table width="100%" border="0" class="table1 t-line30" id="spacialList">
									<tr>
										<th scope="col" width="6%"><input type="checkbox" name="checkAllBoxOne" class="checkbox" id="checkAllBoxOne" /></th>
										<th scope="col">ID</th>
										<th scope="col" width="10%">线路名称</th>
										<th scope="col" width="35%">起点/终点</th>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<!-- 专线列表 end -->
					<!-- 员工列表 start -->
					<div class="mb15 clearfix" style="display:none" id="passengerListDiv">
						<div class="fl dj-box p-r" style="margin-left:0;width:800px;background:#fff;">
							<span style="left:81px;top:-10px" class="sb-arrow p-a"></span>
							<ul class="clearfix">
								<li class="widthfull mt10 p-r"><span class="fl mt5">手机号码：
								</span> 
								<span class="fl w25p mr10"> 
									<input type="text" class="fl r-input w98p mr10" id="telephone" /> </span> 
										<a class="search-btn1 fl mt2 ml5" href="javascript:void(0)" onclick="searchTelphone()"></a>
										<a href="../marketing/downloadTemplate.action" class="fl mt10 blue1 ml10">下载Excel模板</a>
								</li>
							</ul>
								<p class="bt-bot-line f12 blue2 mt10 clearfix line24">
								<span class="bt-ico fl mr5"></span><span class="fl">员工列表</span>
								<a href="javascript:void(0);" class="fr fn blue1" onclick="deleteEmployeeTr('passengerList','checkAllBoxTwo','checkboxchildTwo')">删除</a>
								<span class="p-r yc-file fr ml10">
						       		 <input type="file" class="file2 p-a" onchange="uploadFile();" id="excelFile" name="excelFile"/>
						       		 <a href="javascript:void(0);" class="fr fn blue1 mr10" >导入</a>
					       		 </span>
							</p>
							<div class="table2-text sh-ttext" style="max-height:200px;overflow-y:auto;">
							   <table width="100%" border="0" class="table1 t-line30" id="passengerList">
									<tr>
										<th scope="col" width="6%"><input type="checkbox" name="checkAllBoxTwo" class="checkbox" id="checkAllBoxTwo" /></th>
										<th scope="col" width="10%">序号</th>
										<th scope="col" width="35%">联系方式</th>
									</tr>
							 </table>
							</div>
						 <!--Start page-->
		                 <div class="page t-c mt20" id="pageDivEm">
					       <a href="javascript:void(0);" id="homePageEm" onclick="goToPageEm('homePageEm');">首页</a>
					       <a href="javascript:void(0);" id="prePageEm" onclick="goToPageEm('prePageEm');">上一页</a>
					       <ul id="pageNumDivEm"></ul>
					       <a href="javascript:void(0);" id="nextPageEm" onclick="goToPageEm('nextPageEm');">下一页</a>
					       <a href="javascript:void(0);" id="endPageEm" onclick="goToPageEm('endPageEm');">末页</a>
					       <span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNumEm" onblur="goToPageEm('goPageNumEm');"  onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNumEm(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNumEm(this.value);"/>页</span>
					       <span class="ml25">[共<span id="totalPageNumEm" class="fw"></span>页]</span>
	   				  </div>
	                   <!--End page-->
					</div>
		  		</div>
		  		<!-- 员工列表 end -->
		  		<p class="t-c mt20"><input type="button" class="display-ib btn1 white mr40" value="保存" onclick="subMit();"/><input type="reset" onclick="javascript:history.go(-1);" class="display-ib btn1 white" value="取消"/></p>
			</div>
		</div>
		</s:form>
	</div>
</div>
<script type="text/javascript" src="../js/jquery/ajaxfileupload.js"></script>
<script type="text/javascript" src="../js/common/page3.js"></script>
<script type="text/javascript">
$(function(){
	//浏览器可视窗口的的高度
	$(".table2-text-height").css("height",($(window).height()-180+"px"));
	$(window).resize(function() {
		$(".table2-text-height").css("height",($(window).height()-180+"px"));
	}); 
	//清除input框默认值，验证方法  
	clearInputValidateDefaultVal();
	//清除IE缓存
	$.ajaxSetup ({ 
		cache: false 
	});

	//加载城市
	queryAreaCityEm();
	//加载员工列表
	createEmployeeDate("passengerList","dbTel");
	//加载线路列表
	addTableTrP();
});

function loadDetailDateEm(){
	createEmployeeDate("passengerList","dbTel")
}
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}

//清除input框默认值 
function clearInputValidateDefaultVal()
{
	$('input:text').each(function(){  
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
	
	$("#areaCode").focus(function(){   //城市
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	});
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

//新增线路
function showPopSpacialPage()
{ 
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("../specialLineLine/addSpecialLine.action?random="+Math.floor(Math.random()*10000+1)); 
    $("#mainBody").show();
}

//异步提交添加/修改
function subMit(){
	var flag = judgeForm();
	if(!flag){
		return;
	}

	//获取线路IDs
	var lineIds = getAllIds("checkboxchildOne");
	$("#lineIds").val(lineIds);
	
	
	$("#addForm").ajaxSubmit({
		
		type : 'post',
		url : '../specialLineLine/updateCompany.action',
		success : function(data) {
			
			if (data == "success") {
				parent.parent.showRturnTip("修改成功",true);
				window.location.href="../specialLineLine/getCompanyLineList.action";
			}else if("error"==data){
				parent.parent.popLodeShowPage(false);
				parent.parent.showRturnTip("修改失败",false);
			}else{
				parent.parent.popLodeShowPage(false);
				parent.parent.showRturnTip("系统错误",false);
			}
		}
	});
}

//验证表单
function judgeForm(){
	getthis.each(function(){
			var obj = this;
			checkByType(obj);
	});
	
	selectValidate("provinceCode"); //省份
	selectValidate("areaCode");  //城市
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0)
	{
		
		//验证提交 防止多次提交
		setSubmitDisale(false);
		return true;
	}
	else
	{
		
		//验证提交 防止多次提交
		setSubmitDisale(true);
	    return false;
	}
}

function changePage(id1,id2){
	$("#"+id1).show();
	$("#"+id2).hide();
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
	var left = $("#"+id).offset().left-$("#"+id).parents().offset().left;
	$("#"+id+"ErrTip").css("margin-left",left+"px");
	$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
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
function validateUserDefineClear(id)
{
    $("#"+id+"ErrTip").hide();
    $("#"+id).parent().find(".onCorrect").remove();
}
function regValidate(id,reg,tip)
{
	var thisRegs = new RegExp(reg);
	var valueText = $("#"+id).val();
	if(typeof(valueText) != "undefined")
	{
		if(thisRegs.test(valueText))
		{
			validateUserDefinedSucc(id);
			return true;
		}else{
			createErrorTip(id,tip);
			return false;
		}
	}else{
		createErrorTip(id,tip);
		return false;
	}
}

$(function(){
	$(".sh-add-new").css("height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("height",$(window).height()-130+"px");	
	})

		//全部选中
	$("#checkAllBoxOne").click(function(){
		//判断是否已经打勾
		$('input[name="checkboxchildOne"]').attr("checked",this.checked);
	});
	
	$("#checkAllBoxTwo").click(function(){
		//判断是否已经打勾
		$('input[name="checkboxchildTwo"]').attr("checked",this.checked);
	});
	
	//选中某一项
	$('input[name="checkboxchildOne"]').click(function(){
		var $checkBoxChild = $("input[name='checkboxchildOne']");
		$("#checkAllBoxOne").attr("checked",$checkBoxChild.length == $("input[name='checkboxchildOne']:checked").length ? true : false);
	});

	$('input[name="checkboxchildTwo"]').click(function(){
		var $checkBoxChild = $("input[name='checkboxchildTwo']");
		$("#checkAllBoxTwo").attr("checked",$checkBoxChild.length == $("input[name='checkboxchildTwo']:checked").length ? true : false);
	});
});

//------------------------- 列表选中和未选中start------------------------------

//获取选中的所有id
function getCheckLoginIds(name)
{
	var checkboxs = $("input[name='"+name+"']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

//获取城市
function queryAreaCityEm(){
	
	var areaCode = $("#provinceCode").val().trim();
	
	if(areaCode == "" || areaCode == "请选择"){
		$("#areaCode").children("option").eq(0).nextAll().remove();
		return;
	}

	$.ajax({
		url:'../specialLineLine/queryAreaCity.action?sysArea.arearCode='+areaCode,
		type:'post',
		cache:false,	
		dataType:"json",	
		success:function(data){	
			
			if(data != null && data != ""){
				$("#areaCode").children("option").eq(0).nextAll().remove();
				//选中对应的城市
				var companyCity = $("#companyCity").val();
				
				for(var i=0;i<data.length;i++){
					//alert(companyCity == data[i].arearCode);
					if(companyCity == data[i].arearCode){
						 $("#areaCode").append("<option value='"+data[i].arearCode+"' selected='selected'>"+data[i].areaName+"</option>");
					}else{
						 $("#areaCode").append("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>");
					}
				}

				
				
				
			}
			
		}
	});
}

//员工有数据
function createEmployeeDate(id,chooseId)
{
	
	var employV = $("#"+chooseId).val();
	var trArr;
	// 没有数据的时候
	if(employV != ''){
		$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
		trArr = employV.split(",");
		totalCountEm = trArr.length;//总条数
	}
	pageSizeEm = 10;//每页显示的条数
	//currPageEm = 1;//当前页码
	$("#pageSizeEm").val(pageSizeEm);
	$("#currPageEm").val(currPageEm);
	$("#totalCountEm").val(totalCountEm);
	checkShowPageEm(currPageEm);
	
	if(totalCountEm > 0){
		for(var i = 0;i < pageSizeEm;i++)
		{
			var $tr = "";
			var curr = ((currPageEm -1) * pageSizeEm)+i;
			if(trArr[curr] != undefined)
			{
				if(i % 2 == 0)
				{
					$tr = "<tr align='center' class='tr bg1' telphone=\""+trArr[curr]+"\">";
				}
				else
				{
					$tr = "<tr align='center' class='tr bg2' telphone=\""+trArr[curr]+"\">";
				}
				var $trs = $tr + "<td class='f-arial'><input type='checkbox' name='checkboxchildTwo' id='childTwo"+i+"' value='"+trArr[curr]+"'/></td>"+
		       	"<td class='f-arial'>"+(curr+1)+"</td>"+
		           	"<td class='f-arial'>"+trArr[curr]+"</td>"+
					"</tr>";
				$("#"+id).append($trs);
			}
		}
	}
}

//上传excel
function uploadFile(){
	var value = $("#excelFile").val();
	if(""==value||value.indexOf(".")==-1){
		return;
	}
	/*var arr = value.split(".");
	var suffix = arr[arr.length-1];
	if(suffix!=("jpg") && suffix!=("png") && suffix!=("jpeg")){
		parent.parent.showRturnTip(validateJson.ImgFile.tip,false); 
		return;
	}*/
	var reg = validateJson.excelFile.reg;
	var thisReg = new RegExp(reg);
	if(!thisReg.test(value))
	{
		parent.parent.showRturnTip(validateJson.excelFile.tip,false); 
		$("#excelFile").val('');
		return;
	}
	//获取已经导入的电话号码
	var choocePassengerList = $("#choocePassengerList").val();
	if(choocePassengerList == ""){
		choocePassengerList = null;
	}

	//加载loding层
	parent.parent.popLodingPage(true);
	
    $.ajaxFileUpload({
			fileElementId:'excelFile', 
			secureuri:false,
			data:{"choocePassengerList":choocePassengerList},
			dataType: 'text',
			url : '../specialLineLine/upload.action',
			success : function(data) {	
				//关闭loding层
				parent.parent.popLodingPage(false);
				
				data = eval("(" + data + ")");
				var theSameTelephone=data.theSameTelephone;
				var wrongTelephone = data.wrongTelephone;
				if(data.excelError!=null){
				    parent.parent.showRturnTip(data.excelError,false);
					return;
				}
				if(data.databaseLists != null){
					parent.parent.showRturnTip("Excel表格里电话号码："+data.databaseLists+"已属于别的公司",false);
					return;
				}
				if (wrongTelephone != null) {
					$("#excelFile").val('');
					parent.parent.showRturnTip("Excel表格里错误的手机号码的序号为：" + wrongTelephone,false);
					return;
				}
				if(theSameTelephone!=null){
					$("#excelFile").val('');
					parent.parent.showRturnTip("Excel表格里以下序号的电话号码有重复："+theSameTelephone,false);
					return;
				}
				// 号码已经导入
				if(data.duplicate != null){
					parent.parent.showRturnTip("Excel表格里手机号码：" + data.duplicate+"已经导入!",false);
					return;
				}
				if (data.result == "error") {
					parent.parent.showRturnTip("导入失败",false);
					$("#excelFile").val('');
				} else {

					parent.parent.showRturnTip("导入成功",true);
					var telephones = data.importTelephones;
					var v1 = $("#choocePassengerList").val();
					if("" == v1){
						$("#choocePassengerList").val(telephones);
					}else{
						$("#choocePassengerList").val($("#choocePassengerList").val()+","+telephones);
					}
					$("#dbTel").val($("#choocePassengerList").val());
					//alert($("#choocePassengerList").val());
					createEmployeeDate("passengerList","dbTel");
					$("#excelFile").val('');
				}

			},
			error : function() {
				parent.parent.showRturnTip("导入失败",true);
				$("#excelFile").val('');
			}

		});
}

//删除专线的选中tr
function deleteTr(id,checkId,childId)
{
	var trArr = getCheckTrIds(childId).split(";");
	var trLength = $("#"+id+" tr").length;
	for(var i = 1;i < trLength;i++)
	{
		for(var j=0;j<trArr.length;j++)
		{
			if($("#"+id+" tr").eq(i).attr("businessId") == trArr[j].split(",")[0])
			{
				$("#"+id+" tr").eq(i).remove();
			}
		}
	}
	$("#"+checkId).attr("checked",false);
	var trNewLength = $("#"+id+" tr").length;
	if(trNewLength == 1)
	{
		createNoListDate("spacialList",4);
	}
}

//获取选中的所有id
function getCheckTrIds(name)
{
	var checkboxs = $("input[name='"+name+"']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

//无数据时重置页码相关数据
function resetPage()
{
	pageSizeEm = 0;//每页显示的条数
	totalCountEm = 0;//总条数
	currPageEm = 1;//当前页码
	$("#pageSizeEm").val(pageSizeEm);
	$("#currPageEm").val(currPageEm);
	$("#totalCountEm").val(totalCountEm);
	checkShowPageEm(currPageEm);
}

//无数据
function createNoListDate(id,count)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	$("#"+id).append("<tr align='center' id='noListDateTr' class='tr bg1'><td colspan='"+count+"'><p class='f13 f-yahei gray4 t-c line26 mt10'><span class='index-nodate mr10'></span><span id='noDate'>暂无数据</span></p></td></tr>");
}

//增加专线tr，需要去掉重复添加
function addTableTrP()
{
	// 如果这里是无数据行，需要删除无数据的行
	$("#noListDateTr").remove();
	var lineStr = $("#lineStr").val().trim();
	if(lineStr != "")
	{
		var trArr = lineStr.split(";");
		var trLength = $("#spacialList tr").length;
		for(var i = 1;i < trLength;i++)
		{
			for(var j=0;j<trArr.length;j++)
			{
				if($("#spacialList tr").eq(i).attr("businessId") == trArr[j].split(",")[0])
				{
					$("#spacialList tr").eq(i).remove();
				}
			}
		}
		for(var i=0;i<trArr.length;i++)
		{
			var $tr = "";
			if(i % 2 == 0)
			{
				$tr = "<tr align='center' class='tr bg1' businessId =\""+trArr[i].split(",")[0]+"\">";
			}
			else
			{
				$tr = "<tr align='center' class='tr bg2' businessId =\""+trArr[i].split(",")[0]+"\">";
			}
			var $trs = $tr + "<td class='f-arial'><input type='checkbox' name='checkboxchildOne' id='childOne"+i+"' value='"+trArr[i].split(",")[0]+"'/></td>"+
	           	"<td class='f-arial'>"+trArr[i].split(",")[0]+"</td>"+
	           	"<td class='f-arial'>"+trArr[i].split(",")[1]+"</td>"+
	           	"<td class='f-arial'>"+trArr[i].split(",")[2]+"</td>"+
				"</tr>";
			$("#spacialList").append($trs);
		}
	}
}

//删除专线的选中tr
function deleteEmployeeTr(id,checkId,childId)
{
	var trArr = getCheckTrIds(childId).split(";");
	var trLength = $("#"+id+" tr").length;
	var newArr = $("#dbTel").val().split(",");
	for(var i = 0;i < newArr.length;i++)
	{
		for(var j=0;j<trArr.length;j++)
		{
			if(newArr[i] == trArr[j])
			{
				newArr.splice($.inArray(trArr[j],newArr),1);
				$("#"+id+" tr").eq(i+1).remove();
			}
		}
	}

	//删除导入的电话号码
	var excelArr = $("#choocePassengerList").val().split(",");
	for(var i = 0;i < excelArr.length;i++)
	{
		for(var j=0;j<trArr.length;j++)
		{
			if(excelArr[i] == trArr[j])
			{
				excelArr.splice($.inArray(trArr[j],excelArr),1);
			}
		}
	}
	$("#choocePassengerList").val(excelArr);
	
	
	$("#"+checkId).attr("checked",false);
	$("#dbTel").val(newArr);
	var trNewLength = newArr.length;
	if(trNewLength > 0)
	{
		currPageEm = 1;
		createEmployeeDate("passengerList","dbTel");
	}
	else
	{
		createNoListDate("passengerList",3);
		//无数据时重置页码相关数据
		resetPage()
	}
}

//获得所有的线路ID
function getAllIds(name)
{
	var checkboxs = $("input[name='"+name+"']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			loginIds = loginIds + checkboxs[i].value + ",";
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

//搜索电话号码
function searchTelphone(){
	var telephone = $("#telephone").val().trim();

	//所有员工的电话号码
	$("#choocePassengerList1").val($("#dbTel").val());
	if("" == telephone){
		createEmployeeDate("passengerList","dbTel");
		return;
	}
	
	var employV = $("#choocePassengerList1").val();
	if(employV != ''){
		var tels  = employV.split(",");
	}

	var choocePassengerList1 = "";
	//查到这个号码
	for(var i=0;i<tels.length;i++){
		
		if(tels[i].indexOf(telephone) != -1){
			choocePassengerList1 = choocePassengerList1 + tels[i]+",";
		}
	}

	if ("" != choocePassengerList1)
	{
		choocePassengerList1 = choocePassengerList1.substring(0, choocePassengerList1.length - 1);
		$("#choocePassengerList1").val(choocePassengerList1);
		createEmployeeDate("passengerList","choocePassengerList1")
	}else{
		createNoListDate("passengerList",3);
		//无数据时重置页码相关数据
		resetPage()
	}
	
}
//------------------------- 列表选中和未选中end------------------------------
</script>
</body>
</html>