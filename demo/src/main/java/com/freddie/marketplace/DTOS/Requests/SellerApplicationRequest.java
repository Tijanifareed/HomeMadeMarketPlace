package com.freddie.marketplace.DTOS.Requests;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class SellerApplicationRequest {
    private Long userId;
    private MultipartFile idCardUrl;
    private String nin;
    private String bvn;
    private MultipartFile portfolio;
}
