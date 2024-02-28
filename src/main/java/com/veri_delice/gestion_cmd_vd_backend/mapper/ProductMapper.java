package com.veri_delice.gestion_cmd_vd_backend.mapper;

import com.veri_delice.gestion_cmd_vd_backend.config.ModelMapperConfig;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ToCreateProductDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.productCommand.ProductCommandDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductMapper {

    private ModelMapperConfig modelMapperConfig;

    public ProductDTO toDto(Product product) {
        ProductDTO map = modelMapperConfig.modelMapper().map(product, ProductDTO.class);
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
