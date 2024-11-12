package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.constant.ResponseMessage.CategoryResponseMessage;
import com.veri_delice.gestion_cmd_vd_backend.constant.ResponseMessage.UserResponseMessage;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CategoryRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.UserRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.SaveCategoryRequest;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.BusinessException;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.TechnicalException;
import com.veri_delice.gestion_cmd_vd_backend.mapper.CategoryMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserRepository userRepository;

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
            if (userRepository.findByEmail(categoryDto.getEmail()).get()==null){
                throw new BusinessException(UserResponseMessage.USER_NOT_FOUND);
            }
            Category categoryByName = categoryRepository.getCategoryByName(categoryDto.getParentCategory());
            Category category = Category.builder()
                    .id(UUID.randomUUID().toString())
                    .name(categoryDto.getName())
                    .parentCategory(categoryByName)
                    .user(userRepository.findByEmail(categoryDto.getEmail()).get())
                    .build();
            return categoryMapper.toDto(categoryRepository.save(category));
        } catch (Exception e) {
            throw new TechnicalException(CategoryResponseMessage.CATEGORY_CREATION_ERROR);
        }
    }
    @Override
    public List<CategoryDto> AllCategory() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, CategoryDto> categoryDtoMap = new HashMap<>();

        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> {
                    CategoryDto dto = categoryMapper.toDto(category);
                    dto.setCategoryOwner(category.getUser().getEmail());
                    if (category.getParentCategory() != null) {
                        dto.setParentCategoryId(category.getParentCategory().getId());

                        dto.setParentCategoryName(category.getParentCategory().getName());
                    }
                    categoryDtoMap.put(dto.getId(), dto);

                    return dto;
                })
                .collect(Collectors.toList());

        categoryDtos.forEach(dto -> {
            if (dto.getParentCategoryId() != null) {
                CategoryDto parentDto = categoryDtoMap.get(dto.getParentCategoryId());
                if (parentDto != null) {
                    if (parentDto.getSubCategories() == null) {
                        parentDto.setSubCategories(new ArrayList<>());
                    }
                    parentDto.getSubCategories().add(dto);
                }
            }
        });
        List<CategoryDto> rootCategories = categoryDtos.stream()
                .filter(dto -> dto.getParentCategoryId() == null)
                .sorted(Comparator.comparing(CategoryDto::getName))
                .collect(Collectors.toList());

        return rootCategories;
    }
    @Override
    public CategoryDto categoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(CategoryResponseMessage.CATEGORY_NOT_FOUND, id)));
        return categoryMapper.toDto(category);
    }
}
