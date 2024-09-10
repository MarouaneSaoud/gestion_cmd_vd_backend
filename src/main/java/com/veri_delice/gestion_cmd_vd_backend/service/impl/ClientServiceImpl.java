package com.veri_delice.gestion_cmd_vd_backend.service.impl;
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
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
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
        }catch (Exception e){
            throw new TechnicalException("Une erreur s'est produite lors de la création du Client ");
        }

    }
    @Override
    public List<ClientDto> getAllClient() {
        return clientRepository.findAll().stream().map(client -> {
            return clientMapper.toDto(client);
        }).sorted(Comparator.comparing(ClientDto::getName)).toList();
    }
    @Override
    public ClientDto getClientById(String id) {
        return clientMapper.toDto(clientRepository.findById(id)
                .orElseThrow(() -> new BusinessException("La commande avec l'ID "+id+" n'existe pas.")));
    }
    @Override
    public List<CommandDto> getCommandByClient(String idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        if(client!=null){
           return clientRepository.findCommandsByClient(client).stream()
                    .map(command -> {
                        return commandMapper.toFullDto(command);
                    })
                    .toList();
        }
        else throw new TechnicalException("La Client avec l'ID"+idClient+" n'existe pas.");
    }
}
