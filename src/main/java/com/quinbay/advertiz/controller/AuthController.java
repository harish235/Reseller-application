package com.quinbay.advertiz.controller;


import com.quinbay.advertiz.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Object login(@RequestParam String username, @RequestParam String password){
        return userService.checkLogin(username, password);
    }
}
