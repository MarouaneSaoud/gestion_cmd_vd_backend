package com.veri_delice.gestion_cmd_vd_backend.dao.repo;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ClientRepository extends JpaRepository<Client,String> {
    @Query("SELECT c FROM Command c WHERE c.client = :client")
    List<Command> findCommandsByClient(Client client);
    Set<Client> findClientByUser(User user);

}
