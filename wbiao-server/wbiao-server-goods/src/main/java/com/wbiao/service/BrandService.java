package com.wbiao.service;

import com.wbiao.goods.pojo.Brand;
import com.wbiao.util.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService {
   void insertBrand(Brand brand);
   PageResult<Brand> selectBrand(String key, Integer page, Integer rows, String sortBy, Boolean desc);
   Brand selectBrandById(Integer id);
   void updateBrandById(Brand brand);
   void deleteBrand(Integer id,String name);
   Map<String, List<Brand>> selectAllBrand();
}
