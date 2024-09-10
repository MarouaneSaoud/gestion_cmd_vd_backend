package com.veri_delice.gestion_cmd_vd_backend.service.impl;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CategoryRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
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
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        try {
            Category category = Category.builder()
                    .id(UUID.randomUUID().toString())
                    .name(categoryDto.getName())
                    .build();
            CategoryDto c = categoryMapper.toDto(categoryRepository.save(category));
            return c;
        }catch (Exception e ){
            throw new TechnicalException("Une erreur s'est produite lors de la création du Category ");

        }

    }
    @Override
    public List<CategoryDto> getAllCategory() {
        return categoryRepository.findAll().stream().map(category -> {
                return categoryMapper.toDto(category);
        }).sorted(Comparator.comparing(CategoryDto::getName)).toList();
    }
    @Override
    public CategoryDto getById(String id) {
        Category category=categoryRepository.findById(id).orElse(null);
        if (category!=null){
            return categoryMapper.toDto(category);
        }
        else throw new BusinessException("La commande avec l'ID"+id+" n'existe pas.");
    }
}


