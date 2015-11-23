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
<title>专线列表-新增专线弹出页面</title>
</head>
<body>
<input type="hidden" id="pageSize" />
<input type="hidden" id="currPage" />
<input type="hidden" id="totalCount" />
<input type="hidden" id="selectData" />
<div class="pop black1" id="popSpacialPage" style="width:900px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">新增专线</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopSpacialPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10">
       <div class="fl mr4 line26">城市：</div>
       <select class="p3 fl" id="provinceCode1" onchange="queryAreaCity()" name="provinceCode">
         		<option value="请选择">请选择</option>
         		<s:iterator value="proSysAreas" var="area" >
           			<option value="${area.arearCode }">${area.areaName }</option>
				</s:iterator>
        </select>
		<select class="p3 fl ml8 mr20" id="areaCode1" name="areaCode" >
         		<option value="请选择">请选择</option>
        </select>
        <div class="mb20">线路：<input type="text" class="r-input w170" id="lineName" name="lineName"/>&nbsp;&nbsp;
        	<input type="button" onclick="loadDetailDate1()" class="btn-blue4" value="查找"/></div>
        <div class="table2-text sh-ttext">       
           <table width="100%" border="0" class="table1" id="tableDivSearch">
                 <tr align="center">
                   <th scope="col" width="5%"><input type="checkbox" name="checkAllBox" id="checkAllBox" /></th>
                   <th scope="col" width="20%">ID</th>
                   <th scope="col" width="20%">线路名称</th>
                   <th scope="col">起点/终点</th>
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
	     <p class="t-c mt20 mb20"><input type="button" class="display-ib btn1 white mr40 f14" value="确&nbsp;定" id="submitButton" onclick="toAddLine();"/></p>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="../js/jquery/ajaxfileupload.js"></script>
<script type="text/javascript" src="../js/common/page2.js"></script>
<script type="text/javascript">
var jsonDate;

$(function(){	
    loadDetailDate();
});

//查找
function loadDetailDate1(){
	$("#currPage").val("1");
	 //清掉勾选的值
    $("#selectData").val("")
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
	var lineName = $("#lineName").val();
	lineName = encodeURI(lineName);
	
	$.ajax({
		url : "../specialLineLine/queryAllLine.action",
		type : "post",
		dataType : "json",
		data : {
			pageSize : $("#pageSize").val(),
			currPage : currPage,
			totalCount : $("#totalCount").val(),
			provinceCode:$("#provinceCode1").val(),
			areaCode:$("#areaCode1").val(),
			lineName:lineName
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
	$("#"+id).append("<tr align='center' class='tr bg1'><td colspan='4'><p class='f13 f-yahei gray4 t-c line26 mt10'><span class='index-nodate mr10'></span><span id='noDate'>暂无数据</span></p></td></tr>");
}
//有数据
function createDate(id,jsonDate)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	for(var i = 0;i < jsonDate.resultData.length;i++)
	{
		var $tr = "";
		if(i % 2 == 0)
		{
			$tr = "<tr align='center' class='tr bg1' businessId =\""+jsonDate.resultData[i].lineBaseId+"\">";
		}
		else
		{
			$tr = "<tr align='center' class='tr bg2' businessId =\""+jsonDate.resultData[i].lineBaseId+"\">";
		}
		var $trs = $tr + "<td class='f-arial'><input type='checkbox' name='checkboxchild' id='child"+i+"' onclick='selectData(this)' value='"+jsonDate.resultData[i].lineBaseId+","+jsonDate.resultData[i].lineName+","+jsonDate.resultData[i].startAndEnd+"'/></td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].lineBaseId+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].lineName+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].startAndEnd+"</td>"+
			"</tr>";
		$("#"+id).append($trs);
	}
	//全部选中
	$("#checkAllBox").click(function(){
		//获得所有选中的值
		var allIds = getPopAllIds();
		//全部选中
		if(this.checked){
			//addData
			var selectData =  $("#selectData").val();
			//检查这次选中的原来有没有选中，如果有，就移除
			var allIdsArr = allIds.split(";");
			var selectDataArr = selectData.split(";");
			
			for(var i=0;i<allIdsArr.length;i++){
				for(var j=0;j<selectDataArr.length;j++){
					if(allIdsArr[i] == selectDataArr[j]){
						delData(allIdsArr[i]);
					}
				}
			}

			//然后再加
			for(var i=0;i<allIdsArr.length;i++){
				addData(allIdsArr[i]);
			}
			
		}else{
			//没有选中,不管原来有没有直接删
			var allIdsArr = allIds.split(";");
			for(var i=0;i<allIdsArr.length;i++){
				delData(allIdsArr[i]);
			}

		}
		//判断是否已经打勾
		$('input[name="checkboxchild"]').attr("checked",this.checked);
	
	});

	//选中某一项
	$('input[name="checkboxchild"]').click(function(){
		var $checkBoxChild = $("input[name='checkboxchild']");
		$("#checkAllBox").attr("checked",$checkBoxChild.length == $("input[name='checkboxchild']:checked").length ? true : false);
	});


	//回显
	var selectData =  $("#selectData").val();
	var selectDataArr = selectData.split(";");
	$('input[name="checkboxchild"]').each(function(){
		for(var i=0;i<selectDataArr.length;i++){
			if(selectDataArr[i] == this.value){
				this.checked=true;
				var $checkBoxChild = $("input[name='checkboxchild']");
				$("#checkAllBox").attr("checked",$checkBoxChild.length == $("input[name='checkboxchild']:checked").length ? true : false);
			}
		}
	});	
}
//关闭增加弹出层页面
function closePopSpacialPage()
{
    $("#popSpacialPage").hide();
    $("#mainBody").hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}

//添加
function toAddLine()
{
	
	addTableTr();
	//设置父页面的列表
	closePopSpacialPage();

	 //清掉勾选的值
    $("#selectData").val("");
}

//增加专线tr，需要去掉重复添加
function addTableTr()
{
	// 如果这里是无数据行，需要删除无数据的行
	$("#noListDateTr").remove();
	var selectData =  $("#selectData").val();
	if(selectData != "")
	{
		var trArr = selectData.split(";");
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

function queryAreaCity(){
	
	var provinceCode = $("#provinceCode1").val().trim();
	
	if(provinceCode == "" || provinceCode == "请选择"){
		$("#areaCode1").children("option").eq(0).nextAll().remove();
		return;
	}

	$.ajax({
		url:'../specialLineLine/queryAreaCity.action?sysArea.arearCode='+provinceCode,
		type:'post',
		cache:false,	
		dataType:"json",	
		success:function(data){	
			
			if(data != null && data != ""){
				$("#areaCode1").children("option").eq(0).nextAll().remove();
			
				for(var i=0;i<data.length;i++){
					$("#areaCode1").append("<option value="+data[i].arearCode+">"+data[i].areaName+"</option>");
				}
				
			}
			
		}
	});
}

//点击select事件
function selectData(obj){
	//alert(obj.value+","+obj.id);
	//alert($(obj).parent().parent().attr("businessId"));
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
	var selectData =  $("#selectData").val();
	if("" == selectData){
		$("#selectData").val(objData);
	}else{
		$("#selectData").val(selectData+";"+objData);
	}
}

//删除数据
function delData(objData){
	
	var selectData =  $("#selectData").val();
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
	}
}
</script>
