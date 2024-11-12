package com.veri_delice.gestion_cmd_vd_backend.restController;

import com.veri_delice.gestion_cmd_vd_backend.constant.path.ProductPath;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.CreateProductRequest;
import com.veri_delice.gestion_cmd_vd_backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ProductPath.BASE_URL)
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ProductPath.ADD_PRODUCT)
    public ProductDTO createProduct(@RequestBody CreateProductRequest toCreateDto) {
        return productService.createProduct(toCreateDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ProductPath.PRODUCTS_BY_ID)
    public ProductDTO productById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ProductPath.PRODUCTS)
    public List<ProductDTO> allProducts() {
        return productService.getAllProduct();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ProductPath.PRODUCT_BY_CATEGORY)
    public List<ProductDTO> productsByCategory(@PathVariable String idCategory) {
        return productService.getProductsByCategory(idCategory);
    }
}
