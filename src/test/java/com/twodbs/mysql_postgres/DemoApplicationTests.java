package com.twodbs.mysql_postgres;

import com.twodbs.mysql_postgres.repository.mysql.DTO.RolUserDTO;
import com.twodbs.mysql_postgres.repository.mysql.RolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RolRepository rolRepository;
	@Test
	void RolUser(){
		RolUserDTO rolUserDTO=rolRepository.findUserRol(1);
		System.out.println(rolUserDTO.getRol_description());
	}
}
