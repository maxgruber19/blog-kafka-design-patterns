package de.maxgruber19.blog.kafka.patterns.deadletter.controller;

import de.maxgruber19.blog.kafka.patterns.deadletter.model.Order;
import de.maxgruber19.blog.kafka.patterns.deadletter.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class BlockingOrderController {

    @Autowired
    OrderService orderService;

    @KafkaListener(topics = "order-events-ingoing", groupId = "order-consumer-blocking")
    public void consumeOrder(Order order) {
        long start = System.currentTimeMillis();
        log.info("read order {}", order.getId());
        order.setController("blocking");
        orderService.process(order);
        log.info("processed order within {}ms {}", System.currentTimeMillis() - start, order.getId());
    }


}
