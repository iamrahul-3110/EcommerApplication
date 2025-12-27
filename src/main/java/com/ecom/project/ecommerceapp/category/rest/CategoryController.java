package com.ecom.project.ecommerceapp.category.rest;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api") // Base URL for all endpoints in this controller
public class CategoryController {

    @Autowired // No need for explicit constructor injection
    private CategoryService categoryService;

    // @RequestMapping(value = "/public/categories/get", method = RequestMethod.GET) // Old way of mapping GET request this is for all HTTP methods
    @GetMapping("/public/categories/get")
    public ResponseEntity<List<CategoryVo>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/admin/categories/add")
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryVo categoryVo) { // @Valid to enable validation on request body with proper response status work as per model annotations
        categoryService.addCategory(categoryVo);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED); // the exception handling is done in service layer using springboot exceptions we can set our own global exception handler if needed
    }

    @DeleteMapping("/admin/categories/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
//        try {
//            String status = categoryService.deleteCategory(categoryId);
//            return ResponseEntity.ok(status); // Another way to send 200 OK with body
//            return ResponseEntity.status(HttpStatus.OK).body(status); // Another way to send 200 OK with body
//            return new ResponseEntity<>(status, HttpStatus.OK); // Now we have grip over status code using ResponseEntity
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<>(e.getReason(),e.getStatusCode()); // wrap the error message and status code in the response entity
//        }
        String status = categoryService.deleteCategory(categoryId); // above try-catch is not needed as we are handling exceptions globally
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/update/{categoryId}")
    public ResponseEntity<String> upadateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryVo categoryVo) {
//        try {
//            CategoryVo saveCategory = categoryService.updateCategory(categoryVo, categoryId);
//            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }
        CategoryVo saveCategory = categoryService.updateCategory(categoryVo, categoryId);
        return new ResponseEntity<>("Category updated successfully with categoryId "+categoryId, HttpStatus.OK);
    }
}
