package com.veri_delice.gestion_cmd_vd_backend.dto.category;

import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDto {

    private String id;
    private String name;
    private String categoryOwner;
    private String parentCategoryId;
    private String parentCategoryName;
    private List<CategoryDto> subCategories;

}
