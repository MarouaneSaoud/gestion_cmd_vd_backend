package com.veri_delice.gestion_cmd_vd_backend.service;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientToSave;

import java.util.List;

public interface ClientService {
    ClientDto addclient(ClientToSave clientToSave);
    List<ClientDto> getAllClient();
    ClientDto getClientByid(String id);
}
