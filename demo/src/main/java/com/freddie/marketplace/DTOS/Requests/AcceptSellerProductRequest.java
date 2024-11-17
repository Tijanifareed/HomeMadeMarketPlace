package com.freddie.marketplace.DTOS.Requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AcceptSellerProductRequest {
    private Long adminId;
    private Long productId;
}
