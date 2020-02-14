package com.wbiao.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.wbiao.service.WeixinPayService;
import com.wbiao.util.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {
    //微信支付系统生成支付二维码
    private final String CREATE_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //查询支付状态
    private final String QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    //关闭支付链接
    private final String CLOSE_URL = "https://api.mch.weixin.qq.com/pay/closeorder";

    //注入微信支付信息配置
    //应用id
    @Value(value = "${weixin.appid}")
    private String appid;

    //商户id
    @Value(value = "${weixin.partner}")
    private String partner;

    //商户秘钥
    @Value(value = "${weixin.partnerkey}")
    private String partnerkey;

    //回调地址
    @Value(value = "${weixin.notifyurl}")
    private String notifyurl;

    /***
     * 生成二维码
     * @param paramter
     * @return
     */
    @Override
    public Map createNative(Map<String,String> paramter) {
        try {
            //1.封装参数
            Map map = new HashMap<>();
            map.put("appid", appid);//应用id
            map.put("mch_id", partner);//商户id
            map.put("nonce_str", WXPayUtil.generateNonceStr());//随机数
            map.put("body", "万表");//订单描述
            map.put("out_trade_no",paramter.get("outtradeno"));//商户订单号
            map.put("total_fee", "1");//交易金额
            map.put("spbill_create_ip", "127.0.0.1");//终端ip
            map.put("notify_url", notifyurl);//回调地址
            map.put("trade_type", "NATIVE");//交易类型
            map.put("product_id",paramter.get("outtradeno"));
            //2.将参数转换为xml 并携带签名
            String xml = WXPayUtil.generateSignedXml(map, partnerkey);

            //3.通过httpclient发送请求
            HttpClient httpClient = new HttpClient(CREATE_URL);
            httpClient.setHttps(true);
            httpClient.setXmlParam(xml);
            httpClient.post();

            //4.获取参数
            String content = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  查询订单支付情况
     * @param outtradeno
     * @return
     */
    @Override
    public Map queryState(String outtradeno) {
        try {
            //1.封装参数
            Map map = new HashMap<>();
            map.put("appid", appid);//应用id
            map.put("mch_id", partner);//商户id
            map.put("nonce_str", WXPayUtil.generateNonceStr());//随机数
            map.put("out_trade_no",outtradeno);//商户订单号

            //2.将参数转换为xml 并携带签名
            String xml = WXPayUtil.generateSignedXml(map, partnerkey);

            //3.通过httpclient发送请求
            HttpClient httpClient = new HttpClient(QUERY_URL);
            httpClient.setHttps(true);
            httpClient.setXmlParam(xml);
            httpClient.post();

            //4.获取参数
            String content = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  关闭订单
     *
     *
     */
    @Override
    public Map close(String order_id) {
        System.out.println(order_id);
        try {
            //1.封装参数
            Map map = new HashMap<>();
            map.put("appid", appid);//应用id
            map.put("mch_id", partner);//商户id
            map.put("nonce_str", WXPayUtil.generateNonceStr());//随机数
            map.put("out_trade_no",order_id);//商户订单号

            //2.将参数转换为xml 并携带签名
            String xml = WXPayUtil.generateSignedXml(map, partnerkey);

            //3.通过httpclient发送请求
            HttpClient httpClient = new HttpClient(CLOSE_URL);
            httpClient.setHttps(true);
            httpClient.setXmlParam(xml);
            httpClient.post();

            //4.获取参数
            String content = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
