package de.maxgruber19.blog.kafka.patterns.retry.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class OrderDeserializer implements Deserializer<Order> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Order deserialize(String topic, byte[] data) {
        try {
            return objectMapper.convertValue(objectMapper.readValue(data, Map.class), Order.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
