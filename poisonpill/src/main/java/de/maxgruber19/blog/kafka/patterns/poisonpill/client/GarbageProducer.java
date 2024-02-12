package de.maxgruber19.blog.kafka.patterns.poisonpill.client;

import de.maxgruber19.blog.kafka.patterns.poisonpill.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
class GarbageProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Scheduled(fixedDelay = 15000)
    public void produceTestMessage() {
        log.info("sending garbage");
        ProducerRecord<Integer, String> record = new ProducerRecord<>("order-events-ingoing", "Let me check how fault tolerant you really are, kafka.");
        kafkaTemplate.send(record);
        log.debug("sent garbage {}", record);
    }

}
