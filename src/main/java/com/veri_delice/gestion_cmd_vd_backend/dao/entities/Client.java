package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String numberPhone;
    private String address;
    @OneToMany(mappedBy = "client")
    private List<Command> commands;
}
