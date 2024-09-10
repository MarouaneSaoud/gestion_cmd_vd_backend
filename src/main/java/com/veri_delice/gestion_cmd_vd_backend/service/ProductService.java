package com.veri_delice.gestion_cmd_vd_backend.service;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ToCreateProductDto;
import java.util.List;
public interface ProductService {
    ProductDTO createProduct(ToCreateProductDto toCreateDto);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProduct();
    List<ProductDTO> getProductsByCategory(String idCategory);

}
