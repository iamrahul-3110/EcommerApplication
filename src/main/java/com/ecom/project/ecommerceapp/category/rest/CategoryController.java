package com.ecom.project.ecommerceapp.category.rest;

import com.ecom.project.ecommerceapp.category.payload.CategoryDTO;
import com.ecom.project.ecommerceapp.category.payload.CategoryDtoResponse;
import com.ecom.project.ecommerceapp.category.service.CategoryService;
import com.ecom.project.ecommerceapp.common.config.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // Base URL for all endpoints in this controller
public class CategoryController {

    @Autowired // No need for explicit constructor injection
    private CategoryService categoryService;

    @GetMapping("/public/echo") // this is only for understanding purpose of @RequestParam
//    public ResponseEntity<String > echoMessage(@RequestParam(name = "message", defaultValue = "Hello World" ) String message) { // @RequestParam to get query parameter from URL
    public ResponseEntity<String > echoMessage(@RequestParam(name = "message", required = false ) String message) { // required = false makes the parameter optional
        return new ResponseEntity<>("Echo: " + message, HttpStatus.OK); // if you not provide message parameter it will take default value "Hello World"
    }

    // @RequestMapping(value = "/public/categories/get", method = RequestMethod.GET) // Old way of mapping GET request this is for all HTTP methods
    @GetMapping("/public/categories/get")
    public ResponseEntity<CategoryDtoResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY) String sortBy, // sortBy is the field name to sort
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION ) String sortOrder // choose between asc and desc
    ) {
        CategoryDtoResponse categoriesResponse = categoryService.getAllCategories(pageNumber,pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoriesResponse, HttpStatus.OK); // returning 200 OK with body
    }

    @PostMapping("/admin/categories/add")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) { // @Valid to enable validation on request body with proper response status work as per model annotations
        CategoryDTO savedCategoryDTO = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED); // the exception handling is done in service layer using springboot exceptions we can set our own global exception handler if needed
    }

    @DeleteMapping("/admin/categories/delete/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
//        try {
//            String status = categoryService.deleteCategory(categoryId);
//            return ResponseEntity.ok(status); // Another way to send 200 OK with body
//            return ResponseEntity.status(HttpStatus.OK).body(status); // Another way to send 200 OK with body
//            return new ResponseEntity<>(status, HttpStatus.OK); // Now we have grip over status code using ResponseEntity
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<>(e.getReason(),e.getStatusCode()); // wrap the error message and status code in the response entity
//        }
        CategoryDTO deleteCategory = categoryService.deleteCategory(categoryId); // above try-catch is not needed as we are handling exceptions globally
        return new ResponseEntity<>(deleteCategory, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/update/{categoryId}")
    public ResponseEntity<CategoryDTO> upadateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO) {
//        try {
//            CategoryVo saveCategory = categoryService.updateCategory(categoryVo, categoryId);
//            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}
