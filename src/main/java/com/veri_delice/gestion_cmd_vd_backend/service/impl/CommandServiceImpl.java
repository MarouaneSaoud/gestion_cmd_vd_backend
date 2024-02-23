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
import java.util.Map;
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
        Client client = clientRepository.findById(toOrderDto.getIdClient())
                .orElseThrow(() -> new BusinessException("Le client avec l'ID spécifié n'existe pas."));

        validateProducts(toOrderDto.getItems().keySet());

        try {
            Command command = buildCommandFromDto(client, toOrderDto);
            double total = calculateTotalPrice(toOrderDto.getItems(), command.getProductCommands());
            updatePaymentStatus(command, total);

            return commandMapper.toDto(commandRepository.save(command));

        } catch (Exception e) {
            throw new TechnicalException("Une erreur s'est produite lors de la création de la commande : " + e.getMessage());
        }
    }

    private void validateProducts(Set<String> productIds) {
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            throw new BusinessException("Certains produits spécifiés n'existent pas.");
        }
    }

    private Command buildCommandFromDto(Client client, ToOrderDto toOrderDto) {
        return Command.builder()
                .id(UUID.randomUUID().toString())
                .description(toOrderDto.getDescription())
                .status(Status.IN_PROGRESS)
                .advance(toOrderDto.getAdvance())
                .client(client)
                .dateDelivery(toOrderDto.getDateDelivery())
                .productCommands(buildProductCommands(toOrderDto.getItems()))
                .build();
    }

    private List<ProductCommand> buildProductCommands(Map<String, Integer> items) {
        return items.entrySet().stream()
                .map(entry -> {
                    Product product = productRepository.findById(entry.getKey())
                            .orElseThrow(() -> new BusinessException("Le produit avec l'ID spécifié n'existe pas."));
                    int quantity = entry.getValue();

                    ProductCommand productCommand = new ProductCommand();
                    productCommand.setQte(quantity);
                    productCommand.setProduct(product);

                    return productCommand;
                })
                .toList();
    }

    private double calculateTotalPrice(Map<String, Integer> items, List<ProductCommand> productCommands) {
        return productCommands.stream()
                .mapToDouble(productCommand ->
                        productCommand.getProduct().getPrice() * productCommand.getQte())
                .sum();
    }

    private void updatePaymentStatus(Command command, double total) {
        command.setPayment((total == command.getAdvance()) ? Payment.PAY :
                (total != 0.0 && total > command.getAdvance()) ? Payment.ADVANCE :
                        (command.getAdvance() == 0.0) ? Payment.NO_PAY : null);
    }

}
