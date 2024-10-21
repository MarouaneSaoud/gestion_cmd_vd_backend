package com.veri_delice.gestion_cmd_vd_backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private String roleName;  // Role name field

}
