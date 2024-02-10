package de.maxgruber19.blog.kafka.patterns.deadletter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.maxgruber19.blog.kafka.patterns.deadletter.model.Order;
import de.maxgruber19.blog.kafka.patterns.deadletter.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @KafkaListener(topics = "order-events-ingoing", groupId = "order-consumer")
    public void consumeOrder(Order order) throws JsonProcessingException {
        long start = System.currentTimeMillis();
        log.info("read order {}", order.getId());
        orderService.process(order);
        log.info("processed order within {}ms {}", System.currentTimeMillis() - start, order.getId());
    }

    @KafkaListener(topics = "order-service-retry", groupId = "order-consumer")
    public void retryOrder(Order order) throws JsonProcessingException {
        long start = System.currentTimeMillis();
        log.info("retry order {}", order.getId());
        orderService.retry(order);
        log.info("successfully retried order within {}ms {}", System.currentTimeMillis() - start, order.getId());
    }

}
