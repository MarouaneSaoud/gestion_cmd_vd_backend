package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.config.auth.JwtTokenProvider;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Role;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import com.veri_delice.gestion_cmd_vd_backend.dao.enumeration.UserStatus;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.RoleRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.UserRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.LoginDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.RegisterDto;
import com.veri_delice.gestion_cmd_vd_backend.service.AuthService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository ;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDto loginDto) {
        if (userRepository.findByEmail(loginDto.getUsername()).get().getUserStatus().equals(UserStatus.BANNED)) {
            throw new RuntimeException("Vous etes banner!");
        }
        // 01 - AuthenticationManager is used to authenticate the user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
    @Override
    public void register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUserStatus(UserStatus.PENDING);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Check if role exists, create if it doesn't
        Role role = roleRepository.findRoleByName(registerDto.getRoleName())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(registerDto.getRoleName());
                    return roleRepository.save(newRole);
                });

        // Set user roles
        user.setRoles(Collections.singleton(role));

        // Save user in database
        userRepository.save(user);

    }


}
