package com.freddie.marketplace.data.repositories;

import com.freddie.marketplace.data.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
//    User findUserById(Long id);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    AppUser findByEmail(String email);

    AppUser findByUsername(String username);


    boolean existsByUsername(String userName);
}
