package com.veri_delice.gestion_cmd_vd_backend.dao.repo;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findProductsByCategory(Category category);
}
