package de.maxgruber19.blog.kafka.patterns.deadletter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
class ScheduledOrderProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedRate = 5000)
    public void produceTestMessage() throws JsonProcessingException {
        ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>("order-events-ingoing", this.generateRandomOrder());
        kafkaTemplate.send(record);
        log.debug("sent message {}", record);
    }

    private static final String[] PRODUCTS = {"T-shirt", "Jeans", "Shoes", "Dress", "Jacket", "Socks", "Hat", "Bag", "Watch", "Skirt"};

    public String generateRandomOrder() throws JsonProcessingException {
        Random random = new Random();
        int index = random.nextInt(PRODUCTS.length);
        Map<String, String> value = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String isoTimestamp = LocalDateTime.now().format(formatter);
        value.put("id", UUID.randomUUID().toString());
        value.put("article", PRODUCTS[index]);
        value.put("ordertime", isoTimestamp);
        return objectMapper.writeValueAsString(value);
    }

}
