<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-线路列表-操作记录</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out">
	<p class="r-sub-nav gray2">
		<span>当前位置：首页&nbsp;>&nbsp;线路管理&nbsp;>&nbsp;线路列表</span> 
		<span><a class="gray2" href="javascript:history.go(-1);">&nbsp;&nbsp;返回</a></span>
	</p>
</div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">操作记录</a></li>
        </ul>
    </div>
    <div class="sh-add-new feedbackLi" >
    
      <div class="table2-text sh-ttext mt10">       
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="10%">序号<span class="arrowes acs"><!--升序追加样式acs，降序追加样式desc --></span></th>
                      <th scope="col" width="40%">操作内容</th>
                      <th scope="col" width="10%">操作人</th>
                      <th scope="col" width="10%">操作时间</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="list" var="lineLog" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" >
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" >
						</s:if>
						<th>${s.index+1}</th>
                    	<td class="f-arial">${lineLog.content}</td>
						<td class="f-arial">${lineLog.userName}</td>
                    	<td class="f-arial">${lineLog.updateOn}</td>
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
<script type="text/javascript">
$(function(){
	$(".sh-add-new").css("min-height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("min-height",$(window).height()-130+"px");	
	});

	validateFunction();
});

</script>
</html>
