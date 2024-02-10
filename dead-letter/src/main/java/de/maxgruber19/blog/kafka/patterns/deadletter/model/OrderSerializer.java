package de.maxgruber19.blog.kafka.patterns.deadletter.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class OrderSerializer implements Serializer<Order> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Order order) {
        try {
            return objectMapper.writeValueAsString(order).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
