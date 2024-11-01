package com.freddie.marketplace.data.repositories;

import com.freddie.marketplace.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    User findByEmail(String email);

    User findByUsername(String username);

    boolean existsByUsername(String userName);
}
