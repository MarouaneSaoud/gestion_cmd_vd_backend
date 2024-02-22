package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.config.ModelMapperConfig;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.ProductCommand;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Payment;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Status;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CommandRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductCommandRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;
import com.veri_delice.gestion_cmd_vd_backend.service.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CommandServiceImpl implements CommandService {

    private CommandRepository commandRepository;
    private ProductRepository productRepository;
    private ModelMapperConfig modelMapperConfig;
    private ProductCommandRepository productCommandRepository;


    @Override
    public CommandDto command(ToOrderDto toOrderDto) {
        Command command= Command.builder()
                .id(UUID.randomUUID().toString())
                .description(toOrderDto.getDescription())
                .status(Status.IN_PROGRESS)
                .payment(toOrderDto.getAdvance() == 0 ? Payment.NO_PAY : Payment.ADVANCE)
                .advance(toOrderDto.getAdvance())
                .dateDelivery(toOrderDto.getDateDelivery())
                .build();
        List<Product> products = productRepository.findAllById(toOrderDto.getItems().keySet());

        List<ProductCommand> productCommands=products.stream()
                .map(product -> {
                    ProductCommand productCommand=new ProductCommand();
                    productCommand.setQte(toOrderDto.getItems().get(product.getId()));
                    productCommand.setProduct(product);
                    productCommand.setCommand(command);
                    return productCommand;

            }).collect(Collectors.toList());
        productCommandRepository.saveAll(productCommands);
        Command c = commandRepository.save(command);
        return modelMapperConfig.modelMapper().map(c,CommandDto.class);
    }
}
