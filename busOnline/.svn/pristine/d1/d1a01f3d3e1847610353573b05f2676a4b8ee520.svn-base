<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.util.constants.PropertiesConfig" %>
<script type="text/javascript" src="../js/common/jweixin-1.0.0.js"></script>
<script type="text/javascript">
var nonceStr = "${nonceStr}";
var signature = "${signature}";
var timestamp = parseInt("${timestamp}");
var shareUrl = "${shareUrl}";
var title = {
		'a0': '被鉴定了为1级孤独，击败了全国8.45%的好友~',
		'a1': '被鉴定了为3级孤独，击败了全国56.37%的好友~',
		'a2': '被鉴定了为5级孤独，击败了全国89.24%的好友~',
		'a3': '被鉴定了为8级孤独，击败了全国99.42%的好友~'
};

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
	    title: '${nickname}'+title[resultIndex], // 分享标题
	    desc: '你也快来测测你的孤独指数吧！', // 分享描述
	    link: shareUrl, // 分享链接
	    imgUrl: '<%=PropertiesConfig.SERVER_IP%>busOnline/images/lonelytest/' + resultIndex + '.jpg', // 分享图标
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
        title:'${nickname}'+title[resultIndex],
        link: shareUrl,
        imgUrl: '<%=PropertiesConfig.SERVER_IP%>busOnline/images/lonelytest/' + resultIndex + '.jpg',
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
