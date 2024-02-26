package com.veri_delice.gestion_cmd_vd_backend.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommandDto {
    private String id;
    private String description;
    private Date dateDelivery;
    private Double advance;
}
