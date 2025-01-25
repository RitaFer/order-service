package com.rita.order_service.dto;

import com.rita.order_service.model.OrderItem;
import java.math.BigDecimal;

public record OrderItemResponse(
        String productId,
        Integer quantity,
        BigDecimal price
) {

    static OrderItemResponse from(OrderItem item) {
        return new OrderItemResponse(
                item.getProductId(),
                item.getQuantity(),
                item.getPrice()
        );
    }

}

