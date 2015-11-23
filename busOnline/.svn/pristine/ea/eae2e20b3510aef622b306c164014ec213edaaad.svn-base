<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type">
		<meta content="text/html; charset=utf-8">
		<meta charset="utf-8">
		<title>相约10.11，小猪巴士邀请您共赏海滩日落。</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<style type="text/css">
			body {padding: 0;margin:0;background-color:#f5f5f5;}
			.wrap {position: relative;}
			
			.ticket {
				max-width: 100%;
				position: absolute;
				top: 1%;
			}
			.tcode {
				max-width: 37%;
				position: absolute;
				bottom: 9.4%;
				left: 31.5%;
			}
			
		</style>
	</head>
	<body center="1">
		<div class="wrap">
			<img class="ticket" alt="" src="http://img.pig84.com/download/100000000140">
			<img class="tcode" alt="" src="http://img.pig84.com/download/${code}">
		</div>
		
		<%@include file="../v1/template/orderView.jsp" %>
		<%@include file="../v1/template/loginView.jsp" %>
		<%@include file="../v1/template/tipView.jsp" %>
	</body>
</html>
<%@include file="../v1/template/footJavascript.jsp" %>
<script type="text/javascript">
	var width=window.innerWidth;
	var height=window.innerHeight;
	$('.wrap').css({'width': width,'height':height});
	
	var openId = '${openId}';
	function doBuy() {
		var user = getLoginUser();
		var uid = user['a2'], orderId = user['a6'];
		$.post('${contextPath}/games/anniversary/buy', {
			uid: uid,
			openId: openId
		}, function (res) {
			if (!res) {
				alert('服务器异常！');
				return;
			}
			var wx = eval('(' + res + ')');
			callpay(wx['appId'], wx['timestamp'], wx['noncestr'], wx['package'], wx['sign'], wx['signType'], orderId, uid);
		}, 'text');
	}
</script>