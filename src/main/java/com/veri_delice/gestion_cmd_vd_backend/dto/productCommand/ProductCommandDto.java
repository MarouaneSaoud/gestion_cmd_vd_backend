package com.veri_delice.gestion_cmd_vd_backend.dto.productCommand;

import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommandDto {
    private Long id;
    private Integer qte;
    private CommandDto command;
    private ProductDTO product;
}
