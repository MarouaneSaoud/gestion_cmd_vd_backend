package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.constant.path.AuthPath;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.*;
import com.veri_delice.gestion_cmd_vd_backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(AuthPath.BASE_URL)
public class AuthController {

    private AuthService authService;

    @PostMapping(AuthPath.LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto) {
        LoginResponse loginResponse = authService.login(loginDto);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping(AuthPath.REGISTER)
    public void register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
    }
    @PostMapping(AuthPath.UPDATE_PASSWORD)
    public void updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        authService.updatePassword(updatePasswordDto);
    }
    @GetMapping(AuthPath.REFRESH_TOKEN)
    public ResponseEntity<LoginResponse> refreshToken(@PathVariable String refreshToken) {
        LoginResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}
