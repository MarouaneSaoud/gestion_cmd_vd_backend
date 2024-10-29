package com.veri_delice.gestion_cmd_vd_backend.mapper;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Role;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.RoleDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.UserDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {
    private ModelMapper modelMapper;
    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoles(
                user.getRoles().stream()
                        .map(role -> modelMapper.map(role, RoleDTO.class))
                        .collect(Collectors.toSet())
        );
        return userDTO;
    }
    public User toUserEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setRoles(
                userDTO.getRoles().stream()
                        .map(roleDTO -> modelMapper.map(roleDTO, Role.class))
                        .collect(Collectors.toSet())
        );
        return user;
    }
}
