package co.istad.orderservice.controller;

import co.istad.orderservice.domain.Order;
import co.istad.orderservice.kafka.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping
    public String placeOrder(@RequestBody Order order) {

        order.setOrderId(UUID.randomUUID().toString());

        /*OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order status is in pending state");
        orderEvent.setOrder(order);*/
        Map<String, Object> orderEvent = new HashMap<>();
        orderEvent.put("status", "PENDING");
        orderEvent.put("message", "Order status is in pending state");
        orderEvent.put("order", order);

        orderProducer.sendMessage(orderEvent);

        return "Order placed successfully.";
    }

}
