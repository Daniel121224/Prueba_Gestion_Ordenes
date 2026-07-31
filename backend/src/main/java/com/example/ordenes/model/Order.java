package com.example.ordenes.model;

import jakarta.persistence.*;

@Entity
public class order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    //dicciionario de items: "name": "item1", "quantity": 2, "price": 10.0
    @ElementCollection
    private java.util.Map<String, Item> items;

    @Embeddable
    public static class Item implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private Integer quantity;
        private Double price;

        public Item() {}

        public Item(Integer quantity, Double price) {
            this.quantity = quantity;
            this.price = price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }

    private Double total;

    private String status;
}
