<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="pragma" content="no-cache"> 
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0"> 
	<meta charset="UTF-8">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no" name="format-detection">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>上班者联盟-开始游戏</title>
	<link rel="stylesheet" type="text/css" href="../css/fcz.css">
    <script type="text/javascript" src="../js/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="../js/jquery/fastclick.js"></script>

</head>
<body class="greenBg" onload="stnum()">
    <div class="mainDiv"></div>
	<article>
		<div class="main">
			<div class="hand"></div>
            <p id="startNum"><span class="num">3</span></p>
            <div class="boss p-r"><span class="bang"></span></div>
            <div class="countDiv">
                <div class="count clearfix">
                    <span class="countNum fist" id="click_num">0</span>
                    <span class="countNum clock" id="timerc">10</span>  
                </div>
            </div>
		</div>
	</article>
	<form id="gameStartForm">
	    <input type="hidden" name="nickname" value="${nickname}"/>
	    <input type="hidden" name="photoImage" value="${photoImage}"/>
	    <input type="hidden" name="type" value="${type}"/>
	    <input type="hidden" name="totalPoint" value="${totalPoint}"/>
	    <input type="hidden" name="ownerOpenId" value="${ownerOpenId}"/>
	    <input type="hidden" name="helperOpenId" value="${helperOpenId}"/>
	</form>
</body>
<script type="text/javascript" src="../js/common/common_util.js"></script>
<script type="text/javascript">
window.addEventListener('load', function() {
    FastClick.attach(document.body);
}, false);

var type="${type}";
var ownerOpenId="${ownerOpenId}";
var helperOpenId="${helperOpenId}";

$(".mainDiv,.main").css({"height":$(window).height()});

$(document).ready(function(){
	if(type=="0"){
		if(ownerOpenId==null||ownerOpenId==""){
           alert("加载用户数据失败");
           document.location.href="../fcz/index";
           return;
		}
		leaseGetAjax("../fczAjax/getOwner?openId="+ownerOpenId,"json",function(data){
		       if(data.a1=="100"){
		           var fczOwner = data.a2;
		           if(fczOwner.prideName!=null&&fczOwner.prideName!=""&&fczOwner.prideName!=undefined){
		        	   document.location.href="../fcz/goResult?ownerOpenId="+ownerOpenId;
		        	   return;
			       }
		           if(fczOwner.availablePlayNum>0){
		        	  //
		           }else{
		        	   document.location.href="../fcz/goResult?ownerOpenId="+ownerOpenId;
		           }
		       }else{
		           alert("查询失败。");
		       }
			});
    }else{
        if(ownerOpenId==null||ownerOpenId==""||helperOpenId==null||helperOpenId==""){
             alert("加载盟主数据失败");
             document.location.href="../fcz/index?ownerOpenId="+ownerOpenId;
             return;
         }
         leaseGetAjax("../fczAjax/getHelper?ownerOpenId="+ownerOpenId+"&helperOpenId="+helperOpenId,"json",function(data){
        	 if(data.a1=="100"){
		           var fczHelper = data.a2;
		           if(fczHelper==null){
		        	   //
		           }else{
		        	   document.location.href="../fcz/goResult?ownerOpenId="+ownerOpenId+"&helperOpenId="+helperOpenId;
		           }
		       }else{
		           alert("查询失败。");
		       }
         });
    }
});

var i= 0;
$(function(){
    $(".main").on("touchstart",function(e){
         $(".hand").stop(!0,!1).animate({"top":184+"px"},30,function(){
                    $(".bang").show();
                $(".hand").stop(!0,!1).animate({"top":0+"px"},20,function(){
                    $(".bang").hide();
                });
             })
         e.preventDefault();
         i++;
         $("#click_num").html(i);
    })
})

var num = 4;
function stnum(){
    if(num == 1){
      $("#startNum").html("<span class=\"go\"></span>");
      num--;
      setTimeout("stnum()", 1000);
    }else if(num==0){
      $("#startNum").html("");
      $(".mainDiv").hide();
      subnum();
    }else{
      $("#mainDiv").hide();
      num--; 
      $("#startNum").html("<span class=\"num\">"+num+"</span>"); 
      setTimeout("stnum()", 1000);
    }
}

/*底部倒计时 拳数累计*/
var timerc = 10;
function subnum(){
    if(timerc == 1){
        timerc = 0;
        $("#timerc").html(timerc);
        $(".mainDiv").show();
        var totalPoint=$("#click_num").html();
		var url ="../fcz/updateOwner";
		$("[name='totalPoint']").val(totalPoint);
		$("[name='helperOpenId']").val(helperOpenId);
		$("[name='ownerOpenId']").val(ownerOpenId);
		$("[name='type']").val(type);
		leasePostAjax(url,$("#gameStartForm"),"text",function(data){
			if(data=="100"){
				if(type==0){
					document.location.href="../fcz/goResult?ownerOpenId="+ownerOpenId;
				}else{
					document.location.href="../fcz/goResult?ownerOpenId="+ownerOpenId+"&helperOpenId="+helperOpenId;	
				}
			}else if(data=="101"){
                alert("加载用户数据错误Err101");
			}else if(data=="102"){
                alert("加载用户数据错误Err102");
			}else{
                alert("内部错误");
			}
		});
    }else{
      $("#mainDiv").hide();
      --timerc; 
      $("#timerc").html(timerc); 
      setTimeout("subnum()", 1000);
    }
}
</script>
</html>