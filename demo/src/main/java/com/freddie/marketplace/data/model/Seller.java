package com.freddie.marketplace.data.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Seller  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String idCardUrl;
    private String nin;
    private String bvn;
    private String portfolio;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
