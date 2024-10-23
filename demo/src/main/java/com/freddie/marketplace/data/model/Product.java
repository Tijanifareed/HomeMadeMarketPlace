package com.freddie.marketplace.data.model;

import jakarta.persistence.*;

import java.util.List;

@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;  // The seller who listed the product

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;  // Product category (e.g., Crafts, Art)

    @ElementCollection
    private List<String> images;  // Cloudinary URLs for product images

    private int stock;  // Quantity available for purchase
}

