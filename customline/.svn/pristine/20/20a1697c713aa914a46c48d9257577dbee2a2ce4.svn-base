<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－创建线路-完成</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <input type="hidden" id="lineType" value='<s:property value="#request.lineType"/>'/>
    <input type="hidden" id="lineBaseId" value='<s:property value="#request.lineBaseId"/>'/>
    <input type="hidden" id="orderPrice" value='<s:property value="#request.orderPrice"/>'/>
    <input type="hidden" id="discountRate" value='<s:property value="#request.discountRate"/>'/>
    <input type="hidden" id="firstOrderStartTime" value='<s:property value="#request.orderStartTimes[0]"/>'/>
    <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布专线</p></div>
    <div class="steps">
    	<ol class="clearfix">
			<li><i>1</i><span class="tsl">填写基本信息</span></li>
			<li><i>2</i><span class="tsl">填写班次</span></li>
			<li class="active"><i>3</i><span class="tsl">创建完成</span></li>
		</ol>		
    </div>
     <s:if test='#request.lineType=="0"'>
       <div class="ml30 gray2 mt10 clearfix">
	    	<p class="line24"><span class="fw f14 fl w76">价格信息：</span>单次价格<em class="green1 fw f14"><s:property value="#request.orderPrice"/></em>元/次
	    	   <s:iterator value="#request.orderStartTimes" id='t'>
	                <a href="javascript:void(0);" class="display-ib btn-blue2 white ml20" onClick="loadData('<s:property value='t'/>');"><s:property value='t'/></a>
	           </s:iterator>
	    	</p>
	   			<div class="table2-text sh-ttext table2-text-noshadow fl w920 mt10">
	                <table width="100%" border="0" class="table4 f12 gray2" id="contentBody">
	           		</table>
	          </div>
   		</div>
     </s:if>
    <div class="mt80 w1015">
    	<p class="t-c">
    	<span class="display-ib systips-ico systips-ico-ok mr5 vf6" style="display:none;"></span>
    	<span class="f24">
    	<s:property value="#request.errorMsg"/></span>
        <s:if test='#request.lineType=="2"'>
          <a href="javascript:void(0)" onclick='tohref("line/recruitLinesManager.action?level=two")' class="blue1 ml20 mr20">返回招募线路列表</a>
        </s:if>
        <s:else>
            <a href="javascript:void(0)" onclick='tohref("line/getLinesList.action?level=two")' class="blue1 ml20 mr20">返回线路列表</a>
        </s:else>
    	<a class="display-ib btn1 white f14" href="line/forwardAddLinePage.action">继续创建</a>
    	</p>   
    </div>
  </body>
</html>
<script type="text/javascript">
var firstOrderStartTime = $("#firstOrderStartTime").val();
var lineType = $("#lineType").val();
var lineBaseId=$("#lineBaseId").val();
var orderPrice = $("#orderPrice").val();
var discountRate = $("#discountRate").val();
$(function(){
   loadData(firstOrderStartTime);
});
//跳到指定的左侧菜单
function tohref(url){
	parent.openTowMenu("../"+url);	
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../"+url;
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url=url;
	}
	window.location.href=$the_url;
}
function loadData(s){
   if(lineType=="0"){
      //仅上下班才加载数据
      var orderStartTime = "";
	   if(s.indexOf("(")!=-1){
	      orderStartTime = s.substring(0,s.indexOf("("));
	   }else{
	      orderStartTime=s;
	   }
	   var url = "line/getClassMonthOrderPrice.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime;
	   $.ajax({
			url:url,		
			cache:false,	
			dataType:"json",
			async : false,
			success:function(data){
				showMonthOrderListData(data,"contentBody");
			}
		});
	}
}

//显示车辆列表
function showMonthOrderListData(jsonData,id)
{
	$("#"+id+" tr").remove(); //移除之前所有的li
	var $listUL = $("#"+id);
	var $li = $("<tr align='center' class='trbg2'>"+
                "<td width='115'>月份</td>"+
                "<td width='256'>"+jsonData[0].month+"</td>"+
                "<td width='256'>"+jsonData[1].month+"</td>"+
                "<td width='256'>"+jsonData[2].month+"</td>" +                 
                "</tr>"+
                "<tr align='center' height='32'>"+
                "<td>工作日(天)</td>"+
                "<td class='green1'>"+jsonData[0].totalDays+"</td>"+
                "<td class='green1'>"+jsonData[1].totalDays+"</td>"+
                "<td class='green1'>"+jsonData[2].totalDays+"</td>"+               
                "</tr>"+
                "<tr align='center' height='32'>"+
                "<td>价格(元)</td>"+
                "<td class='red1'>"+jsonData[0].totalPrice+"</td>"+
                "<td class='red1'>"+jsonData[1].totalPrice+"</td>"+
                "<td class='red1'>"+jsonData[2].totalPrice+"</td>"+                
                "</tr>"+
                "<tr align='center' height='32'>"+
                "<td>折扣</td>"+
                "<td align='center' colspan='3'><em class='red1 fw ml10'>"+discountRate+"</em>折</td>"+                                 
                "</tr>"+
                "<tr align='center' height='32'>"+
                "<td>折后价(元)</td>"+
                "<td class='green1'>"+jsonData[0].discountPrice+"</td>"+
                "<td class='green1'>"+jsonData[1].discountPrice+"</td>"+
                "<td class='green1'>"+jsonData[2].discountPrice+"</td>"+                
                "</tr>"); 
	$listUL.append($li);
}

</script>

