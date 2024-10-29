package com.veri_delice.gestion_cmd_vd_backend.service;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.AddCommandRequest;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.UpdateCommandDto;
import java.util.List;
public interface CommandService {
    CommandDto addCommand(AddCommandRequest toOrderDto);
    CommandDto commandById(String commandId);
    List<CommandDto> allCommand();
    CommandDto updateCommand(UpdateCommandDto updateCommandDto);
    Boolean cancelCommand(String id);
    Boolean deliveryCommand(String id);
    Boolean paymentStatus(String id);

}
