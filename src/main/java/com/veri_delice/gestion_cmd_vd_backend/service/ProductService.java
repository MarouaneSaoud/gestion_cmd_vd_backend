package com.veri_delice.gestion_cmd_vd_backend.service;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.CreateProductRequest;
import java.util.List;
public interface ProductService {
    ProductDTO createProduct(CreateProductRequest toCreateDto);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProduct();
    List<ProductDTO> getProductsByCategory(String idCategory);

}
