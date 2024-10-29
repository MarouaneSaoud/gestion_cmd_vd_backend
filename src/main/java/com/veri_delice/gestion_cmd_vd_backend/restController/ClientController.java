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
@RequestMapping(ClientPath.baseUrl)
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;
    @PreAuthorize( "hasRole('ADMIN')" )
    @PostMapping(ClientPath.addClient)
    public ClientDto addClient(@RequestBody SaveClientRequest clientToSave) {
        return clientService.addClient(clientToSave);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(ClientPath.allClient)
    public List<ClientDto> allClient() {
        return clientService.allClient();
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(ClientPath.clientById)
    public ClientDto clientById(@PathVariable String id) {
        return clientService.clientById(id);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(ClientPath.commandByClient)
    public List<CommandDto> commandByClient(String clientId) {
        return clientService.commandByClient(clientId);
    }
}
