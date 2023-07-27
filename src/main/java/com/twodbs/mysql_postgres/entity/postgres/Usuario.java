package com.twodbs.mysql_postgres.entity.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @Column(name = "Usuario")
    private Integer id;
    @Column(name = "nombre")
    private String name;

    @Column(name = "apellido_paterno")
    private String aPaterno;

    @Column(name = "apellido_materno")
    private String aMaterno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
