package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
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
import com.veri_delice.gestion_cmd_vd_backend.exception.error.BusinessException;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.TechnicalException;
import com.veri_delice.gestion_cmd_vd_backend.mapper.CommandMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final CommandRepository commandRepository;
    private final ProductRepository productRepository;
    private final CommandMapper commandMapper;
    private final ClientRepository clientRepository;
    private final ProductCommandRepository productCommandRepository;


    @Override
    public List<Command> getAll() {
        return commandRepository.findAll();
    }

    @Override
    public CommandDto command(ToOrderDto toOrderDto) {
        Client client = clientRepository.findById(toOrderDto.getIdClient()).orElseThrow(() -> new BusinessException("Le client avec l'ID spécifié n'existe pas."));
        Set<String> productIds = toOrderDto.getItems().keySet();
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            throw new BusinessException("Certains produits spécifiés n'existent pas.");
        }

        try {
            Command command = Command.builder()
                    .id(UUID.randomUUID().toString())
                    .description(toOrderDto.getDescription())
                    .status(Status.IN_PROGRESS)
                    .advance(toOrderDto.getAdvance())
                    .client(client)
                    .dateDelivery(toOrderDto.getDateDelivery())
                    .build();
            double total = 0.0;
            List<ProductCommand> productCommands = products.stream()
                    .map(product -> {
                        int quantity = toOrderDto.getItems().get(product.getId());
                        ProductCommand productCommand = new ProductCommand();
                        productCommand.setQte(quantity);
                        productCommand.setProduct(product);
                        productCommand.setCommand(command);
                        return productCommand;
                    }).toList();
             total = productCommands.stream()
                    .mapToDouble(productCommand -> productCommand.getProduct().getPrice() * productCommand.getQte())
                    .sum();
            command.setPayment((total == command.getAdvance()) ? Payment.PAY : (total != 0.0 && total > command.getAdvance()) ? Payment.ADVANCE : (command.getAdvance() == 0.0) ? Payment.NO_PAY : null);

            command.setProductCommands(productCommands);

            return commandMapper.toDto(commandRepository.save(command));

        } catch (Exception e) {
            throw new TechnicalException("Une erreur s'est produite lors de la création de la commande : " + e.getMessage());

        }

    }

    @Override
    public CommandDto getCommandById(String commandId) {
        Command command = commandRepository.findById(commandId)
                .orElseThrow(() -> new BusinessException("La commande avec l'ID spécifié n'existe pas."));
        return commandMapper.toFullDto(command);
    }

    @Override
    public List<CommandDto> getAllCommand() {
        List<Command> allCommands = commandRepository.findAll();
        return allCommands.stream()
                .map(command -> {
                    return commandMapper.toFullDto(command);
                })
                        .collect(Collectors.toList());

    }


}
