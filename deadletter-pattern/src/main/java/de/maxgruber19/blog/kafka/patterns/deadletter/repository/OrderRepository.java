package de.maxgruber19.blog.kafka.patterns.deadletter.repository;

import de.maxgruber19.blog.kafka.patterns.deadletter.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderRepository {

    public void save(Order order) {
        log.info("saved order {}", order.getId());
    }

}
