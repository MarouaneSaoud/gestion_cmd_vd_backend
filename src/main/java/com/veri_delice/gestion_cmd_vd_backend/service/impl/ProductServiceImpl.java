package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CategoryRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.command.CommandDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ToCreateProductDto;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ProductMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

     private final ProductRepository productRepository;
     private final CategoryRepository categoryRepository;
     private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ToCreateProductDto toCreateDto) {
        Category category = categoryRepository.findById(toCreateDto.getIdCategory()).orElse(null);
        if(category!=null){
            Product product= productMapper.toSaveDto(toCreateDto);
            product.setId(UUID.randomUUID().toString());
            product.setCategory(category);
            Product save = productRepository.save(product);
            return productMapper.toDto(save);
        }else return null;
    }

    @Override
    public ProductDTO getProductByid(String id) {
        Product product=productRepository.findById(id).orElse(null);
        if (product!=null){
            return productMapper.toDto(product);
        }
        else
        return null;
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> allProducts = productRepository.findAll();
        return  allProducts.stream()
                .map(product -> {
                    return productMapper.toDto(product);
                })
                .sorted(Comparator.comparing(ProductDTO::getName))
                .toList();
    }

    @Override
    public List<ProductDTO> getProductByCategory(String idCategory) {
        List<Product> products = productRepository.findByCategory(idCategory);
        return products.stream()
                .map(product -> {

                    return productMapper.toDto(product);

                }).toList();
    }
}
