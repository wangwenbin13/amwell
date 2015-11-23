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
<title>线路列表-选择商家弹出页面</title>
</head>
<body>
	<input type="hidden" id="pageSize" />
	<input type="hidden" id="currPage" />
	<input type="hidden" id="totalCount" />
<div class="pop black1" id="popMapPage" style="width:900px;">
    <div class="pop-t">
    	<div class="pop-t-l fl fw white">选择商家</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="loadPage.window.closePopMerchantPage();" class="pop-close fr mt4 mr4"></a></div>
    </div>
    <div class="pop-main p10">
        <input type="hidden" name="search.field05" value="1" />
        <input type="hidden" id="lineProvinceCode" value="${lineCityVo.provinceCode}"/>
        <input type="hidden" id="lineCityCode" value="${lineCityVo.cityCode}"/>
        <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName"	headerKey="" headerValue="--选择省份--" id="provinceCode" name="provinceCode" cssClass="fl r-input mr10" value="lineCityVo.provinceCode" onchange="loadPage.window.loadCity(this.value);"/>
        <select name="areaCode" id="cityCode" class="fl r-input mr10"><option value="">--选择城市--</option></select>
        <div class="mb20">公司名称：<input type="text" name="search.field04" id="field04" value="${search.field04}" class="r-input w170"/>&nbsp;&nbsp;<input type="button" onclick="loadPage.window.loadDetailDate()" class="btn-blue4" value="查找"/></div>
        <div class="table2-text sh-ttext">       
           <table width="100%" border="0" class="table1" id="tableDivSearch">
                 <tr align="center">
                   <th scope="col" width="20%">公司名称</th>
                   <th scope="col" width="20%">联系人</th>
                   <th scope="col" width="20%">联系电话</th>
                   <th scope="col">操作</th>
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

var jsonDate = {
	"pageSize":"1",
	"totalCount":"1",
	"currPage":"1",
	"resultData":[{
		"businessId":"1",//商户id
		"brefName":"111", //公司名称
		"contactsPhone":"13718952632", //联系电话
		"contacts":"zh",  //联系人
	}]
}


var lineBaseId = null;
var lineProvinceCode=null;
var lineCityCode=null;
$(function(){	
    lineBaseId = "<%=request.getParameter("lineBaseId")%>";
    lineProvinceCode=$("#lineProvinceCode",parent.window.document).val();
    lineCityCode=$("#lineCityCode",parent.window.document).val();
    if(lineProvinceCode!=null&&lineProvinceCode!=""&&lineProvinceCode.length>0){
       loadCity(lineProvinceCode);
    }
    loadDetailDate();
});

//根据省份加载城市
function loadCity(value){
  	$.ajax({
		url:'../charteredLine/getProvince.action?proId='+value,
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#cityCode",parent.window.document).empty();
			$("#cityCode",parent.window.document).append("<option value=''>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
			   if(data[i].arearCode==lineCityCode){
			      $("<option value='"+data[i].arearCode+"' selected='selected'>"+data[i].areaName+"</option>").appendTo($("#cityCode",parent.window.document));
			   }else{
			      $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode",parent.window.document));
			   }
			})
		}
	});
}

//调用后台   加载详情数据
function loadDetailDate()
{
	var currPage = $("#currPage",parent.window.document).val();

	if(currPage == "" || typeof(currPage) == "undefined")
	{
		currPage = 1;
	}
	$.ajax({
		url : "../line/getMerchantList.action",
		type : "post",
		dataType : "json",
		data : {
			pageSize : $("#pageSize",parent.window.document).val(),
			currPage : currPage,
			totalCount : $("#totalCount",parent.window.document).val(),
			provinceCode:$("#provinceCode",parent.window.document).val(),
			areaCode:$("#cityCode",parent.window.document).val(),
			brefName:encodeURI(encodeURI($("#field04",parent.window.document).val()))
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
		var $tr = "";
		if(i % 2 == 0)
		{
			$tr = "<tr align='center' class='tr bg1' businessId =\""+jsonDate.resultData[i].businessId+"\">";
		}
		else
		{
			$tr = "<tr align='center' class='tr bg2' businessId =\""+jsonDate.resultData[i].businessId+"\">";
		}
		var $trs = $tr + "<td class='f-arial'>"+jsonDate.resultData[i].brefName+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].contacts+"</td>"+
           	"<td class='f-arial'>"+jsonDate.resultData[i].contactsPhone+"</td>"+
           	"<td><a href='javascript:void(0);' class='fn blue1 mr10' onclick='loadPage.window.sendToMerchant(\""+jsonDate.resultData[i].businessId+"\");'>选择</a></td>"+
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
function sendToMerchant(businessId){
  parent.parent.showPopCommonPage("确定要将线路发送给此商家吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/sendToMerchant.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId,businessId:businessId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("线路发送成功!",true);
			       closePopMerchantPage();
			       window.location.href="../line/getAllLines.action";
			    }else{
			       parent.parent.showRturnTip("线路发送失败!",false);
			    }
				
			},
			error:function(){
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
