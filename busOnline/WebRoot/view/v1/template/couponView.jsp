<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% // 设置一个全局的路径变量，之后获取文件地址的时候直接进行根目录绝对位置，避免../过多的问题； %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<style>
	#couponInfoSpace { position: fixed; right: 0px; top: 0px; width: 100%; height: 100%; z-index: 10; background: url(http://img.pig84.com/download/100000000004) no-repeat; background-size: 100%; display: none; overflow: auto; }
	#couponInfoSpace > ul { clear: both; list-style: none; margin: 28px 0px; }
	#couponInfoSpace > ul > li { position: relative; padding: 8px 4px; width: 301px; height: 93px; color: #FFF; margin: 0px auto 8px; }
	#couponInfoSpace > ul > li._2 { background: url(http://img.pig84.com/download/100000000009) no-repeat; }
	#couponInfoSpace > ul > li._1 { background: url(http://img.pig84.com/download/100000000008) no-repeat; }
	#couponInfoSpace > ul > li._0 { background: url(http://img.pig84.com/download/100000000007) no-repeat; }
	#couponInfoSpace > ul > li.overdue { background: url(http://img.pig84.com/download/100000000006) no-repeat; }
	
	#couponInfoSpace > ul > li .over { display: none; position: absolute; right: 32px; top: 23px; background: url(http://img.pig84.com/download/100000000010) no-repeat; width: 81px; height: 62px; }
	#couponInfoSpace > ul > li .f_24 { font-size: 32px; }
	#couponInfoSpace > ul > li .f_96 { font-size: 78px; line-height: 58px; }
	#couponInfoSpace > ul > li .f_14 { font-size: 12px; line-height: 14px; padding: 2px 0px; }
	#couponInfoSpace > ul > li .f_14 span { color: #fa6868; text-shadow: 0px 0px 2px #fff; }
	#couponInfoSpace > ul > li .f_14 stong { color: #f6dd50; }
	#couponInfoSpace > ul > li .f_18_g { letter-spacing: 2px; font-size: 13px; background: rgba(0, 0, 0, 0.2); padding: 4px 8px 3px; line-height: 15px;}
	#couponInfoSpace > ul > li > table { width: 92%; text-align: center; margin: 0px auto; }
	#couponInfoSpace > ul > li td { line-height: 32px; }
	#couponInfoSpace > ul > li td a { width: 100%; display: block; background: #FFF; border-radius: 6px; margin: 2px auto 0px; line-height: 22px; color: #3A3; font-size: 12px; letter-spacing: 2px; }
	
	#couponInfoSpace > ul > li._2 .f_18_g { background: #ef6969; }
	#couponInfoSpace > ul > li._1 .f_18_g { background: #e5c307; }
	#couponInfoSpace > ul > li._1 .f_14 stong { color: #FFF; }
	#couponInfoSpace > ul > li._0 .f_18_g { background: #55bf96; }
	#couponInfoSpace > ul > li._2 .f_14 span { color: #FFF; }
	#couponInfoSpace > ul > li.overdue .f_14 span { color: #FFF; }
	#couponInfoSpace > ul > li.overdue .over { display: block; }
</style>
<div id="couponInfoSpace">
	<ul></ul>
</div>
<script>
	var isCouponShow, couponPage, isCouponLoading;
	
	/** 加载优惠券; */
	function onCouponLoading(callback) {
		if (isCouponLoading)
			return;
		var user = getLoginUser();
		if (!user) {
			onCouponHide();
			history.go(-1);
			forwardHash('#login');
			return;
		}
		onLoadingShow('加载优惠券中...');
		isCouponLoading = true;
		$.post('${contextPath}/v1/user/coupons', {
			uid: user['a2'],
			page: couponPage * 10
		}, function (res) {
			if (res && res.length) {
				isCouponLoading = false;
				onCouponUpdate(res);
			}
			callback && callback();
			onLoadingHide();
		}, 'json');
	}
	
	/** 选择优惠券 */
	function onCouponSelected(id, val) {
		window['updateByCoupon'] && updateByCoupon(val, id);
		history.go(-1);
	}
	
	var couponTemplate;
	var couponTemplateUse = ['<li class="', null, '" onclick="onCouponSelected(\'', null, '\', \'', null, '\');"><table cellpadding="0" cellspacing="0"><tbody><tr><td width="10%" rowspan="2" class="f_24" valign="top">￥</td><td width="50%" rowspan="2" class="f_96" valign="top" align="left">',
		 null, '</td><td width="40%" class="f_18_g"><p>小猪巴士</p><p>上下班券</p></td></tr><tr><td class="f_14">', null, 
		'<br>', null, '</td></tr><tr><td colspan="3"><a href="javascript:void(0);">点击使用</a></td></tr><tbody></table><div clss="over"></div></li>'];
	var couponTemplateDis = ['<li class="', null, '" ', null, ' ', null, '><table cellpadding="0" cellspacing="0" style="margin:16px auto;"><tbody><tr><td width="10%" rowspan="2" class="f_24" valign="top">￥</td><td width="50%" rowspan="2" class="f_96" valign="top" align="left">',
		 null, '</td><td width="40%" class="f_18_g"><p>小猪巴士</p><p>上下班券</p></td></tr><tr><td class="f_14">', null, 
		'<br>', null, '</td></tr><tr><td colspan="3"><a href="#"></a></td></tr><tbody></table><div class="over"></div></li>'];
	/** 更新优惠券； */
	function onCouponUpdate(couponList) {
		couponPage ++;
		var html = [], today = new Date();
		for (var i = 0, item; item = couponList[i]; i ++) {
			var begin = new Date(item['a4']), end = new Date(item['a5']), template = null;
			var type = getBgType(today, end, item['a1'] - 0);
			var price = item['a2'] - 0;
			template = getTemplate(price, today, begin, end);
			template[1] = type;
			template[3] = item['a6'];
			template[5] = item['a1'];
			template[7] = item['a1'];
			template[9] = getOutStat(begin, end, today);
			template[11] = getPriceStat(price);
			html.push(template.join(''));
		}
		$('#couponInfoSpace ul').append(html.join(''));
		
		function getTemplate(price, day, begin, end) {
			if (ticketPrices < price)
				return couponTemplateDis;
			var todayTime = day.getTime();
			if (todayTime > end.getTime() && day.getDate() != end.getDate())
				return couponTemplateDis;
			if (todayTime < begin.getTime() && day.getDate() != begin.getDate())
				return couponTemplateDis;
			return couponTemplate;
		}
		
		// 检测价格因素
		function getPriceStat(price) {
			price = price || 0;
			if (price > 0)
				return ['满<stong>', price, '</stong>元可用'].join('');
			else
				return '无限制';
		}
		
		// 获取过期状态
		function getOutStat(begin, end, day) {
			var beginOffset = (begin.getTime() - day.getTime()) / 86400000 >> 0;
			if (beginOffset > 0) {
				var res = null;
				switch(beginOffset) {
					case 1: res = '<span>明天开始使用</span>'; break;
					case 2: res = '<span>后天开始使用</span>'; break;
					default: res = ['<span>', beginOffset, '天后开始使用</span>'].join(''); break;
				}
				return res;
			} else {
				var endOffset = end.getTime() - day.getTime();
				if (endOffset > 0) {
					if (endOffset < 86400000) {
						return ['<span>', end.getDate() == day.getDate() ? '今' : '明', '天过期</span>'].join('');
					} else if (endOffset < 172800000) {
						var time = new Date(end.getTime() - 86400000);
						return ['<span>', time.getDate() == day.getDate() ? '明' : '后', '天过期</span>'].join('');
					} else if (endOffset > 2592000000){
						return '还有超过1个月过期';
					} else {
						return ['还有', Math.ceil(endOffset / 86400000) , '天过期'].join('');
					}
				} else {
					return ['过期时间&nbsp;', 1900 + end.getYear(), '.', end.getMonth() + 1, '.', end.getDate()].join('');
				}
			}
		}
		
		// 获取状态
		function getBgType(day, date, sum) {
			if (day.getTime() > date.getTime() && day.getDate() != date.getDate())
				return 'overdue';
			else if (sum < 10)
				return '_0';
			else if (sum <= 20)
				return '_1';
			else
				return '_2';
		}
	}
	
	/** 根据url检测是否需要显示优惠券界面； */
	function checkCouponByUrl() {
		var url = location.href;
		if (url.indexOf('#coupon') != -1) {
			onCouponShow();
		} else {
			if (isCouponShow) {
				onCouponHide();
			}
		}
	}
	
	/** 检测url是否要进行显示； */
	window.addEventListener('load', function (evt) {
		checkCouponByUrl();
	});
	
	/** 地址变更事件； */
	window.addEventListener('popstate', function (evt) {
		checkCouponByUrl();
	}, false);

	/** 滚动后的加载； */
	/* sjx: 如果没有分页这个东西木有用...
	document.getElementById('couponInfoSpace').addEventListener('scroll', function (evt) {
		var max = this.scrollHeight - this.offsetHeight; 
		if (isCouponShow && max - this.scrollTop < 20)
			onCouponLoading();
		stopEvent(evt);
	}, false);
	*/
	
	/** 显示； */
	function onCouponShow() {
		isCouponShow = true;
		couponPage = 0;
		isCouponLoading = false;
		$('#couponInfoSpace ul').html('');
		onCouponLoading(function () {
			if (isCouponLoading) {
				alert('您还没有优惠券');
				history.go(-1);
				return;
			}
			var elem = $('#couponInfoSpace');
			elem.css({
				right: -$(window).width() + 'px',
				display: 'block'
			});
			elem.animate({
				right: '0px'
			}, {
				duration: 400
			});
		});
	}
	
	/** 隐藏； */
	function onCouponHide(callback) {
		isCouponShow = false;
		var elem = $('#couponInfoSpace');
		elem.animate({
			right: -$(window).width() + 'px'
		}, {
			duration: 400,
			complete: function () {
				document.getElementById('couponInfoSpace').scrollTop = 0;
				elem.hide();
				callback && callback();
			}
		});
	}
</script>
