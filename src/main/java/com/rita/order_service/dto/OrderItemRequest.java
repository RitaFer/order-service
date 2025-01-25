package com.rita.order_service.dto;

import com.rita.order_service.model.OrderItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

public record OrderItemRequest(
        @NotEmpty @NotNull String productId,
        @NotNull Integer quantity,
        @NotNull BigDecimal price
) {

    static OrderItem to(OrderItemRequest item) {
        return OrderItem.builder()
                .productId(item.productId())
                .quantity(item.quantity())
                .price(item.price().setScale(2, RoundingMode.HALF_UP))
                .build();
    }

}
