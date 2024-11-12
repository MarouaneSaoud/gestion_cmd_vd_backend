package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.constant.ResponseMessage.ClientResponseMessage;
import com.veri_delice.gestion_cmd_vd_backend.constant.ResponseMessage.CommandResponseMessage;
import com.veri_delice.gestion_cmd_vd_backend.constant.ResponseMessage.ProductResponseMessage;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.ProductCommand;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Payment;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.CommandStatus;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CommandRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.*;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.BusinessException;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.TechnicalException;
import com.veri_delice.gestion_cmd_vd_backend.mapper.CommandMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.CommandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CommandServiceImpl implements CommandService {
    private final CommandRepository commandRepository;
    private final ProductRepository productRepository;
    private final CommandMapper commandMapper;
    private final ClientRepository clientRepository;

    @Override
    public CommandDto addCommand(AddCommandRequest addCommandRequest) {
        Client client = clientRepository.findById(addCommandRequest.getIdClient())
                .orElseThrow(() -> new BusinessException(ClientResponseMessage.CLIENT_NOT_FOUND));

        Set<String> productIds = addCommandRequest.getItems().keySet();
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            throw new BusinessException(ProductResponseMessage.SOME_PRODUCTS_NOT_EXISTS);
        }

        // Vérification des stocks avant de continuer
        for (Product product : products) {
            int requestedQuantity = addCommandRequest.getItems().get(product.getId());
            if (product.getStock() < requestedQuantity) {
                throw new BusinessException(ProductResponseMessage.INSUFFICIENT_STOCK_FOR_PRODUCT + product.getName());
            }
        }

        try {
            Command command = Command.builder()
                    .id(UUID.randomUUID().toString())
                    .description(addCommandRequest.getDescription())
                    .commandStatus(CommandStatus.IN_PROGRESS)
                    .advance(addCommandRequest.getAdvance())
                    .client(client)
                    .dateDelivery(addCommandRequest.getDateDelivery())
                    .build();
            double total = 0.0;
            List<ProductCommand> productCommands = products.stream()
                    .map(product -> {
                        int quantity = addCommandRequest.getItems().get(product.getId());
                        ProductCommand productCommand = new ProductCommand();
                        productCommand.setQte(quantity);
                        productCommand.setProduct(product);
                        productCommand.setCommand(command);

                        // Déduction du stock
                        product.setStock(product.getStock() - quantity); // Mise à jour du stock
                        productRepository.save(product);

                        return productCommand;
                    }).toList();

            total = productCommands.stream()
                    .mapToDouble(productCommand -> productCommand.getProduct().getPrice() * productCommand.getQte())
                    .sum();

            command.setPayment((total == command.getAdvance()) ? Payment.PAY : (total != 0.0 && total > command.getAdvance()) ? Payment.ADVANCE : (command.getAdvance() == 0.0) ? Payment.NO_PAY : null);
            command.setTotal(total);
            command.setProductCommands(productCommands);

            return commandMapper.toDto(commandRepository.save(command));
        } catch (Exception e) {
            throw new TechnicalException("Une erreur s'est produite lors de la création de la commande : " + e.getMessage());
        }
    }

    @Override
    public CommandDto commandById(String commandId) {
        return commandMapper.toFullDto(commandRepository.findById(commandId)
                .orElseThrow(() -> new BusinessException("La commande avec l'ID" + commandId + " n'existe pas."))
        );
    }

    @Override
    public List<CommandDto> allCommand() {
        return commandRepository.findAll().stream()
                .map(command -> {
                    return commandMapper.toFullDto(command);
                })
                .sorted(Comparator.comparing(CommandDto::getDateCommand))
                .toList();
    }

    public CommandDto updateCommand(UpdateCommandDto updateCommandDto) {
        try {
            Command c = commandRepository.findById(updateCommandDto.getId()).orElse(null);
            if (c != null) {
                if (!updateCommandDto.getItems().isEmpty()) {
                    Set<String> productIds = updateCommandDto.getItems().keySet();
                    List<Product> products = productRepository.findAllById(productIds);
                    if (products.size() != productIds.size()) {
                        throw new BusinessException(ProductResponseMessage.SOME_PRODUCTS_NOT_EXISTS);
                    } else {
                        List<ProductCommand> productCommands = products.stream()
                                .map(product -> {
                                    int quantity = updateCommandDto.getItems().get(product.getId());
                                    ProductCommand productCommand = new ProductCommand();
                                    productCommand.setQte(quantity);
                                    productCommand.setProduct(product);
                                    productCommand.setCommand(c);
                                    return productCommand;
                                }).toList();
                        c.getProductCommands().addAll(productCommands);
                        double total = 0.0;
                        total = productCommands.stream()
                                .mapToDouble(productCommand -> productCommand.getProduct().getPrice() * productCommand.getQte())
                                .sum();
                        c.setTotal(c.getTotal() + total);
                    }
                }
                Command savedCommand = commandRepository.save(commandMapper.toUpdate(updateCommandDto, c));
                return commandMapper.toDto(savedCommand);
            } else {
                throw new BusinessException(ClientResponseMessage.CLIENT_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new TechnicalException(CommandResponseMessage.UPDATE_COMMAND_ERROR);
        }

    }
    @Override
    public Boolean ChangePayementStatus(ChangePayementStatusRequest changePayementStatusRequest) {
        Command command = commandRepository.findById(changePayementStatusRequest.getId()).orElse(null);
        if (command != null) {
            command.setPayment(changePayementStatusRequest.getPayment());
            commandRepository.save(command);
            return true;
        } else throw new BusinessException("Une erreur s'est produit ");
    }
    @Override
    public Boolean ChangeCommandetStatus(ChangeCommandStatusRequest changeCommandStatusRequest) {
        Command command = commandRepository.findById(changeCommandStatusRequest.getId()).orElse(null);
        if (command != null) {
            command.setCommandStatus(changeCommandStatusRequest.getStatus());
            commandRepository.save(command);
            return true;
        } else throw new BusinessException("Une erreur s'est produit ");
    }
}
