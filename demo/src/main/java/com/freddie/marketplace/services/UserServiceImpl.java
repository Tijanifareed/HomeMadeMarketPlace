package com.freddie.marketplace.services;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.LoginRequest;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.Exceptions.EmailOrPhoneNumberExistsException;
import com.freddie.marketplace.Exceptions.FieldsRequiredExecption;
import com.freddie.marketplace.Exceptions.NotASellerException;
import com.freddie.marketplace.Exceptions.UsernameAlreadyExistsException;
import com.freddie.marketplace.data.model.Product;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.model.UserRole;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
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
import java.util.Optional;

import static com.freddie.marketplace.utils.Mapper.createUserMapper;
import static com.freddie.marketplace.utils.Mapper.addProductMapper;
import static com.freddie.marketplace.utils.Validators.validateRequest;
import static com.freddie.marketplace.utils.Validators.validateRequestForProduct;





@Service
//@AllArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private JavaMailSender mailSender;

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
        sendEmail(email,userName);
        CreateAccountResponse response = new CreateAccountResponse();
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
    public AddProductResponse addProduct(AddProductRequest request1) {

        validateThatUserIsSeller(request1.getSeller_id());
        validateRequestForProduct(request1);
        Product product = addProductMapper(request1);
        productRepository.save(product);
        AddProductResponse response = new AddProductResponse();
        String productName = product.getProductName();
        response.setMessage(productName+" is added to your products successfully");
        return response;
    }

    private void validateThatUserIsSeller(Long sellerId) {
        List<User> users = userRepository.findAll();
        User user = null;
        for(User user1: users){
            if(Objects.equals(user1.getId(), sellerId)){
                if(user1.getRole() == UserRole.BUYER || user1.getRole() == null){
                    throw new NotASellerException("You need to be a Seller to perform this action");
                }
            }
        }

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


}
