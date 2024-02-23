package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;
import com.veri_delice.gestion_cmd_vd_backend.service.CommandService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/command")
@AllArgsConstructor
public class CommandController {

    private CommandService commandService;

    @PostMapping("/order")
    public CommandDto order(@RequestBody ToOrderDto toOrderDto) {
        return commandService.command(toOrderDto);
    }

    @GetMapping("/all")
    public List<Command> getAll() {
        return commandService.getAll();
    }


}
