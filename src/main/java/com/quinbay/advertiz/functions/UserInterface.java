package com.quinbay.advertiz.functions;

import org.springframework.http.ResponseEntity;

public interface UserInterface {

    Object checkLogin(String Email, String password);
}
