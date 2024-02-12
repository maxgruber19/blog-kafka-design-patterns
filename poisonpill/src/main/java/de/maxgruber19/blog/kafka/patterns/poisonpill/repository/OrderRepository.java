package de.maxgruber19.blog.kafka.patterns.poisonpill.repository;

import de.maxgruber19.blog.kafka.patterns.poisonpill.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}
