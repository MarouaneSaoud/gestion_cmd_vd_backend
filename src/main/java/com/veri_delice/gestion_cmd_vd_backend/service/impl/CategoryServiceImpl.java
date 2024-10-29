package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.constant.ResponseMessage.CategoryResponseMessage;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CategoryRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.SaveCategoryRequest;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.BusinessException;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.TechnicalException;
import com.veri_delice.gestion_cmd_vd_backend.mapper.CategoryMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Boolean checkIfCategoryExist(String categoryName) {
        return categoryRepository.getCategoryByName(categoryName) != null;
    }

    @Override
    public CategoryDto saveCategory(SaveCategoryRequest categoryDto) {
        try {
            if (this.checkIfCategoryExist(categoryDto.getName())) {
                throw new BusinessException(CategoryResponseMessage.CATEGORY_ALREADY_EXISTS);
            }
            Category category = Category.builder()
                    .id(UUID.randomUUID().toString())
                    .name(categoryDto.getName())
                    .build();
            return categoryMapper.toDto(categoryRepository.save(category));
        } catch (Exception e) {
            throw new TechnicalException(CategoryResponseMessage.CATEGORY_CREATION_ERROR);
        }
    }

    @Override
    public List<CategoryDto> AllCategory() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto)
                .sorted(Comparator.comparing(CategoryDto::getName)).toList();
    }

    @Override
    public CategoryDto categoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(CategoryResponseMessage.CATEGORY_NOT_FOUND, id)));
        return categoryMapper.toDto(category);
    }

}
