package com.ecom.project.ecommerceapp.category.service;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.payload.CategoryDTO;
import com.ecom.project.ecommerceapp.category.payload.CategoryDtoResponse;

public interface CategoryService {
    CategoryDtoResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
