package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer qte;

    @ManyToOne
    @JoinColumn(name = "command_id")
    @JsonManagedReference
    private Command command;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
