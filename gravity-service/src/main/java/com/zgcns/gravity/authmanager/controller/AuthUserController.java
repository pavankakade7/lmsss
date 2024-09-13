package com.zgcns.gravity.authmanager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zgcns.gravity.authmanager.dto.UserAuthDTO;

import com.zgcns.gravity.authmanager.service.AuthUserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class AuthUserController {

	@Autowired
    private AuthUserService authuserService;

  //  @GetMapping
   // public List<User> getAllUsers() {
    //    return userService.getAllUsers();
   // }

   /* @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserAuthDTO userDTO) {
    	authuserService.registerUser(userDTO);
        return ResponseEntity.ok("User registered successfully");
    }
}