package com.twodbs.mysql_postgres.repository.mysql.DTO;

import java.io.Serializable;

public class RolUserDTO implements Serializable {
    private Integer id_rol;
    private String rol_description;

    public Integer getId_rol() {
        return id_rol;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }

    public String getRol_description() {
        return rol_description;
    }

    public void setRol_description(String rol_description) {
        this.rol_description = rol_description;
    }

}
