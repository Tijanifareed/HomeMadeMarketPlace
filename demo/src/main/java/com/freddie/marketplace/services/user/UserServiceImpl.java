package com.freddie.marketplace.services.user;

import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.LoginRequest;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.GetProfileResponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.DTOS.Responses.SellerApplicationResponse;
import com.freddie.marketplace.DTOS.Requests.SellerApplicationRequest;
import com.freddie.marketplace.Exceptions.EmailOrPhoneNumberExistsException;
import com.freddie.marketplace.Exceptions.UserNotFoundException;
import com.freddie.marketplace.Exceptions.UsernameAlreadyExistsException;
import com.freddie.marketplace.data.model.Seller;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.model.UserRole;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.SellerRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
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

import java.util.List;
import java.util.Objects;

import static com.freddie.marketplace.utils.Mapper.*;
import static com.freddie.marketplace.utils.Validators.validateRequest;


@Service
//@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SellerRepository sellerRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private  UserRepository userRepository;

    @Autowired
     AuthenticationManager authmanager;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public CreateAccountResponse createNewUser(CreateAccountRequest request) {
        validateRequest(request);
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();
        String userName = request.getUsername();
        request.setPassword(encoder.encode(request.getPassword()));
        if(userExistsByPhoneNumberOrEmail(email, phoneNumber)) throw new EmailOrPhoneNumberExistsException("Email or PhoneNumber has already been used");
        else if(userNameExists(userName)) throw new UsernameAlreadyExistsException("This User "+userName+" has already been used Try another one");
//        System.out.println(userRepository.existsByEmail(email));
        User user = createUserMapper(request);
        userRepository.save(user);
//        sendEmail(email,userName);
        CreateAccountResponse response = new CreateAccountResponse();
        response.setUserId(user.getId());
        response.setMessage("Account Created Successfully");
        return response;
    }

    private boolean userNameExists(String userName) {
        return userRepository.existsByUsername(userName);
    }



    public boolean userExistsByPhoneNumberOrEmail(String email, String phoneNumber){
        return userRepository.existsByEmail(email) || userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public LoginResponse verifyUserWith(LoginRequest request) {
        String userName = request.getUsername();
        User user = userRepository.findByUsername(userName);
        UserRole role = user.getRole();
        Authentication authentication =
                authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()){
            LoginResponse response = new LoginResponse();
            response.setMessage("Success");
            response.setRole(role);
            String token = jwtService.generateToken(request.getUsername());
            response.setToken(token);
            response.setUserName(userName);
            return response;
        }
        LoginResponse response = new LoginResponse();
        response.setMessage("failed");
        return response;
    }

    @Override
    public SellerApplicationResponse applyToBeASellerWith(SellerApplicationRequest request) {
        Seller seller = mapSeller(request);
        sellerRepository.save(seller);
        return null;
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
        List<User> users = userRepository.findAll();
        for(User user : users){
            if(Objects.equals(user.getId(), userId)){
                System.out.println(user.toString());
                user.setProfilePicture(imageUrl);
                userRepository.save(user);
            }else throw new UserNotFoundException("User not found");
        }

    }

    public  User findUserById(Long userId){

        List<User> users = userRepository.findAll();
        for(User user : users){
            if(Objects.equals(user.getId(), userId)){
             return user;
            }
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public GetProfileResponse getUserprofile(Long userId) {
        User user = findUserById(userId);
        GetProfileResponse response = new GetProfileResponse();
        response.setUserName(user.getUsername());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAdress());
        response.setRole(String.valueOf(user.getRole()));
        response.setProfilePicture(user.getProfilePicture());
        response.setBio(user.getBio());
        return response;
    }

    public


}
