package com.veri_delice.gestion_cmd_vd_backend.restController;
import com.veri_delice.gestion_cmd_vd_backend.constant.path.CommandPath;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.ToOrderDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.UpdateCommandDto;
import com.veri_delice.gestion_cmd_vd_backend.service.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CommandPath.BASE_URL)
@AllArgsConstructor
public class CommandController {

    private final CommandService commandService;
    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping(CommandPath.ADD_COMMAND)
    public ResponseEntity<CommandDto> addCommand(@RequestBody ToOrderDto toOrderDto) {
        CommandDto createdCommand = commandService.addCommand(toOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommand);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(CommandPath.ALL_COMMANDS)
    public ResponseEntity<List<CommandDto>> allCommands() {
        List<CommandDto> commands = commandService.allCommand();
        return ResponseEntity.ok(commands);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(CommandPath.COMMAND_BY_ID)
    public ResponseEntity<CommandDto> getCommandById(@PathVariable String id) {
        CommandDto commandDto = commandService.commandById(id);
        return ResponseEntity.ok(commandDto);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @PutMapping(CommandPath.UPDATE_COMMAND)
    public ResponseEntity<CommandDto> updateCommand(@RequestBody UpdateCommandDto updateCommandDto) {
        CommandDto updatedCommand = commandService.updateCommand(updateCommandDto);
        return ResponseEntity.ok(updatedCommand);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @DeleteMapping(CommandPath.CANCEL_COMMAND)
    public ResponseEntity<Void> cancelCommand(@PathVariable String id) {
        boolean isCanceled = commandService.cancelCommand(id);
        return isCanceled ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping(CommandPath.DELIVERY_COMMAND)
    public ResponseEntity<Void> deliverCommand(@PathVariable String id) {
        boolean isDelivered = commandService.deliveryCommand(id);
        return isDelivered ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping(CommandPath.PAYMENT_STATUS)
    public ResponseEntity<Void> updatePaymentStatus(@PathVariable String id) {
        boolean isUpdated = commandService.paymentStatus(id);
        return isUpdated ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
