package com.freddie.marketplace.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "product_id")
        private Product product;  // The product being reviewed

        @ManyToOne
        @JoinColumn(name = "buyer_id")
        private User buyer;  // The buyer who wrote the review

        private int rating;  // Rating out of 5
        private String comment;  // Review comment
        private LocalDateTime reviewDate;
}
