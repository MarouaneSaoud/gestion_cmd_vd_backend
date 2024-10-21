package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.constant.path.AuthPath;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.AuthResponseDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.LoginDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.RegisterDto;
import com.veri_delice.gestion_cmd_vd_backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(AuthPath.BASE_URL)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(AuthPath.LOGIN)
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping(AuthPath.REGISTER)
    public void register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
    }
}
