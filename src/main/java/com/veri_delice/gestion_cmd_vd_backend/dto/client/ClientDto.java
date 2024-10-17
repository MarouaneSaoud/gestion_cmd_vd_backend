package com.veri_delice.gestion_cmd_vd_backend.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private String id;
    private String name;
    private String lastName;
    private String numberPhone;
    private String address;
    private Date createdAt;
    private Date updatedAt;
}
