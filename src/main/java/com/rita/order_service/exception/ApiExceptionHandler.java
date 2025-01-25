package com.rita.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "Validation error: " + ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildErrorResponse(BAD_REQUEST, message, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(IllegalArgumentException ex) {
        return buildErrorResponse(BAD_REQUEST, ex.getMessage(), null);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundOrderException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundOrderException(NotFoundOrderException ex) {
        return buildErrorResponse(NOT_FOUND, ex.getMessage(), null);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleInternalException(Exception ex) {
        String traceId = UUID.randomUUID().toString();
        return buildErrorResponse(INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please contact support.", traceId);
    }

    private ResponseEntity<ErrorMessage> buildErrorResponse(
            HttpStatus status, String message, String traceId) {
        var errorMessage = new ErrorMessage(
                status.value(),
                message,
                traceId,
                LocalDateTime.now()

        );

        return new ResponseEntity<>(errorMessage, status);
    }
}
