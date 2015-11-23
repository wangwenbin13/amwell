<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html >
  <head>
    <base href="<%=basePath%>">
	<title>线路管理-站点位置</title>
	<jsp:include page="../resource.jsp"/>
  </head>
  
  <body>
  <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;线路管理&nbsp;>&nbsp;站点位置</p></div>
  <div class="mt10 ml20 mr20 black1">         
       <div class="table-title">
         <ul>
	      	<li class="on"><a href="javascript:void(0)" class="f14 blue2">站点位置</a></li>
	      </ul>
       </div>
	  	<div class="table2-text sh-ttext mt20">
		  	<table class="table1" width="100%" border="0">
		  		<tr align="center">
                      <th scope="col" width="4%">序号</th>
		  			  <th scope="col">站点名称</th>
		  			  <th scope="col" width="15%%">上报时间</th>
		  			  <th scope="col" width="12%">经度</th>
		  			  <th scope="col" width="12%">维度</th>
		  			  <th scope="col" width="12%">速度</th>
		  			  <th scope="col" width="15%">系统时间</th>
		  			  <th scope="col" width="15%">操作</th>
		  		</tr>
		  		<s:if test="null==stationTrackList || stationTrackList.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="8"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="stationTrackList" var="stationTrack" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
						<th>${s.index+1}</th>
			  			<td align="center">${stationTrack.stationName}</td>
			  			<td class="f-arial">${stationTrack.time}</td>
			  			<td class="f-arial">${stationTrack.lon}</td>
			  			<td class="f-arial">${stationTrack.lat}</td>
			  			<td class="f-arial">${stationTrack.speed}</td>
			  			<td class="f-arial">${stationTrack.systime}</td>
			  			<td class="f-arial"><a href="javascript:void(0);" onclick="select('${stationTrack.stationInfoId}','${stationTrack.lon}','${stationTrack.lat}')">选择</a></td>
                    	</tr>
					</s:iterator>
		  	</table>
	  	</div>
  	</div>
  </body>
</html>
<script type="text/javascript">
function select(stationInfoId,lon,lat){
  parent.parent.showPopCommonPage("此操作将会修改站点的经纬度，确定要执行吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:"lineTrack/updateStationTrack.action?random="+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{stationInfoId:stationInfoId,lon:lon,lat:lat},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("执行成功!",true);
			    }else{
			       parent.parent.showRturnTip("执行失败!",false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("执行失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
}
</script>
