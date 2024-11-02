package com.freddie.marketplace.services;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Responses.CreateAccountResponse;
import com.freddie.marketplace.Exceptions.UsernameAlreadyExistsException;
import com.freddie.marketplace.data.model.CategoryType;
import com.freddie.marketplace.data.model.UserRole;
import com.freddie.marketplace.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.freddie.marketplace.Exceptions.EmailOrPhoneNumberExistsException;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
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
        request.setEmail("freddie2810@gmail.coms");
        request.setPassword("olamide");
        request.setAdress("Badagry");
        request.setPhoneNumber("090223946768");
        request.setRole(UserRole.BUYER);
        request.setProfilePicture("urlwt37373gfff.png");
        request.setBio("i am good trader");
        return request;
    }


    private CreateAccountRequest createUser2() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setUsername("fareed");
        request.setEmail("freddie2810@gmail.comsss");
        request.setPassword("olamide");
        request.setAdress("Badagry");
        request.setPhoneNumber("09022773946768");
        request.setRole(UserRole.SELLER);
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
    public void testThatSellerCanAddProduct(){
        CreateAccountRequest request = createUser2();
        CreateAccountResponse response = userService.createNewUser(request);
        AddProductRequest request1 = addNewProduct();
    }

    private AddProductRequest addNewProduct() {
        AddProductRequest request = new AddProductRequest();
        request.setProductName("weed");
        request.setDescription("A good weed to smoke ");
        request.setPrice(1000.0);
        request.setSeller_id(19L);
        request.setProductType(CategoryType.FOOD);
//        request.setImages(List<String> images);

    }


}