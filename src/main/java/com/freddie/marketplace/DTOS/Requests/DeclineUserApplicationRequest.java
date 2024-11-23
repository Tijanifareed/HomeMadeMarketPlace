package com.freddie.marketplace.DTOS.Requests;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeclineUserApplicationRequest {
    private Long adminId;
    private Long sellerId;
}
