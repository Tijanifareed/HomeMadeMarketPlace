package com.freddie.marketplace.utils;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Requests.CreateAccountRequest;
import com.freddie.marketplace.Exceptions.FieldsRequiredExecption;

public class Validators {



    public static void validateRequest(CreateAccountRequest request){
        if(request.getUsername() == null || request.getEmail() == null || request.getPhoneNumber() == null || request.getUsername().isBlank()){
            throw new FieldsRequiredExecption("field required");
        } else if (request.getUsername().length() < 5 || request.getEmail().isEmpty() || request.getEmail().length() < 5 || request.getPhoneNumber().length() < 11) {
            throw new FieldsRequiredExecption("field required");
        }
    }


    public static void validateRequestForProduct(AddProductRequest request) {
        if(request.getProductName() == null || request.getProductName().isBlank()){
            throw new FieldsRequiredExecption("All fields are required");
        }
        else if(request.getDescription() == null || request.getDescription().isBlank()){
            throw new FieldsRequiredExecption("All fields are required");
        }
        else if(request.getPrice() == null || request.getPrice().isNaN()){
            throw new FieldsRequiredExecption("All fields are required");
        }
        else if(request.getSeller_id() == null){
            throw new FieldsRequiredExecption("All fields are required");
        }
        else if(request.getProductType() == null){
            throw new FieldsRequiredExecption("All fields are required");
        }
       else if (request.getStock() == 0) {
            throw new FieldsRequiredExecption("All fields are required");
        }
    }
}
