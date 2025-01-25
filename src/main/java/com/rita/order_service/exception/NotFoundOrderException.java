package com.rita.order_service.exception;

public class NotFoundOrderException extends RuntimeException {

    public NotFoundOrderException(String message) {
        super(message);
    }

}

