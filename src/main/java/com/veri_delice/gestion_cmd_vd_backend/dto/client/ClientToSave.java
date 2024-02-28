package com.veri_delice.gestion_cmd_vd_backend.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientToSave {
    private String name;
    private String lastName;
    private String numberPhone;
    private String address;
}
