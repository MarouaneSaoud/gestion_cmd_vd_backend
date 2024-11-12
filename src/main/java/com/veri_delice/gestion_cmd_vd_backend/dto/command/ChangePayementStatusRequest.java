package com.veri_delice.gestion_cmd_vd_backend.dto.command;

import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePayementStatusRequest {
    private String id;
    private Payment payment;

}
