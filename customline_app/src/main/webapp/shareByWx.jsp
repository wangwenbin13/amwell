<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>小猪巴士APP下载</title>
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no" name="format-detection">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="x5-orientation" content="portrait">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
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
</style>

<script type="text/javascript">

function test(){
            var browser = {
                versions: function() {
                    var u = navigator.userAgent, app = navigator.appVersion;
                    return { 
                        trident: u.indexOf('Trident') > -1, 
                        presto: u.indexOf('Presto') > -1, 
                        webKit: u.indexOf('AppleWebKit') > -1, 
                        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, 
                        mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), 
                        ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), 
                        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, 
                        iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, 
                        iPad: u.indexOf('iPad') > -1, 
                        webApp: u.indexOf('Safari') == -1 
                    };
                }(),
                language: (navigator.browserLanguage || navigator.language).toLowerCase()
            }

            if (browser.versions.ios || browser.versions.iPhone || browser.versions.iPad) {
               window.location="https://itunes.apple.com/cn/app/xiao-zhu-ba-shi/id924283054?mt=8"
               //document.getElementById("target").src="../4.png";; 
            }
            else if (browser.versions.android) {
            	  window.location.href = "http://ftp.zuchezaixian.com/busonline/o_pic/apk/v.1.4/bus.apk";
            	 // document.getElementById("target").src="../3.png";; 
            }
}
</script>

</head>

<body onload="test()">	
<%--     <p class="mtf20"><img id="target" src="../3.png"/></p>--%>
	<div class="main">
		<p>小伙伴们赶紧来抢票吧！</p>
    	<p>小猪巴士现已开通专线：</p>
		<p class="red1">${sstation }-  ${estation}</p>
		<p>途径：</p>
		<span class="gray1">${station}</span>
		<p>发车时间：<em>${times}</em></p>
        <p>价格：<span class="red">￥<em>${price }</em></span></p>
		<p><a href='http://a.app.qq.com/o/simple.jsp?pkgname=amwell.zxbs'>下载小猪巴士APP</a></p>
    </div>
</body>

</html>

