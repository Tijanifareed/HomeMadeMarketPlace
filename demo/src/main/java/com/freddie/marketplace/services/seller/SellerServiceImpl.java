package com.freddie.marketplace.services.seller;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.Exceptions.NotASellerException;
import com.freddie.marketplace.data.model.Product;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.model.UserRole;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.freddie.marketplace.utils.Mapper.addProductMapper;
import static com.freddie.marketplace.utils.Validators.validateRequestForProduct;


@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public AddProductResponse addProduct(AddProductRequest request1) {
        validateThatUserIsSeller(request1.getSeller_id());
        validateRequestForProduct(request1);
        Product product = addProductMapper(request1);
        productRepository.save(product);
        AddProductResponse response = new AddProductResponse();
        response.setMessage("""
                Your product is going through confirmation.
                We will send a notification to your Email when your
                product has been confirmed Thank you for using RealMart!!!
                """);
        return response;
    }


    private void validateThatUserIsSeller(Long sellerId) {
        List<User> users = userRepository.findAll();
        User user = null;
        for(User user1: users){
            if(Objects.equals(user1.getId(), sellerId)){
                if(user1.getRole() == UserRole.BUYER || user1.getRole() == null){
                    throw new NotASellerException("You need to be a Seller to perform this action");
                }
            }
        }

    }
}
