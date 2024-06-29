package com.catalog.orders.controller;

import com.catalog.orders.domain.Purchase;
import com.catalog.orders.dto.PurchaseDto;
import com.catalog.orders.service.PurchaseService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    private final PurchaseService service;

    public PurchaseController(PurchaseService service) {
        this.service = service;
    }


    @GetMapping("/all")
    public Flux<Purchase> allOrders() {
        return service.all();
    }

    @PostMapping("/accept")
    public Mono<Purchase> acceptOrder(@RequestBody PurchaseDto purchaseDto) {
        return service.accept(purchaseDto);
    }
}
