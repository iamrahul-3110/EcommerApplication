package com.ecom.project.ecommerceapp.category.repository;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryVo, Long> {// to params are entity class and primary key type

}
