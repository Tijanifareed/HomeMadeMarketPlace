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
    private String adress;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String profilePicture;
    private String bio;

}

