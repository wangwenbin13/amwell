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
			
			.p1 {
				max-width: 100%;
				position: absolute;
				top: 0%;
			}
			.p2 {
				max-width: 100%;
				position: absolute;
				bottom: 0%;
			}
			#payfor {
				max-width: 50%;
				bottom: 0%;
				right: 0%;
				position: absolute;
			}
		</style>
	</head>
	<body center="1">
	<div class="wrap">
		<img class="p1" src="http://img.pig84.com/download/100000000138" alt="">
		<img class="p2" src="http://img.pig84.com/download/100000000142" alt="">
		<a id="buyBtn" href="javascript:doBuy();"><img id="payfor" src="http://img.pig84.com/download/100000000136" alt=""></a>
	</div>
		<%@include file="../v1/template/loginView.jsp" %>
		<%@include file="../v1/template/tipView.jsp" %>
	</body>
</html>
<%@include file="../v1/template/footJavascript.jsp" %>
<script type="text/javascript">
	var width=window.innerWidth;
	var height=window.innerHeight;
	var isActivate = ${isActivate};
	$('.wrap').css({'width': width,'height':height});
	
	var openId = '${openId}';
	
	(function () {
		if (!isActivate) {
			$('#buyBtn').get(0).style.WebkitFilter = 'grayscale(100%)'; 
		}
	})();
	
	//调用微信支付
	function callpay(appId, timestamp, noncestr, pkg, sign, type, orderId, uid) {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : appId,
		 	"timeStamp" : timestamp,
		 	"nonceStr" : noncestr, 
		 	"package" : pkg,
		 	"signType" : type, 
		 	"paySign" : sign 
		}, function(res) {
			if (res.err_msg == 'get_brand_wcpay_request:ok') {
    	        location.reload(true);
		    } else if(res.err_msg=='get_brand_wcpay_request:cancel') {
        	    alert("支付取消！");
			} else if(res.err_msg=='get_brand_wcpay_request:fail') {
            	alert("支付失败!");
	    	} else {
	    		alert(res.err_code + res.err_desc + res.err_msg);
			}
		});
	}
	
	function doBuy() {
		if (!isActivate) {
			alert('周年庆典票已售罄。');
			return;
		}
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