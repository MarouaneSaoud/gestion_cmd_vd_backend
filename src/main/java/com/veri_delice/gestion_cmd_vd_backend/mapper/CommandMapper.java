package com.veri_delice.gestion_cmd_vd_backend.mapper;

import com.veri_delice.gestion_cmd_vd_backend.config.ModelMapperConfig;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.productCommand.ProductCommandDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommandMapper {

    private ModelMapperConfig modelMapperConfig;

    public CommandDto toDto(Command command) {

        CommandDto map = modelMapperConfig.modelMapper().map(command, CommandDto.class);
        if (command.getClient() != null) {
            ClientDto clientDto = modelMapperConfig.modelMapper().map(command.getClient(), ClientDto.class);
            map.setClientDto(clientDto);
        }
        if (command.getProductCommands() != null) {

            List<ProductCommandDto> productCommandDto = command.getProductCommands().stream()
                    .map(productCommand ->
                            modelMapperConfig.modelMapper().map(productCommand, ProductCommandDto.class))
                    .collect(Collectors.toList());
            map.setProductCommandDtos(productCommandDto);
        }
        return map;
    }

    public Command toEntity(CommandDto commandDto) {
        return modelMapperConfig.modelMapper().map(commandDto, Command.class);
    }

}
