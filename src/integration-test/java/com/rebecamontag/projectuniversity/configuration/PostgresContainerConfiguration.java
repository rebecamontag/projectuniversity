package com.rebecamontag.projectuniversity.configuration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class PostgresContainerConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        postgresContainer.start();
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.postgres.url=".concat(postgresContainer.getJdbcUrl()),
                "spring.datasource.postgres.username=".concat(postgresContainer.getUsername()),
                "spring.datasource.postgres.password=".concat(postgresContainer.getPassword()));
    }
}
