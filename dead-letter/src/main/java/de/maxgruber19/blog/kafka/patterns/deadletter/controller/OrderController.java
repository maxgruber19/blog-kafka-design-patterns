package de.maxgruber19.blog.kafka.patterns.deadletter.controller;

import de.maxgruber19.blog.kafka.patterns.deadletter.model.Order;
import de.maxgruber19.blog.kafka.patterns.deadletter.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.stereotype.Controller;

/**
 * This controller will receive order events from order-events-ingoing and passes them through the mvc structure
 * to store it in a mysql database in the end. If the database breaks down the client will need to handle the failure.
 * In this case a single retry will be attempted to give messages a second chance. In case of exhaustion the message
 * will be routed to a deadletter queue.
 *
 * The use of this pattern is very powerful because you get a safe retry mechanism and don't lose the message if
 * failures occur and no deadletter topic is active. Alerts can be triggered based on messages in the deadletter topic
 * or users can choose how to proceed with the dead messages. They can be redelivered to a defibrillator topic via
 * ksql statements or other applications.
 */
@Slf4j
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    // This Listener will retry a message one time. Some exceptions are worth a retry others may be not.
    // Messages with exhausted retries and messages with excluded exceptions will be sent to deadletter topic.
    // A topic will be automatically created which is named like <source-topic>-retry-<retry-attempt starting from 0>
    // In this case the topic created will be order-events-ingoing-retry-0.
    // The attempts value has to be decremented by one to calculate the retries because the first regular attempt counts
    // as one.
    @RetryableTopic(attempts = "2", topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    @KafkaListener(topics = "order-events-ingoing", groupId = "order-consumer-blocking")
    public void consumeOrder(Order order) {
        long start = System.currentTimeMillis();
        log.info("read order {}", order.getId());
        orderService.process(order);
        log.info("processed order within {}ms {}", System.currentTimeMillis() - start, order.getId());
    }

    // This listener is just for observability. It will tell us when a message has been sent to the retry-topic.
    @KafkaListener(topics = "order-events-ingoing-retry-0", groupId = "order-consumer-blocking-debug")
    public void consumeRetriedOrder(Order order) {
        log.error("message was sent to retry-0 {}", order.getId());
    }

    // This listener is just for observability. It will tell us when a message has been sent to the retry-topic.
    @KafkaListener(topics = "order-events-ingoing-dlt", groupId = "order-consumer-blocking-debug")
    public void consumeDlqOrder(Order order) {
        log.error("message was sent to dlq because retries exhausted {}", order.getId());
    }

}
