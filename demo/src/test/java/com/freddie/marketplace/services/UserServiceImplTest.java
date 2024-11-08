package com.freddie.marketplace.services;

import com.freddie.marketplace.DTOS.Requests.*;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.DTOS.Responses.GetProfileResponse;
import com.freddie.marketplace.DTOS.Responses.SellerApplicationResponse;
import com.freddie.marketplace.Exceptions.FieldsRequiredExecption;
import com.freddie.marketplace.Exceptions.NotASellerException;
import com.freddie.marketplace.Exceptions.UsernameAlreadyExistsException;
import com.freddie.marketplace.data.model.CategoryType;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.SellerRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import com.freddie.marketplace.services.seller.SellerService;
import com.freddie.marketplace.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.freddie.marketplace.Exceptions.EmailOrPhoneNumberExistsException;
import java.io.IOException;
import java.util.ArrayList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceImplTest {
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
        request.setUsername("fareed");
        request.setEmail("freddie2810@gmail.com");
        request.setPassword("olamide");
        request.setAddress("Badagry");
        request.setPhoneNumber("09022773946768");
        request.setProfilePicture("urlwt37373gfff.png");
        request.setBio("i am good trader");
        return request;
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
    public void testThatSellerCanAddProduct() throws IOException {
        CreateAccountRequest request = createUser2();
        String userName = request.getUsername();
        User user = userRepository.findByUsername(userName);
        CreateAccountResponse response = userService.createNewUser(request);
        AddProductRequest request1 = addNewProduct(user.getId());
        AddProductResponse response1 = sellerService.addProduct(request1);
        assertThat(response1.getMessage()).isEqualTo("weed is added to your products successfully");
    }

    private AddProductRequest addNewProduct(Long id) {
        ArrayList <String> images = new ArrayList<>();
        AddProductRequest request = new AddProductRequest();
        request.setProductName("weed");
        request.setDescription("A good weed to smoke ");
        request.setPrice(1000.0);
        request.setSeller_id(id);
        request.setProductType(CategoryType.FOOD);
        request.setStock(6);
        return request;
    }

    @Test
    public void testThatUserMustInputAllFieldsRequired(){
        CreateAccountRequest request = createUser2();
        String userName = request.getUsername();
        User user = userRepository.findByUsername(userName);
        CreateAccountResponse response = userService.createNewUser(request);
        AddProductRequest request1 = addNewProduct(user.getId());
//        AddProductResponse response1 = userService.addProduct(request1);
        assertThrows(FieldsRequiredExecption.class, ()-> sellerService.addProduct(request1));
    }


    @Test
    public void testThatOnlyUserCanAddProduct(){
        CreateAccountRequest request = createUser2();
        CreateAccountResponse response = userService.createNewUser(request);
        String userName = request.getUsername();

        System.out.println(userName);
        User user = userRepository.findByUsername(userName);
        AddProductRequest request1 = addNewProduct(user.getId());
        assertThrows(NotASellerException.class, ()-> sellerService.addProduct(request1));
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


//    @Test
//    public void testThatWhenAUserApplyToBeASellerItsOnPendingByDefault(){
//        CreateAccountRequest request1 = createUser2();
//        CreateAccountResponse response = userService.createNewUser(request1);
//        SellerApplicationRequest request = new SellerApplicationRequest();
//        request.setUserId(response.getUserId());
//        request.setBvn("hhdue887ye78777d8nd8en88");
//        request.setNin("77e8e7e7774747h7r7r648jj8eje8eey8");
//        request.setPortfolio("etette6e67e77hd77de7h");
//        request.setIdCardUrl("eycbn xaxdf ch dz");
//        SellerApplicationResponse response1 = userService.applyToBeASellerWith(request);
//        assertThat(response1.getMessage()).isEqualTo("Your application has been received and is going under review an email will be sent to you to know your status");
//    }

}