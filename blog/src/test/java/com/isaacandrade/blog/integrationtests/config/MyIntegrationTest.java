package com.isaacandrade.blog.integrationtests.config;
import com.isaacandrade.blog.BlogApplication;
import com.isaacandrade.blog.domain.post.Post;
import com.isaacandrade.blog.domain.post.PostRepository;
import com.isaacandrade.blog.domain.user.User;
import com.isaacandrade.blog.domain.user.UserRepository;
import com.isaacandrade.blog.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



@SpringBootTest(classes = BlogApplication.class)
@Testcontainers
@Transactional
public class MyIntegrationTest {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.4")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void setUp() {
        //Deletando todas as tabelas para que o flyway faça seu trabalho com as migrations durante os testes
        postRepository.deleteAll();
        userRepository.deleteAll();

        //Post post = new Post(null, "titulo", "content", LocalDateTime.now(), user1, true);
        //postRepository.save(post);
    }
}