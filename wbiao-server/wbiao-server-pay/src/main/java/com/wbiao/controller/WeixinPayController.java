package com.wbiao.controller;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.wbiao.service.WeixinPayService;
import com.wbiao.util.ResultUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class WeixinPayController {

    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     *  生成二维码
     * @param parameterMap
     * @return
     */
    @RequestMapping("/createNative")
    public ResultUtil createNative(@RequestParam Map<String,String> parameterMap){
        Map aNative = weixinPayService.createNative(parameterMap);
        return ResultUtil.ok(aNative);
    }

    /**
     *  支付结果查询
     * @param outtradeno
     * @return
     */
    @RequestMapping("/queryState")
    public ResultUtil queryState(String outtradeno){
        Map map = weixinPayService.queryState(outtradeno);
        return ResultUtil.ok(map);
    }


    /**
     *  支付回调信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/natify/url")
    public void natifyUrl(HttpServletRequest request, HttpServletResponse response)throws Exception{
        //获取网络输入流
        ServletInputStream is = request.getInputStream();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        if((len = is.read(bytes))!=-1){
            os.write(bytes,0,len);
        }

        //微信支付结果的字节数组
        byte[] bs = os.toByteArray();
        String xmlresult = new String(bs, "UTF-8");
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlresult);
        System.out.println(resultMap);
        //把支付结果给mq
        rabbitTemplate.convertAndSend("exchange.order","queue.order", JSON.toJSONString(resultMap));
        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
    }

    /**
     *  关闭订单
     * @param order_id
     * @return
     */
    @RequestMapping("/closePay")
    public ResultUtil<Map> closePay(@RequestParam("order_id")String order_id){
        Map result = weixinPayService.close(order_id);
        return ResultUtil.ok(result);
    }
}
