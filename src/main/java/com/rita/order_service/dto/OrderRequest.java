package com.rita.order_service.dto;

import com.rita.order_service.model.Order;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public record OrderRequest(
        @NotEmpty @NotNull String userId,
        @NotEmpty @NotNull List<OrderItemRequest> items
) {

    public static Order to(OrderRequest request) {
        return Order.builder()
                .userId(request.userId())
                .totalAmount(calculateTotalAmount(request.items()))
                .items(request.items().stream().map(OrderItemRequest::to).toList())
                .updatedAt(Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime())
                .build();
    }

    private static BigDecimal calculateTotalAmount(List<OrderItemRequest> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

}
