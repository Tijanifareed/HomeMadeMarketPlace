package com.freddie.marketplace.data.model;


import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;  // The product being purchased

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;  // The order this item belongs to

    private int quantity;
}
