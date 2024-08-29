package com.isaacandrade.blog;

import com.isaacandrade.blog.integrationtests.config.MyIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
class BlogApplicationTests {

	@Test
	void contextLoads() {}

}
