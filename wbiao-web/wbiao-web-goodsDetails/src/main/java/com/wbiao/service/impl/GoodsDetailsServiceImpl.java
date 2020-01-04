package com.wbiao.service.impl;

import com.wbiao.goods.feignService.GoodsFeign;
import com.wbiao.service.GoodsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoodsDetailsServiceImpl implements GoodsDetailsService {
    @Autowired
    private GoodsFeign goodsFeign;


    @Override
    public Map<String, Object> goodsDetails(String skuid) {
       Map<String, Object> mapResultUtil = (Map<String, Object>)goodsFeign.goodsDetails(skuid).getData();
       return mapResultUtil;
    }
}
