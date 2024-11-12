package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.constant.path.ClientPath;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.SaveClientRequest;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ClientPath.BASE_URL)
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;
    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping(ClientPath.ADD_CLIENT)
    public ClientDto addClient(@RequestBody SaveClientRequest clientToSave) {
        return clientService.addClient(clientToSave);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(ClientPath.ALL_CLIENT)
    public List<ClientDto> allClient() {
        return clientService.allClient();
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(ClientPath.CLIENT_BY_ID)
    public ClientDto clientById(@PathVariable String id) {
        return clientService.clientById(id);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(ClientPath.COMMAND_BY_CLIENT)
    public List<CommandDto> commandByClient(String clientId) {
        return clientService.commandByClient(clientId);
    }
}
