package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.CreateAdminAccountRequest;
import com.freddie.marketplace.DTOS.Requests.LoginAsAdminRequest;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.Exceptions.UserNotFoundException;
import com.freddie.marketplace.data.model.Admin;
import com.freddie.marketplace.data.model.ApplicationStatus;
import com.freddie.marketplace.data.model.Seller;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.repositories.AdminRepository;
import com.freddie.marketplace.data.repositories.SellerRepository;
import com.freddie.marketplace.services.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    SellerRepository sellerRepository;
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
    public List<Seller> sellerApplicants() {
        List<Seller> sellerApplicant = sellerRepository.findAll();
        List<Seller> pendingSellers = new ArrayList<Seller>();
        for(Seller seller : sellerApplicant){
            if(seller.getStatus() == ApplicationStatus.PENDING){
                pendingSellers.add(seller);
            }
        }

        return pendingSellers;
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
}
