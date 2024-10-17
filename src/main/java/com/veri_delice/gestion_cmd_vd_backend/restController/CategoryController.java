package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.constant.path.CategoryPath;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CategoryPath.baseUrl)
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(CategoryPath.addCategory)
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(categoryDto);
    }

    @GetMapping(CategoryPath.allCategory)
    public List<CategoryDto> allCategory() {
        return categoryService.AllCategory();
    }

    @GetMapping(CategoryPath.categoryById)
    public CategoryDto getById(@PathVariable String id) {
        return categoryService.categoryById(id);
    }
}
