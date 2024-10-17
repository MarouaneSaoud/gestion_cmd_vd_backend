package com.veri_delice.gestion_cmd_vd_backend.service;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientToSave;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;

import java.util.List;

public interface ClientService {
    ClientDto addClient(ClientToSave clientToSave);
    List<ClientDto> allClient();
    ClientDto clientById(String id);
    List<CommandDto> commandByClient(String idClient);
}
