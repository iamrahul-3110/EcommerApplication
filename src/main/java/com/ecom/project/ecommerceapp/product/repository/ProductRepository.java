package com.ecom.project.ecommerceapp.product.repository;

import com.ecom.project.ecommerceapp.product.model.ProductVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductVo, Long> {
}
