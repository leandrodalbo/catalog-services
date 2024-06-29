package com.catalog.cataloggateway.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class FallbackEndpoints {


    @Bean
    public RouterFunction<ServerResponse> responseRouterFunction(){

        return RouterFunctions.route().GET("/catalog-fallback",
                request -> ServerResponse.ok().body(Mono.just("not working"), String.class)).build();
    }
}
