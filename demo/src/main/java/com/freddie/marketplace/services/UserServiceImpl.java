package com.freddie.marketplace.services;

import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.LoginRequest;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.LoginResponse;
import com.freddie.marketplace.Exceptions.EmailExistsException;
import com.freddie.marketplace.Exceptions.IncorrectInformationException;
import com.freddie.marketplace.Exceptions.PhoneNumberExistsException;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.freddie.marketplace.utils.Mapper.createUserMapper;


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


    @Override
    public CreateAccountResponse createNewUser(CreateAccountRequest request) {
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();
        request.setPassword(encoder.encode(request.getPassword()));
        if(userRepository.existsByEmail(email) || userRepository.existsByPhoneNumber(phoneNumber)) throw new EmailExistsException("Email or PhoneNumber has already been used");
        System.out.println(userRepository.existsByEmail(email));
        User user = createUserMapper(request);
        userRepository.save(user);
        CreateAccountResponse response = new CreateAccountResponse();
        response.setMessage("Account Created Successfully");
        return response;
    }

    @Override
    public LoginResponse verifyUserWith(LoginRequest request) {
        Authentication authentication =
                authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()){
            LoginResponse response = new LoginResponse();
            response.setMessage("Sucess");
            String token = jwtService.generateToken(request.getUsername());
            response.setToken(token);
            return response;
        }
        LoginResponse response = new LoginResponse();
        response.setMessage("failed");
        return response;
    }


}
