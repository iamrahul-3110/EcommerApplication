package com.ecom.project.ecommerceapp.category.rest;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired // No need for explicit constructor injection
    private CategoryService categoryService;

    @GetMapping("/api/public/categories/get")
    public List<CategoryVo> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/api/admin/categories/add")
    public CategoryVo addCategory(@RequestBody CategoryVo categoryVo) {
        categoryService.addCategory(categoryVo);
        return categoryVo;
    }

    @DeleteMapping("/api/admin/categories/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK); // Now we have grip over status code using ResponseEntity
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(),e.getStatusCode()); // wrap the error message and status code in the response entity
        }
    }
}
