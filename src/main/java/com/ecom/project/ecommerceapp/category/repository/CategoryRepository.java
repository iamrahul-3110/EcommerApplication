package com.ecom.project.ecommerceapp.category.repository;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryVo, Long> {
    CategoryVo findByCategoryName(String categoryName); // custom method to find category by name and jpa will implement it automatically
}
