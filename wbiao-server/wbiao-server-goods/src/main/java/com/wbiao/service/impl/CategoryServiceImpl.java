package com.wbiao.service.impl;

import com.wbiao.mapper.CategoryMapper;
import com.wbiao.goods.pojo.Category;
import com.wbiao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> selectCategoryByParentId(Integer parent_id) {
        return categoryMapper.selectCategoryByParentId(parent_id);
    }

    @Override
    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    @Override
    public void updateCategoryById(String name, Integer id) {
        categoryMapper.updateCategoryById(name,id);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        categoryMapper.deleteCategoryById(id);
    }

    @Override
    public Category selectCategoryById(Integer category_id) {
        return categoryMapper.selectCategoryById(category_id);
    }
}
