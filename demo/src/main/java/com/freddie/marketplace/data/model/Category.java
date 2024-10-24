package com.freddie.marketplace.data.model;

import jakarta.persistence.*;

import java.util.List;

@Entity

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CategoryType type;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}

