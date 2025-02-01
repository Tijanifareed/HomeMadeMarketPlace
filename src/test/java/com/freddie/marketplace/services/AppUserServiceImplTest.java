package com.freddie.marketplace.services;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.GetProfileResponse;
import com.freddie.marketplace.DTOS.Responses.SellerApplicationResponse;
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
        request.setUsername("Fareeds");
        request.setPassword("Olamide");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPhoneNumber("08084562169");
        CreateAccountResponse response = userService.createNewUser(request);
        CreateAccountRequest request1 = createUser();
        request1.setUsername("Fareed");
        request1.setPassword("Olamide");
        request1.setEmail("fareedtijani2810@gmail.com");
        request1.setPhoneNumber("08084562160");
        assertThrows(EmailOrPhoneNumberExistsException.class, ()-> userService.createNewUser(request1));
        CreateAccountRequest request2 = createUser();
        request2.setUsername("Fareeda");
        request2.setPassword("Olamide");
        request2.setEmail("fareedtijani2810@gmail.com");
        request2.setPhoneNumber("080845621685");
        assertThrows(EmailOrPhoneNumberExistsException.class, ()-> userService.createNewUser(request2));

    }
    @Test
    public void testThatUserHasAUniqueNumber(){
        CreateAccountRequest request = createUser();
        request.setUsername("Fareeds");
        request.setPassword("Olamide");
        request.setEmail("fareedtijani2810@gmail.coms");
        request.setPhoneNumber("08084562163");
        CreateAccountResponse response = userService.createNewUser(request);
        CreateAccountRequest request1 = createUser();
        request1.setUsername("Fareed");
        request1.setPassword("Olamide");
        request1.setEmail("fareedtijani2810@gmail.coom");
        request1.setPhoneNumber("08084562163");
        assertThrows(EmailOrPhoneNumberExistsException.class, ()-> userService.createNewUser(request1));
        CreateAccountRequest request2 = createUser();
        request2.setUsername("Fareeda");
        request2.setPassword("Olamide");
        request2.setEmail("fareedtijani281@gmail.com");
        request2.setPhoneNumber("08084562163");
        assertThrows(EmailOrPhoneNumberExistsException.class, ()-> userService.createNewUser(request2));


    }
    @Test
    public void testThatUserHasAUniqueuserName(){
        CreateAccountRequest request = createUser();
        request.setUsername("Fareed");
        request.setPassword("Olamide");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPhoneNumber("090998877556");
        CreateAccountResponse response = userService.createNewUser(request);
        CreateAccountRequest request1 = createUser();
        request1.setUsername("Fareed");
        request1.setPassword("Olamide");
        request1.setEmail("fareedtijani2810@gmail.coms");
        request1.setPhoneNumber("090998877506");
        assertThrows(UsernameAlreadyExistsException.class, ()-> userService.createNewUser(request1));
        CreateAccountRequest request2 = createUser();
        request2.setUsername("Fareed");
        request2.setPassword("Olamide");
        request2.setEmail("fareedtijani2810@gmail.comms");
        request2.setPhoneNumber("090998877500");
        assertThrows(UsernameAlreadyExistsException.class, ()-> userService.createNewUser(request2));
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
    public void testThatAllFieldsMustBeAddedBeforeSellerCanAddProduct(){
        CreateAccountRequest request = createUser();
        request.setUsername("Fareed");
        request.setPassword("Olamide");
        request.setEmail("fareedtijani2810@gmail.com");
        request.setPhoneNumber("090998877556");
        CreateAccountResponse response = userService.createNewUser(request);
        String userName = request.getUsername();
        System.out.println(userName);
        AppUser appUser = userRepository.findByUsername(userName);
        AddProductRequest request1 = addNewProduct(appUser.getId());
        assertThrows(FieldsRequiredExecption.class, ()-> sellerService.addProduct(request1));
    }
    @Test
    public void testThatUserCanApplyToBeASeller() throws IOException {
        CreateAccountRequest request = createUser();
        CreateAccountResponse response = userService.createNewUser(request);
        AppUser user = userRepository.findByUsername(request.getUsername());
        SellerApplicationRequest request1 = new SellerApplicationRequest();
        request1.setBusinessName("Freddie Cosmetics");
        request1.setProductType("BEAUTY");
        request1.setUserId(user.getId());
        request1.setProductDescription("cream cheap");
        SellerApplicationResponse response1 = userService.applyToBeASellerWith(request1);
        assertThat(response1).isNotNull();
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
        request.setPhoneNumber("090223946768");
//        request.setProfilePicture("u");
        return request;
    }


    private CreateAccountRequest createUser2() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setUsername("ebuka");
        request.setEmail("freddie28100@gmail.com");
        request.setPassword("solar");
        request.setPhoneNumber("090227739467688");
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