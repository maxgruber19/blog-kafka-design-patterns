package de.maxgruber19.blog.kafka.patterns.deadletter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.maxgruber19.blog.kafka.patterns.deadletter.model.Order;
import de.maxgruber19.blog.kafka.patterns.deadletter.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    public void process(Order order) throws JsonProcessingException {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            log.error("could not save order, moving it to retry-topic");
            kafkaTemplate.send("order-service-retry", objectMapper.writeValueAsString(order));
            log.info("sent order to retry-topic");
        }
    }

    public void retry(Order order) throws JsonProcessingException {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            log.error("retry failed, moving it to deadletter-topic");
            kafkaTemplate.send("order-service-deadletter", objectMapper.writeValueAsString(order));
            log.info("sent order to deadletter-topic");
        }
    }

}
