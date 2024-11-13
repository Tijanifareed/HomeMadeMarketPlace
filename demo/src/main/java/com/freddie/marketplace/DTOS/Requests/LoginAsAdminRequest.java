package com.freddie.marketplace.DTOS.Requests;


import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class    LoginAsAdminRequest {
    private String userName;
    private String password;
}
