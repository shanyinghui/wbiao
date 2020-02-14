package com.wbiao.mapper;

import com.wbiao.goods.pojo.Sku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuMapper {

    void addSku(Sku sku);

    List<Sku> selectSku(@Param("key") String key, @Param("saleable") Boolean saleable);

    List<Sku> selectSkuBySpu_id(String id);

    void deleteSkuBySpu_id(String id);

    void deleteSkuById(String id);

    void updateSkuEnable(String id);

    List<Sku> selectSkuByEnable();

    Sku selectSkuById(String id);

    int decrCount(@Param("id")String id, @Param("stock")Integer stock);

    void InsertCount(@Param("id")String id, @Param("stock")Integer stock);

    void insertSale_num(@Param("id")String id, @Param("sale_num")Integer sale_num);

    void decrSale_num(@Param("id")String id, @Param("sale_num")Integer sale_num);
}
