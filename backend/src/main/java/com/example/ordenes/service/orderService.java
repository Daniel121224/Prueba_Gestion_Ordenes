package com.example.ordenes.service;

import com.example.ordenes.model.Item;
import com.example.ordenes.model.Order;
import com.example.ordenes.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrderById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public Order createOrder(Order order) {

        calculateTotal(order);

        if (order.getStatus() == null) {
            order.setStatus("PENDING");
        }

        return repository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {

        Order order = getOrderById(id);

        order.setCustomerName(updatedOrder.getCustomerName());
        order.setItems(updatedOrder.getItems());
        order.setStatus(updatedOrder.getStatus());

        calculateTotal(order);

        return repository.save(order);
    }

    public void deleteOrder(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }

        repository.deleteById(id);
    }

    private void calculateTotal(Order order) {

        double total = 0;

        for (Item item : order.getItems()) {
            total += item.getUnitPrice() * item.getQuantity();
        }

        order.setTotal(total);
    }

}