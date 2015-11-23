<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../v1/template/headMeta.jsp" %>
<link rel="stylesheet" type="text/css" href="../css/beatboss/beatboss.css">
</head>
<body style="padding:0 !important">
	<div class="model"></div>
	<div class="main bg-3">
		<div class="game-result">
			<div class="result-in">			
					<p><span></span>我打了房东&nbsp;<span class="total-fist">${owner.totalPoint}</span>&nbsp;拳，超过了<span class="over-per">${beyond}％</span>的同行</p>
					<div id="prides">
						<img id="ticket" src="" alt="">
					</div>
		      <div id="continue">
		      	<div id="btn-next"></div>
		      	<img src="../images/games/beatboss/btn_next.png" alt="">
		      </div>
      </div>
      <img src="../images/games/beatboss/result.png" alt="">
		</div>

		<!--排行榜 start-->
		<div class="resulteList">
			<div class="rank-title"><p>我的拳数<span class="total-fist">&nbsp;&nbsp;${owner.totalPoint}</span><p></div>
			<div class="helpers">
				<table>
					<c:forEach items="${owners}" var="ownerItem" varStatus="status">
          				<tr><td>${status.index+1}、</td><td>${ownerItem.nickname}</td><td>打了<span>${ownerItem.totalPoint}</span>拳</td></tr>
					</c:forEach>
				</table>
			</div>
			<img src="../images/games/beatboss/rank.png" alt="">
		</div>
		<!--排行榜 end-->
	  <div class="share">
	  	<img src="../images/games/beatboss/share.png" alt="">
	  	<p>点击右上角分享至<span class="sp1">朋友圈</span></p>
	  	<p>即可继续打房东赢<span class="sp2">500元</span>房租</p>
	    <span class="iknow"></span>
	  </div>

		<div class="rule">
				<div class="rule-des">
					<p>1.最高成绩达到相应的拳数即可兑换奖品，300拳=小猪巴士5元代金券，400拳=小猪巴士10元代金券，游戏结束后成绩700拳以上并且排名前五，将会获得小猪巴士赞助的500元房租。</p>
					<p>2.“小猪巴士”工作人员将在活动结束后一周内发放奖品。</p>
					<p>3. 活动时间：截止2015年10月31日。</p>
					<p>4. 本活动最终解释权归小猪巴适科技有限公司所有。</p>
				</div>
				<img class="btn-rule" src="../images/games/beatboss/rule.png" alt="">
		</div>
	</div>

	
	<%@include file="../v1/template/loginView.jsp" %>
	<%@include file="../v1/template/tipView.jsp" %>
	<%@include file="../v1/template/loadingView.jsp" %>
</body>
</html>
<%@include file="../v1/template/footJavascript.jsp" %>
<script type="text/javascript" src="http://img.pig84.com/download/100000000011"></script>
<script type="text/javascript" src="../js/common/common_util.js"></script>
<script src="../js/beatboss/jquery.touch.min.js"></script>
<script type="text/javascript">
var totalPoint = ${owner.totalPoint};
var openId = '${openId}';

setLoginSuccessCallback(save);

$(function() {
	// 
	$('.model').height($('body').height());
		
	// 显示奖品与继续
	if(totalPoint>300&&totalPoint<400){
		$('#ticket').attr('src', '../images/games/beatboss/ticket_5.png');
		$('#btn-next').html('继续游戏赢取10元优惠券');
	}else if(totalPoint>400){
		$('#ticket').attr('src', '../images/games/beatboss/ticket_10.png');
		$('#btn-next').html('继续游戏赢取500元房租');
	}else{
		$('#prides').html('<p>暂无可兑换奖品</p>');
		$('#btn-next').html('继续游戏赢取5元优惠券');
	}
	
	
	// 继续游戏
	$('#continue').tap(function () {
		var availableNum = ${owner.availablePlayNum};
		if(availableNum>0){
			document.location.href="../beatboss/game?openId="+openId;
	    }else{
	        $('.share,.model').fadeIn();
	    }
	});
	
  $('.iknow').tap(function () {
    $('.share,.model').fadeOut();
  });
  
  // 点击兑换
  $('#ticket').tap(function () {
  	getReward();
  });

  var open = false;
  $('.rule').tap(function () {
  	if (open) {
  		$(this).animate({right: '-270px'}, 0.5)
  	}else{
  		$(this).animate({right: '0px'}, .5);
  	}
  	open = !open;
  })
});


function getReward(){
	leaseGetAjax("../beatboss/getReward?openId="+openId,"text",function(data){
  		if(data=="nologin"){
			localStorage.setItem("channel","WX");
			localStorage.setItem("openId",openId);
			forwardHash('#login');
		}else if(data==""){
  			alert("暂无可兑换奖品");
		}else{
			alert("已兑"+data+",工作人员将在活动结束后7个工作日内把奖品下发");
		}
    });
}

function save(){
	getReward();
}

</script>
<jsp:include page="wxShare.jsp"></jsp:include>
