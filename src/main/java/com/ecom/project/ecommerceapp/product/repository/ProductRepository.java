package com.ecom.project.ecommerceapp.product.repository;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.product.model.ProductVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductVo, Long> {

    List<ProductVo> findByCategoryVoOrderByProductPriceAsc(CategoryVo category); // For searching products by category ordered by price ascending

    List<ProductVo> findByProductNameContainingIgnoreCase(String keyword); // For searching products by keyword ignoring case
}
