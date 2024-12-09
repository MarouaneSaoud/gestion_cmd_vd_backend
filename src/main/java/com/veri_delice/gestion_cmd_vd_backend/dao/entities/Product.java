package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UniteProd;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product extends BaseEntity {

    @Column(unique = true)
    private String name;

    @Column(length = 225)
    private String description;

    private Double price;

    private Double Tva;

    private Double discount;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<ProductSizeStock> productSizeStocks;

    private Long barcode;

    private String pictures;

    private Double stock;

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
