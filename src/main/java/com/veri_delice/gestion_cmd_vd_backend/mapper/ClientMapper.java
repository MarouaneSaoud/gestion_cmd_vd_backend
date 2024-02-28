package com.veri_delice.gestion_cmd_vd_backend.mapper;

import com.veri_delice.gestion_cmd_vd_backend.config.ModelMapperConfig;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientToSave;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ToCreateProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientMapper {

    private final ModelMapperConfig modelMapperConfig;


    public ClientDto toDto(Client client) {
        return modelMapperConfig.modelMapper().map(client, ClientDto.class);
    }

    public Client toEntity(ClientDto clientDto) {
        return modelMapperConfig.modelMapper().map(clientDto, Client.class);
    }

    public Client toSaveDto(ClientToSave clientToSave) {
        Client map = modelMapperConfig.modelMapper().map(clientToSave, Client.class);
        return map;
    }
}
