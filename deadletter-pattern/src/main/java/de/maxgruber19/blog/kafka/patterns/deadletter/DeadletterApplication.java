package de.maxgruber19.blog.kafka.patterns.deadletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@EnableScheduling
@SpringBootApplication
public class DeadletterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeadletterApplication.class, args);
    }

}
