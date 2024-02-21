package com.veri_delice.gestion_cmd_vd_backend.dao.repo;

import com.veri_delice.gestion_cmd_vd_backend.dao.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command,String> {
}
