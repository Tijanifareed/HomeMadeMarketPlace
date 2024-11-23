package com.freddie.marketplace.DTOS.Requests;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateAdminAccountRequest {
    private String userName;
    private String password;}
