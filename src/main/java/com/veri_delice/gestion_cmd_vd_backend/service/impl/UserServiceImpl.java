package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Client;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ClientRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.UserRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.UserDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.UserStatistics;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.BusinessException;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ClientMapper;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ProductMapper;
import com.veri_delice.gestion_cmd_vd_backend.mapper.UserMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ClientMapper clientMapper;
    private final UserMapper userMapper;

    @Override
    public Set<ProductDTO> userProduct(String email) {
        return productRepository.findProductByUser(userRepository.findByEmail(email).
                        orElseThrow(() -> new BusinessException("Utilisateur non trouver")))
                .stream().map(product -> productMapper.toDto(product)).collect(Collectors.toSet());
    }

    @Override
    public Set<ClientDto> userClient(String email) {
        return clientRepository.findClientByUser(userRepository.findByEmail(email)
                        .orElseThrow(() -> new BusinessException("Utilisateur non trouver")))
                .stream().map(client -> clientMapper.toDto(client)).collect(Collectors.toSet());
    }

    @Override
    public UserDTO userByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException("Utilisateur non trouver"));
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserStatistics userStatistics(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException("Utilisateur non trouver"));
        return new UserStatistics(clientRepository.findClientByUser(user).size(), productRepository.findProductByUser(user).size(), clientRepository.findClientByUser(user).stream().mapToInt(client -> client.getCommands().size()).sum());
    }
}
