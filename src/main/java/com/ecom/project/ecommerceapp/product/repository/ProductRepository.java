package com.ecom.project.ecommerceapp.product.repository;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.product.model.ProductVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductVo, Long> {

    Page<ProductVo> findByCategoryVoOrderByProductPriceAsc(CategoryVo category, Pageable pageable); // For searching products by category ordered by price ascending

    Page<ProductVo> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable); // For searching products by keyword ignoring case

    Optional<Object> findByProductNameAndCategoryVo(String productName, CategoryVo category);
}
