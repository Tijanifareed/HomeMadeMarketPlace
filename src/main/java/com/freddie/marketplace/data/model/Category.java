package com.freddie.marketplace.data.model;

import com.freddie.marketplace.data.model.modelEnums.CategoryType;
import jakarta.persistence.*;

import java.util.List;

@Entity

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}

