package com.veri_delice.gestion_cmd_vd_backend.dto.auth;

import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private String email;
    private UserStatus userStatus;
    private Set<RoleDTO> roles;

}
