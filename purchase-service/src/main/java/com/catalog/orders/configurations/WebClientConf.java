package com.catalog.orders.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConf {

    @Bean
    WebClient webClient(WebClient.Builder builder, CatalogConf catalogConf) {
        return builder.baseUrl(catalogConf.endpointUri()).build();
    }
}
