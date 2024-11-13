package com.freddie.marketplace.Controllers;


import com.cloudinary.Api;
import com.freddie.marketplace.DTOS.Requests.CreateAdminAccountRequest;
import com.freddie.marketplace.DTOS.Responses.ApiResponse;
import com.freddie.marketplace.services.admin.AdminService;
import com.freddie.marketplace.services.admin.CreateAdminAccountResponse;
import com.freddie.marketplace.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class AdminController {

    @Autowired
    AdminService service;

    @PostMapping("create_adminAccount")
    public ResponseEntity<?>  createAccountWith(@RequestBody CreateAdminAccountRequest request){
        try{
            CreateAdminAccountResponse response = service.createAccount(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch(RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }
}
