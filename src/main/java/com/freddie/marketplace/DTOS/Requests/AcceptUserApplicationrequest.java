package com.freddie.marketplace.DTOS.Requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptUserApplicationrequest {
    private Long adminId;
    private Long sellerId;
}
