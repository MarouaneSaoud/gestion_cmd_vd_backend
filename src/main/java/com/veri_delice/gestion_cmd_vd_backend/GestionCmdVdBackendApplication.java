package com.veri_delice.gestion_cmd_vd_backend;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Status;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UniteProd;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CategoryRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CommandRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;
import com.veri_delice.gestion_cmd_vd_backend.service.ClientService;
import com.veri_delice.gestion_cmd_vd_backend.service.CommandService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class GestionCmdVdBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionCmdVdBackendApplication.class, args);
    }
}
