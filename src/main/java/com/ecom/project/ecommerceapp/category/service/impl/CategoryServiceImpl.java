package com.ecom.project.ecommerceapp.category.service.impl;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.repository.CategoryRepository;
import com.ecom.project.ecommerceapp.category.service.CategoryService;
import com.ecom.project.ecommerceapp.common.exception.ApiException;
import com.ecom.project.ecommerceapp.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.swing.*;
import java.util.List;

@Service // without this annotation, Spring won't recognize this class as a service component
public class CategoryServiceImpl implements CategoryService {

//    private List<CategoryVo> categoryList = new ArrayList<>();

    @Autowired
    private CategoryRepository categoryRepository; // dependency injection of repository
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public List<CategoryVo> getAllCategories() {
        List<CategoryVo> categoryList = categoryRepository.findAll();
        if(categoryList.isEmpty()) {
            throw new ApiException("No categories found in the database.");
        }
        return categoryRepository.findAll(); // fetches all categories from database
    }

    @Override
    public void addCategory(CategoryVo categoryVo) {
        CategoryVo savedCategory = categoryRepository.findByCategoryName(categoryVo.getCategoryName());
        if (savedCategory != null) {
            throw new ApiException("Category with name '" + categoryVo.getCategoryName() + "' already exists."); // throwing custom exception if category already exists
        }
        categoryVo.setCategoryId(null); // Ensure the ID is null so that JPA generates a new ID
        categoryRepository.save(categoryVo); // saves the category to database
    }

    @Override
    public String deleteCategory(Long categoryId) {
        CategoryVo toRemove = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        // normal way without using JPA repository methods
//        List<CategoryVo> cotegoryList = categoryRepository.findAll(); // fetch all categories from database
//
//        CategoryVo toRemove = cotegoryList.stream()
//                .filter(cat -> cat.getCategoryId().equals(categoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + categoryId + " not found."));

        categoryRepository.delete(toRemove); // removing from database
        return "Category deletion has been done for : " + categoryId;
    }

    @Override
    public CategoryVo updateCategory(CategoryVo categoryVo, Long categoryId) {
        CategoryVo savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryVo.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(categoryVo);
        return savedCategory;
    }
}
