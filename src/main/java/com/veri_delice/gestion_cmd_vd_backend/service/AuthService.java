package com.veri_delice.gestion_cmd_vd_backend.service;

import com.veri_delice.gestion_cmd_vd_backend.dto.auth.LoginDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.RegisterDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.UpdatePasswordDto;

public interface AuthService {
    String login(LoginDto loginDto);
    public void register(RegisterDto registerDto);
    public void updatePassword(UpdatePasswordDto updatePasswordDto) ;
}