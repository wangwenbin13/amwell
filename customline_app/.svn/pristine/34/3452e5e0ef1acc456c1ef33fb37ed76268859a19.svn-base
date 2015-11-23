<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>注册小猪巴士 得30元优惠券</title>
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="yes" name="apple-touch-fullscreen" />
	<meta content="telephone=no" name="format-detection" />
	<meta content="black" name="apple-mobile-web-app-status-bar-style" />
	<meta name="x5-orientation" content="portrait" />
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="../css/default.css" />
<script type="text/javascript">
function doAddSubmit(){
	var tel = document.doAddForm.newUserPhone.value;//获取手机号
	var telReg = !!tel.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|14[0-9]|18[0-9])[0-9]{8}$/);
	//如果手机号码不能通过验证
	if(tel == ""){
		alert("手机号码不能为空");
		document.doAddForm.newUserPhone.focus();
		return false;
	}else if(telReg == false){
	 	alert("请输入正确的手机号码");
	 	document.doAddForm.newUserPhone.focus();
	 	return false;
	}
	document.doAddForm.submit();
}
</script>
</head>

<body>
	<div class="wrap">
		<div class="main">
	        <div class="tip tipIndex">
	            <p>注册小猪巴士</p>
	            <p>你和你的朋友</p>
	            <p>各得<em class="em1"><s:property value="#attr.giftValue"/>元</em>优惠券</p>
	        </div>
	        <div class="box1">
	        <s:if test="#attr.recommendAwardSwitch == 1">
            <form action="addRecommendAward.action" method="post" name="doAddForm">
              <input type="hidden" name="oldUserPhone" value="${oldUserPhone}"/>
        	  <input type="tel" maxlength="11" placeholder="输入手机号" name="newUserPhone"/>
              <input type="button" value="免费坐车上下班" onclick="doAddSubmit()"/>
            </form>
            </s:if>
	        <s:if test="#attr.recommendAwardSwitch == 0">
	                                    本期活动已下线，下期活动即将上线，敬请期待！
	          </s:if>
	        </div>
	        <section class="txt clearfix">
                 <s:property value="#attr.actionRule" escape="false"/>
				 <div class="logo"></div>
	        </section>
	       
	    </div>
	</div>
</body>
</html>
