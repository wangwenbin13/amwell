<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
    <title>运营平台－线路-完成设置</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
</head>
<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div id="showPagePic" class="showPage"></div>
<!-- 运营平台－创建线路-上下班-设置价格 -->
	<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布线路</p></div>
    <div class="steps">
    	<ol class="clearfix">
			<c:if test="${lineBaseId==null}">
				<li><i>1</i><span class="tsl">线路规划</span></li>
				<li><i>2</i><span class="tsl">设置班次</span></li>
				<li><i>3</i><span class="tsl">定价格</span></li>
				<li><i>4</i><span class="tsl">设置供应商</span></li>
				<li class="active"><i>5</i><span class="tsl">完成设置</span></li>
			</c:if>
			<c:if test="${lineBaseId!=null}">
				<a href="../publishLine/loadLineBaseInfo.action?lineBaseId=${lineBaseId}"><li><i>1</i><span class="tsl">线路规划</span></li></a>
				<a href="../publishLine/loadClassInfo.action?lineBaseId=${lineBaseId}"><li><i>2</i><span class="tsl">设置班次</span></li></a>
				<a href="../publishLine/loadClassPrice.action?lineBaseId=${lineBaseId}"><li><i>3</i><span class="tsl">定价格</span></li></a>
				<a href="../publishLine/loadBusinessInfo.action?lineBaseId=${lineBaseId}"><li><i>4</i><span class="tsl">设置供应商</span></li></a>
				<a href="../publishLine/loadCommitLine.action?lineBaseId=${lineBaseId}"><li class="active"><i>5</i><span class="tsl">完成设置</span></li></a>
			</c:if>
		</ol>		
    </div>
   <form id="addForm" method="post">
   <input type="hidden" name="lineBaseId" value="${lineBaseId}"/>
   <ul class="mt20 ml45">
   		<li class="mt20 line24 clearfix">
   			<span class="fl w90 t-r">开启状态：<em class="red1">*</em></span>
   			<c:if test="${status==3}">
   				<c:if test="${forceOpenFlag=='true'}">
   					<input type="checkbox" name="status" class="checkbox" checked="checked" value="3"/>开启
   				</c:if>
   				<c:if test="${forceOpenFlag!='true'}">
   					已发布
   					<input type="hidden" name="status" value="3"/>
   				</c:if>
   			</c:if>
   			<c:if test="${status==5}">
   				<c:if test="${forceOpenFlag=='true'}">
   					<input type="radio" name="status" class="checkbox" value="3"/>开启
   				</c:if>
   				<input type="radio" name="status" class="checkbox ml20" value="1"/>调度中
   			</c:if>
   			<c:if test="${status==0||status==1}">
   			    <c:if test="${forceOpenFlag=='true'}">
   			    	<input type="radio" name="status" class="checkbox" value="3"/>开启
   			    </c:if>
   				<input type="radio" name="status" class="checkbox ml20" checked="checked" value="1"/>调度中
   			</c:if>
   			<c:if test="${status==2}">
   			    待发布
   			    <input type="hidden" name="status" value="2"/>
   			</c:if>
   		</li>
   		<li class="mt20 line24 clearfix">
   			<span class="fl w90 t-r">专线设置：</span>
   			<input type="text" class="r-input w170" name="companyName" value="${companyName}"/>
   			<input type="hidden" class="r-input w170" name="companyId" value="${companyId}"/>
   			<a href="javascript:selectVIP();" class="blue1">选择VIP客户</a>
   		</li>
   		<p class="clearfix mt30 line28">
   			<span class="fl w90 t-r"></span>
   			<a class="blue1" href="../publishLine/loadBusinessInfo.action?lineBaseId=${lineBaseId}">上一步</a>
   			<a class="display-ib btn1 white ml30" href="javascript:saveSpecialInfo();" >完成</a>
   		</p>
	</ul>
	</form>
</body>
</html>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
var isSubmitAvailable = true;

//选择VIP客户
function selectVIP(){
	$("#topHide",parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("../publishLine/selectVIP.action"); 
    $("#mainBody").show();
}

// 保存vip信息
function saveSpecialInfo(){
	if(isSubmitAvailable){
		isSubmitAvailable = false;
		var url = "../publishLine/saveCommitLine.action";
		leasePostAjax(url,$("#addForm"),"json",function(data){
			isSubmitAvailable = true;
	        if(data.a1=="100"){
	        	 parent.parent.showRturnTip("保存成功",true); 
	             window.document.location.href="../line/getLinesList.action?level=two";
	        }else{
	              alert(data.a2);
	        }
		});
	}
}
</script>