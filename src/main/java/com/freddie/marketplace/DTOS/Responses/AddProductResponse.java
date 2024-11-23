package com.freddie.marketplace.DTOS.Responses;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AddProductResponse {
    private String message;
    private boolean isSucessfull;
}
