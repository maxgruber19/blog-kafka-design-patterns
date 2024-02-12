package de.maxgruber19.blog.kafka.patterns.poisonpill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String article;
    private String ordertime;
    private String runId;
    private int sequenceNumber;
    private String controller;

    @Override
    public String toString() {
        return "{ sequenceNo: " + this.sequenceNumber + ", id: " + this.id + ", article: " + this.article + " }";
    }
}