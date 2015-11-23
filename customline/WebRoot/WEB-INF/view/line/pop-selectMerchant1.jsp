<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../resource.jsp"/>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路列表-选择商家弹出页面</title>
</head>
<body>
	<input type="hidden" id="pageSize" />
	<input type="hidden" id="currPage" />
	<input type="hidden" id="totalCount" />
<div class="pop black1" id="popMapPage" style="width:900px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">选择商家</div>
        <div class="pop-t-r fr">
        	<a href="javascript:closePopMerchantPage();" class="pop-close fr mt4 mr4"></a>
        </div>
    </div>
    <div class="pop-main p10">
        <s:form id="addForm" theme="simple">
	        <input type="hidden" name="search.field05" value="1" />
	        <select name="provinceCode" id="provinceCode" class="fl r-input mr10" onchange="loadCity(this.value);">
	            <option value="">--选择省份--</option>
	            <c:forEach items="${proSysAreas}" var="sysArea">
	                <c:if test="${lineBaseInfo.provinceCode==sysArea.arearCode}">
	                    <option value="${sysArea.arearCode}" selected>${sysArea.areaName}</option>
	                </c:if>
	                <c:if test="${lineBaseInfo.provinceCode!=sysArea.arearCode}">
	                	<option value="${sysArea.arearCode}">${sysArea.areaName}</option>
	                </c:if>
	            </c:forEach>
	        </select>
	        <select name="cityCode" id="cityCode" class="fl r-input mr10">
	        	<option value="">--选择城市--</option>
	        </select>
	        <div class="mb20">公司名称：
	        	<input type="text" name="brefName" id="field04" value="${search.field04}" class="r-input w170"/>&nbsp;&nbsp;
	        	<input type="button" onclick="loadBusinessList()" class="btn-blue4" value="查找"/>
	        </div>
        </s:form>
        <div class="table2-text sh-ttext">       
           <table width="100%" border="0" class="table1" id="tableDivSearch"></table>        
         </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
var lineBaseId = null;
var lineProvinceCode="${lineBaseInfo.provinceCode}";
var lineCityCode="${lineBaseInfo.cityCode}";

$(document).ready(function(){	
    lineBaseId = "${lineBaseId}";
    if(lineProvinceCode!=null&&lineProvinceCode!=""&&lineProvinceCode.length>0){
    	loadCityWithDefault(lineProvinceCode,lineCityCode);
    }
});

function setBusiness(businessId,businessName){
	$("#businessId_temp").val(businessId);
	$("#businessName").val(businessName);
	closePopMerchantPage();
}

// 加载商家列表
function loadBusinessList(){
	var url = "../publishLine/loadBusinessListAjax.action";
	leasePostAjax(url,$("#addForm"),"json",function(data){
        if(data.a1=="100"){
            $("#tableDivSearch").html("");
            var dataHtml = "<tr align=\"center\">";
            dataHtml += "<th scope=\"col\" width=\"20%\">公司名称</th>";
            dataHtml += "<th scope=\"col\" width=\"20%\">联系人</th>";
            dataHtml += "<th scope=\"col\" width=\"20%\">联系电话</th>";
            dataHtml += "</tr>";
            var businessList = data.a2;
            for(var index=0;index<businessList.length;index++){
                var business = businessList[index];
                dataHtml += "<tr align=\"center\" onclick=\"setBusiness('"+business.businessId+"','"+business.brefName+"')\">";
                dataHtml += "<td align=\"center\">"+business.brefName+"</td>";
                dataHtml += "<td align=\"center\">"+business.contacts+"</td>";
                dataHtml += "<td align=\"center\">"+business.contactsPhone+"</td>";
                dataHtml += "</tr>";
            }
            $("#tableDivSearch").html(dataHtml);
        }else{
        	parent.parent.showRturnTip(data.a2,false);
        }
	});
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

//根据省份加载城市
function loadCityWithDefault(value,cityCode){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
			$("#cityCode").val(cityCode);
			loadBusinessList();
	    });
	}
}


//关闭增加弹出层页面
function closePopMerchantPage(){
    $("#popMapPage").hide();
    $("#mainBody").hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}

function sendToMerchant(businessId){
  parent.parent.showPopCommonPage("确定要将线路发送给此商家吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		var url = "../line/sendToMerchant.action?lineBaseId="+lineBaseId+"&businessId="+businessId;
		leaseGetAjax(url,"json",function(data){
			if(data=="success"){
			       parent.parent.showRturnTip("线路发送成功!",true);
			       closePopMerchantPage();
			       window.location.href="../line/getAllLines.action";
			    }else{
			       parent.parent.showRturnTip("线路发送失败!",false);
			    }
		});
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  $("#commonCancel",parent.parent.window.document).click(function(){
  		parent.parent.closePopCommonPage();
  		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
}
</script>