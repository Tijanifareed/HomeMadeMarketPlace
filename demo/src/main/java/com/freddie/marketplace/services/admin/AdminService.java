package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.CreateAdminAccountRequest;
import com.freddie.marketplace.DTOS.Requests.GetApplicantrequest;
import com.freddie.marketplace.DTOS.Requests.LoginAsAdminRequest;
import com.freddie.marketplace.DTOS.Responses.GetApplicantresponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.data.model.Admin;
import com.freddie.marketplace.data.model.Seller;

import java.util.List;

public interface AdminService {
    CreateAdminAccountResponse createAccount(CreateAdminAccountRequest request);
    GetApplicantresponse sellerApplicants(GetApplicantrequest request);
    LoginResponse loginAsAdmin(LoginAsAdminRequest request);
}
