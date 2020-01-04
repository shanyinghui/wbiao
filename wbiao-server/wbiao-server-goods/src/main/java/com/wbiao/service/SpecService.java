package com.wbiao.service;

import com.wbiao.goods.pojo.Spec;
import com.wbiao.goods.pojo.SpecGroup;

import java.util.List;

public interface SpecService {
    List<Spec> selectSpec();

    List<SpecGroup> selectAllSpecGroup();

}
