package com.isaacandrade.blog;

import com.isaacandrade.blog.domain.user.UserRepository;
import com.isaacandrade.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class BlogApplicationTests {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
		// Verificando se o contexto carrega o UserService
		assertNotNull(userService, "UserService deve ser carregado no contexto");

		// Verificando se o contexto carrega o UserRepository
		assertNotNull(userRepository, "UserRepository deve ser carregado no contexto");
	}

}
