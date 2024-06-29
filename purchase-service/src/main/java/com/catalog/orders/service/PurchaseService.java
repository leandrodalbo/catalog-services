package com.catalog.orders.service;

import com.catalog.orders.domain.Purchase;
import com.catalog.orders.domain.PurchaseStatus;
import com.catalog.orders.dto.PurchaseAcceptedDto;
import com.catalog.orders.dto.PurchaseDispatchedDto;
import com.catalog.orders.dto.PurchaseDto;
import com.catalog.orders.repository.PurchaseRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurchaseService {

    private final PurchaseRepository repository;
    private final BooksCatalogService booksCatalogService;
    private final StreamBridge streamBridge;

    public PurchaseService(PurchaseRepository repository, BooksCatalogService booksCatalogService, StreamBridge streamBridge) {
        this.repository = repository;
        this.booksCatalogService = booksCatalogService;
        this.streamBridge = streamBridge;
    }

    public Flux<Purchase> all() {
        return repository.findAll();
    }

    @Transactional
    public Mono<Purchase> accept(PurchaseDto dto) {
        return booksCatalogService.byIsbn(dto.bookIsbn())
                .map(book -> {
                    if (dto.bookPrice().equals(Float.valueOf(book.price())))
                        return Purchase.of(dto.bookIsbn(), dto.bookPrice(), dto.quantity(), PurchaseStatus.ACCEPTED);
                    else
                        return Purchase.of(dto.bookIsbn(), dto.bookPrice(), dto.quantity(), PurchaseStatus.REJECTED);

                })
                .defaultIfEmpty(Purchase.of(dto.bookIsbn(), dto.bookPrice(), dto.quantity(), PurchaseStatus.REJECTED))
                .flatMap(p -> {

                    if (PurchaseStatus.REJECTED.equals(p.status()))
                        return Mono.just(p);
                    else
                        return repository.save(p);
                })
                .doOnNext(this::publishAcceptedPurchase);


    }

    public Flux<Purchase> setDispatched(Flux<PurchaseDispatchedDto> purchased) {
        return purchased.flatMap(purchase -> repository.findById(purchase.purchaseId()))
                .map(it -> Purchase.of(it.bookIsbn(), it.bookPrice(), it.quantity(), PurchaseStatus.DISPATCHED))
                .flatMap(repository::save);
    }


    private void publishAcceptedPurchase(Purchase purchase) {
        if (!PurchaseStatus.ACCEPTED.equals(purchase.status()))
            return;

        streamBridge.send("acceptOrder-out-0", new PurchaseAcceptedDto(purchase.id()));

    }
}
