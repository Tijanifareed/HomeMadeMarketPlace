package com.freddie.marketplace.services.jwt;

import com.freddie.marketplace.data.model.Admin;
import com.freddie.marketplace.data.model.User;
import com.freddie.marketplace.data.model.UserPrincipal;
import com.freddie.marketplace.data.repositories.AdminRepository;
import com.freddie.marketplace.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }


//    public UserDetails loadAdminByUsername(String userName) throws UsernameNotFoundException{
//        Admin admin = adminRepository.findByUsername(userName);
//        if(admin == null){
//            throw new UsernameNotFoundException("Admin account does not exists");
//        }
//        return new
//    }
}
