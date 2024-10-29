package com.veri_delice.gestion_cmd_vd_backend.service;

import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.SaveCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDto saveCategory(SaveCategoryRequest saveCategoryRequest);

    List<CategoryDto> AllCategory();

    CategoryDto categoryById(String id);

    Boolean checkIfCategoryExist(String categoryName);
}
