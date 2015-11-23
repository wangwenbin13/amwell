<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% // 设置一个全局的路径变量，之后获取文件地址的时候直接进行根目录绝对位置，避免../过多的问题； %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
// sjx: 以后处理微信分享的时候用;
	wx.error(function(res){
		alert(res);
	});
	wx.ready(function () {
		
	});
	wx.config({
    	debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    	appId: 'wxe30d761e0366b89f', 
    	// a9b9e2fc0ae1f2d447a69ccbf1fe2657
    	timestamp: '', // 必填，生成签名的时间戳
    	nonceStr: '', // 必填，生成签名的随机串
    	signature: '',// 必填，签名，见附录1
    	jsApiList: ['onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone']
	});
</script>
