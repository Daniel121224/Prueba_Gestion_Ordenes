package com.example.ordenes.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long id;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<Item> items = new ArrayList<>();

    private Double total;

    private String status;

    public Order() {
    }

    public Order(Long id,
                 String customerName,
                 List<Item> items,
                 Double total,
                 String status) {

        this.id = id;
        this.customerName = customerName;
        this.items = items;
        this.total = total;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}