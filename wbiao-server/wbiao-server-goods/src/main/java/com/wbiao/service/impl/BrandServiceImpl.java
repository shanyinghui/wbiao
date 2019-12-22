package com.wbiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wbiao.mapper.BrandMapper;
import com.wbiao.mapper.CategoryMapper;
import com.wbiao.pojo.Brand;
import com.wbiao.pojo.Category;
import com.wbiao.service.BrandService;
import com.wbiao.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void insertBrand(Brand brand) {
        brandMapper.insertBrand(brand);
        //添加品牌的同时，在分类表同时添加一条数据
        Category category = new Category();
        category.setName(brand.getName());
        category.setParentId(brand.getCategory_id());
        category.setIsParent(false);
        if(categoryMapper.selectMaxSort(brand.getCategory_id()) != null){
            category.setSort(categoryMapper.selectMaxSort(brand.getCategory_id())+1);
        }else{
            category.setSort(1);
        }
        categoryMapper.insertCategory(category);
    }

    @Override
    public PageResult<Brand> selectBrand(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //判断是否需要排序
        if (StringUtils.isNotBlank(sortBy)) {
            //排序的字段和规则
           String orderby = sortBy + " " + (desc ? "desc" : "asc");
            PageHelper.startPage(page,rows,orderby);
        }else{
            PageHelper.startPage(page,rows);
        }
        List<Brand> brands = brandMapper.selectBrand(key);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        PageResult<Brand> pr = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return pr;
    }

    @Override
    public Brand selectBrandById(Integer id) {
        return brandMapper.selectBrandById(id);
    }

    @Override
    public void updateBrandById(Brand brand) {
        Brand b = brandMapper.selectBrandById(brand.getId());
        brandMapper.updateBrandById(brand);
        //三个参数分别是：修改前的名字，修改后的名字，修改后的分类id
        categoryMapper.updateCategoryByName(b.getName(),brand.getName(),brand.getCategory_id());
    }

    @Override
    public void deleteBrand(Integer id,String name) {
        brandMapper.deleteBrand(id);
        categoryMapper.deleteCategoryByName(name);
    }
}
