package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.*;
import com.freddie.marketplace.Exceptions.UserNotFoundException;
import com.freddie.marketplace.data.model.*;
import com.freddie.marketplace.data.repositories.AdminRepository;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.SellerRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import com.freddie.marketplace.services.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender mailSender;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

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
        Optional<Seller> seller = sellerRepository.findById(request1.getSellerId());

            Optional<User> user = userRepository.findById(seller.get().getUserId());
            String email = user.get().getEmail();
            String name = user.get().getUsername();
            sendEmail(email, name);
            seller.get().setStatus(ApplicationStatus.APPROVED);
            sellerRepository.save(seller.get());
            AcceptUserApplicationResponse response = new AcceptUserApplicationResponse();
            response.setMessage("Success");
            return response;

    }


    @Value("$(RealMart)")
    private  String fromEmailId;

    public  void sendEmail(String userEmail, String name){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(userEmail);
        message.setSubject("RealMart Account Verification");
        message.setText(String.format("""
                Hi %s,

                We are thrilled to inform you that your application to become a seller on RealMart has been successfully approved! 🎊
                As a verified seller, you now have access to tools and features designed to help you list and manage your products seamlessly. You can start sharing your unique creations and connecting with buyers right away.
                To get started:
                1. Log in to your account on RealMart.
                2. Access your Seller Dashboard to add your first product.
                3.Explore the Seller Resources section for tips on maximizing your success on RealMart.
                If you have any questions or need support, our team is here to assist you every step of the way.
                Welcome to the RealMart Seller Community! We look forward to seeing your business thrive.                
                Warm regards,
                The RealMart Team
                """,name));
        mailSender.send(message);

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

    @Override
    public DeclineUserApplicationResponse declineUserRequest(DeclineUserApplicationRequest request) {
        Optional<Seller> seller = sellerRepository.findById(request.getSellerId());

        Optional<User> user = userRepository.findById(seller.get().getUserId());
        String email = user.get().getEmail();
        String name = user.get().getUsername();
//        sendDeclineEmail(email, name);
        seller.get().setStatus(ApplicationStatus.DISAPPROVED);
        sellerRepository.save(seller.get());
        DeclineUserApplicationResponse response = new DeclineUserApplicationResponse();
        response.setMessage("Success");
        return response;
    }

    @Override
    public AcceptSellerProductResponse acceptProductRequest(AcceptSellerProductRequest request) {
        Optional<Product> products = productRepository.findById(request.getProductId());
        products.get().setStatus(ProductStatus.SUCCESSFUL);
        productRepository.save(products.get());
        AcceptSellerProductResponse response = new  AcceptSellerProductResponse();
        response.setMessage("Success");
        return response;
    }

    public  void sendDeclineEmail(String userEmail, String name){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(userEmail);
        message.setSubject("RealMart Account Verification");
        message.setText(String.format("""
                Hi %s,
                
                Warm regards,
                The RealMart Team
                """,name));
        mailSender.send(message);

    }

}
