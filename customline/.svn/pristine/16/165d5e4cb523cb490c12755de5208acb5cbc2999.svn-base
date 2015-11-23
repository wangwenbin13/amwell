<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html  >
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-IOS版本列表</title>
<jsp:include page="../resource.jsp" />

  </head>
<script >
   function	cut(id){
		var count = $("#"+id).attr("title");
		if(count!=""){
			if(count.length>20){
				var sub = count.substring(0,20);
				sub+="...";
			}else{
				sub=count;
			}
		}
		$("#"+id).text(sub);
	}
</script>
  
  <body>
   	<div class="r-sub-nav-out">
		<p class="r-sub-nav gray2">
			当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;IOS相关设置
		</p>
	</div>
	<div class="mb20 mt10 ml20 mr20 black1">
		<div class="table-title">
			<ul>
				<li><a href="javascript:void(0)" class="f14 blue2" onclick="appSettion();">APP相关设置</a></li>
				<li><a href="javascript:void(0)" class="f14 blue2" onclick="appVersion();">APP版本设置</a></li>
				<li><a href="javascript:void(0)" class="f14 blue2" onclick="appVsnList();">APP版本设置</a></li>
				<li class="on"><a href="javascript:void(0)" class="f14 blue2" >IOS版本列表</a></li>
			</ul>
		</div>
		
		<div class="table2-text sh-ttext mt20">
		     <div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table1" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="5%">版本号</th>
                      <th scope="col" width="20%">apk存放文件服务器路径</th>
                      <th scope="col" width="20%">版本发布时间</th>
                      <th scope="col" width="5%">版本编码</th>
                      <th scope="col" width="5%">强制更新</th>
                      <th scope="col" width="10%">客户端</th>
                      <th scope="col" width="">更新内容 </th>
                      <th scope="col" width="5">操作 </th>
                    </tr>
                 <s:iterator value="list" var="vo" status="s">
                    <tr class="bg1 tr" align="center" appid=${iosId }>
                    	<td>${s.index+1}</td>
                    	<td class="f-arial">${vo.version }</td>
                    	<td class="f-arial">${vo.url }</td>
                    	<td class="f-arial">${vo.vtime }</td>
                    	<td class="f-arial">${vo.flag }</td>
                    	<s:if test="%{#vo.ismust==0}"><td class="f-arial">否</td></s:if>
                    	<s:if test="%{#vo.ismust==1}"><td class="f-arial">是</td></s:if>
                    	<td class="f-arial">
                    		<s:if test="%{#vo.appType==1}"><em>乘客端</em></s:if>
                    		<s:if test="%{#vo.appType==2}"><em>司机端</em></s:if>
                    	</td>
                    	<td class="f-arial"><span id="info${s.index }" title="${vo.info }">
                    	  <script type="text/javascript">cut("info"+${s.index})</script>
                    	</span></td>
                    	<td class="f-arial"><a href="javascript:void(0);" class="fn blue1 mr10" onclick="deleteVsn('${vo.iosId }')">删除</a></td>
                    </tr>	
                    	
                    	
                    </s:iterator>
                                    
                  </table>
                 	 <!--Start page-->
					<s:if test="%{page.pageCount != 0}"><jsp:include page="../page.jsp"></jsp:include></s:if>
			    	<!--End page-->
          	</div>
          	
          	
		</div>
		
		
		
		
		
	</div>	
   
   
  </body>
</html>
<script type="text/javascript" >
//页面跳转(appSettion)	
	function appSettion(){
		$("#iframe_right",parent.window.document).attr("src","../appAction/appSetting.action");
	}
	function appVersion(){
		$("#iframe_right",parent.window.document).attr("src","../appAction/appVersion.action");
	}
	function appVsnList(){
		$("#iframe_right",parent.window.document).attr("src","../appAction/appVsnList.action");
	}
	
	
// 	$(function(){
// 		var text = $("#info"+id).attr("title");
// 		alert(text);
// 		var sub;
// 		if(text.length>30){
// 			sub=text.substring(0,30);
// 			sub+="...";
// 		}else{
// 			sub=text;
// 		}
// 		$("#info"+id).text(sub);
// 	})
	
	
	function deleteVsn(appId){
	parent.parent.showPopCommonPage("是否删除版本");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
   parent.parent.closePopCommonPage();
		$.ajax({
			url:"../appAction/deleteIosVsn.action",
			data:{"appId":appId},
			dataType: "text",
			type:'post',
			success:function(data){
				if(data=="success"){
					parent.parent.showRturnTip("删除成功！", true);
					$("#iframe_right",parent.window.document).attr("src","../appAction/iosVsnList.action");
				}else{
					parent.parent.showRturnTip("删除失败！", false);
				}
			},
			error:function(){
					parent.parent.showRturnTip("删除错误！", false);
			}
		})
	 //解绑定，防止多次执行click事件
 	  $("#commonSure",parent.parent.window.document).unbind('click');
 	 });	

}

</script>
