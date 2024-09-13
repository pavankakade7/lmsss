package com.zgcns.gravity.authmanager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zgcns.gravity.authmanager.dto.UserAuthDTO;
import com.zgcns.gravity.authmanager.entity.GravityUser;
import com.zgcns.gravity.authmanager.entity.Role;
import com.zgcns.gravity.authmanager.repository.AuthUserRepository;
import com.zgcns.gravity.authmanager.repository.RoleRepository;


import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthUserService {

	    @Autowired
	    private AuthUserRepository userRepository;

	    @Autowired
	    private RoleRepository roleRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Transactional
	    public void registerUser(UserAuthDTO userDTO) {
	    	  // Create a new user entity
	    	GravityUser newUser = new GravityUser();
	        newUser.setUsername(userDTO.getUsername());
	        newUser.setEmail(userDTO.getEmail());
	        newUser.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
	        newUser.setCreatedAt(LocalDateTime.now());
	        newUser.setUpdatedAt(LocalDateTime.now());
	        newUser.setActive(true);
	        newUser.setVerified(true);

	        // Fetch or create roles and associate them with the user
	        Set<Role> roles = new HashSet<>();
	        for (Role roleDTO : userDTO.getRoles()) {
	            Role role = roleRepository.findByName(roleDTO.getName())
	                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleDTO.getName()));
	            roles.add(role);
	        }
	        newUser.setRoles(roles);

	        userRepository.save(newUser);
	    }

	    }
    
