package com.freddie.marketplace.utils;

import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.data.model.User;

public class Mapper {
    public static User createUserMapper(CreateAccountRequest request){
        User user = new User();
        user.setUsername(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setAdress(request.getAdress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setProfilePicture(request.getProfilePicture());
        user.setBio(request.getBio());
        return user;
    }
}
