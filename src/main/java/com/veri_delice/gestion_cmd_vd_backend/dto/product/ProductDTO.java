package com.veri_delice.gestion_cmd_vd_backend.dto.product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.ProductSizeStock;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UniteProd;
import com.veri_delice.gestion_cmd_vd_backend.dto.ProductSizeStock.ProductSizeStockDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Double Tva;
    private Double stock;
    private Long barcode;
    private Double discount;
    private String pictures;
    private UniteProd uniteProd;
    private Date createdAt;
    private Date updatedAt;
    private CategoryDto category;
    private Set<ProductSizeStockDto> productSizeStockDtos;
}
