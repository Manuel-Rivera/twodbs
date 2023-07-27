package com.twodbs.mysql_postgres.repository.mysql;

import com.twodbs.mysql_postgres.repository.mysql.DTO.RolUserDTO;

public interface IRol {
    public RolUserDTO findUserRol(Integer id);
}
