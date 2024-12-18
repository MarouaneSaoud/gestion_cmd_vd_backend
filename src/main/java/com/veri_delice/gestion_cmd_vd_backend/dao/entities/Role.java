package com.veri_delice.gestion_cmd_vd_backend.dao.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class  Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Override
    public String getAuthority() {
        return name;
    }
}
