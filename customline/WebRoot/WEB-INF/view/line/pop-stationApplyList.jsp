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
<title>站点统计-乘客站点申请列表</title>
</head>
<body>
<input type="hidden" id="pageSize" />
<input type="hidden" id="currPage" />
<input type="hidden" id="totalCount" />
<div class="pop black1" id="popMapPage" style="width:900px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">乘客申请列表</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="loadPage.window.closePopMerchantPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10">
        <input type="hidden" name="search.field05" value="1" />
        <div class="table2-text sh-ttext">       
           <table width="100%" border="0" class="table1" id="tableDivSearch">
                 <tr align="center">
                   <th scope="col" width="20%">乘客昵称/ID</th>
                   <th scope="col" width="20%">联系电话</th>
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
var jsonDate =null;
var type=null;
var stationName = null;
$(function(){	
    type = "<%=request.getParameter("type")%>";
    stationName = "<%=request.getParameter("stationName")%>";
    loadDetailDate();
});

//调用后台   加载详情数据
function loadDetailDate()
{
	var currPage = $("#currPage",parent.window.document).val();

	if(currPage == "" || typeof(currPage) == "undefined")
	{
		currPage = 1;
	}
	$.ajax({
		url : "../line/getStationApplyList.action",
		type : "post",
		dataType : "json",
		data : {
			pageSize : $("#pageSize",parent.window.document).val(),
			currPage : currPage,
			totalCount : $("#totalCount",parent.window.document).val(),
			type:type,
			stationName:stationName
		},
		cache: false,//不从浏览器缓存中取
		async: false, //async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
		error : function() {
			createNoDate("tableDivSearch");
		},
		success : function(data) {
			jsonDate = data;
			//无数据时提示暂无数据
			if(jsonDate.resultData.length == 0)
			{
				createNoDate("tableDivSearch");
			}
			else
			{
				pageSize = jsonDate.pageSize;//每页显示的条数
				totalCount = jsonDate.totalCount;//总条数
				currPage = jsonDate.currPage;//当前页码
				$("#pageSize",parent.window.document).val(pageSize);
				$("#currPage",parent.window.document).val(currPage);
				$("#totalCount",parent.window.document).val(totalCount);
				createDate("tableDivSearch",jsonDate);
				checkShowPage(currPage);
			}
		}
	});
}
//无数据
function createNoDate(id)
{
	$("#"+id+" tr",parent.window.document).eq(0).nextAll().remove(); //除第一行外删除所有的tr
	$("#"+id,parent.window.document).append("<tr align='center' class='tr bg1'><td colspan='4'><p class='f13 f-yahei gray4 t-c line26 mt10'><span class='index-nodate mr10'></span><span id='noDate'>暂无数据</span></p></td></tr>");
}
//有数据
function createDate(id,jsonDate)
{
	$("#"+id+" tr",parent.window.document).eq(0).nextAll().remove(); //除第一行外删除所有的tr

	for(var i = 0;i < jsonDate.resultData.length;i++)
	{
		var $trs ="<tr align='center'>"+
		    "<td class='f-arial'>"+jsonDate.resultData[i].nickName+"/"+jsonDate.resultData[i].displayId+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].telephone+"</td>"+
			"</tr>";
		$("#"+id,parent.window.document).append($trs);
	}
}
//关闭增加弹出层页面
function closePopMerchantPage()
{
    $("#popMapPage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}

$("#commonCancel",parent.parent.window.document).click(function(){
	parent.parent.closePopCommonPage();
	//解绑定，防止多次执行click事件
    $("#commonSure",parent.parent.window.document).unbind('click');
});
</script>
