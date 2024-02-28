package com.veri_delice.gestion_cmd_vd_backend.dto.command;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommandDto {
    private String id;
    private String description;
    private Date dateDelivery;
    private Map<String,Integer> items;
}
