package com.freddie.marketplace.services.admin;

import com.freddie.marketplace.data.model.ApplicationStatus;
import com.freddie.marketplace.data.model.Seller;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    SellerRepository sellerRepository;
    @Override
    public List<Seller> sellerApplicants() {
        List<Seller> sellerApplicant = sellerRepository.findAll();
        List<Seller> pendingSellers = new ArrayList<Seller>();
        for(Seller seller : sellerApplicant){
            if(seller.getStatus() == ApplicationStatus.PENDING){
                pendingSellers.add(seller);
            }
        }

        return pendingSellers;
    }
}
