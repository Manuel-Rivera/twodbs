package com.twodbs.mysql_postgres.repository.mysql;

import com.twodbs.mysql_postgres.repository.mysql.DTO.RolUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolRepository implements  IRol{

    @Autowired
    private JdbcTemplate mysqlJdbcTemplate;
    @Override
    public RolUserDTO findUserRol(Integer id) {
        RolUserDTO roluser = mysqlJdbcTemplate.queryForObject("select * from rol_usuario where id_rol=?", BeanPropertyRowMapper.newInstance(RolUserDTO.class),id);
        return  roluser;
    }
}
