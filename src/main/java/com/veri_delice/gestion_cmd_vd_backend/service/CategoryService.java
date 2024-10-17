package com.veri_delice.gestion_cmd_vd_backend.service;

import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto saveCategory(CategoryDto categoryDto);

    List<CategoryDto> AllCategory();

    CategoryDto categoryById(String id);

    Boolean checkIfCategoryExist(String categoryName);
}
