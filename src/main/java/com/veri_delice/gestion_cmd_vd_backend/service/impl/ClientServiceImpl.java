package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientToSave;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ClientMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    @Override
    public ClientDto addclient(ClientToSave clientToSave) {
        Client c= clientMapper.toSaveDto(clientToSave);
        c.setId(UUID.randomUUID().toString());
        clientRepository.save(c);
        return clientMapper.toDto(c) ;
    }

    @Override
    public List<ClientDto> getAllClient() {
        List<Client> clients=clientRepository.findAll();
        return clients.stream().map(client -> {
            return clientMapper.toDto(client);
        }).sorted(Comparator.comparing(ClientDto::getName)).toList();

    }

    @Override
    public ClientDto getClientByid(String id) {
        Client client=clientRepository.findById(id).orElse(null);

        if (client!=null){
            return clientMapper.toDto(client);
        }
        else
            return null;
    }
}
