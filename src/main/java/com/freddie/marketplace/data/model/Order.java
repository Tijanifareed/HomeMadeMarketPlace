package com.freddie.marketplace.data.model;

import com.freddie.marketplace.data.model.modelEnums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long buyerId;  // The buyer who placed the order

    private Long orderItems;  // List of items in the order

    private Double totalAmount;  // Total cost of the order
    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // Order status: e.g., 'Pending', 'Shipped', 'Delivered'

    private LocalDateTime orderDate;  // Date the order was placed
}

