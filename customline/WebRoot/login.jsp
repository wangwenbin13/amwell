<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta name="renderer" content="webkit" /> 
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<title>运营平台-登录</title>
<jsp:include page="/WEB-INF/view/resource.jsp"/>
<link rel="Shortcut Icon" href="images/favicon.ico"  type="image/x-icon" />
<style type="text/css">
body,div,p,input,a,ul,li{margin:0;padding:0}
ul,li{list-style:none;}
a{text-decoration:none;}
a:hover{text-decoration:underline}
.red1{color:#d1261e;}
.blue1{color:#0093dd}
.white1{color:#fff}
.fl{float:left;}
.ml10{margin-left:10px;}
.mt25{margin-top:25px;}
.f14{font-size:14px;}
.f-arial {font-family: Arial;}
.t-c{text-align:center;}
.widthfull{width:100%}
body{
	background:url(images/login_bg.jpg) center top no-repeat #0a90d4;
	font-size:12px;
	font-family:"宋体",Arial, Helvetica, sans-serif;
	height:100%;
	}
.login-box{
	width:720px;
	position:absolute;
	}
.login-box-t{
	float:left;
	width:112px;
	height:31px;
	background:url(images/login_yy_logo.png) no-repeat;
	}
.login-box-m{
	float:left;
	width:655px;
	height:336px;
	margin-top:20px;
	padding-left:65px;
	background:url(images/login_boxbg.png) no-repeat;
	}
.login-logo{
	margin-left:-10px;
	}
.login-logo,logo img{
	display:block;
	height:80px;
	max-width:220px;
	}
.login-box-m ul li{
	height:33px;
	line-height:33px;
	margin-bottom:10px;
	}
.line{
	width:100%;
	height:1px;
	background:#eaebeb;
	float:left;
	overflow:hidden
	}
.icon{
	float:left;
	width:16px;
	height:16px;
	margin:8px 0px 0 5px;
	background:url(images/ele.png) no-repeat;
	}
.user{
	background-position:-184px -80px;
	}
.psw{
	background-position:-205px -80px;
	}
.login-input-out{
	height:31px;
	float:left;
	border:1px solid #c3e1f2;
	background:#fff;
	box-shadow:inset 1px 0 #f8f8f8,inset -1px  0 #f8f8f8;
	}
.login-input{
	float:left;
	border:0 none;
	height:30px;
	line-height:30px;
	text-indent:5px;
	outline: none;
	}
.w311{
	width:311px;
	}
.w275{
	width:275px;
	}
.w175{
	width:175px;
	}
.yzm{
	width:59px;
	height:23px;
	margin:4px 15px 0 4px;
	border:1px solid #d9d9d9;
	}
.yzm img{
	width:59px;
	height:23px;
	cursor: pointer;
	}
.btn1 {
    background: url("images/ele.png") no-repeat scroll -190px -386px;
    height: 31px;
    line-height: 31px;
    text-align: center;
    width: 101px;
	border:0 none;
	margin-top:15px;
	cursor:pointer;
	}
.btn1:hover{
	background: url("images/ele.png") no-repeat scroll -299px -386px;
	}
.btn2{
	border:0 none;
	background:none;
	cursor:pointer;
	margin:25px 0 0 20px;
	}
.btn2:hover{
	text-decoration:underline
	}
.gray1{color:#919191}	
.ps-laber{
	height:30px;
	line-height:30px;
	left:27px;
	top:0;
	border:0 none;
	background:none;
	outline: none;
	}
</style>
	
</head>

<body>
<div class="login-box">
	<p class="login-box-t"></p>
	<form id="loginForm" action="login/toLogin.action" method="post">
    <div class="login-box-m">
    	<div class="login-logo mt6"><img src="images/logo.png"/></div>
    	<ul class="mt25">
        	<li>
            	<div class="login-input-out w311">
                <p class="line"></p>
        			<span class="icon user"></span><input type="text" id="loginName" name="loginName" class="login-input w285 gray3" value="请输入用户名"/>
        		</div>
                <span id="t_loginName" class="red1 ml10"></span>
            </li>
            <li>
            	<div class="login-input-out w311 p-r">
                <p class="line"></p>
        			<input type="text" class="p-a ps-laber gray3 w285" id="pwdPrompt" name="pwdPrompt" value="请输入密码"/>
                    <span class="icon psw"></span><input id="password" name="password" class="login-input w285 gray1" value="" type="password" autocomplete="off"/>
        		</div>
                <span id="t_passWord" class="red1 ml10"></span>
            </li>
            <li>
            	<div class="login-input-out fl w175">
                <p class="line"></p>
        			<input type="text" class="login-input w175 gray3" value="请输入验证码" id="vCode" name="vcode"/>
        		</div>
                <span class="yzm fl"><img id="v" onclick="changeVCode();" /></span>
                <a href="javascript:void(0);" onclick="changeVCode();" class="blue1">换一张</a><span id="t_vCode" class="red1 ml10"></span>
            </li>
            <li class="f14">
            	<input type="button" onclick="checkLogin();" class="fl btn1 white1 f14" value="登&nbsp;&nbsp;录" />
                <input type="reset" class="btn2 blue1 f14" value="重置" onclick="clearInput();"/>
            </li>
        </ul>
    </div>
    <script>
	var boxW=$(".login-box").width();
		var boxH=$(".login-box").height();
		$(".login-box").css({"left":(($(window).width()-boxW)/2)+"px"});
		$(".login-box").css({"top":(($(window).height()-boxH)/2)-20+"px"});
		$(window).resize(function(){
			$(".login-box").css({"left":(($(window).width()-boxW)/2)+"px"});
			$(".login-box").css({"top":(($(window).height()-boxH)/2)-20+"px"});
		});
	</script>
    </form>
    <div class="login-box-b t-c fl white1 mt25 widthfull">
    	<span class="f-arial">Copyright&nbsp;&copy;&nbsp;2013-2015&nbsp;</span>深圳市小猪巴适科技有限公司   服务热线：0755-61139168<a href="http://www.miitbeian.gov.cn" target="_blank" class="white1 ml4">粤ICP备14029252号-2</a>
    	<!-- 
    	<span class="vf2 ml10"><script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1000515205'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1000515205%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script></span>
    	 -->
    </div>
</div>
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
		$("#t_loginName").html("用户名为空");
		$("#t_passWord").html("密码为空");
		return;
	}else{
		$("#t_loginName").html("");
		$("#t_passWord").html("");
	}
	
	if(""==vCode.trim() || "请输入验证码"==vCode.trim()){
		$("#t_vCode").html("验证码为空");
		return;
	}else{
		$("#t_vCode").html("");
	}
	
	
	$.ajax({
		url:'login/checkLogin.action?loginName='+escape(escape(loginName))+"&password="+password+"&vcode="+vCode,		
		cache:false,	
		dataType:"text",	
		success:function(data){	
			
			if(data=='loginNameOrPasswordIsNull'){
				$("#t_loginName").html("用户名或密码不能为空");
				$("#t_passWord").html("");
				$("#t_vCode").html("");
			}else if(data=="loginNameOrPasswordError"){
				$("#t_loginName").html("用户名或密码错误");
				$("#t_passWord").html("");
				$("#t_vCode").html("");
			}else if(data=="vCodeIsErorr"){
				$("#t_loginName").html("");
				$("#t_passWord").html("");
				$("#t_vCode").html("验证码有误");
				changeVCode();
			}else if(data=="ZHGQ"){
				$("#t_loginName").html("账号已过期");
				$("#t_passWord").html("");
				$("#t_vCode").html("");
			}else if(data=="ZHJY"){
				$("#t_loginName").html("账号已禁用");
				$("#t_passWord").html("");
				$("#t_vCode").html("");
			}else if(data == "logined"){
				$("#t_loginName").html("账号已登录");
				$("#t_passWord").html("");
				$("#t_vCode").html("");
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
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				$(this).val(""); 
				$("#t_"+this.id).text("");
				$(this).removeClass("gray3").addClass("gray2");
			} 
		}).blur(function(){  
			if($(this).val() == ""){
			    $(this).val(txt);  
			    $(this).removeClass("gray2").addClass("gray3");
			}
		});  
	});
	$("input[name=pwdPrompt]").focus(function () { 
        $("input[name=pwdPrompt]").hide(); 
        $("input[name=password]").show().focus().removeClass("gray3").addClass("gray2");
        $("#t_passWord").text("");
    }); 
    $("input[name=password]").blur(function () { 
        $("input[name=pwdPrompt]").attr("value","请输入密码");
        $("input[name=pwdPrompt]").removeClass("gray2").addClass("gray3");
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

//重置
function clearInput()
{
	//清除input框值
	$("input:password,input:text").each(function(){
		$(this).removeClass("gray2").addClass("gray3");
	});
	$("#pwdPrompt").show();
	//清除错误提示语句
	$("#t_loginName").text("");
	$("#t_passWord").text("");
	$("#t_vCode").text("");
}

//在被嵌套时就刷新上级窗口
if(window.parent != window){
	window.parent.location.reload(true);
}


</script>
</body>

</html>
