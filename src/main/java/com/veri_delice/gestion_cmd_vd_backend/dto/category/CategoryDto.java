package com.veri_delice.gestion_cmd_vd_backend.dto.category;

import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String id;
    private String name;
}
