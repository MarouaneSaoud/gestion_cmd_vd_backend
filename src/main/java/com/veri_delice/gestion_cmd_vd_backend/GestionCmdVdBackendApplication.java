package com.veri_delice.gestion_cmd_vd_backend;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Status;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UniteProd;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CommandRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;
import com.veri_delice.gestion_cmd_vd_backend.service.CommandService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class GestionCmdVdBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionCmdVdBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(
            ProductRepository productRepository,
            ClientRepository clientRepository,
            CommandService commandService){
        return args -> {
            Product product = Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Bestila Poison")
                    .description("for special occasions")
                    .price(20.0)
                    .uniteProd(UniteProd.UNIT)
                    .build();

            productRepository.save(product);
            Client client = Client.builder()
                    .id(UUID.randomUUID().toString())
                    .address("hay chrifa")
                    .name("marouane")
                    .lastName("souad")
                    .numberPhone("0622686318")
                    .build();
            clientRepository.save(client);
            ToOrderDto toOrderDto = new ToOrderDto();
            toOrderDto.setIdClient(client.getId());
            toOrderDto.setDescription("Description de la commande");
            toOrderDto.setAdvance(10.0);
            toOrderDto.setDateDelivery(new Date());
            toOrderDto.setItems(Map.of(product.getId(), 2));
            commandService.command(toOrderDto);


        };

    }
}
