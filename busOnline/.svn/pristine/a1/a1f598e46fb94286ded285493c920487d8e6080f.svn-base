Zepto(function() {
	var now = { row : 1, col : 1 }, last = { row : 0, col : 0};
	var towards = {
		up : 1,
		right : 2,
		down : 3,
		left : 4
	};
	var isAnim = false;

	document.addEventListener('touchmove', function(event) {
		event.preventDefault();
	}, false);

	// max row and max col
	var maxRow = 12, maxCol = 1;

	Zepto(document).swipeUp(function() {
		if (isAnim)
			return;
		last.row = now.row;
		last.col = now.col;
		if (last.row != maxRow) {
			now.row = last.row + 1;
			now.col = 1;
			pageMove(towards.up);
		} else {
			forwardHash("#anniversary");
		}
	})

	Zepto(document).swipeDown(function() {
		if (isAnim)
			return;
		last.row = now.row;
		last.col = now.col;
		if (last.row != 1) {
			now.row = last.row - 1;
			now.col = 1;
			pageMove(towards.down);
		}
	})

	Zepto(document).swipeLeft(function() {
		if (isAnim)
			return;
		last.row = now.row;
		last.col = now.col;
		if (last.col != maxCol) {
			now.col = last.col + 1;
			pageMove(towards.left);
		}
	})

	Zepto(document).swipeRight(function() {
		if (isAnim)
			return;
		last.row = now.row;
		last.col = now.col;
		if (last.col != 1) {
			now.col = last.col - 1;
			pageMove(towards.right);
		}
	})

	function pageMove(tw) {
		var lastPage = ".page-" + last.row + "-" + last.col, nowPage = ".page-" + now.row + "-" + now.col;

		if (tw == towards.up) {
			outClass = 'pt-page-moveToTop';
			inClass = 'pt-page-moveFromBottom';
		} else if (tw == towards.right) {
			outClass = 'pt-page-moveToRight';
			inClass = 'pt-page-moveFromLeft';
		} else if (tw == towards.down) {
			outClass = 'pt-page-moveToBottom';
			inClass = 'pt-page-moveFromTop';
		} else if (tw == towards.left) {
			outClass = 'pt-page-moveToLeft';
			inClass = 'pt-page-moveFromRight';
		}

		isAnim = true;
		Zepto(nowPage).removeClass("hide");

		Zepto(lastPage).addClass(outClass);
		Zepto(nowPage).addClass(inClass);

		setTimeout(function() {
			Zepto(lastPage).removeClass('page-current');
			Zepto(lastPage).removeClass(outClass);
			Zepto(lastPage).addClass("hide");
			Zepto(lastPage).find("img").addClass("hide-r");

			Zepto(nowPage).addClass('page-current');
			Zepto(nowPage).removeClass(inClass);
			Zepto(nowPage).find("img").removeClass("hide-r");

			isAnim = false;
		}, 600);
	}

	var isPlay = true;
	// music control
	Zepto('.music-contrl').tap(function() {
		if (isPlay) {
			Zepto('#music')[0].pause();
			Zepto('.music-contrl').removeClass('pt-page-rotation');
		} else {
			Zepto('#music')[0].play();
			Zepto('.music-contrl').addClass('pt-page-rotation');
		}
		isPlay = !isPlay;
	});
	
	// 遮罩层控制
	Zepto('.share').tap(function(){
		isAnim = true;
		Zepto('.page-model').removeClass('hide-r');
	});
	
	Zepto('.iknow').tap(function(){
		Zepto('.page-model').addClass('hide-r');
		isAnim = false;
	});
	var zWin = Zepto(window), zoom = zWin.height() / 850;
	$('.wrap').css({
		'zoom': (zoom * 100 >> 0) / 100,
		'margin-left': (zWin.width() - 540 * zoom >> 0) + 'px'
	});
});
