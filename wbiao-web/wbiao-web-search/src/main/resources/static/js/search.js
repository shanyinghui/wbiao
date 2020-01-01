$(window).ready(function () {
	// 绿 banner 关闭
	$(".toptop .icon_top-close").click(function() {
		$(this).parents(".toptop").slideUp();
	});
	
	$(".said .said-bottom a").hover(function() {
		$(this).css("background-color", "#f1f1f1");
		$(this).find("div").stop(true).fadeIn(500, function() {
			$(this).find("div").attr("style", "display: block;");
		});
	}, function() {
		$(this).css("background-color", "");
		$(this).find("div").stop(true).fadeOut(500, function() {
			$(this).find("div").attr("style", "display: none;");
		});
	});
	
	$(".said-bottom").on("click", ".said-left", function() {
		// 获取点击的 a 的索引
		var a_index = $(this).index();
		$(this).css("background-color", "#333")
		$(this).parents(".said-bar").addClass("on");
		$(".said-right").addClass("show");
		$(".said-right .said-tier").eq(a_index).siblings().removeClass("show");
		$(".said-right .said-tier").eq(a_index).addClass("show");
	})
	
	$('.head-search .srh-btn').click(function () {
		window.location.href="goods-list-rolex.html";
	})
	
	// top 客户服务动画 问题
	$(".top-right .top-sercice").hover(function() {
		$(this).find("a").eq(0).addClass("a-cover");
		$(this).find(".top-cover").css("display", "block");
		$(this).find(".top-cover").stop(true).animate({
			height: "123px"
		});
	}, function() {
		$(this).find(".top-cover").stop(true).animate({
			height: "0px"
		}, function() {
			$(this).find(".top-cover").attr("style", "display: none;");
		});
		$(this).find(".a-cover").removeClass("a-cover");
	});
	
	//    input点击
	$(".head-search input").click(function() {
		$(this).parent('label').siblings('.srh-hot').show();
	});
	
	//    input里面的内容改变时
	$('.head-search input').on('input propertychange', function() {
		$(this).siblings('span').hide();
		$(this).parent('label').siblings('.srh-hot').hide();
		if ($(this).val() == '') {
			$(this).siblings('span').show();
			$(this).parent('label').siblings('.srh-hot').show();
		}
	});
	
	//    input 失去焦点的时候
	$(".head-search input").blur(function() {
		$(this).parent('label').siblings('.srh-hot').hide();
	});
	
	// nav
	$('.nav_left').hover(function () {
		$(this).find('.nav-left-menu ').slideDown();
	},function () {
		$(this).find('.nav-left-menu ').slideUp();
	})
	
	$(".nav-left .nav-left-menu .menu-box").hover(function() {
		$(this).find(".menu-line").css("display", "inline-block");
		$(this).css("background-color", "#fff");
		$(this).find(".sub-menu").css("display", "block");
	}, function() {
		$(this).find(".menu-line").css("display", "none");
		$(this).css("background-color", "");
		$(this).find(".sub-menu").css("display", "none");
	});
	// 鼠标滚轮
	// $(document).scroll(function() {
	// 	var top = $(this).scrollTop();
	// 	var nav_top = $("#nav").offset().top;
	// 	if (top > 206) {
	// 		$(".nav-float").css("display", "block");
	// 	} else {
	// 		$(".nav-float").css("display", "none");
	// 	}
	// });
	
	
	// 左侧
	// 晒单评论
	$('.title-1 .show').hover(function() {
		$(this).css('font-weight', 'bold');
		$('.slider2 .slideBox1').show();
		$('.slider2 .slideBox2').hide();
		$('.title-1 .comment').css('font-weight', 'normal');
	}, function() {
		$('.slider2 .slideBox1').show();
		$('.slider2 .slideBox2').hide();
	})
	$('.title-1 .comment').hover(function() {
		$(this).css('font-weight', 'bold');
		$('.slider2 .slideBox2').show();
		$('.slider2 .slideBox1').hide();
		$('.title-1 .show').css('font-weight', 'normal');
	}, function() {
		$(this).css('font-weight', 'bold');
		$('.slider2 .slideBox2').show();
		$('.slider2 .slideBox1').hide();
	})
	
	// 资讯承诺
	$('.title-3 .news').hover(function() {
		$(this).css('font-weight', 'bold');
		$('.news .clock-news').show();
		$('.news .web-promise').hide();
		$('.title-3 .promise').css('font-weight', 'normal');
	}, function() {
		$('.news .clock-news').show();
		$('.news .web-promise').hide();
	})
	$('.title-3 .promise').hover(function() {
		$(this).css('font-weight', 'bold');
		$('.news .web-promise').show()
		$('.news .clock-news').hide();
		$('.title-3 .news').css('font-weight', 'normal');
	}, function() {
		$(this).css('font-weight', 'bold');
		$('.news .web-promise').show();
		$('.news .clock-news').hide();
	})
	// 热门品牌/热词
	$('.title-4 .brand').hover(function() {
		$(this).css('font-weight', 'bold');
		$('.hot .hotbrand').show();
		$('.hot .hotwords').hide();
		$('.title-4 .word').css('font-weight', 'normal');
	}, function() {
		$('.hot .hotbrand').show();
		$('.hot .hotwords').hide();
	})
	$('.title-4 .word').hover(function() {
		$(this).css('font-weight', 'bold');
		$('.hot .hotwords').show();
		$('.hot .hotbrand').hide();
		$('.title-4 .brand').css('font-weight', 'normal');
	}, function() {
		$(this).css('font-weight', 'bold');
		$('.hot .hotwords').show();
		$('.hot .hotbrand').hide();
	})
	
	// 右侧
	// 底部更多选项
	var flag = true;
	$('.right .more').click(function() {
		// $('.right .middle .cover').slideToggle();
		console.log(flag);
		if (flag) {
			$('.right .cover').show();
			$('.right .more .icon-point1').css('display', 'inline-block');
			$('.right .more .icon-point').hide();
			/*$(this).css('top', '590px');
			$('.right .cover').show();
			$('.right .more .icon-point1').css('display', 'inline-block');
			$('.right .more .icon-point').hide();
			$('.right .more span').text('收起');
			$(this).css({
				width: '40px',
				height: '20px',
				left: '426px'
			});*/
			flag = false;
		} else {
			/*$(this).css('top', '228px');
			$('.right .cover').hide();
			$('.right .more .icon-point1').css('display', 'none');
			$('.right .more .icon-point').show();
			$('.right .more span').text('更多选项(表盘、表带、功能、防水等)');
			$(this).css({
				width: '216px',
				left: '358px'
			});
			flag = true;*/
			$('.right .cover').hide();
			$('.right .more .icon-point1').css('display', 'none');
			$('.right .more .icon-point').show();
			flag = true;
		}
	})
	// 菜单
	// 全部撤销
	$('.menu .toper .right').click(function () {
		$('.right .toper .left').children('span').remove();
		$('.right .toper .left').children('a').remove();
		// 页面重定向
		window.location.replace("/search.html");
	})
	// 全部分类
	$('.menu .total li a').click(function() {
		$(this).css({
			backgroundColor: 'black',
			color: 'white',
			borderRadius: '3px'
		});
		$(this).parent().siblings().children('a').css({
			backgroundColor: 'transparent',
			color: '#666'
		});
	})
	// 价格筛选
	// $('.choose span').click(function() {
	// 	// 获取input 值
	// 	var start = $('.inputs .start').val();
	// 	var start = $('.inputs .end').val();
	// 	// 获取商品价格
	// 	var index = $('.goods-list li').index();
	// 	var price = $('.goods-list .price .now-price').eq(index).text();
	// 	console.log(price);
	// })
	// 品牌--更多
	$('.right .brand-list .choose-1').click(function() {
		if (flag) {
			$('.right .brand-list .first').hide();
			$('.right .brand-list .second').show();
			$('.right .choose-1 .icon-point').css('transform', 'rotate(180deg)');
			flag = false;
		} else {
			$('.right .brand-list .first').show();
			$('.right .brand-list .second').hide();
			$('.right .choose-1 .icon-point').css('transform', 'rotate(0deg)');
			flag = true;
		}
	})
	// 人群--多选
	$('.right .people-list .choose-2').click(function() {
		$('.right .people-list .before').hide();
		$('.right .people-list .after').show();
		$('.right .people-list .after').css('border', '1px solid #cc9952');
	})
	$('.right .people-list .yes').click(function() {
		$('.right .people-list .before').show();
		$('.right .people-list .after').hide();
	})
	$('.right .people-list .no').click(function() {
		$('.right .people-list .before').show();
		$('.right .people-list .after').hide();
	})
	
	// 导航条nav
	// new-list
	$(window).scroll(function() {
		var top = $(this).scrollTop();
		// console.log(top);
		// var height = $('.right .nav').offset().top;
		// console.log(height);
		if (top > 721) {
			$('.right .nav').css({
				position: 'fixed',
				marginTop: '0',
				top: '0',
				zIndex: '2',
				backgroundColor: 'white',
				padding: '10px 10px 0 10px',
				boxShadow: '0 0 10px 0 rgba(0,0,0,0.2)',
				width: '968px'
			});
		} else {
			$('.right .nav').css({
				position: 'static',
				marginTop: '62px',
				marginBottom: '10px',
				top: '427px',
				zIndex: '10',
				backgroundColor: 'white',
				padding: '0',
				boxShadow: '0 0 0 0 rgba(0,0,0,0)'
			});
		}
	})
	// hover事件
	/*$('.right .nav .new-list li').hover(function() {
		$(this).css({
			fontWeight: 'bold',
			color: '#cc9952',
			borderBottom: '5px solid #CC9952'
		})
		$(this).siblings().css({
			fontWeight: 'normal',
			color: '#666',
			borderBottom: '0'
		})
		$('.right .nav .new-list li:first-child').css({
			fontWeight: 'bold',
			borderBottom: '5px solid #CC9952'
		})
		$('.right .nav .new-list li:first-child a').css({
			color: '#cc9952'
		})
	}, function() {
		$(this).css({
			fontWeight: 'normal',
			color: '#666',
			borderBottom: '0'
		})
		$(this).siblings().css({
			fontWeight: 'normal',
			color: '#666',
			borderBottom: '0'
		})
		$('.right .nav .new-list li:first-child').css({
			fontWeight: 'bold',
			borderBottom: '5px solid #CC9952'
		})
		$('.right .nav .new-list li:first-child a').css({
			color: '#cc9952'
		})
	})*/
	// ********!!!
	// new-list 点击事件
	$('.right .nav .new-list li').click(function() {
		var index = $(this).index();
		console.log(index);
		$(this).eq(index).css({
			fontWeight: 'bold',
			borderBottom: '5px solid #CC9952'
		})
		$('.right .nav .new-list li').eq(index).find('a').css({
			color: '#cc9952'
		})
		$(this).eq(index).siblings().css({
			fontWeight: 'normal',
			borderBottom: '0'
		})
		$(this).eq(index).siblings().find('a').css({
			color: '#666'
		})
		$('.right .nav .new-list li:first-child').css({
			fontWeight: 'normal',
			borderBottom: '0'
		})
		$('.right .nav .new-list li:first-child a').css({
			color: '#666'
		})
	})
	// 人群checkbox点击事件----!!!
	$('.people-list .after .person li').click(function() {
			if(flag){
			$(this).find('.icon-checked').css('display','block');
			$('.yes').css({
				border: '1px solid #666',
				borderRadius: '3px',
				color:'#666'
			})
			flag = false;
		}else{
			$(this).find('.icon-checked').css('display','none');
			$('.yes').css({
				border: '1px solid #ccc',
				borderRadius: '3px',
				color:'#ccc'
			})
			flag = true;
		}
	})
	// 导航条
	// check-list点击事件
	$('.check-list li').click(function () {
		if(flag){
			$(this).find('.icon-checked').css('display','block');
			flag = false;
		}else{
			$(this).find('.icon-checked').css('display','none');
			flag = true;
		}
	})
	// 导航条 pageup翻页
	// 左翻页
	/*$('.pageup .page-left').click(function () {
		var numLeft = $('.nav .pageup .num-left').text();
		if(numLeft == '1'){
			$(this).find('a').css('href','javascript:;');
		}else{
			var num = $('.pageup .num-left').text();
			num = num*1;
			console.log(num);
			num--;
			$('.pageup .num-left').text(num);
		}
	})
	// 右翻页
	$('.pageup .page-right').click(function () {
		var numLeft = $('.nav .pageup .num-left').text();
		var num = $('.pageup .num-left').text();
		num = num*1;
		num++;
		$('.pageup .num-left').text(num);
	})*/
	// 底部pages
	// 点击事件
/*	$('.right .pages .page-box a').click(function () {
		$(this).css({
			color:"white",
			backgroundColor:"#666"
		})
		$(this).siblings().css({
			color:"#666",
			backgroundColor:"transparent"
		})
		var text =$(this).text();
		if(text != '1'){
			$('.pages .pages-left').show();
		}else{
			$('.pages .pages-left').hide();
		}
	})*/
	// 左翻页
	// $('.right .pages .pages-left').on('.click','.page-box',function () {
	// 	var text = $(this).find('a').text();
	// 	console.log(text);
	// 	if(text == '1'){
	// 		$('.pages .pages-left').hide();
	// 	}else{
	// 		var index = $('.page-box').index();
	// 		console(index);
	// 		index--;
	// 		$(this).eq(index).css({
	// 			color:"white",
	// 			backgroundColor:"#666"
	// 		})
	// 	}
	// })
	// 右翻页
	// $('.right .pages .pages-right').on('.click','.page-box',function () {
	// 	var text = $(this).find('a').text();
	// 	var index = $('.page-box').index();
	// 	index++;
	// 	$(this).eq(index).css({
	// 		color:"white",
	// 		backgroundColor:"#666"
	// 	})
	// })
})
