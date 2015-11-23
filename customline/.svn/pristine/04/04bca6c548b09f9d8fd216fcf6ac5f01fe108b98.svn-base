<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理-乘客列表-乘客详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
    <div class="sys-text sh-add-new map-shebei-li" style="height: 425px;">
       <ul class="marLi clearfix gray2 f12">
            <li><span class="fl w62 t-r">注册时间：</span><s:property value="passengerInfoEntity.registerTime"/></li>
            <li style="height:72px;">
            <span class="fl w62 t-r mt20">头像：</span>
              	  <s:if test="passengerInfoEntity.sex==0">
              	  <img width="60" height="60" class="p10" src="<s:property value='passengerInfoEntity.headerPicUrl'/>" onerror="javascript:this.src='../images/logo@2x.png'"/>
              	  </s:if>
              	  <s:else>
              	  <img width="60" height="60" class="p10" src="<s:property value='passengerInfoEntity.headerPicUrl'/>" onerror="javascript:this.src='../images/logo_female@2x.png'"/>
              	  </s:else>

            </li>
            <li><span class="fl w62 t-r">ID：</span><s:property value="passengerInfoEntity.displayId"/></li>
            <li><span class="fl w62 t-r">账户余额：</span><s:property value="passengerInfoEntity.theBalance"/>元</li>
            <li><span class="fl w62 t-r">昵称：</span><s:property value="passengerInfoEntity.nickName"/></li>
            <li><span class="fl w62 t-r">性别：</span><s:property value="%{passengerInfoEntity.sex==0?'男':'女'}"/></li>
            <li><span class="fl w62 t-r">账号状态：</span>
            <s:if test="%{passengerInfoEntity.blackFlag==0}">
              <em class="green1">正常</em>
            </s:if>
            <s:else>
              <em class="red1">黑户</em>
            </s:else>
            </li>
            <li><span class="fl w62 t-r">手机号码：</span><s:property value="passengerInfoEntity.telephone"/></li> 
        </ul>
    </div>
</body>
<script>	
$(function(){
	//浏览器可视窗口的的高度
	$(".sys-text").css("height",($(window).height()-50+"px"));
	$(window).resize(function() {
		$(".sys-text").css("height",($(window).height()-50+"px"));
	}); 
})
</script>
</html>
