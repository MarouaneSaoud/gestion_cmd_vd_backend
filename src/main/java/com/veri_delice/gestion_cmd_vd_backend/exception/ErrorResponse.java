package com.veri_delice.gestion_cmd_vd_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String errorCode;
    private long timestamp;

}
