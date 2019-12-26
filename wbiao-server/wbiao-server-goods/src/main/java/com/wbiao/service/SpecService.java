package com.wbiao.service;

import com.wbiao.pojo.Spec;

import java.util.List;

public interface SpecService {

    List<Spec> selectSpec();

    void addSpec_val(String spec);
}
