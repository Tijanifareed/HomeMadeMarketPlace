package com.freddie.marketplace.services.seller;

import com.freddie.marketplace.DTOS.Requests.AddProductRequest;
import com.freddie.marketplace.DTOS.Responses.AddProductResponse;
import com.freddie.marketplace.Exceptions.NotASellerException;
import com.freddie.marketplace.Exceptions.UserNotFoundException;
import com.freddie.marketplace.data.model.*;
import com.freddie.marketplace.data.model.modelEnums.ApplicationStatus;
import com.freddie.marketplace.data.model.modelEnums.UserRole;
import com.freddie.marketplace.data.repositories.ProductRepository;
import com.freddie.marketplace.data.repositories.SellerRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import com.freddie.marketplace.services.image.ImageService;
import com.freddie.marketplace.services.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.freddie.marketplace.utils.Mapper.addProductMapper;
import static com.freddie.marketplace.utils.Validators.validateRequestForProduct;


@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private final ImageService imageService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private SellerRepository sellerRepository;

    public SellerServiceImpl(ImageService imageService) {
        this.imageService = imageService;
    }



        @Override
        public AddProductResponse addProduct(AddProductRequest request1) throws IOException {
            System.out.println(request1.toString());
            validateRequestForProduct(request1);
            checkThatSellerExists(request1.getSeller_id());
            validateThatOnlySellerCanAddProduct(request1.getSeller_id());

            // Map the request to a Product entity and save it
            Product product = addProductMapper(request1);

            productRepository.save(product);
            System.out.println("Product ID: " + product.getId());


            List<String> imageUrls = new ArrayList<>();

            for (MultipartFile file : request1.getFile()) {
                String imageUrl = imageService.uploadImage(file);
                imageUrls.add(imageUrl);
            }

            updateProductPicture(product.getId(), imageUrls);

            AddProductResponse response = new AddProductResponse();
            response.setMessage("""
        Your product is going through confirmation.
        We will send a notification to your Email when your
        product has been confirmed. Thank you for using RealMart!
        """);

            return response;

        }


        public void validateThatOnlySellerCanAddProduct(Long sellerId){
            Optional<Seller> seller = sellerRepository.findById(sellerId);
            if(seller.get().getStatus() != ApplicationStatus.APPROVED){
                throw new NotASellerException("Apply to ba a seller to have access to this feature!!");
            }
        }




    @Override
    public void updateProductPicture(Long productId, List<String> imageUrls) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            List<String> images = product.getImages();

            // Add all new URLs to the existing images list
            images.addAll(imageUrls);

            // Set the updated images list back to the product
            product.setImages(images);

            // Save the updated product
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
    }




    private void validateThatUserIsSeller(Long sellerId) {
        List<AppUser> appUsers = userRepository.findAll();
        AppUser appUser = null;
        for(AppUser appUser1 : appUsers){
            if(Objects.equals(appUser1.getId(), sellerId)){
                if(appUser1.getRole() == UserRole.BUYER || appUser1.getRole() == null){
                    throw new NotASellerException("You need to be a Seller to perform this action");
                }
            }
        }

    }


    public void checkThatSellerExists(Long sellerId){
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        if(seller.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
    }
}
