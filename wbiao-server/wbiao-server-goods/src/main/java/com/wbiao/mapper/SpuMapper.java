package com.wbiao.mapper;

import com.wbiao.goods.pojo.Spu;

public interface SpuMapper {

    void addSpu(Spu spu);

    Spu selectSpuById(String id);

    void updateSpuById(Spu spu);
}
