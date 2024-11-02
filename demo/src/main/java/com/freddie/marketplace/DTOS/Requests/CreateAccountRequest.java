package com.freddie.marketplace.DTOS.Requests;

import com.freddie.marketplace.data.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateAccountRequest {
    private String username;
    private String email;
    private String password;
    private String adress;
    private String phoneNumber;
    private UserRole role;
    private String profilePicture;
    private String bio;


}
