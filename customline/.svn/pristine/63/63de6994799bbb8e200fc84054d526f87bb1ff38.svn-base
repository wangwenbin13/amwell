<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户定制记录</title>
<jsp:include page="../resource.jsp"/>
<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
/* box */
.showbox{width:0px;height:0px;display:none;position:absolute;right:0;top:0;z-index:100;background:#fff;}
.showbox h2{height:30px;font-size:14px;background-color:#0093dd;position:relative;line-height:30px;color:#fff;border:1px #8FA4F5 solid;border-bottom:0;padding:1px 0 0 10px;}
.showbox h2 a{position:absolute;right:5px;top:0;font-size:12px;color:#fff;}
.showbox .mainlist{padding:10px 10px 20px;}
.showbox .mainlist li{height:24px;line-height:24px;}
.showbox .mainlist li span{margin:0 5px 0 0;font-family:"宋体";font-size:12px;font-weight:400;color:#ddd;}
.showbox .mainlist li a{font-size:12px;}
.mask{background:#666;position:absolute;z-index:99;left:0;top:0;display:none;width:100%;height:100%;opacity:0.5;filter:alpha(opacity=50);-moz-opacity:0.5;}
.showboxContent{height:auto;background:#fff;border:1px #8FA4F5 solid;border-top:0;padding:1px;}
.textarea{resize:none;width:100%;height:80px;text-index:5px;padding-top:5px;}
.route-details:hover{cursor:default;}
.stations {display:none;}
</style>
</head>
<body>
	<form id="searchForm" name="searchForm" action="../newApplicationReport/search.action" method="post"> 
		<input type="hidden" name="p" id="myCurrentPageIndex" value=""/>
			<ul class="r-sub-sel black1 mt20 clearfix">  
			    <li class="w29p">
			        <span class="t-r w65 fl">日期：</span>
				    <span class="r-input fl w40p ">
				    <input id="startDate" name="startDate" class="Wdate75 gray2 Wdate" readonly="readonly" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${startDate}"/>
				    </span>
				    <span class="fl" style="margin-left:5px;margin-right:5px;">至</span>
				    <span class="r-input fl w40p ">
				    <input id="endDate" name="endDate" class="Wdate75 gray2 Wdate" readonly="readonly" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" value="${endDate}"/>
				    </span>
				</li>
				<li class="w30p">
					<span class="fl w106 t-r">所属城市：</span>
					<select name="provinceCode" id="provinceCode" class="fl r-input mr10" onchange="loadCity4Line(this.value);">
						<option value="">--选择省份--</option>
					    <c:forEach items="${proSysAreas}" var="proSysArea">
					        <c:if test="${provinceCode==proSysArea.arearCode}">
					            <option value="${proSysArea.arearCode}" selected>${proSysArea.areaName}</option>
					        </c:if>
					        <c:if test="${provinceCode!=proSysArea.arearCode}">
					            <option value="${proSysArea.arearCode}">${proSysArea.areaName}</option>
					        </c:if>
					    </c:forEach>
					</select>
					<select name="cityName" id="cityCode" class="fl r-input mr10">
						<option value="">--选择城市--</option>
					</select>
			    </li>
				<li class="w20p">
					<span class="t-r w65 fl">状态：</span>
					<select name="isHandle" class="r-input w30p">
					    <c:if test="${isHandle=='1'}">
					        <option value="0">未处理</option>
					        <option value="1" selected="selected">已处理</option>
					    </c:if>
					    <c:if test="${isHandle!='1'}">
					        <option value="0" selected="selected">未处理</option>
					        <option value="1">已处理</option>
					    </c:if>
					</select>
				</li>
				<li><a href="javascript:query();" class="btn-blue4"/>查找</a></li>
				<li><a href="javascript:exportExcel();" class="btn-blue4"/>导出Excel</a></li>
			</ul >  
  	</form>
	<div class="table2-text sh-ttext mt10">       
		<table width="100%" border="0" class="table3" id="tableDiv">
			<tr align="center" class="th">
				<th scope="col" width="15%">线路ID</th>
				<th scope="col" width="10%">城市</th>
				<th scope="col" width="15%">上车点</th>
				<th scope="col" width="15%">下车点</th>
				<th scope="col" width="10%">报名人数</th>
				<th scope="col" width="15%">操作</th>
			</tr>
			<c:if test="${list==null||fn:length(list)==0}">
				<tr align="center" class="bg1">
       				<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
       				<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
       			</tr>
			</c:if>
			<c:forEach items="${list}" var="newApplicationReport" varStatus="status">
				<c:if test="${status.index%2==0}">
					<tr align="center" class="tr bg1">
				</c:if>
				<c:if test="${status.index%2==1}">
					<tr align="center" class="tr bg2">
				</c:if>
				<td>${newApplicationReport.applicationId}</td>
				<td class="f-arial">${newApplicationReport.cityName}</td>
                <td class="f-arial">${newApplicationReport.ustation}</td>
                <td class="f-arial">${newApplicationReport.dstation}</td>
				<td class="f-arial">
 					${newApplicationReport.enrollNum}
				</td>
				<th>
					<a href="javascript:void(0);" class="fn blue1 mr10" onclick="exportPassengerTel('${newApplicationReport.applicationId}')">导出手机号</a>
					<c:if test="${newApplicationReport.isHandle==null}">
					    <a href="javascript:void(0);" class="fn blue1 mr10" onclick="handle('${newApplicationReport.applicationId}')">处理</a>
					</c:if>
				</th>
			</tr>
		</c:forEach>
    </table>
	<c:if test="${page.pageCount>0}">
		<div class="page t-c mt20  fl widthfull" id="pageDiv">
			<a href="javascript:turnPage(0);">首页</a>
	        <c:if test="${page.currentPage>1}">
				<a href="javascript:turnPage(${page.currentPage-1})">上一页</a>
	        </c:if>
			<c:forEach items="${page.pageList}" var="item">
				<c:if test="${item.field02==1}">
					<b><span class="current">${item.field01}</span></b>
				</c:if>
				<c:if test="${item.field02!=1}">
					<a href="javascript:turnPage(${item.field01})">${item.field01}</a>
				</c:if>
			</c:forEach>
			<c:if test="${page.currentPage < page.pageCount}">
				<a href="javascript:turnPage(${page.currentPage+1})">下一页</a>
			</c:if>
			<a href="javascript:turnPage(${page.pageCount})">末页</a>
			&nbsp;[&nbsp;
			<font color="red">${page.currentPage}</font>/<font color="red">${page.pageCount}</font>
			&nbsp;共&nbsp;
			<font color="red">${page.totalCount}
			</font>&nbsp;条记录]
		</div>
	</c:if>
	</div>
</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();
	//通过默认省份加载城市信息
	loadOriginalCity();
});

//根据省份加载城市
function loadCity4Line(value){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].areaName+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
	    });
	}
}

function handle(applicationId){
	parent.parent.showPopCommonPage("确定要处理吗？");
	  //解绑定，防止多次执行click事件
	  $("#commonSure",parent.parent.window.document).unbind('click');
	  //动态添加方法
	  $("#commonSure",parent.parent.window.document).click(function(){
			parent.parent.closePopCommonPage();
			$.ajax({
				url:'../newApplicationReport/handle.action?&random='+Math.floor(Math.random()*10000+1)+'&applicationId='+applicationId,
				type:'get',
				cache:false,	
				dataType:"text",	
				success:function(data){	
				    if(data=="success"){
				       parent.parent.showRturnTip("处理成功!",true);
				       $("#currentPageIndex").val("0");
				       $("#searchForm").submit();
				    }else{
				       parent.parent.showRturnTip(data,false);
				    }
					
				},
				error:function(){
					parent.parent.showRturnTip("处理失败!",false);
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

function exportExcel(){
	$("#searchForm").attr("action","../newApplicationReport/exportExcel.action");
	$("#searchForm").submit();
}

function query(){
	$("#searchForm").attr("action","../newApplicationReport/search.action");
	$("#searchForm").submit();
}

function turnPage(pageIndex){
	$("[name='p']").val(pageIndex);
	query();
}

function reset(){
	$("[name='date']").val("");
	$("[name='cityName']").val("");
	$("[name='telephone']").val("");
	query();
}

function exportPassengerTel(applicationId){
	var date = $("[name='date']").val();
	var url = "../newApplicationReport/exportTelehoneList.action?applicationId="+applicationId+"&date="+date;
	window.open(url); 
}

// 查看订票情况
function getOrderNum(lineBaseId){
	parent.document.location.href="../line/getOrderNum.action?lineBaseId="+lineBaseId;
}

//翻页方法
function toPage(value){
	$("#currentPageIndex").val(value);
	$("#turnPage").submit();
}

//判断输入的参数是否符合规定
function toJudgePage(value){
	var totalPage = "${page.pageCount}";
	totalPage = parseInt(totalPage);
	if(""==value){
		return;
	}
	value = parseInt(value);
	if(value<1){
		parent.parent.showCommonTip("请输入大于0的数!");
		return;
	}else if(value>totalPage){
		parent.parent.showCommonTip("超出最大页数!");
		return;
	}
	var pageSize = "${pageSize}";
	toPage((value-1)*pageSize);
}

/*分页enter按键处理函数*/
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		var currPage = $("#goPageNum").val();
        //输入为空不跳转
        if(currPage == "" || typeof(currPage) == "undefined")
    	{
    		return false;
    	}
	    toJudgePage(currPage);
		break;
	default:
		break;
	}
}

var ua = window.navigator.userAgent;
//判断浏览器类型
if (ua.indexOf("MSIE") >= 1 || ua.indexOf("Chrome") >= 1){
	//禁止后退键  作用于IE、Chrome  
	document.onkeydown = pressKey;
}else if(ua.indexOf("Firefox") >= 1){
	//禁止后退键 作用于Firefox、Opera  
	document.onkeypress = pressKey;  
}else{
	document.onkeypress = pressKey;  
}

//检查跳页是否大于总页数
function checkGoPageNum(pageNum){
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(parseInt(pageNum) > parseInt(totalPage))
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}

function loadOriginalCity(){
	   var province = "${provinceCode}";
	   if(null!=province&&province.length>0&&province!="null"){
	      loadCity(province);
	   }
}

//根据省份加载城市
function loadCity(value){
	var $theSelectedCityCode="${cityName}";
	if(null!=$theSelectedCityCode&&""!=$theSelectedCityCode){
		var url = '../merchantAction/getProvince.action?proId='+value;
		leaseGetAjax(url,"json",function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value=''>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
				if($theSelectedCityCode==data[i].areaName){
					$("<option value='"+data[i].areaName+"' selected='true'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
				}
				else{
					$("<option value='"+data[i].areaName+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
				}
			})
		});
	}else{
		var url = '../merchantAction/getProvince.action?proId='+value;
		leaseGetAjax(url,"json",function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value=''>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
		});
	}
}
</script>