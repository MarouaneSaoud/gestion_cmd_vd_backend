package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Client extends BaseEntity {

    private String name;
    private String lastName;
    private String numberPhone;
    private String address;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Command> commands;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
