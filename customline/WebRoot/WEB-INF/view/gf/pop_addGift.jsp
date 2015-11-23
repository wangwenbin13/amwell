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
<title>优惠劵配置-新增礼品弹出页面</title>
</head>
<body>
<input type="hidden" id="pageSize" />
<input type="hidden" id="currPage" />
<input type="hidden" id="totalCount" />
<input type="hidden" id="selectData" />
<input type="hidden" id="checkedGiftId" value="${checkedGiftId }"/>
<div class="pop black1" id="popGiftPage" style="width:900px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">请选择礼品</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closepopGiftPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10">
        <div class="table2-text sh-ttext">       
           <table width="100%" border="0" class="table1" id="tableDivSearch">
                 <tr align="center">
                   <th scope="col" width="5%"></th>
                   <th scope="col" width="20%">礼品ID</th>
                   <th scope="col" width="20%">礼品名称</th>
                   <th scope="col" width="10%">类型</th>
                   <th scope="col" width="10%">面值</th>
                   <th scope="col">约束条件</th>
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
	     <p class="t-c mt20 mb20"><input type="button" class="display-ib btn1 white mr40 f14" value="确&nbsp;定" id="submitButton" onclick="toAddGift();"/></p>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="../js/common/page2.js"></script>
<script type="text/javascript">
var jsonDate = {
		"pageSize":"1",
		"totalCount":"1",
		"currPage":"1",
		"resultData":[{
			"giftId":"10001",//id
			"giftName":"新用户10元礼包", //礼品名称
			"giftTypeStr":"通用", //类型
			"giftValueStr":"10", //面值
			"giftConStr":"无", //约束条件
		}]
}

$(function(){	
    loadDetailDate();
});

//查找
function loadDetailDate1(){
	$("#currPage").val("1");
	 //清掉勾选的值
    $("#selectData").val("");
	loadDetailDate();
}

//调用后台   加载详情数据
function loadDetailDate()
{
	var currPage = $("#currPage").val();

	if(currPage == "" || typeof(currPage) == "undefined")
	{
		currPage = 1;
	}
	
	
	$.ajax({
		url : "../coupon/selectGift.action",
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
			createNoDate("tableDivSearch");
			//无数据时重置页码相关数据
			resetPage();
		},
		success : function(data) {
			
			jsonDate = data;
			//无数据时提示暂无数据
			if(jsonDate.resultData.length == 0)
			{
				createNoDate("tableDivSearch");
				//无数据时重置页码相关数据
				resetPage();
			}
			else
			{
				pageSize = jsonDate.pageSize;//每页显示的条数
				totalCount = jsonDate.totalCount;//总条数
				currPage = jsonDate.currPage;//当前页码
				$("#pageSize").val(pageSize);
				$("#currPage").val(currPage);
				$("#totalCount").val(totalCount);
				createDate("tableDivSearch",jsonDate);
				checkShowPage(currPage);
			}
		}
	});
}

//无数据时重置页码相关数据
function resetPage()
{
	pageSize = 0;//每页显示的条数
	totalCount = 0;//总条数
	currPage = 1;//当前页码
	$("#pageSize").val(pageSize);
	$("#currPage").val(currPage);
	$("#totalCount").val(totalCount);
	checkShowPage(currPage);
}
//无数据
function createNoDate(id)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	$("#"+id).append("<tr align='center' class='tr bg1'><td colspan='6'><p class='f13 f-yahei gray4 t-c line26 mt10'><span class='index-nodate mr10'></span><span id='noDate'>暂无数据</span></p></td></tr>");
}
//有数据
function createDate(id,jsonDate)
{
	var checkedGiftId = $("#checkedGiftId").val();
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	for(var i = 0;i < jsonDate.resultData.length;i++)
	{
		var $tr = "";
		if(i % 2 == 0)
		{
			$tr = "<tr align='center' class='tr bg1' giftId =\""+jsonDate.resultData[i].giftId+"\">";
		}
		else
		{
			$tr = "<tr align='center' class='tr bg2' giftId =\""+jsonDate.resultData[i].giftId+"\">";
		}
		if(checkedGiftId == jsonDate.resultData[i].giftId){
			$tr2 = "<td class='f-arial'><input type='radio' name='checkboxchild' id='child"+i+"' onclick='selectData(this)' value='"+jsonDate.resultData[i].giftId+","+jsonDate.resultData[i].giftName+"' checked='checked'/></td>"
		}else{
			$tr2 = "<td class='f-arial'><input type='radio' name='checkboxchild' id='child"+i+"' onclick='selectData(this)' value='"+jsonDate.resultData[i].giftId+","+jsonDate.resultData[i].giftName+"'/></td>"
		}
		var $trs = $tr +$tr2 +
           	"<td class='f-arial'>"+jsonDate.resultData[i].giftId+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].giftName+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].giftTypeStr+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].giftValueStr+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].giftConStr+"</td>"+
			"</tr>";
		$("#"+id).append($trs);
	}
}
//关闭增加弹出层页面
function closepopGiftPage()
{
    $("#popGiftPage").hide();
    $("#mainBody").hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}

//添加
function toAddGift()
{
	
	addTableTr();
	//设置父页面的列表
	closepopGiftPage();

	 //清掉勾选的值
    $("#selectData").val("");
}

//增加专线tr，需要去掉重复添加
function addTableTr()
{	var trArr="" ;
	var selectData =  $("#selectData").val();
// 	alert(selectData);
	if(selectData != "")
	{
		trArr = selectData.split(";");
		var trLength = $("#giftDiv span").length;
		$("#giftDiv span").remove();
		var $trs = "<span class='ml20 fl w40p' giftId='"+trArr[0].split(",")[0]+"'>礼品ID：<em class='mr10'>"+trArr[0].split(",")[0]+"</em><em>"+trArr[0].split(",")[1]+"</em></span>";
		$("#giftDiv").append($trs);
		//设隐藏域
		$("#giftId").val(trArr[0].split(",")[0]);
	}
	var checkedGiftId = $("#checkedGiftId").val();
	var childLength = $("#giftDiv span").length;
	if(checkedGiftId != ""){
		trArr="1";
	}
	if(childLength > 0)
	{
		$("#giftLi").show();
		$("#giftCountsSpan").show();
		$("#giftCounts").text(trArr.length);
	}
}
//------------------------- 列表选中和未选中start------------------------------

//获取选中的所有id
function getCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
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

//获取所有id
function getPopAllIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			loginIds = loginIds + checkboxs[i].value + ";";
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}
//------------------------- 列表选中和未选中end------------------------------


//点击select事件
function selectData(obj){
	//alert(obj.value+","+obj.id);
	//alert($(obj).parent().parent().attr("giftId"));
	//alert($(obj).is(":checked")==true);
	
	var selectData =  $("#selectData").val();
	//选中
	if($(obj).is(":checked")==true){
		//var selectData =  $("#selectData").val();
		/**
		if("" == selectData){
			$("#selectData").val(obj.value);
		}else{
			$("#selectData").val(selectData+";"+obj.value);
		}
		*/
		addData(obj.value);
	}else{
		//没有选中
		delData(obj.value);
		/**
		if("" != selectData){
			var dataAtrr = selectData.split(";");
			var newDataAtrr = "";
			for(var i=0;i<dataAtrr.length;i++){
				if(obj.value == dataAtrr[i]){
					
				}else{
					newDataAtrr += dataAtrr[i]+";";
				}
			}
			newDataAtrr = newDataAtrr.substring(0,newDataAtrr.length-1)
			$("#selectData").val(newDataAtrr);
		}
		*/
	}

}


//添加，删除数据
function addData(objData){
	$("#selectData").val("");
	$("#selectData").val(objData);
}

//删除数据
function delData(objData){
	/*var selectData =  $("#selectData").val();
	//没有选中
	if("" != selectData){
		var dataAtrr = selectData.split(";");
		var newDataAtrr = "";
		for(var i=0;i<dataAtrr.length;i++){
			if(objData == dataAtrr[i]){
				
			}else{
				newDataAtrr += dataAtrr[i]+";";
			}
		}
		newDataAtrr = newDataAtrr.substring(0,newDataAtrr.length-1)
		$("#selectData").val(newDataAtrr);
	}*/
	
	$("#selectData").val("");
}
</script>
