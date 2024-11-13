package com.freddie.marketplace.data.repositories;

import com.freddie.marketplace.data.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String userName);
}
