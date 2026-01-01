package com.ecom.project.ecommerceapp.product.model;

import com.ecom.project.ecommerceapp.category.model.CategoryVo;
import com.ecom.project.ecommerceapp.category.payload.CategoryDTO;
import jakarta.persistence.*;
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
    private String productName;
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
