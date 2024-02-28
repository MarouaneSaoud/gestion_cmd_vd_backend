package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientToSave;
import com.veri_delice.gestion_cmd_vd_backend.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/addclient")
    public ClientDto addclient(@RequestBody ClientToSave clientToSave){
        return clientService.addclient(clientToSave);
    }
    @GetMapping("/allClient")
    public  List<ClientDto> getAllClient(){
        return clientService.getAllClient();
    }
    @GetMapping("/getClientByid/{id}")
    public ClientDto getClientByid(@PathVariable String id){
        return clientService.getClientByid(id);
    }
}
