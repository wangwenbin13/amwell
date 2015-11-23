<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../resource.jsp"/>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路列表-选择VIP客户弹出页面</title>
</head>
<body>
<input type="hidden" id="pageSize" />
<input type="hidden" id="currPage" />
<input type="hidden" id="totalCount" />
<div class="pop black1" id="popMapPage" style="width:900px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">选择VIP客户</div>
        <div class="pop-t-r fr">
        	<a href="javascript:void(0);" onclick="closePopMerchantPage();" class="pop-close fr mt4 mr4"></a>
        </div>
    </div>
    <div class="pop-main p10">
        <s:form id="searchForm" theme="simple">
        <select name="provinceCode" id="provinceCode" onchange="loadCity(this.value)" class="fl r-input mr10">
        	<option value="">--选择省份--</option>
        	<s:iterator value="%{#request.proSysAreas}" var="sysArea">
        	    <option value="${sysArea.arearCode}">${sysArea.areaName}</option>
        	</s:iterator>
        </select>
        <select name="cityCode" id="cityCode" class="fl r-input mr10">
        	<option value="">--选择城市--</option>
        </select>
        <div class="mb20">公司名称：
        	<input type="text" name="companyName" class="r-input w170"/>&nbsp;&nbsp;
        	<input type="button" class="btn-blue4" onclick="searchSpecialLine()" value="查找"/>
        </div>
        </s:form>
        <div class="table2-text sh-ttext">       
           <table width="100%" border="0" class="table1 f12" id="dataTable"></table>        
         </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	searchSpecialLine();
});

// 选择vip用户
function chooseCompany(companyId,companyName){
	$("[name='companyId']").val(companyId);
	$("[name='companyName']").val(companyName);
	closePopMerchantPage();
}

//根据省份加载城市
function loadCity(value){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
	    });
	}
}

//关闭增加弹出层页面
function closePopMerchantPage(){
    $("#popMapPage").hide();
    $("#mainBody").hide();
    $("#topHide", parent.window.document).hide();
    $("#leftHide", parent.window.document).hide();
}

/**
 *  查询VIP客户
 */
function searchSpecialLine(){
	var url = "../publishLine/loadSpecialLineAjax.action";
	leasePostAjax(url,$("#searchForm"),"json",function(data){
         if(data.a1=="100"){
            var dataContent = "<tr align='center' id='dataHead'>";
            dataContent += "<th scope='col'>公司名称</th>";
            dataContent += "<th scope='col' width='20%'>城市</th>";
            dataContent += "<th scope='col' width='20%'>专线</th>";
            dataContent += "<th scope='col' width='20%'>员工</th>";
            dataContent += "</tr>";
            var specialLineList = data.a2;
            for(var index=0;index<specialLineList.length;index++){
                var specialLine = specialLineList[index];
                dataContent += "<tr onclick='chooseCompany(\""+specialLine.companyId+"\",\""+specialLine.companyName+"\")'>";
                dataContent += "<td align='center'>"+specialLine.companyName+"</td>";
                dataContent += "<td align='center'>"+specialLine.companyProvince+"/"+specialLine.companyCity+"</td>";
                dataContent += "<td align='center'>"+specialLine.lineCount+"</td>";
                dataContent += "<td align='center'>"+specialLine.passengerCount+"</td>";
                dataContent += "</tr>";
            }
            $("#dataTable").html(dataContent);
         }else{
            alert(data.a2);
         }
	});
}
</script>