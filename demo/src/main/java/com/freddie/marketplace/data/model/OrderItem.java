package com.freddie.marketplace.data.model;


import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long productId;  // The product being purchased


    private Long orderId;  // The order this item belongs to

    private int quantity;
}
