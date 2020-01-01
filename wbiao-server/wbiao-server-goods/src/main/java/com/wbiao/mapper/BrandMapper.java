package com.wbiao.mapper;

import com.wbiao.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
   void insertBrand(Brand brand);
   List<Brand> selectBrand(@Param("name") String key);
   Brand selectBrandById(Integer id);
   void updateBrandById(Brand brand);
   void deleteBrand(Integer id);
   List<Brand> selectBrandByC_id(Integer category_id);

}
