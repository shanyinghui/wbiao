package com.wbiao.mapper;

import com.wbiao.pojo.Sku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuMapper {

    void addSku(Sku sku);

    List<Sku> selectSku(@Param("key") String key, @Param("saleable") Boolean saleable);

    List<Sku> selectSkuBySpu_id(String id);

    void deleteSkuBySpu_id(String id);

    void deleteSkuById(String id);

    void updateSkuEnable(String id);

}
