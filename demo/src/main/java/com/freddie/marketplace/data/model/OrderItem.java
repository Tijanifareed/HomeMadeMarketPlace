package com.freddie.marketplace.data.model;


import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;  // The product being purchased

    @ManyToOne
    private Order order;  // The order this item belongs to

    private int quantity;
}
