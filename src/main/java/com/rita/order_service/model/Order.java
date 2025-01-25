package com.rita.order_service.model;

import com.rita.order_service.model.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.coyote.BadRequestException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @Builder.Default
    private String externalOrderId = UUID.randomUUID().toString();

    @NotNull(message = "User ID cannot be null")
    private String userId;

    @Builder.Default
    private Status status = Status.CREATED;

    @Builder.Default
    private LocalDateTime createdAt = Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime();

    private LocalDateTime updatedAt;

    @NotNull(message = "Total amount cannot be null")
    private BigDecimal totalAmount;

    @NotNull(message = "Order items cannot be null")
    private List<OrderItem> items;

    public void cancelOrder() {
        if(this.status != Status.CREATED){
            throw new IllegalArgumentException("This order is already in an advanced status.");
        }
        this.status = Status.CANCELLED;
        this.updatedAt = Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

}
