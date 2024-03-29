package de.maxgruber19.blog.kafka.patterns.poisonpill.service;

import de.maxgruber19.blog.kafka.patterns.poisonpill.model.Order;
import de.maxgruber19.blog.kafka.patterns.poisonpill.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void process(Order order) {
        orderRepository.save(order);
    }

}
