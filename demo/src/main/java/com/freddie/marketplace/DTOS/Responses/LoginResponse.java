package com.freddie.marketplace.DTOS.Responses;

import com.freddie.marketplace.data.model.UserRole;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginResponse {
    private String Message;
    private String Token;
    private String userName;
}
