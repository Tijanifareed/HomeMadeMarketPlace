package com.freddie.marketplace.DTOS.Requests;


import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class    LoginAsAdminRequest {
    private String userName;
    private String password;
}
