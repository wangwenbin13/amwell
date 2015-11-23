<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link type="image/x-icon" rel="icon" href="${contextPath}/images/favicon.ico" /> 
	<style type="text/css">
	  .button{
	    cursor:pointer;
	    line-height:50px;
	    background-color:#ff6000;
	    border-radius:3px;
	    color:#fff;
	    font-weight:bold;
	    font-size:25px;
	    box-shadow:1px 1px 0 #ccc;
	    margin:10px;
	    padding-left:10px;
	  }
	</style>   
  </head>
  <body>
    <section style="background:#cdcdcd;height:50px;box-shadow:0 1px 0 #ccc;">
    	<!--  
        <input type="text" id="openId" value="zxflove08" style="height:50px;width:100%;line-height:50px;font-size:25px;padding-left:10px;"/>
       -->
        <input type="text" id="openId" value="o6kP1t00fjfZUK4hfGHp5fXnF-ic" style="height:50px;width:100%;line-height:50px;font-size:25px;padding-left:10px;"/>
        
    </section>
    <section style="margin-top:30px;">
        <a class="link" url="v1/line/shareByApp?id=150725102253734897&openId="><div class="button">v1分享入口</div></a>
        <a class="link" url="v1/line/workday?client_id=CP0010&access_token=df54e7340d43a7a6cb3d9237fa6d3303-21677312bc0d4593adad491e17808149-null-null&openId="><div class="button">v1买票入口</div></a>
        <a class="link" url="v1/user/home?client_id=CP0010&access_token=df54e7340d43a7a6cb3d9237fa6d3303-21677312bc0d4593adad491e17808149-null-null&openId="><div class="button">v1登录入口</div></a>
        <a class="link" url="games/anniversary/index?client_id=CP0010&access_token=df54e7340d43a7a6cb3d9237fa6d3303-21677312bc0d4593adad491e17808149-null-null&openId="><div class="button">周年入口</div></a>
        <a class="link" url="games/anniversary/buyTicket?uid=150703151220670043&openId="><div class="button">周年买票</div></a>
        
     </section>
  </body>
</html>
<script type="text/javascript" src="http://img.pig84.com/download/100000000011"></script>
<script type="text/javascript" src="../js/common/common_util.js?v=3"></script>
<script type="text/javascript">
	(function () {
		// 把openid写本地存储，方便调用；
		localStorage.setItem('openId', $("#openId").val());
	})();
	
    $(".link").click(function(){
        location.href="../" + this.getAttribute('url') + localStorage.getItem('openId');
    });
</script>
