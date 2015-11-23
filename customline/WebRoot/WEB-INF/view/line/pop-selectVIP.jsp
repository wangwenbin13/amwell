<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../resource.jsp"/>
<%
    String path1 = request.getContextPath();
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1 + "/";
%>
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
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopMerchantPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10">
        <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" id="provinceCode" name="provinceCode" cssClass="fl r-input mr10" value="lineCityVo.provinceCode" onchange="loadPage.window.loadCity(this.value);"/>
        <select name="areaCode" id="cityCode" class="fl r-input mr10"><option value="">--选择城市--</option></select>
        <div class="mb20">公司名称：<input type="text"  class="r-input w170"/>&nbsp;&nbsp;<input type="button" class="btn-blue4" value="查找"/></div>
        <div class="table2-text sh-ttext">       
           <table width="100%" border="0" class="table1 f12">
                 <tr align="center">
                   <th scope="col">公司名称</th>
                   <th scope="col" width="20%">城市</th>
                   <th scope="col" width="20%">专线</th>
                   <th scope="col" width="20%">员工</th>
                 </tr>
            </table>        
         </div>
         <div class="page t-c mt20" id="pageDiv">
	       <a href="javascript:void(0);" id="homePage" onclick="loadPage.window.goToPage('homePage');">首页</a>
	       <a href="javascript:void(0);" id="prePage" onclick="loadPage.window.goToPage('prePage');">上一页</a>
	       <ul id="pageNumDiv"></ul>
	       <a href="javascript:void(0);" id="nextPage" onclick="loadPage.window.goToPage('nextPage');">下一页</a>
	       <a href="javascript:void(0);" id="endPage" onclick="loadPage.window.goToPage('endPage');">末页</a>
	       <span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onblur="loadPage.window.goToPage('goPageNum');"  onkeyup="this.value=this.value.replace(/\D/g,'');loadPage.window.checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');loadPage.window.checkGoPageNum(this.value);"/>页</span>
	       <span class="ml25">[共<span id="totalPageNum" class="fw"></span>页]</span>
	     </div>
    </div>
</div>
<script type="text/javascript" src="../js/common/page.js" type="text/javascript"></script><!-- 分页js --> 
</body>
</html>
<script type="text/javascript">
// 根据省份加载城市
function loadCity(value){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
	    });
	}
}

// 关闭增加弹出层页面
function closePopMerchantPage()
{
    $("#popMapPage").hide();
    $("#mainBody").hide();
    $("#topHide", parent.window.document).hide();
    $("#leftHide", parent.window.document).hide();
}

</script>
