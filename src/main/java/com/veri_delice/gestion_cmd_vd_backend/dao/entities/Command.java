package com.veri_delice.gestion_cmd_vd_backend.dao.entities;

import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Payment;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data @NoArgsConstructor @AllArgsConstructor
public class Command {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(length = 500)
    private String description;

    @CreationTimestamp
    private Date dateCommand;

    private Date dateDelivery;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Payment payment;

    private Double advance;

    @OneToMany(mappedBy = "command", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProductCommand> productCommands;

}
