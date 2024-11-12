package com.freddie.marketplace.Controllers;


import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.*;
import com.freddie.marketplace.services.image.ImageService;
import com.freddie.marketplace.services.seller.SellerService;
import com.freddie.marketplace.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@ModelAttribute AddProductRequest request){

        try{
            AddProductResponse response = sellerService.addProduct(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch(RuntimeException | IOException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }



    @PostMapping("/upload-picture/{userId}")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Long userId, @ModelAttribute PictureUploadRequest request){
        try{
            System.out.println(request.toString());
            String imageUrl = imageService.uploadImage(request.getFile());
            userService.updateUserProfilePicture(userId, imageUrl);
            return ResponseEntity.ok(new PictureUploadResponse(imageUrl));
        }catch (IOException exception){
            return ResponseEntity.status(500).body(new PictureUploadResponse("Image upload failed"));
        }

    }



    @PostMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestBody GetProfileRequest request) {
        try {
            System.out.println(request.toString());
            GetProfileResponse response = userService.getUserprofile(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), BAD_REQUEST);
        }
    }


    @PostMapping("/sellerApplication")
    public ResponseEntity<?> sellerApplication(@ModelAttribute SellerApplicationRequest request){
        try{
            System.out.println(request.toString());
            SellerApplicationResponse response = userService.applyToBeASellerWith(request);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new PictureUploadResponse("Image upload failed"));
        }

    }





}
