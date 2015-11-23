<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>小猪巴士APP下载</title>
<style type="text/css">
body,div,p{margin:0;padding:0;}
body{font-family:"Microsoft yahei";color:#333;font-weight:bold;}
a{text-decoration:none}
em{font-style:normal;}
.main{padding:20px 15px 0;text-align:left;max-width:720px;margin:0 auto;}
.main p,.main p a{font-size:15px;}
.main p:nth-of-type(2){margin-top:25px;}
.main p:nth-of-type(3){margin-top:18px;font-weight:bold;}
.main p:nth-of-type(4){color:#8c8c8c;margin-top:5px;}
.main p:nth-of-type(5){margin-top:15px;}
.main p:nth-of-type(6){margin-top:4px;}
.main p:nth-of-type(6) em{font-size:17px;}
.red{color:#ff8f4b;}
.red1{color:#ff8f4b}
p em{font-weight:bold;}
p a{display:block;margin:30px auto 20px;width:100%;text-align:center;border-radius:8px;background: #4cbd71;color:#fff;height:46px;line-height:46px;font-size: 17px;}
/*6+*/
@media (min-device-width : 414px) and (max-device-width : 736px) and (-webkit-min-device-pixel-ratio : 3) {
.main p,.main p a{font-size:20px;}
.main p:nth-of-type(3){font-weight:bold;}
.main p:nth-of-type(6){margin-top:6px;}
.main p:nth-of-type(6) em{font-size:25px;}
p a{height:69px;line-height:69px;}
}
.downIco {
    background: url("downloadIco.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
    display: inline-block;
    height: 45px;
    vertical-align: -14px;
    width: 39px;
}
.downIco-iso {
    background-position: 0 -45px;
}
.downIco-android {
    background-position: -47px -45px;
}
</style>
</head>

<body>	
	<div class="main">
		<p>小伙伴们赶紧来抢票吧！</p>
    	<p>小猪巴士现已开通专线：</p>
        <p class="red1">${sstation }-  ${estation}</p>
		<p>途径：<span class="gray1">${station}</span></p>
        <p>发车时间：<em>${times}</em></p>
        <p>价格：<span class="red">￥<em>${price }</em></span></p>
<%--        <p><a href="http://a.app.qq.com/o/simple.jsp?pkgname=amwell.zxbs"><!--<span class="downIco downIco-android mr10"></span>-->点击下载安卓客户端</a></p>--%>
        <p><a href="http://a.app.qq.com/o/simple.jsp?pkgname=amwell.zxbs"><!--<span class="downIco downIco-iso mr10"></span>-->下载小猪巴士APP</a></p>
    </div>
</body>

</html>