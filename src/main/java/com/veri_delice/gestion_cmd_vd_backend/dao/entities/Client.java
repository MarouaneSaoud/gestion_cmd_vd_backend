package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class Client {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String numberPhone;
    private String address;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Command> commands;
}
