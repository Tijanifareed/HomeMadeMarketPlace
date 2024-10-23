package com.freddie.marketplace.data.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String profilePicture;
    private String bio;
    @OneToMany(mappedBy = "seller")
    private List<Product> products;
    @OneToMany(mappedBy = "buyer")
    private List<Order> orders;
}

