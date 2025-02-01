package com.freddie.marketplace.utils;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.DTOS.Requests.SellerApplicationRequest;
import com.freddie.marketplace.Exceptions.FieldsRequiredExecption;
import com.freddie.marketplace.data.model.*;
import com.freddie.marketplace.data.model.modelEnums.ApplicationStatus;
import com.freddie.marketplace.data.model.modelEnums.CategoryType;
import com.freddie.marketplace.data.model.modelEnums.ProductStatus;
import com.freddie.marketplace.data.model.modelEnums.UserRole;
import org.springframework.stereotype.Component;


@Component
public class Mapper {




    public static AppUser createUserMapper(CreateAccountRequest request){
        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        appUser.setEmail(request.getEmail());
        appUser.setPassword(request.getPassword());
        appUser.setPhoneNumber(request.getPhoneNumber());
        appUser.setRole(UserRole.BUYER);
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
        CategoryType productType = null;
        if (request.getProductType().equals("JEWELRY")) productType = CategoryType.JEWELRY;
        else if (request.getProductType().equals("CLOTHING")) productType = CategoryType.CLOTHING;
        else if(request.getProductType().equals("ARTANDCRAFTS")) productType = CategoryType.ARTANDCRAFTS;
        else if(request.getProductType().equals("BEAUTY")) productType = CategoryType.BEAUTY;
        else if (request.getProductType().equals("LEATHERGOODS")) productType= CategoryType.LEATHERGOODS;
        else if (request.getProductType().equals("FOOD")) productType = CategoryType.FOOD;
        else if (request.getProductType().equals("HOMEDECORATIONANDFURNITURE")) productType = CategoryType.HOMEDECORATIONANDFURNITURE;
        seller.setUserId(request.getUserId());
        seller.setStatus(ApplicationStatus.PENDING);
        seller.setProductDescription(request.getProductDescription());
        seller.setBusinessName(request.getBusinessName());
        if(productType != null)  seller.setProductType(productType);
        else throw new FieldsRequiredExecption("Product type can not be null");
        return seller;
    }




}
