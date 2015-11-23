<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% // 设置一个全局的路径变量，之后获取文件地址的时候直接进行根目录绝对位置，避免../过多的问题； %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<style>
	#loadingSpace {
		position: fixed;
		left: 0px;
		top: 0px;
		width: 100%;
		height: 100%;
		z-index: 15;
		display: none;
		/*
		background: -webkit-radial-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.1) 20%, rgba(0, 0, 0, 0.2) 40%, rgba(0, 0, 0, 0.6));
		*/
		background: rgba(240, 240, 240, 0.7);
	}
	
	#loadingSpace div {
		width: 180px;
		height: 40px;
		margin: 0px auto 0px;
		-webkit-transform: translateY(-20px);
	}
	
	#loadingSpace span {
		background: url(http://img.pig84.com/download/100000000017);
		background-size: 100%;
		width: 35px;
		height: 35px;
		margin: 16px auto;
		display: block;
		-webkit-animation-name: 'loadingAnimation';
     	-webkit-animation-duration: 2s;
     	-webkit-animation-timing-function: linear;
     	-webkit-animation-iteration-count: infinite;
	}
	
	#loadingSpace p {
		color: #FFF;
		margin-top: 22px;
		font-size: 16px;
		text-align: center;
		text-shadow: 0px 0px 2px #000;
	}
	
	@-webkit-keyframes 'loadingAnimation' {
		from {
			-webkit-transform: rotateZ(0deg);
		} to {
			-webkit-transform: rotateZ(352deg);
		}
	}
	
	#loadingSpace .close { color: #4cbd71; position: absolute; right: 16px; top: 16px; width: 48px; height: 48px; font-size: 32px; text-align: center; line-height: 48px; }
</style>
<div id="loadingSpace" onclick="onLoadingHide();"><div><span></span><p></p></div></div>
<script>
	/** 登录框显示； */
	function onLoadingShow(tip) {
		$('#loadingSpace p').html(tip || '');
		$('#loadingSpace > div').css({
			marginTop: ($(window).height() - 40 >> 1) + 'px'
		});
		$('#loadingSpace').fadeIn();
	}
	
	/** 登录框隐藏； */
	function onLoadingHide() {
		$('#loadingSpace').fadeOut();
	}
</script>
