package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.AcceptUserApplicationResponse;
import com.freddie.marketplace.DTOS.Responses.GetApplicantresponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.DTOS.Responses.ViewApplicationResponse;
import com.freddie.marketplace.data.model.Seller;

import java.util.List;

public interface AdminService {
    CreateAdminAccountResponse createAccount(CreateAdminAccountRequest request);
    GetApplicantresponse sellerApplicants(GetApplicantrequest request);
    LoginResponse loginAsAdmin(LoginAsAdminRequest request);

    AcceptUserApplicationResponse acceptuserRequest(AcceptUserApplicationrequest request1);

    ViewApplicationResponse viewApplication(ViewApplicationRequest request1);
}
