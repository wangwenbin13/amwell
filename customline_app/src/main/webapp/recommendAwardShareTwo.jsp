<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>推荐有奖</title>
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="yes" name="apple-touch-fullscreen" />
	<meta content="telephone=no" name="format-detection" />
	<meta content="black" name="apple-mobile-web-app-status-bar-style" />
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="../css/default.css" />
</head>

<body>
	<div class="wrap">
		<div class="main">
	        <div class="tip tipOther">
	            <s:if test="%{#attr.result == -2}">
	            <p>你已是小猪巴士用户，</p>
	            <p>暂时不能领新人礼了，</p>
	            <p>敬请关注我们的后续活动</p>
	            </s:if>
	            <s:elseif test="%{#attr.result == -1}">
	            <p> 未做推荐有奖设置</p>
	            </s:elseif>
	            <s:elseif test="%{#attr.result == 0}">
	            <p>新用户获取推荐奖励失败</p>
	            </s:elseif>
	            <s:else>
	            <p>30元优惠券已经发放到你的账户，</p>
	            <p>下载小猪巴士APP，</p>
	            <p>通过<em class="em2"><s:property value="#attr.newUserPhone"/></em>手机号</p>
	            <p>登录即可使用</p>
	            </s:else>
	        </div>
	        <div class="box1" style="padding-bottom:34%;">
			<input type="button" value="下载小猪巴士APP" style="margin-top:20px;" onclick="javascript:window.location.href='http://a.app.qq.com/o/simple.jsp?pkgname=amwell.zxbs'"/>
<%--	            <input type="button" value="关注小猪巴士公众帐号"/>--%>
	        </div>
	        <section class="txt clearfix">
				 <div class="logo"></div>
	        </section>
	       
	    </div>
	</div>
</body>
</html>
