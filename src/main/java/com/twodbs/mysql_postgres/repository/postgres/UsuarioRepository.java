package com.twodbs.mysql_postgres.repository.postgres;

import com.twodbs.mysql_postgres.entity.postgres.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
}
