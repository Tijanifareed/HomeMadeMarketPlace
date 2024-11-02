package com.freddie.marketplace.DTOS.Responses;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddProductResponse {
    private String message;
    private boolean isSucessfull;
}
