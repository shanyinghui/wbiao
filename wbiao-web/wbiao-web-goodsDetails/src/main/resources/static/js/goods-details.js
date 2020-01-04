$(window).ready(function() {
	// 绿 banner 关闭
	$(".toptop .icon_top-close").click(function() {
		$(this).parents(".toptop").slideUp();
	});
	
	// 头部nav-list
	$('.nav-list li a').click(function() {
		$(this).css({
			fontWeight: 'bold',
			borderBottom: '3px solid white',
			padding: '11px 10px 11px 10px'
		})
		$(this).parents().siblings().find('a').css({
			fontWeight: 'normal',
			borderBottom: '3px solid transparent',
			padding: '10px'
		})
	})
	
	// 主体
	// 免息分期 hover事件
	$('.price-box .de-stages .free').hover(function() {
		$('.de-stages-cover').show();
	}, function() {
		$('.de-stages-cover').hide();
	})
	// 鼠标滚动置顶事件
	$(window).scroll(function() {
		var top = $(this).scrollTop();
		// console.log(top);
		// var height = $('.goods-nav').offset().top;
		// console.log(height);
		if (top > 1147) {
			$('.goods-nav').css({
				position: 'fixed',
				top: '0',
			});
		} else {
			$('.goods-nav').css({
				position: 'static',
				marginBottom: '10px',
				top: '427px'
			});
		}
	})
	// 底部goods-nav
	$('.goods-nav li a').click(function() {
		$(this).css({
			fontWeight: 'bold',
			borderBottom: '3px solid black',
		})
		$(this).parents().siblings().find('a').css({
			fontWeight: 'normal',
			borderBottom: '3px solid transparent'
		})
	})
	// 底部goods-nav
	$('.goods-nav .good-info').click(function() {
		$('.cover-1').show();
		$('.cover-2,.cover-3').hide();
	})
	$('.goods-nav .specifications').click(function() {
		$('.cover-2').show();
		$('.cover-1,.cover-3').hide();
	})
	$('.goods-nav .after-service').click(function() {
		$('.cover-3').show();
		$('.cover-1,.cover-2').hide();
	})
// 加购物车
// 改变数量
// 左减
	$('.type-choose .reduce').click(function() {
		var number = $('.type-choose .number .num-input').val();
		console.log(number);
		if (number == '1') {
			$('.type-choose .number .num-input').val('1');
		} else {
			var num = $('.type-choose .number .num-input').val();
			num = num * 1;
			console.log(num);
			num--;
			$('.type-choose .number .num-input').val(num);
		}
	})
	// 右加
	$('.type-choose .add').click(function() {
		var num = $('.type-choose .number .num-input').val();
		num = num * 1;
		num++;
		$('.type-choose .number .num-input').val(num);
	})
	
	
})
