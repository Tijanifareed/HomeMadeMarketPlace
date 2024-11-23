package com.freddie.marketplace.utils;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.SellerApplicationRequest;
import com.freddie.marketplace.data.model.*;
import org.springframework.stereotype.Component;


@Component
public class Mapper {




    public static AppUser createUserMapper(CreateAccountRequest request){
        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        appUser.setEmail(request.getEmail());
        appUser.setPassword(request.getPassword());
        appUser.setAdress(request.getAddress());
        appUser.setPhoneNumber(request.getPhoneNumber());
        appUser.setRole(UserRole.BUYER);
        appUser.setProfilePicture(request.getProfilePicture());
        appUser.setBio(request.getBio());
        return appUser;
    }

    public static Product addProductMapper(AddProductRequest request){
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSellerId(request.getSeller_id());
        product.setCategory(request.getProductType());
        product.setStock(request.getStock());
        product.setStatus(ProductStatus.PENDING);

        return product;
    }

    public static Seller mapSeller(SellerApplicationRequest request){

        Seller seller = new Seller();
        seller.setUserId(request.getUserId());
        seller.setBvn(request.getBvn());
        seller.setNin(request.getNin());
        seller.setStatus(ApplicationStatus.PENDING);
        return seller;
    }




}
