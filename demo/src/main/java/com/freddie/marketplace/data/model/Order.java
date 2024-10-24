package com.freddie.marketplace.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;  // The buyer who placed the order

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;  // List of items in the order

    private Double totalAmount;  // Total cost of the order

    private String status;  // Order status: e.g., 'Pending', 'Shipped', 'Delivered'

    private LocalDateTime orderDate;  // Date the order was placed
}

