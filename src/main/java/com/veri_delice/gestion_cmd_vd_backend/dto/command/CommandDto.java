package com.veri_delice.gestion_cmd_vd_backend.dto.command;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Payment;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.Status;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.productCommand.ProductCommandDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandDto {
    private String id;
    private ClientDto clientDto;
    private String description;
    private Date dateCommand;
    private Date dateDelivery;
    private Status status;
    private Payment payment;
    private Double advance;
    private Double total;
    private List<ProductCommandDto> productCommandDtos;
}
