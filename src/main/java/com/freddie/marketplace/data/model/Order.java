package com.freddie.marketplace.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long buyerId;  // The buyer who placed the order

    private Long orderItems;  // List of items in the order

    private Double totalAmount;  // Total cost of the order

    private String status;  // Order status: e.g., 'Pending', 'Shipped', 'Delivered'

    private LocalDateTime orderDate;  // Date the order was placed
}

