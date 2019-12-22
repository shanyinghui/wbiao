package com.wbiao.mapper;

import com.wbiao.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
   void insertBrand(Brand brand);
   List<Brand> selectBrand(@Param("name") String key);
   Brand selectBrandById(Integer id);
   void updateBrandById(Brand brand);
   void deleteBrand(Integer id);
}
