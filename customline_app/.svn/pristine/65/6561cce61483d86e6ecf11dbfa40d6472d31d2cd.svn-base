<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
  <title>汇理财请您免费乘车</title>
  <style>
  html,body,p,span {
    padding: 0;
    margin: 0;
  }

  html,body {
    width: 100%;
    height: 100%;
    font-size: 14px;
    background-color: #ee5b11;
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
    display: block;
  }
  .sec-1 {
    height: 100%;
    position: relative;
  }

  form {
    width: 80%;
    position: absolute;
    left: 50%;
    bottom: 60px;
    margin-left: -40%;
  }
  @media (min-height:481px) and (max-height:650px){
    form {
      bottom: 100px;
    }
  }
  @media (min-height:651px){
    form {
      bottom: 150px;
    }
  }

  form p {
    width: 100%;
    padding: 2px 0;
    vertical-align: middle;
  }

  input {
    border-style: solid;
    border-radius: 2px;
    border: 0;
    height: 40px;
    font-size: 16px;
    -webkit-appearance: none;  /* 解决iphone safari上的圆角问题 */
  }
  ::-webkit-inner-spin-button {
    -webkit-appearance: none;  /* 去除上下小箭头按钮 */
  }
  input[name="tel"] {
    width: 100%;
    box-sizing: border-box;
  }
  input[name="vali"] {
    width: 60%;
    box-sizing: border-box;
  }
  input[name="getvali"] {
    margin-left: 4%;
    width: 36%;
    box-sizing: border-box;
    background-color: #50a3e7;
    color: #fff;
  }
  input[name="getvali"].fade {
    background: #c6bdb9;
    -webkit-tap-highlight-color:rgba(0, 0, 0, 0); /* 去掉点击时效果 */
  }
  input[name="sub"] {
    width: 100%;
    box-sizing: border-box;
    background-color: #ff9829;
    color: #fff;
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
    color: #fff;
  }
  .sec-2 .rule {
    background-color: #32ae5e;
    text-align: center;
    width: 40%;
    margin-left: 0;
  }
  .item-1,.item-2,.item-3{
    padding: 20px 0;
  }
  @media (min-width:321px) {
    .item-1,.item-2,.item-3{
      padding: 20px 10px;
    }  
  }
  .sec-2 .item-1 {
    text-align: left;
  }
  .sec-2 .item-2 {
    text-align: right;
  }
  .sec-2 .item-3 {
    text-align: left;
  }
  .pic-1,.pic-2,.pic-3 {
    width: 80px;
  }
  .sec-2 .pic-1 {
    float: left;
  }
  .sec-2 .pic-2 {
    float: right;
  }
  .sec-2 .pic-3 {
    float: left;
  }
  .sec-2 .title {
    color: #ffe749;
    font-size: 1.5em;
  }
  .bottom {
    position: absolute;
    bottom: 0%;
  }
  .clear {
    clear: both;
  }
  </style>
</head>
<body>
  <div id="wrap">
  <section class="sec-1">
    <form action="" method="post" autofocus="autofocus">
      <p><input name="tel" type="number" placeholder="请输入您的手机号码"></p>
      <p><span><input name="vali" placeholder="请输入验证码"></span><span><input name="getvali" type="button" value="获取验证码"></span></p>
      <p><input name="sub" type="submit" value="领取乘车券"></p>
    </form>  
    <div class="slide up">
      <p>一分钟了解汇理财</p>
      <div class="next"><img src="../images/next.png" alt=""></div>
    </div>
    <img src="../images/bg_1_1.png" alt="">
  </section>
  <section class="sec-2">
    <div class="rule">一分钟了解汇理财</div>
    <div class="item-1">
      <div class="pic-1"><img src="../images/item_1.png" alt=""></div>
      <p><span class="title">卓越实力</span><br>香港上市公司背景(股票代码:HK8030)<br>国内首家房地产全产业链平台</p> 
      <div class="clear"></div>
    </div>
    <div class="item-2">
      <div class="pic-2"><img src="../images/item_2.png" alt=""></div>
      <p><span class="title">安全保障</span><br>100%真实房产优势资产<br>15年专业金融风控管理经验</p>
      <div class="clear"></div>
    </div>
    <div class="item-3">
      <div class="pic-3"><img src="../images/item_3.png" alt=""></div>
      <p><span class="title">稳定收益</span><br>多产品年化收益7%-14%<br>存钱罐(随存随取)7日年化收益率达5%</p>
      <div class="clear"></div>
    </div>
    <div class="bottom"><img src="../images/bg_1_bottom.png" alt=""></div>    
    <div class="slide down">
      <div class="next"><img src="../images/pre.png" alt=""></div>
    </div>
  </section>
  </div>

  <!-- 调试手机端 -->
<!--   <script src="http://192.168.9.86:8081/target/target-script-min.js#anonymous"></script> -->
  <script src="../js/jquery-1.11.3.min.js"></script>
  <script src="../js/jquery.touchSwipe.min.js"></script>
  <script src="../js/common.js"></script>
  <script>
  $(function(){
    // 发送验证码
    var isTick = false; // 是否可点击标记
//     $('input[name=getvali]').on('click',function(event){
    	$('input[name=getvali]').on('touchstart',function(event){
    	
        if (isTick) return;
        // 调用发送短信接口
        var value = $('[name=tel]').val();
        // 验证电话号码
        var result = valiTel(value);
        if(result === 1) {
          $.post('http://192.168.9.87:8080/app2/app_hlc/sendHlcMsg.action', {tel: value}, function(data, textStatus, xhr) {
          });

          isTick = true;
          // 显示倒计时
          var self = $(this);
          var i = 120;
          self.addClass('fade');
          self.val(i + 's后重发');
          var timer = setInterval(function (){
            if(i == 0) {
              self.removeClass('fade');
              self.val('获取验证码');
              isTick = false;
              clearInterval(timer);
              return;
            }
            i--;
            self.val(i + 's后重发');
          },1000);            
          
        }
    });

    // 验证手机号码
    function valiTel (value) {
        var reg = /^[0-9]{11}$/g;
        if(!value){
          alert('电话号码不能为空');
          return 0;
        }else{
          reg.exec('');
          if(!reg.exec(value)) {
            alert('请输入正确的电话号码');
            return 0;
          }
        }
        return 1;
    }
    
    var isSend = false;
    // 点击领取
    $('form').submit(function(event) {
        event.preventDefault();
        if(isSend) return;
        isSend = true;
        $('[name=sub]').addClass('fade');
        var tel = $('[name=tel]').val();
        var passcode = $('[name=vali]').val();
        
        // 验手机号码
        var result = valiTel(tel);
        if(result === 0) return;

        // 判断验证码是否为空
        if(passcode === "") {
          alert('验证码不能为空');
          return;
        }
        // 发送电话号码和验证码
        $.post('http://192.168.9.87:8080/app2/app_hlc/validateHlcMsg.action', {tel: tel,code: passcode}, function(data, textStatus, xhr) {
        	data=eval(data);
        	var res=data['a1'];
        	if(res == 'h0000'){
            // 验证注册成功后跳转
            document.location.href="../go.jsp";
          }else{
            if(res == 'h1') alert("不好意思，手机号验证失败，请重试！");
            if(res == 'h2') alert("对不起，您的手机已经注册。");
            if(res == 'h3') alert("不好意思，系统异常，请稍后重试！");
          }
          isSend = false;
          $('[name=sub]').removeClass('fade');
        });
        return;
    });
  })
  </script>
</body>
</html>