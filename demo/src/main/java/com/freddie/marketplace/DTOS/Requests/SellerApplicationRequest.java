package com.freddie.marketplace.DTOS.Requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerApplicationRequest {
    private Long userId;
    private String idCardUrl;
    private String nin;
    private String bvn;
    private String portfolio;
}
