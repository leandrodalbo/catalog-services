package com.boot.demo.catalog.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class RepositoryTest {

    @ServiceConnection
    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:14.4").withReuse(true);

    @Test
    void loadContainer() {
        assertThat(container.isCreated()).isTrue();
        assertThat(container.isRunning()).isTrue();
    }

}
