package com.example.ordenes.service;

import com.example.ordenes.exception.InvalidStatusException;
import com.example.ordenes.exception.InvalidStatusTransitionException;
import com.example.ordenes.exception.OrderNotFoundException;
import com.example.ordenes.model.Item;
import com.example.ordenes.model.Order;
import com.example.ordenes.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    // Estados válidos y su orden
    private static final List<String> VALID_STATUSES = Arrays.asList("PENDING", "CONFIRMED", "SHIPPED", "DELIVERED");

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrderById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order createOrder(Order order) {
        // Ignorar el total enviado
        calculateTotal(order);

        // Las órdenes nuevas siempre comienzan en PENDING
        order.setStatus("PENDING");

        return repository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existing = getOrderById(id);

        // Validar transición de estado
        validateStatusTransition(existing.getStatus(), updatedOrder.getStatus());

        existing.setCustomerName(updatedOrder.getCustomerName());
        existing.setItems(updatedOrder.getItems());
        existing.setStatus(updatedOrder.getStatus());

        calculateTotal(existing);
        return repository.save(existing);
    }

    // Nuevo método para actualizar solo el estado (PATCH)
    public Order updateOrderStatus(Long id, String newStatus) {
        Order existing = getOrderById(id);

        // Validar que el nuevo estado sea uno de los permitidos
        if (!VALID_STATUSES.contains(newStatus)) {
            throw new InvalidStatusException(newStatus);
        }

        validateStatusTransition(existing.getStatus(), newStatus);
        existing.setStatus(newStatus);
        return repository.save(existing);
    }

    public void deleteOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new OrderNotFoundException(id);
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

    private void validateStatusTransition(String current, String next) {
        if (current == null || next == null) return;
        int currentIdx = VALID_STATUSES.indexOf(current);
        int nextIdx = VALID_STATUSES.indexOf(next);

        // Si el estado actual no es válido (no debería ocurrir) o el siguiente no existe
        if (currentIdx == -1 || nextIdx == -1) {
            throw new InvalidStatusTransitionException(current, next);
        }

        // Solo se permite avanzar al siguiente estado en orden
        if (nextIdx != currentIdx + 1) {
            throw new InvalidStatusTransitionException(current, next);
        }
    }
}