package com.freddie.marketplace.Controllers;


import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.LoginRequest;
import com.freddie.marketplace.DTOS.Requests.PictureUploadRequest;
import com.freddie.marketplace.DTOS.Responses.*;
import com.freddie.marketplace.services.image.ImageService;
import com.freddie.marketplace.services.seller.SellerService;
import com.freddie.marketplace.services.user.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
//@CrossOrigin(origins = "*")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    private final ImageService imageService;

    @Autowired
    public UserController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateAccountRequest request){
        try {
            System.out.print(request.toString());
            CreateAccountResponse response = userService.createNewUser(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch(RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/loginPage")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try {
            System.out.println(request.toString());
            LoginResponse response = userService.verifyUserWith(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest request){
        try{
            AddProductResponse response = sellerService.addProduct(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch(RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }



    @PostMapping("/upload-picture/{userId}")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Long userId, @ModelAttribute PictureUploadRequest request){
        try{
            String imageUrl = imageService.uploadImage(request.getFile());
            userService.updateUserProfilePicture(userId, imageUrl);
            return ResponseEntity.ok(new ProfilePictureUploadResponse(imageUrl));
        }catch (IOException exception){
            return ResponseEntity.status(500).body(new ProfilePictureUploadResponse("Image upload failed"));
        }

    }


}