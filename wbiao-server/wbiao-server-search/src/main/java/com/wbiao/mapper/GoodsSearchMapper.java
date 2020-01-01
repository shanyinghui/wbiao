package com.wbiao.mapper;

import com.wbiao.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsSearchMapper extends ElasticsearchRepository<SkuInfo,String> {
}
