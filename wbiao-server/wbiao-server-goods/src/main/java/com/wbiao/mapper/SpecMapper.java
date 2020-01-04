package com.wbiao.mapper;

import com.wbiao.goods.pojo.SkuSaleVal;
import com.wbiao.goods.pojo.Spec;
import com.wbiao.goods.pojo.SpecGroup;

import java.util.List;

public interface SpecMapper {
    List<Spec> selectSpec();

    void addSkuSaleVal(SkuSaleVal skuSaleVal);

    void updateSkuSaleVal(SkuSaleVal skuSaleVal);

    List<SkuSaleVal> selectAllBySpuid(String spuid);

    //查询规格组
    List<SpecGroup> selectAllSpecGroup();

    //根据规格组id查询规则参数名
    List<Spec> selectSpecByGroupId(Integer group_id);
}
