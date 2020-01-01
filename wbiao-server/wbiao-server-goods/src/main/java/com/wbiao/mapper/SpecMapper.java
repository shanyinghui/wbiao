package com.wbiao.mapper;

import com.wbiao.goods.pojo.Spec;
import com.wbiao.goods.pojo.Spec_val;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpecMapper {

    List<Spec> selectSpec();

    Spec_val selectSpec_NameAndVal(@Param("name")String name,@Param("val")String val);

    void addSpec_val(@Param("name")String name,@Param("val")String val);
}
