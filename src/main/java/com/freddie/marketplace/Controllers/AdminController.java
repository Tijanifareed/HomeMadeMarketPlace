package com.freddie.marketplace.Controllers;


import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.*;
import com.freddie.marketplace.services.admin.AdminService;
import com.freddie.marketplace.DTOS.Responses.CreateAdminAccountResponse;
import com.freddie.marketplace.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://real-mart-by-freddie.vercel.app"})
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

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

//    @PostMapping("loginByAdmin")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request){
//        try {
//            System.out.println(request.toString());
//            LoginResponse response = userService.verifyUserWith(request);
//            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
//        }catch (RuntimeException exception){
//            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
//        }
//    }
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

    @PostMapping("getAppendingSeller")
    public ResponseEntity<?> getAPendingSeller(@RequestBody ViewApplicationRequest request){
        try{
            ViewApplicationResponse response = adminService.viewApplication(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch(RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("approveRequest")
    public ResponseEntity<?>  approveApplication(@RequestBody AcceptUserApplicationrequest request){
        try{
            AcceptUserApplicationResponse response = adminService.acceptuserRequest(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch(RuntimeException exception){
        return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }
}
