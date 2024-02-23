package com.veri_delice.gestion_cmd_vd_backend.service;


import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;

import java.util.List;
public interface CommandService {
   public List<Command> getAll();
   public CommandDto command(ToOrderDto toOrderDto);
   public CommandDto getCommandById(String commandId);
   public List<CommandDto> getAllCommand();


}
