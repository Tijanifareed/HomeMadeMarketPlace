package com.freddie.marketplace.data.model;


import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> items;
}
