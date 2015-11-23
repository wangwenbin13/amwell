<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.util.constants.PropertiesConfig" %>
<script type="text/javascript" src="../js/common/jweixin-1.0.0.js"></script>
<script type="text/javascript">
var nonceStr = "${nonceStr}";
var signature = "${signature}";
var timestamp = parseInt("${timestamp}");
var shareUrl = "${shareUrl}";
var title = "小猪巴士邀你打房东，赢房租！";
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
	    desc: '房东又要涨房租，全民打房东，下手要狠，姿势要美，房租更要免费领！', // 分享描述
	    link: shareUrl, // 分享链接
	    imgUrl: '<%=PropertiesConfig.SERVER_IP%>busOnline/images/games/beatboss/pig300.jpg', // 分享图标
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
        imgUrl: '<%=PropertiesConfig.SERVER_IP%>busOnline/images/games/beatboss/pig300.jpg',
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

function setShare(){
	var url = "../beatboss/addAvailableNum?openId="+'${openId}';
	leaseGetAjax(url,"text",function(data){
    	if(data=="success"){
           	document.location.href="../beatboss/game?openId="+'${openId}';
        }else if(data=="overnum"){
        	alert("分享奖励最多三次");
        }else{
        	alert("您已兑奖,游戏结束");
        }
    });
}
</script>
