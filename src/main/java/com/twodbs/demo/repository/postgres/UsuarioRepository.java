package com.twodbs.demo.repository.postgres;

import com.twodbs.demo.entity.postgres.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
}
