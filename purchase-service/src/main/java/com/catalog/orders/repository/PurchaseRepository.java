package com.catalog.orders.repository;

import com.catalog.orders.domain.Purchase;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends ReactiveCrudRepository<Purchase, Long> {
}
