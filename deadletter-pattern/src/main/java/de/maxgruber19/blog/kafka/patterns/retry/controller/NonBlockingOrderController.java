package de.maxgruber19.blog.kafka.patterns.retry.controller;

import de.maxgruber19.blog.kafka.patterns.retry.model.Order;
import de.maxgruber19.blog.kafka.patterns.retry.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@Profile("disabled")
public class NonBlockingOrderController {

    @Autowired
    OrderService orderService;

    @RetryableTopic
    @KafkaListener(topics = "order-events-ingoing", groupId = "order-consumer-nonblocking")
    public void consumeOrder(Order order) {
        long start = System.currentTimeMillis();
        log.info("read order {}", order.getId());
        order.setController("non-blocking");
        orderService.process(order);
        log.info("processed order within {}ms {}", System.currentTimeMillis() - start, order.getId());
    }

}
