package com.isaacandrade.blog.integrationtests;

import com.isaacandrade.blog.integrationtests.config.MyIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends MyIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void SwaggerUiForbbiden(){
        ResponseEntity<String> response = testRestTemplate.getForEntity("/swagger-ui/index.html", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Swagger deve estar acessível!");

        ResponseEntity<String> apiDocsResponse = testRestTemplate.getForEntity("/v3/api-docs", String.class);
        assertEquals(HttpStatus.OK, apiDocsResponse.getStatusCode(), "Swagger API Docs deve estar acessível!");

    }
}
