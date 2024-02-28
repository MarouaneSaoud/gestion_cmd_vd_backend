package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CategoryRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.mapper.CategoryMapper;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ProductMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category categorie = Category.builder()
                .id(UUID.randomUUID().toString())
                .name(categoryDto.getName())
                .build();
        Category category = categoryRepository.save(categorie);
        CategoryDto c = categoryMapper.toDto(category);
        return c;
    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map(category -> {
                return categoryMapper.toDto(category);
        }).sorted(Comparator.comparing(CategoryDto::getName)).toList();

    }

    @Override
    public CategoryDto getById(String id) {
        Category category=categoryRepository.findById(id).orElse(null);

        if (category!=null){
            return categoryMapper.toDto(category);
        }
        else
        return null;
    }


}


