package com.veri_delice.gestion_cmd_vd_backend.service;

import com.veri_delice.gestion_cmd_vd_backend.dto.auth.LoginDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    public void register(RegisterDto registerDto);
}