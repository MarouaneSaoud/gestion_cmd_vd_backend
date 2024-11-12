package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.constant.path.CategoryPath;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.SaveCategoryRequest;
import com.veri_delice.gestion_cmd_vd_backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CategoryPath.BASE_URL)
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping(CategoryPath.ADD_CATEGORY)
    public CategoryDto saveCategory(@RequestBody SaveCategoryRequest categoryDto) {
        return categoryService.saveCategory(categoryDto);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(CategoryPath.ALL_CATEGORY)
    public List<CategoryDto> allCategory() {
        return categoryService.AllCategory();
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(CategoryPath.CATEGORY_BY_ID)
    public CategoryDto getById(@PathVariable String id) {
        return categoryService.categoryById(id);
    }
}
