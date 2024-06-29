package com.catalog.orders.configurations;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "catalog")
public record CatalogConf(String endpointUri) {
}
