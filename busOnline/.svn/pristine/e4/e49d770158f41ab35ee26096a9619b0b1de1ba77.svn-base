<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
	#tipSpace {
		position: fixed;
		left: 0px;
		top: 0px;
		width: 100%;
		height: 100%;
		z-index: 15;
		display: none;
		background: rgba(0, 0, 0, 0.2);
	}
	
	#tipSpace div {
		width: 264px;
		padding: 8px;
		line-height: 28px;
		background: rgba(255, 255, 255, 0.9);
		border-radius: 14px;
		position: absolute;
	}
	
	#tipSpace a {
		line-height: 28px;
		display: block;
		margin: 0px auto;
		width: 96px;
		font-size: 18px;
		color: #54dcaa;
		text-align: center;
	}
	
	#tipSpace p {
		color: #606060;
		font-size: 16px;
		text-align: center;
		padding: 12px;
		word-break: break-all;
	}
</style>
<div id="tipSpace">
	<div>
		<p></p>
		<a href="javascript:onTipHide();">确认</a>
	</div>
</div>
<script>
	/** 登录框显示； */
	function alert(tip) {
		onTipShow(tip);
	}
	
	function onTipShow(tip, title) {
		var elem = $('#tipSpace');
		$('#tipSpace p').html(tip || '');
		elem.fadeIn(40, function () {
			var space = $('#tipSpace > div'), win = $(window);
			space.css({
				left: (win.width() - space.width() - 16 >> 1) + 'px',
				top: (win.height() - space.height() - 16 >> 1) - 32 + 'px'
			});
		});
	}
	
	/** 登录框隐藏； */
	function onTipHide() {
		$('#tipSpace').fadeOut(40);
	}
</script>
