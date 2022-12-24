package com.example.policycrud.IntegrationTest;


import com.example.policycrud.PolicyCrudApplication;
import com.example.policycrud.dtos.InsuredPersonBaseDto;
import com.example.policycrud.dtos.PolicyCreationRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = PolicyCrudApplication.class)
@Sql(scripts = {"/sql/Cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
@ActiveProfiles("test")
public class BaseIntegrationTest {

    @LocalServerPort
    static int port = 8080;

    public ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

    @BeforeAll
    public static void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost/v1";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry dynamicPropertyRegistry) {

        MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.26")
                .withDatabaseName("embea")
                .withUsername("root")
                .withPassword("root")
                .withEnv("MYSQL_ROOT_HOST", "%");
        dynamicPropertyRegistry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mysqlContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mysqlContainer::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class", () -> "com.mysql.cj.jdbc.Driver");
        dynamicPropertyRegistry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect");
        mysqlContainer.start();
        System.out.println("Containers should be started.");

    }

    //test helper methods (Should be separated depend on the domain)
    public PolicyCreationRequest createPolicyCreationRequest(LocalDate startDate){
        PolicyCreationRequest policyCreationRequest = new PolicyCreationRequest();
        policyCreationRequest.setStartDate(startDate);
        List<InsuredPersonBaseDto> insuredPersonBaseDtoList = new ArrayList<>();
        insuredPersonBaseDtoList.add(new InsuredPersonBaseDto("Omar" , "Aboelsoud", BigDecimal.TEN));
        insuredPersonBaseDtoList.add(new InsuredPersonBaseDto("Paul","Smith", BigDecimal.TEN));
        policyCreationRequest.setInsuredPersons(insuredPersonBaseDtoList);
        return policyCreationRequest;
    }


}