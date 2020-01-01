package com.wbiao.service;

import com.wbiao.goods.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> selectCategoryByParentId(Integer parent_id);

    void insertCategory(Category category);

    void updateCategoryById(String name,Integer id);

    void deleteCategoryById(Integer id);

    Category selectCategoryById(Integer category_id);
}
