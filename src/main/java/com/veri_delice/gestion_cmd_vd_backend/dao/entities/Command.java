package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Payment;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Command extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(length = 500)
    private String description;

    private Date dateDelivery;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Payment payment;

    private Double advance;

    private Double total;

    @OneToMany(mappedBy = "command", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<ProductCommand> productCommands;

}
