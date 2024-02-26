package com.veri_delice.gestion_cmd_vd_backend.service;


import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.UpdateCommandDto;

import java.util.List;
public interface CommandService {
   public CommandDto command(ToOrderDto toOrderDto);
   public CommandDto getCommandById(String commandId);
   public List<CommandDto> getAllCommand();
   public Boolean deleteCommand(String id);
   public CommandDto updateCommand(UpdateCommandDto updateCommandDto);
   public Boolean cancelCommand(String id);
   public Boolean deliveryCommand(String id);

}
