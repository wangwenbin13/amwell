<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% // 设置一个全局的路径变量，之后获取文件地址的时候直接进行根目录绝对位置，避免../过多的问题； %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<style>
	#loginInfoSpace { position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; z-index: 10; background: url(http://img.pig84.com/download/100000000004); background-size: 100%; display: none; }
	#loginInfoSpace .login-space { width: 314px; height: 100%; margin: 23px auto; }
	#loginInfoSpace .login-space img { margin: 20% auto 16%; width: 128px; display: block; }
	#loginInfoSpace .login-space ul { list-style: none; -webkit-box-shadow: 1px 2px 3px #666; background-color: #FFF; padding: 0; }
	#loginInfoSpace .login-space ul li > em { float: left; width: 22px; margin: 16px 12px 8px 18px; height: 32px; }
	#loginInfoSpace .login-space .login_username { line-height: 56px; height: 56px; }
	#loginInfoSpace .login-space .login_username span { float: right; background: #4cbd71; border-radius: 6px; color: #FFF; width: 96px; font-size: 16px; height: 38px; line-height: 38px; margin: 7px 16px 7px 0px; text-align: center; }
	#loginInfoSpace .login-space .login_username span.on { background: #c4c4c4; }
	#loginInfoSpace .login-space .login_username em { background: url(http://img.pig84.com/download/100000000015) no-repeat; background-size: 100%; }
	#loginInfoSpace .login-space .login_split { height: 1px; background-color: rgba(206, 205, 207, 0.8); margin: 0px 32px; }
	#loginInfoSpace .login-space .login_code { line-height: 56px; height: 56px; }
	#loginInfoSpace .login-space .login_code em { background: url(http://img.pig84.com/download/100000000016) no-repeat; background-size: 100%; }
	#loginInfoSpace .login-space input { height: 20px; width: 132px; line-height: 20px; margin: 4px 0px; padding: 10px 4px 6px; font-size: 17px; border: none; color: #666; }
	#loginInfoSpace .login-space .loginBtn { display: block; width: 90%; margin: 20px auto; background: #4cbd71; color: #FFF; font-size: 20px; text-align: center; line-height: 44px; border-radius: 6px; }
	#loginInfoSpace .login-space p { font-size: 14px; color: #666; padding: 64px; }
</style>
<div id="loginInfoSpace">
	<div class="login-space">
		<img src="http://img.pig84.com/download/100000000014" />
		<ul>
			<li class="login_username"><em></em><input tabindex="1" name="mobile" type="tel" placeholder="手机号码" /><span tabindex="2" ontouchstart="sendMessageCode();" >获取验证码</span></li>
			<li class="login_split"></li>
			<li class="login_code"><em></em><input tabindex="3" name="code" type="mobile" placeholder="请输入验证码" /></li>
		</ul>
		<span tabindex="4" class="loginBtn" ontouchstart="onLoginFromLoginView(0);" >登录</span>
		<p>未注册用户第一次登录后，默认密码是手机后6位。</p>
	</div>
</div>
<script>
	var loginSuccessCallback, loginMessageCodeTimer, loginMessageCodeTime, isLoginShow,
		loginMessageCodeTemplate = ['（', null, '）'], loginMessageCodeTip = '获取验证码';
	
	/** 设置登录成功的回调； */
	function setLoginSuccessCallback(callback) {
		loginSuccessCallback = callback;
	}
	
	/** 登录框显示； */
	function onLoginShow() {
		isLoginShow = true;
		$('#loginInfoSpace .login-space p')[$(window).height() < 540 ? 'hide' : 'show']();
		var elem = $('#loginInfoSpace');
		elem.css({
			left: -$(window).width() + 'px',
			display: 'block'
		});
		$('#loginInfoSpace').animate({
			left: '0px'
		}, {
			duration: 400,
			complete: function () {
				$('.login_username input').focus();
			}
		});
	}
	
	/** 根据url检测是否需要显示优惠券界面； */
	function checkLoginByUrl() {
		var url = location.href;
		if (url.indexOf('#login') != -1) {
			onLoginShow();
		} else {
			if (isLoginShow) {
				onLoginHide();
			}
		}
	}
	
	/** 检测url是否要进行显示； */
	window.addEventListener('load', function (evt) {
		checkLoginByUrl();
	});
	
	/** 地址变更事件； */
	window.addEventListener('popstate', function (evt) {
		checkLoginByUrl();
	}, false);
	
	/** 登录框隐藏； */
	function onLoginHide() {
		isLoginShow = false;
		var elem = $('#loginInfoSpace');
		$('#loginInfoSpace').animate({
			left: -$(window).width() + 'px'
		}, {
			duration: 400,
			complete: function () {
				$('input').blur();
				elem.hide();
			}
		});
		onLoginMessageCodeStatUpdate();
	}
	
	/** 发送验证码； */
	function sendMessageCode() {
		var mobile = $('input[name=mobile]').val();
		if (!mobile.match(/^0{0,1}(13[0-9]|15[0-9]|147|18[0-9]|17[0-9])[0-9]{8}$/)) {
			alert("手机号码格式不正确");
			$('input[name=mobile]').focus();
        	return;
		}
		if (loginMessageCodeTime > 0)
			return;
		$.post('${contextPath}/v1/user/sendMessageCode', {
			mobile: mobile
		}, function (res) {
			if (res == "-1") {
				alert('短信发送失败，请稍后再试。');
				onLoginMessageCodeStatUpdate();
			}
		});
		
		$('.login_username span').addClass('on');
		onLoginMessageCodeStatUpdate(loginMessageCodeTime = 60);
		loginMessageCodeTimer = setInterval(function () {
			onLoginMessageCodeStatUpdate(loginMessageCodeTime --);
		}, 1000);
		
		$('input[name=code]').focus();
	}
	
	/** 更新验证码发送按钮状态； */
	function onLoginMessageCodeStatUpdate(time) {
		time = time || 0;
		var elem = $('.login_username span');
		if (time <= 0) {
			elem.html(loginMessageCodeTip);
			clearInterval(loginMessageCodeTimer);
			elem.removeClass('on');
			loginMessageCodeTime = 0;
		} else {
			loginMessageCodeTemplate[1] = time;
			elem.html(loginMessageCodeTemplate.join(''));
		}
	}
	
	/** 登陆操作 */
	function onLoginFromLoginView() {
		var mobile = $('input[name=mobile]').val(), code = $('input[name=code]').val();
		
		if (!mobile.match(/^0{0,1}(13[0-9]|15[0-9]|147|18[0-9]|17[0-9])[0-9]{8}$/)) { 
			alert("手机号码格式不正确");
        	return;
		}
		if (!code) {
			alert("请输入手机验证码");
			return;
		}
		onLoadingShow('登录小猪平台中...');
		$.post('${contextPath}/v1/user/login', {
			mobile: mobile,
			code: code,
			channel: localStorage.getItem("channel"),
			openId: localStorage.getItem("openId")
		}, function (res) {
			console.log(res);
			var json = eval('(' + res + ')');
			if (json['a2']) {
				localStorage.setItem("MyUserInfo", res);
				history.go(-1);
				loginSuccessCallback && loginSuccessCallback();
			} else {
				alert(json['a1']);
			}
			onLoadingHide();
		}, 'text');
		return false;
	}
</script>
