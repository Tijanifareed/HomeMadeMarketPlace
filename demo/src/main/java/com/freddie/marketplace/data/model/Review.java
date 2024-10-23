package com.freddie.marketplace.data.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Product product;  // The product being reviewed

        @ManyToOne
        private User buyer;  // The buyer who wrote the review

        private int rating;  // Rating out of 5
        private String comment;  // Review comment
        private LocalDateTime reviewDate;
}
