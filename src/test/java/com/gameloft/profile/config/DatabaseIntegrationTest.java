package com.gameloft.profile.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class DatabaseIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>();

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }
}
