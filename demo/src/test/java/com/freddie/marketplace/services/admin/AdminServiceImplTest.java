package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    AdminService adminService;

   @Test
    public void testThatAdminCanGetSellerApplicant(){
       GetApplicantrequest request = new GetApplicantrequest();
       request.setAdminId(1L);
       GetApplicantresponse response = adminService.sellerApplicants(request);
        assertThat(response).isNotNull();
       System.out.println(response.toString());
        assertThat(response.getSellerList().size()).isEqualTo(3);
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

}