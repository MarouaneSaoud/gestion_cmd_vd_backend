package com.veri_delice.gestion_cmd_vd_backend.service;

import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ToCreateProductDto;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ToCreateProductDto toCreateDto);
    ProductDTO getProductByid(String id);
    List<ProductDTO> getAllProduct();
    List<ProductDTO> getProductByCategory(String idCategory);

}
