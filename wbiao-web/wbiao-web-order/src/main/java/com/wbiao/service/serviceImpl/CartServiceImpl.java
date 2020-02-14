package com.wbiao.service.serviceImpl;

import com.wbiao.order.pojo.OrderItem;
import com.wbiao.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<OrderItem> cartList(String token) {
        String url = "http://WBIAO-ORDER/cart/list";
        //请求头信息的封装
        MultiValueMap<String,String> headerMap = new LinkedMultiValueMap<String,String>();
        String Authorization = "Bearer "+token;

        headerMap.add("Authorization",Authorization);

        //创建HttpEntity对象，封装了请求头和请求体

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(headerMap);
        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class);
        Map<String,Object> body = exchange.getBody();
       List<OrderItem> orderItems = (List<OrderItem>)body.get("data");


        return orderItems;
    }
}
