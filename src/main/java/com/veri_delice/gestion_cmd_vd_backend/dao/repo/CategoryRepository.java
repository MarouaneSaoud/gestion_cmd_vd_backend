package com.veri_delice.gestion_cmd_vd_backend.dao.repo;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Category;
import com.veri_delice.gestion_cmd_vd_backend.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category,String> {
    Category getCategoryByName(String name);
    Set<Category> findCategoriesByUser(User user);
}
