package com.ecom.project.ecommerceapp.product.service;

import com.ecom.project.ecommerceapp.product.model.ProductVo;
import com.ecom.project.ecommerceapp.product.payload.ProductDTO;
import com.ecom.project.ecommerceapp.product.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductVo productVo, Long categoryId);
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    ProductResponse searchProductsByCategory(Long categoryId);
}
