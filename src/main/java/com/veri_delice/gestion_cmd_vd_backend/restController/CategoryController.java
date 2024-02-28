package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/addCategory")
    public CategoryDto saveCategory (@RequestBody CategoryDto categoryDto){
        return categoryService.saveCategory(categoryDto);
    }
    @GetMapping("/allCategories")
    public List<CategoryDto> getAllCategory(){
        return categoryService.getAllCategory();
    }
    @GetMapping("/Category/{id}")
    public CategoryDto getById(@PathVariable String id){
        return categoryService.getById(id);
    }
}
