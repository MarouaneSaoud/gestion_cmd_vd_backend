package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CategoryRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.UserRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.CreateProductRequest;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.BusinessException;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.TechnicalException;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ProductMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductDTO createProduct(CreateProductRequest toCreateDto) {
        try {
            Category category = categoryRepository.findById(toCreateDto.getIdCategory()).orElseThrow(() -> new BusinessException("Catégorie non trouver"));
            User user = userRepository.findByEmail(toCreateDto.getUserEmail()).orElseThrow(() -> new BusinessException("Utilisateur non trouver"));

            Product product = productMapper.toSaveDto(toCreateDto);
            product.setId(UUID.randomUUID().toString());
            product.setCategory(category);
            product.setUser(user);
            Product save = productRepository.save(product);

            return productMapper.toDto(save);

        } catch (Exception ex) {
            throw new TechnicalException("Une erreur s'est produite lors de la Creation du Produit ");
        }
    }

    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return productMapper.toDto(product);
        } else throw new BusinessException("La Produit avec l'ID" + id + " n'existe pas.");

    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.findAll().stream()
                .map(product -> {
                    return productMapper.toDto(product);
                })
                .sorted(Comparator.comparing(ProductDTO::getName))
                .toList();
    }
    @Override
    public List<ProductDTO> getProductsByCategory(String idCategory) {
        Category category = categoryRepository.findById(idCategory).orElseThrow(() -> new BusinessException("Categorie non trouver"));
        List<Product> products = productRepository.findProductsByCategory(category);
        return products.stream()
                .map(product -> {
                    return productMapper.toDto(product);
                }).toList();
    }
}