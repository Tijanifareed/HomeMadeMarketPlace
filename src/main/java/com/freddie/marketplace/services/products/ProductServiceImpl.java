package com.freddie.marketplace.services.products;


import com.freddie.marketplace.DTOS.Responses.DisplayApprovedProductResponse;
import com.freddie.marketplace.Exceptions.ThereAreNoProductsException;
import com.freddie.marketplace.data.model.Product;
import com.freddie.marketplace.data.model.modelEnums.ProductStatus;
import com.freddie.marketplace.data.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public DisplayApprovedProductResponse displayApprovedproduct() {
        List<Product> products = productRepository.findAll();
        List<Product> approvedproducts = new ArrayList<>();
        for(Product product : products){
            if(product.getStatus() == ProductStatus.SUCCESSFUL){
                approvedproducts.add(product);
                DisplayApprovedProductResponse response = new DisplayApprovedProductResponse();
                response.setProduct(approvedproducts);
                return response;
            }
        }
        throw new ThereAreNoProductsException("There are no products to view");
    }
}
