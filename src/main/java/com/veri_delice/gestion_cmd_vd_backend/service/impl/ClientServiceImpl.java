package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.constant.ResponseMessage.ClientResponseMessage;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientToSave;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.BusinessException;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.TechnicalException;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ClientMapper;
import com.veri_delice.gestion_cmd_vd_backend.mapper.CommandMapper;
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
    private final CommandMapper commandMapper;

    @Override
    public ClientDto addClient(ClientToSave clientToSave) {
        try {
            Client c = clientMapper.toSaveDto(clientToSave);
            c.setId(UUID.randomUUID().toString());
            return clientMapper.toDto(clientRepository.save(c));
        } catch (Exception e) {
            throw new TechnicalException(ClientResponseMessage.CLIENT_CREATION_ERROR);
        }
    }

    @Override
    public List<ClientDto> allClient() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .sorted(Comparator.comparing(ClientDto::getCreatedAt))
                .toList();
    }

    @Override
    public ClientDto clientById(String id) {
        return clientMapper.toDto(clientRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(ClientResponseMessage.CLIENT_NOT_FOUND, id))));
    }

    @Override
    public List<CommandDto> commandByClient(String idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if (client != null) {
            return clientRepository.findCommandsByClient(client).stream()
                    .map(commandMapper::toFullDto)
                    .toList();
        } else {
            throw new BusinessException(String.format(ClientResponseMessage.COMMAND_NOT_FOUND_FOR_CLIENT, idClient));
        }
    }
}
