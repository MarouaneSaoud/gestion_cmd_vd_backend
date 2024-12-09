package com.veri_delice.gestion_cmd_vd_backend.mapper;

import com.veri_delice.gestion_cmd_vd_backend.config.ModelMapperConfig;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.ProductSizeStock;
import com.veri_delice.gestion_cmd_vd_backend.dto.ProductSizeStock.ProductSizeStockDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.CreateProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductMapper {
    private ModelMapperConfig modelMapperConfig;
    public ProductDTO toDto(Product product) {
        if (product.getProductSizeStocks() != null) {
            product.getProductSizeStocks().size();  // Accessing the size triggers the loading
        }
        if (product.getProductCommands() != null) {
            product.getProductCommands().size();  // Similarly for other collections
        }

        ProductDTO map = modelMapperConfig.modelMapper().map(product, ProductDTO.class);

        if (product.getCategory() != null) {
            CategoryDto categoryDto = modelMapperConfig.modelMapper().map(product.getCategory(), CategoryDto.class);
            map.setCategory(categoryDto);
        }
        if (product.getProductSizeStocks() != null) {
            Set<ProductSizeStockDto> productSizeStockDto = product.getProductSizeStocks().stream()
                    .map(p -> new ProductSizeStockDto(p.getSize(), p.getStock()))  // Transform ProductSizeStock to ProductSizeStockDto
                    .collect(Collectors.toSet());
            map.setProductSizeStockDtos(productSizeStockDto);
        }

        return map;
    }

    public Product toEntity(ProductDTO productDTO){
        return modelMapperConfig.modelMapper().map(productDTO,Product.class);
    }
    public Product toSaveDto(CreateProductRequest toCreateProductDto) {
        Product map = modelMapperConfig.modelMapper().map(toCreateProductDto, Product.class);
        return map;
    }
}
