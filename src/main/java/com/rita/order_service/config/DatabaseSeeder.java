package com.rita.order_service.config;

import com.rita.order_service.model.Order;
import com.rita.order_service.model.OrderItem;
import com.rita.order_service.model.enums.Status;
import com.rita.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        orderRepository.deleteAll();

        Order order1 = Order.builder()
                .id(UUID.randomUUID().toString())
                .externalOrderId(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .status(Status.CREATED)
                .totalAmount(new BigDecimal("150.00"))
                .items(List.of(
                        OrderItem.builder()
                                .productId(UUID.randomUUID().toString())
                                .quantity(2)
                                .price(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(UUID.randomUUID().toString())
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .build()
                ))
                .build();

        Order order2 = Order.builder()
                .id(UUID.randomUUID().toString())
                .externalOrderId(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .status(Status.CONCLUDED)
                .totalAmount(new BigDecimal("300.00"))
                .items(List.of(
                        OrderItem.builder()
                                .productId(UUID.randomUUID().toString())
                                .quantity(3)
                                .price(new BigDecimal("100.00"))
                                .build()
                ))
                .build();

        orderRepository.saveAll(List.of(order1, order2));

        System.out.println("[INFO] Mock data inserted into MongoDB.");
    }

}
