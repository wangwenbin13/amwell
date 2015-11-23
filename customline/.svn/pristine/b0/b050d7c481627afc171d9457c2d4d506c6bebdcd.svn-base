<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-招募线路-用户申请线路</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
   <s:form action="../line/getUserLines.action" method="post" id="searchForm" theme="simple">
   	 <ul class="r-sub-sel black1 mt20 clearfix">       	
        <li class="w33p"><span class="t-r w65 fl">时间：</span>
        	<span class="fl r-input w37p t-l"><input type="text" id="startTime" name="search.field01" value="${search.field01}" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate75 gray2 Wdate" readonly="readonly" value="" /></span>
        	<span class="fl ml4 mr4">至</span>
        	<span class="fl r-input w37p t-l"><input type="text" id="endTime" name="search.field02" value="${search.field02}" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\');}'})" class="Wdate75 gray2 Wdate" readonly="readonly" value="" /></span>
        </li>
        <li class="w14p"><span class="t-r w65 fl">申请人：</span><input type="text" name="search.field03" value="${search.field03}" class="r-input w55p gray2"/></li>
        <li class="w14p"><span class="t-r w65 fl">联系电话：</span><input type="text" name="search.field04" value="${search.field04}" class="r-input w55p gray2"/></li>
        <li class="w14p"><span class="t-r w65 fl">线路类型：</span>
        <s:select name="search.field05" list="#{'':'所有类型',0:'上下班',1:'自由行 '}" listKey="key" listValue="value" cssClass="p3 w55p"/>
        </li>
        <!-- 
         <li class="w30p"><span class="t-r w65 fl">位置：</span><input type="text" name="search.field06" value="${search.field06}" class="r-input w35p gray2"/><span class="ml4 mr4">至</span><input type="text" name="search.field07" value="${search.field07}" class="r-input w35p gray2"/></li>
         -->
         <li class="w14p"><span class="t-r w65 fl">发车时间：</span>
        	<span class="fl r-input w55p t-l"><input type="text" name="search.field08" value="${search.field08}" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})" class="Wdate75 gray2 Wdate" readonly="readonly" value="" /></span>
        </li>
        <li class="w33p"><span class="t-r w65 fl">省份城市：</span>
	        <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" name="search.field11" cssClass="fl r-input mr10 w39p" onchange="loadCity(this.value);"/>
	        <s:select list="#{'':'--选择城市--'}" name="search.field12" id="cityCode" cssClass="fl r-input w39p"></s:select>
	        <input type="hidden" id="theSelectedCityCode" value="${search.field12}"/>
        </li>
        <li class="w31p"><span class="t-r w65 fl">回访状态：</span>
            <s:select name="search.field09" list="#{'':'全部',0:'未回访',1:'已回访 '}" listKey="key" listValue="value" cssClass="p3 w26p"/>
            <input type="submit" class="btn-blue4 ml20" value="查找" /><s:reset value="重置" cssClass="btn-blue4 ml10" theme="simple" onclick="clearFormValue('searchForm')"/>
        </li>
  	</ul>   
  </s:form>    
      <div class="table-title mt10">            
           <input type="button" class="btn fr mr4 mt4" value="导出" onclick="exportUserLines();"/>
       </div>
      <div class="table2-text sh-ttext">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="4%">序号</th>
                      <th scope="col" width="11%">申请时间</th>
                      <th scope="col" width="5%">申请人</th>
                      <th scope="col" width="8%">联系电话</th>
                      <th scope="col" width="5%">城市名称</th>
                      <th scope="col" width="5%">线路类型</th>
                      <th scope="col" width="9%">上车点</th>
                      <th scope="col" width="9%">途经点</th>
                      <th scope="col" width="9%">下车点</th>
                      <th scope="col" width="5%">发车时间</th>
                      <th scope="col" width="9%">备注</th>
                      <th scope="col" width="5%">报名人数</th>

                      <th scope="col" width="5%">回访状态</th>
                      <th scope="col">操作</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="13"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="list" var="userLine" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" applicationId="${userLine.applicationId}" backstatu="${userLine.callbackStatus}"> <!-- backstatu:0代表未回访；1代表已回访 -->
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" applicationId="${userLine.applicationId}" backstatu="${userLine.callbackStatus}"> <!-- backstatu:0代表未回访；1代表已回访 -->
						</s:if>
						<th>${s.index+1}</th>
						<td class="f-arial">${userLine.applicationTime}</td>
                    	<td class="f-arial">${userLine.nickName}</td>
                    	<td class="f-arial">${userLine.telephone}</td>
                    	<td class="f-arial">${userLine.cityName}</td>
                    	<td class="f-arial"> 
                    	   <s:if test="%{#userLine.lineType==0}">上下班</s:if>
                    	   <s:if test="%{#userLine.lineType==1}">自由行</s:if>
                    	</td>                  	
                    	<td class="f-arial">${userLine.startAddress}</td>
                    	<td class="f-arial">${userLine.stationNames}</td>
                    	<td class="f-arial">${userLine.endAddress}</td>
                    	<td class="f-arial">${userLine.startTime}</td>
                    	<td class="f-arial">${userLine.remark}</td>
                    	<td class="f-arial">${userLine.applyCount}人</td>

                    	<td class="f-arial">
                    	  	<s:if test="%{#userLine.callbackStatus==0}"><a class="fn blue1" href="javascript:void(0);">未回访</a></s:if>
                    	    <s:if test="%{#userLine.callbackStatus==1}"><a class="fn blue1" href="javascript:void(0);">已回访</a></s:if>
                    	</td> 
                    	<th>
                    		<a class="fn blue1" href="javascript:void(0);" onclick="goUserLinesPublish('${userLine.applicationId}','${userLine.auditStatus}')">
                    		    <s:property value="%{auditStatus==0 | auditStatus==2?'开启':'关闭'}"/>
                    	    </a>
                    	    <a class="fn blue1" style="margin-left:10px;" href="javascript:void(0);" onclick="transToPingTaiZhaoMu('${userLine.applicationId}')">放入平台招募</a>
                    	</th>
						</tr>
					</s:iterator>
                  </table>
                   
          	</div>
          	<!--Start page-->
                    <div class="page t-c mt20  fl widthfull" id="pageDiv">
	                   <s:if test="%{currentPageIndex!=0}">
	                  		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
                     		<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
	                   </s:if>
                       <ul id="pageNumDiv">
                       		<s:iterator value="page.pageList">
							<s:if test="field02==1">
								<b><span class="current"><s:property value="field01" />
								</span>
								</b>
							</s:if>
							<s:else>
								<li>
									<a href="javascript:void(0);" onclick="toPage(${field03});"><s:property value="field01" /></a>
								</li>
							</s:else>
						</s:iterator>
                       </ul>
                       <s:if test="%{page.pageCount!=0 && ((currentPageIndex/page.pageSize)+1)!=page.pageCount}">
		             		<a href="javascript:void(0);" onclick="toPage(${page.nextIndex});">下一页</a>
		             		<a href="javascript:void(0);" onclick="toPage(${page.lastIndex});">末页</a>
		             </s:if>
		             <s:if test="%{page.pageCount!=1 && page.pageCount!=0}">
		             		<span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onblur="toJudgePage(this.value);"/>页</span>
		             </s:if>
		             <s:if test="%{page.pageCount!=0}">
		                 <span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页]</span>
		             </s:if>
                 </div>
                  <!--End page-->
          </div>
          <input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
<form action="../line/getUserLines.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="search.field01" value="${search.field01}"/>
  <input type="text" name="search.field02" value="${search.field02}"/>
  <input type="text" name="search.field03" value="${search.field03}"/>
  <input type="text" name="search.field04" value="${search.field04}"/>
  <input type="text" name="search.field05" value="${search.field05}"/>
  <input type="text" name="search.field06" value="${search.field06}"/>
  <input type="text" name="search.field07" value="${search.field07}"/>
  <input type="text" name="search.field08" value="${search.field08}"/>
  <input type="text" name="search.field09" value="${search.field09}"/>
  <input type="text" name="search.field11" value="${search.field11}"/>
  <input type="text" name="search.field12" value="${search.field12}"/>
  <input type="text" name="currentPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
</form>       

<form action="../line/exportUserLines.action" method="post" style="display: none;" id="exportUserLinesForm">
  <input type="text" name="search.field01" value="${search.field01}"/>
  <input type="text" name="search.field02" value="${search.field02}"/>
  <input type="text" name="search.field03" value="${search.field03}"/>
  <input type="text" name="search.field04" value="${search.field04}"/>
  <input type="text" name="search.field05" value="${search.field05}"/>
  <input type="text" name="search.field06" value="${search.field06}"/>
  <input type="text" name="search.field07" value="${search.field07}"/>
  <input type="text" name="search.field08" value="${search.field08}"/>
  <input type="text" name="search.field09" value="${search.field09}"/>
  <input type="text" name="search.field11" value="${search.field11}"/>
  <input type="text" name="search.field12" value="${search.field12}"/>
  <input type="text" name="currentPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
</form> 

</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();

	//通过默认省份加载城市信息
	loadOriginalCity();
});

function transToPingTaiZhaoMu(applicationId){
	parent.document.location.href="../line/forwardAddLinePageFromUserApplication.action?applicationId="+applicationId;
}

function clearInputDefaultVal()
{
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				
			} 
		})  
	});
}

//乘客详情
$("#tableDiv tr td").click(function(){
	//如果是没有数据就不调用后面的方法
	if($("#noDate").html()!= undefined){	
		return false;
	}
	//获取选中文字，如果选中文字则不响应点击事件
	//ie8写法
	if(typeof(document.selection) != "undefined")
	{
		var selecter = document.selection.createRange().text;
	}
	else if(typeof(window.getSelection()) != "undefined")
	{
		var selecter = window.getSelection();
	}
	else
	{
		var selecter = "";
	}
	if(selecter != "")
	{
	 	return false;
	}
	else
	{
        var applicationId = $(this).parent().attr("applicationId");
        //回访状态
        var backstatu = $(this).parent().attr("backstatu");
		//跳转到用户线路详情
		$("#iframe_right", parent.parent.window.document).attr("src","../line/getUserLineDetailManager.action?theTab=1&applicationId="+applicationId+"&random="+Math.floor(Math.random()*10000+1)+"&backstatu="+backstatu+"&currentPageIndex="+$("#myCurrentPageIndex").val());
	}
});
//用户申请线路发布
function goUserLinesPublish(applicationId,auditStatus){

	var str='';
	if(auditStatus=="0" || auditStatus=="2"){
		str='确认开启线路？';
	}
	else if(auditStatus=="1"){
		str='确认关闭线路？';
	}

		parent.parent.showPopCommonPage(str);
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
		//动态添加方法
		$("#commonSure",parent.parent.window.document).click(function(){
			parent.parent.closePopCommonPage();
			
			$.ajax({
		           url:'../line/changeUserLineStatus.action?applicationId='+applicationId+'&auditStatus='+auditStatus+'&temp='+new Date(),
		           dataType:'text',
		           cache:false,//不从浏览器缓存中取
		           async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
		           success:function(data){
	              	  if(data=='ok'){
		                  //刷新本页
		            	 
		            	 //刷新当前页信息
			              $("#turnPage").submit(); 
		              }
		              else{
		            	  parent.parent.showRturnTip(data,false);
		              }
		           }
				});

			//解绑定，防止多次执行click事件
			$("#commonSure",parent.parent.window.document).unbind('click');
		});
	
	
}
//------------------------- 列表选中和未选中start------------------------------
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

//获取count值
function getCount()
{
	var checkboxs = $("input[name='checkboxchild']");
	var counts = 0;
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				counts += parseInt($("#"+checkboxs[i].id).attr("count"));
			}
		}
	}
	return counts;
}

//获取未选中的所有id
function getNotCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (!checkboxs[i].checked)
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
        if(currPage == "")
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
function checkGoPageNum(pageNum)
{
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(parseInt(pageNum) > parseInt(totalPage))
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}
function exportUserLines(){
	$("#exportUserLinesForm").submit();
}
//------------------------- 列表选中和未选中end------------------------------


function loadOriginalCity(){
	   var province = "${search.field11}";
	   if(null!=province&&province.length>0&&province!="null"){
	      loadCity(province);
	   }
}

//根据省份加载城市
function loadCity(value){
	var $theSelectedCityCode=$("#theSelectedCityCode").val();
	if(null!=$theSelectedCityCode&&""!=$theSelectedCityCode){
		$.ajax({
			url:'../merchantAction/getProvince.action?proId='+value,		
			cache:false,	
			dataType:"json",
			async : false,
			success:function(data){
				$("#cityCode").empty();
				$(data).each(function(i){ //遍历结果数组
					if($theSelectedCityCode==data[i].areaName){
						$("<option value='"+data[i].areaName+"' selected='true'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
					}
					else{
						$("<option value='"+data[i].areaName+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
					}
				})
			}
		});
	}
	else{
		$.ajax({
			url:'../merchantAction/getProvince.action?proId='+value,		
			cache:false,	
			dataType:"json",
			async : false,
			success:function(data){
				$("#cityCode").empty();
				$(data).each(function(i){ //遍历结果数组
				   $("<option value='"+data[i].areaName+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
				})
			}
		});
	}
}
</script>