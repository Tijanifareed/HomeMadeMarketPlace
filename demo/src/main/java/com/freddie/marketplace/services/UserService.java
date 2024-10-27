package com.freddie.marketplace.services;

import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.LoginRequest;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.Exceptions.IncorrectInformationException;

public interface UserService {
    CreateAccountResponse createNewUser(CreateAccountRequest request);

    LoginResponse verifyUserWith(LoginRequest request);
}
