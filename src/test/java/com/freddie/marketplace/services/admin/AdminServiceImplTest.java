package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.*;

import com.freddie.marketplace.data.model.Admin;
import com.freddie.marketplace.data.model.AppUser;
import com.freddie.marketplace.data.repositories.AdminRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest


class AdminServiceImplTest {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        userRepository.deleteAll();

    }

    @Test
    public void testThatAdminCanGetSellerApplicant(){
       CreateAdminAccountRequest request = new CreateAdminAccountRequest();
       request.setUserName("freddie");
       request.setPassword("olamide");
        System.out.println(request.toString());
       CreateAdminAccountResponse response = adminService.createAccount(request);
        System.out.println(request.toString());
       AppUser user = userRepository.findByUsername(request.getUserName());

       GetApplicantrequest request1 = new GetApplicantrequest();
       request1.setAdminId(user.getId());
       GetApplicantresponse response1 = adminService.sellerApplicants(request1);
        assertThat(response).isNotNull();
       System.out.println(response1.toString());
        assertThat(response1.getSellerList().size()).isEqualTo(2);
   }


   @Test
    public void testThatAdminCanViewAUserApplication(){
       GetApplicantrequest request = new GetApplicantrequest();
       request.setAdminId(1L);
       GetApplicantresponse response = adminService.sellerApplicants(request);
       ViewApplicationRequest request1 = new ViewApplicationRequest();
       request1.setSellerId(2L);
        ViewApplicationResponse response1 = adminService.viewApplication(request1);
        assertThat(response1.getSeller().getId()).isEqualTo(2L);

   }

   @Test
    public void testThatAdminCanAcceptASellerApplication(){
       AcceptUserApplicationrequest request = new AcceptUserApplicationrequest();
       request.setSellerId(6L);
       request.setAdminId(2L);
       AcceptUserApplicationResponse response = adminService.acceptuserRequest(request);
       assertThat(response.getMessage()).isEqualTo("Success");
   }

   @Test
    public void testThatAdminCanDeclineASellerApplication(){
       DeclineUserApplicationRequest request = new DeclineUserApplicationRequest();
       request.setAdminId(1L);
       request.setSellerId(5L);
       DeclineUserApplicationResponse response = adminService.declineUserRequest(request);
       assertThat(response).isNotNull();
   }

   @Test
    public void testThatAdminCanAcceptAProduct(){
       AcceptSellerProductRequest request = new AcceptSellerProductRequest();
       request.setAdminId(1L);
       request.setProductId(1L);
       AcceptSellerProductResponse response = adminService.acceptProductRequest(request);
       assertThat(response).isNotNull();
   }

   @Test
    public void testThatAdminCanDeclineAProduct(){

   }

   @Test
    public void testThatAdminCanCreateAccount(){
       CreateAdminAccountRequest request = new CreateAdminAccountRequest();
       request.setUserName("freddie");
       request.setPassword("olamide");
       CreateAdminAccountResponse response = adminService.createAccount(request);

   }



}