package com.ecom.project.ecommerceapp.category.service.impl;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.payload.CategoryDTO;
import com.ecom.project.ecommerceapp.category.payload.CategoryDtoResponse;
import com.ecom.project.ecommerceapp.category.repository.CategoryRepository;
import com.ecom.project.ecommerceapp.category.service.CategoryService;
import com.ecom.project.ecommerceapp.common.exception.ApiException;
import com.ecom.project.ecommerceapp.common.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // without this annotation, Spring won't recognize this class as a service component
public class CategoryServiceImpl implements CategoryService {

//    private List<CategoryVo> categoryList = new ArrayList<>();

    @Autowired
    private CategoryRepository categoryRepository; // dependency injection of repository

    @Autowired
    private ModelMapper modelMapper; // model mapper for DTO conversion for better practice

    @Override
    public CategoryDtoResponse getAllCategories() {
        List<CategoryVo> categoryList = categoryRepository.findAll(); // fetch all categories from database
        if(categoryList.isEmpty()) {
            throw new ApiException("No categories found in the database.");
        }
        List<CategoryDTO> categoryDTOS = categoryList.stream()
                .map(categoryVo -> modelMapper.map(categoryVo, CategoryDTO.class))
                .toList(); // converting entity to DTO using model mapper

        CategoryDtoResponse response = new CategoryDtoResponse();
        response.setContent(categoryDTOS);
        return response; // returning the list of categories using DTO response which is independent of entity and database schema
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        CategoryVo categoryVo = modelMapper.map(categoryDTO, CategoryVo.class); // converting DTO to entity using model mapper
        CategoryVo categoryFromDB = categoryRepository.findByCategoryName(categoryVo.getCategoryName());
        if (categoryFromDB != null) {
            throw new ApiException("Category with name '" + categoryVo.getCategoryName() + "' already exists."); // throwing custom exception if category already exists
        }
        categoryVo.setCategoryId(null); // Ensure the ID is null so that JPA generates a new ID
        CategoryVo saveCategory = categoryRepository.save(categoryVo); // saves the category to database
        return modelMapper.map(saveCategory, CategoryDTO.class); // converting entity back to DTO and returning
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
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
        return modelMapper.map(toRemove, CategoryDTO.class); // returning the deleted category as DTO
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        CategoryVo categoryVo = modelMapper.map(categoryDTO, CategoryVo.class); // converting DTO to entity using model mapper
        CategoryVo savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryVo.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(categoryVo);
        return modelMapper.map(savedCategory, CategoryDTO.class); // converting entity back to DTO and returning
    }
}
