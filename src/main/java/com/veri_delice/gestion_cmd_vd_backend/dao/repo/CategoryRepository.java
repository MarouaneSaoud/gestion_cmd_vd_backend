package com.veri_delice.gestion_cmd_vd_backend.dao.repo;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category,String> {
    Category getCategoryByName(String name);
}
