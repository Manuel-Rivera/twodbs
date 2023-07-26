package com.twodbs.demo.repository.mysql;

import com.twodbs.demo.entity.mysql.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolUsuario,Integer> {
}
