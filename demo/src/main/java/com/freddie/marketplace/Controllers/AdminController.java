package com.freddie.marketplace.Controllers;


import com.freddie.marketplace.DTOS.Requests.CreateAdminAccountRequest;
import com.freddie.marketplace.DTOS.Requests.GetApplicantrequest;
import com.freddie.marketplace.DTOS.Requests.LoginAsAdminRequest;
import com.freddie.marketplace.DTOS.Responses.ApiResponse;
import com.freddie.marketplace.DTOS.Responses.GetApplicantresponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.services.admin.AdminService;
import com.freddie.marketplace.services.admin.CreateAdminAccountResponse;
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
    AdminService adminService;

    @PostMapping("create_adminAccount")
    public ResponseEntity<?>  createAccountWith(@RequestBody CreateAdminAccountRequest request){
        try{
            System.out.println(request.toString());
            CreateAdminAccountResponse response = adminService.createAccount(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch(RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("loginByAdmin")
    public ResponseEntity<?> login(@RequestBody LoginAsAdminRequest request){
        try {
            System.out.println(request.toString());
            LoginResponse response = adminService.loginAsAdmin(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("getAllPendingSellers")
    public ResponseEntity<?> getAllPendingSellers(@RequestBody GetApplicantrequest getApplicantrequest){
        try{
            System.out.print(getApplicantrequest);
            GetApplicantresponse getApplicantresponse = adminService.sellerApplicants(getApplicantrequest);
            return new ResponseEntity<>(new ApiResponse(true, getApplicantresponse), CREATED);
        }catch(RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }
}
