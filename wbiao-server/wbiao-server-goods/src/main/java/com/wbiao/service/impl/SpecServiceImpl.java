package com.wbiao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wbiao.mapper.SpecMapper;
import com.wbiao.pojo.Spec;
import com.wbiao.pojo.Spec_val;
import com.wbiao.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecMapper specMapper;

    @Override
    public List<Spec> selectSpec() {
        return specMapper.selectSpec();
    }

    @Override
    public void addSpec_val(String spec) {
        Map<String,String> map = JSONObject.parseObject(spec, Map.class);
        for(Map.Entry str: map.entrySet()){
            String name = (String)str.getKey();
            String value = (String)str.getValue();
            Spec_val spec_val = specMapper.selectSpec_NameAndVal(name, value);
            if(spec_val==null){
                specMapper.addSpec_val(name,value);
            }
        }
    }
}
