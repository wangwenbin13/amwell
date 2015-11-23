<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../resource.jsp"/>
<%
    String path1 = request.getContextPath();
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>包车线路详情-选择商家弹出页面</title>
</head>
<body>
<div class="pop black1" id="popSelectMerchantPage" style="width:900px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">选择商家</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopMerchantPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
   <!--  <form action="../charteredLine/queryMerchant.action" method="post" > -->
    <div class="pop-main p10">
        <input type="hidden" name="search.field05" value="1" />
        <input type="hidden" id="bcLineId" value="${bcLineId }" name="search.field01" />
        <input type="hidden" id="cityCode2" value="${cityCode }" />
        <input type="hidden" id="provinceCode" value="${provinceCode }" />
        <div class="mb20">公司名称：<input type="text" name="search.field04" id="companyName" value="${search.field04}" class="r-input w170"/>&nbsp;&nbsp;
        <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName"	headerKey=""  headerValue="--选择省份--" name="provinceCode" id="provinceCode2" cssClass="fl r-input mr10" onchange="loadCity(this.value);"/>
        <select name="areaCode" id="cityCode" class="fl r-input mr10" ><option value="" >--选择城市--</option></select>&nbsp;&nbsp;
        <input type="button" onclick="queryBusiness()" class="btn-blue4" value="查找"/></div>
   <!--  </form> -->
        
        <div class="table2-text sh-ttext" style="max-height:250px;overflow:auto">       
           <table width="100%" border="0" class="table1" id="businessList">
                 <tr align="center">
                   <th scope="col" width="8%"><input type="checkbox" class="checkbox" name='checkAllBox' id="checkAllBox"/>全选</th>
                   <th scope="col">公司名称</th>
                   <th scope="col" width="20%">联系人</th>
                   <th scope="col" width="25%">联系电话</th>
                 </tr>
                 <s:iterator value="list" var="business" status="s" >
                 <tr align="center" >
                 	<td><input type="checkbox" class="checkbox" name="checkboxchild" value="${business.businessId}" /></td>
                 	<td>${business.name }</td>
                 	<td>${business.contacts }</td>
                 	<td class="f-arial">${business.contactsPhone }</td>
                 </tr>
                 </s:iterator>
            </table>        
         </div>
       <p class="t-c mt10 mb20">
       		<input type="button" class="display-ib btn1 white f14 mr40" onclick="getCheckBusiness()" id="submitButton" value="确定"/>
        	<a  class="display-ib btn1 white f14" href="javascript:void(0);" onclick="closePopMerchantPage();">取消</a>
        	<!-- <a  class="display-ib btn1 white f14 mr40" href="javascript:void(0);">确定</a> -->
        </p>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
//全部选中
$("#checkAllBox").click(function(){
	//判断是否已经打勾
	$('input[name="checkboxchild"]').attr("checked",this.checked);
});
//选中某一项
$('input[name="checkboxchild"]').click(function(){
	var $checkBoxChild = $("input[name='checkboxchild']");
	$("#checkAllBox").attr("checked",$checkBoxChild.length == $("input[name='checkboxchild']:checked").length ? true : false);
});


var lineProvinceCode=$("#provinceCode").val();
var lineCityCode=$("#cityCode2").val();
$(function(){	
// 	  var lineCityCode=$("#cityCode").val();
       loadCity(lineProvinceCode);
       
 
});

//关闭增加弹出层页面
function closePopMerchantPage()
{
	 	$("#popSelectMerchantPage").hide();
	    $("#topHide", parent.window.document).hide();
	    $("#leftHide", parent.window.document).hide();
	    $("#mainBody").hide();
}

//获取选中的商家
function getCheckBusiness(){
	var checkboxs=$("input[name='checkboxchild']");
	var bcLineId=$("#bcLineId").val();
// 	alert(checkboxs[0]);
	var telephones;
	var loginIds="";
	for(var i = 0; i < checkboxs.length; i++){
		if (checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ",";
			}
	}
	if(loginIds=="" ){
		parent.parent.showRturnTip("没有选择商家",false);
		return;
	}
// 	$("#iframe_right",parent.parent.window.document).attr("src","../charteredLine/sendBcToMerchant.action?loginIds="+loginIds+"&bcLineId="+bcLineId);	
	
	var expireTime = $("#expireTime").val();
	setSubmitDisales(true);
	
	$.ajax({
		url:'../charteredLine/sendBcToMerchant.action?loginIds='+loginIds+'&bcLineId='+bcLineId+'&expireTime='+expireTime,
		cache:false,
		type:'post',
		dataType:"json",
		success:function(data){
			if(data>0){
			closePopMerchantPage();
			window.location.href="../charteredLine/getCharteredLineList.action";
			}else{
// 				alert(data);
				parent.parent.showRturnTip("发送商家失败",false);
			}
		}
	})
		
		
}


//查找商家列表
function queryBusiness(){
	var companyName=$("#companyName").val();
	var provinceCode=$("#provinceCode2").val();
	var cityCode=$("#cityCode").val();
// 	alert("provinceCode="+provinceCode+"&&cityCode="+cityCode);
	$("#businessList  tr:gt(0) ").remove();
	
// 	alert(companyName);
		 $.ajax({
			url:'../charteredLine/queryMerchant.action',
			cache:false,
			type : 'post',
			data:{"companyName":companyName,"provinceCode":provinceCode,"cityCode":cityCode},
			dataType:"json",
			success:function(data){	
// 				alert(data.length);
				if(data!=null){
					for(var i=0;i<data.length;i++){
						var businessId=data[i].businessId
						var name=data[i].name;
						var contacts = data[i].contacts;
						var contactsPhone=data[i].contactsPhone;
						$("#businessList").append("<tr align='center'><td><input type='checkbox' class='checkbox' name='checkboxchild' value="+businessId+" /></td> <td>"+name+"</td><td>"+contacts+"</td><td class='f-arial'>"+contactsPhone+"</td></tr>")
					}
				}
	 		}
	 });
// 	 $("#iframe_right",parent.parent.window.document).attr("src","../charteredLine/getCharteredLineList.action;";
}



//根据省份加载城市
function loadCity(value){
  
// 	alert(value);
  	$.ajax({
		url:'../charteredLine/getProvince.action?proId='+value,
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value=''>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
				if(data[i].arearCode==lineCityCode){
			      $("<option value='"+data[i].arearCode+"' selected='selected'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			   }else{
// 			   		alert(data[i].areaName);
			      $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			   }
			
			})
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

//验证
/* $(function(){
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
})
 */


</script>
