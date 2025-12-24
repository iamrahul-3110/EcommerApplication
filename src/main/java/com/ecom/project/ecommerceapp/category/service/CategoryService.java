package com.ecom.project.ecommerceapp.category.service;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;

import java.util.List;

public interface CategoryService {
    List<CategoryVo> getAllCategories();

    void addCategory(CategoryVo categoryVo);

    String deleteCategory(Long categoryId);
}
