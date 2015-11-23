<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-招募线路-用户线路申请-发布</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;招募线路<span class="blue1 ml25"><!--《<a href="javascript:void(0);" onclick="javascript:history.go(-1);" class="blue1">返回</a>--></span></p></div>  
<div class="mt20 ml20 mr20 black1">
	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">用户申请线路发布</a></li>
        </ul>
    </div>
	<ul class="sys-text sh-add-new clearfix f14 gray2 ">
		<li class="clearfix"><span class="fl w100 t-r">申请时间:</span>${userLineEntity.applicationTime}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r">申请人：</span>${userLineEntity.nickName}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r">联系电话：</span>${userLineEntity.telephone}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r">申请线路类型：</span><s:if test="%{userLineEntity.lineType==0}">上下班</s:if><s:if test="%{userLineEntity.lineType==1}">自由行</s:if></li>
		<li class="clearfix mt20"><span class="fl w100 t-r">上车点：</span>${userLineEntity.startAddress}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r">下车点：</span>${userLineEntity.endAddress}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r">发车时间：</span>${userLineEntity.startTime}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r">备注：</span>
			${userLineEntity.remark}
		</li>
		<li class="fl widthfull mt20"><span class="fl w100 t-r">招募类型：</span><input type="radio" class="checkbox"/>上下班<input type="radio" class="checkbox ml20"/>自由行</li>
		<li class="fl w33p mt20"><span class="fl w100 t-r">线路名称：</span><input type="text" class="r-input w210"/></li>
		<li class="fl w33p mt20"><span class="fl w100 t-r">价格：</span><input type="text" class="r-input w210 mr4"/>元</li>
		<li class="fl w33p mt20"><span class="fl w100 t-r">预计行程：</span><input type="text" class="r-input w210 mr4"/>分</li>
		<li class="fl w33p mt20"><span class="fl w100 t-r">里程：</span><input type="text" class="r-input w210 mr4"/>km</li>
		<li class="fl w33p mt20"><span class="fl w100 t-r">预计开通时间：</span><input type="text" class="r-input w210"/></li>
		<li class="fl w33p mt20"><span class="fl w100 t-r">报名截止时间：</span><input type="text" class="r-input w210"/></li>
		<li class="mt20 fl widthfull"><span class="fl w100 t-r">招募须知：</span>
			<textarea class="r-input more-show" style="width:79%;" name="content" id="content" style="height:350px;"></textarea>
		</li>
		<li class="mt20 fl widthfull t-c" id="bacnLi4"><input type="button"  value="确&nbsp;定" class="display-ib btn1 white mr40 f14" onclick="submitFunction();"/><input type="button"  value="取&nbsp;消" class="display-ib btn1 white f14" /></li>
	</ul>
</div>
</body>
<script type="text/javascript">


function submitFunction()
{
	$("#iframe_right",parent.parent.window.document).attr("src","../line/recruitLinesManager.action?level=two&toAdd=1");
}
</script>
</html>

