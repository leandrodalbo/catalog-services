package catalog.distpatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class DistpatcherFunctions {

    @Bean
    public Function<PurchaseAcceptedMessage, Long> packagePurchase() {
        return PurchaseAcceptedMessage::purchaseId;
    }


    @Bean
    public Function<Flux<Long>, Flux<PurchasedDistpatchedMessage>> labelPurchase() {
        return longFlux -> longFlux.map(PurchasedDistpatchedMessage::new);
    }


}
