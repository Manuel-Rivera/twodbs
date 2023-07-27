package com.twodbs.mysql_postgres.repository.mysql;

import com.twodbs.mysql_postgres.entity.mysql.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolUsuario,Integer> {
}
