//package com.freddie.marketplace.services.seller;
//
//import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
//import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
//import com.freddie.marketplace.data.model.CategoryType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//class SellerServiceImplTest {
//    @Autowired
//    SellerService sellerService;
//
//
//    @Test
//    public void test_thatOnlySellerCanAddProduct(){
//        AddProductRequest request = addNewProduct();
//
//    }
//
//    private AddProductRequest addNewProduct(Long id) {
//        ArrayList<String> images = new ArrayList<>();
//        AddProductRequest request = new AddProductRequest();
//        request.setProductName("weed");
//        request.setDescription("A good weed to smoke ");
//        request.setPrice(1000.0);
//        request.setSeller_id(id);
//        request.setProductType(CategoryType.FOOD);
//        request.setStock(6);
//        return request;
//    }
//
//}