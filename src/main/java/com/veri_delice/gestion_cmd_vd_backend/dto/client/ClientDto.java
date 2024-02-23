package com.veri_delice.gestion_cmd_vd_backend.dto.client;

import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private String id;
    private String name;
    private String lastName;
    private String numberPhone;
    private String address;
}
