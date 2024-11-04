package com.freddie.marketplace.services.user;

import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.LoginRequest;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.GetProfileResponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.DTOS.Responses.SellerApplicationResponse;
import com.freddie.marketplace.DTOS.Requests.SellerApplicationRequest;
import com.freddie.marketplace.data.model.User;

public interface UserService {
    CreateAccountResponse createNewUser(CreateAccountRequest request);

    LoginResponse verifyUserWith(LoginRequest request);

    SellerApplicationResponse applyToBeASellerWith(SellerApplicationRequest request);

    void updateUserProfilePicture(Long userId, String imageUrl);

    GetProfileResponse getUserprofile(Long userId);
}
