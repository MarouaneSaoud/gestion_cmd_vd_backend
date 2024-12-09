package com.veri_delice.gestion_cmd_vd_backend.service.impl;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.ProductSizeStock;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.CategoryRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.ProductSizeStockRepository;
import com.veri_delice.gestion_cmd_vd_backend.dao.repo.UserRepository;
import com.veri_delice.gestion_cmd_vd_backend.dto.ProductSizeStock.ProductSizeStockDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.CreateProductRequest;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.BusinessException;
import com.veri_delice.gestion_cmd_vd_backend.exception.error.TechnicalException;
import com.veri_delice.gestion_cmd_vd_backend.mapper.ProductMapper;
import com.veri_delice.gestion_cmd_vd_backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    private final ProductSizeStockRepository productSizeStockRepository;

    @Override
    @Transactional
    public ProductDTO createProduct(CreateProductRequest toCreateDto) {
        try {
            Category category = categoryRepository.findById(toCreateDto.getIdCategory())
                    .orElseThrow(() -> new BusinessException("Catégorie non trouvée"));

            User user = userRepository.findByEmail(toCreateDto.getUserEmail())
                    .orElseThrow(() -> new BusinessException("Utilisateur non trouvé"));

            // Mapper le DTO en entité Product
            Product product = productMapper.toSaveDto(toCreateDto);
            product.setId(UUID.randomUUID().toString()); // Générer un UUID
            product.setCategory(category); // Associer la catégorie
            product.setUser(user);

            // Sauvegarder d'abord le produit
            Product savedProduct = productRepository.save(product);

            // Vérifier les doublons de taille dans le stock
            if (toCreateDto.getProductSizeStocks() != null) {
                Set<String> sizeSet = new HashSet<>();
                for (ProductSizeStockDto sizeStock : toCreateDto.getProductSizeStocks()) {
                    if (!sizeSet.add(sizeStock.getSize())) {
                        throw new BusinessException("La taille '" + sizeStock.getSize() + "' est dupliquée.");
                    }
                }

                // Sauvegarder chaque ProductSizeStock avec un produit déjà persistant
                toCreateDto.getProductSizeStocks().stream()
                        .map(sizeStock -> new ProductSizeStock(
                                null, // Id généré par la base de données
                                sizeStock.getSize(),
                                sizeStock.getStock(),
                                savedProduct // L'objet 'Product' persistant est déjà sauvegardé
                        ))
                        .forEach(productSizeStockRepository::save);
            }

            // Calculer le stock total
            Double totalStock = toCreateDto.getProductSizeStocks().stream()
                    .mapToDouble(ProductSizeStockDto::getStock)
                    .sum();
            savedProduct.setStock(totalStock);

            return productMapper.toDto(savedProduct);

        } catch (Exception ex) {
            throw new TechnicalException("Une erreur s'est produite lors de la création du produit");
        }
    }



    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return productMapper.toDto(product);
        } else throw new BusinessException("La Produit avec l'ID" + id + " n'existe pas.");

    }

    @Transactional
    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.findAll().stream()
                .map(product -> productMapper.toDto(product))
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