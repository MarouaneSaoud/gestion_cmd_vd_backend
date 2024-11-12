package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UniteProd;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{

    @Column(unique = true)
    private String name;

    @Column(length = 225)
    private String description;

    private Double price;

    private Double Tva;

    private Double discount;

    private Double Stock;

    private Long barcode;

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private List<String> size;

    private String pictures;

    @OneToMany(mappedBy = "product")
    private List<ProductCommand> productCommands;

    @Enumerated(EnumType.STRING)
    private UniteProd uniteProd;

    @ManyToOne
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
