package com.ecom.project.ecommerceapp.category.service.impl;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.repository.CategoryRepository;
import com.ecom.project.ecommerceapp.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        return categoryRepository.findAll(); // fetches all categories from database
    }

    @Override
    public void addCategory(CategoryVo categoryVo) {
        categoryVo.setCategoryId(null); // Ensure the ID is null so that JPA generates a new ID
        categoryRepository.save(categoryVo); // saves the category to database
    }

    @Override
    public String deleteCategory(Long categoryId) {
        CategoryVo toRemove = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + categoryId + " not found."));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + categoryId + " not found."));

        categoryVo.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(categoryVo);
        return savedCategory;
    }
}
