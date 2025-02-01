package com.freddie.marketplace.DTOS.Requests;


import com.freddie.marketplace.data.model.modelEnums.CategoryType;
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
    private String businessName;
    private String productDescription;
    private String productType;
}
