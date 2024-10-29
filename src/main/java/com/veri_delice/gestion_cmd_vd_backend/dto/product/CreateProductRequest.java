package com.veri_delice.gestion_cmd_vd_backend.dto.product;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UniteProd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private String pictures;
    private UniteProd uniteProd;
    private String idCategory;
    private String userEmail;
}
