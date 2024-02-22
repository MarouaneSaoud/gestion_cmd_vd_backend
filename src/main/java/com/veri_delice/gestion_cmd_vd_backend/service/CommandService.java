package com.veri_delice.gestion_cmd_vd_backend.service;


import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;

import java.util.Map;

public interface CommandService {
   public CommandDto command(ToOrderDto toOrderDto);
}
