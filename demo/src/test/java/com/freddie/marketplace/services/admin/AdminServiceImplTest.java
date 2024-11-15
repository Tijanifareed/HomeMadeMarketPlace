package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.AcceptUserApplicationrequest;
import com.freddie.marketplace.DTOS.Requests.GetApplicantrequest;
import com.freddie.marketplace.DTOS.Responses.AcceptUserApplicationRespose;
import com.freddie.marketplace.DTOS.Responses.GetApplicantresponse;
import com.freddie.marketplace.data.model.Seller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    AdminService adminService;

   @Test
    public void testThatAdminCanGetSellerApplicant(){
       GetApplicantrequest request = new GetApplicantrequest();
       request.setAdminId(1L);
       GetApplicantresponse response = new GetApplicantresponse();
       response.setSellerList(adminService.sellerApplicants(request));
        assertThat(response).isNotNull();
       System.out.println(response.toString());
        assertThat(response.getSellerList().size()).isEqualTo(3);
   }
//
   @Test
    public void testThatAdminCanAcceptTheUSerRequestToBecomeASeller(){
       GetApplicantrequest request = new GetApplicantrequest();
       request.setAdminId(1L);
       GetApplicantresponse response = new GetApplicantresponse();
       response.setSellerList(adminService.sellerApplicants(request));
       AcceptUserApplicationrequest request1 = new AcceptUserApplicationrequest();
       request1.setAdminId(1L);
       request1.setSellerId(2L);
       AcceptUserApplicationResponse respose = new AcceptUserApplicationRespose();

   }
}