package com.rita.order_service.exception;

import java.time.LocalDateTime;

public record ErrorMessage(
        Integer status,
        String traceId,
        String message,
        LocalDateTime timestamp
) {

}

