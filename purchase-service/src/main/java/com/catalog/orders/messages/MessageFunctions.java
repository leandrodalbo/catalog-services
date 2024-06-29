package com.catalog.orders.messages;

import com.catalog.orders.dto.PurchaseDispatchedDto;
import com.catalog.orders.service.PurchaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class MessageFunctions {

    @Bean
    public Consumer<Flux<PurchaseDispatchedDto>> setDispatched(PurchaseService service) {
        return it -> service.setDispatched(it).subscribe();
    }
}
