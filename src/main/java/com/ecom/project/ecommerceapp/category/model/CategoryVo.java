package com.ecom.project.ecommerceapp.category.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_category") // table name will be same as class name by default to customize use @Table annotation or specify name in @Entity
@Data // generates getters and setters using lombok
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    @Id // must add annotation for primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
}
