package com.catalog.orders.controller;


import com.catalog.orders.domain.Purchase;
import com.catalog.orders.domain.PurchaseStatus;
import com.catalog.orders.dto.PurchaseDto;
import com.catalog.orders.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebFluxTest(PurchaseController.class)
public class PurchaseControllerTest {

    @MockBean
    PurchaseService service;

    @Autowired
    WebTestClient client;

    @Test
    void createsAnAcceptedOrder() {

        PurchaseDto dto = new PurchaseDto("a3svd", 34.4F, 3);


        given(service.accept(any())).willReturn(Mono.just(Purchase.of("a3svd", 34.4F, 3, PurchaseStatus.ACCEPTED)));

        client.post()
                .uri("/purchase/accept")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful();


    }
}
