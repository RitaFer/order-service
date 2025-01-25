package com.rita.order_service.service;

import com.rita.order_service.exception.NotFoundOrderException;
import com.rita.order_service.dto.OrderEvent;
import com.rita.order_service.dto.OrderRequest;
import com.rita.order_service.model.Order;
import com.rita.order_service.producer.OrderProducer;
import com.rita.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public Order newOrder(OrderRequest orderRequest) {
        Order order = OrderRequest.to(orderRequest);
        Order savedOrder = orderRepository.save(order);
        sendEvent(savedOrder);

        return savedOrder;
    }

    public Order getOrder(String id) {
        return orderRepository.findByExternalOrderId(id)
                .orElseThrow(() -> new NotFoundOrderException("Not Found Order With ID: " + id));
    }

    public void cancelOrder(String id) {
        Order order = getOrder(id);
        order.cancelOrder();
        orderRepository.save(order);
    }

    private void sendEvent(Order order) {
        OrderEvent event = OrderEvent.from(order);
        orderProducer.publishOrderEvent(event);
    }
}
