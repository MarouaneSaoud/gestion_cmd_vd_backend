package com.veri_delice.gestion_cmd_vd_backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserStatistics {
    private int nbClient;
    private int nbProduct;
    private int nbCommand;
}
