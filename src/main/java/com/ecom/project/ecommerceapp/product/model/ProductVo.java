package com.ecom.project.ecommerceapp.product.model;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.payload.CategoryDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    @Size(min=3, message = "Product name must contain least 3 char")
    private String productName;
    @NotBlank
    @Size(min=6 , message = "Product description must contain least 6 char")
    private String productDescription;
    private Double productPrice;
    private Double discount;
    private String image;
    private Double specialPrice;
    private  Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryVo categoryVo;
}
