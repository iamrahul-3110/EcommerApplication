package com.ecom.project.ecommerceapp.category.service.impl;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service // without this annotation, Spring won't recognize this class as a service component
public class CategoryServiceImpl implements CategoryService {

    private List<CategoryVo> categoryList = new ArrayList<>();
    private Long idCounter = 1L;

    @Override
    public List<CategoryVo> getAllCategories() {
        return categoryList;
    }

    @Override
    public void addCategory(CategoryVo categoryVo) {
        categoryVo.setCategoryId(idCounter++);
        categoryList.add(categoryVo);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        CategoryVo toRemove = categoryList.stream()
                .filter(cat -> cat.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + categoryId + " not found."));

        categoryList.remove(toRemove);
        return "Category deletion has been done for : " + categoryId;
    }
}
