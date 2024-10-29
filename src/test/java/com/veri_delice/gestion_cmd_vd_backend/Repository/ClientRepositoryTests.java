package com.veri_delice.gestion_cmd_vd_backend.Repository;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CommandRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CommandRepository commandRepository;

    @Test
    public void ClientRepository_SaveAll_ReturnSavedClient() {
        // Arrange
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
        Client saveClient = clientRepository.save(client);

        // Assert
        Assertions.assertThat(saveClient).isNotNull();
        Assertions.assertThat(saveClient.getId()).isNotEqualTo("");
    }

    @Test
    public void ClientRepository_FindAll_ReturnClients() {
        // Arrange
        Client client = Client.builder()
                .id(UUID.randomUUID().toString())
                .name("Marouane")
                .numberPhone("062278615")
                .lastName("Saoud")
                .address("Casa")
                .user(null)
                .commands(null)
                .build();
        Client saveClient = clientRepository.save(client);

        // Act
        List<Client> all = clientRepository.findAll();

        // Assert
        Assertions.assertThat(all).isNotEmpty();
    }

    @Test
    public void ClientRepository_FindById_ReturnClient() {
        // Arrange
        Client client = Client.builder()
                .id(UUID.randomUUID().toString())
                .name("Marouane")
                .numberPhone("062278615")
                .lastName("Saoud")
                .address("Casa")
                .user(null)
                .commands(null)
                .build();
        Client saveClient = clientRepository.save(client);

        // Act
        Client byId = clientRepository.findById(saveClient.getId()).orElse(null);

        // Assert
        Assertions.assertThat(byId).isNotNull();
        Assertions.assertThat(byId).isInstanceOf(Client.class);
    }

    @Test
    public void ClientRepository_FindClientCommands_ReturnCommands() {
        // Arrange
        Client client = Client.builder()
                .id(UUID.randomUUID().toString())
                .name("Marouane")
                .numberPhone("062278615")
                .lastName("Saoud")
                .address("Casa")
                .user(null)
                .commands(null)
                .build();
        Client saveClient = clientRepository.save(client);
        Command command = Command.builder()
                .id(UUID.randomUUID().toString())
                .client(saveClient)
                .build();
        commandRepository.save(command);
        // Act
        List<Command> commandsByClient = clientRepository.findCommandsByClient(saveClient);

        // Assert
        Assertions.assertThat(commandsByClient).isNotNull();
        Assertions.assertThat(commandsByClient).isNotEmpty();

    }

    @Test
    public void ClientRepository_DeleteClient_ReturnClientDeleted() {
        // Arrange
        Client client = Client.builder()
                .id(UUID.randomUUID().toString())
                .name("Marouane")
                .numberPhone("062278615")
                .lastName("Saoud")
                .address("Casa")
                .user(null)
                .commands(null)
                .build();
        Client saveClient = clientRepository.save(client);

        // Act
        clientRepository.delete(saveClient);
        Client clientById = clientRepository.findById(saveClient.getId()).orElse(null);

        // Assert
        Assertions.assertThat(clientById).isNull();

    }

    @Test
    public void ClientRepository_DeleteClientById_ReturnClientDeleted() {
        // Arrange
        Client client = Client.builder()
                .id(UUID.randomUUID().toString())
                .name("Marouane")
                .numberPhone("062278615")
                .lastName("Saoud")
                .address("Casa")
                .user(null)
                .commands(null)
                .build();
        Client saveClient = clientRepository.save(client);

        // Act
        clientRepository.deleteById(saveClient.getId());
        Client clientById = clientRepository.findById(saveClient.getId()).orElse(null);

        // Assert
        Assertions.assertThat(clientById).isNull();

    }

    @Test
    public void ClientRepository_UpdateClient_ReturnClientUpdated() {
        // Arrange
        Client client = Client.builder()
                .id(UUID.randomUUID().toString())
                .name("Marouane")
                .numberPhone("062278615")
                .lastName("Saoud")
                .address("Casa")
                .user(null)
                .commands(null)
                .build();
        Client saveClient = clientRepository.save(client);

        // Act
        saveClient.setAddress("Casa wa ma jawaraha");
        saveClient.setName("Mar ouane");

        Client updatedClient = clientRepository.save(saveClient);

        // Assert
        Assertions.assertThat(updatedClient).isNotEqualTo(client);
        Assertions.assertThat(updatedClient.getId()).isEqualTo(client.getId());

    }
}
