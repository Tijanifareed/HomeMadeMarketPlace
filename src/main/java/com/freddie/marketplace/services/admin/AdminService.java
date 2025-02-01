package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.*;

public interface AdminService {
    CreateAdminAccountResponse createAccount(CreateAdminAccountRequest request);
    GetApplicantresponse sellerApplicants(GetApplicantrequest request);
    AcceptUserApplicationResponse acceptuserRequest(AcceptUserApplicationrequest request1);
    ViewApplicationResponse viewApplication(ViewApplicationRequest request1);
    DeclineUserApplicationResponse declineUserRequest(DeclineUserApplicationRequest request);
    AcceptSellerProductResponse acceptProductRequest(AcceptSellerProductRequest request);
}
