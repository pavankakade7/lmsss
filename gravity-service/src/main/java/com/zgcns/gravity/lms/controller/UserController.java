package com.zgcns.gravity.lms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zgcns.gravity.authmanager.dto.UserAuthDTO;
import com.zgcns.gravity.lms.dto.UserDTO;
import com.zgcns.gravity.lms.exception.UserNotFoundException;
import com.zgcns.gravity.lms.services.UserService;

@RestController
//@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/adduser")
    public String addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/authuser")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody UserDTO userDTO) {
        return userService.authenticateUser(userDTO);
    }

    @PutMapping("/updateuser/{userId}")
    public UserDTO updateUser(@PathVariable("userId") Long userId, @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

//    @GetMapping("/by-email")
//    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
//        try {
//            UserDTO userDTO = userService.getUserByEmail(email);
//            return ResponseEntity.ok(userDTO);
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(404).body(null);
//        }
//    }

    @GetMapping("/{userId}")
    public UserDTO getUserByUserId(@PathVariable("userId") Long userId) {
        return userService.getUserByUserId(userId);
    }

    @GetMapping("/firstname")
    public UserDTO getUserByFirstName(@RequestParam String firstName) {
        return userService.getUserByFirstName(firstName);
    }

    @GetMapping("/allusers")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
