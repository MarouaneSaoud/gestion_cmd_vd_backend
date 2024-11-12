package com.veri_delice.gestion_cmd_vd_backend.service;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.*;

import java.util.List;
public interface CommandService {
    CommandDto addCommand(AddCommandRequest toOrderDto);
    CommandDto commandById(String commandId);
    List<CommandDto> allCommand();
    CommandDto updateCommand(UpdateCommandDto updateCommandDto);
    Boolean ChangePayementStatus(ChangePayementStatusRequest changePayementStatusRequest);
    Boolean ChangeCommandetStatus(ChangeCommandStatusRequest changeCommandStatusRequest) ;


}
