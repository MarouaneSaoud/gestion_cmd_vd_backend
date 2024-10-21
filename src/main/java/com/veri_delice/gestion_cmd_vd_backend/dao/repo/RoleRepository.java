package com.veri_delice.gestion_cmd_vd_backend.dao.repo;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findRoleByName(String name);
}