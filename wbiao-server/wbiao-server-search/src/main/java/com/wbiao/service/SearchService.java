package com.wbiao.service;

import java.util.Map;

public interface SearchService {
    void importData();

    Map<String,Object> SearchGoods(Map<String,String> searchMap);
}
