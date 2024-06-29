package com.catalog.cataloggateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(
		webEnvironment = 	SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
class CatalogGatewayApplicationTests {

	@Container
	static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.0"))
			.withExposedPorts(6379);

	
	@Test
	void contextLoads() {
	}

}
