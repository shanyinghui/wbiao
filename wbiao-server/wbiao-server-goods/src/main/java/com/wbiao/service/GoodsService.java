package com.wbiao.service;

import com.wbiao.goods.pojo.Goods;
import com.wbiao.goods.pojo.Sku;
import com.wbiao.goods.pojo.Spu;
import com.wbiao.util.PageResult;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    void addGoods(Goods goods);

    PageResult<Sku> selectGoods(String key, Boolean saleable, Integer page, Integer rows);

    Spu selectSpuById(String id);

    List<Sku> selectSkuBySpu_id(String id);

    void updateGoods(Goods goods);

    void deleteSkuById(String id);

    void updateSkuEnable(String id);

    List<Sku> selectSkuByEnable();

    Map<String, Object> goodsDetails(String skuid);
}
