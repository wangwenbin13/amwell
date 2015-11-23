<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
  <title>汇理财请您免费乘车</title>
  <style>
  html,body,p,span,img {
    padding: 0;
    margin: 0;
  }

  html,body {
    width: 100%;
    height: 100%;
    font-size: 14px;
    background-color: #f15d13;
  }
  
  #wrap {
    width: 100%;
    height: 100%;
    overflow: hidden;
    text-align: center;
    z-index: 9;
  }
  img {
    max-width: 100%;
    display: block; /* 消除3px bug */
  }
  .sec-1 {
    height: 100%;
    position: relative;
    color: #fff;
  }
  @media (max-height: 480px) {
    .p1 {
      font-size: 2em;
      padding-top: 60px;
    }
    .p2 {
      font-size: 1.6em;
    }
    .p3 {
      margin-top: 20px;
      margin-bottom: 10px;
    }
    .p4 {
      margin-top: 40px;
    }
  }
  @media (min-height:481px) and (max-height:650px) {
     .p1 {
      font-size: 2em;
      padding-top: 100px;
    }
    .p2 {
      font-size: 1.6em;
    }
    .p3 {
      margin-top: 50px;
      margin-bottom: 10px;
    }
    .p4 {
      margin-top: 60px;
    }   
  }
  @media (min-height:651px) {
     .p1 {
      font-size: 2em;
      padding-top: 140px;
    }
    .p2 {
      font-size: 1.6em;
    }
    .p3 {
      margin-top: 80px;
      margin-bottom: 10px;
    }
    .p4 {
      margin-top: 80px;
    }   
  }  
 
  .btn-50 {
    width: 80%;
    margin: 0px auto;
  }
  .btn-follow {
    background-color: #50a3e7;
    border-radius: 2px;
    margin-left: 4px;
    padding: 4px;
  }
  .slide {
    position: absolute;
    bottom: 10px;
    width: 100%;
    color: #fff;
  }
  .slide .next {
    width: 18px;
    height: 20px;
    margin: 0 auto;
    -webkit-animation: flash 1.3s infinite;
    animation: flash 1.3s infinite;
  }
  @-webkit-keyframes flash {
    from {-webkit-opacity: 0;}
    50% {-webkit-opacity: 0.5;}
    to {-webkit-opacity: 1;}
  }
  @keyframes flash {
    from {opacity: 0;}
    50% {opacity: 0.5;}
    to {opacity: 1;}
  }

  .sec-2 {
    height: 100%;
    position: relative;
    text-align: left;
  }
  .sec-2 .rule {
    background-color: #32ae5e;
    text-align: center;
    margin-left: 0;
    width: 40%;
  }
  .sec-2 p {
    color: #fff;
    padding: 5px 20px 5px 40px;
  }
  .sec-2 .num {
    display: inline-block;
    border-radius: 50%;
    width: 20px;
    height: 20px;
    margin: 0 6px 0 -26px;
    line-height: 20px;
    text-align: center;
    color: #cf3e3a;
    background-color: #fee565;
  }
  .top,.bottom {
    position: absolute;
    width: 100%;
  }
  .top {
    top: 10%;
  }
  @media (max-height:480px) {
    .top {
      top: 5%;
    }
  }
  .bottom {
    bottom: 0%;
  }
  </style>
</head>
<body>
  <div id="wrap">
  <section class="sec-1">
    <div class="top"><img src="images/bg_2_top.png" alt=""></div>
    <div class="bottom"><img src="images/bg_2_bottom.png" alt=""></div>
    <p class="p1">10元优惠券</p>
    <p class="p2">已发送到您的小猪账号</p>
    <p class="p3">提醒：您还有50元零门槛乘车券尚未领取</p>
    <div class="btn-50"><img src="images/btn_50.png" alt=""></div>
    <p class="p4">关注汇理财微信公众号,获取更多理财优惠<span class="btn-follow">关注</span></p>
    <div class="slide up">
      <p>活动规则</p>
      <div class="next"><img src="images/next.png" alt=""></div>
    </div>
  </section>
  <section class="sec-2">
    <div class="bottom"><img src="images/bg_2_bottom.png" alt=""></div>
    <div class="rule">活动规则</div>
    <p><span class="num">1</span>所有小猪巴士用户均可参加此次活动。</p>
    <p><span class="num">2</span>输入手机号成功注册汇理财，即可领取10元无门槛乘车优惠券。已经注册汇理财的手机号码不能参与此活动。</p>
    <p><span class="num">3</span>通过本活动注册的用户在活动期间内首次投资汇理财任意产品满3000元，除享受产品高收益外还可额外获得50元无门槛乘车券(50元无门槛乘车券会在24小时之内返还到您的小猪账户内)。</p>
    <p><span class="num">4</span>小猪巴士用户可以参加汇理财平台所有活动，投资还有机会抽取IPhone6S大奖。</p>
    <p><span class="num">5</span>通过此渠道注册成功的用户才返乘车券，其他渠道注册汇理财平台，不享受乘车券奖励。</p>
    <p><span class="num">6</span>关于乘车券发放、领取等相关问题，可咨询汇理财客服热线：400-966-0198(工作时间：9:00-18:00)。</p>
    <p><span class="num">7</span>本活动最终解释权归汇联汇理财所有。</p>
    <div class="slide down">
      <div class="next"><img src="images/pre.png" alt=""></div>
    </div>
  </section>
  
  </div>

  <!-- 调试手机端 -->
<!--   <script src="http://192.168.9.86:8081/target/target-script-min.js#anonymous"></script> -->
  <script src="js/jquery-1.11.3.min.js"></script>
  <script src="js/jquery.touchSwipe.min.js"></script>
  <script src="js/common.js"></script>
  <script>

    $('.btn-50').swipe({
      // 可能要向后端发送数据
      // 跳转到汇理财
      tap: function(event,target){
        document.location.href="http://www.huilc.cn/WeChat/page/weChatIndex.jsp";
      }
    });

    $('.btn-follow').swipe({
      // 添加关注汇理财公众号
      tap: function(event,target){
        document.location.href="http://mp.weixin.qq.com/s?__biz=MjM5NDMyOTkzOQ==&mid=400133610&idx=1&sn=268f6956345abc1775549959e621d856#wechat_redirect";
      }
    });


  </script>
</body>
</html>