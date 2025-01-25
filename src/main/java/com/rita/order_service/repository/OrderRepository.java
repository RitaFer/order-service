package com.rita.order_service.repository;

import com.rita.order_service.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByExternalOrderId(String externalOrderId);

}
