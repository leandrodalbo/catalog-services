package com.catalog.orders.service;

import com.catalog.orders.domain.Book;
import com.catalog.orders.domain.Purchase;
import com.catalog.orders.domain.PurchaseStatus;
import com.catalog.orders.dto.PurchaseDto;
import com.catalog.orders.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository repository;

    @Mock
    private BooksCatalogService booksCatalogService;

    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private PurchaseService service;

    @Test
    void willCreateAnAcceptedPurchase() {
        var isbn = "abc7890";
        PurchaseDto dto = new PurchaseDto(isbn, 4F, 1);
        Book mockBook = new Book(1L, isbn, "any", "any", "4.0", 0);

        given(booksCatalogService.byIsbn(isbn)).willReturn(Mono.just(mockBook));
        given(repository.save(any())).willReturn(Mono.just(Purchase.of(isbn, dto.bookPrice(), dto.quantity(), PurchaseStatus.ACCEPTED)));


        Mono<Purchase> purchase = service.accept(dto);

        StepVerifier.create(purchase)
                .expectNextMatches(p -> p.status().equals(PurchaseStatus.ACCEPTED))
                .verifyComplete();
    }
}
