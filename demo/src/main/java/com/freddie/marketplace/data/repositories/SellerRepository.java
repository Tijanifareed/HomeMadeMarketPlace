package com.freddie.marketplace.data.repositories;

import com.freddie.marketplace.data.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

}
