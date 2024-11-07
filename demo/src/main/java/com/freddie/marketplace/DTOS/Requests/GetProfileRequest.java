package com.freddie.marketplace.DTOS.Requests;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GetProfileRequest {
    private Long userId;
}
