package com.freddie.marketplace.services.seller;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.Exceptions.NotASellerException;
import com.freddie.marketplace.data.model.CategoryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SellerServiceImplTest {
    @Autowired
    SellerService sellerService;


    @Test
    public void test_thatOnlySellerCanAddProduct() throws IOException {
        AddProductRequest request = addNewProduct(1L);
//        AddProductResponse response = sellerService.addProduct(request);
        assertThrows(NotASellerException.class, ()-> sellerService.addProduct(request));
    }

    @Test
    public void testThatSellerCanAddProduct() throws IOException {
        AddProductRequest request = addNewProduct(2L);
        System.out.println(request.toString());
        AddProductResponse response = sellerService.addProduct(request);
        assertThat(response.getMessage()).isEqualTo( """
        Your product is going through confirmation.
        We will send a notification to your Email when your
        product has been confirmed. Thank you for using RealMart!
        """);
    }

    private AddProductRequest addNewProduct(Long id) {
        ArrayList<String> images = new ArrayList<>();
        AddProductRequest request = new AddProductRequest();
        request.setProductName("weed");
        request.setDescription("A good weed to smoke ");
        request.setPrice(1000.0);
        request.setSeller_id(id);
        request.setProductType(CategoryType.FOOD);
        request.setStock(6);

        return request;
    }

}