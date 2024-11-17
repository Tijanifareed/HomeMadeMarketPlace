package com.freddie.marketplace.DTOS.Responses;

import com.freddie.marketplace.data.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DisplayApprovedProductResponse {
    private List<Product> product;
}
