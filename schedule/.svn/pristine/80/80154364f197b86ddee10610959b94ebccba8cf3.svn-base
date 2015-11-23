<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<title>调度平台-登录</title>
<script type="text/javascript" src="js/jquery/jquery-1.8.2.min.js"></script>
<link rel="Shortcut Icon" href="images/favicon.ico"  type="image/x-icon" />
<style type="text/css">
body,div,ul,li,form,h2,input,textarea,p,blockquote{
     padding: 0;
     margin: 0;
     list-style:none;
     }
body{
     font-size:12px;
     font-family:"宋体",Arial, Helvetica, sans-serif;
     background:#e7e2e2;
     }
img {
     border: 0;
     }
h1,h2,h3,h4,h5,h6 {
     font-weight: normal;
     font-size: 100%;
     }
em{
     font-weight: normal;
     font-style: normal;
     }
q:before,q:after {
     content:"";
     }
a{
     outline:none;
     cursor:pointer;
     text-decoration:none;
     }
a:link,a:visited{
     text-decoration:none;
     }
a:hover{
     text-decoration:underline;
     }
a,img{
     border:none;
     }
.hide{display:none;}
.show{display:block}
.cursor{cursor:pointer}
.p-r{position:relative}
.p-a{position:absolute}
.fl{float:left;}
.fr{float:right}
.f12{font-size:12px;}
.f14{font-size:14px;}
.f16{font-size:16px;}
.white1{color:#fff;}
.red2{color:#f46f5e}
.yellow1{color:#d8a677}
.gray1{color:#a5a5a5}
.gray2{color:#696969;}
.black1{color:#2d2d2d}
.mt20{margin-top:20px;} 
.mt30{margin-top:30px;}
.mt40{margin-top:40px;}
.ml15{margin-left:15px;}
.ml30{margin-left:30px;}
.fw{font-weight:bold;}
.yellow1{color:#ff9e05;vertical-align:-2px;}
.f-arial{font-family:Arial,Tahoma,Helvetica}
.top{height:96px;padding-right:70px;line-height:96px;color:#3f3f3f;background:#faf9f9;}
html,body,.wrap{height:100%;} 
body > .wrap{height:auto;min-height:100%;} 
.main{padding-bottom:83px;} 
.loginFooter{position:relative; ;color:#696969; text-align:center;clear:both;margin-top:-68px; }
.h48{height:48px;}
.content{background:url(images/loginBg.jpg) no-repeat;}
.contentTxt{width:960px;height:702px;margin:0 auto;}
.loginLogoDiv{width:900px;margin:0 auto;}
.loginLogo{float:left;width:210px;height:72px;margin-top:13px;background:url(images/loginLogo.png) no-repeat;}
.loginBox{float:right;width:460px;margin-top:77px;color:#2d2d2d}
.h2{height:70px;padding-left:54px;line-height:70px;font-size:25px;font-family:"Microsoft yahei";background:#fff9e5}
.loginBoxTxt{padding:30px 53px 20px;background:#fff;}
.loginBoxTxt li{position:relative;width:354px;}
.loginInput{width:335px;height:46px;padding-left:16px;line-height:48px;border:2px solid #e1e1e1;border-radius:5px;font-size:16px;outline: none;font-family:"Microsoft yahei"}
.loginPass{left:0;top:0;}
.yzm{height:70px;line-height:70px;}
.yzm img{width:190px;height:70px;cursor:pointer;}
.changYzm{margin-left:15px;color:#2d2d2d;cursor:pointer;}
.loginBtn{outline: none;width:143px;height:44px;line-height:40px;text-align:center;color:#fff;border:0 none;cursor:pointer; background:url(images/loginEle.png) no-repeat;font-size:21px;font-family:"Microsoft yahei"}
.loginBtn:hover{background-position:0 -153px;}
.loginBottom{width:460px;height:35px;background:url(images/loginBottom.png) no-repeat;}
.error{display:inline-block;width:18px;height:18px;margin-right:4px;background:url(images/loginEle.png) no-repeat 0 -53px;vertical-align:-3px;}
.clearIpnut{position:absolute;right:10px;top:11px;width:29px;height:29px;background:url(images/loginEle.png) no-repeat 0 -81px;z-index:5;cursor:pointer;}
.telIco{display:inline-block;width:27px;height:27px;margin-right:5px;background:url(images/loginEle.png) no-repeat 0 -117px;vertical-align:-8px;}
</style>
	
</head>

<body>	
	<div class="wrap">
    	<div class="main clearfix">
      		<div class="top"><span class="fr"><em class="telIco"></em>客服热线：<em class="fw f16 f-arial yellow1">400-168-1866</em></span><div class="loginLogoDiv"><div class="loginLogo"></div></div></div>
            <div class="content clearfix"> 
            	<div class="contentTxt">             	
                    <div class="loginBox">
                    <form id="loginForm" action="login/toLogin.action" method="post">
                        <h2 class="h2">登录排班系统</h2>
                        <ul class="loginBoxTxt">
                            <li class="h48"><input type="text" class="loginInput gray1" id="loginName" name="loginName" value="请输入用户名" autocomplete="off"/></li>
                            <li class="mt20 h48 p-r">
	                            <input type="text" value="请输入密码" id="pwdPrompt" name="pwdPrompt" class="loginInput p-a loginPass gray1" autocomplete="off"/>
	                            <input type="password"  class="loginInput" autocomplete="off" id="password" name="password" />
	                            
	                        </li>
                            <li class="mt20 h48"><input type="text" class="loginInput gray1" value="请输入验证码" id="vCode" name="vcode" autocomplete="off"/></li>
                            <li class="mt20 yzm"><img class="fl" id="v" onclick="changeVCode();" /><span class="fl changYzm f14" onclick="changeVCode();" autocomplete="off">看不清，换一张</span></li>
                            <li class="mt30"><input type="button" class="loginBtn" value="登&nbsp;录" onclick="checkLogin();" autocomplete="off"/><span class="error ml15" style="display:none;" id="errorIcon"></span><span class="red2 f14" id="errorTip"></span></li>
                            <li class="mt40 yellow1">本系统建议在IE7以上浏览器运行，建议在1024*768以上分辨率浏览</li>
                        </ul>
                        <p class="loginBottom"></p>
                    </form>
                    </div>
                </div>
            </div>   
   		</div>   
   </div>
	<div class="loginFooter"><em class="f-arial">Copyright&nbsp;&copy;&nbsp;</em>深圳市小猪巴适科技有限公司&nbsp;<a href="http://www.miitbeian.gov.cn" target="_blank" class="gray2">粤ICP备14029252号-2</a></div>
</body>
</html>
<script>
$(function(){
	$("#v").attr("src","login/securityCodeImage.action?date="+Math.floor(Math.random()*10000+1));
    clearInputDefaultVal();
});

//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}


function checkLogin(){
	var loginName = $("#loginName").val();
	var password = $("#password").val();
	var vCode = $("#vCode").val();
	//alert(loginName+","+password+","+vCode);
	
	if((""==loginName.trim() ||"请输入用户名"==loginName.trim()) && (""==password.trim() || "请输入密码"==password.trim())){
		$("#errorIcon").show();
		$("#errorTip").html("您还没有输入用户名或密码");
		return;
	}else{
		$("#errorIcon").hide();
		$("#errorTip").html("");
	}

	if(""==vCode.trim() || "请输入验证码"==vCode.trim()){
		$("#errorIcon").show();
		$("#errorTip").html("您还没有输入验证码");
		return;
	}else{
		$("#errorIcon").hide();
		$("#errorTip").html("");
	}

	
	$.ajax({
		url:'login/checkLogin.action?loginName='+escape(escape(loginName))+"&password="+password+"&vcode="+vCode,		
		cache:false,	
		dataType:"text",	
		success:function(data){	
			
			if(data=='loginNameOrPasswordIsNull'){
				$("#errorIcon").show();
				$("#errorTip").html("您还没有输入用户名或密码");
			}else if(data=="loginNameOrPasswordError"){
				$("#errorIcon").show();
				$("#errorTip").html("用户名或密码错误");
			}else if(data=="vCodeIsErorr"){
				$("#errorIcon").show();
				$("#errorTip").html("验证码有误");
				changeVCode();
			}else if(data=="ZHGQ"){
				$("#errorIcon").show();
				$("#errorTip").html("账号已过期");
			}else if(data=="ZHJY"){
				$("#errorIcon").show();
				$("#errorTip").html("账号已禁用");
			}else if(data=="OK"){
				$("#loginForm").submit();
			}
		}
				
	});
	
	
}

function changeVCode(){
	$("#v").attr("src","login/securityCodeImage.action?date="+Math.floor(Math.random()*10000+1));
}
	//清除input框默认值 
function clearInputDefaultVal()
{
	//ie8会自动落焦点，导致提示文字不能删去
	$("#loginName,#password,#vCode").blur();
	$('.loginInput').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			$(".clearIpnut").remove()	
			$(this).css("borderColor","#fec73c");
			$(this).after('<span class="clearIpnut"></span>');
			$(".clearIpnut").click(function(){
				if($(this).prev().val() != "请输入用户名" && $(this).prev().val() != "请输入密码" && $(this).prev().val() != "请输入验证码")
				{
					$(this).prev().val('');
				}
			});
			if(txt == $(this).val()){
				$(this).val(""); 
				$("#errorIcon").hide();
				$("#errorTip").html("");
				$(this).removeClass("gray1").addClass("black1");         
			} 
		}).blur(function(){  
			$(this).css("borderColor","#DBDBDB");
			if($(this).val() == ""){
			    $(this).val(txt);  
			    $(this).removeClass("black1").addClass("gray1");
			}
		});  
	});
	$("input[name=pwdPrompt]").focus(function () { 
        $("input[name=pwdPrompt]").hide(); 
        $("input[name=password]").show().focus().removeClass("gray1").addClass("black1");
        $("#errorIcon").hide();
		$("#errorTip").html("");
    }); 
    $("input[name=password]").blur(function () { 
        $("input[name=pwdPrompt]").attr("value","请输入密码");
        $("input[name=pwdPrompt]").removeClass("black1").addClass("gray1");
        if ($(this).val() == "") { 
            $("input[name=pwdPrompt]").show(); 
            $("input[name=password]").hide(); 
        } 
    });
}

/*页面按键处理函数*/
function pressKey(evt) {
	//evt = evt || window.event; 
    //var keyCode = evt.keyCode || evt.which || evt.charCode;//支持IE、FF 
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		checkLogin();
		break;
	default:
		break;
	}

}

var ua = window.navigator.userAgent;
//判断浏览器类型
if (ua.indexOf("MSIE") >= 1 || ua.indexOf("Chrome") >= 1){
	//禁止后退键  作用于IE、Chrome  
	document.onkeydown = pressKey;
}else if(ua.indexOf("Firefox") >= 1){
	//禁止后退键 作用于Firefox、Opera  
	document.onkeypress = pressKey;  
}else{
	document.onkeypress = pressKey;  
}
	  
window.load = function(){
  　　$("input[name=password]").attr("value","");
};

//在被嵌套时就刷新上级窗口
if(window.parent != window){
	window.parent.location.reload(true);
}
</script>

