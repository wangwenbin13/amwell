<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.util.constants.PropertiesConfig" %>
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
	<title>上班者联盟-结果页面</title>
	<link rel="stylesheet" type="text/css" href="../css/fcz.css?v=123">
</head>
<body class="grayBg">
	<article>
		<div class="main">
			<!--击打结果 atart-->
            <div class="gameResulteOut">
                <div class="gameResulte">
                    <!--头像变灰 追加样式gray,去掉则头像变亮-->
                    <span class="resulteItem" id="thirdPrideIcon"></span>
                    <span class="resulteItem" id="secondPrideIcon"></span>
                    <span class="resulteItem" id="firstPrideIcon"></span>  
                </div>
                <p class="mt15 yellow"><span id="callName">我共累计</span>打了BOSS&nbsp;&nbsp;<span class="totalPointSpan" id="helpPoint">0</span>&nbsp;&nbsp;拳</p>
                <p class="yellow"><span style="display:none;">，超过了60％的同行,</span>获得复联<span id="prideCallSpan">小队长</span>称号！</p> 
                <a href="javascript:tryAgain();" id="mainAgain" class="rBtn">再来一次</a>
                <a href="javascript:selectPride();" id="mainPride" class="rBtn">兑换奖品</a>
                <a href="../fcz/index?openId=${helperOpenId}" class="rBtn mt10" id="mainMe">我也要发起联盟</a>
            </div>
            <!--击打结果 end-->
            
            <!--兑完奖后 atart-->
            <div class="gameResulteOutGift">
                <span class="giftEle shouban"></span>
                <span class="giftEle huafei"></span>
                <span class="giftEle busquan"></span>
                <p><span id="prideCallNameId">你的小伙伴</span>已成功兑换奖品<span id="prideNameSpan">复仇者联盟手办</span></p>
                <p>（奖品发放事宜敬请留意小猪巴士微信公众号）</p>
                 <a href="../fcz/index?openId=${helperOpenId}" class="rBtn" id="mainMeAfterPride" style="display:none;">我也要发起联盟</a>
            </div>
            <!--兑完奖后 end-->
            
            <!--排行榜 start-->
            <section>
            	<div class="resulteList">
                	<h2 class="rTitle">____联盟战力排行榜____</h2>
                    <p>累计拳数：<span class="totalPointSpan"></span></p>
                    <ul class="resulteList-item " id="helpListUL"></ul>
                </div>
            </section>
            <!--排行榜 end-->
		</div>
	</article>
	
    <!-- 兑奖弹窗 start-->
    <div class="boxOut" id="prideSelectPanel" style="display:none;">
    	<div class="boxDiv boxDivBgBlue">
        	<a href="javascript:closePop();" class="close"></a>
        	<div class="box boxBgBlue">
            	<div class="radioBox"></div>
                <a href="javascript:setPrideName();" class="bBtn">兑换</a>
                <a href="javascript:tryAgain();" class="bBtn ml15 bBtnS">我要继续</a>
            </div>
        </div>
    </div>
 	<!-- 兑奖弹窗 end-->
 	
 	<!-- 体力用完弹窗 start-->
    <div class="boxOut" id="noEnergy" style="display:none;">
    	<div class="boxDiv boxDivBgGray">
        	<a href="javascript:closePop();" class="close"></a>
        	<div class="box boxBgGray">
        		<div class="noTipsList">
                  	<p>你的体力已用完</p>
                     <p>联盟好友来帮忙</p>
                     <p>（分享给好友即可）</p>
                  </div>
            	<div class="noTips"></div>
            </div>
        </div>
    </div>
 	<!-- 体力用完弹窗 end-->
 	
  	<!-- 输入手机号码弹窗 start-->
    <div class="boxOut" id="telephonePanel" style="display:none;">
        <div class="boxDiv boxDivBgYellow p-r">
            <a href="javascript:closePop();" class="close"></a>
            <div class="box boxBgYellow  p-r">
                <p class="f18 white">输入手机号码，体力倍增</p>
                <p><input type="tel" id="telephone"/></p>
                <p><input type="button" onclick="setTelephone()" class="telBtn" value="提交"/></p>
            </div>
        </div>
    </div>
 	<!-- 输入手机号码弹窗 end-->
 	
 	<form id="setPrideNameForm">
 	    <input type="hidden" name="storeId"/>
 	    <input type="hidden" name="prideName"/>
 	    <input type="hidden" name="openId"/>
 	    <input type="hidden" name="telephone"/>
 	    <input type="hidden" name="availablePlayNum"/>
 	</form>
</body>
</html>
<script type="text/javascript" src="../js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/common/common_util.js"></script>
<script type="text/javascript">
var fczOwner=null;
var fczHelper=null;
var ownerOpenId="${ownerOpenId}";
var helperOpenId="${helperOpenId}";
var isAvailableDate="${isAvailableDate}";

$(document).ready(function(){
	$(".resulteItem").attr("class","resulteItem gray");
	$(".gameResulteOut").hide();
    $(".gameResulteOutGift").hide();
	loadOwner();
	loadHelperList();
});

//加载盟主信息
function loadOwner(){
	leaseGetAjax("../fczAjax/getOwner?openId="+ownerOpenId,"json",function(data){
        if(data.a1=="100"){
            try{
	            var fczOwnerJson = data.a2;
	            if(fczOwnerJson!=null){
	            	fczOwner = new Object();
	                fczOwner.storeId=fczOwnerJson.storeId;
	                fczOwner.openId=fczOwnerJson.openId;
	                fczOwner.nickname=fczOwnerJson.nickname;
	                fczOwner.telephone=fczOwnerJson.telephone;
	                fczOwner.totalPoint=fczOwnerJson.totalPoint;
	                fczOwner.prideName=fczOwnerJson.prideName;
	                fczOwner.availablePlayNum=fczOwnerJson.availablePlayNum;
	                fczOwner.isShare=fczOwnerJson.isShare;
	                loadResultPage();
	                lightPrideIcon();
	                if((helperOpenId!=null&&helperOpenId!=""&&helperOpenId!=undefined)&&(helperOpenId!=ownerOpenId)){
	                	loadHelper();
	                	$("#callName").html("我帮好友");
	                	$("#prideCallNameId").html("你的小伙伴");
	                	$("#mainAgain").hide();
	                	$("#mainPride").hide();
	                	$("#mainMeAfterPride").show();
		            }else{
		            	$("#callName").html("我共累计");
		            	$("#prideCallNameId").html("我");
		            	$("#mainMe").hide();
			        }
	            }
            }catch(e){alert(e);}
        }else{
            alert("加载盟主信息失败。");
        }
    });
}

function loadHelper(){
	leaseGetAjax("../fczAjax/getHelper?ownerOpenId="+ownerOpenId+"&helperOpenId="+helperOpenId,"json",function(data){
        if(data.a1=="100"){
            try{
	            var fczHelperJson = data.a2;
	            if(fczHelperJson!=null){
	            	fczHelper = new Object();
	            	fczHelper.storeId=fczHelperJson.storeId;
	            	fczHelper.ownerOpenId=fczHelperJson.ownerOpenId;
	            	fczHelper.helperOpenId=fczHelperJson.helperOpenId;
	            	fczHelper.nickname=fczHelperJson.nickname;
	            	fczHelper.photoImage=fczHelperJson.photoImage;
	            	fczHelper.point=fczHelperJson.point;
	            	fczHelper.helpTime=fczHelperJson.helpTime;
	            	$(".gameResulteOut").show();
	                $(".gameResulteOutGift").hide();
	                $("#helpPoint").html(fczHelper.point);
	            }
            }catch(e){alert(e);}
        }else{
            alert("加载盟主信息失败。");
        }
    });
}

//加载结果页
function loadResultPage(){
	if(fczOwner!=null){
       if(fczOwner.prideName==null||fczOwner.prideName==undefined||fczOwner.prideName==""){
           $(".gameResulteOut").show();
           $(".gameResulteOutGift").hide();
           lightPrideIcon();
       }else{
    	   $(".gameResulteOut").hide();
           $(".gameResulteOutGift").show();
           loadPrideResult();
       }
    }else{
    	$(".gameResulteOut").hide();
        $(".gameResulteOutGift").hide();
    }
}

//点亮获奖等级图标
function lightPrideIcon(){
	try{
		if(fczOwner!=null){
	        var totalPoint = fczOwner.totalPoint;
	        if(totalPoint!=null){
	            $(".totalPointSpan").html(totalPoint);
	            if(totalPoint>=3000){
	                $("#firstPrideIcon").removeClass("gray");
	                $("#secondPrideIcon").removeClass("gray");
	                $("#thirdPrideIcon").removeClass("gray");
	                $("#prideCallSpan").html("大队长");
	            }else if(totalPoint>=1500){
	            	$("#secondPrideIcon").removeClass("gray");
	            	$("#thirdPrideIcon").removeClass("gray");
	            	$("#prideCallSpan").html("中队长");
	            }else if(totalPoint>=200){
	            	$("#thirdPrideIcon").removeClass("gray");
	            	$("#prideCallSpan").html("大队长");
	            }else{
	               //不做处理
	               $("#prideCallSpan").html("小队长");
	            }
	        }
	    }
	}catch(e){alert(e);}
}

//加载奖品列表
function loadPrideResult(){
	$(".giftEle").hide();
	if(fczOwner!=null){
       var prideName = fczOwner.prideName;
       $("#prideNameSpan").html(prideName);
       if(prideName=="50元小猪巴士乘车劵"){
          $(".busquan").show();
       }else if(prideName=="30元手机话费"){
    	   $(".huafei").show();
       }else if(prideName=="复联英雄手办"){
    	   $(".shouban").show();
       }else{
          //不做处理
       }
    }
	var totalPoint = fczOwner.totalPoint;
    if(totalPoint!=null){
        $(".totalPointSpan").html(totalPoint);
    }
}

//加载帮手成绩列表
function loadHelperList(){
	leaseGetAjax("../fczAjax/getHelperList?ownerOpenId="+ownerOpenId,"json",function(data){
        if(data.a1=="100"){
            var helpList = data.a2;
            if(helpList!=null){
                var helpListHtml="";
                for(var i=0;i<helpList.length;i++){
                	helpListHtml+="<li class=\"clearfix\">";
                	helpListHtml+="<span class=\"time\">"+helpList[i].helpTime+"</span>";
                	helpListHtml+="<img src=\""+helpList[i].photoImage+"\">";
                    helpListHtml+="<span class=\"user\"><em>"+helpList[i].nickname+"</em> 帮我打了"+helpList[i].point+"拳</span>";
                    helpListHtml+="</li>";
                }
                $("#helpListUL").html(helpListHtml);
            }
        }else{
            alert("加载帮忙排行傍失败。");
        }
    });
}

//继续挑战
function tryAgain(){
	$(".boxOut").hide();
	if(fczOwner!=null){
		if(fczOwner.availablePlayNum!=null&&fczOwner.availablePlayNum>0){
            if(fczOwner.telephone!=null&&fczOwner.telephone!=""){
            	if(helperOpenId==null||helperOpenId==""||helperOpenId==undefined){
	            	//前往殴打页面
	                document.location.href="../fcz/gameStart?ownerOpenId="+ownerOpenId+"&type=0";
		        }else{
		        	//前往殴打页面
	                document.location.href="../fcz/gameStart?ownerOpenId="+ownerOpenId+"&helperOpenId="+helperOpenId+"&type=1";
			    }
            }else{
                $("#telephonePanel").show();
            }
	    }else{
            $("#noEnergy").show();
		}
	}
}

//设置电话号码
function setTelephone(){
	try{
		if(fczOwner!=null){
			var telephoneValue = $("#telephone").val();
			if(telephoneValue==null||telephone==""||telephone==undefined){
                 alert("请输入手机号码");
                 return;
			}
			var myreg = /^(1\d{10})$/;
			if(!myreg.test(telephoneValue)){
                 alert("手机格式不正确");
                 return;
			}
			$("[name='telephone']").val($("#telephone").val());
			$("[name='openId']").val(ownerOpenId);
			$("[name='storeId']").val(fczOwner.storeId);
			$("[name='availablePlayNum']").val(fczOwner.availablePlayNum);
			if(isAvailableDate=="false"){
			        alert("不在活动时间");
			        return;
			}
	        leasePostAjax("../fczAjax/editOwner",$("#setPrideNameForm"),"json",function(data){
		          if(data.a1=="100"){
			            if(helperOpenId==null||helperOpenId==""||helperOpenId==undefined){
			            	//前往殴打页面
			                document.location.href="../fcz/gameStart?ownerOpenId="+ownerOpenId+"&type=0";
				        }else{
				        	//前往殴打页面
			                document.location.href="../fcz/gameStart?ownerOpenId="+ownerOpenId+"&helperOpenId="+helperOpenId+"&type=1";
					    }
		          }else{
		                alert("保存电话号码错误。");
		          }
	        });
	    }
	}catch(e){alert(e);}
}

//兑换奖品
function selectPride(){
	if(fczOwner!=null){
        var prideRadioHtml="";
        if(fczOwner.totalPoint>=200){
            prideRadioHtml+="<p><input type=\"radio\" name=\"prideNameRd\" class=\"prideRadioItem\" prideName=\"50元小猪巴士乘车劵\"/>50元小猪巴士乘车劵</p>";
        }
        if(fczOwner.totalPoint>=1500){
             prideRadioHtml+="<p><input type=\"radio\" name=\"prideNameRd\" class=\"prideRadioItem\" prideName=\"30元手机话费\"/>30元手机话费</p>";
        }
        if(fczOwner.totalPoint>=3000){
             prideRadioHtml+="<p><input type=\"radio\" name=\"prideNameRd\" class=\"prideRadioItem\" prideName=\"复联英雄手办\"/>复联英雄手办</p>";
        }
        if(prideRadioHtml==""){
        	$(".radioBox").html("暂无可兑奖品。");
        }else{
        	$(".radioBox").html(prideRadioHtml);
        }
        $("#prideSelectPanel").show();
    }
}

//兑换奖品
function setPrideName(){
	$("#setPrideNameForm [name='storeId']").val(fczOwner.storeId);
	$("#setPrideNameForm [name='openId']").val(fczOwner.openId);
	$("#setPrideNameForm [name='availablePlayNum']").val(fczOwner.availablePlayNum);
	$("#setPrideNameForm [name='prideName']").val($(".prideRadioItem:checked").attr("prideName"));
	if($(".prideRadioItem:checked").attr("prideName")==null||$(".prideRadioItem:checked").attr("prideName")==""||$(".prideRadioItem:checked").attr("prideName")==undefined){
        closePop();
        return;
    }
    if(isAvailableDate=="false"){
        alert("不在活动时间");
        return;
    }
	leasePostAjax("../fczAjax/editOwner",$("#setPrideNameForm"),"json",function(data){
        if(data.a1=="100"){
              document.location.href="../fcz/goResult?ownerOpenId="+ownerOpenId;
        }else{
              alert("兑换错误。");
        }
    });
}

//关闭弹窗
function closePop(){
	$(".boxOut").hide();
}

function setShare(){
	if(fczOwner.isShare==0||fczOwner.isShare==""||fczOwner.isShare==undefined){
		var availablePlayNum = fczOwner.availablePlayNum+1;
		var url = "../fczAjax/editOwner?storeId="+fczOwner.storeId+"&isShare=1&availablePlayNum="+availablePlayNum+"&totalPoint="+fczOwner.totalPoint;
		leaseGetAjax(url,"json",function(data){
             if(data.a1=="100"){
            	 document.location.href="../fcz/gameStart?ownerOpenId="+fczOwner.openId+"&type=0";
             }else{
                 alert("分享奖励失败");
             }
	    });
	}
}
</script>

<script type="text/javascript" src="../js/common/jweixin-1.0.0.js"></script>
<script type="text/javascript">
		
/*
 * 注意：
 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
 * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 *
 * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
 * 邮箱地址：weixin-open@qq.com
 * 邮件主题：【微信JS-SDK反馈】具体问题
 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
 */
var nonceStr = "${nonceStr}";
var signature = "${signature}";
var timestamp = parseInt("${timestamp}");
var shareUrl = "${shareUrl}";
var nickname = "${nickname}";
var title = "一起来帮"+nickname+"打大Boss啦！";
wx.config({
    debug: false,
    appId: "<%=PropertiesConfig.WEIXIN_APPID%>",
    timestamp: timestamp,
    nonceStr: nonceStr,
    signature: signature,
    jsApiList: [
      'checkJsApi',
      'onMenuShareTimeline',
      'onMenuShareAppMessage',
      'onMenuShareQQ',
      'onMenuShareWeibo',
      'hideMenuItems',
      'showMenuItems',
      'hideAllNonBaseMenuItem',
      'showAllNonBaseMenuItem',
      'translateVoice',
      'startRecord',
      'stopRecord',
      'onRecordEnd',
      'playVoice',
      'pauseVoice',
      'stopVoice',
      'uploadVoice',
      'downloadVoice',
      'chooseImage',
      'previewImage',
      'uploadImage',
      'downloadImage',
      'getNetworkType',
      'openLocation',
      'getLocation',
      'hideOptionMenu',
      'showOptionMenu',
      'closeWindow',
      'scanQRCode',
      'chooseWXPay',
      'openProductSpecificView',
      'addCard',
      'chooseCard',
      'openCard'
    ]
});
wx.ready(function(){
	wx.onMenuShareAppMessage({
	    title: title, // 分享标题
	    desc: '一起来来参加上班者联盟啦，还有机会获得大奖！', // 分享描述
	    link: shareUrl, // 分享链接
	    imgUrl: '<%=PropertiesConfig.SERVER_IP%>huodong/images/fczimages/indexTopBg2.jpg', // 分享图标
	    type: '', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function (res) { 
	    	setShare();
	    },
	    cancel: function () { 
	        //alert('已取消');
	    }
	});

	// 2.2 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
    wx.onMenuShareTimeline({
        title:title,
        link: shareUrl,
        imgUrl: '<%=PropertiesConfig.SERVER_IP%>huodong/images/fczimages/indexTopBg2.jpg',
        success: function (res) {
        	setShare();
        },
        cancel: function (res) {
          //alert('已取消');
        }
      });
});



wx.error(function (res) {
    //alert("error:"+JSON.stringify(res));
});
</script>