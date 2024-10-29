package com.veri_delice.gestion_cmd_vd_backend.dao.repo;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Product;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findProductsByCategory(Category category);
    Set<Product> findProductByUser(User user);
}
