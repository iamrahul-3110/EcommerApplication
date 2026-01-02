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

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        CategoryVo category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        ProductVo productVo = modelMapper.map(productDTO, ProductVo.class);
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
        CategoryVo category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        List<ProductVo> products = productRepository.findByCategoryVoOrderByProductPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        List<ProductVo> products = productRepository.findByProductNameContainingIgnoreCase(keyword);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProductByProductId(Long productId, ProductDTO productDTO) {
        // get the existing product from the database
        ProductVo existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

        // update the product details
        ProductVo productVo = modelMapper.map(productDTO, ProductVo.class);
        existingProduct.setProductName(productVo.getProductName());
        existingProduct.setProductDescription(productVo.getProductDescription());
        existingProduct.setProductPrice(productVo.getProductPrice());
        existingProduct.setDiscount(productVo.getDiscount());
        existingProduct.setQuantity(productVo.getQuantity());

        double specialPrice =  productVo.getProductPrice() -
                (productVo.getProductPrice() * productVo.getDiscount()/100);
        existingProduct.setSpecialPrice(specialPrice);

        // save to database
        ProductVo updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProductByProductId(Long productId) {
        ProductVo existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));
        productRepository.delete(existingProduct);
        return modelMapper.map(existingProduct, ProductDTO.class);
    }
}
