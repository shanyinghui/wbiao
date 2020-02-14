package com.wbiao.pay.feignService;

import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "wbiao-pay")
@RequestMapping("/pay")
public interface PayFeign {
    /**
     *  生成二维码
     * @param parameterMap
     * @return
     */
    @RequestMapping("/createNative")
    ResultUtil createNative(@RequestParam Map<String,String> parameterMap);

    /**
     *  支付结果查询
     * @param outtradeno
     * @return
     */
    @RequestMapping("/queryState")
    ResultUtil queryState(String outtradeno);

    /**
     *  关闭订单
     * @param order_id
     * @return
     */
    @RequestMapping("/closePay")
    ResultUtil<Map> closePay(@RequestParam("order_id") String order_id);
}
