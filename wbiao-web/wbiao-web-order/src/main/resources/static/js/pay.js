// 倒计时
window.onload = function() {

	// 查找元素
	var oTime = document.querySelector(".content .box-left .timing");

	var maxtime = 30 * 60;
	function CountDown() {
		if (maxtime >= 0) {
			minutes = Math.floor(maxtime / 60);
			seconds = Math.floor(maxtime % 60);
			--maxtime;
			oTime.innerText = "还剩 " + minutes + " 分 " + seconds + " 秒付完订单全款 ";
		} else{
			clearInterval(timer);
			oTime.innerText = "订单已超时，请重新下单 ";
		}
	}
	CountDown();
	var timer;
	timer = setInterval(function() {
		CountDown();
	}, 1000)

	// 遮罩
	$('.content .bank-list li').click(function () {
		document.getElementById("qrcode").innerHTML = '';
		var url = "";
		var flag = true;
		$.ajax({
			url:"http://www.wbiao.com/pay/createNative",
			type:"post",
			dataType:"json",
			data:{
				outtradeno:orderid
			},
			async:false,
			success:function (result) {
				console.log(result);
				if(result.data.result_code=="FAIL"){
					alert("订单失效，请重新下单！");
					flag = false;
				}else{
					url = result.data.code_url;
				}
			}
		})
		if(!flag){
			return;
		}
		var qrcode = new QRCode(document.getElementById("qrcode"), {
			width : 240,
			height : 240
		});
		qrcode.makeCode(url);
		$('.covers').slideDown(1000);
		$('.black').show(500);
		$('.covers').delay(500).animate({
			top: 100
		},1000)
	})
	$('.close').click(function () {
		$('.covers').hide();
		$('.black').hide();
		document.getElementById("qrcode").innerHTML = '';
	})
}
