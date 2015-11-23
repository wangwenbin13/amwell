<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% // 设置一个全局的路径变量，之后获取文件地址的时候直接进行根目录绝对位置，避免../过多的问题； %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<style>
	#orderInfoSpace {
		display: none;
		position: fixed;
		left: 0px;
		top: 0px;
		width: 100%;
		height: 200%;
		z-index: 10;
		background: #f0f0f0;
		padding-top: 28px;
		color: #919191;
	}
	#orderInfoSpace > div { background: #FFF; padding: 0px 23px; margin: 1px 0px; line-height: 64px; clear: both; font-size: 16px; }
	#orderInfoSpace > div > em { margin: 22px 4px; block; float: right; width: 20px; height: 20px; background: url(http://img.pig84.com/download/100000000018) no-repeat; background-size: 100%; }
	#orderInfoSpace > div.on > em { background: url(http://img.pig84.com/download/100000000019) no-repeat; background-size: 20px 20px; }
	#orderInfoSpace > p { background: #FFF; padding: 0px 23px; margin: 1px 0px; line-height: 48px; clear: both; font-size: 18px; box-shadow: 1px -2px 4px #ddd; }
	#orderInfoSpace img { float: left; display: block; height: 48px; margin: 8px 16px 0px 0px; }
	#orderInfoSpace a { border-radius: 6px; clear: both; margin: 40px auto; display: block; line-height: 48px; width: 90%; text-align: center; background: #3C3; color: #FFF; font-size: 16px; }
	#orderInfoSpace #orderUnionSpace { box-shadow: 1px 2px 4px #ddd; }
</style>
<div id="orderInfoSpace">
	<p>支付方式</p>
	<div class="orderType on" type="1" onclick="orderSelectChanged(this);">
		<img src="http://img.pig84.com/download/100000000020">支付宝支付<em></em>
	</div>
	
	<!--
	<div class="orderType" type="0" onclick="orderSelectChanged(this);">
		<img src="http://img.pig84.com/download/100000000021">微信支付<em></em>
	</div>
	-->
	
	<div class="orderType" type="2" onclick="orderSelectChanged(this);">
		<img src="http://img.pig84.com/download/100000000022">银联支付<em></em>
	</div>
	
	<a href="javascript:orderBuy()">支付</a>
</div>
<script>
	var payOrderId;
	// 支付方式选择事件；
	function orderSelectChanged(elem) {
		var elem = $(this);
		if (!elem.hasClass('on')) {
			$('#orderInfoSpace > div').removeClass('on');
			elem.addClass('on');
		}
	}
	
	// 进行支付， 微信会直接跳过这个界面；
	function orderBuy() {
		var myOrderType = $('#orderInfoSpace > div.on').attr('type') - 0;
		switch(myOrderType) {
			case 1: validateOrderByAli(); break;
			case 2: validateOrderByUni(); break;
		}
		
	}
	
	/** 银联支付； */
	function validateOrderByUni(){
		$.post('${contextPath}/v1/order/pay', {
			type: 2,
			orderId: payOrderId
		}, function(data){
			var elem = document.createElement('div');
			elem.innerHTML = data;
			$(elem).find('form').submit();
    	}, "text");
	}
	
	/** 阿里支付； */
	function validateOrderByAli(){
		$.post('${contextPath}/v1/order/pay', {
			type: 1,
			orderId: payOrderId
		}, function(data) {
			var elem = document.createElement('div');
			elem.innerHTML = data;
			$(elem).find('form').submit();
		}, "text");
	}
	
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
    	        document.location.href = ["${contextPath}/v1/user/ticket?id=" + orderId, '&uid=', uid].join('');
		    } else if(res.err_msg=='get_brand_wcpay_request:cancel') {
        	    alert("支付取消！");
			} else if(res.err_msg=='get_brand_wcpay_request:fail') {
            	alert("支付失败!");
	    	} else {
	    		alert(res.err_code + res.err_desc + res.err_msg);
			}
		});
	}
	
	/** 支付框显示； */
	function onOrderShow(orderId) {
		payOrderId = orderId;
		var elem = $('#orderInfoSpace');
		elem.css({
			left: -$(window).width() + 'px',
			display: 'block'
		});
		elem.animate({
			left: '0px'
		}, {
			duration: 400
		});
	}
	
	/** 登录框隐藏； */
	function onOrderHide() {
		var elem = $('#orderInfoSpace');
		elem.animate({
			left: -$(window).width() + 'px'
		}, {
			duration: 400,
			complete: function () {
				elem.hide();
			}
		});
	}
</script>
