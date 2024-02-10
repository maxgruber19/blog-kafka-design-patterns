package de.maxgruber19.blog.kafka.patterns.deadletter.client;

import de.maxgruber19.blog.kafka.patterns.deadletter.model.Order;
import de.maxgruber19.blog.kafka.patterns.deadletter.model.OrderSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OrderProducerFactory {

    @Bean
    public org.springframework.kafka.core.ProducerFactory<Integer, Order> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OrderSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate<Integer, Order> kafkaTemplate() {
        return new KafkaTemplate<Integer, Order>(producerFactory());
    }



}
