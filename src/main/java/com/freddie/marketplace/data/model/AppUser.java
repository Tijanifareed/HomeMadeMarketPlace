package com.freddie.marketplace.data.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String adress;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String profilePicture;
    private String bio;

}

