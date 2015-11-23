<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拼车管理-乘客线路列表</title>
<%@include file="../resource.jsp" %>

</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;拼车管理&nbsp;>&nbsp;乘客线路列表<span class="blue1 ml25"></span></p></div>
 <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">乘客线路列表</a></li>
                </ul>
            </div>
    <form  action="../passengerLine/getLineList.action" method="post" id="searchForm">
	 <ul class="r-sub-sel black1 mt20 clearfix">       	
       <li class="w30p">
           	<span class="t-r w75 fl">提交时间：</span>
               <span class="r-input fl w32p mr10"><input type="text" name="search.field01" value="${search.field01}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w32p ml10"><input type="text" name="search.field02" value="${search.field02}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
        
        
                     
        <li class="w16p"><span class="t-r w75 fl">起点：</span><s:textfield name="search.field05"   cssClass="r-input w55p gray2"/></li>       
        <li class="w16p"><span class="t-r w75 fl">终点：</span><s:textfield name="search.field06"   cssClass="r-input w55p gray2"/></li>
        <li class="w16p"><span class="t-r w75 fl">用户：</span><s:textfield name="search.field07"  cssClass="r-input w55p gray2"/></li>  
       <!--  
        <li class="w30p"><span class="t-r w75 fl">省份/城市：</span>
            <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="0" headerValue="--选择省份--" name="search.field08" cssClass="fl r-input mr10 w36p" onchange="loadCity(this.value);"/>
        	 <select name="search.field09" id="cityCode" class="fl r-input w36p"><option value="">--选择城市--</option></select>
        </li> 
        -->
        <li class="w16p"><span class="t-r w75 fl">线路类型：</span><s:select name="search.field10" list="#{'':'所有类型',1:'上下班',2:'长途 '}" listKey="key" listValue="value" cssClass="p3 w48p"/></li>
        <li class="w30p"><span class="t-r w75 fl">座位数量：</span><s:textfield name="search.field03"  cssClass="r-input w32p gray2 fl mr10" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');"/><span class="fl">至</span><s:textfield name="search.field04" cssClass="ml10 fl r-input w32p gray2" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');"/></li>
        <li class="w16p"><span class="t-r w75 fl">单程/返程：</span><s:select name="search.field11" list="#{'':'所有',1:'单程',2:'往返 '}" listKey="key" listValue="value" cssClass="fl p3 w57p"/> </li>
        <li class="w33p">
        <span class="t-r w75 fl">联系电话：</span>
        <s:textfield name="search.field12"  cssClass="fl r-input w27p gray2 mr20"/>
        <input type="submit" value="查找" class="btn-blue4" />
        <s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchForm')"/>
        <a class="blue1 ml10" href="javascript:void(0);" onclick="exportPassengerLine();">下载搜索结果</a>
        </li>
  	</ul>  
  	</form> 
  	<div class="table2-text sh-ttext mt10">       
      <table width="100%" border="0" class="table3" id="tableDiv">
            <tr align="center" class="th">
              <th scope="col" width="4%">序号</th>
              <th scope="col" width="12%">提交时间</th>
              <th scope="col" width="7%">用户</th>
              <!--  <th scope="col" width="8%">地区</th>-->
              <th scope="col" width="6%">线路类型</th>
              <th scope="col">起点/终点</th>
              <th scope="col" width="6%">单程往返</th>
              <th scope="col" width="6%">去程时间</th>
              <th scope="col" width="6%">返程时间</th>
              <th scope="col" width="8%">联系电话</th>
              <th scope="col" width="7%">座位</th>
              <th scope="col" width="7%">线路状态</th>
              <th scope="col" width="7%">线路开关</th>
              <th scope="col" width="7%">操作</th>
            </tr>
            
			<s:if test="list==null || list.isEmpty">
					<tr align="center" class="tr bg1">
					<td colspan="13">
						<p	class="f13 f-yahei gray4 t-c line26 mt10">
							<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span>
						</p>
					</td>
				</tr>
			</s:if>
			<s:else>
			   <s:iterator value="list" var="v" status="s">
				<tr align="center" class="tr bg1" pcLineId="${v.pcLineId}">
					<td>${s.count+currentPageIndex}</td>
	            	<td>${v.createOn}</td>
	            	<td>${v.displayId}/${v.nickName}</td>
	            	<td>${v.lineType}</td>
	            	<td>${v.startStationName}<span class="display-ib lineArrow mr5 ml5 vf6"></span>${v.endStationName}</td>
	            	<td>${v.lineSubType}</td>
	            	<td>${v.goTime}</td>
	            	<td>${v.returnTime}</td>            	
	            	<td>${v.telephone}</td>
	            	<td>${v.carSeats}</td>
	            	<td>
	            	   <s:if test="%{#v.lineStatus==1}">已发布</s:if>
	            	   <s:if test="%{#v.lineStatus==2}">已删除</s:if>
	            	   <s:if test="%{#v.lineStatus==3}">已过期</s:if>
	            	</td>
	            	<td>
	            	   <s:if test="%{#v.lineOnOff==0}">已关闭</s:if>
	            	   <s:if test="%{#v.lineOnOff==1}">已开启</s:if>
	            	</td>
	            	<th>
	            	   <s:if test="%{#v.lineOnOff==0}">
	            	      <a href="javascript:void(0);" class="fn blue1 mr10" onclick="openLine('${v.pcLineId}')">开启</a>
	            	   </s:if>
	            	   <s:if test="%{#v.lineOnOff==1}">
	            	      <a href="javascript:void(0);" class="fn blue1 mr10" onclick="closeLine('${v.pcLineId}')">关闭</a>
	            	   </s:if>
	            	</th>
            	</tr>
			   </s:iterator>
			</s:else>
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
            <s:if test="%{page.pageCount!=0 && ((currentPageIndex/pageSize)+1)!=page.pageCount}">
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
</div>
<input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
 <form action="../passengerLine/getLineList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
    <input type="text" name="search.field06" value="${search.field06}"/>
    <input type="text" name="search.field07" value="${search.field07}"/>
    <input type="text" name="search.field08" value="${search.field08}"/>
    <input type="text" name="search.field09" value="${search.field09}"/>
    <input type="text" name="search.field10" value="${search.field10}"/>
    <input type="text" name="search.field11" value="${search.field11}"/>
    <input type="text" name="search.field12" value="${search.field12}"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
  </form> 
 <form action="../passengerLine/exportPassengerLine.action" method="post" style="display: none;" id="exportForm">
    <input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
    <input type="text" name="search.field06" value="${search.field06}"/>
    <input type="text" name="search.field07" value="${search.field07}"/>
    <input type="text" name="search.field08" value="${search.field08}"/>
    <input type="text" name="search.field09" value="${search.field09}"/>
    <input type="text" name="search.field10" value="${search.field10}"/>
    <input type="text" name="search.field11" value="${search.field11}"/>
    <input type="text" name="search.field12" value="${search.field12}"/>
  </form> 
</body>
</html>
<script>

function openLine(pcLineId){
  parent.parent.showPopCommonPage("确定要开启此线路吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../passengerLine/open.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{pcLineId:pcLineId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("线路开启成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#turnPage").submit();
			    }else{
			       parent.parent.showRturnTip("线路开启失败!",false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("线路开启失败!",false);
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

function closeLine(pcLineId){
  parent.parent.showPopCommonPage("确定要关闭此线路吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../passengerLine/close.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{pcLineId:pcLineId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("线路关闭成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#turnPage").submit();
			    }else{
			       parent.parent.showRturnTip("线路关闭失败!",false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("线路关闭失败!",false);
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


function exportPassengerLine(){
  $("#exportForm").submit();
}
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();
    loadOriginalCity();
});



function loadOriginalCity(){
	   var province = "${search.field08}";
	   if(null!=province&&province.length>0&&province!="null"){
	      loadCity(province);
	   }
}
//根据省份加载城市
function loadCity(value){
	$.ajax({
		url:'../sysArea/getProvince.action?proId='+value,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value='0'>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
			loadSelectCity();
		}
	});
}

//加载原来的城市
function loadSelectCity(){
	var city = "${search.field09}";
		if(""!=city){
			var ops =  $("#cityCode option");
			for(var i=0;i<ops.length;i++){
				if(city==ops[i].value){
					ops[i].selected = true;
				}
			}
	}
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
		var pcLineId = $(this).parent().attr("pcLineId");
		//跳转到订单详情
		$("#iframe_right",parent.window.document).attr("src","../passengerLine/getLineDetail.action?pcLineId="+pcLineId+"&currentPageIndex="+$("#myCurrentPageIndex").val()); 
	}
});
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
</script>