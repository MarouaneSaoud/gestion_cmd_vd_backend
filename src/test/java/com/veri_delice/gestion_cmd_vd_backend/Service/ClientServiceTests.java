package com.veri_delice.gestion_cmd_vd_backend.Service;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.SaveClientRequest;
import com.veri_delice.gestion_cmd_vd_backend.service.impl.ClientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void ClientService_addClient_ReturnClientDto(){
        // Arrange
        SaveClientRequest clientRequest = SaveClientRequest.builder().name("aya").lastName("aya")
                .numberPhone("87")
                .address("test")
                .email("aya@gmail.com")
                .build();
        Client client = Client.builder()
                .id(UUID.randomUUID().toString())
                .name("Marouane")
                .numberPhone("062278615")
                .lastName("Saoud")
                .address("Casa")
                .user(null)
                .commands(null)
                .build();

        // Act
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);
        ClientDto clientDto = clientService.addClient(clientRequest);
        // Assert
        Assertions.assertThat(clientDto).isNotNull();
    }
}
