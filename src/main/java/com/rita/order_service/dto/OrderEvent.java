package com.rita.order_service.dto;

import com.rita.order_service.model.Order;
import com.rita.order_service.model.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderEvent(
        Status status,
        String externalOrderId,
        String userId,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {

    public static OrderEvent from(Order order) {
        return new OrderEvent(
                order.getStatus(),
                order.getExternalOrderId(),
                order.getUserId(),
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }

}

