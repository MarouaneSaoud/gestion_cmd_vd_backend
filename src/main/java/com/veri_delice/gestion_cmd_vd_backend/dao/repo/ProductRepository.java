package com.veri_delice.gestion_cmd_vd_backend.dao.repo;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}
