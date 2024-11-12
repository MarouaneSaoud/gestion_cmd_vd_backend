package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Data
@EqualsAndHashCode
public class Category extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
}