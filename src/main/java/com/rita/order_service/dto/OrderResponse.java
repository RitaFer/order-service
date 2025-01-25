package com.rita.order_service.dto;

import com.rita.order_service.model.Order;
import com.rita.order_service.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Status status,
        String externalOrderId,
        String userId,
        BigDecimal totalAmount,
        List<OrderItemResponse> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getStatus(),
                order.getExternalOrderId(),
                order.getUserId(),
                order.getTotalAmount(),
                order.getItems().stream().map(OrderItemResponse::from).toList(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}

