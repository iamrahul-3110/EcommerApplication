package com.ecom.project.ecommerceapp.product.service.impl;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.repository.CategoryRepository;
import com.ecom.project.ecommerceapp.common.exception.ResourceNotFoundException;
import com.ecom.project.ecommerceapp.product.model.ProductVo;
import com.ecom.project.ecommerceapp.product.payload.ProductDTO;
import com.ecom.project.ecommerceapp.product.payload.ProductResponse;
import com.ecom.project.ecommerceapp.product.repository.ProductRepository;
import com.ecom.project.ecommerceapp.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductVo productVo, Long categoryId) {
        CategoryVo category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        productVo.setCategoryVo(category);
        productVo.setImage("default.png");
        double specialPrice =  productVo.getProductPrice() -
                (productVo.getProductPrice() * productVo.getDiscount()/100);
        productVo.setSpecialPrice(specialPrice);
        ProductVo savedProduct = productRepository.save(productVo);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        List<ProductVo>  products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByCategory(Long categoryId) {
        List<ProductVo> products = productRepository.findAll()
                .stream()
                .filter(product -> product.getCategoryVo().getCategoryId().equals(categoryId))
                .toList();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }
}
