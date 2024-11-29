package com.freddie.marketplace.services;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.GetProfileResponse;
import com.freddie.marketplace.Exceptions.*;
import com.freddie.marketplace.data.model.AppUser;
import com.freddie.marketplace.data.model.modelEnums.CategoryType;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.SellerRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import com.freddie.marketplace.services.seller.SellerService;
import com.freddie.marketplace.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AppUserServiceImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerRepository sellerRepository;

    @BeforeEach
    public void setup(){
        productRepository.deleteAll();
        userRepository.deleteAll();
        sellerRepository.deleteAll();
    }

    @Autowired
    UserService userService;

    @Test
    public void testThatUserCanCreateANewAccount(){
        CreateAccountRequest request1 = createUser();
        CreateAccountResponse response = userService.createNewUser(request1);
        assertThat(response.getMessage()).isEqualTo("Account Created Successfully");

    }



    @Test
    public void testThatUserWithTHeSameEmailThrowsException(){
        CreateAccountRequest request = createUser();
        CreateAccountRequest request1 = createUser();
        CreateAccountResponse response = userService.createNewUser(request);
        assertThrows(EmailOrPhoneNumberExistsException.class, ()-> userService.createNewUser(request1));
    }

    @Test
    public void testThatUserHasAUniqueNumber(){
        CreateAccountRequest request = createUser();
        CreateAccountRequest request1 = createUser();
        CreateAccountResponse response = userService.createNewUser(request);
        assertThrows(EmailOrPhoneNumberExistsException.class, ()-> userService.createNewUser(request1));

    }

    @Test
    public void testThatUserHasAUniqueUserName(){
        CreateAccountRequest request = createUser();
        CreateAccountRequest request1 = createUser2();
        CreateAccountResponse response = userService.createNewUser(request);
        assertThrows(UsernameAlreadyExistsException.class, ()-> userService.createNewUser(request1));
    }
    @Test
    public void testThatSellerMustInputAllFieldsAreRequiredtoAddAProduct(){
        CreateAccountRequest request = createUser2();
        String userName = request.getUsername();
        CreateAccountResponse response = userService.createNewUser(request);
        AppUser appUser = userRepository.findByUsername(userName);
        AddProductRequest request1 = addNewProduct(appUser.getId());
        assertThrows(FieldsRequiredExecption.class, ()-> sellerService.addProduct(request1));
    }

    @Test
    public void testThatUserCanGetTheirProfile(){
        CreateAccountRequest request = createUser2();
        CreateAccountResponse response = userService.createNewUser(request);
        System.out.println(response.getUserId());
        GetProfileRequest request1 = new GetProfileRequest();
        request1.setUserId(response.getUserId());
        GetProfileResponse response1 = userService.getUserprofile(request1);
        System.out.println(response1.toString());
        assertThat(response1.getUserName()).isNotNull();

    }

    @Test
    public void testThatOnlySellerCanAddProduct(){
        CreateAccountRequest request = createUser2();
        CreateAccountResponse response = userService.createNewUser(request);
        String userName = request.getUsername();

        System.out.println(userName);
        AppUser appUser = userRepository.findByUsername(userName);
        AddProductRequest request1 = addNewProduct(appUser.getId());
        assertThrows(NotASellerException.class, ()-> sellerService.addProduct(request1));
    }

//    @Test
//    public void testThatUserCanApplyToBeASeller() throws IOException {
//        CreateAccountRequest request = createUser();
//        CreateAccountResponse response = userService.createNewUser(request);
//        SellerApplicationRequest request1 = applicationRequest();
//        SellerApplicationResponse response1 = userService.applyToBeASellerWith(request1);
//        assertThat(response1).isNotNull();
//    }


    public SellerApplicationRequest applicationRequest(){
        SellerApplicationRequest request = new  SellerApplicationRequest();
        request.setNin("27738383737373633663");
        request.setBvn("777282928900990999999");
        return request;
    }

    private AddProductRequest addNewProduct(Long id) {
        ArrayList <String> images = new ArrayList<>();
        AddProductRequest request = new AddProductRequest();
        request.setProductName("");
        request.setDescription("");
        request.setPrice(1000.0);
        request.setSeller_id(id);
        request.setProductType(CategoryType.FOOD);
        request.setStock(6);
        return request;
    }



    private CreateAccountRequest createUser() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setUsername("fareed");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPassword("olamide");
        request.setAddress("Badagry");
        request.setPhoneNumber("090223946768");
//        request.setProfilePicture("u");
        request.setBio("i am good trader");
        return request;
    }


    private CreateAccountRequest createUser2() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setUsername("ebuka");
        request.setEmail("freddie28100@gmail.com");
        request.setPassword("solar");
        request.setAddress("Badagry");
        request.setPhoneNumber("090227739467688");
        request.setProfilePicture("urlwt37373gffff.png");
        request.setBio("i am good trader");
        return request;
    }


    @Test
    public void testThatSellerCanAddProduct() throws IOException {
        CreateAccountRequest request = createUser2();
        String userName = request.getUsername();
        CreateAccountResponse response = userService.createNewUser(request);
        AppUser appUser = userRepository.findByUsername(userName);

        AddProductRequest request1 = addNewProduct(appUser.getId());
        AddProductResponse response1 = sellerService.addProduct(request1);
        assertThat(response1.getMessage()).isEqualTo("weed is added to your products successfully");
    }




}