package com.veri_delice.gestion_cmd_vd_backend.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveClientRequest {
    private String name;
    private String lastName;
    private String numberPhone;
    private String address;
    private String email;
}
