package com.zgcns.gravity.authmanager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.zgcns.gravity.authmanager.entity.GravityUser;
import com.zgcns.gravity.authmanager.repository.AuthUserRepository;
import com.zgcns.gravity.lms.model.User;
import com.zgcns.gravity.lms.repositories.UserRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

/*	@Autowired
    private AuthUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GravityUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPasswordHash());
        //builder.authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return builder.build();
    }*/
	
	
	@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPassword());
        //builder.authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return builder.build();
    }
}