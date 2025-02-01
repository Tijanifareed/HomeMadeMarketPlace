package com.freddie.marketplace.DTOS.Responses;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GetProfileResponse {
    private String userName;
    private String phoneNumber;
    private String email;
    private String address;
    private String profilePicture;

}
