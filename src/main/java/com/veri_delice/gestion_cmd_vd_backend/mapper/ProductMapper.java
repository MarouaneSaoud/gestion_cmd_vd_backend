package com.veri_delice.gestion_cmd_vd_backend.mapper;

import com.veri_delice.gestion_cmd_vd_backend.config.ModelMapperConfig;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dto.category.CategoryDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ToCreateProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@AllArgsConstructor
public class ProductMapper {
    private ModelMapperConfig modelMapperConfig;
    public ProductDTO toDto(Product product) {
        ProductDTO map = modelMapperConfig.modelMapper().map(product, ProductDTO.class);
        if(product.getCategory()!=null){
            CategoryDto categoryDto = modelMapperConfig.modelMapper().map( product.getCategory(), CategoryDto.class);
            map.setCategory(categoryDto);
        }
        return map;
    }
    public Product toEntity(ProductDTO productDTO){
        return modelMapperConfig.modelMapper().map(productDTO,Product.class);
    }
    public Product toSaveDto(ToCreateProductDto toCreateProductDto) {
        Product map = modelMapperConfig.modelMapper().map(toCreateProductDto, Product.class);
        return map;
    }
}
