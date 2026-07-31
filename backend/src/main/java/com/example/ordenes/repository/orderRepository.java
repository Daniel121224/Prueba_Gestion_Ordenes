package com.example.ordenes.repository;

import com.example.ordenes.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    private Long nextId = 1L;

    public List<Order> findAll() {
        return orders;
    }

    public Optional<Order> findById(Long id) {

        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    public Order save(Order order) {

        if (order.getId() == null) {

            order.setId(nextId++);
            orders.add(order);

        } else {

            deleteById(order.getId());
            orders.add(order);
        }

        return order;
    }

    public void deleteById(Long id) {

        orders.removeIf(order -> order.getId().equals(id));
    }

    public boolean existsById(Long id) {

        return orders.stream()
                .anyMatch(order -> order.getId().equals(id));
    }
}
