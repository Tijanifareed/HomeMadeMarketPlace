package com.freddie.marketplace.services.user;

import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.GetProfileRequest;
import com.freddie.marketplace.DTOS.Requests.LoginRequest;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.GetProfileResponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.DTOS.Responses.SellerApplicationResponse;
import com.freddie.marketplace.DTOS.Requests.SellerApplicationRequest;
import com.freddie.marketplace.Exceptions.EmailOrPhoneNumberExistsException;
import com.freddie.marketplace.Exceptions.UserCanNotApplyTwiceException;
import com.freddie.marketplace.Exceptions.UserNotFoundException;
import com.freddie.marketplace.Exceptions.UsernameAlreadyExistsException;
import com.freddie.marketplace.data.model.AppUser;
import com.freddie.marketplace.data.model.Seller;
import com.freddie.marketplace.data.repositories.AdminRepository;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.SellerRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import com.freddie.marketplace.services.image.ImageService;
import com.freddie.marketplace.services.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.freddie.marketplace.utils.Mapper.*;
import static com.freddie.marketplace.utils.Validators.validateRequest;


@Service
//@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SellerRepository sellerRepository;

     @Autowired
    private  UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Autowired
     AuthenticationManager authmanager;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private AdminRepository adminRepository;

    // CREATE USER
    @Override
    public CreateAccountResponse createNewUser(CreateAccountRequest request) {
        validateRequest(request);
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();
        String userName = request.getUsername();
        request.setPassword(encoder.encode(request.getPassword()));
        if(userExistsByPhoneNumberOrEmail(email, phoneNumber)) throw new EmailOrPhoneNumberExistsException("Email or PhoneNumber has already been used");
        else if(userNameExists(userName)) throw new UsernameAlreadyExistsException("This User "+userName+" has already been used Try another one");
        AppUser appUser = createUserMapper(request);
        userRepository.save(appUser);
        sendEmail(email,userName);
        CreateAccountResponse response = new CreateAccountResponse();
        response.setUserId(appUser.getId());
        response.setMessage("Account Created Successfully");
        return response;
    }


    private boolean userNameExists(String userName) {
        return userRepository.existsByUsername(userName);
    }



    public boolean userExistsByPhoneNumberOrEmail(String email, String phoneNumber){
        return userRepository.existsByEmail(email) || userRepository.existsByPhoneNumber(phoneNumber);
    }
    //LOGIN
    @Override
    public LoginResponse verifyUserWith(LoginRequest request) {
        String userName = request.getUsername();
        AppUser appUser = userRepository.findByUsername(userName);
        Authentication authentication =
                authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()){
            LoginResponse response = new LoginResponse();
            response.setMessage("Success");
            String token = jwtService.generateToken(request.getUsername(), appUser.getId());
            response.setToken(token);
            response.setUserName(userName);
            response.setRole(appUser.getRole().toString());
            return response;
        }
        LoginResponse response = new LoginResponse();
        response.setMessage("failed");
        return response;
    }

    @Override
    public SellerApplicationResponse applyToBeASellerWith(SellerApplicationRequest request) throws IOException {
        validateApplicationRequest(request);
        Seller seller = mapSeller(request);
        sellerRepository.save(seller);
        String idCardUrl = imageService.uploadImage(request.getIdCardUrl());
        updateSellerRequirements(seller.getId(), idCardUrl);

        SellerApplicationResponse response = new SellerApplicationResponse();
        response.setMessage("Your application has been received and is going under review an email will be sent to you to know if you are accepted or not");
        return response;
    }

    private void validateApplicationRequest(SellerApplicationRequest request) {
        List<Seller> sellers = sellerRepository.findAll();
        for(Seller seller: sellers){
            if (request.getUserId().equals(seller.getUserId())){
                throw new UserCanNotApplyTwiceException("You have already applied try again later");
            }
        }
    }


    private void updateSellerRequirements(Long id, String idCardUrl) {
        Optional<Seller> seller = sellerRepository.findById(id);
        if(seller.isPresent()){
        Seller seller1 = seller.get();
        seller1.setIdCardUrl(idCardUrl);
        sellerRepository.save(seller1);

        }else throw new RuntimeException("User not found");
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

                Welcome to RealMart! We’re thrilled to have you join our community of creative and discerning shoppers.
                You now have access to explore a wide range of unique, handmade products crafted by talented sellers. If you're interested in selling on RealMart, feel free to apply within your profile settings—our team will review your application and guide you through the process.
                Thank you for joining RealMart! If you have any questions, our support team is always here to help.
                Happy shopping,
                The RealMart Team
                """,name));
        mailSender.send(message);

    }

    @Override
    public void updateUserProfilePicture(Long userId, String imageUrl){

        Optional<AppUser> user = userRepository.findById(userId);

        user.orElseThrow(() -> new RuntimeException("user not found")).setProfilePicture(imageUrl);
        userRepository.save(user.get());

    }

    public AppUser findUserById(Long userId){

        List<AppUser> appUsers = userRepository.findAll();
        for(AppUser appUser : appUsers){
            if(Objects.equals(appUser.getId(), userId)){
             return appUser;
            }
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public GetProfileResponse getUserprofile(GetProfileRequest request) {
        AppUser appUser = findUserById(request.getUserId());
        GetProfileResponse response = new GetProfileResponse();
        response.setUserName(appUser.getUsername());
        response.setPhoneNumber(appUser.getPhoneNumber());
        response.setEmail(appUser.getEmail());
        response.setAddress(appUser.getAdress());
        response.setProfilePicture(appUser.getProfilePicture());
        return response;
    }




}
