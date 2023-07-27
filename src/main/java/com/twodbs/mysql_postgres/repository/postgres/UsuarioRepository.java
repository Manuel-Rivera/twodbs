package com.twodbs.mysql_postgres.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twodbs.mysql_postgres.entity.postgres.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

}
