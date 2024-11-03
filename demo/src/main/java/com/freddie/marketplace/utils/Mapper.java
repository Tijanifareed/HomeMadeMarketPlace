package com.freddie.marketplace.utils;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.SellerApplicationRequest;
import com.freddie.marketplace.data.model.*;
import org.springframework.stereotype.Component;


@Component
public class Mapper {




    public static User createUserMapper(CreateAccountRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setAdress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(UserRole.BUYER);
        user.setProfilePicture(request.getProfilePicture());
        user.setBio(request.getBio());
        return user;
    }

    public static Product addProductMapper(AddProductRequest request){
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSellerId(request.getSeller_id());
        product.setCategory(request.getProductType());
        product.setImages(request.getImages());
        product.setStock(request.getStock());
        product.setStatus(ProductStatus.PENDING);
        return product;
    }

    public static Seller mapSeller(SellerApplicationRequest request){
        Seller seller = new Seller();
        seller.setUserId(request.getUserId());
        seller.setBvn(request.getBvn());
        seller.setNin(request.getNin());
        seller.setIdCardUrl(request.getIdCardUrl());
        seller.setPortfolio(request.getPortfolio());
        return seller;
    }






}
