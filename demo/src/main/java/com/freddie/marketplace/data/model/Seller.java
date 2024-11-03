package com.freddie.marketplace.data.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Seller  {
    @Id
    private Long id;
    private Long userId;
    private String idCardUrl;
    private String nin;
    private String bvn;
    private String portfolio;
}
