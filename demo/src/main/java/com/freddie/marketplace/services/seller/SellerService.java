package com.freddie.marketplace.services.seller;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.data.model.User;

public interface SellerService {
    AddProductResponse addProduct(AddProductRequest request1);
}
