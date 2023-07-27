package com.twodbs.mysql_postgres.entity.mysql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROL_USUARIO")
public class RolUsuario {
    @Id
    @Column(name = "ID_ROL")
    private Integer id;

    @Column(name = "ROL_DESCRIPTION")
    private String rol;
}
