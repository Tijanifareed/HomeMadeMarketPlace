package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.AcceptUserApplicationrequest;
import com.freddie.marketplace.DTOS.Requests.CreateAdminAccountRequest;
import com.freddie.marketplace.DTOS.Requests.GetApplicantrequest;
import com.freddie.marketplace.DTOS.Requests.LoginAsAdminRequest;
import com.freddie.marketplace.DTOS.Responses.AcceptUserApplicationResponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.data.model.Seller;

import java.util.List;

public interface AdminService {
    CreateAdminAccountResponse createAccount(CreateAdminAccountRequest request);
    List<Seller> sellerApplicants(GetApplicantrequest request);
    LoginResponse loginAsAdmin(LoginAsAdminRequest request);

    AcceptUserApplicationResponse acceptuserRequest(AcceptUserApplicationrequest request1);
}
