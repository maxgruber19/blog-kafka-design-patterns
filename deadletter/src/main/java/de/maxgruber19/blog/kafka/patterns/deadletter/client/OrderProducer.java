package de.maxgruber19.blog.kafka.patterns.deadletter.client;

import de.maxgruber19.blog.kafka.patterns.deadletter.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * This producer will send orders to the topic order-events-ingoing scheduled by the spring scheduler. To have some
 * funny testdata the orders will be generated with some simple example articles.
 */
@Slf4j
@Component
class OrderProducer {

    private String runId = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private int sequenceNumber = 0;
    private static final String[] PRODUCTS = {"T-shirt", "Jeans", "Shoes", "Dress", "Jacket", "Socks", "Hat", "Bag", "Watch", "Skirt"};

    @Autowired
    KafkaTemplate<Integer, Order> kafkaTemplate;

    @Scheduled(fixedRate = 1000)
    public void produceTestMessage() {
        ProducerRecord<Integer, Order> record = new ProducerRecord<>("order-events-ingoing", this.generateRandomOrder());
        kafkaTemplate.send(record);
        log.debug("sent message {}", record);
    }

    public Order generateRandomOrder() {
        Order order = new Order();
        Random random = new Random();
        int index = random.nextInt(PRODUCTS.length);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        order.setId(UUID.randomUUID().toString());
        order.setArticle(PRODUCTS[index]);
        order.setRunId(this.runId);
        order.setSequenceNumber(sequenceNumber++);
        order.setOrdertime(LocalDateTime.now().format(formatter));

        return order;
    }

}
