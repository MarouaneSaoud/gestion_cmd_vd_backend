package com.veri_delice.gestion_cmd_vd_backend.mapper;

import com.veri_delice.gestion_cmd_vd_backend.config.ModelMapperConfig;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {
    private final ModelMapperConfig modelMapperConfig;


    public CategoryDto toDto(Category category) {
        return modelMapperConfig.modelMapper().map(category, CategoryDto.class);
    }

    public Category toEntity(CategoryDto categoryDto) {
        return modelMapperConfig.modelMapper().map(categoryDto, Category.class);
    }

}
