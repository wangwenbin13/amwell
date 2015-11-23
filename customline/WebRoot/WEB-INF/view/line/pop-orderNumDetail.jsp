<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路详情-订购人数-详情</title>
<!-- 操作地图的js --> 

</head>
<body>
<input type="hidden" id="pageSize" />
<input type="hidden" id="currPage" />
<input type="hidden" id="totalCount" />
<div class="pop black1 w960" id="popNumPage" style="background:#fff;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">订购人数</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopNumPage()" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10">
       <div class="table2-text sh-ttext mt20">       
         <table width="100%" border="0" class="table1" id="tableDivOrderNum">
               <tr align="center">
                 <th scope="col" width="5%">序号</th>
                 <th scope="col" width="12%">购票时间</th>
                 <th scope="col" width="7%">用户ID</th>
                 <th scope="col" width="10%">昵称</th>
                 <th scope="col" width="15%">手机号</th>                     
               </tr>
             </table>        	
          </div> 
        <div class="page t-c mt20" id="pageDiv">
	       <a href="javascript:void(0);" id="homePage" onclick="goToPage('homePage');">首页</a>
	       <a href="javascript:void(0);" id="prePage" onclick="goToPage('prePage');">上一页</a>
	       <ul id="pageNumDiv"></ul>
	       <a href="javascript:void(0);" id="nextPage" onclick="goToPage('nextPage');">下一页</a>
	       <a href="javascript:void(0);" id="endPage" onclick="goToPage('endPage');">末页</a>
	       <span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onblur="goToPage('goPageNum');"  onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);"/>页</span>
	       <span class="ml25">[共<span id="totalPageNum" class="fw"></span>页]</span>
	     </div>
    </div>
    </div>
</body>
</html>
<script type="text/javascript" src="js/common/page2.js"></script>
<script type="text/javascript">
var jsonDate = {
	"pageSize":"1",
	"totalCount":"1",
	"currPage":"1",
	"resultData":[{
		"passengerId":"1",//商户id
		"presetTime":"111", //公司名称
		"displayId":"13718952632", //联系电话
		"nickName":"zh",  //联系人
		"telephone":"zh"
	}]
}

var lineBaseId = null;
var orderStartTime=null;
var orderDate=null;
$(function(){	
    lineBaseId = "<%=request.getParameter("lineBaseId")%>";
    orderStartTime = "<%=request.getParameter("orderStartTime")%>";
    orderDate = "<%=request.getParameter("orderDate")%>";
    loadDetailDate();
});

//调用后台   加载详情数据
function loadDetailDate(){
	var currPage = $("#currPage").val();
	if(currPage == "" || typeof(currPage) == "undefined"){
		currPage = 1;
	}
	var url="line/getLineOrderPassengerList.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime+"&orderDate="+orderDate;
	$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		data : {
			pageSize : $("#pageSize").val(),
			currPage : currPage,
			totalCount : $("#totalCount").val()
		},
		cache: false,//不从浏览器缓存中取
		async: false, //async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
		error : function() {
			createNoDate("tableDivOrderNum");
		},
		success : function(data) {
			jsonDate = data;
			//无数据时提示暂无数据
			if(jsonDate.resultData.length == 0){
				createNoDate("tableDivOrderNum");
			}else{
				pageSize = jsonDate.pageSize;//每页显示的条数
				totalCount = jsonDate.totalCount;//总条数
				currPage = jsonDate.currPage;//当前页码
				$("#pageSize").val(pageSize);
				$("#currPage").val(currPage);
				$("#totalCount").val(totalCount);
				createDate("tableDivOrderNum",jsonDate);
				checkShowPage(currPage);
			}
		}
	});
}

function createNoDate(id){
	$("#"+id+" tr",parent.window.document).eq(0).nextAll().remove(); //除第一行外删除所有的tr
	$("#"+id,parent.window.document).append("<tr align='center' class='tr bg1'><td colspan='5'><p class='f13 f-yahei gray4 t-c line26 mt10'><span class='index-nodate mr10'></span><span id='noDate'>暂无数据</span></p></td></tr>");
}

//有数据
function createDate(id,jsonDate){
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	for(var i = 0;i < jsonDate.resultData.length;i++){
		var $tr = "";
		if(i % 2 == 0){
			$tr = "<tr align='center' class='tr bg1' passengerId =\""+jsonDate.resultData[i].passengerId+"\">";
		}else{
			$tr = "<tr align='center' class='tr bg2' passengerId =\""+jsonDate.resultData[i].passengerId+"\">";
		}
		var $trs = $tr + "<td class='f-arial'>"+(i+1)+"</td>"+
			"<td class='f-arial'>"+jsonDate.resultData[i].presetTime+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].displayId+"</td>"+
           	"<td>"+jsonDate.resultData[i].nickName+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].telephone+"</td>"+
			"</tr>";
		$("#"+id).append($trs);
	}
}

//关闭增加弹出层页面
function closePopNumPage(){
    $("#popNumPage").hide();
    $("#mainBody").hide();
    $("#topHide",parent.window.document).hide();
    $("#leftHide",parent.window.document).hide();
}
</script>