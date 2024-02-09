package de.maxgruber19.blog.kafka.patterns.deadletter.clients.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
class ScheduledOrderProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Scheduled(fixedRate = 1000)
    public void produceTestMessage() {
        ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>("order-events-ingoing", UUID.randomUUID().toString());
        kafkaTemplate.send(record);
        log.debug("sent message {}", record);
    }

}
