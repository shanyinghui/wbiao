package com.wbiao.service.impl;

import com.wbiao.goods.pojo.Spec;
import com.wbiao.goods.pojo.SpecGroup;
import com.wbiao.mapper.SpecMapper;
import com.wbiao.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecMapper specMapper;

    @Override
    public List<Spec> selectSpec() {
        return specMapper.selectSpec();
    }

    /**
     *  根据规格组查询规格参数
     * @return
     */
    @Override
    public List<SpecGroup> selectAllSpecGroup() {
        List<SpecGroup> groups = specMapper.selectAllSpecGroup();
        groups.forEach(g->{
            g.setSpecs(specMapper.selectSpecByGroupId(g.getId()));
        });
        return groups;
    }

}
