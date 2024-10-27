package com.freddie.marketplace.DTOS.Requests;

import com.freddie.marketplace.data.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAccountRequest {
    private String userName;
    private String email;
    private String password;
    private String adress;
    private String phoneNumber;
    private UserRole role;
    private String profilePicture;
    private String bio;


}
