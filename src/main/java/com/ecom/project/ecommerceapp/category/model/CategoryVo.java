package com.ecom.project.ecommerceapp.category.model;

import com.ecom.project.ecommerceapp.product.model.ProductVo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "tb_category")
// table name will be same as class name by default to customize use @Table annotation or specify name in @Entity
@Data // generates getters and setters using lombok
@NoArgsConstructor // It is used to generate a no-argument constructor
@AllArgsConstructor // It is used to generate a constructor with 1 parameter for each field in your class
public class CategoryVo {
    @Id // must add annotation for primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank  // validation to ensure category name is not null or empty, this only gives 500 Bad Request response when validation fails use @valid in controller method
    @Size(min = 5, message = "Category name must contain least 5 char") // validation to limit length of category name
    private String categoryName;

    @OneToMany(mappedBy = "categoryVo", cascade = CascadeType.ALL)
    private List<ProductVo> productVoList;
}
