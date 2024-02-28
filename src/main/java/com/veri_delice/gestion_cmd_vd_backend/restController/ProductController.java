package com.veri_delice.gestion_cmd_vd_backend.restController;


import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ToCreateProductDto;
import com.veri_delice.gestion_cmd_vd_backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping("/addProduct")
    public ProductDTO createProduct(@RequestBody ToCreateProductDto toCreateDto){
        return productService.createProduct(toCreateDto);
    }
    @GetMapping("/getProduitByid/{id}")
    public ProductDTO getProductByid(@PathVariable String id){
        return productService.getProductByid(id);
    }
    @GetMapping("/getAllProduct")
    public List<ProductDTO> getAllProduct(){
        return productService.getAllProduct();
    }
    @GetMapping("/getProductByCategory/{id}")
    public List<ProductDTO> getProductByCategory(@PathVariable String idCategory){
        return productService.getProductByCategory(idCategory);
    }
}
