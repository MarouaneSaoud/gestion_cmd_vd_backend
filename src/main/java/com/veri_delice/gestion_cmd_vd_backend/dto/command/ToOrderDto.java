package com.veri_delice.gestion_cmd_vd_backend.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToOrderDto {
    private String idClient;
    private String description;
    private Double advance;
    private Date dateDelivery;
    private Map<String,Integer> items;
}
