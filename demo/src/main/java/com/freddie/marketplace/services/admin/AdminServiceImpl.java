package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.AcceptUserApplicationResponse;
import com.freddie.marketplace.DTOS.Responses.GetApplicantresponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.DTOS.Responses.ViewApplicationResponse;
import com.freddie.marketplace.Exceptions.UserNotFoundException;
import com.freddie.marketplace.data.model.Admin;
import com.freddie.marketplace.data.model.ApplicationStatus;
import com.freddie.marketplace.data.model.Seller;
import com.freddie.marketplace.data.repositories.AdminRepository;
import com.freddie.marketplace.data.repositories.SellerRepository;
import com.freddie.marketplace.services.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AuthenticationManager authmanager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Autowired
    private JWTService jwtService;

    @Override
    public CreateAdminAccountResponse createAccount(CreateAdminAccountRequest request) {
        Admin admin = new Admin();
        admin.setUsername(request.getUserName());
        admin.setPassword(encoder.encode(request.getPassword()));
        adminRepository.save(admin);
        CreateAdminAccountResponse response = new CreateAdminAccountResponse();
        response.setMessage("Account created successfully");
        return response;
    }

    @Override
    public GetApplicantresponse sellerApplicants(GetApplicantrequest request) {
//        validateApplicationOfAdmin(request.getAdminId());
        GetApplicantresponse getApplicantresponse = new  GetApplicantresponse();
        List<Seller> sellerApplicant = sellerRepository.findAll();
        List<Seller> pendingSellers = new ArrayList<Seller>();
        for(Seller seller : sellerApplicant){
            if(seller.getStatus() == ApplicationStatus.PENDING){
                pendingSellers.add(seller);
                getApplicantresponse.setSellerList(pendingSellers);
            }
        }

        return getApplicantresponse;
    }

    private void validateApplicationOfAdmin(Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if(admin.isEmpty()) throw new UserNotFoundException("Admin does not exists");
    }

    @Override
    public LoginResponse loginAsAdmin(LoginAsAdminRequest request) {
        LoginResponse response = new LoginResponse();
        Admin admin = adminRepository.findByUsername(request.getUserName());
        if(admin == null) throw new UserNotFoundException("Admin account not found");
       if(admin.getUsername().equals(request.getUserName()) && encoder.matches(request.getPassword(), admin.getPassword())){
           response.setMessage("Login Successfully");
           response.setUserName(request.getUserName());
           return  response;
       }
       throw new UserNotFoundException("Bad Credentials");
    }

    @Override
    public AcceptUserApplicationResponse acceptuserRequest(AcceptUserApplicationrequest request1) {

        return null;
    }

    @Override
    public ViewApplicationResponse viewApplication(ViewApplicationRequest request1) {
        Optional<Seller> seller = sellerRepository.findById(request1.getSellerId());
        if(seller.isPresent()) {
            ViewApplicationResponse response = new ViewApplicationResponse();
            response.setSeller(seller.get());
            return response;
        }
        throw new UserNotFoundException("Seller not found");

    }
}
