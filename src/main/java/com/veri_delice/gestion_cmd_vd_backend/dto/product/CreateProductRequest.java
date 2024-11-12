package com.veri_delice.gestion_cmd_vd_backend.dto.product;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UniteProd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private Double Tva;
    private Double discount;
    private String pictures;
    private UniteProd uniteProd;
    private String idCategory;
    private String userEmail;
    private Long barcode;
    private List<String> size;
}
