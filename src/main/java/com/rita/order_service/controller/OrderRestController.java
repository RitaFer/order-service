package com.rita.order_service.controller;

import com.rita.order_service.dto.OrderRequest;
import com.rita.order_service.dto.OrderResponse;
import com.rita.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderRestController {

    private final OrderService orderService;

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order and returns the created order details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<OrderResponse> newOrder(
            @Valid @RequestBody(description = "Order details for creation", required = true) OrderRequest order) {
        OrderResponse response = OrderResponse.from(orderService.newOrder(order));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Retrieve order details",
            description = "Retrieves the details of an order by its external id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order details retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable String id) {
        OrderResponse response = OrderResponse.from(orderService.getOrder(id));
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Cancel an order",
            description = "Cancels an existing order by its external id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order canceled successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable String id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }

}
