package com.quinbay.advertiz.controller;


import com.quinbay.advertiz.functions.UserInterface;
import com.quinbay.advertiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserInterface userInterface;

    @PostMapping("/login")
    public Object login(@RequestParam String username, @RequestParam String password){
        return userInterface.checkLogin(username, password);
    }
}
