package com.veri_delice.gestion_cmd_vd_backend.dto.ProductSizeStock;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSizeStockDto {
    private String size ;
    private Double stock;
}
