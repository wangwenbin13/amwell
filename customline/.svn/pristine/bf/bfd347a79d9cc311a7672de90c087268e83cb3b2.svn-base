<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html >
  <head>
  	<title>运营平台－站点位置</title> 
    <base href="<%=basePath%>" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
	<script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jquery/chosen.jquery.min.js"></script>
	<link href="<%=basePath %>js/jquery/chosen.css" type="text/css" rel="stylesheet">
  </head>
  
  <body>
  <div id="mainBody" class="mainBody"></div>
  <div id="showPage" class="showPage"></div>
  <div id="showPagePic" class="showPage"></div>
  <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;线路管理&nbsp;>&nbsp;站点位置</p></div>
  <div class="mt10 ml20 mr20 black1">         
       <div class="table-title">
         <ul>
	      	<li class="on"><a href="javascript:void(0)" class="f14 blue2">站点位置</a></li>
	      </ul>
       </div>
	    <s:form id="searchForm" action="../line/getAllLines.action" method="post" theme="simple" > 
	    	<ul class="r-sub-sel black1 mt10 clearfix">
		       	<li class="w30p">线路名称：
		       		<s:select  name="lineBaseId" list="lineList" listKey="lineBaseId" listValue="lineName" cssClass="p3 w75p chzn-select" id="selectData"/>
		       	</li>
		       	<li class="w20p" style="display:none;">设备号：<input type="text" class="r-input w75p" id="deviceNo"/></li>
		       	<li class="w20p" style="display:none;"><span class="fl">采集日期：</span><span class="r-input display-ib"><input type="text" id="collectDate" name="collectDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd'})" readonly="readonly" class="Wdate75 gray2 Wdate" /></span></li>
            </ul>
	  	</s:form>
	  	<div class="table2-text sh-ttext">
		  	<table class="table1" width="100%" border="0" id="tableDiv">
		  		<tr align="center">
		  			<th scope="col" width="50%">区间名称</th>
		  			<th scope="col">操作</th>
		  		</tr>
		  		<tr align="center" class="bg1 hide" id="noDate">
   					<td colspan="2"><p class="f13 f-yahei gray4 t-c line26 mt10">
   					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
   				</tr>
		  	</table>
	  	</div>
	  	<p class="mt20 t-c" style="display:none;"><a onclick="getAllLineTrack()" href="javascript:void(0)" class="display-ib btn1 white">下一步</a></p>
  	</div>
  </body>
</html>
<script type="text/javascript">
   //站点ID
    var stationInfoId="";
    //开始时间数组
	//var beginTimes="";
	//结束时间数组
	//var endTimes="";
	var jsonDate=null;
	//下一步跳转页面
  	function getAllLineTrack(){
  		//设备号
		var deviceNo = $("#deviceNo").val();
		 //所有的开始时间
		$("[name='updateBy']").each(function(){
			beginTimes += $(this).val() + ",";
		});
		//所有的结束时间
		$("[name='updateOn']").each(function(){
			endTimes += $(this).val() + ",";
		});
		var lineBaseId = $("#selectData").find("option:selected").val();
		var collectDate = $("#collectDate").val();
  		var url = "../lineTrack/getAllLineTrack.action?lineBaseId="+lineBaseId+"&stationInfoId="+stationInfoId+"&beginTime="+beginTimes+"&endTime="+endTimes+"&deviceNo="+deviceNo+"&collectDate="+collectDate;
  		$("#iframe_right",parent.window.document).attr("src",url); 
  		 
  	}
  	//请求后台数据
 	 function loadDate(){
 		var lineBaseId = $("#selectData").find("option:selected").val();
		var url = "lineTrack/getStationList.action?lineBaseId="+lineBaseId;
	  	$.ajax({
			url:url,		
			cache:false,	
			dataType:"json",
			async : false,
			success:function(data){
				jsonDate = data;
				if(jsonDate.length == 0)
				{
					$("#tableDiv tr").eq(0).nextAll().remove(); //删除之前的tr
					$("#noDate").show();
				}
				else{
					$("#noDate").hide();
					$("#tableDiv tr").eq(0).nextAll().remove(); //删除之前的tr
					showDate(jsonDate,"tableDiv");
				}
			},error:function(){
				$("#tableDiv tr").eq(0).nextAll().remove(); //删除之前的tr
				$("#noDate").show();
			}
		});
 	 }
 	 $(function(){
 		loadDate();
 		//下拉框改变事件
		$(".chzn-select").change(function(){
			stationInfoId = "";
			beginTimes = "";
			endTimes = "";
			loadDate();
		});
		$(".chzn-select").chosen();
 	 });

  	//展示数据
  	function showDate(jsonDate,id)
 	{
 		for(var i = 0;i < jsonDate.length;i++)
 		{
 			var $tr = "<tr class='tr bg1'>"+
  			"<td style=\"text-align:left; padding-left:10px;\" stationInfoId='"+jsonDate[i].stationInfoId+"'>"+jsonDate[i].startStation.stationName+"&nbsp;&nbsp;到&nbsp;&nbsp;"+jsonDate[i].endStation.stationName;
            if(jsonDate[i].isSetRagion=="1"){
            	$tr = $tr+"<span class=\"fr\">已添加"+(jsonDate[i].totalRagionNum)+"中间点"+"</span>"
            }else{
            	$tr=$tr+"<span class=\"fr red1\">未添加中间点"+"</span>"
            }
            $tr = $tr+"</td>"+
  			"<td align='center'><a href='javascript:void(0);' class='btn' onclick=\"goAddMapPage('"+jsonDate[i].startStation.stationInfoId+"','"+jsonDate[i].endStation.stationInfoId+"','"+jsonDate[i].lineBaseId+"')\">编辑</a></td>"+
  			"</tr>";
			$("#"+id).append($tr);
			stationInfoId += jsonDate[i].stationInfoId + ",";
 		} 
 	}


  //标注站点 地图查询页面
  	function goAddMapPage(startStationId,endStationId,lineBaseId)
  	{
  		$("#topHide", parent.window.document).show();
  	    $("#leftHide", parent.window.document).show();
  	    $("#showPage").load("lineTrack/addStationMap.action?random="+Math.floor(Math.random()*10000+1),{startStationId:startStationId,endStationId:endStationId,lineBaseId:lineBaseId}); 
  	    $("#mainBody").show();
  	}
  	
  </script>
<!-- 操作地图的js --> 
<script src="http://api.map.baidu.com/api?v=1.3" type="text/javascript"></script>