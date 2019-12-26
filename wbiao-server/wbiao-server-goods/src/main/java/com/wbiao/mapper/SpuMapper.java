package com.wbiao.mapper;

import com.wbiao.pojo.Spu;

public interface SpuMapper {

    void addSpu(Spu spu);

    Spu selectSpuById(String id);

    void updateSpuById(Spu spu);
}
