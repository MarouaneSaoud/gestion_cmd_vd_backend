package com.veri_delice.gestion_cmd_vd_backend.Service;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.UserRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.SaveClientRequest;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ClientMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.ClientService;
import com.veri_delice.gestion_cmd_vd_backend.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void ClientService_addClient_ReturnClientDto() {
        // Arrange
        SaveClientRequest saveClientRequest = new SaveClientRequest();
        saveClientRequest.setEmail("test@example.com");
        saveClientRequest.setName("Test Client");

        // Création d'un utilisateur mocké
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");

        // Client à retourner après enregistrement
        Client client = new Client();
        client.setId(UUID.randomUUID().toString());
        client.setUser(user);
        client.setName("Test Client");

        // ClientDto attendu en sortie
        ClientDto expectedClientDto = new ClientDto();
        expectedClientDto.setId(client.getId());
        expectedClientDto.setName(client.getName());

        // Simuler la recherche de l'utilisateur dans le UserRepository
        when(userRepository.findByEmail(saveClientRequest.getEmail())).thenReturn(Optional.of(user));

        // Simuler le mappage SaveClientRequest -> Client
        when(clientMapper.toSaveDto(saveClientRequest)).thenReturn(client);

        // Simuler l'enregistrement du client dans le ClientRepository
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Simuler le mappage de Client -> ClientDto
        when(clientMapper.toDto(client)).thenReturn(expectedClientDto);

        // Act
        ClientDto actualClientDto = clientService.addClient(saveClientRequest);

        // Assert
        assertNotNull(actualClientDto);
        assertEquals(expectedClientDto.getId(), actualClientDto.getId());
        assertEquals(expectedClientDto.getName(), actualClientDto.getName());
    }

    @Test
    public void ClientService_allClient_ReturnSortedClientDtoList() {
        // Arrange
        Client client1 = new Client();
        client1.setId(UUID.randomUUID().toString());
        client1.setName("Client 1");

        Client client2 = new Client();
        client2.setId(UUID.randomUUID().toString());
        client2.setName("Client 2");

        ClientDto clientDto1 = new ClientDto();
        clientDto1.setId(client1.getId());
        clientDto1.setName(client1.getName());

        ClientDto clientDto2 = new ClientDto();
        clientDto2.setId(client2.getId());
        clientDto2.setName(client2.getName());

        // Simuler les appels au repository
        when(clientRepository.findAll()).thenReturn(List.of(client1, client2));

        // Simuler le mappage de Client en ClientDto
        when(clientMapper.toDto(client1)).thenReturn(clientDto1);
        when(clientMapper.toDto(client2)).thenReturn(clientDto2);

        // Act
        List<ClientDto> result = clientService.allClient();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(clientDto1.getName(), result.get(0).getName());
        assertEquals(clientDto2.getName(), result.get(1).getName());
    }

    @Test
    public void ClientService_clientById_ReturnClientDto() {
        // Arrange
        String clientId = UUID.randomUUID().toString();
        Client client = new Client();
        client.setId(clientId);
        client.setName("Test Client");

        ClientDto expectedClientDto = new ClientDto();
        expectedClientDto.setId(client.getId());
        expectedClientDto.setName(client.getName());

        // Simuler le comportement du repository
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // Simuler le mappage de Client en ClientDto
        when(clientMapper.toDto(client)).thenReturn(expectedClientDto);

        // Act
        ClientDto result = clientService.clientById(clientId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedClientDto.getId(), result.getId());
        assertEquals(expectedClientDto.getName(), result.getName());
    }


}
