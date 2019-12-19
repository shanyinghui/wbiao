package com.wbiao.mapper;

import com.wbiao.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    List<Category> selectCategoryByParentId(Integer parent_id);

    void insertCategory(Category category);

    void updateCategoryById(@Param("name")String name,@Param("id")Integer id);

    void deleteCategoryById(Integer id);
}
