package com.ecom.project.ecommerceapp.category.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoResponse {
    private List<CategoryDTO> content;
}
