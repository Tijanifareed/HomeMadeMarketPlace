package com.freddie.marketplace.services;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.LoginRequest;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.Exceptions.EmailOrPhoneNumberExistsException;
import com.freddie.marketplace.Exceptions.FieldsRequiredExecption;
import com.freddie.marketplace.Exceptions.UsernameAlreadyExistsException;
import com.freddie.marketplace.data.model.Product;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.model.UserRole;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.freddie.marketplace.utils.Mapper.createUserMapper;
import static com.freddie.marketplace.utils.Mapper.addProductMapper;



@Service
//@AllArgsConstructor
public class UserServiceImpl implements UserService{

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
        System.out.println(userRepository.existsByEmail(email));
        User user = createUserMapper(request);
        userRepository.save(user);
        CreateAccountResponse response = new CreateAccountResponse();
        response.setMessage("Account Created Successfully");
        return response;
    }

    private boolean userNameExists(String userName) {
        boolean exists = false;
        if(userRepository.existsByUsername(userName)) exists = true;
        else exists = false;

        return exists;
    }

    private void validateRequest(CreateAccountRequest request){
        if(request.getUsername() == null || request.getEmail() == null || request.getPhoneNumber() == null || request.getUsername().equals("")){
            throw new FieldsRequiredExecption("field required");
        } else if (request.getUsername().length() < 5 || request.getEmail().equals("")|| request.getEmail().length() < 5 || request.getPhoneNumber().length() < 11 || request.getPhoneNumber().equals(" ")) {
            throw new FieldsRequiredExecption("field required");
        }
    }

    public boolean userExistsByPhoneNumberOrEmail(String email, String phoneNumber){
        boolean exists = false;
        if(userRepository.existsByEmail(email) || userRepository.existsByPhoneNumber(phoneNumber)) exists = true;
        else exists = false;

        return exists;
    }

    @Override
    public LoginResponse verifyUserWith(LoginRequest request) {
        String userName = request.getUsername();
        User user = findUserByUserName(userName);
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
        Product product = addProductMapper(request1);
        productRepository.save(product);
        AddProductResponse response = new AddProductResponse();
        String productName = product.getProductName();
        response.setMessage(productName+" is added to your products successfully");
        return response;
    }

    private User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }


}
