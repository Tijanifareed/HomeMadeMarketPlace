package com.freddie.marketplace.services.seller;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.data.model.User;

import java.io.IOException;
import java.util.List;

public interface SellerService {
    AddProductResponse addProduct(AddProductRequest request1) throws IOException;
    void updateProductPicture(Long productId ,List<String> imageUrls);

}
