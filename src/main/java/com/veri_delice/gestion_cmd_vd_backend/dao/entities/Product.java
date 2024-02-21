package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id
    private String id;

    @Column(unique = true)
    private String name;

    @Column(length = 225)
    private String description;

    private Double price;
    private String pictures;

    @OneToMany(mappedBy = "product")
    private List<ProductCommand> productCommands;
}
