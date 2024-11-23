package com.freddie.marketplace.services.products;

import com.freddie.marketplace.DTOS.Responses.DisplayApprovedProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    ProductService productService;

    @Test
    public void testThatOnlyProductThatHasBeenApprovedIsDisplayed(){
        DisplayApprovedProductResponse response = productService.displayApprovedproduct();
        assertThat(response.getProduct().size()).isEqualTo(1);
    }
}