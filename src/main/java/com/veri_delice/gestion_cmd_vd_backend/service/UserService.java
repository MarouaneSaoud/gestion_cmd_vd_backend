package com.veri_delice.gestion_cmd_vd_backend.service;

import com.veri_delice.gestion_cmd_vd_backend.dto.auth.UserDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.UserStatistics;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;

import java.util.Set;

public interface UserService {
    Set<ProductDTO> userProduct(String email);
    Set<ClientDto> userClient(String email);
    Set<CategoryDto> userCategory(String email);
    UserDTO userByEmail (String email);
    UserStatistics userStatistics (String email);
}
