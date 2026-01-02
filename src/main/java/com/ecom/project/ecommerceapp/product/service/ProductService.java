package com.ecom.project.ecommerceapp.product.service;

import com.ecom.project.ecommerceapp.product.payload.ProductDTO;
import com.ecom.project.ecommerceapp.product.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productVo, Long categoryId);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ProductResponse searchProductsByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProductByProductId(Long productId, ProductDTO productVo);

    ProductDTO deleteProductByProductId(Long productId);
}
