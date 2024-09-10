package com.veri_delice.gestion_cmd_vd_backend.service.impl;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.ProductCommand;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Payment;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Status;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CommandRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.UpdateCommandDto;
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
            command.setTotal(total);
            command.setProductCommands(productCommands);
            return commandMapper.toDto(commandRepository.save(command));
        } catch (Exception e) {
            throw new TechnicalException("Une erreur s'est produite lors de la création de la commande : " + e.getMessage());
        }
    }
    @Override
    public CommandDto getCommandById(String commandId) {
         return commandMapper.toFullDto(commandRepository.findById(commandId)
                 .orElseThrow(() -> new BusinessException("La commande avec l'ID"+commandId+" n'existe pas."))
       );
    }
    @Override
    public List<CommandDto> getAllCommand() {
        return  commandRepository.findAll().stream()
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
                        throw new BusinessException("Certains produits spécifiés n'existent pas.");
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
            }else{
                throw new BusinessException("Le client avec l'ID"+updateCommandDto.getId()+" n'existe pas.");
            }
        } catch (Exception ex) {
            throw new TechnicalException("Une erreur s'est produite lors de la modification du Command ");
        }

    }

    @Override
    public Boolean cancelCommand(String id) {
        Command command=commandRepository.findById(id).orElse(null);
        if (command!=null) {
            command.setStatus(Status.CANCEL);
            commandRepository.save(command);
            return true;
        }
        else throw new TechnicalException("Une erreur s'est produit ");
    }
    @Override
    public Boolean deliveryCommand(String id) {
        Command command=commandRepository.findById(id).orElse(null);
        if (command!=null) {
            command.setStatus(Status.DELIVERY);
            commandRepository.save(command);
            return true;
        }
        else throw new TechnicalException("Une erreur s'est produit");
    }
    @Override
    public Boolean paymentStatus(String id) {
        Command command=commandRepository.findById(id).orElse(null);
        if (command!=null) {
            command.setPayment(Payment.PAY);
            commandRepository.save(command);
            return true;
        }
        else throw new TechnicalException("Une erreur s'est produit ");
    }
}
