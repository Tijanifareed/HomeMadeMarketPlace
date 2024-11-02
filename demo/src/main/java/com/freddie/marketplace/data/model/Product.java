package com.freddie.marketplace.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String description;
    private Double price;

    private long sellerId;  // The seller who listed the product
    @Enumerated(EnumType.STRING)
    private CategoryType category;  // Product category (e.g., Crafts, Art)
    @ElementCollection
    private List<String> images;  // Cloudinary URLs for product images
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private int stock;  // Quantity available for

    @OneToMany
    private List<Review> reviews;
}

